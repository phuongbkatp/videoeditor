package com.video_editor.pro.ActivityAudioJoiner;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.MediaStore.Audio.Media;
import android.provider.MediaStore.Images;
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
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
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

public class ActivityAudioJoiner extends AppCompatActivity implements MarkerListener, WaveformListener {
    private int anInt;
    static final boolean BOOLEAN = true;
    private String string;
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
    AudioManager audioManager;
    private int anInt10;

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
    private InterstitialAd interstitialAd;

    public Runnable runnable = new Runnable() {
        public void run() {
            if (ActivityAudioJoiner.this.anInt3 != ActivityAudioJoiner.this.anInt12) {
                if (ActivityAudioJoiner.this.d(ActivityAudioJoiner.this.anInt3) == "") {
                    int parseFloat = (int) Float.parseFloat("00.00");
                    int i = parseFloat / 60;
                    if (i <= 9) {
                        ActivityAudioJoiner activityAudioJoiner = ActivityAudioJoiner.this;
                        StringBuilder sb = new StringBuilder();
                        sb.append("0");
                        sb.append(i);
                        activityAudioJoiner.string3 = sb.toString();
                    } else {
                        int i2 = i % 60;
                        if (i2 <= 9) {
                            ActivityAudioJoiner activityAudioJoiner2 = ActivityAudioJoiner.this;
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("0");
                            sb2.append(i2);
                            activityAudioJoiner2.string5 = sb2.toString();
                        } else {
                            int i3 = parseFloat % 60;
                            if (i3 <= 9) {
                                ActivityAudioJoiner activityAudioJoiner3 = ActivityAudioJoiner.this;
                                StringBuilder sb3 = new StringBuilder();
                                sb3.append("0");
                                sb3.append(i3);
                                activityAudioJoiner3.string7 = sb3.toString();
                            }
                        }
                    }
                } else {
                    int parseFloat2 = (int) Float.parseFloat(ActivityAudioJoiner.this.d(ActivityAudioJoiner.this.anInt3));
                    int i4 = parseFloat2 / 3600;
                    if (i4 <= 9) {
                        ActivityAudioJoiner activityAudioJoiner4 = ActivityAudioJoiner.this;
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append("0");
                        sb4.append(i4);
                        activityAudioJoiner4.string3 = sb4.toString();
                    } else {
                        ActivityAudioJoiner activityAudioJoiner5 = ActivityAudioJoiner.this;
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append("");
                        sb5.append(i4);
                        activityAudioJoiner5.string3 = sb5.toString();
                    }
                    int i5 = (parseFloat2 / 60) % 60;
                    if (i5 <= 9) {
                        ActivityAudioJoiner activityAudioJoiner6 = ActivityAudioJoiner.this;
                        StringBuilder sb6 = new StringBuilder();
                        sb6.append("0");
                        sb6.append(i5);
                        activityAudioJoiner6.string5 = sb6.toString();
                    } else {
                        ActivityAudioJoiner activityAudioJoiner7 = ActivityAudioJoiner.this;
                        StringBuilder sb7 = new StringBuilder();
                        sb7.append("");
                        sb7.append(i5);
                        activityAudioJoiner7.string5 = sb7.toString();
                    }
                    int i6 = parseFloat2 % 60;
                    if (i6 <= 9) {
                        ActivityAudioJoiner activityAudioJoiner8 = ActivityAudioJoiner.this;
                        StringBuilder sb8 = new StringBuilder();
                        sb8.append("0");
                        sb8.append(i6);
                        activityAudioJoiner8.string7 = sb8.toString();
                    } else {
                        ActivityAudioJoiner activityAudioJoiner9 = ActivityAudioJoiner.this;
                        StringBuilder sb9 = new StringBuilder();
                        sb9.append("");
                        sb9.append(i6);
                        activityAudioJoiner9.string7 = sb9.toString();
                    }
                }
                ActivityAudioJoiner.this.anInt12 = ActivityAudioJoiner.this.anInt3;
            }
            if (ActivityAudioJoiner.this.anInt2 != ActivityAudioJoiner.this.anInt11) {
                if (ActivityAudioJoiner.this.d(ActivityAudioJoiner.this.anInt2) != "") {
                    int parseFloat3 = (int) Float.parseFloat(ActivityAudioJoiner.this.d(ActivityAudioJoiner.this.anInt2 - ActivityAudioJoiner.this.anInt3));
                    int i7 = parseFloat3 / 3600;
                    if (i7 <= 9) {
                        ActivityAudioJoiner activityAudioJoiner10 = ActivityAudioJoiner.this;
                        StringBuilder sb10 = new StringBuilder();
                        sb10.append("0");
                        sb10.append(i7);
                        activityAudioJoiner10.string4 = sb10.toString();
                    } else {
                        ActivityAudioJoiner activityAudioJoiner11 = ActivityAudioJoiner.this;
                        StringBuilder sb11 = new StringBuilder();
                        sb11.append("");
                        sb11.append(i7);
                        activityAudioJoiner11.string4 = sb11.toString();
                    }
                    int i8 = (parseFloat3 / 60) % 60;
                    if (i8 <= 9) {
                        ActivityAudioJoiner activityAudioJoiner12 = ActivityAudioJoiner.this;
                        StringBuilder sb12 = new StringBuilder();
                        sb12.append("0");
                        sb12.append(i8);
                        activityAudioJoiner12.string6 = sb12.toString();
                    } else {
                        ActivityAudioJoiner activityAudioJoiner13 = ActivityAudioJoiner.this;
                        StringBuilder sb13 = new StringBuilder();
                        sb13.append("");
                        sb13.append(i8);
                        activityAudioJoiner13.string6 = sb13.toString();
                    }
                    int i9 = parseFloat3 % 60;
                    if (i9 <= 9) {
                        ActivityAudioJoiner activityAudioJoiner14 = ActivityAudioJoiner.this;
                        StringBuilder sb14 = new StringBuilder();
                        sb14.append("0");
                        sb14.append(i9);
                        activityAudioJoiner14.string8 = sb14.toString();
                    } else {
                        ActivityAudioJoiner activityAudioJoiner15 = ActivityAudioJoiner.this;
                        StringBuilder sb15 = new StringBuilder();
                        sb15.append("");
                        sb15.append(i9);
                        activityAudioJoiner15.string8 = sb15.toString();
                    }
                }
                ActivityAudioJoiner.this.anInt11 = ActivityAudioJoiner.this.anInt2;
            }
            ActivityAudioJoiner.this.handler.postDelayed(ActivityAudioJoiner.this.runnable, 100);
        }
    };
    private OnClickListener onClickListener = new OnClickListener() {
        @Override public void onClick(View view) {
            ActivityAudioJoiner.this.e(ActivityAudioJoiner.this.anInt3);
        }
    };
    public int audioRate = 64;
    private OnClickListener clickListener = new OnClickListener() {
        @Override public void onClick(View view) {
            if (ActivityAudioJoiner.this.aBoolean4) {
                int currentPosition = ActivityAudioJoiner.this.mediaPlayer.getCurrentPosition() - 5000;
                if (currentPosition < ActivityAudioJoiner.this.anInt7) {
                    currentPosition = ActivityAudioJoiner.this.anInt7;
                }
                ActivityAudioJoiner.this.mediaPlayer.seekTo(currentPosition);
                return;
            }
            ActivityAudioJoiner.this.markerView1.requestFocus();
            ActivityAudioJoiner.this.markerFocus(ActivityAudioJoiner.this.markerView1);
        }
    };
    private OnClickListener onClickListener1 = new OnClickListener() {
        @Override public void onClick(View view) {
            try {
                if (ActivityAudioJoiner.this.aBoolean4) {
                    int currentPosition = BaseImageDownloader.DEFAULT_HTTP_CONNECT_TIMEOUT + ActivityAudioJoiner.this.mediaPlayer.getCurrentPosition();
                    if (currentPosition > ActivityAudioJoiner.this.anInt5) {
                        currentPosition = ActivityAudioJoiner.this.anInt5;
                    }
                    ActivityAudioJoiner.this.mediaPlayer.seekTo(currentPosition);
                    return;
                }
                ActivityAudioJoiner.this.markerView.requestFocus();
                ActivityAudioJoiner.this.markerFocus(ActivityAudioJoiner.this.markerView);
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
    private OnClickListener clickListener1 = new OnClickListener() {
        @Override public void onClick(View view) {
            try {
                AudioManager audioManager = ActivityAudioJoiner.this.audioManager;
                AudioManager audioManager2 = ActivityAudioJoiner.this.audioManager;
                AudioManager audioManager3 = ActivityAudioJoiner.this.audioManager;
                AudioManager audioManager4 = ActivityAudioJoiner.this.audioManager;
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
    private OnClickListener onClickListener2 = new OnClickListener() {
        @Override public void onClick(View view) {
            try {
                AudioManager audioManager = ActivityAudioJoiner.this.audioManager;
                AudioManager audioManager2 = ActivityAudioJoiner.this.audioManager;
                AudioManager audioManager3 = ActivityAudioJoiner.this.audioManager;
                AudioManager audioManager4 = ActivityAudioJoiner.this.audioManager;
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

    public String string2;
    RelativeLayout relativeLayout;
    String string3;
    String string4;
    String string5;
    String string6;
    public FFmpeg ffmpeg;
    String string7;
    String string8;
    WakeLock wakeLock;
    AdapterAudioJoiner adapterAudioJoiner;
    private UnifiedNativeAd nativeAd;
    ArrayList<String> arrayList;
    Spinner spinner;
    Cursor cursor;
    public long aLong1;
    private long aLong2;
    public boolean aBoolean7;
    public ProgressDialog progressDialog;
    public CheapSoundFile cheapSoundFile;
    public File file;
    private String string9;
    private String string10;
    private String string11;
    private String string12;
    private String string13;
    private String string14;


    public void a(CharSequence charSequence, CharSequence charSequence2, Exception exc) {
    }

    public void markerDraw() {
    }

    public void markerEnter(MarkerView markerView) {
    }


    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.string9 = null;
        this.uri = null;
        this.mediaPlayer = null;
        refreshAd();
        this.aBoolean4 = false;
        a();
        this.aBoolean = getIntent().getBooleanExtra("was_get_content_intent", false);
        this.string = EditorHelper.audiopath;
        this.cheapSoundFile = null;
        this.aBoolean1 = false;
        this.string.equals("record");
        this.handler = new Handler();
        this.handler.postDelayed(this.runnable, 100);
        if (!this.string.equals("record")) {
            g();
        }
    }


    @Override public void onDestroy() {
        if (nativeAd != null) {
            nativeAd.destroy();
        }
        if (this.mediaPlayer != null && this.mediaPlayer.isPlaying()) {
            this.mediaPlayer.stop();
        }
        this.mediaPlayer = null;
        if (this.string9 != null) {
            try {
                if (!new File(this.string9).delete()) {
                    a(new Exception(), R.string.delete_tmp_error);
                }
                getContentResolver().delete(this.uri, null, null);
            } catch (SecurityException e2) {
                a(e2, R.string.delete_tmp_error);
            }
        }
        super.onDestroy();
    }

     public void onActivityResult(Configuration configuration) {
        final int zoomLevel = this.waveformView.getZoomLevel();
        super.onConfigurationChanged(configuration);
        a();
        this.handler.postDelayed(new Runnable() {
            public void run() {
                ActivityAudioJoiner.this.markerView1.requestFocus();
                ActivityAudioJoiner.this.markerFocus(ActivityAudioJoiner.this.markerView1);
                ActivityAudioJoiner.this.waveformView.setZoomLevel(zoomLevel);
                ActivityAudioJoiner.this.waveformView.recomputeHeights(ActivityAudioJoiner.this.aFloat1);
                ActivityAudioJoiner.this.i();
            }
        }, 500);
    }

    public void waveformDraw() {
        this.anInt4 = this.waveformView.getMeasuredWidth();
        if (this.anInt9 != this.anInt10 && !this.aBoolean1) {
            i();
        } else if (this.aBoolean4) {
            i();
        } else if (this.anInt8 != 0) {
            i();
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
        i();
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
                p();
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
        i();
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
        i();
    }

    public void markerTouchEnd(MarkerView markerView) {
        this.aBoolean5 = false;
        if (markerView == this.markerView1) {
            l();
        } else {
            n();
        }
    }

    public void markerLeft(MarkerView markerView, int i2) {
        this.aBoolean1 = BOOLEAN;
        if (markerView == this.markerView1) {
            int i3 = this.anInt3;
            this.anInt3 = a(this.anInt3 - i2);
            this.anInt2 = a(this.anInt2 - (i3 - this.anInt3));
            l();
        }
        if (markerView == this.markerView) {
            if (this.anInt2 == this.anInt3) {
                this.anInt3 = a(this.anInt3 - i2);
                this.anInt2 = this.anInt3;
            } else {
                this.anInt2 = a(this.anInt2 - i2);
            }
            n();
        }
        i();
    }

    public void markerRight(MarkerView markerView, int i2) {
        this.aBoolean1 = BOOLEAN;
        if (markerView == this.markerView1) {
            int i3 = this.anInt3;
            this.anInt3 += i2;
            if (this.anInt3 > this.anInt) {
                this.anInt3 = this.anInt;
            }
            this.anInt2 += this.anInt3 - i3;
            if (this.anInt2 > this.anInt) {
                this.anInt2 = this.anInt;
            }
            l();
        }
        if (markerView == this.markerView) {
            this.anInt2 += i2;
            if (this.anInt2 > this.anInt) {
                this.anInt2 = this.anInt;
            }
            n();
        }
        i();
    }

    public void markerKeyUp() {
        this.aBoolean1 = false;
        i();
    }

    public void markerFocus(MarkerView markerView) {
        this.aBoolean1 = false;
        if (markerView == this.markerView1) {
            m();
        } else {
            o();
        }
        this.handler.postDelayed(new Runnable() {
            public void run() {
                ActivityAudioJoiner.this.i();
            }
        }, 100);
    }

    @SuppressLint("InvalidWakeLockTag")
    private void a() {
        setContentView( R.layout.activity_audio_joiner);
        Toolbar toolbar = findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Audio Joiner");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (BOOLEAN || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(BOOLEAN);
            supportActionBar.setDisplayShowTitleEnabled(false);
            this.ffmpeg = FFmpeg.getInstance(this);
            e();
            this.wakeLock = ((PowerManager) getSystemService(Context.POWER_SERVICE)).newWakeLock(6, "VideoMerge");
            if (!this.wakeLock.isHeld()) {
                this.wakeLock.acquire();
            }
            this.arrayList = new ArrayList<>();
            this.arrayList.add("64 K/Bit");
            this.arrayList.add("128 K/Bit");
            this.arrayList.add("256 K/Bit");
            this.relativeLayout = findViewById(R.id.BtnAddMusic);
            this.relativeLayout.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View view) {
                    ActivityAudioJoiner.this.startActivityForResult(new Intent("android.intent.action.PICK", Media.EXTERNAL_CONTENT_URI), 1);
                }
            });
            this.spinner = findViewById(R.id.sp_convert);
            this.adapterAudioJoiner = new AdapterAudioJoiner(this, this.arrayList, 0);
            this.spinner.setAdapter(this.adapterAudioJoiner);
            this.spinner.setSelection(0);
            this.spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                public void onNothingSelected(AdapterView<?> adapterView) {
                }

                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                    if (adapterView.getItemAtPosition(i).toString() == "64 K/Bit") {
                        ActivityAudioJoiner.this.audioRate = 64;
                    } else if (adapterView.getItemAtPosition(i).toString() == "128 K/Bit") {
                        ActivityAudioJoiner.this.audioRate = 128;
                    } else if (adapterView.getItemAtPosition(i).toString() == "256 K/Bit") {
                        ActivityAudioJoiner.this.audioRate = 256;
                    }
                }
            });
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
            this.imageView2 = findViewById(R.id.play);
            this.imageView2.setOnClickListener(this.onClickListener);
            this.imageView1 = findViewById(R.id.rew);
            this.imageView1.setOnClickListener(this.clickListener);
            this.imageView = findViewById(R.id.ffwd);
            this.imageView.setOnClickListener(this.onClickListener1);
            this.imageButton = findViewById(R.id.btnvolumdown);
            this.imageButton.setOnClickListener(this.clickListener1);
            this.imageButton1 = findViewById(R.id.btnvolumup);
            this.imageButton1.setOnClickListener(this.onClickListener2);
            j();
            this.waveformView = findViewById(R.id.waveform);
            this.waveformView.setListener(this);
            this.anInt = 0;
            this.anInt12 = -1;
            this.anInt11 = -1;
            if (this.cheapSoundFile != null) {
                this.waveformView.setSoundFile(this.cheapSoundFile);
                this.waveformView.recomputeHeights(this.aFloat1);
                this.anInt = this.waveformView.maxPos();
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
            i();
            this.interstitialAd = new InterstitialAd(this);
            this.interstitialAd.setAdUnitId(getString(R.string.interstitial_id_admob));
            this.interstitialAd.setAdListener(new AdListener() {
                @Override public void onAdClosed() {
                    ActivityAudioJoiner.this.d();
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
            d();
        } else {
            this.interstitialAd.show();
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
                FrameLayout nativeAdPlaceHolder = ActivityAudioJoiner.this.findViewById(R.id.nativeAdPlaceHolder);
                @SuppressLint("InflateParams") UnifiedNativeAdView adView = (UnifiedNativeAdView) ActivityAudioJoiner.this.getLayoutInflater()
                        .inflate(R.layout.native_ad_unified_smaller, null);
                ActivityAudioJoiner.this.populateUnifiedNativeAdView(unifiedNativeAd, adView);
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
        try {
            this.ffmpeg.loadBinary(new LoadBinaryResponseHandler() {
                @Override public void onFailure() {
                    ActivityAudioJoiner.this.f();
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
            f();
        }
    }


    public void f() {
        new AlertDialog.Builder(this).setIcon(17301543).setTitle("Device not supported").setMessage("FFmpeg is not supported on your device").setCancelable(false).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialogInterface, int i) {
                ActivityAudioJoiner.this.finish();
            }
        }).create().show();
    }

    private void g() {
        this.file = new File(this.string);
        this.string10 = a(this.string);
        SongMetadataReader songMetadataReader = new SongMetadataReader(this, this.string);
        this.string11 = songMetadataReader.mTitle;
        this.string14 = songMetadataReader.mArtist;
        this.string13 = songMetadataReader.mAlbum;
        this.anInt1 = songMetadataReader.mYear;
        this.string12 = songMetadataReader.mGenre;
        String str = this.string11;
        if (this.string14 != null && this.string14.length() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(" - ");
            sb.append(this.string14);
            str = sb.toString();
        }
        this.textView.setText(str);
        this.textView.setSelected(BOOLEAN);
        this.aLong2 = System.currentTimeMillis();
        this.aLong1 = System.currentTimeMillis();
        this.aBoolean7 = BOOLEAN;
        this.progressDialog = new ProgressDialog(this);
        this.progressDialog.setProgressStyle(1);
        this.progressDialog.setTitle(R.string.progress_dialog_loading);
        this.progressDialog.setCancelable(false);
        this.progressDialog.show();
        final ProgressListener r0 = new ProgressListener() {
            public boolean reportProgress(double d) {
                long currentTimeMillis = System.currentTimeMillis();
                if (currentTimeMillis - ActivityAudioJoiner.this.aLong1 > 100) {
                    ActivityAudioJoiner.this.progressDialog.setProgress((int) (((double) ActivityAudioJoiner.this.progressDialog.getMax()) * d));
                    ActivityAudioJoiner.this.aLong1 = currentTimeMillis;
                }
                return ActivityAudioJoiner.this.aBoolean7;
            }
        };
        this.aBoolean6 = false;
        new Thread() {
            public void run() {
                ActivityAudioJoiner.this.aBoolean6 = SeekTest.CanSeekAccurately(ActivityAudioJoiner.this.getPreferences(0));
                System.out.println("Seek test done, creating media player.");
                try {
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(ActivityAudioJoiner.this.file.getAbsolutePath());
                    AudioManager audioManager = ActivityAudioJoiner.this.audioManager;
                    mediaPlayer.setAudioStreamType(3);
                    mediaPlayer.prepare();
                    ActivityAudioJoiner.this.mediaPlayer = mediaPlayer;
                } catch (final IOException e) {
                    ActivityAudioJoiner.this.handler.post(new Runnable() {
                        public void run() {
                            ActivityAudioJoiner.this.a("ReadError", ActivityAudioJoiner.this.getResources().getText(R.string.read_error), e);
                        }
                    });
                }
            }
        }.start();
        new Thread() {
            public void run() {
                final String str;
                try {
                    ActivityAudioJoiner.this.cheapSoundFile = CheapSoundFile.create(ActivityAudioJoiner.this.file.getAbsolutePath(), r0);
                    if (ActivityAudioJoiner.this.cheapSoundFile == null) {
                        ActivityAudioJoiner.this.progressDialog.dismiss();
                        String[] split = ActivityAudioJoiner.this.file.getName().toLowerCase().split("\\.");
                        if (split.length < 2) {
                            str = ActivityAudioJoiner.this.getResources().getString(R.string.no_extension_error);
                        } else {
                            StringBuilder sb = new StringBuilder();
                            sb.append(ActivityAudioJoiner.this.getResources().getString(R.string.bad_extension_error));
                            sb.append(" ");
                            sb.append(split[split.length - 1]);
                            str = sb.toString();
                        }
                        ActivityAudioJoiner.this.handler.post(new Runnable() {
                            public void run() {
                                ActivityAudioJoiner.this.a("UnsupportedExtension", str, new Exception());
                            }
                        });
                        return;
                    }
                    ActivityAudioJoiner.this.progressDialog.dismiss();
                    if (ActivityAudioJoiner.this.aBoolean7) {
                        ActivityAudioJoiner.this.handler.post(new Runnable() {
                            public void run() {
                                ActivityAudioJoiner.this.h();
                            }
                        });
                    } else {
                        ActivityAudioJoiner.this.finish();
                    }
                } catch (final Exception e) {
                    ActivityAudioJoiner.this.progressDialog.dismiss();
                    e.printStackTrace();
                    ActivityAudioJoiner.this.handler.post(new Runnable() {
                        public void run() {
                            ActivityAudioJoiner.this.a("ReadError", ActivityAudioJoiner.this.getResources().getText(R.string.read_error), e);
                        }
                    });
                }
            }
        }.start();
    }


    public void h() {
        this.waveformView.setSoundFile(this.cheapSoundFile);
        this.waveformView.recomputeHeights(this.aFloat1);
        this.anInt = this.waveformView.maxPos();
        this.anInt12 = -1;
        this.anInt11 = -1;
        this.aBoolean5 = false;
        this.anInt10 = 0;
        this.anInt9 = 0;
        this.anInt8 = 0;
        k();
        if (this.anInt2 > this.anInt) {
            this.anInt2 = this.anInt;
        }
        i();
    }


    public synchronized void i() {
        if (this.aBoolean4) {
            int currentPosition = this.mediaPlayer.getCurrentPosition() + this.anInt6;
            int millisecsToPixels = this.waveformView.millisecsToPixels(currentPosition);
            this.waveformView.setPlayback(millisecsToPixels);
            c(millisecsToPixels - (this.anInt4 / 2));
            if (currentPosition >= this.anInt5) {
                p();
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
                if (this.anInt10 + (this.anInt4 / 2) > this.anInt) {
                    this.anInt10 = this.anInt - (this.anInt4 / 2);
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
                    ActivityAudioJoiner.this.aBoolean3 = ActivityAudioJoiner.BOOLEAN;
                    ActivityAudioJoiner.this.markerView1.setAlpha(255);
                }
            }, 0);
        }
        int width = ((this.anInt2 - this.anInt10) - this.markerView.getWidth()) + this.anInt18;
        if (this.markerView.getWidth() + width >= 0) {
            if (!this.aBoolean2) {
                this.handler.postDelayed(new Runnable() {
                    public void run() {
                        ActivityAudioJoiner.this.aBoolean2 = ActivityAudioJoiner.BOOLEAN;
                        ActivityAudioJoiner.this.markerView.setAlpha(255);
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

    private void j() {
        if (this.aBoolean4) {
            this.imageView2.setImageResource(R.drawable.ic_playlist_pause);
            this.imageView2.setContentDescription(getResources().getText(R.string.stop));
            return;
        }
        this.imageView2.setImageResource(R.drawable.ic_playlist_play);
        this.imageView2.setContentDescription(getResources().getText(R.string.play));
    }

    private void k() {
        this.anInt3 = this.waveformView.secondsToPixels(0.0d);
        this.anInt2 = this.waveformView.secondsToPixels(15.0d);
    }

    private int a(int i2) {
        if (i2 < 0) {
            return 0;
        }
        return i2 > this.anInt ? this.anInt : i2;
    }

    private void l() {
        b(this.anInt3 - (this.anInt4 / 2));
    }

    private void m() {
        c(this.anInt3 - (this.anInt4 / 2));
    }

    private void n() {
        b(this.anInt2 - (this.anInt4 / 2));
    }

    private void o() {
        c(this.anInt2 - (this.anInt4 / 2));
    }

    private void b(int i2) {
        c(i2);
        i();
    }

    private void c(int i2) {
        if (!this.aBoolean5) {
            this.anInt9 = i2;
            if (this.anInt9 + (this.anInt4 / 2) > this.anInt) {
                this.anInt9 = this.anInt - (this.anInt4 / 2);
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


    public synchronized void p() {
        if (this.mediaPlayer != null && this.mediaPlayer.isPlaying()) {
            this.mediaPlayer.pause();
        }
        this.waveformView.setPlayback(-1);
        this.aBoolean4 = false;
        j();
    }


    public synchronized void e(int i2) {
        try {
            if (this.aBoolean4) {
                p();
            } else if (this.mediaPlayer != null) {
                this.anInt7 = this.waveformView.pixelsToMillisecs(i2);
                if (i2 < this.anInt3) {
                    this.anInt5 = this.waveformView.pixelsToMillisecs(this.anInt3);
                } else if (i2 > this.anInt2) {
                    this.anInt5 = this.waveformView.pixelsToMillisecs(this.anInt);
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
                        ActivityAudioJoiner.this.p();
                    }
                });
                this.aBoolean4 = BOOLEAN;
                if (this.anInt6 == 0) {
                    this.mediaPlayer.seekTo(this.anInt7);
                }
                this.mediaPlayer.start();
                i();
                j();
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
                ActivityAudioJoiner.this.finish();
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
            if (this.mediaPlayer != null && this.mediaPlayer.isPlaying()) {
                try {
                    this.mediaPlayer.pause();
                    this.imageView2.setImageResource(R.drawable.ic_playlist_play);
                    this.imageView2.setContentDescription(getResources().getText(R.string.play));
                } catch (IllegalStateException e2) {
                    e2.printStackTrace();
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
            sb.append("/");
            sb.append(getResources().getString(R.string.MainFolderName));
            sb.append("/");
            sb.append(getResources().getString(R.string.AudioJoiner));
            this.string2 = sb.toString();
            File file = new File(this.string2);
            if (!file.exists()) {
                file.mkdirs();
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(file.getAbsolutePath());
            sb2.append("/JoinMP3_");
            sb2.append(System.currentTimeMillis());
            sb2.append(".mp3");
            this.string2 = sb2.toString();
            StringBuilder sb3 = new StringBuilder();
            sb3.append("concat:");
            sb3.append(EditorHelper.audiopath);
            sb3.append("|");
            sb3.append(EditorHelper.audiopath1);
            a(new String[]{"-i", sb3.toString(), "-c", "copy", this.string2, "-map_metadata", "0:1"}, this.string2);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void a(String[] strArr, final String str) {
        try {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.show();
            this.ffmpeg.execute(strArr, new ExecuteBinaryResponseHandler() {
                @Override public void onFailure(String str) {
                    Log.d("ffmpegfailure", str);
                    try {
                        new File(str).delete();
                        ActivityAudioJoiner.this.deleteFromGallery(str);
                        Toast.makeText(ActivityAudioJoiner.this.getApplicationContext(), "Error Creating Video", 0).show();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }

                @Override public void onSuccess(String str) {
                    progressDialog.dismiss();
                    Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                    intent.setData(Uri.fromFile(new File(ActivityAudioJoiner.this.string2)));
                    ActivityAudioJoiner.this.sendBroadcast(intent);
                    ActivityAudioJoiner.this.c();
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
                    ActivityAudioJoiner.this.reloadGallery(str);
                }
            });
            getWindow().clearFlags(16);
        } catch (FFmpegCommandAlreadyRunningException unused) {
        }
    }

    public void deleteFromGallery(String str) {
        String[] strArr = {"_id"};
        String[] strArr2 = {str};
        Uri uri = Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver contentResolver = getContentResolver();
        Cursor query = contentResolver.query(uri, strArr, "_data = ?", strArr2, null);
        if (query.moveToFirst()) {
            try {
                contentResolver.delete(ContentUris.withAppendedId(Images.Media.EXTERNAL_CONTENT_URI, query.getLong(query.getColumnIndexOrThrow("_id"))), null, null);
            } catch (IllegalArgumentException e2) {
                e2.printStackTrace();
            }
        } else {
            try {
                new File(str).delete();
                reloadGallery(str);
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        query.close();
    }


    @Override public void onActivityResult(int i2, int i3, Intent intent) {
        super.onActivityResult(i2, i3, intent);
        g();
        if (i2 == 1 && i3 == -1 && intent != null && intent.getData() != null) {
            try {
                this.cursor = getContentResolver().query(intent.getData(), new String[]{"_data"}, null, null, null);
                int columnIndexOrThrow = this.cursor.getColumnIndexOrThrow("_data");
                this.cursor.moveToFirst();
                EditorHelper.audiopath1 = this.cursor.getString(columnIndexOrThrow);
            } catch (NoSuchMethodError e2) {
                e2.printStackTrace();
            } catch (NullPointerException e3) {
                e3.printStackTrace();
            } catch (IllegalStateException e4) {
                e4.printStackTrace();
            } catch (NumberFormatException e5) {
                e5.printStackTrace();
            } catch (Exception e6) {
                e6.printStackTrace();
            }
        }
    }

    public void reloadGallery(String str) {
        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        intent.setData(Uri.fromFile(new File(str)));
        sendBroadcast(intent);
    }
}
