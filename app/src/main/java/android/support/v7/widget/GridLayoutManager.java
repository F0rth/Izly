package android.support.v7.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.CollectionItemInfoCompat;
import android.support.v7.widget.LinearLayoutManager.LayoutChunkResult;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.Recycler;
import android.support.v7.widget.RecyclerView.State;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.MarginLayoutParams;
import java.util.Arrays;

public class GridLayoutManager extends LinearLayoutManager {
    private static final boolean DEBUG = false;
    public static final int DEFAULT_SPAN_COUNT = -1;
    private static final String TAG = "GridLayoutManager";
    int[] mCachedBorders;
    final Rect mDecorInsets = new Rect();
    boolean mPendingSpanCountChange = false;
    final SparseIntArray mPreLayoutSpanIndexCache = new SparseIntArray();
    final SparseIntArray mPreLayoutSpanSizeCache = new SparseIntArray();
    View[] mSet;
    int mSpanCount = -1;
    SpanSizeLookup mSpanSizeLookup = new DefaultSpanSizeLookup();

    public static abstract class SpanSizeLookup {
        private boolean mCacheSpanIndices = false;
        final SparseIntArray mSpanIndexCache = new SparseIntArray();

        int findReferenceIndexFromCache(int i) {
            int i2 = 0;
            int size = this.mSpanIndexCache.size() - 1;
            while (i2 <= size) {
                int i3 = (i2 + size) >>> 1;
                if (this.mSpanIndexCache.keyAt(i3) < i) {
                    i2 = i3 + 1;
                } else {
                    size = i3 - 1;
                }
            }
            size = i2 - 1;
            return (size < 0 || size >= this.mSpanIndexCache.size()) ? -1 : this.mSpanIndexCache.keyAt(size);
        }

        int getCachedSpanIndex(int i, int i2) {
            if (!this.mCacheSpanIndices) {
                return getSpanIndex(i, i2);
            }
            int i3 = this.mSpanIndexCache.get(i, -1);
            if (i3 != -1) {
                return i3;
            }
            i3 = getSpanIndex(i, i2);
            this.mSpanIndexCache.put(i, i3);
            return i3;
        }

        public int getSpanGroupIndex(int i, int i2) {
            int spanSize = getSpanSize(i);
            int i3 = 0;
            int i4 = 0;
            int i5 = 0;
            while (i3 < i) {
                int spanSize2 = getSpanSize(i3);
                i4 += spanSize2;
                if (i4 == i2) {
                    i5++;
                    spanSize2 = 0;
                } else if (i4 > i2) {
                    i5++;
                } else {
                    spanSize2 = i4;
                }
                i3++;
                i4 = spanSize2;
            }
            return i4 + spanSize > i2 ? i5 + 1 : i5;
        }

        public int getSpanIndex(int i, int i2) {
            int spanSize = getSpanSize(i);
            if (spanSize == i2) {
                return 0;
            }
            int findReferenceIndexFromCache;
            int i3;
            int spanSize2;
            if (this.mCacheSpanIndices && this.mSpanIndexCache.size() > 0) {
                findReferenceIndexFromCache = findReferenceIndexFromCache(i);
                if (findReferenceIndexFromCache >= 0) {
                    i3 = findReferenceIndexFromCache + 1;
                    findReferenceIndexFromCache = this.mSpanIndexCache.get(findReferenceIndexFromCache) + getSpanSize(findReferenceIndexFromCache);
                    while (i3 < i) {
                        spanSize2 = getSpanSize(i3);
                        findReferenceIndexFromCache += spanSize2;
                        if (findReferenceIndexFromCache == i2) {
                            findReferenceIndexFromCache = 0;
                        } else if (findReferenceIndexFromCache > i2) {
                            findReferenceIndexFromCache = spanSize2;
                        }
                        i3++;
                    }
                    return findReferenceIndexFromCache + spanSize > i2 ? findReferenceIndexFromCache : 0;
                }
            }
            i3 = 0;
            findReferenceIndexFromCache = 0;
            while (i3 < i) {
                spanSize2 = getSpanSize(i3);
                findReferenceIndexFromCache += spanSize2;
                if (findReferenceIndexFromCache == i2) {
                    findReferenceIndexFromCache = 0;
                } else if (findReferenceIndexFromCache > i2) {
                    findReferenceIndexFromCache = spanSize2;
                }
                i3++;
            }
            if (findReferenceIndexFromCache + spanSize > i2) {
            }
        }

