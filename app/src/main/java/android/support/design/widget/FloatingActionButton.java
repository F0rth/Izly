package android.support.design.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.support.design.R;
import android.support.design.widget.CoordinatorLayout.DefaultBehavior;
import android.support.design.widget.Snackbar.SnackbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import java.util.List;

@DefaultBehavior(Behavior.class)
public class FloatingActionButton extends ImageView {
    private static final int SIZE_MINI = 1;
    private static final int SIZE_NORMAL = 0;
    private ColorStateList mBackgroundTint;
    private Mode mBackgroundTintMode;
    private int mBorderWidth;
    private int mContentPadding;
    private final FloatingActionButtonImpl mImpl;
    private int mRippleColor;
    private final Rect mShadowPadding;
    private int mSize;

    public static class Behavior extends android.support.design.widget.CoordinatorLayout.Behavior<FloatingActionButton> {
        private static final boolean SNACKBAR_BEHAVIOR_ENABLED = (VERSION.SDK_INT >= 11);
        private boolean mIsAnimatingOut;
        private Rect mTmpRect;
        private float mTranslationY;

        private void animateIn(FloatingActionButton floatingActionButton) {
            floatingActionButton.setVisibility(0);
            if (VERSION.SDK_INT >= 14) {
                ViewCompat.animate(floatingActionButton).scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR).withLayer().setListener(null).start();
                return;
            }
            Animation loadAnimation = AnimationUtils.loadAnimation(floatingActionButton.getContext(), R.anim.fab_in);
            loadAnimation.setDuration(200);
            loadAnimation.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
            floatingActionButton.startAnimation(loadAnimation);
        }

