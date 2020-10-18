package com.video_editor.pro.ActivityVideoCollage.CollageUtils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.video_editor.pro.ActivityVideoCollage.CollageModel.CollageBorderAttribute;

public class BorderFrameLayout extends FrameLayout {
    Paint paint;
    Paint paint1 = new Paint();
    int anInt;

    public BorderFrameLayout(Context context) {
        super(context);
        this.paint1.setColor(-7829368);
        this.paint1.setStyle(Style.STROKE);
        this.paint1.setStrokeWidth(10.0f);
        this.paint1.setAlpha(255);
        this.paint = new Paint();
        this.paint.setColor(-7829368);
        this.paint.setStyle(Style.STROKE);
        this.paint.setStrokeWidth(10.0f);
        this.paint.setAlpha(255);
    }

    public BorderFrameLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.paint1.setColor(-7829368);
        this.paint1.setStyle(Style.STROKE);
        this.paint1.setStrokeWidth(10.0f);
        this.paint1.setAlpha(255);
        this.paint = new Paint();
        this.paint.setColor(-7829368);
        this.paint.setStyle(Style.STROKE);
        this.paint.setStrokeWidth(10.0f);
        this.paint.setAlpha(255);
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int parseInt = Integer.parseInt(String.valueOf(getTag()));
        if (CollageUtils.borderParam.get(parseInt).getBorderTop()) {
            try {
                this.paint.setStrokeWidth((float) (this.anInt / 2));
                if (CollageUtils.borderParam.get(parseInt).getBorderLeft() && CollageUtils.borderParam.get(parseInt).getBorderRight()) {
                        canvas.drawLine((float) (this.anInt / 4), 1.0f, (float) (getWidth() - (this.anInt / 4)), 0.0f, this.paint);
                } else if (CollageUtils.borderParam.get(parseInt).getBorderLeft()) {
                        canvas.drawLine((float) (this.anInt / 4), 1.0f, (float) (getWidth() - (this.anInt / 2)), 0.0f, this.paint);
                } else if (CollageUtils.borderParam.get(parseInt).getBorderRight()) {
                        canvas.drawLine((float) (this.anInt / 2), 1.0f, (float) (getWidth() - (this.anInt / 4)), 0.0f, this.paint);
                } else {
                        canvas.drawLine((float) (this.anInt / 2), 1.0f, (float) (getWidth() - (this.anInt / 2)), 0.0f, this.paint);
                }
            } catch (Exception e5) {
                e5.printStackTrace();
            }
        } else if (CollageUtils.borderParam.get(parseInt).getBorderLeft() && CollageUtils.borderParam.get(parseInt).getBorderRight()) {
            try {
                canvas.drawLine((float) (this.anInt / 4), 1.0f, (float) (getWidth() - (this.anInt / 4)), 0.0f, this.paint1);
            } catch (Exception e6) {
                e6.printStackTrace();
            }
        } else if (CollageUtils.borderParam.get(parseInt).getBorderLeft()) {
            try {
                canvas.drawLine((float) (this.anInt / 4), 1.0f, (float) (getWidth() - (this.anInt / 2)), 0.0f, this.paint1);
            } catch (Exception e7) {
                e7.printStackTrace();
            }
        } else if (CollageUtils.borderParam.get(parseInt).getBorderRight()) {
            try {
                canvas.drawLine((float) (this.anInt / 2), 1.0f, (float) (getWidth() - (this.anInt / 4)), 0.0f, this.paint1);
            } catch (Exception e8) {
                e8.printStackTrace();
            }
        } else {
            try {
                canvas.drawLine((float) (this.anInt / 2), 1.0f, (float) (getWidth() - (this.anInt / 2)), 0.0f, this.paint1);
            } catch (Exception e9) {
                e9.printStackTrace();
            }
        }
        if (CollageUtils.borderParam.get(parseInt).getBorderLeft()) {
            try {
                this.paint.setStrokeWidth((float) (this.anInt / 2));
                canvas.drawLine(1.0f, 0.0f, 0.0f, (float) getHeight(), this.paint);
            } catch (Exception e10) {
                e10.printStackTrace();
            }
        } else {
            try {
                canvas.drawLine(1.0f, 0.0f, 0.0f, (float) getHeight(), this.paint1);
            } catch (Exception e11) {
                e11.printStackTrace();
            }
        }
        if (CollageUtils.borderParam.get(parseInt).getBorderRight()) {
            try {
                this.paint.setStrokeWidth((float) (this.anInt / 2));
                canvas.drawLine((float) (getWidth() - 1), 0.0f, (float) (getWidth() - 1), (float) getHeight(), this.paint);
            } catch (Exception e12) {
                e12.printStackTrace();
            }
        } else {
            try {
                canvas.drawLine((float) (getWidth() - 1), 0.0f, (float) (getWidth() - 1), (float) getHeight(), this.paint1);
            } catch (Exception e13) {
                e13.printStackTrace();
            }
        }
        if (CollageUtils.borderParam.get(parseInt).getBorderBottom()) {
            try {
                this.paint.setStrokeWidth((float) (this.anInt / 2));
                if (CollageUtils.borderParam.get(parseInt).getBorderLeft() && CollageUtils.borderParam.get(parseInt).getBorderRight()) {
                        canvas.drawLine((float) (this.anInt / 4), (float) (getHeight() - 1), (float) (getWidth() - (this.anInt / 4)), (float) (getHeight() - 1), this.paint);
                } else if (CollageUtils.borderParam.get(parseInt).getBorderLeft()) {
                        canvas.drawLine((float) (this.anInt / 4), (float) (getHeight() - 1), (float) (getWidth() - (this.anInt / 2)), (float) (getHeight() - 1), this.paint);
                } else if (CollageUtils.borderParam.get(parseInt).getBorderRight()) {
                        canvas.drawLine((float) (this.anInt / 2), (float) (getHeight() - 1), (float) (getWidth() - (this.anInt / 4)), (float) (getHeight() - 1), this.paint);
                } else {
                        canvas.drawLine((float) (this.anInt / 2), (float) (getHeight() - 1), (float) (getWidth() - (this.anInt / 2)), (float) (getHeight() - 1), this.paint);
                }
            } catch (Exception e18) {
                e18.printStackTrace();
            }
        } else if (CollageUtils.borderParam.get(parseInt).getBorderLeft() && CollageUtils.borderParam.get(parseInt).getBorderRight()) {
            try {
                canvas.drawLine((float) (this.anInt / 4), (float) (getHeight() - 1), (float) (getWidth() - (this.anInt / 4)), (float) (getHeight() - 1), this.paint1);
            } catch (Exception e19) {
                e19.printStackTrace();
            }
        } else if (CollageUtils.borderParam.get(parseInt).getBorderLeft()) {
            try {
                canvas.drawLine((float) (this.anInt / 4), (float) (getHeight() - 1), (float) (getWidth() - (this.anInt / 2)), (float) (getHeight() - 1), this.paint1);
            } catch (Exception e20) {
                e20.printStackTrace();
            }
        } else if (CollageUtils.borderParam.get(parseInt).getBorderRight()) {
            try {
                canvas.drawLine((float) (this.anInt / 2), (float) (getHeight() - 1), (float) (getWidth() - (this.anInt / 4)), (float) (getHeight() - 1), this.paint1);
            } catch (Exception e21) {
                e21.printStackTrace();
            }
        } else {
            try {
                canvas.drawLine((float) (this.anInt / 2), (float) (getHeight() - 1), (float) (getWidth() - (this.anInt / 2)), (float) (getHeight() - 1), this.paint1);
            } catch (Exception e22) {
                e22.printStackTrace();
            }
        }
    }

    public void setColor(int i) {
        this.paint1.setColor(i);
        this.paint.setColor(i);
        invalidate();
    }

    public int getColor() {
        return this.paint1.getColor();
    }

    public void setColorAlpha(int i) {
        this.paint1.setAlpha(i);
        this.paint.setAlpha(i);
        invalidate();
    }

    public int getColorAlpha() {
        return this.paint1.getAlpha();
    }

    public void setStrokeWidth(int i) {
        this.anInt = i;
        float f = (float) i;
        this.paint1.setStrokeWidth(f);
        this.paint.setStrokeWidth(f);
        invalidate();
    }

    public int getStrokeWidth() {
        return (int) this.paint1.getStrokeWidth();
    }
}
