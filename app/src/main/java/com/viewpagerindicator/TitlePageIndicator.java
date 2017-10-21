package com.viewpagerindicator;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.GripView;
import java.util.ArrayList;
import org.spongycastle.crypto.tls.CipherSuite;

public class TitlePageIndicator extends View implements PageIndicator {
    private static final float BOLD_FADE_PERCENTAGE = 0.05f;
    private static final String EMPTY_TITLE = "";
    private static final int INVALID_POINTER = -1;
    private static final float SELECTION_FADE_PERCENTAGE = 0.25f;
    private int mActivePointerId;
    private boolean mBoldText;
    private final Rect mBounds;
    private OnCenterItemClickListener mCenterItemClickListener;
    private float mClipPadding;
    private int mColorSelected;
    private int mColorText;
    private int mCurrentPage;
    private float mFooterIndicatorHeight;
    private IndicatorStyle mFooterIndicatorStyle;
    private float mFooterIndicatorUnderlinePadding;
    private float mFooterLineHeight;
    private float mFooterPadding;
    private boolean mIsDragging;
    private float mLastMotionX;
    private LinePosition mLinePosition;
    private OnPageChangeListener mListener;
    private float mPageOffset;
    private final Paint mPaintFooterIndicator;
    private final Paint mPaintFooterLine;
    private final Paint mPaintText;
    private Path mPath;
    private int mScrollState;
    private float mTitlePadding;
    private float mTopPadding;
    private int mTouchSlop;
    private ViewPager mViewPager;

    public enum IndicatorStyle {
        None(0),
        Triangle(1),
        Underline(2);
        
        public final int value;

        private IndicatorStyle(int i) {
            this.value = i;
        }

        public static IndicatorStyle fromValue(int i) {
            for (IndicatorStyle indicatorStyle : values()) {
                if (indicatorStyle.value == i) {
                    return indicatorStyle;
                }
            }
            return null;
        }
    }

    public enum LinePosition {
        Bottom(0),
        Top(1);
        
        public final int value;

        private LinePosition(int i) {
            this.value = i;
        }

        public static LinePosition fromValue(int i) {
            for (LinePosition linePosition : values()) {
                if (linePosition.value == i) {
                    return linePosition;
                }
            }
            return null;
        }
    }

    public interface OnCenterItemClickListener {
        void onCenterItemClick(int i);
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

    public TitlePageIndicator(Context context) {
        this(context, null);
    }

    public TitlePageIndicator(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.vpiTitlePageIndicatorStyle);
    }

