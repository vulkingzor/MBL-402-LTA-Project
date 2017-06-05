package edu.phoenix.mbl402.LTA.Activities;

// Creators: Learning Team A
// Created Date: 5/21/2017
// Class Description: Activity to view the global map, focuses on the user's current location and
//      and shows nearby trails.

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.phoenix.mbl402.LTA.Database.DatabaseManager;
import edu.phoenix.mbl402.LTA.R;
import edu.phoenix.mbl402.LTA.Models.Trail;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    // Variables for the Map, location, Google APi and on screen buttons
    private GoogleMap mMap;
    private GoogleApiClient apiClient;
    private Location lastLocation;
    private Marker mapMark;
    private LocationRequest locationRequest;

    private Button zoomInBtn;
    private Button zoomOutBtn;

    // Database manager to check if data exists
    DatabaseManager databaseManager;
    Cursor cursor;

    ArrayList<Trail> trailList;
    ArrayList<Marker> trailMarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Checks if runtime permissions are required by SDK version
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Setting up Zoom buttons and click listeners
        zoomInBtn = (Button) findViewById(R.id.btn_zoomin);
        zoomOutBtn = (Button) findViewById(R.id.btn_zoomout);

        zoomInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.animateCamera(CameraUpdateFactory.zoomIn());
            }
        });

        zoomOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.animateCamera(CameraUpdateFactory.zoomOut());
            }
        });
    }

    // Creating the Option Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map_options, menu);
        return true;
    }

    // Switch/Case statement for all of the menu options to change the map
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.map_normal:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;
            case R.id.map_hybrid:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true;
            case R.id.map_satellite:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                return true;
            case R.id.map_terrain:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                return true;
            case R.id.map_none:
                mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                return true;
            case R.id.map_about:
                Toast.makeText(MapActivity.this, "This page is a map of your current location. The other markers are other trails logged", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_home:
                // Returns to Main Menu
                Intent homeIntent = new Intent(MapActivity.this, MainMenu.class);
                startActivity(homeIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    // Manipulation for the Map once created
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Initialize Google Play Services for location
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

        // When map is ready, marks known Trails
        setupTrails();

    }

    // Building the Google Api with handlers for lost connect
// and location services
    protected synchronized void buildGoogleApiClient() {
        apiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        apiClient.connect();
    }

    // On connection requesting the location of the device
    @Override
    public void onConnected(Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(apiClient, locationRequest, MapActivity.this);
        }
    }

    // Required methods if the connection is suspended or fails
    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    // Method to handle when the location changes
    @Override
    public void onLocationChanged(Location location) {
        // Gets the location
        lastLocation = location;

        // Removes a mapMark if present
        if (mapMark != null) {
            mapMark.remove();
        }

        // Sets the mark's info window to unknown
        String markInfo = "Unknown";
        String markSnippet = "";

        // Getting the current location
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        // Setting the marker options
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);

        // Getting the current location information based on the coordinates
        Geocoder geocoder = new Geocoder(MapActivity.this);

        // Try / Catch in case the geocoder has an error
        try {
            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());

            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            // Checks if any address found
            if (addresses.size() > 0) {

                // Sets values to blank in case the address does not have this information
                String city = "";
                String state = "";
                String country = "";
                String postalCode = "00000";

                // Checking if each value is not null
                if (addresses.get(0).getLocality() != null) {
                    city = addresses.get(0).getLocality();
                }
                if (addresses.get(0).getAdminArea() != null) {
                    state = addresses.get(0).getAdminArea();
                }
                if (addresses.get(0).getCountryName() != null) {
                    country = addresses.get(0).getCountryName();
                }
                if (addresses.get(0).getPostalCode() != null) {
                    postalCode = addresses.get(0).getPostalCode();
                }

                // Creating the Mark's info
                markInfo = (city);
                markSnippet = (state + ", " + postalCode + ", " + country + " ");

                // In case no address info
                if (markInfo == "" || markInfo == null) {
                    markInfo = "Unknown";
                }
            } else {
                // If no address unknown is kept
                // Placed in the result of ocean coordinates
                markInfo = "Unknown";
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        // Sets the marker title to the address info
        markerOptions.title(markInfo);
        markerOptions.snippet(markSnippet);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
        mapMark = mMap.addMarker(markerOptions);

        // Move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    // Checking if Permissions for Location is allowed
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // In case user wants info
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No reason required shows request
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    // Method to check if the request was allowed or denied
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Permission was granted.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (apiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    // Method to read known trails and mark the map with markers
    public void setupTrails() {

        String TAG = "Log";

        // Opening Database connection
        databaseManager = new DatabaseManager(this);
        databaseManager.open();
        cursor = databaseManager.fetchTrails();

        Trail trailModel;
        trailList = new ArrayList<Trail>();

        // If information found adds the trail to a list
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                trailModel = new Trail();
                trailModel.setTrailID(cursor.getString(0));
                trailModel.setName(cursor.getString(1));
                trailModel.setLatitude(Double.valueOf(cursor.getString(2)));
                trailModel.setLongitude(Double.valueOf(cursor.getString(3)));
                trailModel.setCity(cursor.getString(4));
                trailModel.setState(cursor.getString(5));
                trailModel.setCountry(cursor.getString(6));
                trailModel.setZip(cursor.getString(7));
                trailList.add(trailModel);
                Log.i(TAG, "Logging: " + trailModel.getName());
            }
        }
        // Closing Database
        cursor.close();
        databaseManager.closeDB();

        trailMarks = new ArrayList<Marker>();

        // Creating the markers
        for (int i = 0; i < trailList.size(); i++) {

            // Getting the current location
            LatLng latLng = new LatLng(trailList.get(i).getLatitude(),
                    trailList.get(i).getLongitude());
            // Sets the mark's info window to unknown
            String trailInfo = trailList.get(i).getName();
            String trailSnip = trailList.get(i).getCity() + " " + trailList.get(i).getState() + ", " + trailList.get(i).getZip() + ", " + trailList.get(i).getCountry() + " ";

            trailMarks.add(mMap.addMarker(new MarkerOptions()
                    .position(latLng).title(trailInfo)
                    .snippet(trailSnip)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))));

        }
    }


}