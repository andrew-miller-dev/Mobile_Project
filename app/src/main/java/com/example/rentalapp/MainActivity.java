package com.example.rentalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText inputCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        //initialize android networking library used for simpler API calls
        AndroidNetworking.initialize(getApplicationContext());
        //initialize inputCity user input so it can be used later on submit
        inputCity = findViewById(R.id.inputCity);
    }

    public void searchCity(View v){
        new GetCityProps().execute();
    }

    // helper class for rental API call and data manipulation, extends AsyncTask so we can override
    // async methods to wait for API response before continuing.
    class GetCityProps extends AsyncTask<Void, Void, Void> {
        ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);
        ArrayList<String> propList;
        String city;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // initialize prop list and get/fix city input for google maps and rental api fetch
            propList = new ArrayList<>();
            city = fixCityInput(inputCity.getText().toString());
            // show loading on pre execute until API call completes
            pdLoading.setMessage("\tLoading...");
            pdLoading.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // fetch all data with user input in background
            fetchPropData();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // dismiss loading bar once API response is received
            if(!propList.isEmpty()){
                pdLoading.dismiss();
            }
        }

        // helper method to complete API call and redirect when API call successful
        private void fetchPropData(){
            AndroidNetworking.get("https://realty-mole-property-api.p.rapidapi.com/rentalListings?")
                .addQueryParameter("city", city)
//              can add state parameter later on for input, to avoid accidental USA responses
//              .addQueryParameter("state", "AB")
                .addQueryParameter("limit", "20")
                .addHeaders("x-rapidapi-key", "f1d94c35e3msh00077622ea46cbcp17d0f1jsn5a1ee2750b53")
                .addHeaders("x-rapidapi-host", "realty-mole-property-api.p.rapidapi.com")
                .setPriority(Priority.LOW)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        // onResponse converts response to list and shows city page after API call completes
                        convertResponseToList(response);
                        goToCityViewOnResponse();
                    }

                    @Override
                    public void onError(ANError anError) {
                        // log error if there is a problem getting API call response
                        Log.d("Error", anError.toString());
                    }
            });
        }

        // *will eventually be removed and pass whole parsed response with display info
        // helper method to loop through response after converting to JSONArray and add each
        // formatted address to the propList to display on map
        private void convertResponseToList(String response){
            JSONArray props = propStringToJson(response);

            for (int i = 0; i < props.length(); ++i) {
                try {
                    JSONObject prop = props.getJSONObject(i);
                    propList.add(prop.getString("formattedAddress"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        // get property list, city name and redirect to CityView page when API response is received
        private void goToCityViewOnResponse(){
            // use non empty list to know when to return *need timeout after n seconds if no response
            // is found to stop infinite loop..
            if(!propList.isEmpty()){
                Intent page = new Intent(MainActivity.this, CityView.class);
                page.putExtra("city", city);
                page.putExtra("propList", propList);
                startActivity(page);
            }
        }

        //helper method to fix city input so that first character of each word is capitalize
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

        //helper method for converting api string response to JSONArray, could not get built in
        // networking method to stop throwing errors.
        private JSONArray propStringToJson(String propString){
            try {
                return new JSONArray(propString);
            }catch (JSONException err){
                Log.d("Error", err.toString());
            }
            return null;
        }
    }
}