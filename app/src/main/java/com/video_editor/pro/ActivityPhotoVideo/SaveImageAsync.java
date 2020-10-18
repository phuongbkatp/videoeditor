package com.video_editor.pro.ActivityPhotoVideo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;

import com.video_editor.pro.ActivityPhotoVideo.UtilsSlideshow.BitmapCompression;
import com.video_editor.pro.ActivityPhotoVideo.UtilsSlideshow.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Locale;

public class SaveImageAsync extends AsyncTask<Void, Void, Void> {
    Activity activity;
    boolean aBoolean;
    onUpdateAdapter onUpdateAdapter;

    public interface onUpdateAdapter {
        void onBitmapSaved();
    }

    public SaveImageAsync(Activity activity, boolean z) {
        this.activity = activity;
        this.aBoolean = z;
    }


    public void onPreExecute() {
        super.onPreExecute();
    }


    public Void doInBackground(Void... voidArr) {
        StringBuilder sb = new StringBuilder(Utils.getPath(this.activity));
        sb.append("/temp");
        String sb2 = sb.toString();
        for (int i = 0; i < Utils.createImageList.size(); i++) {
            String str = Utils.createImageList.get(i);
            Options options = new Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(str, options);
            options.inSampleSize = BitmapCompression.calculateInSampleSize(options, Utils.width, Utils.width);
            options.inJustDecodeBounds = false;
            Bitmap adjustImageOrientation = BitmapCompression.adjustImageOrientation(new File(str), BitmapFactory.decodeFile(str, options));
            if (this.aBoolean) {
                adjustImageOrientation = BitmapCompression.scaleCenterCrop(adjustImageOrientation, Utils.width, Utils.width);
            }
            try {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("slide_");
                sb3.append(String.format(Locale.US, "%05d", Integer.valueOf(i + 1)));
                sb3.append(".jpg");
                File file = new File(sb2, sb3.toString());
                adjustImageOrientation.compress(CompressFormat.JPEG, 70, new FileOutputStream(file));
                if (VERSION.SDK_INT >= 19) {
                    Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                    intent.setData(Uri.fromFile(file));
                    this.activity.sendBroadcast(intent);
                } else {
                    this.activity.sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.fromFile(file)));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public void onPostExecute(Void voidR) {
        ImageLoader.getInstance().clearDiskCache();
        ImageLoader.getInstance().clearMemoryCache();
        super.onPostExecute(voidR);
    }

    public void onUpdateListener(onUpdateAdapter onupdateadapter) {
        this.onUpdateAdapter = onupdateadapter;
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor query = this.activity.getContentResolver().query(uri, new String[]{"_data"}, null, null, null);
        String string = query.moveToFirst() ? query.getString(query.getColumnIndexOrThrow("_data")) : null;
        query.close();
        return string;
    }
}
