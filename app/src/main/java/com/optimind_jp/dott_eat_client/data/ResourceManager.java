package com.optimind_jp.dott_eat_client.data;

import com.optimind_jp.dott_eat_client.models.Dish;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Fetches requested data based on ID.
 * Factory creates new Objects based on specs and assigns unique IDs.
 * Object pool.
 * After data from the server is instantiated as Java classes, it will be kept here.
 */

public class ResourceManager {
    ConcurrentHashMap<UUID, Dish> dishes = new ConcurrentHashMap<UUID, Dish>();

    private ResourceManager() {
    }
    private static ResourceManager instance= new ResourceManager();
    public static ResourceManager getInstance(){
        return instance;
    }
    public void addDishes(Collection<Dish> dishList){
        for (Dish d: dishList) {
            dishes.put(d.dishID, d);
        }
    }
    public void setDistances(double lat, double lon){
        for (Dish d: dishes.values()) {
            float[] distance = new float[3];
            android.location.Location.distanceBetween(lat, lon, d.lat, d.lon, distance);
            d.setDistance((int)distance[0]);
        }
    }

    public Collection<Dish> getDishes() {
        return dishes.values();
    }

    public ArrayList<Dish> getDishList(){
        return new ArrayList<>(dishes.values());
    }
}
