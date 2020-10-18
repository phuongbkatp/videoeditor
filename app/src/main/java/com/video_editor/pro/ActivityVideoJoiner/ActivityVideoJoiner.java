package com.video_editor.pro.ActivityVideoJoiner;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.OnScanCompletedListener;
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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.video_editor.pro.R;
import com.video_editor.pro.UtilsAndAdapters.EditorVideoSliceSeekBar;
import com.video_editor.pro.UtilsAndAdapters.EditorVideoSliceSeekBar.SeekBarChangeListener;
import com.video_editor.pro.ActivityVideoJoiner.JoinerModels.VideoPlayerState;
import com.video_editor.pro.ActivityVideoJoiner.util.FileUtils;
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
import java.util.concurrent.TimeUnit;

@SuppressLint({"WrongConstant"})
public class ActivityVideoJoiner extends AppCompatActivity implements OnClickListener {
    static final boolean BOOLEAN = true;
    int anInt = 0;
    int anInt1 = 0;
    Handler handler = new Handler();
    String string;
    ImageView imageView;
    ImageView imageView1;
    public FFmpeg ffmpeg;
    TextView textView;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    EditorVideoSliceSeekBar videoSliceSeekBar;
    EditorVideoSliceSeekBar videoSliceSeekBar1;
    int anInt2 = 0;
    int anInt3 = 0;
    VideoView videoView;
    private UnifiedNativeAd nativeAd;

    VideoView view;
    Runnable runnable = new Runnable() {
        public void run() {
            ActivityVideoJoiner.this.b();
        }
    };

    public VideoPlayerState playerState = new VideoPlayerState();

    public VideoPlayerState playerState1 = new VideoPlayerState();
    private a a = new a();
    private b v = new b();
    private InterstitialAd interstitialAd;

    private class a extends Handler {
        private boolean aBoolean;
        private Runnable runnable1;

        private a() {
            this.aBoolean = false;
            this.runnable1 = new Runnable() {
                public void run() {
                    a.this.a();
                }
            };
        }


        public void a() {
            if (!this.aBoolean) {
                this.aBoolean = ActivityVideoJoiner.BOOLEAN;
                sendEmptyMessage(0);
            }
        }

        @Override public void handleMessage(Message message) {
            this.aBoolean = false;
            ActivityVideoJoiner.this.videoSliceSeekBar.videoPlayingProgress(ActivityVideoJoiner.this.videoView.getCurrentPosition());
            if (!ActivityVideoJoiner.this.videoView.isPlaying() || ActivityVideoJoiner.this.videoView.getCurrentPosition() >= ActivityVideoJoiner.this.videoSliceSeekBar.getRightProgress()) {
                if (ActivityVideoJoiner.this.videoView.isPlaying()) {
                    ActivityVideoJoiner.this.videoView.pause();
                    ActivityVideoJoiner.this.imageView.setBackgroundResource(R.drawable.play2);
                }
                ActivityVideoJoiner.this.videoSliceSeekBar.setSliceBlocked(false);
                ActivityVideoJoiner.this.videoSliceSeekBar.removeVideoStatusThumb();
                return;
            }
            postDelayed(this.runnable1, 50);
        }
    }

    private class b extends Handler {
        private boolean aBoolean;
        private Runnable runnable1;

        private b() {
            this.aBoolean = false;
            this.runnable1 = new Runnable() {
                public void run() {
                    b.this.a();
                }
            };
        }






        public void a() {
            if (!this.aBoolean) {
                this.aBoolean = ActivityVideoJoiner.BOOLEAN;
                sendEmptyMessage(0);
            }
        }




        @Override public void handleMessage(Message message) {
            this.aBoolean = false;
            ActivityVideoJoiner.this.videoSliceSeekBar1.videoPlayingProgress(ActivityVideoJoiner.this.view.getCurrentPosition());
            if (!ActivityVideoJoiner.this.view.isPlaying() || ActivityVideoJoiner.this.view.getCurrentPosition() >= ActivityVideoJoiner.this.videoSliceSeekBar1.getRightProgress()) {
                if (ActivityVideoJoiner.this.view.isPlaying()) {
                    ActivityVideoJoiner.this.view.pause();
                    ActivityVideoJoiner.this.imageView1.setBackgroundResource(R.drawable.play2);
                }
                ActivityVideoJoiner.this.videoSliceSeekBar1.setSliceBlocked(false);
                ActivityVideoJoiner.this.videoSliceSeekBar1.removeVideoStatusThumb();
                return;
            }
            postDelayed(this.runnable1, 50);
        }
    }


    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView( R.layout.activity_video_joiner);


