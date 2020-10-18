package com.video_editor.pro.ActivityVideoReverse;

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
import com.video_editor.pro.UtilsAndAdapters.EditorStaticMethods;
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
import java.util.concurrent.TimeUnit;

@SuppressLint({"WrongConstant", "ResourceType"})
public class ActivityVideoReverse extends AppCompatActivity {
    static final boolean BOOLEAN = true;
    public static ActivityVideoReverse context;
    public static String videoPath;
    private StateObserver stateObserver = new StateObserver();

    public VideoView videoView;
    public boolean CompleteNotificationCreated = false;

    public String string;
    private String string1;
    private InterstitialAd interstitialAd;
    public int MP_DURATION;
    boolean aBoolean = BOOLEAN;
    ViewGroup viewGroup;
    EditorRangeSeekBar<Integer> integerEditorRangeSeekBar;
    EditorRangePlaySeekBar<Integer> integerEditorRangePlaySeekBar;
    WakeLock wakeLock;
    int anInt = 0;
    public FFmpeg ffmpeg;
    int anInt1 = 4;
    int anInt2;
    int anInt3;
    public boolean isInFront = BOOLEAN;
    int anInt4;
    int anInt5;
    int anInt6;
    int anInt7 = 0;
    int anInt8;
    RelativeLayout relativeLayout;
    RelativeLayout relativeLayout1;
    public ProgressDialog progressDialog;
    RelativeLayout relativeLayout2;
    RelativeLayout relativeLayout3;
    TextView textView;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    public ImageView videoPlayBtn;
    TextView textView4;
    TextView textView5;
    TextView textView6;
    TextView textView7;

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
                this.aBoolean1 = ActivityVideoReverse.BOOLEAN;
                sendEmptyMessage(0);
            }
        }

        @Override public void handleMessage(Message message) {
            this.aBoolean1 = false;
            ActivityVideoReverse.this.integerEditorRangePlaySeekBar.setSelectedMaxValue(Integer.valueOf(ActivityVideoReverse.this.videoView.getCurrentPosition()));
            if (!ActivityVideoReverse.this.videoView.isPlaying() || ActivityVideoReverse.this.videoView.getCurrentPosition() >= ActivityVideoReverse.this.integerEditorRangeSeekBar.getSelectedMaxValue().intValue()) {
                if (ActivityVideoReverse.this.videoView.isPlaying()) {
                    ActivityVideoReverse.this.videoView.pause();
                    ActivityVideoReverse.this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
                    ActivityVideoReverse.this.videoView.seekTo(ActivityVideoReverse.this.integerEditorRangeSeekBar.getSelectedMinValue().intValue());
                    ActivityVideoReverse.this.integerEditorRangePlaySeekBar.setSelectedMinValue(ActivityVideoReverse.this.integerEditorRangeSeekBar.getSelectedMinValue());
                    ActivityVideoReverse.this.integerEditorRangePlaySeekBar.setVisibility(4);
                }
                if (!ActivityVideoReverse.this.videoView.isPlaying()) {
                    ActivityVideoReverse.this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
                    ActivityVideoReverse.this.integerEditorRangePlaySeekBar.setVisibility(4);
                    return;
                }
                return;
            }
            ActivityVideoReverse.this.integerEditorRangePlaySeekBar.setVisibility(0);
            postDelayed(this.runnable, 50);
        }
    }

    public void intentToPreviewActivity() {
    }


    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView( R.layout.videoreverseactivity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Video Reverse");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (BOOLEAN || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(BOOLEAN);
            supportActionBar.setDisplayShowTitleEnabled(false);
            this.ffmpeg = FFmpeg.getInstance(this);
            f();
            this.aBoolean = BOOLEAN;
            FindId();
            context = this;
            this.interstitialAd = new InterstitialAd(this);
            this.interstitialAd.setAdUnitId(getString(R.string.interstitial_id_admob));
            this.interstitialAd.setAdListener(new AdListener() {
                @Override public void onAdClosed() {
                    ActivityVideoReverse.this.c();
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
        intent.putExtra("song", this.string);
        startActivity(intent);
        finish();
    }

    @SuppressLint("InvalidWakeLockTag")
    public void FindId() {
        this.isInFront = BOOLEAN;
        this.anInt1 = e() / 100;
        this.anInt = 0;
        this.wakeLock = ((PowerManager) getSystemService(Context.POWER_SERVICE)).newWakeLock(6, "VideoMerge");
        if (!this.wakeLock.isHeld()) {
            this.wakeLock.acquire();
        }
        this.string1 = getIntent().getStringExtra("videouri");
        videoPath = this.string1;
        this.textView7 = findViewById(R.id.Filename);
        this.textView7.setText(new File(videoPath).getName());
        this.relativeLayout = findViewById(R.id.imgbtn_Original);
        this.relativeLayout1 = findViewById(R.id.imgbtn_Reversed);
        this.relativeLayout2 = findViewById(R.id.back_Original);
        this.relativeLayout3 = findViewById(R.id.back_Reversed);
        this.textView4 = findViewById(R.id.textformatValue);
        this.textView3 = findViewById(R.id.textsizeValue);
        this.textView6 = findViewById(R.id.textCompressPercentage);
        this.textView5 = findViewById(R.id.textcompressSize);
        this.videoView = findViewById(R.id.addcutsvideoview);
        this.videoPlayBtn = findViewById(R.id.videoplaybtn);
        this.textView = findViewById(R.id.left_pointer);
        this.textView1 = findViewById(R.id.mid_pointer);
        this.textView2 = findViewById(R.id.right_pointer);
        a(videoPath);
        if (this.anInt7 == 0) {
            a(7);
        }
        runOnUiThread(new Runnable() {
            public void run() {
                ActivityVideoReverse.this.progressDialog = ProgressDialog.show(ActivityVideoReverse.this, "", "Loading...", ActivityVideoReverse.BOOLEAN);
            }
        });
        VideoSeekBar();
        this.relativeLayout.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                ActivityVideoReverse.this.anInt8 = 0;
                ActivityVideoReverse.this.a(6);
            }
        });
        this.relativeLayout1.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                ActivityVideoReverse.this.anInt8 = 1;
                ActivityVideoReverse.this.a(7);
            }
        });
    }


    public void a(int i2) {
        this.anInt7 = i2;
        selectedButton();
        TextView textView = this.textView5;
        StringBuilder sb = new StringBuilder();
        sb.append(EditorStaticMethods.ExpectedOutputSize(videoPath, timeInSecond(this.anInt2, this.anInt3), this.anInt7));
        sb.append("");
        textView.setText(sb.toString());
        TextView textView2 = this.textView6;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("-");
        sb2.append(EditorStaticMethods.SelectedCompressPercentage(videoPath, timeInSecond(this.anInt2, this.anInt3), this.anInt7));
        sb2.append("%");
        textView2.setText(sb2.toString());
    }

    public void selectedButton() {
        if (this.anInt7 == 6) {
            this.relativeLayout2.setVisibility(0);
            this.relativeLayout3.setVisibility(8);
        }
        if (this.anInt7 == 7) {
            this.relativeLayout2.setVisibility(8);
            this.relativeLayout3.setVisibility(0);
        }
        if (this.anInt7 == 9) {
            this.relativeLayout2.setVisibility(8);
            this.relativeLayout3.setVisibility(8);
        }
        if (this.anInt7 == 0) {
            this.relativeLayout2.setVisibility(8);
            this.relativeLayout3.setVisibility(8);
        }
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
                        ActivityVideoReverse.this.deleteFromGallery(str);
                        Toast.makeText(ActivityVideoReverse.this, "Error Creating Video", Toast.LENGTH_LONG).show();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }

                @Override public void onSuccess(String str) {
                    progressDialog.dismiss();
                    Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                    intent.setData(Uri.fromFile(new File(ActivityVideoReverse.this.string)));
                    ActivityVideoReverse.this.sendBroadcast(intent);
                    ActivityVideoReverse.this.b();
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
                    ActivityVideoReverse.this.refreshGallery(str);
                }
            });
            getWindow().clearFlags(16);
        } catch (FFmpegCommandAlreadyRunningException unused) {
        }
    }

    public void VideoSeekBar() {
        this.videoView.setVideoURI(Uri.parse(videoPath));
        this.videoView.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                if (ActivityVideoReverse.this.aBoolean) {
                    ActivityVideoReverse.this.anInt2 = 0;
                    ActivityVideoReverse.this.anInt3 = mediaPlayer.getDuration();
                    ActivityVideoReverse.this.MP_DURATION = mediaPlayer.getDuration();
                    ActivityVideoReverse.this.seekLayout(0, ActivityVideoReverse.this.MP_DURATION);
                    ActivityVideoReverse.this.videoView.start();
                    ActivityVideoReverse.this.videoView.pause();
                    ActivityVideoReverse.this.videoView.seekTo(300);
                    return;
                }
                ActivityVideoReverse.this.seekLayout(ActivityVideoReverse.this.anInt2, ActivityVideoReverse.this.anInt3);
                ActivityVideoReverse.this.videoView.start();
                ActivityVideoReverse.this.videoView.pause();
                ActivityVideoReverse.this.videoView.seekTo(ActivityVideoReverse.this.anInt2);
            }
        });
        this.videoView.setOnErrorListener(new OnErrorListener() {
            public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                ActivityVideoReverse.this.progressDialog.dismiss();
                return false;
            }
        });
        this.videoPlayBtn.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                ActivityVideoReverse.this.d();
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
        this.anInt = 0;
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
            this.integerEditorRangeSeekBar = null;
            this.integerEditorRangePlaySeekBar = null;
        }
        this.viewGroup = findViewById(R.id.seekLayout);
        this.integerEditorRangeSeekBar = new EditorRangeSeekBar<>(Integer.valueOf(0), Integer.valueOf(this.MP_DURATION), this);
        this.integerEditorRangePlaySeekBar = new EditorRangePlaySeekBar<>(Integer.valueOf(0), Integer.valueOf(this.MP_DURATION), this);
        this.integerEditorRangeSeekBar.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Integer>() {

            public void onRangeSeekBarValuesChanged(EditorRangeSeekBar<?> editorRangeSeekBar, Integer num, Integer num2, boolean z) {
                if (ActivityVideoReverse.this.videoView.isPlaying()) {
                    ActivityVideoReverse.this.videoView.pause();
                    ActivityVideoReverse.this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
                }
                if (ActivityVideoReverse.this.anInt3 == num2.intValue()) {
                    if (num2.intValue() - num.intValue() <= 1000) {
                        num = Integer.valueOf(num2.intValue() + NotificationManagerCompat.IMPORTANCE_UNSPECIFIED);
                    }
                    ActivityVideoReverse.this.videoView.seekTo(num.intValue());
                } else if (ActivityVideoReverse.this.anInt2 == num.intValue()) {
                    if (num2.intValue() - num.intValue() <= 1000) {
                        num2 = Integer.valueOf(num.intValue() + 1000);
                    }
                    ActivityVideoReverse.this.videoView.seekTo(num.intValue());
                }
                ActivityVideoReverse.this.integerEditorRangeSeekBar.setSelectedMaxValue(num2);
                ActivityVideoReverse.this.integerEditorRangeSeekBar.setSelectedMinValue(num);
                ActivityVideoReverse.this.textView.setText(ActivityVideoReverse.getTimeForTrackFormat(num.intValue()));
                ActivityVideoReverse.this.textView2.setText(ActivityVideoReverse.getTimeForTrackFormat(num2.intValue()));
                ActivityVideoReverse.this.textView1.setText(ActivityVideoReverse.getTimeForTrackFormat(num2.intValue() - num.intValue()));
                ActivityVideoReverse.this.integerEditorRangePlaySeekBar.setSelectedMinValue(num);
                ActivityVideoReverse.this.integerEditorRangePlaySeekBar.setSelectedMaxValue(num2);
                ActivityVideoReverse.this.anInt2 = num.intValue();
                ActivityVideoReverse.this.anInt3 = num2.intValue();
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
        this.aBoolean = false;
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

    private void f() {
        try {
            this.ffmpeg.loadBinary(new LoadBinaryResponseHandler() {
                @Override public void onFailure() {
                    ActivityVideoReverse.this.g();
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
                ActivityVideoReverse.this.finish();
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
        this.string1 = getIntent().getStringExtra("videouri");
        videoPath = this.string1;
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
            this.anInt4 = this.integerEditorRangeSeekBar.getSelectedMinValue().intValue() / 1000;
            this.anInt6 = this.integerEditorRangeSeekBar.getSelectedMaxValue().intValue() / 1000;
            this.anInt5 = this.anInt6 - this.anInt4;
            StringBuilder sb = new StringBuilder();
            sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
            sb.append("/");
            sb.append(getResources().getString(R.string.MainFolderName));
            sb.append("/");
            sb.append(getResources().getString(R.string.VideoReverse));
            this.string = sb.toString();
            File file = new File(this.string);
            if (!file.exists()) {
                file.mkdirs();
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(file.getAbsolutePath());
            sb2.append("/Rev_");
            sb2.append(System.currentTimeMillis());
            sb2.append(".mp4");
            this.string = sb2.toString();
            String[] strArr = new String[0];
            if (this.anInt8 == 0) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("");
                sb3.append(this.anInt4);
                StringBuilder sb4 = new StringBuilder();
                sb4.append("");
                sb4.append(this.anInt5);
                strArr = new String[]{"-ss", sb3.toString(), "-y", "-i", videoPath, "-t", sb4.toString(), "-vcodec", "mpeg4", "-b:v", "2097152", "-b:a", "48000", "-ac", "2", "-ar", "22050", "-vf", "reverse", this.string};
            } else if (this.anInt8 == 1) {
                StringBuilder sb5 = new StringBuilder();
                sb5.append("");
                sb5.append(this.anInt4);
                StringBuilder sb6 = new StringBuilder();
                sb6.append("");
                sb6.append(this.anInt5);
                strArr = new String[]{"-ss", sb5.toString(), "-y", "-i", videoPath, "-t", sb6.toString(), "-vcodec", "mpeg4", "-b:v", "2097152", "-b:a", "48000", "-ac", "2", "-ar", "22050", "-vf", "reverse", "-af", "areverse", this.string};
            }
            a(strArr, this.string);
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
