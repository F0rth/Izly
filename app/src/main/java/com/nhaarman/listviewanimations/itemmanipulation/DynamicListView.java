package com.nhaarman.listviewanimations.itemmanipulation;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.nhaarman.listviewanimations.BaseAdapterDecorator;
import com.nhaarman.listviewanimations.itemmanipulation.animateaddition.AnimateAdditionAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.DragAndDropHandler;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.DraggableManager;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.DynamicListViewWrapper;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.OnItemMovedListener;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.DismissableManager;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissTouchListener;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeTouchListener;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.SwipeUndoAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.SwipeUndoTouchListener;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.UndoCallback;
import com.nhaarman.listviewanimations.util.Insertable;
import defpackage.kh;
import java.util.Collection;
import java.util.HashSet;

public class DynamicListView extends ListView {
    @Nullable
    private AnimateAdditionAdapter<Object> mAnimateAdditionAdapter;
    @Nullable
    private TouchEventHandler mCurrentHandlingTouchEventHandler;
    @Nullable
    private DragAndDropHandler mDragAndDropHandler;
    @NonNull
    private final MyOnScrollListener mMyOnScrollListener;
    @Nullable
    private SwipeTouchListener mSwipeTouchListener;
    @Nullable
    private SwipeUndoAdapter mSwipeUndoAdapter;

    class MyOnScrollListener implements OnScrollListener {
        private final Collection<OnScrollListener> mOnScrollListeners;

        private MyOnScrollListener() {
            this.mOnScrollListeners = new HashSet();
        }

        public void addOnScrollListener(OnScrollListener onScrollListener) {
            this.mOnScrollListeners.add(onScrollListener);
        }

        public void onScroll(AbsListView absListView, int i, int i2, int i3) {
            for (OnScrollListener onScroll : this.mOnScrollListeners) {
                onScroll.onScroll(absListView, i, i2, i3);
            }
        }

        public void onScrollStateChanged(AbsListView absListView, int i) {
            for (OnScrollListener onScrollStateChanged : this.mOnScrollListeners) {
                onScrollStateChanged.onScrollStateChanged(absListView, i);
            }
            if (i == 1 && (DynamicListView.this.mSwipeTouchListener instanceof SwipeUndoTouchListener)) {
                ((SwipeUndoTouchListener) DynamicListView.this.mSwipeTouchListener).dimissPending();
            }
        }
    }

    public DynamicListView(@NonNull Context context) {
        this(context, null);
    }

