package com.video_editor.pro.ActivityPhotoVideo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore.Audio.Media;
import android.provider.MediaStore.Images;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.video_editor.pro.R;
import com.video_editor.pro.UtilsAndAdapters.EditorVideoPlayer;
import com.video_editor.pro.ActivityPhotoVideo.ModelSlideshow.FramePointSet;
import com.video_editor.pro.ActivityPhotoVideo.ModelSlideshow.MusicModel;
import com.video_editor.pro.ActivityPhotoVideo.UtilsSlideshow.BitmapCompression;
import com.video_editor.pro.ActivityPhotoVideo.UtilsSlideshow.Item;
import com.video_editor.pro.ActivityPhotoVideo.UtilsSlideshow.Utils;
import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration.Builder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@SuppressLint({"WrongConstant"})
public class ActivityMovieMaker extends AppCompatActivity implements OnSeekBarChangeListener {

    Animation animation;
    static final boolean BOOLEAN = true;
    public static int currentSeek;
    public static Activity mContext;
    int anInt;
    ArrayList<Item> items = new ArrayList<>();
    LinearLayout linearLayout;
    LinearLayout linearLayout1;
    RelativeLayout relativeLayout;
    MediaPlayer mediaPlayer = null;
    String string = null;
    String[] strings;
    DisplayImageOptions displayImageOptions;
    RecyclerView recyclerView;
    SeekBar seekBar;
    SeekBar seekBar1;
    SeekBar seekBar2;
    Button button;
    Button button1;
    ImageView imageView;
    ImageView imageView1;
    TextView textView;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textView6;
    String string1 = "2";
    HorizontalScrollView horizontalScrollView;
    String string2 = "";
    Runnable runnable = new Runnable() {
        public void run() {
            int progress = ActivityMovieMaker.this.seekBar1.getProgress();
            Log.d("textView", new StringBuilder(String.valueOf(progress)).toString());
            ActivityMovieMaker.this.seekBar1.setProgress(progress + 100);
            ActivityMovieMaker.this.handler1.postDelayed(this, 100);
        }
    };
    OnClickListener onClickListener = new d();
    OnClickListener clickListener = new e();
    public boolean addEffectThumbsAsync;
    OnClickListener onClickListener1 = new f();
    OnClickListener clickListener1 = new g();
    OnClickListener onClickListener2 = new c();
    OnClickListener clickListener2 = new OnClickListener() {
        @Override
        public void onClick(View view) {
            ActivityMovieMaker.this.aBoolean1 = false;
            ActivityMovieMaker.this.anInt1 = -1;
            Utils.audioSelected = -1;
            ActivityMovieMaker.this.a(ActivityMovieMaker.this.imageView, ActivityMovieMaker.this.anInt1, false);
            ActivityMovieMaker.this.aBoolean4 = false;
            if (ActivityMovieMaker.this.mediaPlayer != null) {
                ActivityMovieMaker.this.mediaPlayer.stop();
            }
            ActivityMovieMaker.this.mediaPlayer = null;
            ActivityMovieMaker.this.string = null;
            ActivityMovieMaker.this.relativeLayout.setVisibility(8);
            ActivityMovieMaker.this.button.setVisibility(0);
        }
    };
    AnimationListener ai = new AnimationListener() {
        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            ActivityMovieMaker.this.frameLayout1.setVisibility(8);
        }
    };

    public boolean aBoolean = BOOLEAN;
    public AdapterSelectMusic adapterSelectMusic;
    private InterstitialAd interstitialAd;
    float aFloat;
    boolean aBoolean1 = false;
    ArrayList<Bitmap> bitmaps = new ArrayList<>();
    Button button2;
    Button button3;
    public FFmpeg ffmpeg;
    private UnifiedNativeAd nativeAd;
    Button button4;
    ArrayList<String> arrayList;
    FrameLayout frameLayout;
    FrameLayout frameLayout1;
    b b;
    Handler handler = new Handler();
    ArrayList<FrameLayout> frameLayouts = new ArrayList<>();
    Handler handler1 = new Handler();
    ImageLoader imageLoader;
    boolean aBoolean2 = false;
    boolean aBoolean3;
    boolean aBoolean4 = false;
    boolean aBoolean5 = false;
    ArrayList<LinearLayout> linearLayouts;
    int anInt1 = -1;
    int anInt2;
    int anInt3 = 0;
    int anInt4;
    int anInt5;
    int anInt6;

    public static class MyFileNameFilter implements FilenameFilter {
        public boolean accept(File file, String str) {
            return str.toLowerCase().startsWith("slide_00");
        }
    }

    class a extends AsyncTask<Void, Void, Boolean> {
        a() {
        }


        public Boolean doInBackground(Void... voidArr) {
            ActivityMovieMaker activityMovieMaker = ActivityMovieMaker.this;
            StringBuilder sb = new StringBuilder();
            sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
            sb.append("/");
            sb.append(ActivityMovieMaker.this.getResources().getString(R.string.MainFolderName));
            sb.append("/");
            sb.append(ActivityMovieMaker.this.getResources().getString(R.string.PhotoToVideo));
            StringBuilder sb2 = new StringBuilder(sb.toString());
            sb2.append("/temp");
            activityMovieMaker.a(sb2.toString());
            int size = ActivityMovieMaker.this.arrayList.size();
            int i = 0;
            while (i < size) {
                Options options = new Options();
                options.inJustDecodeBounds = ActivityMovieMaker.BOOLEAN;
                BitmapFactory.decodeFile(ActivityMovieMaker.this.arrayList.get(i), options);
                options.inSampleSize = BitmapCompression.calculateInSampleSize(options, ActivityMovieMaker.this.anInt6, ActivityMovieMaker.this.anInt6);
                options.inJustDecodeBounds = false;
                Utils.bitmap = BitmapFactory.decodeFile(ActivityMovieMaker.this.arrayList.get(i), options);
                if (Utils.framePostion > -1 && Utils.filterIndex > -1) {
                    Utils.bitmap = ActivityMovieMaker.this.a(ActivityMovieMaker.BOOLEAN, ActivityMovieMaker.BOOLEAN);
                } else if (Utils.framePostion > -1) {
                    Utils.bitmap = ActivityMovieMaker.this.a(ActivityMovieMaker.BOOLEAN, false);
                } else if (Utils.filterIndex > -1) {
                    Utils.bitmap = ActivityMovieMaker.this.a(false, ActivityMovieMaker.BOOLEAN);
                } else {
                    Utils.bitmap = ActivityMovieMaker.this.a(false, false);
                }
                i++;
                ActivityMovieMaker.this.a(i, ActivityMovieMaker.BOOLEAN);
            }
            Options options2 = new Options();
            options2.inJustDecodeBounds = ActivityMovieMaker.BOOLEAN;
            BitmapFactory.decodeFile(ActivityMovieMaker.this.arrayList.get(0), options2);
            options2.inSampleSize = BitmapCompression.calculateInSampleSize(options2, ActivityMovieMaker.this.anInt6, ActivityMovieMaker.this.anInt6);
            options2.inJustDecodeBounds = false;
            Utils.bitmap = BitmapFactory.decodeFile(ActivityMovieMaker.this.arrayList.get(0), options2);
            if (Utils.framePostion > -1 && Utils.filterIndex > -1) {
                Utils.bitmap = ActivityMovieMaker.this.a(ActivityMovieMaker.BOOLEAN, ActivityMovieMaker.BOOLEAN);
            } else if (Utils.framePostion > -1) {
                Utils.bitmap = ActivityMovieMaker.this.a(ActivityMovieMaker.BOOLEAN, false);
            } else if (Utils.filterIndex > -1) {
                Utils.bitmap = ActivityMovieMaker.this.a(false, ActivityMovieMaker.BOOLEAN);
            } else {
                Utils.bitmap = ActivityMovieMaker.this.a(false, false);
            }
            ActivityMovieMaker.this.a(0, ActivityMovieMaker.BOOLEAN);
            return null;
        }


        public void onPostExecute(Boolean bool) {
            ActivityMovieMaker.this.Command();
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
                FrameLayout nativeAdPlaceHolder = ActivityMovieMaker.this.findViewById(R.id.nativeAdPlaceHolder);
                @SuppressLint("InflateParams") UnifiedNativeAdView adView = (UnifiedNativeAdView) ActivityMovieMaker.this.getLayoutInflater()
                        .inflate(R.layout.native_ad_unified_smaller, null);
                ActivityMovieMaker.this.populateUnifiedNativeAdView(unifiedNativeAd, adView);
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


    private class b extends AsyncTask<Void, Void, Boolean> {


        public void onPostExecute(Boolean bool) {
        }

        private b() {
        }


        public void onPreExecute() {
            super.onPreExecute();
            ActivityMovieMaker.this.addEffectThumbsAsync = false;
            ActivityMovieMaker.this.linearLayouts = new ArrayList<>();
            ActivityMovieMaker.this.items.clear();
        }


        public Boolean doInBackground(Void... voidArr) {
            String[] strArr;
            File[] listFiles;
            ActivityMovieMaker.this.strings = ActivityMovieMaker.this.b("frame");
            for (String str : ActivityMovieMaker.this.strings) {
                ArrayList<Item> arrayList = ActivityMovieMaker.this.items;
                StringBuilder sb = new StringBuilder();
                sb.append("assets://");
                sb.append(str);
                arrayList.add(new Item(sb.toString(), ActivityMovieMaker.BOOLEAN));
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(ActivityMovieMaker.this.getFilesDir());
            sb2.append("/VideoFrame");
            File file = new File(sb2.toString());
            if (!file.exists()) {
                file.mkdirs();
            }
            int i = 0;
            for (File file2 : file.listFiles()) {
                if (Utils.isFromOnlineFrame && file2.getAbsolutePath().equals(Utils.onlineFramePath)) {
                    Utils.framePostion = i;
                    ActivityMovieMaker.this.anInt4 = ActivityMovieMaker.this.strings.length + i;
                }
                ArrayList<Item> arrayList2 = ActivityMovieMaker.this.items;
                StringBuilder sb3 = new StringBuilder();
                sb3.append("file://");
                sb3.append(file2.getAbsolutePath());
                sb3.append("/frame.png");
                arrayList2.add(new Item(sb3.toString(), ActivityMovieMaker.BOOLEAN));
                i++;
            }
            if (Utils.isFromOnlineFrame) {
                ActivityMovieMaker.this.g();
                Utils.isFromOnlineFrame = false;
            }
            int size = ActivityMovieMaker.this.items.size();
            for (int i2 = 0; i2 < size; i2++) {
                final int i1 = i2;
                if (!ActivityMovieMaker.this.b.isCancelled()) {
                    ActivityMovieMaker.this.runOnUiThread(new Runnable() {
                        public void run() {
                            LinearLayout linearLayout = new LinearLayout(ActivityMovieMaker.this.getApplicationContext());
                            linearLayout.setLayoutParams(new LayoutParams(-2, -1));
                            linearLayout.setGravity(17);
                            linearLayout.setPadding(5, 5, 5, 5);
                            View inflate = ActivityMovieMaker.this.getLayoutInflater().inflate(R.layout.lay_thumb, null);
                            LinearLayout linearLayout2 = inflate.findViewById(R.id.llThumb);
                            ImageView imageView = inflate.findViewById(R.id.ivThumb);
                            TextView textView = inflate.findViewById(R.id.tvEffectName);
                            textView.setText("");
                            textView.setLayoutParams(new LayoutParams(1, 1));
                            ActivityMovieMaker.this.imageLoader.displayImage(ActivityMovieMaker.this.items.get(i1).path, imageView, ActivityMovieMaker.this.displayImageOptions);
                            linearLayout2.setTag(Integer.valueOf(i1));
                            linearLayout2.setOnClickListener(ActivityMovieMaker.this.onClickListener);
                            ActivityMovieMaker.this.linearLayouts.add(linearLayout2);
                            linearLayout.addView(inflate);
                            ActivityMovieMaker.this.linearLayout.addView(linearLayout);
                            ActivityMovieMaker.this.frameLayouts.add((FrameLayout) imageView.getParent());
                            if (i1 == ActivityMovieMaker.this.anInt4) {
                                ((FrameLayout) imageView.getParent()).setBackgroundResource(R.drawable.selectframe);
                            }
                        }
                    });
                }
            }
            return Boolean.valueOf(ActivityMovieMaker.BOOLEAN);
        }
    }

    class c implements OnClickListener {
        c() {
        }

        @Override
        public void onClick(View view) {
            ActivityMovieMaker.this.button1.setBackgroundResource(R.drawable.play2);
            ActivityMovieMaker.this.horizontalScrollView.setVisibility(8);
            ActivityMovieMaker.this.linearLayout1.setVisibility(8);
            if (ActivityMovieMaker.this.aBoolean1) {
                ActivityMovieMaker.this.relativeLayout.setVisibility(0);
                return;
            }
            ActivityMovieMaker.this.aBoolean3 = false;
            if (ActivityMovieMaker.this.aBoolean) {
                Toast.makeText(ActivityMovieMaker.mContext, "MyAudio list is loading..try again afater some seconds", Toast.LENGTH_LONG).show();
                return;
            }
            ActivityMovieMaker.this.button3.setClickable(ActivityMovieMaker.BOOLEAN);
            try {
                if (ActivityMovieMaker.this.mediaPlayer != null) {
                    ActivityMovieMaker.this.mediaPlayer.pause();
                }
                if (ActivityMovieMaker.this.aBoolean4) {
                    ActivityMovieMaker.this.anInt1 = -1;
                    ActivityMovieMaker.this.a(ActivityMovieMaker.this.imageView, ActivityMovieMaker.this.anInt1, false);
                    ActivityMovieMaker.this.aBoolean4 = false;
                    ActivityMovieMaker.this.handler1.removeCallbacks(ActivityMovieMaker.this.runnable);
                    ActivityMovieMaker.this.seekBar1.setProgress(0);
                }
            } catch (Exception unused) {
            }
            if (ActivityMovieMaker.this.frameLayout1.getVisibility() != 0) {
                ActivityMovieMaker.this.j();
                ActivityMovieMaker.this.button.setBackgroundResource(R.drawable.music_normal);
                ActivityMovieMaker.this.f();
                ActivityMovieMaker.this.adapterSelectMusic.notifyDataSetChanged();
                ActivityMovieMaker.this.frameLayout1.setVisibility(0);
                if (ActivityMovieMaker.this.aBoolean) {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(new MusicModel("", ""));
                    ActivityMovieMaker.this.recyclerView.setAdapter(new AdapterSelectMusic(ActivityMovieMaker.mContext, arrayList));
                    ActivityMovieMaker.this.recyclerView.setAdapter(ActivityMovieMaker.this.adapterSelectMusic);
                    return;
                }
                return;
            }
            ActivityMovieMaker.this.button.setBackgroundResource(R.drawable.music_normal);
        }
    }

    class d implements OnClickListener {
        d() {
        }

        @Override
        public void onClick(View view) {
            int i = 0;
            while (i < ActivityMovieMaker.this.linearLayouts.size()) {
                if (view == ActivityMovieMaker.this.linearLayouts.get(i)) {
                    ActivityMovieMaker.this.anInt4 = i;
                    for (int i2 = 0; i2 < ActivityMovieMaker.this.frameLayouts.size(); i2++) {
                        if (i2 == ActivityMovieMaker.this.anInt4) {
                            ActivityMovieMaker.this.frameLayouts.get(i2).setBackgroundResource(R.drawable.selected_frame);
                        } else {
                            ActivityMovieMaker.this.frameLayouts.get(i2).setBackgroundResource(R.drawable.filter_border);
                        }
                    }
                    ActivityMovieMaker.this.button1.setBackgroundResource(R.drawable.play2);
                    if (ActivityMovieMaker.this.aBoolean4) {
                        ActivityMovieMaker.this.anInt1 = -1;
                        ActivityMovieMaker.this.a(ActivityMovieMaker.this.imageView, ActivityMovieMaker.this.anInt1, false);
                        ActivityMovieMaker.this.aBoolean4 = false;
                        ActivityMovieMaker.this.handler1.removeCallbacks(ActivityMovieMaker.this.runnable);
                        ActivityMovieMaker.this.seekBar1.setProgress(0);
                    }
                    if (ActivityMovieMaker.this.mediaPlayer != null) {
                        ActivityMovieMaker.this.mediaPlayer.pause();
                        ActivityMovieMaker.this.mediaPlayer.seekTo(ActivityMovieMaker.this.anInt3);
                    }
                    if (i == 0) {
                        Utils.framePostion = -1;
                        ActivityMovieMaker.this.imageView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
                        ActivityMovieMaker.this.imageView.setScaleType(ScaleType.FIT_CENTER);
                        ActivityMovieMaker.this.imageView1.setImageBitmap(null);
                        return;
                    }
                    Utils.framePostion = i;
                    Item item = ActivityMovieMaker.this.items.get(i);
                    if (item.isAvailable) {
                        FramePointSet a2 = ActivityMovieMaker.this.a(ActivityMovieMaker.this.frameLayout.getWidth(), ActivityMovieMaker.this.frameLayout.getHeight());
                        if (a2 == null) {
                            ActivityMovieMaker.this.imageView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
                            ActivityMovieMaker.this.imageView.setScaleType(ScaleType.FIT_CENTER);
                        } else {
                            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(a2.width, a2.height);
                            layoutParams.setMargins(a2.left, a2.top, 0, 0);
                            ActivityMovieMaker.this.imageView.setLayoutParams(layoutParams);
                            ActivityMovieMaker.this.imageView.setScaleType(ScaleType.FIT_XY);
                        }
                        try {
                            ActivityMovieMaker.this.imageView1.setImageBitmap(BitmapCompression.decodeSampledBitmapFromAssets(ActivityMovieMaker.mContext, item.path.replace("assets://", ""), Utils.width, Utils.width));
                            return;
                        } catch (IOException unused) {
                            ActivityMovieMaker.this.g();
                            return;
                        }
                    } else {
                        return;
                    }
                } else {
                    i++;
                }
            }
        }
    }

    class e implements OnClickListener {
        e() {
        }

        @Override
        public void onClick(View view) {
            ActivityMovieMaker.this.button1.setBackgroundResource(R.drawable.play2);
            view.setClickable(false);
            ActivityMovieMaker.this.horizontalScrollView.setVisibility(0);
            ActivityMovieMaker.this.frameLayout1.setVisibility(8);
            ActivityMovieMaker.this.linearLayout1.setVisibility(8);
            ActivityMovieMaker.this.relativeLayout.setVisibility(8);
            if (ActivityMovieMaker.this.aBoolean4) {
                ActivityMovieMaker.this.anInt1 = -1;
                ActivityMovieMaker.this.a(ActivityMovieMaker.this.imageView, ActivityMovieMaker.this.anInt1, false);
                ActivityMovieMaker.this.aBoolean4 = false;
                ActivityMovieMaker.this.handler1.removeCallbacks(ActivityMovieMaker.this.runnable);
                ActivityMovieMaker.this.seekBar1.setProgress(0);
            }
            if (ActivityMovieMaker.this.mediaPlayer != null) {
                ActivityMovieMaker.this.mediaPlayer.pause();
                ActivityMovieMaker.this.mediaPlayer.seekTo(ActivityMovieMaker.this.anInt3);
            }
            ActivityMovieMaker.this.j();
            ActivityMovieMaker.this.aBoolean3 = false;
            ActivityMovieMaker.this.button3.setBackgroundResource(R.drawable.frame_presed);
        }
    }

    class f implements OnClickListener {
        f() {
        }

        @Override
        public void onClick(View view) {
            try {
                ActivityMovieMaker.this.aBoolean2 = false;
                if (!ActivityMovieMaker.this.aBoolean4) {
                    ActivityMovieMaker.this.button1.setBackgroundResource(R.drawable.pause2);
                    if (ActivityMovieMaker.this.bitmaps.size() <= 0) {
                        ActivityMovieMaker.this.h();
                    }
                    if (ActivityMovieMaker.this.bitmaps.size() > 0) {
                        ActivityMovieMaker.this.imageView.setImageBitmap(ActivityMovieMaker.this.bitmaps.get(0));
                        ActivityMovieMaker.this.a(ActivityMovieMaker.this.imageView, ActivityMovieMaker.this.anInt1 + 1, false);
                        if (ActivityMovieMaker.this.mediaPlayer != null) {
                            ActivityMovieMaker.this.mediaPlayer.start();
                        }
                        ActivityMovieMaker.this.seekBar1.setMax(Math.round(ActivityMovieMaker.this.aFloat * 1000.0f));
                        ActivityMovieMaker.this.seekBar1.setProgress(0);
                        ActivityMovieMaker.this.handler1.postDelayed(ActivityMovieMaker.this.runnable, 100);
                    } else {
                        Toast.makeText(ActivityMovieMaker.this.getApplicationContext(), "image copy failed", 0).show();
                    }
                } else if (ActivityMovieMaker.this.bitmaps.size() > 0) {
                    ActivityMovieMaker.this.button1.setBackgroundResource(R.drawable.play2);
                    ActivityMovieMaker.this.a(ActivityMovieMaker.this.imageView, ActivityMovieMaker.this.anInt1, false);
                    ActivityMovieMaker.this.handler1.removeCallbacks(ActivityMovieMaker.this.runnable);
                    ActivityMovieMaker.this.seekBar1.setProgress(0);
                    if (ActivityMovieMaker.this.mediaPlayer != null && ActivityMovieMaker.this.mediaPlayer.isPlaying()) {
                        ActivityMovieMaker.currentSeek = ActivityMovieMaker.this.mediaPlayer.getCurrentPosition();
                        ActivityMovieMaker.this.mediaPlayer.pause();
                    }
                }
                ActivityMovieMaker.this.aBoolean4 ^= ActivityMovieMaker.BOOLEAN;
            } catch (Exception unused) {
            }
        }
    }

    class g implements OnClickListener {
        g() {
        }

        @Override
        public void onClick(View view) {
            ActivityMovieMaker.this.button1.setBackgroundResource(R.drawable.play2);
            ActivityMovieMaker.this.aBoolean3 = false;
            ActivityMovieMaker.this.horizontalScrollView.setVisibility(8);
            ActivityMovieMaker.this.button3.setClickable(ActivityMovieMaker.BOOLEAN);
            ActivityMovieMaker.this.linearLayout1.setVisibility(0);
            ActivityMovieMaker.this.relativeLayout.setVisibility(8);
            ActivityMovieMaker.this.frameLayout1.setVisibility(8);
            if (ActivityMovieMaker.this.aBoolean4) {
                ActivityMovieMaker.this.anInt1 = -1;
                ActivityMovieMaker.this.a(ActivityMovieMaker.this.imageView, ActivityMovieMaker.this.anInt1, false);
                ActivityMovieMaker.this.aBoolean4 = false;
                ActivityMovieMaker.this.handler1.removeCallbacks(ActivityMovieMaker.this.runnable);
                ActivityMovieMaker.this.seekBar1.setProgress(0);
            }
            if (ActivityMovieMaker.this.mediaPlayer != null) {
                ActivityMovieMaker.this.mediaPlayer.pause();
                ActivityMovieMaker.this.mediaPlayer.seekTo(ActivityMovieMaker.this.anInt3);
            }
            ActivityMovieMaker.this.j();
            ActivityMovieMaker.this.button4.setBackgroundResource(R.drawable.time_normal);
        }
    }


    @SuppressLint({"StaticFieldLeak"})
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.aBoolean5 = false;


        refreshAd();
        setContentView(R.layout.activity_movie_maker_slideshow);
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView textView = toolbar.findViewById(R.id.toolbar_title);
        StringBuilder sb = new StringBuilder();
        sb.append("Preview(");
        sb.append(Utils.myUri.size());
        sb.append(" photos)");
        textView.setText(sb.toString());
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (BOOLEAN || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(BOOLEAN);
            supportActionBar.setDisplayShowTitleEnabled(false);
            this.ffmpeg = FFmpeg.getInstance(this);
            n();
            Utils.filterIndex = -1;
            Utils.framePostion = -1;
            Utils.audioSelected = -1;
            mContext = this;
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            Utils.width = displayMetrics.widthPixels;
            Utils.height = displayMetrics.heightPixels;
            this.frameLayout1 = findViewById(R.id.flRecycle);
            ImageLoader.getInstance().init(new Builder(this).defaultDisplayImageOptions(this.displayImageOptions).memoryCache(new WeakMemoryCache()).build());
            this.imageLoader = ImageLoader.getInstance();
            e();
            this.animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_top_out);
            this.animation.setAnimationListener(this.ai);
            StringBuilder sb2 = new StringBuilder();
            sb2.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
            sb2.append("/");
            sb2.append(getResources().getString(R.string.MainFolderName));
            sb2.append("/");
            sb2.append(getResources().getString(R.string.PhotoToVideo));
            StringBuilder sb3 = new StringBuilder(sb2.toString());
            sb3.append("/");
            sb3.append("/temp");
            sb3.append("/slide_00001.jpg");
            File file = new File(sb3.toString());
            if (file.exists()) {
                this.imageView.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
            }
            this.anInt = Utils.width - ((Utils.width * 15) / 480);
            this.b = new b();
            this.b.execute();
            this.recyclerView = findViewById(R.id.recyclerView);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(1);
            linearLayoutManager.scrollToPosition(0);
            this.recyclerView.setLayoutManager(linearLayoutManager);
            this.recyclerView.setHasFixedSize(false);
            new AsyncTask<Void, Void, Void>() {


                public Void doInBackground(Void... voidArr) {
                    ActivityMovieMaker.this.aBoolean = ActivityMovieMaker.BOOLEAN;
                    ActivityMovieMaker.this.adapterSelectMusic = new AdapterSelectMusic(ActivityMovieMaker.this, ActivityMovieMaker.this.d());
                    return null;
                }


                public void onPostExecute(Void voidR) {
                    super.onPostExecute(voidR);
                    ActivityMovieMaker.this.aBoolean = false;
                    ViewGroup.LayoutParams layoutParams = ActivityMovieMaker.this.frameLayout1.getLayoutParams();
                    layoutParams.width = Utils.width;
                    Log.d("Size of array", new StringBuilder(String.valueOf(ActivityMovieMaker.this.anInt2)).toString());
                    if (ActivityMovieMaker.this.anInt2 * 80 >= Utils.height / 2) {
                        layoutParams.height = Utils.height / 2;
                    } else {
                        layoutParams.height = ActivityMovieMaker.this.anInt2 * 80;
                    }
                    ActivityMovieMaker.this.frameLayout1.setLayoutParams(layoutParams);
                    ActivityMovieMaker.this.recyclerView.setAdapter(ActivityMovieMaker.this.adapterSelectMusic);
                }
            }.execute();
            this.interstitialAd = new InterstitialAd(this);
            this.interstitialAd.setAdUnitId(getString(R.string.interstitial_id_admob));
            this.interstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    ActivityMovieMaker.this.c();
                }
            });
            a();
            return;
        }
        throw new AssertionError();
    }

    private void a() {
        if (!this.interstitialAd.isLoading() && !this.interstitialAd.isLoaded()) {
            this.interstitialAd.loadAd(new AdRequest.Builder().build());
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

    @SuppressLint({"SimpleDateFormat"})
    public void Command() {
        String str;
        String[] strArr;
        String format = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss").format(Calendar.getInstance().getTime());
        if (Utils.filterIndex > -1 || Utils.framePostion > -1) {
            StringBuilder sb = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
            sb2.append("/");
            sb2.append(getResources().getString(R.string.MainFolderName));
            sb2.append("/");
            sb2.append(getResources().getString(R.string.PhotoToVideo));
            sb.append(sb2.toString());
            sb.append("/temp");
            str = sb.toString();
        } else {
            StringBuilder sb3 = new StringBuilder();
            StringBuilder sb4 = new StringBuilder();
            sb4.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
            sb4.append("/");
            sb4.append(getResources().getString(R.string.MainFolderName));
            sb4.append("/");
            sb4.append(getResources().getString(R.string.PhotoToVideo));
            sb3.append(sb4.toString());
            sb3.append("/temp");
            str = sb3.toString();
        }
        StringBuilder sb5 = new StringBuilder();
        StringBuilder sb6 = new StringBuilder();
        sb6.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb6.append("/");
        sb6.append(getResources().getString(R.string.MainFolderName));
        sb6.append("/");
        sb6.append(getResources().getString(R.string.PhotoToVideo));
        sb5.append(sb6.toString());
        sb5.append("/video_");
        sb5.append(format);
        sb5.append(".mp4");
        this.string2 = sb5.toString();
        if (!this.aBoolean1) {
            StringBuilder sb7 = new StringBuilder();
            sb7.append("1/");
            sb7.append(this.string1);
            StringBuilder sb8 = new StringBuilder();
            sb8.append(str);
            sb8.append("/slide_%05d.jpg");
            strArr = new String[]{"-y", "-r", sb7.toString(), "-i", sb8.toString(), "-vcodec", "libx264", "-r", "2", "-pix_fmt", "yuv420p", "-preset", "ultrafast", this.string2};
            Log.d("Command", Arrays.toString(strArr));
        } else if (this.mediaPlayer.getDuration() - this.anInt3 < Math.round(this.aFloat) * 1000) {
            StringBuilder sb9 = new StringBuilder();
            sb9.append("1/");
            sb9.append(this.string1);
            StringBuilder sb10 = new StringBuilder();
            sb10.append(str);
            sb10.append("/slide_%05d.jpg");
            strArr = new String[]{"-y", "-r", sb9.toString(), "-i", sb10.toString(), "-ss", String.valueOf(this.anInt3 / 1000), "-i", this.string, "-map", "0:0", "-map", "1:0", "-vcodec", "libx264", "-acodec", "aac", "-r", "2", "-pix_fmt", "yuv420p", "-strict", "experimental", "-preset", "ultrafast", this.string2};
            Log.d("Command", Arrays.toString(strArr));
        } else {
            StringBuilder sb11 = new StringBuilder();
            sb11.append("1/");
            sb11.append(this.string1);
            StringBuilder sb12 = new StringBuilder();
            sb12.append(str);
            sb12.append("/slide_%05d.jpg");
            strArr = new String[]{"-y", "-r", sb11.toString(), "-i", sb12.toString(), "-ss", String.valueOf(this.anInt3 / 1000), "-i", this.string, "-map", "0:0", "-map", "1:0", "-vcodec", "libx264", "-acodec", "aac", "-r", "2", "-pix_fmt", "yuv420p", "-strict", "experimental", "-shortest", "-preset", "ultrafast", this.string2};
            Log.d("Command", Arrays.toString(strArr));
        }
        a(strArr, this.string2);
    }

    private void a(String[] strArr, final String str) {
        try {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.show();
            this.ffmpeg.execute(strArr, new ExecuteBinaryResponseHandler() {
                @Override
                public void onFailure(String str) {
                    Log.d("ffmpegfailure", str);
                    try {
                        new File(str).delete();
                        ActivityMovieMaker.this.deleteFromGallery(str);
                        Toast.makeText(ActivityMovieMaker.this, "Error Creating Video", Toast.LENGTH_LONG).show();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }

                @Override
                public void onSuccess(String str) {
                    progressDialog.dismiss();
                    ActivityMovieMaker.this.aBoolean5 = false;
                    ActivityMovieMaker.this.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(ActivityMovieMaker.this.string2))));
                    ActivityMovieMaker.this.b();
                }

                @Override
                public void onProgress(String str) {
                    Log.d("ffmpegResponse", str);
                    StringBuilder sb = new StringBuilder();
                    sb.append("progress : ");
                    sb.append(str);
                    progressDialog.setMessage(sb.toString());
                }

                @Override
                public void onStart() {
                    progressDialog.setMessage("Processing...");
                }

                @Override
                public void onFinish() {
                    ActivityMovieMaker.this.m();
                    ActivityMovieMaker.this.k();
                    ActivityMovieMaker.this.i();
                    ActivityMovieMaker.this.l();
                    progressDialog.dismiss();
                    ActivityMovieMaker.this.refreshGallery(str);
                }
            });
            getWindow().clearFlags(16);
        } catch (FFmpegCommandAlreadyRunningException unused) {
        }
    }


    public void onPause() {
        super.onPause();
    }


    public ArrayList<MusicModel> d() {
        ArrayList<MusicModel> arrayList = new ArrayList<>();
        Cursor managedQuery = managedQuery(Media.EXTERNAL_CONTENT_URI, new String[]{"_display_name", "_data"}, null, null, "LOWER(title) ASC");
        if (managedQuery != null) {
            if (managedQuery.moveToFirst()) {
                do {
                    String string = managedQuery.getString(0);
                    if (string != null) {
                        String fileExt = getFileExt(string);
                        if ((fileExt != null && fileExt.contains("mp3")) || fileExt.contains("a")) {
                            arrayList.add(new MusicModel(string, managedQuery.getString(1)));
                        }
                    }
                } while (managedQuery.moveToNext());
            }
            this.anInt2 = arrayList.size();
        }
        return arrayList;
    }

    public static String getFileExt(String str) {
        try {
            return str.length() > 3 ? str.substring(str.lastIndexOf(".") + 1) : str;
        } catch (Exception unused) {
            return str;
        }
    }

    private void e() {
        this.textView4 = findViewById(R.id.tvSeekUserSec);
        this.textView6 = findViewById(R.id.tvEndVideo1);
        this.seekBar1 = findViewById(R.id.sbVideo);
        this.textView5 = findViewById(R.id.tvInterval);
        this.seekBar = findViewById(R.id.sbInterval);
        this.seekBar.setOnSeekBarChangeListener(this);
        this.seekBar.setProgress(20);
        this.seekBar.setMax(100);
        this.imageView = findViewById(R.id.slide_1);
        this.imageView1 = findViewById(R.id.slide_frame);
        if (Utils.isFromOnlineFrame) {
            ImageView imageView = this.imageView1;
            StringBuilder sb = new StringBuilder();
            sb.append(Utils.onlineFramePath);
            sb.append("/frame.png");
            imageView.setImageBitmap(BitmapCompression.getResizedBitmap(BitmapFactory.decodeFile(sb.toString()), Utils.width, Utils.width));
        }
        this.button1 = findViewById(R.id.btnPlay);
        this.imageView.setOnClickListener(this.onClickListener1);
        this.button1.setOnClickListener(this.onClickListener1);
        this.frameLayout = findViewById(R.id.flPreview);
        this.button = findViewById(R.id.selectmusic);
        this.button.setOnClickListener(this.onClickListener2);
        this.textView1 = findViewById(R.id.tvMusicTrackName);
        this.textView3 = findViewById(R.id.tvTrackDuration);
        this.relativeLayout = findViewById(R.id.llSelctedMusic);
        this.button2 = findViewById(R.id.btnCloseMusic);
        this.button2.setOnClickListener(this.clickListener2);
        this.button3 = findViewById(R.id.btnFrame);
        this.button3.setOnClickListener(this.clickListener);
        this.button4 = findViewById(R.id.btnSecond);
        this.button4.setOnClickListener(this.clickListener1);
        this.linearLayout = findViewById(R.id.llFrameList);
        this.horizontalScrollView = findViewById(R.id.hsFrameList);
        this.seekBar2 = findViewById(R.id.sbMusic);
        this.seekBar2.setOnSeekBarChangeListener(this);
        this.textView2 = findViewById(R.id.tvStartVideo);
        this.textView = findViewById(R.id.tvEndVideo);
        this.linearLayout1 = findViewById(R.id.llSeekbar);
    }


    public void f() {
        this.linearLayout1.setVisibility(8);
        this.relativeLayout.setVisibility(8);
    }


    public void g() {
        final Item item = this.items.get(this.anInt4);
        runOnUiThread(new Runnable() {
            public void run() {
                StringBuilder sb = new StringBuilder();
                sb.append("CollageUtils w: ");
                sb.append(Utils.width);
                Log.d("word", sb.toString());
                ActivityMovieMaker.this.imageView1.setImageBitmap(BitmapCompression.decodeSampledBitmapFromLocal(ActivityMovieMaker.mContext, item.path.replace("file:", "").replace("thumb", "frame"), Utils.width, Utils.width));
            }
        });
        StringBuilder sb = new StringBuilder(String.valueOf(new File(item.path).getParentFile().getPath()));
        sb.append("/dimen.txt");
        String replace = sb.toString().replace("file:", "");
        FramePointSet framePointSet = new FramePointSet();
        framePointSet.left = 0;
        framePointSet.top = 0;
        framePointSet.width = Utils.width;
        framePointSet.height = Utils.height;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("fps w: ");
        sb2.append(Utils.width);
        Log.d("word", sb2.toString());
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(replace)));
            int[] intArray = toIntArray(bufferedReader.readLine());
            StringBuilder sb3 = new StringBuilder();
            sb3.append("flpreview width: ");
            sb3.append(this.frameLayout.getHeight());
            Log.d("word", sb3.toString());
            framePointSet.left = (this.anInt * intArray[0]) / 100;
            framePointSet.top = (this.anInt * intArray[1]) / 100;
            int i2 = (this.anInt * intArray[3]) / 100;
            framePointSet.width = this.anInt - (framePointSet.left + ((this.anInt * intArray[2]) / 100));
            framePointSet.height = this.anInt - (framePointSet.top + i2);
            StringBuilder sb4 = new StringBuilder();
            sb4.append("fps w: ");
            sb4.append(framePointSet.width);
            Log.d("word", sb4.toString());
            bufferedReader.close();
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        final FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(framePointSet.width, framePointSet.height);
        layoutParams.setMargins(framePointSet.left, framePointSet.top, 0, 0);
        runOnUiThread(new Runnable() {
            public void run() {
                ActivityMovieMaker.this.imageView.setLayoutParams(layoutParams);
                ActivityMovieMaker.this.imageView.setScaleType(ScaleType.FIT_XY);
            }
        });
    }

    public int[] toIntArray(String str) {
        String[] split = str.split("\\s");
        int[] iArr = new int[split.length];
        for (int i2 = 0; i2 < split.length; i2++) {
            try {
                iArr[i2] = Integer.parseInt(split[i2]);
            } catch (NumberFormatException unused) {
                iArr[i2] = -1;
            }
        }
        return iArr;
    }


    public void a(String str) {
        this.arrayList = new ArrayList<>();
        File file = new File(str);
        if (file.exists()) {
            File[] listFiles = file.listFiles(new MyFileNameFilter());
            if (listFiles != null) {
                int length = listFiles.length;
                if (length > 0) {
                    for (int i2 = 0; i2 < length; i2++) {
                        if (!listFiles[i2].getName().equals("slide_00000.jpg")) {
                            this.arrayList.add(listFiles[i2].getAbsolutePath());
                        }
                    }
                }
            }
        }
    }


    public Bitmap a(boolean z2, boolean z3) {
        FramePointSet framePointSet;
        Item item;
        this.frameLayout.getWidth();
        this.frameLayout.getHeight();
        Paint paint = new Paint();
        Bitmap createBitmap = Bitmap.createBitmap(this.anInt6, this.anInt6, Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawRect(0.0f, 0.0f, (float) this.anInt6, (float) this.anInt6, paint);
        if (z2) {
            item = this.items.get(Utils.framePostion);
            framePointSet = item.path.contains(getPackageName()) ? a(item) : a(this.anInt6, this.anInt6);
        } else {
            item = null;
            framePointSet = null;
        }
        if (z2) {
            if (framePointSet == null) {
                Bitmap resizedBitmap = BitmapCompression.getResizedBitmap(Utils.bitmap);
                canvas.drawBitmap(resizedBitmap, (float) ((canvas.getWidth() / 2) - (resizedBitmap.getWidth() / 2)), (float) ((canvas.getHeight() / 2) - (resizedBitmap.getHeight() / 2)), null);
            } else {
                canvas.drawBitmap(Bitmap.createScaledBitmap(Utils.bitmap, framePointSet.width, framePointSet.height, false), (float) framePointSet.left, (float) framePointSet.top, null);
            }
            try {
                if (item.path.contains(getPackageName())) {
                    canvas.drawBitmap(Bitmap.createScaledBitmap(BitmapCompression.decodeSampledBitmapFromLocal(mContext, item.path.replace("file://", ""), Utils.width, Utils.width), this.anInt6, this.anInt6, false), 0.0f, 0.0f, null);
                } else {
                    canvas.drawBitmap(Bitmap.createScaledBitmap(BitmapCompression.decodeSampledBitmapFromAssets(mContext, item.path.replace("assets://", ""), Utils.width, Utils.width), this.anInt6, this.anInt6, false), 0.0f, 0.0f, null);
                }
            } catch (IOException unused) {
            }
            Utils.clgstickerviewsList.iterator();
        } else {
            Bitmap resizedBitmap2 = BitmapCompression.getResizedBitmap(Utils.bitmap);
            canvas.drawBitmap(resizedBitmap2, (float) ((canvas.getWidth() / 2) - (resizedBitmap2.getWidth() / 2)), (float) ((canvas.getHeight() / 2) - (resizedBitmap2.getHeight() / 2)), null);
            Utils.clgstickerviewsList.iterator();
        }
        return createBitmap;
    }

    private FramePointSet a(Item item) {
        StringBuilder sb = new StringBuilder(String.valueOf(new File(item.path).getParentFile().getPath()));
        sb.append("/dimen.txt");
        String replace = sb.toString().replace("file:", "");
        FramePointSet framePointSet = new FramePointSet();
        framePointSet.left = 0;
        framePointSet.top = 0;
        framePointSet.width = Utils.width;
        framePointSet.height = Utils.height;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(replace.replace("file:", ""))));
            int[] intArray = toIntArray(bufferedReader.readLine());
            framePointSet.left = (Utils.width * intArray[0]) / 100;
            framePointSet.top = (Utils.width * intArray[1]) / 100;
            int i2 = (Utils.width * intArray[3]) / 100;
            framePointSet.width = Utils.width - (framePointSet.left + ((Utils.width * intArray[2]) / 100));
            framePointSet.height = Utils.width - (framePointSet.top + i2);
            bufferedReader.close();
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        return framePointSet;
    }


    public FramePointSet a(int i2, int i3) {
        FramePointSet framePointSet = new FramePointSet();
        switch (Utils.framePostion) {
            case 0:
                return null;
            case 1:
                int i4 = (i2 * 5) / 100;
                framePointSet.left = i4;
                int i5 = (i3 * 5) / 100;
                framePointSet.top = i5;
                framePointSet.width = i2 - (framePointSet.left + i4);
                framePointSet.height = i3 - (framePointSet.top + i5);
                return framePointSet;
            case 2:
                int i6 = (i2 * 5) / 100;
                framePointSet.left = i6;
                int i7 = (i3 * 5) / 100;
                framePointSet.top = i7;
                framePointSet.width = i2 - (framePointSet.left + i6);
                framePointSet.height = i3 - (framePointSet.top + i7);
                return framePointSet;
            case 3:
                int i8 = (i2 * 2) / 100;
                framePointSet.left = i8;
                int i9 = (i3 * 2) / 100;
                framePointSet.top = i9;
                framePointSet.width = i2 - (framePointSet.left + i8);
                framePointSet.height = i3 - (framePointSet.top + i9);
                return framePointSet;
            case 4:
                int i10 = (i2 * 5) / 100;
                framePointSet.left = i10;
                int i11 = (i3 * 5) / 100;
                framePointSet.top = i11;
                framePointSet.width = i2 - (framePointSet.left + i10);
                framePointSet.height = i3 - (framePointSet.top + i11);
                return framePointSet;
            case 5:
                int i12 = (i2 * 0) / 100;
                framePointSet.left = i12;
                int i13 = (i3 * 5) / 100;
                framePointSet.top = i13;
                framePointSet.width = i2 - (framePointSet.left + i12);
                framePointSet.height = i3 - (framePointSet.top + i13);
                return framePointSet;
            case 6:
                int i14 = (i2 * 0) / 100;
                framePointSet.left = i14;
                int i15 = (i3 * 3) / 100;
                framePointSet.top = i15;
                framePointSet.width = i2 - (framePointSet.left + i14);
                framePointSet.height = i3 - (framePointSet.top + i15);
                return framePointSet;
            case 7:
                int i16 = (i2 * 3) / 100;
                framePointSet.left = i16;
                int i17 = (i3 * 3) / 100;
                framePointSet.top = i17;
                framePointSet.width = i2 - (framePointSet.left + i16);
                framePointSet.height = i3 - (framePointSet.top + i17);
                return framePointSet;
            case 8:
                int i18 = (i2 * 1) / 100;
                framePointSet.left = i18;
                framePointSet.top = (i3 * 3) / 100;
                int i19 = (i3 * 0) / 100;
                framePointSet.width = i2 - (framePointSet.left + i18);
                framePointSet.height = i3 - (framePointSet.top + i19);
                return framePointSet;
            case 9:
                int i20 = (i2 * 5) / 100;
                framePointSet.left = i20;
                int i21 = (i3 * 5) / 100;
                framePointSet.top = i21;
                framePointSet.width = i2 - (framePointSet.left + i20);
                framePointSet.height = i3 - (framePointSet.top + i21);
                return framePointSet;
            case 10:
                int i22 = (i2 * 0) / 100;
                framePointSet.left = i22;
                int i23 = (i3 * 0) / 100;
                framePointSet.top = i23;
                framePointSet.width = i2 - (framePointSet.left + i22);
                framePointSet.height = i3 - (framePointSet.top + i23);
                return framePointSet;
            case 11:
                int i24 = (i2 * 3) / 100;
                framePointSet.left = i24;
                int i25 = (i3 * 5) / 100;
                framePointSet.top = i25;
                framePointSet.width = i2 - (framePointSet.left + i24);
                framePointSet.height = i3 - (framePointSet.top + i25);
                return framePointSet;
            case 12:
                int i26 = (i2 * 0) / 100;
                framePointSet.left = i26;
                int i27 = (i3 * 0) / 100;
                framePointSet.top = i27;
                framePointSet.width = i2 - (framePointSet.left + i26);
                framePointSet.height = i3 - (framePointSet.top + i27);
                return framePointSet;
            case 13:
                int i28 = (i2 * 0) / 100;
                framePointSet.left = i28;
                int i29 = (i3 * 0) / 100;
                framePointSet.top = i29;
                framePointSet.width = i2 - (framePointSet.left + i28);
                framePointSet.height = i3 - (framePointSet.top + i29);
                return framePointSet;
            case 14:
                int i30 = (i2 * 5) / 100;
                framePointSet.left = i30;
                int i31 = (i3 * 5) / 100;
                framePointSet.top = i31;
                framePointSet.width = i2 - (framePointSet.left + i30);
                framePointSet.height = i3 - (framePointSet.top + i31);
                return framePointSet;
            case 15:
                int i32 = (i2 * 2) / 100;
                framePointSet.left = i32;
                int i33 = (i3 * 2) / 100;
                framePointSet.top = i33;
                framePointSet.width = i2 - (framePointSet.left + i32);
                framePointSet.height = i3 - (framePointSet.top + i33);
                return framePointSet;
            default:
                return null;
        }
    }


    public void a(int i2, boolean z2) {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb.append("/");
        sb.append(getResources().getString(R.string.MainFolderName));
        sb.append("/");
        sb.append(getResources().getString(R.string.PhotoToVideo));
        if (new File(new StringBuilder(sb.toString()).toString()).exists()) {
            if (z2) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
                sb2.append("/");
                sb2.append(getResources().getString(R.string.MainFolderName));
                sb2.append("/");
                sb2.append(getResources().getString(R.string.PhotoToVideo));
                StringBuilder sb3 = new StringBuilder(sb2.toString());
                sb3.append("/temp");
                str = sb3.toString();
            } else {
                StringBuilder sb4 = new StringBuilder();
                sb4.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
                sb4.append("/");
                sb4.append(getResources().getString(R.string.MainFolderName));
                sb4.append("/");
                sb4.append(getResources().getString(R.string.PhotoToVideo));
                str = new StringBuilder(sb4.toString()).toString();
            }
            File file = new File(str);
            if (!file.exists()) {
                file.mkdir();
            }
            try {
                StringBuilder sb5 = new StringBuilder();
                sb5.append("slide_");
                sb5.append(String.format(Locale.US, "%05d", Integer.valueOf(i2)));
                sb5.append(".jpg");
                FileOutputStream fileOutputStream = new FileOutputStream(new File(str, sb5.toString()));
                try {
                    Log.d("bitmp w", new StringBuilder(String.valueOf(Utils.bitmap.getWidth())).toString());
                    Log.d("bitmp h", new StringBuilder(String.valueOf(Utils.bitmap.getHeight())).toString());
                    Utils.bitmap.compress(CompressFormat.JPEG, 100, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
    }

    public static String formatTimeUnit(long j2) throws ParseException {
        return String.format("%02d:%02d", Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(j2)), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j2) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j2))));
    }

    public void onProgressChanged(SeekBar seekBar, int i2, boolean z2) {
        float f2;
        if (seekBar == this.seekBar) {
            float f3 = ((float) i2) / 10.0f;
            if (((double) Math.round(f3)) < 0.2d) {
                seekBar.setProgress(5);
                return;
            }
            double d2 = f3;
            if (d2 - Math.floor(d2) < 0.2d) {
                f2 = (float) Math.floor(d2);
                Log.i("Progres val", String.valueOf(Math.floor(f2)));
            } else if (d2 - Math.floor(d2) <= 0.2d || d2 - Math.floor(d2) > 0.7d) {
                f2 = (float) Math.round(f3);
                Log.i("Progres val", String.valueOf(Math.round(f2)));
            } else {
                f2 = (float) (Math.floor(d2) + 0.5d);
                Log.i("Progres val", String.valueOf(Math.floor(f2) + 0.5d));
            }
            Log.d("file size", new StringBuilder(String.valueOf(this.bitmaps.size())).toString());
            this.string1 = new StringBuilder(String.valueOf(f2)).toString();
            TextView textView = this.textView5;
            StringBuilder sb = new StringBuilder(String.valueOf(f2));
            sb.append(" Sec. per Photos");
            textView.setText(sb.toString());
            TextView textView2 = this.textView4;
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Movie Length : ");
            sb2.append(((float) this.bitmaps.size()) * f2);
            sb2.append(" Sec.");
            textView2.setText(sb2.toString());
            this.textView6.setText(new StringBuilder(formatTimeUnit(((long) (((float) this.bitmaps.size()) * f2)) * 1000)).toString());
            this.aFloat = ((float) this.bitmaps.size()) * f2;
            return;
        }
        this.anInt3 = i2;
        try {
            this.textView2.setText(formatTimeUnit(this.anInt3));
        } catch (ParseException e2) {
            e2.printStackTrace();
        }
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
        if (seekBar != this.seekBar) {
            if (this.mediaPlayer != null) {
                this.mediaPlayer.pause();
            }
            this.anInt1 = -1;
            a(this.imageView, this.anInt1, false);
            this.aBoolean4 = false;
        }
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        if (seekBar != this.seekBar && this.mediaPlayer != null) {
            this.mediaPlayer.start();
            this.mediaPlayer.seekTo(this.anInt3);
            this.mediaPlayer.pause();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (this.bitmaps.size() <= 0) {
            runOnUiThread(new Runnable() {
                public void run() {
                    ActivityMovieMaker.this.h();
                }
            });
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (nativeAd != null) {
            nativeAd.destroy();
        }
        getWindow().clearFlags(128);
        if (this.mediaPlayer != null) {
            if (this.mediaPlayer.isPlaying()) {
                this.mediaPlayer.stop();
                this.mediaPlayer.release();
            }


            this.mediaPlayer = null;
        }
        if (Utils.bitmap != null) {
            try {
                Utils.bitmap.recycle();
            } catch (Exception unused) {
            }
        }
        if (this.bitmaps != null) {
            this.bitmaps.clear();
        }
        System.gc();
    }


    @Override
    public void onActivityResult(int i2, int i3, Intent intent) {
        super.onActivityResult(i2, i3, intent);
        if (i3 == -1 && i2 == 99) {
            this.aBoolean2 = BOOLEAN;
            try {
                if (this.bitmaps.size() > 0) {
                    a(this.imageView, this.anInt1, BOOLEAN);
                    this.imageView.setImageBitmap(this.bitmaps.get(0));
                }
            } catch (Exception unused) {
            }
            this.aBoolean4 = false;
            this.imageView.setVisibility(0);
            this.textView1.setText(Utils.audioName);
            TextView textView = this.textView3;
            StringBuilder sb = new StringBuilder();
            sb.append("duration: ");
            sb.append(Utils.audioDuration);
            sb.append("s");
            textView.setText(sb.toString());
            this.relativeLayout.setVisibility(0);
            this.button.setVisibility(8);
            this.string = intent.getData().toString();
            try {
                if (this.mediaPlayer == null) {
                    this.mediaPlayer = new MediaPlayer();
                    this.mediaPlayer.setDataSource(this.string);
                    this.mediaPlayer.setAudioStreamType(3);
                    this.mediaPlayer.prepare();
                } else {
                    this.mediaPlayer.setDataSource(this.string);
                    this.mediaPlayer.setAudioStreamType(3);
                    this.mediaPlayer.prepare();
                }
                this.mediaPlayer.start();
                this.seekBar2.setMax(this.mediaPlayer.getDuration());
                this.seekBar2.setProgress(0);
                this.anInt3 = 0;
                this.mediaPlayer.seekTo(0);
                this.mediaPlayer.pause();
                this.textView2.setText("00:00");
                try {
                    this.textView.setText(formatTimeUnit(this.mediaPlayer.getDuration()));
                } catch (ParseException e2) {
                    e2.printStackTrace();
                }
                this.aBoolean1 = BOOLEAN;
            } catch (IOException unused2) {
            }
        }
        if (i3 == 0) {
            this.aBoolean1 = false;
        }
    }


    public void h() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.anInt5 = displayMetrics.heightPixels;
        this.anInt6 = displayMetrics.widthPixels;
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb.append("/");
        sb.append(getResources().getString(R.string.MainFolderName));
        sb.append("/");
        sb.append(getResources().getString(R.string.PhotoToVideo));
        StringBuilder sb2 = new StringBuilder(sb.toString());
        sb2.append("/temp");
        String sb3 = sb2.toString();
        if (this.bitmaps != null && this.bitmaps.size() > 0) {
            this.bitmaps.clear();
        }
        File file = new File(sb3);
        if (file.exists()) {
            File[] listFiles = file.listFiles(new MyFileNameFilter());
            if (listFiles != null) {
                int length = listFiles.length;
                if (length > 0) {
                    for (int i2 = 0; i2 < length; i2++) {
                        if (!listFiles[i2].getName().equals("slide_00000.jpg")) {
                            this.bitmaps.add(BitmapCompression.decodeFile(new File(listFiles[i2].getAbsolutePath()), this.anInt6 - 200, this.anInt6 + 0));
                            TextView textView = this.textView4;
                            StringBuilder sb4 = new StringBuilder();
                            sb4.append("Movie Length : ");
                            sb4.append(this.bitmaps.size() * 2);
                            sb4.append(" Sec.");
                            textView.setText(sb4.toString());
                            this.textView5.setText("2 Sec. per Photos");
                            this.textView6.setText(new StringBuilder(formatTimeUnit(this.bitmaps.size() * 2 * 1000)).toString());
                            this.string1 = "2";
                            this.aFloat = (float) (this.bitmaps.size() * 2);
                            this.seekBar1.setProgress(0);
                            this.seekBar1.setMax(this.bitmaps.size() * 2 * 1000);
                        }
                    }
                }
            }
        }
    }


    public void a(final ImageView imageView, final int i2, final boolean z2) {
        if (this.string1.equals("")) {
            this.string1 = "2";
        }
        int parseFloat = (int) (Float.parseFloat(this.string1) * 1000.0f);
        imageView.setVisibility(4);
        if (this.anInt1 != i2) {
            if (this.bitmaps.size() > 0) {
                imageView.setImageBitmap(this.bitmaps.get(i2));
            }
        } else if (this.bitmaps.size() > 0) {
            imageView.setImageBitmap(this.bitmaps.get(i2 + 1));
        }
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setInterpolator(new DecelerateInterpolator());
        alphaAnimation.setDuration(0);
        AlphaAnimation alphaAnimation2 = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation2.setInterpolator(new AccelerateInterpolator());
        alphaAnimation2.setStartOffset(parseFloat + 0);
        alphaAnimation2.setDuration(1);
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(alphaAnimation2);
        animationSet.setRepeatCount(1);
        imageView.setAnimation(animationSet);
        animationSet.setAnimationListener(new AnimationListener() {
            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                if (ActivityMovieMaker.this.anInt1 == i2) {
                    ActivityMovieMaker.this.imageView.setVisibility(0);
                    if (ActivityMovieMaker.this.aBoolean2) {
                        ActivityMovieMaker.this.anInt1 = -1;
                    }
                    if (ActivityMovieMaker.this.bitmaps.size() > 0 && Utils.filterIndex == -1) {
                        ActivityMovieMaker.this.imageView.setImageBitmap(ActivityMovieMaker.this.bitmaps.get(ActivityMovieMaker.this.anInt1 + 1));
                    }
                } else if (ActivityMovieMaker.this.bitmaps.size() - 1 > i2) {
                    ActivityMovieMaker.this.anInt1 = i2;
                    ActivityMovieMaker.this.a(imageView, i2 + 1, z2);
                } else {
                    if (z2) {
                        ActivityMovieMaker.this.a(imageView, 0, z2);
                    }
                    if (ActivityMovieMaker.this.mediaPlayer != null) {
                        ActivityMovieMaker.this.mediaPlayer.pause();
                        ActivityMovieMaker.this.mediaPlayer.seekTo(ActivityMovieMaker.this.anInt3);
                    }
                    ActivityMovieMaker.this.anInt1 = -1;
                    ActivityMovieMaker.this.aBoolean4 = false;
                    ActivityMovieMaker.this.imageView.setVisibility(0);
                    ActivityMovieMaker.this.button1.setBackgroundResource(R.drawable.play2);
                    ActivityMovieMaker.this.handler1.removeCallbacks(ActivityMovieMaker.this.runnable);
                    ActivityMovieMaker.this.seekBar1.setProgress(0);
                    if (ActivityMovieMaker.this.bitmaps.size() > 0 && Utils.filterIndex == -1) {
                        ActivityMovieMaker.this.imageView.setImageBitmap(ActivityMovieMaker.this.bitmaps.get(0));
                    }
                }
            }
        });
    }


    public String[] b(String str) {
        String[] strArr;
        try {
            strArr = getAssets().list(str);
        } catch (IOException e2) {
            e2.printStackTrace();
            strArr = null;
        }
        for (int i2 = 0; i2 < strArr.length; i2++) {
            StringBuilder sb = new StringBuilder(str);
            sb.append("/");
            sb.append(strArr[i2]);
            strArr[i2] = sb.toString();
        }
        return strArr;
    }


    public void i() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb.append("/");
        sb.append(getResources().getString(R.string.MainFolderName));
        sb.append("/");
        sb.append(getResources().getString(R.string.PhotoToVideo));
        StringBuilder sb2 = new StringBuilder(sb.toString());
        sb2.append("/temp");
        File file = new File(sb2.toString());
        if (file.exists()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File file2 : listFiles) {
                    if (file2.getName().endsWith(".jpg") || file2.getName().endsWith(".png")) {
                        file2.delete();
                    }
                }
            }
        }
    }


    public void j() {
        this.button.setBackgroundResource(R.drawable.music_normal);
        this.button3.setBackgroundResource(R.drawable.frame_normal);
        this.button4.setBackgroundResource(R.drawable.time_normal);
    }


    public void k() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb.append("/");
        sb.append(getResources().getString(R.string.MainFolderName));
        sb.append("/");
        sb.append(getResources().getString(R.string.PhotoToVideo));
        File file = new File(new StringBuilder(sb.toString()).toString());
        if (file.exists()) {
            String str = ".jpg";
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File file2 : listFiles) {
                    if (file2.getName().endsWith(str)) {
                        file2.delete();
                    }
                }
            }
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb2.append("/");
        sb2.append(getResources().getString(R.string.MainFolderName));
        sb2.append("/");
        sb2.append(getResources().getString(R.string.PhotoToVideo));
        StringBuilder sb3 = new StringBuilder(sb2.toString());
        sb3.append("/");
        sb3.append("/temp");
        File file3 = new File(sb3.toString());
        if (file3.exists()) {
            String str2 = ".jpg";
            File[] listFiles2 = file3.listFiles();
            if (listFiles2 != null) {
                for (File file4 : listFiles2) {
                    if (file4.getName().endsWith(str2)) {
                        file4.delete();
                    }
                }
            }
        }
        if (file3.exists()) {
            file3.delete();
        }
    }


    public void l() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb.append("/");
        sb.append(getResources().getString(R.string.MainFolderName));
        sb.append("/");
        sb.append(getResources().getString(R.string.PhotoToVideo));
        File file = new File(new StringBuilder(sb.toString()).toString());
        if (file.exists()) {
            String str = "tempmusic";
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File file2 : listFiles) {
                    if (file2.getName().startsWith(str)) {
                        file2.delete();
                    }
                }
            }
        }
    }


    public void m() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb.append("/");
        sb.append(getResources().getString(R.string.MainFolderName));
        sb.append("/");
        sb.append(getResources().getString(R.string.PhotoToVideo));
        StringBuilder sb2 = new StringBuilder(sb.toString());
        sb2.append("/temp");
        File file = new File(sb2.toString());
        if (file.exists()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File file2 : listFiles) {
                    file2.delete();
                    if (VERSION.SDK_INT >= 19) {
                        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                        intent.setData(Uri.fromFile(file2));
                        sendBroadcast(intent);
                    } else {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("file://");
                        sb3.append(file2.getAbsolutePath());
                        sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.parse(sb3.toString())));
                    }
                }
            }
            file.delete();
        }
    }

    public void hideMusicList(String str) {
        this.frameLayout1.setVisibility(8);
        this.aBoolean2 = BOOLEAN;
        try {
            if (this.bitmaps.size() > 0) {
                this.imageView.setImageBitmap(this.bitmaps.get(0));
            }
        } catch (Exception unused) {
        }
        this.aBoolean4 = false;
        this.imageView.setVisibility(0);
        this.textView1.setText(Utils.audioName);
        TextView textView = this.textView3;
        StringBuilder sb = new StringBuilder();
        sb.append("duration: ");
        sb.append(Utils.audioDuration);
        sb.append("s");
        textView.setText(sb.toString());
        this.relativeLayout.setVisibility(0);
        this.aBoolean1 = BOOLEAN;
        this.string = str;
        try {
            if (this.mediaPlayer == null) {
                this.mediaPlayer = new MediaPlayer();
                this.mediaPlayer.setDataSource(this.string);
                this.mediaPlayer.setAudioStreamType(3);
                this.mediaPlayer.prepare();
            } else {
                this.mediaPlayer.reset();
                this.mediaPlayer.setDataSource(this.string);
                this.mediaPlayer.setAudioStreamType(3);
                this.mediaPlayer.prepare();
            }
            this.mediaPlayer.start();
            this.seekBar2.setMax(this.mediaPlayer.getDuration());
            this.seekBar2.setProgress(0);
            this.anInt3 = 0;
            this.mediaPlayer.seekTo(0);
            this.mediaPlayer.pause();
            this.textView2.setText("00:00");
            try {
                this.textView.setText(formatTimeUnit(this.mediaPlayer.getDuration()));
            } catch (ParseException e2) {
                e2.printStackTrace();
            }
            this.horizontalScrollView.setVisibility(8);
            this.linearLayout1.setVisibility(8);
            this.relativeLayout.setVisibility(0);
        } catch (Exception unused2) {
        }
    }

    private void n() {
        try {
            this.ffmpeg.loadBinary(new LoadBinaryResponseHandler() {
                @Override
                public void onFailure() {
                    ActivityMovieMaker.this.o();
                    Log.d("ffmpeg loading failed! ", "");
                }

                @Override
                public void onFinish() {
                    Log.d("ffmpeg loading finish! ", "");
                }

                @Override
                public void onStart() {
                    Log.d("ffmpeg loading started!", "");
                }

                @Override
                public void onSuccess() {
                    Log.d("ffmpeg loading success!", "");
                }
            });
        } catch (FFmpegNotSupportedException unused) {
            o();
        }
    }


    public void o() {
        new AlertDialog.Builder(this).setIcon(17301543).setTitle("Device not supported").setMessage("FFmpeg is not supported on your device").setCancelable(false).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ActivityMovieMaker.this.finish();
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

    @Override
    public void onBackPressed() {
        if (this.frameLayout1.getVisibility() == 0) {
            this.frameLayout1.setVisibility(8);
            this.button.setBackgroundResource(R.drawable.music_normal);
            return;
        }
        super.onBackPressed();
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
            if (!this.aBoolean5) {
                onBackPressed();
            }
            return BOOLEAN;
        }
        if (menuItem.getItemId() == R.id.Done) {
            if (this.aBoolean4) {
                this.anInt1 = -1;
                a(this.imageView, this.anInt1, false);
                this.handler1.removeCallbacks(this.runnable);
                this.aBoolean4 = false;
            }
            try {
                this.aBoolean5 = BOOLEAN;
                if (this.mediaPlayer != null) {
                    this.mediaPlayer.pause();
                }
            } catch (Exception unused) {
            }
            if (this.bitmaps != null && this.bitmaps.size() > 0) {
                this.bitmaps.clear();
            }
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            this.anInt5 = displayMetrics.heightPixels;
            this.anInt6 = displayMetrics.widthPixels;
            new a().execute();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
