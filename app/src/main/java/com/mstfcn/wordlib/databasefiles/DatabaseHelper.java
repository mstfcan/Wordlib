package com.mstfcn.wordlib.databasefiles;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final Integer database_version = 2;
    private static final String database_name = "WordLibDB.db";
    private static final String database_path = "data/data/com.mstfcn.wordlib/databases/";
    private SQLiteDatabase sqLiteDatabase;

    DatabaseHelper(Context context) {
        super(context, database_name, null, database_version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.disableWriteAheadLogging();
    }

    void createDatabase() {
        //Veritabanı yoksa.
        if (!checkDatabase()) {
            this.getReadableDatabase();
            this.close();
            copyDatabase();
        }
    }

    //Veritabanının oluşturulup oluşturulmadığı kontrol ediliyor.
    private boolean checkDatabase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(database_path+database_name, null, SQLiteDatabase.OPEN_READONLY);

            if (checkDB != null) {
                checkDB.close();
            }


        } catch (SQLException ignored) {

        }
        return checkDB != null;
    }

    private void copyDatabase() {
        try {
            InputStream myInput = context.getAssets().open(database_name);

            OutputStream myOutput = new FileOutputStream(database_path+database_name);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();

        } catch (IOException ignored) {

        }
    }

    void openDatabase() throws SQLiteException {
        sqLiteDatabase = SQLiteDatabase.openDatabase(database_path+database_name, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close() {
        super.close();
        if (sqLiteDatabase != null) {
            sqLiteDatabase.close();
        }
    }

    SQLiteDatabase getSqLiteDatabase() {
        return sqLiteDatabase;
    }
}
