package com.example.weather.Details;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weather.R;

import java.util.List;

public class RootAdapter extends RecyclerView.Adapter<RootAdapter.ViewHolder> {

    private List<Data> roots;
    private Context context;

    public RootAdapter(List<Data> list, Context context){
        roots=list;
        this.context = context;
    }

    public RootAdapter(List<Data> mylist) {
        roots = mylist;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int itemview) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View inflatedview = inflater.inflate(R.layout.item_row1 , parent , false);
        return new ViewHolder(inflatedview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Data current = roots.get(position);

        holder.tempmax.setText(String.valueOf(current.temperatureMax));
        holder.tempmin.setText(String.valueOf(current.temperatureMin));

        if (current.icon.matches("partly-cloudy-day")){
            holder.imageweather.setImageResource(R.drawable.suncloud);
        }
        else if (current.icon.matches("rain")){
            holder.imageweather.setImageResource(R.drawable.cloudrain);
        }else if (current.icon.matches("clear-day")){
            holder.imageweather.setImageResource(R.drawable.dayclear);
        }else if (current.icon.matches("cloudy-day")){
            holder.imageweather.setImageResource(R.drawable.cloudy);
        }
        holder.con.setText(current.icon);
//        String weatherDescription =
//        Picasso.get().load(IconChanger.getImageIcon(weatherDescription)).into(imageweather);


    }

    @Override
    public int getItemCount() {
        return roots.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

public TextView tempmax , tempmin , con ;
        public ImageView imageweather;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageweather = itemView.findViewById(R.id.weatherimage);
            con = itemView.findViewById(R.id.desc);
            tempmax = itemView.findViewById(R.id.tempmax);
            tempmin = itemView.findViewById(R.id.tempmin);
        }
    }

}
