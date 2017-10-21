package fr.smoney.android.izly.adapter;

import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.adapter.CityAdapter.ViewHolder;

public class CityAdapter$ViewHolder$$ViewBinder<T extends ViewHolder> implements ViewBinder<T> {
    public void bind(Finder finder, T t, Object obj) {
        t.mCityTextView = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.city_text_view, "field 'mCityTextView'"), R.id.city_text_view, "field 'mCityTextView'");
    }

    public void unbind(T t) {
        t.mCityTextView = null;
    }
}
