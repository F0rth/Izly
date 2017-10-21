package com.astuetz.viewpagertabs;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class ViewPagerTabs extends ViewGroup implements OnPageChangeListener {
    private static final int SHADOW_WIDTH = 35;
    private static final String TAG = "ViewPagerTabs";
    private boolean isFirstMeasurement;
    private int mBackgroundColorPressed;
    private Context mContext;
    private int mLineColor;
    private int mLineColorCenter;
    private int mLineHeight;
    private int mOutsideOffset;
    private ViewPager mPager;
    private int mPosition;
    private int mTabPaddingBottom;
    private int mTabPaddingLeft;
    private int mTabPaddingRight;
    private int mTabPaddingTop;
    private int mTextColor;
    private int mTextColorCenter;
    private float mTextSize;

    enum Direction {
        Left,
        Right,
        Center
    }

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public final SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public final SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        int backgroundColorPressed;
        int lineColorCenter;
        int lineHeight;
        int position;
        int tabPaddingBottom;
        int tabPaddingLeft;
        int tabPaddingRight;
        int tabPaddingTop;
        int textColor;
        int textColorCenter;
        float textSize;

        private SavedState(Parcel parcel) {
            super(parcel);
            this.position = parcel.readInt();
            this.backgroundColorPressed = parcel.readInt();
            this.textColor = parcel.readInt();
            this.textColorCenter = parcel.readInt();
            this.lineColorCenter = parcel.readInt();
            this.lineHeight = parcel.readInt();
            this.tabPaddingLeft = parcel.readInt();
            this.tabPaddingTop = parcel.readInt();
            this.tabPaddingRight = parcel.readInt();
            this.tabPaddingBottom = parcel.readInt();
            this.textSize = parcel.readFloat();
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.position);
            parcel.writeInt(this.backgroundColorPressed);
            parcel.writeInt(this.textColor);
            parcel.writeInt(this.textColorCenter);
            parcel.writeInt(this.lineColorCenter);
            parcel.writeInt(this.lineHeight);
            parcel.writeInt(this.tabPaddingLeft);
            parcel.writeInt(this.tabPaddingTop);
            parcel.writeInt(this.tabPaddingRight);
            parcel.writeInt(this.tabPaddingBottom);
            parcel.writeFloat(this.textSize);
        }
    }

    public ViewPagerTabs(Context context) {
        this(context, null);
    }

    public ViewPagerTabs(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ViewPagerTabs(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mPosition = 0;
        this.mOutsideOffset = -1;
        this.isFirstMeasurement = true;
        this.mBackgroundColorPressed = -1723631233;
        this.mTextColor = -6710887;
        this.mTextColorCenter = -7232456;
        this.mLineColor = 0;
        this.mLineColorCenter = -7232456;
        this.mLineHeight = 3;
        this.mTabPaddingLeft = 25;
        this.mTabPaddingTop = 5;
        this.mTabPaddingRight = 25;
        this.mTabPaddingBottom = 10;
        this.mTextSize = 14.0f;
        this.mContext = context;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ViewPagerTabs, i, 0);
        this.mBackgroundColorPressed = obtainStyledAttributes.getColor(R.styleable.ViewPagerTabs_backgroundColorPressed, this.mBackgroundColorPressed);
        this.mTextColor = obtainStyledAttributes.getColor(R.styleable.ViewPagerTabs_textColor, this.mTextColor);
        this.mTextColorCenter = obtainStyledAttributes.getColor(R.styleable.ViewPagerTabs_textColorCenter, this.mTextColorCenter);
        this.mLineColorCenter = obtainStyledAttributes.getColor(R.styleable.ViewPagerTabs_lineColorCenter, this.mLineColorCenter);
        this.mLineHeight = obtainStyledAttributes.getDimensionPixelSize(R.styleable.ViewPagerTabs_lineHeight, this.mLineHeight);
        this.mTextSize = obtainStyledAttributes.getDimension(R.styleable.ViewPagerTabs_textSize, this.mTextSize);
        this.mTabPaddingLeft = obtainStyledAttributes.getDimensionPixelSize(R.styleable.ViewPagerTabs_tabPaddingLeft, this.mTabPaddingLeft);
        this.mTabPaddingTop = obtainStyledAttributes.getDimensionPixelSize(R.styleable.TabLayout_tabPaddingTop, this.mTabPaddingTop);
        this.mTabPaddingRight = obtainStyledAttributes.getDimensionPixelSize(R.styleable.ViewPagerTabs_tabPaddingRight, this.mTabPaddingRight);
        this.mTabPaddingBottom = obtainStyledAttributes.getDimensionPixelSize(R.styleable.TabLayout_tabPaddingBottom, this.mTabPaddingBottom);
        this.mOutsideOffset = obtainStyledAttributes.getDimensionPixelSize(R.styleable.ViewPagerTabs_outsideOffset, this.mOutsideOffset);
        obtainStyledAttributes.recycle();
        setHorizontalFadingEdgeEnabled(true);
        setFadingEdgeLength((int) (getResources().getDisplayMetrics().density * 35.0f));
        setWillNotDraw(false);
    }

    private void applyStyles() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            ViewPagerTab viewPagerTab = (ViewPagerTab) getChildAt(i);
            viewPagerTab.setPadding(this.mTabPaddingLeft, this.mTabPaddingTop, this.mTabPaddingRight, (this.mLineHeight + this.mTabPaddingBottom) - 4);
            viewPagerTab.setTextColors(this.mTextColor, this.mTextColorCenter);
            viewPagerTab.setLineColors(this.mLineColor, this.mLineColorCenter);
            viewPagerTab.setLineHeight(this.mLineHeight);
            viewPagerTab.setBackgroundColorPressed(this.mBackgroundColorPressed);
            viewPagerTab.setTextSize(this.mTextSize);
        }
        measureChildren();
        calculateNewPositions(true);
        requestLayout();
    }

    private void calculateNewPositions(boolean z) {
        int i;
        ViewPagerTab viewPagerTab;
        int i2 = this.mPosition;
        int childCount = getChildCount();
        for (i = 0; i < i2 - 2; i++) {
            viewPagerTab = (ViewPagerTab) getChildAt(i);
            viewPagerTab.currentPos = leftOutside(viewPagerTab);
            viewPagerTab.prevPos = viewPagerTab.currentPos;
            viewPagerTab.nextPos = viewPagerTab.currentPos;
        }
        if (i2 > 1) {
            viewPagerTab = (ViewPagerTab) getChildAt(i2 - 2);
            viewPagerTab.currentPos = leftOutside(viewPagerTab);
            viewPagerTab.prevPos = viewPagerTab.currentPos;
            viewPagerTab.nextPos = left(viewPagerTab);
        }
        if (i2 > 0) {
            viewPagerTab = (ViewPagerTab) getChildAt(i2 - 1);
            viewPagerTab.currentPos = left(viewPagerTab);
            viewPagerTab.prevPos = leftOutside(viewPagerTab);
            viewPagerTab.nextPos = center(viewPagerTab);
        }
        if (childCount > 0) {
            viewPagerTab = (ViewPagerTab) getChildAt(i2);
            viewPagerTab.currentPos = center(viewPagerTab);
            viewPagerTab.prevPos = left(viewPagerTab);
            viewPagerTab.nextPos = right(viewPagerTab);
        }
        if (i2 < childCount - 1) {
            viewPagerTab = (ViewPagerTab) getChildAt(i2 + 1);
            viewPagerTab.currentPos = right(viewPagerTab);
            viewPagerTab.prevPos = center(viewPagerTab);
            viewPagerTab.nextPos = rightOutside(viewPagerTab);
        }
        if (i2 < childCount - 2) {
            viewPagerTab = (ViewPagerTab) getChildAt(i2 + 2);
            viewPagerTab.currentPos = rightOutside(viewPagerTab);
            viewPagerTab.prevPos = right(viewPagerTab);
            viewPagerTab.nextPos = viewPagerTab.currentPos;
        }
        for (i = i2 + 3; i < childCount; i++) {
            viewPagerTab = (ViewPagerTab) getChildAt(i);
            viewPagerTab.currentPos = rightOutside(viewPagerTab);
            viewPagerTab.prevPos = viewPagerTab.currentPos;
            viewPagerTab.nextPos = viewPagerTab.currentPos;
        }
        ViewPagerTab viewPagerTab2 = i2 > 1 ? (ViewPagerTab) getChildAt(i2 - 2) : null;
        ViewPagerTab viewPagerTab3 = i2 > 0 ? (ViewPagerTab) getChildAt(i2 - 1) : null;
        viewPagerTab = (ViewPagerTab) getChildAt(i2);
        ViewPagerTab viewPagerTab4 = i2 < getChildCount() + -1 ? (ViewPagerTab) getChildAt(i2 + 1) : null;
        ViewPagerTab viewPagerTab5 = i2 < getChildCount() + -2 ? (ViewPagerTab) getChildAt(i2 + 2) : null;
        if (viewPagerTab2 != null && viewPagerTab2.nextPos + viewPagerTab2.getMeasuredWidth() >= viewPagerTab3.nextPos) {
            viewPagerTab2.nextPos = viewPagerTab3.nextPos - viewPagerTab2.getMeasuredWidth();
        }
        if (viewPagerTab3 != null) {
            if (viewPagerTab3.currentPos + viewPagerTab3.getMeasuredWidth() >= viewPagerTab.currentPos) {
                viewPagerTab3.currentPos = viewPagerTab.currentPos - viewPagerTab3.getMeasuredWidth();
            }
            if (viewPagerTab.nextPos <= viewPagerTab3.nextPos + viewPagerTab3.getMeasuredWidth()) {
                viewPagerTab.nextPos = viewPagerTab3.getMeasuredWidth() + viewPagerTab3.nextPos;
            }
        }
        if (viewPagerTab4 != null) {
            if (viewPagerTab.prevPos + viewPagerTab.getMeasuredWidth() >= viewPagerTab4.prevPos) {
                viewPagerTab.prevPos = viewPagerTab4.prevPos - viewPagerTab.getMeasuredWidth();
            }
            if (viewPagerTab4.currentPos <= viewPagerTab.currentPos + viewPagerTab.getMeasuredWidth()) {
                viewPagerTab4.currentPos = viewPagerTab.getMeasuredWidth() + viewPagerTab.currentPos;
            }
        }
        if (viewPagerTab5 != null && viewPagerTab5.prevPos <= viewPagerTab4.prevPos + viewPagerTab4.getMeasuredWidth()) {
            viewPagerTab5.prevPos = viewPagerTab4.prevPos + viewPagerTab4.getMeasuredWidth();
        }
        if (z) {
            for (i = 0; i < childCount; i++) {
                viewPagerTab = (ViewPagerTab) getChildAt(i);
                viewPagerTab.layoutPos = viewPagerTab.currentPos;
            }
        }
    }

    private int center(ViewPagerTab viewPagerTab) {
        return (getMeasuredWidth() / 2) - (viewPagerTab.getMeasuredWidth() / 2);
    }

    private int left(ViewPagerTab viewPagerTab) {
        return 0 - viewPagerTab.getPaddingLeft();
    }

    private int leftOutside(ViewPagerTab viewPagerTab) {
        return (viewPagerTab.getMeasuredWidth() * -1) - this.mOutsideOffset;
    }

    private void measureChildren() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            LayoutParams layoutParams = getChildAt(i).getLayoutParams();
            getChildAt(i).measure(MeasureSpec.makeMeasureSpec(layoutParams.width, 1073741824), MeasureSpec.makeMeasureSpec(layoutParams.height, 1073741824));
        }
    }

    private int right(ViewPagerTab viewPagerTab) {
        return (getMeasuredWidth() - viewPagerTab.getMeasuredWidth()) + viewPagerTab.getPaddingRight();
    }

    private int rightOutside(ViewPagerTab viewPagerTab) {
        return getMeasuredWidth() + this.mOutsideOffset;
    }

    public void addTab(int i, String str) {
        View viewPagerTab = new ViewPagerTab(this.mContext);
        viewPagerTab.setText(str);
        viewPagerTab.setIndex(i);
        addView(viewPagerTab);
        viewPagerTab.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ViewPagerTabs.this.mPager.setCurrentItem(((ViewPagerTab) view).getIndex());
            }
        });
    }

    protected float getLeftFadingEdgeStrength() {
        return 1.0f;
    }

    public int getPosition() {
        return this.mPosition;
    }

    protected float getRightFadingEdgeStrength() {
        return 1.0f;
    }

    public void notifyDatasetChanged() {
        removeAllViews();
        for (int i = 0; i < this.mPager.getAdapter().getCount(); i++) {
            addTab(i, ((ViewPagerTabProvider) this.mPager.getAdapter()).getTitle(i));
        }
        applyStyles();
        calculateNewPositions(true);
        requestLayout();
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childCount = getChildCount();
        int measuredWidth = getMeasuredWidth() / 2;
        int measuredWidth2 = getMeasuredWidth() / 5;
        for (int i5 = 0; i5 < childCount; i5++) {
            ViewPagerTab viewPagerTab = (ViewPagerTab) getChildAt(i5);
            viewPagerTab.invalidate();
            int abs = Math.abs(measuredWidth - (viewPagerTab.layoutPos + (viewPagerTab.getMeasuredWidth() / 2)));
            if (abs <= measuredWidth2) {
                viewPagerTab.setCenterPercent(100 - ((abs * 100) / measuredWidth2));
            } else {
                viewPagerTab.setCenterPercent(0);
            }
            viewPagerTab.layout(viewPagerTab.layoutPos, getPaddingTop(), viewPagerTab.layoutPos + viewPagerTab.getMeasuredWidth(), getPaddingTop() + viewPagerTab.getMeasuredHeight());
        }
    }

    protected void onMeasure(int i, int i2) {
        View childAt = getChildAt(0);
        if (childAt != null) {
            TextView textView = (TextView) childAt;
            LayoutParams layoutParams = textView.getLayoutParams();
            textView.measure(MeasureSpec.makeMeasureSpec(layoutParams.width, 1073741824), MeasureSpec.makeMeasureSpec(layoutParams.height, 1073741824));
            setMeasuredDimension(resolveSize(0, i), resolveSize((textView.getMeasuredHeight() + getPaddingTop()) + getPaddingBottom(), i2));
        } else {
            setMeasuredDimension(resolveSize(0, i), resolveSize(getPaddingTop() + getPaddingBottom(), i2));
        }
        if (this.isFirstMeasurement) {
            this.isFirstMeasurement = false;
            if (this.mOutsideOffset < 0) {
                this.mOutsideOffset = getMeasuredWidth();
            }
            measureChildren();
            calculateNewPositions(true);
        }
    }

    public void onPageScrollStateChanged(int i) {
    }

    public void onPageScrolled(int i, float f, int i2) {
        Direction direction;
        int childCount = getChildCount();
        int width = (this.mPager.getWidth() + this.mPager.getPageMargin()) * this.mPosition;
        Direction direction2 = Direction.Center;
        if (this.mPager.getScrollX() < width) {
            f = 1.0f - f;
            direction = Direction.Left;
        } else if (this.mPager.getScrollX() > width) {
            direction = Direction.Right;
        } else {
            f = 0.0f;
            direction = direction2;
        }
        for (int i3 = 0; i3 < childCount; i3++) {
            ViewPagerTab viewPagerTab = (ViewPagerTab) getChildAt(i3);
            float f2 = (float) viewPagerTab.currentPos;
            float f3 = direction == Direction.Left ? (float) viewPagerTab.nextPos : direction == Direction.Right ? (float) viewPagerTab.prevPos : (float) viewPagerTab.currentPos;
            viewPagerTab.layoutPos = (int) (((f3 * f) - (f2 * f)) + f2);
        }
        requestLayout();
    }

    public void onPageSelected(int i) {
        this.mPosition = i;
        calculateNewPositions(false);
        requestLayout();
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mPosition = savedState.position;
        this.mBackgroundColorPressed = savedState.backgroundColorPressed;
        this.mTextColor = savedState.textColor;
        this.mTextColorCenter = savedState.textColorCenter;
        this.mLineColorCenter = savedState.lineColorCenter;
        this.mLineHeight = savedState.lineHeight;
        this.mTabPaddingLeft = savedState.tabPaddingLeft;
        this.mTabPaddingTop = savedState.tabPaddingTop;
        this.mTabPaddingRight = savedState.tabPaddingRight;
        this.mTabPaddingBottom = savedState.tabPaddingBottom;
        this.mTextSize = savedState.textSize;
        applyStyles();
    }

    public Parcelable onSaveInstanceState() {
        Parcelable savedState = new SavedState(super.onSaveInstanceState());
        savedState.position = this.mPosition;
        savedState.backgroundColorPressed = this.mBackgroundColorPressed;
        savedState.textColor = this.mTextColor;
        savedState.textColorCenter = this.mTextColorCenter;
        savedState.lineColorCenter = this.mLineColorCenter;
        savedState.lineHeight = this.mLineHeight;
        savedState.tabPaddingLeft = this.mTabPaddingLeft;
        savedState.tabPaddingTop = this.mTabPaddingTop;
        savedState.tabPaddingRight = this.mTabPaddingRight;
        savedState.tabPaddingBottom = this.mTabPaddingBottom;
        savedState.textSize = this.mTextSize;
        return savedState;
    }

    public void refreshTitles() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            ((ViewPagerTab) getChildAt(i)).setText(((ViewPagerTabProvider) this.mPager.getAdapter()).getTitle(i));
        }
        measureChildren();
        calculateNewPositions(true);
        requestLayout();
    }

    public void setBackgroundColorPressed(int i) {
        this.mBackgroundColorPressed = i;
        applyStyles();
    }

    public void setLineColorCenter(int i) {
        this.mLineColorCenter = i;
        applyStyles();
    }

    public void setLineHeight(int i) {
        this.mLineHeight = i;
        applyStyles();
    }

    public void setOutsideOffset(int i) {
        this.mOutsideOffset = i;
        applyStyles();
    }

    public void setTabPadding(int i, int i2, int i3, int i4) {
        this.mTabPaddingLeft = i;
        this.mTabPaddingTop = i2;
        this.mTabPaddingRight = i3;
        this.mTabPaddingBottom = i4;
        applyStyles();
    }

    public void setTabPaddingBottom(int i) {
        this.mTabPaddingBottom = i;
        applyStyles();
    }

    public void setTabPaddingLeft(int i) {
        this.mTabPaddingLeft = i;
        applyStyles();
    }

    public void setTabPaddingRight(int i) {
        this.mTabPaddingRight = i;
        applyStyles();
    }

    public void setTabPaddingTop(int i) {
        this.mTabPaddingTop = i;
        applyStyles();
    }

    public void setTextColor(int i) {
        this.mTextColor = i;
        applyStyles();
    }

    public void setTextColorCenter(int i) {
        this.mTextColorCenter = i;
        applyStyles();
    }

    public void setTextSize(float f) {
        this.mTextSize = f;
        applyStyles();
    }

    public void setViewPager(ViewPager viewPager) {
        if (viewPager.getAdapter() instanceof ViewPagerTabProvider) {
            this.mPager = viewPager;
            this.mPager.setCurrentItem(this.mPosition);
            this.mPager.setOnPageChangeListener(this);
            notifyDatasetChanged();
            return;
        }
        throw new IllegalStateException("The pager's adapter has to implement ViewPagerTabProvider.");
    }

    public void setViewPager(ViewPager viewPager, int i) {
        this.mPosition = i;
        setViewPager(viewPager);
    }
}
