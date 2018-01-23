package com.example.lequan.lichvannien.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.lequan.lichvannien.utils.Utils;
import com.example.lequan.lichvannien.R;

public class GioHoangDaoAdapter extends Adapter<GioHoangDaoAdapter.MyViewHolder> {
    private String[] arrZodiac;
    private boolean isXung = false;
    private Context mContext;

    public class MyViewHolder extends ViewHolder {
        private ImageView imgZodiac;
        private TextView tvHour;
        private TextView tvName;

        public MyViewHolder(View view) {
            super(view);
            this.imgZodiac = (ImageView) view.findViewById(R.id.img_zodiac);
            this.tvName = (TextView) view.findViewById(R.id.tv_name);
            this.tvHour = (TextView) view.findViewById(R.id.tv_hour);
            if (GioHoangDaoAdapter.this.isXung) {
                this.tvHour.setVisibility(8);
            }
        }
    }

    public GioHoangDaoAdapter(Context mContext, String[] arrZodiac) {
        this.arrZodiac = arrZodiac;
        this.mContext = mContext;
    }

    public GioHoangDaoAdapter(Context mContext, String[] arrZodiac, boolean isXung) {
        this.arrZodiac = arrZodiac;
        this.mContext = mContext;
        this.isXung = isXung;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_gio_hoang_dao, parent, false));
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        String value = this.arrZodiac[position];
        if (value != null) {
            Utils.setIconZodiac(holder.imgZodiac, value);
            if (this.isXung) {
                holder.tvName.setText(value.trim().replace(" ", "\n"));
                return;
            }
            String[] arrName = value.split(" ");
            if (arrName.length > 1) {
                holder.tvName.setText(arrName[0] + " " + arrName[1]);
                holder.tvHour.setText(arrName[2]);
            }
        }
    }

    public int getItemCount() {
        return this.arrZodiac.length;
    }
}
