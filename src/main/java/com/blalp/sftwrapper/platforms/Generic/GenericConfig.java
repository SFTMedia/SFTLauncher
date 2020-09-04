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
        return System.getProperty("user.home")+'/'+".SFTLauncher";
    }

    @Override
    public String getPathMultiMC(){
        return getPathRoot()+'/'+"MultiMC";
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
        new File(getPathMultiMC()).mkdirs();
        new File(getPathRoot()).mkdirs();
    }

    @Override
    public String getPathInstanceCache() {
        return getPathRoot()+'/'+"instanceCache";
    }
}