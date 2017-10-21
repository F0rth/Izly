package com.nineoldandroids.animation;

import android.view.animation.Interpolator;
import java.util.ArrayList;

class FloatKeyframeSet extends KeyframeSet {
    private float deltaValue;
    private boolean firstTime = true;
    private float firstValue;
    private float lastValue;

    public FloatKeyframeSet(FloatKeyframe... floatKeyframeArr) {
        super(floatKeyframeArr);
    }

    public FloatKeyframeSet clone() {
        ArrayList arrayList = this.mKeyframes;
        int size = this.mKeyframes.size();
        FloatKeyframe[] floatKeyframeArr = new FloatKeyframe[size];
        for (int i = 0; i < size; i++) {
            floatKeyframeArr[i] = (FloatKeyframe) ((Keyframe) arrayList.get(i)).clone();
        }
        return new FloatKeyframeSet(floatKeyframeArr);
    }

    public float getFloatValue(float f) {
        if (this.mNumKeyframes == 2) {
            if (this.firstTime) {
                this.firstTime = false;
                this.firstValue = ((FloatKeyframe) this.mKeyframes.get(0)).getFloatValue();
                this.lastValue = ((FloatKeyframe) this.mKeyframes.get(1)).getFloatValue();
                this.deltaValue = this.lastValue - this.firstValue;
            }
            if (this.mInterpolator != null) {
                f = this.mInterpolator.getInterpolation(f);
            }
            return this.mEvaluator == null ? this.firstValue + (this.deltaValue * f) : ((Number) this.mEvaluator.evaluate(f, Float.valueOf(this.firstValue), Float.valueOf(this.lastValue))).floatValue();
        } else if (f <= 0.0f) {
            r0 = (FloatKeyframe) this.mKeyframes.get(0);
            r1 = (FloatKeyframe) this.mKeyframes.get(1);
            r2 = r0.getFloatValue();
            r3 = r1.getFloatValue();
            r0 = r0.getFraction();
            r4 = r1.getFraction();
            r1 = r1.getInterpolator();
            if (r1 != null) {
                f = r1.getInterpolation(f);
            }
            r0 = (f - r0) / (r4 - r0);
            return this.mEvaluator == null ? (r0 * (r3 - r2)) + r2 : ((Number) this.mEvaluator.evaluate(r0, Float.valueOf(r2), Float.valueOf(r3))).floatValue();
        } else if (f >= 1.0f) {
            r0 = (FloatKeyframe) this.mKeyframes.get(this.mNumKeyframes - 2);
            r1 = (FloatKeyframe) this.mKeyframes.get(this.mNumKeyframes - 1);
            r2 = r0.getFloatValue();
            r3 = r1.getFloatValue();
            r0 = r0.getFraction();
            r4 = r1.getFraction();
            r1 = r1.getInterpolator();
            if (r1 != null) {
                f = r1.getInterpolation(f);
            }
            r0 = (f - r0) / (r4 - r0);
            return this.mEvaluator == null ? (r0 * (r3 - r2)) + r2 : ((Number) this.mEvaluator.evaluate(r0, Float.valueOf(r2), Float.valueOf(r3))).floatValue();
        } else {
            int i = 1;
            r1 = (FloatKeyframe) this.mKeyframes.get(0);
            while (i < this.mNumKeyframes) {
                r0 = (FloatKeyframe) this.mKeyframes.get(i);
                if (f < r0.getFraction()) {
                    Interpolator interpolator = r0.getInterpolator();
                    if (interpolator != null) {
                        f = interpolator.getInterpolation(f);
                    }
                    r2 = (f - r1.getFraction()) / (r0.getFraction() - r1.getFraction());
                    float floatValue = r1.getFloatValue();
                    r0 = r0.getFloatValue();
                    return this.mEvaluator == null ? ((r0 - floatValue) * r2) + floatValue : ((Number) this.mEvaluator.evaluate(r2, Float.valueOf(floatValue), Float.valueOf(r0))).floatValue();
                } else {
                    i++;
                    r1 = r0;
                }
            }
            return ((Number) ((Keyframe) this.mKeyframes.get(this.mNumKeyframes - 1)).getValue()).floatValue();
        }
    }

    public Object getValue(float f) {
        return Float.valueOf(getFloatValue(f));
    }
}
