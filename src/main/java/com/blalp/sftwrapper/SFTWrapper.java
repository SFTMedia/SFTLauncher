package com.blalp.sftwrapper;

public class SFTWrapper {
    public static void main(String[] args) {
        /**
         * Check for and start downloading MultiMC on one thread
         * Download the repository file in json
         * Prompt Users for which servers/varients to install, and if there are updates
         * If there are no updates, start a 5 second countdown to launch minecraft, has a button to skip. If they want to stop they can edit instances
         * Start downloading them
         * 
         */
        /**
         * MultiMC Download thread start
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
         * Handle Install
         *  Install into MultiMC first for new instances/updates
         *  
         */
    }
}