package fr.smoney.android.izly.ui;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.IconTextView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.ui.widget.ContactAutoCompleteTextView;

public class MoneyInActivity$$ViewBinder<T extends MoneyInActivity> implements ViewBinder<T> {
    public void bind(Finder finder, T t, Object obj) {
        t.mSpinnerCard = (Spinner) finder.castView((View) finder.findRequiredView(obj, R.id.sp_card, "field 'mSpinnerCard'"), R.id.sp_card, "field 'mSpinnerCard'");
        t.mToggleButtonAmount1 = (ToggleButton) finder.castView((View) finder.findRequiredView(obj, R.id.tb_amount_1, "field 'mToggleButtonAmount1'"), R.id.tb_amount_1, "field 'mToggleButtonAmount1'");
        t.mToggleButtonAmount2 = (ToggleButton) finder.castView((View) finder.findRequiredView(obj, R.id.tb_amount_2, "field 'mToggleButtonAmount2'"), R.id.tb_amount_2, "field 'mToggleButtonAmount2'");
        t.mToggleButtonAmount3 = (ToggleButton) finder.castView((View) finder.findRequiredView(obj, R.id.tb_amount_3, "field 'mToggleButtonAmount3'"), R.id.tb_amount_3, "field 'mToggleButtonAmount3'");
        t.mToggleButtonAmount4 = (ToggleButton) finder.castView((View) finder.findRequiredView(obj, R.id.tb_amount_4, "field 'mToggleButtonAmount4'"), R.id.tb_amount_4, "field 'mToggleButtonAmount4'");
        t.mToggleButtonAmount5 = (ToggleButton) finder.castView((View) finder.findRequiredView(obj, R.id.tb_amount_5, "field 'mToggleButtonAmount5'"), R.id.tb_amount_5, "field 'mToggleButtonAmount5'");
        t.mToggleButtonAmount6 = (ToggleButton) finder.castView((View) finder.findRequiredView(obj, R.id.tb_amount_6, "field 'mToggleButtonAmount6'"), R.id.tb_amount_6, "field 'mToggleButtonAmount6'");
        t.mLayoutAmount = (LinearLayout) finder.castView((View) finder.findRequiredView(obj, R.id.fl_amount, "field 'mLayoutAmount'"), R.id.fl_amount, "field 'mLayoutAmount'");
        t.mTiersMail = (ContactAutoCompleteTextView) finder.castView((View) finder.findRequiredView(obj, R.id.atv_recipient, "field 'mTiersMail'"), R.id.atv_recipient, "field 'mTiersMail'");
        t.mTiersLayout = (LinearLayout) finder.castView((View) finder.findRequiredView(obj, R.id.tiers_input_view, "field 'mTiersLayout'"), R.id.tiers_input_view, "field 'mTiersLayout'");
        t.mSepaLayout = (LinearLayout) finder.castView((View) finder.findRequiredView(obj, R.id.sepa_input_view, "field 'mSepaLayout'"), R.id.sepa_input_view, "field 'mSepaLayout'");
        t.mEditTextBic = (EditText) finder.castView((View) finder.findRequiredView(obj, R.id.et_bic, "field 'mEditTextBic'"), R.id.et_bic, "field 'mEditTextBic'");
        t.mEditTextIban = (EditText) finder.castView((View) finder.findRequiredView(obj, R.id.et_iban, "field 'mEditTextIban'"), R.id.et_iban, "field 'mEditTextIban'");
        t.mCardsLayout = (LinearLayout) finder.castView((View) finder.findRequiredView(obj, R.id.card_input_view, "field 'mCardsLayout'"), R.id.card_input_view, "field 'mCardsLayout'");
        t.mEditTextAmount = (EditText) finder.castView((View) finder.findRequiredView(obj, R.id.et_amount, "field 'mEditTextAmount'"), R.id.et_amount, "field 'mEditTextAmount'");
        t.mTextViewAmount = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.tv_amount, "field 'mTextViewAmount'"), R.id.tv_amount, "field 'mTextViewAmount'");
        t.mButtonSubmit = (Button) finder.castView((View) finder.findRequiredView(obj, R.id.b_submit, "field 'mButtonSubmit'"), R.id.b_submit, "field 'mButtonSubmit'");
        t.messageView = (View) finder.findRequiredView(obj, R.id.layout_message, "field 'messageView'");
        t.messageEditText = (EditText) finder.castView((View) finder.findRequiredView(obj, R.id.et_message, "field 'messageEditText'"), R.id.et_message, "field 'messageEditText'");
        t.mButtonChooseContact = (IconTextView) finder.castView((View) finder.findRequiredView(obj, R.id.iv_choose_contact, "field 'mButtonChooseContact'"), R.id.iv_choose_contact, "field 'mButtonChooseContact'");
    }

    public void unbind(T t) {
        t.mSpinnerCard = null;
        t.mToggleButtonAmount1 = null;
        t.mToggleButtonAmount2 = null;
        t.mToggleButtonAmount3 = null;
        t.mToggleButtonAmount4 = null;
        t.mToggleButtonAmount5 = null;
        t.mToggleButtonAmount6 = null;
        t.mLayoutAmount = null;
        t.mTiersMail = null;
        t.mTiersLayout = null;
        t.mSepaLayout = null;
        t.mEditTextBic = null;
        t.mEditTextIban = null;
        t.mCardsLayout = null;
        t.mEditTextAmount = null;
        t.mTextViewAmount = null;
        t.mButtonSubmit = null;
        t.messageView = null;
        t.messageEditText = null;
        t.mButtonChooseContact = null;
    }
}
