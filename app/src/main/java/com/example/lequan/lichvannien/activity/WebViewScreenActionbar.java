package com.example.lequan.lichvannien.activity;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.lequan.lichvannien.BaseActivity;
import com.example.lequan.lichvannien.location.GPSTracker;
import com.example.lequan.lichvannien.R;
import java.util.List;
import java.util.Locale;

public class WebViewScreenActionbar extends BaseActivity {
    ProgressBar pb;
    WebView wv;

    class C12231 implements OnClickListener {
        C12231() {
        }

        public void onClick(View v) {
            WebViewScreenActionbar.this.onBackPressed();
        }
    }

    class C12242 implements OnClickListener {
        C12242() {
        }

        public void onClick(View v) {
            WebViewScreenActionbar.this.pb.setVisibility(0);
            WebViewScreenActionbar.this.wv.reload();
        }
    }

    class C12253 extends WebViewClient {
        C12253() {
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            WebViewScreenActionbar.this.pb.setVisibility(8);
        }
    }

    class C12264 implements Runnable {
        C12264() {
        }

        public void run() {
            WebViewScreenActionbar.this.pb.setVisibility(8);
        }
    }

    class LoadData extends AsyncTask<Void, Void, String> {
        LoadData() {
        }

        protected String doInBackground(Void... params) {
            String url = "http://ios.hdvietpro.com/content/thoi_tiet.php";
            if (WebViewScreenActionbar.this.getIntent().getExtras().getString("title").equals("Thời Tiết")) {
                GPSTracker gps = new GPSTracker(WebViewScreenActionbar.this);
                if (!gps.canGetLocation()) {
                    return url + "?cityName=Hà%20Nội&deviceID=" + WebViewScreenActionbar.getDeviceID(WebViewScreenActionbar.this);
                }
                try {
                    List<Address> addresses = new Geocoder(WebViewScreenActionbar.this, Locale.getDefault()).getFromLocation(gps.getLatitude(), gps.getLongitude(), 1);
                    if (addresses.size() > 0) {
                        Log.v(BaseActivity.TAG, "cityName: " + ((Address) addresses.get(0)).getLocality());
                        return url + "?cityName=" + ((Address) addresses.get(0)).getLocality().replaceAll(" ", "%20") + "&lat=" + gps.getLatitude() + "&long=" + gps.getLongitude() + "&deviceID=" + WebViewScreenActionbar.getDeviceID(WebViewScreenActionbar.this);
                    }
                    Log.d(BaseActivity.TAG, "address size = 0");
                    return url + "?cityName=Hà%20Nội&lat=" + gps.getLatitude() + "&long=" + gps.getLongitude() + "&deviceID=" + WebViewScreenActionbar.getDeviceID(WebViewScreenActionbar.this);
                } catch (Exception e) {
                    Log.e(BaseActivity.TAG, "error get address: " + e.getMessage());
                    return url;
                }
            } else if (WebViewScreenActionbar.this.getIntent().getExtras().getString("title").equals("Kết Quả Xổ Số")) {
                return "http://ios.hdvietpro.com/content/xo_so.php?deviceID=" + WebViewScreenActionbar.getDeviceID(WebViewScreenActionbar.this);
            } else {
                return "http://ios.hdvietpro.com/content/nhip_sinh_hoc.php?deviceID=" + WebViewScreenActionbar.getDeviceID(WebViewScreenActionbar.this);
            }
        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(BaseActivity.TAG, "url: " + s);
            try {
                WebViewScreenActionbar.this.wv.loadUrl(s);
            } catch (Exception e) {
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            getWindow().requestFeature(8);
        } catch (Exception e) {
        }
        setContentView((int) R.layout.activity_webview_actionbar_screen);
        this.wv = (WebView) findViewById(R.id.activity_webview_screen_wv);
        this.pb = (ProgressBar) findViewById(R.id.activity_webview_screen_pb);
        ((RelativeLayout) findViewById(R.id.img_back)).setOnClickListener(new C12231());
        ((RelativeLayout) findViewById(R.id.img_reload)).setOnClickListener(new C12242());
        this.wv.getSettings().setJavaScriptEnabled(true);
        this.wv.setWebViewClient(new C12253());
        ((TextView) findViewById(R.id.tv_header)).setText(getIntent().getExtras().getString("title"));
        new LoadData().execute(new Void[0]);
        new Handler().postDelayed(new C12264(), 3000);
    }

    public static String getDeviceID(Context context) {
        return Secure.getString(context.getContentResolver(), "android_id");
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.wv != null) {
            this.wv.clearHistory();
            this.wv.clearCache(true);
            this.wv.loadUrl("about:blank");
            this.wv.freeMemory();
            this.wv.pauseTimers();
            this.wv = null;
        }
    }
}
