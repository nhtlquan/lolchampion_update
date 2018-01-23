package com.example.lequan.lichvannien.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.lequan.lichvannien.BaseActivity;
import com.example.lequan.lichvannien.MainApplication;
import com.example.lequan.lichvannien.model.VanKhan;
import com.example.lequan.lichvannien.R;
import java.io.File;

public class VanKhanDetailActivity extends BaseActivity {
    public static VanKhan mVanKhan;
    private MainApplication application;
    ProgressBar pb;
    private TextView tvName;
    private WebView wvContent;

    class C12132 extends WebChromeClient {
        C12132() {
        }

        public void onProgressChanged(WebView view, int progress) {
            if (progress == 100) {
                VanKhanDetailActivity.this.pb.setVisibility(8);
            } else {
                VanKhanDetailActivity.this.pb.setVisibility(0);
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_van_khan_detail);
        this.application = (MainApplication) getApplication();
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
                    VanKhanDetailActivity.this.onBackPressed();
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
        this.tvName = (TextView) findViewById(R.id.tv_name);
        this.tvName.setTypeface(this.typeBoldNew);
        this.wvContent = (WebView) findViewById(R.id.wv_content);
        if (mVanKhan == null) {
            Toast.makeText(this, "Chưa có dữ liệu!", 0).show();
            onBackPressed();
        }
        this.tvName.setText(mVanKhan.getTitle());
        this.pb = (ProgressBar) findViewById(R.id.pb);
        this.wvContent.setWebChromeClient(new C12132());
        this.wvContent.setBackgroundColor(0);
        this.wvContent.loadUrl("file:///android_asset/vankhan/" + mVanKhan.getContent());
    }
}
