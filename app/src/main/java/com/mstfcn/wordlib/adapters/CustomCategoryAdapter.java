package com.mstfcn.wordlib.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mstfcn.wordlib.R;
import com.mstfcn.wordlib.databasefiles.TableWords;
import com.mstfcn.wordlib.objects.Categories;

import java.util.ArrayList;
import java.util.List;

public class CustomCategoryAdapter extends BaseAdapter {
    List<Categories> ctrgry = new ArrayList<>();
    LayoutInflater inflater;
    Context context;

    public CustomCategoryAdapter(List<Categories> ctrgry){
        this.ctrgry=ctrgry;
    }

    //ilk parametre bu adapter'ün nerede oluşacağı, diğeri ise elemanları almak için.
    public CustomCategoryAdapter(Context context, List<Categories> ctrgry) {
        this.context = context;
        this.ctrgry = ctrgry;
    }

    @Override
    public int getCount() {
        //Listview de gösterilen satır sayısı
        return ctrgry.size();
    }

    @Override
    public Object getItem(int position) {
        //position ile sırası gelen eleman
        return position;
    }

    @Override
    public long getItemId(int position) {
        //eğer varsa niteleyeci id bilgisi
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        //position ile sırası gelen satır için bir view döndürür
        inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.list_row_category, null);
        TextView txt_wordCount = row.findViewById(R.id.lrc_txt_wordCount);
        TextView txt_catName = row.findViewById(R.id.lrc_txt_catName);
        TextView txt_catDesc=row.findViewById(R.id.lrc_txt_catDesc);

        TableWords tbl_wrd=new TableWords(row.getContext());

        Categories ctgs = ctrgry.get(i);
        txt_catName.setText(ctgs.getCatName());
        txt_catDesc.setText(ctgs.getCatDesc());
        txt_wordCount.setText(tbl_wrd.CatCount(ctgs.getId()).toString());

        return row;
    }

    public void refreshCategories(List<Categories> ctgs) {
        this.ctrgry.clear();
        this.ctrgry.addAll(ctgs);
        notifyDataSetChanged();
    }
}
