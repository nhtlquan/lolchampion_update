package com.example.lequan.lichvannien.popup;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.lequan.lichvannien.BaseActivity;
import com.example.lequan.lichvannien.activity.DiaryActivity;
import com.example.lequan.lichvannien.activity.EventActivity;
import com.example.lequan.lichvannien.R;

public class DialogPersional extends DialogFragment implements OnClickListener {
    private Button btnBirthday;
    private Button btnDinary;
    private Button btnFamily;
    private Button btnPersional;
    private Button btnWork;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(1);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        return dialog;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_persional, container);
        initUI(view);
        return view;
    }

    private void initUI(View v) {
        this.btnFamily = (Button) v.findViewById(R.id.btn_family);
        this.btnWork = (Button) v.findViewById(R.id.btn_work);
        this.btnBirthday = (Button) v.findViewById(R.id.btn_birthday);
        this.btnPersional = (Button) v.findViewById(R.id.btn_persional);
        this.btnDinary = (Button) v.findViewById(R.id.btn_dinary);
        this.btnFamily.setOnClickListener(this);
        this.btnWork.setOnClickListener(this);
        this.btnBirthday.setOnClickListener(this);
        this.btnPersional.setOnClickListener(this);
        this.btnDinary.setOnClickListener(this);
    }

    public void onClick(View v) {
        dismiss();
        switch (v.getId()) {
            case R.id.btn_family:
                EventActivity.type = 0;
                ((BaseActivity) getContext()).changeActivity(EventActivity.class);
                return;
            case R.id.btn_work:
                EventActivity.type = 1;
                ((BaseActivity) getContext()).changeActivity(EventActivity.class);
                return;
            case R.id.btn_birthday:
                EventActivity.type = 2;
                ((BaseActivity) getContext()).changeActivity(EventActivity.class);
                return;
            case R.id.btn_persional:
                EventActivity.type = 3;
                ((BaseActivity) getContext()).changeActivity(EventActivity.class);
                return;
            case R.id.btn_dinary:
                ((BaseActivity) getContext()).changeActivity(DiaryActivity.class);
                return;
            default:
                return;
        }
    }
}
