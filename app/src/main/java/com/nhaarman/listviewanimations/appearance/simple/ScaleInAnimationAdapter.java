package com.nhaarman.listviewanimations.appearance.simple;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.nhaarman.listviewanimations.appearance.AnimationAdapter;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;

public class ScaleInAnimationAdapter extends AnimationAdapter {
    private static final float DEFAULT_SCALE_FROM = 0.8f;
    private static final String SCALE_X = "scaleX";
    private static final String SCALE_Y = "scaleY";
    private final float mScaleFrom;

    public ScaleInAnimationAdapter(@NonNull BaseAdapter baseAdapter) {
        this(baseAdapter, DEFAULT_SCALE_FROM);
    }

    public ScaleInAnimationAdapter(@NonNull BaseAdapter baseAdapter, float f) {
        super(baseAdapter);
        this.mScaleFrom = f;
    }

    @NonNull
    public Animator[] getAnimators(@NonNull ViewGroup viewGroup, @NonNull View view) {
        Animator[] animatorArr = new ObjectAnimator[2];
        animatorArr[0] = ObjectAnimator.ofFloat((Object) view, SCALE_X, this.mScaleFrom, 1.0f);
        animatorArr[1] = ObjectAnimator.ofFloat((Object) view, SCALE_Y, this.mScaleFrom, 1.0f);
        return animatorArr;
    }
}
