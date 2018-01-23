package com.example.lequan.lichvannien;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.facebook.appevents.AppEventsConstants;
import com.example.lequan.lichvannien.base.utils.BaseUtils;
import com.example.lequan.lichvannien.base.utils.Log;
import com.example.lequan.lichvannien.activity.AnhNenActivity;
import com.example.lequan.lichvannien.activity.EventDetailActivity;
import com.example.lequan.lichvannien.activity.HomeActivity;
import com.example.lequan.lichvannien.activity.PersonalActivity;
import com.example.lequan.lichvannien.adapter.GioHoangDaoAdapter;
import com.example.lequan.lichvannien.common.Define;
import com.example.lequan.lichvannien.dao.DAOWeather;
import com.example.lequan.lichvannien.database.DatabaseAccess;
import com.example.lequan.lichvannien.model.DayInfo;
import com.example.lequan.lichvannien.model.DiaryCustom;
import com.example.lequan.lichvannien.network.DataLoader;
import com.example.lequan.lichvannien.network.StringRequestCallback;
import com.example.lequan.lichvannien.utils.BitmapUtils;
import com.example.lequan.lichvannien.utils.DateUtils;
import com.example.lequan.lichvannien.utils.NSLog;
import com.example.lequan.lichvannien.utils.UtilJS;
import com.example.lequan.lichvannien.utils.Utils;
import com.rey.material.app.BottomSheetDialog;
import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;
import com.rey.material.app.ThemeManager;
import com.rey.material.app.TimePickerDialog;
import com.example.lequan.lichvannien.samsistemas.calendarview.widget.EventInfo;
import com.squareup.picasso.Picasso;
import com.example.lequan.lichvannien.R;

import org.jsoup.nodes.Document;

import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import okhttp3.Request.Builder;
import okhttp3.Response;

public class BaseActivity extends com.example.lequan.lichvannien.base.BaseActivity {
    public static final int PICK_IMAGE_FROM_CAMERA_REQUEST_CODE = 1001;
    public static final int PICK_IMAGE_FROM_GALLERY_REQUEST_CODE = 1002;
    public static int REQUESTCODE = 123;
    public static final String TAG = "HuyAnh";
    String PATH = "";
    DayInfo currentDay;
    private boolean isPaused;
    private boolean isStopped;
    private BottomSheetDialog mBottomSheetDialog;
    protected DatabaseAccess mDatabaseAccess;
    private MainApplication mainApplication;
    long timeDelay = 0;
    public Typeface typeBoldNew;
    public Typeface typeRegularNew;
    private Uri uri_image;

    class C11328 implements OnCancelListener {
        C11328() {
        }

        public void onCancel(DialogInterface dialog) {
            BaseActivity.this.showPopup(null, false);
        }
    }

    public interface DiaryListenner {
        void onSelectBackground(DiaryCustom diaryCustom);

        void onSelectColor(DiaryCustom diaryCustom);

        void onSelectText(DiaryCustom diaryCustom);
    }

    class asyncTask extends AsyncTask<String, Void, Boolean> {
        Button btTryAgain;
        ProgressBar pbWV;
        TextView tvError;
        WebView wvChiTiet;

        class C11341 implements OnClickListener {
            C11341() {
            }

            public void onClick(View v) {
                new asyncTask(asyncTask.this.pbWV, asyncTask.this.wvChiTiet, asyncTask.this.tvError, asyncTask.this.btTryAgain).execute(new String[]{"http://tracuu.tuvisomenh.com/xem-ngay-tot-xau"});
            }
        }

        asyncTask(ProgressBar pbWV, WebView wvChiTiet, TextView tvError, Button btTryAgain) {
            this.pbWV = pbWV;
            this.wvChiTiet = wvChiTiet;
            this.tvError = tvError;
            this.btTryAgain = btTryAgain;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            this.pbWV.setVisibility(0);
            this.wvChiTiet.setVisibility(8);
            this.tvError.setVisibility(8);
            this.btTryAgain.setVisibility(8);
        }

