package com.slidingmenu.lib;

import android.graphics.Canvas;
import android.view.animation.Interpolator;
import com.slidingmenu.lib.SlidingMenu.CanvasTransformer;

public class CanvasTransformerBuilder {
    private static Interpolator lin = new Interpolator() {
        public final float getInterpolation(float f) {
            return f;
        }
    };
    private CanvasTransformer mTrans;

    private void initTransformer() {
        if (this.mTrans == null) {
            this.mTrans = new CanvasTransformer() {
                public void transformCanvas(Canvas canvas, float f) {
                }
            };
        }
    }

    public CanvasTransformer concatTransformer(final CanvasTransformer canvasTransformer) {
        initTransformer();
        this.mTrans = new CanvasTransformer() {
            public void transformCanvas(Canvas canvas, float f) {
                CanvasTransformerBuilder.this.mTrans.transformCanvas(canvas, f);
                canvasTransformer.transformCanvas(canvas, f);
            }
        };
        return this.mTrans;
    }

    public CanvasTransformer rotate(int i, int i2, int i3, int i4) {
        return rotate(i, i2, i3, i4, lin);
    }

    public CanvasTransformer rotate(int i, int i2, int i3, int i4, Interpolator interpolator) {
        initTransformer();
        final Interpolator interpolator2 = interpolator;
        final int i5 = i;
        final int i6 = i2;
        final int i7 = i3;
        final int i8 = i4;
        this.mTrans = new CanvasTransformer() {
            public void transformCanvas(Canvas canvas, float f) {
                CanvasTransformerBuilder.this.mTrans.transformCanvas(canvas, f);
                canvas.rotate((interpolator2.getInterpolation(f) * ((float) (i5 - i6))) + ((float) i6), (float) i7, (float) i8);
            }
        };
        return this.mTrans;
    }

    public CanvasTransformer translate(int i, int i2, int i3, int i4) {
        return translate(i, i2, i3, i4, lin);
    }

    public CanvasTransformer translate(int i, int i2, int i3, int i4, Interpolator interpolator) {
        initTransformer();
        final Interpolator interpolator2 = interpolator;
        final int i5 = i;
        final int i6 = i2;
        final int i7 = i3;
        final int i8 = i4;
        this.mTrans = new CanvasTransformer() {
            public void transformCanvas(Canvas canvas, float f) {
                CanvasTransformerBuilder.this.mTrans.transformCanvas(canvas, f);
                float interpolation = interpolator2.getInterpolation(f);
                canvas.translate((((float) (i5 - i6)) * interpolation) + ((float) i6), (interpolation * ((float) (i7 - i8))) + ((float) i8));
            }
        };
        return this.mTrans;
    }

    public CanvasTransformer zoom(int i, int i2, int i3, int i4, int i5, int i6) {
        return zoom(i, i2, i3, i4, i5, i6, lin);
    }

    public CanvasTransformer zoom(int i, int i2, int i3, int i4, int i5, int i6, Interpolator interpolator) {
        initTransformer();
        final Interpolator interpolator2 = interpolator;
        final int i7 = i;
        final int i8 = i2;
        final int i9 = i3;
        final int i10 = i4;
        final int i11 = i5;
        final int i12 = i6;
        this.mTrans = new CanvasTransformer() {
            public void transformCanvas(Canvas canvas, float f) {
                CanvasTransformerBuilder.this.mTrans.transformCanvas(canvas, f);
                float interpolation = interpolator2.getInterpolation(f);
                canvas.scale((((float) (i7 - i8)) * interpolation) + ((float) i8), (interpolation * ((float) (i9 - i10))) + ((float) i10), (float) i11, (float) i12);
            }
        };
        return this.mTrans;
    }
}
