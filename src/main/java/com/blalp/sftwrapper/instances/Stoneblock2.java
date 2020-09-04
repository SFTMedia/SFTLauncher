package com.blalp.sftwrapper.instances;

public class Stoneblock2 extends GenericInstance {
    public static Stoneblock2 instance = new Stoneblock2();

    @Override
    public long getMinimumRAM() {
        return 6300000000l;
    }

    @Override
    public String getURL() {
        return "https://www.curseforge.com/minecraft/modpacks/ftb-presents-stoneblock-2/download/2818169/file";
    }
    
}
