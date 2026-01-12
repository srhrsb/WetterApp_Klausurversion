package com.brh.wetterapp_klausurversion;

import com.brh.wetterapp_klausurversion.controller.SettingsManager;

import java.util.function.Consumer;

public class Scheduler extends Thread {
    private UpdateCallback updateCallback;
    private long time;
    public Scheduler( UpdateCallback updateCallback, long time ){

        this.updateCallback = updateCallback;
        this.time = time;
    }

    /**
     * Wartet die im Settings-Manager gespeichert Update-Rate und
     * führt dann das Callback aus
     */
    @Override
    public void run(){
        try {
            sleep( time * 1000 * 60);
            updateCallback.invoke();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
