package com.wells.cheerfulrun.app;

import android.app.Application;

/**
 * Created by wei on 16/4/17.
 */
public class ClientApp extends Application {
    private static ClientApp instance;

    public static ClientApp getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
