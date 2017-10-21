package android.support.design.widget;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.support.design.R;
import android.support.v7.graphics.drawable.DrawableWrapper;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

class ShadowDrawableWrapper extends DrawableWrapper {
    static final double COS_45 = Math.cos(Math.toRadians(45.0d));
    static final float SHADOW_BOTTOM_SCALE = 1.0f;
    static final float SHADOW_HORIZ_SCALE = 0.5f;
    static final float SHADOW_MULTIPLIER = 1.5f;
    static final float SHADOW_TOP_SCALE = 0.25f;
    private boolean mAddPaddingForCorners = true;
    final RectF mContentBounds;
    float mCornerRadius;
    final Paint mCornerShadowPaint;
    Path mCornerShadowPath;
    private boolean mDirty = true;
    final Paint mEdgeShadowPaint;
    float mMaxShadowSize;
    private boolean mPrintedShadowClipWarning = false;
    float mRawMaxShadowSize;
    float mRawShadowSize;
    private final int mShadowEndColor;
    private final int mShadowMiddleColor;
    float mShadowSize;
    private final int mShadowStartColor;

    public ShadowDrawableWrapper(Resources resources, Drawable drawable, float f, float f2, float f3) {
        super(drawable);
        this.mShadowStartColor = resources.getColor(R.color.shadow_start_color);
        this.mShadowMiddleColor = resources.getColor(R.color.shadow_mid_color);
        this.mShadowEndColor = resources.getColor(R.color.shadow_end_color);
        this.mCornerShadowPaint = new Paint(5);
        this.mCornerShadowPaint.setStyle(Style.FILL);
        this.mCornerRadius = (float) Math.round(f);
        this.mContentBounds = new RectF();
        this.mEdgeShadowPaint = new Paint(this.mCornerShadowPaint);
        this.mEdgeShadowPaint.setAntiAlias(false);
        setShadowSize(f2, f3);
    }

    private void buildComponents(Rect rect) {
        float f = this.mRawMaxShadowSize * SHADOW_MULTIPLIER;
        this.mContentBounds.set(((float) rect.left) + this.mRawMaxShadowSize, ((float) rect.top) + f, ((float) rect.right) - this.mRawMaxShadowSize, ((float) rect.bottom) - f);
        getWrappedDrawable().setBounds((int) this.mContentBounds.left, (int) this.mContentBounds.top, (int) this.mContentBounds.right, (int) this.mContentBounds.bottom);
        buildShadowCorners();
    }

    private void buildShadowCorners() {
        RectF rectF = new RectF(-this.mCornerRadius, -this.mCornerRadius, this.mCornerRadius, this.mCornerRadius);
        RectF rectF2 = new RectF(rectF);
        rectF2.inset(-this.mShadowSize, -this.mShadowSize);
        if (this.mCornerShadowPath == null) {
            this.mCornerShadowPath = new Path();
        } else {
            this.mCornerShadowPath.reset();
        }
        this.mCornerShadowPath.setFillType(FillType.EVEN_ODD);
        this.mCornerShadowPath.moveTo(-this.mCornerRadius, 0.0f);
        this.mCornerShadowPath.rLineTo(-this.mShadowSize, 0.0f);
        this.mCornerShadowPath.arcTo(rectF2, BitmapDescriptorFactory.HUE_CYAN, 90.0f, false);
        this.mCornerShadowPath.arcTo(rectF, BitmapDescriptorFactory.HUE_VIOLET, -90.0f, false);
        this.mCornerShadowPath.close();
        float f = -rectF2.top;
        if (f > 0.0f) {
            float f2 = (SHADOW_BOTTOM_SCALE - (this.mCornerRadius / f)) / 2.0f;
            Paint paint = this.mCornerShadowPaint;
            int i = this.mShadowStartColor;
            int i2 = this.mShadowMiddleColor;
            int i3 = this.mShadowEndColor;
            int[] iArr = new int[]{0, i, i2, i3};
            float[] fArr = new float[]{0.0f, r10, r10 + f2, SHADOW_BOTTOM_SCALE};
            paint.setShader(new RadialGradient(0.0f, 0.0f, f, iArr, fArr, TileMode.CLAMP));
        }
        Paint paint2 = this.mEdgeShadowPaint;
        float f3 = rectF.top;
        float f4 = rectF2.top;
        int i4 = this.mShadowStartColor;
        int i5 = this.mShadowMiddleColor;
        int i6 = this.mShadowEndColor;
        int[] iArr2 = new int[]{i4, i5, i6};
        paint2.setShader(new LinearGradient(0.0f, f3, 0.0f, f4, iArr2, new float[]{0.0f, SHADOW_HORIZ_SCALE, SHADOW_BOTTOM_SCALE}, TileMode.CLAMP));
        this.mEdgeShadowPaint.setAntiAlias(false);
    }

