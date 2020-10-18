package com.video_editor.pro.ActivityVideoJoiner;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaScannerConnection;
import android.net.ParseException;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.video_editor.pro.UtilsAndAdapters.EditorAudioSeekBar;
import com.video_editor.pro.R;
import com.video_editor.pro.UtilsAndAdapters.EditorSelectMusicActivity;
import com.video_editor.pro.UtilsAndAdapters.EditorVideoPlayer;
import com.video_editor.pro.UtilsAndAdapters.EditorVideoPlayerState;
import com.video_editor.pro.UtilsAndAdapters.EditorVideoSliceSeekBar;
import com.video_editor.pro.UtilsAndAdapters.EditorVideoSliceSeekBar.SeekBarChangeListener;
import com.video_editor.pro.ActivityAudioVideoMixer.AddAudio;
import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@SuppressLint({"WrongConstant"})
public class ActivityAddAudio extends AppCompatActivity {
    static EditorAudioSeekBar editorAudioSeekBar = null;
    static LinearLayout linearLayout = null;
    static LinearLayout linearLayout1 = null;
    static MediaPlayer mediaPlayer = null;
    static Boolean aBoolean = Boolean.valueOf(false);
    static ImageView imageView = null;
    static EditorVideoSliceSeekBar videoSliceSeekBar = null;
    static VideoView videoView = null;
    static final boolean BOOLEAN = true;

    public static TextView textView;
    private static TextView textView1;

    public static a r = new a();
    public FFmpeg ffmpeg;
    ImageView imageView1;
    ImageView imageView2;
    Context context;
    String string;
    ProgressDialog progressDialog = null;
    Uri uri;

    public TextView textView2;

    public TextView textView3;

    public TextView textView4;
    private TextView textView5;

    public EditorVideoPlayerState editorVideoPlayerState = new EditorVideoPlayerState();
    private InterstitialAd interstitialAd;

