package com.optimind_jp.dott_eat_client;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.optimind_jp.dott_eat_client.models.Dish;
import com.optimind_jp.dott_eat_client.servercalls.SCClientFacade;
import com.schibstedspain.leku.LocationPickerActivity;

import java.util.ArrayList;

import socketcluster.io.socketclusterandroidclient.SocketCluster;

import static android.os.SystemClock.sleep;


public class MainActivity extends AppCompatActivity {

    private SocketCluster sc;
    private static String TAG = "SCDemo";
    public static SCClientFacade sCF;
    private final int WAIT_TIME = 2500;

    private GridView gridView;
    private DishGridAdapter dishGridAdapter;
    private double lat=0;
    private double lon=0;
    private ArrayList<Dish> dishList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SCClientFacade.setContext(this);
        sCF = SCClientFacade.getInstance();
        initImageLoader();
        startLocationPicker();
    }
    private void initImageLoader(){
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);
    }


    private void createDishGrid(){
        gridView = (GridView) findViewById(R.id.gridView);
        dishGridAdapter = new DishGridAdapter(this, R.layout.grid_single_dish_layout, dishList);
        gridView.setAdapter(dishGridAdapter);
    }



    private void startLocationPicker(){
        sleep(WAIT_TIME);
        Intent i = new Intent(this, LocationPickerActivity.class);
        startActivityForResult(i, 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                double latitude = data.getDoubleExtra(LocationPickerActivity.LATITUDE, 0);

                Log.d("LATITUDE****", String.valueOf(latitude));
                double longitude = data.getDoubleExtra(LocationPickerActivity.LONGITUDE, 0);
                Log.d("LONGITUDE****", String.valueOf(longitude));
                this.dishList = new ArrayList<Dish>(sCF.getDishesFromCoordinates(latitude, longitude));

                String address = data.getStringExtra(LocationPickerActivity.LOCATION_ADDRESS);
                Log.d("ADDRESS****", String.valueOf(address));
                String postalcode = data.getStringExtra(LocationPickerActivity.ZIPCODE);
                Log.d("POSTALCODE****", String.valueOf(postalcode));
                // bundle = data.getBundleExtra(LocationPickerActivity.TRANSITION_BUNDLE);
                //Log.d("BUNDLE TEXT****", bundle.getString("test"));
                Address fullAddress = data.getParcelableExtra(LocationPickerActivity.ADDRESS);
                Log.d("FULL ADDRESS****", fullAddress.toString());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
        createDishGrid();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_login) {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStop() {
        //sc.disconnect();
        //TODO: add scf disconnect stuff
        super.onStop();
    }
}
