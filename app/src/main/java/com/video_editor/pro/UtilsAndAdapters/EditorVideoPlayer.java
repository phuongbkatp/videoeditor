package com.video_editor.pro.UtilsAndAdapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.ConnectivityManager;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore.Video.Media;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.video_editor.pro.ActivityVideoList.ActivityVideoList;
import com.video_editor.pro.R;
import com.video_editor.pro.ActivityPhotoVideo.ActivitySelectImageAndVideo;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;

import java.io.File;
import java.util.concurrent.TimeUnit;

@SuppressLint({"WrongConstant"})
public class EditorVideoPlayer extends AppCompatActivity implements OnSeekBarChangeListener {
    static final boolean BOOLEAN = true;
    Bundle bundle;
    ImageView imageView;
    int anInt = 0;
    Handler handler = new Handler();
    boolean aBoolean = false;
    int anInt1 = 0;
    SeekBar seekBar;
    Uri uri;
    TextView textView;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    String string = "";
    VideoView videoView;
    Uri uri1;
    Runnable runnable = new Runnable() {
        public void run() {
            if (EditorVideoPlayer.this.videoView.isPlaying()) {
                int currentPosition = EditorVideoPlayer.this.videoView.getCurrentPosition();
                EditorVideoPlayer.this.seekBar.setProgress(currentPosition);
                try {
                    EditorVideoPlayer.this.textView.setText(EditorVideoPlayer.formatTimeUnit(currentPosition));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (currentPosition == EditorVideoPlayer.this.anInt) {
                    EditorVideoPlayer.this.seekBar.setProgress(0);
                    EditorVideoPlayer.this.textView.setText("00:00");
                    EditorVideoPlayer.this.handler.removeCallbacks(EditorVideoPlayer.this.runnable);
                    return;
                }
                EditorVideoPlayer.this.handler.postDelayed(EditorVideoPlayer.this.runnable, 200);
                return;
            }
            EditorVideoPlayer.this.seekBar.setProgress(EditorVideoPlayer.this.anInt);
            try {
                EditorVideoPlayer.this.textView.setText(EditorVideoPlayer.formatTimeUnit(EditorVideoPlayer.this.anInt));
            } catch (ParseException e2) {
                e2.printStackTrace();
            }
            EditorVideoPlayer.this.handler.removeCallbacks(EditorVideoPlayer.this.runnable);
        }
    };
    OnClickListener onClickListener = new OnClickListener() {
        @Override public void onClick(View view) {
            if (EditorVideoPlayer.this.aBoolean) {
                try {
                    EditorVideoPlayer.this.videoView.pause();
                    EditorVideoPlayer.this.handler.removeCallbacks(EditorVideoPlayer.this.runnable);
                    EditorVideoPlayer.this.imageView.setBackgroundResource(R.drawable.play2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    EditorVideoPlayer.this.videoView.seekTo(EditorVideoPlayer.this.seekBar.getProgress());
                    EditorVideoPlayer.this.videoView.start();
                    EditorVideoPlayer.this.handler.postDelayed(EditorVideoPlayer.this.runnable, 200);
                    EditorVideoPlayer.this.videoView.setVisibility(0);
                    EditorVideoPlayer.this.imageView.setBackgroundResource(R.drawable.pause2);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            EditorVideoPlayer.this.aBoolean ^= EditorVideoPlayer.BOOLEAN;
        }
    };
    private AdView adView;

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
    }


    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView( R.layout.videoplayer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("My Video");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (BOOLEAN || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(BOOLEAN);
            supportActionBar.setDisplayShowTitleEnabled(false);
            this.bundle = getIntent().getExtras();
            if (this.bundle != null) {
                this.string = this.bundle.getString("song");
                this.anInt1 = this.bundle.getInt("position", 0);
            }
            try {
                GetVideo(getApplicationContext(), this.string);
            } catch (Exception unused) {
            }
            this.textView2 = findViewById(R.id.Filename);
            this.videoView = findViewById(R.id.videoView);
            this.seekBar = findViewById(R.id.sbVideo);
            this.seekBar.setOnSeekBarChangeListener(this);
            this.textView = findViewById(R.id.left_pointer);
            this.textView1 = findViewById(R.id.right_pointer);
            this.imageView = findViewById(R.id.btnPlayVideo);
            this.textView3 = findViewById(R.id.dur);
            this.textView2.setText(new File(this.string).getName());
            this.videoView.setVideoPath(this.string);
            this.videoView.seekTo(100);
            this.videoView.setOnErrorListener(new OnErrorListener() {
                public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                    Toast.makeText(EditorVideoPlayer.this.getApplicationContext(), "Video Player Not Supproting", 0).show();
                    return EditorVideoPlayer.BOOLEAN;
                }
            });
            this.videoView.setOnPreparedListener(new OnPreparedListener() {
                public void onPrepared(MediaPlayer mediaPlayer) {
                    EditorVideoPlayer.this.anInt = EditorVideoPlayer.this.videoView.getDuration();
                    EditorVideoPlayer.this.seekBar.setMax(EditorVideoPlayer.this.anInt);
                    EditorVideoPlayer.this.textView.setText("00:00");
                    try {
                        TextView textView = EditorVideoPlayer.this.textView3;
                        StringBuilder sb = new StringBuilder();
                        sb.append("duration : ");
                        sb.append(EditorVideoPlayer.formatTimeUnit(EditorVideoPlayer.this.anInt));
                        textView.setText(sb.toString());
                        EditorVideoPlayer.this.textView1.setText(EditorVideoPlayer.formatTimeUnit(EditorVideoPlayer.this.anInt));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
            this.videoView.setOnCompletionListener(new OnCompletionListener() {
                public void onCompletion(MediaPlayer mediaPlayer) {
                    EditorVideoPlayer.this.videoView.setVisibility(0);
                    EditorVideoPlayer.this.imageView.setBackgroundResource(R.drawable.play2);
                    EditorVideoPlayer.this.videoView.seekTo(0);
                    EditorVideoPlayer.this.seekBar.setProgress(0);
                    EditorVideoPlayer.this.textView.setText("00:00");
                    EditorVideoPlayer.this.handler.removeCallbacks(EditorVideoPlayer.this.runnable);
                    EditorVideoPlayer.this.aBoolean ^= EditorVideoPlayer.BOOLEAN;
                }
            });
            this.imageView.setOnClickListener(this.onClickListener);
            this.adView = findViewById(R.id.banner_AdView);
            this.adView.loadAd(new Builder().build());
            if (isOnline()) {
                this.adView.setVisibility(0);
            } else {
                this.adView.setVisibility(8);
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
            this.videoView.seekTo(i2);
            try {
                this.textView.setText(formatTimeUnit(i2));
            } catch (ParseException e2) {
                e2.printStackTrace();
            }
        }
    }

    @SuppressLint({"NewApi"})
    public static String formatTimeUnit(long j2) throws ParseException {
        return String.format("%02d:%02d", Long.valueOf(TimeUnit.MILLISECONDS.toMinutes(j2)), Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(j2) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(j2))));
    }

    public void GetVideo(Context context, String str) {
        Uri uri = Media.EXTERNAL_CONTENT_URI;
        String[] strArr = {"_id", "_data", "_display_name", "_size", "duration", "date_added", "album"};
        StringBuilder sb = new StringBuilder();
        sb.append("%");
        sb.append(str);
        sb.append("%");
        String[] strArr2 = {sb.toString()};
        Cursor managedQuery = managedQuery(uri, strArr, "_data  like ?", strArr2, " _id DESC");
        if (managedQuery.moveToFirst()) {
            try {
                Uri withAppendedPath = Uri.withAppendedPath(Media.EXTERNAL_CONTENT_URI, getLong(managedQuery));
                this.uri1 = withAppendedPath;
                this.uri = withAppendedPath;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static String getLong(Cursor cursor) {
        return String.valueOf(cursor.getLong(cursor.getColumnIndexOrThrow("_id")));
    }

    public void Delete() {
        new AlertDialog.Builder(this).setMessage("Are you sure you want to delete this file ?").setPositiveButton("delete", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialogInterface, int i) {
                File file = new File(EditorVideoPlayer.this.string);
                if (file.exists()) {
                    file.delete();
                    try {
                        ContentResolver contentResolver = EditorVideoPlayer.this.getContentResolver();
                        Uri uri = EditorVideoPlayer.this.uri1;
                        StringBuilder sb = new StringBuilder();
                        sb.append("_data=\"");
                        sb.append(EditorVideoPlayer.this.string);
                        sb.append("\"");
                        contentResolver.delete(uri, sb.toString(), null);
                    } catch (Exception unused) {
                    }
                }
                EditorVideoPlayer.this.onBackPressed();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).setCancelable(BOOLEAN).show();
    }

    @Override public void onBackPressed() {
        super.onBackPressed();
        if (EditorHelper.ModuleId == 1) {
            Intent intent = new Intent(this, ActivityVideoList.class);
            intent.setFlags(67108864);
            startActivity(intent);
            finish();
        } else if (EditorHelper.ModuleId == 2) {
            Intent intent2 = new Intent(this, ActivityVideoList.class);
            intent2.setFlags(67108864);
            startActivity(intent2);
            finish();
        } else if (EditorHelper.ModuleId == 4) {
            Intent intent3 = new Intent(this, ActivityVideoList.class);
            intent3.setFlags(67108864);
            startActivity(intent3);
            finish();
        } else if (EditorHelper.ModuleId == 5) {
            Intent intent4 = new Intent(this, ActivityVideoList.class);
            intent4.setFlags(67108864);
            startActivity(intent4);
            finish();
        } else if (EditorHelper.ModuleId == 6) {
            Intent intent5 = new Intent(this, com.video_editor.pro.ActivityVideoJoiner.ActivityVideoList.class);
            intent5.setFlags(67108864);
            startActivity(intent5);
            finish();
        } else if (EditorHelper.ModuleId == 8) {
            Intent intent6 = new Intent(this, ActivityVideoList.class);
            intent6.setFlags(67108864);
            startActivity(intent6);
            finish();
        } else if (EditorHelper.ModuleId == 9) {
            Intent intent7 = new Intent(this, ActivityVideoList.class);
            intent7.setFlags(67108864);
            startActivity(intent7);
            finish();
        } else if (EditorHelper.ModuleId == 10) {
            Intent intent8 = new Intent(this, ActivityVideoList.class);
            intent8.setFlags(67108864);
            startActivity(intent8);
            finish();
        } else if (EditorHelper.ModuleId == 11) {
            Intent intent9 = new Intent(this, ActivityVideoList.class);
            intent9.setFlags(67108864);
            startActivity(intent9);
            finish();
        } else if (EditorHelper.ModuleId == 13) {
            Intent intent10 = new Intent(this, ActivityVideoList.class);
            intent10.setFlags(67108864);
            startActivity(intent10);
            finish();
        } else if (EditorHelper.ModuleId == 14) {
            Intent intent11 = new Intent(this, ActivityVideoList.class);
            intent11.setFlags(67108864);
            startActivity(intent11);
            finish();
        } else if (EditorHelper.ModuleId == 15) {
            Intent intent12 = new Intent(this, ActivityVideoList.class);
            intent12.setFlags(67108864);
            startActivity(intent12);
            finish();
        } else if (EditorHelper.ModuleId == 16) {
            Intent intent13 = new Intent(this, ActivityVideoList.class);
            intent13.setFlags(67108864);
            startActivity(intent13);
            finish();
        } else if (EditorHelper.ModuleId == 22) {
            Intent intent14 = new Intent(this, ActivityVideoList.class);
            intent14.setFlags(67108864);
            startActivity(intent14);
            finish();
        } else if (EditorHelper.ModuleId == 21) {
            Intent intent15 = new Intent(this, ActivitySelectImageAndVideo.class);
            intent15.setFlags(67108864);
            startActivity(intent15);
            finish();
        }
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_deleteshare, menu);
        return BOOLEAN;
    }

   @Override public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
            return BOOLEAN;
        }
        if (menuItem.getItemId() == R.id.Delete) {
            if (this.videoView.isPlaying()) {
                try {
                    this.videoView.pause();
                    this.handler.removeCallbacks(this.runnable);
                    this.imageView.setBackgroundResource(R.drawable.play2);
                    this.aBoolean = false;
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            Delete();
        } else if (menuItem.getItemId() == R.id.Share) {
            if (this.videoView.isPlaying()) {
                this.videoView.pause();
                this.handler.removeCallbacks(this.runnable);
                this.imageView.setBackgroundResource(R.drawable.play2);
                this.aBoolean = false;
            }
            try {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("video/*");
                intent.putExtra("android.intent.extra.STREAM", this.uri);
                startActivity(Intent.createChooser(intent, "Share File"));
            } catch (Exception unused) {
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
