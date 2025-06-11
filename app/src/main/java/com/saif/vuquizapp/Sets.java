package com.saif.vuquizapp;

import static com.google.android.gms.ads.AdRequest.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class Sets extends AppCompatActivity {
GridView gridView;
private Toolbar toolbar;
private InterstitialAd mInterstitialAd;
int count = 50;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets);
        toolbar = findViewById(R.id.toolBarBookmark);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setTitle("Sets");

        gridView = (GridView) findViewById(R.id.simpleGridView);
    loadAds();
//setsAdapter adapter = new setsAdapter(count);
//setsAdapter adapter = new setsAdapter(getIntent().getIntExtra("sets",0));
setsAdapter adapter = new setsAdapter(getIntent().getIntExtra("sets",0),getIntent().getStringExtra("text"));
gridView.setAdapter(adapter);
//gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Toast.makeText(getApplicationContext(), "Click item "+ position, Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(getApplicationContext(), Questions.class);
//        startActivity(intent);
//    }
//});
    }
    private void loadAds(){
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new Builder().build();
        mAdView.loadAd(adRequest);

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