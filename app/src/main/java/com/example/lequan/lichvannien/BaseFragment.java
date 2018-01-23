package com.example.lequan.lichvannien;

import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.AnalyticsEvents;
import com.example.lequan.lichvannien.common.Define;
import com.example.lequan.lichvannien.network.DataLoader;
import com.example.lequan.lichvannien.network.StringRequestCallback;
import com.example.lequan.lichvannien.utils.NSLog;
import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.Dialog.Builder;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;
import com.rey.material.app.ThemeManager;
import com.example.lequan.lichvannien.R;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;

public class BaseFragment extends Fragment implements AnimationListener {
    public static final String TAG = BaseFragment.class.getSimpleName();
    public MainApplication application;
    protected Animation bottomIn;
    protected Animation bottomOut;
    IntentFilter intentFilter;
    protected Animation leftIn;
    protected Animation leftOut;
    protected Animation rightIn;
    protected Animation rightOut;
    protected Animation topIn;
    protected Animation topOut;
    public Typeface typeBoldNew;
    public Typeface typeRegularNew;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.application = (MainApplication) getActivity().getApplication();
        this.leftOut = AnimationUtils.loadAnimation(getActivity(), R.anim.left_out);
        this.leftIn = AnimationUtils.loadAnimation(getActivity(), R.anim.left_in);
        this.topOut = AnimationUtils.loadAnimation(getActivity(), R.anim.top_out);
        this.topIn = AnimationUtils.loadAnimation(getActivity(), R.anim.top_in);
        this.rightOut = AnimationUtils.loadAnimation(getActivity(), R.anim.right_out);
        this.rightIn = AnimationUtils.loadAnimation(getActivity(), R.anim.right_in);
        this.bottomOut = AnimationUtils.loadAnimation(getActivity(), R.anim.bottom_out);
        this.bottomIn = AnimationUtils.loadAnimation(getActivity(), R.anim.bottom_in);
        this.leftOut.setAnimationListener(this);
        this.leftIn.setAnimationListener(this);
        this.topOut.setAnimationListener(this);
        this.topIn.setAnimationListener(this);
        this.rightOut.setAnimationListener(this);
        this.rightIn.setAnimationListener(this);
        this.bottomIn.setAnimationListener(this);
        this.bottomOut.setAnimationListener(this);
        this.typeRegularNew = Typeface.createFromAsset(getActivity().getAssets(), "fonts/UTM Helve.ttf");
        this.typeBoldNew = Typeface.createFromAsset(getActivity().getAssets(), "fonts/UTM HelveBold.ttf");
    }

    public void postData(String method, boolean isShowLoading, BaseActivity activity, final String api, String... param) {
        DataLoader.postAPI(method, isShowLoading, activity, api, new StringRequestCallback() {
            public void onSuccess(String response) {
                NSLog.m1451d(BaseFragment.TAG, "Success: " + response);
                BaseFragment.this.getResponse(response, api);
            }

            public void onError(int statusCode, String responseString) {
                NSLog.m1451d(BaseFragment.TAG, "Error: " + responseString);
                BaseFragment.this.getError();
            }
        }, param);
    }

    protected void getResponse(String jsonObject, String api) {
    }

    protected void getError() {
    }

    protected void showDatePicker() {
        int i;
        if (ThemeManager.getInstance().getCurrentTheme() == 0) {
            i = R.style.Material_App_Dialog_DatePicker_Light;
        } else {
            i = R.style.Material_App_Dialog_DatePicker;
        }
        Builder builder = new DatePickerDialog.Builder(i) {
            public void onPositiveActionClicked(DialogFragment fragment) {
                String date = ((DatePickerDialog) fragment.getDialog()).getFormattedDate(new SimpleDateFormat(Define.TIME_FORMAT));
                Toast.makeText(BaseFragment.this.getActivity(), "Date is " + date, 0).show();
                BaseFragment.this.onSelectedDayPicker(date);
                super.onPositiveActionClicked(fragment);
            }

            public void onNegativeActionClicked(DialogFragment fragment) {
                Toast.makeText(BaseFragment.this.getActivity(), AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_CANCELLED, 0).show();
                super.onNegativeActionClicked(fragment);
            }
        };
        builder.positiveAction("OK").negativeAction("CANCEL");
        DialogFragment.newInstance(builder).show(getFragmentManager(), null);
    }

    protected void showChooseSingle(String title, String[] listChoise) {
        boolean isLightTheme;
        if (ThemeManager.getInstance().getCurrentTheme() == 0) {
            isLightTheme = true;
        } else {
            isLightTheme = false;
        }
        Builder builder = new SimpleDialog.Builder(isLightTheme ? R.style.SimpleDialogLight : R.style.SimpleDialog) {
            public void onPositiveActionClicked(DialogFragment fragment) {
                BaseFragment.this.onChoise(getSelectedValue().toString(), getSelectedIndex());
                super.onPositiveActionClicked(fragment);
            }

            public void onNegativeActionClicked(DialogFragment fragment) {
                super.onNegativeActionClicked(fragment);
            }
        };
        ((SimpleDialog.Builder) builder).items(listChoise, 0).title(title).positiveAction("Đồng ý").negativeAction("Hủy");
        DialogFragment.newInstance(builder).show(getFragmentManager(), null);
    }

    protected void onSelectedDayPicker(String day) {
    }

    protected void onChoise(String value, int index) {
    }

    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    public void onAnimationStart(Animation animation) {
    }

    public void onAnimationEnd(Animation animation) {
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public static final String md5(String s) {
        try {
            MessageDigest digest = MessageDigest.getInstance(s);
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();
            StringBuffer hexString = new StringBuffer();
            for (byte b : messageDigest) {
                String h = Integer.toHexString(b & 255);
                while (h.length() < 2) {
                    h = AppEventsConstants.EVENT_PARAM_VALUE_NO + h;
                }
                hexString.append(h);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
    }
}
