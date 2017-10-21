package com.nhaarman.listviewanimations.itemmanipulation.animateaddition;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;

class AnimateAdditionAdapter$HeightUpdater implements AnimatorUpdateListener {
    private final View mView;

    AnimateAdditionAdapter$HeightUpdater(View view) {
        this.mView = view;
    }

    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        LayoutParams layoutParams = this.mView.getLayoutParams();
        layoutParams.height = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        this.mView.setLayoutParams(layoutParams);
    }
}
