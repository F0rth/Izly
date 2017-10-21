package fr.smoney.android.izly.ui;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import fr.smoney.android.izly.R;

public class MoneyInCbAndPayActivity$$ViewBinder<T extends MoneyInCbAndPayActivity> implements ViewBinder<T> {
    public void bind(Finder finder, T t, Object obj) {
        t.mSpinnerCard = (Spinner) finder.castView((View) finder.findRequiredView(obj, R.id.sp_card, "field 'mSpinnerCard'"), R.id.sp_card, "field 'mSpinnerCard'");
        t.mToggleButtonAmount1 = (ToggleButton) finder.castView((View) finder.findRequiredView(obj, R.id.tb_amount_1, "field 'mToggleButtonAmount1'"), R.id.tb_amount_1, "field 'mToggleButtonAmount1'");
        t.mToggleButtonAmount2 = (ToggleButton) finder.castView((View) finder.findRequiredView(obj, R.id.tb_amount_2, "field 'mToggleButtonAmount2'"), R.id.tb_amount_2, "field 'mToggleButtonAmount2'");
        t.mToggleButtonAmount6 = (ToggleButton) finder.castView((View) finder.findRequiredView(obj, R.id.tb_amount_6, "field 'mToggleButtonAmount6'"), R.id.tb_amount_6, "field 'mToggleButtonAmount6'");
        t.mCardsLayout = (LinearLayout) finder.castView((View) finder.findRequiredView(obj, R.id.card_input_view, "field 'mCardsLayout'"), R.id.card_input_view, "field 'mCardsLayout'");
        t.mLayoutAmount = (LinearLayout) finder.castView((View) finder.findRequiredView(obj, R.id.fl_amount, "field 'mLayoutAmount'"), R.id.fl_amount, "field 'mLayoutAmount'");
        t.mEditTextAmount = (EditText) finder.castView((View) finder.findRequiredView(obj, R.id.et_amount, "field 'mEditTextAmount'"), R.id.et_amount, "field 'mEditTextAmount'");
        t.mTextViewAmount = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.tv_amount, "field 'mTextViewAmount'"), R.id.tv_amount, "field 'mTextViewAmount'");
        t.mButtonSubmit = (Button) finder.castView((View) finder.findRequiredView(obj, R.id.b_submit, "field 'mButtonSubmit'"), R.id.b_submit, "field 'mButtonSubmit'");
    }

    public void unbind(T t) {
        t.mSpinnerCard = null;
        t.mToggleButtonAmount1 = null;
        t.mToggleButtonAmount2 = null;
        t.mToggleButtonAmount6 = null;
        t.mCardsLayout = null;
        t.mLayoutAmount = null;
        t.mEditTextAmount = null;
        t.mTextViewAmount = null;
        t.mButtonSubmit = null;
    }
}
