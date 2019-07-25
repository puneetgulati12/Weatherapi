package com.example.weather.Weatherdetails;

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

public class ApiAdapter extends RecyclerView.Adapter<ApiAdapter.ViewHolder> {

    private List<lists> roots;
    private Context context;

    public ApiAdapter(List<lists> mylist, Context context) {
//        int i=0;
//
//        while (roots.get(i).getDt_txt().substring(s.indexOf(' ') + 1))
        roots = mylist;
        this.context = context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View inflateview = inflater.inflate(R.layout.item_row, parent, false);
        return new ViewHolder(inflateview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        lists current = roots.get(position);

        String s = current.getDt_txt();
        String a = s.substring(s.indexOf(' ') + 1);
        holder.time.setText(a.substring(0, a.indexOf(":")));
        holder.temp.setText(String.valueOf(current.main.getTemp()) + "°F");
        if (current.weather[0].getMain().matches("partly-cloudy-day")){
            holder.imageView.setImageResource(R.drawable.suncloud);
        }
        else if (current.weather[0].getMain().matches("Rain")){
            holder.imageView.setImageResource(R.drawable.iccloudrain);
        }else if (current.weather[0].getMain().matches("Clear")){
            holder.imageView.setImageResource(R.drawable.dayclear);
        }else if (current.weather[0].getMain().matches("Clouds")){
            holder.imageView.setImageResource(R.drawable.iccloud);
        }



    }

    @Override
    public int getItemCount() {
        return roots.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView time, temp;
        private ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time2);
            temp = itemView.findViewById(R.id.tempitem);
            imageView = itemView.findViewById(R.id.iconimages);

        }
    }
}