    public DynamicListView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, Resources.getSystem().getIdentifier("listViewStyle", "attr", kh.ANDROID_CLIENT_TYPE));
    }

    public DynamicListView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mMyOnScrollListener = new MyOnScrollListener();
        super.setOnScrollListener(this.mMyOnScrollListener);
    }

    private void sendCancelEvent(@Nullable TouchEventHandler touchEventHandler, @NonNull MotionEvent motionEvent) {
        if (touchEventHandler != null) {
            MotionEvent obtain = MotionEvent.obtain(motionEvent);
            obtain.setAction(3);
            touchEventHandler.onTouchEvent(obtain);
        }
    }

    public int computeVerticalScrollExtent() {
        return super.computeVerticalScrollExtent();
    }

    public int computeVerticalScrollOffset() {
        return super.computeVerticalScrollOffset();
    }

    public int computeVerticalScrollRange() {
        return super.computeVerticalScrollRange();
    }

    public void disableDragAndDrop() {
        this.mDragAndDropHandler = null;
    }

    public void disableSwipeToDismiss() {
        this.mSwipeTouchListener = null;
    }

    public void dismiss(int i) {
        if (this.mSwipeTouchListener == null) {
            return;
        }
        if (this.mSwipeTouchListener instanceof SwipeDismissTouchListener) {
            ((SwipeDismissTouchListener) this.mSwipeTouchListener).dismiss(i);
            return;
        }
        throw new IllegalStateException("Enabled swipe functionality does not support dismiss");
    }

    protected void dispatchDraw(@NonNull Canvas canvas) {
        super.dispatchDraw(canvas);
        if (this.mDragAndDropHandler != null) {
            this.mDragAndDropHandler.dispatchDraw(canvas);
        }
    }

    public boolean dispatchTouchEvent(@NonNull MotionEvent motionEvent) {
        if (this.mCurrentHandlingTouchEventHandler != null) {
            return onTouchEvent(motionEvent);
        }
        boolean z;
        if (((this.mSwipeTouchListener instanceof SwipeUndoTouchListener) && ((SwipeUndoTouchListener) this.mSwipeTouchListener).hasPendingItems()) || this.mDragAndDropHandler == null) {
            z = false;
        } else {
            this.mDragAndDropHandler.onTouchEvent(motionEvent);
            z = this.mDragAndDropHandler.isInteracting();
            if (z) {
                this.mCurrentHandlingTouchEventHandler = this.mDragAndDropHandler;
                sendCancelEvent(this.mSwipeTouchListener, motionEvent);
            }
        }
        if (this.mCurrentHandlingTouchEventHandler == null && this.mSwipeTouchListener != null) {
            this.mSwipeTouchListener.onTouchEvent(motionEvent);
            z = this.mSwipeTouchListener.isInteracting();
            if (z) {
                this.mCurrentHandlingTouchEventHandler = this.mSwipeTouchListener;
                sendCancelEvent(this.mDragAndDropHandler, motionEvent);
            }
        }
        if (z) {
            MotionEvent obtain = MotionEvent.obtain(motionEvent);
            obtain.setAction(3);
            super.onTouchEvent(obtain);
        }
        return z || super.dispatchTouchEvent(motionEvent);
    }

    public void enableDragAndDrop() {
        if (VERSION.SDK_INT < 14) {
            throw new UnsupportedOperationException("Drag and drop is only supported API levels 14 and up!");
        }
        this.mDragAndDropHandler = new DragAndDropHandler(this);
    }

    public void enableSimpleSwipeUndo() {
        if (this.mSwipeUndoAdapter == null) {
            throw new IllegalStateException("enableSimpleSwipeUndo requires a SwipeUndoAdapter to be set as an adapter");
        }
        this.mSwipeTouchListener = new SwipeUndoTouchListener(new DynamicListViewWrapper(this), this.mSwipeUndoAdapter.getUndoCallback());
        this.mSwipeUndoAdapter.setSwipeUndoTouchListener((SwipeUndoTouchListener) this.mSwipeTouchListener);
    }

    public void enableSwipeToDismiss(@NonNull OnDismissCallback onDismissCallback) {
        this.mSwipeTouchListener = new SwipeDismissTouchListener(new DynamicListViewWrapper(this), onDismissCallback);
    }

    public void enableSwipeUndo(@NonNull UndoCallback undoCallback) {
        this.mSwipeTouchListener = new SwipeUndoTouchListener(new DynamicListViewWrapper(this), undoCallback);
    }

    public void fling(int i) {
        if (this.mSwipeTouchListener != null) {
            this.mSwipeTouchListener.fling(i);
        }
    }

    public void insert(int i, Object obj) {
        if (this.mAnimateAdditionAdapter == null) {
            throw new IllegalStateException("Adapter should implement Insertable!");
        }
        this.mAnimateAdditionAdapter.insert(i, obj);
    }

    public void insert(int i, Object... objArr) {
        if (this.mAnimateAdditionAdapter == null) {
            throw new IllegalStateException("Adapter should implement Insertable!");
        }
        this.mAnimateAdditionAdapter.insert(i, objArr);
    }

    public <T> void insert(@NonNull Iterable<Pair<Integer, T>> iterable) {
        if (this.mAnimateAdditionAdapter == null) {
            throw new IllegalStateException("Adapter should implement Insertable!");
        }
        this.mAnimateAdditionAdapter.insert(iterable);
    }

    public <T> void insert(@NonNull Pair<Integer, T>... pairArr) {
        if (this.mAnimateAdditionAdapter == null) {
            throw new IllegalStateException("Adapter should implement Insertable!");
        }
        this.mAnimateAdditionAdapter.insert(pairArr);
    }

    public boolean onTouchEvent(@NonNull MotionEvent motionEvent) {
        if (this.mCurrentHandlingTouchEventHandler != null) {
            this.mCurrentHandlingTouchEventHandler.onTouchEvent(motionEvent);
        }
        if (motionEvent.getActionMasked() == 1 || motionEvent.getActionMasked() == 3) {
            this.mCurrentHandlingTouchEventHandler = null;
        }
        return this.mCurrentHandlingTouchEventHandler != null || super.onTouchEvent(motionEvent);
    }

    public void setAdapter(ListAdapter listAdapter) {
        ListAdapter listAdapter2;
        this.mSwipeUndoAdapter = null;
        if (listAdapter instanceof BaseAdapter) {
            BaseAdapter baseAdapter = (BaseAdapter) listAdapter;
            while (baseAdapter instanceof BaseAdapterDecorator) {
                if (baseAdapter instanceof SwipeUndoAdapter) {
                    this.mSwipeUndoAdapter = (SwipeUndoAdapter) baseAdapter;
                }
                baseAdapter = ((BaseAdapterDecorator) baseAdapter).getDecoratedBaseAdapter();
            }
            if (baseAdapter instanceof Insertable) {
                this.mAnimateAdditionAdapter = new AnimateAdditionAdapter((BaseAdapter) listAdapter);
                this.mAnimateAdditionAdapter.setListView(this);
                listAdapter2 = this.mAnimateAdditionAdapter;
                super.setAdapter(listAdapter2);
                if (this.mDragAndDropHandler != null) {
                    this.mDragAndDropHandler.setAdapter(listAdapter);
                }
            }
        }
        listAdapter2 = listAdapter;
        super.setAdapter(listAdapter2);
        if (this.mDragAndDropHandler != null) {
            this.mDragAndDropHandler.setAdapter(listAdapter);
        }
    }

    public void setDismissableManager(@Nullable DismissableManager dismissableManager) {
        if (this.mSwipeTouchListener != null) {
            this.mSwipeTouchListener.setDismissableManager(dismissableManager);
        }
    }

    public void setDraggableManager(@NonNull DraggableManager draggableManager) {
        if (this.mDragAndDropHandler != null) {
            this.mDragAndDropHandler.setDraggableManager(draggableManager);
        }
    }

    public void setMinimumAlpha(float f) {
        if (this.mSwipeTouchListener != null) {
            this.mSwipeTouchListener.setMinimumAlpha(f);
        }
    }

    public void setOnItemMovedListener(@Nullable OnItemMovedListener onItemMovedListener) {
        if (this.mDragAndDropHandler != null) {
            this.mDragAndDropHandler.setOnItemMovedListener(onItemMovedListener);
        }
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mMyOnScrollListener.addOnScrollListener(onScrollListener);
    }

    public void setOnTouchListener(OnTouchListener onTouchListener) {
        if (!(onTouchListener instanceof SwipeTouchListener)) {
            super.setOnTouchListener(onTouchListener);
        }
    }

    public void setScrollSpeed(float f) {
        if (this.mDragAndDropHandler != null) {
            this.mDragAndDropHandler.setScrollSpeed(f);
        }
    }

    public void setSwipeTouchChild(int i) {
        if (this.mSwipeTouchListener != null) {
            this.mSwipeTouchListener.setTouchChild(i);
        }
    }

    public void startDragging(int i) {
        if ((!(this.mSwipeTouchListener instanceof SwipeUndoTouchListener) || !((SwipeUndoTouchListener) this.mSwipeTouchListener).hasPendingItems()) && this.mDragAndDropHandler != null) {
            this.mDragAndDropHandler.startDragging(i);
        }
    }

    public void undo(@NonNull View view) {
        if (this.mSwipeTouchListener == null) {
            return;
        }
        if (this.mSwipeTouchListener instanceof SwipeUndoTouchListener) {
            ((SwipeUndoTouchListener) this.mSwipeTouchListener).undo(view);
            return;
        }
        throw new IllegalStateException("Enabled swipe functionality does not support undo");
    }
}
