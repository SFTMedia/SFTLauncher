package com.blalp.sftwrapper.platforms.Windows;

import com.blalp.sftwrapper.interfaces.IPath;
import com.blalp.sftwrapper.platforms.Generic.GenericConfig;

/**
 * WindowsConfigs
 */
public class WindowsConfigs extends GenericConfig {

    @Override
    public String getURLMultiMC() {
        return "https://github.com/MultiMC/MultiMC5/releases/latest/download/mmc-stable-win32.tar.gz";
    }

    @Override
    public String getMultiMCArchiveFormat() {
        return "zip";
    }

    @Override
    public String getFileMultiMCBinary() {
        // TODO Auto-generated method stub
        return null;
    }
    
}