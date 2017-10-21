package com.nhaarman.listviewanimations.itemmanipulation.swipedismiss;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import com.nhaarman.listviewanimations.itemmanipulation.TouchEventHandler;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.GripView;
import com.nhaarman.listviewanimations.util.AdapterViewUtil;
import com.nhaarman.listviewanimations.util.ListViewWrapper;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

public abstract class SwipeTouchListener implements OnTouchListener, TouchEventHandler {
    private static final String ALPHA = "alpha";
    private static final int MIN_FLING_VELOCITY_FACTOR = 16;
    private static final String TRANSLATION_X = "translationX";
    private int mActiveSwipeCount;
    private final long mAnimationTime;
    private boolean mCanDismissCurrent;
    private int mCurrentPosition = -1;
    @Nullable
    private View mCurrentView;
    @Nullable
    private DismissableManager mDismissableManager;
    private float mDownX;
    private float mDownY;
    @NonNull
    private final ListViewWrapper mListViewWrapper;
    private final int mMaxFlingVelocity;
    private final int mMinFlingVelocity;
    private float mMinimumAlpha;
    private boolean mParentIsHorizontalScrollContainer;
    private final int mSlop;
    private boolean mSwipeEnabled = true;
    private boolean mSwiping;
    @Nullable
    private View mSwipingView;
    private int mTouchChildResId;
    @Nullable
    private VelocityTracker mVelocityTracker;
    private int mViewWidth = 1;
    private int mVirtualListCount = -1;

    class FlingAnimatorListener extends AnimatorListenerAdapter {
        private final int mPosition;
        @NonNull
        private final View mView;

        private FlingAnimatorListener(View view, @NonNull int i) {
            this.mView = view;
            this.mPosition = i;
        }

        public void onAnimationEnd(@NonNull Animator animator) {
            SwipeTouchListener.this.mActiveSwipeCount = SwipeTouchListener.this.mActiveSwipeCount - 1;
            SwipeTouchListener.this.afterViewFling(this.mView, this.mPosition);
        }
    }

    class RestoreAnimatorListener extends AnimatorListenerAdapter {
        private final int mPosition;
        @NonNull
        private final View mView;

        private RestoreAnimatorListener(View view, @NonNull int i) {
            this.mView = view;
            this.mPosition = i;
        }

        public void onAnimationEnd(@NonNull Animator animator) {
            SwipeTouchListener.this.mActiveSwipeCount = SwipeTouchListener.this.mActiveSwipeCount - 1;
            SwipeTouchListener.this.afterCancelSwipe(this.mView, this.mPosition);
        }
    }

    protected SwipeTouchListener(@NonNull ListViewWrapper listViewWrapper) {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(listViewWrapper.getListView().getContext());
        this.mSlop = viewConfiguration.getScaledTouchSlop();
        this.mMinFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity() * 16;
        this.mMaxFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mAnimationTime = (long) listViewWrapper.getListView().getContext().getResources().getInteger(17694720);
        this.mListViewWrapper = listViewWrapper;
    }

    private void disableHorizontalScrollContainerIfNecessary(@NonNull MotionEvent motionEvent, @NonNull View view) {
        if (this.mParentIsHorizontalScrollContainer) {
            this.mListViewWrapper.getListView().requestDisallowInterceptTouchEvent(true);
        } else if (this.mTouchChildResId != 0) {
            this.mParentIsHorizontalScrollContainer = false;
            View findViewById = view.findViewById(this.mTouchChildResId);
            if (findViewById != null && getChildViewRect(this.mListViewWrapper.getListView(), findViewById).contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                this.mListViewWrapper.getListView().requestDisallowInterceptTouchEvent(true);
            }
        }
    }

    @Nullable
    private View findDownView(@NonNull MotionEvent motionEvent) {
        Rect rect = new Rect();
        int childCount = this.mListViewWrapper.getChildCount();
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        View view = null;
        int i = 0;
        while (i < childCount && view == null) {
            View childAt = this.mListViewWrapper.getChildAt(i);
            if (childAt != null) {
                childAt.getHitRect(rect);
                if (rect.contains(x, y)) {
                    i++;
                    view = childAt;
                }
            }
            childAt = view;
            i++;
            view = childAt;
        }
        return view;
    }

    private void flingCurrentView(boolean z) {
        if (this.mCurrentView != null) {
            flingView(this.mCurrentView, this.mCurrentPosition, z);
        }
    }

    private void flingView(@NonNull View view, int i, boolean z) {
        if (this.mViewWidth < 2) {
            this.mViewWidth = this.mListViewWrapper.getListView().getWidth();
        }
        Object swipeView = getSwipeView(view);
        float f = z ? (float) this.mViewWidth : (float) (-this.mViewWidth);
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(swipeView, TRANSLATION_X, f);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(swipeView, ALPHA, 0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat, ofFloat2);
        animatorSet.setDuration(this.mAnimationTime);
        animatorSet.addListener(new FlingAnimatorListener(view, i));
        animatorSet.start();
    }

