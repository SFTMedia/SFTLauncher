package com.blalp.sftwrapper.platforms.Generic;

import java.io.File;

import com.blalp.sftwrapper.interfaces.IPath;
import com.blalp.sftwrapper.platforms.Linux.LinuxConfig;
import com.blalp.sftwrapper.platforms.Mac.MacConfigs;
import com.blalp.sftwrapper.platforms.Windows.WindowsConfigs;
import com.blalp.sftwrapper.util.UserErrorExepction;

import oshi.SystemInfo;

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
        switch(SystemInfo.getCurrentPlatformEnum()){
            case FREEBSD:
            case LINUX:
            case SOLARIS:
            case UNKNOWN:
            return new LinuxConfig();
            case MACOSX:
            return new MacConfigs();
            case WINDOWS:
            return new WindowsConfigs();
            default:
            try {
                throw new UserErrorExepction("unsupported platform");
            } catch (UserErrorExepction e) {
                e.printStackTrace();
            }
            System.exit(-1);
            return null;
        }
    }
    public void mkdirs() {
        new File(getPathInstanceCache()).mkdirs();
        //new File(getPathMultiMC()).mkdirs(); DO NOT check this one, this should be generated from extracting the zip and is how we tell if MultiMC is installed
        new File(getPathRoot()).mkdirs();
    }

    @Override
    public String getPathInstanceCache() {
        return getPathRoot()+File.separatorChar+"instanceCache";
    }
}