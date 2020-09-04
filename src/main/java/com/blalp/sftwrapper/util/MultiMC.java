package com.blalp.sftwrapper.util;

import java.io.File;

/**
 * MultiMC
 */
public class MultiMC {

    public static boolean isInstalled() {
        return new File(Config.path.getPathRoot(), "MultiMC").exists();
    }

    public static void install() {
        // The MultiMC Archive has a MultiMC folder already in it.
        new ExtractedDownload(Config.path.getURLMultiMC(), Config.path.getPathRoot() +File.separatorChar+ "MultiMC."+Config.path.getMultiMCArchiveFormat(),Config.path.getPathMultiMC()+"../");
    }
}