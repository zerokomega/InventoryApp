package com.example.westoncampbellinventoryapp.main;


import static com.example.westoncampbellinventoryapp.R.layout.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.westoncampbellinventoryapp.InventoryItem;
import com.example.westoncampbellinventoryapp.R;
import com.example.westoncampbellinventoryapp.data.DatabaseManager;
import com.example.westoncampbellinventoryapp.edit.EditForm;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    Button noticeButton, alertButton;
    TextView alertText;
    String alertUpdate;
    NavHostFragment navHostFragment;
    NavController navController;


    ArrayList<InventoryItem> itemList = new ArrayList<>();
    ArrayList<InventoryItem> alertList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);


        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();
        BottomNavigationView bottomNav = findViewById(R.id.navView);
        NavigationUI.setupWithNavController(bottomNav, navController);

        noticeButton = findViewById(R.id.noticeButton);
        noticeButton.setOnClickListener(this::onNoticeButtonClick);

        alertButton = findViewById(R.id.alertButton);
        alertButton.setOnClickListener(this::onNoticeButtonClick);

        updateLists();
    }



    public void updateLists() {
        itemList = DatabaseManager.getInstance(this).getItems();
        alertList.clear();
        for (int i = 0; i < itemList.size(); ++i) {
            if (itemList.get(i).getCount() < 1) {
                alertList.add(itemList.get(i));
            }
        }

        if (alertList.isEmpty()) {
            alertButton.setVisibility(View.INVISIBLE);
            alertUpdate = getString(R.string.no_out_of_stock);

        }
        else {
            alertButton.setVisibility(View.VISIBLE);
            StringBuilder updateString = new StringBuilder("The following items are out-of-stock:\n");
            for (int i = 0; i < alertList.size(); ++i) {
                updateString.append("\n").append(alertList.get(i).getName());
            }
            alertUpdate = updateString.toString();
        }

    }

    public void onNoticeButtonClick(View view) {

        alertButton.setVisibility(View.INVISIBLE);

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(low_stock, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        alertText = popupView.findViewById(R.id.alertText);
        alertText.setText(alertUpdate);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            ImageView imageView = findViewById(R.id.formImageView);
            imageView.setImageURI(selectedImage);
        }
    }
    
    public ArrayList <InventoryItem> getItemList() {
        return itemList;
    }

    public void startEditForm(int position) {
        EditForm editForm = new EditForm();

        Bundle args = new Bundle();
        args.putInt("pos", position);
        editForm.setArguments(args);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.nav_host_fragment, editForm)
                .commit();
    }

    public void returnFromEditForm() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}