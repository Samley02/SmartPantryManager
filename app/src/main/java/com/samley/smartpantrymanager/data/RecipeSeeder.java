package com.samley.smartpantrymanager.data;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * Seeds the recipe collection on first run (18 recipes).
 * Called once from DatabaseHelper.onCreate().
 */
final class RecipeSeeder {

    private RecipeSeeder() { }

    static void seed(SQLiteDatabase db) {
        long id;
        id = recipe(db, "Scrambled Eggs", "1) Beat eggs with salt. 2) Melt butter in a pan on low heat. 3) Add eggs and stir gently until just set.");
        ing(db, id, "eggs", 3, "pcs"); ing(db, id, "butter", 10, "g"); ing(db, id, "salt", 1, "tsp");

        id = recipe(db, "Cheese Omelette", "1) Beat eggs. 2) Pour into hot buttered pan. 3) Add cheese, fold and serve.");
        ing(db, id, "eggs", 2, "pcs"); ing(db, id, "cheese", 30, "g"); ing(db, id, "butter", 10, "g");

        id = recipe(db, "Tomato Pasta", "1) Boil pasta until tender. 2) Simmer chopped tomatoes with garlic and oil. 3) Toss pasta in the sauce.");
        ing(db, id, "pasta", 200, "g"); ing(db, id, "tomatoes", 3, "pcs"); ing(db, id, "garlic", 2, "pcs"); ing(db, id, "olive oil", 2, "tbsp");

        id = recipe(db, "Garlic Butter Rice", "1) Cook rice. 2) Fry garlic in butter. 3) Stir garlic butter through the rice.");
        ing(db, id, "rice", 200, "g"); ing(db, id, "garlic", 2, "pcs"); ing(db, id, "butter", 20, "g");

        id = recipe(db, "Pancakes", "1) Mix flour, milk, egg and sugar into a batter. 2) Cook spoonfuls in a buttered pan until golden on both sides.");
        ing(db, id, "flour", 150, "g"); ing(db, id, "milk", 200, "ml"); ing(db, id, "eggs", 1, "pcs"); ing(db, id, "sugar", 2, "tbsp");

        id = recipe(db, "French Toast", "1) Whisk egg and milk. 2) Soak bread slices. 3) Fry in butter until golden.");
        ing(db, id, "bread", 2, "pcs"); ing(db, id, "eggs", 1, "pcs"); ing(db, id, "milk", 50, "ml"); ing(db, id, "butter", 10, "g");

        id = recipe(db, "Grilled Cheese Sandwich", "1) Butter bread. 2) Add cheese between slices. 3) Grill in a pan until golden and melted.");
        ing(db, id, "bread", 2, "pcs"); ing(db, id, "cheese", 40, "g"); ing(db, id, "butter", 10, "g");

        id = recipe(db, "Vegetable Stir Fry", "1) Heat oil. 2) Stir fry onion, carrot and pepper. 3) Season with soy sauce.");
        ing(db, id, "onion", 1, "pcs"); ing(db, id, "carrot", 2, "pcs"); ing(db, id, "pepper", 1, "pcs"); ing(db, id, "soy sauce", 2, "tbsp"); ing(db, id, "olive oil", 1, "tbsp");

        id = recipe(db, "Mashed Potatoes", "1) Boil potatoes until soft. 2) Mash with butter and milk. 3) Season with salt.");
        ing(db, id, "potatoes", 4, "pcs"); ing(db, id, "butter", 20, "g"); ing(db, id, "milk", 50, "ml"); ing(db, id, "salt", 1, "tsp");

        id = recipe(db, "Tomato Omelette", "1) Beat eggs. 2) Add chopped tomatoes and onion. 3) Cook in a buttered pan until set.");
        ing(db, id, "eggs", 2, "pcs"); ing(db, id, "tomatoes", 1, "pcs"); ing(db, id, "onion", 1, "pcs"); ing(db, id, "butter", 10, "g");

        id = recipe(db, "Fried Rice", "1) Fry onion and carrot. 2) Add cooked rice and egg. 3) Season with soy sauce and stir.");
        ing(db, id, "rice", 200, "g"); ing(db, id, "eggs", 1, "pcs"); ing(db, id, "onion", 1, "pcs"); ing(db, id, "carrot", 1, "pcs"); ing(db, id, "soy sauce", 2, "tbsp");

        id = recipe(db, "Cheesy Garlic Bread", "1) Mix butter and garlic. 2) Spread on bread. 3) Top with cheese and grill until melted.");
        ing(db, id, "bread", 2, "pcs"); ing(db, id, "garlic", 2, "pcs"); ing(db, id, "butter", 20, "g"); ing(db, id, "cheese", 30, "g");

        id = recipe(db, "Banana Smoothie", "1) Peel bananas. 2) Blend with milk and sugar until smooth.");
        ing(db, id, "banana", 2, "pcs"); ing(db, id, "milk", 250, "ml"); ing(db, id, "sugar", 1, "tbsp");

        id = recipe(db, "Boiled Egg Salad", "1) Boil eggs and chop. 2) Mix with onion and tomato. 3) Drizzle with oil and salt.");
        ing(db, id, "eggs", 2, "pcs"); ing(db, id, "onion", 1, "pcs"); ing(db, id, "tomatoes", 1, "pcs"); ing(db, id, "olive oil", 1, "tbsp"); ing(db, id, "salt", 1, "tsp");

        id = recipe(db, "Pasta with Garlic Oil", "1) Boil pasta. 2) Warm olive oil with garlic. 3) Toss pasta in the garlic oil with salt.");
        ing(db, id, "pasta", 200, "g"); ing(db, id, "garlic", 3, "pcs"); ing(db, id, "olive oil", 3, "tbsp"); ing(db, id, "salt", 1, "tsp");

        id = recipe(db, "Carrot Soup", "1) Soften onion and carrot. 2) Add water and simmer. 3) Blend and season with salt.");
        ing(db, id, "carrot", 4, "pcs"); ing(db, id, "onion", 1, "pcs"); ing(db, id, "butter", 10, "g"); ing(db, id, "salt", 1, "tsp");

        id = recipe(db, "Cheese Quesadilla", "1) Place cheese and onion on a tortilla. 2) Fold and fry until crisp and melted.");
        ing(db, id, "tortilla", 2, "pcs"); ing(db, id, "cheese", 50, "g"); ing(db, id, "onion", 1, "pcs");

        id = recipe(db, "Simple Buttered Toast", "1) Toast bread. 2) Spread with butter while warm.");
        ing(db, id, "bread", 2, "pcs"); ing(db, id, "butter", 10, "g");
    }

    private static long recipe(SQLiteDatabase db, String name, String steps) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.R_NAME, name);
        cv.put(DatabaseHelper.R_STEPS, steps);
        return db.insert(DatabaseHelper.T_RECIPE, null, cv);
    }

    private static void ing(SQLiteDatabase db, long recipeId, String name, double qty, String unit) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.RI_RECIPE_ID, recipeId);
        cv.put(DatabaseHelper.RI_NAME, name);
        cv.put(DatabaseHelper.RI_QTY, qty);
        cv.put(DatabaseHelper.RI_UNIT, unit);
        db.insert(DatabaseHelper.T_RING, null, cv);
    }
}
