package com.example.lequan.lichvannien.activity;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.lequan.lichvannien.Debug;
import com.facebook.appevents.AppEventsConstants;
import com.example.lequan.lichvannien.base.utils.BaseUtils;
import com.example.lequan.lichvannien.BaseActivity;
import com.example.lequan.lichvannien.MainApplication;
import com.example.lequan.lichvannien.custom.DatePicker;
import com.example.lequan.lichvannien.custom.DatePicker.onActionFromDatePicker;
import com.example.lequan.lichvannien.database.DatabaseAccess;
import com.example.lequan.lichvannien.model.SaoInfo;
import com.example.lequan.lichvannien.utils.Utils;
import com.example.lequan.lichvannien.R;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class StarDetailActivity extends BaseActivity implements OnClickListener {
    public static int BOI_PHUONG_DONG = 3;
    public static int BOI_PHUONG_TAY = 4;
    public static int COI_SAO = 5;
    public static int TU_VI_2016 = 1;
    public static int TU_VI_TRON_DOI = 2;
    public static int TYPE_STAR = 0;
    private MainApplication application;
    DatePicker datePicker;
    String day = "";
    private String gender = "1";
    private LinearLayout layoutStar;
    RelativeLayout layoutYear;
    private RelativeLayout layout_gender;
    private ImageView layout_gender_line;
    private DatabaseAccess mDatabaseAccess;
    ProgressBar pb;
    TextView tvGender;
    TextView tvHan;
    TextView tvHeader;
    private TextView tvResult;
    TextView tvSao;
    TextView tvTitle;
    TextView tvYear;
    WebView webView;
    String year = "1990";
    private int yearOld = 27;

    class C11941 implements onActionFromDatePicker {
        C11941() {
        }

        public void onSelect(String date) {
            StarDetailActivity.this.day = date;
            StarDetailActivity.this.year = Utils.getTime(2, StarDetailActivity.this.day);
            StarDetailActivity.this.tvYear.setText(StarDetailActivity.this.year);
            switch (StarDetailActivity.TYPE_STAR) {
                case 1:
                    StarDetailActivity.this.showTuvi2016(StarDetailActivity.this.gender, StarDetailActivity.this.year);
                    return;
                case 2:
                    StarDetailActivity.this.showTuviTrondoi(StarDetailActivity.this.gender, StarDetailActivity.this.year);
                    return;
                case 3:
                    StarDetailActivity.this.showBoiPhuongDong(StarDetailActivity.this.year);
                    return;
                case 4:
                    StarDetailActivity.this.tvYear.setText(Utils.getTime(0, StarDetailActivity.this.day) + "/" + Utils.getTime(1, StarDetailActivity.this.day));
                    StarDetailActivity.this.showBoiPhuongTay(StarDetailActivity.this.gender, StarDetailActivity.this.day);
                    return;
                case 5:
                    StarDetailActivity.this.yearOld = (Integer.parseInt(Utils.getTime(2, Utils.getCurrentDay())) - Integer.parseInt(Utils.getTime(2, StarDetailActivity.this.day))) + 1;
                    StarDetailActivity.this.showCoiSao(StarDetailActivity.this.yearOld, Integer.parseInt(StarDetailActivity.this.gender));
                    return;
                default:
                    return;
            }
        }
    }

    class C11963 extends WebChromeClient {
        C11963() {
        }

        public void onProgressChanged(WebView view, int progress) {
            if (progress == 100) {
                StarDetailActivity.this.pb.setVisibility(8);
            } else {
                StarDetailActivity.this.pb.setVisibility(0);
            }
        }
    }

    class C11974 implements OnClickListener {
        C11974() {
        }

        public void onClick(View v) {
            StarDetailActivity.this.showChooseSingle("Lựa chọn giới tính", new String[]{"Nam", "Nữ"});
        }
    }

    class C11985 implements OnClickListener {
        C11985() {
        }

        public void onClick(View v) {
            switch (StarDetailActivity.TYPE_STAR) {
                case 1:
                case 2:
                case 3:
                    StarDetailActivity.this.datePicker.numberPickerDay.setVisibility(8);
                    StarDetailActivity.this.datePicker.numberPickerMonth.setVisibility(8);
                    break;
            }
            StarDetailActivity.this.datePicker.setVisibility(0);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_star_detail);
        this.application = (MainApplication) getApplication();
        InitUI();
    }

    private void InitUI() {
        this.layout_gender = (RelativeLayout) findViewById(R.id.layout_gender);
        this.layout_gender_line = (ImageView) findViewById(R.id.layout_gender_line);
        this.datePicker = (DatePicker) findViewById(R.id.datePicker);
        this.datePicker.setOnActionFromDatePicker(new C11941());
        int positionBG = this.baseApplication.pref.getInt(AnhNenActivity.KEY_PREF_BG_POSITION, 0);
        String filePath = getFilesDir().getPath() + "/img/bg_noi_dung_" + positionBG + ".png";
        if (new File(filePath).exists()) {
            Glide.with(this.baseActivity).load(filePath).into((ImageView) findViewById(R.id.ivBG));
        } else if (this.application.listBGNoiDung.size() > positionBG) {
            Glide.with(this.baseActivity).load(this.application.listBGNoiDung.get(positionBG)).into((ImageView) findViewById(R.id.ivBG));
        }
        ((TextView) findViewById(R.id.tvKetQuaTitle)).setTypeface(this.typeBoldNew);
        ((TextView) findViewById(R.id.tvSaoTitle)).setTypeface(this.typeBoldNew);
        ((TextView) findViewById(R.id.tvHanTitle)).setTypeface(this.typeBoldNew);
        final ImageView ivBack = (ImageView) findViewById(R.id.btn_back_iv);
        ((RelativeLayout) findViewById(R.id.btn_back)).setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == 1) {
                    ivBack.setImageResource(R.drawable.ic_back);
                    StarDetailActivity.this.onBackPressed();
                    return true;
                } else if (event.getAction() != 0) {
                    return false;
                } else {
                    ivBack.setImageResource(R.drawable.ic_back_selected);
                    return true;
                }
            }
        });
        this.layoutYear = (RelativeLayout) findViewById(R.id.layout_year);
        this.layoutStar = (LinearLayout) findViewById(R.id.layout_star);
        this.tvGender = (TextView) findViewById(R.id.layout_gender_tv);
        this.tvGender.setTypeface(this.typeRegularNew);
        this.tvYear = (TextView) findViewById(R.id.layout_year_tv);
        this.tvYear.setTypeface(this.typeRegularNew);
        this.tvResult = (TextView) findViewById(R.id.tv_result);
        this.tvResult.setTypeface(this.typeRegularNew);
        this.tvTitle = (TextView) findViewById(R.id.tv_title);
        this.tvTitle.setTypeface(this.typeRegularNew);
        this.tvHeader = (TextView) findViewById(R.id.tv_header);
        this.tvHeader.setTypeface(this.typeRegularNew);
        this.tvSao = (TextView) findViewById(R.id.tv_star);
        this.tvSao.setTypeface(this.typeRegularNew);
        this.tvHan = (TextView) findViewById(R.id.tv_han);
        this.tvHan.setTypeface(this.typeRegularNew);
        this.pb = (ProgressBar) findViewById(R.id.pb);
        this.webView = (WebView) findViewById(R.id.wv_result);
        this.webView.setWebChromeClient(new C11963());
        this.webView.setBackgroundColor(0);
        this.webView.getSettings().setDefaultTextEncodingName("utf-8");
        ((RelativeLayout) findViewById(R.id.layout_gender)).setOnClickListener(new C11974());
        ((RelativeLayout) findViewById(R.id.layout_year)).setOnClickListener(new C11985());
        this.mDatabaseAccess = DatabaseAccess.getInstance(this);
        this.mDatabaseAccess.open();
        switch (TYPE_STAR) {
            case 1:
                this.tvHeader.setText("Tử vi 2018");
                this.tvTitle.setText("Lựa chọn Năm sinh của bạn");
                this.tvResult.setVisibility(8);
                this.webView.setVisibility(0);
                this.layout_gender.setVisibility(8);
                this.layout_gender_line.setVisibility(8);
                showTuvi2016(this.gender, this.year);
                return;
            case 2:
                this.tvHeader.setText("Tử vi 2018 chi tiết");
                this.tvTitle.setText("Lựa chọn Giới tính và Năm sinh của bạn");
                showTuviTrondoi(this.gender, this.year);
                return;
            case 3:
                this.tvHeader.setText("Bói Phương Đông");
                this.tvTitle.setText("Lựa chọn Năm sinh của bạn");
                showBoiPhuongDong(this.year);
                return;
            case 4:
                this.tvHeader.setText("Cung hoàng đạo");
                this.tvTitle.setText("Lựa chọn Ngày sinh của bạn");
                this.tvResult.setVisibility(8);
                this.webView.setVisibility(0);
                this.day = Utils.getCurrentDay();
                this.year = Utils.getTime(2, this.day);
                this.tvYear.setText(this.year);
                showBoiPhuongTay(this.gender, this.day);
                return;
            case 5:
                this.tvHeader.setText("Xem sao - Coi hạn");
                this.layoutStar.setVisibility(0);
                this.yearOld = (Integer.parseInt(Utils.getTime(2, Utils.getCurrentDay())) - 1990) + 1;
                showCoiSao(this.yearOld, 1);
                return;
            default:
                return;
        }
    }

    public void onClick(View v) {
        v.getId();
    }

    protected void onChoise(String value, int index) {
        if (value.equalsIgnoreCase("Nam")) {
            this.gender = "1";
            this.tvGender.setText("Nam");
        } else {
            this.gender = AppEventsConstants.EVENT_PARAM_VALUE_NO;
            this.tvGender.setText("Nữ");
        }
        switch (TYPE_STAR) {
            case 1:
                showTuvi2016(this.gender, this.year);
                return;
            case 2:
                showTuviTrondoi(this.gender, this.year);
                return;
            case 4:
                showBoiPhuongTay(this.gender, this.day);
                break;
            case 5:
                break;
            default:
                return;
        }
        showCoiSao(this.yearOld, Integer.parseInt(this.gender));
    }

    protected void onSelectedDayPicker(String day) {
        this.day = day;
        this.year = Utils.getTime(2, day);
        this.tvYear.setText(this.year);
        switch (TYPE_STAR) {
            case 1:
                showTuvi2016(this.gender, this.year);
                return;
            case 2:
                showTuviTrondoi(this.gender, this.year);
                return;
            case 3:
                showBoiPhuongDong(this.year);
                return;
            case 4:
                this.tvYear.setText(Utils.getTime(0, day) + "/" + Utils.getTime(1, day));
                showBoiPhuongTay(this.gender, this.day);
                return;
            case 5:
                this.yearOld = (Integer.parseInt(Utils.getTime(2, Utils.getCurrentDay())) - Integer.parseInt(Utils.getTime(2, day))) + 1;
                showCoiSao(this.yearOld, Integer.parseInt(this.gender));
                return;
            default:
                return;
        }
    }

    private void showTuvi2016(String gender, String year) {
        String data = this.mDatabaseAccess.getTuvi2016(gender, year);
        if (data == null || data.length() == 0) {
            this.tvResult.setVisibility(0);
            this.webView.setVisibility(8);
            this.tvResult.setText("Không tìm thấy dữ liệu cho tuổi!");
            return;
        }
        this.tvResult.setVisibility(8);
        this.webView.setVisibility(0);
        int i = -1;
        switch (data.hashCode()) {
            case 2857:
                if (data.equals("Tý")) {
                    i = 0;
                    break;
                }
                break;
            case 10529:
                if (data.equals("Tỵ")) {
                    i = 5;
                    break;
                }
                break;
            case 81145:
                if (data.equals("Mão")) {
                    i = 3;
                    break;
                }
                break;
            case 81821:
                if (data.equals("Mùi")) {
                    i = 7;
                    break;
                }
                break;
            case 86036:
                if (data.equals("Ngọ")) {
                    i = 6;
                    break;
                }
                break;
            case 308715:
                if (data.equals("Dần")) {
                    i = 2;
                    break;
                }
                break;
            case 308908:
                if (data.equals("Dậu")) {
                    i = 9;
                    break;
                }
                break;
            case 314414:
                if (data.equals("Hợi")) {
                    i = 11;
                    break;
                }
                break;
            case 325307:
                if (data.equals("Sửu")) {
                    i = 1;
                    break;
                }
                break;
            case 2609504:
                if (data.equals("Thân")) {
                    i = 8;
                    break;
                }
                break;
            case 2609814:
                if (data.equals("Thìn")) {
                    i = 4;
                    break;
                }
                break;
            case 2858192:
                if (data.equals("Tuất")) {
                    i = 10;
                    break;
                }
                break;
        }
        switch (i) {
            case 0:
                this.webView.loadDataWithBaseURL(null, BaseUtils.encryptDecrypt(BaseUtils.readFileFromAsset(this, "a/11.txt")), "text/html", "utf-8", null);
                return;
            case 1:
                this.webView.loadDataWithBaseURL(null, BaseUtils.encryptDecrypt(BaseUtils.readFileFromAsset(this, "a/7.txt")), "text/html", "utf-8", null);
                return;
            case 2:
                this.webView.loadDataWithBaseURL(null, BaseUtils.encryptDecrypt(BaseUtils.readFileFromAsset(this, "a/1.txt")), "text/html", "utf-8", null);
                return;
            case 3:
                this.webView.loadDataWithBaseURL(null, BaseUtils.encryptDecrypt(BaseUtils.readFileFromAsset(this, "a/4.txt")), "text/html", "utf-8", null);
                return;
            case 4:
                this.webView.loadDataWithBaseURL(null, BaseUtils.encryptDecrypt(BaseUtils.readFileFromAsset(this, "a/9.txt")), "text/html", "utf-8", null);
                return;
            case 5:
                this.webView.loadDataWithBaseURL(null, BaseUtils.encryptDecrypt(BaseUtils.readFileFromAsset(this, "a/12.txt")), "text/html", "utf-8", null);
                return;
            case 6:
                this.webView.loadDataWithBaseURL(null, BaseUtils.encryptDecrypt(BaseUtils.readFileFromAsset(this, "a/6.txt")), "text/html", "utf-8", null);
                return;
            case 7:
                this.webView.loadDataWithBaseURL(null, BaseUtils.encryptDecrypt(BaseUtils.readFileFromAsset(this, "a/5.txt")), "text/html", "utf-8", null);
                return;
            case 8:
                this.webView.loadDataWithBaseURL(null, BaseUtils.encryptDecrypt(BaseUtils.readFileFromAsset(this, "a/8.txt")), "text/html", "utf-8", null);
                return;
            case 9:
                this.webView.loadDataWithBaseURL(null, BaseUtils.encryptDecrypt(BaseUtils.readFileFromAsset(this, "a/2.txt")), "text/html", "utf-8", null);
                return;
            case 10:
                this.webView.loadDataWithBaseURL(null, BaseUtils.encryptDecrypt(BaseUtils.readFileFromAsset(this, "a/10.txt")), "text/html", "utf-8", null);
                return;
            case 11:
                this.webView.loadDataWithBaseURL(null, BaseUtils.encryptDecrypt(BaseUtils.readFileFromAsset(this, "a/3.txt")), "text/html", "utf-8", null);
                return;
            default:
                this.tvResult.setVisibility(0);
                this.webView.setVisibility(8);
                this.tvResult.setText("Không có dữ liệu");
                return;
        }
    }

    String readAssetFile(String inFile) {
        Log.v(BaseActivity.TAG, "inFile: " + inFile);
        String tContents = "";
        try {
            InputStream stream = getAssets().open(inFile);
            byte[] buffer = new byte[stream.available()];
            stream.read(buffer);
            stream.close();
            tContents = new String(buffer);
        } catch (IOException e) {
            Log.e(BaseActivity.TAG, "error read assets file: " + e.getMessage());
        }
        return Html.fromHtml(tContents).toString();
    }

    private void showTuviTrondoi(String gender, String year) {
        if (Integer.parseInt(year) < 1930 || Integer.parseInt(year) > 2002) {
            showTuvi2016(gender, year);
            return;
        }
        this.tvResult.setVisibility(8);
        this.webView.setVisibility(0);
        Debug.e(BaseUtils.encryptDecrypt(BaseUtils.readFileFromAsset(this, "b/" + year + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + gender + ".txt")));
        this.webView.loadDataWithBaseURL(null, BaseUtils.encryptDecrypt(BaseUtils.readFileFromAsset(this, "b/" + year + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + gender + ".txt")), "text/html", "utf-8", null);
    }

    private void showBoiPhuongDong(String year) {
        String data = this.mDatabaseAccess.getBoiPhuongDong(year);
        if (data == null || data.length() == 0) {
            this.tvResult.setText("Không tìm thấy dữ liệu cho tuổi!");
        } else {
            this.tvResult.setText(data);
        }
    }

    private void showBoiPhuongTay(String gender, String year) {
        String data = this.mDatabaseAccess.getBoiPhuongTay(year);
        Log.v(BaseActivity.TAG, "gender: " + gender + " year: " + year + " data: " + data);
        if (data == null || data.length() == 0) {
            this.tvResult.setText("Không tìm thấy dữ liệu cho tuổi!");
            return;
        }
        int obj = -1;
        switch (data.hashCode()) {
            case 49:
                if (data.equals("1")) {
                    obj = 0;
                    break;
                }
                break;
            case 50:
                if (data.equals("2")) {
                    obj = 1;
                    break;
                }
                break;
            case 51:
                if (data.equals("3")) {
                    obj = 2;
                    break;
                }
                break;
            case 52:
                if (data.equals("4")) {
                    obj = 3;
                    break;
                }
                break;
            case 53:
                if (data.equals("5")) {
                    obj = 4;
                    break;
                }
                break;
            case 54:
                if (data.equals("6")) {
                    obj = 5;
                    break;
                }
                break;
            case 55:
                if (data.equals("7")) {
                    obj = 6;
                    break;
                }
                break;
            case 56:
                if (data.equals("8")) {
                    obj = 7;
                    break;
                }
                break;
            case 57:
                if (data.equals("9")) {
                    obj = 8;
                    break;
                }
                break;
            case 1567:
                if (data.equals("10")) {
                    obj = 9;
                    break;
                }
                break;
            case 1568:
                if (data.equals("11")) {
                    obj = 10;
                    break;
                }
                break;
            case 1569:
                if (data.equals("12")) {
                    obj = 11;
                    break;
                }
                break;
        }
        switch (obj) {
            case 0:
                if (gender.equals("1")) {
                    this.webView.loadUrl("file:///android_asset/hoangdao/nam/bach_duong.htm");
                    return;
                } else {
                    this.webView.loadUrl("file:///android_asset/hoangdao/nu/bach_duong.htm");
                    return;
                }
            case 1:
                if (gender.equals("1")) {
                    this.webView.loadUrl("file:///android_asset/hoangdao/nam/kim_nguu.htm");
                    return;
                } else {
                    this.webView.loadUrl("file:///android_asset/hoangdao/nu/kim_nguu.htm");
                    return;
                }
            case 2:
                if (gender.equals("1")) {
                    this.webView.loadUrl("file:///android_asset/hoangdao/nam/song_tu.htm");
                    return;
                } else {
                    this.webView.loadUrl("file:///android_asset/hoangdao/nu/song_tu.htm");
                    return;
                }
            case 3:
                if (gender.equals("1")) {
                    this.webView.loadUrl("file:///android_asset/hoangdao/nam/cu_giai.htm");
                    return;
                } else {
                    this.webView.loadUrl("file:///android_asset/hoangdao/nu/cu_giai.htm");
                    return;
                }
            case 4:
                if (gender.equals("1")) {
                    this.webView.loadUrl("file:///android_asset/hoangdao/nam/su_tu.htm");
                    return;
                } else {
                    this.webView.loadUrl("file:///android_asset/hoangdao/nu/su_tu.htm");
                    return;
                }
            case 5:
                if (gender.equals("1")) {
                    this.webView.loadUrl("file:///android_asset/hoangdao/nam/xu_nu.htm");
                    return;
                } else {
                    this.webView.loadUrl("file:///android_asset/hoangdao/nu/xu_nu.htm");
                    return;
                }
            case 6:
                if (gender.equals("1")) {
                    this.webView.loadUrl("file:///android_asset/hoangdao/nam/thien_binh.htm");
                    return;
                } else {
                    this.webView.loadUrl("file:///android_asset/hoangdao/nu/thien_binh.htm");
                    return;
                }
            case 7:
                if (gender.equals("1")) {
                    this.webView.loadUrl("file:///android_asset/hoangdao/nam/ho_cap.htm");
                    return;
                } else {
                    this.webView.loadUrl("file:///android_asset/hoangdao/nu/ho_cap.htm");
                    return;
                }
            case 8:
                if (gender.equals("1")) {
                    this.webView.loadUrl("file:///android_asset/hoangdao/nam/nhan_ma.htm");
                    return;
                } else {
                    this.webView.loadUrl("file:///android_asset/hoangdao/nu/nhan_ma.htm");
                    return;
                }
            case 9:
                if (gender.equals("1")) {
                    this.webView.loadUrl("file:///android_asset/hoangdao/nam/ma_ket.htm");
                    return;
                } else {
                    this.webView.loadUrl("file:///android_asset/hoangdao/nu/ma_ket.htm");
                    return;
                }
            case 10:
                if (gender.equals("1")) {
                    this.webView.loadUrl("file:///android_asset/hoangdao/nam/bao_binh.htm");
                    return;
                } else {
                    this.webView.loadUrl("file:///android_asset/hoangdao/nu/bao_binh.htm");
                    return;
                }
            case 11:
                if (gender.equals("1")) {
                    this.webView.loadUrl("file:///android_asset/hoangdao/nam/song_ngu.htm");
                    return;
                } else {
                    this.webView.loadUrl("file:///android_asset/hoangdao/nu/song_ngu.htm");
                    return;
                }
            default:
                this.tvResult.setText("Không có dữ liệu");
                return;
        }
    }

    private void showCoiSao(int tuoi, int gender) {
        String sao = this.mDatabaseAccess.getSao(tuoi, gender);
        String han = this.mDatabaseAccess.getHan(tuoi, gender);
        if (sao == null || sao.length() == 0) {
            this.tvResult.setText("Không tìm thấy dữ liệu cho tuổi!");
        } else {
            this.tvSao.setText(sao);
            SaoInfo mSaoInfo = this.mDatabaseAccess.getSaoDetail(sao);
            this.tvResult.setText("Bình sao: \n " + mSaoInfo.getDescription() + " \n\n Bình hạn: \n\n" + mSaoInfo.getGiaiHan());
        }
        if (han == null || han.length() == 0) {
            this.tvResult.setText("Không tìm thấy dữ liệu cho tuổi!");
        } else {
            this.tvHan.setText(han);
        }
    }
}
