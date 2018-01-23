package com.example.lequan.lichvannien.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.lequan.lichvannien.base.BaseActivity;
import com.example.lequan.lichvannien.base.utils.BaseConstant;
import com.example.lequan.lichvannien.MainApplication;
import com.example.lequan.lichvannien.util.IabHelper;
import com.example.lequan.lichvannien.util.IabHelper.OnIabPurchaseFinishedListener;
import com.example.lequan.lichvannien.util.IabHelper.OnIabSetupFinishedListener;
import com.example.lequan.lichvannien.util.IabResult;
import com.example.lequan.lichvannien.util.Purchase;
import com.example.lequan.lichvannien.R;

public class UpgradePremiumActivity extends BaseActivity {
    private String TAG = com.example.lequan.lichvannien.BaseActivity.TAG;
    private IabHelper mHelper;
    OnIabPurchaseFinishedListener mPurchaseFinishedListener = new C12094();
    private RelativeLayout rlOk;
    private TextView tvTitleUpgradePremium;

    class C12061 implements OnClickListener {
        C12061() {
        }

        public void onClick(View v) {
            if (UpgradePremiumActivity.this.baseApplication.isPurchase) {
                UpgradePremiumActivity.this.onBackPressed();
                return;
            }
            try {
                UpgradePremiumActivity.this.mHelper.launchPurchaseFlow(UpgradePremiumActivity.this, MainApplication.skuId, BaseConstant.REQUEST_CODE_IN_APP_BILLING, UpgradePremiumActivity.this.mPurchaseFinishedListener, "HuyAnhPayload");
            } catch (Exception e) {
                Log.e(UpgradePremiumActivity.this.TAG, "error launch Purchase: " + e.getMessage());
            }
        }
    }

    class C12072 implements OnClickListener {
        C12072() {
        }

        public void onClick(View v) {
            UpgradePremiumActivity.this.onBackPressed();
        }
    }

    class C12083 implements OnIabSetupFinishedListener {
        C12083() {
        }

        public void onIabSetupFinished(IabResult result) {
            Log.d(UpgradePremiumActivity.this.TAG, "Setup finished.");
            if (!result.isSuccess()) {
                Log.e(UpgradePremiumActivity.this.TAG, "Problem setting up in-app billing: " + result);
            } else if (UpgradePremiumActivity.this.mHelper != null) {
            }
        }
    }

    class C12094 implements OnIabPurchaseFinishedListener {
        C12094() {
        }

        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            Log.d(UpgradePremiumActivity.this.TAG, "Purchase finished: " + result + ", purchase: " + purchase);
            if (UpgradePremiumActivity.this.mHelper != null) {
                if (result.isFailure()) {
                    Log.e(UpgradePremiumActivity.this.TAG, "Error purchasing: " + result);
                    return;
                }
                Log.d(UpgradePremiumActivity.this.TAG, "Purchase successful.");
                if (purchase.getSku().equals(MainApplication.skuId)) {
                    Log.d(UpgradePremiumActivity.this.TAG, "Purchase is premium upgrade. Congratulating user.");
                    UpgradePremiumActivity.this.baseApplication.isPurchase = true;
                    Toast.makeText(UpgradePremiumActivity.this.baseActivity, UpgradePremiumActivity.this.getString(R.string.succes_purchase_message), 0).show();
                    return;
                }
                Log.e(UpgradePremiumActivity.this.TAG, "Purchase is faile.");
            }
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_upgrade_premium);
        this.tvTitleUpgradePremium = (TextView) findViewById(R.id.tvTitleUpgradePremium);
        this.tvTitleUpgradePremium.setText("Nhận ngay\n" + getString(R.string.app_name) + "\nPremium");
        this.rlOk = (RelativeLayout) findViewById(R.id.rlOk);
        this.rlOk.setOnClickListener(new C12061());
        findViewById(R.id.ivClose).setOnClickListener(new C12072());
        this.mHelper = new IabHelper(this, MainApplication.base64EncodedPublicKey);
        this.mHelper.enableDebugLogging(true);
        this.mHelper.startSetup(new C12083());
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(this.TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);
        if (this.mHelper != null) {
            if (this.mHelper.handleActivityResult(requestCode, resultCode, data)) {
                Log.d(this.TAG, "onActivityResult handled by IABUtil.");
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}
