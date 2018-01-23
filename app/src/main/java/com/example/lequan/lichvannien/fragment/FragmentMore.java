package com.example.lequan.lichvannien.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog.Builder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.lequan.lichvannien.base.ads.Banner;
import com.example.lequan.lichvannien.BaseActivity;
import com.example.lequan.lichvannien.BaseFragment;
import com.example.lequan.lichvannien.activity.AnhNenActivity;
import com.example.lequan.lichvannien.activity.FeedbackActivity;
import com.example.lequan.lichvannien.activity.HolidayActivity;
import com.example.lequan.lichvannien.activity.HomeActivity;
import com.example.lequan.lichvannien.activity.LaBanActivity;
import com.example.lequan.lichvannien.activity.UpgradePremiumActivity;
import com.example.lequan.lichvannien.activity.VanKhanActivity;
import com.example.lequan.lichvannien.activity.WeatherActivity;
import com.example.lequan.lichvannien.activity.WebViewScreenActionbar;
import com.example.lequan.lichvannien.customInterface.onActionFromFragmentHome;
import com.example.lequan.lichvannien.R;

public class FragmentMore extends BaseFragment implements OnClickListener {
    private static String TAG = FragmentMore.class.getSimpleName();
    private HomeActivity activity;
    FrameLayout frame;
    private ImageView ivCheck;
    private RelativeLayout layoutAnhNen;
    private RelativeLayout layoutFeedBack;
    private RelativeLayout layoutLaBan;
    private RelativeLayout layoutRate;
    private RelativeLayout layoutShare;
    private RelativeLayout layoutSinhHoc;
    private RelativeLayout layoutVanKhan;
    private RelativeLayout layoutWeather;
    private RelativeLayout layoutXoSo;
    private RelativeLayout layoutYNghia;
    private onActionFromFragmentHome onActionFromFragmentHome;
    private TextView tvUpgrade;
    View view;

    class C12871 implements OnClickListener {
        C12871() {
        }

        public void onClick(View v) {
            FragmentMore.this.getActivity().startActivityForResult(new Intent(FragmentMore.this.getActivity(), UpgradePremiumActivity.class), 1221);
        }
    }

    class C12892 implements OnClickListener {

        class C12881 implements DialogInterface.OnClickListener {
            C12881() {
            }

            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        }

        C12892() {
        }

        public void onClick(View v) {
            if (FragmentMore.this.application.isPurchase) {
                Builder alertDialogBuilder = new Builder(FragmentMore.this.getActivity());
                alertDialogBuilder.setTitle((CharSequence) "Premium");
                alertDialogBuilder.setMessage(FragmentMore.this.getString(R.string.succes_purchase_message)).setCancelable(false).setPositiveButton((CharSequence) "OK", new C12881());
                alertDialogBuilder.create().show();
                return;
            }
            FragmentMore.this.getActivity().startActivityForResult(new Intent(FragmentMore.this.getActivity(), UpgradePremiumActivity.class), 1221);
        }
    }

