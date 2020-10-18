package com.video_editor.pro.UtilsAndAdapters;

import android.content.res.Resources;

public class EditorFileUtils {
    public static int Title_index = 0;
    public static String appFontTitle = "AVENIRLTSTD-MEDIUM.OTF";

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
    public static String strArr(String[] string) {
        String[] s = string;
        String os = "";

        for (int i = 0; i < s.length; i++) {
            os = os + s[i] + " ";
        }
        return os;
    }
}
