package com.video_editor.pro.ActivityAudioCompress;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.video_editor.pro.R;

import java.util.ArrayList;

public class AdapterAudioCompressor extends BaseAdapter {
    int anInt;
    private ArrayList<String> arrayList = new ArrayList<>();
    private LayoutInflater inflater;
    private final Context context;

    private class a {
        TextView textView;

        private a() {
        }

        a(AdapterAudioCompressor adapterAudioCompressor, AdapterAudioCompressor adapterAudioCompressor2, a aVar) {
            this();
        }
    }

    public long getItemId(int i) {
        return i;
    }

    @SuppressLint({"WrongConstant"})
    AdapterAudioCompressor(Context context, ArrayList<String> arrayList, int i) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arrayList.addAll(arrayList);
        this.anInt = i;
    }

    public int getCount() {
        return this.arrayList.size();
    }

    public Object getItem(int i) {
        return this.arrayList.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        a aVar;
        if (view == null) {
            view = this.inflater.inflate(R.layout.videosplit_row_list, null);
            aVar = new a(this, this, null);
            aVar.textView = view.findViewById(R.id.textFormat);
            view.setTag(aVar);
        } else {
            aVar = (a) view.getTag();
        }
        aVar.textView.setText(this.arrayList.get(i));
        return view;
    }
}
