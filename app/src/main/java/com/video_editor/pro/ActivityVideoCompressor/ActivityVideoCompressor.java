package com.video_editor.pro.ActivityVideoCompressor;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
import android.support.v4.media.session.PlaybackStateCompat;
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

import com.video_editor.pro.R;
import com.video_editor.pro.UtilsAndAdapters.EditorRangePlaySeekBar;
import com.video_editor.pro.UtilsAndAdapters.EditorRangeSeekBar;
import com.video_editor.pro.UtilsAndAdapters.EditorRangeSeekBar.OnRangeSeekBarChangeListener;
import com.video_editor.pro.UtilsAndAdapters.EditorStaticMethods;
import com.video_editor.pro.UtilsAndAdapters.EditorVideoPlayer;
import com.video_editor.pro.ActivityVideoList.ActivityVideoList;
import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@SuppressLint({"WrongConstant", "ResourceType"})
public class ActivityVideoCompressor extends AppCompatActivity {
    public static ActivityVideoCompressor context = null;
    static final boolean BOOLEAN = true;
    public static String videoPath;
    public boolean CompleteNotificationCreated = false;
    public int LIST_COLUMN_SIZE = 4;
    public int MP_DURATION;
    boolean a = BOOLEAN;
    RelativeLayout relativeLayout;
    RelativeLayout relativeLayout1;
    RelativeLayout relativeLayout2;
    RelativeLayout relativeLayout3;
    RelativeLayout relativeLayout4;
    public FFmpeg ffmpeg;
    RelativeLayout relativeLayout5;
    ViewGroup viewGroup;
    EditorRangeSeekBar<Integer> integerEditorRangeSeekBar;
    public boolean isInFront = BOOLEAN;
    EditorRangePlaySeekBar<Integer> integerEditorRangePlaySeekBar;
    TextView textView;
    TextView textView1;
    TextView textView2;
    public int maxtime;
    public int mintime;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    public ProgressDialog progressDialog;
    TextView textView6;
    public int quality = 1;
    TextView textView7;
    boolean aBoolean = false;
    public int seekduration;
    public int seekend;
    public int seekstart;
    WakeLock wakeLock;
    public int totalVideoDuration = 0;
    public int type = 0;
    private StateObserver stateObserver = new StateObserver();
    public ImageView videoPlayBtn;

    public VideoView videoView;
    private String string;
    public String string1;
    private InterstitialAd interstitialAd;

    @SuppressLint({"HandlerLeak"})
    public class StateObserver extends Handler {
        private boolean aBoolean1 = false;
        private Runnable runnable = new Runnable() {
            public void run() {
                StateObserver.this.a();
            }
        };

        public StateObserver() {
        }


        public void a() {
            if (!this.aBoolean1) {
                this.aBoolean1 = ActivityVideoCompressor.BOOLEAN;
                sendEmptyMessage(0);
            }
        }

        @Override public void handleMessage(Message message) {
            this.aBoolean1 = false;
            ActivityVideoCompressor.this.integerEditorRangePlaySeekBar.setSelectedMaxValue(Integer.valueOf(ActivityVideoCompressor.this.videoView.getCurrentPosition()));
            if (!ActivityVideoCompressor.this.videoView.isPlaying() || ActivityVideoCompressor.this.videoView.getCurrentPosition() >= ActivityVideoCompressor.this.integerEditorRangeSeekBar.getSelectedMaxValue().intValue()) {
                if (ActivityVideoCompressor.this.videoView.isPlaying()) {
                    ActivityVideoCompressor.this.videoView.pause();
                    ActivityVideoCompressor.this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
                    ActivityVideoCompressor.this.videoView.seekTo(ActivityVideoCompressor.this.integerEditorRangeSeekBar.getSelectedMinValue().intValue());
                    ActivityVideoCompressor.this.integerEditorRangePlaySeekBar.setSelectedMinValue(ActivityVideoCompressor.this.integerEditorRangeSeekBar.getSelectedMinValue());
                    ActivityVideoCompressor.this.integerEditorRangePlaySeekBar.setVisibility(4);
                }
                if (!ActivityVideoCompressor.this.videoView.isPlaying()) {
                    ActivityVideoCompressor.this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
                    ActivityVideoCompressor.this.integerEditorRangePlaySeekBar.setVisibility(4);
                    return;
                }
                return;
            }
            ActivityVideoCompressor.this.integerEditorRangePlaySeekBar.setVisibility(0);
            postDelayed(this.runnable, 50);
        }
    }

