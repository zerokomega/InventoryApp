package com.example.westoncampbellinventoryapp.edit;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.westoncampbellinventoryapp.InventoryItem;
import com.example.westoncampbellinventoryapp.main.MainActivity;
import com.example.westoncampbellinventoryapp.R;
import com.example.westoncampbellinventoryapp.data.DatabaseManager;

public class EditForm extends Fragment {

    private InventoryItem currentItem;
    private ImageView editImage;
    private EditText editName, editDesc, editCount;
    Button editButton;
    boolean newItem = false;

    public EditForm() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parentView;
        try {
            parentView = inflater.inflate(R.layout.form_edit, container, false);

        } catch (Exception e) {
            Log.e(TAG, "onCreateView", e);
            throw e;
        }

        Bundle args = getArguments();
        if (args != null) {
            int pos = args.getInt("pos");

            if (pos < 0) {
                currentItem = new InventoryItem();
                newItem = true;
            }
            else {
                currentItem = ((MainActivity) requireActivity()).getItemList().get(pos);
                newItem = false;
            }
        }

        editImage = parentView.findViewById(R.id.formImageView);
        editName = parentView.findViewById(R.id.formNameEditText);
        editDesc = parentView.findViewById(R.id.formDescEditText);
        editCount = parentView.findViewById(R.id.formCountEditText);

        if (!newItem) {
            editImage.setImageBitmap(currentItem.getImage());
            editName.setText(currentItem.getName());
            editDesc.setText(currentItem.getDescription());
            editCount.setText(String.valueOf(currentItem.getCount()));
        }

        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
            }
        });

        editButton = parentView.findViewById(R.id.formEditButton);
        editButton.setOnClickListener(l -> updateItem());


        return parentView;
    }


    private void updateItem() {
        currentItem.setName(editName.getEditableText().toString());
        currentItem.setDescription(editDesc.getEditableText().toString());
        currentItem.setCount(Integer.parseInt(editCount.getEditableText().toString()));
        currentItem.setImage(drawableToBitmap(editImage.getDrawable()));
        if (newItem) {
            if (DatabaseManager.getInstance(getContext()).addItem(currentItem) != -1) {
                Toast.makeText(getContext(), currentItem.getName() + " has been added.", Toast.LENGTH_LONG).show();
            }
        }
        else {
            if (DatabaseManager.getInstance(getContext()).editItem(currentItem.getId(), currentItem)) {
                Toast.makeText(getContext(), currentItem.getName() + " has been updated.", Toast.LENGTH_LONG).show();
            }
        }

        ((MainActivity) requireActivity()).returnFromEditForm();

    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        return bitmapDrawable.getBitmap();
    }

}
