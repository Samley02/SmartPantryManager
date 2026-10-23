package com.samley.smartpantrymanager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.samley.smartpantrymanager.R;
import com.samley.smartpantrymanager.adapter.PantryAdapter;
import com.samley.smartpantrymanager.data.PantryDataSource;
import com.samley.smartpantrymanager.model.PantryItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Pantry List screen. Shows all ingredients in a RecyclerView (custom adapter),
 * lets the user add (via Intent to AddEditIngredientActivity), edit and delete.
 */
public class PantryListFragment extends Fragment implements PantryAdapter.OnItemActionListener {

    private PantryDataSource dataSource;
    private PantryAdapter adapter;
    private TextView emptyView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pantry_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataSource = new PantryDataSource(requireContext());
        emptyView = view.findViewById(R.id.textEmpty);

        RecyclerView recycler = view.findViewById(R.id.recyclerPantry);
        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new PantryAdapter(this);
        recycler.setAdapter(adapter);

        FloatingActionButton fab = view.findViewById(R.id.fabAdd);
        fab.setOnClickListener(v -> {
            // Intent to the Add/Edit screen with no id = create mode
            Intent intent = new Intent(requireContext(), AddEditIngredientActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    private void refresh() {
        java.util.List<PantryItem> items = dataSource.getAll();
        adapter.setItems(items);
        emptyView.setVisibility(items.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onEdit(PantryItem item) {
        // Intent to the Add/Edit screen, passing the id = edit mode
        Intent intent = new Intent(requireContext(), AddEditIngredientActivity.class);
        intent.putExtra(AddEditIngredientActivity.EXTRA_ID, item.getId());
        startActivity(intent);
    }

    @Override
    public void onDelete(PantryItem item) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Delete ingredient")
                .setMessage("Remove \"" + item.getName() + "\" from your pantry?")
                .setPositiveButton("Delete", (d, w) -> {
                    dataSource.delete(item.getId());
                    refresh();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
