package com.example.myapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.data.DatabaseHelper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class GiaodienchinhActivity extends AppCompatActivity {

    TextView textViewTenNam, textViewTuoiNam, textViewTenNu, textViewTuoiNu, textViewSoNgay;
    Button logout, logout1;
    private DatabaseHelper dbHelper;

    @SuppressLint({"CutPasteId", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.giaodienchinh);

        // Ánh xạ các TextView và Button từ layout
        textViewTenNam = findViewById(R.id.textView14);
        textViewTuoiNam = findViewById(R.id.textView16);
        textViewTenNu = findViewById(R.id.textView15);
        textViewTuoiNu = findViewById(R.id.textView17);
        textViewSoNgay = findViewById(R.id.textViewSoNgay);
        logout = findViewById(R.id.logout);
        logout1 = findViewById(R.id.logout1);

        // Khởi tạo DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Kiểm tra đăng nhập
        if (!isLoggedIn()) {
            redirectToLogin();
            return;
        }

        // Xử lý khi nhấn nút Logout
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogoutConfirmationDialog();
            }
        });

        // Xử lý khi nhấn nút Xóa dữ liệu
        logout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.deleteAllThongTin();
                refreshUIAfterDeletion();
            }
        });

        // Truy vấn và hiển thị thông tin từ cơ sở dữ liệu
        displayThongTinFromDatabase();
    }

    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Đăng xuất");
        builder.setMessage("Bạn có muốn đăng xuất không?");
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                performLogout();
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Dismiss dialog
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Phương thức hiển thị thông tin từ cơ sở dữ liệu
    private void displayThongTinFromDatabase() {
        String[] thongTin = dbHelper.getThongTin();
        if (thongTin != null && thongTin.length == 4) {
            textViewTenNam.setText(thongTin[0]);
            textViewTuoiNam.setText(thongTin[1]);
            textViewTenNu.setText(thongTin[2]);
            textViewTuoiNu.setText(thongTin[3]);

            // Lấy ngày tháng từ bảng thongtin trong DatabaseHelper
            String ngayThangNam = dbHelper.getNgayThangNamFromThongTin();
            if (!ngayThangNam.isEmpty()) {
                long soNgay = tinhSoNgayGiuaNgayHienTaiVaNgayNhap(ngayThangNam);
                textViewSoNgay.setText(soNgay + " ngày");
            }
        }
    }

    // Phương thức cập nhật giao diện sau khi xóa dữ liệu
    private void refreshUIAfterDeletion() {
        textViewTenNam.setText("");
        textViewTuoiNam.setText("");
        textViewTenNu.setText("");
        textViewTuoiNu.setText("");
        textViewSoNgay.setText("");
        Toast.makeText(GiaodienchinhActivity.this, "Đã xóa hết dữ liệu ngày tháng", Toast.LENGTH_SHORT).show();
    }

    // Phương thức kiểm tra đăng nhập từ SharedPreferences
    private boolean isLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("login_info", MODE_PRIVATE);
        return sharedPreferences.contains("username");
    }

    // Phương thức đăng xuất
    private void performLogout() {
        // Xóa thông tin đăng nhập từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("login_info", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("username");
        editor.apply();

        // Chuyển người dùng về màn hình LoginActivity để đăng nhập lại
        Intent intent = new Intent(GiaodienchinhActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish(); // Kết thúc hoạt động hiện tại (GiaodienchinhActivity)
    }

    // Phương thức tính số ngày từ ngày hiện tại đến ngày nhập
    private long tinhSoNgayGiuaNgayHienTaiVaNgayNhap(String ngayThang) {
        // Định dạng ngày tháng năm từ người dùng
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault());
        LocalDate ngayNhap = LocalDate.parse(ngayThang, formatter);

        // Lấy ngày hiện tại
        LocalDate ngayHienTai = LocalDate.now();

        // Tính số ngày giữa hai ngày
        return ChronoUnit.DAYS.between(ngayNhap, ngayHienTai);
    }

    // Phương thức chuyển hướng đến màn hình đăng nhập
    private void redirectToLogin() {
        Intent intent = new Intent(GiaodienchinhActivity.this, LoginActivity.class);
        startActivity(intent);
        finishAffinity(); // Kết thúc hoạt động này cùng với tất cả các hoạt động cha của nó
    }
}
