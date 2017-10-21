package com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.nhaarman.listviewanimations.BaseAdapterDecorator;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import java.util.ArrayList;
import java.util.Collection;

public class SimpleSwipeUndoAdapter extends SwipeUndoAdapter implements UndoCallback {
    @NonNull
    private final Context mContext;
    @NonNull
    private final OnDismissCallback mOnDismissCallback;
    @NonNull
    private final UndoAdapter mUndoAdapter;
    private final Collection<Integer> mUndoPositions = new ArrayList();

    class UndoClickListener implements OnClickListener {
        private final int mPosition;
        @NonNull
        private final SwipeUndoView mView;

        UndoClickListener(SwipeUndoView swipeUndoView, @NonNull int i) {
            this.mView = swipeUndoView;
            this.mPosition = i;
        }

        public void onClick(@NonNull View view) {
            SimpleSwipeUndoAdapter.this.undo(this.mView);
        }
    }

    public SimpleSwipeUndoAdapter(@NonNull BaseAdapter baseAdapter, @NonNull Context context, @NonNull OnDismissCallback onDismissCallback) {
        super(baseAdapter, null);
        setUndoCallback(this);
        BaseAdapter baseAdapter2 = baseAdapter;
        while (baseAdapter2 instanceof BaseAdapterDecorator) {
            baseAdapter2 = ((BaseAdapterDecorator) baseAdapter2).getDecoratedBaseAdapter();
        }
        if (baseAdapter2 instanceof UndoAdapter) {
            this.mUndoAdapter = (UndoAdapter) baseAdapter2;
            this.mContext = context;
            this.mOnDismissCallback = onDismissCallback;
            return;
        }
        throw new IllegalStateException("BaseAdapter must implement UndoAdapter!");
    }

    @NonNull
    public View getPrimaryView(@NonNull View view) {
        View primaryView = ((SwipeUndoView) view).getPrimaryView();
        if (primaryView != null) {
            return primaryView;
        }
        throw new IllegalStateException("primaryView == null");
    }

    @NonNull
    public View getUndoView(@NonNull View view) {
        View undoView = ((SwipeUndoView) view).getUndoView();
        if (undoView != null) {
            return undoView;
        }
        throw new IllegalStateException("undoView == null");
    }

    @NonNull
    public View getView(int i, @Nullable View view, @NonNull ViewGroup viewGroup) {
        int i2 = 0;
        view = (SwipeUndoView) view;
        if (view == null) {
            view = new SwipeUndoView(this.mContext);
        }
        View view2 = super.getView(i, view.getPrimaryView(), view);
        view.setPrimaryView(view2);
        View undoView = this.mUndoAdapter.getUndoView(i, view.getUndoView(), view);
        view.setUndoView(undoView);
        this.mUndoAdapter.getUndoClickView(undoView).setOnClickListener(new UndoClickListener(view, i));
        boolean contains = this.mUndoPositions.contains(Integer.valueOf(i));
        view2.setVisibility(contains ? 8 : 0);
        if (!contains) {
            i2 = 8;
        }
        undoView.setVisibility(i2);
        return view;
    }

    public void onDismiss(@NonNull View view, int i) {
        this.mUndoPositions.remove(Integer.valueOf(i));
    }

    public void onDismiss(@NonNull ViewGroup viewGroup, @NonNull int[] iArr) {
        this.mOnDismissCallback.onDismiss(viewGroup, iArr);
        Collection processDeletions = Util.processDeletions(this.mUndoPositions, iArr);
        this.mUndoPositions.clear();
        this.mUndoPositions.addAll(processDeletions);
    }

    public void onUndo(@NonNull View view, int i) {
        this.mUndoPositions.remove(Integer.valueOf(i));
    }

    public void onUndoShown(@NonNull View view, int i) {
        this.mUndoPositions.add(Integer.valueOf(i));
    }
}
