package com.example.rentalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static com.example.rentalapp.ConvertResponse.getPropertyData;

public class PropertyDetails extends AppCompatActivity {
    Button btnCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_details);
        getSupportActionBar().hide();

        RentalProperty rental = getPropDetails();
        showPropDetails(rental);

        btnCall = findViewById(R.id.btnCallLandlord);

        btnCall.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:1-888-237-7945"));
            startActivity(intent);
        });


    }

    private RentalProperty getPropDetails() {
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

        showPropImages(getPropImages(rental.propType));
    }

    public ArrayList<Integer>  getPropImages(String propType){
        GetRentalPictures rentalImg = new GetRentalPictures();
        ArrayList<Integer> rentalImgList;

        switch(propType){
            case "Condo":
                rentalImgList = rentalImg.getCondoPics();
                break;
            case "Apartment":
                rentalImgList = rentalImg.getApartmentPics();
                break;
            case "Single Family":
                rentalImgList = rentalImg.getHousePics();
                break;
            default:
                rentalImgList = rentalImg.getDefaultPics();
                break;
        }
        return rentalImgList;
    }

    public void showPropImages(ArrayList<Integer> rentalImg){
        ImageView propImg = findViewById(R.id.imgProp);
        Integer img = rentalImg.get(0);
        try{
            propImg.setImageResource(img);
        } catch (Exception e){
            Log.d("Response", e.toString());
        }
    }


    public void viewNextImg(){}

    public void viewPrevImg(){}
}