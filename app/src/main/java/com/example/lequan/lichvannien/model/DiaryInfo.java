package com.example.lequan.lichvannien.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class DiaryInfo implements Parcelable {
    public static final Creator<DiaryInfo> CREATOR = new C12991();
    private String content;
    private String date;
    private int id;
    private String imagePath;
    private String setting;
    private String title;

    public DiaryInfo() {
    }

    static class C12991 implements Creator<DiaryInfo> {
        C12991() {
        }

        public DiaryInfo createFromParcel(Parcel in) {
            return new DiaryInfo(in);
        }

        public DiaryInfo[] newArray(int size) {
            return new DiaryInfo[size];
        }
    }

    protected DiaryInfo(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.content = in.readString();
        this.date = in.readString();
        this.setting = in.readString();
        this.imagePath = in.readString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.date);
        dest.writeString(this.setting);
        dest.writeString(this.imagePath);
    }

    public int describeContents() {
        return 0;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSetting() {
        return this.setting;
    }

    public void setSetting(String setting) {
        this.setting = setting;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
