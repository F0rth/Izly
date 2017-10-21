package fr.smoney.android.izly.adapter;

import android.support.v7.widget.AppCompatButton;
import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import fr.smoney.android.izly.R;

public class CityEventAdapter$TomorrowViewHolder$$ViewBinder<T extends TomorrowViewHolder> implements ViewBinder<T> {
    public void bind(Finder finder, T t, Object obj) {
        t.mTomorrowButton = (AppCompatButton) finder.castView((View) finder.findRequiredView(obj, R.id.tomorrow_button, "field 'mTomorrowButton'"), R.id.tomorrow_button, "field 'mTomorrowButton'");
    }

    public void unbind(T t) {
        t.mTomorrowButton = null;
    }
}
