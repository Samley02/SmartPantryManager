package com.samley.smartpantrymanager.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.samley.smartpantrymanager.model.PantryItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Data-access object for the pantry table. Provides the full CRUD required
 * by the brief (Section 3.2): Create, Read, Update, Delete.
 * Uses parameterised queries (no string concatenation of user input).
 */
public class PantryDataSource {

    private final DatabaseHelper helper;

    public PantryDataSource(Context context) {
        this.helper = new DatabaseHelper(context.getApplicationContext());
    }

    // CREATE
    public long insert(PantryItem item) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = toValues(item);
        long id = db.insert(DatabaseHelper.T_PANTRY, null, cv);
        db.close();
        return id;
    }

    // UPDATE
    public int update(PantryItem item) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = toValues(item);
        int rows = db.update(DatabaseHelper.T_PANTRY, cv,
                DatabaseHelper.P_ID + " = ?",
                new String[]{ String.valueOf(item.getId()) });
        db.close();
        return rows;
    }

    // DELETE
    public int delete(long id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int rows = db.delete(DatabaseHelper.T_PANTRY,
                DatabaseHelper.P_ID + " = ?",
                new String[]{ String.valueOf(id) });
        db.close();
        return rows;
    }

    // READ one
    public PantryItem findById(long id) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.query(DatabaseHelper.T_PANTRY, null,
                DatabaseHelper.P_ID + " = ?",
                new String[]{ String.valueOf(id) }, null, null, null);
        PantryItem item = null;
        if (c.moveToFirst()) {
            item = fromCursor(c);
        }
        c.close();
        db.close();
        return item;
    }

    // READ all
    public List<PantryItem> getAll() {
        List<PantryItem> list = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.query(DatabaseHelper.T_PANTRY, null, null, null, null, null,
                DatabaseHelper.P_NAME + " COLLATE NOCASE ASC");
        while (c.moveToNext()) {
            list.add(fromCursor(c));
        }
        c.close();
        db.close();
        return list;
    }

    private ContentValues toValues(PantryItem item) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.P_NAME, item.getName());
        cv.put(DatabaseHelper.P_QTY, item.getQuantity());
        cv.put(DatabaseHelper.P_UNIT, item.getUnit());
        cv.put(DatabaseHelper.P_EXPIRY, item.getExpiryDate());
        return cv;
    }

    private PantryItem fromCursor(Cursor c) {
        long id = c.getLong(c.getColumnIndexOrThrow(DatabaseHelper.P_ID));
        String name = c.getString(c.getColumnIndexOrThrow(DatabaseHelper.P_NAME));
        double qty = c.getDouble(c.getColumnIndexOrThrow(DatabaseHelper.P_QTY));
        String unit = c.getString(c.getColumnIndexOrThrow(DatabaseHelper.P_UNIT));
        String expiry = c.getString(c.getColumnIndexOrThrow(DatabaseHelper.P_EXPIRY));
        return new PantryItem(id, name, qty, unit, expiry);
    }
}
