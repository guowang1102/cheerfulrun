package com.wells.cheerfulrun.base;

import android.app.Application;
import android.content.res.Configuration;

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
