package com.nhaarman.listviewanimations.appearance;

import android.annotation.SuppressLint;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.View;
import android.widget.GridView;
import com.nhaarman.listviewanimations.util.ListViewWrapper;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.view.ViewHelper;

public class ViewAnimator {
    private static final int DEFAULT_ANIMATION_DELAY_MILLIS = 100;
    private static final int DEFAULT_ANIMATION_DURATION_MILLIS = 300;
    private static final int INITIAL_DELAY_MILLIS = 150;
    private static final String SAVEDINSTANCESTATE_FIRSTANIMATEDPOSITION = "savedinstancestate_firstanimatedposition";
    private static final String SAVEDINSTANCESTATE_LASTANIMATEDPOSITION = "savedinstancestate_lastanimatedposition";
    private static final String SAVEDINSTANCESTATE_SHOULDANIMATE = "savedinstancestate_shouldanimate";
    private int mAnimationDelayMillis = 100;
    private int mAnimationDurationMillis = DEFAULT_ANIMATION_DURATION_MILLIS;
    private long mAnimationStartMillis;
    @NonNull
    private final SparseArray<Animator> mAnimators = new SparseArray();
    private int mFirstAnimatedPosition;
    private int mInitialDelayMillis = INITIAL_DELAY_MILLIS;
    private int mLastAnimatedPosition;
    @NonNull
    private final ListViewWrapper mListViewWrapper;
    private boolean mShouldAnimate = true;

    public ViewAnimator(@NonNull ListViewWrapper listViewWrapper) {
        this.mListViewWrapper = listViewWrapper;
        this.mAnimationStartMillis = -1;
        this.mFirstAnimatedPosition = -1;
        this.mLastAnimatedPosition = -1;
    }

    private void animateView(int i, @NonNull View view, @NonNull Animator[] animatorArr) {
        if (this.mAnimationStartMillis == -1) {
            this.mAnimationStartMillis = SystemClock.uptimeMillis();
        }
        ViewHelper.setAlpha(view, 0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorArr);
        animatorSet.setStartDelay((long) calculateAnimationDelay(i));
        animatorSet.setDuration((long) this.mAnimationDurationMillis);
        animatorSet.start();
        this.mAnimators.put(view.hashCode(), animatorSet);
    }

    @SuppressLint({"NewApi"})
    private int calculateAnimationDelay(int i) {
        if ((this.mListViewWrapper.getLastVisiblePosition() - this.mListViewWrapper.getFirstVisiblePosition()) + 1 < (i - 1) - this.mFirstAnimatedPosition) {
            int i2 = this.mAnimationDelayMillis;
            return (!(this.mListViewWrapper.getListView() instanceof GridView) || VERSION.SDK_INT < 11) ? i2 : ((i % ((GridView) this.mListViewWrapper.getListView()).getNumColumns()) * this.mAnimationDelayMillis) + i2;
        } else {
            return Math.max(0, (int) (((long) ((i - this.mFirstAnimatedPosition) * this.mAnimationDelayMillis)) + (((-SystemClock.uptimeMillis()) + this.mAnimationStartMillis) + ((long) this.mInitialDelayMillis))));
        }
    }

    public void animateViewIfNecessary(int i, @NonNull View view, @NonNull Animator[] animatorArr) {
        if (this.mShouldAnimate && i > this.mLastAnimatedPosition) {
            if (this.mFirstAnimatedPosition == -1) {
                this.mFirstAnimatedPosition = i;
            }
            animateView(i, view, animatorArr);
            this.mLastAnimatedPosition = i;
        }
    }

    void cancelExistingAnimation(@NonNull View view) {
        int hashCode = view.hashCode();
        Animator animator = (Animator) this.mAnimators.get(hashCode);
        if (animator != null) {
            animator.end();
            this.mAnimators.remove(hashCode);
        }
    }

    public void disableAnimations() {
        this.mShouldAnimate = false;
    }

    public void enableAnimations() {
        this.mShouldAnimate = true;
    }

    public void onRestoreInstanceState(@Nullable Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            this.mFirstAnimatedPosition = bundle.getInt(SAVEDINSTANCESTATE_FIRSTANIMATEDPOSITION);
            this.mLastAnimatedPosition = bundle.getInt(SAVEDINSTANCESTATE_LASTANIMATEDPOSITION);
            this.mShouldAnimate = bundle.getBoolean(SAVEDINSTANCESTATE_SHOULDANIMATE);
        }
    }

    @NonNull
    public Parcelable onSaveInstanceState() {
        Parcelable bundle = new Bundle();
        bundle.putInt(SAVEDINSTANCESTATE_FIRSTANIMATEDPOSITION, this.mFirstAnimatedPosition);
        bundle.putInt(SAVEDINSTANCESTATE_LASTANIMATEDPOSITION, this.mLastAnimatedPosition);
        bundle.putBoolean(SAVEDINSTANCESTATE_SHOULDANIMATE, this.mShouldAnimate);
        return bundle;
    }

    public void reset() {
        for (int i = 0; i < this.mAnimators.size(); i++) {
            ((Animator) this.mAnimators.get(this.mAnimators.keyAt(i))).cancel();
        }
        this.mAnimators.clear();
        this.mFirstAnimatedPosition = -1;
        this.mLastAnimatedPosition = -1;
        this.mAnimationStartMillis = -1;
        this.mShouldAnimate = true;
    }

    public void setAnimationDelayMillis(int i) {
        this.mAnimationDelayMillis = i;
    }

    public void setAnimationDurationMillis(int i) {
        this.mAnimationDurationMillis = i;
    }

    public void setInitialDelayMillis(int i) {
        this.mInitialDelayMillis = i;
    }

    void setLastAnimatedPosition(int i) {
        this.mLastAnimatedPosition = i;
    }

    public void setShouldAnimateFromPosition(int i) {
        enableAnimations();
        this.mFirstAnimatedPosition = i - 1;
        this.mLastAnimatedPosition = i - 1;
    }

    public void setShouldAnimateNotVisible() {
        enableAnimations();
        this.mFirstAnimatedPosition = this.mListViewWrapper.getLastVisiblePosition();
        this.mLastAnimatedPosition = this.mListViewWrapper.getLastVisiblePosition();
    }
}
