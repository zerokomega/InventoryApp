package com.example.westoncampbellinventoryapp.edit;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.westoncampbellinventoryapp.InventoryItem;
import com.example.westoncampbellinventoryapp.R;
import com.example.westoncampbellinventoryapp.data.DatabaseManager;
import com.example.westoncampbellinventoryapp.main.MainActivity;

import java.util.ArrayList;
import java.util.List;


public class EditFragment extends Fragment implements EditRecyclerViewInterface {

    private List<InventoryItem> itemList;
    private RecyclerView editRecyclerView;
    private EditRecyclerViewAdapter adapter;



    public EditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parentView;
        try {
            parentView = inflater.inflate(R.layout.fragment_edit, container, false);

        } catch (Exception e) {
            Log.e(TAG, "onCreateView", e);
            throw e;
        }

        itemList = DatabaseManager.getInstance(getContext()).getItems();


        editRecyclerView = parentView.findViewById(R.id.editRecyclerView);
        editRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new EditRecyclerViewAdapter(getContext(), (ArrayList<InventoryItem>) itemList, this);
        editRecyclerView.setAdapter(adapter);

        Button addItemButton = parentView.findViewById(R.id.addItemButton);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) requireActivity()).startEditForm(-1);
            }
        });

        // Inflate the layout for this fragment
        return parentView;
    }

    @Override
    public void onItemClick(int position) {
        ((MainActivity) requireActivity()).startEditForm(position);
    }

    @Override
    public void onDeleteClick(int position) {
        InventoryItem currentItem = itemList.get(position);
        if (DatabaseManager.getInstance(getContext()).deleteItem(currentItem.getId())) {
            itemList.remove(position);
            adapter.notifyItemRemoved(position);
            Toast.makeText(getActivity(), currentItem.getName() + " has been deleted.", Toast.LENGTH_LONG).show();
            ((MainActivity) requireActivity()).updateLists();
        }
        else {
            Toast.makeText(getActivity(), "Delete action failed", Toast.LENGTH_LONG).show();
        }
    }


}