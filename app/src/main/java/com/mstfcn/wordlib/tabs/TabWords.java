package com.mstfcn.wordlib.tabs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.mstfcn.wordlib.R;
import com.mstfcn.wordlib.adapters.CustomWordAdapter;
import com.mstfcn.wordlib.crudfiles.AddCategory;
import com.mstfcn.wordlib.crudfiles.AddWord;
import com.mstfcn.wordlib.crudfiles.DisplayWord;
import com.mstfcn.wordlib.databasefiles.TableWords;
import com.mstfcn.wordlib.objects.Words;

import java.util.ArrayList;
import java.util.List;

public class TabWords extends Fragment {

    List<Words> wrds = new ArrayList<Words>();
    ListView list;
    View rootView;
    TextView search_not_found;
    private static TableWords tbl_wrds;
    private static CustomWordAdapter ccw;
    EditText edt_searchWord;

    public TabWords() {

    }

    public static TabWords newInstance() {
        TabWords fragment = new TabWords();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.content_words, container, false);
        list = rootView.findViewById(R.id.cw_lst_listWord);
        search_not_found=rootView.findViewById(R.id.search_not_found);
        edt_searchWord = rootView.findViewById(R.id.cw_edt_searchWord);
        tbl_wrds = new TableWords(rootView.getContext());
        ccw = new CustomWordAdapter(rootView.getContext(), tbl_wrds.ListWord());
        list.setAdapter(ccw);
        registerForContextMenu(list);

        search_not_found.setVisibility(View.GONE);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        edt_searchWord.setText("");
        TableWords.setSearcList(null);
        ccw.refreshWords(tbl_wrds.ListWord());
        edt_searchWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<Words> arrWords = tbl_wrds.ListWord();
                List<Words> tempWords = new ArrayList<>();
                for (int k = 0; k < arrWords.size(); k++) {
                    if (arrWords.get(k).getFromWord().toLowerCase().contains(charSequence.toString().toLowerCase())
                            || arrWords.get(k).getToWord().toLowerCase().contains(charSequence.toString().toLowerCase())) {

                        tempWords.add(arrWords.get(k));
                    }
                }

                if (tempWords != null && tempWords.size() > 0) {
                    TableWords.setSearcList(tempWords);
                    ccw = new CustomWordAdapter(rootView.getContext(), tempWords);
                    list.setVisibility(View.VISIBLE);
                    search_not_found.setVisibility(View.GONE);
                    list.setAdapter(ccw);
                } else {
                    TableWords.setSearcList(null);
                    list.setAdapter(null);
                    list.setVisibility(View.GONE);
                    search_not_found.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (edt_searchWord.getText().toString().trim().equals("")) {
                    TableWords.setSearcList(null);
                    ccw = new CustomWordAdapter(rootView.getContext(), tbl_wrds.ListWord());
                    list.setAdapter(ccw);
                    list.setVisibility(View.VISIBLE);
                    search_not_found.setVisibility(View.GONE);
                }
            }
        });

        edt_searchWord.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    List<Words> arrWords = tbl_wrds.ListWord();
                    List<Words> tempWords = new ArrayList<>();
                    for (int k = 0; k < arrWords.size(); k++) {
                        if (arrWords.get(k).getFromWord().toLowerCase().contains(textView.getText().toString().toLowerCase())
                                || arrWords.get(k).getToWord().toLowerCase().contains(textView.getText().toString().toLowerCase())) {

                            tempWords.add(arrWords.get(k));
                        }
                    }

                    if (tempWords != null && tempWords.size() > 0) {
                        TableWords.setSearcList(tempWords);
                        ccw = new CustomWordAdapter(rootView.getContext(), tempWords);
                        list.setVisibility(View.VISIBLE);
                        search_not_found.setVisibility(View.GONE);
                        list.setAdapter(ccw);
                    } else {
                        TableWords.setSearcList(null);
                        list.setAdapter(null);
                        list.setVisibility(View.GONE);
                        search_not_found.setVisibility(View.VISIBLE);
                    }

                    InputMethodManager in = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(edt_searchWord.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Words ww = tbl_wrds.ListWord().get(i);
                Intent intnt = new Intent(rootView.getContext(), DisplayWord.class);
                intnt.putExtra("wid", ww.getId());
                startActivity(intnt);
            }
        });
    }

    public static void refreshWord() {
        ccw.refreshWords(tbl_wrds.ListWord());
    }

}

