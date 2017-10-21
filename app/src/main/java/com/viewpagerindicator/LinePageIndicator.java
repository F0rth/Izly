package com.viewpagerindicator;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.GripView;
import org.spongycastle.asn1.cmp.PKIFailureInfo;
import org.spongycastle.crypto.tls.CipherSuite;

public class LinePageIndicator extends View implements PageIndicator {
    private static final int INVALID_POINTER = -1;
    private int mActivePointerId;
    private boolean mCentered;
    private int mCurrentPage;
    private float mGapWidth;
    private boolean mIsDragging;
    private float mLastMotionX;
    private float mLineWidth;
    private OnPageChangeListener mListener;
    private final Paint mPaintSelected;
    private final Paint mPaintUnselected;
    private int mTouchSlop;
    private ViewPager mViewPager;

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public final SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public final SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        int currentPage;

        private SavedState(Parcel parcel) {
            super(parcel);
            this.currentPage = parcel.readInt();
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.currentPage);
        }
    }

    public LinePageIndicator(Context context) {
        this(context, null);
    }

    public LinePageIndicator(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.vpiLinePageIndicatorStyle);
    }

    public LinePageIndicator(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mPaintUnselected = new Paint(1);
        this.mPaintSelected = new Paint(1);
        this.mLastMotionX = -1.0f;
        this.mActivePointerId = -1;
        if (!isInEditMode()) {
            Resources resources = getResources();
            int color = resources.getColor(R.color.default_line_indicator_selected_color);
            int color2 = resources.getColor(R.color.default_line_indicator_unselected_color);
            float dimension = resources.getDimension(R.dimen.default_line_indicator_line_width);
            float dimension2 = resources.getDimension(R.dimen.default_line_indicator_gap_width);
            float dimension3 = resources.getDimension(R.dimen.default_line_indicator_stroke_width);
            boolean z = resources.getBoolean(R.bool.default_line_indicator_centered);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.LinePageIndicator, i, 0);
            this.mCentered = obtainStyledAttributes.getBoolean(R.styleable.LinePageIndicator_centered, z);
            this.mLineWidth = obtainStyledAttributes.getDimension(R.styleable.LinePageIndicator_lineWidth, dimension);
            this.mGapWidth = obtainStyledAttributes.getDimension(R.styleable.LinePageIndicator_gapWidth, dimension2);
            setStrokeWidth(obtainStyledAttributes.getDimension(R.styleable.LinePageIndicator_strokeWidth, dimension3));
            this.mPaintUnselected.setColor(obtainStyledAttributes.getColor(R.styleable.LinePageIndicator_unselectedColor, color2));
            this.mPaintSelected.setColor(obtainStyledAttributes.getColor(R.styleable.LinePageIndicator_selectedColor, color));
            Drawable drawable = obtainStyledAttributes.getDrawable(R.styleable.LinePageIndicator_android_background);
            if (drawable != null) {
                setBackgroundDrawable(drawable);
            }
            obtainStyledAttributes.recycle();
            this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(context));
        }
    }

    private int measureHeight(int i) {
        float f;
        int mode = MeasureSpec.getMode(i);
        int size = MeasureSpec.getSize(i);
        if (mode == 1073741824) {
            f = (float) size;
        } else {
            f = (this.mPaintSelected.getStrokeWidth() + ((float) getPaddingTop())) + ((float) getPaddingBottom());
            if (mode == PKIFailureInfo.systemUnavail) {
                f = Math.min(f, (float) size);
            }
        }
        return (int) FloatMath.ceil(f);
    }

    private int measureWidth(int i) {
        float f;
        int mode = MeasureSpec.getMode(i);
        int size = MeasureSpec.getSize(i);
        if (mode == 1073741824 || this.mViewPager == null) {
            f = (float) size;
        } else {
            int count = this.mViewPager.getAdapter().getCount();
            f = (((float) (count - 1)) * this.mGapWidth) + (((float) (getPaddingLeft() + getPaddingRight())) + (((float) count) * this.mLineWidth));
            if (mode == PKIFailureInfo.systemUnavail) {
                f = Math.min(f, (float) size);
            }
        }
        return (int) FloatMath.ceil(f);
    }

    public float getGapWidth() {
        return this.mGapWidth;
    }

    public float getLineWidth() {
        return this.mLineWidth;
    }

    public int getSelectedColor() {
        return this.mPaintSelected.getColor();
    }

    public float getStrokeWidth() {
        return this.mPaintSelected.getStrokeWidth();
    }

    public int getUnselectedColor() {
        return this.mPaintUnselected.getColor();
    }

    public boolean isCentered() {
        return this.mCentered;
    }

    public void notifyDataSetChanged() {
        invalidate();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mViewPager != null) {
            int count = this.mViewPager.getAdapter().getCount();
            if (count == 0) {
                return;
            }
            if (this.mCurrentPage >= count) {
                setCurrentItem(count - 1);
                return;
            }
            float f = this.mLineWidth + this.mGapWidth;
            float paddingTop = (float) getPaddingTop();
            float paddingLeft = (float) getPaddingLeft();
            paddingTop += ((((float) getHeight()) - paddingTop) - ((float) getPaddingBottom())) / GripView.DEFAULT_DOT_SIZE_RADIUS_DP;
            float width = this.mCentered ? paddingLeft + ((((((float) getWidth()) - paddingLeft) - ((float) getPaddingRight())) / GripView.DEFAULT_DOT_SIZE_RADIUS_DP) - (((((float) count) * f) - this.mGapWidth) / GripView.DEFAULT_DOT_SIZE_RADIUS_DP)) : paddingLeft;
            int i = 0;
            while (i < count) {
                float f2 = width + (((float) i) * f);
                canvas.drawLine(f2, paddingTop, f2 + this.mLineWidth, paddingTop, i == this.mCurrentPage ? this.mPaintSelected : this.mPaintUnselected);
                i++;
            }
        }
    }

    protected void onMeasure(int i, int i2) {
        setMeasuredDimension(measureWidth(i), measureHeight(i2));
    }

    public void onPageScrollStateChanged(int i) {
        if (this.mListener != null) {
            this.mListener.onPageScrollStateChanged(i);
        }
    }

    public void onPageScrolled(int i, float f, int i2) {
        if (this.mListener != null) {
            this.mListener.onPageScrolled(i, f, i2);
        }
    }

    public void onPageSelected(int i) {
        this.mCurrentPage = i;
        invalidate();
        if (this.mListener != null) {
            this.mListener.onPageSelected(i);
        }
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mCurrentPage = savedState.currentPage;
        requestLayout();
    }

    public Parcelable onSaveInstanceState() {
        Parcelable savedState = new SavedState(super.onSaveInstanceState());
        savedState.currentPage = this.mCurrentPage;
        return savedState;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int i = 0;
        if (super.onTouchEvent(motionEvent)) {
            return true;
        }
        if (this.mViewPager == null || this.mViewPager.getAdapter().getCount() == 0) {
            return false;
        }
        int action = motionEvent.getAction() & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV;
        switch (action) {
            case 0:
                this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, 0);
                this.mLastMotionX = motionEvent.getX();
                return true;
            case 1:
            case 3:
                if (!this.mIsDragging) {
                    int count = this.mViewPager.getAdapter().getCount();
                    int width = getWidth();
                    float f = ((float) width) / GripView.DEFAULT_DOT_SIZE_RADIUS_DP;
                    float f2 = ((float) width) / 6.0f;
                    if (this.mCurrentPage <= 0 || motionEvent.getX() >= f - f2) {
                        if (this.mCurrentPage < count - 1 && motionEvent.getX() > f2 + f) {
                            if (action == 3) {
                                return true;
                            }
                            this.mViewPager.setCurrentItem(this.mCurrentPage + 1);
                            return true;
                        }
                    } else if (action == 3) {
                        return true;
                    } else {
                        this.mViewPager.setCurrentItem(this.mCurrentPage - 1);
                        return true;
                    }
                }
                this.mIsDragging = false;
                this.mActivePointerId = -1;
                if (!this.mViewPager.isFakeDragging()) {
                    return true;
                }
                this.mViewPager.endFakeDrag();
                return true;
            case 2:
                float x = MotionEventCompat.getX(motionEvent, MotionEventCompat.findPointerIndex(motionEvent, this.mActivePointerId));
                float f3 = x - this.mLastMotionX;
                if (!this.mIsDragging && Math.abs(f3) > ((float) this.mTouchSlop)) {
                    this.mIsDragging = true;
                }
                if (!this.mIsDragging) {
                    return true;
                }
                this.mLastMotionX = x;
                if (!this.mViewPager.isFakeDragging() && !this.mViewPager.beginFakeDrag()) {
                    return true;
                }
                this.mViewPager.fakeDragBy(f3);
                return true;
            case 5:
                i = MotionEventCompat.getActionIndex(motionEvent);
                this.mLastMotionX = MotionEventCompat.getX(motionEvent, i);
                this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, i);
                return true;
            case 6:
                action = MotionEventCompat.getActionIndex(motionEvent);
                if (MotionEventCompat.getPointerId(motionEvent, action) == this.mActivePointerId) {
                    if (action == 0) {
                        i = 1;
                    }
                    this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, i);
                }
                this.mLastMotionX = MotionEventCompat.getX(motionEvent, MotionEventCompat.findPointerIndex(motionEvent, this.mActivePointerId));
                return true;
            default:
                return true;
        }
    }

    public void setCentered(boolean z) {
        this.mCentered = z;
        invalidate();
    }

    public void setCurrentItem(int i) {
        if (this.mViewPager == null) {
            throw new IllegalStateException("ViewPager has not been bound.");
        }
        this.mViewPager.setCurrentItem(i);
        this.mCurrentPage = i;
        invalidate();
    }

    public void setGapWidth(float f) {
        this.mGapWidth = f;
        invalidate();
    }

    public void setLineWidth(float f) {
        this.mLineWidth = f;
        invalidate();
    }

    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.mListener = onPageChangeListener;
    }

    public void setSelectedColor(int i) {
        this.mPaintSelected.setColor(i);
        invalidate();
    }

    public void setStrokeWidth(float f) {
        this.mPaintSelected.setStrokeWidth(f);
        this.mPaintUnselected.setStrokeWidth(f);
        invalidate();
    }

    public void setUnselectedColor(int i) {
        this.mPaintUnselected.setColor(i);
        invalidate();
    }

    public void setViewPager(ViewPager viewPager) {
        if (this.mViewPager != viewPager) {
            if (this.mViewPager != null) {
                this.mViewPager.setOnPageChangeListener(null);
            }
            if (viewPager.getAdapter() == null) {
                throw new IllegalStateException("ViewPager does not have adapter instance.");
            }
            this.mViewPager = viewPager;
            this.mViewPager.setOnPageChangeListener(this);
            invalidate();
        }
    }

    public void setViewPager(ViewPager viewPager, int i) {
        setViewPager(viewPager);
        setCurrentItem(i);
    }
}
