package com.example.rentalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        //initialize android networking library used for simpler API calls
        AndroidNetworking.initialize(getApplicationContext());
        //initialize inputCity user input so it can be used later on submit
    }

    public void searchCity(View v) {
        TextView inputCity = findViewById(R.id.inputCity);
        ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);
        pdLoading.setMessage("\tLoading...");
        pdLoading.show();

        ExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        Future<?> apiCallStatus = executorService.submit(() -> {
            String city = fixCityInput(inputCity.getText().toString());
            fetchPropData(city);
        });

        while (!apiCallStatus.isDone()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // helper method to complete API call and redirect when API call successful
    private void fetchPropData(String city) {
        AndroidNetworking.get("https://realty-mole-property-api.p.rapidapi.com/rentalListings?")
            .addQueryParameter("city", city)
            .addQueryParameter("limit", "10")
            .addHeaders("x-rapidapi-key", "f1d94c35e3msh00077622ea46cbcp17d0f1jsn5a1ee2750b53")
            .addHeaders("x-rapidapi-host", "realty-mole-property-api.p.rapidapi.com")
            .setPriority(Priority.LOW)
            .build()
            .getAsString(new StringRequestListener() {
                @Override
                public void onResponse(String response) {
                    goToCityViewOnResponse(city, response);
                }
                @Override
                public void onError(ANError anError) {
                    // log error if there is a problem getting API call response
                    Log.d("Error", anError.toString());
                }
            }
        );
    }

    // helper method to fix city input so that first character of each word is capitalize
    // for google map API to recognize and geocode the input
    private String fixCityInput(String city) {
        StringBuffer cb = new StringBuffer();
        String[] cityArr = city.split(" ");

        //string buffer used for efficiency, split input to separate words and capitalize individually and re add space
        for (int i = 0; i < cityArr.length; i++) {
            cb.append(Character.toUpperCase(cityArr[i].charAt(0))).append(cityArr[i].substring(1)).append(" ");
        }
        return cb.toString().trim();
    }

    private void goToCityViewOnResponse(String city, String response) {
        Intent page = new Intent(MainActivity.this, CityView.class);
        page.putExtra("city", city);
        page.putExtra("response", response);
        startActivity(page);
    }
}



