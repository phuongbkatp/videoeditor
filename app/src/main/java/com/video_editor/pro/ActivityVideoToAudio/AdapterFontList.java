package com.video_editor.pro.ActivityVideoToAudio;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.video_editor.pro.R;

import java.util.ArrayList;

@SuppressLint({"WrongConstant"})
public class AdapterFontList extends BaseAdapter {
    ArrayList<String> arrayList;
    Context context = null;
    private LayoutInflater inflater;

    private class a {
        FrameLayout frameLayout;
        ImageView imageView;
        TextView textView;

        private a() {
        }
    }

    public long getItemId(int i) {
        return i;
    }

    public AdapterFontList(Context context, ArrayList<String> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return this.arrayList.size();
    }

    public Object getItem(int i) {
        return this.arrayList.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        View inflate = this.inflater.inflate(R.layout.video_to_audio_font_list_item, null);
        a aVar = new a();
        aVar.textView = inflate.findViewById(R.id.Text);
        aVar.imageView = inflate.findViewById(R.id.selected);
        aVar.frameLayout = inflate.findViewById(R.id.font_item_view);
        inflate.setTag(aVar);
        a aVar2 = (a) inflate.getTag();
        aVar2.textView.setText(this.arrayList.get(i));
        if (FileUtils.Bitrate == i) {
            aVar2.textView.setBackgroundResource(R.drawable.bg_round_press);
            aVar2.textView.setTextColor(Color.parseColor("#ffffff"));
        } else {
            aVar2.textView.setBackgroundResource(R.drawable.bg_round);
            aVar2.textView.setTextColor(Color.parseColor("#0f9d58"));
        }
        return inflate;
    }
}
