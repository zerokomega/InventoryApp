package com.example.westoncampbellinventoryapp.inventory;

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

import com.example.westoncampbellinventoryapp.InventoryItem;
import com.example.westoncampbellinventoryapp.R;
import com.example.westoncampbellinventoryapp.data.DatabaseManager;
import com.example.westoncampbellinventoryapp.main.MainActivity;

import java.util.ArrayList;
import java.util.List;


public class InventoryFragment extends Fragment implements InvRecyclerViewInterface {

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
        adapter = new InvRecyclerViewAdapter(getContext(), (ArrayList<InventoryItem>) itemList, this);
        inventoryRecyclerView.setAdapter(adapter);

        // Inflate the layout for this fragment
        return parentView;
    }


    @Override
    public void onPlusClick(int position) {
        boolean updateNeeded = false;
        InventoryItem currentItem = itemList.get(position);
        if (currentItem.getCount() < 1) {
            updateNeeded = true;
        }
        currentItem.setCount(currentItem.getCount() + 1);
        DatabaseManager.getInstance(getContext()).editItem(currentItem.getId(), currentItem);
        adapter.notifyItemChanged(position);
        if (updateNeeded) {
            ((MainActivity) requireActivity()).updateLists();
        }
    }

    public void onMinusClick(int position) {
        boolean updateNeeded = false;
        InventoryItem currentItem = itemList.get(position);
        if (currentItem.getCount() == 1) {
            updateNeeded = true;
        }
        if (currentItem.getCount() > 0) {
            currentItem.setCount(currentItem.getCount() - 1);
            DatabaseManager.getInstance(getContext()).editItem(currentItem.getId(), currentItem);
            adapter.notifyItemChanged(position);
        }
        if (updateNeeded) {
            ((MainActivity) requireActivity()).updateLists();
        }
    }
}