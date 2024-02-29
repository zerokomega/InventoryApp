package com.example.westoncampbellinventoryapp.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.westoncampbellinventoryapp.InventoryItem;
import com.example.westoncampbellinventoryapp.R;

import java.util.ArrayList;

public class InvRecyclerViewAdapter extends RecyclerView.Adapter<InvRecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<InventoryItem> inventoryItems;

    public InvRecyclerViewAdapter(Context context, ArrayList<InventoryItem> inventoryItems) {
        this.context = context;
        this.inventoryItems = inventoryItems;
    }

    @NonNull
    @Override
    public InvRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);

        return new InvRecyclerViewAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull InvRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.nameText.setText(inventoryItems.get(position).getName());
        holder.descriptionText.setText(inventoryItems.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return inventoryItems.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameText, descriptionText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            nameText = itemView.findViewById(R.id.nameTextView);
            descriptionText = itemView.findViewById(R.id.descriptionTextView);
        }

    }
}
