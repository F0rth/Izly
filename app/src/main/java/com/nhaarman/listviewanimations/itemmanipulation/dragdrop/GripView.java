package com.nhaarman.listviewanimations.itemmanipulation.dragdrop;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.View.MeasureSpec;

public class GripView extends View {
    private static final int[] ATTRS = new int[]{16843173};
    public static final int DEFAULT_COLUMN_COUNT = 2;
    public static final int DEFAULT_DOT_COLOR = 17170432;
    public static final float DEFAULT_DOT_SIZE_RADIUS_DP = 2.0f;
    private int mColumnCount;
    private final Paint mDotPaint;
    private float mDotSizeRadiusPx;
    private float mPaddingTop;
    private int mRowCount;

    public GripView(@NonNull Context context) {
        this(context, null);
    }

    public GripView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public GripView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mColumnCount = 2;
        this.mDotPaint = new Paint(1);
        int color = getResources().getColor(DEFAULT_DOT_COLOR);
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, ATTRS);
            color = obtainStyledAttributes.getColor(0, color);
            obtainStyledAttributes.recycle();
        }
        this.mDotPaint.setColor(color);
        this.mDotSizeRadiusPx = (float) ((int) TypedValue.applyDimension(1, DEFAULT_DOT_SIZE_RADIUS_DP, context.getResources().getDisplayMetrics()));
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < this.mColumnCount; i++) {
            float paddingLeft = (float) getPaddingLeft();
            float f = (float) (i * 2);
            float f2 = this.mDotSizeRadiusPx;
            for (int i2 = 0; i2 < this.mRowCount; i2++) {
                canvas.drawCircle(this.mDotSizeRadiusPx + (((f * f2) * DEFAULT_DOT_SIZE_RADIUS_DP) + paddingLeft), (this.mPaddingTop + ((((float) (i2 * 2)) * this.mDotSizeRadiusPx) * DEFAULT_DOT_SIZE_RADIUS_DP)) + this.mDotSizeRadiusPx, this.mDotSizeRadiusPx, this.mDotPaint);
            }
        }
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(MeasureSpec.makeMeasureSpec((getPaddingLeft() + getPaddingRight()) + ((int) (((float) this.mColumnCount) * ((this.mDotSizeRadiusPx * 4.0f) - DEFAULT_DOT_SIZE_RADIUS_DP))), 1073741824), i2);
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.mRowCount = (int) (((float) ((i2 - getPaddingTop()) - getPaddingBottom())) / (this.mDotSizeRadiusPx * 4.0f));
        this.mPaddingTop = ((((float) i2) - ((((float) this.mRowCount) * this.mDotSizeRadiusPx) * DEFAULT_DOT_SIZE_RADIUS_DP)) - ((((float) (this.mRowCount - 1)) * this.mDotSizeRadiusPx) * DEFAULT_DOT_SIZE_RADIUS_DP)) / DEFAULT_DOT_SIZE_RADIUS_DP;
    }

    public void setColor(int i) {
        this.mDotPaint.setColor(getResources().getColor(i));
    }

    public void setColumnCount(int i) {
        this.mColumnCount = i;
        requestLayout();
    }

    public void setDotSizeRadiusPx(float f) {
        this.mDotSizeRadiusPx = f;
    }
}
