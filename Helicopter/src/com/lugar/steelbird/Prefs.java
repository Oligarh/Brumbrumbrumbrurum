package com.lugar.steelbird;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {

    public static String getStringProperty(Context context, int resId) {
        final String key = context.getString(resId);
        final String value = getPrefs(context).getString(key, "");

        return value;
    }

    public static int getIntProperty(Context context, int resId) {
        final String key = context.getString(resId);
        final int value = getPrefs(context).getInt(key, 0);

        return value;
    }

    public static int getIntProperty(Context context, String key) {
        final int value = getPrefs(context).getInt(key, 0);
        return value;
    }

    public static boolean getBooleanProperty(Context context, int resId) {
        final String key = context.getString(resId);
        return getPrefs(context).getBoolean(key, false);
    }

    public static void setStringProperty(Context context, int resId, String value) {
        final SharedPreferences prefs = getPrefs(context);
        final String key = context.getString(resId);
        prefs.edit().putString(key, value).commit();
    }

    public static void setStringProperty(Context context, String key, String value) {
        final SharedPreferences prefs = getPrefs(context);
        prefs.edit().putString(key, value).commit();
    }

    public static void setIntProperty(Context context, int resId, int value) {
        final SharedPreferences prefs = getPrefs(context);
        final String key = context.getString(resId);
        prefs.edit().putInt(key, value).commit();
    }

    public static void setIntProperty(Context context, String key, int value) {
        final SharedPreferences prefs = getPrefs(context);
        prefs.edit().putInt(key, value).commit();
    }

    public static void setBooleanProperty(Context context, int resId, boolean value) {
        final SharedPreferences prefs = getPrefs(context);
        final String key = context.getString(resId);
        prefs.edit().putBoolean(key, value).commit();
    }

    private static SharedPreferences getPrefs(Context context) {
        String prefsName = context.getString(R.string.app_name);
        return context.getSharedPreferences(prefsName, Context.MODE_PRIVATE);
    }

    public static void clearPreferences(Context context, int resId) {
        final SharedPreferences prefs = getPrefs(context);
        final String key = context.getString(resId);
        prefs.edit().remove(key).commit();
    }

    public static long getLongProperty(Context context, int resId) {
        final String key = context.getString(resId);
        final long value = getPrefs(context).getLong(key, 0);
        return value;
    }
}
