package com.mstfcn.wordlib.databasefiles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mstfcn.wordlib.objects.Categories;
import com.mstfcn.wordlib.tabs.TabWords;
import java.util.ArrayList;
import java.util.List;

public class TableCategories extends SQLiteOpenHelper {

    private static final Integer database_version = 1;
    private static final String database_name = "WordLiDB.db";
    private static final String table_name = "Categories";
    private DatabaseHelper db_helper;

    public TableCategories(Context context) {
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

    public void AddCategory(Categories ctgs) {
        db_helper.openDatabase();
        SQLiteDatabase db = db_helper.getSqLiteDatabase();
        ContentValues cnt_values = new ContentValues();

        cnt_values.put("category_name", ctgs.getCatName());
        cnt_values.put("category_description", ctgs.getCatDesc());
        db.insert(table_name, null, cnt_values);
        db_helper.close();
    }

    public void DeleteCategory(int id) {
        db_helper.openDatabase();
        SQLiteDatabase db = db_helper.getSqLiteDatabase();
        db.delete(table_name, "id" + " = ?",
                new String[]{String.valueOf(id)});
        db.delete("Words", "cid" + " = ?", new String[]{String.valueOf(id)});
        TabWords.refreshWord();
        db_helper.close();
    }

    public void UpdateCategory(int id, String c_name, String c_desc) {
        db_helper.openDatabase();
        SQLiteDatabase db = db_helper.getSqLiteDatabase();
        ContentValues cnt_values = new ContentValues();

        cnt_values.put("category_name", c_name);
        cnt_values.put("category_description", c_desc);
        db.update(table_name, cnt_values, "id=" + id, null);

        db_helper.close();

    }

    public Categories GetCategory(int id) {
        db_helper.openDatabase();
        String query = "SELECT * FROM " + table_name + " WHERE id=" + id;
        SQLiteDatabase db = db_helper.getSqLiteDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        Categories ctg = new Categories();
        ctg.setId(Integer.parseInt(cursor.getString(0)));
        ctg.setCatName(cursor.getString(1));
        ctg.setCatDesc(cursor.getString(2));

        db_helper.close();
        return ctg;
    }

    public List<Categories> ListCategory() {
        db_helper.openDatabase();
        List<Categories> lst_ctg = new ArrayList<>();
        String query = "SELECT * FROM " + table_name + " ORDER BY category_name ASC";
        SQLiteDatabase sqldb = db_helper.getSqLiteDatabase();
        Cursor cursor = sqldb.rawQuery(query, null);
        Categories ctg = null;
        //Verilerin olup olmadığını kontrol ediyoruz.
        while (cursor.moveToNext()) {
            ctg = new Categories();
            ctg.setId(Integer.parseInt(cursor.getString(0)));
            ctg.setCatName(cursor.getString(1));
            ctg.setCatDesc(cursor.getString(2));
            lst_ctg.add(ctg);
        }

        db_helper.close();
        return lst_ctg;
    }

    public boolean CategoryControl(String catName) {
        db_helper.openDatabase();
        String query = "SELECT * FROM " + table_name + " WHERE category_name='" + catName + "'";
        SQLiteDatabase db = db_helper.getSqLiteDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            db_helper.close();
            return false;//aynı kategoriden var.
        } else {
            db_helper.close();
            return true;//aynı kategoriden yok.
        }
    }

}
