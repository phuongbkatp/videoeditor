package com.video_editor.pro.ActivityVideoMirror;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.MediaStore.Images.Media;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationManagerCompat;

import com.video_editor.pro.ActivityVideoList.ActivityVideoList;
import com.video_editor.pro.R;
import com.video_editor.pro.UtilsAndAdapters.EditorRangePlaySeekBar;
import com.video_editor.pro.UtilsAndAdapters.EditorRangeSeekBar;
import com.video_editor.pro.UtilsAndAdapters.EditorRangeSeekBar.OnRangeSeekBarChangeListener;
import com.video_editor.pro.UtilsAndAdapters.EditorVideoPlayer;
import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@SuppressLint({"WrongConstant", "ResourceType"})
public class ActivityVideoMirror extends AppCompatActivity {
    public static String videoPath = null;
    static final boolean BOOLEAN = true;

    public VideoView videoView;
    private String string;

    public String string1;
    private InterstitialAd interstitialAd;
    public int MP_DURATION;
    int anInt = 4;
    int anInt1 = 0;
    boolean aBoolean = BOOLEAN;
    ViewGroup viewGroup;
    int anInt2;
    int anInt3;
    public FFmpeg ffmpeg;
    int anInt4;
    int anInt5;
    int anInt6;
    public boolean isInFront = BOOLEAN;
    EditorRangeSeekBar<Integer> editorRangeSeekBar;
    EditorRangePlaySeekBar<Integer> editorRangePlaySeekBar;
    TextView textView;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    WakeLock wakeLock;
    public ProgressDialog progressDialog;
    RelativeLayout relativeLayout;
    RelativeLayout relativeLayout1;
    RelativeLayout relativeLayout2;
    RelativeLayout relativeLayout3;
    public int totalVideoDuration = 0;
    RelativeLayout relativeLayout4;
    RelativeLayout relativeLayout5;
    public ImageView videoPlayBtn;
    RelativeLayout relativeLayout6;
    RelativeLayout relativeLayout7;
    private StateObserver stateObserver = new StateObserver();

    @SuppressLint({"HandlerLeak"})
    public class StateObserver extends Handler {
        private boolean b = false;
        private Runnable runnable = new Runnable() {
            public void run() {
                StateObserver.this.a();
            }
        };

        public StateObserver() {
        }


        public void a() {
            if (!this.b) {
                this.b = ActivityVideoMirror.BOOLEAN;
                sendEmptyMessage(0);
            }
        }

