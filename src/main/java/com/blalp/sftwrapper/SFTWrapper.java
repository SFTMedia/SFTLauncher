package com.blalp.sftwrapper;

import com.blalp.sftwrapper.display.MainWindow;

public class SFTWrapper {
    public static void main(String[] args) {
        /**
         * MultiMC Download thread start
         * 
         *  Check to see if its installed
         *  If not install it
         * Check if this is the first start
         *  If so, prompt user for which varients they want installed.
         *  Prompt for what packs they want installed
         * Update Checker start
         *  download repo
         *  check against installed versions/varients for updates/new items
         *  Start update download thread, one per update
         * Give user oppertunity to hit a gear or something to change which varients/servers they have installed
         *  If they do want to make changes, call the same function as before on first start to install new ones
         * Join with all of the update/download threads
         * Handle Install of instances
         *  Install into MultiMC first for new instances/updates
         *  Move over any data folders from the old instance into the new one
         *      Saves
         *      Servers
         *      Controls
         *      Screenshots
         *  Delete the old instance
         *  
         */
        MainWindow window = new MainWindow();
        window.show();
    }
}