    private static Rect getChildViewRect(View view, View view2) {
        Rect rect = new Rect(view2.getLeft(), view2.getTop(), view2.getRight(), view2.getBottom());
        if (!view.equals(view2)) {
            while (true) {
                View view3 = (ViewGroup) view2.getParent();
                if (view3.equals(view)) {
                    break;
                }
                rect.offset(view3.getLeft(), view3.getTop());
                view2 = view3;
            }
        }
        return rect;
    }

    private boolean handleCancelEvent() {
        if (!(this.mVelocityTracker == null || this.mCurrentView == null)) {
            if (this.mCurrentPosition != -1 && this.mSwiping) {
                onCancelSwipe(this.mCurrentView, this.mCurrentPosition);
                restoreCurrentViewTranslation();
            }
            reset();
        }
        return false;
    }

    private boolean handleDownEvent(@Nullable View view, @NonNull MotionEvent motionEvent) {
        if (this.mSwipeEnabled) {
            View findDownView = findDownView(motionEvent);
            if (findDownView != null) {
                int positionForView = AdapterViewUtil.getPositionForView(this.mListViewWrapper, findDownView);
                this.mCanDismissCurrent = isDismissable(positionForView);
                if (this.mCurrentPosition != positionForView && positionForView < this.mVirtualListCount) {
                    if (view != null) {
                        view.onTouchEvent(motionEvent);
                    }
                    disableHorizontalScrollContainerIfNecessary(motionEvent, findDownView);
                    this.mDownX = motionEvent.getX();
                    this.mDownY = motionEvent.getY();
                    this.mCurrentView = findDownView;
                    this.mSwipingView = getSwipeView(findDownView);
                    this.mCurrentPosition = positionForView;
                    this.mVelocityTracker = VelocityTracker.obtain();
                    this.mVelocityTracker.addMovement(motionEvent);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean handleMoveEvent(@Nullable View view, @NonNull MotionEvent motionEvent) {
        if (this.mVelocityTracker == null || this.mCurrentView == null) {
            return false;
        }
        this.mVelocityTracker.addMovement(motionEvent);
        float x = motionEvent.getX() - this.mDownX;
        float y = motionEvent.getY();
        float f = this.mDownY;
        if (Math.abs(x) > ((float) this.mSlop) && Math.abs(x) > Math.abs(y - f)) {
            if (!this.mSwiping) {
                this.mActiveSwipeCount++;
                onStartSwipe(this.mCurrentView, this.mCurrentPosition);
            }
            this.mSwiping = true;
            this.mListViewWrapper.getListView().requestDisallowInterceptTouchEvent(true);
            if (view != null) {
                MotionEvent obtain = MotionEvent.obtain(motionEvent);
                obtain.setAction((motionEvent.getActionIndex() << 8) | 3);
                view.onTouchEvent(obtain);
                obtain.recycle();
            }
        }
        if (!this.mSwiping) {
            return false;
        }
        if (this.mCanDismissCurrent) {
            ViewHelper.setTranslationX(this.mSwipingView, x);
            ViewHelper.setAlpha(this.mSwipingView, Math.max(this.mMinimumAlpha, Math.min(1.0f, 1.0f - ((Math.abs(x) * GripView.DEFAULT_DOT_SIZE_RADIUS_DP) / ((float) this.mViewWidth)))));
            return true;
        }
        ViewHelper.setTranslationX(this.mSwipingView, x * 0.1f);
        return true;
    }

    private boolean handleUpEvent(@NonNull MotionEvent motionEvent) {
        boolean z = true;
        if (!(this.mVelocityTracker == null || this.mCurrentView == null)) {
            if (this.mSwiping) {
                boolean z2;
                if (this.mCanDismissCurrent) {
                    float x = motionEvent.getX() - this.mDownX;
                    this.mVelocityTracker.addMovement(motionEvent);
                    this.mVelocityTracker.computeCurrentVelocity(1000);
                    float abs = Math.abs(this.mVelocityTracker.getXVelocity());
                    float abs2 = Math.abs(this.mVelocityTracker.getYVelocity());
                    if (Math.abs(x) > ((float) (this.mViewWidth / 2))) {
                        if (x > 0.0f) {
                            z2 = true;
                        } else {
                            z2 = true;
                            z = false;
                        }
                    } else if (((float) this.mMinFlingVelocity) <= abs && abs <= ((float) this.mMaxFlingVelocity) && abs2 < abs) {
                        if (this.mVelocityTracker.getXVelocity() > 0.0f) {
                            z2 = true;
                        } else {
                            z2 = true;
                            z = false;
                        }
                    }
                    if (z2) {
                        onCancelSwipe(this.mCurrentView, this.mCurrentPosition);
                        restoreCurrentViewTranslation();
                    } else {
                        beforeViewFling(this.mCurrentView, this.mCurrentPosition);
                        if (willLeaveDataSetOnFling(this.mCurrentView, this.mCurrentPosition)) {
                            this.mVirtualListCount--;
                        }
                        flingCurrentView(z);
                    }
                }
                z = false;
                z2 = false;
                if (z2) {
                    onCancelSwipe(this.mCurrentView, this.mCurrentPosition);
                    restoreCurrentViewTranslation();
                } else {
                    beforeViewFling(this.mCurrentView, this.mCurrentPosition);
                    if (willLeaveDataSetOnFling(this.mCurrentView, this.mCurrentPosition)) {
                        this.mVirtualListCount--;
                    }
                    flingCurrentView(z);
                }
            }
            reset();
        }
        return false;
    }

    private boolean isDismissable(int i) {
        if (this.mListViewWrapper.getAdapter() == null) {
            return false;
        }
        if (this.mDismissableManager == null) {
            return true;
        }
        return this.mDismissableManager.isDismissable(this.mListViewWrapper.getAdapter().getItemId(i), i);
    }

    private void reset() {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
        }
        this.mVelocityTracker = null;
        this.mDownX = 0.0f;
        this.mDownY = 0.0f;
        this.mCurrentView = null;
        this.mSwipingView = null;
        this.mCurrentPosition = -1;
        this.mSwiping = false;
        this.mCanDismissCurrent = false;
    }

    private void restoreCurrentViewTranslation() {
        if (this.mCurrentView != null) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mSwipingView, TRANSLATION_X, 0.0f);
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.mSwipingView, ALPHA, 1.0f);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(ofFloat, ofFloat2);
            animatorSet.setDuration(this.mAnimationTime);
            animatorSet.addListener(new RestoreAnimatorListener(this.mCurrentView, this.mCurrentPosition));
            animatorSet.start();
        }
    }

