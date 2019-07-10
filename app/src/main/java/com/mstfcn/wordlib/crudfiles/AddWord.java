package com.mstfcn.wordlib.crudfiles;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mstfcn.wordlib.R;
import com.mstfcn.wordlib.databasefiles.TableCategories;
import com.mstfcn.wordlib.databasefiles.TableWords;
import com.mstfcn.wordlib.library.YandexTranslate;
import com.mstfcn.wordlib.objects.Words;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class AddWord extends AppCompatActivity {

    Spinner languageFrom, languageTo, spnCategory;
    TableCategories tb_ctg;
    TextView txt_wordFromLang, txt_wordToLang;
    EditText edt_translateFrom, txt_translateTo;
    Context context = this;
    String[] languages, languages_abb;
    AlertDialog.Builder alertd_builder;
    AlertDialog alert;
    TableWords tbl_word;
    Button save_button;
    Integer id;
    Integer select_category_id;

    protected boolean internetKontrol() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    void createAlert(int message, int title, int button_text) {
        alertd_builder = new AlertDialog.Builder(context);
        alertd_builder.setMessage(message);
        alertd_builder.setCancelable(true);
        alertd_builder.setTitle(title);
        alertd_builder.setPositiveButton(button_text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert = alertd_builder.create();
        alert.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.panel_word_add);

        tbl_word = new TableWords(context);

        languageFrom = findViewById(R.id.aaw_spn_listLanguageFrom);
        languageTo = findViewById(R.id.aaw_spn_listLanguageTo);
        spnCategory = findViewById(R.id.aaw_spn_category);
        txt_wordFromLang = findViewById(R.id.aaw_txt_wordFromLang);
        txt_wordToLang = findViewById(R.id.aaw_txt_wordToLang);
        edt_translateFrom = findViewById(R.id.aaw_edt_translateFrom);
        txt_translateTo = findViewById(R.id.aaw_txt_translateTo);
        save_button = findViewById(R.id.save_button);

        languages = getResources().getStringArray(R.array.languages);
        languages_abb = getResources().getStringArray(R.array.languages_abb);

        ArrayAdapter lf = new ArrayAdapter(this, R.layout.spinner_row, languages);
        languageFrom.setAdapter(lf);

        ArrayAdapter lt = new ArrayAdapter(this, R.layout.spinner_row, languages);
        languageTo.setAdapter(lt);

        languageFrom.setSelection(36);//İngilizce
        languageTo.setSelection(87);//Türkçe

        txt_wordFromLang.setText(languageFrom.getSelectedItem().toString() + ":");
        txt_wordToLang.setText(languageTo.getSelectedItem().toString() + ":");

        if (getIntent().hasExtra("cid")) {
            id = getIntent().getIntExtra("cid", 0);
        }

        tb_ctg = new TableCategories(this);
        String[] cat_name_list = new String[tb_ctg.ListCategory().size()];
        for (Integer i = 0; i < cat_name_list.length; i++) {
            cat_name_list[i] = tb_ctg.ListCategory().get(i).getCatName();
            if (getIntent().hasExtra("cid")) {
                if (tb_ctg.ListCategory().get(i).getId().equals(id)) {
                    select_category_id = i;
                }
            }

        }

        ArrayAdapter cl = new ArrayAdapter(this, R.layout.spinner_row, cat_name_list);
        spnCategory.setAdapter(cl);

        if (getIntent().hasExtra("cid")) {
            spnCategory.setSelection(select_category_id);
        }

        languageFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                txt_wordFromLang.setText(languageFrom.getSelectedItem().toString() + ":");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        languageTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                txt_wordToLang.setText(languageTo.getSelectedItem().toString() + ":");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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

    public void TranslateIt(View v) throws UnsupportedEncodingException {

        if (internetKontrol()) {

            //Ekran da açılan klavyeyi gizler.
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

            String languagePair = languages_abb[languageFrom.getSelectedItemPosition()] + "-" + languages_abb[languageTo.getSelectedItemPosition()];
            Translate(URLEncoder.encode(edt_translateFrom.getText().toString().trim(), "utf-8"), languagePair, edt_translateFrom, txt_translateTo);
        } else {
            createAlert(R.string.msg_no_wifi_connection, R.string.msg_warning, R.string.okey);
        }
    }

    public void InsertWord(View v) {
        if (edt_translateFrom.getText().toString().trim().equals("")) {
            createAlert(R.string.msg_empty_word, R.string.msg_warning, R.string.okey);
        } else {
            if (spnCategory.getSelectedItem() != null) {

                String fromWord = edt_translateFrom.getText().toString().trim();
                String toWord = txt_translateTo.getText().toString().trim();
                Integer cid = tb_ctg.ListCategory().get(spnCategory.getSelectedItemPosition()).getId();
                String fromLanguage = languageFrom.getSelectedItem().toString();
                String toLanguage = languageTo.getSelectedItem().toString();

                tbl_word.AddWord(new Words(fromWord, toWord, cid, fromLanguage, toLanguage));
                createAlert(R.string.msg_word_saved, R.string.msg_info, R.string.okey);
                edt_translateFrom.setText("");
                txt_translateTo.setText("");

            } else {
                createAlert(R.string.msg_no_selected_category, R.string.msg_warning, R.string.okey);
            }
        }
    }

    void Translate(String wordFrom, String languagePair, EditText translateFrom, final EditText translateTo) {
        if (!translateFrom.getText().toString().trim().equals("")) {

            YandexTranslate translatorBackgroundTask = new YandexTranslate(new YandexTranslate.TaskListener() {
                @Override
                public void onProcess() {
                    translateTo.setText(R.string.translating);
                    save_button.setEnabled(false);
                    save_button.setText(R.string.button_please_wait);
                }

                @Override
                public void onFinished(String result) {
                    translateTo.setText(result);
                    save_button.setEnabled(true);
                    save_button.setText(R.string.button_save);
                }
            });

            translatorBackgroundTask.execute(wordFrom, languagePair);

        }

    }
}
