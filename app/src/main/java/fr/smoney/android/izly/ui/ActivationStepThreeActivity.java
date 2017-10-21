package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import defpackage.ib;
import defpackage.ie;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;

import java.util.Iterator;

public class ActivationStepThreeActivity extends SmoneyABSActivity implements OnClickListener {
    String b;
    private String c;
    private cl d;
    private CharSequence[] e;
    private int f = 0;
    @Bind({2131755213})
    EditText mEditTextSecretAnswer;
    @Bind({2131755210})
    EditText mEditTextSecretQuestionCustom;
    @Bind({2131755211})
    Button mSpinnerSecretQuestion;
    @Bind({2131755192})
    Button mSubmit;

    final class a implements TextWatcher {
        final /* synthetic */ ActivationStepThreeActivity a;
        private View b;

        public a(ActivationStepThreeActivity activationStepThreeActivity, View view) {
            this.a = activationStepThreeActivity;
            this.b = view;
        }

        public final void afterTextChanged(Editable editable) {
            this.a.mSubmit.setEnabled(this.a.k());
        }

        public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (ieVar) {
            case SelectSecretQuestionType:
                this.f = bundle.getInt("Data.SelectItem");
                this.mSpinnerSecretQuestion.setText(this.e[this.f]);
                this.mEditTextSecretQuestionCustom.setVisibility(this.f == this.e.length + -1 ? 0 : 4);
                if (this.f == this.e.length - 1) {
                    this.mSpinnerSecretQuestion.setVisibility(4);
                    this.mEditTextSecretQuestionCustom.requestFocus();
                    ((InputMethodManager) getSystemService("input_method")).showSoftInput(this.mEditTextSecretQuestionCustom, 1);
                    this.mSpinnerSecretQuestion.setText(this.e[0]);
                }
                this.mEditTextSecretAnswer.setEnabled(true);
                this.mSubmit.setEnabled(k());
                return;
            case SelectBirthDate:
                return;
            default:
                super.a(ieVar, bundle);
                return;
        }
    }

    public final void b(ie ieVar) {
        switch (ieVar) {
            case ConnexionErrorType:
                super.b(ieVar);
                return;
            default:
                super.b(ieVar);
                return;
        }
    }

    public final void c(ie ieVar) {
        switch (ieVar) {
            case ErrorType:
                return;
            default:
                super.c(ieVar);
                return;
        }
    }

    public final void d(ie ieVar) {
        super.d(ieVar);
    }

    public final boolean k() {
        int i = this.f;
        return ((i == this.e.length + -1 || i == 0 || TextUtils.isEmpty(this.mEditTextSecretAnswer.getText().toString())) && (i != this.e.length - 1 || TextUtils.isEmpty(this.mEditTextSecretQuestionCustom.getText().toString()) || TextUtils.isEmpty(this.mEditTextSecretAnswer.getText().toString()))) ? false : true;
    }

    public void onClick(View view) {
        if (view == this.mSpinnerSecretQuestion) {
            a(ib.a(getString(R.string.activation_step_three_secret_question), this.e, this, ie.SelectSecretQuestionType));
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActionBar supportActionBar = getSupportActionBar();
        this.d = i();
        if (supportActionBar != null) {
            supportActionBar.setHomeButtonEnabled(true);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle(R.string.activation_step_three_title);
        }
        setContentView((int) R.layout.activity_activation_step_three);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(2);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("fr.smoney.android.izly.userActivationPasswordConfirm")) {
            this.b = intent.getStringExtra("fr.smoney.android.izly.userActivationPasswordConfirm");
        }
        if (bundle != null) {
            this.c = bundle.getString("savedSecretQuestion");
        }
        this.mSpinnerSecretQuestion.setOnClickListener(this);
        if (this.d.cl.c != null) {
            this.e = new String[this.d.cl.c.size()];
            Iterator it = this.d.cl.c.iterator();
            int i = 0;
            while (it.hasNext()) {
                this.e[i] = (String) it.next();
                i++;
            }
        }
        this.mEditTextSecretQuestionCustom = (EditText) findViewById(R.id.et_secret_question_custom);
        this.mEditTextSecretQuestionCustom.addTextChangedListener(new a(this, this.mEditTextSecretQuestionCustom));
        this.mEditTextSecretAnswer = (EditText) findViewById(R.id.et_secret_answer);
        this.mEditTextSecretAnswer.addTextChangedListener(new a(this, this.mEditTextSecretAnswer));
        this.mEditTextSecretQuestionCustom.setOnFocusChangeListener(new OnFocusChangeListener(this) {
            final /* synthetic */ ActivationStepThreeActivity a;

            {
                this.a = r1;
            }

            public final void onFocusChange(View view, boolean z) {
                if (!z && this.a.mEditTextSecretQuestionCustom.getEditableText().toString().trim().length() == 0) {
                    this.a.mSpinnerSecretQuestion.setVisibility(0);
                    this.a.mSpinnerSecretQuestion.setText(this.a.e[0]);
                }
            }
        });
        this.mSpinnerSecretQuestion.setText(this.e[0]);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                finish();
                break;
        }
        return true;
    }

    public void submit(View view) {
        Intent intent = new Intent(this, ActivationCGUActivity.class);
        intent.putExtra("fr.smoney.android.izly.extras.displayCase", fr.smoney.android.izly.ui.ActivationCGUActivity.a.CGU_FOR_SUBSCRIBE);
        intent.putExtra("fr.smoney.android.izly.userActivationPasswordConfirm", this.b);
        int i = this.f;
        if (i == this.e.length - 1) {
            this.c = this.mEditTextSecretQuestionCustom.getText().toString();
        } else {
            this.c = this.e[i].toString();
        }
        intent.putExtra("fr.smoney.android.izly.userActivationSecretQuestion", this.c);
        intent.putExtra("fr.smoney.android.izly.userActivationSecretAnswer", this.mEditTextSecretAnswer.getText().toString());
        startActivity(intent);
    }
}
