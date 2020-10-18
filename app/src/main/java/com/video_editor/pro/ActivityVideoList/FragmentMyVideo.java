package com.video_editor.pro.ActivityVideoList;

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

import com.video_editor.pro.UtilsAndAdapters.EditorHelper;
import com.video_editor.pro.R;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration.Builder;
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

    @SuppressLint({"NewApi"})
    private class a extends AsyncTask<Void, Void, Boolean> {
        ProgressDialog progressDialog;

        private a() {
            this.progressDialog = null;
        }


        public void onPreExecute() {
            this.progressDialog = new ProgressDialog(FragmentMyVideo.this.getActivity());
            this.progressDialog.setMessage("Loading...");
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();
        }



        public Boolean doInBackground(Void... voidArr) {
            return Boolean.valueOf(FragmentMyVideo.this.b());
        }



        public void onPostExecute(Boolean bool) {
            this.progressDialog.dismiss();
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
        return inflate;
    }

    private void a() {
        ImageLoaderConfiguration build = new Builder(getActivity()).memoryCache(new WeakMemoryCache()).defaultDisplayImageOptions(new DisplayImageOptions.Builder().cacheInMemory().cacheOnDisc().bitmapConfig(Config.RGB_565).displayer(new FadeInBitmapDisplayer(400)).build()).build();
        this.imageLoader = ImageLoader.getInstance();
        this.imageLoader.init(build);
    }


    @SuppressLint({"NewApi"})
    public boolean b() {
        if (EditorHelper.ModuleId == 1) {
            this.string = "_data like?";
            StringBuilder sb = new StringBuilder();
            sb.append("%");
            sb.append(getResources().getString(R.string.MainFolderName));
            sb.append("/");
            sb.append(getResources().getString(R.string.VideoCutter));
            sb.append("%");
            this.strings = new String[]{sb.toString()};
        } else if (EditorHelper.ModuleId == 2) {
            this.string = "_data like?";
            StringBuilder sb2 = new StringBuilder();
            sb2.append("%");
            sb2.append(getResources().getString(R.string.MainFolderName));
            sb2.append("/");
            sb2.append(getResources().getString(R.string.VideoCompressor));
            sb2.append("%");
            this.strings = new String[]{sb2.toString()};
        } else if (EditorHelper.ModuleId == 4) {
            this.string = "_data like?";
            StringBuilder sb3 = new StringBuilder();
            sb3.append("%");
            sb3.append(getResources().getString(R.string.MainFolderName));
            sb3.append("/");
            sb3.append(getResources().getString(R.string.AudioVideoMixer));
            sb3.append("%");
            this.strings = new String[]{sb3.toString()};
        } else if (EditorHelper.ModuleId == 5) {
            this.string = "_data like?";
            StringBuilder sb4 = new StringBuilder();
            sb4.append("%");
            sb4.append(getResources().getString(R.string.MainFolderName));
            sb4.append("/");
            sb4.append(getResources().getString(R.string.VideoMute));
            sb4.append("%");
            this.strings = new String[]{sb4.toString()};
        } else if (EditorHelper.ModuleId == 8) {
            this.string = "_data like?";
            StringBuilder sb5 = new StringBuilder();
            sb5.append("%");
            sb5.append(getResources().getString(R.string.MainFolderName));
            sb5.append("/");
            sb5.append(getResources().getString(R.string.VideoConverter));
            sb5.append("%");
            this.strings = new String[]{sb5.toString()};
        } else if (EditorHelper.ModuleId == 9) {
            this.string = "_data like?";
            StringBuilder sb6 = new StringBuilder();
            sb6.append("%");
            sb6.append(getResources().getString(R.string.MainFolderName));
            sb6.append("/");
            sb6.append(getResources().getString(R.string.FastMotionVideo));
            sb6.append("%");
            this.strings = new String[]{sb6.toString()};
        } else if (EditorHelper.ModuleId == 10) {
            this.string = "_data like?";
            StringBuilder sb7 = new StringBuilder();
            sb7.append("%");
            sb7.append(getResources().getString(R.string.MainFolderName));
            sb7.append("/");
            sb7.append(getResources().getString(R.string.SlowMotionVideo));
            sb7.append("%");
            this.strings = new String[]{sb7.toString()};
        } else if (EditorHelper.ModuleId == 11) {
            this.string = "_data like?";
            StringBuilder sb8 = new StringBuilder();
            sb8.append("%");
            sb8.append(getResources().getString(R.string.MainFolderName));
            sb8.append("/");
            sb8.append(getResources().getString(R.string.VideoCroper));
            sb8.append("%");
            this.strings = new String[]{sb8.toString()};
        } else if (EditorHelper.ModuleId == 13) {
            this.string = "_data like?";
            StringBuilder sb9 = new StringBuilder();
            sb9.append("%");
            sb9.append(getResources().getString(R.string.MainFolderName));
            sb9.append("/");
            sb9.append(getResources().getString(R.string.VideoRotate));
            sb9.append("%");
            this.strings = new String[]{sb9.toString()};
        } else if (EditorHelper.ModuleId == 14) {
            this.string = "_data like?";
            StringBuilder sb10 = new StringBuilder();
            sb10.append("%");
            sb10.append(getResources().getString(R.string.MainFolderName));
            sb10.append("/");
            sb10.append(getResources().getString(R.string.VideoMirror));
            sb10.append("%");
            this.strings = new String[]{sb10.toString()};
        } else if (EditorHelper.ModuleId == 15) {
            this.string = "_data like?";
            StringBuilder sb11 = new StringBuilder();
            sb11.append("%");
            sb11.append(getResources().getString(R.string.MainFolderName));
            sb11.append("/");
            sb11.append(getResources().getString(R.string.VideoSplitter));
            sb11.append("%");
            this.strings = new String[]{sb11.toString()};
        } else if (EditorHelper.ModuleId == 16) {
            this.string = "_data like?";
            StringBuilder sb12 = new StringBuilder();
            sb12.append("%");
            sb12.append(getResources().getString(R.string.MainFolderName));
            sb12.append("/");
            sb12.append(getResources().getString(R.string.VideoReverse));
            sb12.append("%");
            this.strings = new String[]{sb12.toString()};
        } else if (EditorHelper.ModuleId == 22) {
            this.string = "_data like?";
            StringBuilder sb13 = new StringBuilder();
            sb13.append("%");
            sb13.append(getResources().getString(R.string.MainFolderName));
            sb13.append("/");
            sb13.append(getResources().getString(R.string.VideoWatermark));
            sb13.append("%");
            this.strings = new String[]{sb13.toString()};
        }
        Cursor managedQuery = getActivity().managedQuery(Media.EXTERNAL_CONTENT_URI, new String[]{"_data", "_id", "_display_name", "duration"}, this.string, this.strings, " _id DESC");
        int count = managedQuery.getCount();
        if (count <= 0) {
            return false;
        }
        managedQuery.moveToFirst();
        for (int i = 0; i < count; i++) {
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
