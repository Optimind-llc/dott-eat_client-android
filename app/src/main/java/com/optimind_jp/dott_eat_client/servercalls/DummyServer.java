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
        String s1 = "{" +
                "    \"dishID\": \"12d05e5b-662d-4b39-b4f6-41fc55ccc226\"" +
                "    \"name\":\"tacos\"," +
                "    \"description\":\"dishDescription\"," +
                "    \"basePrice\":3000," +
                "    \"lat\":35.157692," +
                "    \"lon\":136.951809," +
                "    \"imageURL\":\"http://i.cbc.ca/1.3385054.1452202073!/cpImage/httpImage/image.jpg_gen/derivatives/original_620/food-grocery-costs-recipes-20151224.jpg\"," +
                "    \"rating\":4.6" +
                "    \"reviews\":[{\"authorID\": \"820c6c0e-68ec-11e6-8b77-86f30ca893d3\", \"contents\": \"This dish was delicious\"}]" +
                "  }";
        String s2 = "{" +
                "    \"dishID\": \"96c1e093-d4e2-45e1-8fda-eb2b4c199718\"" +
                "    \"name\":\"margerita\"," +
                "    \"description\":\"dishDescription\"," +
                "    \"basePrice\":3000," +
                "    \"lat\":35.151262," +
                "    \"lon\":136.966370," +
                "    \"imageURL\":\"http://recipetreasure.com/wp-content/uploads/2014/02/flatbread-margherita-pizza-recipe-treasure-3.jpg\"," +
                "    \"rating\":4.6" +
                "    \"reviews\":[{\"authorID\": \"820c6c0e-68ec-11e6-8b77-86f30ca893d3\", \"contents\": \"This dish was delicious\"}]" +
                "  }";
        String s3 = "{" +
                "    \"dishID\": \"46c8722c-c4bc-49b0-b5b8-c907a08a0d3d\"" +
                "    \"name\":\"French toast\"," +
                "    \"description\":\"dishDescription\"," +
                "    \"basePrice\":3000," +
                "    \"lat\":35.159114," +
                "    \"lon\":136.964980," +
                "    \"imageURL\":\"http://d1doqjmisr497k.cloudfront.net/~/media/Recipe-Photos/McCormick/Breakfast-Brunch/1007x545/Quick-and-Easy-French-Toast_Recipes_1007x545.ashx?vd=20150602T025411Z&hash=0387CB1C6668FC27934AB9DF48B972B0F1372545\"," +
                "    \"rating\":4.6" +
                "    \"reviews\":[{\"authorID\": \"820c6c0e-68ec-11e6-8b77-86f30ca893d3\", \"contents\": \"This dish was delicious\"}]" +
                "  }";
        String s4 = "{" +
                "    \"dishID\": \"6176826b-ba39-49bf-b35c-8087107c0d3d\"" +
                "    \"name\":\"Chiken Tikka\"," +
                "    \"description\":\"dishDescription\"," +
                "    \"basePrice\":3000," +
                "    \"lat\":35.167707," +
                "    \"lon\":136.953054," +
                "    \"imageURL\":\"https://bigoven-res.cloudinary.com/image/upload/t_recipe-256/chicken-tikka-masala-94.jpg\"," +
                "    \"rating\":4.6" +
                "    \"reviews\":[{\"authorID\": \"820c6c0e-68ec-11e6-8b77-86f30ca893d3\", \"contents\": \"This dish was delicious\"}]" +
                "  }";
        String s5 = "{" +
                "    \"dishID\": \"31c1b599-7049-45ad-bb88-64c3bf291991\"" +
                "    \"name\":\"Cheese Burger\"," +
                "    \"description\":\"dishDescription\"," +
                "    \"basePrice\":3000," +
                "    \"lat\":35.167707," +
                "    \"lon\":136.953054," +
                "    \"imageURL\":\"http://www.graftonstreetva.com/wp-content/uploads/2013/02/cheeseburger.jpg\"," +
                "    \"rating\":4.6" +
                "    \"reviews\":[{\"authorID\": \"820c6c0e-68ec-11e6-8b77-86f30ca893d3\", \"contents\": \"This dish was delicious\"}]" +
                "  }";
        //String s5 = "{\"name\":\"Cheese Burger\", \"lat\":35.167417, \"lon\":136.945770}";
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
