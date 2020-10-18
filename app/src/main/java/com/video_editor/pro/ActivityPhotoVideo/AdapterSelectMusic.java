package com.video_editor.pro.ActivityPhotoVideo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.video_editor.pro.R;
import com.video_editor.pro.ActivityPhotoVideo.ModelSlideshow.MusicModel;
import com.video_editor.pro.ActivityPhotoVideo.UtilsSlideshow.Utils;
import java.util.ArrayList;

public class AdapterSelectMusic extends Adapter<AdapterSelectMusic.holder> {
    ArrayList<MusicModel> musicModels;
    private LayoutInflater inflater;

    class holder extends ViewHolder {
        LinearLayout linearLayout;
        ToggleButton toggleButton;
        TextView textView;

        public holder(View view) {
            super(view);
            this.textView = view.findViewById(R.id.row_title);
            this.toggleButton = view.findViewById(R.id.toggleButton1);
            this.linearLayout = view.findViewById(R.id.musicPanel);
        }
    }

    @SuppressLint({"WrongConstant"})
    public AdapterSelectMusic(Context context, ArrayList<MusicModel> arrayList) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.musicModels = arrayList;
    }

    public int getItemCount() {
        return this.musicModels.size();
    }

    public void onBindViewHolder(final holder aVar, final int i) {
        aVar.textView.setText(this.musicModels.get(i).name);
        LayoutParams layoutParams = aVar.linearLayout.getLayoutParams();
        layoutParams.width = Utils.width;
        layoutParams.height = 80;
        aVar.linearLayout.setLayoutParams(layoutParams);
        if (Utils.audioSelected == i) {
            aVar.toggleButton.setChecked(true);
        } else {
            aVar.toggleButton.setChecked(false);
        }
        aVar.itemView.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                aVar.toggleButton.setChecked(true);
                Utils.audioSelected = i;
                Utils.audioName = AdapterSelectMusic.this.musicModels.get(i).name;
                ((ActivityMovieMaker) ActivityMovieMaker.mContext).hideMusicList(AdapterSelectMusic.this.musicModels.get(i).path);
            }
        });
    }

    public holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = this.inflater.inflate(R.layout.slideshow_row_select_music, null);
        inflate.setLayoutParams(new LayoutParams(Utils.width, -2));
        return new holder(inflate);
    }
}
