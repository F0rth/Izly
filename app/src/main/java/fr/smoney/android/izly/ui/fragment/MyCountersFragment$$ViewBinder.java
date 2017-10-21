package fr.smoney.android.izly.ui.fragment;

import android.view.View;
import android.widget.ListView;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import fr.smoney.android.izly.R;

public class MyCountersFragment$$ViewBinder<T extends MyCountersFragment> implements ViewBinder<T> {
    public void bind(Finder finder, T t, Object obj) {
        t.myCounterFamilyList = (ListView) finder.castView((View) finder.findRequiredView(obj, R.id.listView, "field 'myCounterFamilyList'"), R.id.listView, "field 'myCounterFamilyList'");
    }

    public void unbind(T t) {
        t.myCounterFamilyList = null;
    }
}
