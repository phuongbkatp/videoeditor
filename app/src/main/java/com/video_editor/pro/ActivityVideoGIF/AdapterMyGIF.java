package com.video_editor.pro.ActivityVideoGIF;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.video_editor.pro.R;
import com.video_editor.pro.ActivityVideoList.VideoData;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public class AdapterMyGIF extends BaseAdapter {
    ImageLoader imageLoader;
    ArrayList<VideoData> videoData = new ArrayList<>();
    ArrayList<VideoData> videoData1 = new ArrayList<>();
    private LayoutInflater inflater;

    public final Context context;

    private class a {
        ImageView imageView;
        TextView textView;
        TextView textView1;

        private a() {
        }
    }

    public long getItemId(int i) {
        return i;
    }

    public AdapterMyGIF(Context context, ArrayList<VideoData> arrayList, ImageLoader imageLoader) {
        this.context = context;
        this.imageLoader = imageLoader;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.videoData1.addAll(arrayList);
        this.videoData.addAll(arrayList);
    }

    public int getCount() {
        return this.videoData1.size();
    }

    public Object getItem(int i) {
        return this.videoData1.get(i);
    }

    public View getView(final int i, View view, ViewGroup viewGroup) {
        a aVar;
        if (view == null) {
            view = this.inflater.inflate(R.layout.row_video, null);
            aVar = new a();
            aVar.imageView = view.findViewById(R.id.image_preview);
            aVar.textView1 = view.findViewById(R.id.file_name);
            aVar.textView = view.findViewById(R.id.duration);
            view.setTag(aVar);
        } else {
            aVar = (a) view.getTag();
        }
        this.imageLoader.displayImage(this.videoData1.get(i).videouri.toString(), aVar.imageView, new Builder().showImageForEmptyUri(0).cacheInMemory(true).showStubImage(R.color.trans).cacheOnDisk(true).resetViewBeforeLoading(true).imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Config.ARGB_8888).delayBeforeLoading(100).postProcessor(new BitmapProcessor() {
            public Bitmap process(Bitmap bitmap) {
                return Bitmap.createScaledBitmap(bitmap, 100, 100, false);
            }
        }).displayer(new SimpleBitmapDisplayer()).build());
        view.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent = new Intent(AdapterMyGIF.this.context, ActivityGIFPreview.class);
                intent.putExtra("videourl", AdapterMyGIF.this.videoData1.get(i).videoPath);
                intent.putExtra("isfrommain", true);
                AdapterMyGIF.this.context.startActivity(intent);
            }
        });
        if (i % 2 == 0) {
            view.setBackgroundResource(R.drawable.divider_1);
        } else {
            view.setBackgroundResource(R.drawable.divider_2);
        }
        TextView textView = aVar.textView1;
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(new File(this.videoData1.get(i).videoName).getName());
        textView.setText(sb.toString());
        TextView textView2 = aVar.textView;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("");
        sb2.append(this.videoData1.get(i).duration);
        textView2.setText(sb2.toString());
        return view;
    }

    public void filter(String str) {
        String lowerCase = str.toLowerCase(Locale.getDefault());
        this.videoData1.clear();
        if (lowerCase.length() == 0) {
            this.videoData1.addAll(this.videoData);
        } else {
            Iterator it = this.videoData.iterator();
            while (it.hasNext()) {
                VideoData videoData = (VideoData) it.next();
                if (videoData.videoName.toLowerCase(Locale.getDefault()).contains(lowerCase)) {
                    this.videoData1.add(videoData);
                }
            }
        }
        notifyDataSetChanged();
    }
}
