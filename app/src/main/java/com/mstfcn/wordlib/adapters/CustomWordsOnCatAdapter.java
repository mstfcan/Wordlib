package com.mstfcn.wordlib.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mstfcn.wordlib.R;
import com.mstfcn.wordlib.objects.Words;

import java.util.List;

public class CustomWordsOnCatAdapter extends BaseAdapter {
    List<Words> wrds;
    LayoutInflater inflater;
    Context context;

    public CustomWordsOnCatAdapter(Context context, List<Words> wrds){
        this.context = context;
        this.wrds = wrds;
    }

    @Override
    public int getCount() {
        return wrds.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.list_row_cat_word, null);

        TextView toWord=row.findViewById(R.id.lrcw_txt_toWord);
        TextView fromLang=row.findViewById(R.id.lrcw_txt_fromLang);
        TextView toLang=row.findViewById(R.id.lrcw_txt_toLang);
        TextView fromWord=row.findViewById(R.id.lrcw_txt_fromWord);

        Words wrd = wrds.get(i);
        toWord.setText(wrd.getToWord());
        fromLang.setText(wrd.getFromLanguage());
        toLang.setText(wrd.getToLanguage());
        fromWord.setText(wrd.getFromWord());

        return row;
    }

    public void refreshWords(List<Words> wrds) {
        this.wrds.clear();
        this.wrds.addAll(wrds);
        notifyDataSetChanged();
    }
}

