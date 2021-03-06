package com.optimind_jp.dott_eat_client;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.location.Address;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.optimind_jp.dott_eat_client.data.ResourceManager;
import com.optimind_jp.dott_eat_client.models.Customer;
import com.optimind_jp.dott_eat_client.models.CustomerStatus;
import com.optimind_jp.dott_eat_client.models.Dish;
import com.optimind_jp.dott_eat_client.servercalls.SCClientFacade;
import com.schibstedspain.leku.LocationPickerActivity;

import java.util.ArrayList;

import socketcluster.io.socketclusterandroidclient.SocketCluster;

import static android.os.SystemClock.sleep;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SocketCluster sc;
    private static String TAG = "SCDemo";
    public static SCClientFacade sCF;
    private final int WAIT_TIME = 2500;

    //private GridView gridView;
    //private DishGridAdapter dishGridAdapter;
    private DishStaggeredAdapter dishAdapter;
    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager gridLayoutManager;
    private double lat=0;
    private double lon=0;
    static Button notifCount;
    static int mNotifCount = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SCClientFacade.setContext(this);
        sCF = SCClientFacade.getInstance();
        initImageLoader();
        startLocationPicker();

        // load customer memory from file
        ResourceManager resMan = ResourceManager.getInstance();
        resMan.loadAuth(this);
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
        //gridView = (GridView) findViewById(R.id.gridView);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        gridLayoutManager = new StaggeredGridLayoutManager(2,1);
        recyclerView.setLayoutManager(gridLayoutManager);
        this.dishAdapter = new DishStaggeredAdapter(this, ResourceManager.getInstance().getDishList(), this);
        recyclerView.setAdapter(dishAdapter);
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
                this.lat= data.getDoubleExtra(LocationPickerActivity.LATITUDE, 0);


                this.lon = data.getDoubleExtra(LocationPickerActivity.LONGITUDE, 0);
                sCF.getDishesFromCoordinates(lat, lon);



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

        MenuItem item = menu.findItem(R.id.badge);
        MenuItemCompat.setActionView(item, R.layout.feed_update_count);
        View view = MenuItemCompat.getActionView(item);
        notifCount = (Button)view.findViewById(R.id.notif_count);
        notifCount.setText(String.valueOf(mNotifCount));
        return true;
    }
    
    private void setNotifCount(int count){
        mNotifCount = count;
        invalidateOptionsMenu();
    }
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        ResourceManager resMan = ResourceManager.getInstance();
        Customer auth = resMan.getAuth();
        boolean isLoggedIn = (auth != null &&
                (CustomerStatus.ONLINE == auth.getStatus() ||
                CustomerStatus.BLOCKED == auth.getStatus()));
        menu.findItem(R.id.menu_login).setVisible(!isLoggedIn);
        menu.findItem(R.id.menu_recent_history).setVisible(isLoggedIn);
        menu.findItem(R.id.menu_settings).setVisible(isLoggedIn);
        menu.findItem(R.id.menu_recommend).setVisible(isLoggedIn);
        menu.findItem(R.id.menu_logout).setVisible(isLoggedIn);
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
        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_logout) {
            ResourceManager resMan = ResourceManager.getInstance();
            resMan.clearAuth(this);
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

    @Override
    public void onClick(View v) {
        if(DishViewHolder.isSelected(v)){
            mNotifCount++;
        }else{
            mNotifCount--;
        }
        invalidateOptionsMenu();
    }
}
