package com.example.lequan.lichvannien.model;

import com.google.gson.Gson;

public class FbUserProfile {
    public String birthday;
    public String email;
    public String first_name;
    public String gender;
    public String id;
    public String last_name;
    public Picture picture;

    public class Data {
        public boolean is_silhouette;
        public String url;

        public String toString() {
            return new Gson().toJson((Object) this);
        }
    }

    public class Picture {
        public Data data;

        public String toString() {
            return new Gson().toJson((Object) this);
        }
    }

    public String toString() {
        return new Gson().toJson((Object) this);
    }
}
