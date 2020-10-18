package com.video_editor.pro.ActivityPhotoVideo.TablayoutSlideshow;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

class a extends LinearLayout {
    private final Paint paint;
    private final int anInt;
    private SlidingTabLayout.tabColorizer tabColorizer;
    private final int anInt1;
    private final C0033a c0033a;
    private final float aFloat;
    private final Paint paint1;
    private final Paint paint2;
    private final int anInt2;
    private int anInt3;
    private float aFloat1;

    private static class C0033a implements SlidingTabLayout.tabColorizer {
        private int[] ints;
        private int[] ints1;

        private C0033a() {
        }

        public final int getIndicatorColor(int i) {
            return this.ints1[i % this.ints1.length];
        }

        public final int getDividerColor(int i) {
            return this.ints[i % this.ints.length];
        }


        public void a(int... iArr) {
            this.ints1 = iArr;
        }


        public void b(int... iArr) {
            this.ints = iArr;
        }
    }

    a(Context context) {
        this(context, null);
    }

    a(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setWillNotDraw(false);
        float f2 = getResources().getDisplayMetrics().density;
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(16842800, typedValue, true);
        this.anInt1 = as(typedValue.data, (byte) 38);
        this.c0033a = new C0033a();
        this.c0033a.a(-13388315);
        this.c0033a.b(32);
        int i2 = (int) (2.0f * f2);
        this.anInt = i2;
        this.paint = new Paint();
        this.paint.setColor(this.anInt1);
        this.anInt2 = i2;
        this.paint2 = new Paint();
        this.aFloat = 0.0f;
        this.paint1 = new Paint();
        this.paint1.setStrokeWidth((float) ((int) (0.0f * f2)));
    }


    public void a(SlidingTabLayout.tabColorizer tabColorizer) {
        this.tabColorizer = tabColorizer;
        invalidate();
    }


    public void a(int i2, float f2) {
        this.anInt3 = i2;
        this.aFloat1 = f2;
        invalidate();
    }

    public void a(int i2) {
        int i3 = 0;
        while (i3 < getChildCount()) {
            if (getChildAt(i3) instanceof TextView) {
                ((TextView) getChildAt(i3)).setTextColor(Color.parseColor(i2 == i3 ? "#FFFFFF" : "#88FFFFFF"));
            }
            i3++;
        }
    }


    public void onDraw(Canvas canvas) {
        SlidingTabLayout.tabColorizer tabColorizer = this.tabColorizer;
        int height = getHeight();
        int childCount = getChildCount();
        float f2 = (float) height;
        int min = (int) (Math.min(Math.max(0.0f, this.aFloat), 1.0f) * f2);
        if (this.tabColorizer != null) {
            tabColorizer = this.tabColorizer;
        } else {
            C0033a aVar = this.c0033a;
        }
        if (childCount > 0) {
            View childAt = getChildAt(this.anInt3);
            int left = childAt.getLeft();
            int right = childAt.getRight();
            int indicatorColor = tabColorizer.getIndicatorColor(this.anInt3);
            if (this.aFloat1 > 0.0f && this.anInt3 < getChildCount() - 1) {
                int indicatorColor2 = tabColorizer.getIndicatorColor(this.anInt3 + 1);
                if (indicatorColor != indicatorColor2) {
                    indicatorColor = a(indicatorColor2, indicatorColor, this.aFloat1);
                }
                View childAt2 = getChildAt(this.anInt3 + 1);
                left = (int) ((this.aFloat1 * ((float) childAt2.getLeft())) + ((1.0f - this.aFloat1) * ((float) left)));
                right = (int) ((this.aFloat1 * ((float) childAt2.getRight())) + ((1.0f - this.aFloat1) * ((float) right)));
            }
            this.paint2.setColor(indicatorColor);
            canvas.drawRect((float) left, (float) (height - this.anInt2), (float) right, f2, this.paint2);
        }
        canvas.drawRect(0.0f, (float) (height - this.anInt), (float) getWidth(), f2, this.paint);
        int i2 = (height - min) / 2;
        for (int i3 = 0; i3 < childCount - 1; i3++) {
            View childAt3 = getChildAt(i3);
            this.paint1.setColor(tabColorizer.getDividerColor(i3));
            canvas.drawLine((float) childAt3.getRight(), (float) i2, (float) childAt3.getRight(), (float) (i2 + min), this.paint1);
        }
    }

    private static int as(int i2, byte b2) {
        int parseColor = Color.parseColor("#f4f3f3");
        return Color.argb(0, Color.red(parseColor), Color.green(parseColor), Color.blue(parseColor));
    }

    private static int a(int i2, int i3, float f2) {
        float f3 = 1.0f - f2;
        return Color.rgb((int) ((((float) Color.red(i2)) * f2) + (((float) Color.red(i3)) * f3)), (int) ((((float) Color.green(i2)) * f2) + (((float) Color.green(i3)) * f3)), (int) ((((float) Color.blue(i2)) * f2) + (((float) Color.blue(i3)) * f3)));
    }
}
