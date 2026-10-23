package com.samley.smartpantrymanager.logic;


import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Makes ingredient matching robust to trivial real-world messiness, as required by
 * the brief (Section 2.3): case, extra spaces, singular/plural (tomato vs tomatoes),
 * and a few common unit aliases. This is deliberately NOT a full NLP solution -
 * just enough normalization so a sensible match does not break on "Tomato" vs "tomatoes".
 */
public final class IngredientNormalizer {

    private IngredientNormalizer() { }

    // Simple unit aliases mapped to a canonical unit.
    private static final Map<String, String> UNIT_ALIASES = new HashMap<>();
    static {
        UNIT_ALIASES.put("piece", "pcs");
        UNIT_ALIASES.put("pieces", "pcs");
        UNIT_ALIASES.put("pc", "pcs");
        UNIT_ALIASES.put("pcs", "pcs");
        UNIT_ALIASES.put("gram", "g");
        UNIT_ALIASES.put("grams", "g");
        UNIT_ALIASES.put("g", "g");
        UNIT_ALIASES.put("kilogram", "kg");
        UNIT_ALIASES.put("kg", "kg");
        UNIT_ALIASES.put("millilitre", "ml");
        UNIT_ALIASES.put("milliliter", "ml");
        UNIT_ALIASES.put("ml", "ml");
        UNIT_ALIASES.put("litre", "l");
        UNIT_ALIASES.put("liter", "l");
        UNIT_ALIASES.put("l", "l");
        UNIT_ALIASES.put("tablespoon", "tbsp");
        UNIT_ALIASES.put("tbsp", "tbsp");
        UNIT_ALIASES.put("teaspoon", "tsp");
        UNIT_ALIASES.put("tsp", "tsp");
    }

    /**
     * Normalize an ingredient NAME for comparison:
     * lower-case, trimmed, collapsed spaces, and a naive singular form
     * (strips a trailing 's' / 'es' so "tomatoes" -> "tomato").
     */
    public static String name(String raw) {
        if (raw == null) return "";
        String s = raw.toLowerCase(Locale.ROOT).trim().replaceAll("\\s+", " ");
        return singular(s);
    }

    private static String singular(String s) {
        if (s.endsWith("ies") && s.length() > 3) {
            // berries -> berry
            return s.substring(0, s.length() - 3) + "y";
        }
        if (s.endsWith("es") && s.length() > 2) {
            // tomatoes -> tomato, boxes -> box
            return s.substring(0, s.length() - 2);
        }
        if (s.endsWith("s") && s.length() > 1) {
            // eggs -> egg
            return s.substring(0, s.length() - 1);
        }
        return s;
    }

    /** Normalize a UNIT string to a canonical form. */
    public static String unit(String raw) {
        if (raw == null) return "";
        String s = raw.toLowerCase(Locale.ROOT).trim();
        return UNIT_ALIASES.getOrDefault(s, s);
    }
}