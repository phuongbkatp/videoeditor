package com.video_editor.pro.UtilsAndAdapters.CollageStickers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.video_editor.pro.R;
import com.video_editor.pro.UtilsAndAdapters.EditorStyleable;

@SuppressLint({"WrongConstant"})
public class CollageSingleFingerView extends LinearLayout {
    private boolean aBoolean;
    private boolean aBoolean1;
    private int anInt;
    private int anInt1;
    private Drawable drawable;
    private Drawable drawable1;
    private Drawable drawable2;
    private float aFloat;
    private float aFloat1;
    private float aFloat2;
    private float aFloat3;
    private float aFloat4;
    public ImageView mPushDelete;
    public ImageView mPushView;
    public ImageView mView;

    public CollageSingleFingerView(Context context) {
        this(context, null, 0);
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
        this.aFloat1 = (float) (this.drawable.getIntrinsicWidth() + 100);
        this.aFloat2 = (float) (this.drawable.getIntrinsicHeight() + 100);
        a(1000, 1000);
    }

    public Drawable getBitmapDrawable() {
        return this.drawable;
    }

    public float getRotation() {
        return this.mView.getRotation();
    }

    public float getImageX() {
        return (float) ((LayoutParams) this.mView.getLayoutParams()).leftMargin;
    }

    public float getImageY() {
        return (float) ((LayoutParams) this.mView.getLayoutParams()).topMargin;
    }

    public int getImageWidth() {
        return ((LayoutParams) this.mView.getLayoutParams()).width;
    }

    public int getImageHeight() {
        return ((LayoutParams) this.mView.getLayoutParams()).height;
    }

