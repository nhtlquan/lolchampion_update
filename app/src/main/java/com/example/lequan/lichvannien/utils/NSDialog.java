package com.example.lequan.lichvannien.utils;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import com.example.lequan.lichvannien.BaseActivity;
import com.example.lequan.lichvannien.R;

public class NSDialog {

    public interface OnDialogClick {
        void onNegative();

        void onPositive();
    }

    static class C13171 implements OnClickListener {
        C13171() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    static class C13193 implements OnClickListener {
        C13193() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    public static void showDialog(BaseActivity activity, String message, String positive, String negative, final OnDialogClick mOnDialogClick) {
        new Builder(activity).setMessage(message).setPositiveButton(positive, new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (mOnDialogClick != null) {
                    mOnDialogClick.onPositive();
                }
            }
        }).setNegativeButton(negative, new C13171()).show();
    }

    public static void showDialogBasic(BaseActivity activity, String message) {
        new Builder(activity).setMessage(message).setPositiveButton(activity.getString(R.string.txt_ok), new C13193()).show();
    }

    public static void showCustomDialog(Context mContext, int layoutId) {
        final Dialog dialog = new Dialog(mContext);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.requestWindowFeature(1);
        dialog.setContentView(layoutId);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                dialog.dismiss();
            }
        }, 1000);
        dialog.show();
    }
}
