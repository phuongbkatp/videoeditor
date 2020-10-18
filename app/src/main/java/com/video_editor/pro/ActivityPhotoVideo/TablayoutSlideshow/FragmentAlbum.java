package com.video_editor.pro.ActivityPhotoVideo.TablayoutSlideshow;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.video_editor.pro.R;
import com.video_editor.pro.ActivityPhotoVideo.ActivityDisplayAlbum;
import com.video_editor.pro.ActivityPhotoVideo.ModelSlideshow.Album;
import com.video_editor.pro.ActivityPhotoVideo.ModelSlideshow.Gallery;
import com.video_editor.pro.ActivityPhotoVideo.ModelSlideshow.ImageSelection;
import com.video_editor.pro.ActivityPhotoVideo.ModelSlideshow.SelectBucketImage;
import com.video_editor.pro.ActivityPhotoVideo.UtilsSlideshow.PreferenceManager;
import com.video_editor.pro.ActivityPhotoVideo.UtilsSlideshow.Utils;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;

@SuppressLint({"WrongConstant"})
public class FragmentAlbum extends Fragment {

    static Context context;
    RecyclerGallaryAlbumAdapter recyclerGallaryAlbumAdapter;
    ArrayList<Album> albums = null;
    ArrayList<Gallery> galleries;
    LayoutManager layoutManager;
    ImageLoader instance = ImageLoader.getInstance();
    String string = "";
    RecyclerView recyclerView;
    SelectBucketImage selectBucketImage = null;
    int anInt;
    View view;

    public class RecyclerGallaryAlbumAdapter extends Adapter<RecyclerGallaryAlbumAdapter.holder> {
        ArrayList<Gallery> a = new ArrayList<>();
        ImageLoader imageLoader;

        class holder extends ViewHolder {
            ImageView imageView;
            TextView textView;

            public holder(View view) {
                super(view);
                this.textView = view.findViewById(R.id.tvAlbumTitle);
                this.imageView = view.findViewById(R.id.ivThumb);
            }
        }

        public RecyclerGallaryAlbumAdapter(Context context, ArrayList<Gallery> arrayList, ImageLoader imageLoader) {
            this.a.addAll(arrayList);
            this.imageLoader = imageLoader;
        }

        public int getItemCount() {
            return this.a.size();
        }

