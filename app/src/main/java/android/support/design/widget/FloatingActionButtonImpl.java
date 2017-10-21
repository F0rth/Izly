package android.support.design.widget;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.support.design.R;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;

abstract class FloatingActionButtonImpl {
    static final int[] EMPTY_STATE_SET = new int[0];
    static final int[] FOCUSED_ENABLED_STATE_SET = new int[]{16842908, 16842910};
    static final int[] PRESSED_ENABLED_STATE_SET = new int[]{16842919, 16842910};
    final ShadowViewDelegate mShadowViewDelegate;
    final View mView;

    FloatingActionButtonImpl(View view, ShadowViewDelegate shadowViewDelegate) {
        this.mView = view;
        this.mShadowViewDelegate = shadowViewDelegate;
    }

    Drawable createBorderDrawable(int i, ColorStateList colorStateList) {
        Resources resources = this.mView.getResources();
        Drawable newCircularDrawable = newCircularDrawable();
        newCircularDrawable.setGradientColors(resources.getColor(R.color.fab_stroke_top_outer_color), resources.getColor(R.color.fab_stroke_top_inner_color), resources.getColor(R.color.fab_stroke_end_inner_color), resources.getColor(R.color.fab_stroke_end_outer_color));
        newCircularDrawable.setBorderWidth((float) i);
        Drawable wrap = DrawableCompat.wrap(newCircularDrawable);
        DrawableCompat.setTintList(wrap, colorStateList);
        DrawableCompat.setTintMode(wrap, Mode.DST_OVER);
        return wrap;
    }

    abstract void jumpDrawableToCurrentState();

    CircularBorderDrawable newCircularDrawable() {
        return new CircularBorderDrawable();
    }

    abstract void onDrawableStateChanged(int[] iArr);

    abstract void setBackgroundDrawable(Drawable drawable, ColorStateList colorStateList, Mode mode, int i, int i2);

    abstract void setBackgroundTintList(ColorStateList colorStateList);

    abstract void setBackgroundTintMode(Mode mode);

    abstract void setElevation(float f);

    abstract void setPressedTranslationZ(float f);

    abstract void setRippleColor(int i);
}
