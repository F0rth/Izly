package fr.smoney.android.izly.adapter;

import android.view.View;
import android.widget.ProgressBar;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import fr.smoney.android.izly.R;

public class CityEventAdapter$LoadingViewHolder$$ViewBinder<T extends LoadingViewHolder> implements ViewBinder<T> {
    public void bind(Finder finder, T t, Object obj) {
        t.mProgressBar = (ProgressBar) finder.castView((View) finder.findRequiredView(obj, R.id.progress_bar, "field 'mProgressBar'"), R.id.progress_bar, "field 'mProgressBar'");
    }

    public void unbind(T t) {
        t.mProgressBar = null;
    }
}