        protected Boolean doInBackground(String... params) {
            try {
                Log.m1449v("url GET: " + params[0]);
                Response response = BaseActivity.this.baseApplication.getOkHttpClient().newCall(new Builder().url(params[0]).build()).execute();
                ArrayList<Object> paramsOb = new ArrayList();
                paramsOb.add(new UtilJS());
                paramsOb.add(response.body().string());

                String result = BaseActivity.this.runJs("parseHTML2", paramsOb);
                if (result.equals("")) {
                    return Boolean.valueOf(false);
                }
                BaseUtils.writeTxtFile(new File(BaseActivity.this.PATH + "temp.html"), "<html lang=\"vi-VN\">\n<head prefix=\"og: http://ogp.me/ns# fb: http://ogp.me/ns/fb#\">\n<meta charset=\"utf-8\" />\n</head>" + result + BaseUtils.readFileFromAsset(BaseActivity.this, "mobile.css") + BaseUtils.readFileFromAsset(BaseActivity.this, "mobileNewChiTiet.css") + "</html>");
                return Boolean.valueOf(true);
            } catch (Exception e) {
                Log.m1447e("error testJS run: " + e.getMessage());
                return Boolean.valueOf(false);
            }
        }

        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            try {
                this.pbWV.setVisibility(8);
                if (aBoolean.booleanValue()) {
                    this.wvChiTiet.setVisibility(0);
                    this.wvChiTiet.loadUrl("file://" + BaseActivity.this.PATH + "temp.html");
                    return;
                }
                this.tvError.setVisibility(0);
                this.btTryAgain.setVisibility(0);
                this.btTryAgain.setOnClickListener(new C11341());
            } catch (Exception e) {
                Log.m1447e("error load content to wv: " + e.getMessage());
            }
        }
    }

    class impleOnClickEvent implements OnClickListener {
        EventInfo mEventInfo;

        public impleOnClickEvent(EventInfo mEventInfo) {
            this.mEventInfo = mEventInfo;
        }

        public void onClick(View v) {
            Intent mIntent = new Intent(BaseActivity.this, EventDetailActivity.class);
            mIntent.putExtra("event_infos", this.mEventInfo);
            ((HomeActivity) BaseActivity.this).startActivityForResult(mIntent, BaseActivity.REQUESTCODE);
            BaseActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mainApplication = (MainApplication) getApplication();
        this.PATH = "/data/data/" + getPackageName() + "/files/";
        new File(this.PATH).mkdirs();
        if (!new File(this.PATH + "lvn.js").exists()) {
            BaseUtils.copyFilefromAsset(getApplication(), "test.js", this.PATH + "lvn.js");
        }
        this.mDatabaseAccess = new DatabaseAccess(this);
        try {
            this.mDatabaseAccess.createDataBase();
            this.mDatabaseAccess.open();
        } catch (Exception e) {
            Log.m1447e("error creat, open db: " + e.getMessage());
        }
        Define.isReset = true;
        this.typeRegularNew = Typeface.createFromAsset(getAssets(), "fonts/UTM Helve.ttf");
        this.typeBoldNew = Typeface.createFromAsset(getAssets(), "fonts/UTM HelveBold.ttf");
    }

    public void postData(String method, boolean isShowLoading, BaseActivity activity, final String api, String... param) {
        Log.m1449v("api: " + api);
        DataLoader.postAPI(method, isShowLoading, activity, api, new StringRequestCallback() {
            public void onSuccess(String response) {
                NSLog.m1451d(BaseActivity.TAG, "Success: " + response);
                Log.m1449v("response: " + response);
                BaseActivity.this.getResponse(response, api);
            }

            public void onError(int statusCode, String responseString) {
                NSLog.m1451d(BaseActivity.TAG, "Error: " + responseString);
                Log.m1449v("Error: : " + responseString);
                BaseActivity.this.getError();
            }
        }, param);
    }

    protected void getError() {
    }

    protected void showImage(ImageView img, String path) {
        Log.m1449v("path avatar: " + path);
        if (!path.equals("")) {
            File avatarFile = new File(path);
            if (avatarFile.exists()) {
                Picasso.with(this).load(avatarFile).into(img);
            } else {
                Picasso.with(this).load(path).into(img);
            }
        }
    }

    public void changeActivity(Class act) {
        startActivity(new Intent(this, act));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    public void changeActivity(boolean isResult, Class act) {
        startActivityForResult(new Intent(this, act), REQUESTCODE);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    public void changeActivity(Class act, String value, String content) {
        Intent mIntent = new Intent(this, act);
        mIntent.putExtra(value, content);
        startActivity(mIntent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    protected void getResponse(String jsonObject, String api) {
    }

    protected void showInterAds() {
    }

    public void choosePhoto() {
        checkAndRequestPermissionsChoosePhoto();
        final Dialog dialog = new Dialog(this);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.layout_choose_photo);
        Button btnGallery = (Button) dialog.findViewById(R.id.btn_gallery);
        ((Button) dialog.findViewById(R.id.btn_camera)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                BaseActivity.this.uri_image = BitmapUtils.getOutputMediaFileUri();
                intent.putExtra("output", BaseActivity.this.uri_image);
                BaseActivity.this.startActivityForResult(intent, 1001);
                dialog.dismiss();
            }
        });
        btnGallery.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                BaseActivity.this.startActivityForResult(new Intent("android.intent.action.PICK", Media.EXTERNAL_CONTENT_URI), 1002);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    protected void showToast(Context mContext, String msg) {
        Toast.makeText(mContext, msg, 1).show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            Log.m1446d("cancel image");
        }
        if (requestCode == 1001 && resultCode == -1 && this.uri_image != null && this.uri_image.getPath() != null && this.uri_image.getPath().length() > 0) {
            getBitmap(this.uri_image.getPath(), BitmapUtils.processToteImage(this.uri_image.getPath(), BitmapUtils.getResizedBitmap(BitmapUtils.decodeSampledBitmapFromPath(this.uri_image.getPath(), 200, 200), 150)));
        }
        if (requestCode == 1002 && resultCode == -1) {
            String[] filePathColumn = new String[]{"_data"};
            Cursor cursor = getContentResolver().query(data.getData(), filePathColumn, null, null, null);
            cursor.moveToFirst();
            String picturePath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
            cursor.close();
            if (!(picturePath == null || picturePath.length() <= 0 || picturePath.contains(".gif"))) {
                getBitmap(picturePath, BitmapUtils.processToteImage(picturePath, BitmapUtils.getResizedBitmap(BitmapUtils.decodeSampledBitmapFromPath(picturePath, 200, 200), 150)));
            }
            Log.m1446d("image path: " + picturePath);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    public void showDateDetail(String mDayInfo) {
        if (Calendar.getInstance().getTimeInMillis() - this.timeDelay >= 1000) {
            this.timeDelay = Calendar.getInstance().getTimeInMillis();
            this.mDatabaseAccess.open();
            this.currentDay = this.mDatabaseAccess.getDay(mDayInfo);
            if (this.currentDay == null) {
                Toast.makeText(this, "Chưa có dữ liệu!", 0).show();
                return;
            }
            this.mBottomSheetDialog = new BottomSheetDialog(this, R.style.Material_App_BottomSheetDialog);
            this.mBottomSheetDialog.inDuration(300);
            this.mBottomSheetDialog.outDuration(300);
            View v = LayoutInflater.from(this).inflate(R.layout.picker_selected_day, null);
            int positionBG = this.baseApplication.pref.getInt(AnhNenActivity.KEY_PREF_BG_POSITION, 0);
            String filePath = getFilesDir().getPath() + "/img/bg_noi_dung_" + positionBG + ".png";
            if (new File(filePath).exists()) {
                Glide.with(this.baseActivity).load(filePath).into((ImageView) v.findViewById(R.id.ivBG));
            } else if (this.mainApplication.listBGNoiDung.size() > positionBG) {
                Glide.with(this.baseActivity).load(this.mainApplication.listBGNoiDung.get(positionBG)).into((ImageView) v.findViewById(R.id.ivBG));
            }
            final RecyclerView rcHour = (RecyclerView) v.findViewById(R.id.recycleGio);
            final RecyclerView rcTuoiXung = (RecyclerView) v.findViewById(R.id.recycle_xung);
            RelativeLayout rlRightHeader = (RelativeLayout) v.findViewById(R.id.header_button_right);
            RelativeLayout rlBack = (RelativeLayout) v.findViewById(R.id.btn_today);
            final TextView tvHuongXuatHanh = (TextView) v.findViewById(R.id.tv_xuat_hanh);
            final TextView tvKhongLam = (TextView) v.findViewById(R.id.tv_khong_nen_lam);
            final TextView tvSaoTot = (TextView) v.findViewById(R.id.tv_sao_tot);
            final TextView tvNenLam = (TextView) v.findViewById(R.id.tv_viec_nen_lam);
            final TextView tvDay = (TextView) v.findViewById(R.id.tvDay);
            tvDay.setTypeface(this.typeBoldNew);
            final TextView tvLunarDay = (TextView) v.findViewById(R.id.tv_day);
            final TextView tvLunarMonth = (TextView) v.findViewById(R.id.tv_month);
            final TextView tvLunarYear = (TextView) v.findViewById(R.id.tv_year);
            final TextView tvLunarDaySo = (TextView) v.findViewById(R.id.tv_daySo);
            final TextView tvLunarMonthSo = (TextView) v.findViewById(R.id.tv_monthSo);
            final TextView tvLunarYearSo = (TextView) v.findViewById(R.id.tv_yearSo);
            tvLunarDaySo.setTypeface(this.typeRegularNew);
            tvLunarMonthSo.setTypeface(this.typeRegularNew);
            tvLunarYearSo.setTypeface(this.typeRegularNew);
            ((TextView) v.findViewById(R.id.tv_lunar)).setTypeface(this.typeRegularNew);
            ((TextView) v.findViewById(R.id.tv_event)).setTypeface(this.typeRegularNew);
            ((TextView) v.findViewById(R.id.tv_gio_hoang_dao)).setTypeface(this.typeRegularNew);
            ((TextView) v.findViewById(R.id.tv_huong_xuat_hanh)).setTypeface(this.typeRegularNew);
            ((TextView) v.findViewById(R.id.tv_sao_tot_header)).setTypeface(this.typeRegularNew);
            ((TextView) v.findViewById(R.id.tv_viec_nen_lam_header)).setTypeface(this.typeRegularNew);
            ((TextView) v.findViewById(R.id.tv_viec_khong_nen_lam_header)).setTypeface(this.typeRegularNew);
            ((TextView) v.findViewById(R.id.tv_tuoi_xung_header)).setTypeface(this.typeRegularNew);
            ((TextView) v.findViewById(R.id.tv_dayChu)).setTypeface(this.typeRegularNew);
            ((TextView) v.findViewById(R.id.tv_monthChu)).setTypeface(this.typeRegularNew);
            ((TextView) v.findViewById(R.id.tv_yearChu)).setTypeface(this.typeRegularNew);
            RelativeLayout rlPrevious = (RelativeLayout) v.findViewById(R.id.btn_previous);
            RelativeLayout rlNext = (RelativeLayout) v.findViewById(R.id.btn_next);
            final LinearLayout layoutEvent = (LinearLayout) v.findViewById(R.id.layout_event);
            updateEventDay(layoutEvent);
            final LinearLayout lnThoiTiet = (LinearLayout) v.findViewById(R.id.ln_thoi_tiet);
            final ImageView imvWeather = (ImageView) v.findViewById(R.id.imv_icon_weather);
            final TextView tvDegree = (TextView) v.findViewById(R.id.tv_degree);
            tvDegree.setTypeface(this.typeBoldNew);
            final TextView tvTTTT = (TextView) v.findViewById(R.id.tv_trang_thai_thoi_tiet);
            tvTTTT.setTypeface(this.typeRegularNew);
            final TextView tvDoAm = (TextView) v.findViewById(R.id.tv_DoAmContent);
            final TextView tvTocDoGio = (TextView) v.findViewById(R.id.tv_toc_do_gioContent);
            final TextView tvMatTroiMoc = (TextView) v.findViewById(R.id.tv_mat_troi_mocContent);
            tvDoAm.setTypeface(this.typeRegularNew);
            ((TextView) v.findViewById(R.id.tv_DoAm)).setTypeface(this.typeRegularNew);
            tvTocDoGio.setTypeface(this.typeRegularNew);
            ((TextView) v.findViewById(R.id.tv_toc_do_gio)).setTypeface(this.typeRegularNew);
            tvMatTroiMoc.setTypeface(this.typeRegularNew);
            ((TextView) v.findViewById(R.id.tv_mat_troi_moc)).setTypeface(this.typeRegularNew);
            LinearLayout llLichAm = (LinearLayout) v.findViewById(R.id.ll_licham);
            final ImageView ivPrevious = (ImageView) v.findViewById(R.id.btn_previous_iv);
            final RelativeLayout rlChiTietNgay = (RelativeLayout) v.findViewById(R.id.rlChiTietNgay);
            WebView wvChiTiet = (WebView) v.findViewById(R.id.wv_chitiet);
            wvChiTiet.getSettings().setJavaScriptEnabled(true);
            wvChiTiet.setBackgroundColor(0);
            ProgressBar pbWV = (ProgressBar) v.findViewById(R.id.pbWV);
            TextView tvError = (TextView) v.findViewById(R.id.tvError);
            Button btTryAgain = (Button) v.findViewById(R.id.btTryAgain);
            if (mDayInfo.equals(Utils.getCurrentDay())) {
                rlChiTietNgay.setVisibility(0);
                new asyncTask(pbWV, wvChiTiet, tvError, btTryAgain).execute(new String[]{"http://tracuu.tuvisomenh.com/xem-ngay-tot-xau"});
            }
            final ProgressBar progressBar = pbWV;
            final WebView webView = wvChiTiet;
            final TextView textView = tvError;
            final Button button = btTryAgain;
            rlPrevious.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == 1) {
                        ivPrevious.setImageResource(R.drawable.ic_back);
                        BaseActivity.this.currentDay = BaseActivity.this.mDatabaseAccess.getDay(Utils.getDayChange(BaseActivity.this.currentDay.getDuongLich(), 0, -1));
                        if (BaseActivity.this.currentDay.getDuongLich() == null) {
                            return true;
                        }
                        Log.m1449v("currentDay DuongLich: " + BaseActivity.this.currentDay.getDuongLich() + " now: " + Utils.getCurrentDay());
                        if (BaseActivity.this.currentDay.getDuongLich().equals(Utils.getCurrentDay())) {
                            rlChiTietNgay.setVisibility(0);
                            new asyncTask(progressBar, webView, textView, button).execute(new String[]{"http://tracuu.tuvisomenh.com/xem-ngay-tot-xau"});
                        } else {
                            rlChiTietNgay.setVisibility(8);
                            progressBar.setVisibility(8);
                            webView.setVisibility(8);
                            textView.setVisibility(8);
                            button.setVisibility(8);
                        }
                        tvHuongXuatHanh.setText(" -  " + BaseActivity.this.currentDay.getHuongXuatHanh().replaceAll(" , ", "\n\n -  "));
                        tvSaoTot.setText(" - SAO TỐT: " + BaseActivity.this.currentDay.getSaoTot() + "\n - SAO XẤU: " + BaseActivity.this.currentDay.getSaoXau());
                        tvNenLam.setText(BaseActivity.this.currentDay.getNenLam());
                        tvKhongLam.setText(BaseActivity.this.currentDay.getKhongNenLam());
                        String[] tempDuongLich = BaseActivity.this.currentDay.getDuongLich().split("/");
                        tvDay.setText(BaseActivity.this.currentDay.getThu() + ", " + tempDuongLich[0] + "-" + tempDuongLich[1] + "-" + tempDuongLich[2]);
                        tvLunarDay.setText(BaseActivity.this.currentDay.getNgay());
                        tvLunarMonth.setText(BaseActivity.this.currentDay.getThang());
                        tvLunarYear.setText(BaseActivity.this.currentDay.getNam());
                        String[] tempAmLich = BaseActivity.this.currentDay.getAmLich().split("/");
                        tvLunarDaySo.setText(tempAmLich[0]);
                        tvLunarMonthSo.setText(tempAmLich[1]);
                        tvLunarYearSo.setText(tempAmLich[2]);
                        String[] arr = BaseActivity.this.currentDay.getGioHoangDao().split(", ");
                        LinearLayoutManager layoutManager = new LinearLayoutManager(BaseActivity.this, 0, false);
                        rcHour.setAdapter(new GioHoangDaoAdapter(BaseActivity.this, arr));
                        rcTuoiXung.setAdapter(new GioHoangDaoAdapter(BaseActivity.this, BaseActivity.this.currentDay.getTuoiXungKhac().split(", "), true));
                        BaseActivity.this.initThoiTiet(lnThoiTiet, tvDegree, tvDoAm, tvTocDoGio, tvMatTroiMoc, tvTTTT, imvWeather);
                        BaseActivity.this.updateEventDay(layoutEvent);
                        return true;
                    } else if (event.getAction() != 0) {
                        return false;
                    } else {
                        ivPrevious.setImageResource(R.drawable.ic_back_selected);
                        return true;
                    }
                }
            });
            final ImageView ivNext = (ImageView) v.findViewById(R.id.btn_next_iv);
            final RelativeLayout relativeLayout = rlChiTietNgay;
            final ProgressBar progressBar2 = pbWV;
            final WebView webView2 = wvChiTiet;
            final TextView textView2 = tvError;
            final Button button2 = btTryAgain;
            final TextView textView3 = tvHuongXuatHanh;
            final TextView textView4 = tvSaoTot;
            final TextView textView5 = tvNenLam;
            final TextView textView6 = tvKhongLam;
            final TextView textView7 = tvLunarDay;
            final TextView textView8 = tvLunarMonth;
            final TextView textView9 = tvLunarYear;
            final TextView textView10 = tvLunarDaySo;
            final TextView textView11 = tvLunarMonthSo;
            final TextView textView12 = tvLunarYearSo;
            final TextView textView13 = tvDay;
            final RecyclerView recyclerView = rcHour;
            final RecyclerView recyclerView2 = rcTuoiXung;
            final LinearLayout linearLayout = lnThoiTiet;
            final TextView textView14 = tvDegree;
            final TextView textView15 = tvDoAm;
            final TextView textView16 = tvTocDoGio;
            final TextView textView17 = tvMatTroiMoc;
            final TextView textView18 = tvTTTT;
            final ImageView imageView = imvWeather;
            final LinearLayout linearLayout2 = layoutEvent;
            rlNext.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == 1) {
                        ivNext.setImageResource(R.drawable.ic_back);
                        BaseActivity.this.currentDay = BaseActivity.this.mDatabaseAccess.getDay(Utils.getDayChange(BaseActivity.this.currentDay.getDuongLich(), 0, 1));
                        if (BaseActivity.this.currentDay.getDuongLich() == null) {
                            return true;
                        }
                        Log.m1449v("duongLich: " + BaseActivity.this.currentDay.getDuongLich() + " now: " + Utils.getCurrentDay());
                        if (BaseActivity.this.currentDay.getDuongLich().equals(Utils.getCurrentDay())) {
                            relativeLayout.setVisibility(0);
                            new asyncTask(progressBar2, webView2, textView2, button2).execute(new String[]{"http://tracuu.tuvisomenh.com/xem-ngay-tot-xau"});
                        } else {
                            relativeLayout.setVisibility(8);
                            progressBar2.setVisibility(8);
                            webView2.setVisibility(8);
                            textView2.setVisibility(8);
                            button2.setVisibility(8);
                        }
                        textView3.setText(" -  " + BaseActivity.this.currentDay.getHuongXuatHanh().replaceAll(" , ", "\n\n -  "));
                        textView4.setText(" - SAO TỐT: " + BaseActivity.this.currentDay.getSaoTot() + "\n - SAO XẤU: " + BaseActivity.this.currentDay.getSaoXau());
                        textView5.setText(BaseActivity.this.currentDay.getNenLam());
                        textView6.setText(BaseActivity.this.currentDay.getKhongNenLam());
                        textView7.setText(BaseActivity.this.currentDay.getNgay());
                        textView8.setText(BaseActivity.this.currentDay.getThang());
                        textView9.setText(BaseActivity.this.currentDay.getNam());
                        String[] tempAmLich = BaseActivity.this.currentDay.getAmLich().split("/");
                        textView10.setText(tempAmLich[0]);
                        textView11.setText(tempAmLich[1]);
                        textView12.setText(tempAmLich[2]);
                        String[] tempDuongLich = BaseActivity.this.currentDay.getDuongLich().split("/");
                        textView13.setText(BaseActivity.this.currentDay.getThu() + ", " + tempDuongLich[0] + "-" + tempDuongLich[1] + "-" + tempDuongLich[2]);
                        String[] arr = BaseActivity.this.currentDay.getGioHoangDao().split(", ");
                        LinearLayoutManager layoutManager = new LinearLayoutManager(BaseActivity.this, 0, false);
                        recyclerView.setAdapter(new GioHoangDaoAdapter(BaseActivity.this, arr));
                        recyclerView2.setAdapter(new GioHoangDaoAdapter(BaseActivity.this, BaseActivity.this.currentDay.getTuoiXungKhac().split(", "), true));
                        BaseActivity.this.initThoiTiet(linearLayout, textView14, textView15, textView16, textView17, textView18, imageView);
                        BaseActivity.this.updateEventDay(linearLayout2);
                        return true;
                    } else if (event.getAction() != 0) {
                        return false;
                    } else {
                        ivNext.setImageResource(R.drawable.ic_back_selected);
                        return true;
                    }
                }
            });
            final ImageView imageView2 = (ImageView) v.findViewById(R.id.btn_today_iv);
            rlBack.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == 1) {
                        imageView2.setImageResource(R.drawable.ic_back);
                        BaseActivity.this.mBottomSheetDialog.cancel();
                        return true;
                    } else if (event.getAction() != 0) {
                        return false;
                    } else {
                        imageView2.setImageResource(R.drawable.ic_back_selected);
                        return true;
                    }
                }
            });
            final ImageView imageView3 = (ImageView) v.findViewById(R.id.header_button_right_iv);
            rlRightHeader.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == 1) {
                        imageView3.setImageResource(R.drawable.header_ca_nhan);
                        BaseActivity.this.mBottomSheetDialog.cancel();
                        BaseActivity.this.changeActivity(true, PersonalActivity.class);
                        return true;
                    } else if (event.getAction() != 0) {
                        return false;
                    } else {
                        imageView3.setImageResource(R.drawable.header_ca_nhan_select);
                        return true;
                    }
                }
            });
            rlRightHeader.setVisibility(8);
            tvHuongXuatHanh.setText(" -  " + this.currentDay.getHuongXuatHanh().replaceAll(" , ", "\n\n -  "));
            tvSaoTot.setText(" - SAO TỐT: " + this.currentDay.getSaoTot() + "\n - SAO XẤU: " + this.currentDay.getSaoXau());
            tvNenLam.setText(this.currentDay.getNenLam());
            tvKhongLam.setText(this.currentDay.getKhongNenLam());
            String[] tempDuongLich = this.currentDay.getDuongLich().split("/");
            tvDay.setText(this.currentDay.getThu() + ", " + tempDuongLich[0] + "-" + tempDuongLich[1] + "-" + tempDuongLich[2]);
            ((TextView) v.findViewById(R.id.header_button_center_tv)).setText(Utils.getMonthYearNew(this.currentDay.getDuongLich()));
            tvLunarDay.setText(this.currentDay.getNgay());
            tvLunarMonth.setText(this.currentDay.getThang());
            tvLunarYear.setText(this.currentDay.getNam());
            String[] tempAmLich = this.currentDay.getAmLich().split("/");
            tvLunarDaySo.setText(tempAmLich[0]);
            tvLunarMonthSo.setText(tempAmLich[1]);
            tvLunarYearSo.setText(tempAmLich[2]);
            LayoutManager linearLayoutManager = new LinearLayoutManager(this, 0, false);
            Adapter gioHoangDaoAdapter = new GioHoangDaoAdapter(this, this.currentDay.getGioHoangDao().split(", "));
            rcHour.setLayoutManager(linearLayoutManager);
            rcHour.setAdapter(gioHoangDaoAdapter);
            gioHoangDaoAdapter = new GioHoangDaoAdapter(this, this.currentDay.getTuoiXungKhac().split(", "), true);
            rcTuoiXung.setLayoutManager(new LinearLayoutManager(this, 0, false));
            rcTuoiXung.setAdapter(gioHoangDaoAdapter);
            ((ScrollView) v.findViewById(R.id.scrollView)).pageScroll(33);
            ((ScrollView) v.findViewById(R.id.scrollView)).fullScroll(33);
            ((ScrollView) v.findViewById(R.id.scrollView)).smoothScrollTo(0, 0);
            this.mBottomSheetDialog.heightParam(-1);
            this.mBottomSheetDialog.setCancelable(true);
            this.mBottomSheetDialog.setOnCancelListener(new C11328());
            this.mBottomSheetDialog.contentView(v).show();
            initThoiTiet(lnThoiTiet, tvDegree, tvDoAm, tvTocDoGio, tvMatTroiMoc, tvTTTT, imvWeather);
        }
    }

    void initThoiTiet(LinearLayout lnThoiTiet, TextView tvDegree, TextView tvDoAm, TextView tvTocDoGio, TextView tvMatTroiMoc, TextView tvTTTT, ImageView imvWeather) {
        if (this.mainApplication.listWeather.size() == 0) {
            lnThoiTiet.setVisibility(8);
            return;
        }
        Log.m1449v("currentDay:" + this.currentDay.getDuongLich() + " currentDayUtils: " + Utils.getCurrentDay());
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(Define.TIME_FORMAT);
            long timeDay = ((((sdf.parse(this.currentDay.getDuongLich()).getTime() - sdf.parse(Utils.getCurrentDay()).getTime()) / 1000) / 60) / 60) / 24;
            Log.m1449v("khoang cach 2 ngay: " + timeDay);
            if (timeDay < 0 || timeDay >= 10 || ((long) this.mainApplication.listWeather.size()) <= timeDay) {
                lnThoiTiet.setVisibility(8);
                return;
            }
            lnThoiTiet.setVisibility(0);
            DAOWeather daoWeather = (DAOWeather) this.mainApplication.listWeather.get((int) timeDay);
            tvDegree.setText(daoWeather.getTemperature() + "°C");
            tvDoAm.setText(daoWeather.getHumidity() + "%");
            imvWeather.setImageResource(Utils.getIconWeatherChiTiet(1));
            tvTocDoGio.setText(daoWeather.getWind() + " m/s");
            tvMatTroiMoc.setText(daoWeather.getSunrise());
            tvTTTT.setText(daoWeather.getStatus());
        } catch (Exception e) {
            Log.m1447e("error gen view thoi tiet: " + e.getMessage());
        }
    }

    protected void getBitmap(String uri, Bitmap bm) {
    }

    protected void onStart() {
        super.onStart();
        synchronized (this) {
            this.isStopped = false;
            notifyAll();
        }
    }

    protected void onPause() {
        super.onPause();
        synchronized (this) {
            this.isPaused = true;
        }
    }

    protected void onResume() {
        super.onResume();
        synchronized (this) {
            this.isPaused = false;
            notifyAll();
        }
    }

    public synchronized boolean isPaused() {
        return this.isPaused;
    }

    public synchronized boolean isStopped() {
        return this.isStopped;
    }

    public void onBackPressed() {
        super.onBackPressed();
        Define.isReset = true;
        overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_right);
    }

    protected void showChooseSingle(String title, String[] listChoise) {
        boolean isLightTheme;
        if (ThemeManager.getInstance().getCurrentTheme() == 0) {
            isLightTheme = true;
        } else {
            isLightTheme = false;
        }
        com.rey.material.app.Dialog.Builder builder = new SimpleDialog.Builder(isLightTheme ? R.style.SimpleDialogLight : R.style.SimpleDialog) {
            public void onPositiveActionClicked(DialogFragment fragment) {
                BaseActivity.this.onChoise(getSelectedValue().toString(), getSelectedIndex());
                super.onPositiveActionClicked(fragment);
            }

            public void onNegativeActionClicked(DialogFragment fragment) {
                super.onNegativeActionClicked(fragment);
            }
        };
        ((SimpleDialog.Builder) builder).items(listChoise, 0).title(title).positiveAction("Đồng ý").negativeAction("Hủy");
        DialogFragment.newInstance(builder).show(getSupportFragmentManager(), null);
    }

    protected void showChooseSingle(String title, String[] listChoise, int index) {
        com.rey.material.app.Dialog.Builder builder = new SimpleDialog.Builder(ThemeManager.getInstance().getCurrentTheme() == 0 ? R.style.SimpleDialogLight : R.style.SimpleDialog) {
            public void onPositiveActionClicked(DialogFragment fragment) {
                BaseActivity.this.onChoise(getSelectedValue().toString(), getSelectedIndex());
                super.onPositiveActionClicked(fragment);
            }

            public void onNegativeActionClicked(DialogFragment fragment) {
                super.onNegativeActionClicked(fragment);
            }
        };
        ((SimpleDialog.Builder) builder).items(listChoise, index).title(title).positiveAction("Đồng ý").negativeAction("Hủy");
        DialogFragment.newInstance(builder).show(getSupportFragmentManager(), null);
    }

    protected void showChooseMutil(String title, String[] listChoise) {
        boolean isLightTheme;
        if (ThemeManager.getInstance().getCurrentTheme() == 0) {
            isLightTheme = true;
        } else {
            isLightTheme = false;
        }
        com.rey.material.app.Dialog.Builder builder = new SimpleDialog.Builder(isLightTheme ? R.style.SimpleDialogLight : R.style.SimpleDialog) {
            public void onPositiveActionClicked(DialogFragment fragment) {
                CharSequence[] values = getSelectedValues();
                if (values == null) {
                    Toast.makeText(BaseActivity.this.getApplicationContext(), "You have selected nothing.", 0).show();
                } else {
                    BaseActivity.this.onMutilChoise(values, getSelectedIndexes());
                }
                super.onPositiveActionClicked(fragment);
            }

            public void onNegativeActionClicked(DialogFragment fragment) {
                super.onNegativeActionClicked(fragment);
            }
        };
        ((SimpleDialog.Builder) builder).multiChoiceItems(listChoise, 0).title(title).positiveAction("Đồng ý").negativeAction("Hủy");
        DialogFragment.newInstance(builder).show(getSupportFragmentManager(), null);
    }

    protected void onChoise(String value, int index) {
    }

    protected void onMutilChoise(CharSequence[] value, int[] index) {
    }

    protected void showDatePicker() {
        int i;
        if (ThemeManager.getInstance().getCurrentTheme() == 0) {
            i = R.style.Material_App_Dialog_DatePicker_Light;
        } else {
            i = R.style.Material_App_Dialog_DatePicker;
        }
        com.rey.material.app.Dialog.Builder builder = new DatePickerDialog.Builder(i) {
            public void onPositiveActionClicked(DialogFragment fragment) {
                BaseActivity.this.onSelectedDayPicker(((DatePickerDialog) fragment.getDialog()).getFormattedDate(new SimpleDateFormat(Define.TIME_FORMAT)));
                super.onPositiveActionClicked(fragment);
            }

            public void onNegativeActionClicked(DialogFragment fragment) {
                super.onNegativeActionClicked(fragment);
            }
        };
        builder.positiveAction("Đồng ý").negativeAction("Hủy");
        DialogFragment.newInstance(builder).show(getSupportFragmentManager(), null);
    }

    protected void showTimePicker() {
        int i;
        if (ThemeManager.getInstance().getCurrentTheme() == 0) {
            i = R.style.Material_App_Dialog_TimePicker_Light;
        } else {
            i = R.style.Material_App_Dialog_TimePicker;
        }
        com.rey.material.app.Dialog.Builder builder = new TimePickerDialog.Builder(i, Calendar.getInstance().get(11), Calendar.getInstance().get(12)) {
            public void onPositiveActionClicked(DialogFragment fragment) {
                BaseActivity.this.onSelectedTimePicker(((TimePickerDialog) fragment.getDialog()).getFormattedTime(new SimpleDateFormat(DateUtils.TIME_FORMAT1)));
                super.onPositiveActionClicked(fragment);
            }

            public void onNegativeActionClicked(DialogFragment fragment) {
                super.onNegativeActionClicked(fragment);
            }
        };
        builder.positiveAction("Đồng ý").negativeAction("Hủy");
        DialogFragment.newInstance(builder).show(getSupportFragmentManager(), null);
    }

    protected void onSelectedDayPicker(String day) {
    }

    protected void onSelectedTimePicker(String time) {
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    private void updateEventDay(LinearLayout linearLayout) {
        linearLayout.removeAllViews();
        linearLayout.setVisibility(8);
        ArrayList<EventInfo> lsEvent = this.mDatabaseAccess.getEvent();
        if (lsEvent != null && lsEvent.size() > 0) {
            Iterator it = lsEvent.iterator();
            while (it.hasNext()) {
                EventInfo mEventInfo = (EventInfo) it.next();
                if (mEventInfo.getStart().split(" ")[0].equals(this.currentDay.getDuongLich().split("/")[2] + "-" + this.currentDay.getDuongLich().split("/")[1] + "-" + this.currentDay.getDuongLich().split("/")[0])) {
                    View v = LayoutInflater.from(this).inflate(R.layout.item_event, null);
                    TextView tvEvent = (TextView) v.findViewById(R.id.tv_event);
                    tvEvent.setTypeface(this.typeRegularNew);
                    tvEvent.setText(mEventInfo.getName());
                    v.setOnClickListener(new impleOnClickEvent(mEventInfo));
                    linearLayout.setVisibility(0);
                    linearLayout.addView(v);
                }
            }
        }
    }

    public void checkAndRequestPermissionsChoosePhoto() {
        int i = 0;
        String[] permissions = new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE"};
        List<String> listPermissionsNeeded = new ArrayList();
        int length = permissions.length;
        while (i < length) {
            String permission = permissions[i];
            if (ContextCompat.checkSelfPermission(this, permission) != 0) {
                listPermissionsNeeded.add(permission);
            }
            i++;
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, (String[]) listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
        }
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

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public String runJs(String r13, ArrayList<Object> r14) {
        ((UtilJS) r14.get(0)).getJsoup(r14.get(1).toString());
        return((UtilJS) r14.get(0)).getJsoup(r14.get(1).toString()).getElementsByClass("bodyText").toString();

    }
}
