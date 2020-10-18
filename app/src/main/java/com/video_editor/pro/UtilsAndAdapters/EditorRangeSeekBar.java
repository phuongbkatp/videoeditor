package com.video_editor.pro.UtilsAndAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import androidx.appcompat.widget.AppCompatImageView;

import com.video_editor.pro.R;

import java.math.BigDecimal;

@SuppressLint({"WrongConstant"})
public class EditorRangeSeekBar<T extends Number> extends AppCompatImageView {
    public static final int ACTION_POINTER_INDEX_MASK = 65280;
    public static final int ACTION_POINTER_INDEX_SHIFT = 8;
    public static final int ACTION_POINTER_UP = 6;
    public static final int BACKGROUND_COLOR = Color.parseColor("#17ce75");
    public static final int DEFAULT_COLOR = Color.parseColor("#4fa2f2");
    public static final int INVALID_POINTER_ID = 255;
    public final boolean IS_MULTI_COLORED;
    public final int LEFT_COLOR;
    public final int MIDDLE_COLOR;
    public final int RIGHT_COLOR;
    public int SINGLE_COLOR;
    float aFloat;
    float aFloat1;
    Bitmap bitmap;
    Bitmap bitmap1;
    Bitmap bitmap2;
    Bitmap bitmap3;
    float aFloat2;
    private final T h;
    private final double aDouble;
    private final T t1;
    private final double aDouble1;
    private final float aFloat3;
    private OnRangeSeekBarChangeListener<T> tOnRangeSeekBarChangeListener;
    private int anInt = 255;
    private float aFloat4;
    private boolean aBoolean;
    private int anInt1;
    private double aDouble2 = 1.0d;
    private double aDouble3 = 0.0d;
    private boolean aBoolean1 = true;
    private final a u;
    private final float aFloat5;
    private final Paint paint = new Paint(1);
    private b aNull = null;

    public interface OnRangeSeekBarChangeListener<T> {
        void onRangeSeekBarValuesChanged(EditorRangeSeekBar<?> editorRangeSeekBar, T t, T t2, boolean z);
    }

    private enum a {
        LONG,
        DOUBLE,
        INTEGER,
        FLOAT,
        SHORT,
        BYTE,
        BIG_DECIMAL;

        public static <E extends Number> a a(E e) throws IllegalArgumentException {
            if (e instanceof Long) {
                return LONG;
            }
            if (e instanceof Double) {
                return DOUBLE;
            }
            if (e instanceof Integer) {
                return INTEGER;
            }
            if (e instanceof Float) {
                return FLOAT;
            }
            if (e instanceof Short) {
                return SHORT;
            }
            if (e instanceof Byte) {
                return BYTE;
            }
            if (e instanceof BigDecimal) {
                return BIG_DECIMAL;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Number class '");
            sb.append(e.getClass().getName());
            sb.append("' is not supported");
            throw new IllegalArgumentException(sb.toString());
        }

        public Number a(double d) {
            switch (this) {
                case LONG:
                    return new Long((long) d);
                case DOUBLE:
                    return Double.valueOf(d);
                case INTEGER:
                    return new Integer((int) d);
                case FLOAT:
                    return new Float(d);
                case SHORT:
                    return new Short((short) ((int) d));
                case BYTE:
                    return new Byte((byte) ((int) d));
                case BIG_DECIMAL:
                    return new BigDecimal(d);
                default:
                    StringBuilder sb = new StringBuilder();
                    sb.append("can't convert ");
                    sb.append(this);
                    sb.append(" to a Number object");
                    throw new InstantiationError(sb.toString());
            }
        }
    }

    private enum b {
        MIN,
        MAX
    }

    public void setOnRangeSeekBarChangeListener(OnRangeSeekBarChangeListener<T> onRangeSeekBarChangeListener) {
        this.tOnRangeSeekBarChangeListener = onRangeSeekBarChangeListener;
    }

