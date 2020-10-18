package com.video_editor.pro.ActivityVideoGIF;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore.Images.Media;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.video_editor.pro.R;
import com.video_editor.pro.UtilsAndAdapters.EditorHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;

import java.io.File;

@SuppressLint({"SetJavaScriptEnabled", "NewApi", "WrongConstant"})
public class ActivityGIFPreview extends AppCompatActivity {
    static final boolean BOOLEAN = true;
    File file;
    ImageView imageView;
    Handler handler = new Handler();
    boolean aBoolean = false;
    ProgressDialog progressDialog;
    TextView textView;
    Runnable runnable = new Runnable() {
        public void run() {
        }
    };

    public String string;
    private AdView adView;

    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView( R.layout.activity_gif_preview);
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView textView = toolbar.findViewById(R.id.toolbar_title);
        if (EditorHelper.ModuleId == 7) {
            textView.setText("Image Preview");
        } else if (EditorHelper.ModuleId == 12) {
            textView.setText("GIF Preview");
        }
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (BOOLEAN || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(BOOLEAN);
            supportActionBar.setDisplayShowTitleEnabled(false);
            this.imageView = findViewById(R.id.imgGif);
            this.textView = findViewById(R.id.Filename);
            Intent intent = getIntent();
            this.string = intent.getStringExtra("videourl");
            this.textView.setText(new File(this.string).getName());
            this.aBoolean = intent.getBooleanExtra("isfrommain", false);
            if (this.string == null) {
                Intent intent2 = new Intent(this, ActivityVideoList.class);
                intent2.setFlags(67108864);
                startActivity(intent2);
                finish();
            }
            new DisplayMetrics();
            getResources().getDisplayMetrics();
            File file = new File(this.string);
            if (file.exists()) {
                file.getName();
            }
            this.file = new File(this.string);
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


    @Override public void onBackPressed() {
        Intent intent = new Intent(this, ActivityVideoList.class);
        intent.setFlags(67108864);
        startActivity(intent);
        finish();
    }


    @Override public void onDestroy() {
        getWindow().clearFlags(128);
        super.onDestroy();
    }

    public static int pxToDp(int i2) {
        return (int) (((float) i2) / Resources.getSystem().getDisplayMetrics().density);
    }

    public static int dpToPx(int i2) {
        return (int) (((float) i2) * Resources.getSystem().getDisplayMetrics().density);
    }

    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        pxToDp(displayMetrics.widthPixels);
        int pxToDp = pxToDp(displayMetrics.heightPixels) / 2;
        RequestManager with = Glide.with(this);
        StringBuilder sb = new StringBuilder();
        sb.append("file://");
        sb.append(this.file.getAbsolutePath());
        with.load(sb.toString()).into(this.imageView);
    }


    public void onPause() {
        super.onPause();
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_deleteshare, menu);
        return BOOLEAN;
    }


   @Override public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            Intent intent = new Intent(this, ActivityVideoList.class);
            intent.setFlags(67108864);
            startActivity(intent);
            finish();
            return BOOLEAN;
        }
        if (menuItem.getItemId() == R.id.Delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure,You want to delete?");
            builder.setPositiveButton("Yes", new OnClickListener() {
                @Override public void onClick(DialogInterface dialogInterface, int i) {
                    File file = new File(ActivityGIFPreview.this.string);
                    if (file.exists()) {
                        file.delete();
                    }
                    ContentResolver contentResolver = ActivityGIFPreview.this.getContentResolver();
                    Uri uri = Media.EXTERNAL_CONTENT_URI;
                    StringBuilder sb = new StringBuilder();
                    sb.append("_data='");
                    sb.append(ActivityGIFPreview.this.string);
                    sb.append("'");
                    contentResolver.delete(uri, sb.toString(), null);
                    ActivityGIFPreview.this.progressDialog = new ProgressDialog(ActivityGIFPreview.this);
                    ActivityGIFPreview.this.progressDialog.setCancelable(false);
                    if (VERSION.SDK_INT <= 19) {
                        ActivityGIFPreview.this.progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                        ActivityGIFPreview.this.progressDialog.setMessage("Please wait...");
                    } else {
                        ActivityGIFPreview.this.progressDialog.setMessage(Html.fromHtml("<font color='#f45677'> Please wait... </font>"));
                    }
                    ActivityGIFPreview.this.progressDialog.show();
                    ActivityGIFPreview.this.handler.postDelayed(ActivityGIFPreview.this.runnable, 2000);
                }
            });
            builder.setNegativeButton("No", new OnClickListener() {
                @Override public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.create().show();
        } else if (menuItem.getItemId() == R.id.Share) {
            Intent intent2 = new Intent("android.intent.action.SEND");
            intent2.putExtra("android.intent.extra.SUBJECT", "Video Maker");
            intent2.setType("video/*");
            intent2.putExtra("android.intent.extra.STREAM", Uri.fromFile(new File(this.string)));
            intent2.putExtra("android.intent.extra.TEXT", "video");
            startActivity(Intent.createChooser(intent2, "Where to Share?"));
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
