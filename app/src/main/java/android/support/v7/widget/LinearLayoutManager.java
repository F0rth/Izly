package android.support.v7.widget;

import android.content.Context;
import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.media.TransportMediator;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.LayoutManager.Properties;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.RecyclerView.Recycler;
import android.support.v7.widget.RecyclerView.SmoothScroller;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper.ViewDropHandler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import java.util.List;

public class LinearLayoutManager extends LayoutManager implements ViewDropHandler {
    private static final boolean DEBUG = false;
    public static final int HORIZONTAL = 0;
    public static final int INVALID_OFFSET = Integer.MIN_VALUE;
    private static final float MAX_SCROLL_FACTOR = 0.33333334f;
    private static final String TAG = "LinearLayoutManager";
    public static final int VERTICAL = 1;
    final AnchorInfo mAnchorInfo;
    private boolean mLastStackFromEnd;
    private LayoutState mLayoutState;
    int mOrientation;
    OrientationHelper mOrientationHelper;
    SavedState mPendingSavedState;
    int mPendingScrollPosition;
    int mPendingScrollPositionOffset;
    private boolean mRecycleChildrenOnDetach;
    private boolean mReverseLayout;
    boolean mShouldReverseLayout;
    private boolean mSmoothScrollbarEnabled;
    private boolean mStackFromEnd;

    class AnchorInfo {
        int mCoordinate;
        boolean mLayoutFromEnd;
        int mPosition;

        AnchorInfo() {
        }

        private boolean isViewValidAsAnchor(View view, State state) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            return !layoutParams.isItemRemoved() && layoutParams.getViewLayoutPosition() >= 0 && layoutParams.getViewLayoutPosition() < state.getItemCount();
        }

        void assignCoordinateFromPadding() {
            this.mCoordinate = this.mLayoutFromEnd ? LinearLayoutManager.this.mOrientationHelper.getEndAfterPadding() : LinearLayoutManager.this.mOrientationHelper.getStartAfterPadding();
        }

        public void assignFromView(View view) {
            if (this.mLayoutFromEnd) {
                this.mCoordinate = LinearLayoutManager.this.mOrientationHelper.getDecoratedEnd(view) + LinearLayoutManager.this.mOrientationHelper.getTotalSpaceChange();
            } else {
                this.mCoordinate = LinearLayoutManager.this.mOrientationHelper.getDecoratedStart(view);
            }
            this.mPosition = LinearLayoutManager.this.getPosition(view);
        }

        public void assignFromViewAndKeepVisibleRect(View view) {
            int totalSpaceChange = LinearLayoutManager.this.mOrientationHelper.getTotalSpaceChange();
            if (totalSpaceChange >= 0) {
                assignFromView(view);
                return;
            }
            this.mPosition = LinearLayoutManager.this.getPosition(view);
            int decoratedMeasurement;
            if (this.mLayoutFromEnd) {
                totalSpaceChange = (LinearLayoutManager.this.mOrientationHelper.getEndAfterPadding() - totalSpaceChange) - LinearLayoutManager.this.mOrientationHelper.getDecoratedEnd(view);
                this.mCoordinate = LinearLayoutManager.this.mOrientationHelper.getEndAfterPadding() - totalSpaceChange;
                if (totalSpaceChange > 0) {
                    decoratedMeasurement = LinearLayoutManager.this.mOrientationHelper.getDecoratedMeasurement(view);
                    int i = this.mCoordinate;
                    int startAfterPadding = LinearLayoutManager.this.mOrientationHelper.getStartAfterPadding();
                    decoratedMeasurement = (i - decoratedMeasurement) - (Math.min(LinearLayoutManager.this.mOrientationHelper.getDecoratedStart(view) - startAfterPadding, 0) + startAfterPadding);
                    if (decoratedMeasurement < 0) {
                        this.mCoordinate = Math.min(totalSpaceChange, -decoratedMeasurement) + this.mCoordinate;
                        return;
                    }
                    return;
                }
                return;
            }
            decoratedMeasurement = LinearLayoutManager.this.mOrientationHelper.getDecoratedStart(view);
            i = decoratedMeasurement - LinearLayoutManager.this.mOrientationHelper.getStartAfterPadding();
            this.mCoordinate = decoratedMeasurement;
            if (i > 0) {
                startAfterPadding = LinearLayoutManager.this.mOrientationHelper.getDecoratedMeasurement(view);
                int endAfterPadding = LinearLayoutManager.this.mOrientationHelper.getEndAfterPadding();
                totalSpaceChange = (LinearLayoutManager.this.mOrientationHelper.getEndAfterPadding() - Math.min(0, (endAfterPadding - totalSpaceChange) - LinearLayoutManager.this.mOrientationHelper.getDecoratedEnd(view))) - (decoratedMeasurement + startAfterPadding);
                if (totalSpaceChange < 0) {
                    this.mCoordinate -= Math.min(i, -totalSpaceChange);
                }
            }
        }

        void reset() {
            this.mPosition = -1;
            this.mCoordinate = Integer.MIN_VALUE;
            this.mLayoutFromEnd = false;
        }

