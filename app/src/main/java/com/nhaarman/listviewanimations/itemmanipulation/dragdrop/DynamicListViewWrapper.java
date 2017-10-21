package com.nhaarman.listviewanimations.itemmanipulation.dragdrop;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;

public class DynamicListViewWrapper implements DragAndDropListViewWrapper {
    @NonNull
    private final DynamicListView mDynamicListView;

    public DynamicListViewWrapper(@NonNull DynamicListView dynamicListView) {
        this.mDynamicListView = dynamicListView;
    }

    public int computeVerticalScrollExtent() {
        return this.mDynamicListView.computeVerticalScrollExtent();
    }

    public int computeVerticalScrollOffset() {
        return this.mDynamicListView.computeVerticalScrollOffset();
    }

    public int computeVerticalScrollRange() {
        return this.mDynamicListView.computeVerticalScrollRange();
    }

    @Nullable
    public ListAdapter getAdapter() {
        return this.mDynamicListView.getAdapter();
    }

    @Nullable
    public View getChildAt(int i) {
        return this.mDynamicListView.getChildAt(i);
    }

    public int getChildCount() {
        return this.mDynamicListView.getChildCount();
    }

    public int getCount() {
        return this.mDynamicListView.getCount();
    }

    public int getFirstVisiblePosition() {
        return this.mDynamicListView.getFirstVisiblePosition();
    }

    public int getHeaderViewsCount() {
        return this.mDynamicListView.getHeaderViewsCount();
    }

    public int getLastVisiblePosition() {
        return this.mDynamicListView.getLastVisiblePosition();
    }

    @NonNull
    public DynamicListView getListView() {
        return this.mDynamicListView;
    }

    public int getPositionForView(@NonNull View view) {
        return this.mDynamicListView.getPositionForView(view);
    }

    public int pointToPosition(int i, int i2) {
        return this.mDynamicListView.pointToPosition(i, i2);
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mDynamicListView.setOnScrollListener(onScrollListener);
    }

    public void smoothScrollBy(int i, int i2) {
        this.mDynamicListView.smoothScrollBy(i, i2);
    }
}
