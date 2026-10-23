package com.samley.smartpantrymanager.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.samley.smartpantrymanager.model.PantryItem;
import com.samley.smartpantrymanager.model.Recipe;
import com.samley.smartpantrymanager.model.RecipeIngredient;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Unit tests proving the strict-matching rule.
 * These run locally on the JVM: Run > app/test.
 */
public class RecipeMatcherTest {

    private Recipe omelette() {
        Recipe r = new Recipe(1, "Cheese Omelette", "...");
        r.addIngredient(new RecipeIngredient("eggs", 2, "pcs"));
        r.addIngredient(new RecipeIngredient("cheese", 30, "g"));
        return r;
    }

    @Test
    public void suggested_whenAllIngredientsPresentInEnoughQuantity() {
        List<PantryItem> pantry = Arrays.asList(
                new PantryItem(1, "Eggs", 3, "pcs", null),
                new PantryItem(2, "Cheese", 50, "g", null));
        List<Recipe> result = RecipeMatcher.suggest(
                new ArrayList<>(Arrays.asList(omelette())), pantry);
        assertEquals(1, result.size());
    }

    @Test
    public void excluded_whenOneIngredientMissing() {
        List<PantryItem> pantry = Arrays.asList(
                new PantryItem(1, "eggs", 3, "pcs", null));
        List<Recipe> result = RecipeMatcher.suggest(
                new ArrayList<>(Arrays.asList(omelette())), pantry);
        assertTrue(result.isEmpty());
    }

    @Test
    public void excluded_whenQuantityTooLow() {
        List<PantryItem> pantry = Arrays.asList(
                new PantryItem(1, "egg", 1, "pcs", null),
                new PantryItem(2, "cheese", 50, "g", null));
        List<Recipe> result = RecipeMatcher.suggest(
                new ArrayList<>(Arrays.asList(omelette())), pantry);
        assertTrue(result.isEmpty());
    }

    @Test
    public void pluralAndCaseAreTreatedAsEqual() {
        PantryItem a = new PantryItem(1, "Tomatoes", 3, "pcs", null);
        List<PantryItem> pantry = Arrays.asList(a,
                new PantryItem(2, "egg", 2, "pcs", null),
                new PantryItem(3, "cheese", 30, "g", null));
        Recipe r = omelette();
        assertTrue(RecipeMatcher.suggest(
                new ArrayList<>(Arrays.asList(r)), pantry).size() == 1);
    }

    @Test
    public void almostThere_listsRecipesMissingExactlyOne() {
        List<PantryItem> pantry = Arrays.asList(
                new PantryItem(1, "eggs", 3, "pcs", null));
        List<Recipe> almost = RecipeMatcher.almostThere(
                new ArrayList<>(Arrays.asList(omelette())), pantry);
        assertEquals(1, almost.size());
        assertFalse(RecipeMatcher.suggest(
                new ArrayList<>(Arrays.asList(omelette())), pantry).size() == 1);
    }
}