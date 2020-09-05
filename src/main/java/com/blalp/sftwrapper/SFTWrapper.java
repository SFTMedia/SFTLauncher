package com.blalp.sftwrapper;


import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.blalp.sftwrapper.display.Configure;
import com.blalp.sftwrapper.display.MainWindow;
import com.blalp.sftwrapper.instances.GenericInstance;
import com.blalp.sftwrapper.interfaces.IJoinable;
import com.blalp.sftwrapper.platforms.Generic.GenericConfig;
import com.blalp.sftwrapper.util.Config;
import com.blalp.sftwrapper.util.MultiMC;

import oshi.SystemInfo;

public class SFTWrapper {
    public static void main(String[] args) {
        /**
         * MultiMC Download thread start
         * 
         * Check to see if its installed If not install it Check if this is the first
         * start If so, prompt user for which varients they want installed. Prompt for
         * what packs they want installed Update Checker start download repo check
         * against installed versions/varients for updates/new items Start update
         * download thread, one per update Give user oppertunity to hit a gear or
         * something to change which varients/servers they have installed If they do
         * want to make changes, call the same function as before on first start to
         * install new ones Join with all of the update/download threads Handle Install
         * of instances Install into MultiMC first for new instances/updates Move over
         * any data folders from the old instance into the new one Saves Servers
         * Controls Screenshots Delete the old instance
         * 
         */
        // Setup the main window
        MainWindow window = new MainWindow(Config.version);
        // get the relevent path (this method still needs implementing)
        Config.path = GenericConfig.getRevelventPath();
        // Make the directories
        Config.path.mkdirs();
        // Add configuration options
        Configure configure = new Configure();
        // Get otimum ram to fill cache
        for (GenericInstance instance : configure.getChecked()) {
            int idealRAM = (int) (instance.getOptimumRAM() / 1000000);
            if(GenericInstance.minIdealRAM==-1||GenericInstance.minIdealRAM>idealRAM){
                GenericInstance.minIdealRAM= idealRAM;
            }
            if(GenericInstance.maxIdealRAM==-1||GenericInstance.maxIdealRAM<idealRAM){
                GenericInstance.maxIdealRAM= idealRAM;
            }
        }
        // Install MultiMC
        if (!MultiMC.isInstalled()) {
            MultiMC.install().join();
        }
        window.show();
        // Do the stuff
        ArrayList<IJoinable> joinables = new ArrayList<>();
        for (GenericInstance instance : configure.getChecked()) {
            if(!instance.isInstalled()) {
                joinables.add(instance.install());
            } else {
                instance.writeInstanceCfg();
            }
        }
        for (IJoinable joinable : joinables) {
            joinable.join();
        }
        if (configure.getChecked().size()==0){
            JFrame error = new JFrame();
            JPanel panel = new JPanel();
            panel.add(new JLabel("No installable modpacks"));
            for (GenericInstance instance : configure.getOptions()) {
                panel.add(new JLabel(instance.getInstanceName()+" requires at LEAST "+((instance.getMinimumRAM()+instance.reserveForSystem)/1000000)+" In your system to run. You have "+(new SystemInfo().getHardware().getMemory().getTotal()/1000000)));
            }
            error.add(panel);
            error.pack();
            error.setVisible(true);
        }
        MultiMC.start().join();
        System.exit(0);
    }
}