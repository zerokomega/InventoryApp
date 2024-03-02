package com.example.westoncampbellinventoryapp.inventory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.westoncampbellinventoryapp.InventoryItem;
import com.example.westoncampbellinventoryapp.R;

import java.util.ArrayList;
import java.util.Locale;

public class InvRecyclerViewAdapter extends RecyclerView.Adapter<InvRecyclerViewAdapter.MyViewHolder> {

    private final InvRecyclerViewInterface recyclerViewInterface;

    Context context;
    ArrayList<InventoryItem> inventoryItems;

    public InvRecyclerViewAdapter(Context context, ArrayList<InventoryItem> inventoryItems,
                                  InvRecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.inventoryItems = inventoryItems;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public InvRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);

        return new InvRecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);

    }

    @Override
    public void onBindViewHolder(@NonNull InvRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.nameText.setText(inventoryItems.get(position).getName());
        holder.descriptionText.setText(inventoryItems.get(position).getDescription());
        holder.countText.setText(String.format(Locale.getDefault(), "%d", inventoryItems.get(position).getCount()));
        holder.imageView.setImageBitmap(inventoryItems.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return inventoryItems.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameText, descriptionText, countText;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView, InvRecyclerViewInterface recyclerViewInterface) {
            super(itemView);


            nameText = itemView.findViewById(R.id.nameTextView);
            descriptionText = itemView.findViewById(R.id.descriptionTextView);
            countText = itemView.findViewById(R.id.countTextView);
            imageView = itemView.findViewById(R.id.imageView);

            itemView.findViewById(R.id.plusButton).setOnClickListener(v -> {
                if (recyclerViewInterface != null) {
                    int pos = getBindingAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onPlusClick(pos);
                    }
                }

            });

            itemView.findViewById(R.id.minusButton).setOnClickListener(v -> {
                if (recyclerViewInterface != null) {
                    int pos = getBindingAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onMinusClick(pos);
                    }
                }

            });
        }

    }

}
