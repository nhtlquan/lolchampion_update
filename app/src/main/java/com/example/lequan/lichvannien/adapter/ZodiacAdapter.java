package com.example.lequan.lichvannien.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.lequan.lichvannien.activity.ZodiacActivity;
import com.example.lequan.lichvannien.model.Zodiac;
import com.example.lequan.lichvannien.R;
import java.util.ArrayList;

public class ZodiacAdapter extends Adapter<ZodiacAdapter.MyViewHolder> {
    private View itemView;
    private ArrayList<Zodiac> lsZodiac = new ArrayList();
    private Context mContext;

    public class MyViewHolder extends ViewHolder {
        private ImageView imgZodiac;
        private ImageView ivBlue;
        private TextView tvName;

        public MyViewHolder(View view) {
            super(view);
            this.imgZodiac = (ImageView) view.findViewById(R.id.img_avatar);
            this.tvName = (TextView) view.findViewById(R.id.tv_name);
            this.ivBlue = (ImageView) view.findViewById(R.id.ivBlue);
        }
    }

    public ZodiacAdapter(Context mContext, ArrayList<Zodiac> lsZodiac) {
        this.lsZodiac = lsZodiac;
        this.mContext = mContext;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_zodiac, parent, false);
        return new MyViewHolder(this.itemView);
    }

    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Zodiac value = (Zodiac) this.lsZodiac.get(position);
        if (value != null) {
            holder.imgZodiac.setImageResource(value.getAvatar());
            holder.tvName.setText(value.getName());
            if (value.isSelect()) {
                holder.ivBlue.setVisibility(8);
            } else {
                holder.ivBlue.setVisibility(0);
            }
            holder.imgZodiac.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    ((ZodiacActivity) ZodiacAdapter.this.mContext).updateData(position);
                }
            });
            holder.tvName.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    ((ZodiacActivity) ZodiacAdapter.this.mContext).updateData(position);
                }
            });
        }
    }

    public int getItemCount() {
        return this.lsZodiac.size();
    }
}
