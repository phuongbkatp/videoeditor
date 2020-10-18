package com.video_editor.pro.UtilsAndAdapters.CollageStickers;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout.LayoutParams;

import com.video_editor.pro.ActivityVideoCollage.CollageUtils.CollageUtils;

class a implements OnTouchListener {
    int anInt;
    int anInt1;
    int anInt2;
    int anInt3;
    int anInt4;
    int anInt5;
    LayoutParams layoutParams;
    LayoutParams layoutParams1;
    Point point;
    LayoutParams layoutParams2;
    private View view;
    private View view1;

    a(View view, View view2) {
        this.view = view;
        this.view1 = view2;
    }

    @SuppressLint({"WrongConstant"})
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction() & 255;
        if (action == 0) {
            for (int i2 = 0; i2 < CollageUtils.clgstickerviewsList.size(); i2++) {
                try {
                    CollageUtils.clgstickerviewsList.get(i2).collageSingleFingerView.hidePushView();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            ClgTagView clgTagView = (ClgTagView) ((CollageSingleFingerView) view.getParent().getParent()).getTag();
            CollageUtils.selectedPos = clgTagView.getPos();
            CollageUtils.selectedText = clgTagView.getText();
            this.view.setVisibility(0);
            this.view1.setVisibility(0);
            if (this.layoutParams2 == null) {
                try {
                    this.layoutParams2 = (LayoutParams) view.getLayoutParams();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
            if (this.layoutParams1 == null) {
                try {
                    this.layoutParams1 = (LayoutParams) this.view.getLayoutParams();
                    this.layoutParams = (LayoutParams) this.view1.getLayoutParams();
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
            }
            this.point = a(motionEvent);
            this.anInt5 = this.layoutParams2.leftMargin;
            this.anInt4 = this.layoutParams2.topMargin;
            this.anInt3 = this.layoutParams1.leftMargin;
            this.anInt2 = this.layoutParams1.topMargin;
            this.anInt1 = this.layoutParams.leftMargin;
            this.anInt = this.layoutParams.topMargin;
        } else if (action == 2) {
            Point a2 = a(motionEvent);
            float f2 = a2.aFloat - this.point.aFloat;
            float f3 = a2.aFloat1 - this.point.aFloat1;
            this.layoutParams2.leftMargin = (int) (((float) this.anInt5) + f2);
            this.layoutParams2.topMargin = (int) (((float) this.anInt4) + f3);
            view.setLayoutParams(this.layoutParams2);
            this.layoutParams1.leftMargin = (int) (((float) this.anInt3) + f2);
            this.layoutParams1.topMargin = (int) (((float) this.anInt2) + f3);
            this.view.setLayoutParams(this.layoutParams1);
            this.layoutParams.leftMargin = (int) (((float) this.anInt1) + f2);
            this.layoutParams.topMargin = (int) (((float) this.anInt) + f3);
            this.view1.setLayoutParams(this.layoutParams);
        }
        return false;
    }

    private Point a(MotionEvent motionEvent) {
        return new Point((float) ((int) motionEvent.getRawX()), (float) ((int) motionEvent.getRawY()));
    }
}
