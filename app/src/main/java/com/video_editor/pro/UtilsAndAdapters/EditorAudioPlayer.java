package com.video_editor.pro.UtilsAndAdapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore.Audio.Media;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.video_editor.pro.R;
import com.video_editor.pro.ActivityMusicList.ActivityMusicList;
import com.video_editor.pro.ActivityVideoMusicList.ActivityVideoMusicList;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;

import java.io.File;
import java.util.concurrent.TimeUnit;

@SuppressLint({"WrongConstant"})
public class EditorAudioPlayer extends AppCompatActivity implements OnSeekBarChangeListener {
    static final boolean BOOLEAN = true;
    Bundle bundle;
    ImageView imageView;
    ImageView imageView1;
    int anInt = 0;
    Handler handler = new Handler();
    boolean aBoolean = false;
    boolean aBoolean1 = false;
    SeekBar seekBar;
    Uri uri;
    TextView textView;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    String string = "";
    Runnable runnable = new Runnable() {
        public void run() {
            if (EditorAudioPlayer.this.mediaPlayer.isPlaying()) {
                int currentPosition = EditorAudioPlayer.this.mediaPlayer.getCurrentPosition();
                EditorAudioPlayer.this.seekBar.setProgress(currentPosition);
                EditorAudioPlayer.this.textView.setText(EditorAudioPlayer.formatTimeUnit(currentPosition));
                if (currentPosition == EditorAudioPlayer.this.anInt) {
                    EditorAudioPlayer.this.seekBar.setProgress(0);
                    EditorAudioPlayer.this.textView.setText(string1);
                    EditorAudioPlayer.this.handler.removeCallbacks(EditorAudioPlayer.this.runnable);
                    return;
                }
                EditorAudioPlayer.this.handler.postDelayed(EditorAudioPlayer.this.runnable, 200);
                return;
            }
            EditorAudioPlayer.this.seekBar.setProgress(EditorAudioPlayer.this.anInt);
            EditorAudioPlayer.this.textView.setText(EditorAudioPlayer.formatTimeUnit(EditorAudioPlayer.this.anInt));
            EditorAudioPlayer.this.handler.removeCallbacks(EditorAudioPlayer.this.runnable);
        }
    };
    OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            StringBuilder sb = new StringBuilder();
            sb.append("play status ");
            sb.append(EditorAudioPlayer.this.aBoolean);
            Log.e("", sb.toString());
            if (EditorAudioPlayer.this.aBoolean) {
                try {
                    EditorAudioPlayer.this.mediaPlayer.pause();
                    EditorAudioPlayer.this.handler.removeCallbacks(EditorAudioPlayer.this.runnable);
                    EditorAudioPlayer.this.imageView1.setBackgroundResource(R.drawable.play2);
                } catch (IllegalStateException e1) {
                    e1.printStackTrace();
                }
            } else {
                try {
                    EditorAudioPlayer.this.mediaPlayer.seekTo(EditorAudioPlayer.this.seekBar.getProgress());
                    EditorAudioPlayer.this.mediaPlayer.start();
                    EditorAudioPlayer.this.handler.postDelayed(EditorAudioPlayer.this.runnable, 200);
                    EditorAudioPlayer.this.imageView.setVisibility(0);
                    EditorAudioPlayer.this.imageView1.setBackgroundResource(R.drawable.pause2);
                } catch (IllegalStateException e2) {
                    e2.printStackTrace();
                }
            }
            EditorAudioPlayer.this.aBoolean ^= EditorAudioPlayer.BOOLEAN;
        }
    };

    MediaPlayer mediaPlayer;
    private String string1 = "00:00";

    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    public void onStopTrackingTouch(SeekBar seekBar) {

    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.audio_player);
        Toolbar toolbar = findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("My Audio");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (BOOLEAN || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(BOOLEAN);
            supportActionBar.setDisplayShowTitleEnabled(false);
            this.bundle = getIntent().getExtras();
            if (this.bundle != null) {
                this.string = this.bundle.getString("song");
                this.aBoolean1 = this.bundle.getBoolean("isfrom", false);
            }
            Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
            intent.setData(Uri.fromFile(new File(this.string)));
            sendBroadcast(intent);
            thumbAudio(this.string);
            this.textView3 = findViewById(R.id.dur);
            this.textView2 = findViewById(R.id.Filename);
            this.textView2.setText(new File(this.string).getName());
            this.imageView = findViewById(R.id.iv_Thumb);
            this.seekBar = findViewById(R.id.sbVideo);
            this.seekBar.setOnSeekBarChangeListener(this);
            this.mediaPlayer = MediaPlayer.create(this, Uri.parse(this.string));
            this.textView = findViewById(R.id.tvStartVideo);
            this.textView1 = findViewById(R.id.tvEndVideo);
            this.imageView1 = findViewById(R.id.btnPlayVideo);
            this.mediaPlayer.setOnErrorListener(new OnErrorListener() {
                public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                    Toast.makeText(EditorAudioPlayer.this.getApplicationContext(), "Audio Player Not Supproting", 0).show();
                    return EditorAudioPlayer.BOOLEAN;
                }
            });
            this.mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
                public void onPrepared(MediaPlayer mediaPlayer) {
                    EditorAudioPlayer.this.anInt = EditorAudioPlayer.this.mediaPlayer.getDuration();
                    EditorAudioPlayer.this.seekBar.setMax(EditorAudioPlayer.this.anInt);
                    EditorAudioPlayer.this.textView.setText(string1);
                    EditorAudioPlayer.this.textView1.setText(EditorAudioPlayer.formatTimeUnit(EditorAudioPlayer.this.anInt));
                    TextView textView = EditorAudioPlayer.this.textView3;
                    StringBuilder sb = new StringBuilder();
                    sb.append("duration : ");
                    sb.append(EditorVideoPlayer.formatTimeUnit(EditorAudioPlayer.this.anInt));
                    textView.setText(sb.toString());
                }
            });
            this.mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
                public void onCompletion(MediaPlayer mediaPlayer) {
                    EditorAudioPlayer.this.imageView1.setBackgroundResource(R.drawable.play2);
                    EditorAudioPlayer.this.mediaPlayer.seekTo(0);
                    EditorAudioPlayer.this.seekBar.setProgress(0);
                    EditorAudioPlayer.this.textView.setText(string1);
                    EditorAudioPlayer.this.handler.removeCallbacks(EditorAudioPlayer.this.runnable);
                    EditorAudioPlayer.this.aBoolean ^= EditorAudioPlayer.BOOLEAN;
                }
            });
            this.imageView1.setOnClickListener(this.onClickListener);
            AdView s = findViewById(R.id.banner_AdView);
            s.loadAd(new Builder().build());
            if (isOnline()) {
                s.setVisibility(0);
            } else {
                s.setVisibility(8);
            }
        } else {
            throw new AssertionError();
        }
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo() == null || !connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting()) {
            return false;
        }
        return BOOLEAN;
    }

    public void onProgressChanged(SeekBar seekBar, int i2, boolean z) {
        if (z) {
            this.mediaPlayer.seekTo(i2);
            this.textView.setText(formatTimeUnit(i2));
        }
    }

    @SuppressLint({"NewApi"})
    public static String formatTimeUnit(long j2) {
        return String.format("%02d:%02d", Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(j2)), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j2) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j2))));
    }

    public void thumbAudio(String str) {
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
            for (int i2 = 0; i2 < count; i2++) {
                Uri withAppendedPath = Uri.withAppendedPath(Media.EXTERNAL_CONTENT_URI, getLong(managedQuery));
                StringBuilder sb3 = new StringBuilder();
                sb3.append("===******* uri ===");
                sb3.append(withAppendedPath);
                Log.e("", sb3.toString());
                this.uri = withAppendedPath;
                managedQuery.moveToNext();
            }
        }
    }

    public String getLong(Cursor cursor) {
        return String.valueOf(cursor.getLong(cursor.getColumnIndexOrThrow("_id")));
    }

    public void delete() {
        new AlertDialog.Builder(this).setTitle("Are you sure?").setMessage("delete Ringtone").setPositiveButton("delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                File file = new File(EditorAudioPlayer.this.string);
                if (file.exists()) {
                    if (file.delete()) {

                    }
                    try {
                        Uri contentUriForPath = Media.getContentUriForPath(EditorAudioPlayer.this.string);
                        StringBuilder sb = new StringBuilder();
                        sb.append("=====Enter ====");
                        sb.append(contentUriForPath);
                        Log.e("", sb.toString());
                        ContentResolver contentResolver = EditorAudioPlayer.this.getContentResolver();
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("_data=\"");
                        sb2.append(EditorAudioPlayer.this.string);
                        sb2.append("\"");
                        contentResolver.delete(contentUriForPath, sb2.toString(), null);
                    } catch (Exception unused) {

                    }
                    EditorAudioPlayer.this.onBackPressed();
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setCancelable(BOOLEAN).show();
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onStop() {
        getWindow().clearFlags(128);
        super.onStop();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (EditorHelper.ModuleId == 3) {
            Intent intent = new Intent(this, ActivityVideoMusicList.class);
            intent.setFlags(67108864);
            startActivity(intent);
            finish();
        } else if (EditorHelper.ModuleId == 18) {
            Intent intent2 = new Intent(this, ActivityMusicList.class);
            intent2.setFlags(67108864);
            startActivity(intent2);
            finish();
        } else if (EditorHelper.ModuleId == 19) {
            Intent intent3 = new Intent(this, ActivityMusicList.class);
            intent3.setFlags(67108864);
            startActivity(intent3);
            finish();
        } else if (EditorHelper.ModuleId == 20) {
            Intent intent4 = new Intent(this, ActivityMusicList.class);
            intent4.setFlags(67108864);
            startActivity(intent4);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_deleteshare, menu);
        return BOOLEAN;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            if (this.mediaPlayer.isPlaying()) {
                this.mediaPlayer.stop();
            }
            super.onBackPressed();
            if (this.aBoolean1) {
                try {
                    onBackPressed();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            finish();
            return BOOLEAN;
        }
        if (menuItem.getItemId() == R.id.Delete) {
            delete();
        } else if (menuItem.getItemId() == R.id.Share) {
            try {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("audio/*");
                intent.putExtra("android.intent.extra.STREAM", this.uri);
                startActivity(Intent.createChooser(intent, "Share File"));
            } catch (Exception unused) {

            }
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
