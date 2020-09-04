package com.blalp.sftwrapper.instances;

import com.blalp.sftwrapper.util.Config;
import com.blalp.sftwrapper.util.Download;
import com.blalp.sftwrapper.util.UserErrorExepction;

import oshi.SystemInfo;

public abstract class GenericInstance {
    protected long reserveForSystem = 1250000000;
    protected long systemRAM;
    protected long usableRAM;
    protected long idealRAM;
    public GenericInstance() {
        SystemInfo systemInfo = new SystemInfo();
        systemRAM = systemInfo.getHardware().getMemory().getTotal();
        usableRAM = systemRAM-reserveForSystem;
        idealRAM = (long) ((systemRAM - getMinimumRAM()) * .65 + getMinimumRAM());
    }
    public long getOptimumRAM() {
        // Memory is in bytes
        if (usableRAM>=getMaximumRAM())
            return getMaximumRAM();
        if (usableRAM<=getMinimumRAM()) {
            try {
                throw new UserErrorExepction("Insufficent RAM for modpack.");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return -1; // This shouldn't happen
        }
        if (usableRAM<=idealRAM)
            return usableRAM;// YIKES LOW RAM
        return idealRAM;
        
    }

    public boolean canRun() {
        return usableRAM>=getMinimumRAM();
    }

    public abstract long getMinimumRAM();
    public long getMaximumRAM() {
        return getMinimumRAM()*2;
    }
    public abstract String getURL();
    public void install() {
        new Download(getURL(),Config.path.getFilePath());
    }
}