        private void animateOut(final FloatingActionButton floatingActionButton) {
            if (VERSION.SDK_INT >= 14) {
                ViewCompat.animate(floatingActionButton).scaleX(0.0f).scaleY(0.0f).alpha(0.0f).setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR).withLayer().setListener(new ViewPropertyAnimatorListener() {
                    public void onAnimationCancel(View view) {
                        Behavior.this.mIsAnimatingOut = false;
                    }

                    public void onAnimationEnd(View view) {
                        Behavior.this.mIsAnimatingOut = false;
                        view.setVisibility(8);
                    }

                    public void onAnimationStart(View view) {
                        Behavior.this.mIsAnimatingOut = true;
                    }
                }).start();
                return;
            }
            Animation loadAnimation = AnimationUtils.loadAnimation(floatingActionButton.getContext(), R.anim.fab_out);
            loadAnimation.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
            loadAnimation.setDuration(200);
            loadAnimation.setAnimationListener(new AnimationListenerAdapter() {
                public void onAnimationEnd(Animation animation) {
                    Behavior.this.mIsAnimatingOut = false;
                    floatingActionButton.setVisibility(8);
                }

                public void onAnimationStart(Animation animation) {
                    Behavior.this.mIsAnimatingOut = true;
                }
            });
            floatingActionButton.startAnimation(loadAnimation);
        }

        private float getFabTranslationYForSnackbar(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton) {
            float f = 0.0f;
            List dependencies = coordinatorLayout.getDependencies(floatingActionButton);
            int size = dependencies.size();
            int i = 0;
            while (i < size) {
                View view = (View) dependencies.get(i);
                float min = ((view instanceof SnackbarLayout) && coordinatorLayout.doViewsOverlap(floatingActionButton, view)) ? Math.min(f, ViewCompat.getTranslationY(view) - ((float) view.getHeight())) : f;
                i++;
                f = min;
            }
            return f;
        }

        private void updateFabTranslationForSnackbar(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton, View view) {
            float fabTranslationYForSnackbar = getFabTranslationYForSnackbar(coordinatorLayout, floatingActionButton);
            if (fabTranslationYForSnackbar != this.mTranslationY) {
                ViewCompat.animate(floatingActionButton).cancel();
                if (Math.abs(fabTranslationYForSnackbar - this.mTranslationY) == ((float) view.getHeight())) {
                    ViewCompat.animate(floatingActionButton).translationY(fabTranslationYForSnackbar).setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR).setListener(null);
                } else {
                    ViewCompat.setTranslationY(floatingActionButton, fabTranslationYForSnackbar);
                }
                this.mTranslationY = fabTranslationYForSnackbar;
            }
        }

        public boolean layoutDependsOn(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton, View view) {
            return SNACKBAR_BEHAVIOR_ENABLED && (view instanceof SnackbarLayout);
        }

        public boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, FloatingActionButton floatingActionButton, View view) {
            if (view instanceof SnackbarLayout) {
                updateFabTranslationForSnackbar(coordinatorLayout, floatingActionButton, view);
            } else if (view instanceof AppBarLayout) {
                AppBarLayout appBarLayout = (AppBarLayout) view;
                if (this.mTmpRect == null) {
                    this.mTmpRect = new Rect();
                }
                Rect rect = this.mTmpRect;
                ViewGroupUtils.getDescendantRect(coordinatorLayout, view, rect);
                if (rect.bottom <= appBarLayout.getMinimumHeightForVisibleOverlappingContent()) {
                    if (!this.mIsAnimatingOut && floatingActionButton.getVisibility() == 0) {
                        animateOut(floatingActionButton);
                    }
                } else if (floatingActionButton.getVisibility() != 0) {
                    animateIn(floatingActionButton);
                }
            }
            return false;
        }
    }

    public FloatingActionButton(Context context) {
        this(context, null);
    }

    public FloatingActionButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public FloatingActionButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mShadowPadding = new Rect();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.FloatingActionButton, i, R.style.Widget_Design_FloatingActionButton);
        Drawable drawable = obtainStyledAttributes.getDrawable(R.styleable.FloatingActionButton_android_background);
        this.mBackgroundTint = obtainStyledAttributes.getColorStateList(R.styleable.FloatingActionButton_backgroundTint);
        this.mBackgroundTintMode = parseTintMode(obtainStyledAttributes.getInt(R.styleable.FloatingActionButton_backgroundTintMode, -1), null);
        this.mRippleColor = obtainStyledAttributes.getColor(R.styleable.FloatingActionButton_rippleColor, 0);
        this.mSize = obtainStyledAttributes.getInt(R.styleable.FloatingActionButton_fabSize, 0);
        this.mBorderWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.FloatingActionButton_borderWidth, 0);
        float dimension = obtainStyledAttributes.getDimension(R.styleable.FloatingActionButton_elevation, 0.0f);
        float dimension2 = obtainStyledAttributes.getDimension(R.styleable.FloatingActionButton_pressedTranslationZ, 0.0f);
        obtainStyledAttributes.recycle();
        ShadowViewDelegate anonymousClass1 = new ShadowViewDelegate() {
            public float getRadius() {
                return ((float) FloatingActionButton.this.getSizeDimension()) / 2.0f;
            }

            public void setBackgroundDrawable(Drawable drawable) {
                super.setBackgroundDrawable(drawable);
            }

            public void setShadowPadding(int i, int i2, int i3, int i4) {
                FloatingActionButton.this.mShadowPadding.set(i, i2, i3, i4);
                FloatingActionButton.this.setPadding(FloatingActionButton.this.mContentPadding + i, FloatingActionButton.this.mContentPadding + i2, FloatingActionButton.this.mContentPadding + i3, FloatingActionButton.this.mContentPadding + i4);
            }
        };
        if (VERSION.SDK_INT >= 21) {
            this.mImpl = new FloatingActionButtonLollipop(this, anonymousClass1);
        } else {
            this.mImpl = new FloatingActionButtonEclairMr1(this, anonymousClass1);
        }
        this.mContentPadding = (getSizeDimension() - ((int) getResources().getDimension(R.dimen.fab_content_size))) / 2;
        this.mImpl.setBackgroundDrawable(drawable, this.mBackgroundTint, this.mBackgroundTintMode, this.mRippleColor, this.mBorderWidth);
        this.mImpl.setElevation(dimension);
        this.mImpl.setPressedTranslationZ(dimension2);
        setClickable(true);
    }

    static Mode parseTintMode(int i, Mode mode) {
        switch (i) {
            case 3:
                return Mode.SRC_OVER;
            case 5:
                return Mode.SRC_IN;
            case 9:
                return Mode.SRC_ATOP;
            case 14:
                return Mode.MULTIPLY;
            case 15:
                return Mode.SCREEN;
            default:
                return mode;
        }
    }

    private static int resolveAdjustedSize(int i, int i2) {
        int mode = MeasureSpec.getMode(i2);
        int size = MeasureSpec.getSize(i2);
        switch (mode) {
            case Integer.MIN_VALUE:
                return Math.min(i, size);
            case 1073741824:
                return size;
            default:
                return i;
        }
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        this.mImpl.onDrawableStateChanged(getDrawableState());
    }

    @Nullable
    public ColorStateList getBackgroundTintList() {
        return this.mBackgroundTint;
    }

    @Nullable
    public Mode getBackgroundTintMode() {
        return this.mBackgroundTintMode;
    }

    final int getSizeDimension() {
        switch (this.mSize) {
            case 1:
                return getResources().getDimensionPixelSize(R.dimen.fab_size_mini);
            default:
                return getResources().getDimensionPixelSize(R.dimen.fab_size_normal);
        }
    }

    @TargetApi(11)
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        this.mImpl.jumpDrawableToCurrentState();
    }

    protected void onMeasure(int i, int i2) {
        int sizeDimension = getSizeDimension();
        sizeDimension = Math.min(resolveAdjustedSize(sizeDimension, i), resolveAdjustedSize(sizeDimension, i2));
        setMeasuredDimension((this.mShadowPadding.left + sizeDimension) + this.mShadowPadding.right, (sizeDimension + this.mShadowPadding.top) + this.mShadowPadding.bottom);
    }

    public void setBackgroundDrawable(Drawable drawable) {
        if (this.mImpl != null) {
            this.mImpl.setBackgroundDrawable(drawable, this.mBackgroundTint, this.mBackgroundTintMode, this.mRippleColor, this.mBorderWidth);
        }
    }

    public void setBackgroundTintList(@Nullable ColorStateList colorStateList) {
        this.mImpl.setBackgroundTintList(colorStateList);
    }

    public void setBackgroundTintMode(@Nullable Mode mode) {
        this.mImpl.setBackgroundTintMode(mode);
    }

    public void setRippleColor(int i) {
        if (this.mRippleColor != i) {
            this.mRippleColor = i;
            this.mImpl.setRippleColor(i);
        }
    }
}
