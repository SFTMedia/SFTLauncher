package com.blalp.sftwrapper.util;

import java.io.File;

/**
 * MultiMC
 */
public class MultiMC {

    public boolean isInstalled() {
        return new File(Config.path.getFilePath(), "MultiMC").exists();
    }

    public void install() {
        new Download(Config.path.getMultiMCURL(), Config.path.getFilePath() + "multimc.archive");
    }

    public void extract() {
        // CompressInput
    }
}