    public static float calculateHorizontalPadding(float f, float f2, boolean z) {
        return z ? (float) (((double) f) + ((1.0d - COS_45) * ((double) f2))) : f;
    }

    public static float calculateVerticalPadding(float f, float f2, boolean z) {
        return z ? (float) (((double) (SHADOW_MULTIPLIER * f)) + ((1.0d - COS_45) * ((double) f2))) : SHADOW_MULTIPLIER * f;
    }

    private void drawShadow(Canvas canvas) {
        float f = (-this.mCornerRadius) - this.mShadowSize;
        float f2 = this.mCornerRadius;
        Object obj = this.mContentBounds.width() - (2.0f * f2) > 0.0f ? 1 : null;
        Object obj2 = this.mContentBounds.height() - (2.0f * f2) > 0.0f ? 1 : null;
        float f3 = f2 / ((this.mRawShadowSize - (this.mRawShadowSize * SHADOW_HORIZ_SCALE)) + f2);
        float f4 = f2 / ((this.mRawShadowSize - (this.mRawShadowSize * SHADOW_TOP_SCALE)) + f2);
        float f5 = f2 / ((this.mRawShadowSize - (SHADOW_BOTTOM_SCALE * this.mRawShadowSize)) + f2);
        int save = canvas.save();
        canvas.translate(this.mContentBounds.left + f2, this.mContentBounds.top + f2);
        canvas.scale(f3, f4);
        canvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
        if (obj != null) {
            canvas.scale(SHADOW_BOTTOM_SCALE / f3, SHADOW_BOTTOM_SCALE);
            canvas.drawRect(0.0f, f, this.mContentBounds.width() - (2.0f * f2), -this.mCornerRadius, this.mEdgeShadowPaint);
        }
        canvas.restoreToCount(save);
        save = canvas.save();
        canvas.translate(this.mContentBounds.right - f2, this.mContentBounds.bottom - f2);
        canvas.scale(f3, f5);
        canvas.rotate(BitmapDescriptorFactory.HUE_CYAN);
        canvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
        if (obj != null) {
            canvas.scale(SHADOW_BOTTOM_SCALE / f3, SHADOW_BOTTOM_SCALE);
            canvas.drawRect(0.0f, f, this.mContentBounds.width() - (2.0f * f2), (-this.mCornerRadius) + this.mShadowSize, this.mEdgeShadowPaint);
        }
        canvas.restoreToCount(save);
        int save2 = canvas.save();
        canvas.translate(this.mContentBounds.left + f2, this.mContentBounds.bottom - f2);
        canvas.scale(f3, f5);
        canvas.rotate(BitmapDescriptorFactory.HUE_VIOLET);
        canvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
        if (obj2 != null) {
            canvas.scale(SHADOW_BOTTOM_SCALE / f5, SHADOW_BOTTOM_SCALE);
            canvas.drawRect(0.0f, f, this.mContentBounds.height() - (2.0f * f2), -this.mCornerRadius, this.mEdgeShadowPaint);
        }
        canvas.restoreToCount(save2);
        save2 = canvas.save();
        canvas.translate(this.mContentBounds.right - f2, this.mContentBounds.top + f2);
        canvas.scale(f3, f4);
        canvas.rotate(90.0f);
        canvas.drawPath(this.mCornerShadowPath, this.mCornerShadowPaint);
        if (obj2 != null) {
            canvas.scale(SHADOW_BOTTOM_SCALE / f4, SHADOW_BOTTOM_SCALE);
            canvas.drawRect(0.0f, f, this.mContentBounds.height() - (2.0f * f2), -this.mCornerRadius, this.mEdgeShadowPaint);
        }
        canvas.restoreToCount(save2);
    }

