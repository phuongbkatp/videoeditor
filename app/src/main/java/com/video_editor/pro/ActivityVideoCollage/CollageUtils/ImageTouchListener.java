package com.video_editor.pro.ActivityVideoCollage.CollageUtils;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class ImageTouchListener implements OnTouchListener {
    private float aFloat = 0.0f;
    private float[] floats = null;
    private Matrix matrix = new Matrix();
    private PointF pointF = new PointF();
    private int anInt = 0;
    private float aFloat1 = 0.0f;
    private float aFloat2 = 1.0f;
    private Matrix matrix1 = new Matrix();
    private PointF pointF1 = new PointF();

    public ImageTouchListener(Context context) {
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        ImageView imageView = (ImageView) view;
        switch (motionEvent.getAction() & 255) {
            case 0:
                this.matrix1.set(this.matrix);
                this.pointF1.set(motionEvent.getX(), motionEvent.getY());
                this.anInt = 1;
                this.floats = null;
                break;
            case 1:
            case 6:
                this.anInt = 0;
                this.floats = null;
                break;
            case 2:
                if (this.anInt != 1) {
                    try {
                        if (this.anInt == 2) {
                            try {
                                float a2 = a(motionEvent);
                                if (a2 > 10.0f) {
                                    try {
                                        this.matrix.set(this.matrix1);
                                        float f2 = a2 / this.aFloat2;
                                        this.matrix.postScale(f2, f2, this.pointF.x, this.pointF.y);
                                    } catch (Exception e2) {
                                        e2.printStackTrace();
                                    }
                                }
                                if (this.floats != null && motionEvent.getPointerCount() == 2) {
                                    try {
                                        this.aFloat1 = b(motionEvent);
                                        float f3 = this.aFloat1 - this.aFloat;
                                        float[] fArr = new float[9];
                                        this.matrix.getValues(fArr);
                                        float f4 = fArr[2];
                                        float f5 = fArr[5];
                                        float f6 = fArr[0];
                                        this.matrix.postRotate(f3, f4 + (((float) (imageView.getWidth() / 2)) * f6), f5 + (((float) (imageView.getHeight() / 2)) * f6));
                                        break;
                                    } catch (Exception e3) {
                                        e3.printStackTrace();
                                    }
                                }
                            } catch (Exception e4) {
                                e4.printStackTrace();
                            }
                        }
                    } catch (Exception e5) {
                        e5.printStackTrace();
                    }
                }
                this.matrix.set(this.matrix1);
                this.matrix.postTranslate(motionEvent.getX() - this.pointF1.x, motionEvent.getY() - this.pointF1.y);
                break;
            case 5:
                this.aFloat2 = a(motionEvent);
                if (this.aFloat2 > 10.0f) {
                    try {
                        this.matrix1.set(this.matrix);
                        a(this.pointF, motionEvent);
                        this.anInt = 2;
                    } catch (Exception e6) {
                        e6.printStackTrace();
                    }
                }
                this.floats = new float[4];
                this.floats[0] = motionEvent.getX(0);
                this.floats[1] = motionEvent.getX(1);
                this.floats[2] = motionEvent.getY(0);
                this.floats[3] = motionEvent.getY(1);
                this.aFloat = b(motionEvent);
                break;
        }
        imageView.setImageMatrix(this.matrix);
        return true;
    }

    private float a(MotionEvent motionEvent) {
        float x = motionEvent.getX(0) - motionEvent.getX(1);
        float y = motionEvent.getY(0) - motionEvent.getY(1);
        return (float) Math.sqrt((x * x) + (y * y));
    }

    private void a(PointF pointF, MotionEvent motionEvent) {
        pointF.set((motionEvent.getX(0) + motionEvent.getX(1)) / 2.0f, (motionEvent.getY(0) + motionEvent.getY(1)) / 2.0f);
    }

    private float b(MotionEvent motionEvent) {
        return (float) Math.toDegrees(Math.atan2(motionEvent.getY(0) - motionEvent.getY(1), motionEvent.getX(0) - motionEvent.getX(1)));
    }
}
