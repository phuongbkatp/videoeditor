package com.video_editor.pro.ActivityVideoConverter;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
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
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.video_editor.pro.ActivityVideoList.ActivityVideoList;
import com.video_editor.pro.R;
import com.video_editor.pro.UtilsAndAdapters.EditorVideoPlayerState;
import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@SuppressLint({"WrongConstant"})
public class ActivityVideoConverter extends AppCompatActivity implements OnSeekBarChangeListener {
    static String string = null;
    public static String outputformat = null;
    static final boolean BOOLEAN = true;
    AdapterFormatBase adapterFormatBase;
    ImageView imageView;
    ArrayList<String> arrayList;
    Handler handler = new Handler();
    private UnifiedNativeAd nativeAd;
    Boolean f = Boolean.valueOf(false);
    public FFmpeg ffmpeg;
    ProgressDialog progressDialog;
    SeekBar seekBar;
    int anInt = 0;
    int anInt1 = 0;
    Spinner spinner;
    TextView textView;
    TextView textView1;
    TextView textView2;
    VideoView videoView;
    OnClickListener onClickListener = new OnClickListener() {
        @Override public void onClick(View view) {
            if (ActivityVideoConverter.this.f.booleanValue()) {
                ActivityVideoConverter.this.videoView.pause();
                ActivityVideoConverter.this.handler.removeCallbacks(ActivityVideoConverter.this.runnable);
                ActivityVideoConverter.this.imageView.setBackgroundResource(R.drawable.play2);
            } else {
                ActivityVideoConverter.this.videoView.seekTo(ActivityVideoConverter.this.seekBar.getProgress());
                ActivityVideoConverter.this.videoView.start();
                ActivityVideoConverter.this.handler.postDelayed(ActivityVideoConverter.this.runnable, 500);
                ActivityVideoConverter.this.imageView.setBackgroundResource(R.drawable.pause2);
            }
            ActivityVideoConverter.this.f = Boolean.valueOf(ActivityVideoConverter.this.f.booleanValue() ^ ActivityVideoConverter.BOOLEAN);
        }
    };
    Runnable runnable = new Runnable() {
        public void run() {
            if (ActivityVideoConverter.this.videoView.isPlaying()) {
                int currentPosition = ActivityVideoConverter.this.videoView.getCurrentPosition();
                ActivityVideoConverter.this.seekBar.setProgress(currentPosition);
                try {
                    ActivityVideoConverter.this.textView1.setText(ActivityVideoConverter.formatTimeUnit(currentPosition));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (currentPosition == ActivityVideoConverter.this.anInt1) {
                    ActivityVideoConverter.this.seekBar.setProgress(0);
                    ActivityVideoConverter.this.imageView.setBackgroundResource(R.drawable.play2);
                    ActivityVideoConverter.this.textView1.setText("00:00");
                    ActivityVideoConverter.this.handler.removeCallbacks(ActivityVideoConverter.this.runnable);
                    return;
                }
                ActivityVideoConverter.this.handler.postDelayed(ActivityVideoConverter.this.runnable, 500);
                return;
            }
            ActivityVideoConverter.this.seekBar.setProgress(ActivityVideoConverter.this.anInt1);
            try {
                ActivityVideoConverter.this.textView1.setText(ActivityVideoConverter.formatTimeUnit(ActivityVideoConverter.this.anInt1));
            } catch (ParseException e2) {
                e2.printStackTrace();
            }
            ActivityVideoConverter.this.handler.removeCallbacks(ActivityVideoConverter.this.runnable);
        }
    };
    private PowerManager s;

    public EditorVideoPlayerState t = new EditorVideoPlayerState();
    private WakeLock wakeLock;

    @SuppressLint({"NewApi"})
    private class a {
        String string1;

        public a() {
            ActivityVideoConverter.this.progressDialog = new ProgressDialog(ActivityVideoConverter.this);
            ActivityVideoConverter.this.progressDialog.setCancelable(false);
            ActivityVideoConverter.this.progressDialog.show();
        }

        public int a(String str, String str2, String str3, String str4) {
            PrintStream printStream = System.out;
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("--");
            sb.append(str2);
            sb.append("--");
            sb.append(str3);
            sb.append("--");
            sb.append(str4);
            printStream.println(sb.toString());
            final String[] strArr = {"-i", str, "-vcodec", str4, "-acodec", "aac", str2};
            try {
                FFmpeg.getInstance(ActivityVideoConverter.this.getApplicationContext()).execute(strArr, new ExecuteBinaryResponseHandler() {
                    @Override public void onFinish() {
                    }

                    @Override public void onStart() {
                        ActivityVideoConverter.this.progressDialog.setMessage("Processing...");
                    }

                    @Override public void onFailure(String str) {
                        Toast.makeText(ActivityVideoConverter.this, "Error Creating Video!", Toast.LENGTH_LONG).show();
                    }

                    @Override public void onSuccess(String str) {
                        a.this.b();
                    }

                    @Override public void onProgress(String str) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Started command : ffmpeg ");
                        sb.append(strArr);
                        ProgressDialog progressDialog = ActivityVideoConverter.this.progressDialog;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("progress : ");
                        sb2.append(str);
                        progressDialog.setMessage(sb2.toString());
                        Log.d("FFmpeg", sb.toString());
                    }
                });
                return 0;
            } catch (FFmpegCommandAlreadyRunningException e) {
                e.printStackTrace();
                return 0;
            }
        }


        public void a() {
            this.string1 = ActivityVideoConverter.this.t.getFilename();
            ActivityVideoConverter.string = ConverterFileUtils.getFileName(ActivityVideoConverter.this, this.string1);
            String extension = ConverterFileUtils.getExt(this.string1);
            String str = "copy";
            String str2 = "copy";
            if (ActivityVideoConverter.outputformat.equalsIgnoreCase("avi") || ActivityVideoConverter.outputformat.equalsIgnoreCase("mov")) {
                str = "mp2";
                str2 = "libx264";
            }
            if (ActivityVideoConverter.outputformat.equalsIgnoreCase("wmv")) {
                str = "mp2";
                str2 = "wmv2";
            }
            if (extension.contains("wmv") && ActivityVideoConverter.outputformat.equalsIgnoreCase("mp4")) {
                str = "mp2";
                str2 = "libx264";
            }
            if (ActivityVideoConverter.outputformat.equalsIgnoreCase("mpg") || ActivityVideoConverter.outputformat.equalsIgnoreCase("mpeg")) {
                str2 = "mpeg2video";
                str = "mp2";
            }
            if (extension.contains("mpg") || extension.contains("mpeg") || (extension.contains("wmv") && ActivityVideoConverter.outputformat.equalsIgnoreCase("3gp"))) {
                str = "mp2";
                str2 = "h263";
            }
            a(this.string1, ActivityVideoConverter.string, str, str2);
        }

        @SuppressLint({"WrongConstant"})
        public void b() {
            Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
            intent.setData(Uri.fromFile(new File(ActivityVideoConverter.string)));
            ActivityVideoConverter.this.sendBroadcast(intent);
        }
    }

