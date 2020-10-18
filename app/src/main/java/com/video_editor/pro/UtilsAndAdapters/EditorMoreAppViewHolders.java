package com.video_editor.pro.UtilsAndAdapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.video_editor.pro.R;

import java.util.List;

public class EditorMoreAppViewHolders extends ViewHolder implements OnClickListener {
    public static Context context;
    public TextView textView;
    public ImageView imageView;
    List<EditorMoreAppItemObject> editorMoreAppItemObjects;

    public EditorMoreAppViewHolders(View view, List<EditorMoreAppItemObject> list) {
        super(view);
        view.setOnClickListener(this);
        context = view.getContext();
        this.editorMoreAppItemObjects = list;
        this.textView = view.findViewById(R.id.country_name);
        this.imageView = view.findViewById(R.id.imageView1);
    }

    @Override public void onClick(View view) {
        try {
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(this.editorMoreAppItemObjects.get(getPosition()).getLink())));
        } catch (ActivityNotFoundException unused) {
            Toast.makeText(context, " unable to find market app", Toast.LENGTH_LONG).show();
        }
    }
}
