package com.example.rentalapp;

public class RentalProperty {
    String price;
    String datePosted;
    String city;
    String state;
    String address;
    String propType;
    String bedroom;
    String bathroom;
    String squareFoot;


    public RentalProperty(String Price, String DatePosted, String City, String State, String Address, String PropType, String Bedroom, String Bathroom, String SquareFoot){
        price = Price;
        datePosted = DatePosted;
        city = City;
        state = State;
        address = Address;
        propType = PropType;
        bedroom = Bedroom;
        bathroom = Bathroom;
        squareFoot = SquareFoot;
    }
}
