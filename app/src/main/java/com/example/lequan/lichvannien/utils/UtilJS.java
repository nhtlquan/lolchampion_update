package com.example.lequan.lichvannien.utils;

import com.example.lequan.lichvannien.base.utils.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class UtilJS {
    public Document getJsoup(String html) {
        try {
            return Jsoup.parse(html);
        } catch (Exception e) {
            Log.m1447e("error get Jsoup: " + e.getMessage());
            return Jsoup.parse("");
        }
    }
}