    private static class a extends Handler {
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
                this.aBoolean1 = ActivityAddAudio.BOOLEAN;
                sendEmptyMessage(0);
            }
        }

        @Override public void handleMessage(Message message) {
            this.aBoolean1 = false;
            ActivityAddAudio.videoSliceSeekBar.videoPlayingProgress(ActivityAddAudio.videoView.getCurrentPosition());
            if (ActivityAddAudio.mediaPlayer != null && ActivityAddAudio.mediaPlayer.isPlaying()) {
                try {
                    ActivityAddAudio.editorAudioSeekBar.videoPlayingProgress(ActivityAddAudio.mediaPlayer.getCurrentPosition());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (!ActivityAddAudio.videoView.isPlaying() || ActivityAddAudio.videoView.getCurrentPosition() >= ActivityAddAudio.videoSliceSeekBar.getRightProgress()) {
                try {
                    if (ActivityAddAudio.videoView.isPlaying()) {
                        try {
                            ActivityAddAudio.videoView.pause();
                            ActivityAddAudio.aBoolean = Boolean.valueOf(false);
                        } catch (OutOfMemoryError e2) {
                            e2.printStackTrace();
                        } catch (ArrayIndexOutOfBoundsException e3) {
                            e3.printStackTrace();
                        } catch (ActivityNotFoundException e4) {
                            e4.printStackTrace();
                        } catch (NotFoundException e5) {
                            e5.printStackTrace();
                        } catch (NullPointerException e6) {
                            e6.printStackTrace();
                        } catch (StackOverflowError e7) {
                            e7.printStackTrace();
                        }
                    }
                    ActivityAddAudio.videoSliceSeekBar.setSliceBlocked(false);
                    ActivityAddAudio.videoSliceSeekBar.removeVideoStatusThumb();
                    if (ActivityAddAudio.mediaPlayer != null && ActivityAddAudio.mediaPlayer.isPlaying()) {
                        try {
                            ActivityAddAudio.mediaPlayer.pause();
                            ActivityAddAudio.editorAudioSeekBar.setSliceBlocked(false);
                            ActivityAddAudio.editorAudioSeekBar.removeVideoStatusThumb();
                            return;
                        } catch (IllegalStateException e8) {
                            e8.printStackTrace();
                        }
                    }
                    return;
                } catch (IllegalStateException e9) {
                    e9.printStackTrace();
                }
            }
            postDelayed(this.runnable, 50);
        }
    }


    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView( R.layout.activity_audio_video_mixer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Audio Video Mixer");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (BOOLEAN || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(BOOLEAN);
            supportActionBar.setDisplayShowTitleEnabled(false);
            this.ffmpeg = FFmpeg.getInstance(this);
            j();
            AddAudio.status = 0;
            AddAudio.audioPath = "";
            aBoolean = Boolean.valueOf(false);
            this.context = this;
            this.textView5 = findViewById(R.id.Filename);
            linearLayout = findViewById(R.id.lnr_audio_select);
            linearLayout1 = findViewById(R.id.imgbtn_add);
            g();
            Object lastNonConfigurationInstance = getLastNonConfigurationInstance();
            if (lastNonConfigurationInstance != null) {
                try {
                    this.editorVideoPlayerState = (EditorVideoPlayerState) lastNonConfigurationInstance;
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } else {
                try {
                    Bundle extras = getIntent().getExtras();
                    this.editorVideoPlayerState.setFilename(extras.getString("song"));
                    extras.getString("song").split("/");
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
            h();
            linearLayout1.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View view) {
                    ActivityAddAudio.this.startActivity(new Intent(ActivityAddAudio.this, EditorSelectMusicActivity.class));
                }
            });
            this.imageView1 = findViewById(R.id.imgbtn_close);
            this.imageView1.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View view) {
                    ActivityAddAudio.linearLayout.setVisibility(8);
                    AddAudio.status = 0;
                    if (ActivityAddAudio.videoView != null && ActivityAddAudio.videoView.isPlaying()) {
                        try {
                            ActivityAddAudio.videoView.pause();
                            ActivityAddAudio.imageView.setBackgroundResource(R.drawable.play2);
                            ActivityAddAudio.aBoolean = Boolean.valueOf(false);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    AddAudio.audioPath = "";
                    if (ActivityAddAudio.mediaPlayer != null && ActivityAddAudio.mediaPlayer.isPlaying()) {
                        try {
                            ActivityAddAudio.mediaPlayer.stop();
                            ActivityAddAudio.mediaPlayer.release();
                            ActivityAddAudio.mediaPlayer = null;
                        } catch (IllegalStateException e2) {
                            e2.printStackTrace();
                        }
                    }
                }
            });
            this.interstitialAd = new InterstitialAd(this);
            this.interstitialAd.setAdUnitId(getString(R.string.interstitial_id_admob));
            this.interstitialAd.setAdListener(new AdListener() {
                @Override public void onAdClosed() {
                    ActivityAddAudio.this.e();
                }
            });
            c();
            return;
        }
        throw new AssertionError();
    }

    private void c() {
        if (!this.interstitialAd.isLoading() && !this.interstitialAd.isLoaded()) {
            this.interstitialAd.loadAd(new Builder().build());
        }
    }


    public void d() {
        if (this.interstitialAd == null || !this.interstitialAd.isLoaded()) {
            e();
        } else {
            this.interstitialAd.show();
        }
    }


    public void e() {
        Intent intent = new Intent(getApplicationContext(), EditorVideoPlayer.class);
        intent.setFlags(67108864);
        intent.putExtra("song", this.string);
        startActivity(intent);
        finish();
    }

    private void f() {
        String format = new SimpleDateFormat("_HHmmss", Locale.US).format(new Date());
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb.append("/");
        sb.append(getResources().getString(R.string.MainFolderName));
        sb.append("/");
        sb.append(getResources().getString(R.string.VideoJoiner));
        File file = new File(sb.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb2.append("/");
        sb2.append(getResources().getString(R.string.MainFolderName));
        sb2.append("/");
        sb2.append(getResources().getString(R.string.VideoJoiner));
        sb2.append("/ActivityVideoJoiner");
        sb2.append(format);
        sb2.append(".mkv");
        this.string = sb2.toString();
        int duration = this.editorVideoPlayerState.getDuration() / 1000;
        a(new String[]{"-y", "-ss", String.valueOf(this.editorVideoPlayerState.getStart() / 1000), "-t", String.valueOf(duration), "-i", this.editorVideoPlayerState.getFilename(), "-ss", String.valueOf((editorAudioSeekBar != null ? editorAudioSeekBar.getLeftProgress() : 0) / 1000), "-i", AddAudio.audioPath, "-map", "0:0", "-map", "1:0", "-acodec", "copy", "-vcodec", "copy", "-preset", "ultrafast", "-ss", "0", "-t", String.valueOf(duration), this.string}, this.string);
    }

    private void a(String[] strArr, final String str) {
        try {
            this.progressDialog = new ProgressDialog(this.context);
            this.progressDialog.setMessage("Adding Audio...");
            this.progressDialog.setCancelable(false);
            this.progressDialog.setIndeterminate(BOOLEAN);
            this.progressDialog.show();
            this.ffmpeg.execute(strArr, new ExecuteBinaryResponseHandler() {
                @Override public void onStart() {
                }

                @Override public void onFailure(String str) {
                    Log.d("ffmpegfailure", str);
                    try {
                        new File(str).delete();
                        ActivityAddAudio.this.deleteFromGallery(str);
                        Toast.makeText(ActivityAddAudio.this, "Error Creating Video", 0).show();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }

                @Override public void onSuccess(String str) {
                    if (ActivityAddAudio.this.progressDialog != null && ActivityAddAudio.this.progressDialog.isShowing()) {
                        ActivityAddAudio.this.progressDialog.dismiss();
                    }
                    MediaScannerConnection.scanFile(ActivityAddAudio.this.context, new String[]{ActivityAddAudio.this.string}, new String[]{"mkv"}, null);
                    File file = new File(ActivityAddAudio.this.editorVideoPlayerState.getFilename());
                    if (file.exists()) {
                        file.delete();
                        try {
                            ContentResolver contentResolver = ActivityAddAudio.this.getContentResolver();
                            Uri uri = ActivityAddAudio.this.uri;
                            StringBuilder sb = new StringBuilder();
                            sb.append("_data=\"");
                            sb.append(ActivityAddAudio.this.editorVideoPlayerState.getFilename());
                            sb.append("\"");
                            contentResolver.delete(uri, sb.toString(), null);
                        } catch (Exception unused) {
                        }
                    }
                    MediaScannerConnection.scanFile(ActivityAddAudio.this.context, new String[]{ActivityAddAudio.this.editorVideoPlayerState.getFilename()}, new String[]{"mkv"}, null);
                    ActivityAddAudio.this.d();
                }

                @Override public void onProgress(String str) {
                    Log.d("ffmpegResponse", str);
                }

                @Override public void onFinish() {
                    ActivityAddAudio.this.progressDialog.dismiss();
                    ActivityAddAudio.this.refreshGallery(str);
                }
            });
            getWindow().clearFlags(16);
        } catch (FFmpegCommandAlreadyRunningException unused) {
        }
    }

    private void g() {
        this.textView2 = findViewById(R.id.left_pointer);
        this.textView4 = findViewById(R.id.right_pointer);
        editorAudioSeekBar = findViewById(R.id.audioSeekBar);
        videoSliceSeekBar = findViewById(R.id.seekBar1);
        videoView = findViewById(R.id.videoView1);
        this.imageView2 = findViewById(R.id.ivScreen);
        imageView = findViewById(R.id.btnPlayVideo);
        this.textView3 = findViewById(R.id.tvStartAudio);
        textView = findViewById(R.id.tvEndAudio);
        textView1 = findViewById(R.id.audio_name);
    }

    private void h() {
        videoView.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(final MediaPlayer mediaPlayer) {
                ActivityAddAudio.videoSliceSeekBar.setSeekBarChangeListener(new SeekBarChangeListener() {
                    public void SeekBarValueChanged(int i, int i2) {
                        if (ActivityAddAudio.videoSliceSeekBar.getSelectedThumb() == 1) {
                            ActivityAddAudio.videoView.seekTo(ActivityAddAudio.videoSliceSeekBar.getLeftProgress());
                        }
                        ActivityAddAudio.this.textView2.setText(ActivityAddAudio.formatTimeUnit(i, ActivityAddAudio.BOOLEAN));
                        ActivityAddAudio.this.textView4.setText(ActivityAddAudio.formatTimeUnit(i2, ActivityAddAudio.BOOLEAN));
                        ActivityAddAudio.this.editorVideoPlayerState.setStart(i);
                        ActivityAddAudio.this.editorVideoPlayerState.setStop(i2);
                        if (ActivityAddAudio.mediaPlayer != null && ActivityAddAudio.mediaPlayer.isPlaying()) {
                            try {
                                ActivityAddAudio.mediaPlayer.seekTo(ActivityAddAudio.editorAudioSeekBar.getLeftProgress());
                                ActivityAddAudio.editorAudioSeekBar.videoPlayingProgress(ActivityAddAudio.editorAudioSeekBar.getLeftProgress());
                                ActivityAddAudio.mediaPlayer.start();
                            } catch (IllegalStateException ewq) {
                                ewq.printStackTrace();
                            }
                        }
                        mediaPlayer.setVolume(0.0f, 0.0f);
                    }
                });
                ActivityAddAudio.videoSliceSeekBar.setMaxValue(mediaPlayer.getDuration());
                ActivityAddAudio.videoSliceSeekBar.setLeftProgress(ActivityAddAudio.this.editorVideoPlayerState.getStart());
                ActivityAddAudio.videoSliceSeekBar.setRightProgress(ActivityAddAudio.this.editorVideoPlayerState.getStop());
                ActivityAddAudio.videoSliceSeekBar.setProgressMinDiff(0);
                ActivityAddAudio.videoView.seekTo(100);
            }
        });
        videoView.setVideoPath(this.editorVideoPlayerState.getFilename());
        this.textView5.setText(new File(this.editorVideoPlayerState.getFilename()).getName());
        imageView.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                if (ActivityAddAudio.mediaPlayer != null) {
                    try {
                        ActivityAddAudio.mediaPlayer.start();
                    } catch (IllegalStateException ewqe) {
                        ewqe.printStackTrace();
                    }
                }
                if (ActivityAddAudio.aBoolean.booleanValue()) {
                    try {
                        ActivityAddAudio.imageView.setBackgroundResource(R.drawable.play2);
                        ActivityAddAudio.aBoolean = Boolean.valueOf(false);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } else {
                    try {
                        ActivityAddAudio.imageView.setBackgroundResource(R.drawable.pause2);
                        ActivityAddAudio.aBoolean = Boolean.valueOf(ActivityAddAudio.BOOLEAN);
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }
                ActivityAddAudio.this.i();
            }
        });
        videoView.setOnCompletionListener(new OnCompletionListener() {
            public void onCompletion(MediaPlayer mediaPlayer) {
                ActivityAddAudio.videoView.seekTo(0);
                ActivityAddAudio.imageView.setBackgroundResource(R.drawable.play2);
            }
        });
    }


    public void i() {
        if (videoView.isPlaying()) {
            try {
                videoView.pause();
                videoSliceSeekBar.setSliceBlocked(BOOLEAN);
                videoSliceSeekBar.removeVideoStatusThumb();
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        return;
                }
                return;
            } catch (IllegalStateException e3) {
                e3.printStackTrace();
            }
        }
        videoView.seekTo(videoSliceSeekBar.getLeftProgress());
        videoView.start();
        videoSliceSeekBar.videoPlayingProgress(videoSliceSeekBar.getLeftProgress());
        r.a();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            try {
                mediaPlayer.seekTo(editorAudioSeekBar.getLeftProgress());
                editorAudioSeekBar.videoPlayingProgress(editorAudioSeekBar.getLeftProgress());
                mediaPlayer.start();
            } catch (IllegalStateException e4) {
                e4.printStackTrace();
            }
        }
    }


    @Override public void onResume() {
        super.onResume();
        try {
            if (AddAudio.status == 1) {
                mediaPlayer = new MediaPlayer();
                imageView.setBackgroundResource(R.drawable.play2);
                aBoolean = Boolean.valueOf(false);
                h();
                linearLayout.setVisibility(0);
                try {
                    String[] split = AddAudio.audioPath.split("/");
                    textView1.setText(split[split.length - 1]);
                    Log.v("audiopath", AddAudio.audioPath);
                    mediaPlayer.setDataSource(AddAudio.audioPath);
                    mediaPlayer.prepare();
                } catch (IllegalArgumentException e2) {
                    e2.printStackTrace();
                } catch (SecurityException e3) {
                    e3.printStackTrace();
                } catch (IllegalStateException e4) {
                    e4.printStackTrace();
                } catch (IOException e5) {
                    e5.printStackTrace();
                }
                mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        ActivityAddAudio.editorAudioSeekBar.setSeekBarChangeListener(new EditorAudioSeekBar.SeekBarChangeListener() {
                            public void SeekBarValueChanged(int i, int i2) {
                                if (ActivityAddAudio.editorAudioSeekBar.getSelectedThumb() == 1) {
                                    ActivityAddAudio.mediaPlayer.seekTo(ActivityAddAudio.editorAudioSeekBar.getLeftProgress());
                                }
                                ActivityAddAudio.this.textView3.setText(ActivityAddAudio.formatTimeUnit(i, ActivityAddAudio.BOOLEAN));
                                ActivityAddAudio.textView.setText(ActivityAddAudio.formatTimeUnit(i2, ActivityAddAudio.BOOLEAN));
                                if (ActivityAddAudio.videoView != null && ActivityAddAudio.videoView.isPlaying()) {
                                    ActivityAddAudio.videoView.seekTo(ActivityAddAudio.videoSliceSeekBar.getLeftProgress());
                                    ActivityAddAudio.videoView.start();
                                    ActivityAddAudio.videoSliceSeekBar.videoPlayingProgress(ActivityAddAudio.videoSliceSeekBar.getLeftProgress());
                                    ActivityAddAudio.r.a();
                                }
                            }
                        });
                        ActivityAddAudio.editorAudioSeekBar.setMaxValue(mediaPlayer.getDuration());
                        ActivityAddAudio.editorAudioSeekBar.setLeftProgress(0);
                        ActivityAddAudio.editorAudioSeekBar.setRightProgress(mediaPlayer.getDuration());
                        ActivityAddAudio.editorAudioSeekBar.setProgressMinDiff(0);
                        ActivityAddAudio.this.textView3.setText("00:00");
                        try {
                            ActivityAddAudio.textView.setText(ActivityAddAudio.formatTimeUnit1(mediaPlayer.getDuration()));
                        } catch (Exception sde) {
                            sde.printStackTrace();
                        }
                    }
                });
                mediaPlayer.setOnErrorListener(new OnErrorListener() {
                    public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                        return ActivityAddAudio.BOOLEAN;
                    }
                });
                return;
            }
            imageView.setBackgroundResource(R.drawable.play2);
            aBoolean = Boolean.valueOf(false);
            AddAudio.status = 0;
            AddAudio.audioPath = "";
            linearLayout.setVisibility(8);
        } catch (Exception unused) {
        }
    }

    @SuppressLint({"NewApi"})
    public static String formatTimeUnit1(long j2) throws ParseException {
        return String.format("%02d:%02d", Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(j2)), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j2) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j2))));
    }

    @SuppressLint({"NewApi"})
    public static String formatTimeUnit(long j2, boolean z) throws ParseException {
        return String.format("%02d:%02d", Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(j2)), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j2) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j2))));
    }

    @Override public void onBackPressed() {
        super.onBackPressed();
        AddAudio.status = 0;
        AddAudio.audioPath = "";
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void j() {
        try {
            this.ffmpeg.loadBinary(new LoadBinaryResponseHandler() {
                @Override public void onFailure() {
                    ActivityAddAudio.this.k();
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
            k();
        }
    }


    public void k() {
        new AlertDialog.Builder(this).setIcon(17301543).setTitle("Device not supported").setMessage("FFmpeg is not supported on your device").setCancelable(false).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialogInterface, int i) {
                ActivityAddAudio.this.finish();
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

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.skip, menu);
        return BOOLEAN;
    }

   @Override public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            AddAudio.status = 0;
            AddAudio.audioPath = "";
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                try {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                    Log.e("", "back  button working...");
                } catch (IllegalStateException e2) {
                    e2.printStackTrace();
                }
            }
            finish();
            return BOOLEAN;
        }
        if (menuItem.getItemId() == R.id.Done) {
            if (videoView != null && videoView.isPlaying()) {
                try {
                    videoView.pause();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
            if (mediaPlayer != null) {
                try {
                    mediaPlayer.pause();
                } catch (IllegalStateException e4) {
                    e4.printStackTrace();
                }
            }
            f();
        }
        if (menuItem.getItemId() == R.id.Skip) {
            Bundle extras = getIntent().getExtras();
            Intent intent = new Intent(getApplicationContext(), EditorVideoPlayer.class);
            intent.setFlags(67108864);
            intent.putExtra("song", extras.getString("song"));
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
