package com.video_editor.pro.ActivityVideoCutter;

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
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.video_editor.pro.R;
import com.video_editor.pro.UtilsAndAdapters.EditorVideoPlayer;
import com.video_editor.pro.UtilsAndAdapters.EditorVideoSliceSeekBar;
import com.video_editor.pro.UtilsAndAdapters.EditorVideoSliceSeekBar.SeekBarChangeListener;
import com.video_editor.pro.ActivityVideoList.ActivityVideoList;
import com.video_editor.pro.ActivityVideoJoiner.ActivityVideoJoiner;
import com.video_editor.pro.ActivityVideoJoiner.JoinerModels.VideoPlayerState;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@SuppressLint({"WrongConstant"})
public class ActivityVideoCutter extends AppCompatActivity implements MediaScannerConnectionClient, OnClickListener {
    public static Context AppContext = null;
    static final boolean BOOLEAN = true;
    private UnifiedNativeAd nativeAd;

    MediaScannerConnection mediaScannerConnection;
    int anInt = 0;
    int anInt1 = 0;
    TextView textView;
    TextView textView1;
    TextView textView2;
    public FFmpeg ffmpeg;
    TextView textView3;
    ImageView imageView;
    EditorVideoSliceSeekBar videoSliceSeekBar;
    VideoView videoView;
    private String string = "";
    private String string1;

    public String string2;

    public VideoPlayerState videoPlayerState = new VideoPlayerState();
    private a p = new a();
    private InterstitialAd interstitialAd;

    private class a extends Handler {
        private boolean aBoolean;
        private Runnable runnable;

        private a() {
            this.aBoolean = false;
            this.runnable = new Runnable() {
                public void run() {
                    a.this.a();
                }
            };
        }


        public void a() {
            if (!this.aBoolean) {
                this.aBoolean = ActivityVideoCutter.BOOLEAN;
                sendEmptyMessage(0);
            }
        }

