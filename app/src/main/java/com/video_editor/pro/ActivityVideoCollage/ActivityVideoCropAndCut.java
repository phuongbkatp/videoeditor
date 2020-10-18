package com.video_editor.pro.ActivityVideoCollage;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Video.Media;
import android.provider.MediaStore.Video.Thumbnails;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.video_editor.pro.R;
import com.video_editor.pro.UtilsAndAdapters.EditorVideoPlayerState;
import com.video_editor.pro.UtilsAndAdapters.EditorVideoSliceSeekBar;
import com.video_editor.pro.UtilsAndAdapters.EditorVideoSliceSeekBar.SeekBarChangeListener;
import com.video_editor.pro.ActivityVideoCollage.CollageModel.CollageData;
import com.video_editor.pro.ActivityVideoCollage.CollageUtils.CollageUtils;
import com.edmodo.cropper.CropImageView;

import java.io.File;

@SuppressLint({"WrongConstant"})
public class ActivityVideoCropAndCut extends AppCompatActivity {
    static final boolean BOOLEAN = true;
    ImageView imageView;
    CropImageView cropImageView;
    EditorVideoSliceSeekBar editorVideoSliceSeekBar;
    VideoView videoView;
    RelativeLayout relativeLayout;
    RelativeLayout relativeLayout1;
    RelativeLayout relativeLayout2;
    RelativeLayout relativeLayout3;

    public TextView textView;

    public TextView textView1;

    public EditorVideoPlayerState editorVideoPlayerState = new EditorVideoPlayerState();
    private b M = new b();
    int anInt;
    int anInt1;
    int anInt2;
    int anInt3;
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
    int anInt18;
    String string;
    String string1;
    ProgressDialog progressDialog;
    int anInt19 = 0;
    int anInt20 = 1;
    String string2 = "00";
    ImageView imageView1;

    @SuppressLint({"NewApi"})
    private class a extends AsyncTask<Void, Void, Void> {


        public void onProgressUpdate(Void... voidArr) {
        }

        public a() {
            ActivityVideoCropAndCut.this.progressDialog = new ProgressDialog(ActivityVideoCropAndCut.this);
            ActivityVideoCropAndCut.this.progressDialog.setMessage("Please Wait");
            ActivityVideoCropAndCut.this.progressDialog.setCancelable(false);
            ActivityVideoCropAndCut.this.progressDialog.show();
        }



