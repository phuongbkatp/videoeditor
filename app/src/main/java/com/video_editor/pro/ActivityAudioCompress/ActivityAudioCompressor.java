package com.video_editor.pro.ActivityAudioCompress;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.MediaStore.Images.Media;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.video_editor.pro.UtilsAndAdapters.EditorAudioPlayer;
import com.video_editor.pro.UtilsAndAdapters.EditorHelper;
import com.video_editor.pro.R;
import com.video_editor.pro.AudioTools.EditorCutter.CheapSoundFile;
import com.video_editor.pro.AudioTools.EditorCutter.CheapSoundFile.ProgressListener;
import com.video_editor.pro.AudioTools.EditorCutter.MarkerView;
import com.video_editor.pro.AudioTools.EditorCutter.MarkerView.MarkerListener;
import com.video_editor.pro.AudioTools.EditorCutter.SeekTest;
import com.video_editor.pro.AudioTools.EditorCutter.SongMetadataReader;
import com.video_editor.pro.AudioTools.EditorCutter.WaveformView;
import com.video_editor.pro.AudioTools.EditorCutter.WaveformView.WaveformListener;
import com.video_editor.pro.ActivityMusicList.ActivityMusicList;
import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ActivityAudioCompressor extends AppCompatActivity implements MarkerListener, WaveformListener {
    public boolean aBoolean;
    static final boolean BOOLEAN = true;
    private Uri uri;
    private WaveformView waveformView;
    public MarkerView markerView;
    public MarkerView markerView1;
    private TextView textView;
    private ImageButton imageButton;
    private ImageButton imageButton1;
    private ImageView imageView;
    private ImageView imageView1;
    private ImageView imageView2;
    private boolean aBoolean1;
    private String string = "";
    public int anInt;
    public int anInt1;
    private int anInt2;
    private int anInt3;
    public boolean aBoolean2;
    public int anInt4;
    private int anInt5;
    public int anInt6;
    private int anInt7;
    private int anInt8;
    private int anInt9;
    public int anInt10;
    AudioManager audioManager;

    private OnClickListener onClickListener = new OnClickListener() {
        @Override public void onClick(View view) {
            try {
                AudioManager audioManager = ActivityAudioCompressor.this.audioManager;
                audioManager.adjustStreamVolume(3, 1, 1);
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
        }
    };

    public String string1;
    public int anInt11;
    public Handler handler;
    public MediaPlayer mediaPlayer;
    public boolean aBoolean3;
    private boolean aBoolean4;
    public boolean aBoolean5;
    private float aFloat;
    private int anInt12;
    private int anInt13;
    private int anInt14;
    private long aLong;
    public float aFloat1;
    private int anInt15;
    private int anInt16;
    private int anInt17;
    private int anInt18;
    private Typeface typeface;
    public EditText editText;

    public EditText editText1;
    private InterstitialAd interstitialAd;
    private TextWatcher textWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void afterTextChanged(Editable editable) {
            if (ActivityAudioCompressor.this.editText.hasFocus()) {
                try {
                    ActivityAudioCompressor.this.anInt1 = ActivityAudioCompressor.this.waveformView.secondsToPixels(Double.parseDouble(ActivityAudioCompressor.this.editText.getText().toString()));
                    ActivityAudioCompressor.this.g();
                } catch (NumberFormatException unused) {
                }
            }
            if (ActivityAudioCompressor.this.editText1.hasFocus()) {
                try {
                    ActivityAudioCompressor.this.anInt = ActivityAudioCompressor.this.waveformView.secondsToPixels(Double.parseDouble(ActivityAudioCompressor.this.editText1.getText().toString()));
                    ActivityAudioCompressor.this.g();
                } catch (NumberFormatException unused2) {
                }
            }
        }
    };
    public int audioRate = 64;

    public Runnable runnable = new Runnable() {
        public void run() {
            if (ActivityAudioCompressor.this.anInt1 != ActivityAudioCompressor.this.anInt11 && !ActivityAudioCompressor.this.editText.hasFocus()) {
                ActivityAudioCompressor.this.editText.setText(ActivityAudioCompressor.this.D(ActivityAudioCompressor.this.anInt1));
                if (ActivityAudioCompressor.this.D(ActivityAudioCompressor.this.anInt1) == "") {
                    int parseFloat = (int) Float.parseFloat("00.00");
                    int i = parseFloat / 60;
                    if (i <= 9) {
                        ActivityAudioCompressor activityAudioCompressor = ActivityAudioCompressor.this;
                        StringBuilder sb = new StringBuilder();
                        sb.append("0");
                        sb.append(i);
                        activityAudioCompressor.b = sb.toString();
                    } else {
                        int i2 = i % 60;
                        if (i2 <= 9) {
                            ActivityAudioCompressor activityAudioCompressor2 = ActivityAudioCompressor.this;
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("0");
                            sb2.append(i2);
                            activityAudioCompressor2.d = sb2.toString();
                        } else {
                            int i3 = parseFloat % 60;
                            if (i3 <= 9) {
                                ActivityAudioCompressor activityAudioCompressor3 = ActivityAudioCompressor.this;
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append("0");
                                sb3.append(i3);
                                activityAudioCompressor3.f = sb3.toString();
                            }
                        }
                    }
                } else {
                    int parseFloat2 = (int) Float.parseFloat(ActivityAudioCompressor.this.D(ActivityAudioCompressor.this.anInt1));
                    int i4 = parseFloat2 / 3600;
                    if (i4 <= 9) {
                        ActivityAudioCompressor activityAudioCompressor4 = ActivityAudioCompressor.this;
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append("0");
                        sb4.append(i4);
                        activityAudioCompressor4.b = sb4.toString();
                    } else {
                        ActivityAudioCompressor activityAudioCompressor5 = ActivityAudioCompressor.this;
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append("");
                        sb5.append(i4);
                        activityAudioCompressor5.b = sb5.toString();
                    }
                    int i5 = (parseFloat2 / 60) % 60;
                    if (i5 <= 9) {
                        ActivityAudioCompressor activityAudioCompressor6 = ActivityAudioCompressor.this;
                        StringBuilder sb6 = new StringBuilder();
                        sb6.append("0");
                        sb6.append(i5);
                        activityAudioCompressor6.d = sb6.toString();
                    } else {
                        ActivityAudioCompressor activityAudioCompressor7 = ActivityAudioCompressor.this;
                        StringBuilder sb7 = new StringBuilder();
                        sb7.append("");
                        sb7.append(i5);
                        activityAudioCompressor7.d = sb7.toString();
                    }
                    int i6 = parseFloat2 % 60;
                    if (i6 <= 9) {
                        ActivityAudioCompressor activityAudioCompressor8 = ActivityAudioCompressor.this;
                        StringBuilder sb8 = new StringBuilder();
                        sb8.append("0");
                        sb8.append(i6);
                        activityAudioCompressor8.f = sb8.toString();
                    } else {
                        ActivityAudioCompressor activityAudioCompressor9 = ActivityAudioCompressor.this;
                        StringBuilder sb9 = new StringBuilder();
                        sb9.append("");
                        sb9.append(i6);
                        activityAudioCompressor9.f = sb9.toString();
                    }
                }
                ActivityAudioCompressor.this.anInt11 = ActivityAudioCompressor.this.anInt1;
            }
            if (ActivityAudioCompressor.this.anInt != ActivityAudioCompressor.this.anInt10 && !ActivityAudioCompressor.this.editText1.hasFocus()) {
                ActivityAudioCompressor.this.editText1.setText(ActivityAudioCompressor.this.D(ActivityAudioCompressor.this.anInt));
                if (ActivityAudioCompressor.this.D(ActivityAudioCompressor.this.anInt) != "") {
                    int parseFloat3 = (int) Float.parseFloat(ActivityAudioCompressor.this.D(ActivityAudioCompressor.this.anInt - ActivityAudioCompressor.this.anInt1));
                    int i7 = parseFloat3 / 3600;
                    if (i7 <= 9) {
                        ActivityAudioCompressor activityAudioCompressor10 = ActivityAudioCompressor.this;
                        StringBuilder sb10 = new StringBuilder();
                        sb10.append("0");
                        sb10.append(i7);
                        activityAudioCompressor10.c = sb10.toString();
                    } else {
                        ActivityAudioCompressor activityAudioCompressor11 = ActivityAudioCompressor.this;
                        StringBuilder sb11 = new StringBuilder();
                        sb11.append("");
                        sb11.append(i7);
                        activityAudioCompressor11.c = sb11.toString();
                    }
                    int i8 = (parseFloat3 / 60) % 60;
                    if (i8 <= 9) {
                        ActivityAudioCompressor activityAudioCompressor12 = ActivityAudioCompressor.this;
                        StringBuilder sb12 = new StringBuilder();
                        sb12.append("0");
                        sb12.append(i8);
                        activityAudioCompressor12.e = sb12.toString();
                    } else {
                        ActivityAudioCompressor activityAudioCompressor13 = ActivityAudioCompressor.this;
                        StringBuilder sb13 = new StringBuilder();
                        sb13.append("");
                        sb13.append(i8);
                        activityAudioCompressor13.e = sb13.toString();
                    }
                    int i9 = parseFloat3 % 60;
                    if (i9 <= 9) {
                        ActivityAudioCompressor activityAudioCompressor14 = ActivityAudioCompressor.this;
                        StringBuilder sb14 = new StringBuilder();
                        sb14.append("0");
                        sb14.append(i9);
                        activityAudioCompressor14.g = sb14.toString();
                    } else {
                        ActivityAudioCompressor activityAudioCompressor15 = ActivityAudioCompressor.this;
                        StringBuilder sb15 = new StringBuilder();
                        sb15.append("");
                        sb15.append(i9);
                        activityAudioCompressor15.g = sb15.toString();
                    }
                }
                ActivityAudioCompressor.this.anInt10 = ActivityAudioCompressor.this.anInt;
            }
            ActivityAudioCompressor.this.handler.postDelayed(ActivityAudioCompressor.this.runnable, 100);
        }
    };
    private OnClickListener aw = new OnClickListener() {
        @Override public void onClick(View view) {
            ActivityAudioCompressor.this.e(ActivityAudioCompressor.this.anInt1);
        }
    };
    private OnClickListener ax = new OnClickListener() {
        @Override public void onClick(View view) {
            if (ActivityAudioCompressor.this.aBoolean3) {
                int currentPosition = ActivityAudioCompressor.this.mediaPlayer.getCurrentPosition() - 5000;
                if (currentPosition < ActivityAudioCompressor.this.anInt6) {
                    currentPosition = ActivityAudioCompressor.this.anInt6;
                }
                ActivityAudioCompressor.this.mediaPlayer.seekTo(currentPosition);
                return;
            }
            ActivityAudioCompressor.this.markerView1.requestFocus();
            ActivityAudioCompressor.this.markerFocus(ActivityAudioCompressor.this.markerView1);
        }
    };
    private OnClickListener ay = new OnClickListener() {
        @Override public void onClick(View view) {
            try {
                if (ActivityAudioCompressor.this.aBoolean3) {
                    int currentPosition = BaseImageDownloader.DEFAULT_HTTP_CONNECT_TIMEOUT + ActivityAudioCompressor.this.mediaPlayer.getCurrentPosition();
                    if (currentPosition > ActivityAudioCompressor.this.anInt4) {
                        currentPosition = ActivityAudioCompressor.this.anInt4;
                    }
                    ActivityAudioCompressor.this.mediaPlayer.seekTo(currentPosition);
                    return;
                }
                ActivityAudioCompressor.this.markerView.requestFocus();
                ActivityAudioCompressor.this.markerFocus(ActivityAudioCompressor.this.markerView);
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
        }
    };
    private OnClickListener az = new OnClickListener() {
        @Override public void onClick(View view) {
            try {
                AudioManager audioManager = ActivityAudioCompressor.this.audioManager;
                AudioManager audioManager2 = ActivityAudioCompressor.this.audioManager;
                AudioManager audioManager3 = ActivityAudioCompressor.this.audioManager;
                AudioManager audioManager4 = ActivityAudioCompressor.this.audioManager;
                audioManager.adjustStreamVolume(3, -1, 1);
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
        }
    };
    String b;
    String c;
    String d;
    String e;
    String f;
    private UnifiedNativeAd nativeAd;

    public FFmpeg fFmpeg;
    String g;
    WakeLock wakeLock;
    AdapterAudioCompressor adapterAudioCompressor;
    ArrayList<String> arrayList;
    Spinner spinner;

    public long m;
    private long n;

    private boolean o;

    public ProgressDialog progressDialog;

    public CheapSoundFile cheapSoundFile;

    public File r;
    private String s;
    private String t;
    private String u;
    private String v;
    private String w;
    private String x;
    private String y;


    public void a(CharSequence charSequence, CharSequence charSequence2, Exception exc) {

    }

    public void markerDraw() {

    }

    public void markerEnter(MarkerView markerView) {

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
                FrameLayout nativeAdPlaceHolder = ActivityAudioCompressor.this.findViewById(R.id.nativeAdPlaceHolder);
                @SuppressLint("InflateParams") UnifiedNativeAdView adView = (UnifiedNativeAdView) ActivityAudioCompressor.this.getLayoutInflater()
                        .inflate(R.layout.native_ad_unified_smaller, null);
                ActivityAudioCompressor.this.populateUnifiedNativeAdView(unifiedNativeAd, adView);
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
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.s = null;
        this.uri = null;

        this.mediaPlayer = null;
        this.aBoolean3 = false;
        a();

        getIntent().getBooleanExtra("was_get_content_intent", false);
        this.y = EditorHelper.audiopath;
        this.cheapSoundFile = null;
        this.aBoolean1 = false;
        this.y.equals("record");
        this.handler = new Handler();
        this.handler.postDelayed(this.runnable, 100);
        if (!this.y.equals("record")) {
            e();
        }
    }


    @Override
    public void onDestroy() {
        if (this.mediaPlayer != null && this.mediaPlayer.isPlaying()) {
            this.mediaPlayer.stop();

        }
        this.mediaPlayer = null;
        if (this.s != null) {
            try {
                if (!new File(this.s).delete()) {
                    a(new Exception(), R.string.delete_tmp_error);
                }
                getContentResolver().delete(this.uri, null, null);
            } catch (SecurityException e2) {
                a(e2, R.string.delete_tmp_error);
            }
        }

        if (nativeAd != null) {
            nativeAd.destroy();
        }
        super.onDestroy();
    }


    @Override public void onActivityResult(int i2, int i3, Intent intent) {
        super.onActivityResult(i2, i3, intent);
        e();
    }

    public void onActivityResult(Configuration configuration) {
        final int zoomLevel = this.waveformView.getZoomLevel();
        super.onConfigurationChanged(configuration);
        a();
        this.handler.postDelayed(new Runnable() {
            public void run() {
                ActivityAudioCompressor.this.markerView1.requestFocus();
                ActivityAudioCompressor.this.markerFocus(ActivityAudioCompressor.this.markerView1);
                ActivityAudioCompressor.this.waveformView.setZoomLevel(zoomLevel);
                ActivityAudioCompressor.this.waveformView.recomputeHeights(ActivityAudioCompressor.this.aFloat1);
                ActivityAudioCompressor.this.g();
            }
        }, 500);
    }

    public void waveformDraw() {
        this.anInt3 = this.waveformView.getMeasuredWidth();
        if (this.anInt8 != this.anInt9 && !this.aBoolean1) {
            g();
        } else if (this.aBoolean3) {
            g();
        } else if (this.anInt7 != 0) {
            g();
        }
    }

    public void waveformTouchStart(float f2) {
        this.aBoolean4 = BOOLEAN;
        this.aFloat = f2;
        this.anInt14 = this.anInt9;
        this.anInt7 = 0;
        this.aLong = System.currentTimeMillis();
    }

    public void waveformTouchMove(float f2) {
        this.anInt9 = a((int) (((float) this.anInt14) + (this.aFloat - f2)));
        g();
    }

    public void waveformTouchEnd() {
        this.aBoolean4 = false;
        this.anInt8 = this.anInt9;
        if (System.currentTimeMillis() - this.aLong >= 300) {
            return;
        }
        if (this.aBoolean3) {
            int pixelsToMillisecs = this.waveformView.pixelsToMillisecs((int) (this.aFloat + ((float) this.anInt9)));
            if (pixelsToMillisecs < this.anInt6 || pixelsToMillisecs >= this.anInt4) {
                n();
            } else {
                this.mediaPlayer.seekTo(pixelsToMillisecs - this.anInt5);
            }
        } else {
            e((int) (this.aFloat + ((float) this.anInt9)));
        }
    }

    public void waveformFling(float f2) {
        this.aBoolean4 = false;
        this.anInt8 = this.anInt9;
        this.anInt7 = (int) (-f2);
        g();
    }

    public void markerTouchStart(MarkerView markerView, float f2) {
        this.aBoolean4 = BOOLEAN;
        this.aFloat = f2;
        this.anInt13 = this.anInt1;
        this.anInt12 = this.anInt;
    }

    public void markerTouchMove(MarkerView markerView, float f2) {
        float f3 = f2 - this.aFloat;
        if (markerView == this.markerView1) {
            this.anInt1 = a((int) (((float) this.anInt13) + f3));
            this.anInt = a((int) (((float) this.anInt12) + f3));
        } else {
            this.anInt = a((int) (((float) this.anInt12) + f3));
            if (this.anInt < this.anInt1) {
                this.anInt = this.anInt1;
            }
        }
        g();
    }

    public void markerTouchEnd(MarkerView markerView) {
        this.aBoolean4 = false;
        if (markerView == this.markerView1) {
            j();
        } else {
            l();
        }
    }

    public void markerLeft(MarkerView markerView, int i2) {
        this.aBoolean1 = BOOLEAN;
        if (markerView == this.markerView1) {
            int i3 = this.anInt1;
            this.anInt1 = a(this.anInt1 - i2);
            this.anInt = a(this.anInt - (i3 - this.anInt1));
            j();
        }
        if (markerView == this.markerView) {
            if (this.anInt == this.anInt1) {
                this.anInt1 = a(this.anInt1 - i2);
                this.anInt = this.anInt1;
            } else {
                this.anInt = a(this.anInt - i2);
            }
            l();
        }
        g();
    }

    public void markerRight(MarkerView markerView, int i2) {
        this.aBoolean1 = BOOLEAN;
        if (markerView == this.markerView1) {
            int i3 = this.anInt1;
            this.anInt1 += i2;
            if (this.anInt1 > this.anInt2) {
                this.anInt1 = this.anInt2;
            }
            this.anInt += this.anInt1 - i3;
            if (this.anInt > this.anInt2) {
                this.anInt = this.anInt2;
            }
            j();
        }
        if (markerView == this.markerView) {
            this.anInt += i2;
            if (this.anInt > this.anInt2) {
                this.anInt = this.anInt2;
            }
            l();
        }
        g();
    }

    public void markerKeyUp() {
        this.aBoolean1 = false;
        g();
    }

    public void markerFocus(MarkerView markerView) {
        this.aBoolean1 = false;
        if (markerView == this.markerView1) {
            k();
        } else {
            m();
        }
        this.handler.postDelayed(new Runnable() {
            public void run() {
                ActivityAudioCompressor.this.g();
            }
        }, 100);
    }

    @SuppressLint("InvalidWakeLockTag")
    private void a() {
        setContentView(R.layout.activity_audio_compress);
        Toolbar toolbar = findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Audio Compressor");
        setSupportActionBar(toolbar);

        ActionBar supportActionBar = getSupportActionBar();
        refreshAd();
        if (BOOLEAN || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(BOOLEAN);
            supportActionBar.setDisplayShowTitleEnabled(false);
            this.fFmpeg = FFmpeg.getInstance(this);
            o();
            this.wakeLock = ((PowerManager) getSystemService(POWER_SERVICE)).newWakeLock(6, "VideoMerge");
            if (!this.wakeLock.isHeld()) {
                this.wakeLock.acquire();
            }
            this.arrayList = new ArrayList<>();
            this.arrayList.add("64 K/Bit");
            this.arrayList.add("128 K/Bit");
            this.arrayList.add("256 K/Bit");
            this.spinner = findViewById(R.id.sp_convert);
            this.adapterAudioCompressor = new AdapterAudioCompressor(this, this.arrayList, 0);
            this.spinner.setAdapter(this.adapterAudioCompressor);
            this.spinner.setSelection(0);
            this.spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                public void onNothingSelected(AdapterView<?> adapterView) {
                }

                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                    if (adapterView.getItemAtPosition(i).toString() == "64 K/Bit") {
                        ActivityAudioCompressor.this.audioRate = 64;
                    } else if (adapterView.getItemAtPosition(i).toString() == "128 K/Bit") {
                        ActivityAudioCompressor.this.audioRate = 128;
                    } else if (adapterView.getItemAtPosition(i).toString() == "256 K/Bit") {
                        ActivityAudioCompressor.this.audioRate = 256;
                    }
                }
            });
            getApplicationContext();
            this.audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            this.aFloat1 = displayMetrics.density;
            this.anInt18 = (int) (46.0f * this.aFloat1);
            this.anInt17 = (int) (48.0f * this.aFloat1);
            this.anInt16 = (int) (this.aFloat1 * 10.0f);
            this.anInt15 = (int) (10.0f * this.aFloat1);
            this.textView = findViewById(R.id.songname);
            this.typeface = Typeface.createFromAsset(getAssets(), EditorHelper.FontStyle);
            this.editText = findViewById(R.id.starttext);
            this.editText.setTypeface(this.typeface);
            this.editText.addTextChangedListener(this.textWatcher);
            this.editText1 = findViewById(R.id.endtext);
            this.editText1.setTypeface(this.typeface);
            this.editText1.addTextChangedListener(this.textWatcher);
            this.imageView2 = findViewById(R.id.play);
            this.imageView2.setOnClickListener(this.aw);
            this.imageView1 = findViewById(R.id.rew);
            this.imageView1.setOnClickListener(this.ax);
            this.imageView = findViewById(R.id.ffwd);
            this.imageView.setOnClickListener(this.ay);
            this.imageButton = findViewById(R.id.btnvolumdown);
            this.imageButton.setOnClickListener(this.az);
            this.imageButton1 = findViewById(R.id.btnvolumup);
            this.imageButton1.setOnClickListener(this.onClickListener);
            h();
            this.waveformView = findViewById(R.id.waveform);
            this.waveformView.setListener(this);
            this.anInt2 = 0;
            this.anInt11 = -1;
            this.anInt10 = -1;
            if (this.cheapSoundFile != null) {
                this.waveformView.setSoundFile(this.cheapSoundFile);
                this.waveformView.recomputeHeights(this.aFloat1);
                this.anInt2 = this.waveformView.maxPos();
            }
            this.markerView1 = findViewById(R.id.startmarker);
            this.markerView1.setListener(this);
            this.markerView1.setAlpha(255);
            this.markerView1.setFocusable(BOOLEAN);
            this.markerView1.setFocusableInTouchMode(BOOLEAN);
            this.aBoolean2 = BOOLEAN;
            this.markerView = findViewById(R.id.endmarker);
            this.markerView.setListener(this);
            this.markerView.setAlpha(255);
            this.markerView.setFocusable(BOOLEAN);
            this.markerView.setFocusableInTouchMode(BOOLEAN);
            this.aBoolean = BOOLEAN;
            g();
            this.interstitialAd = new InterstitialAd(this);
            this.interstitialAd.setAdUnitId(getString(R.string.interstitial_id_admob));
            this.interstitialAd.setAdListener(new AdListener() {
                @Override public void onAdClosed() {
                    ActivityAudioCompressor.this.D();
                }
            });
            b();
            return;
        }
        throw new AssertionError();
    }

    private void b() {
        if (!this.interstitialAd.isLoading() && !this.interstitialAd.isLoaded()) {
            this.interstitialAd.loadAd(new Builder().build());
        }
    }


    public void c() {
        if (this.interstitialAd == null || !this.interstitialAd.isLoaded()) {
            D();
        } else {
            this.interstitialAd.show();
        }
    }


    public void D() {
        Intent intent = new Intent(this, EditorAudioPlayer.class);
        Bundle bundle = new Bundle();
        bundle.putString("song", this.string1);
        bundle.putBoolean("isfrom", BOOLEAN);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private void e() {
        this.r = new File(this.y);
        this.t = a(this.y);
        SongMetadataReader songMetadataReader = new SongMetadataReader(this, this.y);
        this.u = songMetadataReader.mTitle;
        this.x = songMetadataReader.mArtist;
        this.w = songMetadataReader.mAlbum;
        int z = songMetadataReader.mYear;
        this.v = songMetadataReader.mGenre;
        String str = this.u;
        if (this.x != null && this.x.length() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(" - ");
            sb.append(this.x);
            str = sb.toString();
        }
        this.textView.setText(str);
        this.textView.setSelected(BOOLEAN);
        this.n = System.currentTimeMillis();
        this.m = System.currentTimeMillis();
        this.o = BOOLEAN;
        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setProgressStyle(1);
        this.progressDialog.setTitle(R.string.progress_dialog_loading);
        this.progressDialog.setCancelable(false);
        this.progressDialog.show();
        final ProgressListener r0 = new ProgressListener() {
            public boolean reportProgress(double d) {
                long currentTimeMillis = System.currentTimeMillis();
                if (currentTimeMillis - ActivityAudioCompressor.this.m > 100) {
                    ActivityAudioCompressor.this.progressDialog.setProgress((int) (((double) ActivityAudioCompressor.this.progressDialog.getMax()) * d));
                    ActivityAudioCompressor.this.m = currentTimeMillis;
                }
                return ActivityAudioCompressor.this.o;
            }
        };
        this.aBoolean5 = false;
        new Thread() {
            @Override
            public void run() {
                ActivityAudioCompressor.this.aBoolean5 = SeekTest.CanSeekAccurately(ActivityAudioCompressor.this.getPreferences(0));
                System.out.println("Seek test done, creating media player.");
                try {
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(ActivityAudioCompressor.this.r.getAbsolutePath());
                    AudioManager audioManager = ActivityAudioCompressor.this.audioManager;
                    mediaPlayer.setAudioStreamType(3);
                    mediaPlayer.prepare();
                    ActivityAudioCompressor.this.mediaPlayer = mediaPlayer;
                } catch (final IOException e) {
                    ActivityAudioCompressor.this.handler.post(new Runnable() {
                        public void run() {
                            ActivityAudioCompressor.this.a("ReadError", ActivityAudioCompressor.this.getResources().getText(R.string.read_error), e);
                        }
                    });
                }
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                final String str;
                try {
                    ActivityAudioCompressor.this.cheapSoundFile = CheapSoundFile.create(ActivityAudioCompressor.this.r.getAbsolutePath(), r0);
                    if (ActivityAudioCompressor.this.cheapSoundFile == null) {
                        ActivityAudioCompressor.this.progressDialog.dismiss();
                        String[] split = ActivityAudioCompressor.this.r.getName().toLowerCase().split("\\.");
                        if (split.length < 2) {
                            str = ActivityAudioCompressor.this.getResources().getString(R.string.no_extension_error);
                        } else {
                            StringBuilder sb = new StringBuilder();
                            sb.append(ActivityAudioCompressor.this.getResources().getString(R.string.bad_extension_error));
                            sb.append(" ");
                            sb.append(split[split.length - 1]);
                            str = sb.toString();
                        }
                        ActivityAudioCompressor.this.handler.post(new Runnable() {
                            public void run() {
                                ActivityAudioCompressor.this.a("UnsupportedExtension", str, new Exception());
                            }
                        });
                        return;
                    }
                    ActivityAudioCompressor.this.progressDialog.dismiss();
                    if (ActivityAudioCompressor.this.o) {
                        ActivityAudioCompressor.this.handler.post(new Runnable() {
                            public void run() {
                                ActivityAudioCompressor.this.f();
                            }
                        });
                    } else {
                        ActivityAudioCompressor.this.finish();
                    }
                } catch (final Exception e) {
                    ActivityAudioCompressor.this.progressDialog.dismiss();
                    e.printStackTrace();
                    ActivityAudioCompressor.this.handler.post(new Runnable() {
                        public void run() {
                            ActivityAudioCompressor.this.a("ReadError", ActivityAudioCompressor.this.getResources().getText(R.string.read_error),
                                    e);
                        }
                    });
                }
            }
        }.start();
    }


    public void f() {
        this.waveformView.setSoundFile(this.cheapSoundFile);
        this.waveformView.recomputeHeights(this.aFloat1);
        this.anInt2 = this.waveformView.maxPos();
        this.anInt11 = -1;
        this.anInt10 = -1;
        this.aBoolean4 = false;
        this.anInt9 = 0;
        this.anInt8 = 0;
        this.anInt7 = 0;
        i();
        if (this.anInt > this.anInt2) {
            this.anInt = this.anInt2;
        }
        g();
    }


    public synchronized void g() {
        if (this.aBoolean3) {
            int currentPosition = this.mediaPlayer.getCurrentPosition() + this.anInt5;
            int millisecsToPixels = this.waveformView.millisecsToPixels(currentPosition);
            this.waveformView.setPlayback(millisecsToPixels);
            c(millisecsToPixels - (this.anInt3 / 2));
            if (currentPosition >= this.anInt4) {
                n();
            }
        }
        int i2 = 0;
        if (!this.aBoolean4) {
            if (this.anInt7 != 0) {
                int i3 = this.anInt7;
                int i4 = this.anInt7 / 30;
                if (this.anInt7 > 80) {
                    this.anInt7 -= 80;
                } else if (this.anInt7 < -80) {
                    this.anInt7 += 80;
                } else {
                    this.anInt7 = 0;
                }
                this.anInt9 += i4;
                if (this.anInt9 + (this.anInt3 / 2) > this.anInt2) {
                    this.anInt9 = this.anInt2 - (this.anInt3 / 2);
                    this.anInt7 = 0;
                }
                if (this.anInt9 < 0) {
                    this.anInt9 = 0;
                    this.anInt7 = 0;
                }
                this.anInt8 = this.anInt9;
            } else {
                int i5 = this.anInt8 - this.anInt9;
                int i6 = i5 > 10 ? i5 / 10 : i5 > 0 ? 1 : i5 < -10 ? i5 / 10 : i5 < 0 ? -1 : 0;
                this.anInt9 += i6;
            }
        }
        this.waveformView.setParameters(this.anInt1, this.anInt, this.anInt9);
        this.waveformView.invalidate();
        MarkerView markerView = this.markerView1;
        StringBuilder sb = new StringBuilder();
        sb.append(getResources().getText(R.string.start_marker));
        sb.append(" ");
        sb.append(D(this.anInt1));
        markerView.setContentDescription(sb.toString());
        MarkerView markerView2 = this.markerView;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(getResources().getText(R.string.end_marker));
        sb2.append(" ");
        sb2.append(D(this.anInt));
        markerView2.setContentDescription(sb2.toString());
        int i7 = (this.anInt1 - this.anInt9) - this.anInt18;
        if (this.markerView1.getWidth() + i7 < 0) {
            if (this.aBoolean2) {
                this.markerView1.setAlpha(0);
                this.aBoolean2 = false;
            }
            i7 = 0;
        } else if (!this.aBoolean2) {
            this.handler.postDelayed(new Runnable() {
                public void run() {
                    ActivityAudioCompressor.this.aBoolean2 = ActivityAudioCompressor.BOOLEAN;
                    ActivityAudioCompressor.this.markerView1.setAlpha(255);
                }
            }, 0);
        }
        int width = ((this.anInt - this.anInt9) - this.markerView.getWidth()) + this.anInt17;
        if (this.markerView.getWidth() + width >= 0) {
            if (!this.aBoolean) {
                this.handler.postDelayed(new Runnable() {
                    public void run() {
                        ActivityAudioCompressor.this.aBoolean = ActivityAudioCompressor.BOOLEAN;
                        ActivityAudioCompressor.this.markerView.setAlpha(255);
                    }
                }, 0);
            }
            i2 = width;
        } else if (this.aBoolean) {
            this.markerView.setAlpha(0);
            this.aBoolean = false;
        }
        this.markerView1.setLayoutParams(new LayoutParams(-2, -2, i7, this.anInt16));
        this.markerView.setLayoutParams(new LayoutParams(-2, -2, i2, (this.waveformView.getMeasuredHeight() - this.markerView.getHeight()) - this.anInt15));
    }

    private void h() {
        if (this.aBoolean3) {
            this.imageView2.setImageResource(R.drawable.ic_playlist_pause);
            this.imageView2.setContentDescription(getResources().getText(R.string.stop));
            return;
        }
        this.imageView2.setImageResource(R.drawable.ic_playlist_play);
        this.imageView2.setContentDescription(getResources().getText(R.string.play));
    }

    private void i() {
        this.anInt1 = this.waveformView.secondsToPixels(0.0d);
        this.anInt = this.waveformView.secondsToPixels(15.0d);
    }

    private int a(int i2) {
        if (i2 < 0) {
            return 0;
        }
        return i2 > this.anInt2 ? this.anInt2 : i2;
    }

    private void j() {
        b(this.anInt1 - (this.anInt3 / 2));
    }

    private void k() {
        c(this.anInt1 - (this.anInt3 / 2));
    }

    private void l() {
        b(this.anInt - (this.anInt3 / 2));
    }

    private void m() {
        c(this.anInt - (this.anInt3 / 2));
    }

    private void b(int i2) {
        c(i2);
        g();
    }

    private void c(int i2) {
        if (!this.aBoolean4) {
            this.anInt8 = i2;
            if (this.anInt8 + (this.anInt3 / 2) > this.anInt2) {
                this.anInt8 = this.anInt2 - (this.anInt3 / 2);
            }
            if (this.anInt8 < 0) {
                this.anInt8 = 0;
            }
        }
    }


    public String D(int i2) {
        return (this.waveformView == null || !this.waveformView.isInitialized()) ? "" : a(this.waveformView.pixelsToSeconds(i2));
    }

    private String a(double d2) {
        int i2 = (int) d2;
        int i3 = (int) ((100.0d * (d2 - ((double) i2))) + 0.5d);
        if (i3 >= 100) {
            i2++;
            i3 -= 100;
            if (i3 < 10) {
                i3 *= 10;
            }
        }
        if (i3 < 10) {
            StringBuilder sb = new StringBuilder();
            sb.append(i2);
            sb.append(".0");
            sb.append(i3);
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(i2);
        sb2.append(".");
        sb2.append(i3);
        return sb2.toString();
    }


    public synchronized void n() {
        if (this.mediaPlayer != null && this.mediaPlayer.isPlaying()) {
            this.mediaPlayer.pause();
        }
        this.waveformView.setPlayback(-1);
        this.aBoolean3 = false;
        h();
    }

    public synchronized void e(int i2) {
        try {
            if (this.aBoolean3) {
                n();
            } else if (this.mediaPlayer != null) {
                this.anInt6 = this.waveformView.pixelsToMillisecs(i2);
                if (i2 < this.anInt1) {
                    this.anInt4 = this.waveformView.pixelsToMillisecs(this.anInt1);
                } else if (i2 > this.anInt) {
                    this.anInt4 = this.waveformView.pixelsToMillisecs(this.anInt2);
                } else {
                    this.anInt4 = this.waveformView.pixelsToMillisecs(this.anInt);
                }
                this.anInt5 = 0;
                int secondsToFrames = this.waveformView.secondsToFrames(((double) this.anInt6) * 0.001d);
                int secondsToFrames2 = this.waveformView.secondsToFrames(((double) this.anInt4) * 0.001d);
                int seekableFrameOffset = this.cheapSoundFile.getSeekableFrameOffset(secondsToFrames);
                int seekableFrameOffset2 = this.cheapSoundFile.getSeekableFrameOffset(secondsToFrames2);
                if (this.aBoolean5 && seekableFrameOffset >= 0 && seekableFrameOffset2 >= 0) {
                    this.mediaPlayer.reset();
                    MediaPlayer mediaPlayer = this.mediaPlayer;
                    AudioManager audioManager = this.audioManager;
                    mediaPlayer.setAudioStreamType(3);
                    this.mediaPlayer.setDataSource(new FileInputStream(this.r.getAbsolutePath()).getFD(), seekableFrameOffset, seekableFrameOffset2 - seekableFrameOffset);
                    this.mediaPlayer.prepare();
                    this.anInt5 = this.anInt6;
                    System.out.println("Exception trying to play file subset");
                    this.mediaPlayer.reset();
                    MediaPlayer mediaPlayer2 = this.mediaPlayer;
                    AudioManager audioManager2 = this.audioManager;
                    mediaPlayer2.setAudioStreamType(3);
                    this.mediaPlayer.setDataSource(this.r.getAbsolutePath());
                    this.mediaPlayer.prepare();
                    this.anInt5 = 0;
                }
                this.mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
                    public synchronized void onCompletion(MediaPlayer mediaPlayer) {
                        ActivityAudioCompressor.this.n();
                    }
                });
                this.aBoolean3 = BOOLEAN;
                if (this.anInt5 == 0) {
                    this.mediaPlayer.seekTo(this.anInt6);
                }
                this.mediaPlayer.start();
                g();
                h();
            }
        } catch (Exception e2) {
            a(e2, R.string.play_error);
        }
    }

    private void a(Exception exc, CharSequence charSequence) {
        CharSequence charSequence2;
        if (exc != null) {
            charSequence2 = getResources().getText(R.string.alert_title_failure);
            setResult(0, new Intent());
        } else {
            charSequence2 = getResources().getText(R.string.alert_title_success);
        }
        new AlertDialog.Builder(this).setTitle(charSequence2).setMessage(charSequence).setPositiveButton(R.string.alert_ok_button, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialogInterface, int i) {
                ActivityAudioCompressor.this.finish();
            }
        }).setCancelable(false).show();
    }

    private void a(Exception exc, int i2) {
        a(exc, getResources().getText(i2));
    }

    private String a(String str) {
        return str.substring(str.lastIndexOf(46));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), ActivityMusicList.class);
        intent.setFlags(67108864);
        startActivity(intent);
        finish();
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picker, menu);
        return BOOLEAN;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
            return BOOLEAN;
        }
        if (menuItem.getItemId() == R.id.Done) {
            StringBuilder sb = new StringBuilder();
            sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
            sb.append("/");
            sb.append(getResources().getString(R.string.MainFolderName));
            sb.append("/");
            sb.append(getResources().getString(R.string.AudioCompressor));
            this.string1 = sb.toString();
            File file = new File(this.string1);
            if (!file.exists()) {
                file.mkdirs();
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(file.getAbsolutePath());
            sb2.append("/COM_");
            sb2.append(System.currentTimeMillis());
            sb2.append(".mp3");
            this.string1 = sb2.toString();
            String[] strArr = new String[0];
            if (this.audioRate == 64) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(this.b);
                sb3.append(":");
                sb3.append(this.d);
                sb3.append(":");
                sb3.append(this.f);
                StringBuilder sb4 = new StringBuilder();
                sb4.append(this.c);
                sb4.append(":");
                sb4.append(this.e);
                sb4.append(":");
                sb4.append(this.g);
                strArr = new String[]{"-y", "-ss", sb3.toString(), "-t", sb4.toString(), "-i", EditorHelper.audiopath, "-b:a", "64k", this.string1};
            } else if (this.audioRate == 128) {
                StringBuilder sb5 = new StringBuilder();
                sb5.append(this.b);
                sb5.append(":");
                sb5.append(this.d);
                sb5.append(":");
                sb5.append(this.f);
                StringBuilder sb6 = new StringBuilder();
                sb6.append(this.c);
                sb6.append(":");
                sb6.append(this.e);
                sb6.append(":");
                sb6.append(this.g);
                strArr = new String[]{"-y", "-ss", sb5.toString(), "-t", sb6.toString(), "-i", EditorHelper.audiopath, "-b:a", "128k", this.string1};
            } else if (this.audioRate == 256) {
                StringBuilder sb7 = new StringBuilder();
                sb7.append(this.b);
                sb7.append(":");
                sb7.append(this.d);
                sb7.append(":");
                sb7.append(this.f);
                StringBuilder sb8 = new StringBuilder();
                sb8.append(this.c);
                sb8.append(":");
                sb8.append(this.e);
                sb8.append(":");
                sb8.append(this.g);
                strArr = new String[]{"-y", "-ss", sb7.toString(), "-t", sb8.toString(), "-i", EditorHelper.audiopath, "-b:a", "256k", this.string1};
            }
            a(strArr, this.string1);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void a(String[] strArr, final String str) {
        try {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.show();
            this.fFmpeg.execute(strArr, new ExecuteBinaryResponseHandler() {
                @Override
                public void onFailure(String str) {
                    Log.d("ffmpegfailure", str);
                    try {
                        if (new File(str).delete()){
                        }
                        ActivityAudioCompressor.this.deleteFromGallery(str);
                        Toast.makeText(ActivityAudioCompressor.this, "Error Creating Video", Toast.LENGTH_LONG).show();
                    } catch (Exception th) {
                        th.printStackTrace();
                    }
                }

                @Override public void onSuccess(String str) {
                    progressDialog.dismiss();
                    Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                    intent.setData(Uri.fromFile(new File(ActivityAudioCompressor.this.string1)));
                    ActivityAudioCompressor.this.sendBroadcast(intent);
                    ActivityAudioCompressor.this.c();
                }

                @Override public void onProgress(String str) {
                    Log.d("ffmpegResponse", str);
                    StringBuilder sb = new StringBuilder();
                    sb.append("progress : ");
                    sb.append(str);
                    progressDialog.setMessage(sb.toString());
                }

                @Override public void onStart() {
                    progressDialog.setMessage("Processing...");
                }

                @Override public void onFinish() {
                    progressDialog.dismiss();
                    ActivityAudioCompressor.this.refreshGallery(str);
                }
            });
            getWindow().clearFlags(16);
        } catch (FFmpegCommandAlreadyRunningException unused) {
        }
    }

    private void o() {
        try {
            this.fFmpeg.loadBinary(new LoadBinaryResponseHandler() {
                @Override
                public void onFailure() {
                    ActivityAudioCompressor.this.pload();
                    Log.d("ffmpeg loading failed! ", "");
                }

                @Override public void onFinish() {
                    Log.d("ffmpeg loading finish! ", "");
                }

                @Override public void onStart() {
                    Log.d("ffmpeg loading started!", "");
                }

                @Override public void onSuccess() {
                    Log.d("ffmpeg loading success!", "");
                }
            });
        } catch (FFmpegNotSupportedException unused) {
            pload();
        }
    }


    public void pload() {
        new AlertDialog.Builder(this).setIcon(17301543).setTitle("Device not supported").setMessage("FFmpeg is not supported on your device").setCancelable(false).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialogInterface, int i) {
                ActivityAudioCompressor.this.finish();
            }
        }).create().show();
    }

    public void deleteFromGallery(String str) {
        String[] strArr = {"_id"};
        String[] strArr2 = {str};
        Uri contentUri = Media.EXTERNAL_CONTENT_URI;
        ContentResolver contentResolver = getContentResolver();
        Cursor query = contentResolver.query(contentUri, strArr, "_data = ?", strArr2, null);
        if (query.moveToFirst()) {
            try {
                contentResolver.delete(ContentUris.withAppendedId(contentUri, query.getLong(query.getColumnIndexOrThrow("_id"))), null, null);
            } catch (IllegalArgumentException e2) {
                e2.printStackTrace();
            }
        } else {
            try {
                new File(str).delete();
                refreshGallery(str);
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        query.close();
    }

    public void refreshGallery(String str) {
        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        intent.setData(Uri.fromFile(new File(str)));
        sendBroadcast(intent);
    }
}
