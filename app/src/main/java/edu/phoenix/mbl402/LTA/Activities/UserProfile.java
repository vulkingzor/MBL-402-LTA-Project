package edu.phoenix.mbl402.LTA.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.phoenix.mbl402.LTA.Models.AppPreferences;
import edu.phoenix.mbl402.LTA.R;

public class UserProfile extends AppCompatActivity {

    Button saveBtn;
    Button cancelBtn;
    Button clearBtn;

    EditText firstNameET;
    EditText lastNameET;
    EditText phoneET;
    EditText emailET;

    // App Preferences and keys to data retrieval
    SharedPreferences appPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Getting all UI Elements
        firstNameET = (EditText)findViewById(R.id.edit_firstname);
        lastNameET = (EditText)findViewById(R.id.edit_lastname);
        phoneET = (EditText)findViewById(R.id.edit_phone);
        emailET = (EditText)findViewById(R.id.edit_email);

        saveBtn = (Button) findViewById(R.id.button_save);
        cancelBtn = (Button) findViewById(R.id.button_cancel);
        clearBtn = (Button)findViewById(R.id.button_clear);

        // Checking if any preferences are existing
        appPref = getSharedPreferences(AppPreferences.prefName, Context.MODE_PRIVATE);
        if(appPref.contains(AppPreferences.firstKey)){
            firstNameET.setText(appPref.getString(AppPreferences.firstKey, ""));
        }
        if(appPref.contains(AppPreferences.lastKey)){
            lastNameET.setText(appPref.getString(AppPreferences.lastKey, ""));
        }
        if(appPref.contains(AppPreferences.phoneKey)){
            phoneET.setText(appPref.getString(AppPreferences.phoneKey, ""));
        }
        if(appPref.contains(AppPreferences.emailKey)) {
            emailET.setText(appPref.getString(AppPreferences.emailKey, ""));
        }

        // Saves the preferences and moves the user back to the main menu
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameET.getText().toString();
                String lastName = lastNameET.getText().toString();
                String phoneNum = phoneET.getText().toString();
                String email = emailET.getText().toString();

                if (firstName.length() > 0 && lastName.length() > 0 && phoneNum.length() > 9 &&
                        email.length() > 0 ) {

                    savePref(firstName, lastName, email, phoneNum);

                    Intent main = new Intent(UserProfile.this, MainMenu.class);
                    startActivity(main);
                } else {
                    Toast.makeText(UserProfile.this,
                            "Please enter values for each field",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Clears all of the preferences and returns the user to the main menu
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearPref();
                Intent main = new Intent(UserProfile.this, MainMenu.class);
                startActivity(main);
            }
        });

        // Cancels the page and returns the user to the main menu
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnMain = new Intent(UserProfile.this, MainMenu.class);
                startActivity(returnMain);
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
                Intent homeIntent = new Intent(UserProfile.this, MainMenu.class);
                startActivity(homeIntent);
                return true;
            case R.id.menu_about:
                // Shows information about the page
                Toast.makeText(UserProfile.this, "This page is your user profile, please enter your information", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }

    // Method to save the preferences
    public void savePref(String first, String last, String email, String phone){

        SharedPreferences.Editor editor = appPref.edit();
        editor.putString(AppPreferences.firstKey, first);
        editor.putString(AppPreferences.lastKey, last);
        editor.putString(AppPreferences.emailKey, email);
        editor.putString(AppPreferences.phoneKey, phone);
        editor.apply();

    }

    // Method to clear the preferences
    public void clearPref(){
        SharedPreferences.Editor editor = appPref.edit();
        editor.putString(AppPreferences.firstKey, null);
        editor.putString(AppPreferences.lastKey, null);
        editor.putString(AppPreferences.emailKey, null);
        editor.putString(AppPreferences.phoneKey, null);
        editor.commit();
    }
}