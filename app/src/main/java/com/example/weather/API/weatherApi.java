package com.example.weather.API;

public class weatherApi {

    private String name;
   public coord coord;
   wind wind;
   sys sys;

   public  main main = null;

    public weatherApi(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}


   class coord {
        private int lon, lat;


        public coord() {
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

    class main{
        private int temp , humidity;

        public main(int temp, int humidity) {
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

    class  wind{
        private int Speed;

        public wind(int speed) {
            Speed = speed;
        }

        public int getSpeed() {
            return Speed;
        }
    }


    class sys{
        private String country;

        public sys(String country) {
            this.country = country;
        }

        public String getCountry() {
            return country;
        }
    }
