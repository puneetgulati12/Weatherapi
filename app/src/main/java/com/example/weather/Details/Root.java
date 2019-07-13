package com.example.weather.Details;

import java.util.List;

public class Root {
    public DataList daily;
}
class  DataList{
    public List<Data> data;
}
class Data {

 public    float temperatureMin;
 public    float temperatureMax;
  public   String icon;

    public Data(int temperatureMin, int temperatureMax, String icon) {
        this.temperatureMin = temperatureMin;
        this.temperatureMax = temperatureMax;
        this.icon = icon;
    }

}