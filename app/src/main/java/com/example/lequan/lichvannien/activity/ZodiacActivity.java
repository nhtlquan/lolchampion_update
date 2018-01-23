package com.example.lequan.lichvannien.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.share.internal.ShareConstants;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import com.example.lequan.lichvannien.base.utils.Log;
import com.example.lequan.lichvannien.BaseActivity;
import com.example.lequan.lichvannien.MainApplication;
import com.example.lequan.lichvannien.adapter.ZodiacAdapter;
import com.example.lequan.lichvannien.common.Define;
import com.example.lequan.lichvannien.common.Prefs;
import com.example.lequan.lichvannien.model.Zodiac;
import com.example.lequan.lichvannien.network.DataLoader;
import com.example.lequan.lichvannien.utils.Utils;
import com.example.lequan.lichvannien.R;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ZodiacActivity extends BaseActivity {
    private MainApplication application;
    private ImageView imgAvatar;
    private JSONArray jsonArray;
    private ArrayList<Zodiac> lsZodiac = new ArrayList();
    private ZodiacAdapter mZodiacAdapter;
    private int position = 0;
    private RecyclerView rcZodiac;
    private TextView tvColor;
    private TextView tvDate;
    private TextView tvDob;
    private TextView tvFeeling;
    private TextView tvHeath;
    private TextView tvJob;
    private TextView tvLove;
    private TextView tvLucky;
    private TextView tvName;
    private TextView tvNumber;
    private TextView tvStar;
    private TextView tvSuccess;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_zodiac);
        this.application = (MainApplication) getApplication();
        getData();
        InitUI();
    }

    private void InitUI() {
        int positionBG = this.baseApplication.pref.getInt(AnhNenActivity.KEY_PREF_BG_POSITION, 0);
        String filePath = getFilesDir().getPath() + "/img/bg_noi_dung_" + positionBG + ".png";
        if (new File(filePath).exists()) {
            Glide.with(this.baseActivity).load(filePath).into((ImageView) findViewById(R.id.ivBG));
        } else if (this.application.listBGNoiDung.size() > positionBG) {
            Glide.with(this.baseActivity).load(this.application.listBGNoiDung.get(positionBG)).into((ImageView) findViewById(R.id.ivBG));
        }
        final ImageView ivBack = (ImageView) findViewById(R.id.btn_back_iv);
        ((RelativeLayout) findViewById(R.id.btn_back)).setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == 1) {
                    ivBack.setImageResource(R.drawable.ic_back);
                    ZodiacActivity.this.onBackPressed();
                    return true;
                } else if (event.getAction() != 0) {
                    return false;
                } else {
                    ivBack.setImageResource(R.drawable.ic_back_selected);
                    return true;
                }
            }
        });
        this.lsZodiac.clear();
        this.lsZodiac.add(new Zodiac(R.drawable.small_1, R.drawable.big_1, "Bạch Dương", "21/3-19/4", false));
        this.lsZodiac.add(new Zodiac(R.drawable.small_2, R.drawable.big_2, "Kim Ngưu", "20/4-20/5", false));
        this.lsZodiac.add(new Zodiac(R.drawable.small_3, R.drawable.big_3, "Song Tử", "21/5-21/6", false));
        this.lsZodiac.add(new Zodiac(R.drawable.small_4, R.drawable.big_4, "Cự Giải", "22/6-22/7", false));
        this.lsZodiac.add(new Zodiac(R.drawable.small_5, R.drawable.big_5, "Sư tử", "23/7-22/8", false));
        this.lsZodiac.add(new Zodiac(R.drawable.small_6, R.drawable.big_6, "Xử Nữ", "23/8-22/9", false));
        this.lsZodiac.add(new Zodiac(R.drawable.small_7, R.drawable.big_7, "Thiên Bình", "23/9-23/10", false));
        this.lsZodiac.add(new Zodiac(R.drawable.small_8, R.drawable.big_8, "Hổ Cáp", "24/10-21/11", false));
        this.lsZodiac.add(new Zodiac(R.drawable.small_9, R.drawable.big_9, "Nhân Mã", "22/11-21/12", false));
        this.lsZodiac.add(new Zodiac(R.drawable.small_10, R.drawable.big_10, "Ma Kết", "22/12-19/1", false));
        this.lsZodiac.add(new Zodiac(R.drawable.small_11, R.drawable.big_11, "Bảo Bình", "20/1-18/2", false));
        this.lsZodiac.add(new Zodiac(R.drawable.small_12, R.drawable.big_12, "Song Ngư", "19/2-20/3", false));
        this.rcZodiac = (RecyclerView) findViewById(R.id.recycle);
        this.rcZodiac.setLayoutManager(new LinearLayoutManager(this, 0, false));
        this.mZodiacAdapter = new ZodiacAdapter(this, this.lsZodiac);
        this.rcZodiac.setAdapter(this.mZodiacAdapter);
        this.imgAvatar = (ImageView) findViewById(R.id.img_avatar);
        this.tvName = (TextView) findViewById(R.id.tv_name);
        this.tvName.setTypeface(this.typeBoldNew);
        this.tvDob = (TextView) findViewById(R.id.tv_dob);
        this.tvDob.setTypeface(this.typeRegularNew);
        this.tvLove = (TextView) findViewById(R.id.tv_love);
        this.tvLove.setTypeface(this.typeRegularNew);
        this.tvJob = (TextView) findViewById(R.id.tv_job);
        this.tvJob.setTypeface(this.typeRegularNew);
        this.tvFeeling = (TextView) findViewById(R.id.tv_feel);
        this.tvFeeling.setTypeface(this.typeRegularNew);
        this.tvHeath = (TextView) findViewById(R.id.tv_heath);
        this.tvHeath.setTypeface(this.typeRegularNew);
        this.tvColor = (TextView) findViewById(R.id.tv_color);
        this.tvStar = (TextView) findViewById(R.id.tv_star);
        this.tvNumber = (TextView) findViewById(R.id.tv_number);
        this.tvSuccess = (TextView) findViewById(R.id.tv_success);
        this.tvColor.setTypeface(this.typeRegularNew);
        this.tvStar.setTypeface(this.typeRegularNew);
        this.tvNumber.setTypeface(this.typeRegularNew);
        this.tvSuccess.setTypeface(this.typeRegularNew);
        this.tvDate = (TextView) findViewById(R.id.tv_dates);
        this.tvDate.setTypeface(this.typeBoldNew);
        ((TextView) findViewById(R.id.textView165)).setTypeface(this.typeRegularNew);
        ((TextView) findViewById(R.id.textView23)).setTypeface(this.typeRegularNew);
        ((TextView) findViewById(R.id.textView11)).setTypeface(this.typeRegularNew);
        ((TextView) findViewById(R.id.textView12)).setTypeface(this.typeRegularNew);
        ((TextView) findViewById(R.id.textView13)).setTypeface(this.typeRegularNew);
        ((TextView) findViewById(R.id.tv_color_title)).setTypeface(this.typeRegularNew);
        ((TextView) findViewById(R.id.tv_star_title)).setTypeface(this.typeRegularNew);
        ((TextView) findViewById(R.id.tv_number_title)).setTypeface(this.typeRegularNew);
        ((TextView) findViewById(R.id.tv_success_title)).setTypeface(this.typeRegularNew);
        ((TextView) findViewById(R.id.header_button_center_tv)).setTypeface(this.typeRegularNew);
        String[] tempCurrentDay = Utils.getCurrentDay().split("/");
        ((TextView) findViewById(R.id.header_button_center_tv)).setText(Utils.getDayofWeek(Utils.getCurrentDay()) + ", " + tempCurrentDay[0] + "-" + tempCurrentDay[1] + "-" + tempCurrentDay[2]);
    }

    private void getData() {
        postData(DataLoader.GET, true, this, Define.API_ZODIAC, new String[0]);
    }

    protected void getResponse(String result, String api) {
        try {
            this.jsonArray = new JSONArray(result);
            updateData(checkZodiac());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void getError() {
        super.getError();
    }

    public void updateData(int index) {
        this.rcZodiac.scrollToPosition(index);
        if (this.jsonArray != null && this.jsonArray.toString() != null && this.jsonArray.toString().length() != 0) {
            try {
                int i;
                int id;
                JSONObject mJsonObject = this.jsonArray.getJSONObject(index + 1);
                this.tvDate.setText(Utils.getDayofWeek(Utils.getCurrentDay()) + " của " + mJsonObject.getString("name"));
                this.tvLove.setText(mJsonObject.getJSONObject(AppEventsConstants.EVENT_PARAM_VALUE_NO).getString(Param.CONTENT));
                int love_star = Integer.parseInt(mJsonObject.getJSONObject(AppEventsConstants.EVENT_PARAM_VALUE_NO).getString("number"));
                for (i = 0; i < 5; i++) {
                    id = getResources().getIdentifier("img" + (i + 1), ShareConstants.WEB_DIALOG_PARAM_ID, getPackageName());
                    if (i < love_star) {
                        findViewById(id).setVisibility(0);
                    } else {
                        findViewById(id).setVisibility(8);
                    }
                }
                this.tvJob.setText(mJsonObject.getJSONObject("1").getString(Param.CONTENT));
                int job_star = Integer.parseInt(mJsonObject.getJSONObject("1").getString("number"));
                for (i = 0; i < 5; i++) {
                    id = getResources().getIdentifier("img" + (i + 6), ShareConstants.WEB_DIALOG_PARAM_ID, getPackageName());
                    if (i < job_star) {
                        findViewById(id).setVisibility(0);
                    } else {
                        findViewById(id).setVisibility(8);
                    }
                }
                this.tvFeeling.setText(mJsonObject.getJSONObject("2").getString(Param.CONTENT));
                int feel_star = Integer.parseInt(mJsonObject.getJSONObject("2").getString("number"));
                for (i = 0; i < 5; i++) {
                    id = getResources().getIdentifier("img" + (i + 11), ShareConstants.WEB_DIALOG_PARAM_ID, getPackageName());
                    if (i < feel_star) {
                        findViewById(id).setVisibility(0);
                    } else {
                        findViewById(id).setVisibility(8);
                    }
                }
                this.tvHeath.setText(mJsonObject.getJSONObject("3").getString(Param.CONTENT));
                ((TextView) findViewById(R.id.tv_heath_index)).setText(mJsonObject.getJSONObject("3").getString("number"));
                ((TextView) findViewById(R.id.tv_heath_index)).setTypeface(this.typeBoldNew);
                this.tvColor.setText(mJsonObject.getJSONObject("4").getString("mau"));
                this.tvStar.setText(mJsonObject.getJSONObject("4").getString("saohopca"));
                this.tvNumber.setText(mJsonObject.getJSONObject("4").getString("somayman"));
                this.tvSuccess.setText(mJsonObject.getJSONObject("4").getString("damphan"));
                Iterator it = this.lsZodiac.iterator();
                while (it.hasNext()) {
                    ((Zodiac) it.next()).setSelect(false);
                }
                ((Zodiac) this.lsZodiac.get(index)).setSelect(true);
                this.mZodiacAdapter.notifyDataSetChanged();
                this.tvName.setText(((Zodiac) this.lsZodiac.get(index)).getName());
                this.tvDob.setText("(" + ((Zodiac) this.lsZodiac.get(index)).getDate() + ")");
                this.imgAvatar.setImageResource(((Zodiac) this.lsZodiac.get(index)).getBigAvatar());
            } catch (Exception e) {
                Log.m1447e("error request cung hoang dao: " + e.getMessage());
            }
        }
    }

    private int checkZodiac() {
        if (Utils.getDayMonth(Prefs.getValue(getApplicationContext(), Prefs.KEY_DOB)).length() == 0) {
            return 0;
        }
        int day = Integer.parseInt(Utils.getTime(0, Prefs.getValue(getApplicationContext(), Prefs.KEY_DOB)));
        int month = Integer.parseInt(Utils.getTime(1, Prefs.getValue(getApplicationContext(), Prefs.KEY_DOB)));
        if (day >= 21 && month == 3) {
            return 0;
        }
        if (day <= 19 && month == 4) {
            return 0;
        }
        if ((day >= 20 && month == 4) || (day <= 20 && month == 5)) {
            return 1;
        }
        if ((day >= 21 && month == 5) || (day <= 21 && month == 6)) {
            return 2;
        }
        if ((day >= 22 && month == 6) || (day <= 22 && month == 7)) {
            return 3;
        }
        if ((day >= 23 && month == 7) || (day <= 22 && month == 8)) {
            return 4;
        }
        if ((day >= 23 && month == 8) || (day <= 22 && month == 9)) {
            return 5;
        }
        if ((day >= 23 && month == 9) || (day <= 23 && month == 10)) {
            return 6;
        }
        if ((day >= 24 && month == 10) || (day <= 21 && month == 11)) {
            return 7;
        }
        if ((day >= 22 && month == 11) || (day <= 21 && month == 12)) {
            return 8;
        }
        if ((day >= 22 && month == 12) || (day <= 19 && month == 1)) {
            return 9;
        }
        if ((day >= 20 && month == 1) || (day <= 18 && month == 2)) {
            return 10;
        }
        if ((day < 19 || month != 2) && (day > 20 || month != 3)) {
            return 0;
        }
        return 11;
    }
}
