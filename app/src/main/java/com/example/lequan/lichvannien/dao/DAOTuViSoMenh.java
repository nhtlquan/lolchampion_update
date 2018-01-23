package com.example.lequan.lichvannien.dao;

public class DAOTuViSoMenh {
    String name = "";
    int type = 1;

    public DAOTuViSoMenh(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public int getType() {
        return this.type;
    }
}
