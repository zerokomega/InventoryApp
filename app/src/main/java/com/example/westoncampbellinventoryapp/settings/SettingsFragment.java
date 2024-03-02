package com.example.westoncampbellinventoryapp.settings;

import android.content.pm.PackageManager;
import android.os.Bundle;


import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.Manifest;
import android.widget.Toast;

import com.example.westoncampbellinventoryapp.R;


public class SettingsFragment extends Fragment {


    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    EditText textMsg, textPhoneNo;
    String msg, phoneNo;
    Button send;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        // Check for permission granted
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            // if not granted then check if user denied
            if (!shouldShowRequestPermissionRationale(Manifest.permission.SEND_SMS)) {
                // pop-up appears asking for permission
                requestPermissions(new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }



        textMsg = rootView.findViewById(R.id.textMsg);

        textPhoneNo = rootView.findViewById(R.id.textPhoneNo);

        send = rootView.findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendTextMessage();
            }
        });

        return rootView;
    }

    // result of permission request passed to this
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS:
            {
                // check length of grantResults above zero and equal to PERMISSION_GRANTED
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), "Thanks for permitting!", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getContext(), "Okay, you will not receive notifications via SMS", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    protected void sendTextMessage() {
        msg = textMsg.getText().toString();
        phoneNo = textPhoneNo.getText().toString();

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNo, null, msg, null, null);
        Toast.makeText(getContext(), "Sent!", Toast.LENGTH_LONG).show();
    }
}