package fr.smoney.android.izly.ui;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import butterknife.internal.DebouncingOnClickListener;
import fr.smoney.android.izly.R;

public class CGUSmoneyActivity$$ViewBinder<T extends CGUSmoneyActivity> implements ViewBinder<T> {
    public void bind(Finder finder, final T t, Object obj) {
        t.mSubtitle = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.cgu_good_deals_subtitle, "field 'mSubtitle'"), R.id.cgu_good_deals_subtitle, "field 'mSubtitle'");
        t.mSmoneyCgu = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.cgu_smoney, "field 'mSmoneyCgu'"), R.id.cgu_smoney, "field 'mSmoneyCgu'");
        t.mSmoneyCguCheckbox = (CheckBox) finder.castView((View) finder.findRequiredView(obj, R.id.cgu_smoney_checkbox, "field 'mSmoneyCguCheckbox'"), R.id.cgu_smoney_checkbox, "field 'mSmoneyCguCheckbox'");
        t.mSmoneyOffersView = (View) finder.findRequiredView(obj, R.id.smoney_offers_view, "field 'mSmoneyOffersView'");
        t.mSmoneyOffersCheckbox = (CheckBox) finder.castView((View) finder.findRequiredView(obj, R.id.smoney_offers_checkbox, "field 'mSmoneyOffersCheckbox'"), R.id.smoney_offers_checkbox, "field 'mSmoneyOffersCheckbox'");
        t.mSmoneyPartnersOffersView = (View) finder.findRequiredView(obj, R.id.smoney_partners_offers_view, "field 'mSmoneyPartnersOffersView'");
        t.mSmoneyPartnersOffersCheckbox = (CheckBox) finder.castView((View) finder.findRequiredView(obj, R.id.smoney_partners_offers_checkbox, "field 'mSmoneyPartnersOffersCheckbox'"), R.id.smoney_partners_offers_checkbox, "field 'mSmoneyPartnersOffersCheckbox'");
        View view = (View) finder.findRequiredView(obj, R.id.btn_confirm, "field 'mConfirmBtn' and method 'onConfirmClick'");
        t.mConfirmBtn = (Button) finder.castView(view, R.id.btn_confirm, "field 'mConfirmBtn'");
        view.setOnClickListener(new DebouncingOnClickListener(this) {
            final /* synthetic */ CGUSmoneyActivity$$ViewBinder b;

            public final void doClick(View view) {
                t.onConfirmClick(view);
            }
        });
    }

    public void unbind(T t) {
        t.mSubtitle = null;
        t.mSmoneyCgu = null;
        t.mSmoneyCguCheckbox = null;
        t.mSmoneyOffersView = null;
        t.mSmoneyOffersCheckbox = null;
        t.mSmoneyPartnersOffersView = null;
        t.mSmoneyPartnersOffersCheckbox = null;
        t.mConfirmBtn = null;
    }
}