    public CollageSingleFingerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public CollageSingleFingerView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.aBoolean = true;
        this.anInt1 = 0;
        this.anInt = 0;
        this.aBoolean1 = false;
        this.aFloat = TypedValue.applyDimension(1, 1.0f, context.getResources().getDisplayMetrics());
        a(context, attributeSet);
        View inflate = View.inflate(context, R.layout.test_image_view_with_delete, null);
        addView(inflate, -1, -1);
        this.mPushView = inflate.findViewById(R.id.push_view);
        this.mPushDelete = inflate.findViewById(R.id.push_delete);
        this.mView = inflate.findViewById(R.id.view);
        this.mPushView.setOnTouchListener(new b(this.mView));
        this.mPushDelete.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                
                return true;
            }
        });
        this.mView.setOnTouchListener(new a(this.mPushView, this.mPushDelete));
    }

    private void a(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, EditorStyleable.SingleFingerView);
            if (obtainStyledAttributes != null) {
                try {
                    int indexCount = obtainStyledAttributes.getIndexCount();
                    int i2 = 0;
                    while (i2 < indexCount) {
                        try {
                            int index = obtainStyledAttributes.getIndex(i2);
                            if (index == 9) {
                                try {
                                    this.aBoolean = obtainStyledAttributes.getBoolean(index, true);
                                } catch (Exception e2) {
                                    e2.printStackTrace();
                                }
                                i2++;
                            } else if (index == 0) {
                                try {
                                    this.drawable = obtainStyledAttributes.getDrawable(index);
                                } catch (Exception e3) {
                                    e3.printStackTrace();
                                }
                                i2++;
                            } else if (index == 2) {
                                try {
                                    this.aFloat2 = obtainStyledAttributes.getDimension(index, this.aFloat * 200.0f);
                                } catch (Exception e4) {
                                    e4.printStackTrace();
                                }
                                i2++;
                            } else if (index == 1) {
                                try {
                                    this.aFloat1 = obtainStyledAttributes.getDimension(index, this.aFloat * 200.0f);
                                } catch (Exception e5) {
                                    e5.printStackTrace();
                                }
                                i2++;
                            } else if (index == 3) {
                                try {
                                    this.drawable1 = obtainStyledAttributes.getDrawable(index);
                                } catch (Exception e6) {
                                    e6.printStackTrace();
                                }
                                i2++;
                            } else if (index == 4) {
                                try {
                                    this.drawable2 = obtainStyledAttributes.getDrawable(index);
                                } catch (Exception e7) {
                                    e7.printStackTrace();
                                }
                                i2++;
                            } else if (index == 5) {
                                try {
                                    this.aFloat3 = obtainStyledAttributes.getDimension(index, this.aFloat * 50.0f);
                                } catch (Exception e8) {
                                    e8.printStackTrace();
                                }
                                i2++;
                            } else if (index == 6) {
                                try {
                                    this.aFloat4 = obtainStyledAttributes.getDimension(index, this.aFloat * 50.0f);
                                } catch (Exception e9) {
                                    e9.printStackTrace();
                                }
                                i2++;
                            } else if (index == 8) {
                                try {
                                    this.anInt1 = (int) obtainStyledAttributes.getDimension(index, this.aFloat * 0.0f);
                                } catch (Exception e10) {
                                    e10.printStackTrace();
                                }
                                i2++;
                            } else {
                                if (index == 7) {
                                    try {
                                        this.anInt = (int) obtainStyledAttributes.getDimension(index, this.aFloat * 0.0f);
                                    } catch (Exception e11) {
                                        e11.printStackTrace();
                                    }
                                }
                                i2++;
                            }
                        } catch (Exception e12) {
                            e12.printStackTrace();
                        }
                    }
                } catch (Exception e13) {
                    e13.printStackTrace();
                }
            }
        }
    }

    private void a(int i2, int i3) {
        int i4;
        int i5;
        if (this.drawable != null) {
            try {
                this.mView.setBackgroundDrawable(this.drawable);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        if (this.drawable2 != null) {
            try {
                this.mPushDelete.setBackgroundDrawable(this.drawable2);
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
        if (this.drawable1 != null) {
            try {
                this.mPushView.setBackgroundDrawable(this.drawable1);
            } catch (Exception e4) {
                e4.printStackTrace();
            }
        }
        LayoutParams layoutParams = (LayoutParams) this.mView.getLayoutParams();
        layoutParams.width = (int) this.aFloat1;
        layoutParams.height = (int) this.aFloat2;
        int i6 = 0;
        if (this.aBoolean) {
            try {
                i4 = (i2 / 2) - (layoutParams.width / 2);
                try {
                    i6 = (i3 / 2) - (layoutParams.height / 2);
                } catch (Exception e5) {
                    layoutParams.leftMargin = i4;
                    layoutParams.topMargin = i6;
                    this.mView.setLayoutParams(layoutParams);
                    LayoutParams layoutParams2 = (LayoutParams) this.mPushView.getLayoutParams();
                    layoutParams2.width = (int) this.aFloat3;
                    layoutParams2.height = (int) this.aFloat4;
                    layoutParams2.leftMargin = (int) ((((float) layoutParams.leftMargin) + this.aFloat1) - (this.aFloat3 / 2.0f));
                    layoutParams2.topMargin = (int) ((((float) layoutParams.topMargin) + this.aFloat2) - (this.aFloat4 / 2.0f));
                    this.mPushView.setLayoutParams(layoutParams2);
                    LayoutParams layoutParams3 = (LayoutParams) this.mPushDelete.getLayoutParams();
                    layoutParams3.width = (int) this.aFloat3;
                    layoutParams3.height = (int) this.aFloat4;
                    layoutParams3.leftMargin = (int) (((float) (layoutParams.leftMargin + layoutParams.width)) - (this.aFloat3 / 2.0f));
                    layoutParams3.topMargin = (int) (((float) layoutParams.topMargin) + (this.aFloat4 / 2.0f));
                    this.mPushDelete.setLayoutParams(layoutParams3);
                }
            } catch (Exception e6) {
                i4 = 0;
                layoutParams.leftMargin = i4;
                layoutParams.topMargin = i6;
                this.mView.setLayoutParams(layoutParams);
                LayoutParams layoutParams22 = (LayoutParams) this.mPushView.getLayoutParams();
                layoutParams22.width = (int) this.aFloat3;
                layoutParams22.height = (int) this.aFloat4;
                layoutParams22.leftMargin = (int) ((((float) layoutParams.leftMargin) + this.aFloat1) - (this.aFloat3 / 2.0f));
                layoutParams22.topMargin = (int) ((((float) layoutParams.topMargin) + this.aFloat2) - (this.aFloat4 / 2.0f));
                this.mPushView.setLayoutParams(layoutParams22);
                LayoutParams layoutParams32 = (LayoutParams) this.mPushDelete.getLayoutParams();
                layoutParams32.width = (int) this.aFloat3;
                layoutParams32.height = (int) this.aFloat4;
                layoutParams32.leftMargin = (int) (((float) (layoutParams.leftMargin + layoutParams.width)) - (this.aFloat3 / 2.0f));
                layoutParams32.topMargin = (int) (((float) layoutParams.topMargin) + (this.aFloat4 / 2.0f));
                this.mPushDelete.setLayoutParams(layoutParams32);
            }
        } else {
            try {
                if (this.anInt1 > 0) {
                    try {
                        i4 = this.anInt1;
                    } catch (Exception e7) {
                        e7.printStackTrace();
                    }
                    if (this.anInt > 0) {
                        try {
                            i6 = this.anInt;
                        } catch (Exception e8) {
                            e8.printStackTrace();
                        }
                    }
                }
                i4 = 0;
                try {
                    if (this.anInt > 0) {
                    }
                } catch (Exception e9) {
                    Exception exc = e9;
                    i5 = i4;
                }
            } catch (Exception e10) {
                i5 = 0;
                i4 = i5;
                layoutParams.leftMargin = i4;
                layoutParams.topMargin = i6;
                this.mView.setLayoutParams(layoutParams);
                LayoutParams layoutParams222 = (LayoutParams) this.mPushView.getLayoutParams();
                layoutParams222.width = (int) this.aFloat3;
                layoutParams222.height = (int) this.aFloat4;
                layoutParams222.leftMargin = (int) ((((float) layoutParams.leftMargin) + this.aFloat1) - (this.aFloat3 / 2.0f));
                layoutParams222.topMargin = (int) ((((float) layoutParams.topMargin) + this.aFloat2) - (this.aFloat4 / 2.0f));
                this.mPushView.setLayoutParams(layoutParams222);
                LayoutParams layoutParams322 = (LayoutParams) this.mPushDelete.getLayoutParams();
                layoutParams322.width = (int) this.aFloat3;
                layoutParams322.height = (int) this.aFloat4;
                layoutParams322.leftMargin = (int) (((float) (layoutParams.leftMargin + layoutParams.width)) - (this.aFloat3 / 2.0f));
                layoutParams322.topMargin = (int) (((float) layoutParams.topMargin) + (this.aFloat4 / 2.0f));
                this.mPushDelete.setLayoutParams(layoutParams322);
            }
        }
        layoutParams.leftMargin = i4;
        layoutParams.topMargin = i6;
        this.mView.setLayoutParams(layoutParams);
        LayoutParams layoutParams2222 = (LayoutParams) this.mPushView.getLayoutParams();
        layoutParams2222.width = (int) this.aFloat3;
        layoutParams2222.height = (int) this.aFloat4;
        layoutParams2222.leftMargin = (int) ((((float) layoutParams.leftMargin) + this.aFloat1) - (this.aFloat3 / 2.0f));
        layoutParams2222.topMargin = (int) ((((float) layoutParams.topMargin) + this.aFloat2) - (this.aFloat4 / 2.0f));
        this.mPushView.setLayoutParams(layoutParams2222);
        LayoutParams layoutParams3222 = (LayoutParams) this.mPushDelete.getLayoutParams();
        layoutParams3222.width = (int) this.aFloat3;
        layoutParams3222.height = (int) this.aFloat4;
        layoutParams3222.leftMargin = (int) (((float) (layoutParams.leftMargin + layoutParams.width)) - (this.aFloat3 / 2.0f));
        layoutParams3222.topMargin = (int) (((float) layoutParams.topMargin) + (this.aFloat4 / 2.0f));
        this.mPushDelete.setLayoutParams(layoutParams3222);
    }


    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        b(i2, i3);
    }

    private void b(int i2, int i3) {
        int i4;
        int i5 = 0;
        if (getLayoutParams() != null && !this.aBoolean1) {
            try {
                this.aBoolean1 = true;
                if (getLayoutParams().width == -1) {
                    try {
                        i4 = MeasureSpec.getSize(i2);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        i4 = 0;
                        if (getLayoutParams().height == -1) {
                        }
                        a(i4, i5);
                    }
                } else {
                    try {
                        i4 = getLayoutParams().width;
                    } catch (Exception e3) {
                        e3.printStackTrace();
                        i4 = 0;
                        if (getLayoutParams().height == -1) {
                        }
                        a(i4, i5);
                    }
                }
                if (getLayoutParams().height == -1) {
                    try {
                        i5 = MeasureSpec.getSize(i3);
                    } catch (Exception e4) {
                        e4.printStackTrace();
                        i5 = 0;
                        a(i4, i5);
                    }
                } else {
                    try {
                        i5 = getLayoutParams().height;
                    } catch (Exception e5) {
                        e5.printStackTrace();
                        i5 = 0;
                        a(i4, i5);
                    }
                }
                a(i4, i5);
            } catch (Exception e6) {
                e6.printStackTrace();
            }
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            hidePushView();
        }
        return super.onTouchEvent(motionEvent);
    }

    public void hidePushView() {
        this.mPushView.setVisibility(8);
        this.mPushDelete.setVisibility(8);
    }

    public void showPushView() {
        this.mPushView.setVisibility(0);
        this.mPushDelete.setVisibility(0);
    }
}
