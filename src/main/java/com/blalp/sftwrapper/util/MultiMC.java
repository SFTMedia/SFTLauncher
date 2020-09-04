package com.blalp.sftwrapper.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * MultiMC
 */
public class MultiMC {

    public boolean isInstalled() {
        return new File(Config.path.getFilePath(),"MultiMC").exists();
    }
    public void install() {
        try {
            FileOutputStream outputStream = new FileOutputStream(new File(Config.path.getFilePath(),"multimc.archive"));
            ReadableByteChannel input = Channels.newChannel(new URL(Config.path.getMultiMCURL()).openStream());
            outputStream.getChannel().transferFrom(input, 0, Long.MAX_VALUE);
            input.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void extract() {
        //CompressInput
    }
}