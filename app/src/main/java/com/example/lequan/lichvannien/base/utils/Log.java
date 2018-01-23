package com.example.lequan.lichvannien.base.utils;


import com.example.lequan.lichvannien.BaseActivity;

public class Log {
    public static boolean DEBUG = true;
    public static String TAG = BaseActivity.TAG;

    public static void m1446d(String msg) {
        if (DEBUG) {
            android.util.Log.d(TAG, msg);
        }
    }

    public static void m1447e(String msg) {
        if (DEBUG) {
            android.util.Log.e(TAG, msg);
        }
    }

    public static void m1448i(String msg) {
        if (DEBUG) {
            android.util.Log.i(TAG, msg);
        }
    }

    public static void m1449v(String msg) {
        if (DEBUG) {
            android.util.Log.v(TAG, msg);
        }
    }

    public static void m1450w(String msg) {
        if (DEBUG) {
            android.util.Log.w(TAG, msg);
        }
    }
}
