package com.example.rentalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CityView extends AppCompatActivity implements OnMapReadyCallback {
    private Map<LatLng, String> propMarkers = new HashMap<>();
    private GoogleMap cityMap;
    String cityName;
    double cityLat;
    double cityLong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_view);
        getSupportActionBar().hide();

        setupCitySearch();
        setupGoogleMaps();
    }

    public void setupCitySearch(){
        Bundle extras = getIntent().getExtras();

        if (extras != null){
            cityName = extras.getString("city");
        }

        double[] locArr = getLocFromName(cityName);
        cityLat = locArr[0];
        cityLong = locArr[1];
    }

    public void setupGoogleMaps(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.g_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Button seeDetails = findViewById(R.id.btnSeeDetails);
        TextView propName = findViewById(R.id.txtPropName);
        ImageView container = findViewById(R.id.imgPropContainer);
        cityMap = googleMap;

        LatLng city = new LatLng(cityLat, cityLong);
        cityMap.moveCamera(CameraUpdateFactory.newLatLng(city));
        cityMap.setMinZoomPreference(11);
        cityMap.setMaxZoomPreference(18);

        cityMap.setOnMarkerClickListener(marker -> {
            propName.setText(propMarkers.get(marker.getPosition()));

            seeDetails.setVisibility(View.VISIBLE);
            propName.setVisibility(View.VISIBLE);
            container.setVisibility(View.VISIBLE);
            return false;
        });

        showPropsOnMap(cityMap);

        seeDetails.setVisibility(View.INVISIBLE);
        propName.setVisibility(View.INVISIBLE);
        container.setVisibility(View.INVISIBLE);
    }

    public void showPropsOnMap(GoogleMap cityMap){
        FetchCityProps fetchProps = new FetchCityProps();
        ArrayList<String> propList = fetchProps.addCalgaryPropData();

        for(String prop : propList){
            double[] locArr = getLocFromName(prop);
            LatLng city = new LatLng(locArr[0], locArr[1]);
            cityMap.addMarker(new MarkerOptions().position(city));

            propMarkers.put(city, prop);
        }
    }

    public void seeDetails(View v) {
        Intent page = new Intent(CityView.this, PropertyDetails.class);
        startActivity(page);
    }

    public void viewNextProp(){}

    public void viewPrevProp(){}

    public double[] getLocFromName(String location){
        double latitude = 0.0, longitude = 0.0;
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocationName(location, 5);
            latitude = addresses.get(0).getLatitude();
            longitude = addresses.get(0).getLongitude();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new double[]{latitude, longitude};
    }


}

