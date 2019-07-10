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

public class UpdateWord extends AppCompatActivity {

    Spinner languageFrom, languageTo, spnCategory;
    TableCategories tb_ctg;
    TableWords tbl_wrds;
    TextView txt_wordFromLang, txt_wordToLang;
    EditText edt_translateFrom, txt_translateTo;
    Context context = this;
    String[] languages, languages_abb;
    AlertDialog.Builder alertd_builder;
    AlertDialog alert;
    TableWords tbl_word;
    Words wrds;
    Integer wid;
    Button update_button;

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
        setContentView(R.layout.activity_update_word);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.panel_word_update);

        tbl_word = new TableWords(context);

        languageFrom = findViewById(R.id.auw_spn_ulistLanguageFrom);
        languageTo = findViewById(R.id.auw_spn_ulistLanguageTo);
        spnCategory = findViewById(R.id.auw_spn_ucategory);
        txt_wordFromLang = findViewById(R.id.auw_txt_uwordFromLang);
        txt_wordToLang = findViewById(R.id.auw_txt_uwordToLang);
        edt_translateFrom = findViewById(R.id.auw_edt_utranslateFrom);
        txt_translateTo = findViewById(R.id.auw_txt_utranslateTo);
        update_button = findViewById(R.id.update_button);

        languages = getResources().getStringArray(R.array.languages);
        languages_abb = getResources().getStringArray(R.array.languages_abb);

        ArrayAdapter lf = new ArrayAdapter(this, R.layout.spinner_row, languages);
        languageFrom.setAdapter(lf);

        ArrayAdapter lt = new ArrayAdapter(this, R.layout.spinner_row, languages);
        languageTo.setAdapter(lt);

        wid = getIntent().getIntExtra("wid", 0);
        tbl_wrds = new TableWords(context);
        wrds = tbl_wrds.GetWord(wid);

        languageFrom.setSelection(ArrayItemPosition(languages, wrds.getFromLanguage()));
        languageTo.setSelection(ArrayItemPosition(languages, wrds.getToLanguage()));

        txt_wordFromLang.setText(languageFrom.getSelectedItem().toString() + ":");
        txt_wordToLang.setText(languageTo.getSelectedItem().toString() + ":");

        tb_ctg = new TableCategories(this);
        String[] cat_name_list = new String[tb_ctg.ListCategory().size()];
        for (Integer i = 0; i < cat_name_list.length; i++) {
            cat_name_list[i] = tb_ctg.ListCategory().get(i).getCatName();
        }

        ArrayAdapter cl = new ArrayAdapter(this, R.layout.spinner_row, cat_name_list);
        spnCategory.setAdapter(cl);

        spnCategory.setSelection(ArrayItemPosition(cat_name_list, tb_ctg.GetCategory(wrds.getCid()).getCatName()));

        edt_translateFrom.setText(wrds.getFromWord());
        txt_translateTo.setText(wrds.getToWord());

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

    int ArrayItemPosition(String[] list, String value) {
        int i = 0;
        while (!list[i].equals(value)) {
            i++;
        }
        return i;
    }

    public void UpdateWord(View v) {
        if (edt_translateFrom.getText().toString().trim().equals("")) {
            createAlert(R.string.msg_empty_word, R.string.msg_warning, R.string.okey);
        } else {
            if (spnCategory.getSelectedItem() != null) {

                String fromWord = edt_translateFrom.getText().toString().trim();
                String toWord = txt_translateTo.getText().toString().trim();
                Integer cid = tb_ctg.ListCategory().get(spnCategory.getSelectedItemPosition()).getId();
                String fromLanguage = languageFrom.getSelectedItem().toString();
                String toLanguage = languageTo.getSelectedItem().toString();

                tbl_word.UpdateWord(wid, new Words(fromWord, toWord, cid, fromLanguage, toLanguage));
                createAlert(R.string.msg_word_updated, R.string.msg_info, R.string.okey);

            } else {
                createAlert(R.string.msg_no_selected_category, R.string.msg_warning, R.string.okey);
            }
        }
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

    void Translate(String wordFrom, String languagePair, EditText translateFrom, final EditText translateTo) {

        if (!translateFrom.getText().toString().trim().equals("")) {

            YandexTranslate translatorBackgroundTask = new YandexTranslate(new YandexTranslate.TaskListener() {
                @Override
                public void onProcess() {
                    translateTo.setText(R.string.translating);
                    update_button.setEnabled(false);
                    update_button.setText(R.string.button_please_wait);
                }

                @Override
                public void onFinished(String result) {
                    translateTo.setText(result);
                    update_button.setEnabled(true);
                    update_button.setText(R.string.button_update);
                }
            });
            translatorBackgroundTask.execute(wordFrom, languagePair);
        }

    }
}
