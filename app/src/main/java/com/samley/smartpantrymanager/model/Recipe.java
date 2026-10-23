package com.samley.smartpantrymanager.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A recipe: a name, preparation steps, and a list of required ingredients.
 * Maps to one row in the 'recipe' table plus several rows in 'recipe_ingredient'.
 */
public class Recipe {

    private long id;
    private String name;
    private String steps;   // simple preparation method
    private final List<RecipeIngredient> ingredients = new ArrayList<>();

    public Recipe(long id, String name, String steps) {
        this.id = id;
        this.name = name;
        this.steps = steps;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSteps() { return steps; }
    public void setSteps(String steps) { this.steps = steps; }

    public List<RecipeIngredient> getIngredients() { return ingredients; }
    public void addIngredient(RecipeIngredient ri) { ingredients.add(ri); }
}