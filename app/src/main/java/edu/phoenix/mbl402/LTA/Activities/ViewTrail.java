package edu.phoenix.mbl402.LTA.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.phoenix.mbl402.LTA.Database.DatabaseManager;
import edu.phoenix.mbl402.LTA.Models.Trail;
import edu.phoenix.mbl402.LTA.R;

public class ViewTrail extends AppCompatActivity implements OnMapReadyCallback{
    // UI Buttons
    private Button updateButton;
    private Button deleteButton;

    // UI Text Fields
    private TextView trailID;
    private EditText nameET;
    private EditText latET;
    private EditText longET;
    private EditText cityET;
    private EditText stateET;
    private EditText countryET;
    private EditText zipET;

    private GoogleMap trailMap;

    // Long value of the Item ID from the database
    private long _id;
    private double latNum;
    private double longNum;

    // Database manager for access to DB methods
    private DatabaseManager databaseManager;

    String id;
    String trailName;;
    String latitude;
    String longitude;
    String city;
    String state;
    String country;;
    String zip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trail);

        // Getting passed information
        Intent intent = getIntent();
          id = intent.getStringExtra("id");
          trailName = intent.getStringExtra("name");;
         latitude= intent.getStringExtra("lat");
         longitude= intent.getStringExtra("long");
         city = intent.getStringExtra("city");
         state = intent.getStringExtra("state");
         country = intent.getStringExtra("country");
         zip = intent.getStringExtra("zip");

        try{
            _id = Long.parseLong(id);
            longNum = Double.parseDouble(longitude);
            latNum = Double.parseDouble(latitude);
        }catch (Exception e){
            Toast.makeText(ViewTrail.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // All of the edit text fields
        trailID = (TextView)findViewById(R.id.txt_id);
        nameET = (EditText) findViewById(R.id.edit_name);
        latET = (EditText) findViewById(R.id.edit_lat);
        longET = (EditText) findViewById(R.id.edit_long);
        cityET = (EditText) findViewById(R.id.edit_city);
        stateET = (EditText) findViewById(R.id.edit_state);
        countryET = (EditText) findViewById(R.id.edit_country);
        zipET = (EditText) findViewById(R.id.edit_zip);

        // Setting text to passed values
        trailID.setText(id);
        nameET.setText(trailName);
        latET.setText(latitude);
        longET.setText(longitude);
        cityET.setText(city);
        stateET.setText(state);
        countryET.setText(country);
        zipET.setText(zip);

        // The buttons
        updateButton = (Button)findViewById(R.id.button_update);
        deleteButton = (Button)findViewById(R.id.button_delete);

        // Database managers
        databaseManager = new DatabaseManager(this);
        databaseManager.open();

        // Update button method, calls the database manager's update call
        // Validates the entries are all filled in by user
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = nameET.getText().toString();
                final String latitude = latET.getText().toString();
                final String longitude = longET.getText().toString();
                final String city = cityET.getText().toString();
                final String state = stateET.getText().toString();
                final String country = countryET.getText().toString();
                final String zip = zipET.getText().toString();

                try{
                    _id = Long.parseLong(id);
                    longNum = Double.parseDouble(longitude);
                    latNum = Double.parseDouble(latitude);


                    if (name.length()>0 && latitude.length()>0 && longitude.length()>0&&
                            city.length()>0 && state.length()>0 && country.length()>0&&
                            zip.length()>4) {
                        Trail updatedTrail = new Trail(name, latNum, longNum, city, state, country, zip);
                        databaseManager.updateTrail(_id, updatedTrail);

                        Intent main = new Intent(ViewTrail.this, TrailList.class)
                                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(main);
                    }else {
                        Toast.makeText(ViewTrail.this,
                                "Please enter values for each field",
                                Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(ViewTrail.this, "Please enter Numerical Values for Longitude and Latitude", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Delete method, calls the database manager's delete method
        // displays a confirmation
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(ViewTrail.this,
                        "Deleting " + trailName,
                        Toast.LENGTH_SHORT).show();
                databaseManager.deleteTrail(_id);

                Intent main = new Intent(ViewTrail.this, TrailList.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(main);
            }
        });

    }

    // Manipulation for the Map once created
    @Override
    public void onMapReady(GoogleMap googleMap) {
        trailMap = googleMap;

        // Getting the current location
        LatLng latLng = new LatLng(latNum,
                longNum);
        // Sets the mark's info window to unknown
        String trailInfo = trailName;
        String trailSnip = city + " " + state + ", " + zip + ", " + country + " ";

        trailMap.addMarker(new MarkerOptions()
                .position(latLng).title(trailInfo)
                .snippet(trailSnip)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

        // Move map camera
        trailMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        trailMap.animateCamera(CameraUpdateFactory.zoomTo(11));

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
                trailMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;
            case R.id.map_hybrid:
                trailMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true;
            case R.id.map_satellite:
                trailMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                return true;
            case R.id.map_terrain:
                trailMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                return true;
            case R.id.map_none:
                trailMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                return true;
            case R.id.map_about:
                Toast.makeText(ViewTrail.this, "This page information for a Trail. You can update or delete from this screen", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_home:
                // Returns to Main Menu
                Intent homeIntent = new Intent(ViewTrail.this, MainMenu.class);
                startActivity(homeIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
