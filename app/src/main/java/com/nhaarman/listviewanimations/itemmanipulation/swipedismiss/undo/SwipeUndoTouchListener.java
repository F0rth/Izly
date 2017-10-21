package com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissTouchListener;
import com.nhaarman.listviewanimations.util.AdapterViewUtil;
import com.nhaarman.listviewanimations.util.ListViewWrapper;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SwipeUndoTouchListener extends SwipeDismissTouchListener {
    private static final String ALPHA = "alpha";
    private static final String TRANSLATION_X = "translationX";
    @NonNull
    private final UndoCallback mCallback;
    @NonNull
    private final List<Integer> mDismissedPositions = new LinkedList();
    @NonNull
    private final Collection<View> mDismissedViews = new LinkedList();
    @NonNull
    private final Collection<Integer> mUndoPositions = new LinkedList();
    @NonNull
    private final Map<Integer, View> mUndoViews = new HashMap();

    class UndoAnimatorListener extends AnimatorListenerAdapter {
        @NonNull
        private final View mUndoView;

        UndoAnimatorListener(View view) {
            this.mUndoView = view;
        }

        public void onAnimationEnd(@NonNull Animator animator) {
            this.mUndoView.setVisibility(8);
            SwipeUndoTouchListener.this.finalizeDismiss();
        }
    }

    public SwipeUndoTouchListener(@NonNull ListViewWrapper listViewWrapper, @NonNull UndoCallback undoCallback) {
        super(listViewWrapper, undoCallback);
        this.mCallback = undoCallback;
    }

    private void hideUndoView(@NonNull View view) {
        this.mCallback.getPrimaryView(view).setVisibility(0);
        this.mCallback.getUndoView(view).setVisibility(8);
    }

    private void showUndoView(@NonNull View view) {
        this.mCallback.getPrimaryView(view).setVisibility(8);
        Object undoView = this.mCallback.getUndoView(view);
        undoView.setVisibility(0);
        ObjectAnimator.ofFloat(undoView, ALPHA, 0.0f, 1.0f).start();
    }

    protected void afterCancelSwipe(@NonNull View view, int i) {
        finalizeDismiss();
    }

    protected void afterViewFling(@NonNull View view, int i) {
        if (this.mUndoPositions.contains(Integer.valueOf(i))) {
            this.mUndoPositions.remove(Integer.valueOf(i));
            this.mUndoViews.remove(Integer.valueOf(i));
            performDismiss(view, i);
            hideUndoView(view);
            return;
        }
        this.mUndoPositions.add(Integer.valueOf(i));
        this.mUndoViews.put(Integer.valueOf(i), view);
        this.mCallback.onUndoShown(view, i);
        showUndoView(view);
        restoreViewPresentation(view);
    }

    public void dimissPending() {
        for (Integer intValue : this.mUndoPositions) {
            int intValue2 = intValue.intValue();
            performDismiss((View) this.mUndoViews.get(Integer.valueOf(intValue2)), intValue2);
        }
    }

    protected void directDismiss(int i) {
        this.mDismissedPositions.add(Integer.valueOf(i));
        finalizeDismiss();
    }

    protected void finalizeDismiss() {
        if (getActiveDismissCount() == 0 && getActiveSwipeCount() == 0) {
            restoreViewPresentations(this.mDismissedViews);
            notifyCallback(this.mDismissedPositions);
            Collection processDeletions = Util.processDeletions(this.mUndoPositions, this.mDismissedPositions);
            this.mUndoPositions.clear();
            this.mUndoPositions.addAll(processDeletions);
            this.mDismissedViews.clear();
            this.mDismissedPositions.clear();
        }
    }

    public boolean hasPendingItems() {
        return !this.mUndoPositions.isEmpty();
    }

    protected void performDismiss(@NonNull View view, int i) {
        super.performDismiss(view, i);
        this.mDismissedViews.add(view);
        this.mDismissedPositions.add(Integer.valueOf(i));
        this.mCallback.onDismiss(view, i);
    }

    protected void restoreViewPresentation(@NonNull View view) {
        super.restoreViewPresentation(view);
        LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = 0;
        view.setLayoutParams(layoutParams);
    }

    public void undo(@NonNull View view) {
        int positionForView = AdapterViewUtil.getPositionForView(getListViewWrapper(), view);
        this.mUndoPositions.remove(Integer.valueOf(positionForView));
        Object primaryView = this.mCallback.getPrimaryView(view);
        Object undoView = this.mCallback.getUndoView(view);
        primaryView.setVisibility(0);
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(undoView, ALPHA, 1.0f, 0.0f);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(primaryView, ALPHA, 0.0f, 1.0f);
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(primaryView, TRANSLATION_X, (float) primaryView.getWidth(), 0.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat, ofFloat2, ofFloat3);
        animatorSet.addListener(new UndoAnimatorListener(undoView));
        animatorSet.start();
        this.mCallback.onUndo(view, positionForView);
    }

    protected boolean willLeaveDataSetOnFling(@NonNull View view, int i) {
        return this.mUndoPositions.contains(Integer.valueOf(i));
    }
}
