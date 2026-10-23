package com.samley.smartpantrymanager.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.samley.smartpantrymanager.model.Recipe;
import com.samley.smartpantrymanager.model.RecipeIngredient;

import java.util.ArrayList;
import java.util.List;

/**
 * Read-only data-access object for the seeded recipe collection.
 * Loads each recipe together with its required ingredients.
 */
public class RecipeDataSource {

    private final DatabaseHelper helper;

    public RecipeDataSource(Context context) {
        this.helper = new DatabaseHelper(context.getApplicationContext());
    }

    public List<Recipe> getAll() {
        List<Recipe> recipes = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor rc = db.query(DatabaseHelper.T_RECIPE, null, null, null, null, null,
                DatabaseHelper.R_NAME + " ASC");
        while (rc.moveToNext()) {
            long id = rc.getLong(rc.getColumnIndexOrThrow(DatabaseHelper.R_ID));
            String name = rc.getString(rc.getColumnIndexOrThrow(DatabaseHelper.R_NAME));
            String steps = rc.getString(rc.getColumnIndexOrThrow(DatabaseHelper.R_STEPS));
            Recipe recipe = new Recipe(id, name, steps);
            loadIngredients(db, recipe);
            recipes.add(recipe);
        }
        rc.close();
        db.close();
        return recipes;
    }

    public Recipe findById(long recipeId) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor rc = db.query(DatabaseHelper.T_RECIPE, null,
                DatabaseHelper.R_ID + " = ?",
                new String[]{ String.valueOf(recipeId) }, null, null, null);
        Recipe recipe = null;
        if (rc.moveToFirst()) {
            String name = rc.getString(rc.getColumnIndexOrThrow(DatabaseHelper.R_NAME));
            String steps = rc.getString(rc.getColumnIndexOrThrow(DatabaseHelper.R_STEPS));
            recipe = new Recipe(recipeId, name, steps);
            loadIngredients(db, recipe);
        }
        rc.close();
        db.close();
        return recipe;
    }

    private void loadIngredients(SQLiteDatabase db, Recipe recipe) {
        Cursor ic = db.query(DatabaseHelper.T_RING, null,
                DatabaseHelper.RI_RECIPE_ID + " = ?",
                new String[]{ String.valueOf(recipe.getId()) }, null, null, null);
        while (ic.moveToNext()) {
            long id = ic.getLong(ic.getColumnIndexOrThrow(DatabaseHelper.RI_ID));
            String name = ic.getString(ic.getColumnIndexOrThrow(DatabaseHelper.RI_NAME));
            double qty = ic.getDouble(ic.getColumnIndexOrThrow(DatabaseHelper.RI_QTY));
            String unit = ic.getString(ic.getColumnIndexOrThrow(DatabaseHelper.RI_UNIT));
            recipe.addIngredient(new RecipeIngredient(id, recipe.getId(), name, qty, unit));
        }
        ic.close();
    }
}