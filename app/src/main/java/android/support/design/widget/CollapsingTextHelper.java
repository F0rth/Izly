package android.support.design.widget;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.design.R;
import android.support.v4.view.ViewCompat;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.animation.Interpolator;

final class CollapsingTextHelper {
    private static final boolean DEBUG_DRAW = false;
    private static final Paint DEBUG_DRAW_PAINT = null;
    private static final boolean USE_SCALING_TEXTURE = (VERSION.SDK_INT < 18);
    private final Rect mCollapsedBounds;
    private int mCollapsedTextColor;
    private float mCollapsedTextSize;
    private int mCollapsedTextVerticalGravity = 16;
    private float mCollapsedTop;
    private float mCurrentLeft;
    private float mCurrentRight;
    private float mCurrentTextSize;
    private float mCurrentTop;
    private final Rect mExpandedBounds;
    private float mExpandedFraction;
    private int mExpandedTextColor;
    private float mExpandedTextSize;
    private int mExpandedTextVerticalGravity = 16;
    private Bitmap mExpandedTitleTexture;
    private float mExpandedTop;
    private Interpolator mPositionInterpolator;
    private float mScale;
    private CharSequence mText;
    private final TextPaint mTextPaint;
    private Interpolator mTextSizeInterpolator;
    private CharSequence mTextToDraw;
    private float mTextWidth;
    private float mTextureAscent;
    private float mTextureDescent;
    private Paint mTexturePaint;
    private boolean mUseTexture;
    private final View mView;

    public CollapsingTextHelper(View view) {
        this.mView = view;
        this.mTextPaint = new TextPaint();
        this.mTextPaint.setAntiAlias(true);
        this.mCollapsedBounds = new Rect();
        this.mExpandedBounds = new Rect();
    }

    private static int blendColors(int i, int i2, float f) {
        float f2 = 1.0f - f;
        return Color.argb((int) ((((float) Color.alpha(i)) * f2) + (((float) Color.alpha(i2)) * f)), (int) ((((float) Color.red(i)) * f2) + (((float) Color.red(i2)) * f)), (int) ((((float) Color.green(i)) * f2) + (((float) Color.green(i2)) * f)), (int) ((f2 * ((float) Color.blue(i))) + (((float) Color.blue(i2)) * f)));
    }

    private void calculateBaselines() {
        this.mTextPaint.setTextSize(this.mCollapsedTextSize);
        switch (this.mCollapsedTextVerticalGravity) {
            case 48:
                this.mCollapsedTop = ((float) this.mCollapsedBounds.top) - this.mTextPaint.ascent();
                break;
            case 80:
                this.mCollapsedTop = (float) this.mCollapsedBounds.bottom;
                break;
            default:
                this.mCollapsedTop = (((this.mTextPaint.descent() - this.mTextPaint.ascent()) / 2.0f) - this.mTextPaint.descent()) + ((float) this.mCollapsedBounds.centerY());
                break;
        }
        this.mTextPaint.setTextSize(this.mExpandedTextSize);
        switch (this.mExpandedTextVerticalGravity) {
            case 48:
                this.mExpandedTop = ((float) this.mExpandedBounds.top) - this.mTextPaint.ascent();
                break;
            case 80:
                this.mExpandedTop = (float) this.mExpandedBounds.bottom;
                break;
            default:
                this.mExpandedTop = (((this.mTextPaint.descent() - this.mTextPaint.ascent()) / 2.0f) - this.mTextPaint.descent()) + ((float) this.mExpandedBounds.centerY());
                break;
        }
        this.mTextureAscent = this.mTextPaint.ascent();
        this.mTextureDescent = this.mTextPaint.descent();
        clearTexture();
    }

    private void calculateOffsets() {
        float f = this.mExpandedFraction;
        this.mCurrentLeft = interpolate((float) this.mExpandedBounds.left, (float) this.mCollapsedBounds.left, f, this.mPositionInterpolator);
        this.mCurrentTop = interpolate(this.mExpandedTop, this.mCollapsedTop, f, this.mPositionInterpolator);
        this.mCurrentRight = interpolate((float) this.mExpandedBounds.right, (float) this.mCollapsedBounds.right, f, this.mPositionInterpolator);
        setInterpolatedTextSize(interpolate(this.mExpandedTextSize, this.mCollapsedTextSize, f, this.mTextSizeInterpolator));
        if (this.mCollapsedTextColor != this.mExpandedTextColor) {
            this.mTextPaint.setColor(blendColors(this.mExpandedTextColor, this.mCollapsedTextColor, f));
        } else {
            this.mTextPaint.setColor(this.mCollapsedTextColor);
        }
        ViewCompat.postInvalidateOnAnimation(this.mView);
    }

