package android.support.v7.widget;

import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.view.View;

public abstract class OrientationHelper {
    public static final int HORIZONTAL = 0;
    private static final int INVALID_SIZE = Integer.MIN_VALUE;
    public static final int VERTICAL = 1;
    private int mLastTotalSpace;
    protected final LayoutManager mLayoutManager;

    private OrientationHelper(LayoutManager layoutManager) {
        this.mLastTotalSpace = Integer.MIN_VALUE;
        this.mLayoutManager = layoutManager;
    }

    public static OrientationHelper createHorizontalHelper(LayoutManager layoutManager) {
        return new OrientationHelper(layoutManager) {
            public final int getDecoratedEnd(View view) {
                LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                return layoutParams.rightMargin + this.mLayoutManager.getDecoratedRight(view);
            }

            public final int getDecoratedMeasurement(View view) {
                LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                return layoutParams.rightMargin + (this.mLayoutManager.getDecoratedMeasuredWidth(view) + layoutParams.leftMargin);
            }

            public final int getDecoratedMeasurementInOther(View view) {
                LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                return layoutParams.bottomMargin + (this.mLayoutManager.getDecoratedMeasuredHeight(view) + layoutParams.topMargin);
            }

            public final int getDecoratedStart(View view) {
                return this.mLayoutManager.getDecoratedLeft(view) - ((LayoutParams) view.getLayoutParams()).leftMargin;
            }

            public final int getEnd() {
                return this.mLayoutManager.getWidth();
            }

            public final int getEndAfterPadding() {
                return this.mLayoutManager.getWidth() - this.mLayoutManager.getPaddingRight();
            }

            public final int getEndPadding() {
                return this.mLayoutManager.getPaddingRight();
            }

            public final int getMode() {
                return this.mLayoutManager.getWidthMode();
            }

            public final int getModeInOther() {
                return this.mLayoutManager.getHeightMode();
            }

            public final int getStartAfterPadding() {
                return this.mLayoutManager.getPaddingLeft();
            }

            public final int getTotalSpace() {
                return (this.mLayoutManager.getWidth() - this.mLayoutManager.getPaddingLeft()) - this.mLayoutManager.getPaddingRight();
            }

            public final void offsetChild(View view, int i) {
                view.offsetLeftAndRight(i);
            }

            public final void offsetChildren(int i) {
                this.mLayoutManager.offsetChildrenHorizontal(i);
            }
        };
    }

    public static OrientationHelper createOrientationHelper(LayoutManager layoutManager, int i) {
        switch (i) {
            case 0:
                return createHorizontalHelper(layoutManager);
            case 1:
                return createVerticalHelper(layoutManager);
            default:
                throw new IllegalArgumentException("invalid orientation");
        }
    }

    public static OrientationHelper createVerticalHelper(LayoutManager layoutManager) {
        return new OrientationHelper(layoutManager) {
            public final int getDecoratedEnd(View view) {
                LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                return layoutParams.bottomMargin + this.mLayoutManager.getDecoratedBottom(view);
            }

            public final int getDecoratedMeasurement(View view) {
                LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                return layoutParams.bottomMargin + (this.mLayoutManager.getDecoratedMeasuredHeight(view) + layoutParams.topMargin);
            }

            public final int getDecoratedMeasurementInOther(View view) {
                LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                return layoutParams.rightMargin + (this.mLayoutManager.getDecoratedMeasuredWidth(view) + layoutParams.leftMargin);
            }

            public final int getDecoratedStart(View view) {
                return this.mLayoutManager.getDecoratedTop(view) - ((LayoutParams) view.getLayoutParams()).topMargin;
            }

            public final int getEnd() {
                return this.mLayoutManager.getHeight();
            }

            public final int getEndAfterPadding() {
                return this.mLayoutManager.getHeight() - this.mLayoutManager.getPaddingBottom();
            }

            public final int getEndPadding() {
                return this.mLayoutManager.getPaddingBottom();
            }

            public final int getMode() {
                return this.mLayoutManager.getHeightMode();
            }

            public final int getModeInOther() {
                return this.mLayoutManager.getWidthMode();
            }

            public final int getStartAfterPadding() {
                return this.mLayoutManager.getPaddingTop();
            }

            public final int getTotalSpace() {
                return (this.mLayoutManager.getHeight() - this.mLayoutManager.getPaddingTop()) - this.mLayoutManager.getPaddingBottom();
            }

            public final void offsetChild(View view, int i) {
                view.offsetTopAndBottom(i);
            }

            public final void offsetChildren(int i) {
                this.mLayoutManager.offsetChildrenVertical(i);
            }
        };
    }

    public abstract int getDecoratedEnd(View view);

    public abstract int getDecoratedMeasurement(View view);

    public abstract int getDecoratedMeasurementInOther(View view);

    public abstract int getDecoratedStart(View view);

    public abstract int getEnd();

    public abstract int getEndAfterPadding();

    public abstract int getEndPadding();

    public abstract int getMode();

    public abstract int getModeInOther();

    public abstract int getStartAfterPadding();

    public abstract int getTotalSpace();

    public int getTotalSpaceChange() {
        return Integer.MIN_VALUE == this.mLastTotalSpace ? 0 : getTotalSpace() - this.mLastTotalSpace;
    }

    public abstract void offsetChild(View view, int i);

    public abstract void offsetChildren(int i);

    public void onLayoutComplete() {
        this.mLastTotalSpace = getTotalSpace();
    }
}
