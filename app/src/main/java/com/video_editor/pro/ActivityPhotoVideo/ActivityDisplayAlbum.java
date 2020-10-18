package com.video_editor.pro.ActivityPhotoVideo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import com.video_editor.pro.R;
import com.video_editor.pro.ActivityPhotoVideo.AdapterSlideshow.AdapterRecyclerAlbumGrid;
import com.video_editor.pro.ActivityPhotoVideo.HelperSlideshow.HelperAssetsDataBase;
import com.video_editor.pro.ActivityPhotoVideo.ModelSlideshow.Album;
import com.video_editor.pro.ActivityPhotoVideo.ModelSlideshow.SelectBucketImage;
import com.video_editor.pro.ActivityPhotoVideo.TablayoutSlideshow.HomeTab;
import com.video_editor.pro.ActivityPhotoVideo.UtilsSlideshow.Utils;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration.Builder;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

@SuppressLint({"WrongConstant"})
public class ActivityDisplayAlbum extends AppCompatActivity {
    static final boolean BOOLEAN = true;
    AdapterRecyclerAlbumGrid adapterRecyclerAlbumGrid;
    int anInt = 0;
    int anInt1;
    int anInt2 = 0;
    HelperAssetsDataBase helperAssetsDataBase = null;
    LayoutManager layoutManager;
    ImageLoader imageLoader = ImageLoader.getInstance();
    Context context;
    RecyclerView recyclerView;


    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView( R.layout.slideshow_grid_album);
        Toolbar toolbar = findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Select Photo");
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (BOOLEAN || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(BOOLEAN);
            supportActionBar.setDisplayShowTitleEnabled(false);
            this.context = this;
            this.recyclerView = findViewById(R.id.recycler_view);
            this.layoutManager = new GridLayoutManager(this.context, 3);
            this.recyclerView.setLayoutManager(this.layoutManager);
            this.anInt1 = getIntent().getIntExtra("bucketid", 0);
            b();
            a();
            return;
        }
        throw new AssertionError();
    }

    @Override public void onStop() {
        super.onStop();
        if (this.imageLoader != null) {
            this.imageLoader.clearDiskCache();
            this.imageLoader.clearMemoryCache();
        }
    }

    private void a() {
        int i2 = 0;
        while (true) {
            if (i2 >= Utils.imageUri.size()) {
                break;
            } else if (Utils.imageUri.get(i2).bucketid.equals(Integer.valueOf(this.anInt1))) {
                this.anInt = i2;
                break;
            } else {
                i2++;
            }
        }
        this.adapterRecyclerAlbumGrid = new AdapterRecyclerAlbumGrid(this.context, this.anInt1, this.imageLoader);
        this.recyclerView.setAdapter(this.adapterRecyclerAlbumGrid);
    }


    @Override public void onResume() {
        super.onResume();
        try {
            String str = Utils.imageUri.get(this.anInt).bucketid;
        } catch (Exception unused) {
            Intent intent = new Intent(this, HomeTab.class);
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
        if (this.imageLoader != null) {
            this.imageLoader.clearDiskCache();
            this.imageLoader.clearMemoryCache();
        }
    }

    private void b() {
        ImageLoaderConfiguration build = new Builder(getApplicationContext()).memoryCache(new WeakMemoryCache()).defaultDisplayImageOptions(new DisplayImageOptions.Builder().cacheInMemory(BOOLEAN).cacheOnDisk(BOOLEAN).bitmapConfig(Config.RGB_565).displayer(new FadeInBitmapDisplayer(400)).build()).build();
        this.imageLoader = ImageLoader.getInstance();
        this.imageLoader.init(build);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_picker, menu);
        return BOOLEAN;
    }

   @Override public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            int size = Utils.imageUri.get(this.anInt1).imgUri.size();
            int i2 = 0;
            for (int i3 = 0; i3 < size; i3++) {
                if (Utils.imageUri.get(this.anInt1).imgUri.get(i3).getImgPos() >= 0) {
                    i2++;
                }
            }
            Utils.imageUri.get(this.anInt1).count = i2;
            finish();
            return BOOLEAN;
        }
        if (menuItem.getItemId() == R.id.Done) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
