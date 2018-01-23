package com.example.lequan.lichvannien.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import com.facebook.internal.ServerProtocol;
import com.facebook.share.internal.ShareConstants;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.google.gson.Gson;
import com.example.lequan.lichvannien.BaseActivity;
import com.example.lequan.lichvannien.common.Define;
import com.example.lequan.lichvannien.model.Alert;
import com.example.lequan.lichvannien.model.DayInfo;
import com.example.lequan.lichvannien.model.DiaryInfo;
import com.example.lequan.lichvannien.model.Holiday;
import com.example.lequan.lichvannien.model.Quotation;
import com.example.lequan.lichvannien.model.SaoInfo;
import com.example.lequan.lichvannien.model.VanKhan;
import com.example.lequan.lichvannien.utils.DateUtils;
import com.example.lequan.lichvannien.utils.Utils;
import com.example.lequan.lichvannien.samsistemas.calendarview.widget.EventInfo;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DatabaseAccess extends SQLiteOpenHelper {
    private static String DB_NAME = "lichvannien.db";
    private static final int VERSION = 3;
    private static DatabaseAccess instance;
    private String DB_PATH = "";
    private SQLiteDatabase database;
    Context myContext;
    private boolean needUpdate;
    private SharedPreferences sharedPreferences;

    public DatabaseAccess(Context context) {
        super(context, DB_NAME, null, 11);
        this.DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        this.myContext = context;
        this.sharedPreferences = context.getSharedPreferences(context.getPackageName(), 0);
        if (this.sharedPreferences.getInt(ServerProtocol.FALLBACK_DIALOG_PARAM_VERSION, 0) < 3) {
            this.needUpdate = true;
            Editor editor = this.sharedPreferences.edit();
            editor.putInt(ServerProtocol.FALLBACK_DIALOG_PARAM_VERSION, 3);
            editor.commit();
        }
    }

    public void onCreate(SQLiteDatabase db) {
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void createDataBase() throws IOException {
        if (!checkDataBase()) {
            Log.d(BaseActivity.TAG, "COPY DATABASE");
            getReadableDatabase();
            copyDataBase();
        } else if (this.needUpdate) {
            Log.d(BaseActivity.TAG, "UPDATE DATABASE");
            getReadableDatabase();
            copyDataBase();
        }
    }

    public void open() {
        try {
            this.database = SQLiteDatabase.openDatabase(this.DB_PATH + DB_NAME, null, 16);
        } catch (SQLException e) {
            Log.e(BaseActivity.TAG, "error open DB: " + e.getMessage());
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(this.DB_PATH + DB_NAME, null, 16);
        } catch (SQLException e) {
            Log.e(BaseActivity.TAG, "chua co db: " + e.getMessage());
        }
        if (checkDB != null) {
            checkDB.close();
        }
        if (checkDB != null) {
            return true;
        }
        return false;
    }

    private void copyDataBase() {
        try {
            InputStream myInput = this.myContext.getAssets().open(DB_NAME);
            String outFileName = this.DB_PATH + DB_NAME;
            File root = new File(this.DB_PATH);
            if (!root.exists()) {
                root.mkdirs();
            }
            OutputStream myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            while (true) {
                int length = myInput.read(buffer);
                if (length > 0) {
                    myOutput.write(buffer, 0, length);
                } else {
                    myOutput.flush();
                    myOutput.close();
                    myInput.close();
                    return;
                }
            }
        } catch (Exception e) {
            Toast.makeText(this.myContext, "Có lỗi copy dữ liệu", 0).show();
            Log.e(BaseActivity.TAG, "error copy databases: " + e.getMessage());
        }
    }

    public void close() {
        if (this.database != null) {
            this.database.close();
        }
    }

    public List<String> getQuotes() {
        List<String> list = new ArrayList();
        Cursor cursor = this.database.rawQuery("SELECT * FROM lich", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public ArrayList<String> getSpecialDay(String day, String search) {
        ArrayList<String> listStrDay = new ArrayList();
        Cursor cursor = this.database.rawQuery("SELECT * FROM lich WHERE duong_lich LIKE '%" + day + "%' AND nen_lam LIKE '%" + search + "%'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            listStrDay.add(Utils.getTime(0, cursor.getString(3)));
            cursor.moveToNext();
        }
        cursor.close();
        return listStrDay;
    }

    public DayInfo getDay(String day) {
        try {
            Cursor cursor = this.database.rawQuery("SELECT * FROM lich WHERE duong_lich = '" + day + "'", null);
            cursor.moveToFirst();
            DayInfo mDayInfo = null;
            while (!cursor.isAfterLast()) {
                DayInfo mDayInfo2;
                try {
                    mDayInfo2 = new DayInfo();
                    mDayInfo2.setId(cursor.getInt(0));
                    mDayInfo2.setThu(cursor.getString(1));
                    mDayInfo2.setAmLich(cursor.getString(2));
                    mDayInfo2.setDuongLich(cursor.getString(3));
                    mDayInfo2.setNgay(cursor.getString(4));
                    mDayInfo2.setThang(cursor.getString(5));
                    mDayInfo2.setNam(cursor.getString(6));
                    mDayInfo2.setGioHoangDao(cursor.getString(7));
                    mDayInfo2.setGioHacDao(cursor.getString(8));
                    mDayInfo2.setHuongXuatHanh(cursor.getString(9));
                    mDayInfo2.setTuoiXungKhac(cursor.getString(10));
                    mDayInfo2.setSaoTot(cursor.getString(11));
                    mDayInfo2.setSaoXau(cursor.getString(12));
                    mDayInfo2.setNenLam(cursor.getString(13));
                    mDayInfo2.setKhongNenLam(cursor.getString(14));
                    mDayInfo2.setHoangDao(cursor.getInt(15));
                    Define.listDay.add(mDayInfo2);
                    cursor.moveToNext();
                    mDayInfo = mDayInfo2;
                } catch (Exception e) {
                    mDayInfo2 = mDayInfo;
                }
            }
            cursor.close();
            return mDayInfo;
        } catch (Exception e2) {
        }
        return null;
    }

    public String getBG() {
        Cursor cursor = this.database.rawQuery("SELECT * FROM test", null);
        cursor.moveToFirst();
        String abc = "";
        while (!cursor.isAfterLast()) {
            abc = cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        return abc;
    }

    public ArrayList<Quotation> getAllQuotation() {
        ArrayList<Quotation> listQuotation = new ArrayList();
        Cursor cursor = this.database.rawQuery("SELECT * FROM tbl_danh_ngon", null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Quotation mQuotation = new Quotation();
                mQuotation.setId(cursor.getInt(0));
                mQuotation.setQuotation(cursor.getString(1));
                mQuotation.setAuthor(cursor.getString(2));
                listQuotation.add(mQuotation);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return listQuotation;
    }

    public ArrayList<EventInfo> getAllSpecialDay() {
        ArrayList<EventInfo> listSpecial = new ArrayList();
        Cursor cursor = this.database.rawQuery("SELECT * FROM special_day", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            EventInfo special = new EventInfo();
            special.setId(cursor.getInt(0));
            special.setStart(cursor.getString(1));
            special.setSolar(cursor.getInt(2));
            special.setName(cursor.getString(3));
            listSpecial.add(special);
            cursor.moveToNext();
        }
        cursor.close();
        return listSpecial;
    }

    public String getTuvi2016(String gender, String year) {
        String result = "";
        Cursor cursor = this.database.rawQuery("SELECT * FROM tbl_tuvi WHERE duonglich LIKE '%" + year + "%' AND gioitinh = '" + gender + "'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            result = cursor.getString(2).split(" ")[1];
            cursor.moveToNext();
        }
        cursor.close();
        return result;
    }

    public String getTuviTrondoi(String gender, String year) {
        String result = "";
        Cursor cursor = this.database.rawQuery("SELECT tuvi_trondoi FROM tbl_tuvi WHERE duonglich LIKE '%" + year + "%' AND gioitinh = '" + gender + "'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            result = cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        return result;
    }

    public String getBoiPhuongDong(String year) {
        String yearName = "";
        int currentYear = Integer.parseInt(year);
        if (currentYear % 12 == 0) {
            yearName = "Thân";
        } else if (currentYear % 12 == 1) {
            yearName = "Dậu";
        } else if (currentYear % 12 == 2) {
            yearName = "Tuất";
        } else if (currentYear % 12 == 3) {
            yearName = "Hợi";
        } else if (currentYear % 12 == 4) {
            yearName = "Tý";
        } else if (currentYear % 12 == 5) {
            yearName = "Sửu";
        } else if (currentYear % 12 == 6) {
            yearName = "Dần";
        } else if (currentYear % 12 == 7) {
            yearName = "Mão";
        } else if (currentYear % 12 == 8) {
            yearName = "Thìn";
        } else if (currentYear % 12 == 9) {
            yearName = "Tỵ";
        } else if (currentYear % 12 == 10) {
            yearName = "Ngọ";
        } else if (currentYear % 12 == 11) {
            yearName = "Mùi";
        }
        String result = "";
        Cursor cursor = this.database.rawQuery("SELECT content FROM boi_phuong_dong WHERE tuoi = '" + yearName + "'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            result = cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        return result;
    }

    public String getBoiPhuongTay(String date) {
        Log.v(BaseActivity.TAG, "date trong databasesaccess: " + date);
        int day = Integer.parseInt(Utils.getTime(0, date));
        int month = Integer.parseInt(Utils.getTime(1, date));
        int id = 1;
        if (month == 3) {
            if (day < 21) {
                id = 12;
            } else {
                id = 1;
            }
        } else if (month == 4) {
            if (day < 21) {
                id = 1;
            } else {
                id = 2;
            }
        } else if (month == 5) {
            if (day < 22) {
                id = 2;
            } else {
                id = 3;
            }
        } else if (month == 6) {
            if (day < 22) {
                id = 3;
            } else {
                id = 4;
            }
        } else if (month == 7) {
            if (day < 23) {
                id = 4;
            } else {
                id = 5;
            }
        } else if (month == 8) {
            if (day < 24) {
                id = 5;
            } else {
                id = 6;
            }
        } else if (month == 9) {
            if (day < 23) {
                id = 6;
            } else {
                id = 7;
            }
        } else if (month == 10) {
            if (day < 24) {
                id = 7;
            } else {
                id = 8;
            }
        } else if (month == 11) {
            if (day < 23) {
                id = 8;
            } else {
                id = 9;
            }
        } else if (month == 12) {
            if (day < 22) {
                id = 9;
            } else {
                id = 10;
            }
        } else if (month == 1) {
            if (day < 22) {
                id = 10;
            } else {
                id = 11;
            }
        } else if (month == 2) {
            if (day < 19) {
                id = 11;
            } else {
                id = 12;
            }
        }
        return id + "";
    }

    public String getSao(int tuoi, int gender) {
        String result = "";
        Cursor cursor = this.database.rawQuery("SELECT tensao FROM tbl_sao WHERE tuoi = '" + tuoi + "' AND gioitinh = '" + gender + "'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            result = cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        return result;
    }

    public SaoInfo getSaoDetail(String tenSao) {
        SaoInfo mSaoInfo = new SaoInfo();
        Cursor cursor = this.database.rawQuery("SELECT mo_ta,giai_han FROM tbl_sao_detail WHERE tensao = '" + tenSao + "'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            mSaoInfo.setDescription(cursor.getString(0));
            mSaoInfo.setGiaiHan(cursor.getString(1));
            cursor.moveToNext();
        }
        cursor.close();
        return mSaoInfo;
    }

    public String getHan(int tuoi, int gender) {
        String result = "";
        Cursor cursor = this.database.rawQuery("SELECT tenhan FROM tbl_han WHERE tuoi = '" + tuoi + "' AND gioitinh = '" + gender + "'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            result = cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        return result;
    }

    public ArrayList<VanKhan> getVanKhan() {
        ArrayList<VanKhan> listVanKhan = new ArrayList();
        String result = "";
        Cursor cursor = this.database.rawQuery("SELECT * FROM tbl_van_khan", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            VanKhan mVanKhan = new VanKhan();
            mVanKhan.setId(cursor.getInt(0));
            mVanKhan.setContent(cursor.getString(1));
            mVanKhan.setGroup(cursor.getString(2));
            mVanKhan.setTitle(cursor.getString(4));
            mVanKhan.setTitleGroup(cursor.getString(6));
            listVanKhan.add(mVanKhan);
            cursor.moveToNext();
        }
        cursor.close();
        return listVanKhan;
    }

    public ArrayList<Holiday> getHoliday() {
        ArrayList<Holiday> listHoliday = new ArrayList();
        String result = "";
        Cursor cursor = this.database.rawQuery("SELECT * FROM tbl_ngay_le", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Holiday mHoliday = new Holiday();
            mHoliday.setName(cursor.getString(3));
            mHoliday.setContent(cursor.getString(6));
            listHoliday.add(mHoliday);
            cursor.moveToNext();
        }
        cursor.close();
        return listHoliday;
    }

    public ArrayList<DiaryInfo> getDiary() {
        ArrayList<DiaryInfo> listHoliday = new ArrayList();
        String result = "";
        Cursor cursor = this.database.rawQuery("SELECT * FROM diary ORDER BY date DESC", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            DiaryInfo mHoliday = new DiaryInfo();
            mHoliday.setId(cursor.getInt(0));
            mHoliday.setTitle(cursor.getString(1));
            mHoliday.setContent(cursor.getString(2));
            mHoliday.setDate(cursor.getString(3));
            mHoliday.setSetting(cursor.getString(4));
            mHoliday.setImagePath(cursor.getString(5));
            listHoliday.add(mHoliday);
            cursor.moveToNext();
        }
        cursor.close();
        return listHoliday;
    }

    public void addDiary(DiaryInfo mDiaryInfo) {
        ContentValues values = new ContentValues();
        values.put(ShareConstants.WEB_DIALOG_PARAM_ID, Integer.valueOf(mDiaryInfo.getId()));
        values.put("title", mDiaryInfo.getTitle());
        values.put(Param.CONTENT, mDiaryInfo.getContent());
        values.put("date", mDiaryInfo.getDate());
        values.put("setting", mDiaryInfo.getSetting());
        values.put("image_path", mDiaryInfo.getImagePath());
        this.database.insert("diary", null, values);
    }

    public void updateDiary(DiaryInfo mDiaryInfo) {
        ContentValues values = new ContentValues();
        values.put(ShareConstants.WEB_DIALOG_PARAM_ID, Integer.valueOf(mDiaryInfo.getId()));
        values.put("title", mDiaryInfo.getTitle());
        values.put(Param.CONTENT, mDiaryInfo.getContent());
        values.put("date", mDiaryInfo.getDate());
        values.put("setting", mDiaryInfo.getSetting());
        values.put("image_path", mDiaryInfo.getImagePath());
        this.database.update("diary", values, "id = ?", new String[]{String.valueOf(mDiaryInfo.getId())});
    }

    public void addEvent(EventInfo mEventInfo) {
        ContentValues values = new ContentValues();
        values.put(ShareConstants.WEB_DIALOG_PARAM_ID, Integer.valueOf(mEventInfo.getId()));
        values.put(ShareConstants.MEDIA_TYPE, Integer.valueOf(mEventInfo.getType()));
        values.put("name", mEventInfo.getName());
        values.put("solar", Integer.valueOf(mEventInfo.getSolar()));
        values.put("fullday", Integer.valueOf(mEventInfo.getFullDay()));
        values.put("start", mEventInfo.getStart());
        values.put("end", mEventInfo.getEnd());
        values.put("report", mEventInfo.getReport());
        values.put("repeat", mEventInfo.getRepeat());
        values.put("address", mEventInfo.getAddress());
        values.put("note", mEventInfo.getNote());
        values.put("alert", mEventInfo.getAlert());
        this.database.insert("event", null, values);
    }

    public void updateEvent(EventInfo mEventInfo) {
        ContentValues values = new ContentValues();
        values.put(ShareConstants.WEB_DIALOG_PARAM_ID, Integer.valueOf(mEventInfo.getId()));
        values.put(ShareConstants.MEDIA_TYPE, Integer.valueOf(mEventInfo.getType()));
        values.put("name", mEventInfo.getName());
        values.put("solar", Integer.valueOf(mEventInfo.getSolar()));
        values.put("fullday", Integer.valueOf(mEventInfo.getFullDay()));
        values.put("start", mEventInfo.getStart());
        values.put("end", mEventInfo.getEnd());
        values.put("report", mEventInfo.getReport());
        values.put("repeat", mEventInfo.getRepeat());
        values.put("address", mEventInfo.getAddress());
        values.put("note", mEventInfo.getNote());
        values.put("alert", mEventInfo.getAlert());
        this.database.update("event", values, "id = ?", new String[]{String.valueOf(mEventInfo.getId())});
    }

    public ArrayList<EventInfo> getEvent() {
        ArrayList<EventInfo> listEvent = new ArrayList();
        try {
            String result = "";
            Cursor cursor = this.database.rawQuery("SELECT * FROM event ORDER BY start ASC", null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                EventInfo mEventInfo = new EventInfo();
                mEventInfo.setId(cursor.getInt(0));
                mEventInfo.setType(cursor.getInt(1));
                mEventInfo.setName(cursor.getString(2));
                mEventInfo.setSolar(cursor.getInt(3));
                mEventInfo.setFullDay(cursor.getInt(4));
                mEventInfo.setStart(cursor.getString(5));
                mEventInfo.setEnd(cursor.getString(6));
                mEventInfo.setReport(cursor.getString(7));
                mEventInfo.setRepeat(cursor.getString(8));
                mEventInfo.setAddress(cursor.getString(9));
                mEventInfo.setNote(cursor.getString(10));
                mEventInfo.setAlert(cursor.getString(11));
                listEvent.add(mEventInfo);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (Exception e) {
        }
        return listEvent;
    }

    public Alert checkEventCompareDate(Calendar compareCalendar) {
        Cursor cursor = this.database.rawQuery("SELECT * FROM event ORDER BY start ASC", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            EventInfo mEventInfo = new EventInfo();
            mEventInfo.setId(cursor.getInt(0));
            mEventInfo.setType(cursor.getInt(1));
            mEventInfo.setName(cursor.getString(2));
            mEventInfo.setSolar(cursor.getInt(3));
            mEventInfo.setFullDay(cursor.getInt(4));
            mEventInfo.setStart(cursor.getString(5));
            mEventInfo.setEnd(cursor.getString(6));
            mEventInfo.setReport(cursor.getString(7));
            mEventInfo.setRepeat(cursor.getString(8));
            mEventInfo.setAddress(cursor.getString(9));
            mEventInfo.setNote(cursor.getString(10));
            mEventInfo.setAlert(cursor.getString(11));
            Calendar startTime = Calendar.getInstance();
            Calendar endTime = Calendar.getInstance();
            if (mEventInfo.getAlert() != null) {
                Alert mAlert = (Alert) new Gson().fromJson(mEventInfo.getAlert(), Alert.class);
                startTime.setTimeInMillis(mAlert.getStartTimeMillis());
                endTime.setTimeInMillis(mAlert.getEndTimeMillis());
                DateUtils.increaTime(startTime, mAlert);
                DateUtils.increaTime(endTime, mAlert);
                if (compareCalendar.getTimeInMillis() >= startTime.getTimeInMillis() && compareCalendar.getTimeInMillis() <= endTime.getTimeInMillis()) {
                    return mAlert;
                }
            }
            cursor.moveToNext();
        }
        cursor.close();
        return null;
    }

    public EventInfo getEventbyId(String id) {
        EventInfo mEventInfo = new EventInfo();
        String result = "";
        Cursor cursor = this.database.rawQuery("SELECT * FROM event where id = ?", new String[]{id});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            mEventInfo.setId(cursor.getInt(0));
            mEventInfo.setType(cursor.getInt(1));
            mEventInfo.setName(cursor.getString(2));
            mEventInfo.setSolar(cursor.getInt(3));
            mEventInfo.setFullDay(cursor.getInt(4));
            mEventInfo.setStart(cursor.getString(5));
            mEventInfo.setEnd(cursor.getString(6));
            mEventInfo.setReport(cursor.getString(7));
            mEventInfo.setRepeat(cursor.getString(8));
            mEventInfo.setAddress(cursor.getString(9));
            mEventInfo.setNote(cursor.getString(10));
            mEventInfo.setAlert(cursor.getString(11));
            cursor.moveToNext();
        }
        cursor.close();
        return mEventInfo;
    }

    public void deleteEvent(String id) {
        this.database.delete("event", "id = ?", new String[]{String.valueOf(id)});
    }

    public int getMaxEventID() {
        int mx = -1;
        try {
            Cursor cursor = this.database.rawQuery("SELECT max(id) from event", new String[0]);
            if (cursor != null && cursor.moveToFirst()) {
                mx = cursor.getInt(0);
            }
            return mx;
        } catch (Exception e) {
            return -1;
        }
    }

    public int getMaxDiaryID() {
        int mx = -1;
        try {
            Cursor cursor = this.database.rawQuery("SELECT max(id) from diary", new String[0]);
            if (cursor != null && cursor.moveToFirst()) {
                mx = cursor.getInt(0);
            }
            return mx;
        } catch (Exception e) {
            return -1;
        }
    }
}
