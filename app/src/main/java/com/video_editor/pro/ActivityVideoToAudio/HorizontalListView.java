package com.video_editor.pro.ActivityVideoToAudio;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Scroller;

import androidx.core.view.ViewCompat;
import androidx.core.widget.EdgeEffectCompat;

import com.video_editor.pro.UtilsAndAdapters.EditorStyleable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@SuppressLint({"ResourceType"})
public class HorizontalListView extends AdapterView<ListAdapter> {
    private View view = null;
    private DataSetObserver setObserver = new DataSetObserver() {
        public void onChanged() {
            HorizontalListView.this.aBoolean1 = true;
            HorizontalListView.this.aBoolean2 = false;
            HorizontalListView.this.f();
            HorizontalListView.this.invalidate();
            HorizontalListView.this.requestLayout();
        }

        public void onInvalidated() {
            HorizontalListView.this.aBoolean2 = false;
            HorizontalListView.this.f();
            HorizontalListView.this.c();
            HorizontalListView.this.invalidate();
            HorizontalListView.this.requestLayout();
        }
    };

    public boolean aBoolean = false;
    private OnScrollStateChangedListener.ScrollState scrollStateIdle = OnScrollStateChangedListener.ScrollState.SCROLL_STATE_IDLE;
    private int anInt;

    public boolean aBoolean1 = false;
    private Runnable runnable = new Runnable() {
        public void run() {
            HorizontalListView.this.requestLayout();
        }
    };
    private int anInt1;
    private Drawable aNull = null;
    private int anInt2 = 0;
    private EdgeEffectCompat edgeEffectCompat;
    private EdgeEffectCompat edgeEffectCompat1;

    public GestureDetector gestureDetector;
    private final a a = new a();
    protected ListAdapter mAdapter;
    protected int anInt3;
    protected Scroller mFlingTracker = new Scroller(getContext());
    protected int anInt4;

    public boolean aBoolean2 = false;
    private int anInt5;
    private boolean aBoolean3 = false;

    public int anInt6;
    private int maxValue = Integer.MAX_VALUE;

    public OnClickListener onClickListener;
    private OnScrollStateChangedListener aNull1 = null;
    private Rect rect = new Rect();
    private List<Queue<View>> arrayList = new ArrayList();
    private Integer aNull2 = null;
    private int anInt7;
    private RunningOutOfDataListener aNull3 = null;
    private int anInt8 = 0;

    public interface OnScrollStateChangedListener {

        enum ScrollState {
            SCROLL_STATE_IDLE,
            SCROLL_STATE_TOUCH_SCROLL,
            SCROLL_STATE_FLING
        }

        void onScrollStateChanged(ScrollState scrollState);
    }

    public interface RunningOutOfDataListener {
        void onRunningOutOfData();
    }

    private class a extends SimpleOnGestureListener {
        private a() {
        }

