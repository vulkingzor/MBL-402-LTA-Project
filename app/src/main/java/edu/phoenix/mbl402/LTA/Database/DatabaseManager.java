package edu.phoenix.mbl402.LTA.Database;

/**
 * Created by usyag on 5/29/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import edu.phoenix.mbl402.LTA.Models.Trail;

public class DatabaseManager {

    // Calling the Database Helper Class
    private DatabaseHelper helper;

    // Application Context
    private Context context;

    // The database
    private SQLiteDatabase database;

    // Constructor for the Manager
    public DatabaseManager(Context c) {
        context = c;
    }

    // Opening the Database
    public DatabaseManager open() throws SQLException {
        helper = new DatabaseHelper(context);
        database = helper.getWritableDatabase();
        return this;
    }

    // Method to close the Database
    public void closeDB() {
        helper.close();
    }

    // Method to add trails
    public void addTrail(Trail trail) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, trail.getName());
        values.put(DatabaseHelper.COLUMN_LAT, trail.getLatitude());
        values.put(DatabaseHelper.COLUMN_LONG, trail.getLongitude());
        values.put(DatabaseHelper.COLUMN_CITY, trail.getCity());
        values.put(DatabaseHelper.COLUMN_STATE, trail.getState());
        values.put(DatabaseHelper.COLUMN_COUNTRY, trail.getCountry());
        values.put(DatabaseHelper.COLUMN_ZIP, trail.getZip());;
        database.insert(DatabaseHelper.TABLE_TRAILS, null, values);
    }

    // Method to fetch the database information
    public Cursor fetchTrails() {
        // Querying for all the data
        Cursor cursor = database.query(DatabaseHelper.TABLE_TRAILS, DatabaseHelper.ALL_COLUMNS_TRAIL, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    // Method to update a trail
    public int updateTrail(long _id, Trail trail) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, trail.getName());
        values.put(DatabaseHelper.COLUMN_LAT, trail.getLatitude());
        values.put(DatabaseHelper.COLUMN_LONG, trail.getLongitude());
        values.put(DatabaseHelper.COLUMN_CITY, trail.getCity());
        values.put(DatabaseHelper.COLUMN_STATE, trail.getState());
        values.put(DatabaseHelper.COLUMN_COUNTRY, trail.getCountry());
        values.put(DatabaseHelper.COLUMN_ZIP, trail.getZip());
        int i = database.update(DatabaseHelper.TABLE_TRAILS, values, DatabaseHelper.COLUMN_ID + " = " + _id, null);
        return i;
    }

    // Method to delete a trail
    public void deleteTrail(long _id) {
        database.delete(DatabaseHelper.TABLE_TRAILS, DatabaseHelper.COLUMN_ID + "=" + _id, null);
    }
}

