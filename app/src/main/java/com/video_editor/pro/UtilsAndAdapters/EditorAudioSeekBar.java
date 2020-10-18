package com.video_editor.pro.UtilsAndAdapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatImageView;

import com.video_editor.pro.R;

public class EditorAudioSeekBar extends AppCompatImageView {
    private boolean aBoolean;
    private boolean aBoolean1;
    private int anInt = 100;
    private Paint paint = new Paint();
    private Paint paint1 = new Paint();
    private int color = getResources().getColor(R.color.seekbargray);
    private int anInt1 = 2;
    private int anInt2 = 15;
    private SeekBarChangeListener seekBarChangeListener;
    private int color1 = getResources().getColor(R.color.colorPrimary);
    private Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.seekbar_thumb);
    private int anInt3;
    private int anInt4;
    private int anInt5;
    private int dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.default_margin);
    private Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.cutter);
    private int anInt6;
    private int anInt7;
    private int anInt8;
    private int anInt9;
    private int anInt10;
    private int anInt11;
    private int anInt12;
    private int anInt13;
    private int anInt14;

    public interface SeekBarChangeListener {
        void SeekBarValueChanged(int i, int i2);
    }

    public EditorAudioSeekBar(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
    }

    public EditorAudioSeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public EditorAudioSeekBar(Context context) {
        super(context);
    }

    @Override
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        a();
    }

    private void a() {
        if (this.bitmap1.getHeight() > getHeight()) {
            getLayoutParams().height = this.bitmap1.getHeight();
        }
        this.anInt9 = (getHeight() / 2) - (this.bitmap1.getHeight() / 2);
        this.anInt3 = (getHeight() / 2) - (this.bitmap.getHeight() / 2);
        this.anInt14 = this.bitmap1.getWidth() / 2;
        this.anInt5 = this.bitmap.getWidth() / 2;
        if (this.anInt12 == 0 || this.anInt10 == 0) {
            try {
                this.anInt12 = this.dimensionPixelOffset;
                this.anInt10 = getWidth() - this.dimensionPixelOffset;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        this.anInt7 = (getHeight() / 2) - this.anInt1;
        this.anInt8 = (getHeight() / 2) + this.anInt1;
        invalidate();
    }

    public void setSeekBarChangeListener(SeekBarChangeListener seekBarChangeListener) {
        this.seekBarChangeListener = seekBarChangeListener;
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.paint.setColor(this.color);
        canvas.drawRect(new Rect(this.dimensionPixelOffset, this.anInt7, this.anInt12, this.anInt8), this.paint);
        canvas.drawRect(new Rect(this.anInt10, this.anInt7, getWidth() - this.dimensionPixelOffset, this.anInt8), this.paint);
        this.paint.setColor(this.color1);
        canvas.drawRect(new Rect(this.anInt12, this.anInt7, this.anInt10, this.anInt8), this.paint);
        if (!this.aBoolean1) {
            try {
                canvas.drawBitmap(this.bitmap1, (float) (this.anInt12 - this.anInt14), (float) this.anInt9, this.paint1);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        if (this.aBoolean) {
            try {
                canvas.drawBitmap(this.bitmap, (float) (this.anInt4 - this.anInt5), (float) this.anInt3, this.paint1);
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.aBoolean1) {
            try {
                int x2 = (int) motionEvent.getX();
                switch (motionEvent.getAction()) {
                    case 0:
                        if ((x2 < this.anInt12 - this.anInt14 || x2 > this.anInt12 + this.anInt14) && x2 >= this.anInt12 - this.anInt14) {
                            try {
                                if ((x2 < this.anInt10 - this.anInt14 || x2 > this.anInt10 + this.anInt14) && x2 <= this.anInt10 + this.anInt14) {
                                    try {
                                        if ((x2 - this.anInt12) + this.anInt14 >= (this.anInt10 - this.anInt14) - x2) {
                                            try {
                                                if ((x2 - this.anInt12) + this.anInt14 > (this.anInt10 - this.anInt14) - x2) {
                                                    try {
                                                        this.anInt6 = 0;
                                                        break;
                                                    } catch (Exception e2) {
                                                        e2.printStackTrace();
                                                    }
                                                }
                                            } catch (Exception e8) {
                                                e8.printStackTrace();
                                            }
                                        }
                                        this.anInt6 = 1;
                                        break;
                                    } catch (Exception e14) {
                                        e14.printStackTrace();
                                    }
                                }
                                this.anInt6 = 0;
                                break;
                            } catch (Exception e20) {
                                e20.printStackTrace();
                            }
                        }
                        this.anInt6 = 1;
                        break;
                    case 1:
                        this.anInt6 = 0;
                        break;
                    case 2:
                        if ((x2 <= this.anInt12 + this.anInt14 + 0 && this.anInt6 == 2) || (x2 >= (this.anInt10 - this.anInt14) + 0 && this.anInt6 == 1)) {
                            this.anInt6 = 0;
                        }
                        if (this.anInt6 == 2) {
                            this.anInt10 = x2;
                            break;
                        }
                        this.anInt12 = x2;
                        break;
                }
                b();
            } catch (Exception e29) {
                e29.printStackTrace();
            }
        }
        return true;
    }

    private void b() {
        if (this.anInt12 < this.dimensionPixelOffset) {
            try {
                this.anInt12 = this.dimensionPixelOffset;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        if (this.anInt10 < this.dimensionPixelOffset) {
            try {
                this.anInt10 = this.dimensionPixelOffset;
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        if (this.anInt12 > getWidth() - this.dimensionPixelOffset) {
            try {
                this.anInt12 = getWidth() - this.dimensionPixelOffset;
            } catch (Exception e4) {
                e4.printStackTrace();
            }
        }
        if (this.anInt10 > getWidth() - this.dimensionPixelOffset) {
            try {
                this.anInt10 = getWidth() - this.dimensionPixelOffset;
            } catch (Exception e5) {
                e5.printStackTrace();
            }
        }
        invalidate();
        if (this.seekBarChangeListener != null) {
            try {
                c();
                this.seekBarChangeListener.SeekBarValueChanged(this.anInt13, this.anInt11);
            } catch (OutOfMemoryError e6) {
                e6.printStackTrace();
            } catch (ArrayIndexOutOfBoundsException e7) {
                e7.printStackTrace();
            } catch (ActivityNotFoundException e8) {
                e8.printStackTrace();
            } catch (NotFoundException e9) {
                e9.printStackTrace();
            } catch (NullPointerException e10) {
                e10.printStackTrace();
            } catch (StackOverflowError e11) {
                e11.printStackTrace();
            }
        }
    }

    private void c() {
        this.anInt13 = (this.anInt * (this.anInt12 - this.dimensionPixelOffset)) / (getWidth() - (this.dimensionPixelOffset * 2));
        this.anInt11 = (this.anInt * (this.anInt10 - this.dimensionPixelOffset)) / (getWidth() - (this.dimensionPixelOffset * 2));
    }

    private int a(int i2) {
        return ((int) (((((double) getWidth()) - (2.0d * ((double) this.dimensionPixelOffset))) / ((double) this.anInt)) * ((double) i2))) + this.dimensionPixelOffset;
    }

    public void setLeftProgress(int i2) {
        if (i2 < this.anInt11 - this.anInt2) {
            try {
                this.anInt12 = a(i2);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        b();
    }

    public void setRightProgress(int i2) {
        if (i2 > this.anInt13 + this.anInt2) {
            try {
                this.anInt10 = a(i2);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        b();
    }

    public int getSelectedThumb() {
        return this.anInt6;
    }

    public int getLeftProgress() {
        return this.anInt13;
    }

    public void videoPlayingProgress(int i2) {
        this.aBoolean = true;
        this.anInt4 = a(i2);
        invalidate();
    }

    public void removeVideoStatusThumb() {
        this.aBoolean = false;
        invalidate();
    }

    public void setSliceBlocked(boolean z) {
        this.aBoolean1 = z;
        invalidate();
    }

    public void setMaxValue(int i2) {
        this.anInt = i2;
    }

    public void setProgressMinDiff(int i2) {
        this.anInt2 = i2;
    }
}
