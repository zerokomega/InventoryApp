package com.example.westoncampbellinventoryapp.main;


import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.westoncampbellinventoryapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

//    ActivityMainBinding binding;
//    ArrayList<InventoryItem> itemList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
        BottomNavigationView bottomNav = findViewById(R.id.navView);
        NavigationUI.setupWithNavController(bottomNav, navController);




//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
/*
        binding.navView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_inventory:
                    replaceFragment(new InventoryFragment());
                    break;
                case R.id.navigation_edit:
                    replaceFragment(new EditFragment());
                    break;
                case R.id.navigation_settings:
                    replaceFragment(new SettingsFragment());
                    break;
            }


            return true;
        });


        if (DatabaseManager.getInstance(this).getItems().isEmpty()) {
            InventoryItem defaultItem = new InventoryItem(0, getString(R.string.hat_name), getString(R.string.hat_desc));
            DatabaseManager.getInstance(this).addItem(defaultItem);
        }

        RecyclerView recyclerView = findViewById(R.id.inventory_list);

        setUpItemModels();

        InvRecyclerViewAdapter adapter = new InvRecyclerViewAdapter(this, itemList);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
*/


    }
/*
    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainFrameLayout, fragment);
        fragmentTransaction.commit();
    }

    private void setUpItemModels() {
        itemList = DatabaseManager.getInstance(this).getItems();

    }

 */
}