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

    public String getName() {
        return name;
    }


}


   class Cord {
        public int lon, lat;


        public Cord() {
            this.lon = lon;
            this.lat = lat;
        }

        public int getLon() {
            return lon;
        }

        public int getLat() {
            return lat;
        }
    }

    class Ma{
        public int temp , humidity;

        public Ma(int temp, int humidity) {
            this.temp = temp;
            this.humidity = humidity;
        }

        public int getTemp() {
            return temp;
        }

        public int getHumidity() {
            return humidity;
        }
    }

    class  Wynd{
        public int Speed;

        public Wynd(int speed) {
            Speed = speed;
        }

        public int getSpeed() {
            return Speed;
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
