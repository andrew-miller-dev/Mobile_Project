package com.example.rentalapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText inputCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        inputCity = findViewById(R.id.inputCity);
    }

    public void searchCity(View v){
        Intent page = new Intent(MainActivity.this, CityView.class);
        page.putExtra("city", inputCity.getText().toString());
        startActivity(page);
    }

    public void requestLocationShare(){}
}