package com.example.apiweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apiweather.adapter.WeatherAdapter;
import com.example.apiweather.model.Weather;
import com.example.apiweather.network.WeatherAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView tvCity, tvTemp, tvStatus;
    RecyclerView rvWeather;
    List<Weather> weatherList;
    WeatherAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCity = findViewById(R.id.tvCity);
        tvTemp = findViewById(R.id.tvTemp);
        tvStatus = findViewById(R.id.tvStatus);
        rvWeather = findViewById(R.id.rvWeather);

        getHours();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        rvWeather.setLayoutManager(layoutManager);

        weatherList = new ArrayList<>();
//        adapter = new WeatherAdapter(MainActivity.this, weatherList);
    }

    private void getHours() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WeatherAPI.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherAPI weatherAPI = retrofit.create(WeatherAPI.class);
        weatherAPI.getHourWeather().enqueue(new Callback<List<Weather>>() {
            @Override
            public void onResponse(Call<List<Weather>> call, Response<List<Weather>> response) {
                if(response.body() == null) {
                    return;
                }
                List<Weather> weathers = response.body();
                adapter = new WeatherAdapter(MainActivity.this, weathers);
                rvWeather.setAdapter(adapter);

                Weather weather = weathers.get(0);
                tvTemp.setText(weather.getTemperature().getValue() + "°");
                tvStatus.setText(weather.getIconPhrase());
            }

            @Override
            public void onFailure(Call<List<Weather>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });
    }
}