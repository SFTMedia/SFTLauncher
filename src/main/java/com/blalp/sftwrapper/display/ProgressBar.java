package com.blalp.sftwrapper.display;

import javax.swing.JProgressBar;

public class ProgressBar {
    private JProgressBar progressBar = new JProgressBar(0,100);
    public void setLength (long percent) {
        progressBar.setValue((int)percent*100);
    }
}
