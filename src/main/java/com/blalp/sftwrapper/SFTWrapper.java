package com.blalp.sftwrapper;


import com.blalp.sftwrapper.display.Configure;
import com.blalp.sftwrapper.display.MainWindow;
import com.blalp.sftwrapper.instances.GenericInstance;
import com.blalp.sftwrapper.platforms.Generic.GenericConfig;
import com.blalp.sftwrapper.util.Config;
import com.blalp.sftwrapper.util.MultiMC;

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
        MainWindow window = new MainWindow();
        // get the relevent path (this method still needs implementing)
        Config.path = GenericConfig.getRevelventPath();
        // Make the directories
        Config.path.mkdirs();
        // Add configuration options
        Configure configure = new Configure();
        // Install MultiMC
        if (!MultiMC.isInstalled()) {
            MultiMC.install().join();
        }
        // Do the stuff
        for (GenericInstance instance : configure.getChecked()) {
            if(!instance.isInstalled()) {
                window.show();
                // Only can install one at a time
                instance.install().join();
                System.exit(-1);
            }
        }
        window.show();
        MultiMC.start();
        System.exit(0);
    }
}