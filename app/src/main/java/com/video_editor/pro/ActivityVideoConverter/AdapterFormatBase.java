package com.video_editor.pro.ActivityVideoConverter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.video_editor.pro.R;

import java.util.ArrayList;

public class AdapterFormatBase extends BaseAdapter {
    ArrayList arrayList = new ArrayList();
    int anInt;
    private LayoutInflater inflater;
    private final Context context;

    private class a {
        TextView textView;

        private a() {
        }

        a(AdapterFormatBase adapterFormatBase, AdapterFormatBase adapterFormatBase2, a aVar) {
            this();
        }
    }

    public long getItemId(int i) {
        return i;
    }

    @SuppressLint({"WrongConstant"})
    public AdapterFormatBase(Context context, ArrayList arrayList, int i) {
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
        a aVariable;
        if (view == null) {
            view = this.inflater.inflate(R.layout.videosplit_row_list, null);
            aVariable = new a(this, this, null);
            aVariable.textView = view.findViewById(R.id.textFormat);
            view.setTag(aVariable);
        } else {
            aVariable = (a) view.getTag();
        }
        aVariable.textView.setText((CharSequence) this.arrayList.get(i));
        return view;
    }
}
