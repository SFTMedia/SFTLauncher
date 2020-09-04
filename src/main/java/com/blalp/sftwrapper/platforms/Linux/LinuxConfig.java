package com.blalp.sftwrapper.platforms.Linux;

import com.blalp.sftwrapper.platforms.Generic.GenericConfig;
import com.blalp.sftwrapper.util.UserErrorExepction;

/**
 * LinuxConfig
 */
public class LinuxConfig extends GenericConfig {

    @Override
    public String getMultiMCURL() {
        if(System.getProperty ("os.arch")=="amd64") {
            return "https://github.com/MultiMC/MultiMC5/releases/latest/download/mmc-stable-lin64.tar.gz";
        } else if(System.getProperty ("os.arch")=="x86") {
            return "https://github.com/MultiMC/MultiMC5/releases/latest/download/mmc-stable-lin32.tar.gz";
        } else {
            try {
                throw new UserErrorExepction("Not 64 bit or 32 bit.");
            } catch (UserErrorExepction e){
                e.printStackTrace();
            }
            return null;
        }
    }

    
}