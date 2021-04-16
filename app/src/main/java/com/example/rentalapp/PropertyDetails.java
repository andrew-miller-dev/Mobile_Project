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
    ArrayList<Integer> rentalImgList;
    int currentImg;

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
        price.setText("Price: $" + rental.price);

        TextView datePosted = findViewById(R.id.txtDatePosted);
        datePosted.setText("Date Posted: " + rental.datePosted);

        TextView city = findViewById(R.id.txtCity);
        city.setText("City: " + rental.city);

        TextView state = findViewById(R.id.txtState);
        state.setText("State: " + rental.state);

        TextView address = findViewById(R.id.txtAddress);
        address.setText("Address: " + rental.address);

        TextView propType = findViewById(R.id.txtPropType);
        propType.setText("Property Type: " + rental.propType);

        TextView bedroom = findViewById(R.id.txtBedrooms);
        bedroom.setText("Bedrooms: " + rental.bedroom);

        TextView bathroom = findViewById(R.id.txtBathrooms);
        bathroom.setText("Bathrooms: " + rental.bathroom);

        TextView squareFeet = findViewById(R.id.txtSquareFeet);
        squareFeet.setText("Square Footage: " + rental.squareFoot);

        getPropImages(rental.propType);
        showPropImages();
    }

    public ArrayList<Integer>  getPropImages(String propType){
        GetRentalPictures rentalImg = new GetRentalPictures();

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

    public void showPropImages(){
        try{
            Integer img = rentalImgList.get(0);
            ImageView propImg = findViewById(R.id.imgProp);
            propImg.setImageResource(img);
            currentImg = 0;
        } catch (Exception e){
            Log.d("Response", e.toString());
        }
    }

    public void viewNextImg(View v){
        if(currentImg < rentalImgList.size() - 1){
            currentImg = currentImg + 1;
            Integer img = rentalImgList.get(currentImg);
            ImageView propImg = findViewById(R.id.imgProp);
            propImg.setImageResource(img);
        }
    }

    public void viewPrevImg(View v){
        if(currentImg > 0){
            currentImg = currentImg - 1;
            Integer img = rentalImgList.get(currentImg);
            ImageView propImg = findViewById(R.id.imgProp);
            propImg.setImageResource(img);
        }
    }
}