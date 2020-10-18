package com.video_editor.pro.ActivityPhotoVideo.TablayoutSlideshow;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore.Images.Media;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.view.InputDeviceCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.video_editor.pro.R;
import com.video_editor.pro.ActivityPhotoVideo.ActivitySelectImageAndVideo;
import com.video_editor.pro.ActivityPhotoVideo.ActivitySelectionList;
import com.video_editor.pro.ActivityPhotoVideo.HelperSlideshow.HelperAssetsDataBase;
import com.video_editor.pro.ActivityPhotoVideo.ModelSlideshow.Album;
import com.video_editor.pro.ActivityPhotoVideo.ModelSlideshow.ImageSelection;
import com.video_editor.pro.ActivityPhotoVideo.ModelSlideshow.SelectBucketImage;
import com.video_editor.pro.ActivityPhotoVideo.TablayoutSlideshow.SlidingTabLayout.tabColorizer;
import com.video_editor.pro.ActivityPhotoVideo.UtilsSlideshow.BitmapCompression;
import com.video_editor.pro.ActivityPhotoVideo.UtilsSlideshow.PreferenceManager;
import com.video_editor.pro.ActivityPhotoVideo.UtilsSlideshow.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Locale;

@SuppressLint({"WrongConstant"})
public class HomeTab extends FragmentActivity {
    CharSequence[] charSequences = {"ALBUM", "PHOTO"};
    ImageView imageView;
    ImageView imageView1;
    int anInt = 0;
    HelperAssetsDataBase helperAssetsDataBase = null;
    ImageLoader imageLoader;
    boolean aBoolean;
    int anInt1;
    ProgressDialog progressDialog;
    int anInt2;
    int anInt3;
    SlidingTabLayout slidingTabLayout;
    ViewPager viewPager;

    public class TabPagerAdapter extends FragmentPagerAdapter {
        final int a = 2;
        Context b;

        public int getCount() {
            return 2;
        }

        public int getItemPosition(Object obj) {
            return -2;
        }

        public TabPagerAdapter(FragmentManager fragmentManager, Context context) {
            super(fragmentManager);
            this.b = context;
        }

        public CharSequence getPageTitle(int i) {
            return HomeTab.this.charSequences[i];
        }

