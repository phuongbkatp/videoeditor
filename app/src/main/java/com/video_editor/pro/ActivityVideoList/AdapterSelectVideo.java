package com.video_editor.pro.ActivityVideoList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;
import com.video_editor.pro.ActivityAudioVideoMixer.ActivityAudioVideoMixer;
import com.video_editor.pro.ActivityFastMotion.ActivityFastMotion;
import com.video_editor.pro.ActivitySlowmotion.ActivitySlowMotionVideo;
import com.video_editor.pro.ActivityVideoCompressor.ActivityVideoCompressor;
import com.video_editor.pro.ActivityVideoConverter.ActivityVideoConverter;
import com.video_editor.pro.ActivityVideoCropper.ActivityVideoCropper;
import com.video_editor.pro.ActivityVideoCutter.ActivityVideoCutter;
import com.video_editor.pro.ActivityVideoMirror.ActivityVideoMirror;
import com.video_editor.pro.ActivityVideoMute.ActivityVideoMute;
import com.video_editor.pro.ActivityVideoReverse.ActivityVideoReverse;
import com.video_editor.pro.ActivityVideoRotate.ActivityVideoRotate;
import com.video_editor.pro.ActivityVideoSplit.ActivityVideoSplitter;
import com.video_editor.pro.R;
import com.video_editor.pro.UtilsAndAdapters.EditorHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public class AdapterSelectVideo extends BaseAdapter {
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

    public AdapterSelectVideo(Context context, ArrayList<VideoData> arrayList, ImageLoader imageLoader) {
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
                if (EditorHelper.ModuleId == 1) {
                    Intent intent = new Intent(AdapterSelectVideo.this.context, ActivityVideoCutter.class);
                    intent.setFlags(67108864);
                    intent.putExtra("path", AdapterSelectVideo.this.videoData1.get(i).videoPath);
                    AdapterSelectVideo.this.context.startActivity(intent);
                } else if (EditorHelper.ModuleId == 2) {
                    Intent intent2 = new Intent(AdapterSelectVideo.this.context, ActivityVideoCompressor.class);
                    intent2.setFlags(67108864);
                    intent2.putExtra("videouri", AdapterSelectVideo.this.videoData1.get(i).videoPath);
                    AdapterSelectVideo.this.context.startActivity(intent2);
                } else if (EditorHelper.ModuleId == 4) {
                    Intent intent3 = new Intent(AdapterSelectVideo.this.context, ActivityAudioVideoMixer.class);
                    intent3.setFlags(67108864);
                    Bundle bundle = new Bundle();
                    bundle.putString("song", AdapterSelectVideo.this.videoData1.get(i).videoPath);
                    intent3.putExtras(bundle);
                    AdapterSelectVideo.this.context.startActivity(intent3);
                } else if (EditorHelper.ModuleId == 5) {
                    Intent intent4 = new Intent(AdapterSelectVideo.this.context, ActivityVideoMute.class);
                    intent4.setFlags(67108864);
                    intent4.putExtra("videouri", AdapterSelectVideo.this.videoData1.get(i).videoPath);
                    AdapterSelectVideo.this.context.startActivity(intent4);
                } else if (EditorHelper.ModuleId == 8) {
                    Intent intent5 = new Intent(AdapterSelectVideo.this.context, ActivityVideoConverter.class);
                    intent5.setFlags(67108864);
                    intent5.putExtra("videofilename", AdapterSelectVideo.this.videoData1.get(i).videoPath);
                    AdapterSelectVideo.this.context.startActivity(intent5);
                } else if (EditorHelper.ModuleId == 9) {
                    Intent intent6 = new Intent(AdapterSelectVideo.this.context, ActivityFastMotion.class);
                    intent6.setFlags(67108864);
                    intent6.putExtra("videofilename", AdapterSelectVideo.this.videoData1.get(i).videoPath);
                    AdapterSelectVideo.this.context.startActivity(intent6);
                } else if (EditorHelper.ModuleId == 10) {
                    Intent intent7 = new Intent(AdapterSelectVideo.this.context, ActivitySlowMotionVideo.class);
                    intent7.setFlags(67108864);
                    intent7.putExtra("videofilename", AdapterSelectVideo.this.videoData1.get(i).videoPath);
                    AdapterSelectVideo.this.context.startActivity(intent7);
                } else if (EditorHelper.ModuleId == 11) {
                    Intent intent8 = new Intent(AdapterSelectVideo.this.context, ActivityVideoCropper.class);
                    intent8.setFlags(67108864);
                    intent8.putExtra("videofilename", AdapterSelectVideo.this.videoData1.get(i).videoPath);
                    AdapterSelectVideo.this.context.startActivity(intent8);
                } else if (EditorHelper.ModuleId == 13) {
                    Intent intent9 = new Intent(AdapterSelectVideo.this.context, ActivityVideoRotate.class);
                    intent9.setFlags(67108864);
                    intent9.putExtra("videoPath", AdapterSelectVideo.this.videoData1.get(i).videoPath);
                    AdapterSelectVideo.this.context.startActivity(intent9);
                } else if (EditorHelper.ModuleId == 14) {
                    Intent intent10 = new Intent(AdapterSelectVideo.this.context, ActivityVideoMirror.class);
                    intent10.setFlags(67108864);
                    intent10.putExtra("videouri", AdapterSelectVideo.this.videoData1.get(i).videoPath);
                    AdapterSelectVideo.this.context.startActivity(intent10);
                } else if (EditorHelper.ModuleId == 15) {
                    Intent intent11 = new Intent(AdapterSelectVideo.this.context, ActivityVideoSplitter.class);
                    intent11.setFlags(67108864);
                    intent11.putExtra("videouri", AdapterSelectVideo.this.videoData1.get(i).videoPath);
                    AdapterSelectVideo.this.context.startActivity(intent11);
                } else if (EditorHelper.ModuleId == 16) {
                    Intent intent12 = new Intent(AdapterSelectVideo.this.context, ActivityVideoReverse.class);
                    intent12.setFlags(67108864);
                    intent12.putExtra("videouri", AdapterSelectVideo.this.videoData1.get(i).videoPath);
                    AdapterSelectVideo.this.context.startActivity(intent12);
                } else if (EditorHelper.ModuleId == 22) {
                }
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
        sb.append(this.videoData1.get(i).videoName);
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
