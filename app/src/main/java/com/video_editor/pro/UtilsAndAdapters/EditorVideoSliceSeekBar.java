package com.video_editor.pro.UtilsAndAdapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatImageView;

import com.video_editor.pro.R;

public class EditorVideoSliceSeekBar extends AppCompatImageView {
    private Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.cutter);
    private boolean aBoolean;
    private boolean aBoolean1;
    private int anInt = 100;
    private Paint paint = new Paint();
    private Paint paint1 = new Paint();
    private int anInt1;
    private int color = getResources().getColor(R.color.seekbargray);
    private int anInt2 = 3;
    private int anInt3 = 15;
    private int anInt4;
    private int anInt5;
    private SeekBarChangeListener seekBarChangeListener;
    private int color1 = getResources().getColor(R.color.colorPrimary);
    private int anInt6;
    private Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.seekbar_thumb);
    private int anInt7;
    private int anInt8;
    private int anInt9;
    private int dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.default_margin);
    private Bitmap decodeResource = BitmapFactory.decodeResource(getResources(), R.drawable.cutter);
    private int anInt10;
    private int anInt11;
    private int anInt12;
    private int anInt13;
    private int anInt14;
    private int anInt15;

    public interface SeekBarChangeListener {
        void SeekBarValueChanged(int i, int i2);
    }

    public EditorVideoSliceSeekBar(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
    }

    public EditorVideoSliceSeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public EditorVideoSliceSeekBar(Context context) {
        super(context);
    }

    public void onWindowFocusChanged(boolean z2) {
        super.onWindowFocusChanged(z2);
        if (!isInEditMode()) {
            a();
        }
    }

    private void a() {
        if (this.decodeResource.getHeight() > getHeight()) {
            getLayoutParams().height = this.decodeResource.getHeight();
        }
        this.anInt15 = (getHeight() / 2) - (this.decodeResource.getHeight() / 2);
        this.anInt9 = (getHeight() / 2) - (this.bitmap1.getHeight() / 2);
        this.anInt10 = this.decodeResource.getWidth() / 2;
        this.anInt7 = this.bitmap1.getWidth() / 2;
        if (this.anInt12 == 0 || this.anInt14 == 0) {
            this.anInt12 = this.dimensionPixelOffset;
            this.anInt14 = getWidth() - this.dimensionPixelOffset;
        }
        this.anInt4 = a(this.anInt3) - (this.dimensionPixelOffset * 2);
        this.anInt5 = (getHeight() / 2) - this.anInt2;
        this.anInt1 = (getHeight() / 2) + this.anInt2;
        invalidate();
    }

    public void setSeekBarChangeListener(SeekBarChangeListener seekBarChangeListener) {
        this.seekBarChangeListener = seekBarChangeListener;
    }


    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.paint.setColor(this.color);
        canvas.drawRect(new Rect(this.dimensionPixelOffset, this.anInt5, this.anInt12, this.anInt1), this.paint);
        canvas.drawRect(new Rect(this.anInt14, this.anInt5, getWidth() - this.dimensionPixelOffset, this.anInt1), this.paint);
        this.paint.setColor(this.color1);
        canvas.drawRect(new Rect(this.anInt12, this.anInt5, this.anInt14, this.anInt1), this.paint);
        if (!this.aBoolean) {
            canvas.drawBitmap(this.decodeResource, (float) (this.anInt12 - this.anInt10), (float) this.anInt15, this.paint1);
            canvas.drawBitmap(this.bitmap, (float) (this.anInt14 - this.anInt10), (float) this.anInt15, this.paint1);
        }
        if (this.aBoolean1) {
            canvas.drawBitmap(this.bitmap1, (float) (this.anInt8 - this.anInt7), (float) this.anInt9, this.paint1);
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.aBoolean) {
            int x2 = (int) motionEvent.getX();
            switch (motionEvent.getAction()) {
                case 0:
                    if ((x2 < this.anInt12 - this.anInt10 || x2 > this.anInt12 + this.anInt10) && x2 >= this.anInt12 - this.anInt10) {
                        if ((x2 < this.anInt14 - this.anInt10 || x2 > this.anInt14 + this.anInt10) && x2 <= this.anInt14 + this.anInt10) {
                            if ((x2 - this.anInt12) + this.anInt10 >= (this.anInt14 - this.anInt10) - x2 && (x2 - this.anInt12) + this.anInt10 > (this.anInt14 - this.anInt10) - x2) {
                                this.anInt6 = 2;
                                break;
                            } else {
                                this.anInt6 = 1;
                                break;
                            }
                        } else {
                            this.anInt6 = 2;
                            break;
                        }
                    } else {
                        this.anInt6 = 1;
                        break;
                    }
                case 1:
                    this.anInt6 = 0;
                    break;
                case 2:
                    if ((x2 <= this.anInt12 + this.anInt10 + 0 && this.anInt6 == 2) || (x2 >= (this.anInt14 - this.anInt10) + 0 && this.anInt6 == 1)) {
                        this.anInt6 = 0;
                    }
                    if (this.anInt6 != 1 && this.anInt6 == 2) {
                        this.anInt14 = x2;
                        break;
                    } else {
                        this.anInt12 = x2;
                        break;
                    }
            }
            b();
        }
        return true;
    }

    private void b() {
        if (this.anInt12 < this.dimensionPixelOffset) {
            this.anInt12 = this.dimensionPixelOffset;
        }
        if (this.anInt14 < this.dimensionPixelOffset) {
            this.anInt14 = this.dimensionPixelOffset;
        }
        if (this.anInt12 > getWidth() - this.dimensionPixelOffset) {
            this.anInt12 = getWidth() - this.dimensionPixelOffset;
        }
        if (this.anInt14 > getWidth() - this.dimensionPixelOffset) {
            this.anInt14 = getWidth() - this.dimensionPixelOffset;
        }
        invalidate();
        if (this.seekBarChangeListener != null) {
            c();
            this.seekBarChangeListener.SeekBarValueChanged(this.anInt11, this.anInt13);
        }
    }

    private void c() {
        this.anInt11 = (this.anInt * (this.anInt12 - this.dimensionPixelOffset)) / (getWidth() - (this.dimensionPixelOffset * 2));
        this.anInt13 = (this.anInt * (this.anInt14 - this.dimensionPixelOffset)) / (getWidth() - (this.dimensionPixelOffset * 2));
    }

    private int a(int i2) {
        return ((int) (((((double) getWidth()) - (2.0d * ((double) this.dimensionPixelOffset))) / ((double) this.anInt)) * ((double) i2))) + this.dimensionPixelOffset;
    }

    public void setLeftProgress(int i2) {
        if (i2 < this.anInt13 - this.anInt3) {
            this.anInt12 = a(i2);
        }
        b();
    }

    public void setRightProgress(int i2) {
        if (i2 > this.anInt11 + this.anInt3) {
            this.anInt14 = a(i2);
        }
        b();
    }

    public int getSelectedThumb() {
        return this.anInt6;
    }

    public int getLeftProgress() {
        return this.anInt11;
    }

    public int getRightProgress() {
        return this.anInt13;
    }

    public void setProgress(int i2, int i3) {
        if (i3 - i2 > this.anInt3) {
            this.anInt12 = a(i2);
            this.anInt14 = a(i3);
        }
        b();
    }

    public void videoPlayingProgress(int i2) {
        this.aBoolean1 = true;
        this.anInt8 = a(i2);
        invalidate();
    }

    public void removeVideoStatusThumb() {
        this.aBoolean1 = false;
        invalidate();
    }

    public void setSliceBlocked(boolean z2) {
        this.aBoolean = z2;
        invalidate();
    }

    public void setMaxValue(int i2) {
        this.anInt = i2;
    }

    public void setProgressMinDiff(int i2) {
        this.anInt3 = i2;
        this.anInt4 = a(i2);
    }

    public void setProgressHeight(int i2) {
        this.anInt2 /= 2;
        invalidate();
    }

    public void setProgressColor(int i2) {
        this.color = i2;
        invalidate();
    }

    public void setSecondaryProgressColor(int i2) {
        this.color1 = i2;
        invalidate();
    }

    public void setThumbSlice(Bitmap bitmap) {
        this.decodeResource = bitmap;
        a();
    }

    public void setThumbCurrentVideoPosition(Bitmap bitmap) {
        this.bitmap1 = bitmap;
        a();
    }

    public void setThumbPadding(int i2) {
        this.dimensionPixelOffset = i2;
        invalidate();
    }
}
