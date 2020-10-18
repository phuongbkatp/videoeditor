package com.video_editor.pro.ActivityVideoToAudio;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Audio;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Video.Media;
import android.provider.MediaStore.Video.Thumbnails;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.video_editor.pro.UtilsAndAdapters.EditorAudioPlayer;
import com.video_editor.pro.R;
import com.video_editor.pro.UtilsAndAdapters.EditorVideoPlayerState;
import com.video_editor.pro.UtilsAndAdapters.EditorVideoSliceSeekBar;
import com.video_editor.pro.UtilsAndAdapters.EditorVideoSliceSeekBar.SeekBarChangeListener;
import com.video_editor.pro.ActivityVideoMusicList.ActivityVideoMusicList;
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

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@SuppressLint({"WrongConstant"})
public class ActivityVideoToAudio extends AppCompatActivity {
    static final boolean BOOLEAN = true;
    public static String Meta_File_path;
    Boolean aBoolean = Boolean.valueOf(false);
    int anInt = 0;
    int anInt1 = 0;
    int anInt2;
    EditorVideoSliceSeekBar editorVideoSliceSeekBar;
    VideoView videoView;
    public TextView Filename;
    private InterstitialAd interstitialAd;
    ArrayList<String> arrayList = new ArrayList<>();
    String string;
    String string1;
    String string2;
    String string3;
    String string4;
    public FFmpeg ffmpeg;
    String string5;
    String string6;
    String string7 = "00";
    public TextView iv_aac;
    public TextView iv_mp3;
    String[] j = {"None", "40\nCBR", "48\nCBR", "64\nCBR", "80\nCBR", "96\nCBR", "112\nCBR", "128\nCBR", "160\nCBR", "192\nCBR", "224\nCBR", "256\nCBR", "320\nCBR", "245\nVBR", "225\nVBR", "190\nVBR", "175\nVBR", "165\nVBR", "130\nVBR", "115\nVBR", "100\nVBR", "85\nVBR", " 65\nVBR"};
    Bundle bundle;
    ImageView imageView;
    HorizontalListView listView;
    ImageView imageView1;
    LinearLayout linearLayout;
    LinearLayout linearLayout1;
    LinearLayout linearLayout2;
    LinearLayout linearLayout3;
    AdapterFontList adapterFontList;
    ProgressDialog aNull = null;
    public TextView textViewLeft;
    public TextView textViewRight;
    public TextView txt_kbps;
    public TextView txt_selectformat;
    RelativeLayout relativeLayout;
    RelativeLayout relativeLayout1;
    private UnifiedNativeAd nativeAd;
    public EditorVideoPlayerState editorVideoPlayerState = new EditorVideoPlayerState();
    public StateObserver videoStateObserver = new StateObserver();
    RelativeLayout relativeLayout2;
    RelativeLayout relativeLayout3;
    RelativeLayout relativeLayout4;
    RelativeLayout relativeLayout5;

    public class StateObserver extends Handler {
        public boolean alreadyStarted = false;
        public Runnable observerWork;

        public StateObserver() {
            this.observerWork = new Runnable() {
                public void run() {
                    StateObserver.this.startVideoProgressObserving();
                }
            };
        }

        public void startVideoProgressObserving() {
            if (!this.alreadyStarted) {
                this.alreadyStarted = ActivityVideoToAudio.BOOLEAN;
                sendEmptyMessage(0);
            }
        }

