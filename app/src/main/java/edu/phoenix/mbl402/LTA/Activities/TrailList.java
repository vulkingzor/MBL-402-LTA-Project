package edu.phoenix.mbl402.LTA.Activities;

// Creators: Learning Team A
// Created Date: 5/21/2017
// Class Description: Activity to show all the trails in the database

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import edu.phoenix.mbl402.LTA.Database.DatabaseHelper;
import edu.phoenix.mbl402.LTA.Database.DatabaseManager;
import edu.phoenix.mbl402.LTA.R;

public class TrailList extends AppCompatActivity {

    // UI Elements
    private Button addButton;
    private ListView trailLV;

    // Adapter for the Database information
    private SimpleCursorAdapter adapter;

    // Call to the Database Manager Class
    private DatabaseManager databaseManager;
    Cursor cursor;

    // An array of the table columns
    final String[] from = DatabaseHelper.ALL_COLUMNS_TRAIL;
    // Array of the UI elements each column should map to
    final int[] to = new int[]{R.id.txt_id, R.id.txt_name, R.id.txt_lat, R.id.txt_long, R.id.txt_city, R.id.txt_state,
            R.id.txt_country, R.id.txt_zip};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trail_list);

        // Opening Database connection
        databaseManager = new DatabaseManager(this);
        databaseManager.open();
        cursor = databaseManager.fetchTrails();

        //Setting up the list view. Has a default if list is empty
        trailLV = (ListView) findViewById(R.id.lv_trails);
        trailLV.setEmptyView(findViewById(R.id.empty));

        // Setting up the SimpleCursorAdapter for the list view
        adapter = new SimpleCursorAdapter(this, R.layout.trail_row, cursor,
                from, to, 0);
        adapter.notifyDataSetChanged();

        // Setting adapter to listview
        trailLV.setAdapter(adapter);


        trailLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {

                // Marking each text view on the row
                TextView idTV = (TextView) view.findViewById(R.id.txt_id);
                TextView nameTV = (TextView) view.findViewById(R.id.txt_name);
                TextView latTV = (TextView) view.findViewById(R.id.txt_lat);
                TextView longTV = (TextView) view.findViewById(R.id.txt_long);
                TextView cityTV = (TextView) view.findViewById(R.id.txt_city);
                TextView stateTV = (TextView) view.findViewById(R.id.txt_state);
                TextView countryTV = (TextView) view.findViewById(R.id.txt_country);
                TextView zipTV = (TextView) view.findViewById(R.id.txt_zip);

                // Getting the values to pass them to the next activity
                String id = idTV.getText().toString();
                String name = nameTV.getText().toString();
                String latNum = latTV.getText().toString();
                String longNum = longTV.getText().toString();
                String city = cityTV.getText().toString();
                String state = stateTV.getText().toString();
                String country = countryTV.getText().toString();
                String zip = zipTV.getText().toString();

                // Passing the row values to the viewing page
                Intent viewTrail = new Intent(TrailList.this, ViewTrail.class);
                viewTrail.putExtra("id", id);
                viewTrail.putExtra("name", name);
                viewTrail.putExtra("lat", latNum);
                viewTrail.putExtra("long", longNum);
                viewTrail.putExtra("city", city);
                viewTrail.putExtra("state", state);
                viewTrail.putExtra("country", country);
                viewTrail.putExtra("zip", zip);

                // Starting the View Customer Activity
                startActivity(viewTrail);
            }
        });

        // Setting up the Add button and actions
        addButton = (Button) findViewById(R.id.button_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newCustomer = new Intent(TrailList.this, AddTrail.class);
                startActivity(newCustomer);
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
                Intent homeIntent = new Intent(TrailList.this, MainMenu.class);
                startActivity(homeIntent);
                return true;
            case R.id.menu_about:
                // Shows information about the page
                Toast.makeText(TrailList.this, "This page is a list of all logged trails. Click on one for more information", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }

}
