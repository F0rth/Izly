package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.InputDeviceCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.internal.widget.TintTypedArray;
import android.support.v7.internal.widget.ViewUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class LinearLayoutCompat extends ViewGroup {
    public static final int HORIZONTAL = 0;
    private static final int INDEX_BOTTOM = 2;
    private static final int INDEX_CENTER_VERTICAL = 0;
    private static final int INDEX_FILL = 3;
    private static final int INDEX_TOP = 1;
    public static final int SHOW_DIVIDER_BEGINNING = 1;
    public static final int SHOW_DIVIDER_END = 4;
    public static final int SHOW_DIVIDER_MIDDLE = 2;
    public static final int SHOW_DIVIDER_NONE = 0;
    public static final int VERTICAL = 1;
    private static final int VERTICAL_GRAVITY_COUNT = 4;
    private boolean mBaselineAligned;
    private int mBaselineAlignedChildIndex;
    private int mBaselineChildTop;
    private Drawable mDivider;
    private int mDividerHeight;
    private int mDividerPadding;
    private int mDividerWidth;
    private int mGravity;
    private int[] mMaxAscent;
    private int[] mMaxDescent;
    private int mOrientation;
    private int mShowDividers;
    private int mTotalLength;
    private boolean mUseLargestChild;
    private float mWeightSum;

    public static class LayoutParams extends MarginLayoutParams {
        public int gravity;
        public float weight;

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.gravity = -1;
            this.weight = 0.0f;
        }

        public LayoutParams(int i, int i2, float f) {
            super(i, i2);
            this.gravity = -1;
            this.weight = f;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.gravity = -1;
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.LinearLayoutCompat_Layout);
            this.weight = obtainStyledAttributes.getFloat(R.styleable.LinearLayoutCompat_Layout_android_layout_weight, 0.0f);
            this.gravity = obtainStyledAttributes.getInt(R.styleable.LinearLayoutCompat_Layout_android_layout_gravity, -1);
            obtainStyledAttributes.recycle();
        }

        public LayoutParams(LayoutParams layoutParams) {
            super(layoutParams);
            this.gravity = -1;
            this.weight = layoutParams.weight;
            this.gravity = layoutParams.gravity;
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.gravity = -1;
        }

        public LayoutParams(MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.gravity = -1;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface DividerMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface OrientationMode {
    }

    public LinearLayoutCompat(Context context) {
        this(context, null);
    }

    public LinearLayoutCompat(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public LinearLayoutCompat(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mBaselineAligned = true;
        this.mBaselineAlignedChildIndex = -1;
        this.mBaselineChildTop = 0;
        this.mGravity = 8388659;
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, R.styleable.LinearLayoutCompat, i, 0);
        int i2 = obtainStyledAttributes.getInt(R.styleable.LinearLayoutCompat_android_orientation, -1);
        if (i2 >= 0) {
            setOrientation(i2);
        }
        i2 = obtainStyledAttributes.getInt(R.styleable.LinearLayoutCompat_android_gravity, -1);
        if (i2 >= 0) {
            setGravity(i2);
        }
        boolean z = obtainStyledAttributes.getBoolean(R.styleable.LinearLayoutCompat_android_baselineAligned, true);
        if (!z) {
            setBaselineAligned(z);
        }
        this.mWeightSum = obtainStyledAttributes.getFloat(R.styleable.LinearLayoutCompat_android_weightSum, GroundOverlayOptions.NO_DIMENSION);
        this.mBaselineAlignedChildIndex = obtainStyledAttributes.getInt(R.styleable.LinearLayoutCompat_android_baselineAlignedChildIndex, -1);
        this.mUseLargestChild = obtainStyledAttributes.getBoolean(R.styleable.LinearLayoutCompat_measureWithLargestChild, false);
        setDividerDrawable(obtainStyledAttributes.getDrawable(R.styleable.LinearLayoutCompat_divider));
        this.mShowDividers = obtainStyledAttributes.getInt(R.styleable.LinearLayoutCompat_showDividers, 0);
        this.mDividerPadding = obtainStyledAttributes.getDimensionPixelSize(R.styleable.LinearLayoutCompat_dividerPadding, 0);
        obtainStyledAttributes.recycle();
    }

    private void forceUniformHeight(int i, int i2) {
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824);
        for (int i3 = 0; i3 < i; i3++) {
            View virtualChildAt = getVirtualChildAt(i3);
            if (virtualChildAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) virtualChildAt.getLayoutParams();
                if (layoutParams.height == -1) {
                    int i4 = layoutParams.width;
                    layoutParams.width = virtualChildAt.getMeasuredWidth();
                    measureChildWithMargins(virtualChildAt, i2, 0, makeMeasureSpec, 0);
                    layoutParams.width = i4;
                }
            }
        }
    }

    private void forceUniformWidth(int i, int i2) {
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
        for (int i3 = 0; i3 < i; i3++) {
            View virtualChildAt = getVirtualChildAt(i3);
            if (virtualChildAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) virtualChildAt.getLayoutParams();
                if (layoutParams.width == -1) {
                    int i4 = layoutParams.height;
                    layoutParams.height = virtualChildAt.getMeasuredHeight();
                    measureChildWithMargins(virtualChildAt, makeMeasureSpec, 0, i2, 0);
                    layoutParams.height = i4;
                }
            }
        }
    }

    private void setChildFrame(View view, int i, int i2, int i3, int i4) {
        view.layout(i, i2, i + i3, i2 + i4);
    }

    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    void drawDividersHorizontal(Canvas canvas) {
        int virtualChildCount = getVirtualChildCount();
        boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
        int i = 0;
        while (i < virtualChildCount) {
            View virtualChildAt = getVirtualChildAt(i);
            if (!(virtualChildAt == null || virtualChildAt.getVisibility() == 8 || !hasDividerBeforeChildAt(i))) {
                LayoutParams layoutParams = (LayoutParams) virtualChildAt.getLayoutParams();
                drawVerticalDivider(canvas, isLayoutRtl ? layoutParams.rightMargin + virtualChildAt.getRight() : (virtualChildAt.getLeft() - layoutParams.leftMargin) - this.mDividerWidth);
            }
            i++;
        }
        if (hasDividerBeforeChildAt(virtualChildCount)) {
            int paddingLeft;
            View virtualChildAt2 = getVirtualChildAt(virtualChildCount - 1);
            if (virtualChildAt2 == null) {
                paddingLeft = isLayoutRtl ? getPaddingLeft() : (getWidth() - getPaddingRight()) - this.mDividerWidth;
            } else {
                layoutParams = (LayoutParams) virtualChildAt2.getLayoutParams();
                paddingLeft = isLayoutRtl ? (virtualChildAt2.getLeft() - layoutParams.leftMargin) - this.mDividerWidth : layoutParams.rightMargin + virtualChildAt2.getRight();
            }
            drawVerticalDivider(canvas, paddingLeft);
        }
    }

    void drawDividersVertical(Canvas canvas) {
        int virtualChildCount = getVirtualChildCount();
        int i = 0;
        while (i < virtualChildCount) {
            View virtualChildAt = getVirtualChildAt(i);
            if (!(virtualChildAt == null || virtualChildAt.getVisibility() == 8 || !hasDividerBeforeChildAt(i))) {
                drawHorizontalDivider(canvas, (virtualChildAt.getTop() - ((LayoutParams) virtualChildAt.getLayoutParams()).topMargin) - this.mDividerHeight);
            }
            i++;
        }
        if (hasDividerBeforeChildAt(virtualChildCount)) {
            int height;
            View virtualChildAt2 = getVirtualChildAt(virtualChildCount - 1);
            if (virtualChildAt2 == null) {
                height = (getHeight() - getPaddingBottom()) - this.mDividerHeight;
            } else {
                LayoutParams layoutParams = (LayoutParams) virtualChildAt2.getLayoutParams();
                height = layoutParams.bottomMargin + virtualChildAt2.getBottom();
            }
            drawHorizontalDivider(canvas, height);
        }
    }

    void drawHorizontalDivider(Canvas canvas, int i) {
        this.mDivider.setBounds(getPaddingLeft() + this.mDividerPadding, i, (getWidth() - getPaddingRight()) - this.mDividerPadding, this.mDividerHeight + i);
        this.mDivider.draw(canvas);
    }

    void drawVerticalDivider(Canvas canvas, int i) {
        this.mDivider.setBounds(i, getPaddingTop() + this.mDividerPadding, this.mDividerWidth + i, (getHeight() - getPaddingBottom()) - this.mDividerPadding);
        this.mDivider.draw(canvas);
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return this.mOrientation == 0 ? new LayoutParams(-2, -2) : this.mOrientation == 1 ? new LayoutParams(-1, -2) : null;
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    protected LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    public int getBaseline() {
        if (this.mBaselineAlignedChildIndex < 0) {
            return super.getBaseline();
        }
        if (getChildCount() <= this.mBaselineAlignedChildIndex) {
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout set to an index that is out of bounds.");
        }
        View childAt = getChildAt(this.mBaselineAlignedChildIndex);
        int baseline = childAt.getBaseline();
        if (baseline != -1) {
            int i;
            int i2 = this.mBaselineChildTop;
            if (this.mOrientation == 1) {
                i = this.mGravity & 112;
                if (i != 48) {
                    switch (i) {
                        case 16:
                            i = i2 + (((((getBottom() - getTop()) - getPaddingTop()) - getPaddingBottom()) - this.mTotalLength) / 2);
                            break;
                        case 80:
                            i = ((getBottom() - getTop()) - getPaddingBottom()) - this.mTotalLength;
                            break;
                    }
                }
            }
            i = i2;
            return (((LayoutParams) childAt.getLayoutParams()).topMargin + i) + baseline;
        } else if (this.mBaselineAlignedChildIndex == 0) {
            return -1;
        } else {
            throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout points to a View that doesn't know how to get its baseline.");
        }
    }

    public int getBaselineAlignedChildIndex() {
        return this.mBaselineAlignedChildIndex;
    }

    int getChildrenSkipCount(View view, int i) {
        return 0;
    }

    public Drawable getDividerDrawable() {
        return this.mDivider;
    }

    public int getDividerPadding() {
        return this.mDividerPadding;
    }

    public int getDividerWidth() {
        return this.mDividerWidth;
    }

    int getLocationOffset(View view) {
        return 0;
    }

    int getNextLocationOffset(View view) {
        return 0;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public int getShowDividers() {
        return this.mShowDividers;
    }

    View getVirtualChildAt(int i) {
        return getChildAt(i);
    }

    int getVirtualChildCount() {
        return getChildCount();
    }

    public float getWeightSum() {
        return this.mWeightSum;
    }

    protected boolean hasDividerBeforeChildAt(int i) {
        if (i == 0) {
            if ((this.mShowDividers & 1) == 0) {
                return false;
            }
        } else if (i == getChildCount()) {
            if ((this.mShowDividers & 4) == 0) {
                return false;
            }
        } else if ((this.mShowDividers & 2) == 0) {
            return false;
        } else {
            int i2 = i - 1;
            while (i2 >= 0) {
                if (getChildAt(i2).getVisibility() == 8) {
                    i2--;
                }
            }
            return false;
        }
        return true;
    }

    public boolean isBaselineAligned() {
        return this.mBaselineAligned;
    }

    public boolean isMeasureWithLargestChildEnabled() {
        return this.mUseLargestChild;
    }

    void layoutHorizontal(int i, int i2, int i3, int i4) {
        int paddingLeft;
        int i5;
        int i6;
        boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
        int paddingTop = getPaddingTop();
        int i7 = i4 - i2;
        int paddingBottom = getPaddingBottom();
        int paddingBottom2 = getPaddingBottom();
        int virtualChildCount = getVirtualChildCount();
        int i8 = this.mGravity;
        int i9 = this.mGravity;
        boolean z = this.mBaselineAligned;
        int[] iArr = this.mMaxAscent;
        int[] iArr2 = this.mMaxDescent;
        switch (GravityCompat.getAbsoluteGravity(i8 & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK, ViewCompat.getLayoutDirection(this))) {
            case 1:
                paddingLeft = (((i3 - i) - this.mTotalLength) / 2) + getPaddingLeft();
                break;
            case 5:
                paddingLeft = ((getPaddingLeft() + i3) - i) - this.mTotalLength;
                break;
            default:
                paddingLeft = getPaddingLeft();
                break;
        }
        if (isLayoutRtl) {
            i5 = virtualChildCount - 1;
            i6 = -1;
        } else {
            i5 = 0;
            i6 = 1;
        }
        int i10 = 0;
        while (i10 < virtualChildCount) {
            int measureNullChild;
            int i11 = i5 + (i6 * i10);
            View virtualChildAt = getVirtualChildAt(i11);
            if (virtualChildAt == null) {
                measureNullChild = measureNullChild(i11) + paddingLeft;
                i8 = i10;
            } else if (virtualChildAt.getVisibility() != 8) {
                int measuredWidth = virtualChildAt.getMeasuredWidth();
                int measuredHeight = virtualChildAt.getMeasuredHeight();
                LayoutParams layoutParams = (LayoutParams) virtualChildAt.getLayoutParams();
                measureNullChild = (!z || layoutParams.height == -1) ? -1 : virtualChildAt.getBaseline();
                int i12 = layoutParams.gravity;
                if (i12 < 0) {
                    i12 = i9 & 112;
                }
                switch (i12 & 112) {
                    case 16:
                        i12 = ((((((i7 - paddingTop) - paddingBottom2) - measuredHeight) / 2) + paddingTop) + layoutParams.topMargin) - layoutParams.bottomMargin;
                        break;
                    case 48:
                        i12 = layoutParams.topMargin + paddingTop;
                        if (measureNullChild != -1) {
                            i12 += iArr[1] - measureNullChild;
                            break;
                        }
                        break;
                    case 80:
                        i12 = ((i7 - paddingBottom) - measuredHeight) - layoutParams.bottomMargin;
                        if (measureNullChild != -1) {
                            i12 -= iArr2[2] - (virtualChildAt.getMeasuredHeight() - measureNullChild);
                            break;
                        }
                        break;
                    default:
                        i12 = paddingTop;
                        break;
                }
                if (hasDividerBeforeChildAt(i11)) {
                    paddingLeft += this.mDividerWidth;
                }
                int i13 = paddingLeft + layoutParams.leftMargin;
                setChildFrame(virtualChildAt, i13 + getLocationOffset(virtualChildAt), i12, measuredWidth, measuredHeight);
                measureNullChild = ((layoutParams.rightMargin + measuredWidth) + getNextLocationOffset(virtualChildAt)) + i13;
                i8 = getChildrenSkipCount(virtualChildAt, i11) + i10;
            } else {
                measureNullChild = paddingLeft;
                i8 = i10;
            }
            i10 = i8 + 1;
            paddingLeft = measureNullChild;
        }
    }

    void layoutVertical(int i, int i2, int i3, int i4) {
        int paddingLeft = getPaddingLeft();
        int i5 = i3 - i;
        int paddingRight = getPaddingRight();
        int paddingRight2 = getPaddingRight();
        int virtualChildCount = getVirtualChildCount();
        int i6 = this.mGravity;
        int i7 = this.mGravity;
        switch (i6 & 112) {
            case 16:
                i6 = getPaddingTop() + (((i4 - i2) - this.mTotalLength) / 2);
                break;
            case 80:
                i6 = ((getPaddingTop() + i4) - i2) - this.mTotalLength;
                break;
            default:
                i6 = getPaddingTop();
                break;
        }
        int i8 = 0;
        int i9 = i6;
        while (i8 < virtualChildCount) {
            int i10;
            View virtualChildAt = getVirtualChildAt(i8);
            if (virtualChildAt == null) {
                i6 = measureNullChild(i8) + i9;
                i10 = i8;
            } else if (virtualChildAt.getVisibility() != 8) {
                int i11;
                int measuredWidth = virtualChildAt.getMeasuredWidth();
                int measuredHeight = virtualChildAt.getMeasuredHeight();
                LayoutParams layoutParams = (LayoutParams) virtualChildAt.getLayoutParams();
                i6 = layoutParams.gravity;
                if (i6 < 0) {
                    i6 = GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK & i7;
                }
                switch (GravityCompat.getAbsoluteGravity(i6, ViewCompat.getLayoutDirection(this)) & 7) {
                    case 1:
                        i11 = ((((((i5 - paddingLeft) - paddingRight2) - measuredWidth) / 2) + paddingLeft) + layoutParams.leftMargin) - layoutParams.rightMargin;
                        break;
                    case 5:
                        i11 = ((i5 - paddingRight) - measuredWidth) - layoutParams.rightMargin;
                        break;
                    default:
                        i11 = paddingLeft + layoutParams.leftMargin;
                        break;
                }
                if (hasDividerBeforeChildAt(i8)) {
                    i9 += this.mDividerHeight;
                }
                int i12 = i9 + layoutParams.topMargin;
                setChildFrame(virtualChildAt, i11, i12 + getLocationOffset(virtualChildAt), measuredWidth, measuredHeight);
                i6 = ((layoutParams.bottomMargin + measuredHeight) + getNextLocationOffset(virtualChildAt)) + i12;
                i10 = getChildrenSkipCount(virtualChildAt, i8) + i8;
            } else {
                i6 = i9;
                i10 = i8;
            }
            i8 = i10 + 1;
            i9 = i6;
        }
    }

    void measureChildBeforeLayout(View view, int i, int i2, int i3, int i4, int i5) {
        measureChildWithMargins(view, i2, i3, i4, i5);
    }

    void measureHorizontal(int i, int i2) {
        int i3;
        int i4;
        Object obj;
        int baseline;
        LayoutParams layoutParams;
        this.mTotalLength = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        Object obj2 = 1;
        float f = 0.0f;
        int virtualChildCount = getVirtualChildCount();
        int mode = MeasureSpec.getMode(i);
        int mode2 = MeasureSpec.getMode(i2);
        Object obj3 = null;
        Object obj4 = null;
        if (this.mMaxAscent == null || this.mMaxDescent == null) {
            this.mMaxAscent = new int[4];
            this.mMaxDescent = new int[4];
        }
        int[] iArr = this.mMaxAscent;
        int[] iArr2 = this.mMaxDescent;
        iArr[3] = -1;
        iArr[2] = -1;
        iArr[1] = -1;
        iArr[0] = -1;
        iArr2[3] = -1;
        iArr2[2] = -1;
        iArr2[1] = -1;
        iArr2[0] = -1;
        boolean z = this.mBaselineAligned;
        boolean z2 = this.mUseLargestChild;
        Object obj5 = mode == 1073741824 ? 1 : null;
        int i9 = Integer.MIN_VALUE;
        int i10 = 0;
        while (i10 < virtualChildCount) {
            Object obj6;
            int i11;
            Object obj7;
            Object obj8;
            float f2;
            int i12;
            int measuredHeight;
            Object obj9;
            View virtualChildAt = getVirtualChildAt(i10);
            if (virtualChildAt == null) {
                this.mTotalLength += measureNullChild(i10);
                i3 = i5;
                i4 = i6;
                obj6 = obj3;
                i11 = i8;
                obj7 = obj2;
                obj8 = obj4;
                i5 = i9;
                f2 = f;
                i12 = i7;
            } else {
                Object obj10;
                float f3;
                if (virtualChildAt.getVisibility() != 8) {
                    if (hasDividerBeforeChildAt(i10)) {
                        this.mTotalLength += this.mDividerWidth;
                    }
                    LayoutParams layoutParams2 = (LayoutParams) virtualChildAt.getLayoutParams();
                    float f4 = f + layoutParams2.weight;
                    if (mode == 1073741824 && layoutParams2.width == 0 && layoutParams2.weight > 0.0f) {
                        if (obj5 != null) {
                            this.mTotalLength += layoutParams2.leftMargin + layoutParams2.rightMargin;
                        } else {
                            i3 = this.mTotalLength;
                            this.mTotalLength = Math.max(i3, (layoutParams2.leftMargin + i3) + layoutParams2.rightMargin);
                        }
                        if (z) {
                            i3 = MeasureSpec.makeMeasureSpec(0, 0);
                            virtualChildAt.measure(i3, i3);
                        } else {
                            obj4 = 1;
                        }
                    } else {
                        i3 = Integer.MIN_VALUE;
                        if (layoutParams2.width == 0 && layoutParams2.weight > 0.0f) {
                            i3 = 0;
                            layoutParams2.width = -2;
                        }
                        int i13 = i3;
                        measureChildBeforeLayout(virtualChildAt, i10, i, f4 == 0.0f ? this.mTotalLength : 0, i2, 0);
                        if (i13 != Integer.MIN_VALUE) {
                            layoutParams2.width = i13;
                        }
                        i3 = virtualChildAt.getMeasuredWidth();
                        if (obj5 != null) {
                            this.mTotalLength += ((layoutParams2.leftMargin + i3) + layoutParams2.rightMargin) + getNextLocationOffset(virtualChildAt);
                        } else {
                            i12 = this.mTotalLength;
                            this.mTotalLength = Math.max(i12, (((i12 + i3) + layoutParams2.leftMargin) + layoutParams2.rightMargin) + getNextLocationOffset(virtualChildAt));
                        }
                        if (z2) {
                            i9 = Math.max(i3, i9);
                        }
                    }
                    obj = null;
                    if (mode2 != 1073741824 && layoutParams2.height == -1) {
                        obj3 = 1;
                        obj = 1;
                    }
                    i11 = layoutParams2.bottomMargin + layoutParams2.topMargin;
                    measuredHeight = virtualChildAt.getMeasuredHeight() + i11;
                    i6 = ViewUtils.combineMeasuredStates(i6, ViewCompat.getMeasuredState(virtualChildAt));
                    if (z) {
                        baseline = virtualChildAt.getBaseline();
                        if (baseline != -1) {
                            i12 = ((((layoutParams2.gravity < 0 ? this.mGravity : layoutParams2.gravity) & 112) >> 4) & -2) >> 1;
                            iArr[i12] = Math.max(iArr[i12], baseline);
                            iArr2[i12] = Math.max(iArr2[i12], measuredHeight - baseline);
                        }
                    }
                    i5 = Math.max(i5, measuredHeight);
                    Object obj11 = (obj2 == null || layoutParams2.height != -1) ? null : 1;
                    if (layoutParams2.weight > 0.0f) {
                        i8 = Math.max(i8, obj != null ? i11 : measuredHeight);
                        i3 = i5;
                        measuredHeight = i7;
                        obj9 = obj3;
                        baseline = i8;
                        obj8 = obj11;
                        i12 = i6;
                        obj10 = obj4;
                        f3 = f4;
                        i6 = i9;
                    } else {
                        if (obj != null) {
                            measuredHeight = i11;
                        }
                        i3 = i5;
                        measuredHeight = Math.max(i7, measuredHeight);
                        obj9 = obj3;
                        baseline = i8;
                        obj8 = obj11;
                        i12 = i6;
                        obj10 = obj4;
                        f3 = f4;
                        i6 = i9;
                    }
                } else {
                    i3 = i5;
                    measuredHeight = i7;
                    obj9 = obj3;
                    baseline = i8;
                    obj8 = obj2;
                    obj10 = obj4;
                    f3 = f;
                    i12 = i6;
                    i6 = i9;
                }
                i10 += getChildrenSkipCount(virtualChildAt, i10);
                i4 = i12;
                i12 = measuredHeight;
                obj6 = obj9;
                i11 = baseline;
                obj7 = obj8;
                obj8 = obj10;
                i5 = i6;
                f2 = f3;
            }
            i10++;
            obj4 = obj8;
            i9 = i5;
            i8 = i11;
            obj3 = obj6;
            obj2 = obj7;
            i7 = i12;
            i5 = i3;
            f = f2;
            i6 = i4;
        }
        if (this.mTotalLength > 0 && hasDividerBeforeChildAt(virtualChildCount)) {
            this.mTotalLength += this.mDividerWidth;
        }
        i10 = (iArr[1] == -1 && iArr[0] == -1 && iArr[2] == -1 && iArr[3] == -1) ? i5 : Math.max(i5, Math.max(iArr[3], Math.max(iArr[0], Math.max(iArr[1], iArr[2]))) + Math.max(iArr2[3], Math.max(iArr2[0], Math.max(iArr2[1], iArr2[2]))));
        if (z2 && (mode == Integer.MIN_VALUE || mode == 0)) {
            this.mTotalLength = 0;
            i4 = 0;
            while (i4 < virtualChildCount) {
                View virtualChildAt2 = getVirtualChildAt(i4);
                if (virtualChildAt2 == null) {
                    this.mTotalLength += measureNullChild(i4);
                    i3 = i4;
                } else if (virtualChildAt2.getVisibility() == 8) {
                    i3 = getChildrenSkipCount(virtualChildAt2, i4) + i4;
                } else {
                    layoutParams = (LayoutParams) virtualChildAt2.getLayoutParams();
                    if (obj5 != null) {
                        this.mTotalLength = ((layoutParams.rightMargin + (layoutParams.leftMargin + i9)) + getNextLocationOffset(virtualChildAt2)) + this.mTotalLength;
                        i3 = i4;
                    } else {
                        i11 = this.mTotalLength;
                        this.mTotalLength = Math.max(i11, (layoutParams.rightMargin + (layoutParams.leftMargin + (i11 + i9))) + getNextLocationOffset(virtualChildAt2));
                        i3 = i4;
                    }
                }
                i4 = i3 + 1;
            }
        }
        this.mTotalLength += getPaddingLeft() + getPaddingRight();
        int resolveSizeAndState = ViewCompat.resolveSizeAndState(Math.max(this.mTotalLength, getSuggestedMinimumWidth()), i, 0);
        int i14 = (ViewCompat.MEASURED_SIZE_MASK & resolveSizeAndState) - this.mTotalLength;
        if (obj4 != null || (i14 != 0 && f > 0.0f)) {
            if (this.mWeightSum > 0.0f) {
                f = this.mWeightSum;
            }
            iArr[3] = -1;
            iArr[2] = -1;
            iArr[1] = -1;
            iArr[0] = -1;
            iArr2[3] = -1;
            iArr2[2] = -1;
            iArr2[1] = -1;
            iArr2[0] = -1;
            measuredHeight = -1;
            this.mTotalLength = 0;
            baseline = i7;
            obj9 = obj2;
            float f5 = f;
            i12 = i6;
            i7 = 0;
            while (i7 < virtualChildCount) {
                float f6;
                View virtualChildAt3 = getVirtualChildAt(i7);
                if (virtualChildAt3 == null || virtualChildAt3.getVisibility() == 8) {
                    obj = obj9;
                    i4 = baseline;
                    i10 = i12;
                    i12 = i14;
                    f6 = f5;
                } else {
                    layoutParams = (LayoutParams) virtualChildAt3.getLayoutParams();
                    f2 = layoutParams.weight;
                    if (f2 > 0.0f) {
                        i10 = (int) ((((float) i14) * f2) / f5);
                        int childMeasureSpec = getChildMeasureSpec(i2, ((getPaddingTop() + getPaddingBottom()) + layoutParams.topMargin) + layoutParams.bottomMargin, layoutParams.height);
                        if (layoutParams.width == 0 && mode == 1073741824) {
                            i4 = i10 > 0 ? i10 : 0;
                        } else {
                            i4 = virtualChildAt3.getMeasuredWidth() + i10;
                            if (i4 < 0) {
                                i4 = 0;
                            }
                        }
                        virtualChildAt3.measure(MeasureSpec.makeMeasureSpec(i4, 1073741824), childMeasureSpec);
                        f2 = f5 - f2;
                        i5 = i14 - i10;
                        i14 = ViewUtils.combineMeasuredStates(i12, ViewCompat.getMeasuredState(virtualChildAt3) & ViewCompat.MEASURED_STATE_MASK);
                    } else {
                        f2 = f5;
                        i5 = i14;
                        i14 = i12;
                    }
                    if (obj5 != null) {
                        this.mTotalLength += ((virtualChildAt3.getMeasuredWidth() + layoutParams.leftMargin) + layoutParams.rightMargin) + getNextLocationOffset(virtualChildAt3);
                    } else {
                        i4 = this.mTotalLength;
                        this.mTotalLength = Math.max(i4, (((virtualChildAt3.getMeasuredWidth() + i4) + layoutParams.leftMargin) + layoutParams.rightMargin) + getNextLocationOffset(virtualChildAt3));
                    }
                    Object obj12 = (mode2 == 1073741824 || layoutParams.height != -1) ? null : 1;
                    i10 = layoutParams.topMargin + layoutParams.bottomMargin;
                    i12 = virtualChildAt3.getMeasuredHeight() + i10;
                    measuredHeight = Math.max(measuredHeight, i12);
                    i10 = Math.max(baseline, obj12 != null ? i10 : i12);
                    obj12 = (obj9 == null || layoutParams.height != -1) ? null : 1;
                    if (z) {
                        i11 = virtualChildAt3.getBaseline();
                        if (i11 != -1) {
                            i3 = ((((layoutParams.gravity < 0 ? this.mGravity : layoutParams.gravity) & 112) >> 4) & -2) >> 1;
                            iArr[i3] = Math.max(iArr[i3], i11);
                            iArr2[i3] = Math.max(iArr2[i3], i12 - i11);
                        }
                    }
                    obj = obj12;
                    i12 = i5;
                    f6 = f2;
                    i4 = i10;
                    i10 = i14;
                }
                i7++;
                i14 = i12;
                f5 = f6;
                obj9 = obj;
                i12 = i10;
                baseline = i4;
            }
            this.mTotalLength += getPaddingLeft() + getPaddingRight();
            i3 = (iArr[1] == -1 && iArr[0] == -1 && iArr[2] == -1 && iArr[3] == -1) ? measuredHeight : Math.max(measuredHeight, Math.max(iArr[3], Math.max(iArr[0], Math.max(iArr[1], iArr[2]))) + Math.max(iArr2[3], Math.max(iArr2[0], Math.max(iArr2[1], iArr2[2]))));
            obj2 = obj9;
            i4 = i3;
            i6 = i12;
            i3 = baseline;
        } else {
            baseline = Math.max(i7, i8);
            if (z2 && mode != 1073741824) {
                for (i4 = 0; i4 < virtualChildCount; i4++) {
                    View virtualChildAt4 = getVirtualChildAt(i4);
                    if (!(virtualChildAt4 == null || virtualChildAt4.getVisibility() == 8 || ((LayoutParams) virtualChildAt4.getLayoutParams()).weight <= 0.0f)) {
                        virtualChildAt4.measure(MeasureSpec.makeMeasureSpec(i9, 1073741824), MeasureSpec.makeMeasureSpec(virtualChildAt4.getMeasuredHeight(), 1073741824));
                    }
                }
            }
            i4 = i10;
            i3 = baseline;
        }
        if (obj2 != null || mode2 == 1073741824) {
            i3 = i4;
        }
        setMeasuredDimension((ViewCompat.MEASURED_STATE_MASK & i6) | resolveSizeAndState, ViewCompat.resolveSizeAndState(Math.max(i3 + (getPaddingTop() + getPaddingBottom()), getSuggestedMinimumHeight()), i2, i6 << 16));
        if (obj3 != null) {
            forceUniformHeight(virtualChildCount, i);
        }
    }

    int measureNullChild(int i) {
        return 0;
    }

    void measureVertical(int i, int i2) {
        int i3;
        int i4;
        int i5;
        Object obj;
        int i6;
        int i7;
        Object obj2;
        View virtualChildAt;
        this.mTotalLength = 0;
        int i8 = 0;
        int i9 = 0;
        int i10 = 0;
        int i11 = 0;
        Object obj3 = 1;
        float f = 0.0f;
        int virtualChildCount = getVirtualChildCount();
        int mode = MeasureSpec.getMode(i);
        int mode2 = MeasureSpec.getMode(i2);
        Object obj4 = null;
        Object obj5 = null;
        int i12 = this.mBaselineAlignedChildIndex;
        boolean z = this.mUseLargestChild;
        int i13 = Integer.MIN_VALUE;
        int i14 = 0;
        while (i14 < virtualChildCount) {
            Object obj6;
            float f2;
            Object obj7;
            int i15;
            View virtualChildAt2 = getVirtualChildAt(i14);
            if (virtualChildAt2 == null) {
                this.mTotalLength += measureNullChild(i14);
                i3 = i8;
                i4 = i10;
                i5 = i9;
                obj6 = obj4;
                obj = obj3;
                i6 = i11;
                i8 = i13;
                f2 = f;
                obj7 = obj5;
            } else {
                Object obj8;
                float f3;
                if (virtualChildAt2.getVisibility() != 8) {
                    if (hasDividerBeforeChildAt(i14)) {
                        this.mTotalLength += this.mDividerHeight;
                    }
                    LayoutParams layoutParams = (LayoutParams) virtualChildAt2.getLayoutParams();
                    float f4 = f + layoutParams.weight;
                    if (mode2 == 1073741824 && layoutParams.height == 0 && layoutParams.weight > 0.0f) {
                        i3 = this.mTotalLength;
                        this.mTotalLength = Math.max(i3, (layoutParams.topMargin + i3) + layoutParams.bottomMargin);
                        obj5 = 1;
                    } else {
                        i3 = Integer.MIN_VALUE;
                        if (layoutParams.height == 0 && layoutParams.weight > 0.0f) {
                            i3 = 0;
                            layoutParams.height = -2;
                        }
                        int i16 = i3;
                        measureChildBeforeLayout(virtualChildAt2, i14, i, 0, i2, f4 == 0.0f ? this.mTotalLength : 0);
                        if (i16 != Integer.MIN_VALUE) {
                            layoutParams.height = i16;
                        }
                        i3 = virtualChildAt2.getMeasuredHeight();
                        i15 = this.mTotalLength;
                        this.mTotalLength = Math.max(i15, (((i15 + i3) + layoutParams.topMargin) + layoutParams.bottomMargin) + getNextLocationOffset(virtualChildAt2));
                        if (z) {
                            i13 = Math.max(i3, i13);
                        }
                    }
                    if (i12 >= 0 && i12 == i14 + 1) {
                        this.mBaselineChildTop = this.mTotalLength;
                    }
                    if (i14 >= i12 || layoutParams.weight <= 0.0f) {
                        Object obj9 = null;
                        if (mode != 1073741824 && layoutParams.width == -1) {
                            obj4 = 1;
                            obj9 = 1;
                        }
                        i7 = layoutParams.rightMargin + layoutParams.leftMargin;
                        i5 = virtualChildAt2.getMeasuredWidth() + i7;
                        i8 = Math.max(i8, i5);
                        i9 = ViewUtils.combineMeasuredStates(i9, ViewCompat.getMeasuredState(virtualChildAt2));
                        obj7 = (obj3 == null || layoutParams.width != -1) ? null : 1;
                        if (layoutParams.weight > 0.0f) {
                            i11 = Math.max(i11, obj9 != null ? i7 : i5);
                            i3 = i8;
                            i7 = i9;
                            obj = obj4;
                            obj8 = obj7;
                            obj2 = obj5;
                            i15 = i10;
                            i8 = i11;
                            f3 = f4;
                            i10 = i13;
                        } else {
                            if (obj9 != null) {
                                i5 = i7;
                            }
                            i10 = Math.max(i10, i5);
                            i3 = i8;
                            i7 = i9;
                            obj = obj4;
                            obj8 = obj7;
                            obj2 = obj5;
                            i15 = i10;
                            i8 = i11;
                            f3 = f4;
                            i10 = i13;
                        }
                    } else {
                        throw new RuntimeException("A child of LinearLayout with index less than mBaselineAlignedChildIndex has weight > 0, which won't work.  Either remove the weight, or don't set mBaselineAlignedChildIndex.");
                    }
                }
                i3 = i8;
                i7 = i9;
                obj = obj4;
                obj8 = obj3;
                obj2 = obj5;
                i8 = i11;
                f3 = f;
                i15 = i10;
                i10 = i13;
                i14 += getChildrenSkipCount(virtualChildAt2, i14);
                i4 = i15;
                obj7 = obj2;
                i5 = i7;
                obj6 = obj;
                obj = obj8;
                i6 = i8;
                i8 = i10;
                f2 = f3;
            }
            i14++;
            obj5 = obj7;
            i13 = i8;
            i11 = i6;
            obj4 = obj6;
            obj3 = obj;
            i9 = i5;
            i8 = i3;
            f = f2;
            i10 = i4;
        }
        if (this.mTotalLength > 0 && hasDividerBeforeChildAt(virtualChildCount)) {
            this.mTotalLength += this.mDividerHeight;
        }
        if (z && (mode2 == Integer.MIN_VALUE || mode2 == 0)) {
            this.mTotalLength = 0;
            i4 = 0;
            while (i4 < virtualChildCount) {
                virtualChildAt = getVirtualChildAt(i4);
                if (virtualChildAt == null) {
                    this.mTotalLength += measureNullChild(i4);
                    i3 = i4;
                } else if (virtualChildAt.getVisibility() == 8) {
                    i3 = getChildrenSkipCount(virtualChildAt, i4) + i4;
                } else {
                    LayoutParams layoutParams2 = (LayoutParams) virtualChildAt.getLayoutParams();
                    i5 = this.mTotalLength;
                    this.mTotalLength = Math.max(i5, (layoutParams2.bottomMargin + (layoutParams2.topMargin + (i5 + i13))) + getNextLocationOffset(virtualChildAt));
                    i3 = i4;
                }
                i4 = i3 + 1;
            }
        }
        this.mTotalLength += getPaddingTop() + getPaddingBottom();
        int resolveSizeAndState = ViewCompat.resolveSizeAndState(Math.max(this.mTotalLength, getSuggestedMinimumHeight()), i2, 0);
        i5 = (ViewCompat.MEASURED_SIZE_MASK & resolveSizeAndState) - this.mTotalLength;
        if (obj5 != null || (i5 != 0 && f > 0.0f)) {
            if (this.mWeightSum > 0.0f) {
                f = this.mWeightSum;
            }
            this.mTotalLength = 0;
            int i17 = 0;
            obj = obj3;
            i6 = i10;
            float f5 = f;
            i15 = i9;
            while (i17 < virtualChildCount) {
                View virtualChildAt3 = getVirtualChildAt(i17);
                if (virtualChildAt3.getVisibility() != 8) {
                    layoutParams2 = (LayoutParams) virtualChildAt3.getLayoutParams();
                    f2 = layoutParams2.weight;
                    if (f2 > 0.0f) {
                        i14 = (int) ((((float) i5) * f2) / f5);
                        int childMeasureSpec = getChildMeasureSpec(i, ((getPaddingLeft() + getPaddingRight()) + layoutParams2.leftMargin) + layoutParams2.rightMargin, layoutParams2.width);
                        if (layoutParams2.height == 0 && mode2 == 1073741824) {
                            i4 = i14 > 0 ? i14 : 0;
                        } else {
                            i4 = virtualChildAt3.getMeasuredHeight() + i14;
                            if (i4 < 0) {
                                i4 = 0;
                            }
                        }
                        virtualChildAt3.measure(childMeasureSpec, MeasureSpec.makeMeasureSpec(i4, 1073741824));
                        i4 = ViewUtils.combineMeasuredStates(i15, ViewCompat.getMeasuredState(virtualChildAt3) & InputDeviceCompat.SOURCE_ANY);
                        i14 = i5 - i14;
                        f = f5 - f2;
                    } else {
                        i4 = i15;
                        i14 = i5;
                        f = f5;
                    }
                    i5 = layoutParams2.leftMargin + layoutParams2.rightMargin;
                    i7 = virtualChildAt3.getMeasuredWidth() + i5;
                    i8 = Math.max(i8, i7);
                    Object obj10 = (mode == 1073741824 || layoutParams2.width != -1) ? null : 1;
                    if (obj10 == null) {
                        i5 = i7;
                    }
                    i7 = Math.max(i6, i5);
                    obj2 = (obj == null || layoutParams2.width != -1) ? null : 1;
                    int i18 = this.mTotalLength;
                    this.mTotalLength = Math.max(i18, (layoutParams2.bottomMargin + ((virtualChildAt3.getMeasuredHeight() + i18) + layoutParams2.topMargin)) + getNextLocationOffset(virtualChildAt3));
                    i3 = i7;
                    f5 = f;
                    i15 = i14;
                    i14 = i8;
                } else {
                    i4 = i15;
                    i3 = i6;
                    i14 = i8;
                    i15 = i5;
                    obj2 = obj;
                }
                i17++;
                i8 = i14;
                i6 = i3;
                obj = obj2;
                i5 = i15;
                i15 = i4;
            }
            this.mTotalLength += getPaddingTop() + getPaddingBottom();
            obj3 = obj;
            i9 = i15;
            i4 = i8;
            i3 = i6;
        } else {
            i6 = Math.max(i10, i11);
            if (z && mode2 != 1073741824) {
                for (i4 = 0; i4 < virtualChildCount; i4++) {
                    virtualChildAt = getVirtualChildAt(i4);
                    if (!(virtualChildAt == null || virtualChildAt.getVisibility() == 8 || ((LayoutParams) virtualChildAt.getLayoutParams()).weight <= 0.0f)) {
                        virtualChildAt.measure(MeasureSpec.makeMeasureSpec(virtualChildAt.getMeasuredWidth(), 1073741824), MeasureSpec.makeMeasureSpec(i13, 1073741824));
                    }
                }
            }
            i4 = i8;
            i3 = i6;
        }
        if (obj3 != null || mode == 1073741824) {
            i3 = i4;
        }
        setMeasuredDimension(ViewCompat.resolveSizeAndState(Math.max(i3 + (getPaddingLeft() + getPaddingRight()), getSuggestedMinimumWidth()), i, i9), resolveSizeAndState);
        if (obj4 != null) {
            forceUniformWidth(virtualChildCount, i2);
        }
    }

    protected void onDraw(Canvas canvas) {
        if (this.mDivider != null) {
            if (this.mOrientation == 1) {
                drawDividersVertical(canvas);
            } else {
                drawDividersHorizontal(canvas);
            }
        }
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (VERSION.SDK_INT >= 14) {
            super.onInitializeAccessibilityEvent(accessibilityEvent);
            accessibilityEvent.setClassName(LinearLayoutCompat.class.getName());
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        if (VERSION.SDK_INT >= 14) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            accessibilityNodeInfo.setClassName(LinearLayoutCompat.class.getName());
        }
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.mOrientation == 1) {
            layoutVertical(i, i2, i3, i4);
        } else {
            layoutHorizontal(i, i2, i3, i4);
        }
    }

    public void onMeasure(int i, int i2) {
        if (this.mOrientation == 1) {
            measureVertical(i, i2);
        } else {
            measureHorizontal(i, i2);
        }
    }

    public void setBaselineAligned(boolean z) {
        this.mBaselineAligned = z;
    }

    public void setBaselineAlignedChildIndex(int i) {
        if (i < 0 || i >= getChildCount()) {
            throw new IllegalArgumentException("base aligned child index out of range (0, " + getChildCount() + ")");
        }
        this.mBaselineAlignedChildIndex = i;
    }

    public void setDividerDrawable(Drawable drawable) {
        boolean z = false;
        if (drawable != this.mDivider) {
            this.mDivider = drawable;
            if (drawable != null) {
                this.mDividerWidth = drawable.getIntrinsicWidth();
                this.mDividerHeight = drawable.getIntrinsicHeight();
            } else {
                this.mDividerWidth = 0;
                this.mDividerHeight = 0;
            }
            if (drawable == null) {
                z = true;
            }
            setWillNotDraw(z);
            requestLayout();
        }
    }

    public void setDividerPadding(int i) {
        this.mDividerPadding = i;
    }

    public void setGravity(int i) {
        if (this.mGravity != i) {
            int i2 = (GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK & i) == 0 ? GravityCompat.START | i : i;
            if ((i2 & 112) == 0) {
                i2 |= 48;
            }
            this.mGravity = i2;
            requestLayout();
        }
    }

    public void setHorizontalGravity(int i) {
        int i2 = i & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        if ((this.mGravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK) != i2) {
            this.mGravity = i2 | (this.mGravity & -8388616);
            requestLayout();
        }
    }

    public void setMeasureWithLargestChildEnabled(boolean z) {
        this.mUseLargestChild = z;
    }

    public void setOrientation(int i) {
        if (this.mOrientation != i) {
            this.mOrientation = i;
            requestLayout();
        }
    }

    public void setShowDividers(int i) {
        if (i != this.mShowDividers) {
            requestLayout();
        }
        this.mShowDividers = i;
    }

    public void setVerticalGravity(int i) {
        int i2 = i & 112;
        if ((this.mGravity & 112) != i2) {
            this.mGravity = i2 | (this.mGravity & -113);
            requestLayout();
        }
    }

    public void setWeightSum(float f) {
        this.mWeightSum = Math.max(0.0f, f);
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }
}
