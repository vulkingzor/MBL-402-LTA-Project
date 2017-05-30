package edu.phoenix.mbl402.LTA;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class UserProfile extends AppCompatActivity {

    Button saveButton;
    Button cancelButton;
    PopupWindow popWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        saveButton = (Button) findViewById(R.id.buttonSave);
        cancelButton = (Button) findViewById(R.id.buttonCancel);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserProfile.this, "Save Info", Toast.LENGTH_SHORT).show();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserProfile.this, "Cancel", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                Intent homeIntent = new Intent(UserProfile.this, MainMenu.class);
                startActivity(homeIntent);
                return true;
            case R.id.menu_about:
                aboutPopup();
                return true;
        }
        return false;
    }

    // Creates an about page popup for the user's information
    public void aboutPopup() {
        try {
            // Getting the layout inflater
            LayoutInflater inflater = (LayoutInflater) UserProfile.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // Getting the popup layout
            View layout = inflater.inflate(R.layout.popup,
                    (ViewGroup) findViewById(R.id.popup_element));

            // creating the popup window params
            popWindow = new PopupWindow(layout, LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT, true);

            // Showing popup at the center
            popWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

            // Setting the text
            TextView popText = (TextView) layout.findViewById(R.id.text_message);
            popText.setText("This page is your user profile, please enter your information");
            Button cancelButton = (Button) layout.findViewById(R.id.button_cancel);
            cancelButton.setOnClickListener(cancel_button_click_listener);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private View.OnClickListener cancel_button_click_listener = new View.OnClickListener() {
        public void onClick(View v) {
            popWindow.dismiss();
        }
    };



}