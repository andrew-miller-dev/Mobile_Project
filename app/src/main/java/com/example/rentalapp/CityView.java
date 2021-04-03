package com.example.rentalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CityView extends AppCompatActivity implements OnMapReadyCallback {
    private Map<LatLng, String> propMarkers = new HashMap<>();
    ArrayList<String> propList;
    private GoogleMap cityMap;
    String cityName;
    double cityLat;
    double cityLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_view);
        getSupportActionBar().hide();
        // setup the city search criteria data and the map to view it on
        setupCitySearch();
        setupGoogleMaps();
    }

    //helper method that gets all data passed from previous pages API call for google maps
    public void setupCitySearch(){
        Bundle extras = getIntent().getExtras();

        if (extras != null){
            cityName = extras.getString("city");
            double[] locArr = getLocFromName(cityName);
            cityLat = locArr[0]; cityLong = locArr[1];
            propList = extras.getStringArrayList("propList");
        }
    }

    // setup map fragments so that google maps can display on our component
    public void setupGoogleMaps(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.g_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        cityMap = googleMap;
        TextView propName = findViewById(R.id.txtPropName);
        // make prop detail box not visible by default unless prop marker is clicked
        changeDetailsVisibility(false, propName);
        // setup google maps camera position
        setupMapCamera();
        // apply property addresses as google maps markers
        showPropsOnMap(cityMap);

        cityMap.setOnMarkerClickListener(marker -> {
            // detail box prop name display the address of the marker clicked
            propName.setText(propMarkers.get(marker.getPosition()));
            // make prop detail box visible when prop marker is clicked
            changeDetailsVisibility(true, propName);
            return false;
        });
    }

    public void setupMapCamera(){
        // get searched city lat and long and set camera to that position
        LatLng city = new LatLng(cityLat, cityLong);
        cityMap.moveCamera(CameraUpdateFactory.newLatLng(city));
        // zoom for better view of typical sized metropolitan area
        cityMap.setMinZoomPreference(11);
        cityMap.setMaxZoomPreference(18);
    }

    public void showPropsOnMap(GoogleMap cityMap){
        // loop through propList and convert address to lat and long saved in locArr
        for(String prop : propList){
            double[] locArr = getLocFromName(prop);
            LatLng city = new LatLng(locArr[0], locArr[1]);
            //add marker on map with no lat and long from prop address
            cityMap.addMarker(new MarkerOptions().position(city));
            propMarkers.put(city, prop);
        }
    }

    public double[] getLocFromName(String location){
        double latitude = 0.0, longitude = 0.0;
        // use google maps geocoder api to take input address and return the lat and long in doubles
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocationName(location, 5);
            latitude = addresses.get(0).getLatitude();
            longitude = addresses.get(0).getLongitude();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new double[]{latitude, longitude};
    }

    // to toggle visibility of property details container easily
    public void changeDetailsVisibility(Boolean visible, TextView propName){
        ImageView container = findViewById(R.id.imgPropContainer);
        Button seeDetails = findViewById(R.id.btnSeeDetails);

        if(visible){
            // make all container components visible
            seeDetails.setVisibility(View.VISIBLE);
            propName.setVisibility(View.VISIBLE);
            container.setVisibility(View.VISIBLE);
        } else {
            // make all container components invisible
            seeDetails.setVisibility(View.INVISIBLE);
            propName.setVisibility(View.INVISIBLE);
            container.setVisibility(View.INVISIBLE);
        }
    }

    // to change to property details page on user click
    public void seeDetails(View v) {
        Intent page = new Intent(CityView.this, PropertyDetails.class);
        startActivity(page);
    }
}



