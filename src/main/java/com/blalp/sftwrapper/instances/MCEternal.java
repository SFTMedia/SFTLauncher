package com.blalp.sftwrapper.instances;

public class MCEternal extends GenericInstance {
    public static MCEternal instance = new MCEternal();

    @Override
    public long getMinimumRAM() {
        return 6500000000l;
    }

    @Override
    public String getURL() {
        return "https://www.curseforge.com/minecraft/modpacks/minecraft-eternal/download/3018921/file";
    }
    
}
