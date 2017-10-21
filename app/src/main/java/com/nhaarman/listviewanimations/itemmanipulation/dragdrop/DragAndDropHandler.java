package com.nhaarman.listviewanimations.itemmanipulation.dragdrop;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.WrapperListAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.TouchEventHandler;
import com.nhaarman.listviewanimations.util.Swappable;
import org.spongycastle.crypto.tls.CipherSuite;

@TargetApi(14)
public class DragAndDropHandler implements TouchEventHandler {
    static final /* synthetic */ boolean $assertionsDisabled = (!DragAndDropHandler.class.desiredAssertionStatus());
    private static final int INVALID_ID = -1;
    @Nullable
    private ListAdapter mAdapter;
    private float mDownX;
    private float mDownY;
    @NonNull
    private DraggableManager mDraggableManager;
    @Nullable
    private HoverDrawable mHoverDrawable;
    private boolean mIsSettlingHoverDrawable;
    private float mLastMotionEventY;
    private long mMobileItemId;
    @Nullable
    private View mMobileView;
    @Nullable
    private OnItemMovedListener mOnItemMovedListener;
    private int mOriginalMobileItemPosition;
    @NonNull
    private final ScrollHandler mScrollHandler;
    private final int mSlop;
    @NonNull
    private final SwitchViewAnimator mSwitchViewAnimator;
    @NonNull
    private final DragAndDropListViewWrapper mWrapper;

    static class DefaultDraggableManager implements DraggableManager {
        private DefaultDraggableManager() {
        }

        public boolean isDraggable(@NonNull View view, int i, float f, float f2) {
            return false;
        }
    }

    interface SwitchViewAnimator {
        void animateSwitchView(long j, float f);
    }

    class KitKatSwitchViewAnimator implements SwitchViewAnimator {
        static final /* synthetic */ boolean $assertionsDisabled = (!DragAndDropHandler.class.desiredAssertionStatus());

        class AnimateSwitchViewOnPreDrawListener implements OnPreDrawListener {
            private final View mPreviousMobileView;
            private final long mSwitchId;
            private final float mTranslationY;

            AnimateSwitchViewOnPreDrawListener(View view, long j, float f) {
                this.mPreviousMobileView = view;
                this.mSwitchId = j;
                this.mTranslationY = f;
            }

            public boolean onPreDraw() {
                DragAndDropHandler.this.mWrapper.getListView().getViewTreeObserver().removeOnPreDrawListener(this);
                View access$700 = DragAndDropHandler.this.getViewForId(this.mSwitchId);
                if (access$700 != null) {
                    access$700.setTranslationY(this.mTranslationY);
                    access$700.animate().translationY(0.0f).start();
                }
                this.mPreviousMobileView.setVisibility(0);
                if (DragAndDropHandler.this.mMobileView != null) {
                    DragAndDropHandler.this.mMobileView.setVisibility(4);
                }
                return true;
            }
        }

        private KitKatSwitchViewAnimator() {
        }

        public void animateSwitchView(long j, float f) {
            if ($assertionsDisabled || DragAndDropHandler.this.mMobileView != null) {
                DragAndDropHandler.this.mWrapper.getListView().getViewTreeObserver().addOnPreDrawListener(new AnimateSwitchViewOnPreDrawListener(DragAndDropHandler.this.mMobileView, j, f));
                DragAndDropHandler.this.mMobileView = DragAndDropHandler.this.getViewForId(DragAndDropHandler.this.mMobileItemId);
                return;
            }
            throw new AssertionError();
        }
    }

    class LSwitchViewAnimator implements SwitchViewAnimator {

        class AnimateSwitchViewOnPreDrawListener implements OnPreDrawListener {
            static final /* synthetic */ boolean $assertionsDisabled = (!DragAndDropHandler.class.desiredAssertionStatus());
            private final long mSwitchId;
            private final float mTranslationY;

            AnimateSwitchViewOnPreDrawListener(long j, float f) {
                this.mSwitchId = j;
                this.mTranslationY = f;
            }

