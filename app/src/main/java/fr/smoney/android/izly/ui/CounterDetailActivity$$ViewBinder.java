package fr.smoney.android.izly.ui;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import fr.smoney.android.izly.R;

public class CounterDetailActivity$$ViewBinder<T extends CounterDetailActivity> implements ViewBinder<T> {
    public void bind(Finder finder, T t, Object obj) {
        t.detailCounterList = (ListView) finder.castView((View) finder.findRequiredView(obj, R.id.listView, "field 'detailCounterList'"), R.id.listView, "field 'detailCounterList'");
        t.headerCounterDetail = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.header_counter_detail, "field 'headerCounterDetail'"), R.id.header_counter_detail, "field 'headerCounterDetail'");
        t.placeHolderCounter = (View) finder.findRequiredView(obj, R.id.rl_placeholder_counter, "field 'placeHolderCounter'");
    }

    public void unbind(T t) {
        t.detailCounterList = null;
        t.headerCounterDetail = null;
        t.placeHolderCounter = null;
    }
}
