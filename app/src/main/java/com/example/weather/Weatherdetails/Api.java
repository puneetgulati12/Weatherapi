package com.example.weather.Weatherdetails;

public class Api {


    public lists[] list ;

}
class lists {
    public String dt_txt;
    public lists(String dt_txt) {
        this.dt_txt = dt_txt;
    }



    public String getDt_txt() {
        return dt_txt;
    }

    public Datalist main;
    public weather[] weather;

}
class Datalist {
    public float temp;

    public Datalist(float temp) {
        this.temp = temp;
    }

    public float getTemp() {
        return temp;
    }


}


    class weather{
        public String main;

        public weather(String main) {
            this.main = main;
        }

        public String getMain() {
            return main;
        }
    }


