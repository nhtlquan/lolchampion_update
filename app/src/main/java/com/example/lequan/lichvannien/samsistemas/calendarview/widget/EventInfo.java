package com.example.lequan.lichvannien.samsistemas.calendarview.widget;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class EventInfo implements Parcelable {
    public static final Creator<EventInfo> CREATOR = new C14501();
    private String address;
    private String alert;
    private String end;
    private int fullDay;
    private int id;
    private String name;
    private String note;
    private String repeat;
    private String report;
    private int solar;
    private String start;
    private int type;

    public EventInfo() {
    }

    static class C14501 implements Creator<EventInfo> {
        C14501() {
        }

        public EventInfo createFromParcel(Parcel in) {
            return new EventInfo(in);
        }

        public EventInfo[] newArray(int size) {
            return new EventInfo[size];
        }
    }

    protected EventInfo(Parcel in) {
        this.id = in.readInt();
        this.type = in.readInt();
        this.name = in.readString();
        this.solar = in.readInt();
        this.fullDay = in.readInt();
        this.start = in.readString();
        this.end = in.readString();
        this.report = in.readString();
        this.repeat = in.readString();
        this.address = in.readString();
        this.note = in.readString();
        this.alert = in.readString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.type);
        dest.writeString(this.name);
        dest.writeInt(this.solar);
        dest.writeInt(this.fullDay);
        dest.writeString(this.start);
        dest.writeString(this.end);
        dest.writeString(this.report);
        dest.writeString(this.repeat);
        dest.writeString(this.address);
        dest.writeString(this.note);
        dest.writeString(this.alert);
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

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSolar() {
        return this.solar;
    }

    public void setSolar(int solar) {
        this.solar = solar;
    }

    public int getFullDay() {
        return this.fullDay;
    }

    public void setFullDay(int fullDay) {
        this.fullDay = fullDay;
    }

    public String getStart() {
        return this.start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return this.end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getReport() {
        return this.report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getRepeat() {
        return this.repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAlert() {
        return this.alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String toString() {
        return super.toString();
    }
}
