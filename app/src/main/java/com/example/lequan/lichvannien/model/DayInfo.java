package com.example.lequan.lichvannien.model;

import com.google.gson.Gson;

public class DayInfo {
    private String amLich;
    private String duongLich;
    private String gioHacDao;
    private String gioHoangDao;
    private int hoangDao;
    private String huongXuatHanh;
    private int id;
    private String khongNenLam;
    private String nam;
    private String nenLam;
    private String ngay;
    private String saoTot;
    private String saoXau;
    private String thang;
    private String thu;
    private String tuoiXungKhac;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThu() {
        return this.thu;
    }

    public void setThu(String thu) {
        this.thu = thu;
    }

    public String getAmLich() {
        return this.amLich;
    }

    public void setAmLich(String amLich) {
        this.amLich = amLich;
    }

    public String getDuongLich() {
        return this.duongLich;
    }

    public void setDuongLich(String duongLich) {
        this.duongLich = duongLich;
    }

    public String getNgay() {
        return this.ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getThang() {
        return this.thang;
    }

    public void setThang(String thang) {
        this.thang = thang;
    }

    public String getNam() {
        return this.nam;
    }

    public void setNam(String nam) {
        this.nam = nam;
    }

    public String getGioHoangDao() {
        return this.gioHoangDao;
    }

    public void setGioHoangDao(String gioHoangDao) {
        this.gioHoangDao = gioHoangDao;
    }

    public String getGioHacDao() {
        return this.gioHacDao;
    }

    public void setGioHacDao(String gioHacDao) {
        this.gioHacDao = gioHacDao;
    }

    public String getHuongXuatHanh() {
        return this.huongXuatHanh;
    }

    public void setHuongXuatHanh(String huongXuatHanh) {
        this.huongXuatHanh = huongXuatHanh;
    }

    public String getTuoiXungKhac() {
        return this.tuoiXungKhac;
    }

    public void setTuoiXungKhac(String tuoiXungKhac) {
        this.tuoiXungKhac = tuoiXungKhac;
    }

    public String getSaoTot() {
        return this.saoTot;
    }

    public void setSaoTot(String saoTot) {
        this.saoTot = saoTot;
    }

    public String getSaoXau() {
        return this.saoXau;
    }

    public void setSaoXau(String saoXau) {
        this.saoXau = saoXau;
    }

    public String getNenLam() {
        return this.nenLam;
    }

    public void setNenLam(String nenLam) {
        this.nenLam = nenLam;
    }

    public int getHoangDao() {
        return this.hoangDao;
    }

    public void setHoangDao(int hoangDao) {
        this.hoangDao = hoangDao;
    }

    public String getKhongNenLam() {
        return this.khongNenLam;
    }

    public void setKhongNenLam(String khongNenLam) {
        this.khongNenLam = khongNenLam;
    }

    public String toString() {
        return new Gson().toJson((Object) this);
    }
}
