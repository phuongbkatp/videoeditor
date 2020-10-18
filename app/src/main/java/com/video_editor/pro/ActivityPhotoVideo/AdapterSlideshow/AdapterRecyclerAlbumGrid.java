package com.video_editor.pro.ActivityPhotoVideo.AdapterSlideshow;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;

import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.video_editor.pro.R;
import com.video_editor.pro.ActivityPhotoVideo.ModelSlideshow.Album;
import com.video_editor.pro.ActivityPhotoVideo.ModelSlideshow.SelectBucketImage;
import com.video_editor.pro.ActivityPhotoVideo.UtilsSlideshow.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

public class AdapterRecyclerAlbumGrid extends Adapter<AdapterRecyclerAlbumGrid.holder> {
    public static int count;
    int a = 0;
    Context context;
    ImageLoader imageLoader;
    int anInt;
    private LayoutInflater inflater;

    class holder extends ViewHolder {
        ImageView imageView;
        ImageView imageView1;

        public holder(View view) {
            super(view);
            this.imageView1 = view.findViewById(R.id.ivThumbImg);
            this.imageView = view.findViewById(R.id.ivImgSelection);
            this.imageView1.setLayoutParams(new LayoutParams(-1, Utils.width / 3));
            this.imageView.setLayoutParams(new LayoutParams(-1, Utils.width / 3));
        }
    }

    @SuppressLint({"WrongConstant"})
    public AdapterRecyclerAlbumGrid(Context context, int i, ImageLoader imageLoader) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        count = 0;
        this.a = i;
        this.imageLoader = imageLoader;
        this.context = context;
        this.anInt = Utils.width / 3;
    }

    public int getItemCount() {
        try {
            return Utils.imageUri.get(this.a).imgUri.size();
        } catch (Exception unused) {
            return 0;
        }
    }

    public void onBindViewHolder(final holder aVar, final int i) {
        aVar.imageView.setImageResource(R.drawable.album_gridimage_frame);
        String uri = Utils.imageUri.get(this.a).imgUri.get(i).getImgUri().toString();
        int size = Utils.myUri.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (Utils.myUri.get(i2).equals(getRealPathFromURI(Utils.imageUri.get(this.a).imgUri.get(i).getImgUri()))) {
                count++;
                aVar.imageView.setImageResource(R.drawable.select_image2);
            }
        }
        Picasso.with(this.context).load(uri).resize(this.anInt, this.anInt).into(aVar.imageView1);
        aVar.imageView1.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                if (Utils.imageUri.get(AdapterRecyclerAlbumGrid.this.a).imgUri.get(i).getImgPos() >= 0) {
                    aVar.imageView.setImageResource(R.drawable.album_gridimage_frame);
                    int size = Utils.myUri.size();
                    for (int i = 0; i < size; i++) {
                        if (Utils.myUri.get(i).equals(AdapterRecyclerAlbumGrid.this.getRealPathFromURI(Utils.imageUri.get(AdapterRecyclerAlbumGrid.this.a).imgUri.get(i).getImgUri()))) {
                            Utils.myUri.remove(i);
                            size--;
                        }
                    }
                    Utils.imageUri.get(AdapterRecyclerAlbumGrid.this.a).imgUri.get(i).setImgPos(-1);
                    AdapterRecyclerAlbumGrid.count--;
                    return;
                }
                int size2 = Utils.myUri.size();
                boolean z = false;
                for (int i2 = 0; i2 < size2; i2++) {
                    if (Utils.myUri.get(i2).equals(AdapterRecyclerAlbumGrid.this.getRealPathFromURI(Utils.imageUri.get(AdapterRecyclerAlbumGrid.this.a).imgUri.get(i).getImgUri()))) {
                        Utils.myUri.remove(i2);
                        aVar.imageView.setImageResource(R.drawable.album_gridimage_frame);
                        size2--;
                        z = true;
                    }
                }
                if (!z) {
                    AdapterRecyclerAlbumGrid.count = 0;
                    Utils.myUri.add(AdapterRecyclerAlbumGrid.this.getRealPathFromURI(Utils.imageUri.get(AdapterRecyclerAlbumGrid.this.a).imgUri.get(i).getImgUri()));
                    aVar.imageView.setImageResource(R.drawable.select_image2);
                    AdapterRecyclerAlbumGrid.count++;
                }
            }
        });
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor query = this.context.getContentResolver().query(uri, new String[]{"_data"}, null, null, null);
        String string = query.moveToFirst() ? query.getString(query.getColumnIndexOrThrow("_data")) : null;
        query.close();
        return string;
    }

    public holder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new holder(this.inflater.inflate(R.layout.slideshow_row_grid, null));
    }
}
