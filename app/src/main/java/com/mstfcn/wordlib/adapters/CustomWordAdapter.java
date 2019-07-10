package com.mstfcn.wordlib.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mstfcn.wordlib.R;
import com.mstfcn.wordlib.databasefiles.TableCategories;
import com.mstfcn.wordlib.objects.Words;

import java.util.Arrays;
import java.util.List;

public class CustomWordAdapter extends BaseAdapter {

    List<Words> wrds;
    LayoutInflater inflater;
    Context context;

    public CustomWordAdapter(Context context, List<Words> wrds) {
        this.context = context;
        this.wrds = wrds;
    }

    @Override
    public int getCount() {
        return wrds.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.list_row_word, null);
        TableCategories tbl_cat=new TableCategories(context);

        String[] languages = row.getResources().getStringArray(R.array.languages);
        String[] languages_abb = row.getResources().getStringArray(R.array.languages_abb);

        TextView toWord=row.findViewById(R.id.lrw_txt_toWord);
        TextView wordCat=row.findViewById(R.id.lrw_txt_wordCat);
        TextView fromLang=row.findViewById(R.id.lrw_txt_fromLang);
        TextView toLang=row.findViewById(R.id.lrw_txt_toLang);
        TextView fromWord=row.findViewById(R.id.lrw_txt_fromWord);

        Words wrd = wrds.get(i);
        toWord.setText(wrd.getToWord());
        wordCat.setText(tbl_cat.GetCategory(wrd.getCid()).getCatName());
        fromLang.setText(languages_abb[Arrays.asList(languages).indexOf(wrd.getFromLanguage())]);
        toLang.setText(languages_abb[Arrays.asList(languages).indexOf(wrd.getToLanguage())]);
        fromWord.setText(wrd.getFromWord());

        return row;
    }
    public void refreshWords(List<Words> wrd) {
        this.wrds.clear();
        this.wrds.addAll(wrd);
        notifyDataSetChanged();
    }
}