            public boolean onPreDraw() {
                DragAndDropHandler.this.mWrapper.getListView().getViewTreeObserver().removeOnPreDrawListener(this);
                View access$700 = DragAndDropHandler.this.getViewForId(this.mSwitchId);
                if (access$700 != null) {
                    access$700.setTranslationY(this.mTranslationY);
                    access$700.animate().translationY(0.0f).start();
                }
                if ($assertionsDisabled || DragAndDropHandler.this.mMobileView != null) {
                    DragAndDropHandler.this.mMobileView.setVisibility(0);
                    DragAndDropHandler.this.mMobileView = DragAndDropHandler.this.getViewForId(DragAndDropHandler.this.mMobileItemId);
                    if ($assertionsDisabled || DragAndDropHandler.this.mMobileView != null) {
                        DragAndDropHandler.this.mMobileView.setVisibility(4);
                        return true;
                    }
                    throw new AssertionError();
                }
                throw new AssertionError();
            }
        }

        private LSwitchViewAnimator() {
        }

        public void animateSwitchView(long j, float f) {
            DragAndDropHandler.this.mWrapper.getListView().getViewTreeObserver().addOnPreDrawListener(new AnimateSwitchViewOnPreDrawListener(j, f));
        }
    }

    class ScrollHandler implements OnScrollListener {
        static final /* synthetic */ boolean $assertionsDisabled = (!DragAndDropHandler.class.desiredAssertionStatus());
        private static final int SMOOTH_SCROLL_DP = 3;
        private int mCurrentFirstVisibleItem;
        private int mCurrentLastVisibleItem;
        private int mPreviousFirstVisibleItem = -1;
        private int mPreviousLastVisibleItem = -1;
        private float mScrollSpeedFactor = 1.0f;
        private final int mSmoothScrollPx;

        ScrollHandler() {
            this.mSmoothScrollPx = (int) TypedValue.applyDimension(1, 3.0f, DragAndDropHandler.this.mWrapper.getListView().getResources().getDisplayMetrics());
        }

        private void checkAndHandleFirstVisibleCellChange() {
            if (DragAndDropHandler.this.mHoverDrawable != null && DragAndDropHandler.this.mAdapter != null && this.mCurrentFirstVisibleItem < this.mPreviousFirstVisibleItem) {
                int access$1100 = DragAndDropHandler.this.getPositionForId(DragAndDropHandler.this.mMobileItemId);
                if (access$1100 != -1) {
                    long itemId = (access$1100 + -1) - DragAndDropHandler.this.mWrapper.getHeaderViewsCount() >= 0 ? DragAndDropHandler.this.mAdapter.getItemId((access$1100 - 1) - DragAndDropHandler.this.mWrapper.getHeaderViewsCount()) : -1;
                    View access$700 = DragAndDropHandler.this.getViewForId(itemId);
                    if (access$700 != null) {
                        DragAndDropHandler.this.switchViews(access$700, itemId, (float) (-access$700.getHeight()));
                    }
                }
            }
        }

        private void checkAndHandleLastVisibleCellChange() {
            if (DragAndDropHandler.this.mHoverDrawable != null && DragAndDropHandler.this.mAdapter != null && this.mCurrentLastVisibleItem > this.mPreviousLastVisibleItem) {
                int access$1100 = DragAndDropHandler.this.getPositionForId(DragAndDropHandler.this.mMobileItemId);
                if (access$1100 != -1) {
                    long itemId = (access$1100 + 1) - DragAndDropHandler.this.mWrapper.getHeaderViewsCount() < DragAndDropHandler.this.mAdapter.getCount() ? DragAndDropHandler.this.mAdapter.getItemId((access$1100 + 1) - DragAndDropHandler.this.mWrapper.getHeaderViewsCount()) : -1;
                    View access$700 = DragAndDropHandler.this.getViewForId(itemId);
                    if (access$700 != null) {
                        DragAndDropHandler.this.switchViews(access$700, itemId, (float) access$700.getHeight());
                    }
                }
            }
        }

        void handleMobileCellScroll() {
            if (DragAndDropHandler.this.mHoverDrawable != null && !DragAndDropHandler.this.mIsSettlingHoverDrawable) {
                Rect bounds = DragAndDropHandler.this.mHoverDrawable.getBounds();
                int computeVerticalScrollOffset = DragAndDropHandler.this.mWrapper.computeVerticalScrollOffset();
                int height = DragAndDropHandler.this.mWrapper.getListView().getHeight();
                int computeVerticalScrollExtent = DragAndDropHandler.this.mWrapper.computeVerticalScrollExtent();
                int computeVerticalScrollRange = DragAndDropHandler.this.mWrapper.computeVerticalScrollRange();
                int i = bounds.top;
                int height2 = bounds.height();
                int max = (int) Math.max(1.0f, ((float) this.mSmoothScrollPx) * this.mScrollSpeedFactor);
                if (i <= 0 && computeVerticalScrollOffset > 0) {
                    DragAndDropHandler.this.mWrapper.smoothScrollBy(-max, 0);
                } else if (height2 + i >= height && computeVerticalScrollOffset + computeVerticalScrollExtent < computeVerticalScrollRange) {
                    DragAndDropHandler.this.mWrapper.smoothScrollBy(max, 0);
                }
            }
        }

