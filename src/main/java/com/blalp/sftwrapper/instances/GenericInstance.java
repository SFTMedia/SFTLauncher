package com.blalp.sftwrapper.instances;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import com.blalp.sftwrapper.interfaces.IJoinable;
import com.blalp.sftwrapper.util.Config;
import com.blalp.sftwrapper.util.Download;
import com.blalp.sftwrapper.util.JoinableFake;
import com.blalp.sftwrapper.util.JoinableProcess;
import com.blalp.sftwrapper.util.JoinableThread;
import com.blalp.sftwrapper.util.UserErrorExepction;

import oshi.SystemInfo;

public abstract class GenericInstance implements Runnable {
    protected long reserveForSystem = 1250000000;
    protected long systemRAM;
    protected long usableRAM;
    protected long idealRAM;
    public GenericInstance() {
        SystemInfo systemInfo = new SystemInfo();
        systemRAM = systemInfo.getHardware().getMemory().getTotal();
        usableRAM = systemRAM-reserveForSystem;
        idealRAM = (long) ((systemRAM - getMinimumRAM()) * .65 + getMinimumRAM());
    }
    public long getOptimumRAM() {
        // Memory is in bytes
        if (usableRAM>=getMaximumRAM())
            return getMaximumRAM();
        if (usableRAM<=getMinimumRAM()) {
            try {
                throw new UserErrorExepction("Insufficent RAM for modpack.");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return -1; // This shouldn't happen
        }
        if (usableRAM<=idealRAM)
            return usableRAM;// YIKES LOW RAM
        return idealRAM;
        
    }

    public boolean canRun() {
        return usableRAM>=getMinimumRAM();
    }

    public abstract long getMinimumRAM();
    public long getMaximumRAM() {
        return getMinimumRAM()*2;
    }
    public abstract String getURL();
    public IJoinable install() {
        Thread thread = new Thread(this);
        thread.start();
        return new JoinableThread(thread);
    }
    public void run() {
        if (!new File(Config.path.getPathMultiMC()+File.separatorChar+"instances"+File.separatorChar+getBackEndInstanceName()).exists()){
            String folder = Config.path.getPathInstanceCache()+File.separatorChar+getBackEndInstanceName();
            new File(folder).mkdirs();
            Download download = new Download(getURL(),folder+File.separatorChar+getBackEndInstanceName()+".zip");
            download.start().join();
            try {
                new JoinableProcess(Runtime.getRuntime().exec(Config.path.getFileMultiMCBinary()+" -I "+getLatestZIP())).join();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public abstract String getInstanceName();
    public String getBackEndInstanceName(){
        return getInstanceName().replaceAll("[ +]", "");
    }
	public String getLatestZIP() {
        File latest=null;
        for (File file : new File(Config.path.getPathInstanceCache()+File.separatorChar+getBackEndInstanceName()).listFiles()) {
            if (latest==null||file.lastModified()>latest.lastModified()) {
                latest=file;
            }
        }
		return latest.getAbsolutePath();
	}
	public boolean isInstalled() {
		return false;
    }
    public abstract String getPathRelativeMinecraftDir(); //Sometimes this will be .minecraft and sometimes just minecraft.
    public void moveFromAssets(String file) {
        try {
            Files.copy(getClass().getResourceAsStream("/assets/"+getBackEndInstanceName()+File.separatorChar+file),new File(Config.path.getPathMultiMC()+File.separatorChar+"instances"+File.separatorChar+getBackEndInstanceName()+File.separatorChar+getPathRelativeMinecraftDir()+File.separatorChar+file).toPath(),StandardCopyOption.REPLACE_EXISTING); } 
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public abstract int[] getVersion();// {MAJOR,MINOR,PATCH}
}