    public void onProgressChanged(SeekBar seekBar, int i2, boolean z) {
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @SuppressLint({"ClickableViewAccessibility", "InvalidWakeLockTag"})
    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView( R.layout.activity_video_converter);
        refreshAd();
        Toolbar toolbar = findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Video Converter");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (BOOLEAN || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(BOOLEAN);
            supportActionBar.setDisplayShowTitleEnabled(false);
            this.ffmpeg = FFmpeg.getInstance(this);
            b();
            this.arrayList = new ArrayList<>();
            this.arrayList.add("avi");
            this.arrayList.add("flv");
            this.arrayList.add("mp4");
            this.arrayList.add("mkv");
            this.arrayList.add("gif");
            this.arrayList.add("mov");
            this.arrayList.add("mpg");
            this.arrayList.add("mpeg");
            this.arrayList.add("wmv");
            this.arrayList.add("3gp");
            this.s = (PowerManager) getSystemService(Context.POWER_SERVICE);
            this.wakeLock = this.s.newWakeLock(6, "My Tag");
            Object lastNonConfigurationInstance = getLastNonConfigurationInstance();
            if (lastNonConfigurationInstance != null) {
                this.t = (EditorVideoPlayerState) lastNonConfigurationInstance;
            } else {
                a();
                string = getIntent().getExtras().getString("videofilename");
                this.t.setFilename(string);
            }
            this.textView2.setText(new File(string).getName());
            this.videoView.setVideoPath(string);
            this.videoView.seekTo(100);
            this.videoView.setOnErrorListener(new OnErrorListener() {
                public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                    Toast.makeText(ActivityVideoConverter.this, "Video Player Not Supproting !", Toast.LENGTH_LONG).show();
                    return ActivityVideoConverter.BOOLEAN;
                }
            });
            this.videoView.setOnPreparedListener(new OnPreparedListener() {
                public void onPrepared(MediaPlayer mediaPlayer) {
                    ActivityVideoConverter.this.anInt1 = ActivityVideoConverter.this.videoView.getDuration();
                    ActivityVideoConverter.this.seekBar.setMax(ActivityVideoConverter.this.anInt1);
                    ActivityVideoConverter.this.textView1.setText("00:00");
                    try {
                        ActivityVideoConverter.this.textView.setText(ActivityVideoConverter.formatTimeUnit(ActivityVideoConverter.this.anInt1));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
            this.videoView.setOnCompletionListener(new OnCompletionListener() {
                public void onCompletion(MediaPlayer mediaPlayer) {
                    ActivityVideoConverter.this.imageView.setBackgroundResource(R.drawable.pause2);
                    ActivityVideoConverter.this.videoView.seekTo(0);
                    ActivityVideoConverter.this.seekBar.setProgress(0);
                    ActivityVideoConverter.this.textView1.setText("00:00");
                    ActivityVideoConverter.this.handler.removeCallbacks(ActivityVideoConverter.this.runnable);
                }
            });
            this.videoView.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return false;
                }
            });
            int lastIndexOf = this.t.getFilename().lastIndexOf(".") + 1;
            if (this.arrayList.contains(this.t.getFilename().substring(lastIndexOf).toLowerCase())) {
                this.arrayList.remove(this.t.getFilename().substring(lastIndexOf).toLowerCase());
                outputformat = this.arrayList.get(0);
            }
            this.adapterFormatBase = new AdapterFormatBase(this, this.arrayList, this.anInt);
            this.spinner.setAdapter(this.adapterFormatBase);
            this.spinner.setSelection(0);
            return;
        }
        throw new AssertionError();
    }

    private void a() {
        this.textView2 = findViewById(R.id.Filename);
        this.videoView = findViewById(R.id.videoView1);
        this.imageView = findViewById(R.id.buttonply);
        this.imageView.setOnClickListener(this.onClickListener);
        this.textView1 = findViewById(R.id.left_pointer);
        this.textView = findViewById(R.id.right_pointer);
        this.seekBar = findViewById(R.id.sbVideo);
        this.seekBar.setOnSeekBarChangeListener(this);
        this.spinner = findViewById(R.id.sp_convert);
    }

    @SuppressLint({"DefaultLocale"})
    public static String formatTimeUnit(long j2) throws ParseException {
        return String.format("%02d:%02d", Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(j2)), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j2) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j2))));
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        int progress = seekBar.getProgress();
        this.videoView.seekTo(progress);
        try {
            this.textView1.setText(formatTimeUnit(progress));
        } catch (ParseException e2) {
            e2.printStackTrace();
        }
    }

    private void b() {
        try {
            this.ffmpeg.loadBinary(new LoadBinaryResponseHandler() {
                @Override public void onFailure() {
                    ActivityVideoConverter.this.c();
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
            c();
        }
    }


    public void c() {
        new Builder(this).setIcon(17301543).setTitle("Device not supported").setMessage("FFmpeg is not supported on your device").setCancelable(false).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialogInterface, int i) {
                ActivityVideoConverter.this.finish();
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
        super.onBackPressed();
        Intent intent = new Intent(this, ActivityVideoList.class);
        intent.setFlags(67108864);
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
                FrameLayout nativeAdPlaceHolder = ActivityVideoConverter.this.findViewById(R.id.nativeAdPlaceHolder);
                @SuppressLint("InflateParams") UnifiedNativeAdView adView = (UnifiedNativeAdView) ActivityVideoConverter.this.getLayoutInflater()
                        .inflate(R.layout.native_ad_unified_smaller, null);
                ActivityVideoConverter.this.populateUnifiedNativeAdView(unifiedNativeAd, adView);
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







    public void onPause() {
        super.onPause();
        this.wakeLock.release();
        Log.i("VideoView", "In on pause");
    }


    @Override public void onResume() {
        super.onResume();
        this.wakeLock.acquire();
        Log.i("VideoView", "In on resume");
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
            StringBuilder sb = new StringBuilder();
            sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
            sb.append("/");
            sb.append(getResources().getString(R.string.MainFolderName));
            sb.append("/");
            sb.append(getResources().getString(R.string.VideoConverter));
            File file = new File(sb.toString());
            if (!file.exists()) {
                file.mkdirs();
            }
            if (this.videoView.isPlaying()) {
                this.videoView.pause();
                this.handler.removeCallbacks(this.runnable);
                this.f = Boolean.valueOf(false);
                this.imageView.setBackgroundResource(R.drawable.play2);
            }
            outputformat = this.arrayList.get(this.spinner.getSelectedItemPosition());
            if (outputformat != null) {
                getClass();
                new a().a();
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
