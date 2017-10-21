package fr.smoney.android.izly.ui;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.ui.widget.DetailTwoText;

public class MoneyInCbConfirmActivity$$ViewBinder<T extends MoneyInCbConfirmActivity> implements ViewBinder<T> {
    public void bind(Finder finder, T t, Object obj) {
        t.mTextViewAmount = (DetailTwoText) finder.castView((View) finder.findRequiredView(obj, R.id.tv_amount, "field 'mTextViewAmount'"), R.id.tv_amount, "field 'mTextViewAmount'");
        t.mDate = (DetailTwoText) finder.castView((View) finder.findRequiredView(obj, R.id.tv_date, "field 'mDate'"), R.id.tv_date, "field 'mDate'");
        t.mButtonConfirm = (Button) finder.castView((View) finder.findRequiredView(obj, R.id.b_confirm, "field 'mButtonConfirm'"), R.id.b_confirm, "field 'mButtonConfirm'");
        t.mAlias = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.tv_money_operation_alias, "field 'mAlias'"), R.id.tv_money_operation_alias, "field 'mAlias'");
        t.mHint = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.tv_money_operation_hint, "field 'mHint'"), R.id.tv_money_operation_hint, "field 'mHint'");
        t.mTextViewCommission = (DetailTwoText) finder.castView((View) finder.findRequiredView(obj, R.id.tv_commission, "field 'mTextViewCommission'"), R.id.tv_commission, "field 'mTextViewCommission'");
    }

    public void unbind(T t) {
        t.mTextViewAmount = null;
        t.mDate = null;
        t.mButtonConfirm = null;
        t.mAlias = null;
        t.mHint = null;
        t.mTextViewCommission = null;
    }
}
