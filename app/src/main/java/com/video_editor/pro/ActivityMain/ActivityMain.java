package com.video_editor.pro.ActivityMain;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.video_editor.pro.ActivityMusicList.ActivityMusicList;
import com.video_editor.pro.ActivityPhotoVideo.ActivitySelectImageAndVideo;
import com.video_editor.pro.ActivityVideoList.ActivityVideoList;
import com.video_editor.pro.ActivityVideoMusicList.ActivityVideoMusicList;
import com.video_editor.pro.R;
import com.video_editor.pro.UtilsAndAdapters.EditorAdsUtility;
import com.video_editor.pro.UtilsAndAdapters.EditorHelper;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class ActivityMain extends AppCompatActivity {

    private UnifiedNativeAd nativeAd;
    static final boolean a = true;
    private Toolbar toolbar;
    RelativeLayout samplelayout;
    private LinearLayout adviewthree;
    boolean doubleBackToExitPressedOnce = false;
    private InterstitialAd mInterstitialAd;


    public ActivityMain() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE",
                    "android.permission.WRITE_EXTERNAL_STORAGE"}, 101);
        }

        AdView bannerLinear = findViewById(R.id.adLinear);
        AdRequest adRequest = new AdRequest.Builder().build();
        bannerLinear.loadAd(adRequest);


        refreshAd();
        refreshAdAgain();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);



        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_id_admob));


        if (!mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()) {


            mInterstitialAd.loadAd(adRequest);

        }

        this.samplelayout = findViewById(R.id.samplelayout);

        RelativeLayout exit = findViewById(R.id.exit_now);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });


    }



    private void populateUnifiedNativeAdView(UnifiedNativeAd nativeAd, UnifiedNativeAdView adView) {

        MediaView mediaView = adView.findViewById(R.id.ad_media);
        adView.setMediaView(mediaView);


        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));


        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());


        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(VISIBLE);
        }


        adView.setNativeAd(nativeAd);


        VideoController vc = nativeAd.getVideoController();


        if (vc.hasVideoContent()) {


            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                @Override
                public void onVideoEnd() {

                    super.onVideoEnd();
                }
            });
        }
    }

    @Override
    protected void onResume() {

        if (!mInterstitialAd.isLoaded()) {

            mInterstitialAd = new InterstitialAd(this);
            mInterstitialAd.setAdUnitId(getString(R.string.interstitial_id_admob));


            if (!mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()) {

                AdRequest adRequest = new AdRequest.Builder().build();

                mInterstitialAd.loadAd(adRequest);

            }
        }
        super.onResume();
    }




    private void refreshAd() {

        AdLoader.Builder builder = new AdLoader.Builder(this, getResources().getString(R.string.native_id_admob));

            builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {

                if (nativeAd != null) {
                    nativeAd.destroy();
                }
                nativeAd = unifiedNativeAd;
                FrameLayout nativeAdPlaceHolder = ActivityMain.this.findViewById(R.id.nativeAdPlaceHolderTwo);
                @SuppressLint("InflateParams") UnifiedNativeAdView adView = (UnifiedNativeAdView) ActivityMain.this.getLayoutInflater()
                        .inflate(R.layout.native_ad_unified_small_white, null);
                ActivityMain.this.populateUnifiedNativeAdView(unifiedNativeAd, adView);
                nativeAdPlaceHolder.removeAllViews();
                nativeAdPlaceHolder.addView(adView);
                nativeAdPlaceHolder.setVisibility(VISIBLE);
            }
        });
        VideoOptions videoOptions = new VideoOptions.Builder()
                .setStartMuted(true)
                .build();

        NativeAdOptions adOptions = new NativeAdOptions.Builder()
                .setVideoOptions(videoOptions)
                .build();

        builder.withNativeAdOptions(adOptions);

        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.d("failNativeAd", "Failed to load native ad:: ");
            }
        }).build();

        adLoader.loadAd(new AdRequest.Builder().build());

    }


    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        samplelayout.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


    private void refreshAdAgain() {

        AdLoader.Builder builder = new AdLoader.Builder(this, getResources().getString(R.string.native_id_admob));

 builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {

                if (nativeAd != null) {
                    nativeAd.destroy();
                }
                nativeAd = unifiedNativeAd;
                FrameLayout nativeAdPlaceHolder = ActivityMain.this.findViewById(R.id.nativeAdPlaceHolder);
                @SuppressLint("InflateParams") UnifiedNativeAdView adView = (UnifiedNativeAdView) ActivityMain.this.getLayoutInflater()
                        .inflate(R.layout.native_ad_unified_small, null);
                ActivityMain.this.populateUnifiedNativeAdView(unifiedNativeAd, adView);
                nativeAdPlaceHolder.removeAllViews();
                nativeAdPlaceHolder.addView(adView);
                nativeAdPlaceHolder.setVisibility(VISIBLE);
            }
        });
        VideoOptions videoOptions = new VideoOptions.Builder()
                .setStartMuted(true)
                .build();

        NativeAdOptions adOptions = new NativeAdOptions.Builder()
                .setVideoOptions(videoOptions)
                .build();

        builder.withNativeAdOptions(adOptions);

        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.d("failNativeAd", "Failed to load native ad:: ");
            }
        }).build();

        adLoader.loadAd(new AdRequest.Builder().build());

    }


    @Override
    protected void onDestroy() {
        if (nativeAd != null) {
            nativeAd.destroy();
        }
        super.onDestroy();
    }

    public void videocutter(View view) {
        EditorHelper.ModuleId = 1;
        final Intent intent = new Intent(this, ActivityVideoList.class);
        intent.setFlags(67108864);

        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            mInterstitialAd.setAdListener(new AdListener() {

                @Override

                public void onAdClosed() {

                    startActivity(intent);

                }

            });
        } else {
            startActivity(intent);
        }


        finish();
    }

    public void videocompress(View view) {
        EditorHelper.ModuleId = 2;
        final Intent intent = new Intent(this, ActivityVideoList.class);
        intent.setFlags(67108864);
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            mInterstitialAd.setAdListener(new AdListener() {

                @Override

                public void onAdClosed() {

                    startActivity(intent);

                }

            });
        } else {
            startActivity(intent);
        }
        finish();
    }

    public void videotomp3(View view) {
        EditorHelper.ModuleId = 3;
        final Intent intent = new Intent(this, ActivityVideoMusicList.class);
        intent.setFlags(67108864);
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            mInterstitialAd.setAdListener(new AdListener() {

                @Override

                public void onAdClosed() {

                    startActivity(intent);

                }

            });
        } else {
            startActivity(intent);
        }
        finish();
    }


    public void audiovideomixer(View view) {
        EditorHelper.ModuleId = 4;
        final Intent intent = new Intent(this, ActivityVideoList.class);
        intent.setFlags(67108864);
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            mInterstitialAd.setAdListener(new AdListener() {

                @Override

                public void onAdClosed() {

                    startActivity(intent);

                }

            });
        } else {
            startActivity(intent);
        }
        finish();
    }

    public void videomute(View view) {
        EditorHelper.ModuleId = 5;
        final Intent intent = new Intent(this, ActivityVideoList.class);
        intent.setFlags(67108864);
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            mInterstitialAd.setAdListener(new AdListener() {

                @Override

                public void onAdClosed() {

                    startActivity(intent);

                }

            });
        } else {
            startActivity(intent);
        }
        finish();
    }

    public void videojoin(View view) {
        EditorHelper.ModuleId = 6;
        final Intent intent = new Intent(this, com.video_editor.pro.ActivityVideoJoiner.ActivityVideoList.class);
        intent.setFlags(67108864);
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            mInterstitialAd.setAdListener(new AdListener() {

                @Override

                public void onAdClosed() {

                    startActivity(intent);

                }

            });
        } else {
            startActivity(intent);
        }
        finish();
    }

    public void videotoimg(View view) {
        EditorHelper.ModuleId = 7;
        final Intent intent = new Intent(this, com.video_editor.pro.ActivityVideoGIF.ActivityVideoList.class);
        intent.setFlags(67108864);
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            mInterstitialAd.setAdListener(new AdListener() {

                @Override

                public void onAdClosed() {

                    startActivity(intent);

                }

            });
        } else {
            startActivity(intent);
        }
        finish();
    }

    public void videoformatchange(View view) {
        EditorHelper.ModuleId = 8;
        final Intent intent = new Intent(this, ActivityVideoList.class);
        intent.setFlags(67108864);
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            mInterstitialAd.setAdListener(new AdListener() {

                @Override

                public void onAdClosed() {

                    startActivity(intent);

                }

            });
        } else {
            startActivity(intent);
        }
        finish();
    }

    public void fastmotion(View view) {
        EditorHelper.ModuleId = 9;
        final Intent intent = new Intent(this, ActivityVideoList.class);
        intent.setFlags(67108864);
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            mInterstitialAd.setAdListener(new AdListener() {

                @Override

                public void onAdClosed() {

                    startActivity(intent);

                }

            });
        } else {
            startActivity(intent);
        }
        finish();
    }

    public void slowmotion(View view) {
        EditorHelper.ModuleId = 10;
        final Intent intent = new Intent(this, ActivityVideoList.class);
        intent.setFlags(67108864);
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            mInterstitialAd.setAdListener(new AdListener() {

                @Override

                public void onAdClosed() {

                    startActivity(intent);

                }

            });
        } else {
            startActivity(intent);
        }
        finish();
    }

    public void videocrop(View view) {
        EditorHelper.ModuleId = 11;
        final Intent intent = new Intent(this, ActivityVideoList.class);
        intent.setFlags(67108864);
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            mInterstitialAd.setAdListener(new AdListener() {

                @Override

                public void onAdClosed() {

                    startActivity(intent);

                }

            });
        } else {
            startActivity(intent);
        }
        finish();
    }

    public void videotogif(View view) {
        EditorHelper.ModuleId = 12;
        final Intent intent = new Intent(this, com.video_editor.pro.ActivityVideoGIF.ActivityVideoList.class);
        intent.setFlags(67108864);
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            mInterstitialAd.setAdListener(new AdListener() {

                @Override

                public void onAdClosed() {

                    startActivity(intent);

                }

            });
        } else {
            startActivity(intent);
        }
        finish();
    }

    public void videorotate(View view) {
        EditorHelper.ModuleId = 13;
        final Intent intent = new Intent(this, ActivityVideoList.class);
        intent.setFlags(67108864);
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            mInterstitialAd.setAdListener(new AdListener() {

                @Override

                public void onAdClosed() {

                    startActivity(intent);

                }

            });
        } else {
            startActivity(intent);
        }
        finish();
    }


    public void videomirror(View view) {
        EditorHelper.ModuleId = 14;
        final Intent intent = new Intent(this, ActivityVideoList.class);
        intent.setFlags(67108864);
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            mInterstitialAd.setAdListener(new AdListener() {

                @Override

                public void onAdClosed() {

                    startActivity(intent);

                }

            });
        } else {
            startActivity(intent);
        }
        finish();
    }

    public void videosplit(View view) {
        EditorHelper.ModuleId = 15;
        final Intent intent = new Intent(this, ActivityVideoList.class);
        intent.setFlags(67108864);
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            mInterstitialAd.setAdListener(new AdListener() {

                @Override

                public void onAdClosed() {

                    startActivity(intent);

                }

            });
        } else {
            startActivity(intent);
        }
        finish();
    }

    public void videoreverse(View view) {
        EditorHelper.ModuleId = 16;
        final Intent intent = new Intent(this, ActivityVideoList.class);
        intent.setFlags(67108864);
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            mInterstitialAd.setAdListener(new AdListener() {

                @Override

                public void onAdClosed() {

                    startActivity(intent);

                }

            });
        } else {
            startActivity(intent);
        }
        finish();
    }


    public void audiocompress(View view) {
        EditorHelper.ModuleId = 18;
        final Intent intent = new Intent(this, ActivityMusicList.class);
        intent.setFlags(67108864);
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            mInterstitialAd.setAdListener(new AdListener() {

                @Override
                public void onAdClosed() {

                    startActivity(intent);

                }

            });
        } else {
            startActivity(intent);
        }
        finish();
    }

    public void audiojoiner(View view) {
        EditorHelper.ModuleId = 19;
        final Intent intent = new Intent(this, ActivityMusicList.class);
        intent.setFlags(67108864);
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            mInterstitialAd.setAdListener(new AdListener() {

                @Override

                public void onAdClosed() {

                    startActivity(intent);

                }

            });
        } else {
            startActivity(intent);
        }
        finish();
    }

    public void audiocutter(View view) {
        EditorHelper.ModuleId = 20;
        final Intent intent = new Intent(this, ActivityMusicList.class);
        intent.setFlags(67108864);
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            mInterstitialAd.setAdListener(new AdListener() {

                @Override

                public void onAdClosed() {

                    startActivity(intent);

                }

            });
        } else {
            startActivity(intent);
        }
        finish();
    }

    public void phototovideo(View view) {
        EditorHelper.ModuleId = 21;
        final Intent intent = new Intent(this, ActivitySelectImageAndVideo.class);
        intent.setFlags(67108864);
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            mInterstitialAd.setAdListener(new AdListener() {

                @Override

                public void onAdClosed() {

                    startActivity(intent);

                }

            });
        } else {
            startActivity(intent);
        }
        finish();
    }

    public void videowatermark(View view) {
        EditorHelper.ModuleId = 22;
        final Intent intent = new Intent(this, ActivityVideoList.class);
        intent.setFlags(67108864);
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
            mInterstitialAd.setAdListener(new AdListener() {

                @Override

                public void onAdClosed() {

                    startActivity(intent);

                }

            });
        } else {
            startActivity(intent);
        }
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return a;
    }

    public void DisappearExitDialog(View view) {
        samplelayout.setVisibility(INVISIBLE);
    }














    }

