package com.video_editor.pro.ActivitySplash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.video_editor.pro.ActivityMain.ActivityMain;
import com.video_editor.pro.ActivityNoInternet.ActivityNoInternet;
import com.video_editor.pro.R;

public class ActivitySplash extends AppCompatActivity {
    int value = 0;
    private InterstitialAd mInterstitialAd;
    private AdRequest adRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        isConnected(this);
        MobileAds.initialize(this, getString(R.string.app_id_admob));
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_id_admob));
        adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                adRequest = new AdRequest.Builder().build();
                mInterstitialAd.loadAd(adRequest);
                super.onAdClosed();

                if (value == 1) {

                    if (isConnected(ActivitySplash.this)){
                        Intent intent = new Intent(ActivitySplash.this, ActivityMain.class);
                        startActivity(intent);
                    } else {
                        Intent n = new Intent(ActivitySplash.this, ActivityNoInternet.class);
                        startActivity(n);
                    }

                }

            }
        });
        int SPLASH_TIME_OUT = 2500;
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                    value = 1;
                }
                else {
                    if (isConnected(ActivitySplash.this)){
                        Intent intent = new Intent(ActivitySplash.this, ActivityMain.class);
                        startActivity(intent);
                    } else {
                        Intent n = new Intent(ActivitySplash.this, ActivityNoInternet.class);
                        startActivity(n);
                    }
                }
                finish();
            }
        }, SPLASH_TIME_OUT);

    }



    public boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifiInfo != null && wifiInfo.isConnected()) || (mobileInfo != null && mobileInfo.isConnected())) {
            return true;
        } else {

            return false;
        }


    }


}