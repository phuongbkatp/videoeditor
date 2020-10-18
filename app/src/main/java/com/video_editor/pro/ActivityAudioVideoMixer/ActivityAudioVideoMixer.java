package com.video_editor.pro.ActivityAudioVideoMixer;

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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.video_editor.pro.ActivityVideoList.ActivityVideoList;
import com.video_editor.pro.UtilsAndAdapters.EditorAudioSeekBar;
import com.video_editor.pro.R;
import com.video_editor.pro.UtilsAndAdapters.EditorSelectMusicActivity;
import com.video_editor.pro.UtilsAndAdapters.EditorVideoPlayer;
import com.video_editor.pro.UtilsAndAdapters.EditorVideoPlayerState;
import com.video_editor.pro.UtilsAndAdapters.EditorVideoSliceSeekBar;
import com.video_editor.pro.UtilsAndAdapters.EditorVideoSliceSeekBar.SeekBarChangeListener;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@SuppressLint({"WrongConstant"})
public class ActivityAudioVideoMixer extends AppCompatActivity {
    static EditorAudioSeekBar editorAudioSeekBar = null;
    static LinearLayout linearLayout = null;
    static LinearLayout linearLayout1 = null;
    static MediaPlayer mediaPlayer = null;
    static Boolean aBoolean = Boolean.valueOf(false);
    static ImageView imageView = null;
    private UnifiedNativeAd nativeAd;

