package com.mstfcn.wordlib;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.mstfcn.wordlib.crudfiles.AddCategory;
import com.mstfcn.wordlib.crudfiles.AddWord;
import com.mstfcn.wordlib.crudfiles.UpdateCategory;
import com.mstfcn.wordlib.crudfiles.UpdateWord;
import com.mstfcn.wordlib.databasefiles.TableCategories;
import com.mstfcn.wordlib.databasefiles.TableWords;
import com.mstfcn.wordlib.objects.Categories;
import com.mstfcn.wordlib.objects.Words;
import com.mstfcn.wordlib.tabs.TabCategories;
import com.mstfcn.wordlib.tabs.TabWords;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    Context context = this;
    TableWords tbl_wrd;
    TableCategories tbl_cat;
    AlertDialog.Builder alertd_builder;
    AlertDialog alert;
    Categories cc;
    Words ww;

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_category:
                startActivity(new Intent(this, AddCategory.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        tbl_wrd = new TableWords(context);
        tbl_cat = new TableCategories(context);

        alertd_builder = new AlertDialog.Builder(context);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, AddWord.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle(R.string.actions_title);
        if (v.getId() == R.id.cc_lst_listCategory) {
            MenuInflater mnu = getMenuInflater();
            mnu.inflate(R.menu.category_context_menu, menu);
        }
        if (v.getId() == R.id.cw_lst_listWord) {
            MenuInflater mnu = getMenuInflater();
            mnu.inflate(R.menu.word_context_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        View v = info.targetView;
        Intent intnt;

        if (tbl_cat.ListCategory().size() - 1 >= info.position && (item.getItemId() == R.id.menu_category_edit || item.getItemId() == R.id.menu_category_delete)) {
            cc = tbl_cat.ListCategory().get(info.position);
            switch (item.getItemId()) {
                case R.id.menu_category_edit:
                    intnt = new Intent(context, UpdateCategory.class);
                    intnt.putExtra("cid", cc.getId());
                    startActivity(intnt);
                    return true;
                case R.id.menu_category_delete:
                    alertd_builder.setTitle(R.string.msg_warning);
                    alertd_builder.setMessage(R.string.question_delete);
                    alertd_builder.setCancelable(true);
                    alertd_builder.setPositiveButton(R.string.answer_yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            tbl_cat.DeleteCategory(cc.getId());
                            TabCategories.refreshCat();
                            Toast.makeText(context, R.string.msg_category_deleted, Toast.LENGTH_SHORT).show();
                        }
                    });
                    alertd_builder.setNegativeButton(R.string.answer_no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alert = alertd_builder.create();
                    alert.show();
                    return true;
            }
        } else if (tbl_wrd.ListWord().size() - 1 >= info.position && (item.getItemId() == R.id.menu_word_edit || item.getItemId() == R.id.menu_word_delete)) {
            ww = tbl_wrd.ListWord().get(info.position);

            switch (item.getItemId()) {
                case R.id.menu_word_edit:
                    intnt = new Intent(context, UpdateWord.class);
                    intnt.putExtra("wid", ww.getId());
                    startActivity(intnt);
                    return true;
                case R.id.menu_word_delete:
                    alertd_builder.setTitle(R.string.msg_warning);
                    alertd_builder.setMessage(R.string.question_delete);
                    alertd_builder.setCancelable(true);
                    alertd_builder.setPositiveButton(R.string.answer_yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            tbl_wrd.DeleteWord(ww.getId());
                            TabWords.refreshWord();
                            Toast.makeText(context, R.string.msg_word_deleted, Toast.LENGTH_SHORT).show();
                        }
                    });
                    alertd_builder.setNegativeButton(R.string.answer_no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alert = alertd_builder.create();
                    alert.show();
                    return true;
            }
        }
        return false;
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                Fragment f1 = TabCategories.newInstance();
                return f1;
            } else if (position == 1) {
                Fragment f2 = TabWords.newInstance();
                return f2;
            }

            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
