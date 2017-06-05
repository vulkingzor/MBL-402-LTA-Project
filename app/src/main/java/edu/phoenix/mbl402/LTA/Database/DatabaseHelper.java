package edu.phoenix.mbl402.LTA.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database name
    static final String DB_NAME = "EXPEDITION.DB";
    // database version
    static final int DB_VERSION = 4;

    // Trails Table
    public static final String TABLE_TRAILS = "trails";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_LAT = "lat";
    public static final String COLUMN_LONG= "long";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_STATE= "state";
    public static final String COLUMN_COUNTRY= "country";
    public static final String COLUMN_ZIP= "zip";

    // All of the TRAIL table columns
    public static final String[] ALL_COLUMNS_TRAIL={

            DatabaseHelper.COLUMN_ID,
            DatabaseHelper.COLUMN_NAME,
            DatabaseHelper.COLUMN_LAT,
            DatabaseHelper.COLUMN_LONG,
            DatabaseHelper.COLUMN_CITY,
            DatabaseHelper.COLUMN_STATE,
            DatabaseHelper.COLUMN_COUNTRY,
            DatabaseHelper.COLUMN_ZIP
    };

    // Create Trail Table Query
    public static final String TABLE_CREATE_TRAILS =
            "CREATE TABLE " + TABLE_TRAILS + " (" + COLUMN_ID +
                    " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_LAT + " TEXT, " +
                    COLUMN_LONG  + " TEXT, " +
                    COLUMN_CITY  + " TEXT, " +
                    COLUMN_STATE  + " TEXT, " +
                    COLUMN_COUNTRY  + " TEXT, " +
                    COLUMN_ZIP + " TEXT" + ")";

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
        //db.execSQL(TABLE_CREATE_USERS);
    }

    // Call to update the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TRAIL_TABLE);
        db.execSQL(TABLE_CREATE_TRAILS);
    }

}