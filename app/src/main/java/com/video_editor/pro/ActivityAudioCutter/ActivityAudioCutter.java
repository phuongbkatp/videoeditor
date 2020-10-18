package com.video_editor.pro.ActivityAudioCutter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.provider.MediaStore.Audio.Media;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.video_editor.pro.UtilsAndAdapters.EditorAudioPlayer;
import com.video_editor.pro.UtilsAndAdapters.EditorCustomEditText;
import com.video_editor.pro.UtilsAndAdapters.EditorCustomTextView;
import com.video_editor.pro.UtilsAndAdapters.EditorHelper;
import com.video_editor.pro.R;
import com.video_editor.pro.ActivityAudioCutter.EditorCutter.CheapSoundFile;
import com.video_editor.pro.ActivityAudioCutter.EditorCutter.CheapSoundFile.ProgressListener;
import com.video_editor.pro.ActivityAudioCutter.EditorCutter.MarkerView;
import com.video_editor.pro.ActivityAudioCutter.EditorCutter.MarkerView.MarkerListener;
import com.video_editor.pro.ActivityAudioCutter.EditorCutter.SeekTest;
import com.video_editor.pro.ActivityAudioCutter.EditorCutter.SongMetadataReader;
import com.video_editor.pro.ActivityAudioCutter.EditorCutter.WaveformView;
import com.video_editor.pro.ActivityAudioCutter.EditorCutter.WaveformView.WaveformListener;
import com.video_editor.pro.ActivityMusicList.ActivityMusicList;
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
import java.io.RandomAccessFile;

public class ActivityAudioCutter extends AppCompatActivity implements MarkerListener, WaveformListener {
    private int anInt;
    static final boolean BOOLEAN = true;
    public static String string;
    private int anInt1;
    private Uri uri;
    private boolean aBoolean;
    public WaveformView waveformView;
    public MarkerView markerView;
    public MarkerView markerView1;
    private TextView textView;
    private ImageButton imageButton;
    private ImageButton imageButton1;
    private ImageView imageView;
    private ImageView imageView1;
    private ImageView imageView2;
    private boolean aBoolean1;
    private String string1 = "";
    public int anInt2;
    public int anInt3;
    private int anInt4;
    public boolean aBoolean2;
    public boolean aBoolean3;
    public int anInt5;
    private int anInt6;
    public int anInt7;
    private int anInt8;
    private int anInt9;
    private int anInt10;
    AudioManager audioManager;

