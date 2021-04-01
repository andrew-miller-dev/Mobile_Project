package com.example.rentalapp;

// Google Maps API Key:
// AIzaSyBypliw8O2xwVrcT_LoAUx9YFvuYjtvKjs

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
    }

    public void searchCity(View v){
        Intent page = new Intent(MainActivity.this, CityView.class);
        startActivity(page);
    }

    public void requestLocationShare(){}
}