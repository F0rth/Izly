package fr.smoney.android.izly.ui;

import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.ui.widget.DetailTwoText;

public class MoneyInCbResultActivity$$ViewBinder<T extends MoneyInCbResultActivity> implements ViewBinder<T> {
    public void bind(Finder finder, T t, Object obj) {
        t.mTextViewCardAlias = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.tv_money_operation_alias, "field 'mTextViewCardAlias'"), R.id.tv_money_operation_alias, "field 'mTextViewCardAlias'");
        t.mTextViewCardHint = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.tv_money_operation_hint, "field 'mTextViewCardHint'"), R.id.tv_money_operation_hint, "field 'mTextViewCardHint'");
        t.mTextViewAmount = (DetailTwoText) finder.castView((View) finder.findRequiredView(obj, R.id.tv_amount, "field 'mTextViewAmount'"), R.id.tv_amount, "field 'mTextViewAmount'");
        t.mTextViewOldBalance = (DetailTwoText) finder.castView((View) finder.findRequiredView(obj, R.id.tv_old_balance_value, "field 'mTextViewOldBalance'"), R.id.tv_old_balance_value, "field 'mTextViewOldBalance'");
        t.mTextViewNewBalance = (DetailTwoText) finder.castView((View) finder.findRequiredView(obj, R.id.tv_new_balance_value, "field 'mTextViewNewBalance'"), R.id.tv_new_balance_value, "field 'mTextViewNewBalance'");
        t.mTextViewDate = (DetailTwoText) finder.castView((View) finder.findRequiredView(obj, R.id.tv_date, "field 'mTextViewDate'"), R.id.tv_date, "field 'mTextViewDate'");
    }

    public void unbind(T t) {
        t.mTextViewCardAlias = null;
        t.mTextViewCardHint = null;
        t.mTextViewAmount = null;
        t.mTextViewOldBalance = null;
        t.mTextViewNewBalance = null;
        t.mTextViewDate = null;
    }
}
