package fr.smoney.android.izly.ui.fragment;

import android.view.View;
import android.widget.ListView;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import fr.smoney.android.izly.R;

public class AskSendMoneyFragment$$ViewBinder<T extends AskSendMoneyFragment> implements ViewBinder<T> {
    public void bind(Finder finder, T t, Object obj) {
        t.mListView = (ListView) finder.castView((View) finder.findRequiredView(obj, R.id.listView, "field 'mListView'"), R.id.listView, "field 'mListView'");
    }

    public void unbind(T t) {
        t.mListView = null;
    }
}
