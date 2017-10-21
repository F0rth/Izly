package fr.smoney.android.izly.ui;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import fr.smoney.android.izly.R;

public class ActivationStepOneActivity$$ViewBinder<T extends ActivationStepOneActivity> implements ViewBinder<T> {
    public void bind(Finder finder, T t, Object obj) {
        t.mFirstName = (EditText) finder.castView((View) finder.findRequiredView(obj, R.id.et_firstname, "field 'mFirstName'"), R.id.et_firstname, "field 'mFirstName'");
        t.mLastName = (EditText) finder.castView((View) finder.findRequiredView(obj, R.id.et_lastname, "field 'mLastName'"), R.id.et_lastname, "field 'mLastName'");
        t.mButtonCivility = (Button) finder.castView((View) finder.findRequiredView(obj, R.id.b_civility, "field 'mButtonCivility'"), R.id.b_civility, "field 'mButtonCivility'");
        t.mButtonBirthDate = (Button) finder.castView((View) finder.findRequiredView(obj, R.id.b_birth_date, "field 'mButtonBirthDate'"), R.id.b_birth_date, "field 'mButtonBirthDate'");
        t.mSubmit = (Button) finder.castView((View) finder.findRequiredView(obj, R.id.b_submit, "field 'mSubmit'"), R.id.b_submit, "field 'mSubmit'");
        t.mButtonCountry = (Button) finder.castView((View) finder.findRequiredView(obj, R.id.b_country, "field 'mButtonCountry'"), R.id.b_country, "field 'mButtonCountry'");
    }

    public void unbind(T t) {
        t.mFirstName = null;
        t.mLastName = null;
        t.mButtonCivility = null;
        t.mButtonBirthDate = null;
        t.mSubmit = null;
        t.mButtonCountry = null;
    }
}
