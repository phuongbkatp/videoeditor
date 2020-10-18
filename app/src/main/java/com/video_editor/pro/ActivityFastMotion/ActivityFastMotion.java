package com.video_editor.pro.ActivityFastMotion;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.video_editor.pro.R;
import com.video_editor.pro.UtilsAndAdapters.EditorVideoPlayer;
import com.video_editor.pro.UtilsAndAdapters.EditorVideoPlayerState;
import com.video_editor.pro.UtilsAndAdapters.EditorVideoSliceSeekBar;
import com.video_editor.pro.UtilsAndAdapters.EditorVideoSliceSeekBar.SeekBarChangeListener;
import com.video_editor.pro.ActivityVideoList.ActivityVideoList;
import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.xw.repo.BubbleSeekBar;
import com.xw.repo.BubbleSeekBar.OnProgressChangedListener;

import java.io.File;
import java.util.concurrent.TimeUnit;

@SuppressLint({"WrongConstant"})
public class ActivityFastMotion extends AppCompatActivity {

    static final boolean BOOLEAN = true;
    String string;
    String string1 = null;
    Boolean aBoolean = Boolean.valueOf(false);
    String string2 = "00";
    ImageView imageView;
    EditorVideoSliceSeekBar editorVideoSliceSeekBar;
    FFmpeg ffmpeg;
    BubbleSeekBar seekBar;
    int anInt;
    StringBuilder stringBuilder = new StringBuilder();
    private CheckBox checkBox;
    private PowerManager powerManager;
    private UnifiedNativeAd nativeAd;
    public TextView textView;
    public TextView textView1;
    private TextView textView2;
    public EditorVideoPlayerState editorVideoPlayerState = new EditorVideoPlayerState();
    private a q = new a();
    public VideoView videoView;
    private WakeLock wakeLock;
    private InterstitialAd interstitialAd;

    private class   a extends Handler {
        private boolean aBoolean1;
        private Runnable runnable;

        private a() {
            this.aBoolean1 = false;
            this.runnable = new Runnable() {
                public void run() {
                    a.this.a();
                }
            };
        }


        public void a() {
            if (!this.aBoolean1) {
                this.aBoolean1 = ActivityFastMotion.BOOLEAN;
                sendEmptyMessage(0);
            }
        }

        @Override public void handleMessage(Message message) {
            this.aBoolean1 = false;
            ActivityFastMotion.this.editorVideoSliceSeekBar.videoPlayingProgress(ActivityFastMotion.this.videoView.getCurrentPosition());
            if (!ActivityFastMotion.this.videoView.isPlaying() || ActivityFastMotion.this.videoView.getCurrentPosition() >= ActivityFastMotion.this.editorVideoSliceSeekBar.getRightProgress()) {
                if (ActivityFastMotion.this.videoView.isPlaying()) {
                    ActivityFastMotion.this.videoView.pause();
                    ActivityFastMotion.this.aBoolean = Boolean.valueOf(false);
                    ActivityFastMotion.this.imageView.setBackgroundResource(R.drawable.play2);
                }
                ActivityFastMotion.this.editorVideoSliceSeekBar.setSliceBlocked(false);
                ActivityFastMotion.this.editorVideoSliceSeekBar.removeVideoStatusThumb();
                return;
            }
            postDelayed(this.runnable, 50);
        }
    }

    @SuppressLint({"ClickableViewAccessibility", "InvalidWakeLockTag"})
    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView( R.layout.activity_fast_motion);

