package com.example.lequan.lichvannien.dao;

public class AdsConfigDAO {
    public static final String DEFAULT_START_POPUP_FIRST = "1";
    String admob_banner = "ca-app-pub-9849209325477495/7258296360";
    String admob_popup = "ca-app-pub-9849209325477495/8735029562";
    String banner = "admob";
    String facebook_banner = "999215223462174_1003051093078587";
    String facebook_popup = "999215223462174_1003051513078545";
    String facebook_thumbnail = "999215223462174_1003051586411871";
    int offset_show_thumbnail = 10;
    int offset_time_show_popup = 300;
    String popup = "admob";
    int random_show_banner_hdv = 0;
    int random_show_thumbai_hdv = 0;
    String start_popup_first = "1";
    int start_show_thumbnail = 5;
    String thumbnail = "facebook";
    int time_start_show_popup = 0;

    public AdsConfigDAO() {
    }

    public AdsConfigDAO(int time_start_show_popup, int offset_time_show_popup, int random_show_banner_hdv, int start_show_thumbnail, int offset_show_thumbnail, int random_show_thumbai_hdv, String banner, String popup, String thumbnail, String admob_banner, String admob_popup, String facebook_banner, String facebook_popup, String facebook_thumbnail, String start_popup_first) {
        this.time_start_show_popup = time_start_show_popup;
        this.offset_time_show_popup = offset_time_show_popup;
        this.random_show_banner_hdv = random_show_banner_hdv;
        this.start_show_thumbnail = start_show_thumbnail;
        this.offset_show_thumbnail = offset_show_thumbnail;
        this.random_show_thumbai_hdv = random_show_thumbai_hdv;
        this.banner = banner;
        this.popup = popup;
        this.thumbnail = thumbnail;
        this.admob_banner = admob_banner;
        this.admob_popup = admob_popup;
        this.facebook_banner = facebook_banner;
        this.facebook_popup = facebook_popup;
        this.facebook_thumbnail = facebook_thumbnail;
        this.start_popup_first = start_popup_first;
    }

    public void setStart_show_thumbnail(int start_show_thumbnail) {
        this.start_show_thumbnail = start_show_thumbnail;
    }

    public void setOffset_show_thumbnail(int offset_show_thumbnail) {
        this.offset_show_thumbnail = offset_show_thumbnail;
    }

    public void setRandom_show_banner_hdv(int random_show_banner_hdv) {
        this.random_show_banner_hdv = random_show_banner_hdv;
    }

    public void setRandom_show_thumbai_hdv(int random_show_thumbai_hdv) {
        this.random_show_thumbai_hdv = random_show_thumbai_hdv;
    }

    public void setTime_start_show_popup(int time_start_show_popup) {
        this.time_start_show_popup = time_start_show_popup;
    }

    public void setOffset_time_show_popup(int offset_time_show_popup) {
        this.offset_time_show_popup = offset_time_show_popup;
    }

    public void setAdmob_banner(String admob_banner) {
        this.admob_banner = admob_banner;
    }

    public void setAdmob_popup(String admob_popup) {
        this.admob_popup = admob_popup;
    }

    public void setFacebook_banner(String facebook_banner) {
        this.facebook_banner = facebook_banner;
    }

    public void setFacebook_popup(String facebook_popup) {
        this.facebook_popup = facebook_popup;
    }

    public void setFacebook_thumbnail(String facebook_thumbnail) {
        this.facebook_thumbnail = facebook_thumbnail;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public void setPopup(String popup) {
        this.popup = popup;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getTime_start_show_popup() {
        return this.time_start_show_popup;
    }

    public int getStart_show_thumbnail() {
        return this.start_show_thumbnail;
    }

    public int getOffset_show_thumbnail() {
        return this.offset_show_thumbnail;
    }

    public int getOffset_time_show_popup() {
        return this.offset_time_show_popup;
    }

    public String getBanner() {
        return this.banner;
    }

    public String getPopup() {
        return this.popup;
    }

    public String getAdmob_banner() {
        return this.admob_banner;
    }

    public String getAdmob_popup() {
        return this.admob_popup;
    }

    public String getFacebook_banner() {
        return this.facebook_banner;
    }

    public String getFacebook_popup() {
        return this.facebook_popup;
    }

    public int getRandom_show_thumbai_hdv() {
        return this.random_show_thumbai_hdv;
    }

    public String getThumbnail() {
        return this.thumbnail;
    }

    public int getRandom_show_banner_hdv() {
        return this.random_show_banner_hdv;
    }

    public String getFacebook_thumbnail() {
        return this.facebook_thumbnail;
    }

    public String getStart_popup_first() {
        return this.start_popup_first;
    }

    public void setStart_popup_first(String start_popup_first) {
        this.start_popup_first = start_popup_first;
    }
}