        public void onBindViewHolder(holder aVar, final int i) {
            if (Utils.width < 1) {
                DisplayMetrics displayMetrics = FragmentAlbum.context.getResources().getDisplayMetrics();
                Utils.width = displayMetrics.widthPixels;
                Utils.height = displayMetrics.heightPixels;
            }
            FragmentAlbum.this.anInt = 0;
            Picasso.with(FragmentAlbum.context).load(this.a.get(i).imgUri.toString()).into(aVar.imageView);
            String str = this.a.get(i).bucketName;
            int size = Utils.myUri.size();
            for (int i2 = 0; i2 < size; i2++) {
                String str2 = Utils.myUri.get(i2);
                StringBuilder sb = new StringBuilder();
                sb.append("/");
                sb.append(str);
                sb.append("/");
                if (str2.contains(sb.toString())) {
                    FragmentAlbum.this.anInt++;
                }
            }
            if (str.length() > 30) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str.substring(0, 30));
                sb2.append("..");
                str = sb2.toString();
            }
            aVar.textView.setText(str);
            aVar.itemView.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View view) {
                    FragmentAlbum.this.functionToRun(i);
                }
            });
        }

        public holder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new holder(LayoutInflater.from(FragmentAlbum.context).inflate(R.layout.slideshow_row_list_gallary, viewGroup, false));
        }
    }

    class a extends AsyncTask<Void, Void, String> {
        a() {
        }



        public String doInBackground(Void... voidArr) {
            FragmentAlbum.this.b();
            return null;
        }



        public void onPostExecute(String str) {
            super.onPostExecute(str);
            if (FragmentAlbum.this.isAdded()) {
                FragmentAlbum.this.getResources().getString(R.string.app_name);
            }
            FragmentAlbum.this.recyclerGallaryAlbumAdapter = new RecyclerGallaryAlbumAdapter(FragmentAlbum.context, FragmentAlbum.this.galleries, FragmentAlbum.this.instance);
            FragmentAlbum.this.recyclerView.setAdapter(FragmentAlbum.this.recyclerGallaryAlbumAdapter);
        }
    }

    public static Fragment newInstance(int i2, Context context) {

        Bundle bundle = new Bundle();
        FragmentAlbum.context = context;
        bundle.putInt(FragmentPhoto.ARG_PAGE, i2);
        FragmentAlbum fragmentAlbum = new FragmentAlbum();
        fragmentAlbum.setArguments(bundle);
        return fragmentAlbum;
    }

    @Override public void onStart() {
        super.onStart();
        this.galleries = new ArrayList<>();
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.e("", "on attch");
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.view = layoutInflater.inflate(R.layout.slideshow_tab_album, viewGroup, false);
        a();
        return this.view;
    }

    private void a() {
        this.recyclerView = this.view.findViewById(R.id.recycler_view);
        this.layoutManager = new GridLayoutManager(context, 1);
        this.recyclerView.setLayoutManager(this.layoutManager);
        new a().execute();
    }

    @Override public void onStop() {
        super.onStop();
        if (this.instance != null) {
            this.instance.clearDiskCache();
            this.instance.clearMemoryCache();
        }
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
            this.albums = new ArrayList<>();
            this.selectBucketImage.bucketid = this.string;
            do {
                Gallery gallery = new Gallery();
                gallery.bucketName = query.getString(columnIndex);
                gallery.bucketId = query.getString(columnIndex2);
                int i2 = query.getInt(columnIndex3);
                Uri withAppendedPath = Uri.withAppendedPath(Media.EXTERNAL_CONTENT_URI, Integer.toString(i2));
                if (!arrayList.contains(gallery.bucketId)) {
                    arrayList.add(gallery.bucketId);
                    gallery.imgUri = withAppendedPath;
                    gallery.imgId = i2;
                    this.galleries.add(gallery);
                    if (!this.string.equals(gallery.bucketId)) {
                        this.selectBucketImage.bucketid = this.string;
                        this.selectBucketImage.imgUri = new ArrayList<>();
                        this.selectBucketImage.imgUri.addAll(this.albums);
                        Utils.imageUri.add(this.selectBucketImage);
                        this.string = gallery.bucketId;
                        this.selectBucketImage = new SelectBucketImage();
                        this.albums = new ArrayList<>();
                    }
                }
                Album album = new Album(withAppendedPath, Integer.valueOf(i2), -1);
                album.setImgUri(withAppendedPath);
                album.setImgId(Integer.valueOf(i2));
                album.setImgPos(-1);
                this.albums.add(album);
            } while (query.moveToNext());
            this.selectBucketImage.bucketid = this.string;
            this.selectBucketImage.imgUri = new ArrayList<>();
            this.selectBucketImage.imgUri.addAll(this.albums);
            Utils.imageUri.add(this.selectBucketImage);
        }
    }

    @Override public void onActivityResult(int i2, int i3, Intent intent) {
        super.onActivityResult(i2, i3, intent);
        if (this.recyclerGallaryAlbumAdapter != null && i2 == 0) {
            this.recyclerGallaryAlbumAdapter.notifyDataSetChanged();
        } else if (this.recyclerGallaryAlbumAdapter != null && i2 == 69) {
            this.recyclerGallaryAlbumAdapter.notifyDataSetChanged();
        }
    }


    public void onBackPressed() {
        int size = Utils.imageUri.size();
        for (int i2 = 0; i2 < size; i2++) {
            int size2 = Utils.imageUri.get(i2).imgUri.size();
            for (int i3 = 0; i3 < size2; i3++) {
                int i4 = Utils.imageUri.get(i2).imgUri.get(i3).getImgPos();
                if (i4 >= 0) {
                    ImageSelection imageSelection = new ImageSelection();
                    int indexId = PreferenceManager.getIndexId();
                    imageSelection.indexId = indexId;
                    PreferenceManager.setIndexId(indexId + 1);
                    imageSelection.imgId = Utils.imageUri.get(i2).imgUri.get(i3).getImgId().intValue();
                    imageSelection.imgUri = Utils.imageUri.get(i2).imgUri.get(i3).getImgUri().toString();
                    imageSelection.cropIndex = -1;
                    imageSelection.imgPos = i4;
                    Utils.selectImageList.add(imageSelection);
                }
            }
        }
        HashSet hashSet = new HashSet();
        hashSet.addAll(Utils.selectImageList);
        Utils.selectImageList.clear();
        Utils.selectImageList.addAll(hashSet);
        HashSet hashSet2 = new HashSet();
        hashSet2.addAll(Utils.selectedImagesUri);
        Utils.selectedImagesUri.clear();
        Utils.selectedImagesUri.addAll(hashSet2);
        if (Utils.myUri.size() > 0) {
            Builder builder = new Builder(getActivity());
            builder.setTitle("Alert!!!");
            builder.setMessage("you are going to remove selection. Are you sure you want to back from gallery?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                    FragmentAlbum.this.getActivity().finish();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.create().show();
            return;
        }
        getActivity().finish();
    }

    @Override public void onDestroy() {
        getActivity().getWindow().clearFlags(128);
        super.onDestroy();
        if (this.instance != null) {
            this.instance.clearDiskCache();
            this.instance.clearMemoryCache();
        }
    }

    @Override public void onResume() {
        super.onResume();
        if (this.instance == null) {
            c();
        }
    }

    private void c() {
        ImageLoaderConfiguration build = new ImageLoaderConfiguration.Builder(context).memoryCache(new WeakMemoryCache()).defaultDisplayImageOptions(new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).imageScaleType(ImageScaleType.EXACTLY).displayer(new FadeInBitmapDisplayer(200)).build()).build();
        this.instance = ImageLoader.getInstance();
        this.instance.init(build);
    }

    public void functionToRun(int i2) {
        Intent intent = new Intent(context, ActivityDisplayAlbum.class);
        intent.putExtra("bucketid", i2);
        startActivityForResult(intent, 0);
    }
}