    private OnClickListener onClickListener = new OnClickListener() {
        @Override public void onClick(View view) {
            try {
                AudioManager audioManager = ActivityAudioCutter.this.audioManager;
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
    private OnClickListener onClickListener1 = new OnClickListener() {
        @Override public void onClick(View view) {
            try {
                AudioManager audioManager = ActivityAudioCutter.this.audioManager;
                AudioManager audioManager2 = ActivityAudioCutter.this.audioManager;
                AudioManager audioManager3 = ActivityAudioCutter.this.audioManager;
                AudioManager audioManager4 = ActivityAudioCutter.this.audioManager;
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

    public int anInt11;

    public int anInt12;

    public Handler handler;

    public MediaPlayer mediaPlayer;

    public boolean aBoolean4;
    private boolean aBoolean5;

    public boolean aBoolean6;
    private float aFloat;
    private int anInt13;
    private int anInt14;
    private int anInt15;
    private long aLong;

    public float aFloat1;
    private int anInt16;
    private int anInt17;
    private int anInt18;
    private int anInt19;
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
            if (ActivityAudioCutter.this.editText.hasFocus()) {
                try {
                    ActivityAudioCutter.this.anInt3 = ActivityAudioCutter.this.waveformView.secondsToPixels(Double.parseDouble(ActivityAudioCutter.this.editText.getText().toString()));
                    ActivityAudioCutter.this.g();
                } catch (NumberFormatException unused) {
                }
            }
            if (ActivityAudioCutter.this.editText1.hasFocus()) {
                try {
                    ActivityAudioCutter.this.anInt2 = ActivityAudioCutter.this.waveformView.secondsToPixels(Double.parseDouble(ActivityAudioCutter.this.editText1.getText().toString()));
                    ActivityAudioCutter.this.g();
                } catch (NumberFormatException unused2) {
                }
            }
        }
    };

    public Runnable runnable = new Runnable() {
        public void run() {
            if (ActivityAudioCutter.this.anInt3 != ActivityAudioCutter.this.anInt12 && !ActivityAudioCutter.this.editText.hasFocus()) {
                ActivityAudioCutter.this.editText.setText(ActivityAudioCutter.this.d(ActivityAudioCutter.this.anInt3));
                if (ActivityAudioCutter.this.d(ActivityAudioCutter.this.anInt3) == "") {
                    int parseFloat = (int) Float.parseFloat("00.00");
                    int i = parseFloat / 60;
                    if (i <= 9) {
                        ActivityAudioCutter mP3CutterActivity = ActivityAudioCutter.this;
                        StringBuilder sb = new StringBuilder();
                        sb.append("0");
                        sb.append(i);
                        mP3CutterActivity.string4 = sb.toString();
                    } else {
                        int i2 = i % 60;
                        if (i2 <= 9) {
                            ActivityAudioCutter mP3CutterActivity2 = ActivityAudioCutter.this;
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("0");
                            sb2.append(i2);
                            mP3CutterActivity2.string6 = sb2.toString();
                        } else {
                            int i3 = parseFloat % 60;
                            if (i3 <= 9) {
                                ActivityAudioCutter mP3CutterActivity3 = ActivityAudioCutter.this;
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append("0");
                                sb3.append(i3);
                                mP3CutterActivity3.string8 = sb3.toString();
                            }
                        }
                    }
                } else {
                    int parseFloat2 = (int) Float.parseFloat(ActivityAudioCutter.this.d(ActivityAudioCutter.this.anInt3));
                    int i4 = parseFloat2 / 3600;
                    if (i4 <= 9) {
                        ActivityAudioCutter mP3CutterActivity4 = ActivityAudioCutter.this;
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append("0");
                        sb4.append(i4);
                        mP3CutterActivity4.string4 = sb4.toString();
                    } else {
                        ActivityAudioCutter mP3CutterActivity5 = ActivityAudioCutter.this;
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append("");
                        sb5.append(i4);
                        mP3CutterActivity5.string4 = sb5.toString();
                    }
                    int i5 = (parseFloat2 / 60) % 60;
                    if (i5 <= 9) {
                        ActivityAudioCutter mP3CutterActivity6 = ActivityAudioCutter.this;
                        StringBuilder sb6 = new StringBuilder();
                        sb6.append("0");
                        sb6.append(i5);
                        mP3CutterActivity6.string6 = sb6.toString();
                    } else {
                        ActivityAudioCutter mP3CutterActivity7 = ActivityAudioCutter.this;
                        StringBuilder sb7 = new StringBuilder();
                        sb7.append("");
                        sb7.append(i5);
                        mP3CutterActivity7.string6 = sb7.toString();
                    }
                    int i6 = parseFloat2 % 60;
                    if (i6 <= 9) {
                        ActivityAudioCutter mP3CutterActivity8 = ActivityAudioCutter.this;
                        StringBuilder sb8 = new StringBuilder();
                        sb8.append("0");
                        sb8.append(i6);
                        mP3CutterActivity8.string8 = sb8.toString();
                    } else {
                        ActivityAudioCutter mP3CutterActivity9 = ActivityAudioCutter.this;
                        StringBuilder sb9 = new StringBuilder();
                        sb9.append("");
                        sb9.append(i6);
                        mP3CutterActivity9.string8 = sb9.toString();
                    }
                }
                ActivityAudioCutter.this.anInt12 = ActivityAudioCutter.this.anInt3;
            }
            if (ActivityAudioCutter.this.anInt2 != ActivityAudioCutter.this.anInt11 && !ActivityAudioCutter.this.editText1.hasFocus()) {
                ActivityAudioCutter.this.editText1.setText(ActivityAudioCutter.this.d(ActivityAudioCutter.this.anInt2));
                if (ActivityAudioCutter.this.d(ActivityAudioCutter.this.anInt2) != "") {
                    int parseFloat3 = (int) Float.parseFloat(ActivityAudioCutter.this.d(ActivityAudioCutter.this.anInt2 - ActivityAudioCutter.this.anInt3));
                    int i7 = parseFloat3 / 3600;
                    if (i7 <= 9) {
                        ActivityAudioCutter mP3CutterActivity10 = ActivityAudioCutter.this;
                        StringBuilder sb10 = new StringBuilder();
                        sb10.append("0");
                        sb10.append(i7);
                        mP3CutterActivity10.string5 = sb10.toString();
                    } else {
                        ActivityAudioCutter mP3CutterActivity11 = ActivityAudioCutter.this;
                        StringBuilder sb11 = new StringBuilder();
                        sb11.append("");
                        sb11.append(i7);
                        mP3CutterActivity11.string5 = sb11.toString();
                    }
                    int i8 = (parseFloat3 / 60) % 60;
                    if (i8 <= 9) {
                        ActivityAudioCutter mP3CutterActivity12 = ActivityAudioCutter.this;
                        StringBuilder sb12 = new StringBuilder();
                        sb12.append("0");
                        sb12.append(i8);
                        mP3CutterActivity12.string7 = sb12.toString();
                    } else {
                        ActivityAudioCutter mP3CutterActivity13 = ActivityAudioCutter.this;
                        StringBuilder sb13 = new StringBuilder();
                        sb13.append("");
                        sb13.append(i8);
                        mP3CutterActivity13.string7 = sb13.toString();
                    }
                    int i9 = parseFloat3 % 60;
                    if (i9 <= 9) {
                        ActivityAudioCutter mP3CutterActivity14 = ActivityAudioCutter.this;
                        StringBuilder sb14 = new StringBuilder();
                        sb14.append("0");
                        sb14.append(i9);
                        mP3CutterActivity14.string9 = sb14.toString();
                    } else {
                        ActivityAudioCutter mP3CutterActivity15 = ActivityAudioCutter.this;
                        StringBuilder sb15 = new StringBuilder();
                        sb15.append("");
                        sb15.append(i9);
                        mP3CutterActivity15.string9 = sb15.toString();
                    }
                }
                ActivityAudioCutter.this.anInt11 = ActivityAudioCutter.this.anInt2;
            }
            ActivityAudioCutter.this.handler.postDelayed(ActivityAudioCutter.this.runnable, 100);
        }
    };
    private OnClickListener clickListener = new OnClickListener() {
        @Override public void onClick(View view) {
            ActivityAudioCutter.this.e(ActivityAudioCutter.this.anInt3);
        }
    };
    private OnClickListener clickListener1 = new OnClickListener() {
        @Override public void onClick(View view) {
            if (ActivityAudioCutter.this.aBoolean4) {
                int currentPosition = ActivityAudioCutter.this.mediaPlayer.getCurrentPosition() - 5000;
                if (currentPosition < ActivityAudioCutter.this.anInt7) {
                    currentPosition = ActivityAudioCutter.this.anInt7;
                }
                ActivityAudioCutter.this.mediaPlayer.seekTo(currentPosition);
                return;
            }
            ActivityAudioCutter.this.markerView1.requestFocus();
            ActivityAudioCutter.this.markerFocus(ActivityAudioCutter.this.markerView1);
        }
    };
    private OnClickListener clickListener2 = new OnClickListener() {
        @Override public void onClick(View view) {
            try {
                if (ActivityAudioCutter.this.aBoolean4) {
                    int currentPosition = BaseImageDownloader.DEFAULT_HTTP_CONNECT_TIMEOUT + ActivityAudioCutter.this.mediaPlayer.getCurrentPosition();
                    if (currentPosition > ActivityAudioCutter.this.anInt5) {
                        currentPosition = ActivityAudioCutter.this.anInt5;
                    }
                    ActivityAudioCutter.this.mediaPlayer.seekTo(currentPosition);
                    return;
                }
                ActivityAudioCutter.this.markerView.requestFocus();
                ActivityAudioCutter.this.markerFocus(ActivityAudioCutter.this.markerView);
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
    String string2;
    String string3;
    long aLong1;
    Dialog dialog;
    String string4;
    String string5;
    String string6;
    String string7;
    String string8;
    private UnifiedNativeAd nativeAd;
    String string9;
    public long aLong2;
    private long aLong3;
    public boolean aBoolean7;
    public ProgressDialog progressDialog;
    public CheapSoundFile cheapSoundFile;
    public File file;
    private String string10;
    private String string11;
    private String string12;
    private String string13;
    private String string14;
    private String string15;
    private String string16;
    public String string17;


    public void a(CharSequence charSequence, CharSequence charSequence2, Exception exc) {
    }

    public void markerDraw() {
    }

    public void markerEnter(MarkerView markerView) {
    }


    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.string10 = null;

        this.uri = null;
        this.mediaPlayer = null;
        refreshAd();
        this.aBoolean4 = false;
        a();
        this.aBoolean = getIntent().getBooleanExtra("was_get_content_intent", false);
        this.string17 = EditorHelper.audiopath;
        this.cheapSoundFile = null;
        this.aBoolean1 = false;
        this.string17.equals("record");
        this.handler = new Handler();
        this.handler.postDelayed(this.runnable, 100);
        if (!this.string17.equals("record")) {
            e();
        }
    }


    @Override public void onDestroy() {
        if (this.mediaPlayer != null && this.mediaPlayer.isPlaying()) {
            this.mediaPlayer.stop();
        }
        this.mediaPlayer = null;
        if (this.string10 != null) {
            try {
                if (!new File(this.string10).delete()) {
                    a(new Exception(), R.string.delete_tmp_error);
                }
                getContentResolver().delete(this.uri, null, null);
            } catch (SecurityException e2) {
                a(e2, R.string.delete_tmp_error);
            }
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
                ActivityAudioCutter.this.markerView1.requestFocus();
                ActivityAudioCutter.this.markerFocus(ActivityAudioCutter.this.markerView1);
                ActivityAudioCutter.this.waveformView.setZoomLevel(zoomLevel);
                ActivityAudioCutter.this.waveformView.recomputeHeights(ActivityAudioCutter.this.aFloat1);
                ActivityAudioCutter.this.g();
            }
        }, 500);
    }

    public void waveformDraw() {
        this.anInt = this.waveformView.getMeasuredWidth();
        if (this.anInt9 != this.anInt10 && !this.aBoolean1) {
            g();
        } else if (this.aBoolean4) {
            g();
        } else if (this.anInt8 != 0) {
            g();
        }
    }

    public void waveformTouchStart(float f2) {
        this.aBoolean5 = BOOLEAN;
        this.aFloat = f2;
        this.anInt15 = this.anInt10;
        this.anInt8 = 0;
        this.aLong = System.currentTimeMillis();
    }

    public void waveformTouchMove(float f2) {
        this.anInt10 = a((int) (((float) this.anInt15) + (this.aFloat - f2)));
        g();
    }

    public void waveformTouchEnd() {
        this.aBoolean5 = false;
        this.anInt9 = this.anInt10;
        if (System.currentTimeMillis() - this.aLong >= 300) {
            return;
        }
        if (this.aBoolean4) {
            int pixelsToMillisecs = this.waveformView.pixelsToMillisecs((int) (this.aFloat + ((float) this.anInt10)));
            if (pixelsToMillisecs < this.anInt7 || pixelsToMillisecs >= this.anInt5) {
                n();
            } else {
                this.mediaPlayer.seekTo(pixelsToMillisecs - this.anInt6);
            }
        } else {
            e((int) (this.aFloat + ((float) this.anInt10)));
        }
    }

    public void waveformFling(float f2) {
        this.aBoolean5 = false;
        this.anInt9 = this.anInt10;
        this.anInt8 = (int) (-f2);
        g();
    }

    public void markerTouchStart(MarkerView markerView, float f2) {
        this.aBoolean5 = BOOLEAN;
        this.aFloat = f2;
        this.anInt14 = this.anInt3;
        this.anInt13 = this.anInt2;
    }

    public void markerTouchMove(MarkerView markerView, float f2) {
        float f3 = f2 - this.aFloat;
        if (markerView == this.markerView1) {
            this.anInt3 = a((int) (((float) this.anInt14) + f3));
            this.anInt2 = a((int) (((float) this.anInt13) + f3));
        } else {
            this.anInt2 = a((int) (((float) this.anInt13) + f3));
            if (this.anInt2 < this.anInt3) {
                this.anInt2 = this.anInt3;
            }
        }
        g();
    }

    public void markerTouchEnd(MarkerView markerView) {
        this.aBoolean5 = false;
        if (markerView == this.markerView1) {
            j();
        } else {
            l();
        }
    }

    public void markerLeft(MarkerView markerView, int i2) {
        this.aBoolean1 = BOOLEAN;
        if (markerView == this.markerView1) {
            int i3 = this.anInt3;
            this.anInt3 = a(this.anInt3 - i2);
            this.anInt2 = a(this.anInt2 - (i3 - this.anInt3));
            j();
        }
        if (markerView == this.markerView) {
            if (this.anInt2 == this.anInt3) {
                this.anInt3 = a(this.anInt3 - i2);
                this.anInt2 = this.anInt3;
            } else {
                this.anInt2 = a(this.anInt2 - i2);
            }
            l();
        }
        g();
    }

    public void markerRight(MarkerView markerView, int i2) {
        this.aBoolean1 = BOOLEAN;
        if (markerView == this.markerView1) {
            int i3 = this.anInt3;
            this.anInt3 += i2;
            if (this.anInt3 > this.anInt4) {
                this.anInt3 = this.anInt4;
            }
            this.anInt2 += this.anInt3 - i3;
            if (this.anInt2 > this.anInt4) {
                this.anInt2 = this.anInt4;
            }
            j();
        }
        if (markerView == this.markerView) {
            this.anInt2 += i2;
            if (this.anInt2 > this.anInt4) {
                this.anInt2 = this.anInt4;
            }
            l();
        }
        g();
    }

    public void markerKeyUp() {
        this.aBoolean1 = false;
        g();
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

                FrameLayout nativeAdPlaceHolder = ActivityAudioCutter.this.findViewById(R.id.nativeAdPlaceHolder);
                @SuppressLint("InflateParams") UnifiedNativeAdView adView = (UnifiedNativeAdView) ActivityAudioCutter.this.getLayoutInflater()
                        .inflate(R.layout.native_ad_unified_smaller, null);
                ActivityAudioCutter.this.populateUnifiedNativeAdView(unifiedNativeAd, adView);
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
















    public void markerFocus(MarkerView markerView) {
        this.aBoolean1 = false;
        if (markerView == this.markerView1) {
            k();
        } else {
            m();
        }
        this.handler.postDelayed(new Runnable() {
            public void run() {
                ActivityAudioCutter.this.g();
            }
        }, 100);
    }

    private void a() {
        setContentView( R.layout.activity_audio_cutter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("MP3 Cutter");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (BOOLEAN || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(BOOLEAN);
            supportActionBar.setDisplayShowTitleEnabled(false);
            getApplicationContext();
            this.audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            this.aFloat1 = displayMetrics.density;
            this.anInt19 = (int) (46.0f * this.aFloat1);
            this.anInt18 = (int) (48.0f * this.aFloat1);
            this.anInt17 = (int) (this.aFloat1 * 10.0f);
            this.anInt16 = (int) (10.0f * this.aFloat1);
            this.textView = findViewById(R.id.songname);
            this.typeface = Typeface.createFromAsset(getAssets(), EditorHelper.FontStyle);
            this.editText = findViewById(R.id.starttext);
            this.editText.setTypeface(this.typeface);
            this.editText.addTextChangedListener(this.textWatcher);
            this.editText1 = findViewById(R.id.endtext);
            this.editText1.setTypeface(this.typeface);
            this.editText1.addTextChangedListener(this.textWatcher);
            this.imageView2 = findViewById(R.id.play);
            this.imageView2.setOnClickListener(this.clickListener);
            this.imageView1 = findViewById(R.id.rew);
            this.imageView1.setOnClickListener(this.clickListener1);
            this.imageView = findViewById(R.id.ffwd);
            this.imageView.setOnClickListener(this.clickListener2);
            this.imageButton = findViewById(R.id.btnvolumdown);
            this.imageButton.setOnClickListener(this.onClickListener);
            this.imageButton1 = findViewById(R.id.btnvolumup);
            this.imageButton1.setOnClickListener(this.onClickListener1);
            h();
            this.waveformView = findViewById(R.id.waveform);
            this.waveformView.setListener(this);
            this.anInt4 = 0;
            this.anInt12 = -1;
            this.anInt11 = -1;
            if (this.cheapSoundFile != null) {
                this.waveformView.setSoundFile(this.cheapSoundFile);
                this.waveformView.recomputeHeights(this.aFloat1);
                this.anInt4 = this.waveformView.maxPos();
            }
            this.markerView1 = findViewById(R.id.startmarker);
            this.markerView1.setListener(this);
            this.markerView1.setAlpha(255);
            this.markerView1.setFocusable(BOOLEAN);
            this.markerView1.setFocusableInTouchMode(BOOLEAN);
            this.aBoolean3 = BOOLEAN;
            this.markerView = findViewById(R.id.endmarker);
            this.markerView.setListener(this);
            this.markerView.setAlpha(255);
            this.markerView.setFocusable(BOOLEAN);
            this.markerView.setFocusableInTouchMode(BOOLEAN);
            this.aBoolean2 = BOOLEAN;
            g();
            this.interstitialAd = new InterstitialAd(this);
            this.interstitialAd.setAdUnitId(getString(R.string.interstitial_id_admob));
            this.interstitialAd.setAdListener(new AdListener() {
                @Override public void onAdClosed() {
                    ActivityAudioCutter.this.d();
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

    private void c() {
        if (this.interstitialAd == null || !this.interstitialAd.isLoaded()) {
            d();
        } else {
            this.interstitialAd.show();
        }
    }


    public void d() {
        Intent intent = new Intent(this, EditorAudioPlayer.class);
        Bundle bundle = new Bundle();
        bundle.putString("song", this.string2);
        bundle.putBoolean("isfrom", BOOLEAN);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private void e() {
        this.file = new File(this.string17);
        this.string11 = a(this.string17);
        SongMetadataReader songMetadataReader = new SongMetadataReader(this, this.string17);
        this.string12 = songMetadataReader.mTitle;
        this.string15 = songMetadataReader.mArtist;
        this.string14 = songMetadataReader.mAlbum;
        this.anInt1 = songMetadataReader.mYear;
        this.string13 = songMetadataReader.mGenre;
        String str = this.string12;
        if (this.string15 != null && this.string15.length() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(" - ");
            sb.append(this.string15);
            str = sb.toString();
        }
        this.textView.setText(str);
        this.textView.setSelected(BOOLEAN);
        this.aLong3 = System.currentTimeMillis();
        this.aLong2 = System.currentTimeMillis();
        this.aBoolean7 = BOOLEAN;
        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setProgressStyle(1);
        this.progressDialog.setTitle(R.string.progress_dialog_loading);
        this.progressDialog.setCancelable(false);
        this.progressDialog.show();
        final ProgressListener r0 = new ProgressListener() {
            public boolean reportProgress(double d) {
                long currentTimeMillis = System.currentTimeMillis();
                if (currentTimeMillis - ActivityAudioCutter.this.aLong2 > 100) {
                    ActivityAudioCutter.this.progressDialog.setProgress((int) (((double) ActivityAudioCutter.this.progressDialog.getMax()) * d));
                    ActivityAudioCutter.this.aLong2 = currentTimeMillis;
                }
                return ActivityAudioCutter.this.aBoolean7;
            }
        };
        this.aBoolean6 = false;
        new Thread() {
            public void run() {
                ActivityAudioCutter.this.aBoolean6 = SeekTest.CanSeekAccurately(ActivityAudioCutter.this.getPreferences(0));
                System.out.println("Seek test done, creating media player.");
                try {
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(ActivityAudioCutter.this.file.getAbsolutePath());
                    AudioManager audioManager = ActivityAudioCutter.this.audioManager;
                    mediaPlayer.setAudioStreamType(3);
                    mediaPlayer.prepare();
                    ActivityAudioCutter.this.mediaPlayer = mediaPlayer;
                } catch (final IOException e) {
                    ActivityAudioCutter.this.handler.post(new Runnable() {
                        public void run() {
                            ActivityAudioCutter.this.a("ReadError", ActivityAudioCutter.this.getResources().getText(R.string.read_error), e);
                        }
                    });
                }
            }
        }.start();
        new Thread() {
            public void run() {
                final String str;
                try {
                    ActivityAudioCutter.this.cheapSoundFile = CheapSoundFile.create(ActivityAudioCutter.this.file.getAbsolutePath(), r0);
                    if (ActivityAudioCutter.this.cheapSoundFile == null) {
                        ActivityAudioCutter.this.progressDialog.dismiss();
                        String[] split = ActivityAudioCutter.this.file.getName().toLowerCase().split("\\.");
                        if (split.length < 2) {
                            str = ActivityAudioCutter.this.getResources().getString(R.string.no_extension_error);
                        } else {
                            StringBuilder sb = new StringBuilder();
                            sb.append(ActivityAudioCutter.this.getResources().getString(R.string.bad_extension_error));
                            sb.append(" ");
                            sb.append(split[split.length - 1]);
                            str = sb.toString();
                        }
                        ActivityAudioCutter.this.handler.post(new Runnable() {
                            public void run() {
                                ActivityAudioCutter.this.a("UnsupportedExtension", str, new Exception());
                            }
                        });
                        return;
                    }
                    ActivityAudioCutter.this.progressDialog.dismiss();
                    if (ActivityAudioCutter.this.aBoolean7) {
                        ActivityAudioCutter.this.handler.post(new Runnable() {
                            public void run() {
                                ActivityAudioCutter.this.f();
                            }
                        });
                    } else {
                        ActivityAudioCutter.this.finish();
                    }
                } catch (final Exception e) {
                    ActivityAudioCutter.this.progressDialog.dismiss();
                    e.printStackTrace();
                    ActivityAudioCutter.this.handler.post(new Runnable() {
                        public void run() {
                            ActivityAudioCutter.this.a("ReadError", ActivityAudioCutter.this.getResources().getText(R.string.read_error), e);
                        }
                    });
                }
            }
        }.start();
    }


    public void f() {
        this.waveformView.setSoundFile(this.cheapSoundFile);
        this.waveformView.recomputeHeights(this.aFloat1);
        this.anInt4 = this.waveformView.maxPos();
        this.anInt12 = -1;
        this.anInt11 = -1;
        this.aBoolean5 = false;
        this.anInt10 = 0;
        this.anInt9 = 0;
        this.anInt8 = 0;
        i();
        if (this.anInt2 > this.anInt4) {
            this.anInt2 = this.anInt4;
        }
        g();
    }


    public synchronized void g() {
        if (this.aBoolean4) {
            int currentPosition = this.mediaPlayer.getCurrentPosition() + this.anInt6;
            int millisecsToPixels = this.waveformView.millisecsToPixels(currentPosition);
            this.waveformView.setPlayback(millisecsToPixels);
            c(millisecsToPixels - (this.anInt / 2));
            if (currentPosition >= this.anInt5) {
                n();
            }
        }
        int i2 = 0;
        if (!this.aBoolean5) {
            if (this.anInt8 != 0) {
                int i3 = this.anInt8;
                int i4 = this.anInt8 / 30;
                if (this.anInt8 > 80) {
                    this.anInt8 -= 80;
                } else if (this.anInt8 < -80) {
                    this.anInt8 += 80;
                } else {
                    this.anInt8 = 0;
                }
                this.anInt10 += i4;
                if (this.anInt10 + (this.anInt / 2) > this.anInt4) {
                    this.anInt10 = this.anInt4 - (this.anInt / 2);
                    this.anInt8 = 0;
                }
                if (this.anInt10 < 0) {
                    this.anInt10 = 0;
                    this.anInt8 = 0;
                }
                this.anInt9 = this.anInt10;
            } else {
                int i5 = this.anInt9 - this.anInt10;
                int i6 = i5 > 10 ? i5 / 10 : i5 > 0 ? 1 : i5 < -10 ? i5 / 10 : i5 < 0 ? -1 : 0;
                this.anInt10 += i6;
            }
        }
        this.waveformView.setParameters(this.anInt3, this.anInt2, this.anInt10);
        this.waveformView.invalidate();
        MarkerView markerView = this.markerView1;
        StringBuilder sb = new StringBuilder();
        sb.append(getResources().getText(R.string.start_marker));
        sb.append(" ");
        sb.append(d(this.anInt3));
        markerView.setContentDescription(sb.toString());
        MarkerView markerView2 = this.markerView;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(getResources().getText(R.string.end_marker));
        sb2.append(" ");
        sb2.append(d(this.anInt2));
        markerView2.setContentDescription(sb2.toString());
        int i7 = (this.anInt3 - this.anInt10) - this.anInt19;
        if (this.markerView1.getWidth() + i7 < 0) {
            if (this.aBoolean3) {
                this.markerView1.setAlpha(0);
                this.aBoolean3 = false;
            }
            i7 = 0;
        } else if (!this.aBoolean3) {
            this.handler.postDelayed(new Runnable() {
                public void run() {
                    ActivityAudioCutter.this.aBoolean3 = ActivityAudioCutter.BOOLEAN;
                    ActivityAudioCutter.this.markerView1.setAlpha(255);
                }
            }, 0);
        }
        int width = ((this.anInt2 - this.anInt10) - this.markerView.getWidth()) + this.anInt18;
        if (this.markerView.getWidth() + width >= 0) {
            if (!this.aBoolean2) {
                this.handler.postDelayed(new Runnable() {
                    public void run() {
                        ActivityAudioCutter.this.aBoolean2 = ActivityAudioCutter.BOOLEAN;
                        ActivityAudioCutter.this.markerView.setAlpha(255);
                    }
                }, 0);
            }
            i2 = width;
        } else if (this.aBoolean2) {
            this.markerView.setAlpha(0);
            this.aBoolean2 = false;
        }
        this.markerView1.setLayoutParams(new LayoutParams(-2, -2, i7, this.anInt17));
        this.markerView.setLayoutParams(new LayoutParams(-2, -2, i2, (this.waveformView.getMeasuredHeight() - this.markerView.getHeight()) - this.anInt16));
    }

    private void h() {
        if (this.aBoolean4) {
            this.imageView2.setImageResource(R.drawable.ic_playlist_pause);
            this.imageView2.setContentDescription(getResources().getText(R.string.stop));
            return;
        }
        this.imageView2.setImageResource(R.drawable.ic_playlist_play);
        this.imageView2.setContentDescription(getResources().getText(R.string.play));
    }

    private void i() {
        this.anInt3 = this.waveformView.secondsToPixels(0.0d);
        this.anInt2 = this.waveformView.secondsToPixels(15.0d);
    }

    private int a(int i2) {
        if (i2 < 0) {
            return 0;
        }
        return i2 > this.anInt4 ? this.anInt4 : i2;
    }

    private void j() {
        b(this.anInt3 - (this.anInt / 2));
    }

    private void k() {
        c(this.anInt3 - (this.anInt / 2));
    }

    private void l() {
        b(this.anInt2 - (this.anInt / 2));
    }

    private void m() {
        c(this.anInt2 - (this.anInt / 2));
    }

    private void b(int i2) {
        c(i2);
        g();
    }

    private void c(int i2) {
        if (!this.aBoolean5) {
            this.anInt9 = i2;
            if (this.anInt9 + (this.anInt / 2) > this.anInt4) {
                this.anInt9 = this.anInt4 - (this.anInt / 2);
            }
            if (this.anInt9 < 0) {
                this.anInt9 = 0;
            }
        }
    }


    public String d(int i2) {
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
        this.aBoolean4 = false;
        h();
    }
  public synchronized void e(int i2) {
        try {
            if (this.aBoolean4) {
                n();
            } else if (this.mediaPlayer != null) {
                this.anInt7 = this.waveformView.pixelsToMillisecs(i2);
                if (i2 < this.anInt3) {
                    this.anInt5 = this.waveformView.pixelsToMillisecs(this.anInt3);
                } else if (i2 > this.anInt2) {
                    this.anInt5 = this.waveformView.pixelsToMillisecs(this.anInt4);
                } else {
                    this.anInt5 = this.waveformView.pixelsToMillisecs(this.anInt2);
                }
                this.anInt6 = 0;
                int secondsToFrames = this.waveformView.secondsToFrames(((double) this.anInt7) * 0.001d);
                int secondsToFrames2 = this.waveformView.secondsToFrames(((double) this.anInt5) * 0.001d);
                int seekableFrameOffset = this.cheapSoundFile.getSeekableFrameOffset(secondsToFrames);
                int seekableFrameOffset2 = this.cheapSoundFile.getSeekableFrameOffset(secondsToFrames2);
                if (this.aBoolean6 && seekableFrameOffset >= 0 && seekableFrameOffset2 >= 0) {
                    this.mediaPlayer.reset();
                    MediaPlayer mediaPlayer = this.mediaPlayer;
                    AudioManager audioManager = this.audioManager;
                    mediaPlayer.setAudioStreamType(3);
                    this.mediaPlayer.setDataSource(new FileInputStream(this.file.getAbsolutePath()).getFD(), seekableFrameOffset, seekableFrameOffset2 - seekableFrameOffset);
                    this.mediaPlayer.prepare();
                    this.anInt6 = this.anInt7;
                    System.out.println("Exception trying to play file subset");
                    this.mediaPlayer.reset();
                    MediaPlayer mediaPlayer2 = this.mediaPlayer;
                    AudioManager audioManager2 = this.audioManager;
                    mediaPlayer2.setAudioStreamType(3);
                    this.mediaPlayer.setDataSource(this.file.getAbsolutePath());
                    this.mediaPlayer.prepare();
                    this.anInt6 = 0;
                }
                this.mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
                    public synchronized void onCompletion(MediaPlayer mediaPlayer) {
                        ActivityAudioCutter.this.n();
                    }
                });
                this.aBoolean4 = BOOLEAN;
                if (this.anInt6 == 0) {
                    this.mediaPlayer.seekTo(this.anInt7);
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
                ActivityAudioCutter.this.finish();
            }
        }).setCancelable(false).show();
    }

    private void a(Exception exc, int i2) {
        a(exc, getResources().getText(i2));
    }

    private String a(String str) {
        return str.substring(str.lastIndexOf(46));
    }

    @Override public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), ActivityMusicList.class);
        intent.setFlags(67108864);
        startActivity(intent);
        finish();
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picker, menu);
        return BOOLEAN;
    }

   @Override public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
            return BOOLEAN;
        }
        if (menuItem.getItemId() == R.id.Done) {
            this.dialog = new Dialog(this);
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.requestWindowFeature(1);
            this.dialog.setContentView(R.layout.mp3_cutter_enter_filename_popup);
            this.dialog.show();
            this.dialog.findViewById(R.id.closePopup).setOnClickListener(new OnClickListener() {
                @Override public void onClick(View view) {
                    ActivityAudioCutter.this.dialog.dismiss();
                }
            });
            ((EditorCustomTextView) this.dialog.findViewById(R.id.DailogName)).setText("MP3 Cutter");
            final EditorCustomEditText editorCustomEditText = this.dialog.findViewById(R.id.message);
            this.dialog.findViewById(R.id.sendBtn).setOnClickListener(new OnClickListener() {
                @Override public void onClick(View view) {
                    ActivityAudioCutter.this.string3 = editorCustomEditText.getText().toString();
                    if (ActivityAudioCutter.this.string3.isEmpty()) {
                        Toast.makeText(ActivityAudioCutter.this, "Please Enter File Name", Toast.LENGTH_LONG).show();
                        return;
                    }
                    StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
                    long availableBlocks = ((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize());
                    File file = new File(ActivityAudioCutter.this.string17);
                    ActivityAudioCutter.this.aLong1 = 0;
                    ActivityAudioCutter.this.aLong1 = file.length() / 1024;
                    if ((availableBlocks / 1024) / 1024 >= ActivityAudioCutter.this.aLong1 / 1024) {
                        ActivityAudioCutter.this.dialog.dismiss();
                        ActivityAudioCutter.this.a((CharSequence) ActivityAudioCutter.this.string3);
                        return;
                    }
                    Toast.makeText(ActivityAudioCutter.this.getApplicationContext(), "Out of Memory!......", Toast.LENGTH_SHORT).show();
                    ActivityAudioCutter.this.dialog.dismiss();
                }
            });
        }
        return super.onOptionsItemSelected(menuItem);
    }


    public void a(CharSequence charSequence) {
        final String a2 = a(charSequence, this.string11);
        if (a2 == null) {
            a(new Exception(), R.string.no_unique_filename);
            return;
        }
        this.string16 = a2;
        double pixelsToSeconds = this.waveformView.pixelsToSeconds(this.anInt3);
        double pixelsToSeconds2 = this.waveformView.pixelsToSeconds(this.anInt2);
        int secondsToFrames = this.waveformView.secondsToFrames(pixelsToSeconds);
        int secondsToFrames2 = this.waveformView.secondsToFrames(pixelsToSeconds2);
        int i2 = (int) ((pixelsToSeconds2 - pixelsToSeconds) + 0.5d);
        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setProgressStyle(0);
        this.progressDialog.setTitle(R.string.progress_dialog_saving);
        this.progressDialog.setIndeterminate(BOOLEAN);
        this.progressDialog.setCancelable(false);
        this.progressDialog.show();
        final int i3 = secondsToFrames;
        final int i4 = secondsToFrames2;
        final CharSequence charSequence2 = charSequence;
        final int i5 = i2;
        Thread r1 = new Thread() {
            public void run() {
                final Exception exc;
                final CharSequence charSequence;
                final File file = new File(a2);
                try {
                    ActivityAudioCutter.this.cheapSoundFile.WriteFile(file, i3, i4 - i3);
                    CheapSoundFile.create(a2, new ProgressListener() {
                        public boolean reportProgress(double d) {
                            return ActivityAudioCutter.BOOLEAN;
                        }
                    });
                    ActivityAudioCutter.this.progressDialog.dismiss();
                    ActivityAudioCutter.this.handler.post(new Runnable() {
                        public void run() {
                            ActivityAudioCutter.this.a(charSequence2, a2, file, i5);
                        }
                    });
                } catch (Exception e2) {
                    ActivityAudioCutter.this.progressDialog.dismiss();
                    if (e2.getMessage().equals("No space left on device")) {
                        charSequence = ActivityAudioCutter.this.getResources().getText(R.string.no_space_error);
                        exc = null;
                    } else {
                        exc = e2;
                        charSequence = ActivityAudioCutter.this.getResources().getText(R.string.write_error);
                    }
                    ActivityAudioCutter.this.handler.post(new Runnable() {
                        public void run() {
                            ActivityAudioCutter.this.a("WriteError", charSequence, exc);
                        }
                    });
                }
            }
        };
        r1.start();
    }

    private String a(CharSequence charSequence, String str) {
        String str2;
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb.append("/");
        sb.append(getResources().getString(R.string.MainFolderName));
        sb.append("/");
        sb.append(getResources().getString(R.string.AudioCutter));
        sb.append("/");
        String sb2 = sb.toString();
        File file = new File(sb2);
        if (!file.exists()) {
            file.mkdirs();
        }
        int i2 = 0;
        String str3 = "";
        for (int i3 = 0; i3 < charSequence.length(); i3++) {
            if (Character.isLetterOrDigit(charSequence.charAt(i3))) {
                StringBuilder sb3 = new StringBuilder(str3);
                sb3.append(charSequence.charAt(i3));
                str3 = sb3.toString();
            }
        }
        while (i2 < 100) {
            if (i2 > 0) {
                StringBuilder sb4 = new StringBuilder(sb2);
                sb4.append(str3);
                sb4.append(i2);
                sb4.append(str);
                str2 = sb4.toString();
            } else {
                StringBuilder sb5 = new StringBuilder(sb2);
                sb5.append(str3);
                sb5.append(str);
                str2 = sb5.toString();
            }
            try {
                new RandomAccessFile(new File(str2), "r").close();
                i2++;
            } catch (Exception unused) {
                return str2;
            }
        }
        return null;
    }


    @SuppressLint({"WrongConstant"})
    public void a(CharSequence charSequence, String str, File file, int i2) {
        if (file.length() <= 512) {
            file.delete();
            new AlertDialog.Builder(this).setTitle(R.string.alert_title_failure).setMessage(R.string.too_small_error).setPositiveButton(R.string.alert_ok_button, null).setCancelable(false).show();
            return;
        }
        long length = file.length();
        string = str;
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(getResources().getText(R.string.artist_name));
        String sb2 = sb.toString();
        ContentValues contentValues = new ContentValues();
        contentValues.put("_data", str);
        contentValues.put("title", charSequence.toString());
        contentValues.put("_size", Long.valueOf(length));
        contentValues.put("mime_type", "audio/mpeg");
        contentValues.put("artist", sb2);
        contentValues.put("duration", Integer.valueOf(i2));
        setResult(-1, new Intent().setData(getContentResolver().insert(Media.getContentUriForPath(str), contentValues)));
        this.string2 = file.getAbsoluteFile().toString();
        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        intent.setData(Uri.fromFile(file.getAbsoluteFile()));
        getApplicationContext().sendBroadcast(intent);
        c();
    }
}
