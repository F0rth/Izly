package com.nhaarman.listviewanimations.itemmanipulation.swipedismiss;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.BaseAdapter;
import com.nhaarman.listviewanimations.ArrayAdapter;
import com.nhaarman.listviewanimations.BaseAdapterDecorator;
import com.nhaarman.listviewanimations.util.ListViewWrapper;

public class SwipeDismissAdapter extends BaseAdapterDecorator {
    @Nullable
    private SwipeDismissTouchListener mDismissTouchListener;
    @NonNull
    private final OnDismissCallback mOnDismissCallback;
    private boolean mParentIsHorizontalScrollContainer;
    private int mSwipeTouchChildResId;

    public SwipeDismissAdapter(@NonNull BaseAdapter baseAdapter, @NonNull OnDismissCallback onDismissCallback) {
        super(baseAdapter);
        this.mOnDismissCallback = onDismissCallback;
    }

    public void dismiss(int i) {
        if (this.mDismissTouchListener == null) {
            throw new IllegalStateException("Call setListViewWrapper on this SwipeDismissAdapter!");
        }
        this.mDismissTouchListener.dismiss(i);
    }

    @Nullable
    public SwipeDismissTouchListener getDismissTouchListener() {
        return this.mDismissTouchListener;
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if (this.mDismissTouchListener != null) {
            this.mDismissTouchListener.notifyDataSetChanged();
        }
    }

    public void setDismissableManager(@Nullable DismissableManager dismissableManager) {
        if (this.mDismissTouchListener == null) {
            throw new IllegalStateException("You must call setAbsListView() first.");
        }
        this.mDismissTouchListener.setDismissableManager(dismissableManager);
    }

    public void setListViewWrapper(@NonNull ListViewWrapper listViewWrapper) {
        super.setListViewWrapper(listViewWrapper);
        if (getDecoratedBaseAdapter() instanceof ArrayAdapter) {
            ((ArrayAdapter) getDecoratedBaseAdapter()).propagateNotifyDataSetChanged(this);
        }
        this.mDismissTouchListener = new SwipeDismissTouchListener(listViewWrapper, this.mOnDismissCallback);
        if (this.mParentIsHorizontalScrollContainer) {
            this.mDismissTouchListener.setParentIsHorizontalScrollContainer();
        }
        if (this.mSwipeTouchChildResId != 0) {
            this.mDismissTouchListener.setTouchChild(this.mSwipeTouchChildResId);
        }
        listViewWrapper.getListView().setOnTouchListener(this.mDismissTouchListener);
    }

    public void setParentIsHorizontalScrollContainer() {
        this.mParentIsHorizontalScrollContainer = true;
        this.mSwipeTouchChildResId = 0;
        if (this.mDismissTouchListener != null) {
            this.mDismissTouchListener.setParentIsHorizontalScrollContainer();
        }
    }

    public void setSwipeTouchChildResId(int i) {
        this.mSwipeTouchChildResId = i;
        if (this.mDismissTouchListener != null) {
            this.mDismissTouchListener.setTouchChild(i);
        }
    }
}
