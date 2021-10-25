package com.weather.restapi;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WeatherDataService {
    Context ctx;

    WeatherDataService(Context c){
        ctx = c;
    }


    interface getCityId_CallBack{
        //self implemented call back it helps to work with async data in other languages we use async and await for this purpose in java we can use this trick or we can also use other libraries
        void onResponse(String cityId);
        void onError(String error);
    }

    void getCityId(String cityName,getCityId_CallBack callBack){
        String url = "https://www.metaweather.com/api/location/search/?query=" + cityName;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject firstObj = response.getJSONObject(0);
                    String CityId = firstObj.getString("woeid");
                    callBack.onResponse(CityId);
                    Toast.makeText(ctx, "CityId = " + CityId, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onError("Error " +error);
                Toast.makeText(ctx, "Error" + error, Toast.LENGTH_SHORT).show();
            }
        }
        );

        MySingleton.getInstance(ctx).addToRequestQueue(request);
    }


    interface getWeatherById_callback{
        void onResponse(List<WeatherReportModel> list);
        void onError(String error);
    }

    void getWeatherById(String id,getWeatherById_callback callback){
        String url = "https://www.metaweather.com/api/location/" + id;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                Toast.makeText(ctx, "response "+response, Toast.LENGTH_SHORT).show();
                try {
                    List<WeatherReportModel> list = new ArrayList<>();
                    JSONArray array =  response.getJSONArray("consolidated_weather");
                    JSONObject obj;
                    for(int i = 0;i<array.length();i++){
                        WeatherReportModel model = new WeatherReportModel();
                        obj = (JSONObject) array.get(i);
                        model.weather_state = obj.getString("weather_state_name");
                        model.current = obj.getString("the_temp");
                        model.high = obj.getString("max_temp");
                        model.low = obj.getString("min_temp");
                        model.wind_direction = obj.getString("wind_direction_compass");
                        model.date = obj.getString("applicable_date");
                        list.add(model);
                    }

                    callback.onResponse(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError("Error "+error);
            }
        });
        MySingleton.getInstance(ctx).addToRequestQueue(request);

    }

    void getWeatherByName(String name,getWeatherById_callback cb){
        getCityId(name, new getCityId_CallBack() {
            @Override
            public void onResponse(String cityId) {
                getWeatherById(cityId, new getWeatherById_callback() {
                    @Override
                    public void onResponse(List<WeatherReportModel> list) {
                        cb.onResponse(list);
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(ctx, "Error117 "+error, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(String error) {
                Toast.makeText(ctx, "Error124 "+error, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
