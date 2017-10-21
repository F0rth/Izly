package fr.smoney.android.izly.ui;

import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import fr.smoney.android.izly.R;

public class ActivationPhoneValidationActivity$$ViewBinder<T extends ActivationPhoneValidationActivity> implements ViewBinder<T> {
    public void bind(Finder finder, T t, Object obj) {
        t.mActivationDescription = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.tv_activation_description, "field 'mActivationDescription'"), R.id.tv_activation_description, "field 'mActivationDescription'");
    }

    public void unbind(T t) {
        t.mActivationDescription = null;
    }
}
