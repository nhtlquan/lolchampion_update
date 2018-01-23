package com.example.lequan.lichvannien.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.lequan.lichvannien.activity.HolidayDetailActivity;
import com.example.lequan.lichvannien.model.Holiday;
import com.example.lequan.lichvannien.R;
import java.util.ArrayList;

public class HolidayAdapter extends Adapter<HolidayAdapter.MyViewHolder> {
    View itemView;
    private ArrayList<Holiday> listVanKhan = new ArrayList();
    private Context mContext;
    Typeface typeBoldNew;
    Typeface typeRegularNew;

    public class MyViewHolder extends ViewHolder {
        private TextView tvName;

        public MyViewHolder(View view) {
            super(view);
            this.tvName = (TextView) view.findViewById(R.id.tv_name);
            this.tvName.setTypeface(HolidayAdapter.this.typeRegularNew);
        }
    }

    public HolidayAdapter(Context mContext, ArrayList<Holiday> listVanKhan) {
        this.listVanKhan = listVanKhan;
        this.mContext = mContext;
        this.typeRegularNew = Typeface.createFromAsset(mContext.getAssets(), "fonts/UTM Helve.ttf");
        this.typeBoldNew = Typeface.createFromAsset(mContext.getAssets(), "fonts/UTM HelveBold.ttf");
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_van_khan, parent, false);
        return new MyViewHolder(this.itemView);
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Holiday mVanKhan = (Holiday) this.listVanKhan.get(position);
        if (mVanKhan != null) {
            holder.tvName.setText(mVanKhan.getName());
        }
        this.itemView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                HolidayDetailActivity.mHoliday = mVanKhan;
                ((Activity) HolidayAdapter.this.mContext).startActivity(new Intent(HolidayAdapter.this.mContext, HolidayDetailActivity.class));
            }
        });
    }

    public int getItemCount() {
        return this.listVanKhan.size();
    }
}
