package com.nhaarman.listviewanimations.appearance.simple;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.nhaarman.listviewanimations.appearance.SingleAnimationAdapter;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;

public class SwingBottomInAnimationAdapter extends SingleAnimationAdapter {
    private static final String TRANSLATION_Y = "translationY";

    public SwingBottomInAnimationAdapter(@NonNull BaseAdapter baseAdapter) {
        super(baseAdapter);
    }

    @NonNull
    protected Animator getAnimator(@NonNull ViewGroup viewGroup, @NonNull View view) {
        return ObjectAnimator.ofFloat((Object) view, TRANSLATION_Y, (float) (viewGroup.getMeasuredHeight() >> 1), 0.0f);
    }
}
