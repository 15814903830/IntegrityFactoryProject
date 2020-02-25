package com.xsylsb.integrity.util;

import android.content.Context;
import android.content.SharedPreferences;
import com.xsylsb.integrity.MainApplication;
public class SharedPrefUtil {
    public static String ID="ID";
   private static SharedPreferences mSp;

    private static SharedPreferences getSharedPref(Context context) {
        if (mSp == null) {
            mSp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return mSp;
    }

    public static void putBoolean( String key, boolean value) {
        getSharedPref(MainApplication.getInstance()).edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean( String key, boolean defValue) {
        return getSharedPref(MainApplication.getInstance()).getBoolean(key, defValue);
    }

    public static void putString( String key, String value) {
        getSharedPref(MainApplication.getInstance()).edit().putString(key, value).commit();
    }

    public static String getString( String key) {
        return getSharedPref(MainApplication.getInstance()).getString(key, "");
    }

    public static void removeString( String key) {
        getSharedPref(MainApplication.getInstance()).edit().remove(key).commit();
    }

    public static void putInt(String key, int value) {
        getSharedPref(MainApplication.getInstance()).edit().putInt(key, value).commit();
    }

    public static int getInt( String key, int defValue) {
        return getSharedPref(MainApplication.getInstance()).getInt(key, defValue);
    }
}