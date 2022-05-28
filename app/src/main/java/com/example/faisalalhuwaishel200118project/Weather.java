package com.example.faisalalhuwaishel200118project;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;


public class Weather {
    public static String weatherWebserviceURL =
            "http://api.openweathermap.org/data/2.5/weather?q=Berlin&appid=c4e0425479a004ea8e0751e141be2152&units=metric";
    public static String city = "Berlin";

    public static void weather (ImageView img, TextView temperature, TextView humidity, TextView cityText, Context context){
        JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.GET, weatherWebserviceURL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String town = response.getString("main");
                            JSONObject jsonMain = response.getJSONObject("main");
                            double temp = jsonMain.getDouble("temp");
                            double hum = jsonMain.getDouble("humidity");
                            temperature.setText(String.valueOf(temp)+"°C");
                            humidity.setText("Humidity: "+String.valueOf((int)hum)+"%");
                            cityText.setText(city);
                            JSONArray jsonWeather = response.getJSONArray("weather");
                            JSONObject jsonWeatherObject = jsonWeather.getJSONObject(0);
                            String weather = jsonWeatherObject.getString("main");
                            if(weather.equals("Clear")){
                                Glide.with(context)
                                        .load("https://i.ibb.co/tQDKp86/clear.png")
                                        .into(img);
                            }
                            else if(weather.equals("Cloudy") || weather.equals("Clouds")){
                                Glide.with(context)
                                        .load("https://i.ibb.co/6bXZWnL/cloudy.png")
                                        .into(img);
                            }
                            else if(weather.equals("Rainy") || weather.equals("Rain")){
                                Glide.with(context)
                                        .load("https://i.ibb.co/drHB04q/rainy.png")
                                        .into(img);
                            }
                            else if(weather.equals("Snow")){
                                Glide.with(context)
                                        .load("https://i.ibb.co/4dGy4r3/snow.png")
                                        .into(img);
                            }

                        } catch (JSONException e){
                            e.printStackTrace();
                            Log.e("Weather error",e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Log.e("Weather","Error retrieving the URL");
                    }
                });
        JsonObjectRequest jsonObj1 = new JsonObjectRequest(Request.Method.GET, weatherWebserviceURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonObj);
    }


    public static String newUrl(String newCity){
        String newUrl= "http://api.openweathermap.org/data/2.5/weather?q="+newCity+"&appid=c4e0425479a004ea8e0751e141be2152&units=metric";
        city = newCity;
        return newUrl;
    }
    public static void changeUrl(String newCity){
        weatherWebserviceURL= "http://api.openweathermap.org/data/2.5/weather?q="+newCity+"&appid=c4e0425479a004ea8e0751e141be2152&units=metric";
        city = newCity;
    }
}
