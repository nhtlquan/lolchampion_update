package com.example.lequan.lichvannien.base.dao;

import android.content.Context;
import com.example.lequan.lichvannien.base.utils.BaseUtils;
import com.example.lequan.lichvannien.base.utils.Log;
import java.io.Serializable;
import java.util.ArrayList;

public class BaseConfig {
    private ads_network_new ads_network_new = new ads_network_new();
    private config_ads config_ads = new config_ads();
    private key key = new key();
    private ArrayList<more_apps> more_apps = new ArrayList();
    private ArrayList<shortcut_dynamic> shortcut_dynamic = new ArrayList();
    private thumbnail_app_lich thumbnail_app_lich = new thumbnail_app_lich();
    private thumnail_config thumnail_config = new thumnail_config();
    private Update update = new Update();

    public class Update {
        String description = "";
        int offset_show = 0;
        String packagename = "";
        int status = 0;
        String title = "";
        String type = "";
        String url_store = "";
        String version = "";

        public int getStatus() {
            return this.status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getOffset_show() {
            return this.offset_show;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
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

        public void setType(String type) {
            this.type = type;
        }

        public String getType() {
            return this.type;
        }

        public String getVersion() {
            return this.version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getPackagename() {
            return this.packagename;
        }
    }

    public class ads_network_new {
        String banner = "admob";
        String popup = "admob";
        String thumbai = "admob";

        public String getBanner() {
            return this.banner;
        }

        public String getPopup() {
            return this.popup;
        }

        public String getThumbai() {
            return this.thumbai;
        }
    }

    public class config_ads {
        int offset_time_show_popup = 150;
        int time_hidden_to_click_banner = 0;
        int time_hidden_to_click_popup = 0;
        int time_start_show_popup = 15;

        public int getOffset_time_show_popup() {
            return this.offset_time_show_popup;
        }

        public void setOffset_time_show_popup(int offset_time_show_popup) {
            this.offset_time_show_popup = offset_time_show_popup;
        }

        public int getTime_hidden_to_click_banner() {
            return this.time_hidden_to_click_banner;
        }

        public int getTime_hidden_to_click_popup() {
            return this.time_hidden_to_click_popup;
        }

        public int getTime_start_show_popup() {
            return this.time_start_show_popup;
        }

        public void setTime_start_show_popup(int time_start_show_popup) {
            this.time_start_show_popup = time_start_show_popup;
        }
    }

    public class key {
        admob admob = new admob();
        facebook facebook = new facebook();

        public class admob {
            String appid = "ca-app-pub-3602251130565338~5768146603";
            String banner = "ca-app-pub-9849209325477495/6152469966";
            String popup = "ca-app-pub-9849209325477495/9105936362";

            public String getAppid() {
                return this.appid;
            }

            public String getBanner() {
                return this.banner;
            }

            public String getPopup() {
                return this.popup;
            }
        }

        public class facebook {
            String banner = "999215223462174_1209853995731628";
            String popup = "999215223462174_1174872629229765";
            String thumbai = "999215223462174_1209854065731621";

            public String getPopup() {
                return this.popup;
            }

            public String getBanner() {
                return this.banner;
            }

            public String getThumbai() {
                return this.thumbai;
            }
        }

        public admob getAdmob() {
            return this.admob;
        }

        public facebook getFacebook() {
            return this.facebook;
        }
    }

    public class more_apps implements Serializable {
        String banner = "";
        String description = "";
        String icon = "";
        String packagename = "";
        String popup = "";
        String thumbai = "";
        String title = "";
        String type = "";
        String url_store = "";

        public String getPopup() {
            if (this.popup == null || this.popup.equals("")) {
                return this.thumbai;
            }
            return this.popup;
        }

        public String getTitle() {
            return this.title;
        }

        public String getDescription() {
            return this.description;
        }

        public String getPackagename() {
            return this.packagename;
        }

        public String getType() {
            return this.type;
        }

        public String getIcon() {
            return this.icon;
        }

        public String getUrl_store() {
            return this.url_store;
        }

        public String getBanner() {
            return this.banner;
        }

        public String getThumbai() {
            return this.thumbai;
        }
    }

    public class shortcut_dynamic {
        String icon = "";
        int id = -1;
        String link = "";
        int loop = 0;
        String name_shotcut = "";
        String package_name = "";

        public int getId() {
            return this.id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIcon() {
            return this.icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getName_shotcut() {
            return this.name_shotcut;
        }

        public void setName_shotcut(String name_shotcut) {
            this.name_shotcut = name_shotcut;
        }

        public String getLink() {
            return this.link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getPackage_name() {
            return this.package_name;
        }

        public void setPackage_name(String package_name) {
            this.package_name = package_name;
        }

        public int getLoop() {
            return this.loop;
        }
    }

    public class thumbnail_app_lich {
        int offset_show_thumbnail = 5;
        int random_show_thumbai_hdv = 0;
        int start_show_thumbnail = 1;

        public void setStart_show_thumbnail(int start_show_thumbnail) {
            this.start_show_thumbnail = start_show_thumbnail;
        }

        public int getStart_show_thumbnail() {
            return this.start_show_thumbnail;
        }

        public int getOffset_show_thumbnail() {
            return this.offset_show_thumbnail;
        }
    }

    public class thumnail_config {
        int is_show_popup_chuyen_tab = 0;
        int is_show_thumbnail_lich_ngay = 0;
        int offset_show_thumbai_detail_news = 10;
        int offset_show_thumbai_end_detail_news = 8;
        int offset_video_to_show_thumbai = 6;
        int random_show_popup_hdv = 20;
        int random_show_thumbai_detail_hdv = 50;
        int random_show_thumbai_hdv = 0;
        int start_show_thumbai_detail_news = 4;
        int start_video_show_thumbai = 6;

        public int getIs_show_popup_chuyen_tab() {
            return this.is_show_popup_chuyen_tab;
        }

        public int getIs_show_thumbnail_lich_ngay() {
            return this.is_show_thumbnail_lich_ngay;
        }

        public void setIs_show_popup_chuyen_tab(int is_show_popup_chuyen_tab) {
            this.is_show_popup_chuyen_tab = is_show_popup_chuyen_tab;
        }

        public void setIs_show_thumbnail_lich_ngay(int is_show_thumbnail_lich_ngay) {
            this.is_show_thumbnail_lich_ngay = is_show_thumbnail_lich_ngay;
        }

        public int getRandom_show_popup_hdv() {
            return this.random_show_popup_hdv;
        }

        public int getOffset_video_to_show_thumbai() {
            if (this.offset_video_to_show_thumbai == 0) {
                return 6;
            }
            return this.offset_video_to_show_thumbai;
        }

        public int getRandom_show_thumbai_hdv() {
            return this.random_show_thumbai_hdv;
        }

        public int getRandom_show_thumbai_detail_hdv() {
            return this.random_show_thumbai_detail_hdv;
        }

        public int getStart_video_show_thumbai() {
            if (this.start_video_show_thumbai == 0) {
                return 6;
            }
            return this.start_video_show_thumbai;
        }

        public int getOffset_show_thumbai_detail_news() {
            if (this.offset_show_thumbai_detail_news == 0) {
                return 10;
            }
            return this.offset_show_thumbai_detail_news;
        }

        public int getStart_show_thumbai_detail_news() {
            if (this.start_show_thumbai_detail_news == 0) {
                return 4;
            }
            return this.start_show_thumbai_detail_news;
        }

        public int getOffset_show_thumbai_end_detail_news() {
            if (this.offset_show_thumbai_end_detail_news == 0) {
                return 8;
            }
            return this.offset_show_thumbai_end_detail_news;
        }

        public void setRandom_show_popup_hdv(int random_show_popup_hdv) {
            this.random_show_popup_hdv = random_show_popup_hdv;
        }

        public void setRandom_show_thumbai_hdv(int random_show_thumbai_hdv) {
            this.random_show_thumbai_hdv = random_show_thumbai_hdv;
        }
    }

    public thumbnail_app_lich getThumbnail_app_lich() {
        return this.thumbnail_app_lich;
    }

    public void initOtherApps(Context context) {
        int i = 0;
        while (i < this.more_apps.size()) {
            try {
                if (BaseUtils.isInstalled(context, ((more_apps) this.more_apps.get(i)).getPackagename())) {
                    this.more_apps.remove(i);
                } else {
                    i++;
                }
            } catch (Exception e) {
                Log.m1447e("error initOtherApps: " + e.getMessage());
                return;
            }
        }
        i = 0;
        while (i < this.shortcut_dynamic.size()) {
            boolean isInstalled = false;
            for (String packageName : ((shortcut_dynamic) this.shortcut_dynamic.get(i)).getPackage_name().contains(",") ? ((shortcut_dynamic) this.shortcut_dynamic.get(i)).getPackage_name().split(",") : new String[]{((shortcut_dynamic) this.shortcut_dynamic.get(i)).getPackage_name()}) {
                if (BaseUtils.isInstalled(context, packageName)) {
                    isInstalled = true;
                    break;
                }
            }
            if (isInstalled) {
                this.shortcut_dynamic.remove(i);
            } else {
                i++;
            }
        }
    }

    public ArrayList<shortcut_dynamic> getShortcut_dynamic() {
        return this.shortcut_dynamic;
    }

    public Update getUpdate() {
        return this.update;
    }

    public key getKey() {
        return this.key;
    }

    public config_ads getConfig_ads() {
        return this.config_ads;
    }

    public ads_network_new getAds_network_new() {
        return this.ads_network_new;
    }

    public thumnail_config getThumnail_config() {
        return this.thumnail_config;
    }

    public ArrayList<more_apps> getMore_apps() {
        return this.more_apps;
    }
}
