package com.example.lequan.lichvannien.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.facebook.share.internal.ShareConstants;
import com.example.lequan.lichvannien.base.utils.BaseUtils;
import com.example.lequan.lichvannien.base.utils.Log;
import com.example.lequan.lichvannien.BaseActivity;
import com.example.lequan.lichvannien.MainApplication;
import com.example.lequan.lichvannien.custom.DatePickerCenter;
import com.example.lequan.lichvannien.custom.DatePickerCenter.onActionFromDatePicker;
import com.example.lequan.lichvannien.utils.UtilJS;
import com.example.lequan.lichvannien.R;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.File;
import java.util.ArrayList;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TuViSoMenhActivity extends BaseActivity {
    String API_DAT_TEN = "http://tracuu.tuvisomenh.com/dat-ten-cho-con/dat-ten-theo-ngu-hanh";
    String API_GIO_TOT_TRONG_NGAY = "http://tracuu.tuvisomenh.com/xem-ngay-tot-xau/xem-gio-tot-trong-ngay";
    String API_NGAY_TOTXAU = "http://tracuu.tuvisomenh.com/xem-ngay-tot-xau";
    String API_NGAY_TOT_TRONG_THANG = "http://tracuu.tuvisomenh.com/xem-ngay-tot-xau/xem-ngay-tot-trong-thang";
    String API_PHONG_THUY_HOANGOCKIMLAU = "http://tracuu.tuvisomenh.com/tool/bangtamtaihoangockimlau?yearView=";
    String API_PHONG_THUY_TAM_TAI = "http://tracuu.tuvisomenh.com/tool/xemhantamtai?";
    String API_PHONG_THUY_XAY_NHA = "http://tracuu.tuvisomenh.com/tool/xemtuoixaynha?";
    String API_PHONG_THUY_XEM_HUONG = "http://tracuu.tuvisomenh.com/tool/xemhuongnha?";
    String API_SIM = "http://tracuu.tuvisomenh.com/sim-so-dep/sim-phong-thuy-xxxxxx-hop-tuoi-sinh-hhhhhh-gio-0-phut-ngay-dddddd-thang-mmmmmm-nam-yyyyyy";
    String API_XEM_HOP_TUOI = "http://tracuu.tuvisomenh.com/tool/xemhoptuoi?name=xxxxxx&sex=yyyyyy&day=dddddd&month=mmmmmm&year=";
    String API_XEM_NGAY_CHUYEN_NHA = "http://tracuu.tuvisomenh.com/xem-ngay-tot-xau/xem-ngay-chuyen-nha/tuoi-chuyen-nha-";
    String API_XEM_NGAY_CUOI = "http://tracuu.tuvisomenh.com/tool/chonngaycuoi?";
    String API_XEM_NGAY_DO_TRAN = "http://tracuu.tuvisomenh.com/xem-ngay-tot-xau/xem-ngay-do-tran-nha/tuoi-lam-nha-xxxxxx/ngay-dinh-do-tran-yyyyyy";
    String API_XEM_NGAY_KHAI_TRUONG = "http://tracuu.tuvisomenh.com/xem-ngay-tot-xau/xem-ngay-khai-truong/tuoi-khai-truong-";
    String API_XEM_NGAY_KY_KET_HOP_DONG = "http://tracuu.tuvisomenh.com/xem-ngay-tot-xau/xem-ngay-ky-ket-hop-dong";
    String API_XEM_NGAY_LAM_NHA = "http://tracuu.tuvisomenh.com/xem-ngay-tot-xau/xem-ngay-lam-nha/tuoi-lam-nha-dddddd-thang-mmmmmm-nam-";
    String API_XEM_NGAY_MUA_NHA = "http://tracuu.tuvisomenh.com/xem-ngay-tot-xau/xem-ngay-mua-nha/tuoi-mua-nha-";
    String API_XEM_NGAY_MUA_XE = "http://tracuu.tuvisomenh.com/xem-ngay-tot-xau/xem-ngay-mua-xe/tuoi-mua-xe-";
    String API_XEM_NGAY_TRUNG_TANG = "http://tracuu.tuvisomenh.com/tool/tinhtrungtang?";
    String API_XEM_NGAY_XUAT_HANH = "http://tracuu.tuvisomenh.com/tool/xuathanh?";
    String API_XEM_SAO = "http://tracuu.tuvisomenh.com/xem-ngay-tot-xau/xem-sao-han/sao-han-nam-mang-sinh-ngay-";
    String API_XEM_TUOI_KET_HON = "http://tracuu.tuvisomenh.com/xem-tuoi/xem-tuoi-ket-hon-nam-sinh-xxxxxx-voi-nu-sinh-yyyyyy";
    String API_XEM_TUOI_LAM_AN = "http://tracuu.tuvisomenh.com/xem-tuoi/xem-tuoi-lam-an/sinh-ngay-xxxxxx-hop-tac-kinh-doanh-voi-nhung-tuoi-nao";
    String API_XEM_TUOI_SINH_CON = "http://tracuu.tuvisomenh.com/tool/xemnamsinhcon?";
    String API_XEM_TUOI_VO_CHONG = "http://tracuu.tuvisomenh.com/xem-tuoi/xem-tuoi-vo-chong-nam-xxxxxx-voi-nu-yyyyyy";
    String PATH = "";
    private MainApplication application;
    Button btReload;
    DatePickerCenter datePickerCenter;
    ProgressBar pb;
    TextView tvError;
    int type = 1;
    WebView wv;

    class C12002 implements OnClickListener {
        C12002() {
        }

        public void onClick(View v) {
            TuViSoMenhActivity.this.datePickerCenter.setVisibility(0);
        }
    }

    class C12013 implements OnClickListener {
        C12013() {
        }

        public void onClick(View v) {
            TuViSoMenhActivity.this.datePickerCenter.reload();
        }
    }

    class C12024 extends WebViewClient {
        C12024() {
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            TuViSoMenhActivity.this.pb.setVisibility(8);
        }
    }

    class C12035 implements onActionFromDatePicker {
        C12035() {
        }

        public void onSelectedGET(String url) {
            new asyncTask().execute(new String[]{url});
        }

        public void onSelectedPOST(String url, RequestBody formBody) {
            new asyncTaskPOST(formBody).execute(new String[]{url});
        }
    }

    class asyncTask extends AsyncTask<String, Void, Boolean> {
        asyncTask() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            TuViSoMenhActivity.this.pb.setVisibility(0);
            TuViSoMenhActivity.this.tvError.setVisibility(8);
            TuViSoMenhActivity.this.btReload.setVisibility(8);
        }

        protected Boolean doInBackground(String... params) {
            try {
                Log.m1449v("url GET: " + params[0]);
                Response response = ((MainApplication) TuViSoMenhActivity.this.getApplication()).getOkHttpClient().newCall(new Builder().url(params[0]).build()).execute();
                ArrayList<Object> paramsOb = new ArrayList();
                paramsOb.add(new UtilJS());
                paramsOb.add(response.body().string());
                String result = TuViSoMenhActivity.this.runJs("parseHTML" + TuViSoMenhActivity.this.type, paramsOb);
                if (result.equals("")) {
                    return Boolean.valueOf(false);
                }
                if (TuViSoMenhActivity.this.type == 19 || TuViSoMenhActivity.this.type == 25) {
                    BaseUtils.writeTxtFile(new File(TuViSoMenhActivity.this.PATH + "temp.html"), "<html lang=\"vi-VN\">\n<head prefix=\"og: http://ogp.me/ns# fb: http://ogp.me/ns/fb#\">\n<meta charset=\"utf-8\" />\n</head>" + result + BaseUtils.readFileFromAsset(TuViSoMenhActivity.this, "mobile.css") + BaseUtils.readFileFromAsset(TuViSoMenhActivity.this, "mobileNewNoIMG.css") + "</html>");
                } else {
                    BaseUtils.writeTxtFile(new File(TuViSoMenhActivity.this.PATH + "temp.html"), "<html lang=\"vi-VN\">\n<head prefix=\"og: http://ogp.me/ns# fb: http://ogp.me/ns/fb#\">\n<meta charset=\"utf-8\" />\n</head>" + result + BaseUtils.readFileFromAsset(TuViSoMenhActivity.this, "mobile.css") + BaseUtils.readFileFromAsset(TuViSoMenhActivity.this, "mobileNew.css") + "</html>");
                }
                return Boolean.valueOf(true);
            } catch (Exception e) {
                Log.m1447e("error testJS run: " + e.getMessage());
                return Boolean.valueOf(false);
            }
        }

        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean.booleanValue()) {
                try {
                    TuViSoMenhActivity.this.wv.loadUrl("file://" + TuViSoMenhActivity.this.PATH + "temp.html");
                    return;
                } catch (Exception e) {
                    Log.m1447e("error load content to wv: " + e.getMessage());
                    return;
                }
            }
            Toast.makeText(TuViSoMenhActivity.this, "Không thể lấy dữ liệu!", 0).show();
            TuViSoMenhActivity.this.pb.setVisibility(8);
            TuViSoMenhActivity.this.tvError.setVisibility(0);
            TuViSoMenhActivity.this.btReload.setVisibility(0);
        }
    }

    class asyncTaskPOST extends AsyncTask<String, Void, Boolean> {
        RequestBody formBody;

        public asyncTaskPOST(RequestBody formBody) {
            this.formBody = formBody;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            TuViSoMenhActivity.this.pb.setVisibility(0);
            TuViSoMenhActivity.this.tvError.setVisibility(8);
            TuViSoMenhActivity.this.btReload.setVisibility(8);
        }

        protected Boolean doInBackground(String... params) {
            try {
                Response response = ((MainApplication) TuViSoMenhActivity.this.getApplication()).getOkHttpClient().newCall(new Builder().url(params[0]).method(HttpRequest.METHOD_POST, RequestBody.create(null, new byte[0])).post(this.formBody).build()).execute();
                ArrayList<Object> paramsOb = new ArrayList();
                paramsOb.add(new UtilJS());
                paramsOb.add(response.body().string());
                String result = TuViSoMenhActivity.this.runJs("parseHTML" + TuViSoMenhActivity.this.type, paramsOb);
                if (result.equals("")) {
                    return Boolean.valueOf(false);
                }
                BaseUtils.writeTxtFile(new File(TuViSoMenhActivity.this.PATH + "temp.html"), "<html lang=\"vi-VN\">\n<head prefix=\"og: http://ogp.me/ns# fb: http://ogp.me/ns/fb#\">\n<meta charset=\"utf-8\" />\n</head>" + result + BaseUtils.readFileFromAsset(TuViSoMenhActivity.this, "mobile.css") + BaseUtils.readFileFromAsset(TuViSoMenhActivity.this, "mobileNew.css") + "</html>");
                return Boolean.valueOf(true);
            } catch (Exception e) {
                Log.m1447e("error testJS run: " + e.getMessage());
                return Boolean.valueOf(false);
            }
        }

        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean.booleanValue()) {
                try {
                    TuViSoMenhActivity.this.wv.loadUrl("file://" + TuViSoMenhActivity.this.PATH + "temp.html");
                    return;
                } catch (Exception e) {
                    Log.m1447e("error load content to wv: " + e.getMessage());
                    return;
                }
            }
            TuViSoMenhActivity.this.pb.setVisibility(8);
            TuViSoMenhActivity.this.tvError.setVisibility(0);
            Toast.makeText(TuViSoMenhActivity.this, "Không thể lấy dữ liệu!", 0).show();
            TuViSoMenhActivity.this.btReload.setVisibility(0);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_tuvi_somenh);
        this.application = (MainApplication) getApplication();
        this.PATH = "/data/data/" + getPackageName() + "/files/";
        this.type = getIntent().getExtras().getInt(ShareConstants.MEDIA_TYPE);
        Log.m1449v("XXXXXXXX type: " + this.type);
        setTitle();
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
                    TuViSoMenhActivity.this.onBackPressed();
                    return true;
                } else if (event.getAction() != 0) {
                    return false;
                } else {
                    ivBack.setImageResource(R.drawable.ic_back_selected);
                    return true;
                }
            }
        });
        ((TextView) findViewById(R.id.header_button_center_tv)).setTypeface(this.typeRegularNew);
        ((RelativeLayout) findViewById(R.id.btn_date)).setOnClickListener(new C12002());
        this.wv = (WebView) findViewById(R.id.activity_webview_screen_wv);
        this.pb = (ProgressBar) findViewById(R.id.activity_webview_screen_pb);
        this.tvError = (TextView) findViewById(R.id.tvError);
        this.btReload = (Button) findViewById(R.id.btReload);
        this.btReload.setOnClickListener(new C12013());
        this.wv.getSettings().setJavaScriptEnabled(true);
        this.wv.setWebViewClient(new C12024());
        this.wv.setBackgroundColor(0);
        this.datePickerCenter = (DatePickerCenter) findViewById(R.id.datePicker);
        this.datePickerCenter.setType(this.type);
        this.datePickerCenter.setOnActionFromDatePicker(new C12035());
        if (this.type == 2) {
            this.datePickerCenter.setVisibility(8);
            new asyncTask().execute(new String[]{this.API_NGAY_TOTXAU});
            ((RelativeLayout) findViewById(R.id.btn_date)).setVisibility(8);
        } else if (this.type == 4) {
            this.datePickerCenter.setVisibility(8);
            new asyncTask().execute(new String[]{this.API_NGAY_TOT_TRONG_THANG});
            ((RelativeLayout) findViewById(R.id.btn_date)).setVisibility(8);
        } else if (this.type == 13) {
            this.datePickerCenter.setVisibility(8);
            new asyncTask().execute(new String[]{this.API_XEM_NGAY_KY_KET_HOP_DONG});
            ((RelativeLayout) findViewById(R.id.btn_date)).setVisibility(8);
        }
    }

    void setTitle() {
        switch (this.type) {
            case 1:
                ((TextView) findViewById(R.id.header_button_center_tv)).setText("Xem sao - Coi hạn");
                return;
            case 2:
                ((TextView) findViewById(R.id.header_button_center_tv)).setText("Xem ngày tốt xấu");
                return;
            case 3:
                ((TextView) findViewById(R.id.header_button_center_tv)).setText("Xem giờ tốt trong ngày");
                return;
            case 4:
                ((TextView) findViewById(R.id.header_button_center_tv)).setText("Xem ngày tốt trong tháng");
                return;
            case 5:
                ((TextView) findViewById(R.id.header_button_center_tv)).setText("Xem ngày cưới");
                return;
            case 6:
                ((TextView) findViewById(R.id.header_button_center_tv)).setText("Xem ngày giờ xuất hành");
                return;
            case 7:
                ((TextView) findViewById(R.id.header_button_center_tv)).setText("Xem ngày làm nhà");
                return;
            case 8:
                ((TextView) findViewById(R.id.header_button_center_tv)).setText("Xem ngày đổ trần nhà");
                return;
            case 9:
                ((TextView) findViewById(R.id.header_button_center_tv)).setText("Xem ngày mua nhà");
                return;
            case 10:
                ((TextView) findViewById(R.id.header_button_center_tv)).setText("Xem ngày chuyển nhà");
                return;
            case 11:
                ((TextView) findViewById(R.id.header_button_center_tv)).setText("Xem ngày khai trương");
                return;
            case 12:
                ((TextView) findViewById(R.id.header_button_center_tv)).setText("Xem ngày mua xe");
                return;
            case 13:
                ((TextView) findViewById(R.id.header_button_center_tv)).setText("Xem ngày ký kết hợp đồng");
                return;
            case 14:
                ((TextView) findViewById(R.id.header_button_center_tv)).setText("Xem Trùng Tang");
                return;
            case 15:
                ((TextView) findViewById(R.id.header_button_center_tv)).setText("Đặt tên cho con");
                return;
            case 16:
                ((TextView) findViewById(R.id.header_button_center_tv)).setText("Xem tuổi làm ăn");
                return;
            case 17:
                ((TextView) findViewById(R.id.header_button_center_tv)).setText("Xem tuổi kết hôn");
                return;
            case 18:
                ((TextView) findViewById(R.id.header_button_center_tv)).setText("Xem tuổi vợ chồng");
                return;
            case 19:
                ((TextView) findViewById(R.id.header_button_center_tv)).setText("Xem tuổi sinh con");
                return;
            case 20:
                ((TextView) findViewById(R.id.header_button_center_tv)).setText("Xem hợp tuổi");
                return;
            case 21:
                ((TextView) findViewById(R.id.header_button_center_tv)).setText("Sim phong thủy");
                return;
            case 22:
                ((TextView) findViewById(R.id.header_button_center_tv)).setText("Xem tuổi làm nhà");
                return;
            case 23:
                ((TextView) findViewById(R.id.header_button_center_tv)).setText("Xem hướng nhà");
                return;
            case 24:
                ((TextView) findViewById(R.id.header_button_center_tv)).setText("Xem hạn tam tai");
                return;
            case 25:
                ((TextView) findViewById(R.id.header_button_center_tv)).setText("Tam tai Hoàng ốc Kim lâu");
                return;
            default:
                return;
        }
    }
}
