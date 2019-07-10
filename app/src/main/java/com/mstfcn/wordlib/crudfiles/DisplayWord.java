package com.mstfcn.wordlib.crudfiles;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.speech.tts.TextToSpeech;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mstfcn.wordlib.R;
import com.mstfcn.wordlib.databasefiles.TableCategories;
import com.mstfcn.wordlib.databasefiles.TableWords;
import com.mstfcn.wordlib.objects.Words;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.Locale;

public class DisplayWord extends AppCompatActivity implements TextToSpeech.OnInitListener {

    TextView txt_dwFromLang, txt_dwToLang, txt_dwCategory, txt_dwtranslateFrom, txt_dwtranslateTo;
    Integer wid;
    TableWords tbl_wrd;
    TableCategories tbl_cat;
    Words wrd;
    Context context = this;
    AlertDialog.Builder alertd_builder;
    AlertDialog alert;
    TextToSpeech tts;
    String[] supported_languages;
    ImageButton speak_from_language_button, speak_to_language_button;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_word);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        speak_from_language_button = findViewById(R.id.speak_from_language_button);
        speak_to_language_button = findViewById(R.id.speak_to_language_button);

        supported_languages = new String[]{"Türkçe", "Almanca", "İngilizce", "Fransızca", "İspanyolca", "İtalyanca", "Portekizce", "Çince","Korece", "Turkish", "German", "English", "French", "Spanish", "Italian", "Portuguese", "Chinese","Korean"};

        alertd_builder = new AlertDialog.Builder(context);
        tts = new TextToSpeech(this, this);

    }

    void updateValues() {
        tbl_wrd = new TableWords(context);
        tbl_cat = new TableCategories(context);

        wid = getIntent().getIntExtra("wid", 0);
        wrd = tbl_wrd.GetWord(wid);

        txt_dwFromLang = findViewById(R.id.adw_txt_dwFromLang);
        txt_dwToLang = findViewById(R.id.adw_txt_dwToLang);
        txt_dwtranslateFrom = findViewById(R.id.adw_txt_dwtranslateFrom);
        txt_dwtranslateTo = findViewById(R.id.adw_txt_dwtranslateTo);
        txt_dwCategory = findViewById(R.id.adw_txt_dwCategory);

        txt_dwFromLang.setText(wrd.getFromLanguage());
        txt_dwToLang.setText(wrd.getToLanguage());
        txt_dwtranslateFrom.setText(wrd.getFromWord());
        txt_dwtranslateTo.setText(wrd.getToWord());
        txt_dwCategory.setText(tbl_cat.GetCategory(wrd.getCid()).getCatName());
    }

    public void SpeakFromLanguage(View v) {

        Locale locale;
        if (wrd.getFromLanguage().equals("İngilizce") || wrd.getFromLanguage().equals("English")) {
            tts.setLanguage(Locale.ENGLISH);
        } else if (wrd.getFromLanguage().equals("Türkçe") || wrd.getFromLanguage().equals("Turkish")) {
            locale = new Locale("TR", "tr");
            tts.setLanguage(locale);
        } else if (wrd.getFromLanguage().equals("Almanca") || wrd.getFromLanguage().equals("German")) {
            tts.setLanguage(Locale.GERMAN);
        } else if (wrd.getFromLanguage().equals("Fransızca") || wrd.getFromLanguage().equals("French")) {
            tts.setLanguage(Locale.FRENCH);
        } else if (wrd.getFromLanguage().equals("İspanyolca") || wrd.getFromLanguage().equals("Spanish")) {
            locale = new Locale("ES", "es");
            tts.setLanguage(locale);
        } else if (wrd.getFromLanguage().equals("İtalyanca") || wrd.getFromLanguage().equals("Italian")) {
            tts.setLanguage(Locale.ITALIAN);
        } else if (wrd.getFromLanguage().equals("Portekizce") || wrd.getFromLanguage().equals("Portuguese")) {
            locale = new Locale("PT", "pt");
            tts.setLanguage(locale);
        } else if (wrd.getFromLanguage().equals("Çince") || wrd.getFromLanguage().equals("Chinese")) {
            tts.setLanguage(Locale.CHINESE);
        } else if (wrd.getFromLanguage().equals("Korece") || wrd.getFromLanguage().equals("Korean")) {
            tts.setLanguage(Locale.KOREAN);
        } else {
            tts.setLanguage(Locale.getDefault());
        }

        tts.speak(wrd.getFromWord(), TextToSpeech.QUEUE_ADD, null);

    }

    public void SpeakToLanguage(View v) {

        Locale locale;
        if (wrd.getToLanguage().equals("İngilizce") || wrd.getFromLanguage().equals("English")) {
            tts.setLanguage(Locale.ENGLISH);
        } else if (wrd.getToLanguage().equals("Türkçe") || wrd.getToLanguage().equals("Turkish")) {
            locale = new Locale("TR", "tr");
            tts.setLanguage(locale);
        } else if (wrd.getToLanguage().equals("Almanca") || wrd.getToLanguage().equals("German")) {
            tts.setLanguage(Locale.GERMAN);
        } else if (wrd.getToLanguage().equals("Fransızca") || wrd.getToLanguage().equals("French")) {
            tts.setLanguage(Locale.FRENCH);
        } else if (wrd.getToLanguage().equals("İspanyolca") || wrd.getToLanguage().equals("Spanish")) {
            locale = new Locale("ES", "es");
            tts.setLanguage(locale);
        } else if (wrd.getToLanguage().equals("İtalyanca") || wrd.getToLanguage().equals("Italian")) {
            tts.setLanguage(Locale.ITALIAN);
        } else if (wrd.getToLanguage().equals("Portekizce") || wrd.getToLanguage().equals("Portuguese")) {
            locale = new Locale("PT", "pt");
            tts.setLanguage(locale);
        } else if (wrd.getToLanguage().equals("Çince") || wrd.getToLanguage().equals("Chinese")) {
            tts.setLanguage(Locale.CHINESE);
        } else if (wrd.getToLanguage().equals("Korece") || wrd.getToLanguage().equals("Korean")) {
            tts.setLanguage(Locale.KOREAN);
        } else {
            tts.setLanguage(Locale.getDefault());
        }

        tts.speak(wrd.getToWord(), TextToSpeech.QUEUE_ADD, null);

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateValues();

        if (!Arrays.asList(supported_languages).contains(wrd.getFromLanguage())) {
            speak_from_language_button.setEnabled(false);
            speak_from_language_button.setBackground(ContextCompat.getDrawable(context, R.drawable.speak_button_border_radius_disable));
        }
        if (!Arrays.asList(supported_languages).contains(wrd.getToLanguage())) {
            speak_to_language_button.setEnabled(false);
            speak_to_language_button.setBackground(ContextCompat.getDrawable(context, R.drawable.speak_button_border_radius_disable));
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tts.stop();
        tts.shutdown();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void DisplayUpdateButton(View v) {
        Intent intnt = new Intent(context, UpdateWord.class);
        intnt.putExtra("wid", wid);
        startActivity(intnt);
    }

    public void DisplayDeleteButton(View v) {
        alertd_builder.setTitle(R.string.msg_warning);
        alertd_builder.setMessage(R.string.question_delete);
        alertd_builder.setCancelable(true);
        alertd_builder.setPositiveButton(R.string.answer_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tbl_wrd.DeleteWord(wid);
                Toast.makeText(context, R.string.msg_word_deleted, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        alertd_builder.setNegativeButton(R.string.answer_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert = alertd_builder.create();
        alert.show();
    }

    @Override
    public void onInit(int i) {
        if(i==TextToSpeech.ERROR){
            Toast.makeText(context, R.string.speak_word_error, Toast.LENGTH_LONG).show();
        }
    }
}
