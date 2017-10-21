package com.nhaarman.listviewanimations.appearance;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.nhaarman.listviewanimations.BaseAdapterDecorator;
import com.nhaarman.listviewanimations.util.AnimatorUtil;
import com.nhaarman.listviewanimations.util.ListViewWrapper;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;

public abstract class AnimationAdapter extends BaseAdapterDecorator {
    static final /* synthetic */ boolean $assertionsDisabled = (!AnimationAdapter.class.desiredAssertionStatus());
    private static final String ALPHA = "alpha";
    private static final String SAVEDINSTANCESTATE_VIEWANIMATOR = "savedinstancestate_viewanimator";
    private int mGridViewMeasuringPosition = -1;
    private boolean mGridViewPossiblyMeasuring = true;
    private boolean mIsRootAdapter = true;
    @Nullable
    private ViewAnimator mViewAnimator;

    protected AnimationAdapter(@NonNull BaseAdapter baseAdapter) {
        super(baseAdapter);
        if (baseAdapter instanceof AnimationAdapter) {
            ((AnimationAdapter) baseAdapter).setIsWrapped();
        }
    }

    private void animateViewIfNecessary(int i, @NonNull View view, @NonNull ViewGroup viewGroup) {
        if ($assertionsDisabled || this.mViewAnimator != null) {
            boolean z = this.mGridViewPossiblyMeasuring && (this.mGridViewMeasuringPosition == -1 || this.mGridViewMeasuringPosition == i);
            this.mGridViewPossiblyMeasuring = z;
            if (this.mGridViewPossiblyMeasuring) {
                this.mGridViewMeasuringPosition = i;
                this.mViewAnimator.setLastAnimatedPosition(-1);
            }
            this.mViewAnimator.animateViewIfNecessary(i, view, AnimatorUtil.concatAnimators(getDecoratedBaseAdapter() instanceof AnimationAdapter ? ((AnimationAdapter) getDecoratedBaseAdapter()).getAnimators(viewGroup, view) : new Animator[0], getAnimators(viewGroup, view), ObjectAnimator.ofFloat((Object) view, ALPHA, 0.0f, 1.0f)));
            return;
        }
        throw new AssertionError();
    }

    private void setIsWrapped() {
        this.mIsRootAdapter = false;
    }

    @NonNull
    public abstract Animator[] getAnimators(@NonNull ViewGroup viewGroup, @NonNull View view);

    @NonNull
    public final View getView(int i, @Nullable View view, @NonNull ViewGroup viewGroup) {
        if (this.mIsRootAdapter) {
            if (getListViewWrapper() == null) {
                throw new IllegalStateException("Call setAbsListView() on this AnimationAdapter first!");
            } else if (!$assertionsDisabled && this.mViewAnimator == null) {
                throw new AssertionError();
            } else if (view != null) {
                this.mViewAnimator.cancelExistingAnimation(view);
            }
        }
        View view2 = super.getView(i, view, viewGroup);
        if (this.mIsRootAdapter) {
            animateViewIfNecessary(i, view2, viewGroup);
        }
        return view2;
    }

    @Nullable
    public ViewAnimator getViewAnimator() {
        return this.mViewAnimator;
    }

    public void onRestoreInstanceState(@Nullable Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            Bundle bundle = (Bundle) parcelable;
            if (this.mViewAnimator != null) {
                this.mViewAnimator.onRestoreInstanceState(bundle.getParcelable(SAVEDINSTANCESTATE_VIEWANIMATOR));
            }
        }
    }

    @NonNull
    public Parcelable onSaveInstanceState() {
        Parcelable bundle = new Bundle();
        if (this.mViewAnimator != null) {
            bundle.putParcelable(SAVEDINSTANCESTATE_VIEWANIMATOR, this.mViewAnimator.onSaveInstanceState());
        }
        return bundle;
    }

    public void reset() {
        if (getListViewWrapper() == null) {
            throw new IllegalStateException("Call setAbsListView() on this AnimationAdapter first!");
        } else if ($assertionsDisabled || this.mViewAnimator != null) {
            this.mViewAnimator.reset();
            this.mGridViewPossiblyMeasuring = true;
            this.mGridViewMeasuringPosition = -1;
            if (getDecoratedBaseAdapter() instanceof AnimationAdapter) {
                ((AnimationAdapter) getDecoratedBaseAdapter()).reset();
            }
        } else {
            throw new AssertionError();
        }
    }

    public void setListViewWrapper(@NonNull ListViewWrapper listViewWrapper) {
        super.setListViewWrapper(listViewWrapper);
        this.mViewAnimator = new ViewAnimator(listViewWrapper);
    }
}
