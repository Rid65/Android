package com.geekbrains.weather;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private List<WeatherItem> dataSource;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView date;
        public TextView temperature;
        public ImageView picture;

        public ViewHolder(View v) {
            super(v);
            date = v.findViewById(R.id.tv_date);
            picture = v.findViewById(R.id.weather_icon);
            temperature = v.findViewById(R.id.tv_temperature);
        }
    }

    public WeatherAdapter(List<WeatherItem> dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WeatherItem item = dataSource.get(position);
        holder.date.setText(item.getDate());
        holder.temperature.setText(item.getTemperature());
        holder.picture.setImageResource(item.getImage());
    }

    @NonNull
    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.future_weather_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }
}
