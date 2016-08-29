package com.optimind_jp.dott_eat_client.data;

import android.content.Context;
import android.util.Log;

import com.optimind_jp.dott_eat_client.R;
import com.optimind_jp.dott_eat_client.models.Customer;
import com.optimind_jp.dott_eat_client.models.Dish;

import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    private Customer auth = null;

    private ResourceManager() {
    }
    private static ResourceManager instance= new ResourceManager();
    public static ResourceManager getInstance(){
        return instance;
    }
    public boolean loadAuth(Context context){
        File file = context.getFileStreamPath(context.getString(R.string.customer_file));
        if( file.exists() ) {
            auth = (Customer) loadSerializedObject(file);
        }
        if(null == auth)
            return false;
        else
            return true;
    }

    public boolean updateAuth(Context context, Customer newAuth)
    {
        if(null == auth)
            auth = new Customer();
        auth.copy(newAuth);
        File file = context.getFileStreamPath(context.getString(R.string.customer_file));
        return saveObject(auth, file);
    }

    public boolean clearAuth(Context context)
    {
        auth = null;

        File file = new File(context.getString(R.string.customer_file));
        return file.delete();
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

    public Customer getAuth() {
        return auth;
    }

    private Object loadSerializedObject(File f){
        try
        {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
            Object o = ois.readObject();
            ois.close();
            return o;
        }
        catch(Exception ex)
        {
            Log.v("Read Error : ",ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    private boolean saveObject(Object o, File f){
        try
        {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f)); //Select where you wish to save the file...
            oos.writeObject(o); // write the class as an 'object'
            oos.flush(); // flush the stream to insure all of the information was written to 'save_object.bin'
            oos.close();// close the stream
        }
        catch(Exception ex)
        {
            Log.v("Save Error : ",ex.getMessage());
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}
