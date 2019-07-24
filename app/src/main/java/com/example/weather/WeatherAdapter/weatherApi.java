package com.example.weather.WeatherAdapter;

public class weatherApi {

    public String name;
   public Cord coord;
   public  Wynd wind;
  public Sis sys;

   public  Ma main = null;


    public weatherApi(String name) {
        this.name = name;
    }

    public String getname() {
        return name;
    }

    public weather[] weather;
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

   class Cord {
        public float lon, lat;


        public Cord() {
            this.lon = lon;
            this.lat = lat;
        }

        public float getLon() {
            return lon;
        }

        public float getLat() {
            return lat;
        }
    }

    class Ma{
        public float temp , humidity;
        public Ma(float temp, float humidity) {
            this.temp = temp;
            this.humidity = humidity;
        }

        public float getTemp() {
            return temp;
        }

        public float getHumidity() {
            return humidity;
        }
    }

    class  Wynd{
        public float speed;

        public Wynd(float speed) {
            this.speed = speed;
        }

        public float getSpeed() {
            return speed;
        }
    }


    class Sis{
        public String country;

        public Sis(String country) {
            this.country = country;
        }

        public String getCountry() {
            return country;
        }
    }
