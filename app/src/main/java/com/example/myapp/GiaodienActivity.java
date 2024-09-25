package com.example.myapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.data.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class GiaodienActivity extends AppCompatActivity {

    private EditText editTextDate;
    private Button dangnhap, huy, dangxuat;

    private Calendar calendar;
    private DatabaseHelper databaseHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.giaodien);

        editTextDate = findViewById(R.id.editTextDate);
        dangnhap = findViewById(R.id.connect);
        huy = findViewById(R.id.huy);


        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Initialize calendar instance
        calendar = Calendar.getInstance();


        // Show DatePickerDialog when clicking on EditText Date
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // Handle click event for Đăng nhập button
        dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ngayThangNam = editTextDate.getText().toString().trim();

                if (!ngayThangNam.isEmpty()) {
                    Toast.makeText(GiaodienActivity.this, "Thêm Ngày Thành Công", Toast.LENGTH_SHORT).show();
                    // Add to database
                    long newRowId = databaseHelper.addNgayThangNam(ngayThangNam);
                    if (newRowId != -1) {
                        Toast.makeText(GiaodienActivity.this, "Thêm Ngày vào Cơ sở Dữ liệu Thành Công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(GiaodienActivity.this, GiaodiennamActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(GiaodienActivity.this, "Thêm Ngày vào Cơ sở Dữ liệu Thất Bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(GiaodienActivity.this, "Vui lòng nhập ngày tháng năm", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Handle click event for Hủy button
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmDeleteDialog();
            }
        });


    }

    // Method to redirect to LoginActivity
    private void redirectToLogin() {
        Intent intent = new Intent(GiaodienActivity.this, LoginActivity.class);
        startActivity(intent);
        finishAffinity(); // Finish this activity along with all parent activities
    }

    // Method to check if user is logged in


    // Method to perform logout

    // Method to show DatePickerDialog
    private void showDatePickerDialog() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateEditTextDate();
            }
        };

        // Get current date to show in DatePickerDialog
        new DatePickerDialog(GiaodienActivity.this, dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    // Method to update EditText Date with selected date
    private void updateEditTextDate() {
        String myFormat = "dd/MM/yyyy"; // Date format
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        editTextDate.setText(sdf.format(calendar.getTime())); // Set selected date to EditText
    }

    // Method to show confirmation dialog before deleting data
    private void showConfirmDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa dữ liệu ngày tháng?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editTextDate.setText(""); // Clear EditText content
                Toast.makeText(GiaodienActivity.this, "Đã xóa hết dữ liệu ngày tháng", Toast.LENGTH_SHORT).show();
                // Delete data from database
                databaseHelper.deleteAllNgayThangNam();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // Dismiss the dialog
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close database connection when no longer needed
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }
}
