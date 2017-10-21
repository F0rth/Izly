package fr.smoney.android.izly.ui.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import butterknife.internal.DebouncingOnClickListener;
import fr.smoney.android.izly.R;

public class CityEventActivity$$ViewBinder<T extends CityEventActivity> implements ViewBinder<T> {
    public void bind(Finder finder, final T t, Object obj) {
        t.mToolbar = (Toolbar) finder.castView((View) finder.findRequiredView(obj, R.id.toolbar, "field 'mToolbar'"), R.id.toolbar, "field 'mToolbar'");
        t.mEmptyView = (View) finder.findRequiredView(obj, R.id.empty_view, "field 'mEmptyView'");
        t.mSwipeRefreshLayout = (SwipeRefreshLayout) finder.castView((View) finder.findRequiredView(obj, R.id.swipe_refresh_layout, "field 'mSwipeRefreshLayout'"), R.id.swipe_refresh_layout, "field 'mSwipeRefreshLayout'");
        t.mRecyclerView = (RecyclerView) finder.castView((View) finder.findRequiredView(obj, R.id.recycler_view, "field 'mRecyclerView'"), R.id.recycler_view, "field 'mRecyclerView'");
        t.mEmptyTextView = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.empty_view_icon_msg, "field 'mEmptyTextView'"), R.id.empty_view_icon_msg, "field 'mEmptyTextView'");
        View view = (View) finder.findRequiredView(obj, R.id.refine_filters_btn, "field 'mRefineFiltersButton' and method 'onFiltersClick'");
        t.mRefineFiltersButton = (AppCompatButton) finder.castView(view, R.id.refine_filters_btn, "field 'mRefineFiltersButton'");
        view.setOnClickListener(new DebouncingOnClickListener(this) {
            final /* synthetic */ CityEventActivity$$ViewBinder b;

            public final void doClick(View view) {
                t.onFiltersClick();
            }
        });
        t.mLoader = (View) finder.findRequiredView(obj, R.id.loader, "field 'mLoader'");
    }

    public void unbind(T t) {
        t.mToolbar = null;
        t.mEmptyView = null;
        t.mSwipeRefreshLayout = null;
        t.mRecyclerView = null;
        t.mEmptyTextView = null;
        t.mRefineFiltersButton = null;
        t.mLoader = null;
    }
}
