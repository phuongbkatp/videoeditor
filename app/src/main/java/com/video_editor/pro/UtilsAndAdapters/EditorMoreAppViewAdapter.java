package com.video_editor.pro.UtilsAndAdapters;

import android.content.Context;
import android.net.ConnectivityManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.video_editor.pro.R;

import java.util.List;

public class EditorMoreAppViewAdapter extends Adapter<EditorMoreAppViewHolders> {
    private List<EditorMoreAppItemObject> editorMoreAppItemObjects;
    private Context context;

    public EditorMoreAppViewAdapter(Context context, List<EditorMoreAppItemObject> list) {
        this.editorMoreAppItemObjects = list;
        this.context = context;
    }

    public EditorMoreAppViewHolders onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new EditorMoreAppViewHolders(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.more_app_icon, null), this.editorMoreAppItemObjects);
    }

    public void onBindViewHolder(EditorMoreAppViewHolders moreAppViewHolders, int i) {
        moreAppViewHolders.textView.setText(this.editorMoreAppItemObjects.get(i).getName());
        moreAppViewHolders.imageView.setImageResource(this.editorMoreAppItemObjects.get(i).getPhoto());
    }

    public int getItemCount() {
        return this.editorMoreAppItemObjects.size();
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
