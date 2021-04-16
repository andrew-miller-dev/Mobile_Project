package com.example.rentalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rentalapp.Helpers.ConvertResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.rentalapp.Helpers.ConvertResponse.changeDetailsVisibility;
import static com.example.rentalapp.Helpers.ConvertResponse.getLocFromName;

public class CityView extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap cityMap;
    private Map<LatLng, String> propMarkers = new HashMap<>();
    private String city;
    private String response;
    private String currentAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_view);
        getSupportActionBar().hide();
        setupCitySearch();
        setupGoogleMaps();
    }

    //helper method that gets all data passed from previous pages API call for google maps
    private void setupCitySearch(){
        Bundle extras = getIntent().getExtras();

        if (extras != null){
            response = extras.getString("response");
            city = extras.getString("city");
        }
    }

    // setup map fragments so that google maps can display on our component
    private void setupGoogleMaps(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.g_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        cityMap = googleMap;
        TextView propName = findViewById(R.id.txtPropName);
        ImageView container = findViewById(R.id.imgPropContainer);
        Button seeDetails = findViewById(R.id.btnSeeDetails);
        // make prop detail box not visible by default unless prop marker is clicked
        changeDetailsVisibility(false, propName, container, seeDetails);
        // setup google maps camera position
        setupMapCamera();
        // apply property addresses as google maps markers
        showPropsOnMap(cityMap);

        cityMap.setOnMarkerClickListener(marker -> {
            // detail box prop name display the address of the marker clicked
            propName.setText(propMarkers.get(marker.getPosition()));
            currentAddress = propName.getText().toString();
            // make prop detail box visible when prop marker is clicked
            changeDetailsVisibility(true, propName, container, seeDetails);
            return false;
        });
    }

    private void setupMapCamera(){
        // get searched city lat and long and set camera to that position
        double[] locArr = getLocFromName(city, this);
        LatLng city = new LatLng(locArr[0], locArr[1]);
        cityMap.moveCamera(CameraUpdateFactory.newLatLng(city));
        // zoom for better view of typical sized metropolitan area
        cityMap.setMinZoomPreference(11);
        cityMap.setMaxZoomPreference(18);
    }

    private void showPropsOnMap(GoogleMap cityMap){
        ArrayList<String> propList = ConvertResponse.getPropAddressList(response);
        // loop through propList and convert address to lat and long saved in locArr
        for(String address : propList){
            double[] locArr = getLocFromName(address, this);
            LatLng locCoordinates = new LatLng(locArr[0], locArr[1]);
            //add marker on map with no lat and long from prop address
            cityMap.addMarker(new MarkerOptions().position(locCoordinates));
            propMarkers.put(locCoordinates, address);
        }
    }

    // to change to property details page on user click
    public void seeDetails(View v) {
        Intent page = new Intent(CityView.this, PropertyDetails.class);
        page.putExtra("response", response);
        page.putExtra("address", currentAddress);
        startActivity(page);
    }
}

