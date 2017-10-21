package fr.smoney.android.izly.ui;

import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.ui.widget.DetailTwoText;

public class AddChangeTransferAccountResultActivity$$ViewBinder<T extends AddChangeTransferAccountResultActivity> implements ViewBinder<T> {
    public void bind(Finder finder, T t, Object obj) {
        t.mTextViewAlias = (DetailTwoText) finder.castView((View) finder.findRequiredView(obj, R.id.tv_alias, "field 'mTextViewAlias'"), R.id.tv_alias, "field 'mTextViewAlias'");
        t.mTextViewIban = (DetailTwoText) finder.castView((View) finder.findRequiredView(obj, R.id.tv_iban, "field 'mTextViewIban'"), R.id.tv_iban, "field 'mTextViewIban'");
        t.mTextViewBic = (DetailTwoText) finder.castView((View) finder.findRequiredView(obj, R.id.tv_bic, "field 'mTextViewBic'"), R.id.tv_bic, "field 'mTextViewBic'");
    }

    public void unbind(T t) {
        t.mTextViewAlias = null;
        t.mTextViewIban = null;
        t.mTextViewBic = null;
    }
}
