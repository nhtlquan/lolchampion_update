package com.example.lequan.lichvannien.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.lequan.lichvannien.MainApplication;
import com.example.lequan.lichvannien.activity.AnhNenActivity;
import com.example.lequan.lichvannien.R;
import java.io.File;

public class AnhNenAdapter extends Adapter<android.support.v7.widget.RecyclerView.ViewHolder> {
    private Activity activity;
    private MainApplication application;

    class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        private ImageView iv;
        private ImageView ivTick;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    AnhNenAdapter.this.application.editor.putInt(AnhNenActivity.KEY_PREF_BG_POSITION, ViewHolder.this.getAdapterPosition());
                    AnhNenAdapter.this.application.editor.commit();
                    AnhNenAdapter.this.notifyDataSetChanged();
                }
            });
            this.ivTick = (ImageView) v.findViewById(R.id.ivTick);
            this.iv = (ImageView) v.findViewById(R.id.iv);
        }
    }

    public AnhNenAdapter(Activity activity) {
        this.activity = activity;
        this.application = (MainApplication) activity.getApplication();
    }

    public int getItemCount() {
        return this.application.listBGNoiDung.size();
    }

    public android.support.v7.widget.RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_anhnen_item, parent, false));
    }

    public void onBindViewHolder(android.support.v7.widget.RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        String filePath = this.activity.getFilesDir().getPath() + "/img/bg_noi_dung_" + position + ".png";
        if (new File(filePath).exists()) {
            Glide.with(this.activity).load(filePath).apply(new RequestOptions().centerCrop().override(516, 896)).into(viewHolder.iv);
        } else {
            Glide.with(this.activity).load(this.application.listBGNoiDung.get(position)).apply(new RequestOptions().centerCrop().override(516, 896)).into(viewHolder.iv);
        }
        if (this.application.pref.getInt(AnhNenActivity.KEY_PREF_BG_POSITION, 0) == position) {
            viewHolder.ivTick.setVisibility(0);
        } else {
            viewHolder.ivTick.setVisibility(8);
        }
    }
}
