package android.support.design.widget;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;

class CircularBorderDrawableLollipop extends CircularBorderDrawable {
    private ColorStateList mTint;
    private PorterDuffColorFilter mTintFilter;
    private Mode mTintMode = Mode.SRC_IN;

    CircularBorderDrawableLollipop() {
    }

    private PorterDuffColorFilter updateTintFilter(ColorStateList colorStateList, Mode mode) {
        return (colorStateList == null || mode == null) ? null : new PorterDuffColorFilter(colorStateList.getColorForState(getState(), 0), mode);
    }

    public void draw(Canvas canvas) {
        Object obj;
        if (this.mTintFilter == null || this.mPaint.getColorFilter() != null) {
            obj = null;
        } else {
            this.mPaint.setColorFilter(this.mTintFilter);
            obj = 1;
        }
        super.draw(canvas);
        if (obj != null) {
            this.mPaint.setColorFilter(null);
        }
    }

    public void setTintList(ColorStateList colorStateList) {
        this.mTint = colorStateList;
        this.mTintFilter = updateTintFilter(colorStateList, this.mTintMode);
        invalidateSelf();
    }

    public void setTintMode(Mode mode) {
        this.mTintMode = mode;
        this.mTintFilter = updateTintFilter(this.mTint, mode);
        invalidateSelf();
    }
}
