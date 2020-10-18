package com.video_editor.pro.ActivityPhotoVideo.UtilsSlideshow;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Environment;

import com.video_editor.pro.R;
import com.video_editor.pro.ActivityPhotoVideo.ModelSlideshow.ImageSelection;
import com.video_editor.pro.ActivityPhotoVideo.ModelSlideshow.SelectBucketImage;
import com.video_editor.pro.UtilsAndAdapters.CollageStickers.StickerData;

import java.util.ArrayList;

public class Utils {
    public static int audioDuration = 0;
    public static String audioName = "";
    public static int audioSelected = -1;
    public static Bitmap bitmap = null;
    public static ArrayList<StickerData> clgstickerviewsList = new ArrayList<>();
    public static ArrayList<String> createImageList = new ArrayList<>();
    public static int filterIndex = -1;
    public static int framePostion = -1;
    public static int height = 0;
    public static ArrayList<SelectBucketImage> imageUri = new ArrayList<>();
    public static int imgCount = 0;
    public static boolean isFromOnlineFrame = false;
    public static ArrayList<String> myUri = new ArrayList<>();
    public static String onlineFramePath = null;
    public static int pos = -1;
    public static ArrayList<ImageSelection> selectImageList = new ArrayList<>();
    public static ArrayList<String> selectedImagesUri = new ArrayList<>();
    public static int width;

    public static String getPath(Context context) {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsoluteFile());
        sb.append("/");
        sb.append(context.getResources().getString(R.string.MainFolderName));
        sb.append("/");
        sb.append(context.getResources().getString(R.string.PhotoToVideo));
        return new StringBuilder(sb.toString()).toString();
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
}
