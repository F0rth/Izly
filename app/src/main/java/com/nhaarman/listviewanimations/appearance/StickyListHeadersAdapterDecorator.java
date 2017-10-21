package com.nhaarman.listviewanimations.appearance;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.nhaarman.listviewanimations.BaseAdapterDecorator;
import com.nhaarman.listviewanimations.util.AnimatorUtil;
import com.nhaarman.listviewanimations.util.ListViewWrapper;
import com.nhaarman.listviewanimations.util.StickyListHeadersListViewWrapper;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class StickyListHeadersAdapterDecorator extends BaseAdapterDecorator implements StickyListHeadersAdapter {
    static final /* synthetic */ boolean $assertionsDisabled = (!StickyListHeadersAdapterDecorator.class.desiredAssertionStatus());
    private static final String ALPHA = "alpha";
    @NonNull
    private final StickyListHeadersAdapter mStickyListHeadersAdapter;
    @Nullable
    private ViewAnimator mViewAnimator;

    public StickyListHeadersAdapterDecorator(@NonNull BaseAdapter baseAdapter) {
        super(baseAdapter);
        Object obj = baseAdapter;
        while (obj instanceof BaseAdapterDecorator) {
            obj = ((BaseAdapterDecorator) obj).getDecoratedBaseAdapter();
        }
        if (obj instanceof StickyListHeadersAdapter) {
            this.mStickyListHeadersAdapter = (StickyListHeadersAdapter) obj;
            return;
        }
        throw new IllegalArgumentException(obj.getClass().getCanonicalName() + " does not implement StickyListHeadersAdapter");
    }

    private void animateViewIfNecessary(int i, @NonNull View view, @NonNull ViewGroup viewGroup) {
        Animator[] animators = getDecoratedBaseAdapter() instanceof AnimationAdapter ? ((AnimationAdapter) getDecoratedBaseAdapter()).getAnimators(viewGroup, view) : new Animator[0];
        Animator ofFloat = ObjectAnimator.ofFloat((Object) view, ALPHA, 0.0f, 1.0f);
        if ($assertionsDisabled || this.mViewAnimator != null) {
            this.mViewAnimator.animateViewIfNecessary(i, view, AnimatorUtil.concatAnimators(animators, new Animator[0], ofFloat));
            return;
        }
        throw new AssertionError();
    }

    public long getHeaderId(int i) {
        return this.mStickyListHeadersAdapter.getHeaderId(i);
    }

    public View getHeaderView(int i, View view, ViewGroup viewGroup) {
        if (getListViewWrapper() == null) {
            throw new IllegalStateException("Call setStickyListHeadersListView() on this AnimationAdapter first!");
        }
        if (view != null) {
            if ($assertionsDisabled || this.mViewAnimator != null) {
                this.mViewAnimator.cancelExistingAnimation(view);
            } else {
                throw new AssertionError();
            }
        }
        View headerView = this.mStickyListHeadersAdapter.getHeaderView(i, view, viewGroup);
        animateViewIfNecessary(i, headerView, viewGroup);
        return headerView;
    }

    @Nullable
    public ViewAnimator getViewAnimator() {
        return this.mViewAnimator;
    }

    public void setListViewWrapper(@NonNull ListViewWrapper listViewWrapper) {
        super.setListViewWrapper(listViewWrapper);
        this.mViewAnimator = new ViewAnimator(listViewWrapper);
    }

    public void setStickyListHeadersListView(@NonNull StickyListHeadersListView stickyListHeadersListView) {
        setListViewWrapper(new StickyListHeadersListViewWrapper(stickyListHeadersListView));
    }
}
