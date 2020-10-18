package com.video_editor.pro.ActivityAudioJoiner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.video_editor.pro.R;

import java.util.ArrayList;

public class AdapterAudioJoiner extends BaseAdapter {
    int a;
    private ArrayList<String> b = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private final Context context;
    private class a {
        TextView a;

        private a() {
        }

        a(AdapterAudioJoiner adapterAudioJoiner, AdapterAudioJoiner adapterAudioJoiner2, a aVar) {
            this();
        }
    }

    public long getItemId(int i) {
        return i;
    }

    @SuppressLint({"WrongConstant"})
    AdapterAudioJoiner(Context context, ArrayList<String> arrayList, int i) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.b.addAll(arrayList);
        this.a = i;
    }

    public int getCount() {
        return this.b.size();
    }

    public Object getItem(int i) {
        return this.b.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        a aVar;
        if (view == null) {
            view = this.layoutInflater.inflate(R.layout.videosplit_row_list, null);
            aVar = new a(this, this, null);
            aVar.a = view.findViewById(R.id.textFormat);
            view.setTag(aVar);
        } else {
            aVar = (a) view.getTag();
        }
        aVar.a.setText(this.b.get(i));
        return view;
    }
}
