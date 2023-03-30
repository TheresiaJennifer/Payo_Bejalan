package com.si61.payobejalan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context ctx;
    private static final String DATABASE_NAME = "db_payo_bejalan";
    private static final int DATABSE_VERSION = 1;
    private static final String TABLE_NAME = "tbl_destinasi";
    private static final String FIELD_ID = "id";
    private static final String FIELD_NAMA = "nama";
    private static final String FIELD_ALAMAT = "alamat";
    private static final String FIELD_JAM = "jam";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABSE_VERSION);
        this.ctx = context;
    }//nanti akan ada lampu merah, pilih implementasi. Ada lampu merah lagi, pilih create constructor matching super lalu pilih salah 1 saja yg paling atas

    @Override
    public void onCreate(SQLiteDatabase db) {//akan dieksekusi ketika contructor dijalankan. Disini akan di create tabelnya
        /*
        CREATE TABEL tbl_destinasi (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nama VARCHAR(50),
            alamat TEXT,
            jam VARCHAR(30)
         );
         */
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FIELD_NAMA + " VARCHAR(50), " +
                FIELD_ALAMAT + " TEXT, " +
                FIELD_JAM + " VARCHAR(30)" +
                ");"
                ;
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
        onCreate(db);
    }

    //jika method dgn tipe data void, maka tdk perlu return. Selain dari itu, maka harus direturn dan hrs sm tipe data yg direturn misalnya long mka hrs direturn long jg
    public long tambahData (String nama, String alamat, String jam) {
        SQLiteDatabase db = this.getWritableDatabase();//minta izin untuk melakukan operasi write ke database
        ContentValues cv = new ContentValues();//untuk memasukkan data ke dalam database dengan cara dibawah ini

        cv.put(FIELD_NAMA, nama);
        cv.put(FIELD_ALAMAT, alamat);
        cv.put(FIELD_JAM, jam);

        long eksekusi = db.insert(TABLE_NAME, null, cv);
        return eksekusi;
    }

    public Cursor bacaDataDestinasi() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;

        Cursor varCursor = null;
        if (db != null) {
            varCursor = db.rawQuery(query, null);//kenapa null? karena tdk ada argumen, argumen it where di query
        }

        return varCursor;
    }

    public long ubahData (String id, String nama, String alamat, String jam) {
        SQLiteDatabase db = this.getWritableDatabase();//minta izin untuk melakukan operasi write ke database
        ContentValues cv = new ContentValues();//untuk memasukkan data ke dalam database dengan cara dibawah ini

        cv.put(FIELD_NAMA, nama);
        cv.put(FIELD_ALAMAT, alamat);
        cv.put(FIELD_JAM, jam);

        long eksekusi = db.update(TABLE_NAME, cv, "id = ?", new String[]{id});//id = akan diambil dari parameter methodnya
        return eksekusi;
    }

    public long hapusData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();//minta izin untuk melakukan operasi write ke database


        long eksekusi = db.delete(TABLE_NAME, "id = ?", new String[]{id});
        return eksekusi;
    }


}
