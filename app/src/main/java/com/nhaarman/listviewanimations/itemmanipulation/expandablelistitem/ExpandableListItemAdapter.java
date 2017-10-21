package com.nhaarman.listviewanimations.itemmanipulation.expandablelistitem;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.nhaarman.listviewanimations.ArrayAdapter;
import com.nhaarman.listviewanimations.util.AdapterViewUtil;
import com.nhaarman.listviewanimations.util.ListViewWrapper;
import com.nhaarman.listviewanimations.util.ListViewWrapperSetter;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import org.spongycastle.asn1.cmp.PKIFailureInfo;

public abstract class ExpandableListItemAdapter<T> extends ArrayAdapter<T> implements ListViewWrapperSetter {
    private static final int DEFAULTCONTENTPARENTRESID = 10001;
    private static final int DEFAULTTITLEPARENTRESID = 10000;
    private int mActionViewResId;
    private final int mContentParentResId;
    @NonNull
    private final Context mContext;
    @Nullable
    private ExpandCollapseListener mExpandCollapseListener;
    @NonNull
    private final List<Long> mExpandedIds;
    private int mLimit;
    @Nullable
    private ListViewWrapper mListViewWrapper;
    private final int mTitleParentResId;
    private int mViewLayoutResId;

    static class ExpandCollapseHelper {
        private ExpandCollapseHelper() {
        }

        public static void animateCollapsing(final View view) {
            ValueAnimator createHeightAnimator = createHeightAnimator(view, view.getHeight(), 0);
            createHeightAnimator.addListener(new AnimatorListenerAdapter() {
                public final void onAnimationEnd(Animator animator) {
                    view.setVisibility(8);
                }
            });
            createHeightAnimator.start();
        }

        public static void animateExpanding(@NonNull final View view, @NonNull final ListViewWrapper listViewWrapper) {
            view.setVisibility(0);
            View view2 = (View) view.getParent();
            view.measure(MeasureSpec.makeMeasureSpec((view2.getMeasuredWidth() - view2.getPaddingLeft()) - view2.getPaddingRight(), PKIFailureInfo.systemUnavail), MeasureSpec.makeMeasureSpec(0, 0));
            ValueAnimator createHeightAnimator = createHeightAnimator(view, 0, view.getMeasuredHeight());
            createHeightAnimator.addUpdateListener(new AnimatorUpdateListener() {
                final int listViewBottomPadding = listViewWrapper.getListView().getPaddingBottom();
                final int listViewHeight = listViewWrapper.getListView().getHeight();
                final View v = ExpandCollapseHelper.findDirectChild(view, listViewWrapper.getListView());

                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int bottom = this.v.getBottom();
                    if (bottom > this.listViewHeight) {
                        int top = this.v.getTop();
                        if (top > 0) {
                            listViewWrapper.smoothScrollBy(Math.min((bottom - this.listViewHeight) + this.listViewBottomPadding, top), 0);
                        }
                    }
                }
            });
            createHeightAnimator.start();
        }

