package com.optimind_jp.dott_eat_client.servercalls;

import android.app.Activity;
import android.util.Log;

import com.optimind_jp.dott_eat_client.data.ResourceManager;
import com.optimind_jp.dott_eat_client.models.Dish;
import com.optimind_jp.dott_eat_client.parsers.DataParser;

import java.util.ArrayList;
import java.util.Collection;

import socketcluster.io.socketclusterandroidclient.ISocketCluster;
import socketcluster.io.socketclusterandroidclient.SocketCluster;

/**
 * Created by hugh on 2016-08-22.
 */
public class SCClientFacade implements ServerCallAPI, ISocketCluster {
    private static SCClientFacade ourInstance;
    private static String TAG = "DFD";
    public static SCClientFacade getInstance() {
        return ourInstance;
    }
    private static Activity context;
    private SocketCluster sc;


    //CALL ONCE WITH LIGHT-WEIGHT ACTIVITY
    public static void setContext(Activity context){
        SCClientFacade.context = context;
        ourInstance= new SCClientFacade(context);
     }

    private SCClientFacade(Activity context) {
        sc = new SocketCluster("192.168.0.10", "8000", false, context);
        sc.setDelegate(this);
        sc.connect();
    }

    public String getDishesJsonFromCoordinates(double lat, double lon){
        //sc.emitEvent("dishes from coords",String.format("{lat:%1$f,lon:%2$f}", lat, lon));
        String dishes = DummyServer.emit(ServerTags.DISHES_FROM_COORDS,String.format("{\"lat\":%1$f,\"lon\":%2$f}", lat, lon));
        return dishes;
    }
    public Collection<Dish> getDishesFromCoordinates(double lat, double lon){
        ResourceManager.getInstance().addDishes(DataParser.parseDishList(getDishesJsonFromCoordinates(lat, lon)));
        ResourceManager.getInstance().setDistances(lat, lon);
        return ResourceManager.getInstance().getDishes();
    }

    @Override
    public void socketClusterReceivedEvent(String name, String data) {
        Log.i(TAG, "ReceivedEvent " + name);
        Log.i(TAG, "ReceivedEvent " + data);
    }

    @Override
    public void socketClusterChannelReceivedEvent(String name, String data) {
        Log.i(TAG, "socketClusterChannelReceivedEvent " + name + " data: " + data);
    }
    @Override
    public void socketClusterDidConnect() {
        Log.i(TAG, "SocketClusterDidConnect");
        sc.emitEvent("check", "{message:'shiichi'}");
    }

    @Override
    public void socketClusterDidDisconnect() {
        Log.i(TAG, "socketClusterDidDisconnect");
    }
    @Override
    public void socketClusterOnError(String error) {
        Log.i(TAG, "socketClusterOnError");
    }
    @Override
    public void socketClusterOnKickOut() {
        Log.i(TAG, "socketClusterOnKickOut");
    }
    @Override
    public void socketClusterOnSubscribe() {
        Log.i(TAG, "socketClusterOnSubscribe");
    }
    @Override
    public void socketClusterOnSubscribeFail() {
        Log.i(TAG, "socketClusterOnSubscribeFail");
    }
    @Override
    public void socketClusterOnUnsubscribe() {
        Log.i(TAG, "socketClusterOnUnsubscribe");
    }

}
