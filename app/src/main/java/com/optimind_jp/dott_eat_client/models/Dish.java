package com.optimind_jp.dott_eat_client.models;

import android.location.Location;

import java.net.URL;
import java.util.List;
import java.util.UUID;

/**
 * Created by hugh on 2016-08-20.
 */

public class Dish {
    UUID dishID;
    public String name;
    String description;
    int basePrice;
    Location location;
    public String imageURL;
    float rating; //shows 0~5 stars
    List<DishReview> reviews;

    public Dish(String name, String description, int basePrice, Location location, String imageURL, float rating, List<DishReview> reviews) {
        this.name = name;
        this.description = description;
        this.basePrice = basePrice;
        this.location = location;
        this.imageURL = imageURL;
        this.rating = rating;
        this.reviews = reviews;
    }
}
