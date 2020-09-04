package com.blalp.sftwrapper;

import java.io.File;
import java.io.IOException;

import com.blalp.sftwrapper.display.Configure;
import com.blalp.sftwrapper.display.MainWindow;
import com.blalp.sftwrapper.instances.GenericInstance;
import com.blalp.sftwrapper.platforms.Generic.GenericConfig;
import com.blalp.sftwrapper.util.Config;

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
        // Add configuration options
        Configure configure = new Configure();
        // Show the window
        window.show();
        // Do the stuff
        for (GenericInstance instance : configure.getChecked()) {
            instance.install();
        }
        // Now install all the instances
        for (GenericInstance instance : configure.getChecked()) {
            try {
                Runtime.getRuntime().exec(Config.path.getFileMultiMCBinary()+" -I "+instance.getLatestZIP());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}