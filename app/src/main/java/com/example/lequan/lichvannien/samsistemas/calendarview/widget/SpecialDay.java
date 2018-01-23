package com.example.lequan.lichvannien.samsistemas.calendarview.widget;

public class SpecialDay {
    private String date;
    private int id;
    private boolean isDuongLich;
    private String nameDate;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isDuongLich() {
        return this.isDuongLich;
    }

    public void setDuongLich(boolean duongLich) {
        this.isDuongLich = duongLich;
    }

    public String getNameDate() {
        return this.nameDate;
    }

    public void setNameDate(String nameDate) {
        this.nameDate = nameDate;
    }
}
