package com.mstfcn.wordlib;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1500;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Animation anim_logo = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.logo_splash);
        logo = findViewById(R.id.logo);
        logo.startAnimation(anim_logo);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent main_intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(main_intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
