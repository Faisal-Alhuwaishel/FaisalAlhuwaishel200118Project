package com.example.faisalalhuwaishel200118project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView img = (ImageView) findViewById(R.id.imgWeatherMain);
        TextView temp = (TextView) findViewById(R.id.txtTempMain);
        TextView humid = (TextView) findViewById(R.id.txtHumidMain);
        TextView city = (TextView) findViewById(R.id.txtCityMain);
        Weather.weather(img,temp,humid,city,MainActivity.this);
        Button firebase = (Button) findViewById(R.id.firebase);
        Button firebaseEdit = (Button) findViewById(R.id.btnFirebaseEdit);
        Button firebaseInsert = (Button) findViewById(R.id.btnFirebaseInsert);
        Button sql = (Button) findViewById(R.id.sql);
        Button sqlEdit = (Button) findViewById(R.id.sqlEdit);
        Button weatherAPI = (Button) findViewById(R.id.btnWeatherAPI);
        firebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FirebaseList.class));
            }
        });
        firebaseEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FirebaseEdit.class));
            }
        });
        firebaseInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FirebaseInsert.class));
            }
        });
        weatherAPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, WeatherEdit.class));
            }
        });
        sql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, sqlList.class));
            }
        });
        sqlEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, sqlEdit.class));
            }
        });

    }
}
