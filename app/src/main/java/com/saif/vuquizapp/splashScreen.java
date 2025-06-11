package com.saif.vuquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.airbnb.lottie.LottieAnimationView;

public class splashScreen extends AppCompatActivity {
LottieAnimationView rubix, loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);
        rubix = (LottieAnimationView) findViewById(R.id.rubix);
        loading = (LottieAnimationView) findViewById(R.id.loading);

        final Handler h = new Handler();

        h.postDelayed(new Runnable() {

            @Override

            public void run() {

                //Do something after 1s
                rubix.playAnimation();
                loading.playAnimation();
                Intent intent = new Intent(splashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();

            }

        }, 4000);

    }

}