        public void onScroll(@NonNull AbsListView absListView, int i, int i2, int i3) {
            this.mCurrentFirstVisibleItem = i;
            this.mCurrentLastVisibleItem = i + i2;
            this.mPreviousFirstVisibleItem = this.mPreviousFirstVisibleItem == -1 ? this.mCurrentFirstVisibleItem : this.mPreviousFirstVisibleItem;
            this.mPreviousLastVisibleItem = this.mPreviousLastVisibleItem == -1 ? this.mCurrentLastVisibleItem : this.mPreviousLastVisibleItem;
            if (DragAndDropHandler.this.mHoverDrawable != null) {
                if ($assertionsDisabled || DragAndDropHandler.this.mMobileView != null) {
                    DragAndDropHandler.this.mHoverDrawable.onScroll(DragAndDropHandler.this.mMobileView.getY());
                } else {
                    throw new AssertionError();
                }
            }
            if (!DragAndDropHandler.this.mIsSettlingHoverDrawable) {
                checkAndHandleFirstVisibleCellChange();
                checkAndHandleLastVisibleCellChange();
            }
            this.mPreviousFirstVisibleItem = this.mCurrentFirstVisibleItem;
            this.mPreviousLastVisibleItem = this.mCurrentLastVisibleItem;
        }

        public void onScrollStateChanged(@NonNull AbsListView absListView, int i) {
            if (i == 0 && DragAndDropHandler.this.mHoverDrawable != null) {
                handleMobileCellScroll();
            }
        }

        void setScrollSpeed(float f) {
            this.mScrollSpeedFactor = f;
        }
    }

    class SettleHoverDrawableAnimatorListener extends AnimatorListenerAdapter implements AnimatorUpdateListener {
        @NonNull
        private final HoverDrawable mAnimatingHoverDrawable;
        @NonNull
        private final View mAnimatingMobileView;

        private SettleHoverDrawableAnimatorListener(HoverDrawable hoverDrawable, @NonNull View view) {
            this.mAnimatingHoverDrawable = hoverDrawable;
            this.mAnimatingMobileView = view;
        }

        public void onAnimationEnd(Animator animator) {
            this.mAnimatingMobileView.setVisibility(0);
            DragAndDropHandler.this.mHoverDrawable = null;
            DragAndDropHandler.this.mMobileView = null;
            DragAndDropHandler.this.mMobileItemId = -1;
            DragAndDropHandler.this.mOriginalMobileItemPosition = -1;
            DragAndDropHandler.this.mIsSettlingHoverDrawable = false;
        }

        public void onAnimationStart(Animator animator) {
            DragAndDropHandler.this.mIsSettlingHoverDrawable = true;
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            this.mAnimatingHoverDrawable.setTop(((Integer) valueAnimator.getAnimatedValue()).intValue());
            DragAndDropHandler.this.mWrapper.getListView().postInvalidate();
        }
    }

    public DragAndDropHandler(@NonNull DynamicListView dynamicListView) {
        this(new DynamicListViewWrapper(dynamicListView));
    }

    public DragAndDropHandler(@NonNull DragAndDropListViewWrapper dragAndDropListViewWrapper) {
        this.mLastMotionEventY = -1.0f;
        this.mOriginalMobileItemPosition = -1;
        this.mWrapper = dragAndDropListViewWrapper;
        if (this.mWrapper.getAdapter() != null) {
            setAdapterInternal(this.mWrapper.getAdapter());
        }
        this.mScrollHandler = new ScrollHandler();
        this.mWrapper.setOnScrollListener(this.mScrollHandler);
        this.mDraggableManager = new DefaultDraggableManager();
        if (VERSION.SDK_INT <= 19) {
            this.mSwitchViewAnimator = new KitKatSwitchViewAnimator();
        } else {
            this.mSwitchViewAnimator = new LSwitchViewAnimator();
        }
        this.mMobileItemId = -1;
        this.mSlop = ViewConfiguration.get(dragAndDropListViewWrapper.getListView().getContext()).getScaledTouchSlop();
    }

