package com.blalp.sftwrapper.platforms.Linux;

import java.io.File;
import java.nio.file.Path;

import com.blalp.sftwrapper.platforms.Generic.GenericConfig;
import com.blalp.sftwrapper.util.UserErrorExepction;

/**
 * LinuxConfig
 */
public class LinuxConfig extends GenericConfig {

    @Override
    public String getURLMultiMC() {
        if(System.getProperty ("os.arch").equals("amd64")) {
            return "https://github.com/MultiMC/MultiMC5/releases/latest/download/mmc-stable-lin64.tar.gz";
        } else if(System.getProperty ("os.arch").equals("x86")) {
            return "https://github.com/MultiMC/MultiMC5/releases/latest/download/mmc-stable-lin32.tar.gz";
        } else {
            try {
                throw new UserErrorExepction("Not 64 bit or 32 bit. "+System.getProperty ("os.arch"));
            } catch (UserErrorExepction e){
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public String getMultiMCArchiveFormat() {
        return "tar.gz";
    }

    @Override
    public String getFileMultiMCBinary() {
        return "/bin/sh "+getPathMultiMC()+File.separatorChar+"MultiMC";
    }

    
}