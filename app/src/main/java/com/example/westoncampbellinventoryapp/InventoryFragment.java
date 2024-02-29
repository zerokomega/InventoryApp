package com.example.westoncampbellinventoryapp;

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

import com.example.westoncampbellinventoryapp.data.DatabaseManager;
import com.example.westoncampbellinventoryapp.main.InvRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class InventoryFragment extends Fragment {

    private List<InventoryItem> itemList;
    private RecyclerView inventoryRecyclerView;
    private InvRecyclerViewAdapter adapter;



    public InventoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View parentView;
        try {
            parentView = inflater.inflate(R.layout.fragment_inventory, container, false);

        } catch (Exception e) {
            Log.e(TAG, "onCreateView", e);
            throw e;
        }

        itemList = DatabaseManager.getInstance(getContext()).getItems();

        inventoryRecyclerView = parentView.findViewById(R.id.inventoryRecyclerView);
        inventoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new InvRecyclerViewAdapter(getContext(), (ArrayList<InventoryItem>) itemList);
        inventoryRecyclerView.setAdapter(adapter);

        // Inflate the layout for this fragment
        return parentView;
    }


}