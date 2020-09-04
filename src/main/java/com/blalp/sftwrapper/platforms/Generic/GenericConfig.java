package com.blalp.sftwrapper.platforms.Generic;

import java.io.File;

import com.blalp.sftwrapper.interfaces.IPath;

/**
 * GenericConfig
 */
public abstract class GenericConfig implements IPath {

    @Override
    public String getFilePath() {
        return System.getProperty("user.home")+File.pathSeparatorChar+"SFTInstaller";
    }

    @Override
    public String getMultiMCPath() {
        return getFilePath()+File.pathSeparatorChar+"MultiMC";
    }

    
}