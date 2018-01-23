package com.example.lequan.lichvannien.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.lequan.lichvannien.BaseActivity;
import com.example.lequan.lichvannien.common.Define;
import com.example.lequan.lichvannien.model.DayInfo;
import com.example.lequan.lichvannien.R;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utils {
    private static final String TAG = Utils.class.getSimpleName();

    public static final boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static boolean checkNetworkConnection(Context context) {
        NetworkInfo activeNetwork = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        boolean isConnect = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        Log.d(TAG, "Network connected: " + isConnect);
        return isConnect;
    }

    public static boolean isJSONValid(String test) {
        try {
            JSONObject jSONObject = new JSONObject(test);
        } catch (JSONException e) {
            try {
                JSONArray jSONArray = new JSONArray(test);
            } catch (JSONException e2) {
                return false;
            }
        }
        return true;
    }

    public static String getTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
    }

    public static void shareIntent(Context context, String msg) {
        Intent sendIntent = new Intent();
        sendIntent.setAction("android.intent.action.SEND");
        sendIntent.putExtra("android.intent.extra.TEXT", msg);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }

    public static void writeToFile(String data, String filename) {
        File dir = new File(Environment.getExternalStorageDirectory(), "Simple Tracking");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            FileWriter out = new FileWriter(new File(dir + File.separator + filename), false);
            out.write(data);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readFromFile(String filename) {
        String result = "";
        File dir = new File(Environment.getExternalStorageDirectory(), "Simple Tracking/" + filename);
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(dir));
            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                text.append(line);
                text.append('\n');
            }
            br.close();
        } catch (IOException e) {
        }
        return text.toString();
    }

    public static boolean isEmailInvalid(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static DayInfo getDayInfo(String day) {
        Iterator it = Define.listDay.iterator();
        while (it.hasNext()) {
            DayInfo mInfo = (DayInfo) it.next();
            if (mInfo.getDuongLich().equalsIgnoreCase(day)) {
                return mInfo;
            }
        }
        return null;
    }

    public static int getDayIndex(String date) {
        for (int i = 0; i < Define.listDay.size(); i++) {
            if (((DayInfo) Define.listDay.get(i)).getDuongLich().equalsIgnoreCase(date)) {
                return i;
            }
        }
        return 0;
    }

    public static final String getCurrentDay() {
        return new SimpleDateFormat(Define.TIME_FORMAT).format(Calendar.getInstance().getTime());
    }

    public static final String getCurrentDayEvent() {
        return new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
    }

    public static final String getCurrentDayNewFormat() {
        return new SimpleDateFormat(Define.TIME_FORMAT_NEW).format(Calendar.getInstance().getTime());
    }

    public static String getTime(int index, String date) {
        String time = "";
        if (date.length() > 0) {
            return date.split("/")[index];
        }
        return time;
    }

    public static String getMonthYear(String date) {
        String time = "";
        if (date.length() <= 0) {
            return time;
        }
        String[] arrDate = date.split("/");
        return arrDate[1] + "/" + arrDate[2];
    }

    public static String getMonthYearNew(String date) {
        String time = "";
        if (date.length() <= 0) {
            return time;
        }
        String[] arrDate = date.split("/");
        return "Tháng " + arrDate[1] + " - " + arrDate[2];
    }

    public static String getDayMonth(String date) {
        String time = "";
        if (date.length() <= 0) {
            return time;
        }
        String[] arrDate = date.split("/");
        return arrDate[0] + "/" + arrDate[1];
    }

    public static String getDayChange(String day, int type, int value) {
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(new SimpleDateFormat(Define.TIME_FORMAT).parse(day));
            if (type == 0) {
                cal.add(5, value);
                return new SimpleDateFormat(Define.TIME_FORMAT).format(cal.getTime());
            }
            cal.add(2, value);
            return new SimpleDateFormat(Define.TIME_FORMAT).format(cal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void setIconZodiac(ImageView img, String zodiac) {
        if (zodiac.contains("Tý")) {
            img.setImageResource(R.drawable.giap_1);
        } else if (zodiac.contains("Sửu")) {
            img.setImageResource(R.drawable.giap_2);
        } else if (zodiac.contains("Dần")) {
            img.setImageResource(R.drawable.giap_3);
        } else if (zodiac.contains("Mão")) {
            img.setImageResource(R.drawable.giap_4);
        } else if (zodiac.contains("Thìn")) {
            img.setImageResource(R.drawable.giap_5);
        } else if (zodiac.contains("Tỵ")) {
            img.setImageResource(R.drawable.giap_6);
        } else if (zodiac.contains("Ngọ")) {
            img.setImageResource(R.drawable.giap_7);
        } else if (zodiac.contains("Mùi")) {
            img.setImageResource(R.drawable.giap_8);
        } else if (zodiac.contains("Thân")) {
            img.setImageResource(R.drawable.giap_9);
        } else if (zodiac.contains("Dậu")) {
            img.setImageResource(R.drawable.giap_10);
        } else if (zodiac.contains("Tuất")) {
            img.setImageResource(R.drawable.giap_11);
        } else if (zodiac.contains("Hợi")) {
            img.setImageResource(R.drawable.giap_12);
        }
    }

    public static String converDate(String date) {
        String[] arr = date.split("/");
        return arr[2] + "/" + arr[1] + "/" + arr[0];
    }

    public static String converDateEvent(String date) {
        String[] arr = date.split("-");
        return arr[2] + "-" + arr[1] + "-" + arr[0];
    }

    public static String getDayofWeek(String day) {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(Define.TIME_FORMAT);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(mSimpleDateFormat.parse(day));
            switch (calendar.get(7)) {
                case 1:
                    return "Chủ nhật";
                case 2:
                    return "Thứ hai";
                case 3:
                    return "Thứ ba";
                case 4:
                    return "Thứ tư";
                case 5:
                    return "Thứ năm";
                case 6:
                    return "Thứ sáu";
                case 7:
                    return "Thứ bảy";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDayofWeek2(String day) {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(Define.TIME_FORMAT);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(mSimpleDateFormat.parse(day));
            switch (calendar.get(7)) {
                case 1:
                    return "CN";
                case 2:
                    return "T.Hai";
                case 3:
                    return "T.Ba";
                case 4:
                    return "T.Tư";
                case 5:
                    return "T.Năm";
                case 6:
                    return "T.Sáu";
                case 7:
                    return "T.Bảy";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int compareRangeDate(String currentDate, String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date current = sdf.parse(currentDate);
            Date start = sdf.parse(startDate);
            Date end = sdf.parse(endDate);
            if (current.before(start)) {
                return -1;
            }
            if (current.after(end)) {
                return 1;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int compareRangeDate2(String currentDate, String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date current = sdf.parse(currentDate);
            Date start = sdf.parse(startDate);
            Date end = sdf.parse(endDate);
            if (current.before(start)) {
                return -1;
            }
            if (current.after(end)) {
                return 1;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int compareDate(String currentDate, String compareDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try {
            Date current = sdf.parse(currentDate);
            Date compare = sdf.parse(currentDate);
            if (current.before(compare)) {
                return -1;
            }
            if (current.after(compare)) {
                return 1;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getResourceId(Context mContext, String resource) {
        return mContext.getResources().getIdentifier(resource, "drawable", mContext.getPackageName());
    }

    public static void setFontTextview(Context mContext, TextView textView, String font) {
        textView.setTypeface(Typeface.createFromAsset(mContext.getAssets(), font));
    }

    public static void setFontEditText(Context mContext, EditText textView, String font) {
        textView.setTypeface(Typeface.createFromAsset(mContext.getAssets(), font));
    }

    public static int getIconWeatherChiTiet(int key) {
        return R.drawable.thoitiet_nang_nhe;
    }

    public static String getTrangThaiThoiTiet(int key) {
        if (key == 1 || key == 2 || key == 3 || key == 4 || key == 30 || key == 33 || key == 34 || key == 37) {
            return "Trời Nắng";
        }
        if (key == 5 || key == 6) {
            return "Nắng Nhẹ";
        }
        if (key == 7 || key == 31 || key == 8 || key == 32 || key == 38 || key == 11 || key == 35 || key == 36) {
            return "Nhiều Mây";
        }
        if (key == 39) {
            return "Mưa Nặng";
        }
        if (key == 26 || key == 15 || key == 16 || key == 17 || key == 40 || key == 41 || key == 42) {
            return "Mưa Giông";
        }
        return "Trời Mưa";
    }
}
