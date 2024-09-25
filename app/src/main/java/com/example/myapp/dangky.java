package com.example.myapp;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.data.DatabaseHelper;

public class dangky extends AppCompatActivity {

    EditText tkdk, mkdk, mk2;
    Button dk, huy;
    SQLiteDatabase mydata;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);

        dbHelper = new DatabaseHelper(this);

        tkdk = findViewById(R.id.editTextUsername);
        mkdk = findViewById(R.id.editTextPassword);
        mk2 = findViewById(R.id.editTextConfirmPassword);
        dk = findViewById(R.id.buttonRegister);
        huy = findViewById(R.id.buttonCancel);

        dk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tkdk1 = tkdk.getText().toString();
                String mkdk1 = mkdk.getText().toString();
                String mkdk2 = mk2.getText().toString();

                if (tkdk1.isEmpty() || mkdk1.isEmpty() || mkdk2.isEmpty()) {
                    Toast.makeText(dangky.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!mkdk1.equals(mkdk2)) {
                    Toast.makeText(dangky.this, "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
                    return;
                }

                long result = dbHelper.addTaiKhoan(tkdk1, mkdk1);
                if (result == -1) {
                    Toast.makeText(dangky.this, "Đăng Ký Thất Bại", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(dangky.this, "Đăng Ký Thành Công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(dangky.this, LoginActivity.class);
                    startActivity(intent);
                    finish(); // Close the current activity after switching
                }
            }
        });

        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCancelConfirmationDialog();
            }
        });
    }

    // Show cancel registration confirmation dialog
    private void showCancelConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(dangky.this);
        builder.setTitle("Hủy Đăng Ký");
        builder.setMessage("Bạn có muốn hủy đăng ký không?");

        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(dangky.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Close the current activity after switching
            }
        });


        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
