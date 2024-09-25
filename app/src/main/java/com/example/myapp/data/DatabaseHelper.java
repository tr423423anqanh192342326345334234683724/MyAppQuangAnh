package com.example.myapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "quangnh.db";
    private static final int DATABASE_VERSION = 1;

    // Tên bảng và các cột của bảng 'thongtin'
    public static final String TABLE_THONGTIN = "thongtin";
    public static final String COLUMN_TEN_NAM = "tennam";
    public static final String COLUMN_TUOI_NAM = "tuoinam";
    public static final String COLUMN_TEN_NU = "tennu";
    public static final String COLUMN_TUOI_NU = "tuoinu";
    public static final String COLUMN_NGAYTHANGNAM = "ngaythangnam";

    // Tên bảng và các cột của bảng 'taikhoan'
    public static final String TABLE_TAIKHOAN = "taikhoan";
    public static final String COLUMN_TK = "tk";
    public static final String COLUMN_MK = "mk";

    // Câu lệnh tạo bảng 'thongtin'
    private static final String SQL_CREATE_TABLE_THONGTIN =
            "CREATE TABLE " + TABLE_THONGTIN + " (" +
                    COLUMN_TEN_NAM + " TEXT," +
                    COLUMN_TUOI_NAM + " INTEGER," +
                    COLUMN_TEN_NU + " TEXT," +
                    COLUMN_TUOI_NU + " INTEGER," +
                    COLUMN_NGAYTHANGNAM + " TEXT)";

    // Câu lệnh tạo bảng 'taikhoan'
    private static final String SQL_CREATE_TABLE_TAIKHOAN =
            "CREATE TABLE " + TABLE_TAIKHOAN + " (" +
                    COLUMN_TK + " TEXT PRIMARY KEY," +
                    COLUMN_MK + " TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng 'thongtin'
        db.execSQL(SQL_CREATE_TABLE_THONGTIN);
        // Tạo bảng 'taikhoan'
        db.execSQL(SQL_CREATE_TABLE_TAIKHOAN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xử lý khi có sự thay đổi phiên bản cơ sở dữ liệu
        // Ví dụ: db.execSQL("DROP TABLE IF EXISTS " + TABLE_THONGTIN);
        // và sau đó gọi onCreate(db) để tạo lại bảng
    }

    // Thêm tài khoản mới vào bảng 'taikhoan'
    public long addTaiKhoan(String tk, String mk) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TK, tk);
        values.put(COLUMN_MK, mk);

        long newRowId = db.insert(TABLE_TAIKHOAN, null, values);
        db.close();

        return newRowId;
    }

    // Cập nhật thông tin vào bảng 'thongtin'
    public int updateThongTin(String tenNam, int tuoiNam, String tenNu, int tuoiNu) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEN_NAM, tenNam);
        values.put(COLUMN_TUOI_NAM, tuoiNam);
        values.put(COLUMN_TEN_NU, tenNu);
        values.put(COLUMN_TUOI_NU, tuoiNu);

        // Updating row
        int rowsAffected = db.update(TABLE_THONGTIN, values, null, null);
        db.close();

        return rowsAffected;
    }

    // Lấy thông tin từ bảng 'thongtin'
    public String[] getThongTin() {
        String[] thongTin = new String[4];
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT * FROM " + TABLE_THONGTIN + " LIMIT 1";
            cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                thongTin[0] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEN_NAM));
                thongTin[1] = String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TUOI_NAM)));
                thongTin[2] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEN_NU));
                thongTin[3] = String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TUOI_NU)));
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return thongTin;
    }

    // Lấy ngày tháng năm từ bảng 'thongtin'
    public String getNgayThangNamFromThongTin() {
        String ngayThang = "";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT " + COLUMN_NGAYTHANGNAM + " FROM " + TABLE_THONGTIN + " LIMIT 1";
            cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                ngayThang = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NGAYTHANGNAM));
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return ngayThang;
    }

    // Lấy tất cả các thông tin từ bảng 'thongtin'
    public List<String> getAllThongTin() {
        List<String> thongTinList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT * FROM " + TABLE_THONGTIN;
            cursor = db.rawQuery(query, null);

            while (cursor != null && cursor.moveToNext()) {
                String tenNam = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEN_NAM));
                int tuoiNam = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TUOI_NAM));
                String tenNu = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEN_NU));
                int tuoiNu = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TUOI_NU));

                String thongTin = "Tên Nam: " + tenNam + ", Tuổi Nam: " + tuoiNam +
                        ", Tên Nữ: " + tenNu + ", Tuổi Nữ: " + tuoiNu;
                thongTinList.add(thongTin);
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return thongTinList;
    }

    // Thêm ngày tháng năm vào bảng 'thongtin'
    public long addNgayThangNam(String ngayThangNam) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NGAYTHANGNAM, ngayThangNam);

        long newRowId = db.insert(TABLE_THONGTIN, null, values);
        db.close();

        return newRowId;
    }

    // Xóa tất cả các ngày tháng năm từ bảng 'thongtin'
    public void deleteAllNgayThangNam() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_THONGTIN, null, null);
        db.close();
    }

    // Chèn thông tin vào bảng 'thongtin'
    public void insertThongTin(String tenNam, int tuoiNam, String tenNu, int tuoiNu) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEN_NAM, tenNam);
        values.put(COLUMN_TUOI_NAM, tuoiNam);
        values.put(COLUMN_TEN_NU, tenNu);
        values.put(COLUMN_TUOI_NU, tuoiNu);

        // Chèn dòng mới vào bảng
        db.insert(TABLE_THONGTIN, null, values);
        db.close();
    }

    // Kiểm tra đăng nhập
    public boolean checkLogin(String tk, String mk) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Câu truy vấn kiểm tra tài khoản và mật khẩu
            String query = "SELECT * FROM " + TABLE_TAIKHOAN + " WHERE " +
                    COLUMN_TK + " = ? AND " + COLUMN_MK + " = ?";
            cursor = db.rawQuery(query, new String[]{tk, mk});

            // Kiểm tra xem có dòng dữ liệu nào được trả về không
            if (cursor != null && cursor.getCount() > 0) {
                return true; // Tài khoản và mật khẩu hợp lệ
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return false; // Tài khoản hoặc mật khẩu không hợp lệ
    }
    // Xóa tất cả các thông tin từ bảng 'thongtin'
    public void deleteAllThongTin() {
        SQLiteDatabase db = this.getWritableDatabase();

        // Xóa hết dữ liệu trong bảng thongtin
        db.delete(TABLE_THONGTIN, null, null);

        // Gọi lại onCreate để tạo lại bảng thongtin
        onCreate(db);

        db.close();
    }

    public String[] getThongTinGanNhat() {
        String[] thongTin = new String[5];
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT * FROM " + TABLE_THONGTIN + " ORDER BY " + COLUMN_NGAYTHANGNAM + " DESC LIMIT 1";
            cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                thongTin[0] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEN_NAM));
                thongTin[1] = String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TUOI_NAM)));
                thongTin[2] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEN_NU));
                thongTin[3] = String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TUOI_NU)));
                thongTin[4] = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NGAYTHANGNAM));
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return thongTin;
    }

}
