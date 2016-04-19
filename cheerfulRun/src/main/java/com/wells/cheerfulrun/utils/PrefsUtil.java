package com.wells.cheerfulrun.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.wells.cheerfulrun.app.ClientApp;

/**
 * SharedPreferences 工具类
 */
public class PrefsUtil {

    private static Context context;
    private static SharedPreferences prefs;
    private static PrefsUtil instance;

    public static PrefsUtil getInstance() {
        if (instance == null)
            instance = new PrefsUtil(ClientApp.getInstance());
        return instance;
    }

    private PrefsUtil(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences("share_prefs", Context.MODE_PRIVATE);
    }

    public void setPrefsStrValue(String key, String value) {
        prefs.edit().putString(key, value).commit();
    }


    public void setPrefsIntValue(String key, int value) {
        prefs.edit().putInt(key, value).commit();
    }


    public void setPrefsLongValue(String key, long value) {
        prefs.edit().putLong(key, value).commit();
    }


    public void setPrefsBooleanValue(String key, boolean value) {
        prefs.edit().putBoolean(key, value).commit();
    }


    public void setPrefsFloatValue(String key, float value) {
        prefs.edit().putFloat(key, value).commit();
    }



    public String getPrefsStrValue(String key) {
        return prefs.getString(key, "");
    }


    public String getPrefsStrValue(String key, String defaultValue) {
        return prefs.getString(key, defaultValue);
    }

    public Boolean getPrefsBooleanValue(String key, boolean defaultValue) {
        return prefs.getBoolean(key, defaultValue);
    }

    public Float getPrefsFloatValue(String key) {
        return prefs.getFloat(key, Float.MIN_VALUE);
    }

    public Integer getPrefsIntValue(String key) {
        return prefs.getInt(key, Integer.MIN_VALUE);
    }

    public Integer getPrefsIntValue(String key, int defaultValue) {
        return prefs.getInt(key, defaultValue);
    }

    public Long getPrefsLongValue(String key) {
        return prefs.getLong(key, Long.MIN_VALUE);
    }

}
