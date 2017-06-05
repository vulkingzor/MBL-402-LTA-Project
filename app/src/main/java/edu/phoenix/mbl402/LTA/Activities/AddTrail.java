package edu.phoenix.mbl402.LTA.Activities;

// Creators: Learning Team A
// Created Date: 5/21/2017
// Class Description: Activity to Add trails to the database

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.phoenix.mbl402.LTA.Database.DatabaseManager;
import edu.phoenix.mbl402.LTA.Models.Trail;
import edu.phoenix.mbl402.LTA.R;

public class AddTrail extends AppCompatActivity {

    // UI Buttons
    private Button saveButton;
    private Button cancelButton;

    // UI Text Fields
    private EditText nameET;
    private EditText latET;
    private EditText longET;
    private EditText cityET;
    private EditText stateET;
    private EditText countryET;
    private EditText zipET;

    // Database manager for access to DB methods
    private DatabaseManager databaseManager;

    // Numerical values for longitude and latitude
    private double latNum;
    private double longNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trail);

        // All of the edit text fields
        nameET = (EditText) findViewById(R.id.edit_name);
        latET = (EditText) findViewById(R.id.edit_lat);
        longET = (EditText) findViewById(R.id.edit_long);
        cityET = (EditText) findViewById(R.id.edit_city);
        stateET = (EditText) findViewById(R.id.edit_state);
        countryET = (EditText) findViewById(R.id.edit_country);
        zipET = (EditText) findViewById(R.id.edit_zip);

        saveButton = (Button)findViewById(R.id.button_save);
        cancelButton = (Button)findViewById(R.id.button_cancel);

        // Database manager and opening the database
        databaseManager = new DatabaseManager(this);
        databaseManager.open();


        // Update button method, calls the database manager's update call
        // Validates the entries are all filled in by user
        saveButton.setOnClickListener(new View.OnClickListener() {
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
                    // Attempting to Parse the Numbers
                    longNum = Double.parseDouble(longitude);
                    latNum = Double.parseDouble(latitude);

                    // Checking if Lat/Long valid, then checking if other entries are completed
                    if(longNum >= -180 && longNum <= 180 && latNum >= -90 && latNum <= 90){
                        if (name.length()>0 && latitude.length()>0 && longitude.length()>0&&
                                city.length()>0 && state.length()>0 && country.length()>0&&
                                zip.length()>4) {

                            // Creating a new trail
                            Trail newTrail = new Trail(name, latNum, longNum, city, state, country, zip);
                            databaseManager.addTrail(newTrail);

                            // Going back to the list
                            Intent main = new Intent(AddTrail.this, TrailList.class)
                                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(main);
                        }else {
                            // Error message if not complete
                            Toast.makeText(AddTrail.this,
                                    "Please enter values for each field",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Error if lat/long incorrect
                        Toast.makeText(AddTrail.this,
                                "Please Enter a Valid Longitude (-180 to 180) and Latitude (-90 to 90)",
                                Toast.LENGTH_SHORT).show();
                    }
                    // Handling if the Number Parse fails
                }catch (Exception e){
                    Toast.makeText(AddTrail.this, "Please enter Numerical Values for Longitude and Latitude", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Returns to List
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnList = new Intent(AddTrail.this, TrailList.class);
                startActivity(returnList);
            }
        });
    }

    // Creating the Menu Options
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // Menu actions
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                // Returns to Main Menu
                Intent homeIntent = new Intent(AddTrail.this, MainMenu.class);
                startActivity(homeIntent);
                return true;
            case R.id.menu_about:
                // Shows information about the page
                Toast.makeText(AddTrail.this, "This pages allows you to add a Trail. Click Save when complete", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }
}
