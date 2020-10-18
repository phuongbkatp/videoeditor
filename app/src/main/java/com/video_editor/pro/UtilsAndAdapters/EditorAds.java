package com.video_editor.pro.UtilsAndAdapters;

import android.content.Context;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.video_editor.pro.R;

public class EditorAds {

    public EditorAds() {

    }

    InterstitialAd mInterstitialAd;

    public void loadAd(Context context){
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(context.getResources().getString(R.string.interstitial_id_admob));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    public void loadd(final AdLisoner adLisoner){
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {


                }

                @Override
                public void onAdFailedToLoad(int errorCode) {

                    adLisoner.onun();
                }

                @Override
                public void onAdOpened() {


                }

                @Override
                public void onAdClicked() {


                }

                @Override
                public void onAdLeftApplication() {


                }

                @Override public void onAdClosed() {

                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                    adLisoner.onSucssec(mInterstitialAd);
                }
            });
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                adLisoner.onun();
            }
        } else {
            adLisoner.onun();
        }
    }

    public interface AdLisoner {
        void onSucssec(InterstitialAd mInterstitialAd);
        void onun();
    }

}