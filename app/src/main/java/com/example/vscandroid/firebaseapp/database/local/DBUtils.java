package com.example.vscandroid.firebaseapp.database.local;

import android.content.Context;

import com.example.vscandroid.firebaseapp.injection.ApplicationContext;

import javax.inject.Inject;

public class DBUtils {

    private DBHelper db;

    @Inject
    public DBUtils(@ApplicationContext Context context) {
        initDB(context);

    }
    private DBHelper initDB(Context context) {
        if (db == null) {
            db = new DBHelper(context);
        }
        return db;
    }
}
