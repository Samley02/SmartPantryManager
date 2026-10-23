package com.samley.smartpantrymanager.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Single on-device SQLite database for the app.
 * Creates three tables and seeds the recipe collection on first run.
 *
 * Tables:
 *   pantry            - the user's own ingredients (full CRUD from the UI)
 *   recipe            - the recipe collection (seeded once)
 *   recipe_ingredient - the required ingredients for each recipe (seeded once)
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "smart_pantry.db";
    public static final int DB_VERSION = 1;

    // pantry table
    public static final String T_PANTRY = "pantry";
    public static final String P_ID = "_id";
    public static final String P_NAME = "name";
    public static final String P_QTY = "quantity";
    public static final String P_UNIT = "unit";
    public static final String P_EXPIRY = "expiry_date";

    // recipe table
    public static final String T_RECIPE = "recipe";
    public static final String R_ID = "_id";
    public static final String R_NAME = "name";
    public static final String R_STEPS = "steps";

    // recipe_ingredient table
    public static final String T_RING = "recipe_ingredient";
    public static final String RI_ID = "_id";
    public static final String RI_RECIPE_ID = "recipe_id";
    public static final String RI_NAME = "name";
    public static final String RI_QTY = "quantity";
    public static final String RI_UNIT = "unit";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + T_PANTRY + " (" +
                P_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                P_NAME + " TEXT NOT NULL, " +
                P_QTY + " REAL NOT NULL, " +
                P_UNIT + " TEXT NOT NULL, " +
                P_EXPIRY + " TEXT)");

        db.execSQL("CREATE TABLE " + T_RECIPE + " (" +
                R_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                R_NAME + " TEXT NOT NULL, " +
                R_STEPS + " TEXT NOT NULL)");

        db.execSQL("CREATE TABLE " + T_RING + " (" +
                RI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                RI_RECIPE_ID + " INTEGER NOT NULL, " +
                RI_NAME + " TEXT NOT NULL, " +
                RI_QTY + " REAL NOT NULL, " +
                RI_UNIT + " TEXT NOT NULL, " +
                "FOREIGN KEY(" + RI_RECIPE_ID + ") REFERENCES " + T_RECIPE + "(" + R_ID + "))");

        RecipeSeeder.seed(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + T_RING);
        db.execSQL("DROP TABLE IF EXISTS " + T_RECIPE);
        db.execSQL("DROP TABLE IF EXISTS " + T_PANTRY);
        onCreate(db);
    }
}
