package com.example.westoncampbellinventoryapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class InventoryFragment extends Fragment {


/*
    public InventoryFragment() {
        // Required empty public constructor
    }

    public static InventoryFragment newInstance(String param1, String param2) {
        InventoryFragment fragment = new InventoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        // Inflate the layout for this fragment
        return parentView;
    }
}