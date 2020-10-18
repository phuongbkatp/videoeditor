package com.video_editor.pro.ActivityPhotoVideo.TablayoutSlideshow;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout.LayoutParams;

import androidx.fragment.app.Fragment;

import com.video_editor.pro.R;
import com.video_editor.pro.ActivityPhotoVideo.ModelSlideshow.ImageSelection;
import com.video_editor.pro.ActivityPhotoVideo.UtilsSlideshow.Utils;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration.Builder;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

public class FragmentPhoto extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    static Context context;
    static int anInt;
    ImageLoader imageLoader;
    int anInt1;
    int anInt2;

    public Cursor cursor;

    public LayoutInflater inflater;

    private class a extends BaseAdapter {
        public long getItemId(int i) {
            return i;
        }

        public a(Context context) {
            a();
        }

        private void a() {
            ImageLoaderConfiguration build = new Builder(FragmentPhoto.this.getActivity()).memoryCache(new WeakMemoryCache()).defaultDisplayImageOptions(new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).imageScaleType(ImageScaleType.EXACTLY).displayer(new FadeInBitmapDisplayer(200)).build()).build();
            FragmentPhoto.this.imageLoader = ImageLoader.getInstance();
            FragmentPhoto.this.imageLoader.init(build);
        }

        public int getCount() {
            return FragmentPhoto.this.cursor.getCount();
        }

        public Object getItem(int i) {
            return Integer.valueOf(i);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            View inflate = FragmentPhoto.this.inflater.inflate(R.layout.slideshow_row_grid, viewGroup, false);
            ImageView imageView = inflate.findViewById(R.id.ivThumbImg);
            final ImageView imageView2 = inflate.findViewById(R.id.ivImgSelection);
            FragmentPhoto.this.cursor.moveToPosition(i);
            int columnIndex = FragmentPhoto.this.cursor.getColumnIndex("_data");
            int columnIndex2 = FragmentPhoto.this.cursor.getColumnIndex("_id");
            final String string = FragmentPhoto.this.cursor.getString(columnIndex);
            int size = Utils.myUri.size();
            for (int i2 = 0; i2 < size; i2++) {
                if (string.equals(Utils.myUri.get(i2))) {
                    imageView2.setImageResource(R.drawable.select_image2);
                }
            }
            final int i3 = FragmentPhoto.this.cursor.getInt(columnIndex2);
            imageView2.setScaleType(ScaleType.FIT_XY);
            imageView.setScaleType(ScaleType.FIT_XY);
            imageView.setLayoutParams(new LayoutParams(FragmentPhoto.anInt, FragmentPhoto.anInt));
            imageView2.setLayoutParams(new LayoutParams(FragmentPhoto.anInt, FragmentPhoto.anInt));
            ImageLoader.getInstance().displayImage(Uri.withAppendedPath(Media.EXTERNAL_CONTENT_URI, String.valueOf(i3)).toString(), new ImageViewAware(imageView));
            imageView.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View view) {
                    int size = Utils.myUri.size();
                    boolean z = false;
                    for (int i = 0; i < size; i++) {
                        if (string.equals(Utils.myUri.get(i))) {
                            Utils.myUri.remove(i);
                            imageView2.setImageResource(R.drawable.album_gridimage_frame);
                            size--;
                            z = true;
                        }
                    }
                    if (!z) {
                        Utils.myUri.add(string);
                        ImageSelection imageSelection = new ImageSelection();
                        imageSelection.imgId = i3;
                        imageSelection.imgUri = string;
                        imageSelection.cropIndex = -1;
                        Utils.selectImageList.add(imageSelection);
                        imageView2.setImageResource(R.drawable.select_image2);
                    }
                }
            });
            return inflate;
        }
    }

    public static Fragment newInstance(int i, Context context) {
        Bundle bundle = new Bundle();
        FragmentPhoto.context = context;
        bundle.putInt(ARG_PAGE, i);
        FragmentPhoto fragmentPhoto = new FragmentPhoto();
        fragmentPhoto.setArguments(bundle);
        anInt = (FragmentPhoto.context.getResources().getDisplayMetrics().widthPixels - ((int) TypedValue.applyDimension(1, 8.0f, FragmentPhoto.context.getResources().getDisplayMetrics()))) / 3;
        return fragmentPhoto;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.e("", "on attch");
    }

    @Override public void onResume() {
        super.onResume();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.inflater = layoutInflater;
        View inflate = layoutInflater.inflate(R.layout.slideshow_tab_photo, viewGroup, false);
        this.cursor = getActivity().getContentResolver().query(Media.EXTERNAL_CONTENT_URI, new String[]{"_id", "_id", "_data", "bucket_id"}, null, null, "datetaken DESC");
        this.anInt2 = this.cursor.getColumnIndexOrThrow("_id");
        this.anInt1 = this.cursor.getColumnIndexOrThrow("_data");
        ((GridView) inflate.findViewById(R.id.gridView1)).setAdapter(new a(getActivity()));
        return inflate;
    }
}
