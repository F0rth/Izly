package com.nineoldandroids.animation;

import android.view.animation.Interpolator;
import java.util.ArrayList;

class IntKeyframeSet extends KeyframeSet {
    private int deltaValue;
    private boolean firstTime = true;
    private int firstValue;
    private int lastValue;

    public IntKeyframeSet(IntKeyframe... intKeyframeArr) {
        super(intKeyframeArr);
    }

    public IntKeyframeSet clone() {
        ArrayList arrayList = this.mKeyframes;
        int size = this.mKeyframes.size();
        IntKeyframe[] intKeyframeArr = new IntKeyframe[size];
        for (int i = 0; i < size; i++) {
            intKeyframeArr[i] = (IntKeyframe) ((Keyframe) arrayList.get(i)).clone();
        }
        return new IntKeyframeSet(intKeyframeArr);
    }

    public int getIntValue(float f) {
        if (this.mNumKeyframes == 2) {
            if (this.firstTime) {
                this.firstTime = false;
                this.firstValue = ((IntKeyframe) this.mKeyframes.get(0)).getIntValue();
                this.lastValue = ((IntKeyframe) this.mKeyframes.get(1)).getIntValue();
                this.deltaValue = this.lastValue - this.firstValue;
            }
            if (this.mInterpolator != null) {
                f = this.mInterpolator.getInterpolation(f);
            }
            return this.mEvaluator == null ? this.firstValue + ((int) (((float) this.deltaValue) * f)) : ((Number) this.mEvaluator.evaluate(f, Integer.valueOf(this.firstValue), Integer.valueOf(this.lastValue))).intValue();
        } else if (f <= 0.0f) {
            r0 = (IntKeyframe) this.mKeyframes.get(0);
            r1 = (IntKeyframe) this.mKeyframes.get(1);
            r2 = r0.getIntValue();
            r3 = r1.getIntValue();
            r0 = r0.getFraction();
            r4 = r1.getFraction();
            r1 = r1.getInterpolator();
            if (r1 != null) {
                f = r1.getInterpolation(f);
            }
            r0 = (f - r0) / (r4 - r0);
            return this.mEvaluator == null ? ((int) (r0 * ((float) (r3 - r2)))) + r2 : ((Number) this.mEvaluator.evaluate(r0, Integer.valueOf(r2), Integer.valueOf(r3))).intValue();
        } else if (f >= 1.0f) {
            r0 = (IntKeyframe) this.mKeyframes.get(this.mNumKeyframes - 2);
            r1 = (IntKeyframe) this.mKeyframes.get(this.mNumKeyframes - 1);
            r2 = r0.getIntValue();
            r3 = r1.getIntValue();
            r0 = r0.getFraction();
            r4 = r1.getFraction();
            r1 = r1.getInterpolator();
            if (r1 != null) {
                f = r1.getInterpolation(f);
            }
            r0 = (f - r0) / (r4 - r0);
            return this.mEvaluator == null ? ((int) (r0 * ((float) (r3 - r2)))) + r2 : ((Number) this.mEvaluator.evaluate(r0, Integer.valueOf(r2), Integer.valueOf(r3))).intValue();
        } else {
            r2 = 1;
            r1 = (IntKeyframe) this.mKeyframes.get(0);
            while (r2 < this.mNumKeyframes) {
                r0 = (IntKeyframe) this.mKeyframes.get(r2);
                if (f < r0.getFraction()) {
                    Interpolator interpolator = r0.getInterpolator();
                    if (interpolator != null) {
                        f = interpolator.getInterpolation(f);
                    }
                    float fraction = (f - r1.getFraction()) / (r0.getFraction() - r1.getFraction());
                    int intValue = r1.getIntValue();
                    int intValue2 = r0.getIntValue();
                    return this.mEvaluator == null ? ((int) (((float) (intValue2 - intValue)) * fraction)) + intValue : ((Number) this.mEvaluator.evaluate(fraction, Integer.valueOf(intValue), Integer.valueOf(intValue2))).intValue();
                } else {
                    r2++;
                    r1 = r0;
                }
            }
            return ((Number) ((Keyframe) this.mKeyframes.get(this.mNumKeyframes - 1)).getValue()).intValue();
        }
    }

    public Object getValue(float f) {
        return Integer.valueOf(getIntValue(f));
    }
}
