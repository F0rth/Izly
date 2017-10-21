package com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.nhaarman.listviewanimations.BaseAdapterDecorator;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.DismissableManager;
import com.nhaarman.listviewanimations.util.ListViewWrapper;

public abstract class SwipeUndoAdapter extends BaseAdapterDecorator {
    @Nullable
    private SwipeUndoTouchListener mSwipeUndoTouchListener;
    @NonNull
    private UndoCallback mUndoCallback;

    protected SwipeUndoAdapter(@NonNull BaseAdapter baseAdapter, @NonNull UndoCallback undoCallback) {
        super(baseAdapter);
        this.mUndoCallback = undoCallback;
    }

    public void dismiss(int i) {
        this.mSwipeUndoTouchListener.dismiss(i);
    }

    @NonNull
    public UndoCallback getUndoCallback() {
        return this.mUndoCallback;
    }

    @NonNull
    public View getView(int i, @Nullable View view, @NonNull ViewGroup viewGroup) {
        if (getListViewWrapper() != null) {
            return super.getView(i, view, viewGroup);
        }
        throw new IllegalArgumentException("Call setAbsListView() on this SwipeUndoAdapter before setAdapter()!");
    }

    public void setDismissableManager(@Nullable DismissableManager dismissableManager) {
        if (this.mSwipeUndoTouchListener == null) {
            throw new IllegalStateException("You must call setAbsListView() first.");
        }
        this.mSwipeUndoTouchListener.setDismissableManager(dismissableManager);
    }

    public void setListViewWrapper(@NonNull ListViewWrapper listViewWrapper) {
        super.setListViewWrapper(listViewWrapper);
        this.mSwipeUndoTouchListener = new SwipeUndoTouchListener(listViewWrapper, this.mUndoCallback);
        if (!(listViewWrapper.getListView() instanceof DynamicListView)) {
            listViewWrapper.getListView().setOnTouchListener(this.mSwipeUndoTouchListener);
        }
    }

    public void setSwipeUndoTouchListener(@NonNull SwipeUndoTouchListener swipeUndoTouchListener) {
        this.mSwipeUndoTouchListener = swipeUndoTouchListener;
    }

    public void setUndoCallback(@NonNull UndoCallback undoCallback) {
        this.mUndoCallback = undoCallback;
    }

    public void undo(@NonNull View view) {
        this.mSwipeUndoTouchListener.undo(view);
    }
}
