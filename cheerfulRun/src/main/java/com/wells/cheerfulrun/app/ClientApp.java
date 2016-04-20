package com.wells.cheerfulrun.app;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wei on 16/4/17.
 */
public class ClientApp extends Application {
    private static ClientApp instance;
    public static List<Activity> activityList = new ArrayList<Activity>();

    public static ClientApp getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public void exit(){
        for(Activity activity:activityList){
            activity.finish();
        }
    }
}
