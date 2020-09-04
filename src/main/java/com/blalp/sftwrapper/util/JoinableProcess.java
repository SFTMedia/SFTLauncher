package com.blalp.sftwrapper.util;

import com.blalp.sftwrapper.interfaces.IJoinable;

public class JoinableProcess implements IJoinable {
    private Process process;

    public JoinableProcess(Process process) {
        this.process = process;
    }

    @Override
    public void join() {
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
