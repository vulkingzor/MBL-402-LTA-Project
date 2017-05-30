package edu.phoenix.mbl402.LTA;

/**
 * Created by usyag on 5/29/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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

    // Method to add customers
    public void addUser(User user) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_FIRST, user.getFirstName());
        values.put(DatabaseHelper.COLUMN_LAST, user.getLastName());
        values.put(DatabaseHelper.COLUMN_EMAIL, user.getEmail());
        values.put(DatabaseHelper.COLUMN_PHONE, user.getPhoneNumber());
        database.insert(DatabaseHelper.TABLE_USERS, null, values);
    }

    // Method to fetch the database information
    public Cursor fetchUsers() {
        // Querying for all the data
        Cursor cursor = database.query(DatabaseHelper.TABLE_USERS, DatabaseHelper.ALL_COLUMNS_USER, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    // Method to update a user
    public int updateUser(long _id, User user) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_FIRST, user.getFirstName());
        values.put(DatabaseHelper.COLUMN_LAST, user.getLastName());
        values.put(DatabaseHelper.COLUMN_EMAIL, user.getEmail());
        values.put(DatabaseHelper.COLUMN_PHONE, user.getPhoneNumber());
        int i = database.update(DatabaseHelper.TABLE_USERS, values, DatabaseHelper.COLUMN_ID + " = " + _id, null);
        return i;
    }

    // Method to delete a user
    public void deleteUser(long _id) {
        database.delete(DatabaseHelper.TABLE_USERS, DatabaseHelper.COLUMN_ID + "=" + _id, null);
    }

    // Method to add trails
    public void addTrail(Trail trail) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_LAT, trail.getLatitude());
        values.put(DatabaseHelper.COLUMN_LONG, trail.getLongitude());
        values.put(DatabaseHelper.COLUMN_CITY, trail.getCity());
        values.put(DatabaseHelper.COLUMN_STATE, trail.getState());
        values.put(DatabaseHelper.COLUMN_COUNTRY, trail.getCountry());
        values.put(DatabaseHelper.COLUMN_ZIP, trail.getZip());;
        database.insert(DatabaseHelper.TABLE_USERS, null, values);
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
    public int updateUser(long _id, Trail trail) {
        ContentValues values = new ContentValues();
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