        Toolbar toolbar = findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Fast Motion Video");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (BOOLEAN || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(BOOLEAN);
            supportActionBar.setDisplayShowTitleEnabled(false);
            d();
            this.ffmpeg = FFmpeg.getInstance(this);
            e();
            this.powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
            this.wakeLock = this.powerManager.newWakeLock(6, "My Tag");
            Object lastNonConfigurationInstance = getLastNonConfigurationInstance();
            if (lastNonConfigurationInstance != null) {
                this.editorVideoPlayerState = (EditorVideoPlayerState) lastNonConfigurationInstance;
            } else {
                Bundle extras = getIntent().getExtras();
                this.editorVideoPlayerState.setFilename(extras.getString("videofilename"));
                this.string1 = extras.getString("videofilename");
            }
            this.textView2.setText(new File(this.string1).getName());
            this.videoView.setOnCompletionListener(new OnCompletionListener() {
                public void onCompletion(MediaPlayer mediaPlayer) {
                    ActivityFastMotion.this.aBoolean = Boolean.valueOf(false);
                    ActivityFastMotion.this.imageView.setBackgroundResource(R.drawable.play2);
                }
            });
            this.videoView.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (ActivityFastMotion.this.aBoolean.booleanValue()) {
                        ActivityFastMotion.this.videoView.pause();
                        ActivityFastMotion.this.aBoolean = Boolean.valueOf(false);
                        ActivityFastMotion.this.imageView.setBackgroundResource(R.drawable.play2);
                    }
                    return ActivityFastMotion.BOOLEAN;
                }
            });
            g();
            this.interstitialAd = new InterstitialAd(this);
            this.interstitialAd.setAdUnitId(getString(R.string.interstitial_id_admob));
            this.interstitialAd.setAdListener(new AdListener() {
                @Override public void onAdClosed() {
                    ActivityFastMotion.this.c();
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




    @Override
    protected void onDestroy() {

        super.onDestroy();
    }



    public void c() {
        Intent intent = new Intent(getApplicationContext(), EditorVideoPlayer.class);
        intent.setFlags(67108864);
        intent.putExtra("song", this.string1);
        startActivity(intent);
        finish();
    }

    public void Command() {
        String[] strArr;
        String str = "";
        float f2 = this.anInt == 2 ? 0.5f : this.anInt == 3 ? 0.33333334f : this.anInt == 4 ? 0.25f : this.anInt == 5 ? 0.2f : this.anInt == 6 ? 0.16666667f : this.anInt == 7 ? 0.14285715f : this.anInt == 8 ? 0.125f : 2.0f;
        String valueOf = String.valueOf(this.editorVideoPlayerState.getStart() / 1000);
        String valueOf2 = String.valueOf(this.editorVideoPlayerState.getDuration() / 1000);
        String filename = this.editorVideoPlayerState.getFilename();
        this.string1 = FastmotionFileUtils.getTargetFileName(this, filename);
        if (this.checkBox.isChecked()) {
            if (this.anInt == 2) {
                str = "atempo=2.0";
            } else if (this.anInt == 3) {
                str = "atempo=2.0";
            } else if (this.anInt == 4) {
                str = "atempo=2.0,atempo=2.0";
            } else if (this.anInt == 5) {
                str = "atempo=2.0,atempo=2.0";
            } else if (this.anInt == 6) {
                str = "atempo=2.0,atempo=2.0,atempo=2.0";
            } else if (this.anInt == 7) {
                str = "atempo=2.0,atempo=2.0,atempo=2.0";
            } else if (this.anInt == 8) {
                str = "atempo=2.0,atempo=2.0,atempo=2.0,atempo=2.0";
            }
        }
        try {
            if (this.checkBox.isChecked()) {
                StringBuilder sb = new StringBuilder();
                sb.append("setpts=");
                sb.append(f2);
                sb.append("*PTS");
                strArr = new String[]{"-y", "-ss", valueOf, "-i", filename, "-filter:v", sb.toString(), "-filter:Zdgz", str, "-r", "15", "-b:v", "2500k", "-strict", "experimental", "-t", valueOf2, this.string1};
            } else {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("setpts=");
                sb2.append(f2);
                sb2.append("*PTS");
                strArr = new String[]{"-y", "-ss", valueOf, "-i", filename, "-filter:v", sb2.toString(), "-an", "-r", "15", "-ab", "128k", "-vcodec", "mpeg4", "-b:v", "2500k", "-sample_fmt", "s16", "-strict", "experimental", "-t", valueOf2, this.string1};
            }
            a(strArr, this.string1);
        } catch (Exception unused) {
            File file = new File(this.string1);
            if (file.exists()) {
                file.delete();
                finish();
                return;
            }
            Toast.makeText(this, "please select Quality", 0).show();
        }
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
                        ActivityFastMotion.this.deleteFromGallery(str);
                        Toast.makeText(ActivityFastMotion.this, "Error Creating Video", 0).show();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }

                @Override public void onSuccess(String str) {
                    progressDialog.dismiss();
                    Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                    intent.setData(Uri.fromFile(new File(ActivityFastMotion.this.string1)));
                    ActivityFastMotion.this.sendBroadcast(intent);
                    ActivityFastMotion.this.b();
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
                    ActivityFastMotion.this.reloadGallery(str);
                }
            });
            getWindow().clearFlags(16);
        } catch (FFmpegCommandAlreadyRunningException unused) {
        }
    }

    private void d() {
        this.textView2 = findViewById(R.id.Filename);
        this.textView = findViewById(R.id.left_pointer);
        this.textView1 = findViewById(R.id.right_pointer);
        this.imageView = findViewById(R.id.buttonply1);
        this.imageView.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                if (ActivityFastMotion.this.aBoolean.booleanValue()) {
                    ActivityFastMotion.this.imageView.setBackgroundResource(R.drawable.play2);
                    ActivityFastMotion.this.aBoolean = Boolean.valueOf(false);
                } else {
                    ActivityFastMotion.this.imageView.setBackgroundResource(R.drawable.pause2);
                    ActivityFastMotion.this.aBoolean = Boolean.valueOf(ActivityFastMotion.BOOLEAN);
                }
                ActivityFastMotion.this.h();
            }
        });
        this.videoView = findViewById(R.id.videoView1);
        this.checkBox = findViewById(R.id.checkBox1);
        this.editorVideoSliceSeekBar = findViewById(R.id.seek_bar);
        this.seekBar = findViewById(R.id.seekBar);
        this.checkBox.setChecked(false);
        this.seekBar.setOnProgressChangedListener(new OnProgressChangedListener() {

            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int i, float f, boolean fromUser) {
                ActivityFastMotion.this.stringBuilder.delete(0, ActivityFastMotion.this.stringBuilder.length());
                StringBuilder sb = ActivityFastMotion.this.stringBuilder;
                sb.append("(listener) int:");
                sb.append(i);
                ActivityFastMotion.this.anInt = i;
            }

            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int i, float f) {

            }

            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int i, float f,boolean b) {

            }

        });
    }

    private void e() {
        try {
            this.ffmpeg.loadBinary(new LoadBinaryResponseHandler() {
                @Override public void onFailure() {
                    ActivityFastMotion.this.f();
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
                ActivityFastMotion.this.finish();
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
                reloadGallery(str);
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        query.close();
    }

    public void reloadGallery(String str) {
        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        intent.setData(Uri.fromFile(new File(str)));
        sendBroadcast(intent);
    }

    private void g() {
        this.videoView.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                ActivityFastMotion.this.editorVideoSliceSeekBar.setSeekBarChangeListener(new SeekBarChangeListener() {
                    public void SeekBarValueChanged(int i, int i2) {
                        if (ActivityFastMotion.this.editorVideoSliceSeekBar.getSelectedThumb() == 1) {
                            ActivityFastMotion.this.videoView.seekTo(ActivityFastMotion.this.editorVideoSliceSeekBar.getLeftProgress());
                        }
                        ActivityFastMotion.this.textView.setText(ActivityFastMotion.getTimeForTrackFormat(i));
                        ActivityFastMotion.this.textView1.setText(ActivityFastMotion.getTimeForTrackFormat(i2));
                        ActivityFastMotion.this.string2 = ActivityFastMotion.getTimeForTrackFormat(i);
                        ActivityFastMotion.this.editorVideoPlayerState.setStart(i);
                        ActivityFastMotion.this.string = ActivityFastMotion.getTimeForTrackFormat(i2);
                        ActivityFastMotion.this.editorVideoPlayerState.setStop(i2);
                    }
                });
                ActivityFastMotion.this.string = ActivityFastMotion.getTimeForTrackFormat(mediaPlayer.getDuration());
                ActivityFastMotion.this.editorVideoSliceSeekBar.setMaxValue(mediaPlayer.getDuration());
                ActivityFastMotion.this.editorVideoSliceSeekBar.setLeftProgress(0);
                ActivityFastMotion.this.editorVideoSliceSeekBar.setRightProgress(mediaPlayer.getDuration());
                ActivityFastMotion.this.editorVideoSliceSeekBar.setProgressMinDiff(0);
            }
        });
        this.videoView.setVideoPath(this.editorVideoPlayerState.getFilename());
        this.string = getTimeForTrackFormat(this.videoView.getDuration());
    }


    public void h() {
        if (this.videoView.isPlaying()) {
            this.videoView.pause();
            this.editorVideoSliceSeekBar.setSliceBlocked(false);
            this.editorVideoSliceSeekBar.removeVideoStatusThumb();
            return;
        }
        this.videoView.seekTo(this.editorVideoSliceSeekBar.getLeftProgress());
        this.videoView.start();
        this.editorVideoSliceSeekBar.videoPlayingProgress(this.editorVideoSliceSeekBar.getLeftProgress());
        this.q.a();
    }

    public static String getTimeForTrackFormat(int i2) {
        long j2 = i2;
        return String.format("%02d:%02d", Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(j2)), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j2) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j2))));
    }


    @Override public void onResume() {
        super.onResume();
        this.wakeLock.acquire();
        if (!this.videoView.isPlaying()) {
            this.aBoolean = Boolean.valueOf(false);
            this.imageView.setBackgroundResource(R.drawable.play2);
        }
        this.videoView.seekTo(this.editorVideoPlayerState.getCurrentTime());
    }

@Override
    public void onPause() {
        this.wakeLock.release();
        super.onPause();
        this.editorVideoPlayerState.setCurrentTime(this.videoView.getCurrentPosition());
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picker, menu);
        return BOOLEAN;
    }

    @Override public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, ActivityVideoList.class);
        intent.setFlags(67108864);
        startActivity(intent);
        finish();
    }

   @Override public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
            return BOOLEAN;
        }
        if (menuItem.getItemId() == R.id.Done) {
            if (this.videoView.isPlaying()) {
                this.videoView.pause();
                this.imageView.setBackgroundResource(R.drawable.play2);
            }
            if (this.editorVideoPlayerState.isValid()) {
                Command();
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
