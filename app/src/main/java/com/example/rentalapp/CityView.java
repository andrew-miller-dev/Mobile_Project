package com.example.rentalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CityView extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap cityMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_view);

        getSupportActionBar().hide();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.g_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        cityMap = googleMap;

        //seattle coordinates
        LatLng calgary = new LatLng(50.98647017296167, -114.05180901726462);
        cityMap.addMarker(new MarkerOptions().position(calgary).title("Calgary"));
        cityMap.moveCamera(CameraUpdateFactory.newLatLng(calgary));
        cityMap.setMinZoomPreference(11);
        cityMap.setMaxZoomPreference(18);
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