package com.video_editor.pro.ActivityVideoJoiner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap.Config;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore.Video.Media;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.video_editor.pro.R;
import com.video_editor.pro.ActivityVideoJoiner.JoinerModels.Album;
import com.video_editor.pro.ActivityVideoJoiner.JoinerModels.Gallery;
import com.video_editor.pro.ActivityVideoJoiner.JoinerModels.SelectBucketImage;
import com.video_editor.pro.ActivityVideoJoiner.util.FileUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

@SuppressLint({"WrongConstant"})
public class ActivityGalleryList extends AppCompatActivity {
    public static ActivityGalleryList gallaryList = null;
    static final boolean BOOLEAN = true;
    public static Uri images;
    public static ImageLoader imgLoader;
    RecyclerGallaryAlbumAdapter galleryAlbumAdapter;
    ArrayList<Album> albumArrayList = null;
    ArrayList<Gallery> galleryArrayList = new ArrayList<>();
    String string = "";
    ListView listView;
    SelectBucketImage selectBucketImage = null;
    int anInt;

    public class RecyclerGallaryAlbumAdapter extends BaseAdapter {
        ArrayList<Gallery> galleryArrayList1 = new ArrayList<>();
        ImageLoader imageLoader;
        LayoutInflater inflater = null;

        public class Holder {
            ImageView imageView;
            LinearLayout linearLayout;
            TextView textView;

            public Holder() {
            }
        }

        public long getItemId(int i) {
            return i;
        }

        public RecyclerGallaryAlbumAdapter(Context context, ArrayList<Gallery> arrayList, ImageLoader imageLoader) {
            this.galleryArrayList1.addAll(arrayList);
            this.imageLoader = imageLoader;
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return this.galleryArrayList1.size();
        }

        public Object getItem(int i) {
            return Integer.valueOf(i);
        }

