package com.blalp.sftwrapper.instances;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntryPredicate;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.compress.utils.IOUtils;

import oshi.SystemInfo;
import oshi.driver.unix.solaris.disk.Iostat;

public abstract class GenericInstance implements Runnable {
    protected long reserveForSystem = 1250000000;
    protected long systemRAM;
    protected long usableRAM;
    protected long idealRAM;
    protected String jvmArgs = null;

    public GenericInstance() {
        SystemInfo systemInfo = new SystemInfo();
        systemRAM = systemInfo.getHardware().getMemory().getTotal();
        usableRAM = systemRAM - reserveForSystem;
        idealRAM = (long) ((systemRAM - getMinimumRAM()) * .65 + getMinimumRAM());
    }

    public long getOptimumRAM() {
        // Memory is in bytes
        if (usableRAM >= getMaximumRAM())
            return getMaximumRAM();
        if (usableRAM <= getMinimumRAM()) {
            try {
                throw new UserErrorExepction("Insufficent RAM for modpack.");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return -1; // This shouldn't happen
        }
        if (usableRAM <= idealRAM) {
            jvmArgs = "-XX:+UseG1GC -Dsun.rmi.dgc.server.gcInterval=2147483646 -XX:+UnlockExperimentalVMOptions -XX:G1NewSizePercent=20 -XX:MaxGCPauseMillis=50 -XX:G1HeapRegionSize=32M";
            return usableRAM;// YIKES LOW RAM
        }
        return idealRAM;

    }

    public boolean canRun() {
        return usableRAM >= getMinimumRAM();
    }

    public abstract long getMinimumRAM();

    public long getMaximumRAM() {
        return getMinimumRAM() * 2;
    }

    public abstract String getURL();

    public IJoinable install() {
        Thread thread = new Thread(this);
        thread.start();
        return new JoinableThread(thread);
    }

    public void run() {
        if (!new File(Config.path.getPathMultiMC() + File.separatorChar + "instances" + File.separatorChar
                + getBackEndInstanceName()).exists()) {
            String folder = Config.path.getPathInstanceCache() + File.separatorChar + getBackEndInstanceName();
            new File(folder).mkdirs();
            Download download = new Download(getURL(), folder + File.separatorChar + getURL().replaceAll("^.*/", ""));
            download.start().join();
            writeFiles();
            try {
                Runtime.getRuntime()
                        .exec(Config.path.getFileMultiMCBinary() + " -I " + getLatestZIP().replace('\\', '/'));
            } catch (IOException e) {
                e.printStackTrace();
            }
            File dir = new File(Config.path.getPathMultiMC() + File.separatorChar + "instances" + File.separatorChar
                    + getBackEndInstanceName() + File.separatorChar + getPathRelativeMinecraftDir()
                    + File.separatorChar);
            while (!dir.exists()) { // Wait until the directory is created on its own
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public abstract String getInstanceName();

    public String getBackEndInstanceName() {
        return getInstanceName().replaceAll("[ +]", "");
    }

    public String getLatestZIP() {
        File latest = null;
        for (File file : new File(Config.path.getPathInstanceCache() + File.separatorChar + getBackEndInstanceName())
                .listFiles()) {
            if (latest == null || file.lastModified() > latest.lastModified()) {
                latest = file;
            }
        }
        return latest.getAbsolutePath();
    }

    public boolean isInstalled() {
        return new File(Config.path.getPathMultiMC() + File.separatorChar + "instances" + File.separatorChar
                + getBackEndInstanceName()).exists();
    }

    public abstract String getPathRelativeMinecraftDir(); // Sometimes this will be .minecraft and sometimes just
                                                          // minecraft.

    public void moveFromAssets(String file) {
        try {
            new File(Config.path.getPathMultiMC() + File.separatorChar + "instances" + File.separatorChar
                    + getBackEndInstanceName() + File.separatorChar + getPathRelativeMinecraftDir()).mkdirs();
            Files.copy(getClass().getResourceAsStream("/assets/" + getBackEndInstanceName() + "/" + file),
                    new File(Config.path.getPathMultiMC() + File.separatorChar + "instances" + File.separatorChar
                            + getBackEndInstanceName() + File.separatorChar + getPathRelativeMinecraftDir()
                            + File.separatorChar + file).toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract int[] getVersion();// {MAJOR,MINOR,PATCH}

    public void writeFiles() {
        String data = "";
        if(jvmArgs!=null){
            data+="OverrideJavaArgs=true\n";
            data+="JvmArgs="+jvmArgs+"\n";
        }
        data+="InstanceType="+getInstanceType()+"\n";
        data+="name="+getInstanceName()+"\n";
        data+="iconKey="+getIcon()+"\n";
        data+="VersionPath="+getVersion()[2]+"\n";
        data+="MaxMemAlloc="+getOptimumRAM()/1000000+"\n";
        data+="MinMemAlloc="+getOptimumRAM()/1000000+"\n";
        data+="OverrideMemory=true"+"\n";
        data+="VersionMajor="+getVersion()[0]+"\n";
        data+="VersionMinor="+getVersion()[1]+"\n";
        data+="VersionPath="+getVersion()[2]+"\n";
        try {
            ZipArchiveEntry entry = new ZipArchiveEntry("instance.cfg");
            entry.setSize(data.getBytes("UTF-8").length);
            ZipFile inputStream = new ZipFile(new File(getLatestZIP()));
            ZipArchiveOutputStream zip = new ZipArchiveOutputStream(new File(Config.path.getPathInstanceCache() + File.separatorChar + getBackEndInstanceName()+File.separatorChar+getBackEndInstanceName()+".zip"));
            zip.setUseZip64(Zip64Mode.Always);
            inputStream.copyRawEntries(zip, new ZipArchiveEntryPredicate(){

                @Override
                public boolean test(ZipArchiveEntry zipArchiveEntry) {
                    return true;
                }
                
            });
            zip.putArchiveEntry(entry);
            zip.write(data.getBytes("UTF-8"));
            zip.closeArchiveEntry();
            zip.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public String getIcon() {
        return "steve";
    }
    public abstract String getInstanceType();
}
