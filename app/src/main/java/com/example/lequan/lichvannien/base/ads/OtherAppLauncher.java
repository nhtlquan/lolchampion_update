package com.example.lequan.lichvannien.base.ads;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.lequan.lichvannien.R;
import com.example.lequan.lichvannien.base.utils.BaseUtils;

public class OtherAppLauncher extends AppCompatActivity {

    class C11181 implements Runnable {
        C11181() {
        }

        public void run() {
            OtherAppLauncher.this.finish();
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        int i = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_app);
        String url_store = getIntent().getExtras().getString("link");
        String[] listPackageName = getIntent().getExtras().getString("package_name").contains(",") ? getIntent().getExtras().getString("package_name").split(",") : new String[]{getIntent().getExtras().getString("package_name")};
        boolean isInstalled = false;
        String packageNameInstalled = "";
        int length = listPackageName.length;
        while (i < length) {
            String packageName = listPackageName[i];
            if (BaseUtils.isInstalled(this, packageName)) {
                isInstalled = true;
                packageNameInstalled = packageName;
                break;
            }
            i++;
        }
        if (isInstalled) {
            Intent launchIntent = getPackageManager().getLaunchIntentForPackage(packageNameInstalled);
            if (launchIntent != null) {
                startActivity(launchIntent);
            } else {
                gotoUrl(url_store);
            }
        } else {
            gotoUrl(url_store);
        }
        new Handler().postDelayed(new C11181(), 1000);
    }

    private boolean isInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, 1);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void gotoUrl(String url) {
        Intent i = new Intent("android.intent.action.VIEW");
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
