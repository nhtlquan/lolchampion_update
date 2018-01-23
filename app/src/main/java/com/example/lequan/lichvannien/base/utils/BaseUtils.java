package com.example.lequan.lichvannien.base.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.TypedValue;
import com.facebook.appevents.AppEventsConstants;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

public class BaseUtils {
    public static String getCountry(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(BaseConstant.KEY_COUNTRY_REQUEST, "VN");
    }

    public static void setCountry(Context context, String country) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        if (pref.getString(BaseConstant.KEY_COUNTRY_REQUEST, "").equals("")) {
            Editor editor = pref.edit();
            editor.putString(BaseConstant.KEY_COUNTRY_REQUEST, country);
            editor.apply();
        }
    }

    public static void setDateInstall(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        if (pref.getString(BaseConstant.KEY_INSTALL_DATE, null) == null) {
            Editor editor = pref.edit();
            Calendar c = Calendar.getInstance();
            editor.putString(BaseConstant.KEY_INSTALL_DATE, c.get(1) + "-" + standardNumber(c.get(2) + 1) + "-" + standardNumber(c.get(5)));
            editor.apply();
        }
    }

    private static String standardNumber(int number) {
        if (number <= 9) {
            return AppEventsConstants.EVENT_PARAM_VALUE_NO + number;
        }
        return String.valueOf(number);
    }

    public static String getDateInstall(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String date = pref.getString(BaseConstant.KEY_INSTALL_DATE, "");
        if (!date.equals("")) {
            return date;
        }
        setDateInstall(context);
        return pref.getString(BaseConstant.KEY_INSTALL_DATE, "");
    }

    public static String getDeviceID(Context context) {
        return Secure.getString(context.getContentResolver(), "android_id");
    }

    public static String getNetwork(Context context) {
        return ((TelephonyManager) context.getSystemService("phone")).getNetworkOperatorName().replace(" ", "%20");
    }

    public static boolean isInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, 1);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void copyFilefromAsset(Context conetxt, String filename, String filePath) {
        try {
            InputStream in = conetxt.getAssets().open(filename);
            OutputStream out = new FileOutputStream(filePath);
            try {
                byte[] buffer = new byte[1024];
                while (true) {
                    int read = in.read(buffer);
                    if (read == -1) {
                        in.close();
                        out.flush();
                        out.close();
                        return;
                    }
                    out.write(buffer, 0, read);
                }
            } catch (Exception e2) {
                OutputStream outputStream = out;
                Log.m1447e("error copy file from assets to sd: " + e2.getMessage());
            }
        } catch (Exception e3) {
            Log.m1447e("error copy file from assets to sd: " + e3.getMessage());
        }
    }

    public static String getDeviceName() {
        return Build.MANUFACTURER + " - " + Build.MODEL;
    }

    public static void gotoUrl(Context context, String url) {
        Intent i = new Intent("android.intent.action.VIEW");
        i.setData(Uri.parse(url));
        context.startActivity(i);
    }

    public static String readFileFromAsset(Context context, String fileName) {
        try {
            InputStream stream = context.getAssets().open(fileName);
            byte[] buffer = new byte[stream.available()];
            stream.read(buffer);
            stream.close();
            Log.m1446d("doc file asset: " + fileName);
            return new String(buffer);
        } catch (IOException e) {
            Log.m1447e("error read file asset: " + fileName + " msg: " + e.getMessage());
            return "";
        }
    }

    public static String readTxtFile(File file) {
        try {
            InputStream input = new FileInputStream(file);
            byte[] buffer = new byte[input.available()];
            input.read(buffer);
            input.close();
            Log.m1446d("doc file " + file.getName());
            return new String(buffer);
        } catch (Exception e) {
            Log.m1447e("error read file: " + file.getName() + " msg: " + e.getMessage());
            return "";
        }
    }

    public static String readTxtFile2(File file) {
        try {
            BufferedReader myReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String aDataRow = "";
            String aBuffer = "";
            while (true) {
                aDataRow = myReader.readLine();
                if (aDataRow != null) {
                    aBuffer = aBuffer + aDataRow + "\n";
                } else {
                    myReader.close();
                    return aBuffer;
                }
            }
        } catch (Exception e) {
            Log.m1447e("error read txt file sdcard: " + e.getMessage());
            return "";
        }
    }

    public static boolean writeTxtFile(File file, String fileContents) {
        try {
            FileWriter out = new FileWriter(file);
            out.write(fileContents);
            out.close();
            Log.m1446d("write file " + file.getName());
            return true;
        } catch (IOException e) {
            Log.m1447e("error write file: " + file.getName() + " msg: " + e.getMessage());
            return false;
        }
    }

    public static int genpx(Context context, int dp) {
        int pxTemp = (int) TypedValue.applyDimension(1, (float) dp, context.getResources().getDisplayMetrics());
        return pxTemp != 0 ? pxTemp : dp * 4;
    }

    public static final String md5(String s) {
        try {
            MessageDigest digest = MessageDigest.getInstance(s);
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();
            StringBuffer hexString = new StringBuffer();
            for (byte b : messageDigest) {
                String h = Integer.toHexString(b & 255);
                while (h.length() < 2) {
                    h = AppEventsConstants.EVENT_PARAM_VALUE_NO + h;
                }
                hexString.append(h);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
    }

    public static String encryptDecrypt(String input) {
        char[] key = new char[]{'K', 'C', 'Q'};
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            output.append((char) (input.charAt(i) ^ key[i % key.length]));
        }
        return output.toString();
    }
}
