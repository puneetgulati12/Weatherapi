package com.example.weather.Weatherdetails;

import java.util.List;

public class Api {
    public Datalist ma;

    public List<list> lists;
}
class list{
    public String dt_txt;
    public list(String dt_txt) {
        this.dt_txt = dt_txt;
    }



    public String getDt_txt() {
        return dt_txt;
    }

    public Datalist main;
}
class Datalist{
    public float temp;

    public Datalist(float temp) {
        this.temp = temp;
    }

    public float getTemp() {
        return temp;
    }
}
