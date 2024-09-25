package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.data.DatabaseHelper;

public class GiaodiennamActivity extends AppCompatActivity {
    EditText tennam, tuoinam;
    Button tt;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.giaodiennam);

        tt = findViewById(R.id.ttnam);
        tennam = findViewById(R.id.tennam);
        tuoinam = findViewById(R.id.tuoinam);
        dbHelper = new DatabaseHelper(this);

        tt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Tentext = tennam.getText().toString().trim();
                String tuoitext = tuoinam.getText().toString().trim();

                if (!Tentext.isEmpty() && isNumeric(tuoitext)) {
                    dbHelper.insertThongTin(Tentext, Integer.parseInt(tuoitext), "", 0);
                    Intent intent = new Intent(GiaodiennamActivity.this, GiaodiennuActivity.class);
                    intent.putExtra("TENTEXT_NAM", Tentext);
                    intent.putExtra("TUOITEXT_NAM", tuoitext);
                    startActivity(intent);
                } else {
                    Toast.makeText(GiaodiennamActivity.this, "Nhập đầy đủ thông tin hoặc bạn đang không nhập tuổi bằng số", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isNumeric(String tuoitext) {
        if (tuoitext == null || tuoitext.isEmpty()) {
            return false;
        }
        for (char c : tuoitext.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
