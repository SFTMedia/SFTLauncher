package com.blalp.sftwrapper.instances;

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
    public void run() {
        super.run();
        moveFromAssets("options.txt");
        moveFromAssets("servers.dat");
    }

    @Override
    public int[] getVersion() {
        return new int[]{0,0,0};
    }
}
