package com.nhaarman.listviewanimations.itemmanipulation.animateaddition;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import com.nhaarman.listviewanimations.BaseAdapterDecorator;
import com.nhaarman.listviewanimations.util.AbsListViewWrapper;
import com.nhaarman.listviewanimations.util.Insertable;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.view.ViewHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class AnimateAdditionAdapter<T> extends BaseAdapterDecorator {
    private static final String ALPHA = "alpha";
    private static final long DEFAULT_INSERTION_ANIMATION_MS = 300;
    private static final long DEFAULT_SCROLLDOWN_ANIMATION_MS = 300;
    @NonNull
    private final InsertQueue<T> mInsertQueue;
    @NonNull
    private final Insertable<T> mInsertable;
    private long mInsertionAnimationDurationMs = 300;
    private long mScrolldownAnimationDurationMs = 300;
    private boolean mShouldAnimateDown = true;

    public AnimateAdditionAdapter(@NonNull BaseAdapter baseAdapter) {
        super(baseAdapter);
        BaseAdapter rootAdapter = getRootAdapter();
        if (rootAdapter instanceof Insertable) {
            this.mInsertable = (Insertable) rootAdapter;
            this.mInsertQueue = new InsertQueue(this.mInsertable);
            return;
        }
        throw new IllegalArgumentException("BaseAdapter should implement Insertable!");
    }

    private boolean childrenFillAbsListView() {
        if (getListViewWrapper() == null) {
            throw new IllegalStateException("Call setListView on this AnimateAdditionAdapter first!");
        }
        int i = 0;
        for (int i2 = 0; i2 < getListViewWrapper().getCount(); i2++) {
            View childAt = getListViewWrapper().getChildAt(i2);
            if (childAt != null) {
                i += childAt.getHeight();
            }
        }
        return getListViewWrapper().getListView().getHeight() <= i;
    }

    @NonNull
    protected Animator[] getAdditionalAnimators(@NonNull View view, @NonNull ViewGroup viewGroup) {
        return new Animator[0];
    }

    @NonNull
    public View getView(int i, @Nullable View view, @NonNull ViewGroup viewGroup) {
        View view2 = super.getView(i, view, viewGroup);
        if (this.mInsertQueue.getActiveIndexes().contains(Integer.valueOf(i))) {
            view2.measure(MeasureSpec.makeMeasureSpec(-1, Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(-2, 0));
            ValueAnimator ofInt = ValueAnimator.ofInt(new int[]{1, view2.getMeasuredHeight()});
            ofInt.addUpdateListener(new HeightUpdater(view2));
            Object additionalAnimators = getAdditionalAnimators(view2, viewGroup);
            Object obj = new Animator[(additionalAnimators.length + 1)];
            obj[0] = ofInt;
            System.arraycopy(additionalAnimators, 0, obj, 1, additionalAnimators.length);
            new AnimatorSet().playTogether(obj);
            ViewHelper.setAlpha(view2, 0.0f);
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view2, ALPHA, new float[]{0.0f, 1.0f});
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playSequentially(new Animator[]{r1, ofFloat});
            animatorSet.setDuration(this.mInsertionAnimationDurationMs);
            animatorSet.addListener(new ExpandAnimationListener(this, i));
            animatorSet.start();
        }
        return view2;
    }

    public void insert(int i, @NonNull T t) {
        insert(new Pair(Integer.valueOf(i), t));
    }

    public void insert(int i, @NonNull T... tArr) {
        Pair[] pairArr = new Pair[tArr.length];
        for (int i2 = 0; i2 < tArr.length; i2++) {
            pairArr[i2] = new Pair(Integer.valueOf(i + i2), tArr[i2]);
        }
        insert(pairArr);
    }

    public void insert(@NonNull Iterable<Pair<Integer, T>> iterable) {
        int i = 0;
        if (getListViewWrapper() == null) {
            throw new IllegalStateException("Call setListView on this AnimateAdditionAdapter!");
        }
        int intValue;
        View view;
        Collection arrayList = new ArrayList();
        Collection<Integer> arrayList2 = new ArrayList();
        Collection<Integer> arrayList3 = new ArrayList();
        int i2 = 0;
        int i3 = 0;
        for (Pair pair : iterable) {
            int i4;
            if (getListViewWrapper().getFirstVisiblePosition() > ((Integer) pair.first).intValue()) {
                intValue = ((Integer) pair.first).intValue();
                i4 = intValue;
                for (Integer intValue2 : arrayList2) {
                    i4 = i4 >= intValue2.intValue() ? i4 + 1 : i4;
                }
                this.mInsertable.add(i4, pair.second);
                arrayList2.add(Integer.valueOf(i4));
                if (this.mShouldAnimateDown) {
                    view = getView(((Integer) pair.first).intValue(), null, getListViewWrapper().getListView());
                    view.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
                    intValue = i3 - view.getMeasuredHeight();
                } else {
                    intValue = i3;
                }
                i2++;
                i3 = intValue;
            } else if (getListViewWrapper().getLastVisiblePosition() >= ((Integer) pair.first).intValue() || getListViewWrapper().getLastVisiblePosition() == -1 || !childrenFillAbsListView()) {
                intValue = ((Integer) pair.first).intValue();
                i4 = intValue;
                for (Integer intValue22 : arrayList2) {
                    i4 = i4 >= intValue22.intValue() ? i4 + 1 : i4;
                }
                arrayList.add(new Pair(Integer.valueOf(i4), pair.second));
            } else {
                intValue = ((Integer) pair.first).intValue();
                i4 = intValue;
                for (Integer intValue222 : arrayList2) {
                    i4 = i4 >= intValue222.intValue() ? i4 + 1 : i4;
                }
                for (Integer intValue2222 : arrayList3) {
                    if (i4 >= intValue2222.intValue()) {
                        i4++;
                    }
                }
                arrayList3.add(Integer.valueOf(i4));
                this.mInsertable.add(i4, pair.second);
            }
        }
        if (this.mShouldAnimateDown) {
            ((AbsListView) getListViewWrapper().getListView()).smoothScrollBy(i3, (int) (this.mScrolldownAnimationDurationMs * ((long) i2)));
        }
        this.mInsertQueue.insert(arrayList);
        intValue = getListViewWrapper().getFirstVisiblePosition();
        view = getListViewWrapper().getChildAt(0);
        if (view != null) {
            i = view.getTop();
        }
        ((ListView) getListViewWrapper().getListView()).setSelectionFromTop(intValue + i2, i);
    }

    public void insert(@NonNull Pair<Integer, T>... pairArr) {
        insert(Arrays.asList(pairArr));
    }

    @Deprecated
    public void setAbsListView(@NonNull AbsListView absListView) {
        if (absListView instanceof ListView) {
            setListView((ListView) absListView);
            return;
        }
        throw new IllegalArgumentException("AnimateAdditionAdapter requires a ListView!");
    }

    public void setInsertionAnimationDuration(long j) {
        this.mInsertionAnimationDurationMs = j;
    }

    public void setListView(@NonNull ListView listView) {
        setListViewWrapper(new AbsListViewWrapper(listView));
    }

    public void setScrolldownAnimationDuration(long j) {
        this.mScrolldownAnimationDurationMs = j;
    }

    public void setShouldAnimateDown(boolean z) {
        this.mShouldAnimateDown = z;
    }
}
