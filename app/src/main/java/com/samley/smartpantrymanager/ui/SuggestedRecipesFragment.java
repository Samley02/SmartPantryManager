package com.samley.smartpantrymanager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.samley.smartpantrymanager.R;
import com.samley.smartpantrymanager.adapter.RecipeAdapter;
import com.samley.smartpantrymanager.data.PantryDataSource;
import com.samley.smartpantrymanager.data.RecipeDataSource;
import com.samley.smartpantrymanager.logic.RecipeMatcher;
import com.samley.smartpantrymanager.model.Recipe;

import java.util.List;

/**
 * Suggested Recipes screen. Runs the strict-matching logic (RecipeMatcher) against
 * the current pantry and shows ONLY recipes the user can make right now.
 * Shows a friendly empty message when nothing matches.
 */
public class SuggestedRecipesFragment extends Fragment implements RecipeAdapter.OnRecipeClickListener {

    private RecipeDataSource recipeDataSource;
    private PantryDataSource pantryDataSource;
    private RecipeAdapter adapter;
    private TextView emptyView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_suggested_recipes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recipeDataSource = new RecipeDataSource(requireContext());
        pantryDataSource = new PantryDataSource(requireContext());
        emptyView = view.findViewById(R.id.textEmptySuggested);

        RecyclerView recycler = view.findViewById(R.id.recyclerSuggested);
        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new RecipeAdapter(this);
        recycler.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Recipe> suggestions = RecipeMatcher.suggest(
                recipeDataSource.getAll(),
                pantryDataSource.getAll());
        adapter.setRecipes(suggestions);
        emptyView.setVisibility(suggestions.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        Intent intent = new Intent(requireContext(), RecipeDetailActivity.class);
        intent.putExtra(RecipeDetailActivity.EXTRA_RECIPE_ID, recipe.getId());
        startActivity(intent);
    }
}
