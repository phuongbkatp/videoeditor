package com.video_editor.pro.ActivityVideoJoiner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.video_editor.pro.ActivityMain.ActivityMain;
import com.video_editor.pro.R;
import com.video_editor.pro.ActivityVideoJoiner.JoinerAdapter.AdapterRecyclerAlbumGrid;
import com.video_editor.pro.ActivityVideoJoiner.JoinerModels.Album;
import com.video_editor.pro.ActivityVideoJoiner.JoinerModels.SelectBucketImage;
import com.video_editor.pro.ActivityVideoJoiner.util.FileUtils;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration.Builder;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;

@SuppressLint({"WrongConstant"})
public class ActivityGalleryPhotos extends AppCompatActivity {
    static final boolean BOOLEAN = true;
    GridView gridView;
    AdapterRecyclerAlbumGrid recyclerAlbumGrid;
    int anInt = 0;
    int anInt1;
    int anInt2 = 0;
    ArrayList<Album> arrayList = new ArrayList<>();
    ImageLoader instance = ImageLoader.getInstance();
    Context context;
    OnClickListener onClickListener = new OnClickListener() {
        @Override public void onClick(View view) {
            int size = FileUtils.imageUri.get(ActivityGalleryPhotos.this.anInt1).imgUri.size();
            int i = 0;
            for (int i2 = 0; i2 < size; i2++) {
                if (FileUtils.imageUri.get(ActivityGalleryPhotos.this.anInt1).imgUri.get(i2).getImgPos() >= 0) {
                    i++;
                }
            }
            FileUtils.imageUri.get(ActivityGalleryPhotos.this.anInt1).count = i;
            ActivityGalleryPhotos.this.finish();
        }
    };


    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView( R.layout.activity_gallery_photos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Select Video");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (BOOLEAN || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(BOOLEAN);
            supportActionBar.setDisplayShowTitleEnabled(false);
            this.context = this;
            this.gridView = findViewById(R.id.GridViewPhoto);
            this.anInt1 = getIntent().getIntExtra("bucketid", 0);
            b();
            a();
            return;
        }
        throw new AssertionError();
    }

    private void a() {
        int i2 = 0;
        while (true) {
            if (i2 >= FileUtils.imageUri.size()) {
                break;
            } else if (FileUtils.imageUri.get(i2).bucketid.equals(Integer.valueOf(this.anInt1))) {
                this.anInt = i2;
                break;
            } else {
                i2++;
            }
        }
        this.recyclerAlbumGrid = new AdapterRecyclerAlbumGrid(this.context, this.anInt1, this.instance);
        this.gridView.setAdapter(this.recyclerAlbumGrid);
    }


    @Override public void onResume() {
        super.onResume();
        try {
            String str = FileUtils.imageUri.get(this.anInt).bucketid;
        } catch (Exception unused) {
            Intent intent = new Intent(this, ActivityMain.class);
            intent.addFlags(335544320);
            startActivity(intent);
        }
    }

    @Override public void onBackPressed() {
        finish();
    }


    @Override public void onDestroy() {
        getWindow().clearFlags(128);
        super.onDestroy();
        if (this.instance != null) {
            this.instance.clearDiskCache();
            this.instance.clearMemoryCache();
        }
    }

    private void b() {
        ImageLoaderConfiguration build = new Builder(getApplicationContext()).memoryCache(new WeakMemoryCache()).defaultDisplayImageOptions(new DisplayImageOptions.Builder().cacheInMemory(BOOLEAN).cacheOnDisk(BOOLEAN).bitmapConfig(Config.RGB_565).displayer(new FadeInBitmapDisplayer(400)).build()).build();
        this.instance = ImageLoader.getInstance();
        this.instance.init(build);
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
            if (FileUtils.myUri.size() > 1) {
                Intent intent = new Intent(this, ActivityVideoJoiner.class);
                intent.addFlags(335544320);
                startActivity(intent);
            }
            Toast.makeText(getApplicationContext(), "Select Maximum two Videos", 0).show();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
