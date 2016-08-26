package com.optimind_jp.dott_eat_client.models;

import android.location.Location;
import android.util.Log;

import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;

/**
 * Created by hugh on 2016-08-20.
 */

public class Dish {
    public UUID dishID;
    public String name;
    String description;
    public int basePrice;
    public double lat;
    public double lon;
    public String imageURL;
    float rating; //shows 0~5 stars
    List<DishReview> reviews;
    public int distance;
    public BigDecimal priceWithDistance;


    public Dish(String name, String description, int basePrice, double lat, double lon, String imageURL, float rating, List<DishReview> reviews) {
        this.name = name;
        this.description = description;
        this.basePrice = basePrice;
        this.lat = lat;
        this.lon = lon;
        this.imageURL = imageURL;
        this.rating = rating;
        this.reviews = reviews;
    }

    public void setDistance(int meters){
        Log.d("Meters=", String.valueOf(meters));
        priceWithDistance= new BigDecimal(basePrice+meters).setScale(0, BigDecimal.ROUND_HALF_UP);
        distance=meters;
    }

    public String getPriceString(){
        return "¥"+priceWithDistance.toString();
    }
}
