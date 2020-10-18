package com.video_editor.pro.ActivityVideoToImage;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.video_editor.pro.UtilsAndAdapters.EditorHelper;
import com.video_editor.pro.R;
import com.video_editor.pro.UtilsAndAdapters.EditorVideoPlayer;
import com.video_editor.pro.ActivityVideoGIF.ActivityVideoList;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

@SuppressLint({"WrongConstant"})
public class ActivityVideoToImage extends AppCompatActivity implements OnSeekBarChangeListener {
    public static String PATH = "";
    public static ArrayList<Integer> myList = new ArrayList<>();
    static final boolean BOOLEAN = true;
    public static int time;
    Handler handler = new Handler();
    VideoView videoView;
    int anInt = 0;
    TextView textView;
    TextView textView1;
    TextView textView2;
    ImageView imageView;
    SeekBar seekBar;
    int anInt1 = 0;
    boolean aBoolean = false;
    Bitmap bitmap;
    File file;
    String string;
    File file1;
    String string1;
    String string2;
    private UnifiedNativeAd nativeAd;
    OnClickListener onClickListener = new OnClickListener() {
        @Override public void onClick(View view) {
            if (ActivityVideoToImage.this.aBoolean) {
                try {
                    ActivityVideoToImage.this.videoView.pause();
                    ActivityVideoToImage.this.handler.removeCallbacks(ActivityVideoToImage.this.runnable);
                    ActivityVideoToImage.this.imageView.setBackgroundResource(R.drawable.play2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    ActivityVideoToImage.this.videoView.seekTo(ActivityVideoToImage.this.seekBar.getProgress());
                    ActivityVideoToImage.this.videoView.start();
                    ActivityVideoToImage.this.handler.postDelayed(ActivityVideoToImage.this.runnable, 200);
                    ActivityVideoToImage.this.videoView.setVisibility(0);
                    ActivityVideoToImage.this.imageView.setBackgroundResource(R.drawable.pause2);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            ActivityVideoToImage.this.aBoolean ^= ActivityVideoToImage.BOOLEAN;
        }
    };
    Runnable runnable = new Runnable() {
        public void run() {
            if (ActivityVideoToImage.this.videoView.isPlaying()) {
                int currentPosition = ActivityVideoToImage.this.videoView.getCurrentPosition();
                ActivityVideoToImage.this.seekBar.setProgress(currentPosition);
                try {
                    ActivityVideoToImage.this.textView.setText(EditorVideoPlayer.formatTimeUnit(currentPosition));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (currentPosition == ActivityVideoToImage.this.anInt1) {
                    ActivityVideoToImage.this.seekBar.setProgress(0);
                    ActivityVideoToImage.this.textView.setText("00:00");
                    ActivityVideoToImage.this.handler.removeCallbacks(ActivityVideoToImage.this.runnable);
                    return;
                }
                ActivityVideoToImage.this.handler.postDelayed(ActivityVideoToImage.this.runnable, 200);
                return;
            }
            ActivityVideoToImage.this.seekBar.setProgress(ActivityVideoToImage.this.anInt1);
            try {
                ActivityVideoToImage.this.textView.setText(EditorVideoPlayer.formatTimeUnit(ActivityVideoToImage.this.anInt1));
            } catch (ParseException e2) {
                e2.printStackTrace();
            }
            ActivityVideoToImage.this.handler.removeCallbacks(ActivityVideoToImage.this.runnable);
        }
    };
    private FileOutputStream t;

    private class a extends AsyncTask<Void, Void, Void> {
        private ProgressDialog b;

        private a() {
        }


        public void onPreExecute() {
            this.b = ProgressDialog.show(ActivityVideoToImage.this, "Capture Image", "Please wait...", ActivityVideoToImage.BOOLEAN);
            super.onPreExecute();
        }



        public Void doInBackground(Void... voidArr) {
            try {
                Thread.sleep(1000);
                ActivityVideoToImage.this.storeImage();
                Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                intent.setData(Uri.fromFile(new File(ActivityVideoToImage.this.string1)));
                ActivityVideoToImage.this.sendBroadcast(intent);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (NoSuchMethodError e2) {
                e2.printStackTrace();
            } catch (IllegalArgumentException e3) {
                e3.printStackTrace();
            } catch (NullPointerException e4) {
                e4.printStackTrace();
            } catch (Exception e5) {
                e5.printStackTrace();
            }
            return null;
        }



        public void onPostExecute(Void voidR) {
            this.b.dismiss();
            super.onPostExecute(voidR);
        }
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView( R.layout.activity_video_to_image);


        Toolbar toolbar = findViewById(R.id.toolbar);
        refreshAd();
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Video To Image");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (BOOLEAN || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(BOOLEAN);
            supportActionBar.setDisplayShowTitleEnabled(false);
            myList.clear();
            this.videoView = findViewById(R.id.videoView_player);
            this.seekBar = findViewById(R.id.sbVideo);
            this.seekBar.setOnSeekBarChangeListener(this);
            this.textView = findViewById(R.id.left_pointer);
            this.textView1 = findViewById(R.id.right_pointer);
            this.imageView = findViewById(R.id.btnPlayVideo);
            this.textView2 = findViewById(R.id.Filename);
            PATH = getIntent().getStringExtra("videouri");
            this.textView2.setText(new File(PATH).getName());
            this.videoView.setVideoPath(PATH);
            this.videoView.seekTo(100);
            this.videoView.setOnErrorListener(new OnErrorListener() {
                public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                    Toast.makeText(ActivityVideoToImage.this.getApplicationContext(), "Video Player Not Supproting", 0).show();
                    return ActivityVideoToImage.BOOLEAN;
                }
            });
            this.videoView.setOnPreparedListener(new OnPreparedListener() {
                public void onPrepared(MediaPlayer mediaPlayer) {
                    ActivityVideoToImage.this.anInt1 = ActivityVideoToImage.this.videoView.getDuration();
                    ActivityVideoToImage.this.seekBar.setMax(ActivityVideoToImage.this.anInt1);
                    ActivityVideoToImage.this.textView.setText("00:00");
                    try {
                        ActivityVideoToImage.this.textView1.setText(EditorVideoPlayer.formatTimeUnit(ActivityVideoToImage.this.anInt1));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
            this.videoView.setOnCompletionListener(new OnCompletionListener() {
                public void onCompletion(MediaPlayer mediaPlayer) {
                    ActivityVideoToImage.this.videoView.setVisibility(0);
                    ActivityVideoToImage.this.imageView.setBackgroundResource(R.drawable.play2);
                    ActivityVideoToImage.this.videoView.seekTo(0);
                    ActivityVideoToImage.this.seekBar.setProgress(0);
                    ActivityVideoToImage.this.textView.setText("00:00");
                    ActivityVideoToImage.this.handler.removeCallbacks(ActivityVideoToImage.this.runnable);
                    ActivityVideoToImage.this.aBoolean ^= ActivityVideoToImage.BOOLEAN;
                }
            });
            this.imageView.setOnClickListener(this.onClickListener);
            findViewById(R.id.imageView_capture).setOnClickListener(new OnClickListener() {
                @Override public void onClick(View view) {
                    ActivityVideoToImage.time = ActivityVideoToImage.this.videoView.getCurrentPosition() * 1000;
                    ActivityVideoToImage.this.bitmap = ActivityVideoToImage.getVideoFrame(ActivityVideoToImage.PATH);
                    new a().execute();
                }
            });
            return;
        }
        throw new AssertionError();
    }

    public void storeImage() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        stringBuilder.append("/");
        stringBuilder.append(getResources().getString(R.string.MainFolderName));
        stringBuilder.append("/");
        stringBuilder.append(getResources().getString(R.string.VideoToImage));
        this.file = new File(stringBuilder.toString());
        if (!this.file.exists()) {
            this.file.mkdirs();
        }
        Calendar instance = Calendar.getInstance();
        int i2 = instance.get(13);
        int i3 = instance.get(10);
        int i4 = instance.get(12);
        StringBuilder builder = new StringBuilder();
        builder.append(i3);
        builder.append("-");
        builder.append(i4);
        builder.append("-");
        builder.append(i2);
        this.string2 = builder.toString();
        StringBuilder sb3 = new StringBuilder("Image");
        sb3.append(this.string2);
        sb3.append(".jpg");
        this.string = sb3.toString();
        this.file1 = new File(this.file, this.string);
        this.string1 = this.file1.getAbsolutePath();
        try {
            this.t = new FileOutputStream(this.file1);
            this.bitmap.compress(CompressFormat.PNG, 90, this.t);
            Context applicationContext = getApplicationContext();
            StringBuilder sb4 = new StringBuilder("Saved\n");
            sb4.append(this.string1);
            Toast.makeText(applicationContext, sb4.toString(), Toast.LENGTH_LONG).show();
            this.t.flush();
            this.t.close();
        } catch (Exception unused) {
        }
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
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
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



    private void refreshAd() {

        AdLoader.Builder builder = new AdLoader.Builder(this, getResources().getString(R.string.native_id_admob));

 builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {

                if (nativeAd != null) {
                    nativeAd.destroy();
                }
                nativeAd = unifiedNativeAd;
                FrameLayout nativeAdPlaceHolder = ActivityVideoToImage.this.findViewById(R.id.nativeAdPlaceHolder);
                @SuppressLint("InflateParams") UnifiedNativeAdView adView = (UnifiedNativeAdView) ActivityVideoToImage.this.getLayoutInflater()
                        .inflate(R.layout.native_ad_unified_smaller, null);
                ActivityVideoToImage.this.populateUnifiedNativeAdView(unifiedNativeAd, adView);
                nativeAdPlaceHolder.removeAllViews();
                nativeAdPlaceHolder.addView(adView);
                nativeAdPlaceHolder.setVisibility(View.VISIBLE);
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


    public static Bitmap getVideoFrame(String str) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(str);
        return mediaMetadataRetriever.getFrameAtTime(time);
    }

    public void onProgressChanged(SeekBar seekBar, int i2, boolean z) {
        if (z) {
            this.videoView.seekTo(i2);
            try {
                this.textView.setText(formatTimeUnit(i2));
            } catch (ParseException e2) {
                e2.printStackTrace();
            }
        }
    }

    @SuppressLint({"NewApi"})
    public static String formatTimeUnit(long j2) throws ParseException {
        return String.format("%02d:%02d", Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(j2)), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j2) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j2))));
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return BOOLEAN;
    }

   @Override public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == 16908332) {
            onBackPressed();
            return BOOLEAN;
        }
        if (itemId == R.id.shareapp) {
            StringBuilder sb = new StringBuilder();
            sb.append(EditorHelper.share_string);
            sb.append(EditorHelper.package_name);
            String intent1 = sb.toString();
            Intent intent = new Intent();
            intent.setAction("android.intent.action.SEND");
            intent.setType("text/plain");
            intent.putExtra("android.intent.extra.TEXT", intent1);
            startActivity(intent);
        } else if (itemId == R.id.moreapp) {
            try {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(EditorHelper.account_string)));
            } catch (ActivityNotFoundException unused) {
                Toast.makeText(getApplicationContext(), " unable to find market app", Toast.LENGTH_LONG).show();
            }
        } else if (itemId == R.id.rateus) {
            try {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(EditorHelper.package_name)));
            } catch (ActivityNotFoundException unused2) {
                Toast.makeText(getApplicationContext(), " unable to find market app", Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override public void onBackPressed() {
        Intent intent = new Intent(this, ActivityVideoList.class);
        intent.setFlags(67108864);
        startActivity(intent);
        finish();
    }


    public void onPause() {
        this.anInt = this.videoView.getCurrentPosition();
        super.onPause();
    }


    @Override public void onResume() {
        this.videoView.seekTo(this.anInt);
        super.onResume();
    }
}
