package com.example.lequan.lichvannien.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.facebook.share.internal.ShareConstants;
import com.example.lequan.lichvannien.base.ads.Banner;
import com.example.lequan.lichvannien.BaseActivity;
import com.example.lequan.lichvannien.BaseFragment;
import com.example.lequan.lichvannien.activity.GieoQueActivity;
import com.example.lequan.lichvannien.activity.HomeActivity;
import com.example.lequan.lichvannien.activity.StarDetailActivity;
import com.example.lequan.lichvannien.activity.TuViSoMenhActivity;
import com.example.lequan.lichvannien.activity.TuViSoMenhListActivity;
import com.example.lequan.lichvannien.activity.UpgradePremiumActivity;
import com.example.lequan.lichvannien.activity.ZodiacActivity;
import com.example.lequan.lichvannien.customInterface.onActionFromFragmentHome;
import com.example.lequan.lichvannien.R;

public class FragmentStar extends BaseFragment implements OnClickListener {
    private static String TAG = FragmentStar.class.getSimpleName();
    private HomeActivity activity;
    RelativeLayout boiGieoQue;
    RelativeLayout boiPhuongDong;
    RelativeLayout boiPhuongTay;
    RelativeLayout coiSao;
    FrameLayout frame;
    private onActionFromFragmentHome onActionFromFragmentHome;
    RelativeLayout rlDatTen;
    RelativeLayout rlNgayDep;
    RelativeLayout rlPhongThuy;
    RelativeLayout rlSim;
    RelativeLayout rlXemNgay;
    RelativeLayout rlXemTuoi;
    private RelativeLayout tuVi2016;
    private RelativeLayout tuViTronDoi;
    View view;

    class C12901 implements OnClickListener {
        C12901() {
        }

        public void onClick(View v) {
            Intent mIntent = new Intent(FragmentStar.this.getActivity(), TuViSoMenhActivity.class);
            mIntent.putExtra(ShareConstants.MEDIA_TYPE, 21);
            FragmentStar.this.startActivityForResult(mIntent, BaseActivity.REQUESTCODE);
            FragmentStar.this.getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        }
    }

    class C12912 implements OnClickListener {
        C12912() {
        }

        public void onClick(View v) {
            Intent it = new Intent(FragmentStar.this.getActivity(), TuViSoMenhListActivity.class);
            it.putExtra(ShareConstants.MEDIA_TYPE, 4);
            FragmentStar.this.startActivityForResult(it, BaseActivity.REQUESTCODE);
            FragmentStar.this.getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        }
    }

    class C12923 implements OnClickListener {
        C12923() {
        }

        public void onClick(View v) {
            Intent mIntent = new Intent(FragmentStar.this.getActivity(), TuViSoMenhActivity.class);
            mIntent.putExtra(ShareConstants.MEDIA_TYPE, 15);
            FragmentStar.this.startActivityForResult(mIntent, BaseActivity.REQUESTCODE);
            FragmentStar.this.getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        }
    }

    class C12934 implements OnClickListener {
        C12934() {
        }

        public void onClick(View v) {
            Intent it = new Intent(FragmentStar.this.getActivity(), TuViSoMenhListActivity.class);
            it.putExtra(ShareConstants.MEDIA_TYPE, 2);
            FragmentStar.this.startActivityForResult(it, BaseActivity.REQUESTCODE);
            FragmentStar.this.getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        }
    }

    class C12945 implements OnClickListener {
        C12945() {
        }

        public void onClick(View v) {
            Intent it = new Intent(FragmentStar.this.getActivity(), TuViSoMenhListActivity.class);
            it.putExtra(ShareConstants.MEDIA_TYPE, 1);
            FragmentStar.this.startActivityForResult(it, BaseActivity.REQUESTCODE);
            FragmentStar.this.getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        }
    }

    class C12956 implements OnClickListener {
        C12956() {
        }

        public void onClick(View v) {
            Intent it = new Intent(FragmentStar.this.getActivity(), TuViSoMenhListActivity.class);
            it.putExtra(ShareConstants.MEDIA_TYPE, 6);
            FragmentStar.this.startActivityForResult(it, BaseActivity.REQUESTCODE);
            FragmentStar.this.getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        }
    }

    class C12967 implements OnClickListener {
        C12967() {
        }

        public void onClick(View v) {
            FragmentStar.this.getActivity().startActivityForResult(new Intent(FragmentStar.this.getActivity(), UpgradePremiumActivity.class), 1221);
        }
    }

    public static FragmentStar newInstance() {
        return new FragmentStar();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = (HomeActivity) getActivity();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (this.frame != null) {
            this.frame.removeAllViews();
            this.frame = null;
        }
        this.frame = new FrameLayout(getActivity());
        if (this.view == null) {
            this.view = inflater.inflate(R.layout.fragment_star, container, false);
            Init();
        }
        this.frame.addView(this.view);
        return this.frame;
    }

