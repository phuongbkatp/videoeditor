package com.video_editor.pro.ActivityPhotoVideo;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.video_editor.pro.R;
import com.video_editor.pro.ActivityPhotoVideo.TablayoutSlideshow.HomeTab;

public class FragmentSelectImage extends Fragment {
    RelativeLayout relativeLayout;

    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.slideshow_select_image_fragment, viewGroup, false);
        this.relativeLayout = inflate.findViewById(R.id.A12);
        this.relativeLayout.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                FragmentSelectImage.this.startActivityForResult(new Intent(FragmentSelectImage.this.getActivity(), HomeTab.class), 99);
            }
        });
        return inflate;
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
