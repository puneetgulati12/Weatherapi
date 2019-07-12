package com.example.weather.Details;

import java.util.ArrayList;

public class Root {
    public Ma main;

    public sity name;

    public String dt_txt;

    public Root(String dt_txt) {
        this.dt_txt = dt_txt;
    }

    public String dt_txt() {
        return dt_txt;
    }

    ArrayList<String> list = new ArrayList<String>();
}

class Ma{
    public float tempmin , tempmax;

    public Ma(float tempmin, float tempmax) {
        this.tempmin = tempmin;
        this.tempmax = tempmax;
    }

    public float getTempmin() {
        return tempmin;
    }

    public float getTempmax() {
        return tempmax;
    }
}

class  weather{
    ArrayList<String> weather = new ArrayList<>();

}
class sity{
    public String name;

    public sity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
