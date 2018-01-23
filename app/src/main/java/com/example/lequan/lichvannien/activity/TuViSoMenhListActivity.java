package com.example.lequan.lichvannien.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.facebook.share.internal.ShareConstants;
import com.example.lequan.lichvannien.BaseActivity;
import com.example.lequan.lichvannien.MainApplication;
import com.example.lequan.lichvannien.adapter.AdapterTuViSoMenhList;
import com.example.lequan.lichvannien.dao.DAOTuViSoMenh;
import com.example.lequan.lichvannien.R;
import java.io.File;
import java.util.ArrayList;

public class TuViSoMenhListActivity extends BaseActivity {
    private MainApplication application;
    ArrayList<DAOTuViSoMenh> daoTuViSoMenhs = new ArrayList();
    ListView lv;
    TextView tvTitle;
    int type = 1;

    class C12052 implements OnItemClickListener {
        C12052() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            Intent mIntent = new Intent(TuViSoMenhListActivity.this, TuViSoMenhActivity.class);
            mIntent.putExtra(ShareConstants.MEDIA_TYPE, ((DAOTuViSoMenh) TuViSoMenhListActivity.this.daoTuViSoMenhs.get(position)).getType());
            TuViSoMenhListActivity.this.startActivity(mIntent);
            TuViSoMenhListActivity.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_tuvi_somenh_list);
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
                    TuViSoMenhListActivity.this.onBackPressed();
                    return true;
                } else if (event.getAction() != 0) {
                    return false;
                } else {
                    ivBack.setImageResource(R.drawable.ic_back_selected);
                    return true;
                }
            }
        });
        this.type = getIntent().getExtras().getInt(ShareConstants.MEDIA_TYPE);
        this.tvTitle = (TextView) findViewById(R.id.header_button_center_tv);
        this.lv = (ListView) findViewById(R.id.lv);
        switch (this.type) {
            case 1:
                this.tvTitle.setText("Xem ngày tốt - xấu");
                this.daoTuViSoMenhs.add(new DAOTuViSoMenh("Xem ngày tốt xấu", 2));
                this.daoTuViSoMenhs.add(new DAOTuViSoMenh("Xem giờ tốt trong ngày", 3));
                this.daoTuViSoMenhs.add(new DAOTuViSoMenh("Xem giờ ngày tốt trong tháng", 4));
                break;
            case 2:
                this.tvTitle.setText("Ngày đẹp theo tuổi");
                this.daoTuViSoMenhs.add(new DAOTuViSoMenh("Xem ngày cưới", 5));
                this.daoTuViSoMenhs.add(new DAOTuViSoMenh("Xem ngày giờ xuất hành", 6));
                this.daoTuViSoMenhs.add(new DAOTuViSoMenh("Xem ngày làm nhà", 7));
                this.daoTuViSoMenhs.add(new DAOTuViSoMenh("Xem ngày đổ trần nhà", 8));
                this.daoTuViSoMenhs.add(new DAOTuViSoMenh("Xem ngày mua nhà", 9));
                this.daoTuViSoMenhs.add(new DAOTuViSoMenh("Xem ngày chuyển nhà", 10));
                this.daoTuViSoMenhs.add(new DAOTuViSoMenh("Xem ngày khai trương", 11));
                this.daoTuViSoMenhs.add(new DAOTuViSoMenh("Xem ngày mua xe", 12));
                this.daoTuViSoMenhs.add(new DAOTuViSoMenh("Xem ngày ký kết hợp đồng", 13));
                this.daoTuViSoMenhs.add(new DAOTuViSoMenh("Xem Trùng Tang", 14));
                break;
            case 4:
                this.tvTitle.setText("Xem tuổi");
                this.daoTuViSoMenhs.add(new DAOTuViSoMenh("Xem tuổi làm ăn", 16));
                this.daoTuViSoMenhs.add(new DAOTuViSoMenh("Xem tuổi kết hôn", 17));
                this.daoTuViSoMenhs.add(new DAOTuViSoMenh("Xem tuổi vợ chồng", 18));
                this.daoTuViSoMenhs.add(new DAOTuViSoMenh("Xem tuổi sinh con", 19));
                this.daoTuViSoMenhs.add(new DAOTuViSoMenh("Xem hợp tuổi", 20));
                break;
            case 6:
                this.tvTitle.setText("Phong thủy nhà cửa");
                this.daoTuViSoMenhs.add(new DAOTuViSoMenh("Xem tuổi làm nhà", 22));
                this.daoTuViSoMenhs.add(new DAOTuViSoMenh("Xem hướng nhà", 23));
                this.daoTuViSoMenhs.add(new DAOTuViSoMenh("Xem hạn tam tai", 24));
                break;
        }
        this.lv.setAdapter(new AdapterTuViSoMenhList(this, this.daoTuViSoMenhs));
        this.lv.setOnItemClickListener(new C12052());
    }
}
