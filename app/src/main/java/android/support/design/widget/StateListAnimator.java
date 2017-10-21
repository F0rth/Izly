package android.support.design.widget;

import android.util.StateSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

final class StateListAnimator {
    private AnimationListener mAnimationListener = new AnimationListener() {
        public void onAnimationEnd(Animation animation) {
            if (StateListAnimator.this.mRunningAnimation == animation) {
                StateListAnimator.this.mRunningAnimation = null;
            }
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationStart(Animation animation) {
        }
    };
    private Tuple mLastMatch = null;
    private Animation mRunningAnimation = null;
    private final ArrayList<Tuple> mTuples = new ArrayList();
    private WeakReference<View> mViewRef;

    static class Tuple {
        final Animation mAnimation;
        final int[] mSpecs;

        private Tuple(int[] iArr, Animation animation) {
            this.mSpecs = iArr;
            this.mAnimation = animation;
        }

        Animation getAnimation() {
            return this.mAnimation;
        }

        int[] getSpecs() {
            return this.mSpecs;
        }
    }

    StateListAnimator() {
    }

    private void cancel() {
        if (this.mRunningAnimation != null) {
            View target = getTarget();
            if (target != null && target.getAnimation() == this.mRunningAnimation) {
                target.clearAnimation();
            }
            this.mRunningAnimation = null;
        }
    }

    private void clearTarget() {
        View target = getTarget();
        int size = this.mTuples.size();
        for (int i = 0; i < size; i++) {
            if (target.getAnimation() == ((Tuple) this.mTuples.get(i)).mAnimation) {
                target.clearAnimation();
            }
        }
        this.mViewRef = null;
        this.mLastMatch = null;
        this.mRunningAnimation = null;
    }

    private void start(Tuple tuple) {
        this.mRunningAnimation = tuple.mAnimation;
        View target = getTarget();
        if (target != null) {
            target.startAnimation(this.mRunningAnimation);
        }
    }

    public final void addState(int[] iArr, Animation animation) {
        Tuple tuple = new Tuple(iArr, animation);
        animation.setAnimationListener(this.mAnimationListener);
        this.mTuples.add(tuple);
    }

    final Animation getRunningAnimation() {
        return this.mRunningAnimation;
    }

    final View getTarget() {
        return this.mViewRef == null ? null : (View) this.mViewRef.get();
    }

    final ArrayList<Tuple> getTuples() {
        return this.mTuples;
    }

    public final void jumpToCurrentState() {
        if (this.mRunningAnimation != null) {
            View target = getTarget();
            if (target != null && target.getAnimation() == this.mRunningAnimation) {
                target.clearAnimation();
            }
        }
    }

    final void setState(int[] iArr) {
        Tuple tuple;
        int size = this.mTuples.size();
        for (int i = 0; i < size; i++) {
            tuple = (Tuple) this.mTuples.get(i);
            if (StateSet.stateSetMatches(tuple.mSpecs, iArr)) {
                break;
            }
        }
        tuple = null;
        if (tuple != this.mLastMatch) {
            if (this.mLastMatch != null) {
                cancel();
            }
            this.mLastMatch = tuple;
            if (tuple != null) {
                start(tuple);
            }
        }
    }

    final void setTarget(View view) {
        View target = getTarget();
        if (target != view) {
            if (target != null) {
                clearTarget();
            }
            if (view != null) {
                this.mViewRef = new WeakReference(view);
            }
        }
    }
}
