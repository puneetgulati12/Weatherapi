package com.example.weather.Details;

import java.util.List;

public class Root {
 public listt.Ma main;

    public sity name;

    public String dt_txt;

    public Root(String dt_txt) {
        this.dt_txt = dt_txt;
    }

    public String dt_txt() {
        return dt_txt;
    }


   List<listt> list;
}


class listt{


    class Ma {
        public float temp_min, temp_max;

        public Ma(float tempmin, float tempmax) {
            this.temp_min = tempmin;
            this.temp_max = tempmax;
        }

        public float getTemp_min() {
            return temp_min;
        }

        public float getTemp_max() {
            return temp_max;
        }
    }
}

class sity {
    public String name;

    public sity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
