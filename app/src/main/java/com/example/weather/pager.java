package com.example.weather;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.weather.Details.Tab3;
import com.example.weather.WeatherAdapter.Tab1;
import com.example.weather.Weatherdetails.Tab2;

public class pager extends FragmentStatePagerAdapter {

    int count;
    public pager(FragmentManager fm , int count) {
        super(fm);

        this.count = count;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Tab1 tab1 = new Tab1();
                return tab1;
            case 1:
                Tab2 tab2 = new Tab2();
                return tab2;
            case 2:
                Tab3 tab3 = new Tab3();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return count;
    }
}
