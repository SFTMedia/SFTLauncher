package com.blalp.sftwrapper.util;

import java.io.File;

import com.blalp.sftwrapper.interfaces.IJoinable;

/**
 * MultiMC
 */
public class MultiMC {

    public static boolean isInstalled() {
        return new File(Config.path.getPathRoot(), "MultiMC").listFiles().length!=0;
    }

    public static IJoinable install() {
        // The MultiMC Archive has a MultiMC folder already in it.
        return new ExtractedDownload(Config.path.getURLMultiMC(), Config.path.getPathRoot() +File.separatorChar+ "MultiMC."+Config.path.getMultiMCArchiveFormat(),Config.path.getPathMultiMCExtract()).start();
    }
}