        @Override public void handleMessage(Message message) {
            this.aBoolean = false;
            ActivityVideoCutter.this.videoSliceSeekBar.videoPlayingProgress(ActivityVideoCutter.this.videoView.getCurrentPosition());
            if (!ActivityVideoCutter.this.videoView.isPlaying() || ActivityVideoCutter.this.videoView.getCurrentPosition() >= ActivityVideoCutter.this.videoSliceSeekBar.getRightProgress()) {
                if (ActivityVideoCutter.this.videoView.isPlaying()) {
                    ActivityVideoCutter.this.videoView.pause();
                    ActivityVideoCutter.this.imageView.setBackgroundResource(R.drawable.play2);
                }
                ActivityVideoCutter.this.videoSliceSeekBar.setSliceBlocked(false);
                ActivityVideoCutter.this.videoSliceSeekBar.removeVideoStatusThumb();
                return;
            }
            postDelayed(this.runnable, 50);
        }
    }


    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView( R.layout.activity_video_cutter);
        Toolbar toolbar = findViewById(R.id.toolbar);

        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Video Cutter");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (BOOLEAN || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(BOOLEAN);
            supportActionBar.setDisplayShowTitleEnabled(false);
            AppContext = this;
            this.imageView = findViewById(R.id.buttonply1);
            this.videoSliceSeekBar = findViewById(R.id.seek_bar1);
            this.textView2 = findViewById(R.id.Filename);
            this.textView = findViewById(R.id.left_pointer);
            this.textView1 = findViewById(R.id.right_pointer);
            this.textView3 = findViewById(R.id.dur);
            this.videoView = findViewById(R.id.videoView1);
            this.imageView.setOnClickListener(this);
            this.string1 = getIntent().getStringExtra("path");
            if (this.string1 == null) {
                finish();
            }
            this.ffmpeg = FFmpeg.getInstance(this);
            g();
            this.textView2.setText(new File(this.string1).getName());
            this.videoView.setVideoPath(this.string1);
            this.videoView.seekTo(100);
            e();
            this.videoView.setOnCompletionListener(new OnCompletionListener() {
                public void onCompletion(MediaPlayer mediaPlayer) {
                    ActivityVideoCutter.this.imageView.setBackgroundResource(R.drawable.play2);
                }
            });
            this.interstitialAd = new InterstitialAd(this);
            this.interstitialAd.setAdUnitId(getString(R.string.interstitial_id_admob));
            this.interstitialAd.setAdListener(new AdListener() {
                @Override public void onAdClosed() {
                    ActivityVideoCutter.this.c();
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
        intent.putExtra("song", this.string2);
        startActivity(intent);
        finish();
    }



    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    private void d() {
        String valueOf = String.valueOf(this.anInt1);
        String.valueOf(this.anInt);
        String valueOf2 = String.valueOf(this.anInt - this.anInt1);
        String format = new SimpleDateFormat("_HHmmss", Locale.US).format(new Date());
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb.append("/");
        sb.append(getResources().getString(R.string.MainFolderName));
        sb.append("/");
        sb.append(getResources().getString(R.string.VideoCutter));
        File file = new File(sb.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb2.append("/");
        sb2.append(getResources().getString(R.string.MainFolderName));
        sb2.append("/");
        sb2.append(getResources().getString(R.string.VideoCutter));
        sb2.append("/ActivityVideoCutter");
        sb2.append(format);
        sb2.append(".mp4");
        this.string2 = sb2.toString();
        a(new String[]{"-ss", valueOf, "-y", "-i", this.string1, "-t", valueOf2, "-vcodec", "mpeg4", "-b:v", "2097152", "-b:a", "48000", "-ac", "2", "-ar", "22050", this.string2}, this.string2);
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
                        ActivityVideoCutter.this.delete(str);
                        Toast.makeText(ActivityVideoCutter.this, "Error Creating Video", 0).show();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }

                @Override public void onSuccess(String str) {
                    progressDialog.dismiss();
                    Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                    intent.setData(Uri.fromFile(new File(ActivityVideoCutter.this.string2)));
                    ActivityVideoCutter.this.sendBroadcast(intent);
                    ActivityVideoCutter.this.b();
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
                    ActivityVideoCutter.this.refreshGallery(str);
                }
            });
            getWindow().clearFlags(16);
        } catch (FFmpegCommandAlreadyRunningException unused) {
        }
    }

    private void e() {
        this.videoView.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                ActivityVideoCutter.this.videoSliceSeekBar.setSeekBarChangeListener(new SeekBarChangeListener() {
                    public void SeekBarValueChanged(int i, int i2) {
                        if (ActivityVideoCutter.this.videoSliceSeekBar.getSelectedThumb() == 1) {
                            ActivityVideoCutter.this.videoView.seekTo(ActivityVideoCutter.this.videoSliceSeekBar.getLeftProgress());
                        }
                        ActivityVideoCutter.this.textView.setText(ActivityVideoJoiner.formatTimeUnit(i));
                        ActivityVideoCutter.this.textView1.setText(ActivityVideoJoiner.formatTimeUnit(i2));
                        ActivityVideoCutter.this.videoPlayerState.setStart(i);
                        ActivityVideoCutter.this.videoPlayerState.setStop(i2);
                        ActivityVideoCutter.this.anInt1 = i / 1000;
                        ActivityVideoCutter.this.anInt = i2 / 1000;
                        TextView textView = ActivityVideoCutter.this.textView3;
                        StringBuilder sb = new StringBuilder();
                        sb.append("duration : ");
                        sb.append(String.format("%02d:%02d:%02d", Integer.valueOf((ActivityVideoCutter.this.anInt - ActivityVideoCutter.this.anInt1) / 3600), Integer.valueOf(((ActivityVideoCutter.this.anInt - ActivityVideoCutter.this.anInt1) % 3600) / 60), Integer.valueOf((ActivityVideoCutter.this.anInt - ActivityVideoCutter.this.anInt1) % 60)));
                        textView.setText(sb.toString());
                    }
                });
                ActivityVideoCutter.this.videoSliceSeekBar.setMaxValue(mediaPlayer.getDuration());
                ActivityVideoCutter.this.videoSliceSeekBar.setLeftProgress(0);
                ActivityVideoCutter.this.videoSliceSeekBar.setRightProgress(mediaPlayer.getDuration());
                ActivityVideoCutter.this.videoSliceSeekBar.setProgressMinDiff(0);
            }
        });
    }

    private void f() {
        if (this.videoView.isPlaying()) {
            this.videoView.pause();
            this.videoSliceSeekBar.setSliceBlocked(false);
            this.imageView.setBackgroundResource(R.drawable.play2);
            this.videoSliceSeekBar.removeVideoStatusThumb();
            return;
        }
        this.videoView.seekTo(this.videoSliceSeekBar.getLeftProgress());
        this.videoView.start();
        this.videoSliceSeekBar.videoPlayingProgress(this.videoSliceSeekBar.getLeftProgress());
        this.imageView.setBackgroundResource(R.drawable.pause2);
        this.p.a();
    }

    @Override public void onClick(View view) {
        if (view == this.imageView) {
            f();
        }
    }

    public void onMediaScannerConnected() {
        this.mediaScannerConnection.scanFile(this.string, "video/*");
    }

    public void onScanCompleted(String str, Uri uri) {
        this.mediaScannerConnection.disconnect();
    }


    @Override public void onResume() {
        super.onResume();
    }

    private void g() {
        try {
            this.ffmpeg.loadBinary(new LoadBinaryResponseHandler() {
                @Override public void onFailure() {
                    ActivityVideoCutter.this.h();
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
            h();
        }
    }


    public void h() {
        new AlertDialog.Builder(this).setIcon(17301543).setTitle("Device not supported").setMessage("FFmpeg is not supported on your device").setCancelable(false).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialogInterface, int i) {
                ActivityVideoCutter.this.finish();
            }
        }).create().show();
    }

    public void delete(String str) {
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
            if (this.videoView.isPlaying()) {
                this.videoView.pause();
                this.imageView.setBackgroundResource(R.drawable.play2);
            }
            d();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
