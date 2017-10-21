package fr.smoney.android.izly.ui.activity;

import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import butterknife.internal.DebouncingOnClickListener;
import fr.smoney.android.izly.R;

public class FilterActivity$$ViewBinder<T extends FilterActivity> implements ViewBinder<T> {
    public void bind(Finder finder, final T t, Object obj) {
        t.mToolbar = (Toolbar) finder.castView((View) finder.findRequiredView(obj, R.id.toolbar, "field 'mToolbar'"), R.id.toolbar, "field 'mToolbar'");
        View view = (View) finder.findRequiredView(obj, R.id.today_button, "field 'mTodayButton' and method 'onTodayButtonClick'");
        t.mTodayButton = (AppCompatButton) finder.castView(view, R.id.today_button, "field 'mTodayButton'");
        view.setOnClickListener(new DebouncingOnClickListener(this) {
            final /* synthetic */ FilterActivity$$ViewBinder b;

            public final void doClick(View view) {
                t.onTodayButtonClick();
            }
        });
        view = (View) finder.findRequiredView(obj, R.id.tomorrow_button, "field 'mTomorrowButton' and method 'onTomorrowButtonClick'");
        t.mTomorrowButton = (AppCompatButton) finder.castView(view, R.id.tomorrow_button, "field 'mTomorrowButton'");
        view.setOnClickListener(new DebouncingOnClickListener(this) {
            final /* synthetic */ FilterActivity$$ViewBinder b;

            public final void doClick(View view) {
                t.onTomorrowButtonClick();
            }
        });
        view = (View) finder.findRequiredView(obj, R.id.sort_popular_button, "field 'mSortPopularButton' and method 'onSortPopularClick'");
        t.mSortPopularButton = (AppCompatButton) finder.castView(view, R.id.sort_popular_button, "field 'mSortPopularButton'");
        view.setOnClickListener(new DebouncingOnClickListener(this) {
            final /* synthetic */ FilterActivity$$ViewBinder b;

            public final void doClick(View view) {
                t.onSortPopularClick();
            }
        });
        view = (View) finder.findRequiredView(obj, R.id.sort_time_button, "field 'mSortTimeButton' and method 'onSortTimeClick'");
        t.mSortTimeButton = (AppCompatButton) finder.castView(view, R.id.sort_time_button, "field 'mSortTimeButton'");
        view.setOnClickListener(new DebouncingOnClickListener(this) {
            final /* synthetic */ FilterActivity$$ViewBinder b;

            public final void doClick(View view) {
                t.onSortTimeClick();
            }
        });
        view = (View) finder.findRequiredView(obj, R.id.sort_distance_button, "field 'mSortDistanceButton' and method 'onSortDistanceClick'");
        t.mSortDistanceButton = (AppCompatButton) finder.castView(view, R.id.sort_distance_button, "field 'mSortDistanceButton'");
        view.setOnClickListener(new DebouncingOnClickListener(this) {
            final /* synthetic */ FilterActivity$$ViewBinder b;

            public final void doClick(View view) {
                t.onSortDistanceClick();
            }
        });
        t.mCategoriesRecyclerView = (RecyclerView) finder.castView((View) finder.findRequiredView(obj, R.id.categories_recycler_view, "field 'mCategoriesRecyclerView'"), R.id.categories_recycler_view, "field 'mCategoriesRecyclerView'");
        t.mLocationIcon = (ImageView) finder.castView((View) finder.findRequiredView(obj, R.id.location_icon, "field 'mLocationIcon'"), R.id.location_icon, "field 'mLocationIcon'");
        t.mToDistanceLabel = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.to_distance_label, "field 'mToDistanceLabel'"), R.id.to_distance_label, "field 'mToDistanceLabel'");
        t.mCustomSeekBar = (SeekBar) finder.castView((View) finder.findRequiredView(obj, R.id.distance_seekbar, "field 'mCustomSeekBar'"), R.id.distance_seekbar, "field 'mCustomSeekBar'");
    }

    public void unbind(T t) {
        t.mToolbar = null;
        t.mTodayButton = null;
        t.mTomorrowButton = null;
        t.mSortPopularButton = null;
        t.mSortTimeButton = null;
        t.mSortDistanceButton = null;
        t.mCategoriesRecyclerView = null;
        t.mLocationIcon = null;
        t.mToDistanceLabel = null;
        t.mCustomSeekBar = null;
    }
}