        public View getView(final int i, View view, ViewGroup viewGroup) {
            Holder holder = new Holder();
            View inflate = this.inflater.inflate(R.layout.slideshow_row_list_gallary, null);
            holder.textView = inflate.findViewById(R.id.tvAlbumTitle);
            holder.linearLayout = inflate.findViewById(R.id.layList2);
            holder.imageView = inflate.findViewById(R.id.ivThumb);
            int i2 = i % 2;
            if (i2 == 0) {
                holder.linearLayout.setBackgroundResource(R.drawable.album_bg);
            }
            if (i2 != 0) {
                holder.linearLayout.setBackgroundResource(R.drawable.album_bg2);
            }
            if (FileUtils.width < 1) {
                DisplayMetrics displayMetrics = ActivityGalleryList.this.getApplicationContext().getResources().getDisplayMetrics();
                FileUtils.width = displayMetrics.widthPixels;
                FileUtils.height = displayMetrics.heightPixels;
            }
            ActivityGalleryList.this.anInt = 0;
            this.imageLoader.displayImage(this.galleryArrayList1.get(i).imgUri.toString(), holder.imageView, new Builder().showImageOnLoading(17170445).resetViewBeforeLoading(ActivityGalleryList.BOOLEAN).showImageForEmptyUri(R.drawable.videothumb_images).showImageOnFail(R.drawable.videothumb_images).cacheInMemory(ActivityGalleryList.BOOLEAN).cacheOnDisc(ActivityGalleryList.BOOLEAN).bitmapConfig(Config.RGB_565).build());
            String str = this.galleryArrayList1.get(i).bucketName;
            int size = FileUtils.myUri.size();
            for (int i3 = 0; i3 < size; i3++) {
                String str2 = FileUtils.myUri.get(i3);
                StringBuilder sb = new StringBuilder();
                sb.append("/");
                sb.append(str);
                sb.append("/");
                if (str2.contains(sb.toString())) {
                    ActivityGalleryList.this.anInt++;
                }
            }
            if (str.length() > 30) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str.substring(0, 30));
                sb2.append("..");
                str = sb2.toString();
            }
            holder.textView.setText(str);
            inflate.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View view) {
                    ActivityGalleryList.this.Run(i);
                }
            });
            return inflate;
        }
    }

    class a extends AsyncTask<Void, Void, String> {
        a() {
        }



        public String doInBackground(Void... voidArr) {
            ActivityGalleryList.this.b();
            return null;
        }



        public void onPostExecute(String str) {
            super.onPostExecute(str);
            ActivityGalleryList.this.galleryAlbumAdapter = new RecyclerGallaryAlbumAdapter(ActivityGalleryList.this, ActivityGalleryList.this.galleryArrayList, ActivityGalleryList.imgLoader);
            ActivityGalleryList.this.listView.setAdapter(ActivityGalleryList.this.galleryAlbumAdapter);
        }
    }


    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView( R.layout.activity_gallery_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText("Select Album");
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (BOOLEAN || supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(BOOLEAN);
            supportActionBar.setDisplayShowTitleEnabled(false);
            a();
            this.listView = findViewById(R.id.listView);
            gallaryList = this;
            new a().execute();
            return;
        }
        throw new AssertionError();
    }


    @Override public void onResume() {
        super.onResume();
    }

    public static ActivityGalleryList gallary() {
        return gallaryList;
    }

    @Override public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void a() {
        imgLoader = ImageLoader.getInstance();
        imgLoader.init(new ImageLoaderConfiguration.Builder(this).defaultDisplayImageOptions(new Builder().cacheInMemory(BOOLEAN).cacheOnDisc(BOOLEAN).resetViewBeforeLoading(BOOLEAN).build()).build());
    }


    public void b() {
        Uri uri = Media.EXTERNAL_CONTENT_URI;
        String[] strArr = {"_id", "_data", "bucket_id", "bucket_display_name"};
        ArrayList arrayList = new ArrayList();
        Cursor query = getContentResolver().query(uri, strArr, "bucket_display_name != ?", new String[]{getResources().getString(R.string.app_name)}, "bucket_display_name ASC,_id DESC");
        if (query.moveToFirst()) {
            int columnIndex = query.getColumnIndex("bucket_display_name");
            int columnIndex2 = query.getColumnIndex("bucket_id");
            int columnIndex3 = query.getColumnIndex("_id");
            this.string = query.getString(columnIndex2);
            this.selectBucketImage = new SelectBucketImage();
            this.albumArrayList = new ArrayList<>();
            this.selectBucketImage.bucketid = this.string;
            do {
                Gallery gallery = new Gallery();
                gallery.bucketName = query.getString(columnIndex);
                gallery.bucketId = query.getString(columnIndex2);
                int i = query.getInt(columnIndex3);
                Uri withAppendedPath = Uri.withAppendedPath(Media.EXTERNAL_CONTENT_URI, Integer.toString(i));
                if (!arrayList.contains(gallery.bucketId)) {
                    arrayList.add(gallery.bucketId);
                    gallery.imgUri = withAppendedPath;
                    gallery.imgId = i;
                    this.galleryArrayList.add(gallery);
                    if (!this.string.equals(gallery.bucketId)) {
                        this.selectBucketImage.bucketid = this.string;
                        this.selectBucketImage.imgUri = new ArrayList<>();
                        this.selectBucketImage.imgUri.addAll(this.albumArrayList);
                        FileUtils.imageUri.add(this.selectBucketImage);
                        this.string = gallery.bucketId;
                        this.selectBucketImage = new SelectBucketImage();
                        this.albumArrayList = new ArrayList<>();
                    }
                }
                Album album = new Album(withAppendedPath, Integer.valueOf(i), -1);
                album.setImgUri(withAppendedPath);
                album.setImgId(Integer.valueOf(i));
                album.setImgPos(-1);
                this.albumArrayList.add(album);
            } while (query.moveToNext());
            this.selectBucketImage.bucketid = this.string;
            this.selectBucketImage.imgUri = new ArrayList<>();
            this.selectBucketImage.imgUri.addAll(this.albumArrayList);
            FileUtils.imageUri.add(this.selectBucketImage);
        }
    }

    public void Run(int i) {
        Intent intent = new Intent(this, ActivityGalleryPhotos.class);
        intent.putExtra("bucketid", i);
        startActivityForResult(intent, 0);
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
