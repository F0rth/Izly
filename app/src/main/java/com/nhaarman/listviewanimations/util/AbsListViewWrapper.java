package com.nhaarman.listviewanimations.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class AbsListViewWrapper implements ListViewWrapper {
    @NonNull
    private final AbsListView mAbsListView;

    public AbsListViewWrapper(@NonNull AbsListView absListView) {
        this.mAbsListView = absListView;
    }

    public ListAdapter getAdapter() {
        return (ListAdapter) this.mAbsListView.getAdapter();
    }

    @Nullable
    public View getChildAt(int i) {
        return this.mAbsListView.getChildAt(i);
    }

    public int getChildCount() {
        return this.mAbsListView.getChildCount();
    }

    public int getCount() {
        return this.mAbsListView.getCount();
    }

    public int getFirstVisiblePosition() {
        return this.mAbsListView.getFirstVisiblePosition();
    }

    public int getHeaderViewsCount() {
        return this.mAbsListView instanceof ListView ? ((ListView) this.mAbsListView).getHeaderViewsCount() : 0;
    }

    public int getLastVisiblePosition() {
        return this.mAbsListView.getLastVisiblePosition();
    }

    @NonNull
    public AbsListView getListView() {
        return this.mAbsListView;
    }

    public int getPositionForView(@NonNull View view) {
        return this.mAbsListView.getPositionForView(view);
    }

    public void smoothScrollBy(int i, int i2) {
        this.mAbsListView.smoothScrollBy(i, i2);
    }
}
