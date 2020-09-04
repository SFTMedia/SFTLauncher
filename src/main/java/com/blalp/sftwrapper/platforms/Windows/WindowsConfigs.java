package com.blalp.sftwrapper.platforms.Windows;

import java.io.File;

import com.blalp.sftwrapper.platforms.Generic.GenericConfig;

/**
 * WindowsConfigs
 */
public class WindowsConfigs extends GenericConfig {

    @Override
    public String getURLMultiMC() {
        return "https://github.com/MultiMC/MultiMC5/releases/latest/download/mmc-stable-win32.zip";
    }

    @Override
    public String getMultiMCArchiveFormat() {
        return "zip";
    }

    @Override
    public String getFileMultiMCBinary() {
        return getPathMultiMC()+File.separatorChar+"MultiMC.exe";
    }

    @Override
    public String getPathMultiMCExtract(){
        return getPathRoot();
    }
    @Override
    public String getPathRoot() {
        return System.getProperty("user.home")+File.separatorChar+"AppData"+File.pathSeparatorChar+"Roaming"+File.pathSeparatorChar+".SFTLauncher";
    }
    
}