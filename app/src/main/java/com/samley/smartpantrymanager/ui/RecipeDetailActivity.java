package com.samley.smartpantrymanager.ui;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.samley.smartpantrymanager.R;
import com.samley.smartpantrymanager.data.RecipeDataSource;
import com.samley.smartpantrymanager.model.Recipe;
import com.samley.smartpantrymanager.model.RecipeIngredient;

/**
 * Recipe Detail screen. Receives a recipe id via Intent extra and shows the
 * full ingredient list and the preparation method.
 */
public class RecipeDetailActivity extends AppCompatActivity {

    public static final String EXTRA_RECIPE_ID = "extra_recipe_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        long id = getIntent().getLongExtra(EXTRA_RECIPE_ID, -1);
        RecipeDataSource dataSource = new RecipeDataSource(this);
        Recipe recipe = dataSource.findById(id);
        if (recipe == null) {
            Toast.makeText(this, "Recipe not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setTitle(recipe.getName());
        TextView ingredientsView = findViewById(R.id.textIngredients);
        TextView stepsView = findViewById(R.id.textSteps);

        StringBuilder sb = new StringBuilder();
        for (RecipeIngredient ri : recipe.getIngredients()) {
            sb.append("\u2022 ")
                    .append(trimNumber(ri.getQuantity())).append(" ")
                    .append(ri.getUnit()).append(" ")
                    .append(ri.getName()).append("\n");
        }
        ingredientsView.setText(sb.toString().trim());
        stepsView.setText(recipe.getSteps());
    }

    private String trimNumber(double d) {
        if (d == Math.floor(d)) return String.valueOf((long) d);
        return String.valueOf(d);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}