        @Override public void handleMessage(Message message) {
            this.alreadyStarted = false;
            ActivityVideoToAudio.this.editorVideoSliceSeekBar.videoPlayingProgress(ActivityVideoToAudio.this.videoView.getCurrentPosition());
            if (!ActivityVideoToAudio.this.videoView.isPlaying() || ActivityVideoToAudio.this.videoView.getCurrentPosition() >= ActivityVideoToAudio.this.editorVideoSliceSeekBar.getRightProgress()) {
                if (ActivityVideoToAudio.this.videoView.isPlaying()) {
                    ActivityVideoToAudio.this.videoView.pause();
                    ActivityVideoToAudio.this.aBoolean = Boolean.valueOf(false);
                    ActivityVideoToAudio.this.imageView.setBackgroundResource(R.drawable.play2);
                }
                ActivityVideoToAudio.this.editorVideoSliceSeekBar.setSliceBlocked(false);
                ActivityVideoToAudio.this.editorVideoSliceSeekBar.removeVideoStatusThumb();
                return;
            }
            postDelayed(this.observerWork, 50);
        }
    }

    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView( R.layout.video_to_audio_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);

        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Video To MP3");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (BOOLEAN || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(BOOLEAN);
            supportActionBar.setDisplayShowTitleEnabled(false);
            for (String add : this.j) {
                this.arrayList.add(add);
            }
            this.ffmpeg = FFmpeg.getInstance(this);
            e();
            this.bundle = getIntent().getExtras();
            this.anInt2 = 1;
            if (this.bundle != null) {
                this.string6 = this.bundle.getString("videopath");
                StringBuilder sb = new StringBuilder();
                sb.append("=== videopath");
                sb.append(this.string6);
                Log.e("", sb.toString());
                this.editorVideoPlayerState.setFilename(this.string6);
            }
            try {
                ThumbVideo(getApplicationContext(), this.string6);
            } catch (Exception unused) {
            }
            this.editorVideoSliceSeekBar = findViewById(R.id.seek_bar);
            this.videoView = findViewById(R.id.videoView);
            this.imageView = findViewById(R.id.btnPlayVideo);
            this.textViewLeft = findViewById(R.id.left_pointer);
            this.textViewRight = findViewById(R.id.right_pointer);
            this.relativeLayout = findViewById(R.id.rev_format);
            this.Filename = findViewById(R.id.Filename);
            this.Filename.setText(new File(this.string6).getName());
            this.relativeLayout2 = findViewById(R.id.imgbtn_bitrate);
            this.relativeLayout5 = findViewById(R.id.back_bitrate);
            this.relativeLayout3 = findViewById(R.id.imgbtn_Format);
            this.relativeLayout4 = findViewById(R.id.back_Format);
            this.listView = findViewById(R.id.hs_Bitrate);
            this.iv_aac = findViewById(R.id.iv_aac);
            this.iv_mp3 = findViewById(R.id.iv_mp3);
            this.linearLayout3 = findViewById(R.id.llBitrate);
            this.linearLayout2 = findViewById(R.id.llFormate);
            this.adapterFontList = new AdapterFontList(getApplicationContext(), this.arrayList);
            this.listView.setAdapter(this.adapterFontList);
            this.relativeLayout3.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View view) {
                    ActivityVideoToAudio.this.linearLayout3.setVisibility(8);
                    ActivityVideoToAudio.this.linearLayout2.setVisibility(0);
                    ActivityVideoToAudio.this.relativeLayout5.setVisibility(8);
                    ActivityVideoToAudio.this.relativeLayout4.setVisibility(0);
                }
            });
            this.relativeLayout2.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View view) {
                    ActivityVideoToAudio.this.linearLayout3.setVisibility(0);
                    ActivityVideoToAudio.this.linearLayout2.setVisibility(8);
                    ActivityVideoToAudio.this.relativeLayout5.setVisibility(0);
                    ActivityVideoToAudio.this.relativeLayout4.setVisibility(8);
                }
            });
            this.iv_aac.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View view) {
                    ActivityVideoToAudio.this.string2 = "AAC";
                    ActivityVideoToAudio.this.iv_aac.setBackgroundResource(R.drawable.bg_round_press);
                    ActivityVideoToAudio.this.iv_aac.setTextColor(Color.parseColor("#ffffff"));
                    ActivityVideoToAudio.this.iv_mp3.setBackgroundResource(R.drawable.bg_round);
                    ActivityVideoToAudio.this.iv_mp3.setTextColor(Color.parseColor("#0f9d58"));
                }
            });
            this.iv_mp3.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View view) {
                    ActivityVideoToAudio.this.string2 = "MP3";
                    ActivityVideoToAudio.this.iv_aac.setBackgroundResource(R.drawable.bg_round);
                    ActivityVideoToAudio.this.iv_aac.setTextColor(Color.parseColor("#0f9d58"));
                    ActivityVideoToAudio.this.iv_mp3.setBackgroundResource(R.drawable.bg_round_press);
                    ActivityVideoToAudio.this.iv_mp3.setTextColor(Color.parseColor("#ffffff"));
                }
            });
            this.listView.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                    FileUtils.Bitrate = i;
                    ActivityVideoToAudio.this.adapterFontList.notifyDataSetChanged();
                    if (i == 0) {
                        try {
                            ActivityVideoToAudio.this.string5 = "None";
                            ActivityVideoToAudio.this.string4 = "None";
                            ActivityVideoToAudio.this.string3 = "None";
                            ActivityVideoToAudio.this.txt_kbps.setText("None");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (i == 1) {
                        try {
                            ActivityVideoToAudio.this.txt_kbps.setText(" 40 (CBR)");
                            ActivityVideoToAudio.this.string5 = "-ab";
                            ActivityVideoToAudio.this.string4 = "40k";
                            ActivityVideoToAudio.this.string3 = "-vn";
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    } else if (i == 2) {
                        try {
                            ActivityVideoToAudio.this.txt_kbps.setText(" 48 (CBR)");
                            ActivityVideoToAudio.this.string5 = "-ab";
                            ActivityVideoToAudio.this.string4 = "48k";
                            ActivityVideoToAudio.this.string3 = "-vn";
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        }
                    } else if (i == 3) {
                        try {
                            ActivityVideoToAudio.this.txt_kbps.setText(" 64 (CBR)");
                            ActivityVideoToAudio.this.string5 = "-ab";
                            ActivityVideoToAudio.this.string4 = "64k";
                            ActivityVideoToAudio.this.string3 = "-vn";
                        } catch (Exception e4) {
                            e4.printStackTrace();
                        }
                    } else if (i == 4) {
                        try {
                            ActivityVideoToAudio.this.txt_kbps.setText(" 80 (CBR)");
                            ActivityVideoToAudio.this.string5 = "-ab";
                            ActivityVideoToAudio.this.string4 = "80k";
                            ActivityVideoToAudio.this.string3 = "-vn";
                        } catch (Exception e5) {
                            e5.printStackTrace();
                        }
                    } else if (i == 5) {
                        try {
                            ActivityVideoToAudio.this.txt_kbps.setText(" 96 (CBR)");
                            ActivityVideoToAudio.this.string5 = "-ab";
                            ActivityVideoToAudio.this.string4 = "96k";
                            ActivityVideoToAudio.this.string3 = "-vn";
                        } catch (Exception e6) {
                            e6.printStackTrace();
                        }
                    } else if (i == 6) {
                        try {
                            ActivityVideoToAudio.this.txt_kbps.setText(" 112 (CBR)");
                            ActivityVideoToAudio.this.string5 = "-ab";
                            ActivityVideoToAudio.this.string4 = "112k";
                            ActivityVideoToAudio.this.string3 = "-vn";
                        } catch (Exception e7) {
                            e7.printStackTrace();
                        }
                    } else if (i == 7) {
                        try {
                            ActivityVideoToAudio.this.txt_kbps.setText(" 128 (CBR)");
                            ActivityVideoToAudio.this.string5 = "-ab";
                            ActivityVideoToAudio.this.string4 = "128k";
                            ActivityVideoToAudio.this.string3 = "-vn";
                        } catch (Exception e8) {
                            e8.printStackTrace();
                        }
                    } else if (i == 8) {
                        try {
                            ActivityVideoToAudio.this.txt_kbps.setText(" 160 (CBR)");
                            ActivityVideoToAudio.this.string5 = "-ab";
                            ActivityVideoToAudio.this.string4 = "160k";
                            ActivityVideoToAudio.this.string3 = "-vn";
                        } catch (Exception e9) {
                            e9.printStackTrace();
                        }
                    } else if (i == 9) {
                        try {
                            ActivityVideoToAudio.this.txt_kbps.setText(" 192 (CBR)");
                            ActivityVideoToAudio.this.string5 = "-ab";
                            ActivityVideoToAudio.this.string4 = "192k";
                            ActivityVideoToAudio.this.string3 = "-vn";
                        } catch (Exception e10) {
                            e10.printStackTrace();
                        }
                    } else if (i == 10) {
                        try {
                            ActivityVideoToAudio.this.txt_kbps.setText(" 224 (CBR)");
                            ActivityVideoToAudio.this.string5 = "-ab";
                            ActivityVideoToAudio.this.string4 = "224k";
                            ActivityVideoToAudio.this.string3 = "-vn";
                        } catch (Exception e11) {
                            e11.printStackTrace();
                        }
                    } else if (i == 11) {
                        try {
                            ActivityVideoToAudio.this.txt_kbps.setText(" 256 (CBR)");
                            ActivityVideoToAudio.this.string5 = "-ab";
                            ActivityVideoToAudio.this.string4 = "256k";
                            ActivityVideoToAudio.this.string3 = "-vn";
                        } catch (Exception e12) {
                            e12.printStackTrace();
                        }
                    } else if (i == 12) {
                        try {
                            ActivityVideoToAudio.this.txt_kbps.setText(" 320 (CBR)");
                            ActivityVideoToAudio.this.string5 = "-ab";
                            ActivityVideoToAudio.this.string4 = "320k";
                            ActivityVideoToAudio.this.string3 = "-vn";
                        } catch (Exception e13) {
                            e13.printStackTrace();
                        }
                    } else if (i == 13) {
                        try {
                            ActivityVideoToAudio.this.txt_kbps.setText(" 245 (VBR)");
                            ActivityVideoToAudio.this.string5 = "-q:a";
                            ActivityVideoToAudio.this.string4 = "0";
                            ActivityVideoToAudio.this.string3 = "-vn";
                        } catch (Exception e14) {
                            e14.printStackTrace();
                        }
                    } else if (i == 14) {
                        try {
                            ActivityVideoToAudio.this.txt_kbps.setText(" 225 (VBR)");
                            ActivityVideoToAudio.this.string5 = "-q:a";
                            ActivityVideoToAudio.this.string4 = "1";
                            ActivityVideoToAudio.this.string3 = "-vn";
                        } catch (Exception e15) {
                            e15.printStackTrace();
                        }
                    } else if (i == 15) {
                        try {
                            ActivityVideoToAudio.this.txt_kbps.setText(" 190 (VBR)");
                            ActivityVideoToAudio.this.string5 = "-q:a";
                            ActivityVideoToAudio.this.string4 = "2";
                            ActivityVideoToAudio.this.string3 = "-vn";
                        } catch (Exception e16) {
                            e16.printStackTrace();
                        }
                    } else if (i == 16) {
                        try {
                            ActivityVideoToAudio.this.txt_kbps.setText(" 175 (VBR)");
                            ActivityVideoToAudio.this.string5 = "-q:a";
                            ActivityVideoToAudio.this.string4 = "3";
                            ActivityVideoToAudio.this.string3 = "-vn";
                        } catch (Exception e17) {
                            e17.printStackTrace();
                        }
                    } else if (i == 17) {
                        try {
                            ActivityVideoToAudio.this.txt_kbps.setText(" 165 (VBR)");
                            ActivityVideoToAudio.this.string5 = "-q:a";
                            ActivityVideoToAudio.this.string4 = "4";
                            ActivityVideoToAudio.this.string3 = "-vn";
                        } catch (Exception e18) {
                            e18.printStackTrace();
                        }
                    } else if (i == 18) {
                        try {
                            ActivityVideoToAudio.this.txt_kbps.setText(" 130 (VBR)");
                            ActivityVideoToAudio.this.string5 = "-q:a";
                            ActivityVideoToAudio.this.string4 = "5";
                            ActivityVideoToAudio.this.string3 = "-vn";
                        } catch (Exception e19) {
                            e19.printStackTrace();
                        }
                    } else if (i == 19) {
                        try {
                            ActivityVideoToAudio.this.txt_kbps.setText(" 115 (VBR)");
                            ActivityVideoToAudio.this.string5 = "-q:a";
                            ActivityVideoToAudio.this.string4 = "6";
                            ActivityVideoToAudio.this.string3 = "-vn";
                        } catch (Exception e20) {
                            e20.printStackTrace();
                        }
                    } else if (i == 20) {
                        try {
                            ActivityVideoToAudio.this.txt_kbps.setText(" 100 (VBR)");
                            ActivityVideoToAudio.this.string5 = "-q:a";
                            ActivityVideoToAudio.this.string4 = "7";
                            ActivityVideoToAudio.this.string3 = "-vn";
                        } catch (Exception e21) {
                            e21.printStackTrace();
                        }
                    } else if (i == 21) {
                        try {
                            ActivityVideoToAudio.this.txt_kbps.setText(" 85 (VBR)");
                            ActivityVideoToAudio.this.string5 = "-q:a";
                            ActivityVideoToAudio.this.string4 = "8";
                            ActivityVideoToAudio.this.string3 = "-vn";
                        } catch (Exception e22) {
                            e22.printStackTrace();
                        }
                    } else if (i == 22) {
                        try {
                            ActivityVideoToAudio.this.txt_kbps.setText(" 65 (VBR)");
                            ActivityVideoToAudio.this.string5 = "-q:a";
                            ActivityVideoToAudio.this.string4 = "9";
                            ActivityVideoToAudio.this.string3 = "-vn";
                        } catch (Exception e23) {
                            e23.printStackTrace();
                        }
                    }
                }
            });
            this.txt_selectformat = findViewById(R.id.txt_selectformat);
            this.txt_selectformat.setText("MP3");
            initVideoView();
            this.txt_kbps = findViewById(R.id.txt_kbps);
            this.txt_kbps.setText(" None ");
            this.string2 = "MP3";
            this.string5 = "None";
            this.linearLayout1 = findViewById(R.id.lnr_pop);
            this.relativeLayout.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View view) {
                }
            });
            this.linearLayout = findViewById(R.id.lnr_popkbps);
            this.linearLayout.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View view) {
                }
            });
            this.relativeLayout1 = findViewById(R.id.rev_bitrate);
            this.relativeLayout1.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View view) {
                }
            });
            this.interstitialAd = new InterstitialAd(this);
            this.interstitialAd.setAdUnitId(getString(R.string.interstitial_id_admob));
            this.interstitialAd.setAdListener(new AdListener() {
                @Override public void onAdClosed() {
                    ActivityVideoToAudio.this.c();
                }
            });
            a();
            return;
        }
        throw new AssertionError();
    }

    private void a() {
        if (!this.interstitialAd.isLoading() && !this.interstitialAd.isLoaded()) {
            this.interstitialAd.loadAd(new Builder().build());
        }
    }


    public void b() {
        if (this.interstitialAd == null || !this.interstitialAd.isLoaded()) {
            c();
        } else {
            this.interstitialAd.show();
        }
    }


    public void c() {
        Intent intent = new Intent(this, EditorAudioPlayer.class);
        Bundle bundle = new Bundle();
        bundle.putString("song", this.string1);
        bundle.putBoolean("isfrom", BOOLEAN);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private void d() {
        String valueOf = String.valueOf(this.anInt / 1000);
        String valueOf2 = String.valueOf((this.anInt1 / 1000) - (this.anInt / 1000));
        String[] strArr = !this.string5.equals("None") ? new String[]{"-y", "-i", this.string6, "-vn", "-acodec", "copy", this.string5, this.string4, this.string3, "-strict", "experimental", "-ss", valueOf, "-t", valueOf2, this.string1} : this.string2.equals("MP3") ? new String[]{"-y", "-i", this.string6, "-vn", "-acodec", "copy", "-strict", "experimental", "-ss", valueOf, "-t", valueOf2, this.string1} : this.string2.equals("AAC") ? new String[]{"-y", "-ss", valueOf, "-t", valueOf2, "-i", this.string6, "-vn", "-acodec", "copy", "-strict", "experimental", this.string1} : null;
        for (int i2 = 0; i2 < strArr.length; i2++) {
            StringBuilder sb = new StringBuilder();
            sb.append(i2);
            sb.append(strArr[i2]);
            Log.e("", sb.toString());
        }
        a(strArr, this.string1);
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
    }




    private void a(String[] strArr, final String str) {
        try {
            this.aNull = new ProgressDialog(this);
            this.aNull.setProgressStyle(1);
            this.aNull.setIndeterminate(BOOLEAN);
            this.aNull.setMessage("Please wait...");
            this.aNull.setCancelable(false);
            this.aNull.show();
            this.ffmpeg.execute(strArr, new ExecuteBinaryResponseHandler() {
                @Override public void onStart() {
                }

                @Override public void onFailure(String str) {
                    Log.d("ffmpegfailure", str);
                    try {
                        new File(str).delete();
                        ActivityVideoToAudio.this.deleteFromGallery(str);
                        Toast.makeText(ActivityVideoToAudio.this, "Error Creating Video", 0).show();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }

                @Override public void onSuccess(String str) {
                    ActivityVideoToAudio.this.aNull.dismiss();
                    if (ActivityVideoToAudio.this.string2.equals("MP3")) {
                        StringBuilder sb = new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().getPath()));
                        sb.append("/VEditor/VideoToMP3/");
                        File file = new File(sb.toString());
                        String substring = ActivityVideoToAudio.this.string1.substring(ActivityVideoToAudio.this.string1.lastIndexOf("/") + 1);
                        String substring2 = substring.substring(0, substring.lastIndexOf("."));
                        if (file.exists()) {
                            File file2 = new File(file, substring);
                            StringBuilder sb2 = new StringBuilder(substring2);
                            sb2.append(".mp3");
                            File file3 = new File(file, sb2.toString());
                            if (file2.exists()) {
                                file2.renameTo(file3);
                            }
                            ActivityVideoToAudio.this.string1 = file3.getPath();
                        }
                    }
                    Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                    intent.setData(Uri.fromFile(new File(ActivityVideoToAudio.this.string1)));
                    ActivityVideoToAudio.this.sendBroadcast(intent);
                    ActivityVideoToAudio.this.scanMedia(ActivityVideoToAudio.this.string1);
                    Intent intent2 = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                    intent2.setData(Uri.fromFile(new File(ActivityVideoToAudio.this.string1)));
                    ActivityVideoToAudio.this.sendBroadcast(intent2);
                    ActivityVideoToAudio.this.b();
                }

                @Override public void onProgress(String str) {
                    Log.d("ffmpegResponse", str);
                }

                @Override public void onFinish() {
                    ActivityVideoToAudio.this.aNull.dismiss();
                    ActivityVideoToAudio.this.refreshGallery(str);
                }
            });
            getWindow().clearFlags(16);
        } catch (FFmpegCommandAlreadyRunningException unused) {
        }
    }

    public static String formatTimeUnit(long j2) throws ParseException {
        return String.format("%02d:%02d", Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(j2)), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j2) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j2))));
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public void initVideoView() {
        this.videoView.setVideoPath(this.string6);
        try {
            this.videoView.seekTo(200);
        } catch (Exception unused) {
        }
        this.videoView.setOnErrorListener(new OnErrorListener() {
            public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                Toast.makeText(ActivityVideoToAudio.this.getApplicationContext(), "Video Player Not Supproting", 0).show();
                return ActivityVideoToAudio.BOOLEAN;
            }
        });
        this.videoView.setOnCompletionListener(new OnCompletionListener() {
            public void onCompletion(MediaPlayer mediaPlayer) {
                ActivityVideoToAudio.this.videoView.pause();
                ActivityVideoToAudio.this.imageView.setBackgroundResource(R.drawable.play2);
                ActivityVideoToAudio.this.aBoolean = Boolean.valueOf(false);
            }
        });
        this.videoView.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (ActivityVideoToAudio.this.aBoolean.booleanValue()) {
                    ActivityVideoToAudio.this.videoView.pause();
                    ActivityVideoToAudio.this.aBoolean = Boolean.valueOf(false);
                    ActivityVideoToAudio.this.imageView.setBackgroundResource(R.drawable.play2);
                }
                return ActivityVideoToAudio.BOOLEAN;
            }
        });
        this.videoView.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                ActivityVideoToAudio.this.editorVideoSliceSeekBar.setSeekBarChangeListener(new SeekBarChangeListener() {
                    public void SeekBarValueChanged(int i, int i2) {
                        if (ActivityVideoToAudio.this.editorVideoSliceSeekBar.getSelectedThumb() == 1) {
                            ActivityVideoToAudio.this.videoView.seekTo(ActivityVideoToAudio.this.editorVideoSliceSeekBar.getLeftProgress());
                        }
                        try {
                            ActivityVideoToAudio.this.textViewLeft.setText(ActivityVideoToAudio.formatTimeUnit(i));
                            ActivityVideoToAudio.this.textViewRight.setText(ActivityVideoToAudio.formatTimeUnit(i2));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        ActivityVideoToAudio.this.string7 = ActivityVideoToAudio.getTimeForTrackFormat(i, ActivityVideoToAudio.BOOLEAN);
                        ActivityVideoToAudio.this.editorVideoPlayerState.setStart(i);
                        ActivityVideoToAudio.this.string = ActivityVideoToAudio.getTimeForTrackFormat(i2, ActivityVideoToAudio.BOOLEAN);
                        ActivityVideoToAudio.this.editorVideoPlayerState.setStop(i2);
                        ActivityVideoToAudio.this.anInt = i;
                        ActivityVideoToAudio.this.anInt1 = i2;
                    }
                });
                ActivityVideoToAudio.this.string = ActivityVideoToAudio.getTimeForTrackFormat(mediaPlayer.getDuration(), ActivityVideoToAudio.BOOLEAN);
                ActivityVideoToAudio.this.editorVideoSliceSeekBar.setMaxValue(mediaPlayer.getDuration());
                ActivityVideoToAudio.this.editorVideoSliceSeekBar.setLeftProgress(0);
                ActivityVideoToAudio.this.editorVideoSliceSeekBar.setRightProgress(mediaPlayer.getDuration());
                ActivityVideoToAudio.this.editorVideoSliceSeekBar.setProgressMinDiff(0);
                ActivityVideoToAudio.this.videoView.seekTo(200);
                ActivityVideoToAudio.this.imageView.setOnClickListener(new OnClickListener() {
                    @Override public void onClick(View view) {
                        if (ActivityVideoToAudio.this.aBoolean.booleanValue()) {
                            ActivityVideoToAudio.this.imageView.setBackgroundResource(R.drawable.play2);
                            ActivityVideoToAudio.this.aBoolean = Boolean.valueOf(false);
                        } else {
                            ActivityVideoToAudio.this.imageView.setBackgroundResource(R.drawable.pause2);
                            ActivityVideoToAudio.this.aBoolean = Boolean.valueOf(ActivityVideoToAudio.BOOLEAN);
                        }
                        ActivityVideoToAudio.this.performVideoViewClick();
                    }
                });
            }
        });
        this.string = getTimeForTrackFormat(this.videoView.getDuration(), BOOLEAN);
    }


    @Override public void onResume() {
        super.onResume();
        this.string2 = "MP3";
        this.string5 = "None";
    }

    public void ThumbVideo(Context context, String str) {
        Uri uri = Media.EXTERNAL_CONTENT_URI;
        String[] strArr = {"_data", "_id"};
        StringBuilder sb = new StringBuilder();
        sb.append("%");
        sb.append(str);
        sb.append("%");
        Cursor managedQuery = managedQuery(uri, strArr, "_data  like ?", new String[]{sb.toString()}, " _id DESC");
        int count = managedQuery.getCount();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("count");
        sb2.append(count);
        Log.e("", sb2.toString());
        if (count > 0) {
            try {
                managedQuery.moveToFirst();
                for (int i2 = 0; i2 < count; i2++) {
                    try {
                        Uri.withAppendedPath(Media.EXTERNAL_CONTENT_URI, getLong(managedQuery));
                        managedQuery.getString(managedQuery.getColumnIndex("_data"));
                        Bitmap thumbnail = Thumbnails.getThumbnail(getContentResolver(), Long.valueOf(managedQuery.getLong(managedQuery.getColumnIndexOrThrow("_id"))).longValue(), 1, null);
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("Bitmap");
                        sb3.append(thumbnail);
                        Log.e("", sb3.toString());
                        managedQuery.moveToNext();
                    } catch (IllegalArgumentException e2) {
                        e2.printStackTrace();
                    }
                }
            } catch (IllegalArgumentException e3) {
                e3.printStackTrace();
            }
        }
    }

    public static String getLong(Cursor cursor) {
        return String.valueOf(cursor.getLong(cursor.getColumnIndexOrThrow("_id")));
    }

    public static String getTimeForTrackFormat(int i2, boolean z2) {
        int i3 = i2 / 3600000;
        int i4 = i2 / 60000;
        int i5 = (i2 - ((i4 * 60) * 1000)) / 1000;
        StringBuilder sb = new StringBuilder((!z2 || i3 >= 10) ? "" : "0");
        sb.append(i3);
        sb.append(":");
        StringBuilder sb2 = new StringBuilder(sb.toString());
        sb2.append((!z2 || i4 >= 10) ? "" : "0");
        StringBuilder sb3 = new StringBuilder(sb2.toString());
        sb3.append(i4 % 60);
        sb3.append(":");
        String sb4 = sb3.toString();
        if (i5 < 10) {
            StringBuilder sb5 = new StringBuilder(sb4);
            sb5.append("0");
            sb5.append(i5);
            return sb5.toString();
        }
        StringBuilder sb6 = new StringBuilder(sb4);
        sb6.append(i5);
        return sb6.toString();
    }

    public void performVideoViewClick() {
        if (this.videoView.isPlaying()) {
            this.videoView.pause();
            this.editorVideoSliceSeekBar.setSliceBlocked(false);
            this.editorVideoSliceSeekBar.removeVideoStatusThumb();
            return;
        }
        this.videoView.seekTo(this.editorVideoSliceSeekBar.getLeftProgress());
        this.videoView.start();
        this.editorVideoSliceSeekBar.videoPlayingProgress(this.editorVideoSliceSeekBar.getLeftProgress());
        this.videoStateObserver.startVideoProgressObserving();
    }

    public void scanMedia(String str) {
        String substring = str.substring(str.lastIndexOf("/") + 1);
        String substring2 = substring.substring(0, substring.lastIndexOf("."));
        ContentValues contentValues = new ContentValues();
        contentValues.put("_data", str);
        contentValues.put("title", substring2);
        contentValues.put("_size", Integer.valueOf(str.length()));
        contentValues.put("mime_type", "audio/mp3");
        contentValues.put("artist", getResources().getString(R.string.app_name));
        contentValues.put("is_ringtone", Boolean.valueOf(BOOLEAN));
        contentValues.put("is_notification", Boolean.valueOf(false));
        contentValues.put("is_alarm", Boolean.valueOf(false));
        contentValues.put("is_music", Boolean.valueOf(false));
        Uri contentUriForPath = Audio.Media.getContentUriForPath(str);
        StringBuilder sb = new StringBuilder();
        sb.append("=====Enter ====");
        sb.append(contentUriForPath);
        Log.e("", sb.toString());
        ContentResolver contentResolver = getContentResolver();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("_data=\"");
        sb2.append(str);
        sb2.append("\"");
        contentResolver.delete(contentUriForPath, sb2.toString(), null);
        getApplicationContext().getContentResolver().insert(contentUriForPath, contentValues);
    }

    @Override public void onActivityResult(int i2, int i3, Intent intent) {
        super.onActivityResult(i2, i3, intent);
        if (intent != null) {
            Uri data = intent.getData();
            StringBuilder sb = new StringBuilder();
            sb.append("File Path :");
            sb.append(data);
            Log.e("", sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Final Image Path :");
            sb2.append(getRealPathFromURI(data));
            Log.e("", sb2.toString());
            String realPathFromURI = getRealPathFromURI(data);
            Meta_File_path = realPathFromURI;
            this.imageView1.setImageBitmap(rotateBitmapOrientation(realPathFromURI));
        }
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor managedQuery = managedQuery(uri, new String[]{"_data"}, null, null, null);
        int columnIndexOrThrow = managedQuery.getColumnIndexOrThrow("_data");
        managedQuery.moveToFirst();
        return managedQuery.getString(columnIndexOrThrow);
    }

    public Bitmap rotateBitmapOrientation(String str) {
        ExifInterface exifInterface;
        Options options = new Options();
        int i2 = 1;
        options.inJustDecodeBounds = BOOLEAN;
        BitmapFactory.decodeFile(str, options);
        Bitmap decodeFile = BitmapFactory.decodeFile(str, new Options());
        try {
            exifInterface = new ExifInterface(str);
        } catch (IOException e2) {
            e2.printStackTrace();
            exifInterface = null;
        }
        String attribute = exifInterface.getAttribute("Orientation");
        if (attribute != null) {
            i2 = Integer.parseInt(attribute);
        }
        int i3 = 0;
        if (i2 == 6) {
            i3 = 90;
        }
        if (i2 == 3) {
            i3 = 180;
        }
        if (i2 == 8) {
            i3 = 270;
        }
        Matrix matrix = new Matrix();
        matrix.setRotate((float) i3, ((float) decodeFile.getWidth()) / 2.0f, ((float) decodeFile.getHeight()) / 2.0f);
        return Bitmap.createBitmap(decodeFile, 0, 0, options.outWidth, options.outHeight, matrix, BOOLEAN);
    }

    private void e() {
        try {
            this.ffmpeg.loadBinary(new LoadBinaryResponseHandler() {
                @Override public void onFailure() {
                    ActivityVideoToAudio.this.f();
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
                ActivityVideoToAudio.this.finish();
            }
        }).create().show();
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

    @Override public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, ActivityVideoMusicList.class);
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
            StringBuilder sb = new StringBuilder();
            sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
            sb.append("/");
            sb.append(getResources().getString(R.string.MainFolderName));
            sb.append("/");
            sb.append(getResources().getString(R.string.VideoToMP3));
            sb.append("/");
            File file = new File(sb.toString());
            if (!file.exists()) {
                file.mkdirs();
            }
            new SimpleDateFormat("_HHmmss", Locale.US).format(new Date());
            if (this.videoView.isPlaying()) {
                this.videoView.pause();
                this.imageView.setBackgroundResource(R.drawable.play2);
                this.aBoolean = Boolean.valueOf(false);
            }
            try {
                if (this.string2.equals("MP3")) {
                    this.string1 = FileUtils.getFileName(this, this.string6);
                } else if (this.string2.equals("AAC")) {
                    String substring = this.string6.substring(this.string6.lastIndexOf("/") + 1);
                    String substring2 = substring.substring(0, substring.lastIndexOf("."));
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
                    sb2.append("/");
                    sb2.append(getResources().getString(R.string.MainFolderName));
                    sb2.append("/");
                    sb2.append(getResources().getString(R.string.VideoToMP3));
                    sb2.append("/");
                    sb2.append(substring2);
                    sb2.append(System.currentTimeMillis());
                    sb2.append(".aac");
                    this.string1 = sb2.toString();
                }
            } catch (Exception unused) {
            }
            d();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
