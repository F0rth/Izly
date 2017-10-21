package fr.smoney.android.izly.ui.activity;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import fr.smoney.android.izly.R;

public class GetCityLocationActivity$$ViewBinder<T extends GetCityLocationActivity> implements ViewBinder<T> {
    public void bind(Finder finder, T t, Object obj) {
        t.mLoader = (View) finder.findRequiredView(obj, R.id.loader, "field 'mLoader'");
    }

    public void unbind(T t) {
        t.mLoader = null;
    }
}