    private void clearTexture() {
        if (this.mExpandedTitleTexture != null) {
            this.mExpandedTitleTexture.recycle();
            this.mExpandedTitleTexture = null;
        }
    }

    private void ensureExpandedTexture() {
        if (this.mExpandedTitleTexture == null && !this.mExpandedBounds.isEmpty() && !TextUtils.isEmpty(this.mTextToDraw)) {
            this.mTextPaint.setTextSize(this.mExpandedTextSize);
            this.mTextPaint.setColor(this.mExpandedTextColor);
            int round = Math.round(this.mTextPaint.measureText(this.mTextToDraw, 0, this.mTextToDraw.length()));
            int round2 = Math.round(this.mTextPaint.descent() - this.mTextPaint.ascent());
            this.mTextWidth = (float) round;
            if (round > 0 || round2 > 0) {
                this.mExpandedTitleTexture = Bitmap.createBitmap(round, round2, Config.ARGB_8888);
                new Canvas(this.mExpandedTitleTexture).drawText(this.mTextToDraw, 0, this.mTextToDraw.length(), 0.0f, ((float) round2) - this.mTextPaint.descent(), this.mTextPaint);
                if (this.mTexturePaint == null) {
                    this.mTexturePaint = new Paint();
                    this.mTexturePaint.setAntiAlias(true);
                    this.mTexturePaint.setFilterBitmap(true);
                }
            }
        }
    }

    private static float interpolate(float f, float f2, float f3, Interpolator interpolator) {
        if (interpolator != null) {
            f3 = interpolator.getInterpolation(f3);
        }
        return AnimationUtils.lerp(f, f2, f3);
    }

    private static boolean isClose(float f, float f2) {
        return Math.abs(f - f2) < 0.001f;
    }

    private void recalculate() {
        if (ViewCompat.isLaidOut(this.mView)) {
            calculateBaselines();
            calculateOffsets();
        }
    }

    private void setInterpolatedTextSize(float f) {
        boolean z = true;
        if (this.mText != null) {
            float f2;
            float f3;
            float width;
            if (isClose(f, this.mCollapsedTextSize)) {
                width = (float) this.mCollapsedBounds.width();
                f2 = this.mCollapsedTextSize;
                this.mScale = 1.0f;
                f3 = f2;
                f2 = width;
            } else {
                width = (float) this.mExpandedBounds.width();
                f2 = this.mExpandedTextSize;
                if (isClose(f, this.mExpandedTextSize)) {
                    this.mScale = 1.0f;
                    f3 = f2;
                    f2 = width;
                } else {
                    this.mScale = f / this.mExpandedTextSize;
                    f3 = f2;
                    f2 = width;
                }
            }
            boolean z2;
            if (f2 > 0.0f) {
                z2 = this.mCurrentTextSize != f3;
                this.mCurrentTextSize = f3;
            } else {
                z2 = false;
            }
            if (this.mTextToDraw == null || r0) {
                this.mTextPaint.setTextSize(this.mCurrentTextSize);
                CharSequence ellipsize = TextUtils.ellipsize(this.mText, this.mTextPaint, f2, TruncateAt.END);
                if (this.mTextToDraw == null || !this.mTextToDraw.equals(ellipsize)) {
                    this.mTextToDraw = ellipsize;
                }
                this.mTextWidth = this.mTextPaint.measureText(this.mTextToDraw, 0, this.mTextToDraw.length());
            }
            if (!USE_SCALING_TEXTURE || this.mScale == 1.0f) {
                z = false;
            }
            this.mUseTexture = z;
            if (this.mUseTexture) {
                ensureExpandedTexture();
            }
            ViewCompat.postInvalidateOnAnimation(this.mView);
        }
    }

    public final void draw(Canvas canvas) {
        int i = 1;
        int save = canvas.save();
        if (this.mTextToDraw != null) {
            float f;
            int i2 = ViewCompat.getLayoutDirection(this.mView) == 1 ? 1 : 0;
            float f2 = i2 != 0 ? this.mCurrentRight : this.mCurrentLeft;
            float f3 = this.mCurrentTop;
            if (!this.mUseTexture || this.mExpandedTitleTexture == null) {
                i = 0;
            }
            this.mTextPaint.setTextSize(this.mCurrentTextSize);
            if (i != 0) {
                f = this.mTextureAscent * this.mScale;
            } else {
                this.mTextPaint.ascent();
                f = 0.0f;
                this.mTextPaint.descent();
            }
            if (i != 0) {
                f3 += f;
            }
            if (this.mScale != 1.0f) {
                canvas.scale(this.mScale, this.mScale, f2, f3);
            }
            float f4 = i2 != 0 ? f2 - this.mTextWidth : f2;
            if (i != 0) {
                canvas.drawBitmap(this.mExpandedTitleTexture, f4, f3, this.mTexturePaint);
            } else {
                canvas.drawText(this.mTextToDraw, 0, this.mTextToDraw.length(), f4, f3, this.mTextPaint);
            }
        }
        canvas.restoreToCount(save);
    }

