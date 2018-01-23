package com.example.lequan.lichvannien.base.ads;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.lequan.lichvannien.R;
import com.example.lequan.lichvannien.base.BaseActivity;
import com.example.lequan.lichvannien.base.BaseApplication;
import com.example.lequan.lichvannien.base.dao.BaseConfig.more_apps;
import com.example.lequan.lichvannien.base.utils.BaseUtils;
import java.util.Random;

public class PopupCustom extends BaseActivity {
    private ImageView iv;
    private more_apps more_app = null;
    private ProgressBar pb;

    class C11211 implements OnClickListener {
        C11211() {
        }

        public void onClick(View v) {
            PopupCustom.this.onBackPressed();
        }
    }

    class C11222 implements OnClickListener {
        C11222() {
        }

        public void onClick(View v) {
            BaseUtils.gotoUrl(PopupCustom.this, PopupCustom.this.more_app.getUrl_store());
        }
    }

    class C11233 extends SimpleTarget<Drawable> {
        C11233() {
        }

        public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
            if (resource != null) {
                if (PopupCustom.this.pb != null) {
                    PopupCustom.this.pb.setVisibility(8);
                }
                if (PopupCustom.this.iv != null) {
                    PopupCustom.this.iv.setImageDrawable(resource);
                }
            }
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_custom);
        BaseApplication baseApplication = (BaseApplication) getApplication();
        findViewById(R.id.ivClose).setOnClickListener(new C11211());
        this.pb = (ProgressBar) findViewById(R.id.pb);
        if (baseApplication.getBaseConfig().getMore_apps().size() > 0) {
            this.more_app = (more_apps) baseApplication.getBaseConfig().getMore_apps().get(new Random().nextInt(baseApplication.getBaseConfig().getMore_apps().size()));
            this.iv = (ImageView) findViewById(R.id.ivPopup);
            this.iv.setOnClickListener(new C11222());
            if (this.more_app.getPopup().equals("")) {
                onBackPressed();
                return;
            } else {
                Glide.with((FragmentActivity) this).load(this.more_app.getPopup()).into(new C11233());
                return;
            }
        }
        onBackPressed();
    }
}
