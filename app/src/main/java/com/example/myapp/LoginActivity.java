package com.example.myapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.data.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    EditText editTextUsername, editTextPassword;
    Button buttonLogin, buttonRegister;
    DatabaseHelper databaseHelper;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Initialize UI components
        editTextUsername = findViewById(R.id.editTextTaiKhoan);
        editTextPassword = findViewById(R.id.editTextMatKhau);
        buttonLogin = findViewById(R.id.buttonDangNhap);
        buttonRegister = findViewById(R.id.buttonDdangky);

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("login_info", MODE_PRIVATE);

        // Check if the user is already logged in
        if (isLoggedIn()) {
            redirectToMain(); // Redirect to main screen if already logged in
            finish(); // End the current activity to prevent going back
        }

        // Set click event for Register button
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterDialog();
            }
        });

        // Set click event for Login button
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check login information
                if (databaseHelper.checkLogin(username, password)) {
                    saveLoginInfo(username); // Save login information
                    redirectToMain(); // Redirect to main screen
                } else {
                    // If login fails, show error message
                    Toast.makeText(LoginActivity.this, "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Method to show registration confirmation dialog
    private void showRegisterDialog() {
        Intent intent = new Intent(LoginActivity.this, dangky.class);
        startActivity(intent);
    }

    // Method to save login information to SharedPreferences
    private void saveLoginInfo(String username) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.apply();
    }

    // Method to check if the user is logged in
    private boolean isLoggedIn() {
        return sharedPreferences.contains("username");
    }

    // Method to redirect to the main screen
    private void redirectToMain() {
        Intent intent = new Intent(LoginActivity.this, GiaodienActivity.class);
        startActivity(intent);
        finish(); // Finish LoginActivity after redirecting to GiaodienchinhActivity
    }
}