    private int getPositionForId(long j) {
        View viewForId = getViewForId(j);
        return viewForId == null ? -1 : this.mWrapper.getPositionForView(viewForId);
    }

    @Nullable
    private View getViewForId(long j) {
        View view = null;
        ListAdapter listAdapter = this.mAdapter;
        if (!(j == -1 || listAdapter == null)) {
            int firstVisiblePosition = this.mWrapper.getFirstVisiblePosition();
            for (int i = 0; i < this.mWrapper.getChildCount() && r0 == null; i++) {
                int i2 = firstVisiblePosition + i;
                if (i2 - this.mWrapper.getHeaderViewsCount() >= 0 && listAdapter.getItemId(i2 - this.mWrapper.getHeaderViewsCount()) == j) {
                    view = this.mWrapper.getChildAt(i);
                }
            }
        }
        return view;
    }

    private boolean handleCancelEvent() {
        return handleUpEvent();
    }

    private boolean handleDownEvent(@NonNull MotionEvent motionEvent) {
        this.mDownX = motionEvent.getRawX();
        this.mDownY = motionEvent.getRawY();
        return true;
    }

    private boolean handleMoveEvent(@NonNull MotionEvent motionEvent) {
        float rawX = motionEvent.getRawX();
        float f = this.mDownX;
        float rawY = motionEvent.getRawY() - this.mDownY;
        if (this.mHoverDrawable == null && Math.abs(rawY) > ((float) this.mSlop) && Math.abs(rawY) > Math.abs(rawX - f)) {
            int pointToPosition = this.mWrapper.pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY());
            if (pointToPosition != -1) {
                View childAt = this.mWrapper.getChildAt(pointToPosition - this.mWrapper.getFirstVisiblePosition());
                if (!$assertionsDisabled && childAt == null) {
                    throw new AssertionError();
                } else if (this.mDraggableManager.isDraggable(childAt, pointToPosition - this.mWrapper.getHeaderViewsCount(), motionEvent.getX() - childAt.getX(), motionEvent.getY() - childAt.getY())) {
                    startDragging(pointToPosition - this.mWrapper.getHeaderViewsCount());
                    return true;
                }
            }
        } else if (this.mHoverDrawable != null) {
            this.mHoverDrawable.handleMoveEvent(motionEvent);
            switchIfNecessary();
            this.mWrapper.getListView().invalidate();
            return true;
        }
        return false;
    }

    private boolean handleUpEvent() {
        if (this.mMobileView == null) {
            return false;
        }
        if ($assertionsDisabled || this.mHoverDrawable != null) {
            ValueAnimator ofInt = ValueAnimator.ofInt(new int[]{this.mHoverDrawable.getTop(), (int) this.mMobileView.getY()});
            Object settleHoverDrawableAnimatorListener = new SettleHoverDrawableAnimatorListener(this.mHoverDrawable, this.mMobileView);
            ofInt.addUpdateListener(settleHoverDrawableAnimatorListener);
            ofInt.addListener(settleHoverDrawableAnimatorListener);
            ofInt.start();
            int positionForId = getPositionForId(this.mMobileItemId) - this.mWrapper.getHeaderViewsCount();
            if (!(this.mOriginalMobileItemPosition == positionForId || this.mOnItemMovedListener == null)) {
                this.mOnItemMovedListener.onItemMoved(this.mOriginalMobileItemPosition, positionForId);
            }
            return true;
        }
        throw new AssertionError();
    }

    private void setAdapterInternal(@NonNull ListAdapter listAdapter) {
        if (listAdapter instanceof WrapperListAdapter) {
            listAdapter = ((WrapperListAdapter) listAdapter).getWrappedAdapter();
        }
        if (!listAdapter.hasStableIds()) {
            throw new IllegalStateException("Adapter doesn't have stable ids! Make sure your adapter has stable ids, and override hasStableIds() to return true.");
        } else if (listAdapter instanceof Swappable) {
            this.mAdapter = listAdapter;
        } else {
            throw new IllegalArgumentException("Adapter should implement Swappable!");
        }
    }

    private void switchIfNecessary() {
        long j = -1;
        if (this.mHoverDrawable != null && this.mAdapter != null) {
            int positionForId = getPositionForId(this.mMobileItemId);
            long itemId = (positionForId + -1) - this.mWrapper.getHeaderViewsCount() >= 0 ? this.mAdapter.getItemId((positionForId - 1) - this.mWrapper.getHeaderViewsCount()) : -1;
            if ((positionForId + 1) - this.mWrapper.getHeaderViewsCount() < this.mAdapter.getCount()) {
                j = this.mAdapter.getItemId((positionForId + 1) - this.mWrapper.getHeaderViewsCount());
            }
            if (this.mHoverDrawable.isMovingUpwards()) {
                j = itemId;
            }
            View viewForId = getViewForId(j);
            int deltaY = this.mHoverDrawable.getDeltaY();
            if (viewForId != null && Math.abs(deltaY) > this.mHoverDrawable.getIntrinsicHeight()) {
                switchViews(viewForId, j, (float) ((deltaY < 0 ? -1 : 1) * this.mHoverDrawable.getIntrinsicHeight()));
            }
            this.mScrollHandler.handleMobileCellScroll();
            this.mWrapper.getListView().invalidate();
        }
    }

    private void switchViews(View view, long j, float f) {
        if (!$assertionsDisabled && this.mHoverDrawable == null) {
            throw new AssertionError();
        } else if (!$assertionsDisabled && this.mAdapter == null) {
            throw new AssertionError();
        } else if ($assertionsDisabled || this.mMobileView != null) {
            ((Swappable) this.mAdapter).swapItems(this.mWrapper.getPositionForView(view) - this.mWrapper.getHeaderViewsCount(), this.mWrapper.getPositionForView(this.mMobileView) - this.mWrapper.getHeaderViewsCount());
            ((BaseAdapter) this.mAdapter).notifyDataSetChanged();
            this.mHoverDrawable.shift(view.getHeight());
            this.mSwitchViewAnimator.animateSwitchView(j, f);
        } else {
            throw new AssertionError();
        }
    }

    public void dispatchDraw(@NonNull Canvas canvas) {
        if (this.mHoverDrawable != null) {
            this.mHoverDrawable.draw(canvas);
        }
    }

    public boolean isInteracting() {
        return this.mMobileItemId != -1;
    }

    public boolean onTouchEvent(@NonNull MotionEvent motionEvent) {
        if (!this.mIsSettlingHoverDrawable) {
            boolean handleUpEvent;
            switch (motionEvent.getAction() & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) {
                case 0:
                    this.mLastMotionEventY = motionEvent.getY();
                    return handleDownEvent(motionEvent);
                case 1:
                    handleUpEvent = handleUpEvent();
                    this.mLastMotionEventY = -1.0f;
                    return handleUpEvent;
                case 2:
                    this.mLastMotionEventY = motionEvent.getY();
                    return handleMoveEvent(motionEvent);
                case 3:
                    handleUpEvent = handleCancelEvent();
                    this.mLastMotionEventY = -1.0f;
                    return handleUpEvent;
            }
        }
        return false;
    }

    public void setAdapter(@NonNull ListAdapter listAdapter) {
        setAdapterInternal(listAdapter);
    }

    public void setDraggableManager(@NonNull DraggableManager draggableManager) {
        this.mDraggableManager = draggableManager;
    }

    public void setOnItemMovedListener(@Nullable OnItemMovedListener onItemMovedListener) {
        this.mOnItemMovedListener = onItemMovedListener;
    }

    public void setScrollSpeed(float f) {
        this.mScrollHandler.setScrollSpeed(f);
    }

    public void startDragging(int i) {
        if (this.mMobileItemId == -1) {
            if (this.mLastMotionEventY < 0.0f) {
                throw new IllegalStateException("User must be touching the DynamicListView!");
            } else if (this.mAdapter == null) {
                throw new IllegalStateException("This DynamicListView has no adapter set!");
            } else if (i >= 0 && i < this.mAdapter.getCount()) {
                this.mMobileView = this.mWrapper.getChildAt((i - this.mWrapper.getFirstVisiblePosition()) + this.mWrapper.getHeaderViewsCount());
                if (this.mMobileView != null) {
                    this.mOriginalMobileItemPosition = i;
                    this.mMobileItemId = this.mAdapter.getItemId(i);
                    this.mHoverDrawable = new HoverDrawable(this.mMobileView, this.mLastMotionEventY);
                    this.mMobileView.setVisibility(4);
                }
            }
        }
    }
}
