package com.mstfcn.wordlib.databasefiles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mstfcn.wordlib.objects.Words;

import java.util.ArrayList;
import java.util.List;

public class TableWords extends SQLiteOpenHelper {
    private static final Integer database_version = 1;
    private static final String database_name = "MyWordsDB";
    private static final String table_name = "Words";
    private DatabaseHelper db_helper;

    private static List<Words> search_list;

    public TableWords(Context context) {
        super(context, database_name, null, database_version);
        db_helper = new DatabaseHelper(context);
        db_helper.createDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        this.onCreate(sqLiteDatabase);
    }

    public void AddWord(Words wrd) {
        db_helper.openDatabase();
        SQLiteDatabase db = db_helper.getSqLiteDatabase();
        ContentValues cnt_values = new ContentValues();

        cnt_values.put("cid", wrd.getCid());
        cnt_values.put("from_word", wrd.getFromWord());
        cnt_values.put("to_word", wrd.getToWord());
        cnt_values.put("from_language", wrd.getFromLanguage());
        cnt_values.put("to_language", wrd.getToLanguage());
        db.insert(table_name, null, cnt_values);
        db_helper.close();
    }

    public void DeleteWord(int id) {
        db_helper.openDatabase();
        SQLiteDatabase db = db_helper.getSqLiteDatabase();
        db.delete(table_name, "id" + " = ?",
                new String[]{String.valueOf(id)});
        db_helper.close();
    }

    public void UpdateWord(int id, Words wrd) {
        db_helper.openDatabase();
        SQLiteDatabase db = db_helper.getSqLiteDatabase();
        ContentValues cnt_values = new ContentValues();

        cnt_values.put("cid", wrd.getCid());
        cnt_values.put("from_word", wrd.getFromWord());
        cnt_values.put("to_word", wrd.getToWord());
        cnt_values.put("from_language", wrd.getFromLanguage());
        cnt_values.put("to_language", wrd.getToLanguage());
        db.update(table_name, cnt_values, "id=" + id, null);

        db_helper.close();

    }

    public Words GetWord(int id) {
        db_helper.openDatabase();
        String query = "SELECT * FROM " + table_name + " WHERE id=" + id;
        SQLiteDatabase db = db_helper.getSqLiteDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        Words wrd = new Words();
        wrd.setId(Integer.parseInt(cursor.getString(0)));
        wrd.setCid(Integer.parseInt(cursor.getString(1)));
        wrd.setFromWord(cursor.getString(2));
        wrd.setToWord(cursor.getString(3));
        wrd.setFromLanguage(cursor.getString(4));
        wrd.setToLanguage(cursor.getString(5));
        db_helper.close();
        return wrd;
    }

    public static void setSearcList(List<Words> lst) {
            search_list = lst;
    }

    public List<Words> ListWord() {

        if (search_list != null && search_list.size() > 0) {
            return search_list;
        } else {
            db_helper.openDatabase();
            List<Words> lst_wrd = new ArrayList<>();
            String query = "SELECT * FROM " + table_name + " as wrd INNER JOIN Categories as ctg ON ctg.id=wrd.cid ORDER BY ctg.category_name,wrd.from_word ASC";
            SQLiteDatabase sqldb = db_helper.getSqLiteDatabase();
            Cursor cursor = sqldb.rawQuery(query, null);
            Words wrd = null;
            //Verilerin olup olmadığını kontrol ediyoruz.
            while (cursor.moveToNext()) {
                wrd = new Words();
                wrd.setId(Integer.parseInt(cursor.getString(0)));
                wrd.setCid(Integer.parseInt(cursor.getString(1)));
                wrd.setFromWord(cursor.getString(2));
                wrd.setToWord(cursor.getString(3));
                wrd.setFromLanguage(cursor.getString(4));
                wrd.setToLanguage(cursor.getString(5));
                lst_wrd.add(wrd);
            }

            db_helper.close();
            return lst_wrd;
        }

    }

    public List<Words> ListWordsOnCat(int id) {
        db_helper.openDatabase();
        List<Words> lst_wrd = new ArrayList<>();
        String query = "SELECT * FROM " + table_name + " WHERE cid=" + id + " ORDER BY from_word ASC";
        SQLiteDatabase sqldb = db_helper.getSqLiteDatabase();
        Cursor cursor = sqldb.rawQuery(query, null);
        Words wrd = null;
        //Verilerin olup olmadığını kontrol ediyoruz.
        while (cursor.moveToNext()) {
            wrd = new Words();
            wrd.setId(Integer.parseInt(cursor.getString(0)));
            wrd.setCid(Integer.parseInt(cursor.getString(1)));
            wrd.setFromWord(cursor.getString(2));
            wrd.setToWord(cursor.getString(3));
            wrd.setFromLanguage(cursor.getString(4));
            wrd.setToLanguage(cursor.getString(5));
            lst_wrd.add(wrd);
        }

        db_helper.close();
        return lst_wrd;
    }

    public Integer CatCount(Integer cid) {
        db_helper.openDatabase();
        String query = "SELECT COUNT(*) FROM " + table_name + " WHERE cid=" + cid;
        SQLiteDatabase db = db_helper.getSqLiteDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        db_helper.close();
        return Integer.parseInt(cursor.getString(0));
    }

}
