package com.optimind_jp.dott_eat_client;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.optimind_jp.dott_eat_client.servercalls.SCClientFacade;
import com.schibstedspain.leku.LocationPickerActivity;

import socketcluster.io.socketclusterandroidclient.ISocketCluster;
import socketcluster.io.socketclusterandroidclient.SocketCluster;

import static android.os.SystemClock.sleep;


public class MainActivity extends AppCompatActivity {

    private SocketCluster sc;
    private static String TAG = "SCDemo";
    public static SCClientFacade SCF;
    private final int WAIT_TIME = 2500;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SCClientFacade.setContext(this);
        SCF = SCClientFacade.getInstance();

        startLocationPicker();
    }
    private void startLocationPicker(){
        sleep(WAIT_TIME);
        Intent i = new Intent(this, LocationPickerActivity.class);
        startActivityForResult(i, 1);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
          //  return true;
        //}

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStop() {
        //sc.disconnect();
        //TODO: add scf disconnect stuff
        super.onStop();
    }
}
