package com.blalp.sftwrapper.util;

import java.io.File;
import java.io.IOException;

import com.blalp.sftwrapper.interfaces.IJoinable;

import oshi.SystemInfo;
import oshi.software.os.OSProcess;

/**
 * MultiMC
 */
public class MultiMC implements Runnable {

    public static boolean isInstalled() {
        return new File(Config.path.getPathRoot(), "MultiMC").listFiles().length != 0;
    }

    public static IJoinable install() {
        // The MultiMC Archive has a MultiMC folder already in it.
        return new ExtractedDownload(Config.path.getURLMultiMC(),
                Config.path.getPathRoot() + '/' + "MultiMC." + Config.path.getMultiMCArchiveFormat(),
                Config.path.getPathMultiMCExtract()).start();
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