package com.example.rentalapp.Helpers;

public class RentalProperty {
    public String price;
    public String datePosted;
    public String city;
    public String state;
    public String address;
    public String propType;
    public String bedroom;
    public String bathroom;
    public String squareFoot;

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