    public void Init() {
        ((TextView) this.view.findViewById(R.id.header_button_center_tv)).setTypeface(this.typeRegularNew);
        ((TextView) this.view.findViewById(R.id.tu_vi_2016_tv)).setTypeface(this.typeRegularNew);
        ((TextView) this.view.findViewById(R.id.tu_vi_tron_doi_tv)).setTypeface(this.typeRegularNew);
        this.tuVi2016 = (RelativeLayout) this.view.findViewById(R.id.tu_vi_2016);
        this.tuViTronDoi = (RelativeLayout) this.view.findViewById(R.id.tu_vi_tron_doi);
        this.boiGieoQue = (RelativeLayout) this.view.findViewById(R.id.boi_gieo_que);
        this.boiPhuongDong = (RelativeLayout) this.view.findViewById(R.id.boi_phuong_dong);
        this.boiPhuongTay = (RelativeLayout) this.view.findViewById(R.id.boi_phuong_tay);
        this.coiSao = (RelativeLayout) this.view.findViewById(R.id.xem_sao_coi_han);
        this.rlSim = (RelativeLayout) this.view.findViewById(R.id.sim);
        this.rlXemTuoi = (RelativeLayout) this.view.findViewById(R.id.xem_tuoi);
        this.rlDatTen = (RelativeLayout) this.view.findViewById(R.id.datten);
        this.rlNgayDep = (RelativeLayout) this.view.findViewById(R.id.ngaydep_theotuoi);
        this.rlXemNgay = (RelativeLayout) this.view.findViewById(R.id.xemngay_totxau);
        this.rlPhongThuy = (RelativeLayout) this.view.findViewById(R.id.phongthuy);
        this.tuVi2016.setOnClickListener(this);
        this.tuViTronDoi.setOnClickListener(this);
        this.boiGieoQue.setOnClickListener(this);
        this.boiPhuongDong.setOnClickListener(this);
        this.boiPhuongTay.setOnClickListener(this);
        this.coiSao.setOnClickListener(this);
        this.rlSim.setOnClickListener(new C12901());
        this.rlXemTuoi.setOnClickListener(new C12912());
        this.rlDatTen.setOnClickListener(new C12923());
        this.rlNgayDep.setOnClickListener(new C12934());
        this.rlXemNgay.setOnClickListener(new C12945());
        this.rlPhongThuy.setOnClickListener(new C12956());
        if (this.application.isPurchase) {
            this.view.findViewById(R.id.header_button_right_iv_upgrade_premium).setVisibility(8);
        } else {
            this.view.findViewById(R.id.header_button_right_iv_upgrade_premium).setOnClickListener(new C12967());
        }
    }

    public void reloadAds() {
        if (this.application.isPurchase) {
            this.view.findViewById(R.id.header_button_right_iv_upgrade_premium).setVisibility(8);
        }
        ((Banner) this.view.findViewById(R.id.banner)).reloadAds();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tu_vi_2016:
                StarDetailActivity.TYPE_STAR = StarDetailActivity.TU_VI_2016;
                ((BaseActivity) getActivity()).changeActivity(true, StarDetailActivity.class);
                return;
            case R.id.tu_vi_tron_doi:
                StarDetailActivity.TYPE_STAR = StarDetailActivity.TU_VI_TRON_DOI;
                ((BaseActivity) getActivity()).changeActivity(true, StarDetailActivity.class);
                return;
            case R.id.xem_sao_coi_han:
                Intent mIntent = new Intent(getActivity(), TuViSoMenhActivity.class);
                mIntent.putExtra(ShareConstants.MEDIA_TYPE, 1);
                startActivityForResult(mIntent, BaseActivity.REQUESTCODE);
                getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                return;
            case R.id.boi_phuong_tay:
                ((BaseActivity) getActivity()).changeActivity(true, ZodiacActivity.class);
                return;
            case R.id.boi_phuong_dong:
                StarDetailActivity.TYPE_STAR = StarDetailActivity.BOI_PHUONG_DONG;
                ((BaseActivity) getActivity()).changeActivity(true, StarDetailActivity.class);
                return;
            case R.id.boi_gieo_que:
                ((BaseActivity) getActivity()).changeActivity(true, GieoQueActivity.class);
                return;
            default:
                return;
        }
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (this.onActionFromFragmentHome != null) {
            this.onActionFromFragmentHome.onViewCreated();
        }
    }

    public void setOnActionFromFragmentHome(onActionFromFragmentHome onActionFromFragmentHome) {
        this.onActionFromFragmentHome = onActionFromFragmentHome;
    }
}
