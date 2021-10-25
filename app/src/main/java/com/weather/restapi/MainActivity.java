package com.weather.restapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.weather.restapi.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        WeatherDataService wds = new WeatherDataService(MainActivity.this);

        binding.getCityId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                wds.getCityId(binding.et.getText().toString(), new WeatherDataService.getCityId_CallBack() {
                    @Override
                    public void onResponse(String cityId) {
                        Toast.makeText(MainActivity.this, "City id from call back = "+cityId, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        binding.getById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wds.getWeatherById(binding.et.getText().toString(), new WeatherDataService.getWeatherById_callback() {
                    @Override
                    public void onResponse(List<WeatherReportModel> list) {
                        Toast.makeText(MainActivity.this, "Weather list size is  = "+list.size(), Toast.LENGTH_SHORT).show();
                        binding.list.setAdapter(new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,list));
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        binding.getByName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wds.getWeatherByName(binding.et.getText().toString(), new WeatherDataService.getWeatherById_callback() {
                    @Override
                    public void onResponse(List<WeatherReportModel> list) {
                        binding.list.setAdapter(new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,list));
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(MainActivity.this, "Error89: "+error, Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });


    }
}