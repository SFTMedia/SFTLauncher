package com.blalp.sftwrapper;


import java.util.ArrayList;

import com.blalp.sftwrapper.display.Configure;
import com.blalp.sftwrapper.display.MainWindow;
import com.blalp.sftwrapper.instances.GenericInstance;
import com.blalp.sftwrapper.interfaces.IJoinable;
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
        MainWindow window = new MainWindow(Config.version);
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
        window.show();
        // Do the stuff
        ArrayList<IJoinable> joinables = new ArrayList<>();
        for (GenericInstance instance : configure.getChecked()) {
            if(!instance.isInstalled()) {
                joinables.add(instance.install());
            }
        }
        for (IJoinable joinable : joinables) {
            joinable.join();
        }
        MultiMC.start();
        System.exit(0);
    }
}