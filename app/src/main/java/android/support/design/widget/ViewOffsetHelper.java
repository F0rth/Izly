package android.support.design.widget;

import android.os.Build.VERSION;
import android.support.v4.view.ViewCompat;
import android.view.View;

class ViewOffsetHelper {
    private int mLayoutLeft;
    private int mLayoutTop;
    private int mOffsetLeft;
    private int mOffsetTop;
    private final View mView;

    public ViewOffsetHelper(View view) {
        this.mView = view;
    }

    private void updateOffsets() {
        if (VERSION.SDK_INT == 22) {
            ViewCompat.setTranslationY(this.mView, (float) this.mOffsetTop);
            ViewCompat.setTranslationX(this.mView, (float) this.mOffsetLeft);
            return;
        }
        ViewCompat.offsetTopAndBottom(this.mView, (this.mOffsetTop - this.mView.getTop()) - this.mLayoutTop);
        ViewCompat.offsetLeftAndRight(this.mView, (this.mOffsetLeft - this.mView.getLeft()) - this.mLayoutLeft);
    }

    public int getLeftAndRightOffset() {
        return this.mOffsetLeft;
    }

    public int getTopAndBottomOffset() {
        return this.mOffsetTop;
    }

    public void onViewLayout() {
        this.mLayoutTop = this.mView.getTop();
        this.mLayoutLeft = this.mView.getLeft();
        updateOffsets();
    }

    public boolean setLeftAndRightOffset(int i) {
        if (this.mOffsetLeft == i) {
            return false;
        }
        this.mOffsetLeft = i;
        updateOffsets();
        return true;
    }

    public boolean setTopAndBottomOffset(int i) {
        if (this.mOffsetTop == i) {
            return false;
        }
        this.mOffsetTop = i;
        updateOffsets();
        return true;
    }
}