    private static int toEven(float f) {
        int round = Math.round(f);
        return round % 2 == 1 ? round - 1 : round;
    }

    public void draw(Canvas canvas) {
        if (this.mDirty) {
            buildComponents(getBounds());
            this.mDirty = false;
        }
        drawShadow(canvas);
        super.draw(canvas);
    }

    public float getCornerRadius() {
        return this.mCornerRadius;
    }

    public float getMaxShadowSize() {
        return this.mRawMaxShadowSize;
    }

    public float getMinHeight() {
        return (Math.max(this.mRawMaxShadowSize, this.mCornerRadius + ((this.mRawMaxShadowSize * SHADOW_MULTIPLIER) / 2.0f)) * 2.0f) + ((this.mRawMaxShadowSize * SHADOW_MULTIPLIER) * 2.0f);
    }

    public float getMinWidth() {
        return (Math.max(this.mRawMaxShadowSize, this.mCornerRadius + (this.mRawMaxShadowSize / 2.0f)) * 2.0f) + (this.mRawMaxShadowSize * 2.0f);
    }

    public int getOpacity() {
        return -3;
    }

    public boolean getPadding(Rect rect) {
        int ceil = (int) Math.ceil((double) calculateVerticalPadding(this.mRawMaxShadowSize, this.mCornerRadius, this.mAddPaddingForCorners));
        int ceil2 = (int) Math.ceil((double) calculateHorizontalPadding(this.mRawMaxShadowSize, this.mCornerRadius, this.mAddPaddingForCorners));
        rect.set(ceil2, ceil, ceil2, ceil);
        return true;
    }

    public float getShadowSize() {
        return this.mRawShadowSize;
    }

    protected void onBoundsChange(Rect rect) {
        this.mDirty = true;
    }

    public void setAddPaddingForCorners(boolean z) {
        this.mAddPaddingForCorners = z;
        invalidateSelf();
    }

    public void setAlpha(int i) {
        super.setAlpha(i);
        this.mCornerShadowPaint.setAlpha(i);
        this.mEdgeShadowPaint.setAlpha(i);
    }

    public void setCornerRadius(float f) {
        float round = (float) Math.round(f);
        if (this.mCornerRadius != round) {
            this.mCornerRadius = round;
            this.mDirty = true;
            invalidateSelf();
        }
    }

    public void setMaxShadowSize(float f) {
        setShadowSize(this.mRawShadowSize, f);
    }

    public void setShadowSize(float f) {
        setShadowSize(f, this.mRawMaxShadowSize);
    }

    void setShadowSize(float f, float f2) {
        if (f < 0.0f || f2 < 0.0f) {
            throw new IllegalArgumentException("invalid shadow size");
        }
        float toEven = (float) toEven(f);
        float toEven2 = (float) toEven(f2);
        if (toEven > toEven2) {
            if (!this.mPrintedShadowClipWarning) {
                this.mPrintedShadowClipWarning = true;
            }
            toEven = toEven2;
        }
        if (this.mRawShadowSize != toEven || this.mRawMaxShadowSize != toEven2) {
            this.mRawShadowSize = toEven;
            this.mRawMaxShadowSize = toEven2;
            this.mShadowSize = (float) Math.round(toEven * SHADOW_MULTIPLIER);
            this.mMaxShadowSize = toEven2;
            this.mDirty = true;
            invalidateSelf();
        }
    }
}
