package com.nineoldandroids.view;

import android.view.View;
import android.view.animation.Interpolator;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

class ViewPropertyAnimatorHC extends ViewPropertyAnimator {
    private static final int ALPHA = 512;
    private static final int NONE = 0;
    private static final int ROTATION = 16;
    private static final int ROTATION_X = 32;
    private static final int ROTATION_Y = 64;
    private static final int SCALE_X = 4;
    private static final int SCALE_Y = 8;
    private static final int TRANSFORM_MASK = 511;
    private static final int TRANSLATION_X = 1;
    private static final int TRANSLATION_Y = 2;
    private static final int X = 128;
    private static final int Y = 256;
    private Runnable mAnimationStarter = new Runnable() {
        public void run() {
            ViewPropertyAnimatorHC.this.startAnimation();
        }
    };
    private AnimatorEventListener mAnimatorEventListener = new AnimatorEventListener();
    private HashMap<Animator, PropertyBundle> mAnimatorMap = new HashMap();
    private long mDuration;
    private boolean mDurationSet = false;
    private Interpolator mInterpolator;
    private boolean mInterpolatorSet = false;
    private AnimatorListener mListener = null;
    ArrayList<NameValuesHolder> mPendingAnimations = new ArrayList();
    private long mStartDelay = 0;
    private boolean mStartDelaySet = false;
    private final WeakReference<View> mView;

    class AnimatorEventListener implements AnimatorListener, AnimatorUpdateListener {
        private AnimatorEventListener() {
        }

        public void onAnimationCancel(Animator animator) {
            if (ViewPropertyAnimatorHC.this.mListener != null) {
                ViewPropertyAnimatorHC.this.mListener.onAnimationCancel(animator);
            }
        }

        public void onAnimationEnd(Animator animator) {
            if (ViewPropertyAnimatorHC.this.mListener != null) {
                ViewPropertyAnimatorHC.this.mListener.onAnimationEnd(animator);
            }
            ViewPropertyAnimatorHC.this.mAnimatorMap.remove(animator);
            if (ViewPropertyAnimatorHC.this.mAnimatorMap.isEmpty()) {
                ViewPropertyAnimatorHC.this.mListener = null;
            }
        }

        public void onAnimationRepeat(Animator animator) {
            if (ViewPropertyAnimatorHC.this.mListener != null) {
                ViewPropertyAnimatorHC.this.mListener.onAnimationRepeat(animator);
            }
        }

