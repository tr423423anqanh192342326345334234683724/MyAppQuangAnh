package com.example.myapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int DELAY_TIME = 5000; // Delay time in milliseconds (5 seconds)
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main); // Assuming activity_main is your actual layout name

        sharedPreferences = getSharedPreferences("login_info", MODE_PRIVATE);

        // Check if the user is logged in
        if (!isLoggedIn()) {
            redirectToMain();
            return;
        }



        // Wait for 5 seconds before redirecting to GiaodienActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                redirectToMain();
            }
        }, DELAY_TIME);
    }


    // Method to check if the user is logged in (using SharedPreferences)
    private boolean isLoggedIn() {
        return sharedPreferences.contains("username");
    }

    // Method to redirect to LoginActivity
    private void redirectToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Finish MainActivity after redirecting to LoginActivity
    }

    // Method to redirect to GiaodienActivity
    private void redirectToMain() {
        Intent intent = new Intent(MainActivity.this, GiaodienchinhActivity.class);
        startActivity(intent);
        finish(); // Finish MainActivity after redirecting to GiaodienActivity
    }

}
