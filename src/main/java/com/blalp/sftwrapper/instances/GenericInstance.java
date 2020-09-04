package com.blalp.sftwrapper.instances;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import com.blalp.sftwrapper.interfaces.IJoinable;
import com.blalp.sftwrapper.util.Config;
import com.blalp.sftwrapper.util.Download;
import com.blalp.sftwrapper.util.JoinableProcess;
import com.blalp.sftwrapper.util.JoinableThread;
import com.blalp.sftwrapper.util.UserErrorExepction;

import oshi.SystemInfo;

public abstract class GenericInstance implements Runnable {
    protected long reserveForSystem = 1250000000;
    protected long systemRAM;
    protected long usableRAM;
    protected long idealRAM;
    protected String jvmArgs = null;
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
        if (usableRAM<=idealRAM) {
            jvmArgs = "-XX:+UseG1GC -Dsun.rmi.dgc.server.gcInterval=2147483646 -XX:+UnlockExperimentalVMOptions -XX:G1NewSizePercent=20 -XX:MaxGCPauseMillis=50 -XX:G1HeapRegionSize=32M";
            return usableRAM;// YIKES LOW RAM
        }
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
                new JoinableProcess(Runtime.getRuntime().exec(Config.path.getFileMultiMCBinary()+" -I "+getLatestZIP().replace('\\', '/'))).join();
                writeFiles();
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
		return new File(Config.path.getPathMultiMC()+File.separatorChar+"instances"+File.separatorChar+getBackEndInstanceName()).exists();
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
    public void writeFiles() throws IOException {
        String basedir = Config.path.getPathMultiMC()+File.separatorChar+"instances"+File.separatorChar+getBackEndInstanceName()+File.separatorChar;
        PrintWriter printWriter = new PrintWriter(new FileWriter(basedir+"instance.cfg",true));
        if(jvmArgs!=null){
            printWriter.write("OverrideJavaArgs=true\n");
            printWriter.write("JvmArgs="+jvmArgs+"\n");
        }
        printWriter.write("MaxMemAlloc="+getOptimumRAM()/1000000+"\n");
        printWriter.write("MinMemAlloc="+getOptimumRAM()/1000000+"\n");
        printWriter.write("OverrideMemory=true"+"\n");
        printWriter.write("VersionMajor="+getVersion()[0]+"\n");
        printWriter.write("VersionMinor="+getVersion()[1]+"\n");
        printWriter.write("VersionPath="+getVersion()[2]+"\n");
        printWriter.close();
    }
}
