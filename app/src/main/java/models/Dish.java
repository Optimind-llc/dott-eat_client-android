package models;

import android.location.Location;

import java.util.List;

/**
 * Created by hugh on 2016-08-20.
 */

public class Dish {
    String name;
    String description;
    int basePrice;
    Location location;
    float rating; //shows 0~5 stars
    List<DishReview> reviews;

}
