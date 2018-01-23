package com.example.lequan.lichvannien.dao;

public class DAOWeather {
    String CodeId = "";
    String Date = "";
    String DayNumber = "";
    String DayText = "";
    String FullDate = "";
    String High = "";
    String Humidity = "";
    String Id = "";
    String LocationName = "";
    String Low = "";
    String Status = "";
    String Sunrise = "";
    String Sunset = "";
    String Temperature = "";
    String Time = "";
    String WeatherDate = "";
    String Wind = "";

    public String getId() {
        return this.Id;
    }

    public String getCodeId() {
        return this.CodeId;
    }

    public String getWeatherDate() {
        return this.WeatherDate;
    }

    public String getLocationName() {
        return this.LocationName;
    }

    public String getHumidity() {
        return this.Humidity;
    }

    public String getWind() {
        return this.Wind;
    }

    public String getSunset() {
        return this.Sunset;
    }

    public String getSunrise() {
        return this.Sunrise;
    }

    public String getTemperature() {
        return this.Temperature;
    }

    public String getHigh() {
        return this.High;
    }

    public String getLow() {
        return this.Low;
    }

    public String getStatus() {
        return this.Status;
    }

    public String getDayNumber() {
        return this.DayNumber;
    }

    public String getDayText() {
        return this.DayText;
    }

    public String getDate() {
        return this.Date;
    }

    public String getFullDate() {
        return this.FullDate;
    }

    public String getTime() {
        return this.Time;
    }
}
