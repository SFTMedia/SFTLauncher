package com.blalp.sftwrapper.instances;

public class MCEternal extends GenericInstance {
    public static MCEternal instance = new MCEternal();

    @Override
    public long getMinimumRAM() {
        return 6500000000l;
    }

    @Override
    public String getURL() {
        return "https://media.forgecdn.net/files/3018/921/MC+Eternal-1.3.7.1.zip";
    }

    @Override
    public String getInstanceName() {
        return "MC Eternal";
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
    @Override
    public String getIcon() {
        return "netherstar";
    }
    public String getInstanceType(){
        return "OneSix";
    }
    
}
