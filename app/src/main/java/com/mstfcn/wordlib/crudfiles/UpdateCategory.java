package com.mstfcn.wordlib.crudfiles;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mstfcn.wordlib.R;
import com.mstfcn.wordlib.databasefiles.TableCategories;
import com.mstfcn.wordlib.objects.Categories;

public class UpdateCategory extends AppCompatActivity {

    Context context = this;
    TableCategories tb_ctg = new TableCategories(context);
    TextView txt_CatName, txt_CatDesc;

    AlertDialog.Builder alertd_builder;
    AlertDialog alert;

    Integer cid;

    String temp;//kategori adını değiştirmediyse aynı kategori daha önce eklendi hatası vermemesi için.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_category);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.panel_category_update);

        txt_CatName = findViewById(R.id.auc_txt_ucatName);
        txt_CatDesc = findViewById(R.id.auc_txt_ucatDesc);

        cid = getIntent().getIntExtra("cid", 0);

        Categories cc = tb_ctg.GetCategory(cid);
        temp=cc.getCatName();
        txt_CatName.setText(cc.getCatName());
        txt_CatDesc.setText(cc.getCatDesc());
    }

    public void UpdateCategory(View v) {
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
                if (tb_ctg.CategoryControl(c_name) || temp.equals(c_name)) {
                    tb_ctg.UpdateCategory(cid,c_name,c_desc);
                    Toast.makeText(context, R.string.msg_category_updated, Toast.LENGTH_SHORT).show();

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
                    alert= alertd_builder.create();
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
