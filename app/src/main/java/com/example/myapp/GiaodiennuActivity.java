package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.data.DatabaseHelper;

public class GiaodiennuActivity extends AppCompatActivity {
    EditText tennu, tuoinu;
    Button tt;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.giaodiennu);

        tt = findViewById(R.id.ttnu);
        tennu = findViewById(R.id.tennu);
        tuoinu = findViewById(R.id.tuoinu);
        dbHelper = new DatabaseHelper(this);

        tt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TentextNam = getIntent().getStringExtra("TENTEXT_NAM");
                String TuoiTextNam = getIntent().getStringExtra("TUOITEXT_NAM");
                String Tentext = tennu.getText().toString().trim();
                String tuoitext = tuoinu.getText().toString().trim();

                if (!Tentext.isEmpty() && isNumeric(tuoitext)) {
                    dbHelper.updateThongTin(TentextNam, Integer.parseInt(TuoiTextNam), Tentext, Integer.parseInt(tuoitext));
                    Intent intent = new Intent(GiaodiennuActivity.this, Giaodien1Activity.class);
                    intent.putExtra("TENTEXT_NAM", TentextNam);
                    intent.putExtra("TUOITEXT_NAM", TuoiTextNam);
                    intent.putExtra("TENTEXT_NU", Tentext);
                    intent.putExtra("TUOITEXT_NU", tuoitext);
                    startActivity(intent);
                } else {
                    Toast.makeText(GiaodiennuActivity.this, "Nhập đầy đủ thông tin hoặc bạn đang không nhập tuổi bằng số", Toast.LENGTH_SHORT).show();
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
