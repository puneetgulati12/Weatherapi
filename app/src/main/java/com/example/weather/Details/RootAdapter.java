package com.example.weather.Details;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.weather.R;

import java.util.ArrayList;

public class RootAdapter extends RecyclerView.Adapter<RootAdapter.ViewHolder> {

    private ArrayList<Root> roots;
    private Context context;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int itemview) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View inflatedview = inflater.inflate(R.layout.item_row1 , parent , false);
        return new ViewHolder(inflatedview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Root current = roots.get(position);

        holder.tempmax.setText(String.valueOf(current.main.getTempmax()));
        holder.tempmin.setText(String.valueOf(current.main.getTempmin()));
        holder.name.setText(current.name.getName());


    }

    @Override
    public int getItemCount() {
        return roots.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

private TextView tempmax , tempmin , name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.city);
            tempmax = itemView.findViewById(R.id.tempmax);
            tempmin = itemView.findViewById(R.id.tempmin);
        }
    }
}
