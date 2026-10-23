package com.samley.smartpantrymanager.model;

/**
 * A single ingredient the user currently has at home.
 * One PantryItem maps to one row in the 'pantry' table.
 */
public class PantryItem {

    private long id;          // primary key, -1 until saved
    private String name;      // e.g. "Tomatoes"
    private double quantity;  // e.g. 3
    private String unit;      // e.g. "pcs", "g", "ml"
    private String expiryDate;// optional, ISO yyyy-MM-dd, may be null/empty

    public PantryItem() {
        this.id = -1;
    }

    public PantryItem(long id, String name, double quantity, String unit, String expiryDate) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.expiryDate = expiryDate;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getQuantity() { return quantity; }
    public void setQuantity(double quantity) { this.quantity = quantity; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public String getExpiryDate() { return expiryDate; }
    public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }
}
