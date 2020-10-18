package com.video_editor.pro.ActivityVideoCollage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.video_editor.pro.R;
import com.video_editor.pro.UtilsAndAdapters.CollageStickers.Item;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ActivitySelectSticker extends AppCompatActivity implements OnClickListener, OnItemClickListener {
    public static Bitmap bitmap = null;
    static final boolean BOOLEAN = true;
    ImageAdapter imageAdapter;
    Button button;
    Button button1;
    Button button2;
    String string;
    String[] strings;
    GridView gridView;
    int anInt;
    int anInt1;
    ArrayList<Item> itemArrayList = new ArrayList<>();
    String[] strings1;
    DisplayMetrics displayMetrics;
    DisplayImageOptions displayImageOptions;
    DisplayImageOptions displayImageOptions1;
    int anInt2 = 0;

    public ImageLoader imageLoader;
    private AdView adView;

    private class ImageAdapter extends BaseAdapter {
        public Activity activity;
        int anInt3;
        private LayoutInflater inflater = null;

        public class ViewHolder {
            ImageView imageView;
            LinearLayout linearLayout;

            public ViewHolder() {
            }
        }

        public long getItemId(int i) {
            return i;
        }

        public ImageAdapter(Activity activity) {
            this.activity = activity;
            this.inflater = LayoutInflater.from(this.activity);
            this.inflater = this.activity.getLayoutInflater();
            this.anInt3 = this.activity.getResources().getDisplayMetrics().widthPixels / 4;
        }

        public int getCount() {
            return ActivitySelectSticker.this.itemArrayList.size();
        }

        public Object getItem(int i) {
            return ActivitySelectSticker.this.itemArrayList.get(i);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            Item item;
            View view2;
            Exception e;
            if (view == null) {
                try {
                    viewHolder = new ViewHolder();
                    try {
                        view2 = this.inflater.inflate(R.layout.sub_sticker_img_raw, null);
                        try {
                            view2.setLayoutParams(new LayoutParams(this.anInt3, this.anInt3));
                            viewHolder.linearLayout = view2.findViewById(R.id.ll_border);
                            viewHolder.imageView = view2.findViewById(R.id.ivpip_tiny);
                            viewHolder.imageView.setScaleType(ScaleType.CENTER_INSIDE);
                            view2.setTag(viewHolder);
                        } catch (Exception e2) {
                            e = e2;
                        }
                    } catch (Exception e3) {
                        Exception exc = e3;
                        view2 = view;
                        e = exc;
                        e.printStackTrace();
                        view = view2;
                        item = ActivitySelectSticker.this.itemArrayList.get(i);
                        if (item.isAvailable) {
                        }
                        return view;
                    }
                } catch (Exception e4) {
                    view2 = view;
                    e = e4;
                    viewHolder = null;
                    e.printStackTrace();
                    view = view2;
                    item = ActivitySelectSticker.this.itemArrayList.get(i);
                    if (item.isAvailable) {
                    }
                    return view;
                }
                view = view2;
            } else {
                try {
                    viewHolder = (ViewHolder) view.getTag();
                } catch (Exception e5) {
                    e5.printStackTrace();
                    viewHolder = null;
                }
            }
            item = ActivitySelectSticker.this.itemArrayList.get(i);
            if (item.isAvailable) {
                try {
                    ActivitySelectSticker.this.imageLoader.displayImage(item.path, viewHolder.imageView, ActivitySelectSticker.this.displayImageOptions1);
                } catch (Exception e6) {
                    e6.printStackTrace();
                }
            } else {
                try {
                    ActivitySelectSticker.this.imageLoader.displayImage(item.path, viewHolder.imageView, ActivitySelectSticker.this.displayImageOptions);
                } catch (Exception e7) {
                    e7.printStackTrace();
                }
            }
            return view;
        }
    }


    @Override public void onCreate(Bundle bundle) {
        String[] strArr;
        File[] listFiles;
        super.onCreate(bundle);
        setContentView( R.layout.collage_select_sticker);
        Toolbar toolbar = findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Select Sticker");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (BOOLEAN || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(BOOLEAN);
            supportActionBar.setDisplayShowTitleEnabled(false);
            this.displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(this.displayMetrics);
            this.anInt1 = this.displayMetrics.widthPixels;
            this.anInt = this.displayMetrics.heightPixels;
            Intent intent = getIntent();
            try {
                this.strings = getAssets().list("stickers");
            } catch (IOException unused) {
            }
            this.string = intent.getStringExtra("folderName");
            this.button2 = findViewById(R.id.btn_animal);
            this.button1 = findViewById(R.id.btn_baby);
            this.button = findViewById(R.id.btn_birth);
            this.button2.setOnClickListener(this);
            this.button1.setOnClickListener(this);
            this.button.setOnClickListener(this);
            this.itemArrayList.clear();
            StringBuilder sb = new StringBuilder();
            sb.append("stickers/");
            sb.append(this.strings[0]);
            this.strings1 = a(sb.toString());
            for (String str : this.strings1) {
                try {
                    ArrayList<Item> arrayList = this.itemArrayList;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("assets://");
                    sb2.append(str);
                    arrayList.add(new Item(sb2.toString(), BOOLEAN));
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            StringBuilder sb3 = new StringBuilder();
            sb3.append(getFilesDir());
            sb3.append("/Stickers/");
            sb3.append(this.string);
            File file = new File(sb3.toString());
            if (!file.exists()) {
                try {
                    file.mkdirs();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
            for (File file2 : file.listFiles()) {
                try {
                    ArrayList<Item> arrayList2 = this.itemArrayList;
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("file://");
                    sb4.append(file2.getAbsolutePath());
                    arrayList2.add(new Item(sb4.toString(), BOOLEAN));
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
            }
            a();
            this.imageLoader = ImageLoader.getInstance();
            this.gridView = findViewById(R.id.gridView1);
            this.imageAdapter = new ImageAdapter(this);
            this.gridView.setAdapter(this.imageAdapter);
            this.gridView.setOnItemClickListener(this);
            this.adView = findViewById(R.id.banner_AdView);
            this.adView.loadAd(new Builder().build());
            if (isOnline()) {
                this.adView.setVisibility(View.VISIBLE);
            } else {
                this.adView.setVisibility(View.GONE);
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

    @Override public void onStart() {
        super.onStart();
    }

    @Override public void onStop() {
        super.onStop();
    }


    public void onPause() {
        super.onPause();
    }

    @Override public void onBackPressed() {
        finish();
    }

    private String[] a(String str) {
        String[] strArr;
        try {
            strArr = getAssets().list(str);
        } catch (IOException e2) {
            e2.printStackTrace();
            strArr = null;
        }
        for (int i2 = 0; i2 < strArr.length; i2++) {
            try {
                StringBuilder sb = new StringBuilder(str);
                sb.append("/");
                sb.append(strArr[i2]);
                strArr[i2] = sb.toString();
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        return strArr;
    }

    private void a() {
        this.displayImageOptions1 = new DisplayImageOptions.Builder().cacheOnDisk(BOOLEAN).imageScaleType(ImageScaleType.NONE).showImageOnLoading(0).bitmapConfig(Config.ARGB_4444).build();
        this.displayImageOptions = new DisplayImageOptions.Builder().cacheOnDisk(BOOLEAN).imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Config.ARGB_4444).build();
        ImageLoader.getInstance().init(new ImageLoaderConfiguration.Builder(this).defaultDisplayImageOptions(this.displayImageOptions).memoryCache(new WeakMemoryCache()).build());
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j2) {
        Item item = this.itemArrayList.get(i2);
        new File(item.path).getName();
        this.anInt2 = i2;
        this.imageAdapter.notifyDataSetChanged();
        if (item.isAvailable) {
            try {
                bitmap = BitmapFactory.decodeStream(getAssets().open(this.strings1[i2]));
            } catch (Exception unused) {
                bitmap = BitmapFactory.decodeFile(item.path.replace("file://", ""));
            }
            setResult(-1);
            finish();
            overridePendingTransition(0, R.anim.dialog_close);
        }
    }

    @SuppressLint({"NewApi"})
    @Override public void onClick(View view) {
        String[] strArr;
        String[] strArr2;
        String[] strArr3;
        int i2 = 0;
        if (view == this.button2) {
            try {
                this.itemArrayList.clear();
                StringBuilder sb = new StringBuilder();
                sb.append("stickers/");
                sb.append(this.strings[0]);
                this.strings1 = a(sb.toString());
                for (String str : this.strings1) {
                    try {
                        ArrayList<Item> arrayList = this.itemArrayList;
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("assets://");
                        sb2.append(str);
                        arrayList.add(new Item(sb2.toString(), BOOLEAN));
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                StringBuilder sb3 = new StringBuilder();
                sb3.append(getFilesDir());
                sb3.append("/Stickers/");
                sb3.append(this.string);
                File file = new File(sb3.toString());
                if (!file.exists()) {
                    try {
                        file.mkdirs();
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }
                File[] listFiles = file.listFiles();
                int length = listFiles.length;
                while (i2 < length) {
                    try {
                        ArrayList<Item> arrayList2 = this.itemArrayList;
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append("file://");
                        sb4.append(listFiles[i2].getAbsolutePath());
                        arrayList2.add(new Item(sb4.toString(), BOOLEAN));
                        i2++;
                    } catch (Exception e4) {
                        e4.printStackTrace();
                    }
                }
                a();
                this.imageLoader = ImageLoader.getInstance();
                this.gridView = findViewById(R.id.gridView1);
                this.imageAdapter = new ImageAdapter(this);
                this.gridView.setAdapter(this.imageAdapter);
                this.imageAdapter.notifyDataSetChanged();
            } catch (Exception e5) {
                e5.printStackTrace();
            }
        } else if (view == this.button1) {
            try {
                this.itemArrayList.clear();
                StringBuilder sb5 = new StringBuilder();
                sb5.append("stickers/");
                sb5.append(this.strings[1]);
                this.strings1 = a(sb5.toString());
                for (String str2 : this.strings1) {
                    try {
                        ArrayList<Item> arrayList3 = this.itemArrayList;
                        StringBuilder sb6 = new StringBuilder();
                        sb6.append("assets://");
                        sb6.append(str2);
                        arrayList3.add(new Item(sb6.toString(), BOOLEAN));
                    } catch (Exception e6) {
                        e6.printStackTrace();
                    }
                }
                StringBuilder sb7 = new StringBuilder();
                sb7.append(getFilesDir());
                sb7.append("/Stickers/");
                sb7.append(this.string);
                File file2 = new File(sb7.toString());
                if (!file2.exists()) {
                    try {
                        file2.mkdirs();
                    } catch (Exception e7) {
                        e7.printStackTrace();
                    }
                }
                File[] listFiles2 = file2.listFiles();
                int length2 = listFiles2.length;
                while (i2 < length2) {
                    try {
                        ArrayList<Item> arrayList4 = this.itemArrayList;
                        StringBuilder sb8 = new StringBuilder();
                        sb8.append("file://");
                        sb8.append(listFiles2[i2].getAbsolutePath());
                        arrayList4.add(new Item(sb8.toString(), BOOLEAN));
                        i2++;
                    } catch (Exception e8) {
                        e8.printStackTrace();
                    }
                }
                a();
                this.imageLoader = ImageLoader.getInstance();
                this.gridView = findViewById(R.id.gridView1);
                this.imageAdapter = new ImageAdapter(this);
                this.gridView.setAdapter(this.imageAdapter);
                this.imageAdapter.notifyDataSetChanged();
            } catch (Exception e9) {
                e9.printStackTrace();
            }
        } else if (view == this.button) {
            try {
                this.itemArrayList.clear();
                StringBuilder sb9 = new StringBuilder();
                sb9.append("stickers/");
                sb9.append(this.strings[2]);
                this.strings1 = a(sb9.toString());
                for (String str3 : this.strings1) {
                    try {
                        ArrayList<Item> arrayList5 = this.itemArrayList;
                        StringBuilder sb10 = new StringBuilder();
                        sb10.append("assets://");
                        sb10.append(str3);
                        arrayList5.add(new Item(sb10.toString(), BOOLEAN));
                    } catch (Exception e10) {
                        e10.printStackTrace();
                    }
                }
                StringBuilder sb11 = new StringBuilder();
                sb11.append(getFilesDir());
                sb11.append("/Stickers/");
                sb11.append(this.strings[2]);
                File file3 = new File(sb11.toString());
                if (!file3.exists()) {
                    try {
                        file3.mkdirs();
                    } catch (Exception e11) {
                        e11.printStackTrace();
                    }
                }
                File[] listFiles3 = file3.listFiles();
                int length3 = listFiles3.length;
                while (i2 < length3) {
                    try {
                        ArrayList<Item> arrayList6 = this.itemArrayList;
                        StringBuilder sb12 = new StringBuilder();
                        sb12.append("file://");
                        sb12.append(listFiles3[i2].getAbsolutePath());
                        arrayList6.add(new Item(sb12.toString(), BOOLEAN));
                        i2++;
                    } catch (Exception e12) {
                        e12.printStackTrace();
                    }
                }
                a();
                this.imageLoader = ImageLoader.getInstance();
                this.gridView = findViewById(R.id.gridView1);
                this.imageAdapter = new ImageAdapter(this);
                this.gridView.setAdapter(this.imageAdapter);
                this.imageAdapter.notifyDataSetChanged();
            } catch (Exception e13) {
                e13.printStackTrace();
            }
        }
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
                setResult(-1);
                finish();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