    final int getCollapsedTextColor() {
        return this.mCollapsedTextColor;
    }

    final float getCollapsedTextSize() {
        return this.mCollapsedTextSize;
    }

    final int getExpandedTextColor() {
        return this.mExpandedTextColor;
    }

    final float getExpandedTextSize() {
        return this.mExpandedTextSize;
    }

    final float getExpansionFraction() {
        return this.mExpandedFraction;
    }

    final CharSequence getText() {
        return this.mText;
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        recalculate();
    }

    final void setCollapsedBounds(int i, int i2, int i3, int i4) {
        this.mCollapsedBounds.set(i, i2, i3, i4);
        recalculate();
    }

    final void setCollapsedTextAppearance(int i) {
        TypedArray obtainStyledAttributes = this.mView.getContext().obtainStyledAttributes(i, R.styleable.TextAppearance);
        if (obtainStyledAttributes.hasValue(R.styleable.TextAppearance_android_textColor)) {
            this.mCollapsedTextColor = obtainStyledAttributes.getColor(R.styleable.TextAppearance_android_textColor, 0);
        }
        if (obtainStyledAttributes.hasValue(R.styleable.TextAppearance_android_textSize)) {
            this.mCollapsedTextSize = (float) obtainStyledAttributes.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, 0);
        }
        obtainStyledAttributes.recycle();
        recalculate();
    }

    final void setCollapsedTextColor(int i) {
        if (this.mCollapsedTextColor != i) {
            this.mCollapsedTextColor = i;
            recalculate();
        }
    }

    final void setCollapsedTextSize(float f) {
        if (this.mCollapsedTextSize != f) {
            this.mCollapsedTextSize = f;
            recalculate();
        }
    }

    final void setCollapsedTextVerticalGravity(int i) {
        int i2 = i & 112;
        if (this.mCollapsedTextVerticalGravity != i2) {
            this.mCollapsedTextVerticalGravity = i2;
            recalculate();
        }
    }

    final void setExpandedBounds(int i, int i2, int i3, int i4) {
        this.mExpandedBounds.set(i, i2, i3, i4);
        recalculate();
    }

    final void setExpandedTextAppearance(int i) {
        TypedArray obtainStyledAttributes = this.mView.getContext().obtainStyledAttributes(i, R.styleable.TextAppearance);
        if (obtainStyledAttributes.hasValue(R.styleable.TextAppearance_android_textColor)) {
            this.mExpandedTextColor = obtainStyledAttributes.getColor(R.styleable.TextAppearance_android_textColor, 0);
        }
        if (obtainStyledAttributes.hasValue(R.styleable.TextAppearance_android_textSize)) {
            this.mExpandedTextSize = (float) obtainStyledAttributes.getDimensionPixelSize(R.styleable.TextAppearance_android_textSize, 0);
        }
        obtainStyledAttributes.recycle();
        recalculate();
    }

    final void setExpandedTextColor(int i) {
        if (this.mExpandedTextColor != i) {
            this.mExpandedTextColor = i;
            recalculate();
        }
    }

    final void setExpandedTextSize(float f) {
        if (this.mExpandedTextSize != f) {
            this.mExpandedTextSize = f;
            recalculate();
        }
    }

    final void setExpandedTextVerticalGravity(int i) {
        int i2 = i & 112;
        if (this.mExpandedTextVerticalGravity != i2) {
            this.mExpandedTextVerticalGravity = i2;
            recalculate();
        }
    }

    final void setExpansionFraction(float f) {
        float constrain = MathUtils.constrain(f, 0.0f, 1.0f);
        if (constrain != this.mExpandedFraction) {
            this.mExpandedFraction = constrain;
            calculateOffsets();
        }
    }

    final void setPositionInterpolator(Interpolator interpolator) {
        this.mPositionInterpolator = interpolator;
        recalculate();
    }

    final void setText(CharSequence charSequence) {
        if (charSequence == null || !charSequence.equals(this.mText)) {
            this.mText = charSequence;
            clearTexture();
            recalculate();
        }
    }

    final void setTextSizeInterpolator(Interpolator interpolator) {
        this.mTextSizeInterpolator = interpolator;
        recalculate();
    }
}
