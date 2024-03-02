package com.example.westoncampbellinventoryapp.edit;

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

public class EditRecyclerViewAdapter extends RecyclerView.Adapter<EditRecyclerViewAdapter.MyViewHolder> {

    private final EditRecyclerViewInterface recyclerViewInterface;

    Context context;
    ArrayList<InventoryItem> inventoryItems;

    public EditRecyclerViewAdapter(Context context, ArrayList<InventoryItem> inventoryItems,
                                  EditRecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.inventoryItems = inventoryItems;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public EditRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.edit_recycler_row, parent, false);

        return new EditRecyclerViewAdapter.MyViewHolder(view, recyclerViewInterface);

    }

    @Override
    public void onBindViewHolder(@NonNull EditRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.nameText.setText(inventoryItems.get(position).getName());
        holder.descriptionText.setText(inventoryItems.get(position).getDescription());
        holder.imageView.setImageBitmap(inventoryItems.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return inventoryItems.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameText, descriptionText;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView, EditRecyclerViewInterface recyclerViewInterface) {
            super(itemView);


            nameText = itemView.findViewById(R.id.nameTextViewEdit);
            descriptionText = itemView.findViewById(R.id.descriptionTextViewEdit);
            imageView = itemView.findViewById(R.id.imageViewEdit);

            itemView.setOnClickListener(v -> {
                if (recyclerViewInterface != null) {
                    int pos = getBindingAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onItemClick(pos);
                    }
                }

            });

            itemView.findViewById(R.id.deleteButton).setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (recyclerViewInterface != null) {
                        int pos = getBindingAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onDeleteClick(pos);
                        }
                    }

                    return true;
                }
            });
        }

    }

}
