package com.samley.smartpantrymanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.samley.smartpantrymanager.R;
import com.samley.smartpantrymanager.model.PantryItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom RecyclerView adapter that binds pantry items to row views.
 * Exposes click and delete callbacks to the host screen.
 */
public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    public interface OnItemActionListener {
        void onEdit(PantryItem item);
        void onDelete(PantryItem item);
    }

    private final List<PantryItem> items = new ArrayList<>();
    private final OnItemActionListener listener;

    public PantryAdapter(OnItemActionListener listener) {
        this.listener = listener;
    }

    public void setItems(List<PantryItem> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_pantry, parent, false);
        return new PantryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PantryViewHolder holder, int position) {
        PantryItem item = items.get(position);
        holder.name.setText(item.getName());
        String qty = trimNumber(item.getQuantity()) + " " + item.getUnit();
        holder.qty.setText(qty);
        if (item.getExpiryDate() == null || item.getExpiryDate().isEmpty()) {
            holder.expiry.setText("");
        } else {
            holder.expiry.setText("Expires: " + item.getExpiryDate());
        }
        holder.itemView.setOnClickListener(v -> listener.onEdit(item));
        holder.delete.setOnClickListener(v -> listener.onDelete(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private String trimNumber(double d) {
        if (d == Math.floor(d)) {
            return String.valueOf((long) d);
        }
        return String.valueOf(d);
    }

    static class PantryViewHolder extends RecyclerView.ViewHolder {
        final TextView name;
        final TextView qty;
        final TextView expiry;
        final View delete;

        PantryViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textName);
            qty = itemView.findViewById(R.id.textQty);
            expiry = itemView.findViewById(R.id.textExpiry);
            delete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}
