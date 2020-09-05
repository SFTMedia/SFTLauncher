package com.blalp.sftwrapper.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import com.blalp.sftwrapper.instances.GenericInstance;
import com.blalp.sftwrapper.interfaces.IJoinable;

import oshi.SystemInfo;

/**
 * MultiMC
 */
public class MultiMC implements Runnable {

    public static boolean isInstalled() {
        return new File(Config.path.getPathRoot(), "MultiMC").listFiles().length != 0;
    }

    public static IJoinable install() {
        new ExtractedDownload(Config.path.getURLMultiMC(),
                Config.path.getPathRoot() + File.separatorChar + "MultiMC." + Config.path.getMultiMCArchiveFormat(),
                Config.path.getPathMultiMCExtract()).start().join();
        new File(Config.path.getPathMultiMCExtract()).mkdirs();
        try {
            Files.copy(MultiMC.class.getClass().getResourceAsStream("/assets/MultiMC/multimc.cfg"),
                    new File(Config.path.getPathMultiMC() + File.separatorChar + "multimc.cfg").toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
            PrintWriter printWriter = new PrintWriter(new FileWriter(Config.path.getPathMultiMC() + File.separatorChar + "multimc.cfg",true));
            printWriter.write("LastHostname="+new SystemInfo().getOperatingSystem().getNetworkParams().getHostName()+"\n");
            printWriter.write("MaxMemAlloc="+GenericInstance.minIdealRAM+"\n");
            printWriter.write("MinMemAlloc="+GenericInstance.maxIdealRAM+"\n");
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // The MultiMC Archive has a MultiMC folder already in it.
        return new JoinableFake();
    }

    public static IJoinable start() {
        Thread thread = new Thread(new MultiMC());
        thread.start();
        return new JoinableThread(thread);
    }

    @Override
    public void run() {
        // Start MultiMC and wait for the notifications file to be written
        try {
            new JoinableProcess(Runtime.getRuntime().exec(Config.path.getFileMultiMCBinary())).join();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}