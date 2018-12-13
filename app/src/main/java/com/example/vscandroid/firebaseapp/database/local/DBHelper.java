package com.example.vscandroid.firebaseapp.database.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "safe_net";
    private static final int DB_VERSION = 1;
    private SQLiteDatabase database;


    DBHelper(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
        open();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void open() {
        database = this.getWritableDatabase();
    }
}
