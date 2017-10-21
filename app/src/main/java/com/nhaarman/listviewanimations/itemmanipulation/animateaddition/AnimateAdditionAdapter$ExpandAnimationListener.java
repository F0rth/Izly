package com.nhaarman.listviewanimations.itemmanipulation.animateaddition;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;

class AnimateAdditionAdapter$ExpandAnimationListener extends AnimatorListenerAdapter {
    private final int mPosition;
    final /* synthetic */ AnimateAdditionAdapter this$0;

    AnimateAdditionAdapter$ExpandAnimationListener(AnimateAdditionAdapter animateAdditionAdapter, int i) {
        this.this$0 = animateAdditionAdapter;
        this.mPosition = i;
    }

    public void onAnimationEnd(Animator animator) {
        AnimateAdditionAdapter.access$000(this.this$0).removeActiveIndex(this.mPosition);
    }
}