    public TitlePageIndicator(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mCurrentPage = -1;
        this.mPaintText = new Paint();
        this.mPath = new Path();
        this.mBounds = new Rect();
        this.mPaintFooterLine = new Paint();
        this.mPaintFooterIndicator = new Paint();
        this.mLastMotionX = -1.0f;
        this.mActivePointerId = -1;
        if (!isInEditMode()) {
            Resources resources = getResources();
            int color = resources.getColor(R.color.default_title_indicator_footer_color);
            float dimension = resources.getDimension(R.dimen.default_title_indicator_footer_line_height);
            int integer = resources.getInteger(R.integer.default_title_indicator_footer_indicator_style);
            float dimension2 = resources.getDimension(R.dimen.default_title_indicator_footer_indicator_height);
            float dimension3 = resources.getDimension(R.dimen.default_title_indicator_footer_indicator_underline_padding);
            float dimension4 = resources.getDimension(R.dimen.default_title_indicator_footer_padding);
            int integer2 = resources.getInteger(R.integer.default_title_indicator_line_position);
            int color2 = resources.getColor(R.color.default_title_indicator_selected_color);
            boolean z = resources.getBoolean(R.bool.default_title_indicator_selected_bold);
            int color3 = resources.getColor(R.color.default_title_indicator_text_color);
            float dimension5 = resources.getDimension(R.dimen.default_title_indicator_text_size);
            float dimension6 = resources.getDimension(R.dimen.default_title_indicator_title_padding);
            float dimension7 = resources.getDimension(R.dimen.default_title_indicator_clip_padding);
            float dimension8 = resources.getDimension(R.dimen.default_title_indicator_top_padding);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.TitlePageIndicator, i, 0);
            this.mFooterLineHeight = obtainStyledAttributes.getDimension(R.styleable.TitlePageIndicator_footerLineHeight, dimension);
            this.mFooterIndicatorStyle = IndicatorStyle.fromValue(obtainStyledAttributes.getInteger(R.styleable.TitlePageIndicator_footerIndicatorStyle, integer));
            this.mFooterIndicatorHeight = obtainStyledAttributes.getDimension(R.styleable.TitlePageIndicator_footerIndicatorHeight, dimension2);
            this.mFooterIndicatorUnderlinePadding = obtainStyledAttributes.getDimension(R.styleable.TitlePageIndicator_footerIndicatorUnderlinePadding, dimension3);
            this.mFooterPadding = obtainStyledAttributes.getDimension(R.styleable.TitlePageIndicator_footerPadding, dimension4);
            this.mLinePosition = LinePosition.fromValue(obtainStyledAttributes.getInteger(R.styleable.TitlePageIndicator_linePosition, integer2));
            this.mTopPadding = obtainStyledAttributes.getDimension(R.styleable.TitlePageIndicator_topPadding, dimension8);
            this.mTitlePadding = obtainStyledAttributes.getDimension(R.styleable.TitlePageIndicator_titlePadding, dimension6);
            this.mClipPadding = obtainStyledAttributes.getDimension(R.styleable.TitlePageIndicator_clipPadding, dimension7);
            this.mColorSelected = obtainStyledAttributes.getColor(R.styleable.TitlePageIndicator_selectedColor, color2);
            this.mColorText = obtainStyledAttributes.getColor(R.styleable.TitlePageIndicator_android_textColor, color3);
            this.mBoldText = obtainStyledAttributes.getBoolean(R.styleable.TitlePageIndicator_selectedBold, z);
            dimension8 = obtainStyledAttributes.getDimension(R.styleable.TitlePageIndicator_android_textSize, dimension5);
            color = obtainStyledAttributes.getColor(R.styleable.TitlePageIndicator_footerColor, color);
            this.mPaintText.setTextSize(dimension8);
            this.mPaintText.setAntiAlias(true);
            this.mPaintFooterLine.setStyle(Style.FILL_AND_STROKE);
            this.mPaintFooterLine.setStrokeWidth(this.mFooterLineHeight);
            this.mPaintFooterLine.setColor(color);
            this.mPaintFooterIndicator.setStyle(Style.FILL_AND_STROKE);
            this.mPaintFooterIndicator.setColor(color);
            Drawable drawable = obtainStyledAttributes.getDrawable(R.styleable.TitlePageIndicator_android_background);
            if (drawable != null) {
                setBackgroundDrawable(drawable);
            }
            obtainStyledAttributes.recycle();
            this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(ViewConfiguration.get(context));
        }
    }

    private Rect calcBounds(int i, Paint paint) {
        Rect rect = new Rect();
        CharSequence title = getTitle(i);
        rect.right = (int) paint.measureText(title, 0, title.length());
        rect.bottom = (int) (paint.descent() - paint.ascent());
        return rect;
    }

    private ArrayList<Rect> calculateAllBounds(Paint paint) {
        ArrayList<Rect> arrayList = new ArrayList();
        int count = this.mViewPager.getAdapter().getCount();
        int width = getWidth();
        int i = width / 2;
        for (int i2 = 0; i2 < count; i2++) {
            Rect calcBounds = calcBounds(i2, paint);
            int i3 = calcBounds.right - calcBounds.left;
            int i4 = calcBounds.bottom;
            int i5 = calcBounds.top;
            calcBounds.left = (int) ((((float) i) - (((float) i3) / GripView.DEFAULT_DOT_SIZE_RADIUS_DP)) + ((((float) (i2 - this.mCurrentPage)) - this.mPageOffset) * ((float) width)));
            calcBounds.right = i3 + calcBounds.left;
            calcBounds.top = 0;
            calcBounds.bottom = i4 - i5;
            arrayList.add(calcBounds);
        }
        return arrayList;
    }

    private void clipViewOnTheLeft(Rect rect, float f, int i) {
        rect.left = (int) (((float) i) + this.mClipPadding);
        rect.right = (int) (this.mClipPadding + f);
    }

    private void clipViewOnTheRight(Rect rect, float f, int i) {
        rect.right = (int) (((float) i) - this.mClipPadding);
        rect.left = (int) (((float) rect.right) - f);
    }

    private CharSequence getTitle(int i) {
        CharSequence pageTitle = this.mViewPager.getAdapter().getPageTitle(i);
        return pageTitle == null ? "" : pageTitle;
    }

    public float getClipPadding() {
        return this.mClipPadding;
    }

    public int getFooterColor() {
        return this.mPaintFooterLine.getColor();
    }

    public float getFooterIndicatorHeight() {
        return this.mFooterIndicatorHeight;
    }

    public float getFooterIndicatorPadding() {
        return this.mFooterPadding;
    }

    public IndicatorStyle getFooterIndicatorStyle() {
        return this.mFooterIndicatorStyle;
    }

    public float getFooterLineHeight() {
        return this.mFooterLineHeight;
    }

    public LinePosition getLinePosition() {
        return this.mLinePosition;
    }

    public int getSelectedColor() {
        return this.mColorSelected;
    }

    public int getTextColor() {
        return this.mColorText;
    }

    public float getTextSize() {
        return this.mPaintText.getTextSize();
    }

    public float getTitlePadding() {
        return this.mTitlePadding;
    }

    public float getTopPadding() {
        return this.mTopPadding;
    }

    public Typeface getTypeface() {
        return this.mPaintText.getTypeface();
    }

    public boolean isSelectedBold() {
        return this.mBoldText;
    }

    public void notifyDataSetChanged() {
        invalidate();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mViewPager != null) {
            int count = this.mViewPager.getAdapter().getCount();
            if (count != 0) {
                if (this.mCurrentPage == -1 && this.mViewPager != null) {
                    this.mCurrentPage = this.mViewPager.getCurrentItem();
                }
                ArrayList calculateAllBounds = calculateAllBounds(this.mPaintText);
                int size = calculateAllBounds.size();
                if (this.mCurrentPage >= size) {
                    setCurrentItem(size - 1);
                    return;
                }
                float f;
                int i;
                int i2;
                Rect rect;
                float width = ((float) getWidth()) / GripView.DEFAULT_DOT_SIZE_RADIUS_DP;
                int left = getLeft();
                float f2 = ((float) left) + this.mClipPadding;
                int width2 = getWidth();
                int height = getHeight();
                int i3 = left + width2;
                float f3 = ((float) i3) - this.mClipPadding;
                int i4 = this.mCurrentPage;
                if (((double) this.mPageOffset) <= 0.5d) {
                    f = this.mPageOffset;
                    i = i4;
                } else {
                    f = 1.0f - this.mPageOffset;
                    i = i4 + 1;
                }
                Object obj = f <= SELECTION_FADE_PERCENTAGE ? 1 : null;
                Object obj2 = f <= BOLD_FADE_PERCENTAGE ? 1 : null;
                float f4 = (SELECTION_FADE_PERCENTAGE - f) / SELECTION_FADE_PERCENTAGE;
                Rect rect2 = (Rect) calculateAllBounds.get(this.mCurrentPage);
                f = (float) (rect2.right - rect2.left);
                if (((float) rect2.left) < f2) {
                    clipViewOnTheLeft(rect2, f, left);
                }
                if (((float) rect2.right) > f3) {
                    clipViewOnTheRight(rect2, f, i3);
                }
                if (this.mCurrentPage > 0) {
                    for (i2 = this.mCurrentPage - 1; i2 >= 0; i2--) {
                        rect2 = (Rect) calculateAllBounds.get(i2);
                        if (((float) rect2.left) < f2) {
                            int i5 = rect2.right - rect2.left;
                            clipViewOnTheLeft(rect2, (float) i5, left);
                            rect = (Rect) calculateAllBounds.get(i2 + 1);
                            if (((float) rect2.right) + this.mTitlePadding > ((float) rect.left)) {
                                rect2.left = (int) (((float) (rect.left - i5)) - this.mTitlePadding);
                                rect2.right = rect2.left + i5;
                            }
                        }
                    }
                }
                if (this.mCurrentPage < count - 1) {
                    for (i2 = this.mCurrentPage + 1; i2 < count; i2++) {
                        rect2 = (Rect) calculateAllBounds.get(i2);
                        if (((float) rect2.right) > f3) {
                            int i6 = rect2.right - rect2.left;
                            clipViewOnTheRight(rect2, (float) i6, i3);
                            rect = (Rect) calculateAllBounds.get(i2 - 1);
                            if (((float) rect2.left) - this.mTitlePadding < ((float) rect.right)) {
                                rect2.left = (int) (((float) rect.right) + this.mTitlePadding);
                                rect2.right = rect2.left + i6;
                            }
                        }
                    }
                }
                int i7 = this.mColorText >>> 24;
                int i8 = 0;
                while (i8 < count) {
                    Rect rect3 = (Rect) calculateAllBounds.get(i8);
                    if ((rect3.left > left && rect3.left < i3) || (rect3.right > left && rect3.right < i3)) {
                        Object obj3 = i8 == i ? 1 : null;
                        CharSequence title = getTitle(i8);
                        Paint paint = this.mPaintText;
                        boolean z = (obj3 == null || obj2 == null || !this.mBoldText) ? false : true;
                        paint.setFakeBoldText(z);
                        this.mPaintText.setColor(this.mColorText);
                        if (!(obj3 == null || obj == null)) {
                            this.mPaintText.setAlpha(i7 - ((int) (((float) i7) * f4)));
                        }
                        if (i8 < size - 1) {
                            rect2 = (Rect) calculateAllBounds.get(i8 + 1);
                            if (((float) rect3.right) + this.mTitlePadding > ((float) rect2.left)) {
                                i2 = rect3.right - rect3.left;
                                rect3.left = (int) (((float) (rect2.left - i2)) - this.mTitlePadding);
                                rect3.right = rect3.left + i2;
                            }
                        }
                        canvas.drawText(title, 0, title.length(), (float) rect3.left, this.mTopPadding + ((float) rect3.bottom), this.mPaintText);
                        if (!(obj3 == null || obj == null)) {
                            this.mPaintText.setColor(this.mColorSelected);
                            this.mPaintText.setAlpha((int) (((float) (this.mColorSelected >>> 24)) * f4));
                            canvas.drawText(title, 0, title.length(), (float) rect3.left, this.mTopPadding + ((float) rect3.bottom), this.mPaintText);
                        }
                    }
                    i8++;
                }
                f = this.mFooterLineHeight;
                float f5 = this.mFooterIndicatorHeight;
                if (this.mLinePosition == LinePosition.Top) {
                    i4 = 0;
                    f = -f;
                    f5 = -f5;
                } else {
                    i4 = height;
                }
                this.mPath.reset();
                this.mPath.moveTo(0.0f, ((float) i4) - (f / GripView.DEFAULT_DOT_SIZE_RADIUS_DP));
                this.mPath.lineTo((float) width2, ((float) i4) - (f / GripView.DEFAULT_DOT_SIZE_RADIUS_DP));
                this.mPath.close();
                canvas.drawPath(this.mPath, this.mPaintFooterLine);
                f = ((float) i4) - f;
                switch (this.mFooterIndicatorStyle) {
                    case Triangle:
                        this.mPath.reset();
                        this.mPath.moveTo(width, f - f5);
                        this.mPath.lineTo(width + f5, f);
                        this.mPath.lineTo(width - f5, f);
                        this.mPath.close();
                        canvas.drawPath(this.mPath, this.mPaintFooterIndicator);
                        return;
                    case Underline:
                        if (obj != null && i < size) {
                            rect2 = (Rect) calculateAllBounds.get(i);
                            f2 = ((float) rect2.right) + this.mFooterIndicatorUnderlinePadding;
                            float f6 = ((float) rect2.left) - this.mFooterIndicatorUnderlinePadding;
                            f5 = f - f5;
                            this.mPath.reset();
                            this.mPath.moveTo(f6, f);
                            this.mPath.lineTo(f2, f);
                            this.mPath.lineTo(f2, f5);
                            this.mPath.lineTo(f6, f5);
                            this.mPath.close();
                            this.mPaintFooterIndicator.setAlpha((int) (255.0f * f4));
                            canvas.drawPath(this.mPath, this.mPaintFooterIndicator);
                            this.mPaintFooterIndicator.setAlpha(CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        }
    }

    protected void onMeasure(int i, int i2) {
        float size;
        int size2 = MeasureSpec.getSize(i);
        if (MeasureSpec.getMode(i2) == 1073741824) {
            size = (float) MeasureSpec.getSize(i2);
        } else {
            this.mBounds.setEmpty();
            this.mBounds.bottom = (int) (this.mPaintText.descent() - this.mPaintText.ascent());
            size = ((((float) (this.mBounds.bottom - this.mBounds.top)) + this.mFooterLineHeight) + this.mFooterPadding) + this.mTopPadding;
            if (this.mFooterIndicatorStyle != IndicatorStyle.None) {
                size += this.mFooterIndicatorHeight;
            }
        }
        setMeasuredDimension(size2, (int) size);
    }

    public void onPageScrollStateChanged(int i) {
        this.mScrollState = i;
        if (this.mListener != null) {
            this.mListener.onPageScrollStateChanged(i);
        }
    }

    public void onPageScrolled(int i, float f, int i2) {
        this.mCurrentPage = i;
        this.mPageOffset = f;
        invalidate();
        if (this.mListener != null) {
            this.mListener.onPageScrolled(i, f, i2);
        }
    }

    public void onPageSelected(int i) {
        if (this.mScrollState == 0) {
            this.mCurrentPage = i;
            invalidate();
        }
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
                    float x = motionEvent.getX();
                    if (x < f - f2) {
                        if (this.mCurrentPage > 0) {
                            if (action == 3) {
                                return true;
                            }
                            this.mViewPager.setCurrentItem(this.mCurrentPage - 1);
                            return true;
                        }
                    } else if (x > f2 + f) {
                        if (this.mCurrentPage < count - 1) {
                            if (action == 3) {
                                return true;
                            }
                            this.mViewPager.setCurrentItem(this.mCurrentPage + 1);
                            return true;
                        }
                    } else if (!(this.mCenterItemClickListener == null || action == 3)) {
                        this.mCenterItemClickListener.onCenterItemClick(this.mCurrentPage);
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
                float x2 = MotionEventCompat.getX(motionEvent, MotionEventCompat.findPointerIndex(motionEvent, this.mActivePointerId));
                float f3 = x2 - this.mLastMotionX;
                if (!this.mIsDragging && Math.abs(f3) > ((float) this.mTouchSlop)) {
                    this.mIsDragging = true;
                }
                if (!this.mIsDragging) {
                    return true;
                }
                this.mLastMotionX = x2;
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

    public void setClipPadding(float f) {
        this.mClipPadding = f;
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

    public void setFooterColor(int i) {
        this.mPaintFooterLine.setColor(i);
        this.mPaintFooterIndicator.setColor(i);
        invalidate();
    }

    public void setFooterIndicatorHeight(float f) {
        this.mFooterIndicatorHeight = f;
        invalidate();
    }

    public void setFooterIndicatorPadding(float f) {
        this.mFooterPadding = f;
        invalidate();
    }

    public void setFooterIndicatorStyle(IndicatorStyle indicatorStyle) {
        this.mFooterIndicatorStyle = indicatorStyle;
        invalidate();
    }

    public void setFooterLineHeight(float f) {
        this.mFooterLineHeight = f;
        this.mPaintFooterLine.setStrokeWidth(this.mFooterLineHeight);
        invalidate();
    }

    public void setLinePosition(LinePosition linePosition) {
        this.mLinePosition = linePosition;
        invalidate();
    }

    public void setOnCenterItemClickListener(OnCenterItemClickListener onCenterItemClickListener) {
        this.mCenterItemClickListener = onCenterItemClickListener;
    }

    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.mListener = onPageChangeListener;
    }

    public void setSelectedBold(boolean z) {
        this.mBoldText = z;
        invalidate();
    }

    public void setSelectedColor(int i) {
        this.mColorSelected = i;
        invalidate();
    }

    public void setTextColor(int i) {
        this.mPaintText.setColor(i);
        this.mColorText = i;
        invalidate();
    }

    public void setTextSize(float f) {
        this.mPaintText.setTextSize(f);
        invalidate();
    }

    public void setTitlePadding(float f) {
        this.mTitlePadding = f;
        invalidate();
    }

    public void setTopPadding(float f) {
        this.mTopPadding = f;
        invalidate();
    }

    public void setTypeface(Typeface typeface) {
        this.mPaintText.setTypeface(typeface);
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
