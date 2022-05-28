package com.example.faisalalhuwaishel200118project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

public class WeatherEdit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_edit);
        ImageView img = (ImageView) findViewById(R.id.imgWeatherWEdit);
        TextView temp = (TextView) findViewById(R.id.txtTempWEdit);
        TextView humid = (TextView) findViewById(R.id.txtHumidWEdit);
        TextView city = (TextView) findViewById(R.id.txtCityWEdit);
        Weather.weather(img,temp,humid,city,WeatherEdit.this);
        EditText cityInput = (EditText) findViewById(R.id.cityInput);
        Button editCity = (Button) findViewById(R.id.editCity);
        editCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!cityInput.getText().toString().isEmpty() &&
                        !(cityInput.getText().toString().charAt(0) == ' ')) {
                    JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.GET, Weather.newUrl(cityInput.getText().toString()), null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Weather.changeUrl(cityInput.getText().toString());
                            Weather.weather(img, temp, humid, city, WeatherEdit.this);
                            Toasty.success(getBaseContext(), "City Successfully change", Toast.LENGTH_SHORT,true).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toasty.error(getBaseContext(), "City Name Invalid",
                                    Toast.LENGTH_SHORT, true).show();

                        }
                    });
                    RequestQueue queue = Volley.newRequestQueue(WeatherEdit.this);
                    queue.add(jsonObj);
                }
                else {
                    Toasty.error(getBaseContext(), "Enter a Valid City",
                            Toast.LENGTH_SHORT, true).show();
                }
            }
        });
    }
}