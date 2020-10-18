package com.video_editor.pro.UtilsAndAdapters.CollageStickers;

import android.annotation.TargetApi;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout.LayoutParams;

@TargetApi(11)
class b implements OnTouchListener {
    double aDouble;
    int anInt;
    int anInt1;
    int anInt2;
    int anInt3;
    int anInt4;
    int anInt5;
    int anInt6;
    int anInt7;
    int anInt8;
    int anInt9;
    int anInt10;
    float aFloat = -1.0f;
    float aFloat1 = -1.0f;
    Point point;
    private LayoutParams layoutParams;
    private View view;
    private Point point1;
    private LayoutParams layoutParams1;
    private LayoutParams layoutParams2;

    public b(View view) {
        this.view = view;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction() & 255;
        if (action == 0) {
            this.layoutParams2 = (LayoutParams) view.getLayoutParams();
            this.layoutParams = (LayoutParams) this.view.getLayoutParams();
            this.layoutParams1 = (LayoutParams) ((CollageSingleFingerView) view.getParent().getParent()).mPushDelete.getLayoutParams();
            this.point = a(this.layoutParams2, motionEvent);
            this.anInt6 = this.layoutParams.width;
            this.anInt9 = this.layoutParams.height;
            this.anInt8 = this.layoutParams.leftMargin;
            this.anInt7 = this.layoutParams.topMargin;
            this.anInt10 = (int) this.view.getRotation();
            this.anInt5 = this.layoutParams2.leftMargin;
            this.anInt4 = this.layoutParams2.topMargin;
            this.anInt3 = this.layoutParams1.leftMargin;
            this.anInt2 = this.layoutParams1.topMargin;
            this.anInt = this.layoutParams2.width;
            this.anInt1 = this.layoutParams2.height;
            this.aFloat = motionEvent.getRawX();
            this.aFloat1 = motionEvent.getRawY();
            a();
        } else if (action == 2) {
            float rawX = motionEvent.getRawX();
            float rawY = motionEvent.getRawY();
            if (this.aFloat == -1.0f || Math.abs(rawX - this.aFloat) >= 5.0f || Math.abs(rawY - this.aFloat1) >= 5.0f) {
                try {
                    this.aFloat = rawX;
                    this.aFloat1 = rawY;
                    Point point = this.point1;
                    Point point2 = this.point;
                    Point a2 = a(this.layoutParams2, motionEvent);
                    float a3 = a(point, point2);
                    float a4 = a(point, a2);
                    float f2 = a4 / a3;
                    int i2 = (int) (((float) this.anInt6) * f2);
                    int i3 = (int) (((float) this.anInt9) * f2);
                    this.layoutParams.leftMargin = this.anInt8 - ((i2 - this.anInt6) / 2);
                    this.layoutParams.topMargin = this.anInt7 - ((i3 - this.anInt9) / 2);
                    this.layoutParams.width = i2;
                    this.layoutParams.height = i3;
                    this.view.setLayoutParams(this.layoutParams);
                    double acos = (180.0d * Math.acos((((point2.aFloat - point.aFloat) * (a2.aFloat - point.aFloat)) + ((point2.aFloat1 - point.aFloat1) * (a2.aFloat1 - point.aFloat1))) / (a3 * a4))) / 3.14159265359d;
                    if (Double.isNaN(acos)) {
                        try {
                            acos = (this.aDouble < 90.0d || this.aDouble > 270.0d) ? 0 : 180;
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    } else if ((a2.aFloat1 - point.aFloat1) * (point2.aFloat - point.aFloat) < (point2.aFloat1 - point.aFloat1) * (a2.aFloat - point.aFloat)) {
                        acos = 360.0d - acos;
                    }
                    this.aDouble = acos;
                    float f3 = ((float) (((double) this.anInt10) + acos)) % 360.0f;
                    this.view.setRotation(f3);
                    Point a5 = a(point, new Point((float) (this.view.getLeft() + this.view.getWidth()), (float) (this.view.getTop() + this.view.getHeight())), f3);
                    this.layoutParams2.leftMargin = (int) (a5.aFloat - ((float) (this.anInt / 2)));
                    this.layoutParams2.topMargin = (int) (a5.aFloat1 - ((float) (this.anInt1 / 2)));
                    this.layoutParams1.leftMargin = this.view.getRight();
                    this.layoutParams1.topMargin = this.view.getTop();
                    view.setLayoutParams(this.layoutParams2);
                    ((CollageSingleFingerView) view.getParent().getParent()).mPushDelete.setLayoutParams(this.layoutParams1);
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
            return false;
        }
        return false;
    }

    private void a() {
        this.point1 = new Point((float) (this.view.getLeft() + (this.view.getWidth() / 2)), (float) (this.view.getTop() + (this.view.getHeight() / 2)));
    }

    private Point a(LayoutParams layoutParams, MotionEvent motionEvent) {
        return new Point((float) (layoutParams.leftMargin + ((int) motionEvent.getX())), (float) (layoutParams.topMargin + ((int) motionEvent.getY())));
    }

    private float a(Point point, Point point2) {
        return ((float) ((int) (Math.sqrt(((point.aFloat - point2.aFloat) * (point.aFloat - point2.aFloat)) + ((point.aFloat1 - point2.aFloat1) * (point.aFloat1 - point2.aFloat1))) * 100.0d))) / 100.0f;
    }

    private Point a(Point point, Point point2, float f2) {
        float a2 = a(point, point2);
        double d2 = (((double) f2) * 3.14159265359d) / 180.0d;
        double d3 = a2;
        return new Point((float) ((int) (((double) point.aFloat) + (Math.cos(Math.acos((point2.aFloat - point.aFloat) / a2) + d2) * d3))), (float) ((int) (((double) point.aFloat1) + (d3 * Math.sin(d2 + Math.acos((point2.aFloat - point.aFloat) / a2))))));
    }
}