        public boolean onDown(MotionEvent motionEvent) {
            return HorizontalListView.this.onDown(motionEvent);
        }

        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return HorizontalListView.this.onFling(motionEvent, motionEvent2, f, f2);
        }

        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            HorizontalListView.this.a(Boolean.valueOf(true));
            HorizontalListView.this.setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_TOUCH_SCROLL);
            HorizontalListView.this.f();
            HorizontalListView.this.anInt4 += (int) f;
            HorizontalListView.this.i(Math.round(f));
            HorizontalListView.this.requestLayout();
            return true;
        }

        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            HorizontalListView.this.f();
            OnItemClickListener onItemClickListener = HorizontalListView.this.getOnItemClickListener();
            int a2 = HorizontalListView.this.c((int) motionEvent.getX(), (int) motionEvent.getY());
            if (a2 >= 0 && !HorizontalListView.this.aBoolean) {
                try {
                    View childAt = HorizontalListView.this.getChildAt(a2);
                    int d = HorizontalListView.this.anInt6 + a2;
                    if (onItemClickListener != null) {
                        try {
                            onItemClickListener.onItemClick(HorizontalListView.this, childAt, d, HorizontalListView.this.mAdapter.getItemId(d));
                            return true;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            if (HorizontalListView.this.onClickListener != null && !HorizontalListView.this.aBoolean) {
                HorizontalListView.this.onClickListener.onClick(HorizontalListView.this);
            }
            return false;
        }

        public void onLongPress(MotionEvent motionEvent) {
            HorizontalListView.this.f();
            int a2 = HorizontalListView.this.c((int) motionEvent.getX(), (int) motionEvent.getY());
            if (a2 >= 0 && !HorizontalListView.this.aBoolean) {
                try {
                    View childAt = HorizontalListView.this.getChildAt(a2);
                    OnItemLongClickListener onItemLongClickListener = HorizontalListView.this.getOnItemLongClickListener();
                    if (onItemLongClickListener != null) {
                        try {
                            int d = HorizontalListView.this.anInt6 + a2;
                            if (onItemLongClickListener.onItemLongClick(HorizontalListView.this, childAt, d, HorizontalListView.this.mAdapter.getItemId(d))) {
                                try {
                                    HorizontalListView.this.performHapticFeedback(0);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        }
    }

    @TargetApi(11)
    private static final class b {
        static {
            if (VERSION.SDK_INT < 11) {
                throw new RuntimeException("Should not get to HoneycombPlus class unless sdk is >= 11!");
            }
        }

        public static void a(Scroller scroller, float f) {
            if (scroller != null) {
                scroller.setFriction(f);
            }
        }
    }

    @TargetApi(14)
    private static final class c {
        static {
            if (VERSION.SDK_INT < 14) {
                throw new RuntimeException("Should not get to IceCreamSandwichPlus class unless sdk is >= 14!");
            }
        }

        public static float a(Scroller scroller) {
            return scroller.getCurrVelocity();
        }
    }


    public void dispatchSetPressed(boolean z2) {
    }

    public HorizontalListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.edgeEffectCompat = new EdgeEffectCompat(context);
        this.edgeEffectCompat1 = new EdgeEffectCompat(context);
        this.gestureDetector = new GestureDetector(context, this.a);
        a();
        b();
        a(context, attributeSet);
        setWillNotDraw(false);
        if (VERSION.SDK_INT >= 11) {
            b.a(this.mFlingTracker, 0.009f);
        }
    }

    private void a() {
        setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return HorizontalListView.this.gestureDetector.onTouchEvent(motionEvent);
            }
        });
    }


    public void a(Boolean bool) {
        if (this.aBoolean3 != bool.booleanValue()) {
            for (View view = this; view.getParent() instanceof View; view = (View) view.getParent()) {
                if ((view.getParent() instanceof ListView) || (view.getParent() instanceof ScrollView)) {
                    view.getParent().requestDisallowInterceptTouchEvent(bool.booleanValue());
                    this.aBoolean3 = bool.booleanValue();
                    return;
                }
            }
        }
    }

    private void a(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            try {
                TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, EditorStyleable.HorizontalListView);
                Drawable drawable = obtainStyledAttributes.getDrawable(1);
                if (drawable != null) {
                    setDivider(drawable);
                }
                int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(3, 0);
                if (dimensionPixelSize != 0) {
                    setDividerWidth(dimensionPixelSize);
                }
                obtainStyledAttributes.recycle();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("BUNDLE_ID_PARENT_STATE", super.onSaveInstanceState());
        bundle.putInt("BUNDLE_ID_CURRENT_X", this.anInt3);
        return bundle;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            this.aNull2 = Integer.valueOf(bundle.getInt("BUNDLE_ID_CURRENT_X"));
            super.onRestoreInstanceState(bundle.getParcelable("BUNDLE_ID_PARENT_STATE"));
        }
    }

    public void setDivider(Drawable drawable) {
        this.aNull = drawable;
        if (drawable != null) {
            setDividerWidth(drawable.getIntrinsicWidth());
        } else {
            setDividerWidth(0);
        }
    }

    public void setDividerWidth(int i2) {
        this.anInt2 = i2;
        requestLayout();
        invalidate();
    }

    private void b() {
        this.anInt6 = -1;
        this.anInt7 = -1;
        this.anInt1 = 0;
        this.anInt3 = 0;
        this.anInt4 = 0;
        this.maxValue = Integer.MAX_VALUE;
        setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_IDLE);
    }


    public void c() {
        b();
        removeAllViewsInLayout();
        requestLayout();
    }

    public void setSelection(int i2) {
        this.anInt = i2;
    }

    public View getSelectedView() {
        return g(this.anInt);
    }

    public void setAdapter(ListAdapter listAdapter) {
        if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver(this.setObserver);
        }
        if (listAdapter != null) {
            this.aBoolean2 = false;
            this.mAdapter = listAdapter;
            this.mAdapter.registerDataSetObserver(this.setObserver);
        }
        a(this.mAdapter.getViewTypeCount());
        c();
    }

    public ListAdapter getAdapter() {
        return this.mAdapter;
    }

    private void a(int i2) {
        this.arrayList.clear();
        for (int i3 = 0; i3 < i2; i3++) {
            this.arrayList.add(new LinkedList());
        }
    }

    private View b(int i2) {
        int itemViewType = this.mAdapter.getItemViewType(i2);
        if (c(itemViewType)) {
            return (View) ((Queue) this.arrayList.get(itemViewType)).poll();
        }
        return null;
    }

    private void a(int i2, View view) {
        int itemViewType = this.mAdapter.getItemViewType(i2);
        if (c(itemViewType)) {
            this.arrayList.get(itemViewType).offer(view);
        }
    }

    private boolean c(int i2) {
        return i2 < this.arrayList.size();
    }

    private void a(View view, int i2) {
        addViewInLayout(view, i2, b(view), true);
        a(view);
    }

    private void a(View view) {
        int i2;
        LayoutParams b2 = b(view);
        int childMeasureSpec = ViewGroup.getChildMeasureSpec(this.anInt5, getPaddingTop() + getPaddingBottom(), b2.height);
        if (b2.width > 0) {
            i2 = MeasureSpec.makeMeasureSpec(b2.width, 1073741824);
        } else {
            i2 = MeasureSpec.makeMeasureSpec(0, 0);
        }
        view.measure(i2, childMeasureSpec);
    }

    private LayoutParams b(View view) {
        LayoutParams layoutParams = view.getLayoutParams();
        return layoutParams == null ? new LayoutParams(-2, -1) : layoutParams;
    }


    @SuppressLint({"WrongCall"})
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        super.onLayout(z2, i2, i3, i4, i5);
        if (this.mAdapter != null) {
            invalidate();
            if (this.aBoolean1) {
                int i6 = this.anInt3;
                b();
                removeAllViewsInLayout();
                this.anInt4 = i6;
                this.aBoolean1 = false;
            }
            if (this.aNull2 != null) {
                this.anInt4 = this.aNull2.intValue();
                this.aNull2 = null;
            }
            if (this.mFlingTracker.computeScrollOffset()) {
                this.anInt4 = this.mFlingTracker.getCurrX();
            }
            if (this.anInt4 < 0) {
                this.anInt4 = 0;
                if (this.edgeEffectCompat.isFinished()) {
                    this.edgeEffectCompat.onAbsorb((int) d());
                }
                this.mFlingTracker.forceFinished(true);
                setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_IDLE);
            } else if (this.anInt4 > this.maxValue) {
                this.anInt4 = this.maxValue;
                if (this.edgeEffectCompat1.isFinished()) {
                    this.edgeEffectCompat1.onAbsorb((int) d());
                }
                this.mFlingTracker.forceFinished(true);
                setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_IDLE);
            }
            int i7 = this.anInt3 - this.anInt4;
            e(i7);
            d(i7);
            f(i7);
            this.anInt3 = this.anInt4;
            if (e()) {
                onLayout(z2, i2, i3, i4, i5);
            } else if (!this.mFlingTracker.isFinished()) {
                ViewCompat.postOnAnimation(this, this.runnable);
            } else if (this.scrollStateIdle == OnScrollStateChangedListener.ScrollState.SCROLL_STATE_FLING) {
                setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_IDLE);
            }
        }
    }


    public float getLeftFadingEdgeStrength() {
        int horizontalFadingEdgeLength = getHorizontalFadingEdgeLength();
        if (this.anInt3 == 0) {
            return 0.0f;
        }
        if (this.anInt3 < horizontalFadingEdgeLength) {
            return ((float) this.anInt3) / ((float) horizontalFadingEdgeLength);
        }
        return 1.0f;
    }


    public float getRightFadingEdgeStrength() {
        int horizontalFadingEdgeLength = getHorizontalFadingEdgeLength();
        if (this.anInt3 == this.maxValue) {
            return 0.0f;
        }
        if (this.maxValue - this.anInt3 < horizontalFadingEdgeLength) {
            return ((float) (this.maxValue - this.anInt3)) / ((float) horizontalFadingEdgeLength);
        }
        return 1.0f;
    }

    private float d() {
        if (VERSION.SDK_INT >= 14) {
            return c.a(this.mFlingTracker);
        }
        return 30.0f;
    }


    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        this.anInt5 = i3;
    }

    private boolean e() {
        if (!h(this.anInt7)) {
            return false;
        }
        View rightmostChild = getRightmostChild();
        if (rightmostChild == null) {
            return false;
        }
        int i2 = this.maxValue;
        this.maxValue = (this.anInt3 + (rightmostChild.getRight() - getPaddingLeft())) - getRenderWidth();
        if (this.maxValue < 0) {
            this.maxValue = 0;
        }
        return this.maxValue != i2;
    }

    private void d(int i2) {
        View rightmostChild = getRightmostChild();
        int i3 = 0;
        a(rightmostChild != null ? rightmostChild.getRight() : 0, i2);
        View leftmostChild = getLeftmostChild();
        if (leftmostChild != null) {
            i3 = leftmostChild.getLeft();
        }
        b(i3, i2);
    }

    private void e(int i2) {
        int i3;
        View leftmostChild = getLeftmostChild();
        while (leftmostChild != null && leftmostChild.getRight() + i2 <= 0) {
            int i4 = this.anInt1;
            if (h(this.anInt6)) {
                i3 = leftmostChild.getMeasuredWidth();
            } else {
                i3 = this.anInt2 + leftmostChild.getMeasuredWidth();
            }
            this.anInt1 = i3 + i4;
            a(this.anInt6, leftmostChild);
            removeViewInLayout(leftmostChild);
            this.anInt6++;
            leftmostChild = getLeftmostChild();
        }
        View rightmostChild = getRightmostChild();
        while (rightmostChild != null && rightmostChild.getLeft() + i2 >= getWidth()) {
            a(this.anInt7, rightmostChild);
            removeViewInLayout(rightmostChild);
            this.anInt7--;
            rightmostChild = getRightmostChild();
        }
    }

    private void a(int i2, int i3) {
        while (i2 + i3 + this.anInt2 < getWidth() && this.anInt7 + 1 < this.mAdapter.getCount()) {
            this.anInt7++;
            if (this.anInt6 < 0) {
                this.anInt6 = this.anInt7;
            }
            View view = this.mAdapter.getView(this.anInt7, b(this.anInt7), this);
            a(view, -1);
            i2 += (this.anInt7 == 0 ? 0 : this.anInt2) + view.getMeasuredWidth();
            h();
        }
    }

    private void b(int i2, int i3) {
        int i4;
        int i5;
        while ((i2 + i3) - this.anInt2 > 0 && this.anInt6 >= 1) {
            this.anInt6--;
            View view = this.mAdapter.getView(this.anInt6, b(this.anInt6), this);
            a(view, 0);
            if (this.anInt6 == 0) {
                i4 = view.getMeasuredWidth();
            } else {
                i4 = this.anInt2 + view.getMeasuredWidth();
            }
            i2 -= i4;
            int i6 = this.anInt1;
            if (i2 + i3 == 0) {
                i5 = view.getMeasuredWidth();
            } else {
                i5 = view.getMeasuredWidth() + this.anInt2;
            }
            this.anInt1 = i6 - i5;
        }
    }

    private void f(int i2) {
        int childCount = getChildCount();
        if (childCount > 0) {
            this.anInt1 += i2;
            int i3 = this.anInt1;
            for (int i4 = 0; i4 < childCount; i4++) {
                View childAt = getChildAt(i4);
                int paddingLeft = getPaddingLeft() + i3;
                int paddingTop = getPaddingTop();
                childAt.layout(paddingLeft, paddingTop, childAt.getMeasuredWidth() + paddingLeft, childAt.getMeasuredHeight() + paddingTop);
                i3 += childAt.getMeasuredWidth() + this.anInt2;
            }
        }
    }

    private View getLeftmostChild() {
        return getChildAt(0);
    }

    private View getRightmostChild() {
        return getChildAt(getChildCount() - 1);
    }

    private View g(int i2) {
        if (i2 < this.anInt6 || i2 > this.anInt7) {
            return null;
        }
        return getChildAt(i2 - this.anInt6);
    }


    public int c(int i2, int i3) {
        int childCount = getChildCount();
        for (int i4 = 0; i4 < childCount; i4++) {
            getChildAt(i4).getHitRect(this.rect);
            if (this.rect.contains(i2, i3)) {
                return i4;
            }
        }
        return -1;
    }

    private boolean h(int i2) {
        return i2 == this.mAdapter.getCount() + -1;
    }

    private int getRenderHeight() {
        return (getHeight() - getPaddingTop()) - getPaddingBottom();
    }

    private int getRenderWidth() {
        return (getWidth() - getPaddingLeft()) - getPaddingRight();
    }

    public void scrollTo(int i2) {
        this.mFlingTracker.startScroll(this.anInt4, 0, i2 - this.anInt4, 0);
        setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_FLING);
        requestLayout();
    }

    public int getFirstVisiblePosition() {
        return this.anInt6;
    }

    public int getLastVisiblePosition() {
        return this.anInt7;
    }

    private void a(Canvas canvas) {
        if (this.edgeEffectCompat != null && !this.edgeEffectCompat.isFinished() && i()) {
            int save = canvas.save();
            int height = getHeight();
            canvas.rotate(-90.0f, 0.0f, 0.0f);
            canvas.translate((float) ((-height) + getPaddingBottom()), 0.0f);
            this.edgeEffectCompat.setSize(getRenderHeight(), getRenderWidth());
            if (this.edgeEffectCompat.draw(canvas)) {
                invalidate();
            }
            canvas.restoreToCount(save);
        } else if (this.edgeEffectCompat1 != null && !this.edgeEffectCompat1.isFinished() && i()) {
            int save2 = canvas.save();
            int width = getWidth();
            canvas.rotate(90.0f, 0.0f, 0.0f);
            canvas.translate((float) getPaddingTop(), (float) (-width));
            this.edgeEffectCompat1.setSize(getRenderHeight(), getRenderWidth());
            if (this.edgeEffectCompat1.draw(canvas)) {
                invalidate();
            }
            canvas.restoreToCount(save2);
        }
    }

    private void b(Canvas canvas) {
        int childCount = getChildCount();
        Rect rect = this.rect;
        this.rect.top = getPaddingTop();
        this.rect.bottom = this.rect.top + getRenderHeight();
        for (int i2 = 0; i2 < childCount; i2++) {
            if (i2 != childCount - 1 || !h(this.anInt7)) {
                View childAt = getChildAt(i2);
                rect.left = childAt.getRight();
                rect.right = childAt.getRight() + this.anInt2;
                if (rect.left < getPaddingLeft()) {
                    rect.left = getPaddingLeft();
                }
                if (rect.right > getWidth() - getPaddingRight()) {
                    rect.right = getWidth() - getPaddingRight();
                }
                a(canvas, rect);
                if (i2 == 0 && childAt.getLeft() > getPaddingLeft()) {
                    rect.left = getPaddingLeft();
                    rect.right = childAt.getLeft();
                    a(canvas, rect);
                }
            }
        }
    }

    private void a(Canvas canvas, Rect rect) {
        if (this.aNull != null) {
            this.aNull.setBounds(rect);
            this.aNull.draw(canvas);
        }
    }


    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        b(canvas);
    }


    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        a(canvas);
    }


    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f2, float f3) {
        this.mFlingTracker.fling(this.anInt4, 0, (int) (-f2), 0, 0, this.maxValue, 0, 0);
        setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_FLING);
        requestLayout();
        return true;
    }


    public boolean onDown(MotionEvent motionEvent) {
        this.aBoolean = !this.mFlingTracker.isFinished();
        this.mFlingTracker.forceFinished(true);
        setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_IDLE);
        f();
        if (!this.aBoolean) {
            int c2 = c((int) motionEvent.getX(), (int) motionEvent.getY());
            if (c2 >= 0) {
                this.view = getChildAt(c2);
                if (this.view != null) {
                    this.view.setPressed(true);
                    refreshDrawableState();
                }
            }
        }
        return true;
    }


    public void f() {
        if (this.view != null) {
            this.view.setPressed(false);
            refreshDrawableState();
            this.view = null;
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1) {
            if (this.mFlingTracker == null || this.mFlingTracker.isFinished()) {
                setCurrentScrollState(OnScrollStateChangedListener.ScrollState.SCROLL_STATE_IDLE);
            }
            a(Boolean.valueOf(false));
            g();
        } else if (motionEvent.getAction() == 3) {
            f();
            g();
            a(Boolean.valueOf(false));
        }
        return super.onTouchEvent(motionEvent);
    }

    private void g() {
        if (this.edgeEffectCompat != null) {
            this.edgeEffectCompat.onRelease();
        }
        if (this.edgeEffectCompat1 != null) {
            this.edgeEffectCompat1.onRelease();
        }
    }

    private void h() {
        if (this.aNull3 != null && this.mAdapter != null && this.mAdapter.getCount() - (this.anInt7 + 1) < this.anInt8 && !this.aBoolean2) {
            this.aBoolean2 = true;
            this.aNull3.onRunningOutOfData();
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    public void setCurrentScrollState(OnScrollStateChangedListener.ScrollState scrollState) {
        if (!(this.scrollStateIdle == scrollState || this.aNull1 == null)) {
            this.aNull1.onScrollStateChanged(scrollState);
        }
        this.scrollStateIdle = scrollState;
    }


    public void i(int i2) {
        if (!(this.edgeEffectCompat == null || this.edgeEffectCompat1 == null)) {
            try {
                int i3 = this.anInt3 + i2;
                if (this.mFlingTracker != null && !this.mFlingTracker.isFinished()) {
                    return;
                }
                if (i3 < 0) {
                    try {
                        this.edgeEffectCompat.onPull(((float) Math.abs(i2)) / ((float) getRenderWidth()));
                        if (!this.edgeEffectCompat1.isFinished()) {
                            try {
                                this.edgeEffectCompat1.onRelease();
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                        }
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                } else if (i3 > this.maxValue) {
                    try {
                        this.edgeEffectCompat1.onPull(((float) Math.abs(i2)) / ((float) getRenderWidth()));
                        if (!this.edgeEffectCompat.isFinished()) {
                            try {
                                this.edgeEffectCompat.onRelease();
                            } catch (Exception e4) {
                                e4.printStackTrace();
                            }
                        }
                    } catch (Exception e5) {
                        e5.printStackTrace();
                    }
                }
            } catch (Exception e6) {
                e6.printStackTrace();
            }
        }
    }

    private boolean i() {
        return this.mAdapter != null && !this.mAdapter.isEmpty() && this.maxValue > 0;
    }
}
