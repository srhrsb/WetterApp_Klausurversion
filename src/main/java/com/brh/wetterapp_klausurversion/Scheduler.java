package com.brh.wetterapp_klausurversion;

import com.brh.wetterapp_klausurversion.controller.SettingsManager;

import java.util.function.Consumer;

public class Scheduler extends Thread {
    private UpdateCallback updateCallback;

    public Scheduler( UpdateCallback updateCallback  ){
       this.updateCallback = updateCallback;
    }

    @Override
    public void run(){
        try {
            sleep( SettingsManager.getInstance().getUpdateFrequency() * 1000 * 60);
            updateCallback.invoke();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
