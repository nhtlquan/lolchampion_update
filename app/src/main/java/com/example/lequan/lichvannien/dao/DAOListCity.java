package com.example.lequan.lichvannien.dao;

import java.util.ArrayList;

public class DAOListCity {
    ArrayList<DAOCity> listCity = new ArrayList();

    public class DAOCity {
        String cityCode;
        String cityName;

        public DAOCity(String cityName, String cityCode) {
            this.cityName = cityName;
            this.cityCode = cityCode;
        }

        public String getCityCode() {
            return this.cityCode;
        }

        public String getCityName() {
            return this.cityName;
        }
    }

    public DAOListCity() {
        this.listCity.add(new DAOCity("Hà Nội", "2347727"));
        this.listCity.add(new DAOCity("Hồ Chí Minh", "2347728"));
        this.listCity.add(new DAOCity("Hải Phòng", "2347707"));
        this.listCity.add(new DAOCity("Đà Nẵng", "20070085"));
        this.listCity.add(new DAOCity("Cần Thơ", "2347732"));
        this.listCity.add(new DAOCity("Thái Bình", "2347716"));
        this.listCity.add(new DAOCity("Bến Tre", "2347703"));
        this.listCity.add(new DAOCity("Cao Bằng", "2347704"));
        this.listCity.add(new DAOCity("Lai Châu", "2347708"));
        this.listCity.add(new DAOCity("Lâm Đồng", "2347709"));
        this.listCity.add(new DAOCity("Long An", "2347710"));
        this.listCity.add(new DAOCity("Quảng Nam", "2347711"));
        this.listCity.add(new DAOCity("Quảng Ninh", "2347712"));
        this.listCity.add(new DAOCity("Sơn La", "2347713"));
        this.listCity.add(new DAOCity("Tây Ninh", "2347714"));
        this.listCity.add(new DAOCity("Thanh Hóa", "2347715"));
        this.listCity.add(new DAOCity("Tiền Giang", "2347717"));
        this.listCity.add(new DAOCity("Lạng Sơn", "2347718"));
        this.listCity.add(new DAOCity("An Giang", "2347719"));
        this.listCity.add(new DAOCity("Đắk Lắk", "2347720"));
        this.listCity.add(new DAOCity("Đồng Nai", "2347721"));
        this.listCity.add(new DAOCity("Đồng Tháp", "2347722"));
        this.listCity.add(new DAOCity("Kiên Giang", "2347723"));
        this.listCity.add(new DAOCity("Bà Rịa - Vũng Tàu", "2347729"));
        this.listCity.add(new DAOCity("Bình Định", "2347730"));
        this.listCity.add(new DAOCity("Bình Thuận", "2347731"));
        this.listCity.add(new DAOCity("Gia Lai", "2347733"));
        this.listCity.add(new DAOCity("Hà Giang", "2347734"));
        this.listCity.add(new DAOCity("Hà Tây", "2347735"));
        this.listCity.add(new DAOCity("Hà Tĩnh", "2347736"));
        this.listCity.add(new DAOCity("Hòa Bình", "2347737"));
        this.listCity.add(new DAOCity("Khánh Hòa", "2347738"));
        this.listCity.add(new DAOCity("Lào Cai", "2347740"));
        this.listCity.add(new DAOCity("Hà Nam", "2347741"));
        this.listCity.add(new DAOCity("Nghệ An", "2347742"));
        this.listCity.add(new DAOCity("Ninh Bình", "2347743"));
        this.listCity.add(new DAOCity("Ninh Thuận", "2347744"));
        this.listCity.add(new DAOCity("Phú Yên", "2347745"));
        this.listCity.add(new DAOCity("Quảng Bình", "2347746"));
        this.listCity.add(new DAOCity("Quảng Trị", "2347747"));
        this.listCity.add(new DAOCity("Sóc Trăng", "2347748"));
        this.listCity.add(new DAOCity("Thừa Thiên Huế", "2347749"));
        this.listCity.add(new DAOCity("Trà Vinh", "2347750"));
        this.listCity.add(new DAOCity("Tuyên Quang", "2347751"));
        this.listCity.add(new DAOCity("Vĩnh Long", "2347752"));
        this.listCity.add(new DAOCity("Yên Bái", "2347753"));
        this.listCity.add(new DAOCity("Kon Tum", "20070076"));
        this.listCity.add(new DAOCity("Quảng Ngãi", "20070077"));
        this.listCity.add(new DAOCity("Bình Dương", "20070078"));
        this.listCity.add(new DAOCity("Hưng Yên", "20070079"));
        this.listCity.add(new DAOCity("Hải Dương", "20070080"));
        this.listCity.add(new DAOCity("Bạc Liêu", "20070081"));
        this.listCity.add(new DAOCity("Cà Mau", "20070082"));
        this.listCity.add(new DAOCity("Thái Nguyên", "20070083"));
        this.listCity.add(new DAOCity("Bắc Cạn", "20070084"));
        this.listCity.add(new DAOCity("Bình Phước", "20070086"));
        this.listCity.add(new DAOCity("Bắc Giang", "20070087"));
        this.listCity.add(new DAOCity("Bắc Ninh", "20070088"));
        this.listCity.add(new DAOCity("Nam Định", "20070089"));
        this.listCity.add(new DAOCity("Vĩnh Phúc", "20070090"));
        this.listCity.add(new DAOCity("Phú Thọ", "20070091"));
        this.listCity.add(new DAOCity("Điện Biên", "28301718"));
        this.listCity.add(new DAOCity("Đắk Nông", "28301719"));
        this.listCity.add(new DAOCity("Hậu Giang", "28301720"));
    }

    public ArrayList<DAOCity> getListCity() {
        return this.listCity;
    }

    public String getCityCode(int position) {
        return ((DAOCity) this.listCity.get(position)).getCityCode();
    }

    public String getCityName(String cityCode) {
        for (int i = 0; i < this.listCity.size(); i++) {
            if (((DAOCity) this.listCity.get(i)).getCityCode().equals(cityCode)) {
                return ((DAOCity) this.listCity.get(i)).getCityName();
            }
        }
        return "";
    }

    public int getIndex(String cityCode) {
        for (int i = 0; i < this.listCity.size(); i++) {
            if (((DAOCity) this.listCity.get(i)).getCityCode().equals(cityCode)) {
                return i;
            }
        }
        return 0;
    }

    public String[] getListName() {
        String[] listName = new String[this.listCity.size()];
        for (int i = 0; i < listName.length; i++) {
            listName[i] = ((DAOCity) this.listCity.get(i)).getCityName();
        }
        return listName;
    }
}
