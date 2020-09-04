package com.blalp.sftwrapper.display;

import javax.swing.JProgressBar;

public class ProgressBar {
    protected JProgressBar progressBar = new JProgressBar(0,100);
    public void setLength (long percent) {
        progressBar.setValue((int)percent*100);
    }
}