        public abstract int getSpanSize(int i);

        public void invalidateSpanIndexCache() {
            this.mSpanIndexCache.clear();
        }

        public boolean isSpanIndexCacheEnabled() {
            return this.mCacheSpanIndices;
        }

        public void setSpanIndexCacheEnabled(boolean z) {
            this.mCacheSpanIndices = z;
        }
    }

    public static final class DefaultSpanSizeLookup extends SpanSizeLookup {
        public final int getSpanIndex(int i, int i2) {
            return i % i2;
        }

        public final int getSpanSize(int i) {
            return 1;
        }
    }

    public static class LayoutParams extends android.support.v7.widget.RecyclerView.LayoutParams {
        public static final int INVALID_SPAN_ID = -1;
        private int mSpanIndex = -1;
        private int mSpanSize = 0;

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(android.support.v7.widget.RecyclerView.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public int getSpanIndex() {
            return this.mSpanIndex;
        }

        public int getSpanSize() {
            return this.mSpanSize;
        }
    }

    public GridLayoutManager(Context context, int i) {
        super(context);
        setSpanCount(i);
    }

    public GridLayoutManager(Context context, int i, int i2, boolean z) {
        super(context, i2, z);
        setSpanCount(i);
    }

    public GridLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        setSpanCount(LayoutManager.getProperties(context, attributeSet, i, i2).spanCount);
    }

    private void assignSpans(Recycler recycler, State state, int i, int i2, boolean z) {
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        if (z) {
            i3 = 1;
            i4 = 0;
        } else {
            i4 = i - 1;
            i = -1;
            i3 = -1;
        }
        if (this.mOrientation == 1 && isLayoutRTL()) {
            i5 = this.mSpanCount - 1;
            i6 = i4;
            i7 = -1;
        } else {
            i5 = 0;
            i6 = i4;
            i7 = 1;
        }
        while (i6 != i) {
            View view = this.mSet[i6];
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            layoutParams.mSpanSize = getSpanSize(recycler, state, getPosition(view));
            if (i7 != -1 || layoutParams.mSpanSize <= 1) {
                layoutParams.mSpanIndex = i5;
            } else {
                layoutParams.mSpanIndex = i5 - (layoutParams.mSpanSize - 1);
            }
            i6 += i3;
            i5 = (layoutParams.mSpanSize * i7) + i5;
        }
    }