    public void afterCancelSwipe(@NonNull View view, int i) {
    }

    public abstract void afterViewFling(@NonNull View view, int i);

    protected void beforeViewFling(@NonNull View view, int i) {
    }

    public void disableSwipe() {
        this.mSwipeEnabled = false;
    }

    public void enableSwipe() {
        this.mSwipeEnabled = true;
    }

    public void fling(int i) {
        int firstVisiblePosition = this.mListViewWrapper.getFirstVisiblePosition();
        int lastVisiblePosition = this.mListViewWrapper.getLastVisiblePosition();
        if (i < firstVisiblePosition || i > lastVisiblePosition) {
            throw new IllegalArgumentException("View for position " + i + " not visible!");
        }
        View viewForPosition = AdapterViewUtil.getViewForPosition(this.mListViewWrapper, i);
        if (viewForPosition == null) {
            throw new IllegalStateException("No view found for position " + i);
        }
        flingView(viewForPosition, i, true);
        this.mActiveSwipeCount++;
        this.mVirtualListCount--;
    }

    protected int getActiveSwipeCount() {
        return this.mActiveSwipeCount;
    }

    @NonNull
    public ListViewWrapper getListViewWrapper() {
        return this.mListViewWrapper;
    }

    @NonNull
    protected View getSwipeView(@NonNull View view) {
        return view;
    }

    public boolean isInteracting() {
        return this.mSwiping;
    }

    public boolean isSwiping() {
        return this.mSwiping;
    }

    public void notifyDataSetChanged() {
        if (this.mListViewWrapper.getAdapter() != null) {
            this.mVirtualListCount = this.mListViewWrapper.getCount() - this.mListViewWrapper.getHeaderViewsCount();
        }
    }

    protected void onCancelSwipe(@NonNull View view, int i) {
    }

    protected void onStartSwipe(@NonNull View view, int i) {
    }

    public boolean onTouch(@Nullable View view, @NonNull MotionEvent motionEvent) {
        if (this.mListViewWrapper.getAdapter() == null) {
            return false;
        }
        if (this.mVirtualListCount == -1 || this.mActiveSwipeCount == 0) {
            this.mVirtualListCount = this.mListViewWrapper.getCount() - this.mListViewWrapper.getHeaderViewsCount();
        }
        if (this.mViewWidth < 2) {
            this.mViewWidth = this.mListViewWrapper.getListView().getWidth();
        }
        switch (motionEvent.getActionMasked()) {
            case 0:
                return handleDownEvent(view, motionEvent);
            case 1:
                return handleUpEvent(motionEvent);
            case 2:
                return handleMoveEvent(view, motionEvent);
            case 3:
                return handleCancelEvent();
            default:
                return false;
        }
    }

    public boolean onTouchEvent(@NonNull MotionEvent motionEvent) {
        return onTouch(null, motionEvent);
    }

    public void restoreViewPresentation(@NonNull View view) {
        View swipeView = getSwipeView(view);
        ViewHelper.setAlpha(swipeView, 1.0f);
        ViewHelper.setTranslationX(swipeView, 0.0f);
    }

    public void setDismissableManager(@Nullable DismissableManager dismissableManager) {
        this.mDismissableManager = dismissableManager;
    }

    public void setMinimumAlpha(float f) {
        this.mMinimumAlpha = f;
    }

    public void setParentIsHorizontalScrollContainer() {
        this.mParentIsHorizontalScrollContainer = true;
        this.mTouchChildResId = 0;
    }

    public void setTouchChild(int i) {
        this.mTouchChildResId = i;
        this.mParentIsHorizontalScrollContainer = false;
    }

    public abstract boolean willLeaveDataSetOnFling(@NonNull View view, int i);
}
