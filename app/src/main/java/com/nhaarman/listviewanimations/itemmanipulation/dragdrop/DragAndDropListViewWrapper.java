package com.nhaarman.listviewanimations.itemmanipulation.dragdrop;

import android.widget.AbsListView.OnScrollListener;
import com.nhaarman.listviewanimations.util.ListViewWrapper;

public interface DragAndDropListViewWrapper extends ListViewWrapper {
    int computeVerticalScrollExtent();

    int computeVerticalScrollOffset();

    int computeVerticalScrollRange();

    int pointToPosition(int i, int i2);

    void setOnScrollListener(OnScrollListener onScrollListener);
}