    private void cachePreLayoutSpanMapping() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            LayoutParams layoutParams = (LayoutParams) getChildAt(i).getLayoutParams();
            int viewLayoutPosition = layoutParams.getViewLayoutPosition();
            this.mPreLayoutSpanSizeCache.put(viewLayoutPosition, layoutParams.getSpanSize());
            this.mPreLayoutSpanIndexCache.put(viewLayoutPosition, layoutParams.getSpanIndex());
        }
    }

    private void calculateItemBorders(int i) {
        this.mCachedBorders = calculateItemBorders(this.mCachedBorders, this.mSpanCount, i);
    }

    static int[] calculateItemBorders(int[] iArr, int i, int i2) {
        int i3 = 0;
        if (!(iArr != null && iArr.length == i + 1 && iArr[iArr.length - 1] == i2)) {
            iArr = new int[(i + 1)];
        }
        iArr[0] = 0;
        int i4 = i2 / i;
        int i5 = i2 % i;
        int i6 = 0;
        int i7 = 1;
        while (i7 <= i) {
            int i8 = i3 + i5;
            if (i8 <= 0 || i - i8 >= i5) {
                i3 = i4;
            } else {
                i3 = i4 + 1;
                i8 -= i;
            }
            i3 += i6;
            iArr[i7] = i3;
            i7++;
            i6 = i3;
            i3 = i8;
        }
        return iArr;
    }

    private void clearPreLayoutSpanMappingCache() {
        this.mPreLayoutSpanSizeCache.clear();
        this.mPreLayoutSpanIndexCache.clear();
    }

    private void ensureAnchorIsInCorrectSpan(Recycler recycler, State state, AnchorInfo anchorInfo, int i) {
        Object obj = 1;
        if (i != 1) {
            obj = null;
        }
        int spanIndex = getSpanIndex(recycler, state, anchorInfo.mPosition);
        if (obj != null) {
            while (spanIndex > 0 && anchorInfo.mPosition > 0) {
                anchorInfo.mPosition--;
                spanIndex = getSpanIndex(recycler, state, anchorInfo.mPosition);
            }
            return;
        }
        int itemCount = state.getItemCount();
        int i2 = anchorInfo.mPosition;
        int i3 = spanIndex;
        while (i2 < itemCount - 1) {
            spanIndex = getSpanIndex(recycler, state, i2 + 1);
            if (spanIndex <= i3) {
                break;
            }
            i2++;
            i3 = spanIndex;
        }
        anchorInfo.mPosition = i2;
    }

    private void ensureViewSet() {
        if (this.mSet == null || this.mSet.length != this.mSpanCount) {
            this.mSet = new View[this.mSpanCount];
        }
    }

    private int getSpanGroupIndex(Recycler recycler, State state, int i) {
        if (!state.isPreLayout()) {
            return this.mSpanSizeLookup.getSpanGroupIndex(i, this.mSpanCount);
        }
        int convertPreLayoutPositionToPostLayout = recycler.convertPreLayoutPositionToPostLayout(i);
        if (convertPreLayoutPositionToPostLayout != -1) {
            return this.mSpanSizeLookup.getSpanGroupIndex(convertPreLayoutPositionToPostLayout, this.mSpanCount);
        }
        Log.w(TAG, "Cannot find span size for pre layout position. " + i);
        return 0;
    }

    private int getSpanIndex(Recycler recycler, State state, int i) {
        if (!state.isPreLayout()) {
            return this.mSpanSizeLookup.getCachedSpanIndex(i, this.mSpanCount);
        }
        int i2 = this.mPreLayoutSpanIndexCache.get(i, -1);
        if (i2 != -1) {
            return i2;
        }
        i2 = recycler.convertPreLayoutPositionToPostLayout(i);
        if (i2 != -1) {
            return this.mSpanSizeLookup.getCachedSpanIndex(i2, this.mSpanCount);
        }
        Log.w(TAG, "Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:" + i);
        return 0;
    }

    private int getSpanSize(Recycler recycler, State state, int i) {
        if (!state.isPreLayout()) {
            return this.mSpanSizeLookup.getSpanSize(i);
        }
        int i2 = this.mPreLayoutSpanSizeCache.get(i, -1);
        if (i2 != -1) {
            return i2;
        }
        i2 = recycler.convertPreLayoutPositionToPostLayout(i);
        if (i2 != -1) {
            return this.mSpanSizeLookup.getSpanSize(i2);
        }
        Log.w(TAG, "Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:" + i);
        return 1;
    }

    private void guessMeasurement(float f, int i) {
        calculateItemBorders(Math.max(Math.round(((float) this.mSpanCount) * f), i));
    }

    private void measureChildWithDecorationsAndMargin(View view, int i, int i2, boolean z, boolean z2) {
        calculateItemDecorationsForChild(view, this.mDecorInsets);
        android.support.v7.widget.RecyclerView.LayoutParams layoutParams = (android.support.v7.widget.RecyclerView.LayoutParams) view.getLayoutParams();
        if (z || this.mOrientation == 1) {
            i = updateSpecWithExtra(i, layoutParams.leftMargin + this.mDecorInsets.left, layoutParams.rightMargin + this.mDecorInsets.right);
        }
        if (z || this.mOrientation == 0) {
            i2 = updateSpecWithExtra(i2, layoutParams.topMargin + this.mDecorInsets.top, layoutParams.bottomMargin + this.mDecorInsets.bottom);
        }
        if (z2 ? shouldReMeasureChild(view, i, i2, layoutParams) : shouldMeasureChild(view, i, i2, layoutParams)) {
            view.measure(i, i2);
        }
    }

    private void updateMeasurements() {
        calculateItemBorders(getOrientation() == 1 ? (getWidth() - getPaddingRight()) - getPaddingLeft() : (getHeight() - getPaddingBottom()) - getPaddingTop());
    }

    private int updateSpecWithExtra(int i, int i2, int i3) {
        if (i2 == 0 && i3 == 0) {
            return i;
        }
        int mode = MeasureSpec.getMode(i);
        return (mode == Integer.MIN_VALUE || mode == 1073741824) ? MeasureSpec.makeMeasureSpec((MeasureSpec.getSize(i) - i2) - i3, mode) : i;
    }

    public boolean checkLayoutParams(android.support.v7.widget.RecyclerView.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    View findReferenceChild(Recycler recycler, State state, int i, int i2, int i3) {
        View view = null;
        ensureLayoutState();
        int startAfterPadding = this.mOrientationHelper.getStartAfterPadding();
        int endAfterPadding = this.mOrientationHelper.getEndAfterPadding();
        int i4 = i2 > i ? 1 : -1;
        View view2 = null;
        while (i != i2) {
            View view3;
            View childAt = getChildAt(i);
            int position = getPosition(childAt);
            if (position >= 0 && position < i3 && getSpanIndex(recycler, state, position) == 0) {
                if (!((android.support.v7.widget.RecyclerView.LayoutParams) childAt.getLayoutParams()).isItemRemoved()) {
                    if (this.mOrientationHelper.getDecoratedStart(childAt) < endAfterPadding && this.mOrientationHelper.getDecoratedEnd(childAt) >= startAfterPadding) {
                        break;
                    } else if (view2 == null) {
                        view3 = view;
                        i += i4;
                        view = view3;
                        view2 = childAt;
                    }
                } else if (view == null) {
                    view3 = childAt;
                    childAt = view2;
                    i += i4;
                    view = view3;
                    view2 = childAt;
                }
            }
            view3 = view;
            childAt = view2;
            i += i4;
            view = view3;
            view2 = childAt;
        }
        if (view2 == null) {
            return view;
        }
        childAt = view2;
        return childAt;
    }

    public android.support.v7.widget.RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return this.mOrientation == 0 ? new LayoutParams(-2, -1) : new LayoutParams(-1, -2);
    }

    public android.support.v7.widget.RecyclerView.LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
        return new LayoutParams(context, attributeSet);
    }

    public android.support.v7.widget.RecyclerView.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof MarginLayoutParams ? new LayoutParams((MarginLayoutParams) layoutParams) : new LayoutParams(layoutParams);
    }

    public int getColumnCountForAccessibility(Recycler recycler, State state) {
        return this.mOrientation == 1 ? this.mSpanCount : state.getItemCount() <= 0 ? 0 : getSpanGroupIndex(recycler, state, state.getItemCount() - 1) + 1;
    }

    public int getRowCountForAccessibility(Recycler recycler, State state) {
        return this.mOrientation == 0 ? this.mSpanCount : state.getItemCount() <= 0 ? 0 : getSpanGroupIndex(recycler, state, state.getItemCount() - 1) + 1;
    }

    public int getSpanCount() {
        return this.mSpanCount;
    }

    public SpanSizeLookup getSpanSizeLookup() {
        return this.mSpanSizeLookup;
    }

    void layoutChunk(Recycler recycler, State state, LayoutState layoutState, LayoutChunkResult layoutChunkResult) {
        int modeInOther = this.mOrientationHelper.getModeInOther();
        Object obj = modeInOther != 1073741824 ? 1 : null;
        int i = getChildCount() > 0 ? this.mCachedBorders[this.mSpanCount] : 0;
        if (obj != null) {
            updateMeasurements();
        }
        boolean z = layoutState.mItemDirection == 1;
        int i2 = 0;
        int i3 = 0;
        int i4 = this.mSpanCount;
        if (!z) {
            i4 = getSpanIndex(recycler, state, layoutState.mCurrentPosition) + getSpanSize(recycler, state, layoutState.mCurrentPosition);
        }
        while (i2 < this.mSpanCount && layoutState.hasMore(state) && i4 > 0) {
            int i5 = layoutState.mCurrentPosition;
            int spanSize = getSpanSize(recycler, state, i5);
            if (spanSize <= this.mSpanCount) {
                i4 -= spanSize;
                if (i4 < 0) {
                    break;
                }
                View next = layoutState.next(recycler);
                if (next == null) {
                    break;
                }
                i3 += spanSize;
                this.mSet[i2] = next;
                i2++;
            } else {
                throw new IllegalArgumentException("Item at position " + i5 + " requires " + spanSize + " spans but GridLayoutManager has only " + this.mSpanCount + " spans.");
            }
        }
        if (i2 == 0) {
            layoutChunkResult.mFinished = true;
            return;
        }
        View view;
        int childMeasureSpec;
        int childMeasureSpec2;
        assignSpans(recycler, state, i2, i3, z);
        float f = 0.0f;
        spanSize = 0;
        int i6 = 0;
        while (i6 < i2) {
            View view2 = this.mSet[i6];
            if (layoutState.mScrapList == null) {
                if (z) {
                    addView(view2);
                } else {
                    addView(view2, 0);
                }
            } else if (z) {
                addDisappearingView(view2);
            } else {
                addDisappearingView(view2, 0);
            }
            LayoutParams layoutParams = (LayoutParams) view2.getLayoutParams();
            int childMeasureSpec3 = LayoutManager.getChildMeasureSpec(this.mCachedBorders[layoutParams.mSpanIndex + layoutParams.mSpanSize] - this.mCachedBorders[layoutParams.mSpanIndex], modeInOther, 0, this.mOrientation == 0 ? layoutParams.height : layoutParams.width, false);
            int childMeasureSpec4 = LayoutManager.getChildMeasureSpec(this.mOrientationHelper.getTotalSpace(), this.mOrientationHelper.getMode(), 0, this.mOrientation == 1 ? layoutParams.height : layoutParams.width, true);
            if (this.mOrientation == 1) {
                measureChildWithDecorationsAndMargin(view2, childMeasureSpec3, childMeasureSpec4, false, false);
            } else {
                measureChildWithDecorationsAndMargin(view2, childMeasureSpec4, childMeasureSpec3, false, false);
            }
            i5 = this.mOrientationHelper.getDecoratedMeasurement(view2);
            if (i5 <= spanSize) {
                i5 = spanSize;
            }
            float decoratedMeasurementInOther = (1.0f * ((float) this.mOrientationHelper.getDecoratedMeasurementInOther(view2))) / ((float) layoutParams.mSpanSize);
            if (decoratedMeasurementInOther <= f) {
                decoratedMeasurementInOther = f;
            }
            i6++;
            f = decoratedMeasurementInOther;
            spanSize = i5;
        }
        if (obj != null) {
            guessMeasurement(f, i);
            spanSize = 0;
            int i7 = 0;
            while (i7 < i2) {
                view = this.mSet[i7];
                layoutParams = (LayoutParams) view.getLayoutParams();
                childMeasureSpec = LayoutManager.getChildMeasureSpec(this.mCachedBorders[layoutParams.mSpanIndex + layoutParams.mSpanSize] - this.mCachedBorders[layoutParams.mSpanIndex], 1073741824, 0, this.mOrientation == 0 ? layoutParams.height : layoutParams.width, false);
                childMeasureSpec2 = LayoutManager.getChildMeasureSpec(this.mOrientationHelper.getTotalSpace(), this.mOrientationHelper.getMode(), 0, this.mOrientation == 1 ? layoutParams.height : layoutParams.width, true);
                if (this.mOrientation == 1) {
                    measureChildWithDecorationsAndMargin(view, childMeasureSpec, childMeasureSpec2, false, true);
                } else {
                    measureChildWithDecorationsAndMargin(view, childMeasureSpec2, childMeasureSpec, false, true);
                }
                i4 = this.mOrientationHelper.getDecoratedMeasurement(view);
                if (i4 <= spanSize) {
                    i4 = spanSize;
                }
                i7++;
                spanSize = i4;
            }
        }
        childMeasureSpec2 = MeasureSpec.makeMeasureSpec(spanSize, 1073741824);
        for (i5 = 0; i5 < i2; i5++) {
            view = this.mSet[i5];
            if (this.mOrientationHelper.getDecoratedMeasurement(view) != spanSize) {
                layoutParams = (LayoutParams) view.getLayoutParams();
                childMeasureSpec = LayoutManager.getChildMeasureSpec(this.mCachedBorders[layoutParams.mSpanIndex + layoutParams.mSpanSize] - this.mCachedBorders[layoutParams.mSpanIndex], 1073741824, 0, this.mOrientation == 0 ? layoutParams.height : layoutParams.width, false);
                if (this.mOrientation == 1) {
                    measureChildWithDecorationsAndMargin(view, childMeasureSpec, childMeasureSpec2, true, true);
                } else {
                    measureChildWithDecorationsAndMargin(view, childMeasureSpec2, childMeasureSpec, true, true);
                }
            }
        }
        layoutChunkResult.mConsumed = spanSize;
        i4 = 0;
        i5 = 0;
        if (this.mOrientation == 1) {
            if (layoutState.mLayoutDirection == -1) {
                i5 = layoutState.mOffset;
                i4 = i5 - spanSize;
                i3 = 0;
                spanSize = 0;
            } else {
                i4 = layoutState.mOffset;
                i5 = i4 + spanSize;
                i3 = 0;
                spanSize = 0;
            }
        } else if (layoutState.mLayoutDirection == -1) {
            i3 = layoutState.mOffset;
            spanSize = i3 - spanSize;
        } else {
            i3 = layoutState.mOffset;
            int i8 = i3;
            i3 = spanSize + i3;
            spanSize = i8;
        }
        int i9 = 0;
        childMeasureSpec = i3;
        i3 = spanSize;
        spanSize = i4;
        while (i9 < i2) {
            int i10;
            int i11;
            view = this.mSet[i9];
            layoutParams = (LayoutParams) view.getLayoutParams();
            if (this.mOrientation != 1) {
                spanSize = this.mCachedBorders[layoutParams.mSpanIndex] + getPaddingTop();
                i5 = this.mOrientationHelper.getDecoratedMeasurementInOther(view) + spanSize;
                i10 = i3;
                i11 = childMeasureSpec;
            } else if (isLayoutRTL()) {
                childMeasureSpec = this.mCachedBorders[layoutParams.mSpanIndex + layoutParams.mSpanSize] + getPaddingLeft();
                i10 = childMeasureSpec - this.mOrientationHelper.getDecoratedMeasurementInOther(view);
                i11 = childMeasureSpec;
            } else {
                i3 = getPaddingLeft() + this.mCachedBorders[layoutParams.mSpanIndex];
                i10 = i3;
                i11 = this.mOrientationHelper.getDecoratedMeasurementInOther(view) + i3;
            }
            layoutDecorated(view, i10 + layoutParams.leftMargin, spanSize + layoutParams.topMargin, i11 - layoutParams.rightMargin, i5 - layoutParams.bottomMargin);
            if (layoutParams.isItemRemoved() || layoutParams.isItemChanged()) {
                layoutChunkResult.mIgnoreConsumed = true;
            }
            layoutChunkResult.mFocusable |= view.isFocusable();
            i9++;
            childMeasureSpec = i11;
            i3 = i10;
        }
        Arrays.fill(this.mSet, null);
    }

    void onAnchorReady(Recycler recycler, State state, AnchorInfo anchorInfo, int i) {
        super.onAnchorReady(recycler, state, anchorInfo, i);
        updateMeasurements();
        if (state.getItemCount() > 0 && !state.isPreLayout()) {
            ensureAnchorIsInCorrectSpan(recycler, state, anchorInfo, i);
        }
        ensureViewSet();
    }

    public View onFocusSearchFailed(View view, int i, Recycler recycler, State state) {
        View view2;
        View findContainingItemView = findContainingItemView(view);
        if (findContainingItemView == null) {
            view2 = null;
        } else {
            LayoutParams layoutParams = (LayoutParams) findContainingItemView.getLayoutParams();
            int access$000 = layoutParams.mSpanIndex;
            int access$0002 = layoutParams.mSpanIndex + layoutParams.mSpanSize;
            if (super.onFocusSearchFailed(view, i, recycler, state) == null) {
                return null;
            }
            int childCount;
            int i2;
            int i3;
            if (((convertFocusDirectionToLayoutDirection(i) == 1) != this.mShouldReverseLayout ? 1 : null) != null) {
                childCount = getChildCount() - 1;
                i2 = -1;
                i3 = -1;
            } else {
                childCount = 0;
                i2 = getChildCount();
                i3 = 1;
            }
            Object obj = (this.mOrientation == 1 && isLayoutRTL()) ? 1 : null;
            View view3 = null;
            int i4 = -1;
            int i5 = 0;
            int i6 = childCount;
            while (i6 != i2) {
                View childAt = getChildAt(i6);
                if (childAt == findContainingItemView) {
                    return view3;
                }
                View view4;
                int min;
                if (childAt.isFocusable()) {
                    layoutParams = (LayoutParams) childAt.getLayoutParams();
                    int access$0003 = layoutParams.mSpanIndex;
                    int access$0004 = layoutParams.mSpanIndex + layoutParams.mSpanSize;
                    if (access$0003 == access$000 && access$0004 == access$0002) {
                        view2 = childAt;
                    } else {
                        Object obj2;
                        if (view3 == null) {
                            obj2 = 1;
                        } else {
                            int min2 = Math.min(access$0004, access$0002) - Math.max(access$0003, access$000);
                            if (min2 > i5) {
                                obj2 = 1;
                            } else {
                                if (min2 == i5) {
                                    if (obj == (access$0003 > i4 ? 1 : null)) {
                                        obj2 = 1;
                                    }
                                }
                                obj2 = null;
                            }
                        }
                        if (obj2 != null) {
                            childCount = layoutParams.mSpanIndex;
                            view4 = childAt;
                            min = Math.min(access$0004, access$0002) - Math.max(access$0003, access$000);
                            i6 += i3;
                            view3 = view4;
                            i4 = childCount;
                            i5 = min;
                        }
                    }
                }
                childCount = i4;
                min = i5;
                view4 = view3;
                i6 += i3;
                view3 = view4;
                i4 = childCount;
                i5 = min;
            }
            return view3;
        }
        return view2;
    }

    public void onInitializeAccessibilityNodeInfoForItem(Recycler recycler, State state, View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        android.view.ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof LayoutParams) {
            LayoutParams layoutParams2 = (LayoutParams) layoutParams;
            int spanGroupIndex = getSpanGroupIndex(recycler, state, layoutParams2.getViewLayoutPosition());
            if (this.mOrientation == 0) {
                int spanIndex = layoutParams2.getSpanIndex();
                int spanSize = layoutParams2.getSpanSize();
                boolean z = this.mSpanCount > 1 && layoutParams2.getSpanSize() == this.mSpanCount;
                accessibilityNodeInfoCompat.setCollectionItemInfo(CollectionItemInfoCompat.obtain(spanIndex, spanSize, spanGroupIndex, 1, z, false));
                return;
            }
            int spanIndex2 = layoutParams2.getSpanIndex();
            int spanSize2 = layoutParams2.getSpanSize();
            boolean z2 = this.mSpanCount > 1 && layoutParams2.getSpanSize() == this.mSpanCount;
            accessibilityNodeInfoCompat.setCollectionItemInfo(CollectionItemInfoCompat.obtain(spanGroupIndex, 1, spanIndex2, spanSize2, z2, false));
            return;
        }
        super.onInitializeAccessibilityNodeInfoForItem(view, accessibilityNodeInfoCompat);
    }

    public void onItemsAdded(RecyclerView recyclerView, int i, int i2) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    public void onItemsChanged(RecyclerView recyclerView) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    public void onItemsMoved(RecyclerView recyclerView, int i, int i2, int i3) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    public void onItemsRemoved(RecyclerView recyclerView, int i, int i2) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    public void onItemsUpdated(RecyclerView recyclerView, int i, int i2, Object obj) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    public void onLayoutChildren(Recycler recycler, State state) {
        if (state.isPreLayout()) {
            cachePreLayoutSpanMapping();
        }
        super.onLayoutChildren(recycler, state);
        clearPreLayoutSpanMappingCache();
        if (!state.isPreLayout()) {
            this.mPendingSpanCountChange = false;
        }
    }

    public int scrollHorizontallyBy(int i, Recycler recycler, State state) {
        updateMeasurements();
        ensureViewSet();
        return super.scrollHorizontallyBy(i, recycler, state);
    }

    public int scrollVerticallyBy(int i, Recycler recycler, State state) {
        updateMeasurements();
        ensureViewSet();
        return super.scrollVerticallyBy(i, recycler, state);
    }

    public void setMeasuredDimension(Rect rect, int i, int i2) {
        if (this.mCachedBorders == null) {
            super.setMeasuredDimension(rect, i, i2);
        }
        int paddingLeft = getPaddingLeft() + getPaddingRight();
        int paddingTop = getPaddingTop() + getPaddingBottom();
        if (this.mOrientation == 1) {
            paddingTop = LayoutManager.chooseSize(i2, paddingTop + rect.height(), getMinimumHeight());
            paddingLeft = LayoutManager.chooseSize(i, paddingLeft + this.mCachedBorders[this.mCachedBorders.length - 1], getMinimumWidth());
        } else {
            paddingLeft = LayoutManager.chooseSize(i, paddingLeft + rect.width(), getMinimumWidth());
            paddingTop = LayoutManager.chooseSize(i2, paddingTop + this.mCachedBorders[this.mCachedBorders.length - 1], getMinimumHeight());
        }
        setMeasuredDimension(paddingLeft, paddingTop);
    }

    public void setSpanCount(int i) {
        if (i != this.mSpanCount) {
            this.mPendingSpanCountChange = true;
            if (i <= 0) {
                throw new IllegalArgumentException("Span count should be at least 1. Provided " + i);
            }
            this.mSpanCount = i;
            this.mSpanSizeLookup.invalidateSpanIndexCache();
        }
    }

    public void setSpanSizeLookup(SpanSizeLookup spanSizeLookup) {
        this.mSpanSizeLookup = spanSizeLookup;
    }

    public void setStackFromEnd(boolean z) {
        if (z) {
            throw new UnsupportedOperationException("GridLayoutManager does not support stack from end. Consider using reverse layout");
        }
        super.setStackFromEnd(false);
    }

    public boolean supportsPredictiveItemAnimations() {
        return this.mPendingSavedState == null && !this.mPendingSpanCountChange;
    }
}
