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
import com.example.lequan.lichvannien.BaseActivity;
import com.example.lequan.lichvannien.MainApplication;
import com.example.lequan.lichvannien.adapter.VanKhanAdapter;
import com.example.lequan.lichvannien.database.DatabaseAccess;
import com.example.lequan.lichvannien.model.VanKhan;
import com.example.lequan.lichvannien.R;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class VanKhanActivity extends BaseActivity {
    public static ArrayList<VanKhan> listVanKhan = new ArrayList();
    private MainApplication application;
    public ArrayList<VanKhan> listParent = new ArrayList();
    private DatabaseAccess mDatabaseAccess;
    private RecyclerView mRecyclerView;
    private VanKhanAdapter mVanKhanAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_van_khan);
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
                    VanKhanActivity.this.onBackPressed();
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
        this.mDatabaseAccess = DatabaseAccess.getInstance(this);
        this.mDatabaseAccess.open();
        this.listParent.clear();
        if (listVanKhan.size() == 0) {
            listVanKhan = this.mDatabaseAccess.getVanKhan();
        }
        Iterator it = listVanKhan.iterator();
        while (it.hasNext()) {
            VanKhan mVanKhan = (VanKhan) it.next();
            boolean isExist = false;
            Iterator it2 = this.listParent.iterator();
            while (it2.hasNext()) {
                if (mVanKhan.getGroup().equalsIgnoreCase(((VanKhan) it2.next()).getGroup())) {
                    isExist = true;
                }
            }
            if (!isExist) {
                this.listParent.add(mVanKhan);
            }
        }
        this.mRecyclerView = (RecyclerView) findViewById(R.id.rc_van_khan);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this, 1, false));
        this.mVanKhanAdapter = new VanKhanAdapter(this, this.listParent, 1);
        this.mRecyclerView.setAdapter(this.mVanKhanAdapter);
    }
}