    public void intentToPreviewActivity() {
    }


    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView( R.layout.activity_video_compressor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Video Compressor");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (BOOLEAN || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(BOOLEAN);
            supportActionBar.setDisplayShowTitleEnabled(false);
            this.ffmpeg = FFmpeg.getInstance(this);
            f();
            this.aBoolean = false;
            this.a = BOOLEAN;
            copyCreate();
            context = this;
            this.interstitialAd = new InterstitialAd(this);
            this.interstitialAd.setAdUnitId(getString(R.string.interstitial_id_admob));
            this.interstitialAd.setAdListener(new AdListener() {
                @Override public void onAdClosed() {
                    ActivityVideoCompressor.this.c();
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
        this.LIST_COLUMN_SIZE = e() / 100;
        this.totalVideoDuration = 0;
        this.wakeLock = ((PowerManager) getSystemService(Context.POWER_SERVICE)).newWakeLock(6, "VideoMerge");
        if (!this.wakeLock.isHeld()) {
            this.wakeLock.acquire();
        }
        this.string = getIntent().getStringExtra("videouri");
        videoPath = this.string;
        this.textView7 = findViewById(R.id.Filename);
        this.videoView = findViewById(R.id.addcutsvideoview);
        this.videoPlayBtn = findViewById(R.id.videoplaybtn);
        this.relativeLayout1 = findViewById(R.id.btnlow);
        this.relativeLayout = findViewById(R.id.btnmedium);
        this.relativeLayout2 = findViewById(R.id.btnhigh);
        this.relativeLayout4 = findViewById(R.id.back_low);
        this.relativeLayout3 = findViewById(R.id.back_medium);
        this.relativeLayout5 = findViewById(R.id.back_high);
        this.textView4 = findViewById(R.id.textformatValue);
        this.textView3 = findViewById(R.id.textsizeValue);
        this.textView6 = findViewById(R.id.textCompressPercentage);
        this.textView5 = findViewById(R.id.textcompressSize);
        this.textView = findViewById(R.id.left_pointer);
        this.textView1 = findViewById(R.id.mid_pointer);
        this.textView2 = findViewById(R.id.right_pointer);
        this.textView7.setText(new File(videoPath).getName());
        a(videoPath);
        if (this.type == 0) {
            a(7);
        }
        runOnUiThread(new Runnable() {
            public void run() {
                ActivityVideoCompressor.this.progressDialog = ProgressDialog.show(ActivityVideoCompressor.this, "", "Loading...", ActivityVideoCompressor.BOOLEAN);
            }
        });
        VideoSeekBar();
        this.relativeLayout1.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                ActivityVideoCompressor.this.quality = 0;
                ActivityVideoCompressor.this.a(6);
            }
        });
        this.relativeLayout.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                ActivityVideoCompressor.this.quality = 1;
                ActivityVideoCompressor.this.a(7);
            }
        });
        this.relativeLayout2.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                ActivityVideoCompressor.this.quality = 2;
                ActivityVideoCompressor.this.a(8);
            }
        });
    }

    public void executeCompressCommand() {
        String[] strArr;
        String[] strArr2 = new String[0];
        String format = new SimpleDateFormat("_HHmmss", Locale.US).format(new Date());
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb.append("/");
        sb.append(getResources().getString(R.string.MainFolderName));
        sb.append("/");
        sb.append(getResources().getString(R.string.VideoCompressor));
        File file = new File(sb.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb2.append("/");
        sb2.append(getResources().getString(R.string.MainFolderName));
        sb2.append("/");
        sb2.append(getResources().getString(R.string.VideoCompressor));
        sb2.append("/videocompressor");
        sb2.append(format);
        sb2.append(".mp4");
        this.string1 = sb2.toString();
        if (this.quality == 0) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("");
            sb3.append(this.seekstart);
            StringBuilder sb4 = new StringBuilder();
            sb4.append("");
            sb4.append(this.seekduration);
            strArr = new String[]{"-ss", sb3.toString(), "-y", "-i", videoPath, "-t", sb4.toString(), "-s", "720x576", "-r", "25", "-vcodec", "mpeg4", "-b:v", "1200k", "-b:a", "48000", "-ac", "2", "-ar", "22050", this.string1};
        } else if (this.quality == 2) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append("");
            sb5.append(this.seekstart);
            StringBuilder sb6 = new StringBuilder();
            sb6.append("");
            sb6.append(this.seekduration);
            strArr = new String[]{"-ss", sb5.toString(), "-y", "-i", videoPath, "-t", sb6.toString(), "-s", "160x120", "-r", "25", "-vcodec", "mpeg4", "-b:v", "150k", "-b:a", "48000", "-ac", "2", "-ar", "22050", this.string1};
        } else {
            StringBuilder sb7 = new StringBuilder();
            sb7.append("");
            sb7.append(this.seekstart);
            StringBuilder sb8 = new StringBuilder();
            sb8.append("");
            sb8.append(this.seekduration);
            strArr = new String[]{"-ss", sb7.toString(), "-y", "-i", videoPath, "-t", sb8.toString(), "-s", "480x320", "-r", "25", "-vcodec", "mpeg4", "-b:v", "900k", "-b:a", "48000", "-ac", "2", "-ar", "22050", this.string1};
        }
        a(strArr, this.string1);
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
                        ActivityVideoCompressor.this.deleteFromGallery(str);
                        Toast.makeText(ActivityVideoCompressor.this, "Error Creating Video", 0).show();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }

                @Override public void onSuccess(String str) {
                    progressDialog.dismiss();
                    Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                    intent.setData(Uri.fromFile(new File(ActivityVideoCompressor.this.string1)));
                    ActivityVideoCompressor.this.sendBroadcast(intent);
                    ActivityVideoCompressor.this.b();
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
                    ActivityVideoCompressor.this.refreshGallery(str);
                }
            });
            getWindow().clearFlags(16);
        } catch (FFmpegCommandAlreadyRunningException unused) {
        }
    }


    public void a(int i2) {
        this.type = i2;
        selectedButton();
        TextView textView = this.textView5;
        StringBuilder sb = new StringBuilder();
        sb.append(EditorStaticMethods.ExpectedOutputSize(videoPath, timeInSecond(this.mintime, this.maxtime), this.type));
        sb.append("");
        textView.setText(sb.toString());
        TextView textView2 = this.textView6;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("-");
        sb2.append(EditorStaticMethods.SelectedCompressPercentage(videoPath, timeInSecond(this.mintime, this.maxtime), this.type));
        sb2.append("%");
        textView2.setText(sb2.toString());
    }

    private void a(String str) {
        TextView textView = this.textView3;
        StringBuilder sb = new StringBuilder();
        sb.append("Size :- ");
        sb.append(EditorStaticMethods.sizeInMBbyFilepath(str));
        textView.setText(sb.toString());
        TextView textView2 = this.textView4;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Format :- ");
        sb2.append(EditorStaticMethods.FormatofVideo(str));
        textView2.setText(sb2.toString());
    }

    public int timeInSecond(int i2, int i3) {
        return (i3 - i2) / 1000;
    }

    public void VideoSeekBar() {
        this.videoView.setVideoURI(Uri.parse(videoPath));
        this.videoView.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                if (ActivityVideoCompressor.this.a) {
                    ActivityVideoCompressor.this.mintime = 0;
                    ActivityVideoCompressor.this.maxtime = mediaPlayer.getDuration();
                    ActivityVideoCompressor.this.MP_DURATION = mediaPlayer.getDuration();
                    ActivityVideoCompressor.this.seekLayout(0, ActivityVideoCompressor.this.MP_DURATION);
                    ActivityVideoCompressor.this.videoView.start();
                    ActivityVideoCompressor.this.videoView.pause();
                    ActivityVideoCompressor.this.videoView.seekTo(300);
                    return;
                }
                ActivityVideoCompressor.this.seekLayout(ActivityVideoCompressor.this.mintime, ActivityVideoCompressor.this.maxtime);
                ActivityVideoCompressor.this.videoView.start();
                ActivityVideoCompressor.this.videoView.pause();
                ActivityVideoCompressor.this.videoView.seekTo(ActivityVideoCompressor.this.mintime);
            }
        });
        this.videoView.setOnErrorListener(new OnErrorListener() {
            public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                ActivityVideoCompressor.this.progressDialog.dismiss();
                return false;
            }
        });
        this.videoPlayBtn.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                ActivityVideoCompressor.this.d();
            }
        });
    }


    public void d() {
        if (this.videoView.isPlaying()) {
            this.videoView.pause();
            this.videoView.seekTo(this.integerEditorRangeSeekBar.getSelectedMinValue().intValue());
            this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
            this.integerEditorRangePlaySeekBar.setVisibility(4);
            return;
        }
        this.videoView.seekTo(this.integerEditorRangeSeekBar.getSelectedMinValue().intValue());
        this.videoView.start();
        this.integerEditorRangePlaySeekBar.setSelectedMaxValue(Integer.valueOf(this.videoView.getCurrentPosition()));
        this.stateObserver.a();
        this.videoPlayBtn.setBackgroundResource(R.drawable.pause2);
        this.integerEditorRangePlaySeekBar.setVisibility(0);
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
        if (this.type != 9) {
            TextView textView4 = this.textView5;
            StringBuilder sb4 = new StringBuilder();
            sb4.append(EditorStaticMethods.ExpectedOutputSize(videoPath, timeInSecond(i2, i3), this.type));
            sb4.append("");
            textView4.setText(sb4.toString());
            TextView textView5 = this.textView6;
            StringBuilder sb5 = new StringBuilder();
            sb5.append("-");
            sb5.append(EditorStaticMethods.SelectedCompressPercentage(videoPath, timeInSecond(i2, i3), this.type));
            sb5.append("%");
            textView5.setText(sb5.toString());
        }
        if (this.viewGroup != null) {
            this.viewGroup.removeAllViews();
            this.viewGroup = null;
            this.integerEditorRangeSeekBar = null;
            this.integerEditorRangePlaySeekBar = null;
        }
        this.viewGroup = findViewById(R.id.seekLayout);
        this.integerEditorRangeSeekBar = new EditorRangeSeekBar<>(Integer.valueOf(0), Integer.valueOf(this.MP_DURATION), this);
        this.integerEditorRangePlaySeekBar = new EditorRangePlaySeekBar<>(Integer.valueOf(0), Integer.valueOf(this.MP_DURATION), this);
        this.integerEditorRangeSeekBar.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Integer>() {

            public void onRangeSeekBarValuesChanged(EditorRangeSeekBar<?> editorRangeSeekBar, Integer num, Integer num2, boolean z) {
                if (ActivityVideoCompressor.this.videoView.isPlaying()) {
                    ActivityVideoCompressor.this.videoView.pause();
                    ActivityVideoCompressor.this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
                }
                if (ActivityVideoCompressor.this.maxtime == num2.intValue()) {
                    if (num2.intValue() - num.intValue() <= 1000) {
                        num = Integer.valueOf(num2.intValue() + NotificationManagerCompat.IMPORTANCE_UNSPECIFIED);
                    }
                    ActivityVideoCompressor.this.videoView.seekTo(num.intValue());
                } else if (ActivityVideoCompressor.this.mintime == num.intValue()) {
                    if (num2.intValue() - num.intValue() <= 1000) {
                        num2 = Integer.valueOf(num.intValue() + 1000);
                    }
                    ActivityVideoCompressor.this.videoView.seekTo(num.intValue());
                }
                ActivityVideoCompressor.this.integerEditorRangeSeekBar.setSelectedMaxValue(num2);
                ActivityVideoCompressor.this.integerEditorRangeSeekBar.setSelectedMinValue(num);
                ActivityVideoCompressor.this.textView.setText(ActivityVideoCompressor.getTimeForTrackFormat(num.intValue()));
                ActivityVideoCompressor.this.textView2.setText(ActivityVideoCompressor.getTimeForTrackFormat(num2.intValue()));
                ActivityVideoCompressor.this.textView1.setText(ActivityVideoCompressor.getTimeForTrackFormat(num2.intValue() - num.intValue()));
                if (ActivityVideoCompressor.this.type != 9) {
                    TextView textView = ActivityVideoCompressor.this.textView5;
                    StringBuilder sb = new StringBuilder();
                    sb.append(EditorStaticMethods.ExpectedOutputSize(ActivityVideoCompressor.videoPath, ActivityVideoCompressor.this.timeInSecond(num.intValue(), num2.intValue()), ActivityVideoCompressor.this.type));
                    sb.append("");
                    textView.setText(sb.toString());
                    TextView textView2 = ActivityVideoCompressor.this.textView6;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("-");
                    sb2.append(EditorStaticMethods.SelectedCompressPercentage(ActivityVideoCompressor.videoPath, ActivityVideoCompressor.this.timeInSecond(num.intValue(), ActivityVideoCompressor.this.maxtime), ActivityVideoCompressor.this.type));
                    sb2.append("%");
                    textView2.setText(sb2.toString());
                }
                ActivityVideoCompressor.this.integerEditorRangePlaySeekBar.setSelectedMinValue(num);
                ActivityVideoCompressor.this.integerEditorRangePlaySeekBar.setSelectedMaxValue(num2);
                ActivityVideoCompressor.this.mintime = num.intValue();
                ActivityVideoCompressor.this.maxtime = num2.intValue();
            }
        });
        this.viewGroup.addView(this.integerEditorRangeSeekBar);
        this.viewGroup.addView(this.integerEditorRangePlaySeekBar);
        this.integerEditorRangeSeekBar.setSelectedMinValue(Integer.valueOf(i2));
        this.integerEditorRangeSeekBar.setSelectedMaxValue(Integer.valueOf(i3));
        this.integerEditorRangePlaySeekBar.setSelectedMinValue(Integer.valueOf(i2));
        this.integerEditorRangePlaySeekBar.setSelectedMaxValue(Integer.valueOf(i3));
        this.integerEditorRangePlaySeekBar.setEnabled(false);
        this.integerEditorRangePlaySeekBar.setVisibility(4);
        this.progressDialog.dismiss();
    }

    private int e() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int i2 = displayMetrics.heightPixels;
        int i3 = displayMetrics.widthPixels;
        return i3 <= i2 ? i3 : i2;
    }

    public static String getTimeForTrackFormat(int i2) {
        long j2 = i2;
        return String.format("%02d:%02d:%02d", Long.valueOf(TimeUnit.MILLISECONDS.toHours(j2)), Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(j2) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(j2))), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j2) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j2))));
    }


    @Override public void onStart() {
        super.onStart();
        if (this.CompleteNotificationCreated) {
            intentToPreviewActivity();
            ((NotificationManager) context.getSystemService("notification")).cancelAll();
        }
        this.CompleteNotificationCreated = false;
    }

    public void onPause() {
        super.onPause();
        this.a = false;
        try {
            if (this.videoView.isPlaying()) {
                this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
                this.videoView.pause();
            }
        } catch (Exception unused) {
        }
        this.isInFront = false;
        if (this.integerEditorRangePlaySeekBar != null && this.integerEditorRangePlaySeekBar.getVisibility() == 0) {
            this.integerEditorRangePlaySeekBar.setVisibility(4);
        }
    }


    public void onRestart() {
        super.onRestart();
    }

    public boolean comparesize(long j2, int i2) {
        if (j2 > (((long) i2) * (Long.parseLong(EditorStaticMethods.bitRate(videoPath)) / 1024)) / PlaybackStateCompat.ACTION_PLAY_FROM_URI) {
            return BOOLEAN;
        }
        return false;
    }

    public void selectedButton() {
        if (this.type == 6) {
            this.relativeLayout4.setVisibility(0);
            this.relativeLayout3.setVisibility(8);
            this.relativeLayout5.setVisibility(8);
        }
        if (this.type == 7) {
            this.relativeLayout4.setVisibility(8);
            this.relativeLayout3.setVisibility(0);
            this.relativeLayout5.setVisibility(8);
        }
        if (this.type == 8) {
            this.relativeLayout4.setVisibility(8);
            this.relativeLayout3.setVisibility(8);
            this.relativeLayout5.setVisibility(0);
        }
        if (this.type == 9) {
            this.relativeLayout4.setVisibility(8);
            this.relativeLayout3.setVisibility(8);
            this.relativeLayout5.setVisibility(8);
        }
        if (this.type == 0) {
            this.relativeLayout4.setVisibility(8);
            this.relativeLayout3.setVisibility(8);
            this.relativeLayout5.setVisibility(8);
        }
    }

    private void f() {
        try {
            this.ffmpeg.loadBinary(new LoadBinaryResponseHandler() {
                @Override public void onFailure() {
                    ActivityVideoCompressor.this.g();
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
                ActivityVideoCompressor.this.finish();
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
        this.aBoolean = false;
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
            try {
                if (this.videoView.isPlaying()) {
                    this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
                    this.videoView.pause();
                }
            } catch (Exception unused) {
            }
            this.seekstart = this.integerEditorRangeSeekBar.getSelectedMinValue().intValue() / 1000;
            this.seekend = this.integerEditorRangeSeekBar.getSelectedMaxValue().intValue() / 1000;
            this.seekduration = this.seekend - this.seekstart;
            executeCompressCommand();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