        public Void doInBackground(Void... voidArr) {
            int i;
            int i2;
            int i3 = 0;
            if (ActivityVideoCropAndCut.this.anInt19 == 90) {
                try {
                    ActivityVideoCropAndCut.this.anInt14 = ActivityVideoCropAndCut.this.anInt;
                    int i4 = ActivityVideoCropAndCut.this.anInt2;
                    ActivityVideoCropAndCut.this.anInt6 = ActivityVideoCropAndCut.this.anInt;
                    ActivityVideoCropAndCut.this.anInt5 = ActivityVideoCropAndCut.this.anInt1;
                    ActivityVideoCropAndCut.this.anInt16 = ActivityVideoCropAndCut.this.anInt3;
                    ActivityVideoCropAndCut.this.anInt15 = ActivityVideoCropAndCut.this.anInt2;
                    ActivityVideoCropAndCut.this.anInt8 = ActivityVideoCropAndCut.this.anInt3;
                    ActivityVideoCropAndCut.this.anInt7 = ActivityVideoCropAndCut.this.anInt1;
                    i = ActivityVideoCropAndCut.this.anInt16 - ActivityVideoCropAndCut.this.anInt14;
                    try {
                        i3 = ActivityVideoCropAndCut.this.anInt5 - i4;
                        ActivityVideoCropAndCut.this.anInt13 = ActivityVideoCropAndCut.this.anInt12 - (i4 + i3);
                    } catch (Exception e) {
                        e = e;
                    }
                } catch (Exception e2) {
                    i = 0;
                    String valueOf = String.valueOf(ActivityVideoCropAndCut.this.editorVideoPlayerState.getStart() / 1000);
                    String.valueOf(ActivityVideoCropAndCut.this.editorVideoPlayerState.getDuration() / 1000);
                    CollageUtils.collageData.get(ActivityVideoCropAndCut.this.anInt17).setStartTime(valueOf);
                    CollageUtils.collageData.get(ActivityVideoCropAndCut.this.anInt17).setDurationTime(ActivityVideoCropAndCut.this.editorVideoPlayerState.getDuration() / 1000);
                    CollageUtils.collageData.get(ActivityVideoCropAndCut.this.anInt17).setCrop_X(ActivityVideoCropAndCut.this.anInt14);
                    CollageUtils.collageData.get(ActivityVideoCropAndCut.this.anInt17).setCrop_Y(ActivityVideoCropAndCut.this.anInt13);
                    CollageUtils.collageData.get(ActivityVideoCropAndCut.this.anInt17).setCrop_width(i);
                    CollageUtils.collageData.get(ActivityVideoCropAndCut.this.anInt17).setCrop_height(i3);
                    return null;
                }
            } else if (ActivityVideoCropAndCut.this.anInt19 == 270) {
                try {
                    int i5 = ActivityVideoCropAndCut.this.anInt;
                    int i6 = ActivityVideoCropAndCut.this.anInt2;
                    ActivityVideoCropAndCut.this.anInt6 = ActivityVideoCropAndCut.this.anInt;
                    ActivityVideoCropAndCut.this.anInt5 = ActivityVideoCropAndCut.this.anInt1;
                    ActivityVideoCropAndCut.this.anInt16 = ActivityVideoCropAndCut.this.anInt3;
                    ActivityVideoCropAndCut.this.anInt15 = ActivityVideoCropAndCut.this.anInt2;
                    ActivityVideoCropAndCut.this.anInt8 = ActivityVideoCropAndCut.this.anInt3;
                    ActivityVideoCropAndCut.this.anInt7 = ActivityVideoCropAndCut.this.anInt1;
                    int i7 = ActivityVideoCropAndCut.this.anInt16 - i5;
                    try {
                        i3 = ActivityVideoCropAndCut.this.anInt5 - i6;
                        ActivityVideoCropAndCut.this.anInt14 = ActivityVideoCropAndCut.this.anInt11 - (i5 + i7);
                        ActivityVideoCropAndCut.this.anInt13 = i6;
                        i = i7;
                    } catch (Exception e3) {
                        i = i7;
                        String valueOf2 = String.valueOf(ActivityVideoCropAndCut.this.editorVideoPlayerState.getStart() / 1000);
                        String.valueOf(ActivityVideoCropAndCut.this.editorVideoPlayerState.getDuration() / 1000);
                        CollageUtils.collageData.get(ActivityVideoCropAndCut.this.anInt17).setStartTime(valueOf2);
                        CollageUtils.collageData.get(ActivityVideoCropAndCut.this.anInt17).setDurationTime(ActivityVideoCropAndCut.this.editorVideoPlayerState.getDuration() / 1000);
                        CollageUtils.collageData.get(ActivityVideoCropAndCut.this.anInt17).setCrop_X(ActivityVideoCropAndCut.this.anInt14);
                        CollageUtils.collageData.get(ActivityVideoCropAndCut.this.anInt17).setCrop_Y(ActivityVideoCropAndCut.this.anInt13);
                        CollageUtils.collageData.get(ActivityVideoCropAndCut.this.anInt17).setCrop_width(i);
                        CollageUtils.collageData.get(ActivityVideoCropAndCut.this.anInt17).setCrop_height(i3);
                        return null;
                    }
                } catch (Exception e4) {
                    i = 0;
                    String valueOf22 = String.valueOf(ActivityVideoCropAndCut.this.editorVideoPlayerState.getStart() / 1000);
                    String.valueOf(ActivityVideoCropAndCut.this.editorVideoPlayerState.getDuration() / 1000);
                    CollageUtils.collageData.get(ActivityVideoCropAndCut.this.anInt17).setStartTime(valueOf22);
                    CollageUtils.collageData.get(ActivityVideoCropAndCut.this.anInt17).setDurationTime(ActivityVideoCropAndCut.this.editorVideoPlayerState.getDuration() / 1000);
                    CollageUtils.collageData.get(ActivityVideoCropAndCut.this.anInt17).setCrop_X(ActivityVideoCropAndCut.this.anInt14);
                    CollageUtils.collageData.get(ActivityVideoCropAndCut.this.anInt17).setCrop_Y(ActivityVideoCropAndCut.this.anInt13);
                    CollageUtils.collageData.get(ActivityVideoCropAndCut.this.anInt17).setCrop_width(i);
                    CollageUtils.collageData.get(ActivityVideoCropAndCut.this.anInt17).setCrop_height(i3);
                    return null;
                }
            } else {
                try {
                    ActivityVideoCropAndCut.this.anInt14 = ActivityVideoCropAndCut.this.anInt2;
                    ActivityVideoCropAndCut.this.anInt13 = ActivityVideoCropAndCut.this.anInt;
                    ActivityVideoCropAndCut.this.anInt6 = ActivityVideoCropAndCut.this.anInt1;
                    ActivityVideoCropAndCut.this.anInt5 = ActivityVideoCropAndCut.this.anInt;
                    ActivityVideoCropAndCut.this.anInt16 = ActivityVideoCropAndCut.this.anInt2;
                    ActivityVideoCropAndCut.this.anInt15 = ActivityVideoCropAndCut.this.anInt3;
                    ActivityVideoCropAndCut.this.anInt8 = ActivityVideoCropAndCut.this.anInt1;
                    ActivityVideoCropAndCut.this.anInt7 = ActivityVideoCropAndCut.this.anInt3;
                    i2 = ActivityVideoCropAndCut.this.anInt6 - ActivityVideoCropAndCut.this.anInt14;
                    try {
                        i3 = ActivityVideoCropAndCut.this.anInt15 - ActivityVideoCropAndCut.this.anInt13;
                    } catch (Exception e5) {
                        i = i2;
                        String valueOf222 = String.valueOf(ActivityVideoCropAndCut.this.editorVideoPlayerState.getStart() / 1000);
                        String.valueOf(ActivityVideoCropAndCut.this.editorVideoPlayerState.getDuration() / 1000);
                        CollageUtils.collageData.get(ActivityVideoCropAndCut.this.anInt17).setStartTime(valueOf222);
                        CollageUtils.collageData.get(ActivityVideoCropAndCut.this.anInt17).setDurationTime(ActivityVideoCropAndCut.this.editorVideoPlayerState.getDuration() / 1000);
                        CollageUtils.collageData.get(ActivityVideoCropAndCut.this.anInt17).setCrop_X(ActivityVideoCropAndCut.this.anInt14);
                        CollageUtils.collageData.get(ActivityVideoCropAndCut.this.anInt17).setCrop_Y(ActivityVideoCropAndCut.this.anInt13);
                        CollageUtils.collageData.get(ActivityVideoCropAndCut.this.anInt17).setCrop_width(i);
                        CollageUtils.collageData.get(ActivityVideoCropAndCut.this.anInt17).setCrop_height(i3);
                        return null;
                    }
                } catch (Exception e6) {
                    i2 = 0;
                    i = i2;
                    String valueOf2222 = String.valueOf(ActivityVideoCropAndCut.this.editorVideoPlayerState.getStart() / 1000);
                    String.valueOf(ActivityVideoCropAndCut.this.editorVideoPlayerState.getDuration() / 1000);
                    CollageUtils.collageData.get(ActivityVideoCropAndCut.this.anInt17).setStartTime(valueOf2222);
                    CollageUtils.collageData.get(ActivityVideoCropAndCut.this.anInt17).setDurationTime(ActivityVideoCropAndCut.this.editorVideoPlayerState.getDuration() / 1000);
                    CollageUtils.collageData.get(ActivityVideoCropAndCut.this.anInt17).setCrop_X(ActivityVideoCropAndCut.this.anInt14);
                    CollageUtils.collageData.get(ActivityVideoCropAndCut.this.anInt17).setCrop_Y(ActivityVideoCropAndCut.this.anInt13);
                    CollageUtils.collageData.get(ActivityVideoCropAndCut.this.anInt17).setCrop_width(i);
                    CollageUtils.collageData.get(ActivityVideoCropAndCut.this.anInt17).setCrop_height(i3);
                    return null;
                }
                i = i2;
            }
            String valueOf22222 = String.valueOf(ActivityVideoCropAndCut.this.editorVideoPlayerState.getStart() / 1000);
            String.valueOf(ActivityVideoCropAndCut.this.editorVideoPlayerState.getDuration() / 1000);
            CollageUtils.collageData.get(ActivityVideoCropAndCut.this.anInt17).setStartTime(valueOf22222);
            CollageUtils.collageData.get(ActivityVideoCropAndCut.this.anInt17).setDurationTime(ActivityVideoCropAndCut.this.editorVideoPlayerState.getDuration() / 1000);
            CollageUtils.collageData.get(ActivityVideoCropAndCut.this.anInt17).setCrop_X(ActivityVideoCropAndCut.this.anInt14);
            CollageUtils.collageData.get(ActivityVideoCropAndCut.this.anInt17).setCrop_Y(ActivityVideoCropAndCut.this.anInt13);
            CollageUtils.collageData.get(ActivityVideoCropAndCut.this.anInt17).setCrop_width(i);
            CollageUtils.collageData.get(ActivityVideoCropAndCut.this.anInt17).setCrop_height(i3);
            return null;
        }



