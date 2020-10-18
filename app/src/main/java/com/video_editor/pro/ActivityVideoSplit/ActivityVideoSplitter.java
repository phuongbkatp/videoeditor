package com.video_editor.pro.ActivityVideoSplit;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.MediaStore.Images.Media;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationManagerCompat;

import com.video_editor.pro.ActivityVideoList.ActivityVideoList;
import com.video_editor.pro.R;
import com.video_editor.pro.UtilsAndAdapters.EditorRangePlaySeekBar;
import com.video_editor.pro.UtilsAndAdapters.EditorRangeSeekBar;
import com.video_editor.pro.UtilsAndAdapters.EditorRangeSeekBar.OnRangeSeekBarChangeListener;
import com.video_editor.pro.UtilsAndAdapters.EditorStaticMethods;
import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@SuppressLint({"WrongConstant", "ResourceType"})
public class ActivityVideoSplitter extends AppCompatActivity {
    public static ActivityVideoSplitter context = null;
    static final boolean BOOLEAN = true;
    public static String videoPath;
    public boolean CompleteNotificationCreated = false;
    public int LIST_COLUMN_SIZE = 4;
    public int MP_DURATION;
    boolean aBoolean = BOOLEAN;
    ViewGroup viewGroup;
    EditorRangeSeekBar<Integer> editorRangeSeekBar;
    EditorRangePlaySeekBar<Integer> editorRangePlaySeekBar;
    TextView textView;
    TextView textView1;
    public FFmpeg ffmpeg;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    public boolean isInFront = BOOLEAN;
    TextView textView5;
    TextView textView6;
    TextView textView7;
    boolean aBoolean1 = false;
    public int maxtime;
    public int mintime;
    WakeLock wakeLock;
    AdapterSplitBase adapterSplitBase;
    ArrayList<String> arrayList;
    public ProgressDialog progressDialog;
    Spinner spinner;
    private StateObserver stateObserver = new StateObserver();
    public int seekduration;
    public int seekend;
    public int seekstart;

    public VideoView videoView;
    public int totalVideoDuration = 0;
    public int type = 0;
    private String string;
    private String string1;
    public ImageView videoPlayBtn;

    @SuppressLint({"HandlerLeak"})
    public class StateObserver extends Handler {
        private boolean aBoolean2 = false;
        private Runnable runnable = new Runnable() {
            public void run() {
                StateObserver.this.a();
            }
        };

        public StateObserver() {
        }


        public void a() {
            if (!this.aBoolean2) {
                this.aBoolean2 = ActivityVideoSplitter.BOOLEAN;
                sendEmptyMessage(0);
            }
        }

        @Override public void handleMessage(Message message) {
            this.aBoolean2 = false;
            ActivityVideoSplitter.this.editorRangePlaySeekBar.setSelectedMaxValue(Integer.valueOf(ActivityVideoSplitter.this.videoView.getCurrentPosition()));
            if (!ActivityVideoSplitter.this.videoView.isPlaying() || ActivityVideoSplitter.this.videoView.getCurrentPosition() >= ActivityVideoSplitter.this.editorRangeSeekBar.getSelectedMaxValue().intValue()) {
                if (ActivityVideoSplitter.this.videoView.isPlaying()) {
                    ActivityVideoSplitter.this.videoView.pause();
                    ActivityVideoSplitter.this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
                    ActivityVideoSplitter.this.videoView.seekTo(ActivityVideoSplitter.this.editorRangeSeekBar.getSelectedMinValue().intValue());
                    ActivityVideoSplitter.this.editorRangePlaySeekBar.setSelectedMinValue(ActivityVideoSplitter.this.editorRangeSeekBar.getSelectedMinValue());
                    ActivityVideoSplitter.this.editorRangePlaySeekBar.setVisibility(4);
                }
                if (!ActivityVideoSplitter.this.videoView.isPlaying()) {
                    ActivityVideoSplitter.this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
                    ActivityVideoSplitter.this.editorRangePlaySeekBar.setVisibility(4);
                    return;
                }
                return;
            }
            ActivityVideoSplitter.this.editorRangePlaySeekBar.setVisibility(0);
            postDelayed(this.runnable, 50);
        }
    }

