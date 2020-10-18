package com.video_editor.pro.ActivityVideoRotate;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
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
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.video_editor.pro.UtilsAndAdapters.EditorCustomEditText;
import com.video_editor.pro.UtilsAndAdapters.EditorCustomTextView;
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
import java.text.ParseException;
import java.util.concurrent.TimeUnit;

@SuppressLint({"NewApi", "ResourceType"})
public class ActivityVideoRotate extends AppCompatActivity {
    static final boolean BOOLEAN = true;
    RelativeLayout relativeLayout;
    RelativeLayout layout;
    RelativeLayout layout1;
    Dialog dialog;
    OnClickListener onClickListener = new OnClickListener() {
        @Override public void onClick(View view) {
            if (ActivityVideoRotate.this.aBoolean1.booleanValue()) {
                ActivityVideoRotate.this.imageView.setBackgroundResource(R.drawable.play2);
                ActivityVideoRotate.this.aBoolean1 = Boolean.valueOf(false);
            } else {
                ActivityVideoRotate.this.imageView.setBackgroundResource(R.drawable.pause2);
                ActivityVideoRotate.this.aBoolean1 = Boolean.valueOf(ActivityVideoRotate.BOOLEAN);
            }
            ActivityVideoRotate.this.e();
        }
    };
    Runnable runnable = new Runnable() {
        @SuppressLint({"SetTextI18n"})
        public void run() {
            if (ActivityVideoRotate.this.videoView.isPlaying()) {
                int currentPosition = ActivityVideoRotate.this.videoView.getCurrentPosition();
                ActivityVideoRotate.this.seekBar.setProgress(currentPosition);
                try {
                    TextView textView = ActivityVideoRotate.this.textView1;
                    StringBuilder sb = new StringBuilder();
                    sb.append("");
                    sb.append(ActivityVideoRotate.formatTimeUnit(currentPosition));
                    textView.setText(sb.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (currentPosition == ActivityVideoRotate.this.anInt) {
                    ActivityVideoRotate.this.seekBar.setProgress(0);
                    ActivityVideoRotate.this.textView1.setText("00:00");
                    ActivityVideoRotate.this.handler1.removeCallbacks(ActivityVideoRotate.this.runnable);
                    return;
                }
                ActivityVideoRotate.this.handler1.postDelayed(ActivityVideoRotate.this.runnable, 500);
                return;
            }
            ActivityVideoRotate.this.seekBar.setProgress(ActivityVideoRotate.this.anInt);
            try {
                TextView textView2 = ActivityVideoRotate.this.textView1;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("");
                sb2.append(ActivityVideoRotate.formatTimeUnit(ActivityVideoRotate.this.anInt));
                textView2.setText(sb2.toString());
            } catch (ParseException e2) {
                e2.printStackTrace();
            }
            ActivityVideoRotate.this.handler1.removeCallbacks(ActivityVideoRotate.this.runnable);
        }
    };
    Runnable runnable1 = new Runnable() {
        public void run() {
            ActivityVideoRotate.this.handler.removeCallbacks(ActivityVideoRotate.this.runnable1);
            ActivityVideoRotate.this.b();
        }
    };

    public EditorVideoPlayerState I = new EditorVideoPlayerState();
    private a a1 = new a();
    private InterstitialAd interstitialAd;
    ImageView imageView;
    int anInt = 0;
    int anInt1 = 0;
    int anInt2 = 0;
    String string;
    String string1;
    public FFmpeg ffmpeg;
    String string2 = "90";
    String string3 = "00";
    String string4;
    String string5 = "";
    Handler handler = new Handler();
    Handler handler1 = new Handler();
    boolean aBoolean = false;
    private UnifiedNativeAd nativeAd;

    Context context;
    Boolean aBoolean1 = Boolean.valueOf(false);
    SeekBar seekBar;
    TextView textView;
    TextView textView1;
    TextView textView2;
    EditorVideoSliceSeekBar editorVideoSliceSeekBar;
    VideoView videoView;
    RelativeLayout relativeLayout1;
    RelativeLayout relativeLayout2;
    RelativeLayout relativeLayout3;
    RelativeLayout relativeLayout4;
    RelativeLayout relativeLayout5;

    private class a extends Handler {
        private boolean aBoolean2;
        private Runnable runnable2;

        private a() {
            this.aBoolean2 = false;
            this.runnable2 = new Runnable() {
                public void run() {
                    a.this.a();
                }
            };
        }


        public void a() {
            if (!this.aBoolean2) {
                this.aBoolean2 = ActivityVideoRotate.BOOLEAN;
                sendEmptyMessage(0);
            }
        }

        @Override public void handleMessage(Message message) {
            this.aBoolean2 = false;
            ActivityVideoRotate.this.editorVideoSliceSeekBar.videoPlayingProgress(ActivityVideoRotate.this.videoView.getCurrentPosition());
            if (!ActivityVideoRotate.this.videoView.isPlaying() || ActivityVideoRotate.this.videoView.getCurrentPosition() >= ActivityVideoRotate.this.editorVideoSliceSeekBar.getRightProgress()) {
                if (ActivityVideoRotate.this.videoView.isPlaying()) {
                    ActivityVideoRotate.this.videoView.pause();
                    ActivityVideoRotate.this.imageView.setBackgroundResource(R.drawable.play2);
                    ActivityVideoRotate.this.aBoolean1 = Boolean.valueOf(false);
                }
                ActivityVideoRotate.this.editorVideoSliceSeekBar.setSliceBlocked(false);
                ActivityVideoRotate.this.editorVideoSliceSeekBar.removeVideoStatusThumb();
                return;
            }
            postDelayed(this.runnable2, 50);
        }
    }


    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView( R.layout.videorotateactivity);
        refreshAd();
        Toolbar toolbar = findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Video Rotate");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (BOOLEAN || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(BOOLEAN);
            supportActionBar.setDisplayShowTitleEnabled(false);
            Intent intent = getIntent();
            this.context = this;
            this.string5 = intent.getStringExtra("videoPath");
            this.ffmpeg = FFmpeg.getInstance(this);
            f();
            this.editorVideoSliceSeekBar = findViewById(R.id.sbVideo);
            this.videoView = findViewById(R.id.vvScreen);
            this.textView1 = findViewById(R.id.tvStartVideo);
            this.textView = findViewById(R.id.tvEndVideo);
            this.string1 = this.string5.substring(this.string5.lastIndexOf(".") + 1);
            MediaScannerConnection.scanFile(this, new String[]{new File(this.string5).getAbsolutePath()}, new String[]{this.string1}, null);
            d();
            this.imageView = findViewById(R.id.btnPlayVideo);
            this.imageView.setOnClickListener(this.onClickListener);
            this.relativeLayout1 = findViewById(R.id.btn_rotate180);
            this.relativeLayout2 = findViewById(R.id.btn_rotate90);
            this.relativeLayout3 = findViewById(R.id.btn_rotate270);
            this.relativeLayout4 = findViewById(R.id.btn_custom);
            this.relativeLayout5 = findViewById(R.id.back_rotate180);
            this.relativeLayout = findViewById(R.id.back_rotate90);
            this.layout = findViewById(R.id.back_rotate270);
            this.layout1 = findViewById(R.id.back_custom);
            this.textView2 = findViewById(R.id.Filename);
            this.textView2.setText(new File(this.string5).getName());
            this.relativeLayout1.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View view) {
                    ActivityVideoRotate.this.relativeLayout5.setVisibility(0);
                    ActivityVideoRotate.this.relativeLayout.setVisibility(8);
                    ActivityVideoRotate.this.layout.setVisibility(8);
                    ActivityVideoRotate.this.layout1.setVisibility(8);
                    ActivityVideoRotate.this.string2 = "180";
                }
            });
            this.relativeLayout2.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View view) {
                    ActivityVideoRotate.this.relativeLayout5.setVisibility(8);
                    ActivityVideoRotate.this.relativeLayout.setVisibility(0);
                    ActivityVideoRotate.this.layout.setVisibility(8);
                    ActivityVideoRotate.this.layout1.setVisibility(8);
                    ActivityVideoRotate.this.string2 = "90";
                }
            });
            this.relativeLayout3.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View view) {
                    ActivityVideoRotate.this.relativeLayout5.setVisibility(8);
                    ActivityVideoRotate.this.relativeLayout.setVisibility(8);
                    ActivityVideoRotate.this.layout.setVisibility(0);
                    ActivityVideoRotate.this.layout1.setVisibility(8);
                    ActivityVideoRotate.this.string2 = "270";
                }
            });
            this.relativeLayout4.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View view) {
                    ActivityVideoRotate.this.relativeLayout5.setVisibility(8);
                    ActivityVideoRotate.this.relativeLayout.setVisibility(8);
                    ActivityVideoRotate.this.layout.setVisibility(8);
                    ActivityVideoRotate.this.layout1.setVisibility(0);
                    ActivityVideoRotate.this.dialog = new Dialog(ActivityVideoRotate.this);
                    ActivityVideoRotate.this.dialog.setCanceledOnTouchOutside(false);
                    ActivityVideoRotate.this.dialog.requestWindowFeature(1);
                    ActivityVideoRotate.this.dialog.setContentView(R.layout.enter_file_name_dialog);
                    ActivityVideoRotate.this.dialog.show();
                    ActivityVideoRotate.this.dialog.findViewById(R.id.closePopup).setOnClickListener(new OnClickListener() {
                        @Override public void onClick(View view) {
                            ActivityVideoRotate.this.dialog.dismiss();
                        }
                    });
                    ((TextView) ActivityVideoRotate.this.dialog.findViewById(R.id.Name)).setText("Enter Degree");
                    final EditorCustomEditText editorCustomEditText = ActivityVideoRotate.this.dialog.findViewById(R.id.message);
                    ActivityVideoRotate.this.dialog.findViewById(R.id.sendBtn).setOnClickListener(new OnClickListener() {
                        @Override public void onClick(View view) {
                            if (editorCustomEditText.getText().toString().length() == 0) {
                                Toast.makeText(ActivityVideoRotate.this, "Please Enter Value Between 1 To 360", 0).show();
                                return;
                            }
                            int parseInt = Integer.parseInt(editorCustomEditText.getText().toString());
                            if (parseInt < 1 || parseInt > 360) {
                                Toast.makeText(ActivityVideoRotate.this, "Please Enter Value Between 1 To 360", 0).show();
                                return;
                            }
                            ActivityVideoRotate.this.string2 = editorCustomEditText.getText().toString();
                            ActivityVideoRotate.this.dialog.dismiss();
                        }
                    });
                }
            });
            this.interstitialAd = new InterstitialAd(this);
            this.interstitialAd.setAdUnitId(getString(R.string.interstitial_id_admob));
            this.interstitialAd.setAdListener(new AdListener() {
                @Override public void onAdClosed() {
                    ActivityVideoRotate.this.c();
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
        intent.putExtra("song", this.string4);
        startActivity(intent);
        finish();
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
                FrameLayout nativeAdPlaceHolder = ActivityVideoRotate.this.findViewById(R.id.nativeAdPlaceHolder);
                @SuppressLint("InflateParams") UnifiedNativeAdView adView = (UnifiedNativeAdView) ActivityVideoRotate.this.getLayoutInflater()
                        .inflate(R.layout.native_ad_unified_smaller, null);
                ActivityVideoRotate.this.populateUnifiedNativeAdView(unifiedNativeAd, adView);
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
















    public void rotatecommand() {
        String valueOf = String.valueOf(this.I.getStart() / 1000);
        String valueOf2 = String.valueOf(this.I.getDuration() / 1000);
        Log.e("start", valueOf);
        Log.e("end", valueOf2);
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb.append("/");
        sb.append(getResources().getString(R.string.MainFolderName));
        sb.append("/");
        sb.append(getResources().getString(R.string.VideoRotate));
        File file = new File(sb.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb2.append("/");
        sb2.append(getResources().getString(R.string.MainFolderName));
        sb2.append("/");
        sb2.append(getResources().getString(R.string.VideoRotate));
        sb2.append("/");
        sb2.append(System.currentTimeMillis());
        sb2.append(".mp4");
        this.string4 = sb2.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append("rotate=");
        sb3.append(this.string2);
        sb3.append("*PI/180");
        a(new String[]{"-y", "-ss", valueOf, "-t", valueOf2, "-i", this.string5, "-filter_complex", sb3.toString(), "-c:a", "copy", this.string4}, this.string4);
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
                        ActivityVideoRotate.this.deleteFromGallery(str);
                        Toast.makeText(ActivityVideoRotate.this.getApplicationContext(), "Error Creating Video", 0).show();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }

                @Override public void onSuccess(String str) {
                    progressDialog.dismiss();
                    Intent intent = new Intent(ActivityVideoRotate.this.getApplicationContext(), EditorVideoPlayer.class);
                    intent.setFlags(67108864);
                    intent.putExtra("song", ActivityVideoRotate.this.string4);
                    ActivityVideoRotate.this.startActivity(intent);
                    ActivityVideoRotate.this.finish();
                }

                @Override public void onProgress(String str) {
                    Log.d("ffmpegResponse", str);
                    StringBuilder sb = new StringBuilder();
                    sb.append("Rotating Video at ");
                    sb.append(ActivityVideoRotate.this.string2);
                    sb.append(176);
                    sb.append("\n");
                    sb.append(str);
                    progressDialog.setMessage(sb.toString());
                }

                @Override public void onStart() {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Rotating Video at ");
                    sb.append(ActivityVideoRotate.this.string2);
                    sb.append(176);
                    progressDialog.setMessage(sb.toString());
                }

                @Override public void onFinish() {
                    progressDialog.dismiss();
                    ActivityVideoRotate.this.refreshGallery(str);
                }
            });
            getWindow().clearFlags(16);
        } catch (FFmpegCommandAlreadyRunningException unused) {
        }
    }

    private void d() {
        this.videoView.setVideoPath(this.string5);
        this.videoView.setOnCompletionListener(new OnCompletionListener() {
            public void onCompletion(MediaPlayer mediaPlayer) {
                ActivityVideoRotate.this.imageView.setBackgroundResource(R.drawable.play2);
                ActivityVideoRotate.this.aBoolean1 = Boolean.valueOf(false);
            }
        });
        this.videoView.setOnErrorListener(new OnErrorListener() {
            public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                Toast.makeText(ActivityVideoRotate.this.getApplicationContext(), "Video Player Not Supproting", 0).show();
                ActivityVideoRotate.this.aBoolean = false;
                return ActivityVideoRotate.BOOLEAN;
            }
        });
        this.videoView.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                ActivityVideoRotate.this.editorVideoSliceSeekBar.setSeekBarChangeListener(new SeekBarChangeListener() {
                    public void SeekBarValueChanged(int i, int i2) {
                        if (ActivityVideoRotate.this.editorVideoSliceSeekBar.getSelectedThumb() == 1) {
                            ActivityVideoRotate.this.videoView.seekTo(ActivityVideoRotate.this.editorVideoSliceSeekBar.getLeftProgress());
                        }
                        ActivityVideoRotate.this.textView1.setText(ActivityVideoRotate.getTimeForTrackFormat(i, ActivityVideoRotate.BOOLEAN));
                        ActivityVideoRotate.this.textView.setText(ActivityVideoRotate.getTimeForTrackFormat(i2, ActivityVideoRotate.BOOLEAN));
                        ActivityVideoRotate.this.string3 = ActivityVideoRotate.getTimeForTrackFormat(i, ActivityVideoRotate.BOOLEAN);
                        ActivityVideoRotate.this.I.setStart(i);
                        ActivityVideoRotate.this.string = ActivityVideoRotate.getTimeForTrackFormat(i2, ActivityVideoRotate.BOOLEAN);
                        ActivityVideoRotate.this.I.setStop(i2);
                        ActivityVideoRotate.this.anInt2 = i;
                        ActivityVideoRotate.this.anInt1 = i2;
                    }
                });
                ActivityVideoRotate.this.string = ActivityVideoRotate.getTimeForTrackFormat(mediaPlayer.getDuration(), ActivityVideoRotate.BOOLEAN);
                ActivityVideoRotate.this.editorVideoSliceSeekBar.setMaxValue(mediaPlayer.getDuration());
                ActivityVideoRotate.this.editorVideoSliceSeekBar.setLeftProgress(0);
                ActivityVideoRotate.this.editorVideoSliceSeekBar.setRightProgress(mediaPlayer.getDuration());
                ActivityVideoRotate.this.editorVideoSliceSeekBar.setProgressMinDiff(0);
                ActivityVideoRotate.this.imageView.setOnClickListener(new OnClickListener() {
                    @Override public void onClick(View view) {
                        if (ActivityVideoRotate.this.aBoolean1.booleanValue()) {
                            ActivityVideoRotate.this.imageView.setBackgroundResource(R.drawable.play2);
                            ActivityVideoRotate.this.aBoolean1 = Boolean.valueOf(false);
                        } else {
                            ActivityVideoRotate.this.imageView.setBackgroundResource(R.drawable.pause2);
                            ActivityVideoRotate.this.aBoolean1 = Boolean.valueOf(ActivityVideoRotate.BOOLEAN);
                        }
                        ActivityVideoRotate.this.e();
                    }
                });
            }
        });
        this.string = getTimeForTrackFormat(this.videoView.getDuration(), BOOLEAN);
    }


    public void e() {
        if (this.videoView.isPlaying()) {
            this.videoView.pause();
            this.editorVideoSliceSeekBar.setSliceBlocked(false);
            this.editorVideoSliceSeekBar.removeVideoStatusThumb();
            return;
        }
        this.videoView.seekTo(this.editorVideoSliceSeekBar.getLeftProgress());
        this.videoView.start();
        this.imageView.setBackgroundResource(R.drawable.pause2);
        this.editorVideoSliceSeekBar.videoPlayingProgress(this.editorVideoSliceSeekBar.getLeftProgress());
        this.a1.a();
    }

    @SuppressLint({"DefaultLocale"})
    public static String formatTimeUnit(long j2) throws ParseException {
        return String.format("%02d:%02d", Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(j2)), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j2) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j2))));
    }

    public static String getTimeForTrackFormat(int i2, boolean z2) {
        int i3 = i2 / 3600000;
        int i4 = i2 / 60000;
        int i5 = (i2 - ((i4 * 60) * 1000)) / 1000;
        String str = (!z2 || i3 >= 10) ? "" : "0";
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append(i3);
        sb2.append(":");
        sb.append(sb2.toString());
        String str2 = (!z2 || i4 >= 10) ? "" : "0";
        StringBuilder sb3 = new StringBuilder();
        sb.append(str2);
        sb3.append(sb.toString());
        sb3.append(i4 % 60);
        sb3.append(":");
        String sb4 = sb3.toString();
        if (i5 < 10) {
            StringBuilder sb5 = new StringBuilder();
            sb5.append(sb4);
            sb5.append("0");
            sb5.append(i5);
            return sb5.toString();
        }
        StringBuilder sb6 = new StringBuilder();
        sb6.append(sb4);
        sb6.append(i5);
        return sb6.toString();
    }

    private void f() {
        try {
            this.ffmpeg.loadBinary(new LoadBinaryResponseHandler() {
                @Override public void onFailure() {
                    ActivityVideoRotate.this.g();
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
                ActivityVideoRotate.this.finish();
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

    @Override public void onBackPressed() {
        Intent intent = new Intent(this, ActivityVideoList.class);
        intent.setFlags(67108864);
        startActivity(intent);
        finish();
    }


    @Override public void onResume() {
        super.onResume();
    }


    public void onPause() {
        super.onPause();
    }


    @Override public void onDestroy() {
        getWindow().clearFlags(128);
        if (nativeAd != null) {
            nativeAd.destroy();
        }
        super.onDestroy();
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
            rotatecommand();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
