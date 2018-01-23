package com.example.lequan.lichvannien.activity;

import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.lequan.lichvannien.BaseActivity;
import com.example.lequan.lichvannien.MainApplication;
import com.example.lequan.lichvannien.common.Define;
import com.example.lequan.lichvannien.network.DataLoader;
import com.example.lequan.lichvannien.utils.NSDialog;
import com.example.lequan.lichvannien.R;
import java.io.File;

public class FeedbackActivity extends BaseActivity implements OnClickListener {
    private MainApplication application;
    private EditText edtContent;
    private int error = 0;
    ImageView iv1;
    ImageView iv2;

    class C11572 implements OnClickListener {
        C11572() {
        }

        public void onClick(View v) {
            if (FeedbackActivity.this.edtContent.getText().toString().trim().length() <= 20) {
                NSDialog.showDialogBasic(FeedbackActivity.this, "Vui lòng nhập nội dung lỗi (>20 ký tự)");
                return;
            }
            String android_id = Secure.getString(FeedbackActivity.this.getContentResolver(), "android_id");
            String version = VERSION.RELEASE;
            FeedbackActivity.this.postData(DataLoader.POST, true, FeedbackActivity.this, Define.API_FEEDBACK, "deviceID", android_id, "phone_name", Build.MODEL, "os_version", version, "message", FeedbackActivity.this.edtContent.getText().toString(), "topic", FeedbackActivity.this.error + "", "code", "55890");
        }
    }

    class C11583 implements OnClickListener {
        C11583() {
        }

        public void onClick(View v) {
            FeedbackActivity.this.error = 0;
            FeedbackActivity.this.iv1.setImageResource(R.drawable.taosukien_ic_chon);
            FeedbackActivity.this.iv2.setImageResource(R.drawable.taosukien_ic_khong_chon);
        }
    }

    class C11594 implements OnClickListener {
        C11594() {
        }

        public void onClick(View v) {
            FeedbackActivity.this.error = 1;
            FeedbackActivity.this.iv2.setImageResource(R.drawable.taosukien_ic_chon);
            FeedbackActivity.this.iv1.setImageResource(R.drawable.taosukien_ic_khong_chon);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_feedback);
        this.application = (MainApplication) getApplication();
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
                    FeedbackActivity.this.onBackPressed();
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
        ((TextView) findViewById(R.id.btn_right_tv)).setTypeface(this.typeRegularNew);
        ((TextView) findViewById(R.id.textView6)).setTypeface(this.typeRegularNew);
        ((TextView) findViewById(R.id.tv_feefback)).setTypeface(this.typeRegularNew);
        ((TextView) findViewById(R.id.tv_error)).setTypeface(this.typeRegularNew);
        this.edtContent = (EditText) findViewById(R.id.edt_content);
        this.edtContent.setTypeface(this.typeRegularNew);
        ((RelativeLayout) findViewById(R.id.btn_right)).setOnClickListener(new C11572());
        this.iv1 = (ImageView) findViewById(R.id.iv1);
        this.iv2 = (ImageView) findViewById(R.id.iv2);
        ((RelativeLayout) findViewById(R.id.rl1)).setOnClickListener(new C11583());
        ((RelativeLayout) findViewById(R.id.rl2)).setOnClickListener(new C11594());
    }

    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    protected void getResponse(String jsonObject, String api) {
        super.getResponse(jsonObject, api);
        showToast(getApplicationContext(), "Cảm ơn bạn đã góp ý");
        onBackPressed();
    }
}
