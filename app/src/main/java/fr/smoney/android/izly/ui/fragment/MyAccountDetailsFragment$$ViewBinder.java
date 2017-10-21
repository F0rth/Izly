package fr.smoney.android.izly.ui.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.ui.widget.FormEditText;

public class MyAccountDetailsFragment$$ViewBinder<T extends MyAccountDetailsFragment> implements ViewBinder<T> {
    public void bind(Finder finder, T t, Object obj) {
        t.mEditTextFirstName = (EditText) finder.castView((View) finder.findRequiredView(obj, R.id.et_first_name, "field 'mEditTextFirstName'"), R.id.et_first_name, "field 'mEditTextFirstName'");
        t.mEditTextLastName = (EditText) finder.castView((View) finder.findRequiredView(obj, R.id.et_last_name, "field 'mEditTextLastName'"), R.id.et_last_name, "field 'mEditTextLastName'");
        t.mTextViewPhone = (FormEditText) finder.castView((View) finder.findRequiredView(obj, R.id.tv_phone, "field 'mTextViewPhone'"), R.id.tv_phone, "field 'mTextViewPhone'");
        t.mEditTextNickname = (EditText) finder.castView((View) finder.findRequiredView(obj, R.id.et_nickname, "field 'mEditTextNickname'"), R.id.et_nickname, "field 'mEditTextNickname'");
        t.mEditTextEmail = (FormEditText) finder.castView((View) finder.findRequiredView(obj, R.id.et_email, "field 'mEditTextEmail'"), R.id.et_email, "field 'mEditTextEmail'");
        t.mEditTextStreet = (EditText) finder.castView((View) finder.findRequiredView(obj, R.id.et_street, "field 'mEditTextStreet'"), R.id.et_street, "field 'mEditTextStreet'");
        t.mEditTextPostalCode = (EditText) finder.castView((View) finder.findRequiredView(obj, R.id.et_postal_code, "field 'mEditTextPostalCode'"), R.id.et_postal_code, "field 'mEditTextPostalCode'");
        t.mEditTextCity = (EditText) finder.castView((View) finder.findRequiredView(obj, R.id.et_city, "field 'mEditTextCity'"), R.id.et_city, "field 'mEditTextCity'");
        t.mCheckBoxOptIn = (CheckBox) finder.castView((View) finder.findRequiredView(obj, R.id.cb_smoney_opt_in, "field 'mCheckBoxOptIn'"), R.id.cb_smoney_opt_in, "field 'mCheckBoxOptIn'");
        t.mButtonBirthDate = (Button) finder.castView((View) finder.findRequiredView(obj, R.id.b_birth_date, "field 'mButtonBirthDate'"), R.id.b_birth_date, "field 'mButtonBirthDate'");
        t.mCheckBoxOptInPartners = (CheckBox) finder.castView((View) finder.findRequiredView(obj, R.id.cb_partner_opt_in, "field 'mCheckBoxOptInPartners'"), R.id.cb_partner_opt_in, "field 'mCheckBoxOptInPartners'");
        t.mButtonModify = (Button) finder.castView((View) finder.findRequiredView(obj, R.id.b_modify, "field 'mButtonModify'"), R.id.b_modify, "field 'mButtonModify'");
        t.mSpinnerCountry = (Spinner) finder.castView((View) finder.findRequiredView(obj, R.id.sp_country, "field 'mSpinnerCountry'"), R.id.sp_country, "field 'mSpinnerCountry'");
        t.mButtonCivility = (Button) finder.castView((View) finder.findRequiredView(obj, R.id.b_civility, "field 'mButtonCivility'"), R.id.b_civility, "field 'mButtonCivility'");
        t.mTextViewUpdatedProfile = (View) finder.findRequiredView(obj, R.id.tv_my_account_details_updated_profile, "field 'mTextViewUpdatedProfile'");
        t.etTwitter = (EditText) finder.castView((View) finder.findRequiredView(obj, R.id.et_twitter, "field 'etTwitter'"), R.id.et_twitter, "field 'etTwitter'");
        t.izlyEmail = (EditText) finder.castView((View) finder.findRequiredView(obj, R.id.et_email_univ, "field 'izlyEmail'"), R.id.et_email_univ, "field 'izlyEmail'");
        t.izlyCard = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.tv_studentcard, "field 'izlyCard'"), R.id.tv_studentcard, "field 'izlyCard'");
        t.mEditTextSocietyCode = (EditText) finder.castView((View) finder.findRequiredView(obj, R.id.et_society_code, "field 'mEditTextSocietyCode'"), R.id.et_society_code, "field 'mEditTextSocietyCode'");
        t.mEditTextPriceCode = (EditText) finder.castView((View) finder.findRequiredView(obj, R.id.et_price_code, "field 'mEditTextPriceCode'"), R.id.et_price_code, "field 'mEditTextPriceCode'");
        t.mEditTextEndDateCrous = (EditText) finder.castView((View) finder.findRequiredView(obj, R.id.et_end_date_crous, "field 'mEditTextEndDateCrous'"), R.id.et_end_date_crous, "field 'mEditTextEndDateCrous'");
    }

    public void unbind(T t) {
        t.mEditTextFirstName = null;
        t.mEditTextLastName = null;
        t.mTextViewPhone = null;
        t.mEditTextNickname = null;
        t.mEditTextEmail = null;
        t.mEditTextStreet = null;
        t.mEditTextPostalCode = null;
        t.mEditTextCity = null;
        t.mCheckBoxOptIn = null;
        t.mButtonBirthDate = null;
        t.mCheckBoxOptInPartners = null;
        t.mButtonModify = null;
        t.mSpinnerCountry = null;
        t.mButtonCivility = null;
        t.mTextViewUpdatedProfile = null;
        t.etTwitter = null;
        t.izlyEmail = null;
        t.izlyCard = null;
        t.mEditTextSocietyCode = null;
        t.mEditTextPriceCode = null;
        t.mEditTextEndDateCrous = null;
    }
}
