package com.video_editor.pro.ActivityVideoJoiner;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap.Config;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore.Video.Media;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.video_editor.pro.R;
import com.video_editor.pro.ActivityVideoJoiner.JoinerModels.Album;
import com.video_editor.pro.ActivityVideoJoiner.JoinerModels.Gallery;
import com.video_editor.pro.ActivityVideoJoiner.JoinerModels.SelectBucketImage;
import com.video_editor.pro.ActivityVideoJoiner.util.FileUtils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

public class FragmentSelectVideo extends Fragment {
    public static Uri images;
    public static ImageLoader imageLoader;
    RecyclerGallaryAlbumAdapter recyclerGallaryAlbumAdapter;
    ArrayList<Album> aNull = null;
    ArrayList<Gallery> galleryArrayList = new ArrayList<>();
    String string = "";
    ListView listView;
    SelectBucketImage selectBucketImage = null;
    int anInt;
    private AdView adView;

    public class RecyclerGallaryAlbumAdapter extends BaseAdapter {
        ArrayList<Gallery> galleries = new ArrayList<>();
        ImageLoader imageLoader1;
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
            this.galleries.addAll(arrayList);
            this.imageLoader1 = imageLoader;
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return this.galleries.size();
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
                DisplayMetrics displayMetrics = FragmentSelectVideo.this.getActivity().getResources().getDisplayMetrics();
                FileUtils.width = displayMetrics.widthPixels;
                FileUtils.height = displayMetrics.heightPixels;
            }
            FragmentSelectVideo.this.anInt = 0;
            this.imageLoader1.displayImage(this.galleries.get(i).imgUri.toString(), holder.imageView, new Builder().showImageOnLoading(17170445).resetViewBeforeLoading(true).showImageForEmptyUri(R.drawable.videothumb_images).showImageOnFail(R.drawable.videothumb_images).cacheInMemory(true).cacheOnDisc(true).bitmapConfig(Config.RGB_565).build());
            String str = this.galleries.get(i).bucketName;
            int size = FileUtils.myUri.size();
            for (int i3 = 0; i3 < size; i3++) {
                String str2 = FileUtils.myUri.get(i3);
                StringBuilder sb = new StringBuilder();
                sb.append("/");
                sb.append(str);
                sb.append("/");
                if (str2.contains(sb.toString())) {
                    FragmentSelectVideo.this.anInt++;
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
                    FragmentSelectVideo.this.Run(i);
                }
            });
            return inflate;
        }
    }

    class a extends AsyncTask<Void, Void, String> {
        a() {
        }



        public String doInBackground(Void... voidArr) {
            FragmentSelectVideo.this.b();
            return null;
        }



        public void onPostExecute(String str) {
            super.onPostExecute(str);
            FragmentSelectVideo.this.recyclerGallaryAlbumAdapter = new RecyclerGallaryAlbumAdapter(FragmentSelectVideo.this.getActivity(), FragmentSelectVideo.this.galleryArrayList, FragmentSelectVideo.imageLoader);
            FragmentSelectVideo.this.listView.setAdapter(FragmentSelectVideo.this.recyclerGallaryAlbumAdapter);
        }
    }

    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_video_joiner, viewGroup, false);
        a();
        this.listView = inflate.findViewById(R.id.listView);
        new a().execute();
        this.adView = inflate.findViewById(R.id.banner_AdView);
        this.adView.loadAd(new AdRequest.Builder().build());
        if (isOnline()) {
            this.adView.setVisibility(View.VISIBLE);
        } else {
            this.adView.setVisibility(View.GONE);
        }
        return inflate;
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    private void a() {
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(new ImageLoaderConfiguration.Builder(getActivity()).defaultDisplayImageOptions(new Builder().cacheInMemory(true).cacheOnDisc(true).resetViewBeforeLoading(true).build()).build());
    }


    public void b() {
        Uri uri = Media.EXTERNAL_CONTENT_URI;
        String[] strArr = {"_id", "_data", "bucket_id", "bucket_display_name"};
        ArrayList arrayList = new ArrayList();
        Cursor query = getActivity().getContentResolver().query(uri, strArr, "bucket_display_name != ?", new String[]{getResources().getString(R.string.app_name)}, "bucket_display_name ASC,_id DESC");
        if (query.moveToFirst()) {
            int columnIndex = query.getColumnIndex("bucket_display_name");
            int columnIndex2 = query.getColumnIndex("bucket_id");
            int columnIndex3 = query.getColumnIndex("_id");
            this.string = query.getString(columnIndex2);
            this.selectBucketImage = new SelectBucketImage();
            this.aNull = new ArrayList<>();
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
                        this.selectBucketImage.imgUri.addAll(this.aNull);
                        FileUtils.imageUri.add(this.selectBucketImage);
                        this.string = gallery.bucketId;
                        this.selectBucketImage = new SelectBucketImage();
                        this.aNull = new ArrayList<>();
                    }
                }
                Album album = new Album(withAppendedPath, Integer.valueOf(i), -1);
                album.setImgUri(withAppendedPath);
                album.setImgId(Integer.valueOf(i));
                album.setImgPos(-1);
                this.aNull.add(album);
            } while (query.moveToNext());
            this.selectBucketImage.bucketid = this.string;
            this.selectBucketImage.imgUri = new ArrayList<>();
            this.selectBucketImage.imgUri.addAll(this.aNull);
            FileUtils.imageUri.add(this.selectBucketImage);
        }
    }

    public void Run(int i) {
        Intent intent = new Intent(getActivity(), ActivityGalleryPhotos.class);
        intent.putExtra("bucketid", i);
        startActivityForResult(intent, 0);
    }
}
