package com.example.rentalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.example.rentalapp.ConvertResponse.getPropertyData;

public class PropertyDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_details);
        getSupportActionBar().hide();

        RentalProperty rental = setupPropDetails();
        showPropDetails(rental);
    }

    private RentalProperty setupPropDetails(){
        Bundle extras = getIntent().getExtras();

        if (extras != null){
            String response = extras.getString("response");
            String address = extras.getString("address");
            return getPropertyData(response, address);
        }
        return null;
    }

    public void showPropDetails(RentalProperty rental){
        TextView txtProp = findViewById(R.id.txtPropDetails);
        txtProp.setText("City: " + rental.city + "\nAddress: " + rental.address + "\nBedroom: " + rental.bedroom + "\nBathroom: " + rental.bathroom + "\nPrice: $" + rental.price + " per month");
    }

    public void showPropImg(){}

    public void viewNextImg(){}

    public void viewPrevImg(){}
}