    public EditorRangeSeekBar(T t2, T t3, Context context) throws IllegalArgumentException {
        super(context);
        this.t1 = t2;
        this.h = t3;
        this.aDouble1 = t2.doubleValue();
        this.aDouble = t3.doubleValue();
        this.u = a.a(t2);
        this.IS_MULTI_COLORED = false;
        this.SINGLE_COLOR = Color.parseColor("#0f9d58");
        this.LEFT_COLOR = 0;
        this.MIDDLE_COLOR = 0;
        this.RIGHT_COLOR = 0;
        this.SINGLE_COLOR = Color.parseColor("#0f9d58");
        this.bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.seekleft);
        this.bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.seekleft);
        this.bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.seekright);
        this.bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.seekright);
        if ((getResources().getConfiguration().screenLayout & 15) == 3) {
            this.bitmap = scaleCenterCrop(this.bitmap, 50, 64);
            this.bitmap2 = scaleCenterCrop(this.bitmap2, 50, 64);
            this.bitmap1 = scaleCenterCrop(this.bitmap1, 50, 64);
            this.bitmap3 = scaleCenterCrop(this.bitmap3, 50, 64);
        } else {
            this.bitmap = scaleCenterCrop(this.bitmap, 50, 64);
            this.bitmap2 = scaleCenterCrop(this.bitmap2, 50, 64);
            this.bitmap1 = scaleCenterCrop(this.bitmap1, 50, 64);
            this.bitmap3 = scaleCenterCrop(this.bitmap3, 50, 64);
        }
        Log.e("rangeSeekBar", "You are in  first rangeseek baar method");
        this.aFloat2 = (float) this.bitmap.getWidth();
        this.aFloat1 = this.aFloat2 * 0.5f;
        this.aFloat = ((float) this.bitmap.getHeight()) * 0.5f;
        this.aFloat3 = 0.3f * this.aFloat;
        StringBuilder sb = new StringBuilder();
        sb.append("value of lineHeight is  ");
        sb.append(this.aFloat3);
        Log.e("RangeseekBar", sb.toString());
        this.aFloat5 = this.aFloat1;
        setFocusable(true);
        setFocusableInTouchMode(true);
        c();
    }

    private int a(int i2) {
        return (int) ((((float) i2) * getResources().getDisplayMetrics().density) + 0.5f);
    }

    private final void c() {
        this.anInt1 = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    public boolean isNotifyWhileDragging() {
        return this.aBoolean1;
    }

    public void setNotifyWhileDragging(boolean z) {
        this.aBoolean1 = z;
    }

    public T getAbsoluteMinValue() {
        return this.t1;
    }

    public T getAbsoluteMaxValue() {
        return this.h;
    }

    public T getSelectedMinValue() {
        return a(this.aDouble3);
    }

    public void setSelectedMinValue(T t2) {
        if (0.0d == this.aDouble - this.aDouble1) {
            setNormalizedMinValue(0.0d);
        } else {
            setNormalizedMinValue(a(t2));
        }
    }

    public T getSelectedMaxValue() {
        return a(this.aDouble2);
    }

    public void setSelectedMaxValue(T t2) {
        if (0.0d == this.aDouble - this.aDouble1) {
            setNormalizedMaxValue(1.0d);
        } else {
            setNormalizedMaxValue(a(t2));
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!isEnabled()) {
            return false;
        }
        switch (motionEvent.getAction() & 255) {
            case 0:
                this.anInt = motionEvent.getPointerId(motionEvent.getPointerCount() - 1);
                this.aFloat4 = motionEvent.getX(motionEvent.findPointerIndex(this.anInt));
                this.aNull = a(this.aFloat4);
                if (this.aNull != null) {
                    setPressed(true);
                    invalidate();
                    a();
                    b(motionEvent);
                    d();
                    break;
                } else {
                    return super.onTouchEvent(motionEvent);
                }
            case 1:
                if (this.aBoolean) {
                    b(motionEvent);
                    b();
                    setPressed(false);
                } else {
                    a();
                    b(motionEvent);
                    b();
                }
                this.aNull = null;
                invalidate();
                if (this.tOnRangeSeekBarChangeListener != null) {
                    this.tOnRangeSeekBarChangeListener.onRangeSeekBarValuesChanged(this, getSelectedMinValue(), getSelectedMaxValue(), true);
                    break;
                }
                break;
            case 2:
                if (this.aNull != null) {
                    if (this.aBoolean) {
                        b(motionEvent);
                    } else if (Math.abs(motionEvent.getX(motionEvent.findPointerIndex(this.anInt)) - this.aFloat4) > ((float) this.anInt1)) {
                        setPressed(true);
                        invalidate();
                        a();
                        b(motionEvent);
                        d();
                    }
                    if (this.aBoolean1 && this.tOnRangeSeekBarChangeListener != null) {
                        this.tOnRangeSeekBarChangeListener.onRangeSeekBarValuesChanged(this, getSelectedMinValue(), getSelectedMaxValue(), false);
                        break;
                    }
                }
                break;
            case 3:
                if (this.aBoolean) {
                    b();
                    setPressed(false);
                }
                invalidate();
                break;
            case 5:
                int pointerCount = motionEvent.getPointerCount() - 1;
                this.aFloat4 = motionEvent.getX(pointerCount);
                this.anInt = motionEvent.getPointerId(pointerCount);
                invalidate();
                break;
            case 6:
                a(motionEvent);
                invalidate();
                break;
        }
        return true;
    }

    private final void a(MotionEvent motionEvent) {
        int action = (motionEvent.getAction() & 65280) >> 8;
        if (motionEvent.getPointerId(action) == this.anInt) {
            int i2 = action == 0 ? 1 : 0;
            this.aFloat4 = motionEvent.getX(i2);
            this.anInt = motionEvent.getPointerId(i2);
        }
    }

    private final void b(MotionEvent motionEvent) {
        float x2 = motionEvent.getX(motionEvent.findPointerIndex(this.anInt));
        if (b.MIN.equals(this.aNull)) {
            setNormalizedMinValue(b(x2));
        } else if (b.MAX.equals(this.aNull)) {
            setNormalizedMaxValue(b(x2));
        }
    }

    private void d() {
        if (getParent() != null) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
    }


    public void a() {
        this.aBoolean = true;
    }


    public void b() {
        this.aBoolean = false;
    }


    public synchronized void onMeasure(int i2, int i3) {
        int i4 = 200;
        if (MeasureSpec.getMode(i2) != 0) {
            i4 = MeasureSpec.getSize(i2);
        }
        int height = this.bitmap.getHeight();
        if (MeasureSpec.getMode(i3) != 0) {
            height = Math.min(height, MeasureSpec.getSize(i3));
        }
        setMeasuredDimension(i4, height);
    }


    public synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.paint.setStyle(Style.FILL);
        this.paint.setAntiAlias(true);
        if (this.IS_MULTI_COLORED) {
            RectF rectF = new RectF(this.aFloat5, (((float) getHeight()) - this.aFloat3) * 0.5f, b(this.aDouble3), (((float) getHeight()) + this.aFloat3) * 0.5f);
            this.paint.setColor(this.LEFT_COLOR);
            canvas.drawRect(rectF, this.paint);
            RectF rectF2 = new RectF(this.aFloat5, (((float) getHeight()) - this.aFloat3) * 0.5f, ((float) getWidth()) - this.aFloat5, (((float) getHeight()) + this.aFloat3) * 0.5f);
            rectF2.left = b(this.aDouble3);
            rectF2.right = b(this.aDouble2);
            this.paint.setColor(this.MIDDLE_COLOR);
            canvas.drawRect(rectF2, this.paint);
            RectF rectF3 = new RectF(b(this.aDouble2), (((float) getHeight()) - this.aFloat3) * 0.5f, ((float) getWidth()) - this.aFloat5, (((float) getHeight()) + this.aFloat3) * 0.5f);
            this.paint.setColor(this.RIGHT_COLOR);
            canvas.drawRect(rectF3, this.paint);
        } else {
            RectF rectF4 = new RectF(this.aFloat5, ((float) (getHeight() - a(40))) * 0.5f, ((float) getWidth()) - this.aFloat5, ((float) (getHeight() + a(40))) * 0.5f);
            this.paint.setColor(BACKGROUND_COLOR);
            canvas.drawRect(rectF4, this.paint);
            rectF4.left = b(this.aDouble3);
            rectF4.right = b(this.aDouble2);
            this.paint.setColor(this.SINGLE_COLOR);
            canvas.drawRect(rectF4, this.paint);
        }
        a(b(this.aDouble3), b.MIN.equals(this.aNull), canvas);
        b(b(this.aDouble2), b.MAX.equals(this.aNull), canvas);
    }


    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("SUPER", super.onSaveInstanceState());
        bundle.putDouble("MIN", this.aDouble3);
        bundle.putDouble("MAX", this.aDouble2);
        return bundle;
    }


    public void onRestoreInstanceState(Parcelable parcelable) {
        Bundle bundle = (Bundle) parcelable;
        super.onRestoreInstanceState(bundle.getParcelable("SUPER"));
        this.aDouble3 = bundle.getDouble("MIN");
        this.aDouble2 = bundle.getDouble("MAX");
    }

    private void a(float f2, boolean z, Canvas canvas) {
        canvas.drawBitmap(z ? this.bitmap2 : this.bitmap, f2 - this.aFloat1, (0.5f * ((float) getHeight())) - this.aFloat, null);
    }

    private void b(float f2, boolean z, Canvas canvas) {
        canvas.drawBitmap(z ? this.bitmap3 : this.bitmap1, f2 - this.aFloat1, (0.5f * ((float) getHeight())) - this.aFloat, null);
    }

    private b a(float f2) {
        boolean a2 = a(f2, this.aDouble3);
        boolean a3 = a(f2, this.aDouble2);
        if (a2 && a3) {
            return f2 / ((float) getWidth()) > 0.5f ? b.MIN : b.MAX;
        } else if (a2) {
            return b.MIN;
        } else {
            if (a3) {
                return b.MAX;
            }
            return null;
        }
    }

    private boolean a(float f2, double d2) {
        return Math.abs(f2 - b(d2)) <= this.aFloat1;
    }

    public void setNormalizedMinValue(double d2) {
        this.aDouble3 = Math.max(0.0d, Math.min(1.0d, Math.min(d2, this.aDouble2)));
        invalidate();
    }

    public void setNormalizedMaxValue(double d2) {
        this.aDouble2 = Math.max(0.0d, Math.min(1.0d, Math.max(d2, this.aDouble3)));
        invalidate();
    }

    private T a(double d2) {
        return (T) this.u.a(this.aDouble1 + ((this.aDouble - this.aDouble1) * d2));
    }

    private double a(T t2) {
        if (0.0d == this.aDouble - this.aDouble1) {
            return 0.0d;
        }
        return (t2.doubleValue() - this.aDouble1) / (this.aDouble - this.aDouble1);
    }

    private float b(double d2) {
        return (float) (((double) this.aFloat5) + (((double) (((float) getWidth()) - (2.0f * this.aFloat5))) * d2));
    }

    private double b(float f2) {
        float width = (float) getWidth();
        if (width <= this.aFloat5 * 2.0f) {
            return 0.0d;
        }
        return Math.min(1.0d, Math.max(0.0d, (f2 - this.aFloat5) / (width - (this.aFloat5 * 2.0f))));
    }

    public Bitmap scaleCenterCrop(Bitmap bitmap, int i2, int i3) {
        float f2 = (float) i3;
        float width = (float) bitmap.getWidth();
        float f3 = (float) i2;
        float height = (float) bitmap.getHeight();
        float max = Math.max(f2 / width, f3 / height);
        float f4 = width * max;
        float f5 = max * height;
        float f6 = (f2 - f4) / 2.0f;
        float f7 = (f3 - f5) / 2.0f;
        RectF rectF = new RectF(f6, f7, f4 + f6, f5 + f7);
        Bitmap createBitmap = Bitmap.createBitmap(i3, i2, bitmap.getConfig());
        new Canvas(createBitmap).drawBitmap(bitmap, null, rectF, null);
        return createBitmap;
    }
}
