package fr.smoney.android.izly.ui;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import fr.smoney.android.izly.R;

public class ActivationStepThreeActivity$$ViewBinder<T extends ActivationStepThreeActivity> implements ViewBinder<T> {
    public void bind(Finder finder, T t, Object obj) {
        t.mSubmit = (Button) finder.castView((View) finder.findRequiredView(obj, R.id.b_submit, "field 'mSubmit'"), R.id.b_submit, "field 'mSubmit'");
        t.mSpinnerSecretQuestion = (Button) finder.castView((View) finder.findRequiredView(obj, R.id.b_secret_question, "field 'mSpinnerSecretQuestion'"), R.id.b_secret_question, "field 'mSpinnerSecretQuestion'");
        t.mEditTextSecretQuestionCustom = (EditText) finder.castView((View) finder.findRequiredView(obj, R.id.et_secret_question_custom, "field 'mEditTextSecretQuestionCustom'"), R.id.et_secret_question_custom, "field 'mEditTextSecretQuestionCustom'");
        t.mEditTextSecretAnswer = (EditText) finder.castView((View) finder.findRequiredView(obj, R.id.et_secret_answer, "field 'mEditTextSecretAnswer'"), R.id.et_secret_answer, "field 'mEditTextSecretAnswer'");
    }

    public void unbind(T t) {
        t.mSubmit = null;
        t.mSpinnerSecretQuestion = null;
        t.mEditTextSecretQuestionCustom = null;
        t.mEditTextSecretAnswer = null;
    }
}
