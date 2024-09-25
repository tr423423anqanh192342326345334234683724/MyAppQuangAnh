package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.data.DatabaseHelper;

import java.util.List;

public class Giaodien1Activity extends AppCompatActivity {
    TextView tenTextViewNam, tuoiTextViewNam, tenTextViewNu, tuoiTextViewNu;
    Button tieptuc, trolai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.giaodien1);

        tenTextViewNam = findViewById(R.id.textView2);
        tuoiTextViewNam = findViewById(R.id.textView3);
        tenTextViewNu = findViewById(R.id.textView6);
        tuoiTextViewNu = findViewById(R.id.textView7);
        tieptuc = findViewById(R.id.tieptuc);
        trolai = findViewById(R.id.again);

        Intent intent = getIntent();
        String tenTextNam = intent.getStringExtra("TENTEXT_NAM");
        String tuoiTextNam = intent.getStringExtra("TUOITEXT_NAM");

        tenTextViewNam.setText(tenTextNam);
        tuoiTextViewNam.setText(tuoiTextNam);

        DatabaseHelper db = new DatabaseHelper(this);
        List<String> thongTinList = db.getAllThongTin();

        if (!thongTinList.isEmpty()) {
            String[] thongTin = thongTinList.get(thongTinList.size() - 1).split(", ");

            if (thongTin.length == 4) {
                tenTextViewNu.setText(thongTin[2].split(": ")[1]);
                tuoiTextViewNu.setText(thongTin[3].split(": ")[1]);
            }
        }

        tieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Giaodien1Activity.this, GiaodienchinhActivity.class);
                startActivity(intent);
            }
        });

        trolai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Giaodien1Activity.this, GiaodiennamActivity.class);
                startActivity(intent);
            }
        });
    }
}
