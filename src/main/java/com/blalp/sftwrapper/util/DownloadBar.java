package com.blalp.sftwrapper.util;

import java.io.File;

import com.blalp.sftwrapper.display.ProgressBar;

public class DownloadBar extends ProgressBar implements Runnable {

    private long size;
    private File path;

    public DownloadBar(long size, File path) {
        this.size=size;
        this.path=path;
    } 
    public void run () {
        while (true) {
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setLength(size/path.getTotalSpace());
        }
    }
}
