package fr.smoney.android.izly.ui;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import fr.smoney.android.izly.R;

public class ActivationAddPhoneNumberActivity$$ViewBinder<T extends ActivationAddPhoneNumberActivity> implements ViewBinder<T> {
    public void bind(Finder finder, T t, Object obj) {
        t.mSubmit = (Button) finder.castView((View) finder.findRequiredView(obj, R.id.b_submit, "field 'mSubmit'"), R.id.b_submit, "field 'mSubmit'");
        t.mSpinnerCountry = (Spinner) finder.castView((View) finder.findRequiredView(obj, R.id.sp_country_phone, "field 'mSpinnerCountry'"), R.id.sp_country_phone, "field 'mSpinnerCountry'");
        t.mPhoneNumber = (EditText) finder.castView((View) finder.findRequiredView(obj, R.id.et_phone_number, "field 'mPhoneNumber'"), R.id.et_phone_number, "field 'mPhoneNumber'");
    }

    public void unbind(T t) {
        t.mSubmit = null;
        t.mSpinnerCountry = null;
        t.mPhoneNumber = null;
    }
}
