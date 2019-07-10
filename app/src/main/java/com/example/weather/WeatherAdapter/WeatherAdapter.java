package com.example.weather.WeatherAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.weather.R;

import java.util.ArrayList;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {
    private ArrayList<weatherApi> weatherApis;


    private Context context;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int itemView) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View inflatedview = inflater.inflate(R.layout.tab1 , parent  , false);
        return new ViewHolder(inflatedview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        weatherApi current =  weatherApis.get(position);

        holder.temp.setText(current.main.getTemp());
        holder.city.setText(current.getName());
        holder.humiditytemp.setText(current.main.getHumidity());
        holder.wind.setText(current.wind.getSpeed());

    }


    @Override
    public int getItemCount() {
        return weatherApis.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView temp , humiditytemp , wind , city ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

city = itemView.findViewById(R.id.city);
            temp = itemView.findViewById(R.id.temp);
            humiditytemp = itemView.findViewById(R.id.humiditytemp);
            wind = itemView.findViewById(R.id.wind);
        }
    }
}
