package com.blalp.sftwrapper.platforms.Generic;

import java.io.File;

import com.blalp.sftwrapper.interfaces.IPath;
import com.blalp.sftwrapper.platforms.Linux.LinuxConfig;

/**
 * GenericConfig
 */
public abstract class GenericConfig implements IPath {
    @Override
    public String getPathRoot() {
        return System.getProperty("user.home")+File.separatorChar+".SFTLauncher";
    }

    @Override
    public String getPathMultiMC(){
        return getPathRoot()+File.separatorChar+"MultiMC";
    }

    public static IPath getRevelventPath(){
        return new LinuxConfig();
    }
    public void mkdirs() {
        new File(getPathInstanceCache()).mkdirs();
        new File(getPathMultiMC()).mkdirs();
        new File(getPathRoot()).mkdirs();
    }

    @Override
    public String getPathInstanceCache() {
        return getPathRoot()+File.separatorChar+"instanceCache";
    }
}