package com.nhaarman.listviewanimations.appearance;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.nineoldandroids.animation.Animator;

public abstract class SingleAnimationAdapter extends AnimationAdapter {
    protected SingleAnimationAdapter(@NonNull BaseAdapter baseAdapter) {
        super(baseAdapter);
    }

    @NonNull
    public abstract Animator getAnimator(@NonNull ViewGroup viewGroup, @NonNull View view);

    @NonNull
    public Animator[] getAnimators(@NonNull ViewGroup viewGroup, @NonNull View view) {
        return new Animator[]{getAnimator(viewGroup, view)};
    }
}