    static EditorVideoSliceSeekBar editorVideoSliceSeekBar = null;
    static VideoView videoView = null;
    static final boolean BOOLEAN = true;
    public static TextView textView;
    private static TextView textView1;
    public static a a = new a();
    public FFmpeg ffmpeg;
    ImageView imageView1;
    ImageView imageView2;
    Context context;
    String string;
    ProgressDialog progressDialog = null;
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
                this.aBoolean1 = ActivityAudioVideoMixer.BOOLEAN;
                sendEmptyMessage(0);
            }
        }

        @Override
        public void handleMessage(Message message) {
            this.aBoolean1 = false;
            ActivityAudioVideoMixer.editorVideoSliceSeekBar.videoPlayingProgress(ActivityAudioVideoMixer.videoView.getCurrentPosition());
            if (ActivityAudioVideoMixer.mediaPlayer != null && ActivityAudioVideoMixer.mediaPlayer.isPlaying()) {
                try {
                    ActivityAudioVideoMixer.editorAudioSeekBar.videoPlayingProgress(ActivityAudioVideoMixer.mediaPlayer.getCurrentPosition());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (!ActivityAudioVideoMixer.videoView.isPlaying() || ActivityAudioVideoMixer.videoView.getCurrentPosition() >= ActivityAudioVideoMixer.editorVideoSliceSeekBar.getRightProgress()) {
                try {
                    if (ActivityAudioVideoMixer.videoView.isPlaying()) {
                        ActivityAudioVideoMixer.videoView.pause();
                        ActivityAudioVideoMixer.aBoolean = Boolean.valueOf(false);

                    }
                    ActivityAudioVideoMixer.editorVideoSliceSeekBar.setSliceBlocked(false);
                    ActivityAudioVideoMixer.editorVideoSliceSeekBar.removeVideoStatusThumb();
                    if (ActivityAudioVideoMixer.mediaPlayer != null && ActivityAudioVideoMixer.mediaPlayer.isPlaying()) {
                        ActivityAudioVideoMixer.mediaPlayer.pause();
                        ActivityAudioVideoMixer.editorAudioSeekBar.setSliceBlocked(false);
                        ActivityAudioVideoMixer.editorAudioSeekBar.removeVideoStatusThumb();
                        return;
                    }
                    return;
                } catch (IllegalStateException e9) {
                    e9.printStackTrace();
                }
            }
            postDelayed(this.runnable, 50);
        }
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        refreshAd();
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
                @Override
                public void onClick(View view) {
                    ActivityAudioVideoMixer.this.startActivity(new Intent(ActivityAudioVideoMixer.this, EditorSelectMusicActivity.class));
                }
            });
            this.imageView1 = findViewById(R.id.imgbtn_close);
            this.imageView1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityAudioVideoMixer.linearLayout.setVisibility(8);
                    AddAudio.status = 0;
                    if (ActivityAudioVideoMixer.videoView != null && ActivityAudioVideoMixer.videoView.isPlaying()) {
                        try {
                            ActivityAudioVideoMixer.videoView.pause();
                            ActivityAudioVideoMixer.imageView.setBackgroundResource(R.drawable.play2);
                            ActivityAudioVideoMixer.aBoolean = Boolean.valueOf(false);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
                    AddAudio.audioPath = "";
                    if (ActivityAudioVideoMixer.mediaPlayer != null && ActivityAudioVideoMixer.mediaPlayer.isPlaying()) {
                        try {
                            ActivityAudioVideoMixer.mediaPlayer.stop();
                            ActivityAudioVideoMixer.mediaPlayer.release();
                            ActivityAudioVideoMixer.mediaPlayer = null;
                        } catch (IllegalStateException e2) {
                            e2.printStackTrace();
                        }
                    }
                }
            });
            this.interstitialAd = new InterstitialAd(this);
            this.interstitialAd.setAdUnitId(getString(R.string.interstitial_id_admob));
            this.interstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    ActivityAudioVideoMixer.this.e();
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
        sb.append(getResources().getString(R.string.AudioVideoMixer));
        this.string = sb.toString();
        File file = new File(this.string);
        if (!file.exists()) {
            file.mkdirs();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb2.append("/");
        sb2.append(getResources().getString(R.string.MainFolderName));
        sb2.append("/");
        sb2.append(getResources().getString(R.string.AudioVideoMixer));
        sb2.append("/ActivityAudioVideoMixer");
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
                @Override
                public void onStart() {
                }

                @Override
                public void onFailure(String str) {
                    Log.d("ffmpegfailure", str);
                    new File(str).delete();
                    ActivityAudioVideoMixer.this.deleteFromGallery(str);
                    Toast.makeText(ActivityAudioVideoMixer.this, "Error Creating Video", 0).show();
                }


                @Override
                public void onSuccess(String str) {
                    if (ActivityAudioVideoMixer.this.progressDialog != null && ActivityAudioVideoMixer.this.progressDialog.isShowing()) {
                        ActivityAudioVideoMixer.this.progressDialog.dismiss();
                    }
                    MediaScannerConnection.scanFile(ActivityAudioVideoMixer.this.context, new String[]{ActivityAudioVideoMixer.this.string}, new String[]{"mkv"}, null);
                    ActivityAudioVideoMixer.this.d();
                }

                @Override public void onProgress(String str) {
                    Log.d("ffmpegResponse", str);
                }

                @Override public void onFinish() {
                    ActivityAudioVideoMixer.this.progressDialog.dismiss();
                    ActivityAudioVideoMixer.this.reloadGallery(str);
                }
            });
            getWindow().clearFlags(16);
        } catch (FFmpegCommandAlreadyRunningException unused) {
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

    @Override
    protected void onDestroy() {
        if (nativeAd != null) {
            nativeAd.destroy();
        }
        super.onDestroy();
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
                FrameLayout nativeAdPlaceHolder = ActivityAudioVideoMixer.this.findViewById(R.id.nativeAdPlaceHolder);
                @SuppressLint("InflateParams") UnifiedNativeAdView adView = (UnifiedNativeAdView) ActivityAudioVideoMixer.this.getLayoutInflater()
                        .inflate(R.layout.native_ad_unified_smaller, null);
                ActivityAudioVideoMixer.this.populateUnifiedNativeAdView(unifiedNativeAd, adView);
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

    private void g() {
        this.textView2 = findViewById(R.id.left_pointer);
        this.textView4 = findViewById(R.id.right_pointer);
        editorAudioSeekBar = findViewById(R.id.audioSeekBar);
        editorVideoSliceSeekBar = findViewById(R.id.seekBar1);
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
                ActivityAudioVideoMixer.editorVideoSliceSeekBar.setSeekBarChangeListener(new SeekBarChangeListener() {
                    public void SeekBarValueChanged(int i, int i2) {
                        if (ActivityAudioVideoMixer.editorVideoSliceSeekBar.getSelectedThumb() == 1) {
                            ActivityAudioVideoMixer.videoView.seekTo(ActivityAudioVideoMixer.editorVideoSliceSeekBar.getLeftProgress());
                        }
                        ActivityAudioVideoMixer.this.textView2.setText(ActivityAudioVideoMixer.formatTimeUnit(i, ActivityAudioVideoMixer.BOOLEAN));
                        ActivityAudioVideoMixer.this.textView4.setText(ActivityAudioVideoMixer.formatTimeUnit(i2, ActivityAudioVideoMixer.BOOLEAN));
                        ActivityAudioVideoMixer.this.editorVideoPlayerState.setStart(i);
                        ActivityAudioVideoMixer.this.editorVideoPlayerState.setStop(i2);
                        if (ActivityAudioVideoMixer.mediaPlayer != null && ActivityAudioVideoMixer.mediaPlayer.isPlaying()) {
                            try {
                                ActivityAudioVideoMixer.mediaPlayer.seekTo(ActivityAudioVideoMixer.editorAudioSeekBar.getLeftProgress());
                                ActivityAudioVideoMixer.editorAudioSeekBar.videoPlayingProgress(ActivityAudioVideoMixer.editorAudioSeekBar.getLeftProgress());
                                ActivityAudioVideoMixer.mediaPlayer.start();
                            } catch (IllegalStateException e) {
                                e.printStackTrace();
                            }
                        }
                        mediaPlayer.setVolume(0.0f, 0.0f);
                    }
                });
                ActivityAudioVideoMixer.editorVideoSliceSeekBar.setMaxValue(mediaPlayer.getDuration());
                ActivityAudioVideoMixer.editorVideoSliceSeekBar.setLeftProgress(ActivityAudioVideoMixer.this.editorVideoPlayerState.getStart());
                ActivityAudioVideoMixer.editorVideoSliceSeekBar.setRightProgress(ActivityAudioVideoMixer.this.editorVideoPlayerState.getStop());
                ActivityAudioVideoMixer.editorVideoSliceSeekBar.setProgressMinDiff(0);
                ActivityAudioVideoMixer.videoView.seekTo(100);
            }
        });
        videoView.setVideoPath(this.editorVideoPlayerState.getFilename());
        this.textView5.setText(new File(this.editorVideoPlayerState.getFilename()).getName());
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityAudioVideoMixer.mediaPlayer != null) {
                    try {
                        ActivityAudioVideoMixer.mediaPlayer.start();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
                }
                if (ActivityAudioVideoMixer.aBoolean.booleanValue()) {
                    try {
                        ActivityAudioVideoMixer.imageView.setBackgroundResource(R.drawable.play2);
                        ActivityAudioVideoMixer.aBoolean = Boolean.valueOf(false);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } else {
                    try {
                        ActivityAudioVideoMixer.imageView.setBackgroundResource(R.drawable.pause2);
                        ActivityAudioVideoMixer.aBoolean = Boolean.valueOf(ActivityAudioVideoMixer.BOOLEAN);
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }
                ActivityAudioVideoMixer.this.i();
            }
        });
        videoView.setOnCompletionListener(new OnCompletionListener() {
            public void onCompletion(MediaPlayer mediaPlayer) {
                ActivityAudioVideoMixer.videoView.seekTo(0);
                ActivityAudioVideoMixer.imageView.setBackgroundResource(R.drawable.play2);
            }
        });
    }


    public void i() {
        if (videoView.isPlaying()) {
            try {
                videoView.pause();
                editorVideoSliceSeekBar.setSliceBlocked(BOOLEAN);
                editorVideoSliceSeekBar.removeVideoStatusThumb();
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    try {
                        mediaPlayer.pause();
                        return;
                    } catch (IllegalStateException e2) {
                        e2.printStackTrace();
                    }
                }
                return;
            } catch (IllegalStateException e3) {
                e3.printStackTrace();
            }
        }
        videoView.seekTo(editorVideoSliceSeekBar.getLeftProgress());
        videoView.start();
        editorVideoSliceSeekBar.videoPlayingProgress(editorVideoSliceSeekBar.getLeftProgress());
        a.a();
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


    @Override
    public void onResume() {
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
                        ActivityAudioVideoMixer.editorAudioSeekBar.setSeekBarChangeListener(new EditorAudioSeekBar.SeekBarChangeListener() {
                            public void SeekBarValueChanged(int i, int i2) {
                                if (ActivityAudioVideoMixer.editorAudioSeekBar.getSelectedThumb() == 1) {
                                    ActivityAudioVideoMixer.mediaPlayer.seekTo(ActivityAudioVideoMixer.editorAudioSeekBar.getLeftProgress());
                                }
                                ActivityAudioVideoMixer.this.textView3.setText(ActivityAudioVideoMixer.formatTimeUnit(i, ActivityAudioVideoMixer.BOOLEAN));
                                ActivityAudioVideoMixer.textView.setText(ActivityAudioVideoMixer.formatTimeUnit(i2, ActivityAudioVideoMixer.BOOLEAN));
                                if (ActivityAudioVideoMixer.videoView != null && ActivityAudioVideoMixer.videoView.isPlaying()) {
                                    ActivityAudioVideoMixer.videoView.seekTo(ActivityAudioVideoMixer.editorVideoSliceSeekBar.getLeftProgress());
                                    ActivityAudioVideoMixer.videoView.start();
                                    ActivityAudioVideoMixer.editorVideoSliceSeekBar.videoPlayingProgress(ActivityAudioVideoMixer.editorVideoSliceSeekBar.getLeftProgress());
                                    ActivityAudioVideoMixer.a.a();
                                }
                            }
                        });
                        ActivityAudioVideoMixer.editorAudioSeekBar.setMaxValue(mediaPlayer.getDuration());
                        ActivityAudioVideoMixer.editorAudioSeekBar.setLeftProgress(0);
                        ActivityAudioVideoMixer.editorAudioSeekBar.setRightProgress(mediaPlayer.getDuration());
                        ActivityAudioVideoMixer.editorAudioSeekBar.setProgressMinDiff(0);
                        ActivityAudioVideoMixer.this.textView3.setText("00:00");
                        try {
                            ActivityAudioVideoMixer.textView.setText(ActivityAudioVideoMixer.formatTimeUnit1(mediaPlayer.getDuration()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                mediaPlayer.setOnErrorListener(new OnErrorListener() {
                    public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                        return ActivityAudioVideoMixer.BOOLEAN;
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

    private void j() {
        try {
            this.ffmpeg.loadBinary(new LoadBinaryResponseHandler() {
                @Override
                public void onFailure() {
                    ActivityAudioVideoMixer.this.k();
                    Log.d("ffmpeg loading failed! ", "");
                }

                @Override public void onFinish() {
                    Log.d("ffmpeg loading finish! ", "");
                }

                @Override
                public void onStart() {
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
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ActivityAudioVideoMixer.this.finish();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AddAudio.status = 0;
        AddAudio.audioPath = "";
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        Intent intent = new Intent(this, ActivityVideoList.class);
        intent.setFlags(67108864);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picker, menu);
        return BOOLEAN;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
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
            Intent intent = new Intent(this, ActivityVideoList.class);
            intent.setFlags(67108864);
            startActivity(intent);
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
        return super.onOptionsItemSelected(menuItem);
    }
}
