package com.blalp.sftwrapper.instances;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.ConcurrentHashMap;

import com.blalp.sftwrapper.util.Config;

public class Stoneblock2 extends GenericInstance {
    public static Stoneblock2 instance = new Stoneblock2();

    @Override
    public long getMinimumRAM() {
        return 4000000000l;
    }

    @Override
    public String getURL() {
        return "https://media.forgecdn.net/files/2818/169/FTBPresentsStoneblock2-1.16.1-1.12.2.zip";
    }
    @Override
    public String getInstanceName() {
        return "Stoneblock 2";
    }

    @Override
    public String getPathRelativeMinecraftDir() {
        return "minecraft";
    }

    @Override
    public void install() {
        super.install();
        moveFromAssets("options.txt");
        moveFromAssets("servers.dat");
    }
}
