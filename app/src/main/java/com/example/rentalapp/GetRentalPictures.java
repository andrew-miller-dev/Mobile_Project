package com.example.rentalapp;

import java.util.ArrayList;
import java.util.Random;

public class GetRentalPictures {

    public int getRandomNumb(int min, int max){
        Random rand = new Random();
        int numb = rand.nextInt((max - min) + 1) + min;
        return numb;
    }

    public ArrayList<Integer> getApartmentPics(){
        ArrayList<Integer> apartment1 = new ArrayList<>();
        apartment1.add(R.drawable.apt1_1);
        apartment1.add(R.drawable.apt1_2);
        apartment1.add(R.drawable.apt1_3);
        apartment1.add(R.drawable.apt1_4);
        apartment1.add(R.drawable.apt1_5);

        ArrayList<Integer> apartment2 = new ArrayList<>();
        apartment2.add(R.drawable.apt2_1);
        apartment2.add(R.drawable.apt2_2);
        apartment2.add(R.drawable.apt2_3);
        apartment2.add(R.drawable.apt2_4);
        apartment2.add(R.drawable.apt2_5);
        apartment2.add(R.drawable.apt2_6);

        ArrayList<Integer> apartment3 = new ArrayList<>();
        apartment3.add(R.drawable.apt3_1);
        apartment3.add(R.drawable.apt3_2);
        apartment3.add(R.drawable.apt3_3);
        apartment3.add(R.drawable.apt3_4);
        apartment3.add(R.drawable.apt3_5);

        int numb = getRandomNumb(1, 3);
        if(numb == 1)
            return apartment1;
        if(numb == 2)
            return apartment2;
        else
            return apartment3;
    }
    public ArrayList<Integer> getCondoPics(){
        ArrayList<Integer> condo1 = new ArrayList<>();
        condo1.add(R.drawable.condo1_1);
        condo1.add(R.drawable.condo1_2);
        condo1.add(R.drawable.condo1_3);
        condo1.add(R.drawable.condo1_4);
        condo1.add(R.drawable.condo1_5);

        ArrayList<Integer> condo2 = new ArrayList<>();
        condo2.add(R.drawable.condo2_1);
        condo2.add(R.drawable.condo2_2);
        condo2.add(R.drawable.condo2_3);
        condo2.add(R.drawable.condo2_4);
        condo2.add(R.drawable.condo2_5);

        ArrayList<Integer> condo3 = new ArrayList<>();
        condo3.add(R.drawable.condo3_1);
        condo3.add(R.drawable.condo3_2);
        condo3.add(R.drawable.condo3_3);
        condo3.add(R.drawable.condo3_4);

        int numb = getRandomNumb(1, 3);
        if(numb == 1)
            return condo1;
        if(numb == 2)
            return condo2;
        else
            return condo3;
    }
    public ArrayList<Integer> getDefaultPics(){
        ArrayList<Integer> default1 = new ArrayList<>();
        default1.add(R.drawable.default1);
        default1.add(R.drawable.default2);
        default1.add(R.drawable.default3);
        default1.add(R.drawable.default4);
        default1.add(R.drawable.default5);

        return default1;
    }
    public ArrayList<Integer> getHousePics(){
        ArrayList<Integer> house1 = new ArrayList<>();
        house1.add(R.drawable.house1_1);
        house1.add(R.drawable.house1_2);
        house1.add(R.drawable.house1_3);
        house1.add(R.drawable.house1_4);
        house1.add(R.drawable.house1_5);

        ArrayList<Integer> house2 = new ArrayList<>();
        house2.add(R.drawable.house2_1);
        house2.add(R.drawable.house2_2);
        house2.add(R.drawable.house2_3);
        house2.add(R.drawable.house2_4);
        house2.add(R.drawable.house2_5);

        int numb = getRandomNumb(1, 2);

        if(numb == 1)
            return house1;
        else
            return house2;
    }
}
