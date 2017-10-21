package fr.smoney.android.izly.ui.activity;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import fr.smoney.android.izly.R;

public class CitySelectActivity$$ViewBinder<T extends CitySelectActivity> implements ViewBinder<T> {
    public void bind(Finder finder, T t, Object obj) {
        t.mRecyclerView = (RecyclerView) finder.castView((View) finder.findRequiredView(obj, R.id.recycler_view, "field 'mRecyclerView'"), R.id.recycler_view, "field 'mRecyclerView'");
        t.mToolbar = (Toolbar) finder.castView((View) finder.findRequiredView(obj, R.id.toolbar, "field 'mToolbar'"), R.id.toolbar, "field 'mToolbar'");
    }

    public void unbind(T t) {
        t.mRecyclerView = null;
        t.mToolbar = null;
    }
}
