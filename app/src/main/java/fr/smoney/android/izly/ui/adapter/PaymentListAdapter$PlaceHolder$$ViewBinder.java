package fr.smoney.android.izly.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import fr.smoney.android.izly.R;

public class PaymentListAdapter$PlaceHolder$$ViewBinder<T extends PlaceHolder> implements ViewBinder<T> {
    public void bind(Finder finder, T t, Object obj) {
        t.paymentLogo = (ImageView) finder.castView((View) finder.findRequiredView(obj, R.id.payment_logo, "field 'paymentLogo'"), R.id.payment_logo, "field 'paymentLogo'");
        t.paymentTitle = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.payment_title, "field 'paymentTitle'"), R.id.payment_title, "field 'paymentTitle'");
        t.paymentDesc = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.payment_desc, "field 'paymentDesc'"), R.id.payment_desc, "field 'paymentDesc'");
        t.paymentInfos = (ImageView) finder.castView((View) finder.findRequiredView(obj, R.id.payment_infos, "field 'paymentInfos'"), R.id.payment_infos, "field 'paymentInfos'");
    }

    public void unbind(T t) {
        t.paymentLogo = null;
        t.paymentTitle = null;
        t.paymentDesc = null;
        t.paymentInfos = null;
    }
}
