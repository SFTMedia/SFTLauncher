package com.blalp.sftwrapper.platforms.Mac;

import java.io.File;

import com.blalp.sftwrapper.platforms.Generic.GenericConfig;
import com.blalp.sftwrapper.util.UserErrorExepction;

/**
 * MacConfigs
 */
public class MacConfigs extends GenericConfig {

    @Override
    public String getURLMultiMC() {
        if(System.getProperty ("os.arch")=="amd64") {
            return "https://github.com/MultiMC/MultiMC5/releases/latest/download/mmc-stable-osx64.tar.gz";
        } else {
            try {
                throw new UserErrorExepction("non 64 bit mac not supported.");
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
        return getPathMultiMC()+File.separatorChar+"MultiMC.app";
    }

    @Override
    public String getPathMultiMCExtract(){
        return getPathRoot()+File.separatorChar+"MultiMC";
    }
    
}