package com.video_editor.pro.UtilsAndAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class EditorCustomTextView extends TextView {
    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

    public EditorCustomTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context, attributeSet);
    }

    public EditorCustomTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context, attributeSet);
    }

    private void a(Context context, AttributeSet attributeSet) {
        setTypeface(a(context, attributeSet.getAttributeIntValue("http://schemas.android.com/apk/res/android", "textStyle", 0)));
    }

    private Typeface a(Context context, int i) {
        switch (i) {
            case 0:
                return EditorFontCache.getTypeface(EditorHelper.FontStyle, context);
            case 1:
                return EditorFontCache.getTypeface(EditorHelper.FontStyle, context);
            default:
                return EditorFontCache.getTypeface(EditorHelper.FontStyle, context);
        }
    }
}
