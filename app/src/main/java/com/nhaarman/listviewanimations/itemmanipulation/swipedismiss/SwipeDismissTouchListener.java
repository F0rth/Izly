package com.nhaarman.listviewanimations.itemmanipulation.swipedismiss;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import com.nhaarman.listviewanimations.util.AdapterViewUtil;
import com.nhaarman.listviewanimations.util.ListViewWrapper;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SwipeDismissTouchListener extends SwipeTouchListener {
    private int mActiveDismissCount;
    @NonNull
    private final OnDismissCallback mCallback;
    private final long mDismissAnimationTime;
    @NonNull
    private final List<Integer> mDismissedPositions = new LinkedList();
    @NonNull
    private final Collection<View> mDismissedViews = new LinkedList();
    @NonNull
    private final Handler mHandler = new Handler();

    class DismissAnimatorListener extends AnimatorListenerAdapter {
        private DismissAnimatorListener() {
        }

        public void onAnimationEnd(@NonNull Animator animator) {
            SwipeDismissTouchListener.this.mActiveDismissCount = SwipeDismissTouchListener.this.mActiveDismissCount - 1;
            SwipeDismissTouchListener.this.finalizeDismiss();
        }
    }

    static class DismissAnimatorUpdateListener implements AnimatorUpdateListener {
        @NonNull
        private final View mView;

        DismissAnimatorUpdateListener(@NonNull View view) {
            this.mView = view;
        }

        public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
            LayoutParams layoutParams = this.mView.getLayoutParams();
            layoutParams.height = ((Integer) valueAnimator.getAnimatedValue()).intValue();
            this.mView.setLayoutParams(layoutParams);
        }
    }

    class RestoreScrollRunnable implements Runnable {
        private final int mPosition;
        private final int mScrollDistance;

        RestoreScrollRunnable(int i, int i2) {
            this.mScrollDistance = i;
            this.mPosition = i2;
        }

        public void run() {
            SwipeDismissTouchListener.this.getListViewWrapper().smoothScrollBy(-this.mScrollDistance, 1);
            SwipeDismissTouchListener.this.directDismiss(this.mPosition);
        }
    }

    public SwipeDismissTouchListener(@NonNull ListViewWrapper listViewWrapper, @NonNull OnDismissCallback onDismissCallback) {
        super(listViewWrapper);
        this.mCallback = onDismissCallback;
        this.mDismissAnimationTime = (long) listViewWrapper.getListView().getContext().getResources().getInteger(17694720);
    }

    private void dismissAbove(int i) {
        View viewForPosition = AdapterViewUtil.getViewForPosition(getListViewWrapper(), getListViewWrapper().getFirstVisiblePosition());
        if (viewForPosition != null) {
            viewForPosition.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
            int measuredHeight = viewForPosition.getMeasuredHeight();
            getListViewWrapper().smoothScrollBy(measuredHeight, (int) this.mDismissAnimationTime);
            this.mHandler.postDelayed(new RestoreScrollRunnable(measuredHeight, i), this.mDismissAnimationTime);
        }
    }

    public void afterCancelSwipe(@NonNull View view, int i) {
        finalizeDismiss();
    }

    public void afterViewFling(@NonNull View view, int i) {
        performDismiss(view, i);
    }

    public void directDismiss(int i) {
        this.mDismissedPositions.add(Integer.valueOf(i));
        finalizeDismiss();
    }

    public void dismiss(int i) {
        fling(i);
    }

    public void finalizeDismiss() {
        if (this.mActiveDismissCount == 0 && getActiveSwipeCount() == 0) {
            restoreViewPresentations(this.mDismissedViews);
            notifyCallback(this.mDismissedPositions);
            this.mDismissedViews.clear();
            this.mDismissedPositions.clear();
        }
    }

    public void fling(int i) {
        int firstVisiblePosition = getListViewWrapper().getFirstVisiblePosition();
        int lastVisiblePosition = getListViewWrapper().getLastVisiblePosition();
        if (firstVisiblePosition <= i && i <= lastVisiblePosition) {
            super.fling(i);
        } else if (i > lastVisiblePosition) {
            directDismiss(i);
        } else {
            dismissAbove(i);
        }
    }

    protected int getActiveDismissCount() {
        return this.mActiveDismissCount;
    }

    public long getDismissAnimationTime() {
        return this.mDismissAnimationTime;
    }

    protected void notifyCallback(@NonNull List<Integer> list) {
        if (!list.isEmpty()) {
            Collections.sort(list, Collections.reverseOrder());
            int[] iArr = new int[list.size()];
            int i = 0;
            for (Integer intValue : list) {
                iArr[i] = intValue.intValue();
                i++;
            }
            this.mCallback.onDismiss(getListViewWrapper().getListView(), iArr);
        }
    }

    public void performDismiss(@NonNull View view, int i) {
        this.mDismissedViews.add(view);
        this.mDismissedPositions.add(Integer.valueOf(i));
        ValueAnimator duration = ValueAnimator.ofInt(view.getHeight(), 1).setDuration(this.mDismissAnimationTime);
        duration.addUpdateListener(new DismissAnimatorUpdateListener(view));
        duration.addListener(new DismissAnimatorListener());
        duration.start();
        this.mActiveDismissCount++;
    }

    public void restoreViewPresentation(@NonNull View view) {
        super.restoreViewPresentation(view);
        LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = 0;
        view.setLayoutParams(layoutParams);
    }

    protected void restoreViewPresentations(@NonNull Iterable<View> iterable) {
        for (View restoreViewPresentation : iterable) {
            restoreViewPresentation(restoreViewPresentation);
        }
    }

    public boolean willLeaveDataSetOnFling(@NonNull View view, int i) {
        return true;
    }
}
