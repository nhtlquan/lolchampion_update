package com.example.lequan.lichvannien.model;

public class Zodiac {
    private int avatar;
    private int bigAvatar;
    private String date;
    private int id;
    private boolean isSelect;
    private String name;

    public Zodiac(int avatar, int bigAvatar, String name, String date, boolean isSelect) {
        this.avatar = avatar;
        this.name = name;
        this.isSelect = isSelect;
        this.date = date;
        this.bigAvatar = bigAvatar;
    }

    public boolean isSelect() {
        return this.isSelect;
    }

    public void setSelect(boolean select) {
        this.isSelect = select;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAvatar() {
        return this.avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBigAvatar() {
        return this.bigAvatar;
    }

    public void setBigAvatar(int bigAvatar) {
        this.bigAvatar = bigAvatar;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
