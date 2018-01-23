package com.example.lequan.lichvannien.dao;

public class UpdateDAO {
    String button1 = "Yes";
    String button2 = "No";
    String description = "";
    int offset_show = 0;
    int status = 0;
    String title = "";
    String type = "";
    String url_store = "";
    String version = "";

    public UpdateDAO(int status, String type, String version, String title, String description, String url_store, int offset_show) {
        this.status = status;
        this.type = type;
        this.version = version;
        this.title = title;
        this.description = description;
        this.url_store = url_store;
        this.offset_show = offset_show;
    }

    public int getStatus() {
        return this.status;
    }

    public String getDescription() {
        return this.description;
    }

    public String getTitle() {
        return this.title;
    }

    public String getType() {
        return this.type;
    }

    public String getUrl_store() {
        return this.url_store;
    }

    public String getVersion() {
        return this.version;
    }

    public String getButton1() {
        return this.button1;
    }

    public String getButton2() {
        return this.button2;
    }

    public long getOffset_show() {
        return (long) (this.offset_show * 1000);
    }
}
