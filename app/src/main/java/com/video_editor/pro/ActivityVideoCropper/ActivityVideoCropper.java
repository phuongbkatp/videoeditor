package com.video_editor.pro.ActivityVideoCropper;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.StatFs;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Video.Media;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.video_editor.pro.ActivityVideoList.ActivityVideoList;
import com.video_editor.pro.UtilsAndAdapters.EditorHelper;
import com.video_editor.pro.R;
import com.video_editor.pro.UtilsAndAdapters.EditorVideoPlayer;
import com.video_editor.pro.UtilsAndAdapters.EditorVideoPlayerState;
import com.video_editor.pro.UtilsAndAdapters.EditorVideoSliceSeekBar;
import com.video_editor.pro.UtilsAndAdapters.EditorVideoSliceSeekBar.SeekBarChangeListener;
import com.edmodo.cropper.CropImageView;
import com.edmodo.cropper.cropwindow.edge.Edge;
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

@SuppressLint({"WrongConstant"})
public class ActivityVideoCropper extends AppCompatActivity {
    TextView Rs;
    static final boolean BOOLEAN = true;
    int anInt;
    int anInt1;
    String string;
    String string1;
    String string2;
    String string3;
    private UnifiedNativeAd nativeAd;

    String string4 = "00";
    String string5;
    ImageView imageView;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;
    TextView textView;
    TextView textView1;
    Bitmap bitmap;
    View view;
    EditorVideoPlayerState editorVideoPlayerState = new EditorVideoPlayerState();
    EditorVideoSliceSeekBar videoSliceSeekBar;
    a Y = new a();
    VideoView videoView;
    CropImageView cropImageView;
    float aFloat;
    float aFloat1;
    float aFloat2;
    float aFloat3;
    long aLong;
    private int anInt2;
    private int anInt3;
    private InterstitialAd interstitialAd;
    String string6;
    ImageButton imageButton;
    ImageButton imageButton1;
    ImageButton imageButton2;
    ImageButton imageButton3;
    public FFmpeg ffmpeg;
    ImageButton imageButton4;
    ImageButton imageButton5;
    ImageButton imageButton6;
    ImageButton imageButton7;
    ImageButton imageButton8;
    String string7;
    int anInt4;
    int anInt5;
    int anInt6;
    int anInt7;
    int anInt8;
    int anInt9;
    int anInt10;
    int anInt11;
    int anInt12;
    int anInt13;
    int anInt14;
    int anInt15;
    int anInt16;
    int anInt17;

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
                this.aBoolean = ActivityVideoCropper.BOOLEAN;
                sendEmptyMessage(0);
            }
        }

        @Override public void handleMessage(Message message) {
            this.aBoolean = false;
            ActivityVideoCropper.this.videoSliceSeekBar.videoPlayingProgress(ActivityVideoCropper.this.videoView.getCurrentPosition());
            if (!ActivityVideoCropper.this.videoView.isPlaying() || ActivityVideoCropper.this.videoView.getCurrentPosition() >= ActivityVideoCropper.this.videoSliceSeekBar.getRightProgress()) {
                if (ActivityVideoCropper.this.videoView.isPlaying()) {
                    ActivityVideoCropper.this.videoView.pause();
                    ActivityVideoCropper.this.view.setBackgroundResource(R.drawable.play2);
                    ActivityVideoCropper.this.videoView.seekTo(100);
                }
                ActivityVideoCropper.this.videoSliceSeekBar.setSliceBlocked(false);
                ActivityVideoCropper.this.videoSliceSeekBar.removeVideoStatusThumb();
                return;
            }
            postDelayed(this.runnable, 50);
        }
    }


    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView( R.layout.activity_video_cropper);

        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView textView = toolbar.findViewById(R.id.toolbar_title);
        textView.setText("Video Crop");
        refreshAd();
        textView.setTypeface(EditorHelper.txtface);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (BOOLEAN || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(BOOLEAN);
            supportActionBar.setDisplayShowTitleEnabled(false);
            this.string2 = getIntent().getStringExtra("videofilename");
            if (this.string2 != null) {
                this.bitmap = ThumbnailUtils.createVideoThumbnail(this.string2, 1);
            }
            FrameLayout frameLayout = findViewById(R.id.rl_container);
            LayoutParams layoutParams = (LayoutParams) frameLayout.getLayoutParams();
            this.anInt15 = CropperFileUtils.getWidth();
            layoutParams.width = this.anInt15;
            layoutParams.height = this.anInt15;
            frameLayout.setLayoutParams(layoutParams);
            this.cropImageView = findViewById(R.id.cropperView);
            d();
            this.ffmpeg = FFmpeg.getInstance(this);
            i();
            e();
            this.interstitialAd = new InterstitialAd(this);
            this.interstitialAd.setAdUnitId(getString(R.string.interstitial_id_admob));
            this.interstitialAd.setAdListener(new AdListener() {
                @Override public void onAdClosed() {
                    ActivityVideoCropper.this.c();
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
                FrameLayout nativeAdPlaceHolder = ActivityVideoCropper.this.findViewById(R.id.nativeAdPlaceHolder);
                @SuppressLint("InflateParams") UnifiedNativeAdView adView = (UnifiedNativeAdView) ActivityVideoCropper.this.getLayoutInflater()
                        .inflate(R.layout.native_ad_unified_smaller, null);
                ActivityVideoCropper.this.populateUnifiedNativeAdView(unifiedNativeAd, adView);
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
        Intent intent = new Intent(getApplicationContext(), EditorVideoPlayer.class);
        intent.setFlags(67108864);
        intent.putExtra("song", this.string1);
        startActivity(intent);
        finish();
    }

    public void cropcommand() {
        h();
        getpath();
    }

    @SuppressLint("InvalidWakeLockTag")
    public void getpath() {
        if (this.anInt14 == 90) {
            try {
                this.anInt6 = this.anInt1;
                int i2 = this.anInt17;
                this.anInt12 = this.anInt1;
                this.anInt13 = this.anInt;
                this.anInt4 = this.anInt16;
                this.anInt5 = this.anInt17;
                this.anInt10 = this.anInt16;
                this.anInt11 = this.anInt;
                this.anInt2 = this.anInt4 - this.anInt6;
                this.anInt3 = this.anInt13 - i2;
                this.anInt7 = this.anInt8 - (this.anInt3 + i2);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else if (this.anInt14 == 270) {
            try {
                int i3 = this.anInt1;
                int i4 = this.anInt17;
                this.anInt12 = this.anInt1;
                this.anInt13 = this.anInt;
                this.anInt4 = this.anInt16;
                this.anInt5 = this.anInt17;
                this.anInt10 = this.anInt16;
                this.anInt11 = this.anInt;
                this.anInt2 = this.anInt4 - i3;
                this.anInt3 = this.anInt13 - i4;
                this.anInt6 = this.anInt9 - (this.anInt2 + i3);
                this.anInt7 = i4;
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        } else {
            try {
                this.anInt6 = this.anInt17;
                this.anInt7 = this.anInt1;
                this.anInt12 = this.anInt;
                this.anInt13 = this.anInt1;
                this.anInt4 = this.anInt17;
                this.anInt5 = this.anInt16;
                this.anInt10 = this.anInt;
                this.anInt11 = this.anInt16;
                this.anInt2 = this.anInt12 - this.anInt6;
                this.anInt3 = this.anInt5 - this.anInt7;
            } catch (Exception e4) {
                e4.printStackTrace();
            }
        }
        this.string5 = String.valueOf(this.editorVideoPlayerState.getStart() / 1000);
        this.string3 = String.valueOf(this.editorVideoPlayerState.getDuration() / 1000);
        this.string7 = this.string2;
        if (this.string7.contains(".3gp") || this.string7.contains(".3GP")) {
            try {
                this.string = CropperFileUtils.getFileName(this, this.string7.replace(".3gp", ".mp4"));
            } catch (Exception e5) {
                e5.printStackTrace();
            }
        } else if (this.string7.contains(".flv") || this.string7.contains(".FLv")) {
            try {
                this.string = CropperFileUtils.getFileName(this, this.string7.replace(".flv", ".mp4"));
            } catch (Exception e6) {
                e6.printStackTrace();
            }
        } else if (this.string7.contains(".mov") || this.string7.contains(".MOV")) {
            try {
                this.string = CropperFileUtils.getFileName(this, this.string7.replace(".mov", ".mp4"));
            } catch (Exception e7) {
                e7.printStackTrace();
            }
        } else if (this.string7.contains(".wmv") || this.string7.contains(".WMV")) {
            try {
                this.string = CropperFileUtils.getFileName(this, this.string7.replace(".wmv", ".mp4"));
            } catch (Exception e8) {
                e8.printStackTrace();
            }
        } else {
            try {
                this.string = CropperFileUtils.getFileName(this, this.string7);
            } catch (Exception e9) {
                e9.printStackTrace();
            }
        }
        this.string1 = CropperFileUtils.getFileName(this, this.string);
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long availableBlocks = ((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize());
        File file = new File(this.editorVideoPlayerState.getFilename());
        this.aLong = 0;
        this.aLong = file.length() / 1024;
        if ((availableBlocks / 1024) / 1024 >= this.aLong / 1024) {
            ((PowerManager) getSystemService(Context.POWER_SERVICE)).newWakeLock(1, "VK_LOCK").acquire();
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("crop=w=");
                sb.append(this.anInt2);
                sb.append(":h=");
                sb.append(this.anInt3);
                sb.append(":x=");
                sb.append(this.anInt6);
                sb.append(":y=");
                sb.append(this.anInt7);
                a(new String[]{"-y", "-ss", this.string5, "-t", this.string3, "-i", this.string7, "-strict", "experimental", "-vf", sb.toString(), "-r", "15", "-ab", "128k", "-vcodec", "mpeg4", "-acodec", "copy", "-b:v", "2500k", "-sample_fmt", "s16", "-ss", "0", "-t", this.string3, this.string1}, this.string1);
            } catch (Exception unused) {
                File file2 = new File(this.string1);
                if (file2.exists()) {
                    file2.delete();
                    finish();
                    return;
                }
                Toast.makeText(this, "please select any option!", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Out of Memory!......", 0).show();
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
                        ActivityVideoCropper.this.deleteFromGallery(str);
                        Toast.makeText(ActivityVideoCropper.this, "Error Creating Video", Toast.LENGTH_LONG).show();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }

                @Override public void onSuccess(String str) {
                    progressDialog.dismiss();
                    Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                    intent.setData(Uri.fromFile(new File(ActivityVideoCropper.this.string1)));
                    ActivityVideoCropper.this.sendBroadcast(intent);
                    ActivityVideoCropper.this.b();
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
                    ActivityVideoCropper.this.refreshGallery(str);
                }
            });
            getWindow().clearFlags(16);
        } catch (FFmpegCommandAlreadyRunningException unused) {
        }
    }


    @Override public void onResume() {
        this.videoView.seekTo(this.editorVideoPlayerState.getCurrentTime());
        super.onResume();
    }


    public void onPause() {
        super.onPause();
        this.editorVideoPlayerState.setCurrentTime(this.videoView.getCurrentPosition());
    }

    private void d() {
        this.Rs = findViewById(R.id.left_pointer);
        this.textView = findViewById(R.id.right_pointer);
        this.videoSliceSeekBar = findViewById(R.id.seek_bar);
        this.textView1 = findViewById(R.id.Filename);
        this.textView1.setText(new File(this.string2).getName());
        this.view = findViewById(R.id.buttonply);
        this.view.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                if (ActivityVideoCropper.this.videoView == null || !ActivityVideoCropper.this.videoView.isPlaying()) {
                    ActivityVideoCropper.this.view.setBackgroundResource(R.drawable.pause2);
                } else {
                    ActivityVideoCropper.this.view.setBackgroundResource(R.drawable.play2);
                }
                ActivityVideoCropper.this.g();
            }
        });
        Object lastNonConfigurationInstance = getLastNonConfigurationInstance();
        if (lastNonConfigurationInstance != null) {
            this.editorVideoPlayerState = (EditorVideoPlayerState) lastNonConfigurationInstance;
        } else {
            this.editorVideoPlayerState.setFilename(this.string2);
        }
        this.imageView6 = findViewById(R.id.slideimbtn_seven);
        this.imageView1 = findViewById(R.id.slideimbtn_eight);
        this.imageView2 = findViewById(R.id.slideimbtn_five);
        this.imageView8 = findViewById(R.id.slideimbtn_three);
        this.imageView = findViewById(R.id.slideimbtn_cland);
        this.imageView5 = findViewById(R.id.slideimbtn_port);
        this.imageView7 = findViewById(R.id.slideimbtn_square);
        this.imageView4 = findViewById(R.id.slideimbtn_o);
        this.imageView3 = findViewById(R.id.slideimbtn_45);
        this.imageButton = findViewById(R.id.imbtn_custom);
        this.imageButton.setOnClickListener(setRatioOriginal());
        this.imageButton2 = findViewById(R.id.imgbtn_eight);
        this.imageButton2.setOnClickListener(setRatioEight());
        this.imageButton6 = findViewById(R.id.imgbtn_seven);
        this.imageButton6.setOnClickListener(setRatioSeven());
        this.imageButton3 = findViewById(R.id.imgbtn_five);
        this.imageButton3.setOnClickListener(setRatioFive());
        this.imageButton8 = findViewById(R.id.imgbtn_three);
        this.imageButton8.setOnClickListener(setRatioThree());
        this.imageButton7 = findViewById(R.id.imgbtn_square);
        this.imageButton7.setOnClickListener(setRatioSqaure());
        this.imageButton5 = findViewById(R.id.imgbtn_port);
        this.imageButton5.setOnClickListener(setRatioPort());
        this.imageButton1 = findViewById(R.id.imgbtn_cland);
        this.imageButton1.setOnClickListener(setRatioLand());
        this.imageButton4 = findViewById(R.id.imgbtn_45);
        this.imageButton4.setOnClickListener(setRatioNine());
    }

    @SuppressLint({"NewApi"})
    private void e() {
        this.videoView = findViewById(R.id.videoview);
        this.videoView.setVideoPath(this.string2);
        this.string6 = getTimeForTrackFormat(this.videoView.getDuration(), BOOLEAN);
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(this.string2);
        this.anInt9 = Integer.valueOf(mediaMetadataRetriever.extractMetadata(18)).intValue();
        this.anInt8 = Integer.valueOf(mediaMetadataRetriever.extractMetadata(19)).intValue();
        if (VERSION.SDK_INT > 16) {
            this.anInt14 = Integer.valueOf(mediaMetadataRetriever.extractMetadata(24)).intValue();
        } else {
            this.anInt14 = 0;
        }
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.cropImageView.getLayoutParams();
        if (this.anInt14 == 90 || this.anInt14 == 270) {
            if (this.anInt9 >= this.anInt8) {
                if (this.anInt9 >= this.anInt15) {
                    layoutParams.height = this.anInt15;
                    layoutParams.width = (int) (((float) this.anInt15) / (((float) this.anInt9) / ((float) this.anInt8)));
                } else {
                    layoutParams.width = this.anInt15;
                    layoutParams.height = (int) (((float) this.anInt8) * (((float) this.anInt15) / ((float) this.anInt9)));
                }
            } else if (this.anInt8 >= this.anInt15) {
                layoutParams.width = this.anInt15;
                layoutParams.height = (int) (((float) this.anInt15) / (((float) this.anInt8) / ((float) this.anInt9)));
            } else {
                layoutParams.width = (int) (((float) this.anInt9) * (((float) this.anInt15) / ((float) this.anInt8)));
                layoutParams.height = this.anInt15;
            }
        } else if (this.anInt9 >= this.anInt8) {
            if (this.anInt9 >= this.anInt15) {
                layoutParams.width = this.anInt15;
                layoutParams.height = (int) (((float) this.anInt15) / (((float) this.anInt9) / ((float) this.anInt8)));
            } else {
                layoutParams.width = this.anInt15;
                layoutParams.height = (int) (((float) this.anInt8) * (((float) this.anInt15) / ((float) this.anInt9)));
            }
        } else if (this.anInt8 >= this.anInt15) {
            layoutParams.width = (int) (((float) this.anInt15) / (((float) this.anInt8) / ((float) this.anInt9)));
            layoutParams.height = this.anInt15;
        } else {
            layoutParams.width = (int) (((float) this.anInt9) * (((float) this.anInt15) / ((float) this.anInt8)));
            layoutParams.height = this.anInt15;
        }
        this.cropImageView.setLayoutParams(layoutParams);
        this.cropImageView.setImageBitmap(Bitmap.createBitmap(layoutParams.width, layoutParams.height, Config.ARGB_8888));
        try {
            SearchVideo(getApplicationContext(), this.string2, layoutParams.width, layoutParams.height);
        } catch (Exception unused) {
        }
        this.videoView.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                ActivityVideoCropper.this.videoSliceSeekBar.setSeekBarChangeListener(new SeekBarChangeListener() {
                    public void SeekBarValueChanged(int i, int i2) {
                        if (ActivityVideoCropper.this.videoSliceSeekBar.getSelectedThumb() == 1) {
                            ActivityVideoCropper.this.videoView.seekTo(ActivityVideoCropper.this.videoSliceSeekBar.getLeftProgress());
                        }
                        ActivityVideoCropper.this.Rs.setText(ActivityVideoCropper.getTimeForTrackFormat(i, ActivityVideoCropper.BOOLEAN));
                        ActivityVideoCropper.this.textView.setText(ActivityVideoCropper.getTimeForTrackFormat(i2, ActivityVideoCropper.BOOLEAN));
                        ActivityVideoCropper.this.string4 = ActivityVideoCropper.getTimeForTrackFormat(i, ActivityVideoCropper.BOOLEAN);
                        ActivityVideoCropper.this.editorVideoPlayerState.setStart(i);
                        ActivityVideoCropper.this.string6 = ActivityVideoCropper.getTimeForTrackFormat(i2, ActivityVideoCropper.BOOLEAN);
                        ActivityVideoCropper.this.editorVideoPlayerState.setStop(i2);
                    }
                });
                ActivityVideoCropper.this.string6 = ActivityVideoCropper.getTimeForTrackFormat(mediaPlayer.getDuration(), ActivityVideoCropper.BOOLEAN);
                ActivityVideoCropper.this.videoSliceSeekBar.setMaxValue(mediaPlayer.getDuration());
                ActivityVideoCropper.this.videoSliceSeekBar.setLeftProgress(0);
                ActivityVideoCropper.this.videoSliceSeekBar.setRightProgress(mediaPlayer.getDuration());
                ActivityVideoCropper.this.videoSliceSeekBar.setProgressMinDiff(0);
                ActivityVideoCropper.this.videoView.seekTo(100);
            }
        });
    }


    public void f() {
        this.imageButton.setBackgroundResource(R.drawable.ic_crop_custom);
        this.imageButton2.setBackgroundResource(R.drawable.ic_crop_35);
        this.imageButton6.setBackgroundResource(R.drawable.ic_crop_34);
        this.imageButton3.setBackgroundResource(R.drawable.ic_crop_32);
        this.imageButton8.setBackgroundResource(R.drawable.ic_crop_23);
        this.imageButton7.setBackgroundResource(R.drawable.ic_crop_square);
        this.imageButton5.setBackgroundResource(R.drawable.ic_crop_portrait);
        this.imageButton1.setBackgroundResource(R.drawable.ic_crop_landscape);
        this.imageButton4.setBackgroundResource(R.drawable.ic_crop_45);
    }


    public void g() {
        if (this.videoView.isPlaying()) {
            this.videoView.pause();
            this.videoSliceSeekBar.setSliceBlocked(false);
            this.videoSliceSeekBar.removeVideoStatusThumb();
            return;
        }
        this.videoView.seekTo(this.videoSliceSeekBar.getLeftProgress());
        this.videoView.start();
        this.videoSliceSeekBar.videoPlayingProgress(this.videoSliceSeekBar.getLeftProgress());
        this.Y.a();
    }

    public static String getTimeForTrackFormat(int i2, boolean z2) {
        String str;
        int i3 = i2 / 60000;
        int i4 = (i2 - ((i3 * 60) * 1000)) / 1000;
        StringBuilder sb = new StringBuilder((!z2 || i3 >= 10) ? "" : "0");
        sb.append(i3 % 60);
        sb.append(":");
        String sb2 = sb.toString();
        if (i4 < 10) {
            StringBuilder sb3 = new StringBuilder(sb2);
            sb3.append("0");
            sb3.append(i4);
            str = sb3.toString();
        } else {
            StringBuilder sb4 = new StringBuilder(sb2);
            sb4.append(i4);
            str = sb4.toString();
        }
        StringBuilder sb5 = new StringBuilder();
        sb5.append("Display Result");
        sb5.append(str);
        Log.e("", sb5.toString());
        return str;
    }

    private void h() {
        if (this.anInt14 == 90 || this.anInt14 == 270) {
            this.aFloat = (float) this.anInt8;
            this.aFloat1 = (float) this.anInt9;
            this.aFloat2 = (float) this.cropImageView.getWidth();
            this.aFloat3 = (float) this.cropImageView.getHeight();
            this.anInt17 = (int) ((Edge.LEFT.getCoordinate() * this.aFloat) / this.aFloat2);
            this.anInt = (int) ((Edge.RIGHT.getCoordinate() * this.aFloat) / this.aFloat2);
            this.anInt1 = (int) ((Edge.TOP.getCoordinate() * this.aFloat1) / this.aFloat3);
            this.anInt16 = (int) ((Edge.BOTTOM.getCoordinate() * this.aFloat1) / this.aFloat3);
            return;
        }
        this.aFloat = (float) this.anInt9;
        this.aFloat1 = (float) this.anInt8;
        this.aFloat2 = (float) this.cropImageView.getWidth();
        this.aFloat3 = (float) this.cropImageView.getHeight();
        this.anInt17 = (int) ((Edge.LEFT.getCoordinate()  * this.aFloat) / this.aFloat2);
        this.anInt = (int) ((Edge.RIGHT.getCoordinate() * this.aFloat) / this.aFloat2);
        this.anInt1 = (int) ((Edge.TOP.getCoordinate() * this.aFloat1) / this.aFloat3);
        this.anInt16 = (int) ((Edge.BOTTOM.getCoordinate() * this.aFloat1) / this.aFloat3);
    }

    public OnClickListener setRatioOriginal() {
        return new OnClickListener() {
            @Override public void onClick(View view) {
                ActivityVideoCropper.this.cropImageView.setFixedAspectRatio(false);
                ActivityVideoCropper.this.f();
                ActivityVideoCropper.this.imageButton.setBackgroundResource(R.drawable.ic_crop_custom_press);
                ActivityVideoCropper.this.imageView1.setVisibility(8);
                ActivityVideoCropper.this.imageView6.setVisibility(8);
                ActivityVideoCropper.this.imageView.setVisibility(8);
                ActivityVideoCropper.this.imageView2.setVisibility(8);
                ActivityVideoCropper.this.imageView4.setVisibility(0);
                ActivityVideoCropper.this.imageView5.setVisibility(8);
                ActivityVideoCropper.this.imageView7.setVisibility(8);
                ActivityVideoCropper.this.imageView8.setVisibility(8);
                ActivityVideoCropper.this.imageView3.setVisibility(8);
            }
        };
    }

    public OnClickListener setRatioSqaure() {
        return new OnClickListener() {
            @Override public void onClick(View view) {
                ActivityVideoCropper.this.cropImageView.setFixedAspectRatio(ActivityVideoCropper.BOOLEAN);
                ActivityVideoCropper.this.cropImageView.setAspectRatio(10, 10);
                ActivityVideoCropper.this.f();
                ActivityVideoCropper.this.imageButton7.setBackgroundResource(R.drawable.ic_crop_square_press);
                ActivityVideoCropper.this.imageView1.setVisibility(8);
                ActivityVideoCropper.this.imageView6.setVisibility(8);
                ActivityVideoCropper.this.imageView.setVisibility(8);
                ActivityVideoCropper.this.imageView2.setVisibility(8);
                ActivityVideoCropper.this.imageView4.setVisibility(8);
                ActivityVideoCropper.this.imageView5.setVisibility(8);
                ActivityVideoCropper.this.imageView7.setVisibility(0);
                ActivityVideoCropper.this.imageView8.setVisibility(8);
                ActivityVideoCropper.this.imageView3.setVisibility(8);
            }
        };
    }

    public OnClickListener setRatioPort() {
        return new OnClickListener() {
            @Override public void onClick(View view) {
                ActivityVideoCropper.this.cropImageView.setFixedAspectRatio(ActivityVideoCropper.BOOLEAN);
                ActivityVideoCropper.this.cropImageView.setAspectRatio(8, 16);
                ActivityVideoCropper.this.f();
                ActivityVideoCropper.this.imageButton5.setBackgroundResource(R.drawable.ic_crop_portrait_press);
                ActivityVideoCropper.this.imageView1.setVisibility(8);
                ActivityVideoCropper.this.imageView6.setVisibility(8);
                ActivityVideoCropper.this.imageView.setVisibility(8);
                ActivityVideoCropper.this.imageView2.setVisibility(8);
                ActivityVideoCropper.this.imageView4.setVisibility(8);
                ActivityVideoCropper.this.imageView5.setVisibility(0);
                ActivityVideoCropper.this.imageView7.setVisibility(8);
                ActivityVideoCropper.this.imageView8.setVisibility(8);
                ActivityVideoCropper.this.imageView3.setVisibility(8);
            }
        };
    }

    public OnClickListener setRatioLand() {
        return new OnClickListener() {
            @Override public void onClick(View view) {
                ActivityVideoCropper.this.cropImageView.setFixedAspectRatio(ActivityVideoCropper.BOOLEAN);
                ActivityVideoCropper.this.cropImageView.setAspectRatio(16, 8);
                ActivityVideoCropper.this.f();
                ActivityVideoCropper.this.imageButton1.setBackgroundResource(R.drawable.ic_crop_landscape_press);
                ActivityVideoCropper.this.imageView1.setVisibility(8);
                ActivityVideoCropper.this.imageView6.setVisibility(8);
                ActivityVideoCropper.this.imageView.setVisibility(0);
                ActivityVideoCropper.this.imageView2.setVisibility(8);
                ActivityVideoCropper.this.imageView4.setVisibility(8);
                ActivityVideoCropper.this.imageView5.setVisibility(8);
                ActivityVideoCropper.this.imageView7.setVisibility(8);
                ActivityVideoCropper.this.imageView8.setVisibility(8);
                ActivityVideoCropper.this.imageView3.setVisibility(8);
            }
        };
    }

    public OnClickListener setRatioThree() {
        return new OnClickListener() {
            @Override public void onClick(View view) {
                ActivityVideoCropper.this.cropImageView.setFixedAspectRatio(ActivityVideoCropper.BOOLEAN);
                ActivityVideoCropper.this.cropImageView.setAspectRatio(3, 2);
                ActivityVideoCropper.this.f();
                ActivityVideoCropper.this.imageButton8.setBackgroundResource(R.drawable.ic_crop_23_press);
                ActivityVideoCropper.this.imageView1.setVisibility(8);
                ActivityVideoCropper.this.imageView6.setVisibility(8);
                ActivityVideoCropper.this.imageView.setVisibility(8);
                ActivityVideoCropper.this.imageView2.setVisibility(8);
                ActivityVideoCropper.this.imageView4.setVisibility(8);
                ActivityVideoCropper.this.imageView5.setVisibility(8);
                ActivityVideoCropper.this.imageView7.setVisibility(8);
                ActivityVideoCropper.this.imageView8.setVisibility(0);
                ActivityVideoCropper.this.imageView3.setVisibility(8);
            }
        };
    }

    public OnClickListener setRatioFive() {
        return new OnClickListener() {
            @Override public void onClick(View view) {
                ActivityVideoCropper.this.cropImageView.setFixedAspectRatio(ActivityVideoCropper.BOOLEAN);
                ActivityVideoCropper.this.cropImageView.setAspectRatio(2, 3);
                ActivityVideoCropper.this.f();
                ActivityVideoCropper.this.imageButton3.setBackgroundResource(R.drawable.ic_crop_32_press);
                ActivityVideoCropper.this.imageView1.setVisibility(8);
                ActivityVideoCropper.this.imageView6.setVisibility(8);
                ActivityVideoCropper.this.imageView.setVisibility(8);
                ActivityVideoCropper.this.imageView2.setVisibility(0);
                ActivityVideoCropper.this.imageView4.setVisibility(8);
                ActivityVideoCropper.this.imageView5.setVisibility(8);
                ActivityVideoCropper.this.imageView7.setVisibility(8);
                ActivityVideoCropper.this.imageView8.setVisibility(8);
                ActivityVideoCropper.this.imageView3.setVisibility(8);
            }
        };
    }

    public OnClickListener setRatioSeven() {
        return new OnClickListener() {
            @Override public void onClick(View view) {
                ActivityVideoCropper.this.cropImageView.setFixedAspectRatio(ActivityVideoCropper.BOOLEAN);
                ActivityVideoCropper.this.cropImageView.setAspectRatio(4, 3);
                ActivityVideoCropper.this.f();
                ActivityVideoCropper.this.imageButton6.setBackgroundResource(R.drawable.ic_crop_34_press);
                ActivityVideoCropper.this.imageView1.setVisibility(8);
                ActivityVideoCropper.this.imageView6.setVisibility(0);
                ActivityVideoCropper.this.imageView.setVisibility(8);
                ActivityVideoCropper.this.imageView2.setVisibility(8);
                ActivityVideoCropper.this.imageView4.setVisibility(8);
                ActivityVideoCropper.this.imageView5.setVisibility(8);
                ActivityVideoCropper.this.imageView7.setVisibility(8);
                ActivityVideoCropper.this.imageView8.setVisibility(8);
                ActivityVideoCropper.this.imageView3.setVisibility(8);
            }
        };
    }

    public OnClickListener setRatioEight() {
        return new OnClickListener() {
            @Override public void onClick(View view) {
                ActivityVideoCropper.this.cropImageView.setFixedAspectRatio(ActivityVideoCropper.BOOLEAN);
                ActivityVideoCropper.this.cropImageView.setAspectRatio(5, 3);
                ActivityVideoCropper.this.f();
                ActivityVideoCropper.this.imageButton2.setBackgroundResource(R.drawable.ic_crop_35_press);
                ActivityVideoCropper.this.imageView1.setVisibility(0);
                ActivityVideoCropper.this.imageView6.setVisibility(8);
                ActivityVideoCropper.this.imageView.setVisibility(8);
                ActivityVideoCropper.this.imageView2.setVisibility(8);
                ActivityVideoCropper.this.imageView4.setVisibility(8);
                ActivityVideoCropper.this.imageView5.setVisibility(8);
                ActivityVideoCropper.this.imageView7.setVisibility(8);
                ActivityVideoCropper.this.imageView8.setVisibility(8);
                ActivityVideoCropper.this.imageView3.setVisibility(8);
            }
        };
    }

    public OnClickListener setRatioNine() {
        return new OnClickListener() {
            @Override public void onClick(View view) {
                ActivityVideoCropper.this.cropImageView.setFixedAspectRatio(ActivityVideoCropper.BOOLEAN);
                ActivityVideoCropper.this.cropImageView.setAspectRatio(5, 4);
                ActivityVideoCropper.this.f();
                ActivityVideoCropper.this.imageButton4.setBackgroundResource(R.drawable.ic_crop_45_press);
                ActivityVideoCropper.this.imageView1.setVisibility(8);
                ActivityVideoCropper.this.imageView6.setVisibility(8);
                ActivityVideoCropper.this.imageView.setVisibility(8);
                ActivityVideoCropper.this.imageView2.setVisibility(8);
                ActivityVideoCropper.this.imageView4.setVisibility(8);
                ActivityVideoCropper.this.imageView5.setVisibility(8);
                ActivityVideoCropper.this.imageView7.setVisibility(8);
                ActivityVideoCropper.this.imageView8.setVisibility(8);
                ActivityVideoCropper.this.imageView3.setVisibility(0);
            }
        };
    }

    public void SearchVideo(Context context, String str, int i2, int i3) {
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
            managedQuery.moveToFirst();
            Long.valueOf(managedQuery.getLong(managedQuery.getColumnIndexOrThrow("_id")));
            managedQuery.moveToNext();
        }
    }

    private void i() {
        try {
            this.ffmpeg.loadBinary(new LoadBinaryResponseHandler() {
                @Override public void onFailure() {
                    ActivityVideoCropper.this.j();
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
            j();
        }
    }


    public void j() {
        new AlertDialog.Builder(this).setIcon(17301543).setTitle("Device not supported").setMessage("FFmpeg is not supported on your device").setCancelable(false).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialogInterface, int i) {
                ActivityVideoCropper.this.finish();
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
            if (this.videoView != null && this.videoView.isPlaying()) {
                this.videoView.pause();
            }
            cropcommand();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
