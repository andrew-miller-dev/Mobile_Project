package com.example.rentalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;

import java.util.List;
import java.util.Locale;

public class CityView extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap cityMap;
    public String cityName;
    public double cityLat;
    public double cityLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_view);
        getSupportActionBar().hide();

        setupCitySearch();
    }

    public void setupCitySearch(){
        Bundle extras = getIntent().getExtras();

        if (extras != null){
            cityName = extras.getString("city");
        }

        getLocFromName();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.g_map);
        mapFragment.getMapAsync(this);
    }

    public void getLocFromName(){
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocationName(cityName, 5);
            cityLat = addresses.get(0).getLatitude();
            cityLong = addresses.get(0).getLongitude();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        cityMap = googleMap;

        LatLng calgary = new LatLng(cityLat, cityLong);
        cityMap.addMarker(new MarkerOptions().position(calgary).title(cityName));
        cityMap.moveCamera(CameraUpdateFactory.newLatLng(calgary));
        cityMap.setMinZoomPreference(11);
        cityMap.setMaxZoomPreference(18);
    }

    public void seeDetails(View v){
        Intent page = new Intent(CityView.this, PropertyDetails.class);
        startActivity(page);
    }

    public void showPropsOnMap(){}

    public void showPropDetails(){}

    public void viewNextProp(){}

    public void viewPrevProp(){}
}

