package com.example.rentalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.URI;

import static com.example.rentalapp.ConvertResponse.getPropertyData;

public class PropertyDetails extends AppCompatActivity {
    Button btnCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_details);
        getSupportActionBar().hide();

        RentalProperty rental = setupPropDetails();
        showPropDetails(rental);

        btnCall = findViewById(R.id.btnCallLandlord);

        btnCall.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:1-888-237-7945"));
            startActivity(intent);
        });
    }

    private RentalProperty setupPropDetails() {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String response = extras.getString("response");
            String address = extras.getString("address");
            return getPropertyData(response, address);
        }
        return null;
    }

    private void showPropDetails(RentalProperty rental) {
        TextView price = findViewById(R.id.txtPrice);
        TextView datePosted = findViewById(R.id.txtDatePosted);
        TextView city = findViewById(R.id.txtCity);
        TextView state = findViewById(R.id.txtState);
        TextView address = findViewById(R.id.txtAddress);
        TextView propType = findViewById(R.id.txtPropType);
        TextView bedroom = findViewById(R.id.txtBedrooms);
        TextView bathroom = findViewById(R.id.txtBathrooms);
        TextView squareFeet = findViewById(R.id.txtSquareFeet);

        price.setText("Price: $" + rental.price);
        datePosted.setText("Date Posted: " + rental.datePosted);
        city.setText("City: " + rental.city);
        state.setText("State: " + rental.state);
        address.setText("Address: " + rental.address);
        propType.setText("Property Type: " + rental.propType);
        bedroom.setText("Bedrooms: " + rental.bedroom);
        bathroom.setText("Bathrooms: " + rental.bathroom);
        squareFeet.setText("Square Footage: " + rental.squareFoot);
    }

    public void callLandlord(View v){

    }


    public void showPropImg(){}

    public void viewNextImg(){}

    public void viewPrevImg(){}
}