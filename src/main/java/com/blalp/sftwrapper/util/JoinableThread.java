package com.blalp.sftwrapper.util;

import com.blalp.sftwrapper.interfaces.IJoinable;

public class JoinableThread implements IJoinable {
    private Thread thread;

    public JoinableThread(Thread thread) {
        this.thread = thread;
    }

    @Override
    public void join() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}