        public void onAnimationStart(Animator animator) {
            if (ViewPropertyAnimatorHC.this.mListener != null) {
                ViewPropertyAnimatorHC.this.mListener.onAnimationStart(animator);
            }
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            float animatedFraction = valueAnimator.getAnimatedFraction();
            PropertyBundle propertyBundle = (PropertyBundle) ViewPropertyAnimatorHC.this.mAnimatorMap.get(valueAnimator);
            if ((propertyBundle.mPropertyMask & ViewPropertyAnimatorHC.TRANSFORM_MASK) != 0) {
                View view = (View) ViewPropertyAnimatorHC.this.mView.get();
                if (view != null) {
                    view.invalidate();
                }
            }
            ArrayList arrayList = propertyBundle.mNameValuesHolder;
            if (arrayList != null) {
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    NameValuesHolder nameValuesHolder = (NameValuesHolder) arrayList.get(i);
                    ViewPropertyAnimatorHC.this.setValue(nameValuesHolder.mNameConstant, nameValuesHolder.mFromValue + (nameValuesHolder.mDeltaValue * animatedFraction));
                }
            }
            View view2 = (View) ViewPropertyAnimatorHC.this.mView.get();
            if (view2 != null) {
                view2.invalidate();
            }
        }
    }

    static class NameValuesHolder {
        float mDeltaValue;
        float mFromValue;
        int mNameConstant;

        NameValuesHolder(int i, float f, float f2) {
            this.mNameConstant = i;
            this.mFromValue = f;
            this.mDeltaValue = f2;
        }
    }

    static class PropertyBundle {
        ArrayList<NameValuesHolder> mNameValuesHolder;
        int mPropertyMask;

        PropertyBundle(int i, ArrayList<NameValuesHolder> arrayList) {
            this.mPropertyMask = i;
            this.mNameValuesHolder = arrayList;
        }

        boolean cancel(int i) {
            if (!((this.mPropertyMask & i) == 0 || this.mNameValuesHolder == null)) {
                int size = this.mNameValuesHolder.size();
                for (int i2 = 0; i2 < size; i2++) {
                    if (((NameValuesHolder) this.mNameValuesHolder.get(i2)).mNameConstant == i) {
                        this.mNameValuesHolder.remove(i2);
                        this.mPropertyMask &= i ^ -1;
                        return true;
                    }
                }
            }
            return false;
        }
    }

    ViewPropertyAnimatorHC(View view) {
        this.mView = new WeakReference(view);
    }

    private void animateProperty(int i, float f) {
        float value = getValue(i);
        animatePropertyBy(i, value, f - value);
    }

    private void animatePropertyBy(int i, float f) {
        animatePropertyBy(i, getValue(i), f);
    }

    private void animatePropertyBy(int i, float f, float f2) {
        if (this.mAnimatorMap.size() > 0) {
            for (Animator animator : this.mAnimatorMap.keySet()) {
                PropertyBundle propertyBundle = (PropertyBundle) this.mAnimatorMap.get(animator);
                if (propertyBundle.cancel(i) && propertyBundle.mPropertyMask == 0) {
                    break;
                }
            }
            Animator animator2 = null;
            if (animator2 != null) {
                animator2.cancel();
            }
        }
        this.mPendingAnimations.add(new NameValuesHolder(i, f, f2));
        View view = (View) this.mView.get();
        if (view != null) {
            view.removeCallbacks(this.mAnimationStarter);
            view.post(this.mAnimationStarter);
        }
    }

    private float getValue(int i) {
        View view = (View) this.mView.get();
        if (view != null) {
            switch (i) {
                case 1:
                    return view.getTranslationX();
                case 2:
                    return view.getTranslationY();
                case 4:
                    return view.getScaleX();
                case 8:
                    return view.getScaleY();
                case 16:
                    return view.getRotation();
                case 32:
                    return view.getRotationX();
                case 64:
                    return view.getRotationY();
                case 128:
                    return view.getX();
                case 256:
                    return view.getY();
                case 512:
                    return view.getAlpha();
            }
        }
        return 0.0f;
    }

    private void setValue(int i, float f) {
        View view = (View) this.mView.get();
        if (view != null) {
            switch (i) {
                case 1:
                    view.setTranslationX(f);
                    return;
                case 2:
                    view.setTranslationY(f);
                    return;
                case 4:
                    view.setScaleX(f);
                    return;
                case 8:
                    view.setScaleY(f);
                    return;
                case 16:
                    view.setRotation(f);
                    return;
                case 32:
                    view.setRotationX(f);
                    return;
                case 64:
                    view.setRotationY(f);
                    return;
                case 128:
                    view.setX(f);
                    return;
                case 256:
                    view.setY(f);
                    return;
                case 512:
                    view.setAlpha(f);
                    return;
                default:
                    return;
            }
        }
    }

    private void startAnimation() {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f);
        ArrayList arrayList = (ArrayList) this.mPendingAnimations.clone();
        this.mPendingAnimations.clear();
        int i = 0;
        int i2 = 0;
        while (i2 < arrayList.size()) {
            int i3 = ((NameValuesHolder) arrayList.get(i2)).mNameConstant | i;
            i2++;
            i = i3;
        }
        this.mAnimatorMap.put(ofFloat, new PropertyBundle(i, arrayList));
        ofFloat.addUpdateListener(this.mAnimatorEventListener);
        ofFloat.addListener(this.mAnimatorEventListener);
        if (this.mStartDelaySet) {
            ofFloat.setStartDelay(this.mStartDelay);
        }
        if (this.mDurationSet) {
            ofFloat.setDuration(this.mDuration);
        }
        if (this.mInterpolatorSet) {
            ofFloat.setInterpolator(this.mInterpolator);
        }
        ofFloat.start();
    }

    public ViewPropertyAnimator alpha(float f) {
        animateProperty(512, f);
        return this;
    }

    public ViewPropertyAnimator alphaBy(float f) {
        animatePropertyBy(512, f);
        return this;
    }

    public void cancel() {
        if (this.mAnimatorMap.size() > 0) {
            for (Animator cancel : ((HashMap) this.mAnimatorMap.clone()).keySet()) {
                cancel.cancel();
            }
        }
        this.mPendingAnimations.clear();
        View view = (View) this.mView.get();
        if (view != null) {
            view.removeCallbacks(this.mAnimationStarter);
        }
    }

    public long getDuration() {
        return this.mDurationSet ? this.mDuration : new ValueAnimator().getDuration();
    }

    public long getStartDelay() {
        return this.mStartDelaySet ? this.mStartDelay : 0;
    }

    public ViewPropertyAnimator rotation(float f) {
        animateProperty(16, f);
        return this;
    }

    public ViewPropertyAnimator rotationBy(float f) {
        animatePropertyBy(16, f);
        return this;
    }

    public ViewPropertyAnimator rotationX(float f) {
        animateProperty(32, f);
        return this;
    }

    public ViewPropertyAnimator rotationXBy(float f) {
        animatePropertyBy(32, f);
        return this;
    }

    public ViewPropertyAnimator rotationY(float f) {
        animateProperty(64, f);
        return this;
    }

    public ViewPropertyAnimator rotationYBy(float f) {
        animatePropertyBy(64, f);
        return this;
    }

    public ViewPropertyAnimator scaleX(float f) {
        animateProperty(4, f);
        return this;
    }

    public ViewPropertyAnimator scaleXBy(float f) {
        animatePropertyBy(4, f);
        return this;
    }

    public ViewPropertyAnimator scaleY(float f) {
        animateProperty(8, f);
        return this;
    }

    public ViewPropertyAnimator scaleYBy(float f) {
        animatePropertyBy(8, f);
        return this;
    }

    public ViewPropertyAnimator setDuration(long j) {
        if (j < 0) {
            throw new IllegalArgumentException("Animators cannot have negative duration: " + j);
        }
        this.mDurationSet = true;
        this.mDuration = j;
        return this;
    }

    public ViewPropertyAnimator setInterpolator(Interpolator interpolator) {
        this.mInterpolatorSet = true;
        this.mInterpolator = interpolator;
        return this;
    }

    public ViewPropertyAnimator setListener(AnimatorListener animatorListener) {
        this.mListener = animatorListener;
        return this;
    }

    public ViewPropertyAnimator setStartDelay(long j) {
        if (j < 0) {
            throw new IllegalArgumentException("Animators cannot have negative duration: " + j);
        }
        this.mStartDelaySet = true;
        this.mStartDelay = j;
        return this;
    }

    public void start() {
        startAnimation();
    }

    public ViewPropertyAnimator translationX(float f) {
        animateProperty(1, f);
        return this;
    }

    public ViewPropertyAnimator translationXBy(float f) {
        animatePropertyBy(1, f);
        return this;
    }

    public ViewPropertyAnimator translationY(float f) {
        animateProperty(2, f);
        return this;
    }

    public ViewPropertyAnimator translationYBy(float f) {
        animatePropertyBy(2, f);
        return this;
    }

    public ViewPropertyAnimator x(float f) {
        animateProperty(128, f);
        return this;
    }

    public ViewPropertyAnimator xBy(float f) {
        animatePropertyBy(128, f);
        return this;
    }

    public ViewPropertyAnimator y(float f) {
        animateProperty(256, f);
        return this;
    }

    public ViewPropertyAnimator yBy(float f) {
        animatePropertyBy(256, f);
        return this;
    }
}
