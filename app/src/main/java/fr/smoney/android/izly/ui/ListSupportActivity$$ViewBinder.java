package fr.smoney.android.izly.ui;

import android.view.View;
import android.widget.ListView;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import fr.smoney.android.izly.R;

public class ListSupportActivity$$ViewBinder<T extends ListSupportActivity> implements ViewBinder<T> {
    public void bind(Finder finder, T t, Object obj) {
        t.mSupportsList = (ListView) finder.castView((View) finder.findRequiredView(obj, R.id.listView, "field 'mSupportsList'"), R.id.listView, "field 'mSupportsList'");
        t.noSupportsView = (View) finder.findRequiredView(obj, R.id.no_supports_view, "field 'noSupportsView'");
    }

    public void unbind(T t) {
        t.mSupportsList = null;
        t.noSupportsView = null;
    }
}
