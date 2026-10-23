package com.samley.smartpantrymanager.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.samley.smartpantrymanager.R;
import com.samley.smartpantrymanager.data.PantryDataSource;
import com.samley.smartpantrymanager.model.PantryItem;

/**
 * Add/Edit Ingredient screen. Receives an optional id via Intent extra:
 *   no id  -> create a new pantry item
 *   id set -> load and update the existing item
 * Performs input validation before saving.
 */
public class AddEditIngredientActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "extra_pantry_id";

    private PantryDataSource dataSource;
    private long editingId = -1;

    private EditText editName, editQty, editUnit, editExpiry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_ingredient);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        dataSource = new PantryDataSource(this);
        editName = findViewById(R.id.editName);
        editQty = findViewById(R.id.editQty);
        editUnit = findViewById(R.id.editUnit);
        editExpiry = findViewById(R.id.editExpiry);
        Button save = findViewById(R.id.buttonSave);

        editingId = getIntent().getLongExtra(EXTRA_ID, -1);
        if (editingId != -1) {
            setTitle("Edit Ingredient");
            loadExisting();
        } else {
            setTitle("Add Ingredient");
        }

        save.setOnClickListener(v -> save());
    }

    private void loadExisting() {
        PantryItem item = dataSource.findById(editingId);
        if (item == null) {
            Toast.makeText(this, "Item not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        editName.setText(item.getName());
        editQty.setText(String.valueOf(item.getQuantity()));
        editUnit.setText(item.getUnit());
        editExpiry.setText(item.getExpiryDate());
    }

    private void save() {
        String name = editName.getText().toString().trim();
        String qtyText = editQty.getText().toString().trim();
        String unit = editUnit.getText().toString().trim();
        String expiry = editExpiry.getText().toString().trim();

        // --- Input validation ---
        boolean valid = true;
        if (TextUtils.isEmpty(name)) {
            editName.setError("Name is required");
            valid = false;
        }
        if (TextUtils.isEmpty(unit)) {
            editUnit.setError("Unit is required (e.g. pcs, g, ml)");
            valid = false;
        }
        double qty = 0;
        if (TextUtils.isEmpty(qtyText)) {
            editQty.setError("Quantity is required");
            valid = false;
        } else {
            try {
                qty = Double.parseDouble(qtyText);
                if (qty <= 0) {
                    editQty.setError("Quantity must be greater than 0");
                    valid = false;
                }
            } catch (NumberFormatException e) {
                editQty.setError("Enter a valid number");
                valid = false;
            }
        }
        if (!valid) return;

        PantryItem item = new PantryItem();
        item.setName(name);
        item.setQuantity(qty);
        item.setUnit(unit);
        item.setExpiryDate(expiry);

        if (editingId == -1) {
            dataSource.insert(item);
            Toast.makeText(this, "Ingredient added", Toast.LENGTH_SHORT).show();
        } else {
            item.setId(editingId);
            dataSource.update(item);
            Toast.makeText(this, "Ingredient updated", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}