package fr.smoney.android.izly.ui;

import android.view.View;
import android.widget.LinearLayout;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import fr.smoney.android.izly.R;

public class OffersAndServicesActivity$$ViewBinder<T extends OffersAndServicesActivity> implements ViewBinder<T> {
    public void bind(Finder finder, T t, Object obj) {
        t.mOffersServicesLayout = (LinearLayout) finder.castView((View) finder.findRequiredView(obj, R.id.offers_and_services_activity_list, "field 'mOffersServicesLayout'"), R.id.offers_and_services_activity_list, "field 'mOffersServicesLayout'");
    }

    public void unbind(T t) {
        t.mOffersServicesLayout = null;
    }
}
