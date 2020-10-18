package com.video_editor.pro.ActivityVideoCollage;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.video_editor.pro.R;

public class AdapterSelectCollage extends BaseAdapter {
    int anInt;
    private final int[] ints;
    private Context context;

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return 0;
    }

    public AdapterSelectCollage(Context context, int[] iArr) {
        this.context = context;
        this.ints = iArr;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((ActivityCollageAlbumList) this.context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.anInt = displayMetrics.widthPixels;
    }

    public int getCount() {
        return this.ints.length;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view != null) {
            return view;
        }
        new View(this.context);
        View inflate = layoutInflater.inflate(R.layout.row_frame_select, null);
        ImageView imageView = inflate.findViewById(R.id.grid_image);
        LinearLayout linearLayout = inflate.findViewById(R.id.lnr_main);
        linearLayout.getLayoutParams().width = this.anInt / 3;
        linearLayout.getLayoutParams().height = this.anInt / 3;
        imageView.setImageResource(this.ints[i]);
        return inflate;
    }
}
