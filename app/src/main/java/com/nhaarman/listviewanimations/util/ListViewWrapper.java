package com.nhaarman.listviewanimations.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

public interface ListViewWrapper {
    @Nullable
    ListAdapter getAdapter();

    @Nullable
    View getChildAt(int i);

    int getChildCount();

    int getCount();

    int getFirstVisiblePosition();

    int getHeaderViewsCount();

    int getLastVisiblePosition();

    @NonNull
    ViewGroup getListView();

    int getPositionForView(@NonNull View view);

    void smoothScrollBy(int i, int i2);
}
