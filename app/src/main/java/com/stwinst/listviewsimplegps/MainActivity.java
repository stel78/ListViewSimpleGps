package com.stwinst.listviewsimplegps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LocationListener {


    ListView listView ;

    ArrayList<String> gpsList;



    Location location; // location


    private String provider;

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 2; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000  * 1; // 1 second

    // Declaring a Location Manager
    protected LocationManager locationManager;

    //ArrayAdapter for ListView
    ArrayAdapter<String> itemsAdapter;



    private final static int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.listView);

        //Initializing gpsList
       gpsList = new ArrayList<>();

       for(int i=0;i<7;i++){
            gpsList.add("Null");
        }
        itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, gpsList);
        // Assign adapter to ListView
        listView.setAdapter(itemsAdapter);

        // check if GPS enabled
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);



                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        location = locationManager.getLastKnownLocation(provider);

        if(location!=null){

            double lat = location.getLatitude();
            double speed = location.getSpeed();
            double log = location.getLongitude();
            double alt = location.getAltitude();
            double bearing = location.getBearing();
            double accuracy = location.getAccuracy();
            double time = location.getTime();

             gpsList.clear();
            //update gpsList
            gpsList.add(   "Lat "+String.valueOf(lat));
                    gpsList.add(    "Long "+String.valueOf(log));
                            gpsList.add(   "Alt "+String.valueOf(alt));
                                    gpsList.add(    "Speed "+String.valueOf(speed));
                                            gpsList.add(    "Bearing "+String.valueOf(bearing));
                                                    gpsList.add(    "Accuracy "+String.valueOf(accuracy));
                                                            gpsList.add(     "Time "+String.valueOf(time));

            //update adapter
            itemsAdapter.notifyDataSetChanged();






        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {



                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    // \n is for new line
                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                }

                 else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onLocationChanged(Location location) {

            double lat = location.getLatitude();
            double speed = location.getSpeed();
            double log = location.getLongitude();
            double alt = location.getAltitude();
            double bearing = location.getBearing();
            double accuracy = location.getAccuracy();
            double time = location.getTime();

            gpsList.clear();
            //update gpsList
            gpsList.add(   "Lat "+String.valueOf(lat));
            gpsList.add(    "Long "+String.valueOf(log));
            gpsList.add(   "Alt "+String.valueOf(alt));
            gpsList.add(    "Speed "+String.valueOf(speed));
            gpsList.add(    "Bearing "+String.valueOf(bearing));
            gpsList.add(    "Accuracy "+String.valueOf(accuracy));
            gpsList.add(     "Time "+String.valueOf(time));

            //update adapter
            itemsAdapter.notifyDataSetChanged();





    }

    /* Request updates at startup */
    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(provider, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
