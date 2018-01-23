package com.example.lequan.lichvannien.model;

import com.google.gson.Gson;
import java.util.List;

public class Weather {
    public String copyright;
    public Currentconditions currentconditions;
    public Forecast forecast;
    public String use;
    public String xmlns;

    public class Currentconditions {
        public String humidity;
        public int temperature;
        public int visibility;

        public String toString() {
            return new Gson().toJson((Object) this);
        }
    }

    public class Day {
        public Daytime daytime;
        public Nighttime nighttime;
        public String sunset;

        public String toString() {
            return new Gson().toJson((Object) this);
        }
    }

    public class Daytime {
        public int hightemperature;
        public int lowtemperature;
        public float rainamount;
        public String winddirection;

        public String toString() {
            return new Gson().toJson((Object) this);
        }
    }

    public class Forecast {
        public List<Day> day = null;

        public String toString() {
            return new Gson().toJson((Object) this);
        }
    }

    public class Nighttime {
        public int hightemperature;
        public int lowtemperature;
        public float rainamount;

        public String toString() {
            return new Gson().toJson((Object) this);
        }
    }

    public String toString() {
        return new Gson().toJson((Object) this);
    }
}
