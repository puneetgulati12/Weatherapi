package com.example.weather;

public class IconChanger {

    public static int getImageIcon(String weatherDescription){
        int weatherIcon ;
        switch(weatherDescription) {
            case "Thunderstorm":
                weatherIcon = R.drawable.thunderstrom;
                break;
            case "Rain":
                weatherIcon = R.drawable.cloudrain;
                break;
            case "Atmosphere":
                weatherIcon = R.drawable.ic_atmosphere;
                break;
            case "Clouds":
                weatherIcon = R.drawable.iccloud;
                break;
            default:
                weatherIcon = R.drawable.suncloud;
        }
        return weatherIcon;

    }

}

