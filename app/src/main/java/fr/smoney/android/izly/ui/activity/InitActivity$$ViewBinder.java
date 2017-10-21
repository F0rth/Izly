package fr.smoney.android.izly.ui.activity;

import android.support.v7.widget.AppCompatButton;
import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import butterknife.internal.DebouncingOnClickListener;
import fr.smoney.android.izly.R;

public class InitActivity$$ViewBinder<T extends InitActivity> implements ViewBinder<T> {
    public void bind(Finder finder, final T t, Object obj) {
        View view = (View) finder.findRequiredView(obj, R.id.location_supported_button, "field 'mLocationSupportedButton' and method 'onLocationSupportedClick'");
        t.mLocationSupportedButton = (AppCompatButton) finder.castView(view, R.id.location_supported_button, "field 'mLocationSupportedButton'");
        view.setOnClickListener(new DebouncingOnClickListener(this) {
            final /* synthetic */ InitActivity$$ViewBinder b;

            public final void doClick(View view) {
                t.onLocationSupportedClick();
            }
        });
        view = (View) finder.findRequiredView(obj, R.id.location_not_supported_button, "field 'mLocationNotSupportedButton' and method 'onLocationNotSupportedClick'");
        t.mLocationNotSupportedButton = (AppCompatButton) finder.castView(view, R.id.location_not_supported_button, "field 'mLocationNotSupportedButton'");
        view.setOnClickListener(new DebouncingOnClickListener(this) {
            final /* synthetic */ InitActivity$$ViewBinder b;

            public final void doClick(View view) {
                t.onLocationNotSupportedClick();
            }
        });
        view = (View) finder.findRequiredView(obj, R.id.city_button, "field 'mCityButton' and method 'onCityClick'");
        t.mCityButton = (AppCompatButton) finder.castView(view, R.id.city_button, "field 'mCityButton'");
        view.setOnClickListener(new DebouncingOnClickListener(this) {
            final /* synthetic */ InitActivity$$ViewBinder b;

            public final void doClick(View view) {
                t.onCityClick();
            }
        });
    }

    public void unbind(T t) {
        t.mLocationSupportedButton = null;
        t.mLocationNotSupportedButton = null;
        t.mCityButton = null;
    }
}