        public void startUpdate(ViewGroup viewGroup) {
            super.startUpdate(viewGroup);
        }

        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return FragmentAlbum.newInstance(i, HomeTab.this);
                case 1:
                    return FragmentPhoto.newInstance(i, HomeTab.this);
                default:
                    return null;
            }
        }
    }

    class a extends AsyncTask<Void, Void, Boolean> {
        a() {
        }



        public Boolean doInBackground(Void... voidArr) {
            try {
                Utils.createImageList.add(HomeTab.this.a());
                HomeTab.this.helperAssetsDataBase.addImageDetails(Utils.createImageList.get(HomeTab.this.anInt));
                return Boolean.valueOf(true);
            } catch (Exception unused) {
                return Boolean.valueOf(false);
            }
        }



        public void onPostExecute(Boolean bool) {
            super.onPostExecute(bool);
            if (HomeTab.this.anInt < HomeTab.this.anInt1 - 1) {
                HomeTab.this.progressDialog.setProgress((HomeTab.this.anInt * 100) / HomeTab.this.anInt1);
                HomeTab.this.anInt++;
                new c().execute();
                return;
            }
            HomeTab.this.anInt = 0;
            if (HomeTab.this.progressDialog != null && HomeTab.this.progressDialog.isShowing()) {
                HomeTab.this.progressDialog.dismiss();
            }
            if (Utils.bitmap != null) {
                Utils.bitmap.recycle();
            }
            if (Utils.selectImageList.size() > 0) {
                Utils.selectImageList.clear();
            }
            HomeTab.this.startActivityForResult(new Intent(HomeTab.this, ActivitySelectionList.class), 69);
        }
    }

    private class b extends AsyncTask<Void, Void, Boolean> {
        int a;

        private b() {
        }


        public void onPreExecute() {
            try {
                HomeTab.this.helperAssetsDataBase = new HelperAssetsDataBase(HomeTab.this.getApplicationContext());
            } catch (Exception e) {
                e.printStackTrace();
            }
            HomeTab.this.progressDialog = new ProgressDialog(HomeTab.this);
            HomeTab.this.progressDialog.setCancelable(false);
            HomeTab.this.progressDialog.setProgressStyle(1);
            HomeTab.this.progressDialog.show();
            this.a = Utils.imageUri.size();
        }



        public Boolean doInBackground(Void... voidArr) {
            for (int i = 0; i < this.a; i++) {
                int size = Utils.imageUri.get(i).imgUri.size();
                for (int i2 = 0; i2 < size; i2++) {
                    int i3 = Utils.imageUri.get(i).imgUri.get(i2).getImgPos();
                    if (i3 >= 0) {
                        ImageSelection imageSelection = new ImageSelection();
                        int indexId = PreferenceManager.getIndexId();
                        imageSelection.indexId = indexId;
                        PreferenceManager.setIndexId(indexId + 1);
                        imageSelection.imgId = Utils.imageUri.get(i).imgUri.get(i2).getImgId().intValue();
                        imageSelection.imgUri = Utils.imageUri.get(i).imgUri.get(i2).getImgUri().toString();
                        imageSelection.cropIndex = -1;
                        imageSelection.imgPos = i3;
                        Utils.selectImageList.add(imageSelection);
                    }
                }
            }
            HomeTab.this.anInt1 = Utils.myUri.size();
            if (HomeTab.this.anInt1 <= 0) {
                return Boolean.valueOf(false);
            }
            return Boolean.valueOf(true);
        }



        public void onPostExecute(Boolean bool) {
            if (bool.booleanValue()) {
                Utils.imgCount = Utils.createImageList.size() + 1;
                if (Utils.imgCount == 0) {
                    Utils.imgCount = 1;
                }
                HomeTab.this.anInt = 0;
                DisplayMetrics displayMetrics = new DisplayMetrics();
                HomeTab.this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                HomeTab.this.anInt3 = displayMetrics.widthPixels;
                HomeTab.this.anInt2 = displayMetrics.heightPixels;
                new c().execute();
                return;
            }
            if (!(HomeTab.this.progressDialog == null || HomeTab.this.progressDialog == null)) {
                HomeTab.this.progressDialog.dismiss();
            }
            Toast.makeText(HomeTab.this.getApplicationContext(), "Select At Least One Image", 0).show();
        }
    }

    class c extends AsyncTask<Boolean, Void, Boolean> {
        boolean aBoolean1;
        String string = "";
        int anInt4;
        int anInt5;
        String string1;
        int anInt6;
        int anInt7;

        c() {
        }


        public void onPreExecute() {
            this.string1 = Utils.myUri.get(HomeTab.this.anInt);
            this.aBoolean1 = false;
        }



        public Boolean doInBackground(Boolean... boolArr) {
            if (this.aBoolean1) {
                Log.i("isDuplicate", "TRUE");
                return Boolean.valueOf(false);
            }
            File file = new File(this.string1);
            try {
                Options options = new Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(new FileInputStream(file), null, options);
                options.inSampleSize = BitmapCompression.calculateInSampleSize(options, HomeTab.this.anInt3, HomeTab.this.anInt3);
                options.inJustDecodeBounds = false;
                if (Utils.bitmap != null) {
                    Utils.bitmap.recycle();
                    Utils.bitmap = null;
                }
                Utils.bitmap = BitmapFactory.decodeStream(new FileInputStream(file), null, options);
                BitmapCompression.adjustImageOrientationUri(HomeTab.this.getApplicationContext(), HomeTab.getImageContentUri(HomeTab.this.getApplicationContext(), new File(Utils.myUri.get(HomeTab.this.anInt))));
                if (Utils.bitmap == null) {
                    Utils.bitmap = BitmapFactory.decodeStream(new FileInputStream(file), null, options);
                }
                int width = Utils.bitmap.getWidth();
                int height = Utils.bitmap.getHeight();
                if (width > height) {
                    this.anInt5 = HomeTab.this.anInt3;
                    this.anInt4 = (HomeTab.this.anInt3 * height) / width;
                    this.anInt6 = 0;
                    this.anInt7 = (HomeTab.this.anInt3 - this.anInt4) / 2;
                } else {
                    this.anInt4 = HomeTab.this.anInt3;
                    this.anInt5 = (HomeTab.this.anInt3 * width) / height;
                    this.anInt7 = 0;
                    this.anInt6 = (HomeTab.this.anInt3 - this.anInt5) / 2;
                }
                return Boolean.valueOf(true);
            } catch (Exception e2) {
                Log.i("Background", "TRUE");
                e2.printStackTrace();
                e2.getMessage();
                return Boolean.valueOf(false);
            }
        }



        public void onPostExecute(Boolean bool) {
            if (bool.booleanValue()) {
                new a().execute();
                return;
            }
            if (!(HomeTab.this.progressDialog == null || HomeTab.this.progressDialog == null)) {
                HomeTab.this.progressDialog.dismiss();
            }
            Toast.makeText(HomeTab.this.getApplicationContext(), "Error in Creating Image ", 0).show();
            HomeTab.this.finish();
        }
    }


    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.slideshow_activity_home_tab);
        this.imageLoader = ImageLoader.getInstance();
        this.slidingTabLayout = findViewById(R.id.sliding_tabs);

        this.slidingTabLayout.setCustomTabColorizer(new tabColorizer() {
            public int getDividerColor(int i) {
                return InputDeviceCompat.SOURCE_ANY;
            }

            public int getIndicatorColor(int i) {
                return InputDeviceCompat.SOURCE_ANY;
            }
        });
        this.viewPager = findViewById(R.id.pager);
        Universal.fadt = new TabPagerAdapter(getSupportFragmentManager(), this);
        this.viewPager.setAdapter(Universal.fadt);
        Universal.vobj = this.viewPager;
        this.slidingTabLayout.setViewPager(this.viewPager);
        this.imageView1 = findViewById(R.id.btnNext);
        this.imageView = findViewById(R.id.btn_back);
        this.imageView.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                Utils.myUri.clear();
                Utils.selectedImagesUri.clear();
                Utils.createImageList.clear();
                Utils.imageUri.clear();
                Intent intent = new Intent(HomeTab.this, ActivitySelectImageAndVideo.class);
                intent.setFlags(67108864);
                HomeTab.this.startActivity(intent);
                HomeTab.this.finish();
            }
        });
        this.imageView1.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                HomeTab.this.nextClick();
            }
        });
    }


    @Override public void onResume() {
        super.onResume();
    }

    public void nextClick() {
        if (!this.aBoolean) {
            if (Utils.createImageList != null && Utils.createImageList.size() > 0) {
                Utils.createImageList.clear();
            }
            if (Utils.selectedImagesUri != null && Utils.selectedImagesUri.size() > 0) {
                Utils.selectedImagesUri.clear();
            }
            if (Utils.selectImageList != null && Utils.selectImageList.size() > 0) {
                Utils.selectImageList.clear();
            }
            new b().execute();
            this.imageLoader.clearDiskCache();
            this.imageLoader.clearMemoryCache();
            this.aBoolean = true;
        }
        new Handler().postDelayed(new Runnable() {
            public void run() {
                HomeTab.this.aBoolean = false;
            }
        }, 2000);
    }

    public static Uri getImageContentUri(Context context, File file) {
        String absolutePath = file.getAbsolutePath();
        Cursor query = context.getContentResolver().query(Media.EXTERNAL_CONTENT_URI, new String[]{"_id"}, "_data=? ", new String[]{absolutePath}, null);
        if (query != null && query.moveToFirst()) {
            int i2 = query.getInt(query.getColumnIndex("_id"));
            query.close();
            return Uri.withAppendedPath(Media.EXTERNAL_CONTENT_URI, String.valueOf(i2));
        } else if (!file.exists()) {
            return null;
        } else {
            ContentValues contentValues = new ContentValues();
            contentValues.put("_data", absolutePath);
            return context.getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, contentValues);
        }
    }


    public String a() {
        StringBuilder sb = new StringBuilder(Utils.getPath(getApplicationContext()));
        sb.append("/temp");
        File file = new File(sb.toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append("slide_");
        sb2.append(String.format(Locale.US, "%05d", Integer.valueOf(Utils.imgCount)));
        sb2.append(".jpg");
        File file2 = new File(file, sb2.toString());
        Utils.imgCount++;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            try {
                Utils.bitmap.compress(CompressFormat.JPEG, 70, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                return file2.getAbsolutePath();
            } catch (Exception e2) {
                e2.printStackTrace();
                return file2.getAbsolutePath();
            }
        } catch (Exception e3) {
            e3.printStackTrace();
            return file2.getAbsolutePath();
        }
    }

    public int dpToPx(int i2) {
        return Math.round(((float) i2) * (getResources().getDisplayMetrics().xdpi / 160.0f));
    }

    @Override public void onBackPressed() {
        super.onBackPressed();
        Utils.myUri.clear();
        Utils.selectedImagesUri.clear();
        Utils.createImageList.clear();
        Utils.imageUri.clear();
        Intent intent = new Intent(this, ActivitySelectImageAndVideo.class);
        intent.setFlags(67108864);
        startActivity(intent);
        finish();
    }
}
