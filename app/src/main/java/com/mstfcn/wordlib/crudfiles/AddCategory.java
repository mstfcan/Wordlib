package com.mstfcn.wordlib.crudfiles;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.mstfcn.wordlib.R;
import com.mstfcn.wordlib.databasefiles.TableCategories;
import com.mstfcn.wordlib.objects.Categories;

public class AddCategory extends AppCompatActivity {

    Context context = this;
    TableCategories tb_ctg = new TableCategories(context);
    EditText txt_CatName, txt_CatDesc;

    AlertDialog.Builder alertd_builder;
    AlertDialog alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.panel_category_add);


        txt_CatName = findViewById(R.id.aac_txt_catName);
        txt_CatDesc = findViewById(R.id.aac_txt_catDesc);

    }

    public void InsertCategory(View v) {

        String c_name = txt_CatName.getText().toString().trim();
        String c_desc = txt_CatDesc.getText().toString().trim();

        try {
            if (c_name.matches("")) {
                alertd_builder = new AlertDialog.Builder(context);
                alertd_builder.setMessage(R.string.msg_empty_category_name);
                alertd_builder.setCancelable(true);
                alertd_builder.setTitle(R.string.msg_warning);
                alertd_builder.setPositiveButton(R.string.okey, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                alert = alertd_builder.create();
                alert.show();
            } else {
                if (tb_ctg.CategoryControl(c_name)) {

                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    tb_ctg.AddCategory(new Categories(c_name, c_desc));
                    Toast.makeText(context, R.string.msg_category_saved, Toast.LENGTH_SHORT).show();
                    txt_CatName.setText("");
                    txt_CatDesc.setText("");

                } else {
                    alertd_builder = new AlertDialog.Builder(context);
                    alertd_builder.setMessage(R.string.msg_same_category);
                    alertd_builder.setCancelable(true);
                    alertd_builder.setTitle(R.string.msg_warning);
                    alertd_builder.setPositiveButton(R.string.okey, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alert = alertd_builder.create();
                    alert.show();
                }
            }


        } catch (Exception e) {
            Toast.makeText(context, R.string.problem, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