        public static ValueAnimator createHeightAnimator(final View view, int i, int i2) {
            ValueAnimator ofInt = ValueAnimator.ofInt(i, i2);
            ofInt.addUpdateListener(new AnimatorUpdateListener() {
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                    LayoutParams layoutParams = view.getLayoutParams();
                    layoutParams.height = intValue;
                    view.setLayoutParams(layoutParams);
                }
            });
            return ofInt;
        }

        @NonNull
        private static View findDirectChild(@NonNull View view, @NonNull ViewGroup viewGroup) {
            for (View view2 = (View) view.getParent(); !view2.equals(viewGroup); view2 = (View) view2.getParent()) {
                view = view2;
            }
            return view;
        }
    }

    public interface ExpandCollapseListener {
        void onItemCollapsed(int i);

        void onItemExpanded(int i);
    }

    static class RootView extends LinearLayout {
        private ViewGroup mContentViewGroup;
        private ViewGroup mTitleViewGroup;

        private RootView(@NonNull Context context) {
            super(context);
            init();
        }

        private void init() {
            setOrientation(1);
            this.mTitleViewGroup = new FrameLayout(getContext());
            this.mTitleViewGroup.setId(10000);
            addView(this.mTitleViewGroup);
            this.mContentViewGroup = new FrameLayout(getContext());
            this.mContentViewGroup.setId(ExpandableListItemAdapter.DEFAULTCONTENTPARENTRESID);
            addView(this.mContentViewGroup);
        }
    }

    class TitleViewOnClickListener implements OnClickListener {
        private final View mContentParent;

        private TitleViewOnClickListener(View view) {
            this.mContentParent = view;
        }

        public void onClick(View view) {
            ExpandableListItemAdapter.this.toggle(this.mContentParent);
        }
    }

    static class ViewHolder {
        ViewGroup contentParent;
        View contentView;
        ViewGroup titleParent;
        View titleView;

        private ViewHolder() {
        }
    }

    protected ExpandableListItemAdapter(@NonNull Context context) {
        this(context, null);
    }

    protected ExpandableListItemAdapter(@NonNull Context context, int i, int i2, int i3) {
        this(context, i, i2, i3, null);
    }

    protected ExpandableListItemAdapter(@NonNull Context context, int i, int i2, int i3, @Nullable List<T> list) {
        super(list);
        this.mContext = context;
        this.mViewLayoutResId = i;
        this.mTitleParentResId = i2;
        this.mContentParentResId = i3;
        this.mExpandedIds = new ArrayList();
    }

    protected ExpandableListItemAdapter(@NonNull Context context, @Nullable List<T> list) {
        super(list);
        this.mContext = context;
        this.mTitleParentResId = 10000;
        this.mContentParentResId = DEFAULTCONTENTPARENTRESID;
        this.mExpandedIds = new ArrayList();
    }

    @NonNull
    private ViewGroup createView(@NonNull ViewGroup viewGroup) {
        return this.mViewLayoutResId == 0 ? new RootView(this.mContext) : (ViewGroup) LayoutInflater.from(this.mContext).inflate(this.mViewLayoutResId, viewGroup, false);
    }

    private int findPositionForId(long j) {
        for (int i = 0; i < getCount(); i++) {
            if (getItemId(i) == j) {
                return i;
            }
        }
        return -1;
    }

    @Nullable
    private View findViewForPosition(int i) {
        if (this.mListViewWrapper == null) {
            throw new IllegalStateException("Call setAbsListView on this ExpanableListItemAdapter!");
        }
        View view = null;
        int i2 = 0;
        while (i2 < this.mListViewWrapper.getChildCount() && view == null) {
            View childAt = this.mListViewWrapper.getChildAt(i2);
            if (childAt == null || AdapterViewUtil.getPositionForView(this.mListViewWrapper, childAt) != i) {
                childAt = view;
            }
            i2++;
            view = childAt;
        }
        return view;
    }

    @Nullable
    private View getContentParent(int i) {
        View findViewForPosition = findViewForPosition(i);
        if (findViewForPosition != null) {
            Object tag = findViewForPosition.getTag();
            if (tag instanceof ViewHolder) {
                return ((ViewHolder) tag).contentParent;
            }
        }
        return null;
    }

    private void toggle(@NonNull View view) {
        int i = 1;
        if (this.mListViewWrapper == null) {
            throw new IllegalStateException("No ListView set!");
        }
        Long l;
        int findPositionForId;
        int i2 = view.getVisibility() == 0 ? 1 : 0;
        if (i2 != 0 || this.mLimit <= 0 || this.mExpandedIds.size() < this.mLimit) {
            i = 0;
        }
        if (i != 0) {
            l = (Long) this.mExpandedIds.get(0);
            findPositionForId = findPositionForId(l.longValue());
            View contentParent = getContentParent(findPositionForId);
            if (contentParent != null) {
                ExpandCollapseHelper.animateCollapsing(contentParent);
            }
            this.mExpandedIds.remove(l);
            if (this.mExpandCollapseListener != null) {
                this.mExpandCollapseListener.onItemCollapsed(findPositionForId);
            }
        }
        l = (Long) view.getTag();
        findPositionForId = findPositionForId(l.longValue());
        if (i2 != 0) {
            ExpandCollapseHelper.animateCollapsing(view);
            this.mExpandedIds.remove(l);
            if (this.mExpandCollapseListener != null) {
                this.mExpandCollapseListener.onItemCollapsed(findPositionForId);
                return;
            }
            return;
        }
        ExpandCollapseHelper.animateExpanding(view, this.mListViewWrapper);
        this.mExpandedIds.add(l);
        if (this.mExpandCollapseListener != null) {
            this.mExpandCollapseListener.onItemExpanded(findPositionForId);
        }
    }

    public void collapse(int i) {
        if (this.mExpandedIds.contains(Long.valueOf(getItemId(i)))) {
            toggle(i);
        }
    }

    public void expand(int i) {
        if (!this.mExpandedIds.contains(Long.valueOf(getItemId(i)))) {
            toggle(i);
        }
    }

    @Nullable
    public View getContentView(int i) {
        View findViewForPosition = findViewForPosition(i);
        if (findViewForPosition != null) {
            Object tag = findViewForPosition.getTag();
            if (tag instanceof ViewHolder) {
                return ((ViewHolder) tag).contentView;
            }
        }
        return null;
    }

    @NonNull
    public abstract View getContentView(int i, @Nullable View view, @NonNull ViewGroup viewGroup);

    @Nullable
    public View getTitleView(int i) {
        View findViewForPosition = findViewForPosition(i);
        if (findViewForPosition != null) {
            Object tag = findViewForPosition.getTag();
            if (tag instanceof ViewHolder) {
                return ((ViewHolder) tag).titleView;
            }
        }
        return null;
    }

    @NonNull
    public abstract View getTitleView(int i, @Nullable View view, @NonNull ViewGroup viewGroup);

    @NonNull
    public View getView(int i, @Nullable View view, @NonNull ViewGroup viewGroup) {
        ViewHolder viewHolder;
        view = (ViewGroup) view;
        if (view == null) {
            view = createView(viewGroup);
            ViewHolder viewHolder2 = new ViewHolder();
            viewHolder2.titleParent = (ViewGroup) view.findViewById(this.mTitleParentResId);
            viewHolder2.contentParent = (ViewGroup) view.findViewById(this.mContentParentResId);
            view.setTag(viewHolder2);
            viewHolder = viewHolder2;
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        View titleView = getTitleView(i, viewHolder.titleView, viewHolder.titleParent);
        if (!titleView.equals(viewHolder.titleView)) {
            viewHolder.titleParent.removeAllViews();
            viewHolder.titleParent.addView(titleView);
            if (this.mActionViewResId == 0) {
                view.setOnClickListener(new TitleViewOnClickListener(viewHolder.contentParent));
            } else {
                view.findViewById(this.mActionViewResId).setOnClickListener(new TitleViewOnClickListener(viewHolder.contentParent));
            }
        }
        viewHolder.titleView = titleView;
        titleView = getContentView(i, viewHolder.contentView, viewHolder.contentParent);
        if (!titleView.equals(viewHolder.contentView)) {
            viewHolder.contentParent.removeAllViews();
            viewHolder.contentParent.addView(titleView);
        }
        viewHolder.contentView = titleView;
        viewHolder.contentParent.setVisibility(this.mExpandedIds.contains(Long.valueOf(getItemId(i))) ? 0 : 8);
        viewHolder.contentParent.setTag(Long.valueOf(getItemId(i)));
        LayoutParams layoutParams = viewHolder.contentParent.getLayoutParams();
        layoutParams.height = -2;
        viewHolder.contentParent.setLayoutParams(layoutParams);
        return view;
    }

    public boolean isExpanded(int i) {
        return this.mExpandedIds.contains(Long.valueOf(getItemId(i)));
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        Collection hashSet = new HashSet(this.mExpandedIds);
        for (int i = 0; i < getCount(); i++) {
            hashSet.remove(Long.valueOf(getItemId(i)));
        }
        this.mExpandedIds.removeAll(hashSet);
    }

    public void setActionViewResId(int i) {
        this.mActionViewResId = i;
    }

    public void setExpandCollapseListener(@Nullable ExpandCollapseListener expandCollapseListener) {
        this.mExpandCollapseListener = expandCollapseListener;
    }

    public void setLimit(int i) {
        this.mLimit = i;
        this.mExpandedIds.clear();
        notifyDataSetChanged();
    }

    public void setListViewWrapper(@NonNull ListViewWrapper listViewWrapper) {
        this.mListViewWrapper = listViewWrapper;
    }

    public void toggle(int i) {
        long itemId = getItemId(i);
        boolean contains = this.mExpandedIds.contains(Long.valueOf(itemId));
        View contentParent = getContentParent(i);
        if (contentParent != null) {
            toggle(contentParent);
        }
        if (contentParent == null && contains) {
            this.mExpandedIds.remove(Long.valueOf(itemId));
        } else if (contentParent == null) {
            this.mExpandedIds.add(Long.valueOf(itemId));
        }
    }
}
