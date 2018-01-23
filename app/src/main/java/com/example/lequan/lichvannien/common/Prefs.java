package com.example.lequan.lichvannien.common;

import android.content.Context;
import android.content.SharedPreferences.Editor;

public class Prefs {
    public static final String KEY_AVATAR = "avatar";
    public static final String KEY_DOB = "dob";
    public static final String KEY_NAME = "name";
    public static final String PREFS_NAME = "Calendar";

    public static void setValue(Context context, String key, String value) {
        Editor editor = context.getSharedPreferences(PREFS_NAME, 0).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getValue(Context context, String key) {
        return context.getSharedPreferences(PREFS_NAME, 0).getString(key, "");
    }
}
