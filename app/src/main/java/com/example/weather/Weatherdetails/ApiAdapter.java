package com.example.weather.Weatherdetails;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.weather.R;

import java.util.List;

public class ApiAdapter extends RecyclerView.Adapter<ApiAdapter.ViewHolder> {

    private List<lists> roots;
    private Context context;

    public ApiAdapter(List<lists> mylist) {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View inflateview = inflater.inflate(R.layout.item_row , parent , false);
        return new ViewHolder(inflateview) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        lists current = roots.get(position);

        holder.time.setText(String.valueOf( current.getDt_txt()));
        holder.temp.setText(String.valueOf(current.main.getTemp()));

    }

    @Override
    public int getItemCount() {
        return roots.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView time , temp;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time  = itemView.findViewById(R.id.time2);
            temp  = itemView.findViewById(R.id.tempitem);

        }
    }
}
