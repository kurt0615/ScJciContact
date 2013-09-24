package com.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    public static final String PASS_INFO = "passInfo";

    public static void saveLoginInfo(Activity activity, Boolean pass) {
        SharedPreferences settings = activity.getSharedPreferences(PASS_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("pass", pass);
        editor.commit();
    }

    public static Boolean checkPassed(Activity activity) {
        SharedPreferences settings = activity.getSharedPreferences(PASS_INFO, Context.MODE_PRIVATE);
        if (settings.getBoolean("pass", false)) {
            return true;
        }
        return false;
    }
}

