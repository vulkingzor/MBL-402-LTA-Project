package edu.phoenix.mbl402.LTA.Activities;

// Creators: Learning Team A
// Created Date: 5/21/2017
// Class Description: Main menu Activity, uses the users preferences. Hub for other activities

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import edu.phoenix.mbl402.LTA.Models.AppPreferences;
import edu.phoenix.mbl402.LTA.Database.DatabaseManager;
import edu.phoenix.mbl402.LTA.R;
import edu.phoenix.mbl402.LTA.Models.Trail;

public class MainMenu extends AppCompatActivity {

    // App Preferences and keys to data retrieval
    SharedPreferences appPref;

    // TextView at the top of the page
    TextView titleTV;

    // UI Buttons
    Button globalMapBtn;
    Button trailMapBtn;
    Button userProfileBtn;

    // Database manager to check if data exists
    DatabaseManager databaseManager;
    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // Opening Database connection to create database then closing
        databaseManager = new DatabaseManager(this);
        databaseManager.open();
        databaseManager.closeDB();


        // Setting up the UI elements
        titleTV = (TextView)findViewById(R.id.txtMainTagline);
        globalMapBtn = (Button)findViewById(R.id.btnGlobalMap);
        trailMapBtn = (Button)findViewById(R.id.btnViewMap);
        userProfileBtn = (Button)findViewById(R.id.btnUser);

        // Getting the shared preference. If user name is found the page title will call their name
        appPref = getSharedPreferences(AppPreferences.prefName, Context.MODE_PRIVATE);
        if(appPref.contains(AppPreferences.firstKey)){
            String name = appPref.getString(AppPreferences.firstKey, "");

            if(name != null) {
                titleTV.setText("Welcome " + name + ". Ready for an Expedition?");
            }
        }

        // Buttons change the activity to their respective pages
        globalMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(MainMenu.this, MapActivity.class);
                startActivity(mapIntent);
            }
        });

        trailMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent trailIntent = new Intent(MainMenu.this, TrailList.class);
                startActivity(trailIntent);
            }
        });

        userProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userIntent = new Intent(MainMenu.this, UserProfile.class);
                startActivity(userIntent);
            }
        });
    }

}