        @Override public void handleMessage(Message message) {
            this.b = false;
            ActivityVideoMirror.this.editorRangePlaySeekBar.setSelectedMaxValue(Integer.valueOf(ActivityVideoMirror.this.videoView.getCurrentPosition()));
            if (!ActivityVideoMirror.this.videoView.isPlaying() || ActivityVideoMirror.this.videoView.getCurrentPosition() >= ActivityVideoMirror.this.editorRangeSeekBar.getSelectedMaxValue().intValue()) {
                if (ActivityVideoMirror.this.videoView.isPlaying()) {
                    ActivityVideoMirror.this.videoView.pause();
                    ActivityVideoMirror.this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
                    ActivityVideoMirror.this.videoView.seekTo(ActivityVideoMirror.this.editorRangeSeekBar.getSelectedMinValue().intValue());
                    ActivityVideoMirror.this.editorRangePlaySeekBar.setSelectedMinValue(ActivityVideoMirror.this.editorRangeSeekBar.getSelectedMinValue());
                    ActivityVideoMirror.this.editorRangePlaySeekBar.setVisibility(4);
                }
                if (!ActivityVideoMirror.this.videoView.isPlaying()) {
                    ActivityVideoMirror.this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
                    ActivityVideoMirror.this.editorRangePlaySeekBar.setVisibility(4);
                    return;
                }
                return;
            }
            ActivityVideoMirror.this.editorRangePlaySeekBar.setVisibility(0);
            postDelayed(this.runnable, 50);
        }
    }


    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView( R.layout.activity_video_mirror);
        Toolbar toolbar = findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Video Mirror");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (BOOLEAN || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(BOOLEAN);
            supportActionBar.setDisplayShowTitleEnabled(false);
            this.ffmpeg = FFmpeg.getInstance(this);
            f();
            this.aBoolean = BOOLEAN;
            copyCreate();
            this.interstitialAd = new InterstitialAd(this);
            this.interstitialAd.setAdUnitId(getString(R.string.interstitial_id_admob));
            this.interstitialAd.setAdListener(new AdListener() {
                @Override public void onAdClosed() {
                    ActivityVideoMirror.this.c();
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
        Intent intent = new Intent(getApplicationContext(), EditorVideoPlayer.class);
        intent.setFlags(67108864);
        intent.putExtra("song", this.string1);
        startActivity(intent);
        finish();
    }

    @SuppressLint("InvalidWakeLockTag")
    public void copyCreate() {
        this.isInFront = BOOLEAN;
        this.anInt = e() / 100;
        this.totalVideoDuration = 0;
        this.wakeLock = ((PowerManager) getSystemService(Context.POWER_SERVICE)).newWakeLock(6, "VideoMerge");
        if (!this.wakeLock.isHeld()) {
            this.wakeLock.acquire();
        }
        this.string = getIntent().getStringExtra("videouri");
        videoPath = this.string;
        this.textView3 = findViewById(R.id.Filename);
        this.videoView = findViewById(R.id.addcutsvideoview);
        this.videoPlayBtn = findViewById(R.id.videoplaybtn);
        this.textView = findViewById(R.id.left_pointer);
        this.textView1 = findViewById(R.id.mid_pointer);
        this.textView2 = findViewById(R.id.right_pointer);
        this.relativeLayout = findViewById(R.id.btn_rightmirror);
        this.relativeLayout1 = findViewById(R.id.back_rightmirror);
        this.relativeLayout2 = findViewById(R.id.btn_leftmirror);
        this.relativeLayout3 = findViewById(R.id.back_leftmirror);
        this.relativeLayout4 = findViewById(R.id.btn_TopMirror);
        this.relativeLayout5 = findViewById(R.id.back_TopMirror);
        this.relativeLayout7 = findViewById(R.id.btn_BottumMirror);
        this.relativeLayout6 = findViewById(R.id.back_BottumMirror);
        this.textView3.setText(new File(videoPath).getName());
        runOnUiThread(new Runnable() {
            public void run() {
                ActivityVideoMirror.this.progressDialog = ProgressDialog.show(ActivityVideoMirror.this, "", "Loading...", ActivityVideoMirror.BOOLEAN);
            }
        });
        VideoSeekBar();
        this.relativeLayout.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                ActivityVideoMirror.this.relativeLayout1.setVisibility(0);
                ActivityVideoMirror.this.relativeLayout3.setVisibility(8);
                ActivityVideoMirror.this.relativeLayout5.setVisibility(8);
                ActivityVideoMirror.this.relativeLayout6.setVisibility(8);
                ActivityVideoMirror.this.anInt1 = 1;
            }
        });
        this.relativeLayout2.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                ActivityVideoMirror.this.relativeLayout1.setVisibility(8);
                ActivityVideoMirror.this.relativeLayout3.setVisibility(0);
                ActivityVideoMirror.this.relativeLayout5.setVisibility(8);
                ActivityVideoMirror.this.relativeLayout6.setVisibility(8);
                ActivityVideoMirror.this.anInt1 = 2;
            }
        });
        this.relativeLayout4.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                ActivityVideoMirror.this.relativeLayout1.setVisibility(8);
                ActivityVideoMirror.this.relativeLayout3.setVisibility(8);
                ActivityVideoMirror.this.relativeLayout5.setVisibility(0);
                ActivityVideoMirror.this.relativeLayout6.setVisibility(8);
                ActivityVideoMirror.this.anInt1 = 3;
            }
        });
        this.relativeLayout7.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                ActivityVideoMirror.this.relativeLayout1.setVisibility(8);
                ActivityVideoMirror.this.relativeLayout3.setVisibility(8);
                ActivityVideoMirror.this.relativeLayout5.setVisibility(8);
                ActivityVideoMirror.this.relativeLayout6.setVisibility(0);
                ActivityVideoMirror.this.anInt1 = 4;
            }
        });
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
                        new File(ActivityVideoMirror.this.string1).delete();
                        ActivityVideoMirror.this.deleteFromGallery(str);
                        Toast.makeText(ActivityVideoMirror.this, "Error Creating Video", Toast.LENGTH_LONG).show();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }

                @Override public void onSuccess(String str) {
                    progressDialog.dismiss();
                    Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                    intent.setData(Uri.fromFile(new File(ActivityVideoMirror.this.string1)));
                    ActivityVideoMirror.this.sendBroadcast(intent);
                    ActivityVideoMirror.this.b();
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
                    ActivityVideoMirror.this.refreshGallery(str);
                }
            });
            getWindow().clearFlags(16);
        } catch (FFmpegCommandAlreadyRunningException unused) {
        }
    }

    public String[] cmdleft(String str, String str2) {
        boolean aa = aa(str, 1280, 720);
        int aaa = aaa(str);
        String str3 = "scale=iw/2:-1";
        if (aa) {
            str3 = "scale=640:-1";
        }
        String str4 = "scale=-1:ih/2";
        if (aa) {
            str4 = "scale=-1:640";
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add("-i");
        arrayList.add(str);
        arrayList.add("-filter_complex");
        if (aaa == 90) {
            StringBuilder sb = new StringBuilder();
            sb.append("transpose=1,");
            sb.append(str4);
            sb.append(",split[tmp],pad=2*iw:0[left]; [tmp]hflip[right]; [left][right] overlay=W/2:0");
            arrayList.add(sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append("");
            sb2.append(aaa);
            Log.e("rotate", sb2.toString());
        } else if (aaa == 180) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("transpose=1:landscape,rotate=PI,");
            sb3.append(str3);
            sb3.append(",split[tmp],pad=2*iw:0[left]; [tmp]hflip[right]; [left][right] overlay=W/2:0");
            arrayList.add(sb3.toString());
            StringBuilder sb4 = new StringBuilder();
            sb4.append("");
            sb4.append(aaa);
            Log.e("rotate", sb4.toString());
        } else if (aaa != 270) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append(str3);
            sb5.append(",split[tmp],pad=2*iw:0[left]; [tmp]hflip[right]; [left][right] overlay=W/2:0");
            arrayList.add(sb5.toString());
            StringBuilder sb6 = new StringBuilder();
            sb6.append("");
            sb6.append(aaa);
            Log.e("rotate", sb6.toString());
        } else {
            StringBuilder sb7 = new StringBuilder();
            sb7.append("transpose=2,");
            sb7.append(str4);
            sb7.append(",split[tmp],pad=2*iw:0[left]; [tmp]hflip[right]; [left][right] overlay=W/2:0");
            arrayList.add(sb7.toString());
            StringBuilder sb8 = new StringBuilder();
            sb8.append("");
            sb8.append(aaa);
            Log.e("rotate", sb8.toString());
        }
        arrayList.add("-vcodec");
        arrayList.add("libx264");
        arrayList.add("-strict");
        arrayList.add("experimental");
        arrayList.add("-threads");
        arrayList.add("5");
        arrayList.add("-preset");
        arrayList.add("ultrafast");
        arrayList.add(str2);
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public String[] cmdtop(String str, String str2) {
        boolean aa = aa(str, 1280, 720);
        int aaa = aaa(str);
        String str3 = "scale=-1:ih/2";
        if (aa) {
            str3 = "scale=640:-1";
        }
        String str4 = "scale=-1:ih/2";
        if (aa) {
            str4 = "scale=-1:640";
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add("-i");
        arrayList.add(str);
        arrayList.add("-filter_complex");
        if (aaa == 90) {
            StringBuilder sb = new StringBuilder();
            sb.append("transpose=1,");
            sb.append(str4);
            sb.append(",split[tmp],pad=0:2*ih[top]; [tmp]vflip[bottom]; [top][bottom] overlay=0:H/2");
            arrayList.add(sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append("");
            sb2.append(aaa);
            Log.e("rotate", sb2.toString());
        } else if (aaa == 180) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("transpose=1:landscape,rotate=PI,");
            sb3.append(str3);
            sb3.append(",split[tmp],pad=0:2*ih[top]; [tmp]vflip[bottom]; [top][bottom] overlay=0:H/2");
            arrayList.add(sb3.toString());
            StringBuilder sb4 = new StringBuilder();
            sb4.append("");
            sb4.append(aaa);
            Log.e("rotate", sb4.toString());
        } else if (aaa != 270) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append(str3);
            sb5.append(",split[tmp],pad=0:2*ih[top]; [tmp]vflip[bottom]; [top][bottom] overlay=0:H/2");
            arrayList.add(sb5.toString());
            StringBuilder sb6 = new StringBuilder();
            sb6.append("");
            sb6.append(aaa);
            Log.e("rotate", sb6.toString());
        } else {
            StringBuilder sb7 = new StringBuilder();
            sb7.append("transpose=2,");
            sb7.append(str4);
            sb7.append(",split[tmp],pad=0:2*ih[top]; [tmp]vflip[bottom]; [top][bottom] overlay=0:H/2");
            arrayList.add(sb7.toString());
            StringBuilder sb8 = new StringBuilder();
            sb8.append("");
            sb8.append(aaa);
            Log.e("rotate", sb8.toString());
        }
        arrayList.add("-vcodec");
        arrayList.add("libx264");
        arrayList.add("-strict");
        arrayList.add("experimental");
        arrayList.add("-threads");
        arrayList.add("5");
        arrayList.add("-preset");
        arrayList.add("ultrafast");
        arrayList.add(str2);
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public String[] cmddown(String str, String str2) {
        boolean aa = aa(str, 1280, 720);
        String str3 = "scale=-1:ih/2";
        if (aa) {
            str3 = "scale=640:-1";
        }
        String str4 = "scale=-1:ih/2";
        if (aa) {
            str4 = "scale=-1:640";
        }
        int aaa = aaa(str);
        ArrayList arrayList = new ArrayList();
        arrayList.add("-i");
        arrayList.add(str);
        arrayList.add("-filter_complex");
        if (aaa == 90) {
            StringBuilder sb = new StringBuilder();
            sb.append("transpose=1,");
            sb.append(str4);
            sb.append(",split[bottom],vflip,pad=0:2*ih[top]; [top][bottom] overlay=0:H/2");
            arrayList.add(sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append("");
            sb2.append(aaa);
            Log.e("rotate", sb2.toString());
        } else if (aaa == 180) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("transpose=1:landscape,rotate=PI,");
            sb3.append(str3);
            sb3.append(",split[bottom],vflip,pad=0:2*ih[top]; [top][bottom] overlay=0:H/2");
            arrayList.add(sb3.toString());
            StringBuilder sb4 = new StringBuilder();
            sb4.append("");
            sb4.append(aaa);
            Log.e("rotate", sb4.toString());
        } else if (aaa != 270) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append(str3);
            sb5.append(",split[bottom],vflip,pad=0:2*ih[top]; [top][bottom] overlay=0:H/2");
            arrayList.add(sb5.toString());
            StringBuilder sb6 = new StringBuilder();
            sb6.append("");
            sb6.append(aaa);
            Log.e("rotate", sb6.toString());
        } else {
            StringBuilder sb7 = new StringBuilder();
            sb7.append("transpose=2,");
            sb7.append(str4);
            sb7.append(",split[bottom],vflip,pad=0:2*ih[top]; [top][bottom] overlay=0:H/2");
            arrayList.add(sb7.toString());
            StringBuilder sb8 = new StringBuilder();
            sb8.append("");
            sb8.append(aaa);
            Log.e("rotate", sb8.toString());
        }
        arrayList.add("-vcodec");
        arrayList.add("libx264");
        arrayList.add("-strict");
        arrayList.add("experimental");
        arrayList.add("-threads");
        arrayList.add("5");
        arrayList.add("-preset");
        arrayList.add("ultrafast");
        arrayList.add(str2);
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    public static int aaa(String str) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(str);
        String extractMetadata = mediaMetadataRetriever.extractMetadata(24);
        if (extractMetadata == null) {
            return 0;
        }
        return Integer.valueOf(extractMetadata).intValue();
    }

    public static boolean aa(String str, int i2, int i3) {
        Point bb = bb(new File(str));
        if (bb == null || ((bb.x <= i2 || bb.y <= i3) && (bb.y <= i2 || bb.x <= i3))) {
            return false;
        }
        return BOOLEAN;
    }

    public static Point bb(File file) {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(file.getAbsolutePath());
        String extractMetadata = mediaMetadataRetriever.extractMetadata(19);
        String extractMetadata2 = mediaMetadataRetriever.extractMetadata(18);
        if (extractMetadata == null || extractMetadata2 == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(extractMetadata);
        sb.append(" ");
        sb.append(extractMetadata2);
        Log.e("size", sb.toString());
        return new Point(Integer.parseInt(extractMetadata), Integer.parseInt(extractMetadata2));
    }

    public void VideoSeekBar() {
        this.videoView.setVideoURI(Uri.parse(videoPath));
        this.videoView.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                if (ActivityVideoMirror.this.aBoolean) {
                    ActivityVideoMirror.this.anInt2 = 0;
                    ActivityVideoMirror.this.anInt3 = mediaPlayer.getDuration();
                    ActivityVideoMirror.this.MP_DURATION = mediaPlayer.getDuration();
                    ActivityVideoMirror.this.seekLayout(0, ActivityVideoMirror.this.MP_DURATION);
                    ActivityVideoMirror.this.videoView.start();
                    ActivityVideoMirror.this.videoView.pause();
                    ActivityVideoMirror.this.videoView.seekTo(300);
                    return;
                }
                ActivityVideoMirror.this.seekLayout(ActivityVideoMirror.this.anInt2, ActivityVideoMirror.this.anInt3);
                ActivityVideoMirror.this.videoView.start();
                ActivityVideoMirror.this.videoView.pause();
                ActivityVideoMirror.this.videoView.seekTo(ActivityVideoMirror.this.anInt2);
            }
        });
        this.videoView.setOnErrorListener(new OnErrorListener() {
            public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                ActivityVideoMirror.this.progressDialog.dismiss();
                return false;
            }
        });
        this.videoPlayBtn.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                ActivityVideoMirror.this.d();
            }
        });
    }


    public void d() {
        if (this.videoView.isPlaying()) {
            this.videoView.pause();
            this.videoView.seekTo(this.editorRangeSeekBar.getSelectedMinValue().intValue());
            this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
            this.editorRangePlaySeekBar.setVisibility(4);
            return;
        }
        this.videoView.seekTo(this.editorRangeSeekBar.getSelectedMinValue().intValue());
        this.videoView.start();
        this.editorRangePlaySeekBar.setSelectedMaxValue(Integer.valueOf(this.videoView.getCurrentPosition()));
        this.stateObserver.a();
        this.videoPlayBtn.setBackgroundResource(R.drawable.pause2);
        this.editorRangePlaySeekBar.setVisibility(0);
    }


    @Override public void onStop() {
        super.onStop();
    }


    @Override public void onDestroy() {
        this.totalVideoDuration = 0;
        if (this.wakeLock.isHeld()) {
            this.wakeLock.release();
        }
        super.onDestroy();
    }

    public void seekLayout(int i2, int i3) {
        TextView textView = this.textView;
        StringBuilder sb = new StringBuilder();
        sb.append(getTimeForTrackFormat(i2));
        sb.append("");
        textView.setText(sb.toString());
        TextView textView2 = this.textView2;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(getTimeForTrackFormat(i3));
        sb2.append("");
        textView2.setText(sb2.toString());
        TextView textView3 = this.textView1;
        StringBuilder sb3 = new StringBuilder();
        sb3.append(getTimeForTrackFormat(i3 - i2));
        sb3.append("");
        textView3.setText(sb3.toString());
        if (this.viewGroup != null) {
            this.viewGroup.removeAllViews();
            this.viewGroup = null;
            this.editorRangeSeekBar = null;
            this.editorRangePlaySeekBar = null;
        }
        this.viewGroup = findViewById(R.id.seekLayout);
        this.editorRangeSeekBar = new EditorRangeSeekBar<>(Integer.valueOf(0), Integer.valueOf(this.MP_DURATION), this);
        this.editorRangePlaySeekBar = new EditorRangePlaySeekBar<>(Integer.valueOf(0), Integer.valueOf(this.MP_DURATION), this);
        this.editorRangeSeekBar.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Integer>() {

            public void onRangeSeekBarValuesChanged(EditorRangeSeekBar<?> editorRangeSeekBar, Integer num, Integer num2, boolean z) {
                if (ActivityVideoMirror.this.videoView.isPlaying()) {
                    ActivityVideoMirror.this.videoView.pause();
                    ActivityVideoMirror.this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
                }
                if (ActivityVideoMirror.this.anInt3 == num2.intValue()) {
                    if (num2.intValue() - num.intValue() <= 1000) {
                        num = Integer.valueOf(num2.intValue() + NotificationManagerCompat.IMPORTANCE_UNSPECIFIED);
                    }
                    ActivityVideoMirror.this.videoView.seekTo(num.intValue());
                } else if (ActivityVideoMirror.this.anInt2 == num.intValue()) {
                    if (num2.intValue() - num.intValue() <= 1000) {
                        num2 = Integer.valueOf(num.intValue() + 1000);
                    }
                    ActivityVideoMirror.this.videoView.seekTo(num.intValue());
                }
                ActivityVideoMirror.this.editorRangeSeekBar.setSelectedMaxValue(num2);
                ActivityVideoMirror.this.editorRangeSeekBar.setSelectedMinValue(num);
                ActivityVideoMirror.this.textView.setText(ActivityVideoMirror.getTimeForTrackFormat(num.intValue()));
                ActivityVideoMirror.this.textView2.setText(ActivityVideoMirror.getTimeForTrackFormat(num2.intValue()));
                ActivityVideoMirror.this.textView1.setText(ActivityVideoMirror.getTimeForTrackFormat(num2.intValue() - num.intValue()));
                ActivityVideoMirror.this.editorRangePlaySeekBar.setSelectedMinValue(num);
                ActivityVideoMirror.this.editorRangePlaySeekBar.setSelectedMaxValue(num2);
                ActivityVideoMirror.this.anInt2 = num.intValue();
                ActivityVideoMirror.this.anInt3 = num2.intValue();
            }
        });
        this.viewGroup.addView(this.editorRangeSeekBar);
        this.viewGroup.addView(this.editorRangePlaySeekBar);
        this.editorRangeSeekBar.setSelectedMinValue(Integer.valueOf(i2));
        this.editorRangeSeekBar.setSelectedMaxValue(Integer.valueOf(i3));
        this.editorRangePlaySeekBar.setSelectedMinValue(Integer.valueOf(i2));
        this.editorRangePlaySeekBar.setSelectedMaxValue(Integer.valueOf(i3));
        this.editorRangePlaySeekBar.setEnabled(false);
        this.editorRangePlaySeekBar.setVisibility(4);
        this.progressDialog.dismiss();
    }

    private int e() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int i2 = displayMetrics.heightPixels;
        int i3 = displayMetrics.widthPixels;
        return i3 <= i2 ? i3 : i2;
    }

    @SuppressLint({"DefaultLocale"})
    public static String getTimeForTrackFormat(int i2) {
        long j2 = i2;
        return String.format("%02d:%02d:%02d", Long.valueOf(TimeUnit.MILLISECONDS.toHours(j2)), Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(j2) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(j2))), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j2) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j2))));
    }


    @Override public void onStart() {
        super.onStart();
    }

    public void onPause() {
        super.onPause();
        this.aBoolean = false;
        try {
            if (this.videoView.isPlaying()) {
                this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
                this.videoView.pause();
            }
        } catch (Exception unused) {
        }
        this.isInFront = false;
        if (this.editorRangePlaySeekBar != null && this.editorRangePlaySeekBar.getVisibility() == 0) {
            this.editorRangePlaySeekBar.setVisibility(4);
        }
    }


    public void onRestart() {
        super.onRestart();
    }

    private void f() {
        try {
            this.ffmpeg.loadBinary(new LoadBinaryResponseHandler() {
                @Override public void onFailure() {
                    ActivityVideoMirror.this.g();
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
            g();
        }
    }


    public void g() {
        new AlertDialog.Builder(this).setIcon(17301543).setTitle("Device not supported").setMessage("FFmpeg is not supported on your device").setCancelable(false).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialogInterface, int i) {
                ActivityVideoMirror.this.finish();
            }
        }).create().show();
    }

    public void deleteFromGallery(String str) {
        String[] strArr = {"_id"};
        String[] strArr2 = {str};
        Uri uri = Media.EXTERNAL_CONTENT_URI;
        ContentResolver contentResolver = getContentResolver();
        Cursor query = contentResolver.query(uri, strArr, "_data = ?", strArr2, null);
        if (query.moveToFirst()) {
            try {
                contentResolver.delete(ContentUris.withAppendedId(Media.EXTERNAL_CONTENT_URI, query.getLong(query.getColumnIndexOrThrow("_id"))), null, null);
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


    @Override public void onResume() {
        super.onResume();
        this.isInFront = BOOLEAN;
        this.string = getIntent().getStringExtra("videouri");
        videoPath = this.string;
    }

    @Override public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, ActivityVideoList.class);
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
            String[] strArr = new String[0];
            try {
                if (this.videoView.isPlaying()) {
                    this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
                    this.videoView.pause();
                }
            } catch (Exception unused) {
            }
            this.anInt4 = this.editorRangeSeekBar.getSelectedMinValue().intValue() / 1000;
            this.anInt6 = this.editorRangeSeekBar.getSelectedMaxValue().intValue() / 1000;
            this.anInt5 = this.anInt6 - this.anInt4;
            StringBuilder sb = new StringBuilder();
            sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
            sb.append("/");
            sb.append(getResources().getString(R.string.MainFolderName));
            sb.append("/");
            sb.append(getResources().getString(R.string.VideoMirror));
            File file = new File(sb.toString());
            if (!file.exists()) {
                file.mkdirs();
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
            sb2.append("/");
            sb2.append(getResources().getString(R.string.MainFolderName));
            sb2.append("/");
            sb2.append(getResources().getString(R.string.VideoMirror));
            sb2.append("/Video_");
            sb2.append(System.currentTimeMillis());
            sb2.append(".mp4");
            this.string1 = sb2.toString();
            if (this.anInt1 != 0) {
                if (this.anInt1 == 1) {
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("");
                    sb3.append(this.anInt4);
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("");
                    sb4.append(this.anInt5);
                    strArr = new String[]{"-y", "-i", videoPath, "-strict", "experimental", "-vf", "crop=iw/2:ih:0:0,split[tmp],pad=2*iw[left]; [tmp]hflip[right]; [left][right] overlay=W/2", "-vb", "20M", "-r", "15", "-ss", sb3.toString(), "-t", sb4.toString(), this.string1};
                } else if (this.anInt1 == 2) {
                    strArr = cmdleft(videoPath, this.string1);
                } else if (this.anInt1 == 3) {
                    strArr = cmdtop(videoPath, this.string1);
                } else if (this.anInt1 == 4) {
                    strArr = cmddown(videoPath, this.string1);
                }
                a(strArr, this.string1);
            } else {
                Toast.makeText(this, "Select Option From List!", Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
