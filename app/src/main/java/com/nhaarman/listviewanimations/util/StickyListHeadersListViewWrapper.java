package com.nhaarman.listviewanimations.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class StickyListHeadersListViewWrapper implements ListViewWrapper {
    @NonNull
    private final StickyListHeadersListView mListView;

    public StickyListHeadersListViewWrapper(@NonNull StickyListHeadersListView stickyListHeadersListView) {
        this.mListView = stickyListHeadersListView;
    }

    @NonNull
    public ListAdapter getAdapter() {
        return this.mListView.getAdapter();
    }

    @Nullable
    public View getChildAt(int i) {
        return this.mListView.getListChildAt(i);
    }

    public int getChildCount() {
        return this.mListView.getChildCount();
    }

    public int getCount() {
        return this.mListView.getCount();
    }

    public int getFirstVisiblePosition() {
        return this.mListView.getFirstVisiblePosition();
    }

    public int getHeaderViewsCount() {
        return this.mListView.getHeaderViewsCount();
    }

    public int getLastVisiblePosition() {
        return this.mListView.getLastVisiblePosition();
    }

    @NonNull
    public StickyListHeadersListView getListView() {
        return this.mListView;
    }

    public int getPositionForView(@NonNull View view) {
        return this.mListView.getPositionForView(view);
    }

    public void smoothScrollBy(int i, int i2) {
        this.mListView.smoothScrollBy(i, i2);
    }
}
