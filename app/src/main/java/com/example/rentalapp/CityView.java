package com.example.rentalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CityView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_view);

        getSupportActionBar().hide();
    }

    public void seeDetails(View v){
        Intent page = new Intent(CityView.this, PropertyDetails.class);
        startActivity(page);
    }

    public void showCityMap(){}

    public void showPropsOnMap(){}

    public void showPropDetails(){}

    public void showMoreDetails(){}

    public void viewNextProp(){}

    public void viewPrevProp(){}
}