        public void onPostExecute(Void voidR) {
            ActivityVideoCropAndCut.this.progressDialog.dismiss();
            Intent intent = new Intent();
            intent.putExtra("videopath", ActivityVideoCropAndCut.this.string1);
            intent.putExtra("frmpos", ActivityVideoCropAndCut.this.anInt17);
            ActivityVideoCropAndCut.this.setResult(-1, intent);
            ActivityVideoCropAndCut.this.finish();
        }
    }

    private class b extends Handler {
        private boolean b;
        private Runnable c;

        private b() {
            this.b = false;
            this.c = new Runnable() {
                public void run() {
                    b.this.a();
                }
            };
        }


        public void a() {
            if (!this.b) {
                try {
                    this.b = ActivityVideoCropAndCut.BOOLEAN;
                    sendEmptyMessage(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override public void handleMessage(Message message) {
            this.b = false;
            ActivityVideoCropAndCut.this.editorVideoSliceSeekBar.videoPlayingProgress(ActivityVideoCropAndCut.this.videoView.getCurrentPosition());
            if (!ActivityVideoCropAndCut.this.videoView.isPlaying() || ActivityVideoCropAndCut.this.videoView.getCurrentPosition() >= ActivityVideoCropAndCut.this.editorVideoSliceSeekBar.getRightProgress()) {
                try {
                    if (ActivityVideoCropAndCut.this.videoView.isPlaying()) {
                        try {
                            ActivityVideoCropAndCut.this.videoView.pause();
                            ActivityVideoCropAndCut.this.imageView1.setBackgroundResource(R.drawable.play2);
                            ActivityVideoCropAndCut.this.imageView.setVisibility(0);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    ActivityVideoCropAndCut.this.editorVideoSliceSeekBar.setSliceBlocked(false);
                    ActivityVideoCropAndCut.this.editorVideoSliceSeekBar.removeVideoStatusThumb();
                    return;
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            postDelayed(this.c, 50);
        }
    }


    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView( R.layout.video_collage_crop_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Video Cut Crop");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (BOOLEAN || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(BOOLEAN);
            supportActionBar.setDisplayShowTitleEnabled(false);
            RelativeLayout relativeLayout = findViewById(R.id.rl_container);
            LayoutParams layoutParams = (LayoutParams) relativeLayout.getLayoutParams();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            this.anInt4 = displayMetrics.widthPixels;
            this.anInt18 = displayMetrics.heightPixels;
            layoutParams.width = this.anInt4;
            layoutParams.height = this.anInt4;
            relativeLayout.setLayoutParams(layoutParams);
            this.string1 = getIntent().getStringExtra("videopath");
            this.cropImageView = findViewById(R.id.cropperView);
            a();
            ((TextView) findViewById(R.id.textfilename)).setText(new File(this.string1).getName());
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
                    this.string1 = extras.getString("videopath");
                    this.editorVideoPlayerState.setFilename(this.string1);
                    this.anInt17 = extras.getInt("frmpos");
                    this.anInt10 = extras.getInt("ratio_x");
                    this.anInt9 = extras.getInt("ratio_y");
                    this.string1.split("/");
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
            b();
            this.cropImageView.setFixedAspectRatio(BOOLEAN);
            this.cropImageView.setAspectRatio(this.anInt10, this.anInt9);
            return;
        }
        throw new AssertionError();
    }

    @Override public void onBackPressed() {
        if (this.videoView != null && this.videoView.isPlaying()) {
            this.videoView.pause();
        }
        getWindow().clearFlags(128);
        setResult(0);
        finish();
    }


    public void onPause() {
        super.onPause();
        this.editorVideoPlayerState.setCurrentTime(this.videoView.getCurrentPosition());
    }


    @Override public void onResume() {
        super.onResume();
        this.videoView.seekTo(this.editorVideoPlayerState.getCurrentTime());
    }

    private void a() {
        this.relativeLayout2 = findViewById(R.id.back_Original);
        this.relativeLayout = findViewById(R.id.imgbtn_Original);
        this.relativeLayout3 = findViewById(R.id.back_Fit);
        this.relativeLayout1 = findViewById(R.id.imgbtn_Fit);
        this.relativeLayout.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                ActivityVideoCropAndCut.this.relativeLayout2.setVisibility(0);
                ActivityVideoCropAndCut.this.relativeLayout3.setVisibility(8);
                ActivityVideoCropAndCut.this.cropImageView.setFixedAspectRatio(false);
            }
        });
        this.relativeLayout1.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                ActivityVideoCropAndCut.this.relativeLayout2.setVisibility(8);
                ActivityVideoCropAndCut.this.relativeLayout3.setVisibility(0);
                ActivityVideoCropAndCut.this.cropImageView.setFixedAspectRatio(ActivityVideoCropAndCut.BOOLEAN);
                ActivityVideoCropAndCut.this.cropImageView.setAspectRatio(ActivityVideoCropAndCut.this.anInt10, ActivityVideoCropAndCut.this.anInt9);
            }
        });
        this.textView1 = findViewById(R.id.left_pointer);
        this.textView = findViewById(R.id.right_pointer);
        this.editorVideoSliceSeekBar = findViewById(R.id.seek_bar);
        this.imageView = findViewById(R.id.ivScreen);
        this.imageView1 = findViewById(R.id.buttonply);
        this.imageView1.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                if (ActivityVideoCropAndCut.this.videoView == null || !ActivityVideoCropAndCut.this.videoView.isPlaying()) {
                    try {
                        ActivityVideoCropAndCut.this.imageView1.setBackgroundResource(R.drawable.pause2);
                        ActivityVideoCropAndCut.this.imageView.setVisibility(8);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        ActivityVideoCropAndCut.this.imageView.setVisibility(0);
                        ActivityVideoCropAndCut.this.imageView1.setBackgroundResource(R.drawable.play2);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                ActivityVideoCropAndCut.this.d();
            }
        });
        Object lastNonConfigurationInstance = getLastNonConfigurationInstance();
        if (lastNonConfigurationInstance != null) {
            try {
                this.editorVideoPlayerState = (EditorVideoPlayerState) lastNonConfigurationInstance;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else {
            try {
                this.editorVideoPlayerState.setFilename(this.string1);
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
    }

    private void b() {
        this.videoView = findViewById(R.id.videoview);
        this.videoView.setVideoPath(this.string1);
        this.string = getTimeForTrackFormat(this.videoView.getDuration(), BOOLEAN);
        this.videoView.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                ActivityVideoCropAndCut.this.editorVideoSliceSeekBar.setSeekBarChangeListener(new SeekBarChangeListener() {
                    public void SeekBarValueChanged(int i, int i2) {
                        if (ActivityVideoCropAndCut.this.editorVideoSliceSeekBar.getSelectedThumb() == 1) {
                            try {
                                ActivityVideoCropAndCut.this.videoView.seekTo(ActivityVideoCropAndCut.this.editorVideoSliceSeekBar.getLeftProgress());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        ActivityVideoCropAndCut.this.textView1.setText(ActivityVideoCropAndCut.getTimeForTrackFormat(i, ActivityVideoCropAndCut.BOOLEAN));
                        ActivityVideoCropAndCut.this.textView.setText(ActivityVideoCropAndCut.getTimeForTrackFormat(i2, ActivityVideoCropAndCut.BOOLEAN));
                        ActivityVideoCropAndCut.this.string2 = ActivityVideoCropAndCut.getTimeForTrackFormat(i, ActivityVideoCropAndCut.BOOLEAN);
                        ActivityVideoCropAndCut.this.editorVideoPlayerState.setStart(i);
                        ActivityVideoCropAndCut.this.string = ActivityVideoCropAndCut.getTimeForTrackFormat(i2, ActivityVideoCropAndCut.BOOLEAN);
                        ActivityVideoCropAndCut.this.editorVideoPlayerState.setStop(i2);
                    }
                });
                ActivityVideoCropAndCut.this.string = ActivityVideoCropAndCut.getTimeForTrackFormat(mediaPlayer.getDuration(), ActivityVideoCropAndCut.BOOLEAN);
                ActivityVideoCropAndCut.this.editorVideoSliceSeekBar.setMaxValue(mediaPlayer.getDuration());
                ActivityVideoCropAndCut.this.editorVideoSliceSeekBar.setLeftProgress(0);
                ActivityVideoCropAndCut.this.editorVideoSliceSeekBar.setRightProgress(mediaPlayer.getDuration());
                ActivityVideoCropAndCut.this.editorVideoSliceSeekBar.setProgressMinDiff(0);
            }
        });
        try {
            c();
        } catch (Exception unused) {
            Toast.makeText(getApplicationContext(), "Not supported video...", 0).show();
            finish();
        }
    }

    private void c() throws Exception {
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(this.string1);
        this.anInt11 = Integer.valueOf(mediaMetadataRetriever.extractMetadata(18)).intValue();
        this.anInt12 = Integer.valueOf(mediaMetadataRetriever.extractMetadata(19)).intValue();
        StringBuilder sb = new StringBuilder();
        sb.append("Width=");
        sb.append(this.anInt11);
        sb.append("  height=");
        sb.append(this.anInt12);
        Log.d("Width", sb.toString());
        if (VERSION.SDK_INT > 16) {
            try {
                this.anInt19 = Integer.valueOf(mediaMetadataRetriever.extractMetadata(24)).intValue();
            } catch (NumberFormatException e2) {
                e2.printStackTrace();
            }
        } else {
            try {
                this.anInt19 = 0;
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.cropImageView.getLayoutParams();
        if (this.anInt19 == 90 || this.anInt19 == 270) {
            try {
                if (this.anInt11 >= this.anInt12) {
                    try {
                        layoutParams.height = this.anInt4;
                        layoutParams.width = (int) (((float) this.anInt4) / (((float) this.anInt11) / ((float) this.anInt12)));
                    } catch (Exception e4) {
                        e4.printStackTrace();
                    }
                } else {
                    try {
                        layoutParams.width = this.anInt4;
                        layoutParams.height = (int) (((float) this.anInt4) / (((float) this.anInt12) / ((float) this.anInt11)));
                    } catch (Exception e5) {
                        e5.printStackTrace();
                    }
                }
            } catch (Exception e6) {
                e6.printStackTrace();
            }
        } else if (this.anInt11 >= this.anInt12) {
            try {
                layoutParams.width = this.anInt4;
                layoutParams.height = (int) (((float) this.anInt4) / (((float) this.anInt11) / ((float) this.anInt12)));
            } catch (Exception e7) {
                e7.printStackTrace();
            }
        } else {
            try {
                layoutParams.width = (int) (((float) this.anInt4) / (((float) this.anInt12) / ((float) this.anInt11)));
                layoutParams.height = this.anInt4;
            } catch (Exception e8) {
                e8.printStackTrace();
            }
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Width=");
        sb2.append(layoutParams.width);
        sb2.append("  height=");
        sb2.append(layoutParams.height);
        Log.d("===Width layout param", sb2.toString());
        this.cropImageView.setLayoutParams(layoutParams);
        this.cropImageView.setImageBitmap(Bitmap.createBitmap(layoutParams.width, layoutParams.height, Config.ARGB_8888));
        try {
            SearchVideo(getApplicationContext(), this.string1, layoutParams.width, layoutParams.height);
        } catch (Exception unused) {
        }
    }


    public void d() {
        if (this.videoView.isPlaying()) {
            try {
                this.videoView.pause();
                this.editorVideoSliceSeekBar.setSliceBlocked(false);
                this.editorVideoSliceSeekBar.removeVideoStatusThumb();
                return;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        this.videoView.seekTo(this.editorVideoSliceSeekBar.getLeftProgress());
        this.videoView.start();
        this.editorVideoSliceSeekBar.videoPlayingProgress(this.editorVideoSliceSeekBar.getLeftProgress());
        this.M.a();
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
            try {
                StringBuilder sb3 = new StringBuilder(sb2);
                sb3.append("0");
                sb3.append(i4);
                str = sb3.toString();
            } catch (Exception e2) {
                e2.printStackTrace();
                str = sb2;
                StringBuilder sb4 = new StringBuilder();
                sb4.append("Display Result");
                sb4.append(str);
                Log.e("", sb4.toString());
                return str;
            }
        } else {
            try {
                StringBuilder sb5 = new StringBuilder(sb2);
                sb5.append(i4);
                str = sb5.toString();
            } catch (Exception e3) {
                e3.printStackTrace();
                str = sb2;
                StringBuilder sb42 = new StringBuilder();
                sb42.append("Display Result");
                sb42.append(str);
                Log.e("", sb42.toString());
                return str;
            }
        }
        StringBuilder sb422 = new StringBuilder();
        sb422.append("Display Result");
        sb422.append(str);
        Log.e("", sb422.toString());
        return str;
    }

    private void e() {
        if (this.anInt19 == 90 || this.anInt19 == 270) {
            try {
                float f2 = (float) this.anInt11;
                float height = (float) this.cropImageView.getHeight();
                int width = (int) (((float) this.anInt12) / ((float) this.cropImageView.getWidth()));
                this.anInt2 = width;
                this.anInt1 = width;
                int i2 = (int) (f2 / height);
                this.anInt = i2;
                this.anInt3 = i2;
                return;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        float f3 = (float) this.anInt12;
        float height2 = (float) this.cropImageView.getHeight();
        int width2 = (int) (((float) this.anInt11) / ((float) this.cropImageView.getWidth()));
        this.anInt2 = width2;
        this.anInt1 = width2;
        int i3 = (int) (f3 / height2);
        this.anInt = i3;
        this.anInt3 = i3;
    }

    public OnClickListener setRatioOriginal() {
        return new OnClickListener() {
            @Override public void onClick(View view) {
            }
        };
    }

    public OnClickListener setRatioFixed() {
        return new OnClickListener() {
            @Override public void onClick(View view) {
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
            try {
                managedQuery.moveToFirst();
                Bitmap createScaledBitmap = Bitmap.createScaledBitmap(Thumbnails.getThumbnail(getContentResolver(), Long.valueOf(managedQuery.getLong(managedQuery.getColumnIndexOrThrow("_id"))).longValue(), 1, null), i2, i3, false);
                ViewGroup.LayoutParams layoutParams = this.imageView.getLayoutParams();
                layoutParams.width = i2;
                layoutParams.height = i3;
                this.imageView.setLayoutParams(layoutParams);
                this.imageView.setImageBitmap(createScaledBitmap);
                managedQuery.moveToNext();
            } catch (IllegalArgumentException e2) {
                e2.printStackTrace();
            }
        }
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picker, menu);
        return BOOLEAN;
    }

   @Override public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            finish();
            return BOOLEAN;
        }
        if (menuItem.getItemId() == R.id.Done) {
            if (this.videoView != null && this.videoView.isPlaying()) {
                try {
                    this.videoView.pause();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            e();
            new a().execute();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
