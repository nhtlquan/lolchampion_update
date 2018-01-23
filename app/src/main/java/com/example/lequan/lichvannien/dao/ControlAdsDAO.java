package com.example.lequan.lichvannien.dao;

import java.util.ArrayList;

public class ControlAdsDAO {
    AdsConfigDAO adsConfigDAO = new AdsConfigDAO();
    ArrayList<AdsCustomDAO> listadsCustomDAO = new ArrayList();

    public ControlAdsDAO(AdsConfigDAO adsConfigDAO, ArrayList<AdsCustomDAO> listadsCustomDAO) {
        this.adsConfigDAO = adsConfigDAO;
        this.listadsCustomDAO = listadsCustomDAO;
    }

    public AdsConfigDAO getAdsConfigDAO() {
        return this.adsConfigDAO;
    }

    public void setAdsConfigDAO(AdsConfigDAO adsConfigDAO) {
        this.adsConfigDAO = adsConfigDAO;
    }

    public ArrayList<AdsCustomDAO> getListadsCustomDAO() {
        return this.listadsCustomDAO;
    }

    public void setListadsCustomDAO(ArrayList<AdsCustomDAO> listadsCustomDAO) {
        this.listadsCustomDAO = listadsCustomDAO;
    }
}
