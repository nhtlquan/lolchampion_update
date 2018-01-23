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
import com.example.lequan.lichvannien.MainApplication;
import com.example.lequan.lichvannien.activity.VanKhanChildActivity;
import com.example.lequan.lichvannien.activity.VanKhanDetailActivity;
import com.example.lequan.lichvannien.model.VanKhan;
import com.example.lequan.lichvannien.R;
import java.util.ArrayList;

public class VanKhanAdapter extends Adapter<VanKhanAdapter.MyViewHolder> {
    View itemView;
    private ArrayList<VanKhan> listVanKhan = new ArrayList();
    private Context mContext;
    private int type;
    Typeface typeBoldNew;
    Typeface typeRegularNew;

    public class MyViewHolder extends ViewHolder {
        private TextView tvName;

        public MyViewHolder(View view) {
            super(view);
            this.tvName = (TextView) view.findViewById(R.id.tv_name);
            this.tvName.setTypeface(VanKhanAdapter.this.typeRegularNew);
        }
    }

    public VanKhanAdapter(Context mContext, ArrayList<VanKhan> listVanKhan, int type) {
        this.listVanKhan = listVanKhan;
        this.mContext = mContext;
        this.type = type;
        this.typeRegularNew = Typeface.createFromAsset(mContext.getAssets(), "fonts/UTM Helve.ttf");
        this.typeBoldNew = Typeface.createFromAsset(mContext.getAssets(), "fonts/UTM HelveBold.ttf");
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_van_khan, parent, false);
        return new MyViewHolder(this.itemView);
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        final VanKhan mVanKhan = (VanKhan) this.listVanKhan.get(position);
        if (mVanKhan != null) {
            if (this.type == 1) {
                holder.tvName.setText(mVanKhan.getTitleGroup());
            } else {
                holder.tvName.setText(mVanKhan.getTitle());
            }
        }
        this.itemView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MainApplication application = (MainApplication) VanKhanAdapter.this.mContext.getApplicationContext();
                if (VanKhanAdapter.this.type == 1) {
                    VanKhanChildActivity.GROUP = mVanKhan.getGroup();
                    ((Activity) VanKhanAdapter.this.mContext).startActivity(new Intent(VanKhanAdapter.this.mContext, VanKhanChildActivity.class));
                    return;
                }
                VanKhanDetailActivity.mVanKhan = mVanKhan;
                ((Activity) VanKhanAdapter.this.mContext).startActivity(new Intent(VanKhanAdapter.this.mContext, VanKhanDetailActivity.class));
            }
        });
    }

    public int getItemCount() {
        return this.listVanKhan.size();
    }
}
