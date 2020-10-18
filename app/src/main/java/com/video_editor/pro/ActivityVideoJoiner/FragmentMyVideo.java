package com.video_editor.pro.ActivityVideoJoiner;

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
import android.provider.MediaStore.Video.Media;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

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

public class FragmentMyVideo extends Fragment {
    AdapterMyVideo adapterMyVideo;
    ImageLoader imageLoader;
    ArrayList<VideoData> videoData = new ArrayList<>();
    ListView listView;
    String string;
    String[] strings;
    private PowerManager powerManager;
    private WakeLock wakeLock;
    private AdView adView;

    @SuppressLint({"NewApi"})
    private class a extends AsyncTask<Void, Void, Boolean> {
        ProgressDialog a;

        private a() {
            this.a = null;
        }


        public void onPreExecute() {
            this.a = new ProgressDialog(FragmentMyVideo.this.getActivity());
            this.a.setMessage("Loading...");
            this.a.setCancelable(false);
            this.a.show();
        }



        public Boolean doInBackground(Void... voidArr) {
            return Boolean.valueOf(FragmentMyVideo.this.b());
        }



        public void onPostExecute(Boolean bool) {
            this.a.dismiss();
            if (bool.booleanValue()) {
                FragmentMyVideo.this.adapterMyVideo = new AdapterMyVideo(FragmentMyVideo.this.getActivity(), FragmentMyVideo.this.videoData, FragmentMyVideo.this.imageLoader);
                FragmentMyVideo.this.listView.setAdapter(FragmentMyVideo.this.adapterMyVideo);
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
        this.string = "_data like?";
        StringBuilder sb = new StringBuilder();
        sb.append("%");
        sb.append(getResources().getString(R.string.MainFolderName));
        sb.append("/");
        sb.append(getResources().getString(R.string.VideoJoiner));
        sb.append("%");
        this.strings = new String[]{sb.toString()};
        Cursor managedQuery = getActivity().managedQuery(Media.EXTERNAL_CONTENT_URI, new String[]{"_data", "_id", "_display_name", "duration"}, this.string, this.strings, " _id DESC");
        int count = managedQuery.getCount();
        if (count <= 0) {
            return false;
        }
        managedQuery.moveToFirst();
        for (int i2 = 0; i2 < count; i2++) {
            Uri withAppendedPath = Uri.withAppendedPath(Media.EXTERNAL_CONTENT_URI, ListContentUtill.getLong(managedQuery));
            this.videoData.add(new VideoData(managedQuery.getString(managedQuery.getColumnIndexOrThrow("_display_name")), withAppendedPath, managedQuery.getString(managedQuery.getColumnIndex("_data")), ListContentUtill.getTime(managedQuery, "duration")));
            managedQuery.moveToNext();
        }
        return true;
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
