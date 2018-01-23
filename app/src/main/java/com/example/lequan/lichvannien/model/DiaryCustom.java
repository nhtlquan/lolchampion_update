package com.example.lequan.lichvannien.model;

public class DiaryCustom {
    public String color;
    public String font;
    public String img;
    public boolean isSelect;

    public DiaryCustom(String img, String font, String color, boolean isSelect) {
        this.img = img;
        this.font = font;
        this.isSelect = isSelect;
        this.color = color;
    }
}
