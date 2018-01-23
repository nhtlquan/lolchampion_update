package com.example.lequan.lichvannien.base.tracking;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

public class SharedPreferencesLibUtil {
    public static String getValue(Context context, String key) {
        return context.getSharedPreferences("global_lib", 0).getString(key, null);
    }

    public static void setValue(Context context, String key, String value) {
        Editor editor = context.getSharedPreferences("global_lib", 0).edit();
        if (TextUtils.isEmpty(value)) {
            editor.remove(key);
        } else {
            editor.putString(key, value);
        }
        editor.apply();
    }
}
