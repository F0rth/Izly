package android.support.v7.internal.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v7.appcompat.R;
import android.support.v7.internal.VersionUtils;
import android.support.v7.view.ActionMode;
import android.support.v7.view.ActionMode.Callback;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

public class ActionBarContainer extends FrameLayout {
    private View mActionBarView;
    Drawable mBackground;
    private View mContextView;
    private int mHeight;
    boolean mIsSplit;
    boolean mIsStacked;
    private boolean mIsTransitioning;
    Drawable mSplitBackground;
    Drawable mStackedBackground;
    private View mTabContainer;

    public ActionBarContainer(Context context) {
        this(context, null);
    }

    public ActionBarContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setBackgroundDrawable(VersionUtils.isAtLeastL() ? new ActionBarBackgroundDrawableV21(this) : new ActionBarBackgroundDrawable(this));
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ActionBar);
        this.mBackground = obtainStyledAttributes.getDrawable(R.styleable.ActionBar_background);
        this.mStackedBackground = obtainStyledAttributes.getDrawable(R.styleable.ActionBar_backgroundStacked);
        this.mHeight = obtainStyledAttributes.getDimensionPixelSize(R.styleable.ActionBar_height, -1);
        if (getId() == R.id.split_action_bar) {
            this.mIsSplit = true;
            this.mSplitBackground = obtainStyledAttributes.getDrawable(R.styleable.ActionBar_backgroundSplit);
        }
        obtainStyledAttributes.recycle();
        boolean z = this.mIsSplit ? this.mSplitBackground == null : this.mBackground == null && this.mStackedBackground == null;
        setWillNotDraw(z);
    }

    private int getMeasuredHeightWithMargins(View view) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        return layoutParams.bottomMargin + (view.getMeasuredHeight() + layoutParams.topMargin);
    }

    private boolean isCollapsed(View view) {
        return view == null || view.getVisibility() == 8 || view.getMeasuredHeight() == 0;
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.mBackground != null && this.mBackground.isStateful()) {
            this.mBackground.setState(getDrawableState());
        }
        if (this.mStackedBackground != null && this.mStackedBackground.isStateful()) {
            this.mStackedBackground.setState(getDrawableState());
        }
        if (this.mSplitBackground != null && this.mSplitBackground.isStateful()) {
            this.mSplitBackground.setState(getDrawableState());
        }
    }

    public View getTabContainer() {
        return this.mTabContainer;
    }

    public void jumpDrawablesToCurrentState() {
        if (VERSION.SDK_INT >= 11) {
            super.jumpDrawablesToCurrentState();
            if (this.mBackground != null) {
                this.mBackground.jumpToCurrentState();
            }
            if (this.mStackedBackground != null) {
                this.mStackedBackground.jumpToCurrentState();
            }
            if (this.mSplitBackground != null) {
                this.mSplitBackground.jumpToCurrentState();
            }
        }
    }

    public void onFinishInflate() {
        super.onFinishInflate();
        this.mActionBarView = findViewById(R.id.action_bar);
        this.mContextView = findViewById(R.id.action_context_bar);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return this.mIsTransitioning || super.onInterceptTouchEvent(motionEvent);
    }

    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5 = 1;
        super.onLayout(z, i, i2, i3, i4);
        View view = this.mTabContainer;
        boolean z2 = (view == null || view.getVisibility() == 8) ? false : true;
        if (!(view == null || view.getVisibility() == 8)) {
            int measuredHeight = getMeasuredHeight();
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            view.layout(i, (measuredHeight - view.getMeasuredHeight()) - layoutParams.bottomMargin, i3, measuredHeight - layoutParams.bottomMargin);
        }
        if (!this.mIsSplit) {
            int i6;
            if (this.mBackground != null) {
                if (this.mActionBarView.getVisibility() == 0) {
                    this.mBackground.setBounds(this.mActionBarView.getLeft(), this.mActionBarView.getTop(), this.mActionBarView.getRight(), this.mActionBarView.getBottom());
                } else if (this.mContextView == null || this.mContextView.getVisibility() != 0) {
                    this.mBackground.setBounds(0, 0, 0, 0);
                } else {
                    this.mBackground.setBounds(this.mContextView.getLeft(), this.mContextView.getTop(), this.mContextView.getRight(), this.mContextView.getBottom());
                }
                i6 = 1;
            } else {
                i6 = 0;
            }
            this.mIsStacked = z2;
            if (!z2 || this.mStackedBackground == null) {
                i5 = i6;
            } else {
                this.mStackedBackground.setBounds(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
            }
        } else if (this.mSplitBackground != null) {
            this.mSplitBackground.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
        } else {
            i5 = 0;
        }
        if (i5 != 0) {
            invalidate();
        }
    }

    public void onMeasure(int i, int i2) {
        if (this.mActionBarView == null && MeasureSpec.getMode(i2) == Integer.MIN_VALUE && this.mHeight >= 0) {
            i2 = MeasureSpec.makeMeasureSpec(Math.min(this.mHeight, MeasureSpec.getSize(i2)), Integer.MIN_VALUE);
        }
        super.onMeasure(i, i2);
        if (this.mActionBarView != null) {
            int mode = MeasureSpec.getMode(i2);
            if (this.mTabContainer != null && this.mTabContainer.getVisibility() != 8 && mode != 1073741824) {
                int measuredHeightWithMargins = !isCollapsed(this.mActionBarView) ? getMeasuredHeightWithMargins(this.mActionBarView) : !isCollapsed(this.mContextView) ? getMeasuredHeightWithMargins(this.mContextView) : 0;
                setMeasuredDimension(getMeasuredWidth(), Math.min(measuredHeightWithMargins + getMeasuredHeightWithMargins(this.mTabContainer), mode == Integer.MIN_VALUE ? MeasureSpec.getSize(i2) : ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED));
            }
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        return true;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setPrimaryBackground(android.graphics.drawable.Drawable r8) {
        /*
        r7 = this;
        r0 = 0;
        r1 = 1;
        r2 = r7.mBackground;
        if (r2 == 0) goto L_0x0011;
    L_0x0006:
        r2 = r7.mBackground;
        r3 = 0;
        r2.setCallback(r3);
        r2 = r7.mBackground;
        r7.unscheduleDrawable(r2);
    L_0x0011:
        r7.mBackground = r8;
        if (r8 == 0) goto L_0x0039;
    L_0x0015:
        r8.setCallback(r7);
        r2 = r7.mActionBarView;
        if (r2 == 0) goto L_0x0039;
    L_0x001c:
        r2 = r7.mBackground;
        r3 = r7.mActionBarView;
        r3 = r3.getLeft();
        r4 = r7.mActionBarView;
        r4 = r4.getTop();
        r5 = r7.mActionBarView;
        r5 = r5.getRight();
        r6 = r7.mActionBarView;
        r6 = r6.getBottom();
        r2.setBounds(r3, r4, r5, r6);
    L_0x0039:
        r2 = r7.mIsSplit;
        if (r2 == 0) goto L_0x0049;
    L_0x003d:
        r2 = r7.mSplitBackground;
        if (r2 != 0) goto L_0x0042;
    L_0x0041:
        r0 = r1;
    L_0x0042:
        r7.setWillNotDraw(r0);
        r7.invalidate();
        return;
    L_0x0049:
        r2 = r7.mBackground;
        if (r2 != 0) goto L_0x0042;
    L_0x004d:
        r2 = r7.mStackedBackground;
        if (r2 == 0) goto L_0x0041;
    L_0x0051:
        goto L_0x0042;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.internal.widget.ActionBarContainer.setPrimaryBackground(android.graphics.drawable.Drawable):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setSplitBackground(android.graphics.drawable.Drawable r6) {
        /*
        r5 = this;
        r0 = 0;
        r1 = 1;
        r2 = r5.mSplitBackground;
        if (r2 == 0) goto L_0x0011;
    L_0x0006:
        r2 = r5.mSplitBackground;
        r3 = 0;
        r2.setCallback(r3);
        r2 = r5.mSplitBackground;
        r5.unscheduleDrawable(r2);
    L_0x0011:
        r5.mSplitBackground = r6;
        if (r6 == 0) goto L_0x002d;
    L_0x0015:
        r6.setCallback(r5);
        r2 = r5.mIsSplit;
        if (r2 == 0) goto L_0x002d;
    L_0x001c:
        r2 = r5.mSplitBackground;
        if (r2 == 0) goto L_0x002d;
    L_0x0020:
        r2 = r5.mSplitBackground;
        r3 = r5.getMeasuredWidth();
        r4 = r5.getMeasuredHeight();
        r2.setBounds(r0, r0, r3, r4);
    L_0x002d:
        r2 = r5.mIsSplit;
        if (r2 == 0) goto L_0x003d;
    L_0x0031:
        r2 = r5.mSplitBackground;
        if (r2 != 0) goto L_0x0036;
    L_0x0035:
        r0 = r1;
    L_0x0036:
        r5.setWillNotDraw(r0);
        r5.invalidate();
        return;
    L_0x003d:
        r2 = r5.mBackground;
        if (r2 != 0) goto L_0x0036;
    L_0x0041:
        r2 = r5.mStackedBackground;
        if (r2 == 0) goto L_0x0035;
    L_0x0045:
        goto L_0x0036;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.internal.widget.ActionBarContainer.setSplitBackground(android.graphics.drawable.Drawable):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setStackedBackground(android.graphics.drawable.Drawable r8) {
        /*
        r7 = this;
        r0 = 0;
        r1 = 1;
        r2 = r7.mStackedBackground;
        if (r2 == 0) goto L_0x0011;
    L_0x0006:
        r2 = r7.mStackedBackground;
        r3 = 0;
        r2.setCallback(r3);
        r2 = r7.mStackedBackground;
        r7.unscheduleDrawable(r2);
    L_0x0011:
        r7.mStackedBackground = r8;
        if (r8 == 0) goto L_0x003d;
    L_0x0015:
        r8.setCallback(r7);
        r2 = r7.mIsStacked;
        if (r2 == 0) goto L_0x003d;
    L_0x001c:
        r2 = r7.mStackedBackground;
        if (r2 == 0) goto L_0x003d;
    L_0x0020:
        r2 = r7.mStackedBackground;
        r3 = r7.mTabContainer;
        r3 = r3.getLeft();
        r4 = r7.mTabContainer;
        r4 = r4.getTop();
        r5 = r7.mTabContainer;
        r5 = r5.getRight();
        r6 = r7.mTabContainer;
        r6 = r6.getBottom();
        r2.setBounds(r3, r4, r5, r6);
    L_0x003d:
        r2 = r7.mIsSplit;
        if (r2 == 0) goto L_0x004d;
    L_0x0041:
        r2 = r7.mSplitBackground;
        if (r2 != 0) goto L_0x0046;
    L_0x0045:
        r0 = r1;
    L_0x0046:
        r7.setWillNotDraw(r0);
        r7.invalidate();
        return;
    L_0x004d:
        r2 = r7.mBackground;
        if (r2 != 0) goto L_0x0046;
    L_0x0051:
        r2 = r7.mStackedBackground;
        if (r2 == 0) goto L_0x0045;
    L_0x0055:
        goto L_0x0046;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.internal.widget.ActionBarContainer.setStackedBackground(android.graphics.drawable.Drawable):void");
    }

    public void setTabContainer(ScrollingTabContainerView scrollingTabContainerView) {
        if (this.mTabContainer != null) {
            removeView(this.mTabContainer);
        }
        this.mTabContainer = scrollingTabContainerView;
        if (scrollingTabContainerView != null) {
            addView(scrollingTabContainerView);
            ViewGroup.LayoutParams layoutParams = scrollingTabContainerView.getLayoutParams();
            layoutParams.width = -1;
            layoutParams.height = -2;
            scrollingTabContainerView.setAllowCollapse(false);
        }
    }

    public void setTransitioning(boolean z) {
        this.mIsTransitioning = z;
        setDescendantFocusability(z ? 393216 : 262144);
    }

    public void setVisibility(int i) {
        super.setVisibility(i);
        boolean z = i == 0;
        if (this.mBackground != null) {
            this.mBackground.setVisible(z, false);
        }
        if (this.mStackedBackground != null) {
            this.mStackedBackground.setVisible(z, false);
        }
        if (this.mSplitBackground != null) {
            this.mSplitBackground.setVisible(z, false);
        }
    }

    public ActionMode startActionModeForChild(View view, Callback callback) {
        return null;
    }

    public android.view.ActionMode startActionModeForChild(View view, android.view.ActionMode.Callback callback) {
        return null;
    }

    protected boolean verifyDrawable(Drawable drawable) {
        return (drawable == this.mBackground && !this.mIsSplit) || ((drawable == this.mStackedBackground && this.mIsStacked) || ((drawable == this.mSplitBackground && this.mIsSplit) || super.verifyDrawable(drawable)));
    }
}
