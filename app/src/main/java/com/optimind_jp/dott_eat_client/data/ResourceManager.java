package com.optimind_jp.dott_eat_client.data;

import com.optimind_jp.dott_eat_client.models.Dish;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Fetches requested data based on ID.
 * Factory creates new Objects based on specs and assigns unique IDs.
 * Object pool.
 * After data from the server is instantiated as Java classes, it will be kept here.
 */

public class ResourceManager {
    ConcurrentHashMap<UUID, Dish> dishes;

    public ResourceManager() {

    }
}
