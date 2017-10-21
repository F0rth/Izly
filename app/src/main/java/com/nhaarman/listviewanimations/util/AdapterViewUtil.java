package com.nhaarman.listviewanimations.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

public class AdapterViewUtil {
    private AdapterViewUtil() {
    }

    public static int getPositionForView(@NonNull AbsListView absListView, @NonNull View view) {
        int positionForView = absListView.getPositionForView(view);
        return absListView instanceof ListView ? positionForView - ((ListView) absListView).getHeaderViewsCount() : positionForView;
    }

    public static int getPositionForView(@NonNull ListViewWrapper listViewWrapper, @NonNull View view) {
        return listViewWrapper.getPositionForView(view) - listViewWrapper.getHeaderViewsCount();
    }

    @Nullable
    public static View getViewForPosition(@NonNull AbsListView absListView, int i) {
        int childCount = absListView.getChildCount();
        View view = null;
        int i2 = 0;
        while (i2 < childCount && view == null) {
            View childAt = absListView.getChildAt(i2);
            if (childAt == null || getPositionForView(absListView, childAt) != i) {
                childAt = view;
            }
            i2++;
            view = childAt;
        }
        return view;
    }

    @Nullable
    public static View getViewForPosition(@NonNull ListViewWrapper listViewWrapper, int i) {
        int childCount = listViewWrapper.getChildCount();
        View view = null;
        int i2 = 0;
        while (i2 < childCount && view == null) {
            View childAt = listViewWrapper.getChildAt(i2);
            if (childAt == null || getPositionForView(listViewWrapper, childAt) != i) {
                childAt = view;
            }
            i2++;
            view = childAt;
        }
        return view;
    }
}
