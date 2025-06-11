package com.saif.vuquizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class Result extends AppCompatActivity {
private TextView scoure, totalMarks, progressResult;
    private InterstitialAd mInterstitialAd;
    private Toolbar toolbar;
private Button done;
private ProgressBar progressBar;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        scoure = findViewById(R.id.scoure);
        totalMarks = findViewById(R.id.totalMarks);
        done = findViewById(R.id.doneBtn);
        toolbar = findViewById(R.id.toolBarBookmark);
        progressBar = findViewById(R.id.progressBar);
        progressResult = findViewById(R.id.progressResult);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setTitle("Result");
        loadAds();
        interstialAds();
        scoure.setText(String.valueOf(getIntent().getIntExtra("score", 0)));
//        totalMarks.setText("OUT OF " + String.valueOf(getIntent().getIntExtra("total",0)));
        totalMarks.setText(String.valueOf(getIntent().getIntExtra("total", 0)));
        String num1 = scoure.getText().toString();
        String num2 = totalMarks.getText().toString();

        float earn = Integer.parseInt(num1);
        float total = Integer.parseInt(num2);
        float value = ((earn / total) * 100);
//        progressResult.setText(String.valueOf((int) value));
//        progressBar.setProgress(Integer.parseInt(String.valueOf(value)));
        progressBar.setProgress((int) value);
        progressResult.setText(String.valueOf((int) value)+"%");
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(Result.this);
                    mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();
                            finish();
                        }
                    });

                } else {
                    finish();
                }

            }
        });
    }
    private void loadAds(){
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
    public void interstialAds(){
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,"ca-app-pub-9612630357269923/9277107724", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Toast.makeText(Result.this, "Ad Loaded", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Toast.makeText(Result.this, "Error is: "+loadAdError.getMessage(), Toast.LENGTH_SHORT).show();


                        mInterstitialAd = null;
                    }
                });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}