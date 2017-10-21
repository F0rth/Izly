package fr.smoney.android.izly.ui;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import fr.smoney.android.izly.R;

public class ActivationActivity$$ViewBinder<T extends ActivationActivity> implements ViewBinder<T> {
    public void bind(Finder finder, T t, Object obj) {
        t.mActivationCodeNotReceived = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.tv_activation_code_not_received, "field 'mActivationCodeNotReceived'"), R.id.tv_activation_code_not_received, "field 'mActivationCodeNotReceived'");
        t.mEmail = (EditText) finder.castView((View) finder.findRequiredView(obj, R.id.et_email, "field 'mEmail'"), R.id.et_email, "field 'mEmail'");
        t.mActivationCode = (EditText) finder.castView((View) finder.findRequiredView(obj, R.id.et_activation_code, "field 'mActivationCode'"), R.id.et_activation_code, "field 'mActivationCode'");
        t.mSubmit = (Button) finder.castView((View) finder.findRequiredView(obj, R.id.b_submit, "field 'mSubmit'"), R.id.b_submit, "field 'mSubmit'");
    }

    public void unbind(T t) {
        t.mActivationCodeNotReceived = null;
        t.mEmail = null;
        t.mActivationCode = null;
        t.mSubmit = null;
    }
}
