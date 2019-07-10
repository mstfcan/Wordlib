package com.mstfcn.wordlib.tabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mstfcn.wordlib.R;
import com.mstfcn.wordlib.adapters.CustomCategoryAdapter;
import com.mstfcn.wordlib.crudfiles.ListWordsOnCat;
import com.mstfcn.wordlib.databasefiles.TableCategories;
import com.mstfcn.wordlib.objects.Categories;

public class TabCategories extends Fragment {

    ListView list;
    private static TabCategories instance = null;


    public TabCategories() {
    }

    public static TabCategories getInstance() {
        if (instance == null) {
            instance = new TabCategories();
        }
        return instance;
    }

    public static TabCategories newInstance() {
        TabCategories fragment = new TabCategories();
        return fragment;
    }

    View rootView;
    private static TableCategories tb_ctg;
    private static CustomCategoryAdapter cca;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.content_categories, container, false);
        tb_ctg = new TableCategories(rootView.getContext());
        list = rootView.findViewById(R.id.cc_lst_listCategory);
        cca = new CustomCategoryAdapter(rootView.getContext(), tb_ctg.ListCategory());
        list.setAdapter(cca);
        registerForContextMenu(list);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        cca.refreshCategories(tb_ctg.ListCategory());

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Categories cc = tb_ctg.ListCategory().get(i);
                Intent intnt = new Intent(rootView.getContext(), ListWordsOnCat.class);
                intnt.putExtra("cid", cc.getId());
                startActivity(intnt);
            }
        });
    }

    public static void refreshCat() {
        cca.refreshCategories(tb_ctg.ListCategory());
    }

}

