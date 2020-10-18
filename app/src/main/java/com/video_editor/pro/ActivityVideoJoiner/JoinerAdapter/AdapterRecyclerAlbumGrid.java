package com.video_editor.pro.ActivityVideoJoiner.JoinerAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.video_editor.pro.R;
import com.video_editor.pro.ActivityVideoJoiner.JoinerModels.Album;
import com.video_editor.pro.ActivityVideoJoiner.JoinerModels.SelectBucketImage;
import com.video_editor.pro.ActivityVideoJoiner.util.FileUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

@SuppressLint({"WrongConstant"})
public class AdapterRecyclerAlbumGrid extends BaseAdapter {
    public static int count;
    int anInt = 0;
    Context context;
    ImageLoader imageLoader;
    int anInt1;
    private LayoutInflater inflater;

    public class Holder {
        ImageView imageView;
        ImageView imageView1;

        public Holder() {
        }
    }

    public long getItemId(int i) {
        return i;
    }

    public AdapterRecyclerAlbumGrid(Context context, int i, ImageLoader imageLoader) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        count = 0;
        this.anInt = i;
        this.imageLoader = imageLoader;
        this.context = context;
        this.anInt1 = FileUtils.width / 3;
    }

    public int getCount() {
        try {
            return FileUtils.imageUri.get(this.anInt).imgUri.size();
        } catch (Exception unused) {
            return 0;
        }
    }

    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    public View getView(final int i, View view, ViewGroup viewGroup) {
        final Holder holder = new Holder();
        View inflate = this.inflater.inflate(R.layout.slideshow_row_grid, null);
        holder.imageView1 = inflate.findViewById(R.id.ivThumbImg);
        holder.imageView = inflate.findViewById(R.id.ivImgSelection);
        holder.imageView1.setLayoutParams(new LayoutParams(-1, FileUtils.width / 3));
        holder.imageView.setImageResource(R.drawable.album_gridimage_frame);
        String uri = FileUtils.imageUri.get(this.anInt).imgUri.get(i).getImgUri().toString();
        int size = FileUtils.myUri.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (FileUtils.myUri.get(i2).equals(getRealPathFromURI(FileUtils.imageUri.get(this.anInt).imgUri.get(i).getImgUri()))) {
                count++;
                holder.imageView.setImageResource(R.drawable.album_gridimage_frameselect);
            }
        }
        Picasso.with(this.context).load(uri).resize(this.anInt1, this.anInt1).into(holder.imageView1);
        holder.imageView1.setOnClickListener(new OnClickListener() {
            @Override public void onClick(View view) {
                Log.v("size", String.valueOf(FileUtils.myUri.size()));
                if (FileUtils.imageUri.get(AdapterRecyclerAlbumGrid.this.anInt).imgUri.get(i).getImgPos() >= 0) {
                    holder.imageView.setImageResource(R.drawable.album_gridimage_frame);
                    int size = FileUtils.myUri.size();
                    for (int i = 0; i < size; i++) {
                        if (FileUtils.myUri.get(i).equals(AdapterRecyclerAlbumGrid.this.getRealPathFromURI(FileUtils.imageUri.get(AdapterRecyclerAlbumGrid.this.anInt).imgUri.get(i).getImgUri()))) {
                            FileUtils.myUri.remove(i);
                            size--;
                        }
                    }
                    FileUtils.imageUri.get(AdapterRecyclerAlbumGrid.this.anInt).imgUri.get(i).setImgPos(-1);
                    AdapterRecyclerAlbumGrid.count--;
                    return;
                }
                int size2 = FileUtils.myUri.size();
                boolean z = false;
                for (int i2 = 0; i2 < size2; i2++) {
                    if (FileUtils.myUri.get(i2).equals(AdapterRecyclerAlbumGrid.this.getRealPathFromURI(FileUtils.imageUri.get(AdapterRecyclerAlbumGrid.this.anInt).imgUri.get(i).getImgUri()))) {
                        FileUtils.myUri.remove(i2);
                        holder.imageView.setImageResource(R.drawable.album_gridimage_frame);
                        size2--;
                        z = true;
                    }
                }
                if (!z) {
                    if (FileUtils.myUri.size() < 2) {
                        FileUtils.myUri.add(AdapterRecyclerAlbumGrid.this.getRealPathFromURI(FileUtils.imageUri.get(AdapterRecyclerAlbumGrid.this.anInt).imgUri.get(i).getImgUri()));
                        holder.imageView.setImageResource(R.drawable.album_gridimage_frameselect);
                        AdapterRecyclerAlbumGrid.count++;
                        return;
                    }
                    Toast.makeText(AdapterRecyclerAlbumGrid.this.context, "Select Maximum two Videos", 0).show();
                }
            }
        });
        return inflate;
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor query = this.context.getContentResolver().query(uri, new String[]{"_data"}, null, null, null);
        String string = query.moveToFirst() ? query.getString(query.getColumnIndexOrThrow("_data")) : null;
        query.close();
        return string;
    }
}
