package com.example.lequan.lichvannien.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lequan.lichvannien.R;

import java.util.ArrayList;

public class BestDayAdapter extends Adapter<BestDayAdapter.MyViewHolder> {
    ClickNgayTotListener clickNgayTotListener;
    private boolean isXung = false;
    private ArrayList<String> listDay = new ArrayList();
    private Context mContext;
    Typeface typeBoldNew;
    Typeface typeRegularNew;

    public interface ClickNgayTotListener {
        void clickNgayTot(String str);
    }

    public class MyViewHolder extends ViewHolder {
        TextView tvDay;

        public MyViewHolder(View view) {
            super(view);
            this.tvDay = (TextView) view.findViewById(R.id.tvDay);
            this.tvDay.setTypeface(BestDayAdapter.this.typeBoldNew);
            ((TextView) view.findViewById(R.id.tvChiTiet)).setTypeface(BestDayAdapter.this.typeRegularNew);
        }
    }

    public void setClickNgayTotListener(ClickNgayTotListener clickNgayTotListener) {
        this.clickNgayTotListener = clickNgayTotListener;
    }

    public BestDayAdapter(Context mContext, ArrayList<String> listDay) {
        this.listDay = listDay;
        this.mContext = mContext;
        this.typeRegularNew = Typeface.createFromAsset(mContext.getAssets(), "fonts/UTM Helve.ttf");
        this.typeBoldNew = Typeface.createFromAsset(mContext.getAssets(), "fonts/UTM HelveBold.ttf");
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_best_day, parent, false));
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        final String value = (String) this.listDay.get(position);
        holder.tvDay.setText(value + "");
        holder.itemView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (BestDayAdapter.this.clickNgayTotListener != null) {
                    BestDayAdapter.this.clickNgayTotListener.clickNgayTot(value);
                }
            }
        });
    }

    public int getItemCount() {
        return this.listDay.size();
    }
}
