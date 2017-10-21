package com.nhaarman.listviewanimations.appearance;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorInflater;

public abstract class ResourceAnimationAdapter extends AnimationAdapter {
    @NonNull
    private final Context mContext;

    protected ResourceAnimationAdapter(@NonNull BaseAdapter baseAdapter, @NonNull Context context) {
        super(baseAdapter);
        this.mContext = context;
    }

    protected abstract int getAnimationResourceId();

    @NonNull
    public Animator[] getAnimators(@NonNull ViewGroup viewGroup, @NonNull View view) {
        return new Animator[]{AnimatorInflater.loadAnimator(this.mContext, getAnimationResourceId())};
    }
}
