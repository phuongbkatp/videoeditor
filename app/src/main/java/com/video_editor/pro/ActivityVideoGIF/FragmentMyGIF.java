package com.video_editor.pro.ActivityVideoGIF;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap.Config;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.provider.MediaStore.Images.Media;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.video_editor.pro.UtilsAndAdapters.EditorHelper;
import com.video_editor.pro.R;
import com.video_editor.pro.ActivityVideoList.ListContentUtill;
import com.video_editor.pro.ActivityVideoList.VideoData;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;

public class FragmentMyGIF extends Fragment {
    AdapterMyGIF adapterMyGIF;
    ImageLoader imageLoader;
    ArrayList<VideoData> videoData = new ArrayList<>();
    ListView listView;
    String string;
    String[] strings;
    String[] strings1;
    private PowerManager powerManager;
    private WakeLock wakeLock;
    private AdView adView;



    @SuppressLint({"NewApi"})
    private class a extends AsyncTask<Void, Void, Boolean> {
        ProgressDialog progressDialog;
        private a() {
            this.progressDialog = null;
        }
        public void onPreExecute() {
            this.progressDialog = new ProgressDialog(FragmentMyGIF.this.getActivity());
            this.progressDialog.setMessage("Loading...");
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();
        }



        public Boolean doInBackground(Void... voidArr) {
            return Boolean.valueOf(FragmentMyGIF.this.b());
        }



        public void onPostExecute(Boolean bool) {
            this.progressDialog.dismiss();
            if (bool.booleanValue()) {
                FragmentMyGIF.this.adapterMyGIF = new AdapterMyGIF(FragmentMyGIF.this.getActivity(), FragmentMyGIF.this.videoData, FragmentMyGIF.this.imageLoader);
                FragmentMyGIF.this.listView.setAdapter(FragmentMyGIF.this.adapterMyGIF);
            }
        }
    }

    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @SuppressLint("InvalidWakeLockTag")
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.videomyfragment, viewGroup, false);
        this.powerManager = (PowerManager) getActivity().getSystemService(Context.POWER_SERVICE);
        this.wakeLock = this.powerManager.newWakeLock(6, "My Tag");
        a();
        this.listView = inflate.findViewById(R.id.VideogridView);
        new a().execute();
        this.adView = inflate.findViewById(R.id.banner_AdView);
        this.adView.loadAd(new Builder().build());
        if (isOnline()) {
            this.adView.setVisibility(View.VISIBLE);
        } else {
            this.adView.setVisibility(View.GONE);
        }
        return inflate;
    }

    private void a() {
        ImageLoaderConfiguration build = new ImageLoaderConfiguration.Builder(getActivity()).memoryCache(new WeakMemoryCache()).defaultDisplayImageOptions(new DisplayImageOptions.Builder().cacheInMemory().cacheOnDisc().bitmapConfig(Config.RGB_565).displayer(new FadeInBitmapDisplayer(400)).build()).build();
        this.imageLoader = ImageLoader.getInstance();
        this.imageLoader.init(build);
    }


    @SuppressLint({"NewApi"})
    public boolean b() {
        this.strings1 = new String[]{"_data", "_size", "_id"};
        if (EditorHelper.ModuleId == 7) {
            this.string = "_data like?";
            StringBuilder builder = new StringBuilder();
            builder.append("%");
            builder.append(getResources().getString(R.string.MainFolderName));
            builder.append("/");
            builder.append(getResources().getString(R.string.VideoToImage));
            builder.append("%");
            this.strings = new String[]{builder.toString()};
        } else if (EditorHelper.ModuleId == 12) {
            this.string = "_data like?";
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("%");
            stringBuilder.append(getResources().getString(R.string.MainFolderName));
            stringBuilder.append("/");
            stringBuilder.append(getResources().getString(R.string.VideoToGIF));
            stringBuilder.append("%");
            this.strings = new String[]{stringBuilder.toString()};
        }
        Cursor managedQuery = getActivity().managedQuery(Media.EXTERNAL_CONTENT_URI, this.strings1, this.string, this.strings, " _id DESC");
        int count = managedQuery.getCount();
        if (count <= 0) {
            return false;
        }
        managedQuery.moveToFirst();
        for (int i2 = 0; i2 < count; i2++) {
            Uri withAppendedPath = Uri.withAppendedPath(Media.EXTERNAL_CONTENT_URI, ListContentUtill.getLong(managedQuery));
            String string = managedQuery.getString(managedQuery.getColumnIndexOrThrow("_data"));
            StringBuilder sb3 = new StringBuilder();
            sb3.append(managedQuery.getString(managedQuery.getColumnIndex("_size")));
            sb3.append(" KB");
            this.videoData.add(new VideoData(string, withAppendedPath, string, sb3.toString()));
            managedQuery.moveToNext();
        }
        return true;
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
