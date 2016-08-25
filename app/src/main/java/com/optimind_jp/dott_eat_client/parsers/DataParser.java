package com.optimind_jp.dott_eat_client.parsers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.optimind_jp.dott_eat_client.models.Dish;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.lang.reflect.Type;
import java.util.Collection;

/**
 * Created by hugh on 2016-08-23.
 */

public class DataParser {
    private static Gson gson = new Gson();
    private static Type dishListType = new TypeToken<Collection<Dish>>(){}.getType();
    private static JSONParser parser = new JSONParser();
    public static Collection<Dish> parseDishList(String json){
        return gson.fromJson(json, dishListType);
    }
    public static Dish parseDish(String json){
        return gson.fromJson(json, Dish.class);
    }

}
