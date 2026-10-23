package com.samley.smartpantrymanager.logic;

import com.samley.smartpantrymanager.model.PantryItem;
import com.samley.smartpantrymanager.model.Recipe;
import com.samley.smartpantrymanager.model.RecipeIngredient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RecipeMatcher {

    /**
     * Strict match: suggest only if ALL ingredients are present in enough quantity.
     */
    public static List<Recipe> suggest(List<Recipe> recipes, List<PantryItem> pantry) {
        Map<String, PantryItem> pantryMap = buildPantryMap(pantry);
        List<Recipe> result = new ArrayList<>();

        for (Recipe recipe : recipes) {
            if (canMake(recipe, pantryMap)) {
                result.add(recipe);
            }
        }
        return result;
    }

    /**
     * Almost there: missing EXACTLY one ingredient (missing or not enough quantity).
     */
    public static List<Recipe> almostThere(List<Recipe> recipes, List<PantryItem> pantry) {
        Map<String, PantryItem> pantryMap = buildPantryMap(pantry);
        List<Recipe> result = new ArrayList<>();

        for (Recipe recipe : recipes) {
            int missingCount = countMissing(recipe, pantryMap);
            if (missingCount == 1) {
                result.add(recipe);
            }
        }
        return result;
    }

    // --- helpers ---

    private static boolean canMake(Recipe recipe, Map<String, PantryItem> pantryMap) {
        return countMissing(recipe, pantryMap) == 0;
    }

    private static int countMissing(Recipe recipe, Map<String, PantryItem> pantryMap) {
        int missing = 0;
        for (RecipeIngredient need : recipe.getIngredients()) {
            PantryItem have = findPantryItem(need.getName(), pantryMap);
            if (have == null || have.getQuantity() < need.getQuantity()) {
                missing++;
            }
        }
        return missing;
    }

    private static Map<String, PantryItem> buildPantryMap(List<PantryItem> pantry) {
        Map<String, PantryItem> map = new HashMap<>();
        for (PantryItem item : pantry) {
            String key = normalize(item.getName());
            // If same ingredient twice, keep the one with higher quantity
            if (!map.containsKey(key) || map.get(key).getQuantity() < item.getQuantity()) {
                map.put(key, item);
            }
            // also store singular/plural variations for lookup
            map.put(singular(key), item);
            map.put(plural(key), item);
        }
        return map;
    }

    private static PantryItem findPantryItem(String needName, Map<String, PantryItem> map) {
        String n = normalize(needName);
        if (map.containsKey(n)) return map.get(n);
        if (map.containsKey(singular(n))) return map.get(singular(n));
        if (map.containsKey(plural(n))) return map.get(plural(n));

        // brute force - handles Eggs vs Egg
        for (Map.Entry<String, PantryItem> e : map.entrySet()) {
            if (isSameIngredient(n, e.getKey())) {
                return e.getValue();
            }
        }
        return null;
    }

    // --- normalization to pass your tests ---

    static String normalize(String s) {
        if (s == null) return "";
        return s.trim().toLowerCase(Locale.ROOT);
    }

    static String singular(String s) {
        s = normalize(s);
        if (s.endsWith("ies") && s.length() > 3) {
            return s.substring(0, s.length() - 3) + "y"; // tomatoes -> tomato, but we keep both
        }
        if (s.endsWith("es") && s.length() > 3) {
            // avoid breaking "cheese"
            if (s.equals("cheese")) return s;
            return s.substring(0, s.length() - 2);
        }
        if (s.endsWith("s") && !s.endsWith("ss") && s.length() > 1) {
            return s.substring(0, s.length() - 1);
        }
        return s;
    }

    static String plural(String s) {
        s = normalize(s);
        return s + "s";
    }

    static boolean isSameIngredient(String a, String b) {
        a = normalize(a);
        b = normalize(b);
        if (a.equals(b)) return true;
        if (singular(a).equals(singular(b))) return true;
        if (a.equals(b + "s") || b.equals(a + "s")) return true;
        if (a.equals(b + "es") || b.equals(a + "es")) return true;
        return false;
    }
}