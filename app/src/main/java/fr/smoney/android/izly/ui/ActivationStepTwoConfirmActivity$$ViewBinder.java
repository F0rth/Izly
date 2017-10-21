package fr.smoney.android.izly.ui;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import fr.smoney.android.izly.R;

public class ActivationStepTwoConfirmActivity$$ViewBinder<T extends ActivationStepTwoConfirmActivity> implements ViewBinder<T> {
    public void bind(Finder finder, T t, Object obj) {
        t.mSubmit = (Button) finder.castView((View) finder.findRequiredView(obj, R.id.b_submit, "field 'mSubmit'"), R.id.b_submit, "field 'mSubmit'");
        t.mFirstNum = (EditText) finder.castView((View) finder.findRequiredView(obj, R.id.first, "field 'mFirstNum'"), R.id.first, "field 'mFirstNum'");
        t.mSecondNum = (EditText) finder.castView((View) finder.findRequiredView(obj, R.id.second, "field 'mSecondNum'"), R.id.second, "field 'mSecondNum'");
        t.mThirdNum = (EditText) finder.castView((View) finder.findRequiredView(obj, R.id.third, "field 'mThirdNum'"), R.id.third, "field 'mThirdNum'");
        t.mFourthNum = (EditText) finder.castView((View) finder.findRequiredView(obj, R.id.fourth, "field 'mFourthNum'"), R.id.fourth, "field 'mFourthNum'");
        t.mFifthNum = (EditText) finder.castView((View) finder.findRequiredView(obj, R.id.fifth, "field 'mFifthNum'"), R.id.fifth, "field 'mFifthNum'");
        t.mSixthNum = (EditText) finder.castView((View) finder.findRequiredView(obj, R.id.sixth, "field 'mSixthNum'"), R.id.sixth, "field 'mSixthNum'");
        t.mSecretCode = (LinearLayout) finder.castView((View) finder.findRequiredView(obj, R.id.ll_secret_code, "field 'mSecretCode'"), R.id.ll_secret_code, "field 'mSecretCode'");
        t.mTitle = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.tv_complete_your_profile_title, "field 'mTitle'"), R.id.tv_complete_your_profile_title, "field 'mTitle'");
        t.mDescription = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.tv_activation_description, "field 'mDescription'"), R.id.tv_activation_description, "field 'mDescription'");
        t.mEyeDescription = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.tv_see_your_password, "field 'mEyeDescription'"), R.id.tv_see_your_password, "field 'mEyeDescription'");
        t.mLinearLayoutEye = (LinearLayout) finder.castView((View) finder.findRequiredView(obj, R.id.ll_see_your_password, "field 'mLinearLayoutEye'"), R.id.ll_see_your_password, "field 'mLinearLayoutEye'");
    }

    public void unbind(T t) {
        t.mSubmit = null;
        t.mFirstNum = null;
        t.mSecondNum = null;
        t.mThirdNum = null;
        t.mFourthNum = null;
        t.mFifthNum = null;
        t.mSixthNum = null;
        t.mSecretCode = null;
        t.mTitle = null;
        t.mDescription = null;
        t.mEyeDescription = null;
        t.mLinearLayoutEye = null;
    }
}
