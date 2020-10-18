package com.video_editor.pro.UtilsAndAdapters;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;

public class EditorFontCache {
    private static HashMap<String, Typeface> a = new HashMap<>();

    public static Typeface getTypeface(String str, Context context) {
        Typeface typeface = a.get(str);
        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), str);
                a.put(str, typeface);
            } catch (Exception unused) {
                return null;
            }
        }
        return typeface;
    }
}
