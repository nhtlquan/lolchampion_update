package com.example.lequan.lichvannien.dao;

public class AdsCustomDAO {
    String banner = "";
    String description = "";
    String icon = "";
    String package_name = "";
    String thumbnail = "";
    String title = "";
    String url_store = "";

    public AdsCustomDAO(String title, String description, String package_name, String icon, String url_store, String banner, String thumbnail) {
        this.package_name = package_name;
        this.title = title;
        this.icon = icon;
        this.banner = banner;
        this.thumbnail = thumbnail;
        this.description = description;
        this.url_store = url_store;
    }

    public String getPackage_name() {
        return this.package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getBanner() {
        return this.banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getThumbnail() {
        return this.thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl_store() {
        return this.url_store;
    }

    public void setUrl_store(String url_store) {
        this.url_store = url_store;
    }
}
