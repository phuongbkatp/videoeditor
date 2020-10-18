package com.video_editor.pro.ActivityVideoCollage;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import androidx.fragment.app.Fragment;

import com.video_editor.pro.R;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;

public class FragmentSelectCollage extends Fragment {
    AdapterSelectCollage adapterSelectCollage;
    GridView gridView;
    int[] ints = {R.drawable.frame_2, R.drawable.frame_3, R.drawable.frame_4, R.drawable.frame_5, R.drawable.frame_6, R.drawable.frame_7, R.drawable.frame_8, R.drawable.frame_9, R.drawable.frame_10, R.drawable.frame_11, R.drawable.frame_12};
    private AdView adView;

    @Override public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.select_video_collage_fragment, viewGroup, false);
        this.gridView = inflate.findViewById(R.id.grid);
        this.adapterSelectCollage = new AdapterSelectCollage(getActivity(), this.ints);
        this.gridView.setAdapter(this.adapterSelectCollage);
        this.gridView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                Intent intent = new Intent(FragmentSelectCollage.this.getActivity(), ActivityVideoCollageMaker.class);
                Bundle bundle = new Bundle();
                bundle.putInt("position", i + 1);
                intent.putExtras(bundle);
                FragmentSelectCollage.this.getActivity().startActivity(intent);
            }
        });
        this.adView = inflate.findViewById(R.id.banner_AdView);
        this.adView.loadAd(new Builder().build());
        if (isOnline()) {
            this.adView.setVisibility(0);
        } else {
            this.adView.setVisibility(8);
        }
        return inflate;
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