        Toolbar toolbar = findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Video Joiner");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (BOOLEAN || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(BOOLEAN);
            supportActionBar.setDisplayShowTitleEnabled(false);
            i();
            this.ffmpeg = FFmpeg.getInstance(this);
            j();
            this.videoView.setVideoPath(FileUtils.myUri.get(0));
            this.view.setVideoPath(FileUtils.myUri.get(1));
            this.videoView.seekTo(100);
            this.view.seekTo(100);
            d();
            f();
            this.videoView.setOnCompletionListener(new OnCompletionListener() {
                public void onCompletion(MediaPlayer mediaPlayer) {
                    ActivityVideoJoiner.this.imageView.setBackgroundResource(R.drawable.play2);
                }
            });
            this.view.setOnCompletionListener(new OnCompletionListener() {
                public void onCompletion(MediaPlayer mediaPlayer) {
                    ActivityVideoJoiner.this.imageView1.setBackgroundResource(R.drawable.play2);
                }
            });
            this.interstitialAd = new InterstitialAd(this);
            this.interstitialAd.setAdUnitId(getString(R.string.interstitial_id_admob));
            this.interstitialAd.setAdListener(new AdListener() {
                @Override public void onAdClosed() {
                    ActivityVideoJoiner.this.c();
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
                FrameLayout nativeAdPlaceHolder = ActivityVideoJoiner.this.findViewById(R.id.nativeAdPlaceHolder);
                @SuppressLint("InflateParams") UnifiedNativeAdView adView = (UnifiedNativeAdView) ActivityVideoJoiner.this.getLayoutInflater()
                        .inflate(R.layout.native_ad_unified_smaller, null);
                ActivityVideoJoiner.this.populateUnifiedNativeAdView(unifiedNativeAd, adView);
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

    @Override
    protected void onDestroy() {
        if (nativeAd != null) {
            nativeAd.destroy();
        }
        super.onDestroy();
    }








    public void c() {
        Intent intent = new Intent(this, ActivityAddAudio.class);
        Bundle bundle = new Bundle();
        bundle.putString("song", this.string);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public static String formatTimeUnit(long j2) throws ParseException {
        return String.format("%02d:%02d", Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(j2)), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j2) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j2))));
    }

    private void d() {
        this.videoView.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                ActivityVideoJoiner.this.videoSliceSeekBar.setSeekBarChangeListener(new SeekBarChangeListener() {
                    public void SeekBarValueChanged(int i, int i2) {
                        if (ActivityVideoJoiner.this.videoSliceSeekBar.getSelectedThumb() == 1) {
                            ActivityVideoJoiner.this.videoView.seekTo(ActivityVideoJoiner.this.videoSliceSeekBar.getLeftProgress());
                        }
                        ActivityVideoJoiner.this.textView2.setText(ActivityVideoJoiner.formatTimeUnit(i));
                        ActivityVideoJoiner.this.textView.setText(ActivityVideoJoiner.formatTimeUnit(i2));
                        ActivityVideoJoiner.this.playerState.setStart(i);
                        ActivityVideoJoiner.this.playerState.setStop(i2);
                        ActivityVideoJoiner.this.anInt2 = i / 1000;
                        ActivityVideoJoiner.this.anInt = i2 / 1000;
                    }
                });
                ActivityVideoJoiner.this.videoSliceSeekBar.setMaxValue(mediaPlayer.getDuration());
                ActivityVideoJoiner.this.videoSliceSeekBar.setLeftProgress(0);
                ActivityVideoJoiner.this.videoSliceSeekBar.setRightProgress(mediaPlayer.getDuration());
                ActivityVideoJoiner.this.videoSliceSeekBar.setProgressMinDiff(0);
            }
        });
    }

    private void e() {
        if (this.view.isPlaying()) {
            this.view.pause();
            this.imageView1.setBackgroundResource(R.drawable.play2);
        }
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
        this.a.a();
    }

    private void f() {
        this.view.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                ActivityVideoJoiner.this.videoSliceSeekBar1.setSeekBarChangeListener(new SeekBarChangeListener() {
                    public void SeekBarValueChanged(int i, int i2) {
                        if (ActivityVideoJoiner.this.videoSliceSeekBar1.getSelectedThumb() == 1) {
                            ActivityVideoJoiner.this.view.seekTo(ActivityVideoJoiner.this.videoSliceSeekBar1.getLeftProgress());
                        }
                        ActivityVideoJoiner.this.textView3.setText(ActivityVideoJoiner.formatTimeUnit(i));
                        ActivityVideoJoiner.this.textView1.setText(ActivityVideoJoiner.formatTimeUnit(i2));
                        ActivityVideoJoiner.this.playerState1.setStart(i);
                        ActivityVideoJoiner.this.playerState1.setStop(i2);
                        ActivityVideoJoiner.this.anInt3 = i / 1000;
                        ActivityVideoJoiner.this.anInt1 = i2 / 1000;
                    }
                });
                ActivityVideoJoiner.this.videoSliceSeekBar1.setMaxValue(mediaPlayer.getDuration());
                ActivityVideoJoiner.this.videoSliceSeekBar1.setLeftProgress(0);
                ActivityVideoJoiner.this.videoSliceSeekBar1.setRightProgress(mediaPlayer.getDuration());
                ActivityVideoJoiner.this.videoSliceSeekBar1.setProgressMinDiff(0);
            }
        });
    }

    private void g() {
        if (this.videoView.isPlaying()) {
            this.videoView.pause();
            this.imageView.setBackgroundResource(R.drawable.play2);
        }
        if (this.view.isPlaying()) {
            this.view.pause();
            this.videoSliceSeekBar1.setSliceBlocked(false);
            this.imageView1.setBackgroundResource(R.drawable.play2);
            this.videoSliceSeekBar1.removeVideoStatusThumb();
            return;
        }
        this.view.seekTo(this.videoSliceSeekBar1.getLeftProgress());
        this.view.start();
        this.videoSliceSeekBar1.videoPlayingProgress(this.videoSliceSeekBar1.getLeftProgress());
        this.imageView1.setBackgroundResource(R.drawable.pause2);
        this.v.a();
    }

    @Override public void onClick(View view) {
        if (view == this.imageView) {
            e();
        }
        if (view == this.imageView1) {
            g();
        }
    }

    private void h() {
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
        sb2.append(".mp4");
        this.string = sb2.toString();
        a(new String[]{"-y", "-ss", String.valueOf(this.anInt2), "-t", String.valueOf(this.anInt), "-i", FileUtils.myUri.get(0), "-ss", String.valueOf(this.anInt3), "-t", String.valueOf(this.anInt1), "-i", FileUtils.myUri.get(1), "-strict", "experimental", "-filter_complex", "[0:v]scale=320x240,setsar=1:1[v0];[1:v]scale=320x240,setsar=1:1[v1];[v0][v1] concat=n=2:v=1", "-ab", "48000", "-ac", "2", "-ar", "22050", "-s", "320x240", "-r", "15", "-b", "2097k", "-vcodec", "mpeg4", this.string}, this.string);
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
                        ActivityVideoJoiner.this.deleteFromGallery(str);
                        Toast.makeText(ActivityVideoJoiner.this, "Error Creating Video", 0).show();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }

                @Override public void onSuccess(String str) {
                    progressDialog.dismiss();
                    Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                    intent.setData(Uri.fromFile(new File(ActivityVideoJoiner.this.string)));
                    ActivityVideoJoiner.this.sendBroadcast(intent);
                    try {
                        MediaScannerConnection.scanFile(ActivityVideoJoiner.this, new String[]{ActivityVideoJoiner.this.string}, null, new OnScanCompletedListener() {
                            public void onScanCompleted(String str, Uri uri) {
                                ActivityVideoJoiner.this.handler.postDelayed(ActivityVideoJoiner.this.runnable, 100);
                            }
                        });
                    } catch (Exception unused) {
                        ActivityVideoJoiner.this.handler.postDelayed(ActivityVideoJoiner.this.runnable, 100);
                    }
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
                    ActivityVideoJoiner.this.refreshGallery(str);
                }
            });
            getWindow().clearFlags(16);
        } catch (FFmpegCommandAlreadyRunningException unused) {
        }
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

    private void i() {
        this.imageView = findViewById(R.id.buttonply1);
        this.imageView1 = findViewById(R.id.buttonply2);
        this.videoSliceSeekBar = findViewById(R.id.seek_bar1);
        this.videoSliceSeekBar1 = findViewById(R.id.seek_bar2);
        this.textView2 = findViewById(R.id.left_pointer1);
        this.textView3 = findViewById(R.id.left_pointer2);
        this.textView = findViewById(R.id.right_pointer1);
        this.textView1 = findViewById(R.id.right_pointer2);
        this.videoView = findViewById(R.id.videoView1);
        this.view = findViewById(R.id.videoView2);
        this.imageView.setOnClickListener(this);
        this.imageView1.setOnClickListener(this);
    }

    private void j() {
        try {
            this.ffmpeg.loadBinary(new LoadBinaryResponseHandler() {
                @Override public void onFailure() {
                    ActivityVideoJoiner.this.k();
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
                ActivityVideoJoiner.this.finish();
            }
        }).create().show();
    }


    public void onPause() {
        super.onPause();
        if (this.videoView.isPlaying()) {
            this.videoView.pause();
            this.imageView.setBackgroundResource(R.drawable.play2);
        } else if (this.view.isPlaying()) {
            this.view.pause();
            this.imageView1.setBackgroundResource(R.drawable.play2);
        }
    }

    @Override public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), ActivityVideoList.class);
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
            } else if (this.view.isPlaying()) {
                this.view.pause();
                this.imageView1.setBackgroundResource(R.drawable.play2);
            }
            h();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
