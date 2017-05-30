package edu.phoenix.mbl402.LTA;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database name
    static final String DB_NAME = "EXPEDITION.DB";
    // database version
    static final int DB_VERSION = 1;

    // User Table
    public static final String TABLE_USERS = "USERS";
    public static final String COLUMN_ID = "_ID";
    public static final String COLUMN_FIRST = "FIRST_NAME";
    public static final String COLUMN_LAST = "LAST_NAME";
    public static final String COLUMN_PHONE = "PHONE";
    public static final String COLUMN_EMAIL = "EMAIL";

    // All of the USER table columns
    public static final String[] ALL_COLUMNS_USER ={
            DatabaseHelper.COLUMN_ID,
            DatabaseHelper.COLUMN_FIRST,
            DatabaseHelper.COLUMN_LAST,
            DatabaseHelper.COLUMN_PHONE,
            DatabaseHelper.COLUMN_EMAIL
    };

    // Create User Table Query
    private static final String TABLE_CREATE_USERS =
            "CREATE TABLE " + TABLE_USERS + " (" + COLUMN_ID +
                    "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                   COLUMN_FIRST + " TEXT, " +
                    COLUMN_LAST  + " TEXT, " +
                    COLUMN_PHONE  + " TEXT, " +
                    COLUMN_EMAIL + " TEXT, " + ")";

    // Drop statement for the user table
    public static final String DROP_USER_TABLE = "DROP TABLE " + TABLE_USERS;

    // Trails Table
    public static final String TABLE_TRAILS = "TRAILS";
    public static final String COLUMN_LAT = "LAT";
    public static final String COLUMN_LONG= "LONG";
    public static final String COLUMN_CITY = "CITY";
    public static final String COLUMN_STATE= "STATE";
    public static final String COLUMN_COUNTRY= "COUNTRY";
    public static final String COLUMN_ZIP= "ZIP";

    // All of the TRAIL table columns
    public static final String[] ALL_COLUMNS_TRAIL={
            DatabaseHelper.COLUMN_ID,
            DatabaseHelper.COLUMN_LAT,
            DatabaseHelper.COLUMN_LONG,
            DatabaseHelper.COLUMN_CITY,
            DatabaseHelper.COLUMN_STATE,
            DatabaseHelper.COLUMN_COUNTRY,
            DatabaseHelper.COLUMN_ZIP
    };

    // Create Trail Table Query
    public static final String TABLE_CREATE_TRAILS =
            "CREATE TABLE " + TABLE_USERS + " (" + COLUMN_ID +
                    "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_LAT + " TEXT, " +
                    COLUMN_LONG  + " TEXT, " +
                    COLUMN_CITY  + " TEXT, " +
                    COLUMN_STATE  + " TEXT, " +
                    COLUMN_COUNTRY  + " TEXT, " +
                    COLUMN_ZIP + " TEXT, " + ")";

    // Drop statement for the trails table
    public static final String DROP_TRAIL_TABLE = "DROP TABLE " + TABLE_TRAILS;

    // Constructor for the help
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // Call to the create query
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_TRAILS);
        db.execSQL(TABLE_CREATE_USERS);
    }

    // Call to update the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TRAIL_TABLE);
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(TABLE_CREATE_TRAILS);
        db.execSQL(TABLE_CREATE_USERS);
    }

}