    public static FragmentMore newInstance() {
        return new FragmentMore();
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
            this.view = inflater.inflate(R.layout.fragment_more, container, false);
            InitUI();
        }
        this.frame.addView(this.view);
        return this.frame;
    }

    private void InitUI() {
        ((TextView) this.view.findViewById(R.id.header_button_center_tv)).setTypeface(this.typeRegularNew);
        ((TextView) this.view.findViewById(R.id.van_khan_tv)).setTypeface(this.typeRegularNew);
        ((TextView) this.view.findViewById(R.id.y_nghia_ngay_le_tv)).setTypeface(this.typeRegularNew);
        ((TextView) this.view.findViewById(R.id.danh_gia_tv)).setTypeface(this.typeRegularNew);
        ((TextView) this.view.findViewById(R.id.chia_se_tv)).setTypeface(this.typeRegularNew);
        ((TextView) this.view.findViewById(R.id.la_ban_tv)).setTypeface(this.typeRegularNew);
        ((TextView) this.view.findViewById(R.id.phan_hoi_tv)).setTypeface(this.typeRegularNew);
        ((TextView) this.view.findViewById(R.id.weather_tv)).setTypeface(this.typeRegularNew);
        ((TextView) this.view.findViewById(R.id.xoso_tv)).setTypeface(this.typeRegularNew);
        ((TextView) this.view.findViewById(R.id.anhnen_tv)).setTypeface(this.typeRegularNew);
        ((TextView) this.view.findViewById(R.id.sinh_hoc_tv)).setTypeface(this.typeRegularNew);
        ((TextView) this.view.findViewById(R.id.anhnen_tv)).setTypeface(this.typeRegularNew);
        ((TextView) this.view.findViewById(R.id.upgrade_tv)).setTypeface(this.typeRegularNew);
        this.tvUpgrade = (TextView) this.view.findViewById(R.id.upgrade_tv);
        this.ivCheck = (ImageView) this.view.findViewById(R.id.ivCheck);
        this.layoutVanKhan = (RelativeLayout) this.view.findViewById(R.id.van_khan);
        this.layoutYNghia = (RelativeLayout) this.view.findViewById(R.id.y_nghia_ngay_le);
        this.layoutRate = (RelativeLayout) this.view.findViewById(R.id.danh_gia);
        this.layoutShare = (RelativeLayout) this.view.findViewById(R.id.chia_se);
        this.layoutFeedBack = (RelativeLayout) this.view.findViewById(R.id.phan_hoi);
        this.layoutWeather = (RelativeLayout) this.view.findViewById(R.id.weather);
        this.layoutXoSo = (RelativeLayout) this.view.findViewById(R.id.xoso);
        this.layoutSinhHoc = (RelativeLayout) this.view.findViewById(R.id.sinh_hoc);
        this.layoutLaBan = (RelativeLayout) this.view.findViewById(R.id.la_ban);
        this.layoutAnhNen = (RelativeLayout) this.view.findViewById(R.id.anhnen);
        this.layoutVanKhan.setOnClickListener(this);
        this.layoutYNghia.setOnClickListener(this);
        this.layoutRate.setOnClickListener(this);
        this.layoutShare.setOnClickListener(this);
        this.layoutFeedBack.setOnClickListener(this);
        this.layoutAnhNen.setOnClickListener(this);
        this.layoutWeather.setOnClickListener(this);
        this.layoutXoSo.setOnClickListener(this);
        this.layoutSinhHoc.setOnClickListener(this);
        this.layoutLaBan.setOnClickListener(this);
        if (this.application.isPurchase) {
            this.tvUpgrade.setText("Đã mua");
            this.ivCheck.setVisibility(0);
            this.view.findViewById(R.id.header_button_right_iv_upgrade_premium).setVisibility(8);
        } else {
            this.view.findViewById(R.id.header_button_right_iv_upgrade_premium).setOnClickListener(new C12871());
        }
        this.view.findViewById(R.id.upgrade).setOnClickListener(new C12892());
    }

    public void reloadAds() {
        if (this.application.isPurchase) {
            this.tvUpgrade.setText("Đã mua");
            this.ivCheck.setVisibility(0);
            this.view.findViewById(R.id.header_button_right_iv_upgrade_premium).setVisibility(8);
        }
        ((Banner) this.view.findViewById(R.id.banner)).reloadAds();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.anhnen:
                ((BaseActivity) getActivity()).changeActivity(true, AnhNenActivity.class);
                return;
            case R.id.weather:
                ((BaseActivity) getActivity()).changeActivity(true, WeatherActivity.class);
                return;
            case R.id.van_khan:
                ((BaseActivity) getActivity()).changeActivity(true, VanKhanActivity.class);
                return;
            case R.id.la_ban:
                ((BaseActivity) getActivity()).changeActivity(true, LaBanActivity.class);
                return;
            case R.id.xoso:
                Intent xosoIntent = new Intent(getActivity(), WebViewScreenActionbar.class);
                xosoIntent.putExtra("title", "Kết Quả Xổ Số");
                getActivity().startActivityForResult(xosoIntent, BaseActivity.REQUESTCODE);
                getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                return;
            case R.id.sinh_hoc:
                Intent sinhhocIntent = new Intent(getActivity(), WebViewScreenActionbar.class);
                sinhhocIntent.putExtra("title", "Nhịp Sinh Học");
                getActivity().startActivityForResult(sinhhocIntent, BaseActivity.REQUESTCODE);
                getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                return;
            case R.id.y_nghia_ngay_le:
                ((BaseActivity) getActivity()).changeActivity(true, HolidayActivity.class);
                return;
            case R.id.chia_se:
                Intent sendIntent = new Intent();
                sendIntent.setAction("android.intent.action.SEND");
                sendIntent.putExtra("android.intent.extra.TEXT", "https://play.google.com/store/apps/details?id=com.example.lequan.lichvannien");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                return;
            case R.id.phan_hoi:
                ((BaseActivity) getActivity()).changeActivity(true, FeedbackActivity.class);
                return;
            case R.id.danh_gia:
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + getActivity().getPackageName())));
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
