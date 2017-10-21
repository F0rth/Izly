package com.tekle.oss.android.animation;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.GripView;

public class FlipAnimation extends Animation {
    public static final float SCALE_DEFAULT = 0.75f;
    private Camera mCamera;
    private final float mCenterX;
    private final float mCenterY;
    private final float mFromDegrees;
    private final float mToDegrees;
    private float scale;
    private final ScaleUpDownEnum scaleType;

    public enum ScaleUpDownEnum {
        SCALE_UP,
        SCALE_DOWN,
        SCALE_CYCLE,
        SCALE_NONE;

        public final float getScale(float f, float f2) {
            switch (this) {
                case SCALE_UP:
                    return ((1.0f - f) * f2) + f;
                case SCALE_DOWN:
                    return 1.0f - ((1.0f - f) * f2);
                case SCALE_CYCLE:
                    return ((((double) f2) > 0.5d ? 1 : (((double) f2) == 0.5d ? 0 : -1)) > 0 ? 1 : null) != null ? (((1.0f - f) * (f2 - 0.5f)) * GripView.DEFAULT_DOT_SIZE_RADIUS_DP) + f : 1.0f - ((1.0f - f) * (f2 * GripView.DEFAULT_DOT_SIZE_RADIUS_DP));
                default:
                    return 1.0f;
            }
        }
    }

    public FlipAnimation(float f, float f2, float f3, float f4, float f5, ScaleUpDownEnum scaleUpDownEnum) {
        this.mFromDegrees = f;
        this.mToDegrees = f2;
        this.mCenterX = f3;
        this.mCenterY = f4;
        if (f5 <= 0.0f || f5 >= 1.0f) {
            f5 = SCALE_DEFAULT;
        }
        this.scale = f5;
        if (scaleUpDownEnum == null) {
            scaleUpDownEnum = ScaleUpDownEnum.SCALE_CYCLE;
        }
        this.scaleType = scaleUpDownEnum;
    }

    protected void applyTransformation(float f, Transformation transformation) {
        float f2 = this.mFromDegrees;
        float f3 = this.mToDegrees;
        float f4 = this.mCenterX;
        float f5 = this.mCenterY;
        Camera camera = this.mCamera;
        Matrix matrix = transformation.getMatrix();
        camera.save();
        camera.rotateY(f2 + ((f3 - f2) * f));
        camera.getMatrix(matrix);
        camera.restore();
        matrix.preTranslate(-f4, -f5);
        matrix.postTranslate(f4, f5);
        matrix.preScale(this.scaleType.getScale(this.scale, f), this.scaleType.getScale(this.scale, f), f4, f5);
    }

    public void initialize(int i, int i2, int i3, int i4) {
        super.initialize(i, i2, i3, i4);
        this.mCamera = new Camera();
    }
}
