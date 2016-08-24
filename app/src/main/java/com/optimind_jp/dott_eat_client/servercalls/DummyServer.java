package com.optimind_jp.dott_eat_client.servercalls;

import android.util.Log;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

/**
 * Created by hugh on 2016-08-23.
 */

public class DummyServer {
    static JSONParser parser = new JSONParser();
    public static JSONObject makeDish(String name, double lat, double lon){
        JSONObject jo = new JSONObject();

            jo.put("name", name);
            jo.put("lat", lat);
            jo.put("lon", lon);

        return jo;
    }
    private static JSONArray getDummyDishes(){
        String s1 = "{\"name\":\"salmon maki\",\"lat\":35.157692, \"lon\":136.951809}";
        String s2 = "{\"name\":\"margerita\", \"lat\":35.151262, \"lon\":136.966370}";
        String s3 = "{\"name\":\"French toast\",\"lat\":35.159114, \"lon\":136.964980}";
        String s4 = "{\"name\":\"Chicken Tika\", \"lat\":35.167707, \"lon\":136.953054}";
        String s5 = "{\"name\":\"Cheese Burger\", \"lat\":35.167417, \"lon\":136.945770}";
        String[] slist = new String[]{s1,s2,s3,s4,s5};
        JSONArray res = new JSONArray();
        for (String s: slist) {
            try {
                res.add((JSONObject)parser.parse(s));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return res;
    }
    public static String emit(String tag, String jsonStr){
        if(tag==ServerTags.DISHES_FROM_COORDS){
            return emitGetDishesFromLoc(jsonStr);
        }else if(tag==ServerTags.USR_FROM_UUID){
            //just return default for test
            return "{\n" +
                    "  \"client\":{\n" +
                    "    \"UUID\":\"820c6c0e-68ec-11e6-8b77-86f30ca893d3\",\n" +
                    "    \"firstName\":\"Brad\",\n" +
                    "    \"lastName\":\"Pitt\",\n" +
                    "    \"nickName\":\"bpit\",\n" +
                    "    \"age\": 35,\n" +
                    "    \"email\": \"bpitt@starcat.com\"\n" +
                    "  }\n" +
                    "}";
        }else {
            return "FAIL";
        }
    }


    private static String emitGetDishesFromLoc(String jsonStr) {
        Log.d("JSONSTR", jsonStr);
        JSONArray res = null;
        try {
            Object o = parser.parse(jsonStr);
            JSONObject jo = (JSONObject) o;
            Double lat = (Double) jo.get("lat");
            Double lon = (Double) jo.get("lon");
            res = getCloseDishes(getDummyDishes(), lat, lon);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return res.toJSONString();
    }


    private static JSONArray getCloseDishes(JSONArray dishes, double lat, double lon){
        JSONArray ja = new JSONArray();
        for (Object o: dishes) {
            JSONObject jo = (JSONObject)o;
            Double lat2 = null;

                lat2 = (Double) jo.get("lat");

            Double lon2 = null;

                lon2 = (Double) jo.get("lon");

            float[] res = new float[2];
            android.location.Location.distanceBetween(lat, lon, lat2, lon2,res);
            if(res[0]<3000){
                ja.add(jo);
            }
        }
        return ja;
    }
}
