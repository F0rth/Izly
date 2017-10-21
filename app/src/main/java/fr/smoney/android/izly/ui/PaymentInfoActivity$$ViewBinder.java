package fr.smoney.android.izly.ui;

import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import fr.smoney.android.izly.R;

public class PaymentInfoActivity$$ViewBinder<T extends PaymentInfoActivity> implements ViewBinder<T> {
    public void bind(Finder finder, T t, Object obj) {
        t.infos = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.infos, "field 'infos'"), R.id.infos, "field 'infos'");
        t.title = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.payment_title, "field 'title'"), R.id.payment_title, "field 'title'");
    }

    public void unbind(T t) {
        t.infos = null;
        t.title = null;
    }
}
