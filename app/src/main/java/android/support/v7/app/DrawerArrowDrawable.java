package android.support.v7.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.appcompat.R;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

abstract class DrawerArrowDrawable extends Drawable {
    private static final float ARROW_HEAD_ANGLE = ((float) Math.toRadians(45.0d));
    private final float mBarGap;
    private final float mBarSize;
    private final float mBarThickness;
    private float mCenterOffset;
    private float mMaxCutForBarSize;
    private final float mMiddleArrowSize;
    private final Paint mPaint = new Paint();
    private final Path mPath = new Path();
    private float mProgress;
    private final int mSize;
    private final boolean mSpin;
    private final float mTopBottomArrowSize;
    private boolean mVerticalMirror = false;

    DrawerArrowDrawable(Context context) {
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(null, R.styleable.DrawerArrowToggle, R.attr.drawerArrowStyle, R.style.Base_Widget_AppCompat_DrawerArrowToggle);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setColor(obtainStyledAttributes.getColor(R.styleable.DrawerArrowToggle_color, 0));
        this.mSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.DrawerArrowToggle_drawableSize, 0);
        this.mBarSize = (float) Math.round(obtainStyledAttributes.getDimension(R.styleable.DrawerArrowToggle_barSize, 0.0f));
        this.mTopBottomArrowSize = (float) Math.round(obtainStyledAttributes.getDimension(R.styleable.DrawerArrowToggle_topBottomBarArrowSize, 0.0f));
        this.mBarThickness = obtainStyledAttributes.getDimension(R.styleable.DrawerArrowToggle_thickness, 0.0f);
        this.mBarGap = (float) Math.round(obtainStyledAttributes.getDimension(R.styleable.DrawerArrowToggle_gapBetweenBars, 0.0f));
        this.mSpin = obtainStyledAttributes.getBoolean(R.styleable.DrawerArrowToggle_spinBars, true);
        this.mMiddleArrowSize = obtainStyledAttributes.getDimension(R.styleable.DrawerArrowToggle_middleBarArrowSize, 0.0f);
        this.mCenterOffset = (float) ((((int) ((((float) this.mSize) - (this.mBarThickness * 3.0f)) - (this.mBarGap * 2.0f))) / 4) * 2);
        this.mCenterOffset = (float) (((double) this.mCenterOffset) + ((((double) this.mBarThickness) * 1.5d) + ((double) this.mBarGap)));
        obtainStyledAttributes.recycle();
        this.mPaint.setStyle(Style.STROKE);
        this.mPaint.setStrokeJoin(Join.MITER);
        this.mPaint.setStrokeCap(Cap.BUTT);
        this.mPaint.setStrokeWidth(this.mBarThickness);
        this.mMaxCutForBarSize = (float) (((double) (this.mBarThickness / 2.0f)) * Math.cos((double) ARROW_HEAD_ANGLE));
    }

    private static float lerp(float f, float f2, float f3) {
        return ((f2 - f) * f3) + f;
    }

    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        boolean isLayoutRtl = isLayoutRtl();
        float lerp = lerp(this.mBarSize, this.mTopBottomArrowSize, this.mProgress);
        float lerp2 = lerp(this.mBarSize, this.mMiddleArrowSize, this.mProgress);
        float round = (float) Math.round(lerp(0.0f, this.mMaxCutForBarSize, this.mProgress));
        float lerp3 = lerp(0.0f, ARROW_HEAD_ANGLE, this.mProgress);
        float lerp4 = lerp(isLayoutRtl ? 0.0f : -180.0f, isLayoutRtl ? BitmapDescriptorFactory.HUE_CYAN : 0.0f, this.mProgress);
        float round2 = (float) Math.round(((double) lerp) * Math.cos((double) lerp3));
        lerp = (float) Math.round(((double) lerp) * Math.sin((double) lerp3));
        this.mPath.rewind();
        lerp3 = lerp(this.mBarGap + this.mBarThickness, -this.mMaxCutForBarSize, this.mProgress);
        float f = (-lerp2) / 2.0f;
        this.mPath.moveTo(f + round, 0.0f);
        this.mPath.rLineTo(lerp2 - (round * 2.0f), 0.0f);
        this.mPath.moveTo(f, lerp3);
        this.mPath.rLineTo(round2, lerp);
        this.mPath.moveTo(f, -lerp3);
        this.mPath.rLineTo(round2, -lerp);
        this.mPath.close();
        canvas.save();
        canvas.translate((float) bounds.centerX(), this.mCenterOffset);
        if (this.mSpin) {
            canvas.rotate(((float) ((this.mVerticalMirror ^ isLayoutRtl) != 0 ? -1 : 1)) * lerp4);
        } else if (isLayoutRtl) {
            canvas.rotate(BitmapDescriptorFactory.HUE_CYAN);
        }
        canvas.drawPath(this.mPath, this.mPaint);
        canvas.restore();
    }

    public int getIntrinsicHeight() {
        return this.mSize;
    }

    public int getIntrinsicWidth() {
        return this.mSize;
    }

    public int getOpacity() {
        return -3;
    }

    public float getProgress() {
        return this.mProgress;
    }

    public boolean isAutoMirrored() {
        return true;
    }

    abstract boolean isLayoutRtl();

    public void setAlpha(int i) {
        this.mPaint.setAlpha(i);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }

    public void setProgress(float f) {
        this.mProgress = f;
        invalidateSelf();
    }

    protected void setVerticalMirror(boolean z) {
        this.mVerticalMirror = z;
    }
}