        public String toString() {
            return "AnchorInfo{mPosition=" + this.mPosition + ", mCoordinate=" + this.mCoordinate + ", mLayoutFromEnd=" + this.mLayoutFromEnd + '}';
        }
    }

    public static class LayoutChunkResult {
        public int mConsumed;
        public boolean mFinished;
        public boolean mFocusable;
        public boolean mIgnoreConsumed;

        protected LayoutChunkResult() {
        }

        void resetInternal() {
            this.mConsumed = 0;
            this.mFinished = false;
            this.mIgnoreConsumed = false;
            this.mFocusable = false;
        }
    }

    static class LayoutState {
        static final int INVALID_LAYOUT = Integer.MIN_VALUE;
        static final int ITEM_DIRECTION_HEAD = -1;
        static final int ITEM_DIRECTION_TAIL = 1;
        static final int LAYOUT_END = 1;
        static final int LAYOUT_START = -1;
        static final int SCOLLING_OFFSET_NaN = Integer.MIN_VALUE;
        static final String TAG = "LinearLayoutManager#LayoutState";
        int mAvailable;
        int mCurrentPosition;
        int mExtra = 0;
        boolean mInfinite;
        boolean mIsPreLayout = false;
        int mItemDirection;
        int mLastScrollDelta;
        int mLayoutDirection;
        int mOffset;
        boolean mRecycle = true;
        List<ViewHolder> mScrapList = null;
        int mScrollingOffset;

        LayoutState() {
        }

        private View nextViewFromScrapList() {
            int size = this.mScrapList.size();
            int i = 0;
            while (i < size) {
                View view = ((ViewHolder) this.mScrapList.get(i)).itemView;
                LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                if (layoutParams.isItemRemoved() || this.mCurrentPosition != layoutParams.getViewLayoutPosition()) {
                    i++;
                } else {
                    assignPositionFromScrapList(view);
                    return view;
                }
            }
            return null;
        }

        public void assignPositionFromScrapList() {
            assignPositionFromScrapList(null);
        }

        public void assignPositionFromScrapList(View view) {
            View nextViewInLimitedList = nextViewInLimitedList(view);
            if (nextViewInLimitedList == null) {
                this.mCurrentPosition = -1;
            } else {
                this.mCurrentPosition = ((LayoutParams) nextViewInLimitedList.getLayoutParams()).getViewLayoutPosition();
            }
        }

        boolean hasMore(State state) {
            return this.mCurrentPosition >= 0 && this.mCurrentPosition < state.getItemCount();
        }

        void log() {
            Log.d(TAG, "avail:" + this.mAvailable + ", ind:" + this.mCurrentPosition + ", dir:" + this.mItemDirection + ", offset:" + this.mOffset + ", layoutDir:" + this.mLayoutDirection);
        }

        View next(Recycler recycler) {
            if (this.mScrapList != null) {
                return nextViewFromScrapList();
            }
            View viewForPosition = recycler.getViewForPosition(this.mCurrentPosition);
            this.mCurrentPosition += this.mItemDirection;
            return viewForPosition;
        }

        public View nextViewInLimitedList(View view) {
            int size = this.mScrapList.size();
            View view2 = null;
            int i = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            int i2 = 0;
            while (i2 < size) {
                int viewLayoutPosition;
                View view3 = ((ViewHolder) this.mScrapList.get(i2)).itemView;
                LayoutParams layoutParams = (LayoutParams) view3.getLayoutParams();
                if (!(view3 == view || layoutParams.isItemRemoved())) {
                    viewLayoutPosition = (layoutParams.getViewLayoutPosition() - this.mCurrentPosition) * this.mItemDirection;
                    if (viewLayoutPosition >= 0 && viewLayoutPosition < i) {
                        if (viewLayoutPosition == 0) {
                            return view3;
                        }
                        i2++;
                        i = viewLayoutPosition;
                        view2 = view3;
                    }
                }
                view3 = view2;
                viewLayoutPosition = i;
                i2++;
                i = viewLayoutPosition;
                view2 = view3;
            }
            return view2;
        }
    }

    public static class SavedState implements Parcelable {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public final SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public final SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        boolean mAnchorLayoutFromEnd;
        int mAnchorOffset;
        int mAnchorPosition;

        SavedState(Parcel parcel) {
            boolean z = true;
            this.mAnchorPosition = parcel.readInt();
            this.mAnchorOffset = parcel.readInt();
            if (parcel.readInt() != 1) {
                z = false;
            }
            this.mAnchorLayoutFromEnd = z;
        }

        public SavedState(SavedState savedState) {
            this.mAnchorPosition = savedState.mAnchorPosition;
            this.mAnchorOffset = savedState.mAnchorOffset;
            this.mAnchorLayoutFromEnd = savedState.mAnchorLayoutFromEnd;
        }

        public int describeContents() {
            return 0;
        }

        boolean hasValidAnchor() {
            return this.mAnchorPosition >= 0;
        }

        void invalidateAnchor() {
            this.mAnchorPosition = -1;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mAnchorPosition);
            parcel.writeInt(this.mAnchorOffset);
            parcel.writeInt(this.mAnchorLayoutFromEnd ? 1 : 0);
        }
    }

    public LinearLayoutManager(Context context) {
        this(context, 1, false);
    }

    public LinearLayoutManager(Context context, int i, boolean z) {
        this.mReverseLayout = false;
        this.mShouldReverseLayout = false;
        this.mStackFromEnd = false;
        this.mSmoothScrollbarEnabled = true;
        this.mPendingScrollPosition = -1;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        this.mPendingSavedState = null;
        this.mAnchorInfo = new AnchorInfo();
        setOrientation(i);
        setReverseLayout(z);
        setAutoMeasureEnabled(true);
    }

    public LinearLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        this.mReverseLayout = false;
        this.mShouldReverseLayout = false;
        this.mStackFromEnd = false;
        this.mSmoothScrollbarEnabled = true;
        this.mPendingScrollPosition = -1;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        this.mPendingSavedState = null;
        this.mAnchorInfo = new AnchorInfo();
        Properties properties = LayoutManager.getProperties(context, attributeSet, i, i2);
        setOrientation(properties.orientation);
        setReverseLayout(properties.reverseLayout);
        setStackFromEnd(properties.stackFromEnd);
        setAutoMeasureEnabled(true);
    }

    private int computeScrollExtent(State state) {
        boolean z = false;
        if (getChildCount() == 0) {
            return 0;
        }
        ensureLayoutState();
        OrientationHelper orientationHelper = this.mOrientationHelper;
        View findFirstVisibleChildClosestToStart = findFirstVisibleChildClosestToStart(!this.mSmoothScrollbarEnabled, true);
        if (!this.mSmoothScrollbarEnabled) {
            z = true;
        }
        return ScrollbarHelper.computeScrollExtent(state, orientationHelper, findFirstVisibleChildClosestToStart, findFirstVisibleChildClosestToEnd(z, true), this, this.mSmoothScrollbarEnabled);
    }

    private int computeScrollOffset(State state) {
        boolean z = false;
        if (getChildCount() == 0) {
            return 0;
        }
        ensureLayoutState();
        OrientationHelper orientationHelper = this.mOrientationHelper;
        View findFirstVisibleChildClosestToStart = findFirstVisibleChildClosestToStart(!this.mSmoothScrollbarEnabled, true);
        if (!this.mSmoothScrollbarEnabled) {
            z = true;
        }
        return ScrollbarHelper.computeScrollOffset(state, orientationHelper, findFirstVisibleChildClosestToStart, findFirstVisibleChildClosestToEnd(z, true), this, this.mSmoothScrollbarEnabled, this.mShouldReverseLayout);
    }

    private int computeScrollRange(State state) {
        boolean z = false;
        if (getChildCount() == 0) {
            return 0;
        }
        ensureLayoutState();
        OrientationHelper orientationHelper = this.mOrientationHelper;
        View findFirstVisibleChildClosestToStart = findFirstVisibleChildClosestToStart(!this.mSmoothScrollbarEnabled, true);
        if (!this.mSmoothScrollbarEnabled) {
            z = true;
        }
        return ScrollbarHelper.computeScrollRange(state, orientationHelper, findFirstVisibleChildClosestToStart, findFirstVisibleChildClosestToEnd(z, true), this, this.mSmoothScrollbarEnabled);
    }

    private View findFirstReferenceChild(Recycler recycler, State state) {
        return findReferenceChild(recycler, state, 0, getChildCount(), state.getItemCount());
    }

    private View findFirstVisibleChildClosestToEnd(boolean z, boolean z2) {
        return this.mShouldReverseLayout ? findOneVisibleChild(0, getChildCount(), z, z2) : findOneVisibleChild(getChildCount() - 1, -1, z, z2);
    }

    private View findFirstVisibleChildClosestToStart(boolean z, boolean z2) {
        return this.mShouldReverseLayout ? findOneVisibleChild(getChildCount() - 1, -1, z, z2) : findOneVisibleChild(0, getChildCount(), z, z2);
    }

    private View findLastReferenceChild(Recycler recycler, State state) {
        return findReferenceChild(recycler, state, getChildCount() - 1, -1, state.getItemCount());
    }

    private View findReferenceChildClosestToEnd(Recycler recycler, State state) {
        return this.mShouldReverseLayout ? findFirstReferenceChild(recycler, state) : findLastReferenceChild(recycler, state);
    }

    private View findReferenceChildClosestToStart(Recycler recycler, State state) {
        return this.mShouldReverseLayout ? findLastReferenceChild(recycler, state) : findFirstReferenceChild(recycler, state);
    }

    private int fixLayoutEndGap(int i, Recycler recycler, State state, boolean z) {
        int endAfterPadding = this.mOrientationHelper.getEndAfterPadding() - i;
        if (endAfterPadding <= 0) {
            return 0;
        }
        endAfterPadding = -scrollBy(-endAfterPadding, recycler, state);
        if (!z) {
            return endAfterPadding;
        }
        int endAfterPadding2 = this.mOrientationHelper.getEndAfterPadding() - (i + endAfterPadding);
        if (endAfterPadding2 <= 0) {
            return endAfterPadding;
        }
        this.mOrientationHelper.offsetChildren(endAfterPadding2);
        return endAfterPadding + endAfterPadding2;
    }

    private int fixLayoutStartGap(int i, Recycler recycler, State state, boolean z) {
        int startAfterPadding = i - this.mOrientationHelper.getStartAfterPadding();
        if (startAfterPadding <= 0) {
            return 0;
        }
        startAfterPadding = -scrollBy(startAfterPadding, recycler, state);
        if (!z) {
            return startAfterPadding;
        }
        int startAfterPadding2 = (i + startAfterPadding) - this.mOrientationHelper.getStartAfterPadding();
        if (startAfterPadding2 <= 0) {
            return startAfterPadding;
        }
        this.mOrientationHelper.offsetChildren(-startAfterPadding2);
        return startAfterPadding - startAfterPadding2;
    }

    private View getChildClosestToEnd() {
        return getChildAt(this.mShouldReverseLayout ? 0 : getChildCount() - 1);
    }

    private View getChildClosestToStart() {
        return getChildAt(this.mShouldReverseLayout ? getChildCount() - 1 : 0);
    }

    private void layoutForPredictiveAnimations(Recycler recycler, State state, int i, int i2) {
        if (state.willRunPredictiveAnimations() && getChildCount() != 0 && !state.isPreLayout() && supportsPredictiveItemAnimations()) {
            int i3 = 0;
            int i4 = 0;
            List scrapList = recycler.getScrapList();
            int size = scrapList.size();
            int position = getPosition(getChildAt(0));
            int i5 = 0;
            while (i5 < size) {
                int i6;
                int i7;
                ViewHolder viewHolder = (ViewHolder) scrapList.get(i5);
                if (viewHolder.isRemoved()) {
                    i6 = i3;
                    i7 = i4;
                } else {
                    if (((viewHolder.getLayoutPosition() < position) != this.mShouldReverseLayout ? -1 : 1) == -1) {
                        i6 = this.mOrientationHelper.getDecoratedMeasurement(viewHolder.itemView) + i3;
                        i7 = i4;
                    } else {
                        i7 = this.mOrientationHelper.getDecoratedMeasurement(viewHolder.itemView) + i4;
                        i6 = i3;
                    }
                }
                i5++;
                i4 = i7;
                i3 = i6;
            }
            this.mLayoutState.mScrapList = scrapList;
            if (i3 > 0) {
                updateLayoutStateToFillStart(getPosition(getChildClosestToStart()), i);
                this.mLayoutState.mExtra = i3;
                this.mLayoutState.mAvailable = 0;
                this.mLayoutState.assignPositionFromScrapList();
                fill(recycler, this.mLayoutState, state, false);
            }
            if (i4 > 0) {
                updateLayoutStateToFillEnd(getPosition(getChildClosestToEnd()), i2);
                this.mLayoutState.mExtra = i4;
                this.mLayoutState.mAvailable = 0;
                this.mLayoutState.assignPositionFromScrapList();
                fill(recycler, this.mLayoutState, state, false);
            }
            this.mLayoutState.mScrapList = null;
        }
    }

    private void logChildren() {
        Log.d(TAG, "internal representation of views on the screen");
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            Log.d(TAG, "item " + getPosition(childAt) + ", coord:" + this.mOrientationHelper.getDecoratedStart(childAt));
        }
        Log.d(TAG, "==============");
    }

    private void recycleByLayoutState(Recycler recycler, LayoutState layoutState) {
        if (layoutState.mRecycle && !layoutState.mInfinite) {
            if (layoutState.mLayoutDirection == -1) {
                recycleViewsFromEnd(recycler, layoutState.mScrollingOffset);
            } else {
                recycleViewsFromStart(recycler, layoutState.mScrollingOffset);
            }
        }
    }

    private void recycleChildren(Recycler recycler, int i, int i2) {
        if (i != i2) {
            if (i2 > i) {
                for (int i3 = i2 - 1; i3 >= i; i3--) {
                    removeAndRecycleViewAt(i3, recycler);
                }
                return;
            }
            while (i > i2) {
                removeAndRecycleViewAt(i, recycler);
                i--;
            }
        }
    }

    private void recycleViewsFromEnd(Recycler recycler, int i) {
        int childCount = getChildCount();
        if (i >= 0) {
            int end = this.mOrientationHelper.getEnd() - i;
            int i2;
            if (this.mShouldReverseLayout) {
                for (i2 = 0; i2 < childCount; i2++) {
                    if (this.mOrientationHelper.getDecoratedStart(getChildAt(i2)) < end) {
                        recycleChildren(recycler, 0, i2);
                        return;
                    }
                }
                return;
            }
            for (i2 = childCount - 1; i2 >= 0; i2--) {
                if (this.mOrientationHelper.getDecoratedStart(getChildAt(i2)) < end) {
                    recycleChildren(recycler, childCount - 1, i2);
                    return;
                }
            }
        }
    }

    private void recycleViewsFromStart(Recycler recycler, int i) {
        if (i >= 0) {
            int childCount = getChildCount();
            int i2;
            if (this.mShouldReverseLayout) {
                for (i2 = childCount - 1; i2 >= 0; i2--) {
                    if (this.mOrientationHelper.getDecoratedEnd(getChildAt(i2)) > i) {
                        recycleChildren(recycler, childCount - 1, i2);
                        return;
                    }
                }
                return;
            }
            for (i2 = 0; i2 < childCount; i2++) {
                if (this.mOrientationHelper.getDecoratedEnd(getChildAt(i2)) > i) {
                    recycleChildren(recycler, 0, i2);
                    return;
                }
            }
        }
    }

    private void resolveShouldLayoutReverse() {
        boolean z = true;
        if (this.mOrientation == 1 || !isLayoutRTL()) {
            z = this.mReverseLayout;
        } else if (this.mReverseLayout) {
            z = false;
        }
        this.mShouldReverseLayout = z;
    }

    private boolean updateAnchorFromChildren(Recycler recycler, State state, AnchorInfo anchorInfo) {
        boolean z = false;
        if (getChildCount() == 0) {
            return false;
        }
        View focusedChild = getFocusedChild();
        if (focusedChild != null && anchorInfo.isViewValidAsAnchor(focusedChild, state)) {
            anchorInfo.assignFromViewAndKeepVisibleRect(focusedChild);
            return true;
        } else if (this.mLastStackFromEnd != this.mStackFromEnd) {
            return false;
        } else {
            focusedChild = anchorInfo.mLayoutFromEnd ? findReferenceChildClosestToEnd(recycler, state) : findReferenceChildClosestToStart(recycler, state);
            if (focusedChild == null) {
                return false;
            }
            anchorInfo.assignFromView(focusedChild);
            if (!state.isPreLayout() && supportsPredictiveItemAnimations()) {
                if (this.mOrientationHelper.getDecoratedStart(focusedChild) >= this.mOrientationHelper.getEndAfterPadding() || this.mOrientationHelper.getDecoratedEnd(focusedChild) < this.mOrientationHelper.getStartAfterPadding()) {
                    z = true;
                }
                if (z) {
                    anchorInfo.mCoordinate = anchorInfo.mLayoutFromEnd ? this.mOrientationHelper.getEndAfterPadding() : this.mOrientationHelper.getStartAfterPadding();
                }
            }
            return true;
        }
    }

    private boolean updateAnchorFromPendingData(State state, AnchorInfo anchorInfo) {
        boolean z = false;
        if (state.isPreLayout() || this.mPendingScrollPosition == -1) {
            return false;
        }
        if (this.mPendingScrollPosition < 0 || this.mPendingScrollPosition >= state.getItemCount()) {
            this.mPendingScrollPosition = -1;
            this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
            return false;
        }
        anchorInfo.mPosition = this.mPendingScrollPosition;
        if (this.mPendingSavedState != null && this.mPendingSavedState.hasValidAnchor()) {
            anchorInfo.mLayoutFromEnd = this.mPendingSavedState.mAnchorLayoutFromEnd;
            if (anchorInfo.mLayoutFromEnd) {
                anchorInfo.mCoordinate = this.mOrientationHelper.getEndAfterPadding() - this.mPendingSavedState.mAnchorOffset;
                return true;
            }
            anchorInfo.mCoordinate = this.mOrientationHelper.getStartAfterPadding() + this.mPendingSavedState.mAnchorOffset;
            return true;
        } else if (this.mPendingScrollPositionOffset == Integer.MIN_VALUE) {
            View findViewByPosition = findViewByPosition(this.mPendingScrollPosition);
            if (findViewByPosition == null) {
                if (getChildCount() > 0) {
                    if ((this.mPendingScrollPosition < getPosition(getChildAt(0))) == this.mShouldReverseLayout) {
                        z = true;
                    }
                    anchorInfo.mLayoutFromEnd = z;
                }
                anchorInfo.assignCoordinateFromPadding();
                return true;
            } else if (this.mOrientationHelper.getDecoratedMeasurement(findViewByPosition) > this.mOrientationHelper.getTotalSpace()) {
                anchorInfo.assignCoordinateFromPadding();
                return true;
            } else if (this.mOrientationHelper.getDecoratedStart(findViewByPosition) - this.mOrientationHelper.getStartAfterPadding() < 0) {
                anchorInfo.mCoordinate = this.mOrientationHelper.getStartAfterPadding();
                anchorInfo.mLayoutFromEnd = false;
                return true;
            } else if (this.mOrientationHelper.getEndAfterPadding() - this.mOrientationHelper.getDecoratedEnd(findViewByPosition) < 0) {
                anchorInfo.mCoordinate = this.mOrientationHelper.getEndAfterPadding();
                anchorInfo.mLayoutFromEnd = true;
                return true;
            } else {
                anchorInfo.mCoordinate = anchorInfo.mLayoutFromEnd ? this.mOrientationHelper.getDecoratedEnd(findViewByPosition) + this.mOrientationHelper.getTotalSpaceChange() : this.mOrientationHelper.getDecoratedStart(findViewByPosition);
                return true;
            }
        } else {
            anchorInfo.mLayoutFromEnd = this.mShouldReverseLayout;
            if (this.mShouldReverseLayout) {
                anchorInfo.mCoordinate = this.mOrientationHelper.getEndAfterPadding() - this.mPendingScrollPositionOffset;
                return true;
            }
            anchorInfo.mCoordinate = this.mOrientationHelper.getStartAfterPadding() + this.mPendingScrollPositionOffset;
            return true;
        }
    }

    private void updateAnchorInfoForLayout(Recycler recycler, State state, AnchorInfo anchorInfo) {
        if (!updateAnchorFromPendingData(state, anchorInfo) && !updateAnchorFromChildren(recycler, state, anchorInfo)) {
            anchorInfo.assignCoordinateFromPadding();
            anchorInfo.mPosition = this.mStackFromEnd ? state.getItemCount() - 1 : 0;
        }
    }

    private void updateLayoutState(int i, int i2, boolean z, State state) {
        int decoratedEnd;
        int i3 = 1;
        this.mLayoutState.mInfinite = this.mOrientationHelper.getMode() == 0;
        this.mLayoutState.mExtra = getExtraLayoutSpace(state);
        this.mLayoutState.mLayoutDirection = i;
        View childClosestToEnd;
        LayoutState layoutState;
        if (i == 1) {
            LayoutState layoutState2 = this.mLayoutState;
            layoutState2.mExtra += this.mOrientationHelper.getEndPadding();
            childClosestToEnd = getChildClosestToEnd();
            layoutState = this.mLayoutState;
            if (this.mShouldReverseLayout) {
                i3 = -1;
            }
            layoutState.mItemDirection = i3;
            this.mLayoutState.mCurrentPosition = getPosition(childClosestToEnd) + this.mLayoutState.mItemDirection;
            this.mLayoutState.mOffset = this.mOrientationHelper.getDecoratedEnd(childClosestToEnd);
            decoratedEnd = this.mOrientationHelper.getDecoratedEnd(childClosestToEnd) - this.mOrientationHelper.getEndAfterPadding();
        } else {
            childClosestToEnd = getChildClosestToStart();
            layoutState = this.mLayoutState;
            layoutState.mExtra += this.mOrientationHelper.getStartAfterPadding();
            layoutState = this.mLayoutState;
            if (!this.mShouldReverseLayout) {
                i3 = -1;
            }
            layoutState.mItemDirection = i3;
            this.mLayoutState.mCurrentPosition = getPosition(childClosestToEnd) + this.mLayoutState.mItemDirection;
            this.mLayoutState.mOffset = this.mOrientationHelper.getDecoratedStart(childClosestToEnd);
            decoratedEnd = (-this.mOrientationHelper.getDecoratedStart(childClosestToEnd)) + this.mOrientationHelper.getStartAfterPadding();
        }
        this.mLayoutState.mAvailable = i2;
        if (z) {
            LayoutState layoutState3 = this.mLayoutState;
            layoutState3.mAvailable -= decoratedEnd;
        }
        this.mLayoutState.mScrollingOffset = decoratedEnd;
    }

    private void updateLayoutStateToFillEnd(int i, int i2) {
        this.mLayoutState.mAvailable = this.mOrientationHelper.getEndAfterPadding() - i2;
        this.mLayoutState.mItemDirection = this.mShouldReverseLayout ? -1 : 1;
        this.mLayoutState.mCurrentPosition = i;
        this.mLayoutState.mLayoutDirection = 1;
        this.mLayoutState.mOffset = i2;
        this.mLayoutState.mScrollingOffset = Integer.MIN_VALUE;
    }

    private void updateLayoutStateToFillEnd(AnchorInfo anchorInfo) {
        updateLayoutStateToFillEnd(anchorInfo.mPosition, anchorInfo.mCoordinate);
    }

    private void updateLayoutStateToFillStart(int i, int i2) {
        this.mLayoutState.mAvailable = i2 - this.mOrientationHelper.getStartAfterPadding();
        this.mLayoutState.mCurrentPosition = i;
        this.mLayoutState.mItemDirection = this.mShouldReverseLayout ? 1 : -1;
        this.mLayoutState.mLayoutDirection = -1;
        this.mLayoutState.mOffset = i2;
        this.mLayoutState.mScrollingOffset = Integer.MIN_VALUE;
    }

    private void updateLayoutStateToFillStart(AnchorInfo anchorInfo) {
        updateLayoutStateToFillStart(anchorInfo.mPosition, anchorInfo.mCoordinate);
    }

    public void assertNotInLayoutOrScroll(String str) {
        if (this.mPendingSavedState == null) {
            super.assertNotInLayoutOrScroll(str);
        }
    }

    public boolean canScrollHorizontally() {
        return this.mOrientation == 0;
    }

    public boolean canScrollVertically() {
        return this.mOrientation == 1;
    }

    public int computeHorizontalScrollExtent(State state) {
        return computeScrollExtent(state);
    }

    public int computeHorizontalScrollOffset(State state) {
        return computeScrollOffset(state);
    }

    public int computeHorizontalScrollRange(State state) {
        return computeScrollRange(state);
    }

    public PointF computeScrollVectorForPosition(int i) {
        int i2 = 1;
        boolean z = false;
        if (getChildCount() == 0) {
            return null;
        }
        if (i < getPosition(getChildAt(0))) {
            z = true;
        }
        if (z != this.mShouldReverseLayout) {
            i2 = -1;
        }
        return this.mOrientation == 0 ? new PointF((float) i2, 0.0f) : new PointF(0.0f, (float) i2);
    }

    public int computeVerticalScrollExtent(State state) {
        return computeScrollExtent(state);
    }

    public int computeVerticalScrollOffset(State state) {
        return computeScrollOffset(state);
    }

    public int computeVerticalScrollRange(State state) {
        return computeScrollRange(state);
    }

    int convertFocusDirectionToLayoutDirection(int i) {
        switch (i) {
            case 1:
                break;
            case 2:
                return 1;
            case 17:
                if (this.mOrientation != 0) {
                    return Integer.MIN_VALUE;
                }
                break;
            case 33:
                if (this.mOrientation != 1) {
                    return Integer.MIN_VALUE;
                }
                break;
            case 66:
                return this.mOrientation == 0 ? 1 : Integer.MIN_VALUE;
            case TransportMediator.KEYCODE_MEDIA_RECORD /*130*/:
                return this.mOrientation == 1 ? 1 : Integer.MIN_VALUE;
            default:
                return Integer.MIN_VALUE;
        }
        return -1;
    }

    LayoutState createLayoutState() {
        return new LayoutState();
    }

    void ensureLayoutState() {
        if (this.mLayoutState == null) {
            this.mLayoutState = createLayoutState();
        }
        if (this.mOrientationHelper == null) {
            this.mOrientationHelper = OrientationHelper.createOrientationHelper(this, this.mOrientation);
        }
    }

    int fill(Recycler recycler, LayoutState layoutState, State state, boolean z) {
        int i = layoutState.mAvailable;
        if (layoutState.mScrollingOffset != Integer.MIN_VALUE) {
            if (layoutState.mAvailable < 0) {
                layoutState.mScrollingOffset += layoutState.mAvailable;
            }
            recycleByLayoutState(recycler, layoutState);
        }
        int i2 = layoutState.mAvailable + layoutState.mExtra;
        LayoutChunkResult layoutChunkResult = new LayoutChunkResult();
        while (true) {
            if ((!layoutState.mInfinite && i2 <= 0) || !layoutState.hasMore(state)) {
                break;
            }
            layoutChunkResult.resetInternal();
            layoutChunk(recycler, state, layoutState, layoutChunkResult);
            if (!layoutChunkResult.mFinished) {
                layoutState.mOffset += layoutChunkResult.mConsumed * layoutState.mLayoutDirection;
                if (!(layoutChunkResult.mIgnoreConsumed && this.mLayoutState.mScrapList == null && state.isPreLayout())) {
                    layoutState.mAvailable -= layoutChunkResult.mConsumed;
                    i2 -= layoutChunkResult.mConsumed;
                }
                if (layoutState.mScrollingOffset != Integer.MIN_VALUE) {
                    layoutState.mScrollingOffset += layoutChunkResult.mConsumed;
                    if (layoutState.mAvailable < 0) {
                        layoutState.mScrollingOffset += layoutState.mAvailable;
                    }
                    recycleByLayoutState(recycler, layoutState);
                }
                if (z && layoutChunkResult.mFocusable) {
                    break;
                }
            } else {
                break;
            }
        }
        return i - layoutState.mAvailable;
    }

    public int findFirstCompletelyVisibleItemPosition() {
        View findOneVisibleChild = findOneVisibleChild(0, getChildCount(), true, false);
        return findOneVisibleChild == null ? -1 : getPosition(findOneVisibleChild);
    }

    public int findFirstVisibleItemPosition() {
        View findOneVisibleChild = findOneVisibleChild(0, getChildCount(), false, true);
        return findOneVisibleChild == null ? -1 : getPosition(findOneVisibleChild);
    }

    public int findLastCompletelyVisibleItemPosition() {
        View findOneVisibleChild = findOneVisibleChild(getChildCount() - 1, -1, true, false);
        return findOneVisibleChild == null ? -1 : getPosition(findOneVisibleChild);
    }

    public int findLastVisibleItemPosition() {
        View findOneVisibleChild = findOneVisibleChild(getChildCount() - 1, -1, false, true);
        return findOneVisibleChild == null ? -1 : getPosition(findOneVisibleChild);
    }

    View findOneVisibleChild(int i, int i2, boolean z, boolean z2) {
        ensureLayoutState();
        int startAfterPadding = this.mOrientationHelper.getStartAfterPadding();
        int endAfterPadding = this.mOrientationHelper.getEndAfterPadding();
        int i3 = i2 > i ? 1 : -1;
        View view = null;
        while (i != i2) {
            View childAt = getChildAt(i);
            int decoratedStart = this.mOrientationHelper.getDecoratedStart(childAt);
            int decoratedEnd = this.mOrientationHelper.getDecoratedEnd(childAt);
            if (decoratedStart < endAfterPadding && decoratedEnd > startAfterPadding) {
                if (!z) {
                    return childAt;
                }
                if (decoratedStart >= startAfterPadding && decoratedEnd <= endAfterPadding) {
                    return childAt;
                }
                if (z2 && view == null) {
                    i += i3;
                    view = childAt;
                }
            }
            childAt = view;
            i += i3;
            view = childAt;
        }
        return view;
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
            if (position >= 0 && position < i3) {
                if (!((LayoutParams) childAt.getLayoutParams()).isItemRemoved()) {
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

    public View findViewByPosition(int i) {
        int childCount = getChildCount();
        if (childCount == 0) {
            return null;
        }
        int position = i - getPosition(getChildAt(0));
        if (position >= 0 && position < childCount) {
            View childAt = getChildAt(position);
            if (getPosition(childAt) == i) {
                return childAt;
            }
        }
        return super.findViewByPosition(i);
    }

    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    protected int getExtraLayoutSpace(State state) {
        return state.hasTargetScrollPosition() ? this.mOrientationHelper.getTotalSpace() : 0;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public boolean getRecycleChildrenOnDetach() {
        return this.mRecycleChildrenOnDetach;
    }

    public boolean getReverseLayout() {
        return this.mReverseLayout;
    }

    public boolean getStackFromEnd() {
        return this.mStackFromEnd;
    }

    protected boolean isLayoutRTL() {
        return getLayoutDirection() == 1;
    }

    public boolean isSmoothScrollbarEnabled() {
        return this.mSmoothScrollbarEnabled;
    }

    void layoutChunk(Recycler recycler, State state, LayoutState layoutState, LayoutChunkResult layoutChunkResult) {
        View next = layoutState.next(recycler);
        if (next == null) {
            layoutChunkResult.mFinished = true;
            return;
        }
        int width;
        int decoratedMeasurementInOther;
        int i;
        int i2;
        LayoutParams layoutParams = (LayoutParams) next.getLayoutParams();
        if (layoutState.mScrapList == null) {
            if (this.mShouldReverseLayout == (layoutState.mLayoutDirection == -1)) {
                addView(next);
            } else {
                addView(next, 0);
            }
        } else {
            if (this.mShouldReverseLayout == (layoutState.mLayoutDirection == -1)) {
                addDisappearingView(next);
            } else {
                addDisappearingView(next, 0);
            }
        }
        measureChildWithMargins(next, 0, 0);
        layoutChunkResult.mConsumed = this.mOrientationHelper.getDecoratedMeasurement(next);
        if (this.mOrientation == 1) {
            if (isLayoutRTL()) {
                width = getWidth() - getPaddingRight();
                decoratedMeasurementInOther = width - this.mOrientationHelper.getDecoratedMeasurementInOther(next);
            } else {
                decoratedMeasurementInOther = getPaddingLeft();
                width = this.mOrientationHelper.getDecoratedMeasurementInOther(next) + decoratedMeasurementInOther;
            }
            if (layoutState.mLayoutDirection == -1) {
                i = layoutState.mOffset;
                i2 = width;
                width = decoratedMeasurementInOther;
                decoratedMeasurementInOther = layoutState.mOffset - layoutChunkResult.mConsumed;
            } else {
                i = layoutState.mOffset + layoutChunkResult.mConsumed;
                i2 = width;
                width = decoratedMeasurementInOther;
                decoratedMeasurementInOther = layoutState.mOffset;
            }
        } else {
            decoratedMeasurementInOther = getPaddingTop();
            width = this.mOrientationHelper.getDecoratedMeasurementInOther(next) + decoratedMeasurementInOther;
            if (layoutState.mLayoutDirection == -1) {
                i2 = layoutState.mOffset;
                i = width;
                width = layoutState.mOffset - layoutChunkResult.mConsumed;
            } else {
                i2 = layoutState.mOffset + layoutChunkResult.mConsumed;
                i = width;
                width = layoutState.mOffset;
            }
        }
        layoutDecorated(next, width + layoutParams.leftMargin, layoutParams.topMargin + decoratedMeasurementInOther, i2 - layoutParams.rightMargin, i - layoutParams.bottomMargin);
        if (layoutParams.isItemRemoved() || layoutParams.isItemChanged()) {
            layoutChunkResult.mIgnoreConsumed = true;
        }
        layoutChunkResult.mFocusable = next.isFocusable();
    }

    void onAnchorReady(Recycler recycler, State state, AnchorInfo anchorInfo, int i) {
    }

    public void onDetachedFromWindow(RecyclerView recyclerView, Recycler recycler) {
        super.onDetachedFromWindow(recyclerView, recycler);
        if (this.mRecycleChildrenOnDetach) {
            removeAndRecycleAllViews(recycler);
            recycler.clear();
        }
    }

    public View onFocusSearchFailed(View view, int i, Recycler recycler, State state) {
        resolveShouldLayoutReverse();
        if (getChildCount() != 0) {
            int convertFocusDirectionToLayoutDirection = convertFocusDirectionToLayoutDirection(i);
            if (convertFocusDirectionToLayoutDirection != Integer.MIN_VALUE) {
                ensureLayoutState();
                View findReferenceChildClosestToStart = convertFocusDirectionToLayoutDirection == -1 ? findReferenceChildClosestToStart(recycler, state) : findReferenceChildClosestToEnd(recycler, state);
                if (findReferenceChildClosestToStart != null) {
                    ensureLayoutState();
                    updateLayoutState(convertFocusDirectionToLayoutDirection, (int) (MAX_SCROLL_FACTOR * ((float) this.mOrientationHelper.getTotalSpace())), false, state);
                    this.mLayoutState.mScrollingOffset = Integer.MIN_VALUE;
                    this.mLayoutState.mRecycle = false;
                    fill(recycler, this.mLayoutState, state, true);
                    View childClosestToStart = convertFocusDirectionToLayoutDirection == -1 ? getChildClosestToStart() : getChildClosestToEnd();
                    if (childClosestToStart != findReferenceChildClosestToStart && childClosestToStart.isFocusable()) {
                        return childClosestToStart;
                    }
                }
            }
        }
        return null;
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (getChildCount() > 0) {
            AccessibilityRecordCompat asRecord = AccessibilityEventCompat.asRecord(accessibilityEvent);
            asRecord.setFromIndex(findFirstVisibleItemPosition());
            asRecord.setToIndex(findLastVisibleItemPosition());
        }
    }

    public void onLayoutChildren(Recycler recycler, State state) {
        if (!(this.mPendingSavedState == null && this.mPendingScrollPosition == -1) && state.getItemCount() == 0) {
            removeAndRecycleAllViews(recycler);
            return;
        }
        int i;
        int endAfterPadding;
        if (this.mPendingSavedState != null && this.mPendingSavedState.hasValidAnchor()) {
            this.mPendingScrollPosition = this.mPendingSavedState.mAnchorPosition;
        }
        ensureLayoutState();
        this.mLayoutState.mRecycle = false;
        resolveShouldLayoutReverse();
        this.mAnchorInfo.reset();
        this.mAnchorInfo.mLayoutFromEnd = this.mShouldReverseLayout ^ this.mStackFromEnd;
        updateAnchorInfoForLayout(recycler, state, this.mAnchorInfo);
        int extraLayoutSpace = getExtraLayoutSpace(state);
        if (this.mLayoutState.mLastScrollDelta >= 0) {
            i = extraLayoutSpace;
            extraLayoutSpace = 0;
        } else {
            i = 0;
        }
        extraLayoutSpace += this.mOrientationHelper.getStartAfterPadding();
        i += this.mOrientationHelper.getEndPadding();
        if (!(!state.isPreLayout() || this.mPendingScrollPosition == -1 || this.mPendingScrollPositionOffset == Integer.MIN_VALUE)) {
            View findViewByPosition = findViewByPosition(this.mPendingScrollPosition);
            if (findViewByPosition != null) {
                endAfterPadding = this.mShouldReverseLayout ? (this.mOrientationHelper.getEndAfterPadding() - this.mOrientationHelper.getDecoratedEnd(findViewByPosition)) - this.mPendingScrollPositionOffset : this.mPendingScrollPositionOffset - (this.mOrientationHelper.getDecoratedStart(findViewByPosition) - this.mOrientationHelper.getStartAfterPadding());
                if (endAfterPadding > 0) {
                    extraLayoutSpace += endAfterPadding;
                } else {
                    i -= endAfterPadding;
                }
            }
        }
        endAfterPadding = this.mAnchorInfo.mLayoutFromEnd ? this.mShouldReverseLayout ? 1 : -1 : this.mShouldReverseLayout ? -1 : 1;
        onAnchorReady(recycler, state, this.mAnchorInfo, endAfterPadding);
        detachAndScrapAttachedViews(recycler);
        this.mLayoutState.mInfinite = this.mOrientationHelper.getMode() == 0;
        this.mLayoutState.mIsPreLayout = state.isPreLayout();
        int i2;
        if (this.mAnchorInfo.mLayoutFromEnd) {
            updateLayoutStateToFillStart(this.mAnchorInfo);
            this.mLayoutState.mExtra = extraLayoutSpace;
            fill(recycler, this.mLayoutState, state, false);
            extraLayoutSpace = this.mLayoutState.mOffset;
            endAfterPadding = this.mLayoutState.mCurrentPosition;
            if (this.mLayoutState.mAvailable > 0) {
                i += this.mLayoutState.mAvailable;
            }
            updateLayoutStateToFillEnd(this.mAnchorInfo);
            this.mLayoutState.mExtra = i;
            LayoutState layoutState = this.mLayoutState;
            layoutState.mCurrentPosition += this.mLayoutState.mItemDirection;
            fill(recycler, this.mLayoutState, state, false);
            i = this.mLayoutState.mOffset;
            if (this.mLayoutState.mAvailable > 0) {
                i2 = this.mLayoutState.mAvailable;
                updateLayoutStateToFillStart(endAfterPadding, extraLayoutSpace);
                this.mLayoutState.mExtra = i2;
                fill(recycler, this.mLayoutState, state, false);
                extraLayoutSpace = this.mLayoutState.mOffset;
            }
        } else {
            updateLayoutStateToFillEnd(this.mAnchorInfo);
            this.mLayoutState.mExtra = i;
            fill(recycler, this.mLayoutState, state, false);
            i = this.mLayoutState.mOffset;
            endAfterPadding = this.mLayoutState.mCurrentPosition;
            if (this.mLayoutState.mAvailable > 0) {
                extraLayoutSpace += this.mLayoutState.mAvailable;
            }
            updateLayoutStateToFillStart(this.mAnchorInfo);
            this.mLayoutState.mExtra = extraLayoutSpace;
            LayoutState layoutState2 = this.mLayoutState;
            layoutState2.mCurrentPosition += this.mLayoutState.mItemDirection;
            fill(recycler, this.mLayoutState, state, false);
            extraLayoutSpace = this.mLayoutState.mOffset;
            if (this.mLayoutState.mAvailable > 0) {
                i2 = this.mLayoutState.mAvailable;
                updateLayoutStateToFillEnd(endAfterPadding, i);
                this.mLayoutState.mExtra = i2;
                fill(recycler, this.mLayoutState, state, false);
                i = this.mLayoutState.mOffset;
            }
        }
        if (getChildCount() > 0) {
            int fixLayoutStartGap;
            if ((this.mShouldReverseLayout ^ this.mStackFromEnd) != 0) {
                endAfterPadding = fixLayoutEndGap(i, recycler, state, true);
                extraLayoutSpace += endAfterPadding;
                fixLayoutStartGap = fixLayoutStartGap(extraLayoutSpace, recycler, state, false);
                extraLayoutSpace += fixLayoutStartGap;
                i = (i + endAfterPadding) + fixLayoutStartGap;
            } else {
                endAfterPadding = fixLayoutStartGap(extraLayoutSpace, recycler, state, true);
                i += endAfterPadding;
                fixLayoutStartGap = fixLayoutEndGap(i, recycler, state, false);
                extraLayoutSpace = (extraLayoutSpace + endAfterPadding) + fixLayoutStartGap;
                i += fixLayoutStartGap;
            }
        }
        layoutForPredictiveAnimations(recycler, state, extraLayoutSpace, i);
        if (!state.isPreLayout()) {
            this.mPendingScrollPosition = -1;
            this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
            this.mOrientationHelper.onLayoutComplete();
        }
        this.mLastStackFromEnd = this.mStackFromEnd;
        this.mPendingSavedState = null;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            this.mPendingSavedState = (SavedState) parcelable;
            requestLayout();
        }
    }

    public Parcelable onSaveInstanceState() {
        if (this.mPendingSavedState != null) {
            return new SavedState(this.mPendingSavedState);
        }
        Parcelable savedState = new SavedState();
        if (getChildCount() > 0) {
            ensureLayoutState();
            boolean z = this.mLastStackFromEnd ^ this.mShouldReverseLayout;
            savedState.mAnchorLayoutFromEnd = z;
            View childClosestToEnd;
            if (z) {
                childClosestToEnd = getChildClosestToEnd();
                savedState.mAnchorOffset = this.mOrientationHelper.getEndAfterPadding() - this.mOrientationHelper.getDecoratedEnd(childClosestToEnd);
                savedState.mAnchorPosition = getPosition(childClosestToEnd);
                return savedState;
            }
            childClosestToEnd = getChildClosestToStart();
            savedState.mAnchorPosition = getPosition(childClosestToEnd);
            savedState.mAnchorOffset = this.mOrientationHelper.getDecoratedStart(childClosestToEnd) - this.mOrientationHelper.getStartAfterPadding();
            return savedState;
        }
        savedState.invalidateAnchor();
        return savedState;
    }

    public void prepareForDrop(View view, View view2, int i, int i2) {
        assertNotInLayoutOrScroll("Cannot drop a view during a scroll or layout calculation");
        ensureLayoutState();
        resolveShouldLayoutReverse();
        int position = getPosition(view);
        int position2 = getPosition(view2);
        if (position < position2) {
            Object obj = 1;
        } else {
            position = -1;
        }
        if (this.mShouldReverseLayout) {
            if (obj == 1) {
                scrollToPositionWithOffset(position2, this.mOrientationHelper.getEndAfterPadding() - (this.mOrientationHelper.getDecoratedStart(view2) + this.mOrientationHelper.getDecoratedMeasurement(view)));
            } else {
                scrollToPositionWithOffset(position2, this.mOrientationHelper.getEndAfterPadding() - this.mOrientationHelper.getDecoratedEnd(view2));
            }
        } else if (obj == -1) {
            scrollToPositionWithOffset(position2, this.mOrientationHelper.getDecoratedStart(view2));
        } else {
            scrollToPositionWithOffset(position2, this.mOrientationHelper.getDecoratedEnd(view2) - this.mOrientationHelper.getDecoratedMeasurement(view));
        }
    }

    int scrollBy(int i, Recycler recycler, State state) {
        if (getChildCount() == 0 || i == 0) {
            return 0;
        }
        this.mLayoutState.mRecycle = true;
        ensureLayoutState();
        int i2 = i > 0 ? 1 : -1;
        int abs = Math.abs(i);
        updateLayoutState(i2, abs, true, state);
        int fill = this.mLayoutState.mScrollingOffset + fill(recycler, this.mLayoutState, state, false);
        if (fill < 0) {
            return 0;
        }
        if (abs > fill) {
            i = i2 * fill;
        }
        this.mOrientationHelper.offsetChildren(-i);
        this.mLayoutState.mLastScrollDelta = i;
        return i;
    }

    public int scrollHorizontallyBy(int i, Recycler recycler, State state) {
        return this.mOrientation == 1 ? 0 : scrollBy(i, recycler, state);
    }

    public void scrollToPosition(int i) {
        this.mPendingScrollPosition = i;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        if (this.mPendingSavedState != null) {
            this.mPendingSavedState.invalidateAnchor();
        }
        requestLayout();
    }

    public void scrollToPositionWithOffset(int i, int i2) {
        this.mPendingScrollPosition = i;
        this.mPendingScrollPositionOffset = i2;
        if (this.mPendingSavedState != null) {
            this.mPendingSavedState.invalidateAnchor();
        }
        requestLayout();
    }

    public int scrollVerticallyBy(int i, Recycler recycler, State state) {
        return this.mOrientation == 0 ? 0 : scrollBy(i, recycler, state);
    }

    public void setOrientation(int i) {
        if (i == 0 || i == 1) {
            assertNotInLayoutOrScroll(null);
            if (i != this.mOrientation) {
                this.mOrientation = i;
                this.mOrientationHelper = null;
                requestLayout();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("invalid orientation:" + i);
    }

    public void setRecycleChildrenOnDetach(boolean z) {
        this.mRecycleChildrenOnDetach = z;
    }

    public void setReverseLayout(boolean z) {
        assertNotInLayoutOrScroll(null);
        if (z != this.mReverseLayout) {
            this.mReverseLayout = z;
            requestLayout();
        }
    }

    public void setSmoothScrollbarEnabled(boolean z) {
        this.mSmoothScrollbarEnabled = z;
    }

    public void setStackFromEnd(boolean z) {
        assertNotInLayoutOrScroll(null);
        if (this.mStackFromEnd != z) {
            this.mStackFromEnd = z;
            requestLayout();
        }
    }

    boolean shouldMeasureTwice() {
        return (getHeightMode() == 1073741824 || getWidthMode() == 1073741824 || !hasFlexibleChildInBothOrientations()) ? false : true;
    }

    public void smoothScrollToPosition(RecyclerView recyclerView, State state, int i) {
        SmoothScroller anonymousClass1 = new LinearSmoothScroller(recyclerView.getContext()) {
            public PointF computeScrollVectorForPosition(int i) {
                return LinearLayoutManager.this.computeScrollVectorForPosition(i);
            }
        };
        anonymousClass1.setTargetPosition(i);
        startSmoothScroll(anonymousClass1);
    }

    public boolean supportsPredictiveItemAnimations() {
        return this.mPendingSavedState == null && this.mLastStackFromEnd == this.mStackFromEnd;
    }

    void validateChildOrder() {
        boolean z = true;
        Log.d(TAG, "validating child count " + getChildCount());
        if (getChildCount() > 0) {
            int position = getPosition(getChildAt(0));
            int decoratedStart = this.mOrientationHelper.getDecoratedStart(getChildAt(0));
            int i;
            View childAt;
            int position2;
            int decoratedStart2;
            StringBuilder stringBuilder;
            if (this.mShouldReverseLayout) {
                i = 1;
                while (i < getChildCount()) {
                    childAt = getChildAt(i);
                    position2 = getPosition(childAt);
                    decoratedStart2 = this.mOrientationHelper.getDecoratedStart(childAt);
                    if (position2 < position) {
                        logChildren();
                        stringBuilder = new StringBuilder("detected invalid position. loc invalid? ");
                        if (decoratedStart2 >= decoratedStart) {
                            z = false;
                        }
                        throw new RuntimeException(stringBuilder.append(z).toString());
                    } else if (decoratedStart2 > decoratedStart) {
                        logChildren();
                        throw new RuntimeException("detected invalid location");
                    } else {
                        i++;
                    }
                }
                return;
            }
            i = 1;
            while (i < getChildCount()) {
                childAt = getChildAt(i);
                position2 = getPosition(childAt);
                decoratedStart2 = this.mOrientationHelper.getDecoratedStart(childAt);
                if (position2 < position) {
                    logChildren();
                    stringBuilder = new StringBuilder("detected invalid position. loc invalid? ");
                    if (decoratedStart2 >= decoratedStart) {
                        z = false;
                    }
                    throw new RuntimeException(stringBuilder.append(z).toString());
                } else if (decoratedStart2 < decoratedStart) {
                    logChildren();
                    throw new RuntimeException("detected invalid location");
                } else {
                    i++;
                }
            }
        }
    }
}
