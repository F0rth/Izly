package com.handmark.pulltorefresh.library;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.AnimationStyle;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Orientation;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.internal.EmptyViewMethodAccessor;
import com.handmark.pulltorefresh.library.internal.LoadingLayout;

public class PullToRefreshListView extends PullToRefreshAdapterViewBase<ListView> {
    private LoadingLayout mFooterLoadingView;
    private LoadingLayout mHeaderLoadingView;
    private boolean mListViewExtrasEnabled;
    private FrameLayout mLvFooterLoadingFrame;

    public class InternalListView extends ListView implements EmptyViewMethodAccessor {
        private boolean mAddedLvFooter = false;

        public InternalListView(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        protected void dispatchDraw(Canvas canvas) {
            try {
                super.dispatchDraw(canvas);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }

        public boolean dispatchTouchEvent(MotionEvent motionEvent) {
            try {
                return super.dispatchTouchEvent(motionEvent);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
                return false;
            }
        }

        public void setAdapter(ListAdapter listAdapter) {
            if (!(PullToRefreshListView.this.mLvFooterLoadingFrame == null || this.mAddedLvFooter)) {
                addFooterView(PullToRefreshListView.this.mLvFooterLoadingFrame, null, false);
                this.mAddedLvFooter = true;
            }
            super.setAdapter(listAdapter);
        }

        public void setEmptyView(View view) {
            PullToRefreshListView.this.setEmptyView(view);
        }

        public void setEmptyViewInternal(View view) {
            super.setEmptyView(view);
        }
    }

    @TargetApi(9)
    final class InternalListViewSDK9 extends InternalListView {
        public InternalListViewSDK9(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        protected final boolean overScrollBy(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z) {
            boolean overScrollBy = super.overScrollBy(i, i2, i3, i4, i5, i6, i7, i8, z);
            OverscrollHelper.overScrollBy(PullToRefreshListView.this, i, i3, i2, i4, z);
            return overScrollBy;
        }
    }

    public PullToRefreshListView(Context context) {
        super(context);
    }

    public PullToRefreshListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public PullToRefreshListView(Context context, Mode mode) {
        super(context, mode);
    }

    public PullToRefreshListView(Context context, Mode mode, AnimationStyle animationStyle) {
        super(context, mode, animationStyle);
    }

    protected ListView createListView(Context context, AttributeSet attributeSet) {
        return VERSION.SDK_INT >= 9 ? new InternalListViewSDK9(context, attributeSet) : new InternalListView(context, attributeSet);
    }

    protected LoadingLayoutProxy createLoadingLayoutProxy(boolean z, boolean z2) {
        LoadingLayoutProxy createLoadingLayoutProxy = super.createLoadingLayoutProxy(z, z2);
        if (this.mListViewExtrasEnabled) {
            Mode mode = getMode();
            if (z && mode.showHeaderLoadingLayout()) {
                createLoadingLayoutProxy.addLayout(this.mHeaderLoadingView);
            }
            if (z2 && mode.showFooterLoadingLayout()) {
                createLoadingLayoutProxy.addLayout(this.mFooterLoadingView);
            }
        }
        return createLoadingLayoutProxy;
    }

    protected ListView createRefreshableView(Context context, AttributeSet attributeSet) {
        ListView createListView = createListView(context, attributeSet);
        createListView.setId(16908298);
        return createListView;
    }

    public final Orientation getPullToRefreshScrollDirection() {
        return Orientation.VERTICAL;
    }

    protected void handleStyledAttributes(TypedArray typedArray) {
        super.handleStyledAttributes(typedArray);
        this.mListViewExtrasEnabled = typedArray.getBoolean(R.styleable.PullToRefresh_ptrListViewExtrasEnabled, true);
        if (this.mListViewExtrasEnabled) {
            LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2, 1);
            View frameLayout = new FrameLayout(getContext());
            this.mHeaderLoadingView = createLoadingLayout(getContext(), Mode.PULL_FROM_START, typedArray);
            this.mHeaderLoadingView.setVisibility(8);
            frameLayout.addView(this.mHeaderLoadingView, layoutParams);
            ((ListView) this.mRefreshableView).addHeaderView(frameLayout, null, false);
            this.mLvFooterLoadingFrame = new FrameLayout(getContext());
            this.mFooterLoadingView = createLoadingLayout(getContext(), Mode.PULL_FROM_END, typedArray);
            this.mFooterLoadingView.setVisibility(8);
            this.mLvFooterLoadingFrame.addView(this.mFooterLoadingView, layoutParams);
            if (!typedArray.hasValue(R.styleable.PullToRefresh_ptrScrollingWhileRefreshingEnabled)) {
                setScrollingWhileRefreshingEnabled(true);
            }
        }
    }

    protected void onRefreshing(boolean z) {
        ListAdapter adapter = ((ListView) this.mRefreshableView).getAdapter();
        if (!this.mListViewExtrasEnabled || !getShowViewWhileRefreshing() || adapter == null || adapter.isEmpty()) {
            super.onRefreshing(z);
            return;
        }
        LoadingLayout loadingLayout;
        int scrollY;
        LoadingLayout loadingLayout2;
        int count;
        LoadingLayout loadingLayout3;
        super.onRefreshing(false);
        switch (getCurrentMode()) {
            case MANUAL_REFRESH_ONLY:
            case PULL_FROM_END:
                LoadingLayout footerLayout = getFooterLayout();
                loadingLayout = this.mFooterLoadingView;
                scrollY = getScrollY() - getFooterSize();
                LoadingLayout loadingLayout4 = this.mHeaderLoadingView;
                loadingLayout2 = loadingLayout;
                loadingLayout = footerLayout;
                count = ((ListView) this.mRefreshableView).getCount() - 1;
                loadingLayout3 = loadingLayout4;
                break;
            default:
                loadingLayout = getHeaderLayout();
                loadingLayout2 = this.mHeaderLoadingView;
                loadingLayout3 = this.mFooterLoadingView;
                scrollY = getScrollY() + getHeaderSize();
                count = 0;
                break;
        }
        loadingLayout.reset();
        loadingLayout.hideAllViews();
        loadingLayout3.setVisibility(8);
        loadingLayout2.setVisibility(0);
        loadingLayout2.refreshing();
        if (z) {
            disableLoadingLayoutVisibilityChanges();
            setHeaderScroll(scrollY);
            ((ListView) this.mRefreshableView).setSelection(count);
            smoothScrollTo(0);
        }
    }

    protected void onReset() {
        Object obj = null;
        if (this.mListViewExtrasEnabled) {
            LoadingLayout footerLayout;
            int footerSize;
            LoadingLayout loadingLayout;
            int i;
            LoadingLayout loadingLayout2;
            switch (getCurrentMode()) {
                case MANUAL_REFRESH_ONLY:
                case PULL_FROM_END:
                    footerLayout = getFooterLayout();
                    loadingLayout2 = this.mFooterLoadingView;
                    int count = ((ListView) this.mRefreshableView).getCount() - 1;
                    footerSize = getFooterSize();
                    if (Math.abs(((ListView) this.mRefreshableView).getLastVisiblePosition() - count) <= 1) {
                        obj = 1;
                    }
                    loadingLayout = footerLayout;
                    footerLayout = loadingLayout2;
                    i = footerSize;
                    footerSize = count;
                    break;
                default:
                    footerLayout = getHeaderLayout();
                    loadingLayout2 = this.mHeaderLoadingView;
                    footerSize = -getHeaderSize();
                    if (Math.abs(((ListView) this.mRefreshableView).getFirstVisiblePosition() + 0) > 1) {
                        loadingLayout = footerLayout;
                        footerLayout = loadingLayout2;
                        i = footerSize;
                        footerSize = 0;
                        break;
                    }
                    loadingLayout = footerLayout;
                    footerLayout = loadingLayout2;
                    i = footerSize;
                    footerSize = 0;
                    obj = 1;
                    break;
            }
            if (footerLayout.getVisibility() == 0) {
                loadingLayout.showInvisibleViews();
                footerLayout.setVisibility(8);
                if (!(obj == null || getState() == State.MANUAL_REFRESHING)) {
                    ((ListView) this.mRefreshableView).setSelection(footerSize);
                    setHeaderScroll(i);
                }
            }
            super.onReset();
            return;
        }
        super.onReset();
    }
}
