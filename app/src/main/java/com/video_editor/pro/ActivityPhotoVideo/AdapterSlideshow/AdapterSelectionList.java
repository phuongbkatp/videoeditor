package com.video_editor.pro.ActivityPhotoVideo.AdapterSlideshow;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap.Config;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.video_editor.pro.R;
import com.video_editor.pro.ActivityPhotoVideo.ActivitySelectionList;
import com.video_editor.pro.ActivityPhotoVideo.UtilsSlideshow.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.askerov.dynamicgrid.BaseDynamicGridAdapter;

import java.io.File;
import java.util.List;

@SuppressLint({"ResourceType"})
public class AdapterSelectionList extends BaseDynamicGridAdapter {
    Context context;
    int anInt = 180;
    DisplayImageOptions build = new Builder().cacheInMemory(true).cacheOnDisk(true).imageScaleType(ImageScaleType.EXACTLY_STRETCHED).cacheInMemory(true).cacheOnDisk(true).bitmapConfig(Config.RGB_565).considerExifParams(true).build();
    ScaleType d = ScaleType.CENTER_INSIDE;
    public ImageLoader imageLoader = ImageLoader.getInstance();

    private class a {
        ImageView imageView;
        ImageView imageView1;
        RelativeLayout relativeLayout;

        private a(View view) {
            this.imageView1 = view.findViewById(R.id.ivThumbImg);
            this.relativeLayout = view.findViewById(R.id.relative);
            this.imageView = view.findViewById(R.id.btnDelete);
        }


        public void a(String str, final int i) {
            StringBuilder sb = new StringBuilder();
            sb.append("file://");
            sb.append(str);
            Uri parse = Uri.parse(sb.toString());
            this.imageView1.setScaleType(AdapterSelectionList.this.d);
            try {
                int i2 = Utils.width;
                this.imageView1.setLayoutParams(new LayoutParams(i2 / 2, i2 / 2));
                AdapterSelectionList.this.imageLoader.displayImage(parse.toString(), this.imageView1, AdapterSelectionList.this.build);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.imageView.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View view) {
                    new AlertDialog.Builder(AdapterSelectionList.this.context).setTitle("Are you sure to delete image ?").setPositiveButton("delete", new DialogInterface.OnClickListener() {
                        @Override public void onClick(DialogInterface dialogInterface, int ir) {
                            AdapterSelectionList.this.Delete(Utils.createImageList.get(i));
                            Utils.createImageList.remove(i);
                            AdapterSelectionList.this.getItems().remove(i);
                            Utils.myUri.remove(i);
                            AdapterSelectionList.this.notifyDataSetChanged();
                            Toast.makeText(AdapterSelectionList.this.context, "delete Successfully", 0).show();
                            dialogInterface.dismiss();
                            Intent intent = new Intent(AdapterSelectionList.this.context, ActivitySelectionList.class);
                            intent.addFlags(335544320);
                            AdapterSelectionList.this.context.startActivity(intent);
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).setCancelable(true).show();
                }
            });
        }
    }

    public AdapterSelectionList(Context context, List<?> list, int i) {
        super(context, list, i);
        this.context = context;
    }

    public void setScaleType(ScaleType scaleType) {
        this.d = scaleType;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        a aVar;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.slideshow_row_select_image_list, null);
            aVar = new a(view);
            view.setTag(aVar);
        } else {
            aVar = (a) view.getTag();
        }
        aVar.a(getItem(i).toString(), i);
        return view;
    }

    public void refreshList() {
        notifyDataSetChanged();
    }


    public void Delete(String str) {
        File file = new File(str);
        if (file.exists()) {
            file.delete();
            StringBuilder sb = new StringBuilder();
            sb.append("Deleted :");
            sb.append(file);
            Log.i("DELETE", sb.toString());
        }
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor query = this.context.getContentResolver().query(uri, new String[]{"_data"}, null, null, null);
        String string = query.moveToFirst() ? query.getString(query.getColumnIndexOrThrow("_data")) : null;
        query.close();
        return string;
    }
}
