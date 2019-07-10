package com.mstfcn.wordlib.crudfiles;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.mstfcn.wordlib.R;
import com.mstfcn.wordlib.adapters.CustomWordsOnCatAdapter;
import com.mstfcn.wordlib.databasefiles.TableCategories;
import com.mstfcn.wordlib.databasefiles.TableWords;
import com.mstfcn.wordlib.objects.Words;

import java.util.ArrayList;
import java.util.List;

public class ListWordsOnCat extends AppCompatActivity {

    ListView lst;
    TableWords tbl_wrds;
    TableCategories tbl_cat;
    CustomWordsOnCatAdapter cwca;
    Context context = this;
    Words ww;
    AlertDialog.Builder alertd_builder;
    AlertDialog alert;
    Integer id;
    EditText search_word;
    TextView search_not_found;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_words_on_cat);

        search_word=findViewById(R.id.search_word);
        search_not_found=findViewById(R.id.search_not_found);

        //Tamamen gizlendi
        search_not_found.setVisibility(View.GONE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        id = getIntent().getIntExtra("cid", 0);

        tbl_cat=new TableCategories(context);
        getSupportActionBar().setTitle(tbl_cat.GetCategory(id).getCatName());

        lst = findViewById(R.id.lwoc_lst_listWordOnCat);
        tbl_wrds = new TableWords(context);
        cwca = new CustomWordsOnCatAdapter(context, tbl_wrds.ListWordsOnCat(id));
        lst.setAdapter(cwca);

        alertd_builder=new AlertDialog.Builder(context);

        registerForContextMenu(lst);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Words ww = tbl_wrds.ListWordsOnCat(id).get(i);
                Intent intnt = new Intent(context, DisplayWord.class);
                intnt.putExtra("wid", ww.getId());
                startActivity(intnt);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, AddWord.class);
                intent.putExtra("cid", id);
                startActivity(intent);
            }
        });

    }

   @Override
   protected void onResume() {
        super.onResume();
        search_word.setText("");
        search_word.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<Words> arrWords = tbl_wrds.ListWord();
                List<Words> tempWords = new ArrayList<>();
                for (int k = 0; k < arrWords.size(); k++) {
                    if ((arrWords.get(k).getFromWord().toLowerCase().contains(charSequence.toString().toLowerCase())
                            || arrWords.get(k).getToWord().toLowerCase().contains(charSequence.toString().toLowerCase()))
                    && arrWords.get(k).getCid().equals(id)) {

                        tempWords.add(arrWords.get(k));
                    }
                }

                if (tempWords != null && tempWords.size() > 0) {
                    TableWords.setSearcList(tempWords);
                    cwca = new CustomWordsOnCatAdapter(context, tempWords);
                    lst.setVisibility(View.VISIBLE);
                    search_not_found.setVisibility(View.GONE);
                    lst.setAdapter(cwca);
                } else {
                    TableWords.setSearcList(null);
                    lst.setAdapter(null);
                    lst.setVisibility(View.GONE);
                    search_not_found.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (search_word.getText().toString().trim().equals("")) {
                    TableWords.setSearcList(null);
                    cwca = new CustomWordsOnCatAdapter(context, tbl_wrds.ListWordsOnCat(id));
                    lst.setAdapter(cwca);
                    lst.setVisibility(View.VISIBLE);
                    search_not_found.setVisibility(View.GONE);
                }
            }
        });

        search_word.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    List<Words> arrWords = tbl_wrds.ListWord();
                    List<Words> tempWords = new ArrayList<>();
                    for (int k = 0; k < arrWords.size(); k++) {
                        if ((arrWords.get(k).getFromWord().toLowerCase().contains(textView.getText().toString().toLowerCase())
                                || arrWords.get(k).getToWord().toLowerCase().contains(textView.getText().toString().toLowerCase()))
                                && arrWords.get(k).getCid().equals(id)) {

                            tempWords.add(arrWords.get(k));
                        }
                    }

                    if (tempWords != null && tempWords.size() > 0) {
                        TableWords.setSearcList(tempWords);
                        cwca = new CustomWordsOnCatAdapter(context, tempWords);
                        lst.setVisibility(View.VISIBLE);
                        search_not_found.setVisibility(View.GONE);
                        lst.setAdapter(cwca);
                    } else {
                        TableWords.setSearcList(null);
                        lst.setAdapter(null);
                        lst.setVisibility(View.GONE);
                        search_not_found.setVisibility(View.VISIBLE);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle(R.string.actions_title);
        MenuInflater mnu = getMenuInflater();
        mnu.inflate(R.menu.word_context_menu, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        View v = info.targetView;
        Intent intnt;

        if (tbl_wrds.ListWord().size() - 1 >= info.position && (item.getItemId() == R.id.menu_word_edit || item.getItemId() == R.id.menu_word_delete)) {
            ww = tbl_wrds.ListWordsOnCat(id).get(info.position);

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
                            tbl_wrds.DeleteWord(ww.getId());
                            cwca.refreshWords(tbl_wrds.ListWordsOnCat(id));
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
}
