package com.example.apiweather.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apiweather.R;
import com.example.apiweather.model.Weather;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter {
    private Activity activity;
    private List<Weather> weatherList;

    public WeatherAdapter(Activity activity, List<Weather> weatherList) {
        this.activity = activity;
        this.weatherList = weatherList;
    }

    public void reloadData(List<Weather> weatherList) {
        this.weatherList = weatherList;
        this.notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = activity.getLayoutInflater().inflate(R.layout.activity_list_weather, parent, false);
        HourHolder weatherHolder = new HourHolder(view);
        return weatherHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HourHolder weatherHolder = (HourHolder) holder;
        Weather weather = weatherList.get(position);
        weatherHolder.tvTime.setText(convertTime(weather.getDateTime()));
        weatherHolder.tvTemp.setText(weather.getTemperature().getValue() + "°");
        String url = "";
        if (weather.getWeatherIcon() < 10) {
         url ="https://developer.accuweather.com/sites/default/files/0" + weather.getWeatherIcon() + "-s.png";
        }else {
            url ="https://developer.accuweather.com/sites/default/files/" + weather.getWeatherIcon() + "-s.png";
        }
        Glide.with(activity).load(url).into(weatherHolder.ivIcon);
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    private class HourHolder extends RecyclerView.ViewHolder {
        TextView tvTime, tvTemp;
        ImageView ivIcon;

        public HourHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvTemp = itemView.findViewById(R.id.tvTemp);
            ivIcon = itemView.findViewById(R.id.ivIcon);
        }
    }

    public String convertTime(String inputTime) {
        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = null;
        try {
            date = inFormat.parse(inputTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outFormat = new SimpleDateFormat("ha");
        String goal = outFormat.format(date);
        return goal;
    }

}