    public void intentToPreviewActivity() {
    }


    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView( R.layout.activity_video_splitter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Video Splitter");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (BOOLEAN || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(BOOLEAN);
            supportActionBar.setDisplayShowTitleEnabled(false);
            this.ffmpeg = FFmpeg.getInstance(this);
            c();
            this.aBoolean1 = false;
            this.aBoolean = BOOLEAN;
            copyCreate();
            context = this;
            return;
        }
        throw new AssertionError();
    }

    @SuppressLint("InvalidWakeLockTag")
    public void copyCreate() {
        this.arrayList = new ArrayList<>();
        this.arrayList.add("10 Sec");
        this.arrayList.add("20 Sec");
        this.arrayList.add("30 Sec");
        this.arrayList.add("40 Sec");
        this.arrayList.add("50 Sec");
        this.arrayList.add("60 Sec");
        this.isInFront = BOOLEAN;
        this.LIST_COLUMN_SIZE = b() / 100;
        this.totalVideoDuration = 0;
        this.wakeLock = ((PowerManager) getSystemService(Context.POWER_SERVICE)).newWakeLock(6, "VideoMerge");
        if (!this.wakeLock.isHeld()) {
            this.wakeLock.acquire();
        }
        this.string = getIntent().getStringExtra("videouri");
        videoPath = this.string;
        this.textView7 = findViewById(R.id.Filename);
        this.videoView = findViewById(R.id.addcutsvideoview);
        this.videoPlayBtn = findViewById(R.id.videoplaybtn);
        this.textView4 = findViewById(R.id.textformatValue);
        this.textView3 = findViewById(R.id.textsizeValue);
        this.textView6 = findViewById(R.id.textCompressPercentage);
        this.textView5 = findViewById(R.id.textcompressSize);
        this.textView = findViewById(R.id.left_pointer);
        this.textView1 = findViewById(R.id.mid_pointer);
        this.textView2 = findViewById(R.id.right_pointer);
        this.textView7.setText(new File(videoPath).getName());
        a(videoPath);
        a(2);
        runOnUiThread(new Runnable() {
            public void run() {
                ActivityVideoSplitter.this.progressDialog = ProgressDialog.show(ActivityVideoSplitter.this, "", "Loading...", ActivityVideoSplitter.BOOLEAN);
            }
        });
        VideoSeekBar();
        this.spinner = findViewById(R.id.sp_convert);
        this.adapterSplitBase = new AdapterSplitBase(this, this.arrayList, 0);
        this.spinner.setAdapter(this.adapterSplitBase);
        this.spinner.setSelection(0);
        this.spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                if (adapterView.getItemAtPosition(i).toString() == "10 Sec") {
                    ActivityVideoSplitter.this.a(1);
                } else if (adapterView.getItemAtPosition(i).toString() == "20 Sec") {
                    ActivityVideoSplitter.this.a(2);
                } else if (adapterView.getItemAtPosition(i).toString() == "30 Sec") {
                    ActivityVideoSplitter.this.a(3);
                } else if (adapterView.getItemAtPosition(i).toString() == "40 Sec") {
                    ActivityVideoSplitter.this.a(4);
                } else if (adapterView.getItemAtPosition(i).toString() == "50 Sec") {
                    ActivityVideoSplitter.this.a(5);
                } else if (adapterView.getItemAtPosition(i).toString() == "60 Sec") {
                    ActivityVideoSplitter.this.a(6);
                }
            }
        });
    }

    public void executeCompressCommand() {
        String[] strArr = new String[0];
        this.seekstart = this.editorRangeSeekBar.getSelectedMinValue().intValue() / 1000;
        this.seekend = this.editorRangeSeekBar.getSelectedMaxValue().intValue() / 1000;
        this.seekduration = this.seekend - this.seekstart;
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb.append("/");
        sb.append(getResources().getString(R.string.MainFolderName));
        sb.append("/");
        sb.append(getResources().getString(R.string.VideoSplitter));
        File file = new File(sb.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        String name = new File(videoPath).getName();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb2.append("/");
        sb2.append(getResources().getString(R.string.MainFolderName));
        sb2.append("/");
        sb2.append(getResources().getString(R.string.VideoSplitter));
        sb2.append("/");
        sb2.append(name.substring(0, name.lastIndexOf(".")));
        sb2.append("-part%2d");
        sb2.append(videoPath.substring(videoPath.lastIndexOf(".")));
        this.string1 = sb2.toString();
        if (this.arrayList.get(this.spinner.getSelectedItemPosition()) == "10 Sec") {
            StringBuilder sb3 = new StringBuilder();
            sb3.append("");
            sb3.append(this.seekstart);
            StringBuilder sb4 = new StringBuilder();
            sb4.append("");
            sb4.append(this.seekduration);
            strArr = new String[]{"-y", "-ss", sb3.toString(), "-t", sb4.toString(), "-i", videoPath, "-acodec", "copy", "-f", "segment", "-segment_time", "10", "-vcodec", "copy", "-reset_timestamps", "1", "-map", "0", "-preset", "ultrafast", this.string1};
        } else if (this.arrayList.get(this.spinner.getSelectedItemPosition()) == "20 Sec") {
            StringBuilder sb5 = new StringBuilder();
            sb5.append("");
            sb5.append(this.seekstart);
            StringBuilder sb6 = new StringBuilder();
            sb6.append("");
            sb6.append(this.seekduration);
            strArr = new String[]{"-y", "-ss", sb5.toString(), "-t", sb6.toString(), "-i", videoPath, "-acodec", "copy", "-f", "segment", "-segment_time", "20", "-vcodec", "copy", "-reset_timestamps", "1", "-map", "0", "-preset", "ultrafast", this.string1};
        } else if (this.arrayList.get(this.spinner.getSelectedItemPosition()) == "30 Sec") {
            StringBuilder sb7 = new StringBuilder();
            sb7.append("");
            sb7.append(this.seekstart);
            StringBuilder sb8 = new StringBuilder();
            sb8.append("");
            sb8.append(this.seekduration);
            strArr = new String[]{"-y", "-ss", sb7.toString(), "-t", sb8.toString(), "-i", videoPath, "-acodec", "copy", "-f", "segment", "-segment_time", "30", "-vcodec", "copy", "-reset_timestamps", "1", "-map", "0", "-preset", "ultrafast", this.string1};
        } else if (this.arrayList.get(this.spinner.getSelectedItemPosition()) == "40 Sec") {
            StringBuilder sb9 = new StringBuilder();
            sb9.append("");
            sb9.append(this.seekstart);
            StringBuilder sb10 = new StringBuilder();
            sb10.append("");
            sb10.append(this.seekduration);
            strArr = new String[]{"-y", "-ss", sb9.toString(), "-t", sb10.toString(), "-i", videoPath, "-acodec", "copy", "-f", "segment", "-segment_time", "40", "-vcodec", "copy", "-reset_timestamps", "1", "-map", "0", "-preset", "ultrafast", this.string1};
        } else if (this.arrayList.get(this.spinner.getSelectedItemPosition()) == "50 Sec") {
            StringBuilder sb11 = new StringBuilder();
            sb11.append("");
            sb11.append(this.seekstart);
            StringBuilder sb12 = new StringBuilder();
            sb12.append("");
            sb12.append(this.seekduration);
            strArr = new String[]{"-y", "-ss", sb11.toString(), "-t", sb12.toString(), "-i", videoPath, "-acodec", "copy", "-f", "segment", "-segment_time", "50", "-vcodec", "copy", "-reset_timestamps", "1", "-map", "0", "-preset", "ultrafast", this.string1};
        } else if (this.arrayList.get(this.spinner.getSelectedItemPosition()) == "60 Sec") {
            StringBuilder sb13 = new StringBuilder();
            sb13.append("");
            sb13.append(this.seekstart);
            StringBuilder sb14 = new StringBuilder();
            sb14.append("");
            sb14.append(this.seekduration);
            strArr = new String[]{"-y", "-ss", sb13.toString(), "-t", sb14.toString(), "-i", videoPath, "-acodec", "copy", "-f", "segment", "-segment_time", "60", "-vcodec", "copy", "-reset_timestamps", "1", "-map", "0", "-preset", "ultrafast", this.string1};
        }
        a(strArr, this.string1);
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
                        ActivityVideoSplitter.this.deleteFromGallery(str);
                        Toast.makeText(ActivityVideoSplitter.this, "Error Creating Video", 0).show();
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                }

                @Override public void onSuccess(String str) {
                    progressDialog.dismiss();
                    StringBuilder sb = new StringBuilder();
                    sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
                    sb.append("/");
                    sb.append(ActivityVideoSplitter.this.getResources().getString(R.string.MainFolderName));
                    Toast.makeText(ActivityVideoSplitter.this, "Video Saved In Gallery", Toast.LENGTH_SHORT).show();
                    sb.append("/");
                    sb.append(ActivityVideoSplitter.this.getResources().getString(R.string.VideoSplitter));
                    sb.append("/");
                    File[] listFiles = new File(sb.toString()).listFiles();
                    for (File file : listFiles) {
                        if (file.isFile()) {
                            Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                            intent.setData(Uri.fromFile(file));
                            ActivityVideoSplitter.this.getApplicationContext().sendBroadcast(intent);
                        }
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
                    ActivityVideoSplitter.this.refreshGallery(str);
                }
            });
            getWindow().clearFlags(16);
        } catch (FFmpegCommandAlreadyRunningException unused) {
        }
    }


    public void a(int i2) {
        this.type = i2;
        TextView textView = this.textView5;
        StringBuilder sb = new StringBuilder();
        sb.append(EditorStaticMethods.ExpectedOutputSize(videoPath, timeInSecond(this.mintime, this.maxtime), this.type));
        sb.append("");
        textView.setText(sb.toString());
        TextView textView2 = this.textView6;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("-");
        sb2.append(EditorStaticMethods.SelectedCompressPercentage(videoPath, timeInSecond(this.mintime, this.maxtime), this.type));
        sb2.append("%");
        textView2.setText(sb2.toString());
    }

    private void a(String str) {
        TextView textView = this.textView3;
        StringBuilder sb = new StringBuilder();
        sb.append("Size :- ");
        sb.append(EditorStaticMethods.sizeInMBbyFilepath(str));
        textView.setText(sb.toString());
        TextView textView2 = this.textView4;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Format :- ");
        sb2.append(EditorStaticMethods.FormatofVideo(str));
        textView2.setText(sb2.toString());
    }

    public int timeInSecond(int i2, int i3) {
        return (i3 - i2) / 1000;
    }

    public void VideoSeekBar() {
        this.videoView.setVideoURI(Uri.parse(videoPath));
        this.videoView.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                if (ActivityVideoSplitter.this.aBoolean) {
                    ActivityVideoSplitter.this.mintime = 0;
                    ActivityVideoSplitter.this.maxtime = mediaPlayer.getDuration();
                    ActivityVideoSplitter.this.MP_DURATION = mediaPlayer.getDuration();
                    ActivityVideoSplitter.this.seekLayout(0, ActivityVideoSplitter.this.MP_DURATION);
                    ActivityVideoSplitter.this.videoView.start();
                    ActivityVideoSplitter.this.videoView.pause();
                    ActivityVideoSplitter.this.videoView.seekTo(300);
                    return;
                }
                ActivityVideoSplitter.this.seekLayout(ActivityVideoSplitter.this.mintime, ActivityVideoSplitter.this.maxtime);
                ActivityVideoSplitter.this.videoView.start();
                ActivityVideoSplitter.this.videoView.pause();
                ActivityVideoSplitter.this.videoView.seekTo(ActivityVideoSplitter.this.mintime);
            }
        });
        this.videoView.setOnErrorListener(new OnErrorListener() {
            public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                ActivityVideoSplitter.this.progressDialog.dismiss();
                return false;
            }
        });
        this.videoPlayBtn.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                ActivityVideoSplitter.this.a();
            }
        });
    }


    public void a() {
        if (this.videoView.isPlaying()) {
            this.videoView.pause();
            this.videoView.seekTo(this.editorRangeSeekBar.getSelectedMinValue().intValue());
            this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
            this.editorRangePlaySeekBar.setVisibility(4);
            return;
        }
        this.videoView.seekTo(this.editorRangeSeekBar.getSelectedMinValue().intValue());
        this.videoView.start();
        this.editorRangePlaySeekBar.setSelectedMaxValue(Integer.valueOf(this.videoView.getCurrentPosition()));
        this.stateObserver.a();
        this.videoPlayBtn.setBackgroundResource(R.drawable.pause2);
        this.editorRangePlaySeekBar.setVisibility(0);
    }


    @Override public void onStop() {
        super.onStop();
    }


    @Override public void onDestroy() {
        this.totalVideoDuration = 0;
        if (this.wakeLock.isHeld()) {
            this.wakeLock.release();
        }
        super.onDestroy();
    }

    public void seekLayout(int i2, int i3) {
        TextView textView = this.textView;
        StringBuilder sb = new StringBuilder();
        sb.append(getTimeForTrackFormat(i2));
        sb.append("");
        textView.setText(sb.toString());
        TextView textView2 = this.textView2;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(getTimeForTrackFormat(i3));
        sb2.append("");
        textView2.setText(sb2.toString());
        TextView textView3 = this.textView1;
        StringBuilder sb3 = new StringBuilder();
        sb3.append(getTimeForTrackFormat(i3 - i2));
        sb3.append("");
        textView3.setText(sb3.toString());
        if (this.type != 9) {
            TextView textView4 = this.textView5;
            StringBuilder sb4 = new StringBuilder();
            sb4.append(EditorStaticMethods.ExpectedOutputSize(videoPath, timeInSecond(i2, i3), this.type));
            sb4.append("");
            textView4.setText(sb4.toString());
            TextView textView5 = this.textView6;
            StringBuilder sb5 = new StringBuilder();
            sb5.append("-");
            sb5.append(EditorStaticMethods.SelectedCompressPercentage(videoPath, timeInSecond(i2, i3), this.type));
            sb5.append("%");
            textView5.setText(sb5.toString());
        }
        if (this.viewGroup != null) {
            this.viewGroup.removeAllViews();
            this.viewGroup = null;
            this.editorRangeSeekBar = null;
            this.editorRangePlaySeekBar = null;
        }
        this.viewGroup = findViewById(R.id.seekLayout);
        this.editorRangeSeekBar = new EditorRangeSeekBar<>(Integer.valueOf(0), Integer.valueOf(this.MP_DURATION), this);
        this.editorRangePlaySeekBar = new EditorRangePlaySeekBar<>(Integer.valueOf(0), Integer.valueOf(this.MP_DURATION), this);
        this.editorRangeSeekBar.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Integer>() {

            public void onRangeSeekBarValuesChanged(EditorRangeSeekBar<?> editorRangeSeekBar, Integer num, Integer num2, boolean z) {
                if (ActivityVideoSplitter.this.videoView.isPlaying()) {
                    ActivityVideoSplitter.this.videoView.pause();
                    ActivityVideoSplitter.this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
                }
                if (ActivityVideoSplitter.this.maxtime == num2.intValue()) {
                    if (num2.intValue() - num.intValue() <= 1000) {
                        num = Integer.valueOf(num2.intValue() + NotificationManagerCompat.IMPORTANCE_UNSPECIFIED);
                    }
                    ActivityVideoSplitter.this.videoView.seekTo(num.intValue());
                } else if (ActivityVideoSplitter.this.mintime == num.intValue()) {
                    if (num2.intValue() - num.intValue() <= 1000) {
                        num2 = Integer.valueOf(num.intValue() + 1000);
                    }
                    ActivityVideoSplitter.this.videoView.seekTo(num.intValue());
                }
                ActivityVideoSplitter.this.editorRangeSeekBar.setSelectedMaxValue(num2);
                ActivityVideoSplitter.this.editorRangeSeekBar.setSelectedMinValue(num);
                ActivityVideoSplitter.this.textView.setText(ActivityVideoSplitter.getTimeForTrackFormat(num.intValue()));
                ActivityVideoSplitter.this.textView2.setText(ActivityVideoSplitter.getTimeForTrackFormat(num2.intValue()));
                ActivityVideoSplitter.this.textView1.setText(ActivityVideoSplitter.getTimeForTrackFormat(num2.intValue() - num.intValue()));
                if (ActivityVideoSplitter.this.type != 9) {
                    TextView textView = ActivityVideoSplitter.this.textView5;
                    StringBuilder sb = new StringBuilder();
                    sb.append(EditorStaticMethods.ExpectedOutputSize(ActivityVideoSplitter.videoPath, ActivityVideoSplitter.this.timeInSecond(num.intValue(), num2.intValue()), ActivityVideoSplitter.this.type));
                    sb.append("");
                    textView.setText(sb.toString());
                    TextView textView2 = ActivityVideoSplitter.this.textView6;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("-");
                    sb2.append(EditorStaticMethods.SelectedCompressPercentage(ActivityVideoSplitter.videoPath, ActivityVideoSplitter.this.timeInSecond(num.intValue(), ActivityVideoSplitter.this.maxtime), ActivityVideoSplitter.this.type));
                    sb2.append("%");
                    textView2.setText(sb2.toString());
                }
                ActivityVideoSplitter.this.editorRangePlaySeekBar.setSelectedMinValue(num);
                ActivityVideoSplitter.this.editorRangePlaySeekBar.setSelectedMaxValue(num2);
                ActivityVideoSplitter.this.mintime = num.intValue();
                ActivityVideoSplitter.this.maxtime = num2.intValue();
            }
        });
        this.viewGroup.addView(this.editorRangeSeekBar);
        this.viewGroup.addView(this.editorRangePlaySeekBar);
        this.editorRangeSeekBar.setSelectedMinValue(Integer.valueOf(i2));
        this.editorRangeSeekBar.setSelectedMaxValue(Integer.valueOf(i3));
        this.editorRangePlaySeekBar.setSelectedMinValue(Integer.valueOf(i2));
        this.editorRangePlaySeekBar.setSelectedMaxValue(Integer.valueOf(i3));
        this.editorRangePlaySeekBar.setEnabled(false);
        this.editorRangePlaySeekBar.setVisibility(4);
        this.progressDialog.dismiss();
    }

    private int b() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int i2 = displayMetrics.heightPixels;
        int i3 = displayMetrics.widthPixels;
        return i3 <= i2 ? i3 : i2;
    }

    public static String getTimeForTrackFormat(int i2) {
        long j2 = i2;
        return String.format("%02d:%02d:%02d", Long.valueOf(TimeUnit.MILLISECONDS.toHours(j2)), Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(j2) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(j2))), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j2) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j2))));
    }


    @Override public void onStart() {
        super.onStart();
        if (this.CompleteNotificationCreated) {
            intentToPreviewActivity();
            ((NotificationManager) context.getSystemService("notification")).cancelAll();
        }
        this.CompleteNotificationCreated = false;
    }

    public void onPause() {
        super.onPause();
        this.aBoolean = false;
        try {
            if (this.videoView.isPlaying()) {
                this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
                this.videoView.pause();
            }
        } catch (Exception unused) {
        }
        this.isInFront = false;
        if (this.editorRangePlaySeekBar != null && this.editorRangePlaySeekBar.getVisibility() == 0) {
            this.editorRangePlaySeekBar.setVisibility(4);
        }
    }


    public void onRestart() {
        super.onRestart();
    }

    public boolean comparesize(long j2, int i2) {
        if (j2 > (((long) i2) * (Long.parseLong(EditorStaticMethods.bitRate(videoPath)) / 1024)) / PlaybackStateCompat.ACTION_PLAY_FROM_URI) {
            return BOOLEAN;
        }
        return false;
    }

    private void c() {
        try {
            this.ffmpeg.loadBinary(new LoadBinaryResponseHandler() {
                @Override public void onFailure() {
                    ActivityVideoSplitter.this.d();
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
            d();
        }
    }


    public void d() {
        new Builder(this).setIcon(17301543).setTitle("Device not supported").setMessage("FFmpeg is not supported on your device").setCancelable(false).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialogInterface, int i) {
                ActivityVideoSplitter.this.finish();
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


    @Override public void onResume() {
        super.onResume();
        this.isInFront = BOOLEAN;
        this.aBoolean1 = false;
        this.string = getIntent().getStringExtra("videouri");
        videoPath = this.string;
    }

    @Override public void onBackPressed() {
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
            try {
                if (this.videoView.isPlaying()) {
                    this.videoPlayBtn.setBackgroundResource(R.drawable.play2);
                    this.videoView.pause();
                }
            } catch (Exception unused) {
            }
            executeCompressCommand();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
