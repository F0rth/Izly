package com.astuetz.viewpagertabs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.widget.TextView;

public class ViewPagerTab extends TextView {
    private static final String TAG = "ViewPagerTabs";
    public int currentPos;
    public int layoutPos;
    private int mBackgroundColorPressed;
    private int mCenterPercent;
    private int mIndex;
    private int mLineColorCenter;
    private int mLineColorNormal;
    private int mLineHeight;
    private Paint mLinePaint;
    private Paint mSelectedPaint;
    private int mTextColorCenter;
    private int mTextColorNormal;
    public int nextPos;
    public int prevPos;

    public ViewPagerTab(Context context) {
        this(context, null);
    }

    public ViewPagerTab(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ViewPagerTab(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mTextColorNormal = -6710887;
        this.mTextColorCenter = -7232456;
        this.mLineColorNormal = -12895429;
        this.mLineColorCenter = -7232456;
        this.mBackgroundColorPressed = -1723631233;
        this.mLineHeight = 4;
        this.mCenterPercent = 0;
        this.mIndex = -1;
        this.mLinePaint = new Paint();
        this.mSelectedPaint = new Paint();
        this.currentPos = 0;
        this.prevPos = 0;
        this.nextPos = 0;
        this.layoutPos = 0;
        setSingleLine(true);
        setEllipsize(TruncateAt.END);
        setPadding(0, 0, 0, 0);
        setFocusable(true);
    }

    private int ave(int i, int i2, float f) {
        return Math.round(((float) (i2 - i)) * f) + i;
    }

    private int interpColor(int[] iArr, float f) {
        if (f <= 0.0f) {
            return iArr[0];
        }
        if (f >= 1.0f) {
            return iArr[iArr.length - 1];
        }
        float length = ((float) (iArr.length - 1)) * f;
        int i = (int) length;
        length -= (float) i;
        int i2 = iArr[i];
        i = iArr[i + 1];
        return Color.argb(ave(Color.alpha(i2), Color.alpha(i), length), ave(Color.red(i2), Color.red(i), length), ave(Color.green(i2), Color.green(i), length), ave(Color.blue(i2), Color.blue(i), length));
    }

    protected void drawableStateChanged() {
        invalidate();
        super.drawableStateChanged();
    }

    public int getIndex() {
        return this.mIndex;
    }

    protected void onDraw(Canvas canvas) {
        synchronized (this) {
            Paint paint = this.mLinePaint;
            Paint paint2 = this.mSelectedPaint;
            paint2.setColor(this.mBackgroundColorPressed);
            int i = this.mTextColorNormal;
            int i2 = this.mTextColorCenter;
            int[] iArr = new int[]{i, i2};
            setTextColor(interpColor(iArr, ((float) this.mCenterPercent) / 100.0f));
            i = this.mLineColorNormal;
            i2 = this.mLineColorCenter;
            iArr = new int[]{i, i2};
            paint.setColor(interpColor(iArr, ((float) this.mCenterPercent) / 100.0f));
            canvas.drawRect(0.0f, (float) (getHeight() - this.mLineHeight), (float) getWidth(), (float) getHeight(), paint);
            if (isFocused() || isPressed()) {
                canvas.drawRect(0.0f, 0.0f, (float) getWidth(), (float) getHeight(), paint2);
            }
            super.onDraw(canvas);
        }
    }

    public void setBackgroundColorPressed(int i) {
        this.mBackgroundColorPressed = i;
    }

    public void setCenterPercent(int i) {
        int i2 = 100;
        int i3 = i < 0 ? 0 : i;
        if (i3 <= 100) {
            i2 = i3;
        }
        this.mCenterPercent = i2;
    }

    public void setIndex(int i) {
        this.mIndex = i;
    }

    public void setLineColors(int i, int i2) {
        this.mLineColorNormal = i;
        this.mLineColorCenter = i2;
    }

    public void setLineHeight(int i) {
        this.mLineHeight = i;
    }

    public void setTextColors(int i, int i2) {
        this.mTextColorNormal = i;
        this.mTextColorCenter = i2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getText()).append(": ");
        stringBuilder.append(this.prevPos);
        stringBuilder.append(" <- ").append(this.currentPos);
        stringBuilder.append(" -> ").append(this.nextPos);
        stringBuilder.append(" (").append(this.layoutPos).append(")");
        return stringBuilder.toString();
    }
}
