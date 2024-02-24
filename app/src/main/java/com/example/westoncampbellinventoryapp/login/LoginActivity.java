package com.example.westoncampbellinventoryapp.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.westoncampbellinventoryapp.main.MainActivity;
import com.example.westoncampbellinventoryapp.R;
import com.example.westoncampbellinventoryapp.data.DatabaseManager;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameText;
    private EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameText = findViewById(R.id.usernameText);
        passwordText = findViewById(R.id.passwordText);

        Button loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(l -> handleLogin());

        Button registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(l -> registerUser());
    }


    private void handleLogin() {
        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();

        if (DatabaseManager.getInstance(getApplicationContext()).authenticate(username, password)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_LONG).show();
        }
    }

    private void registerUser() {
        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();

        if (DatabaseManager.getInstance(getApplicationContext()).addUser(username, password) != -1) {
            Toast.makeText(this, "Your username " + username + " has been added", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "Failed to add " + username + " to the system", Toast.LENGTH_LONG).show();
        }
    }
}