package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import defpackage.hv;
import defpackage.ie;
import defpackage.is;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.CheckUserActivationData;

import java.util.Iterator;

public class ActivationStepTwoActivity extends SmoneyABSActivity implements TextWatcher, OnClickListener, OnKeyListener {
    int b;
    int c;
    boolean d = false;
    private CheckUserActivationData e;
    @Bind({2131755223})
    TextView mEyeDescription;
    @Bind({2131755219})
    EditText mFifthNum;
    @Bind({2131755215})
    EditText mFirstNum;
    @Bind({2131755218})
    EditText mFourthNum;
    @Bind({2131755221})
    LinearLayout mLinearLayoutEye;
    @Bind({2131755216})
    EditText mSecondNum;
    @Bind({2131755214})
    LinearLayout mSecretCode;
    @Bind({2131755220})
    EditText mSixthNum;
    @Bind({2131755192})
    Button mSubmit;
    @Bind({2131755217})
    EditText mThirdNum;

    private void a(EditText editText) {
        if (editText == this.mFirstNum) {
            this.mFirstNum.setEnabled(true);
            this.mSecondNum.setEnabled(false);
            this.mSixthNum.setEnabled(false);
            this.mThirdNum.setEnabled(false);
            this.mFourthNum.setEnabled(false);
            this.mFifthNum.setEnabled(false);
        }
        if (editText == this.mSecondNum) {
            this.mSecondNum.setEnabled(true);
            this.mFirstNum.setEnabled(false);
            this.mSixthNum.setEnabled(false);
            this.mThirdNum.setEnabled(false);
            this.mFourthNum.setEnabled(false);
            this.mFifthNum.setEnabled(false);
        }
        if (editText == this.mThirdNum) {
            this.mThirdNum.setEnabled(true);
            this.mFirstNum.setEnabled(false);
            this.mSixthNum.setEnabled(false);
            this.mSecondNum.setEnabled(false);
            this.mFourthNum.setEnabled(false);
            this.mFifthNum.setEnabled(false);
        }
        if (editText == this.mFourthNum) {
            this.mFourthNum.setEnabled(true);
            this.mFirstNum.setEnabled(false);
            this.mSixthNum.setEnabled(false);
            this.mThirdNum.setEnabled(false);
            this.mSecondNum.setEnabled(false);
            this.mFifthNum.setEnabled(false);
        }
        if (editText == this.mFifthNum) {
            this.mFifthNum.setEnabled(true);
            this.mFirstNum.setEnabled(false);
            this.mSixthNum.setEnabled(false);
            this.mThirdNum.setEnabled(false);
            this.mFourthNum.setEnabled(false);
            this.mSecondNum.setEnabled(false);
        }
        if (editText == this.mSixthNum) {
            this.mSixthNum.setEnabled(true);
            this.mFirstNum.setEnabled(false);
            this.mSecondNum.setEnabled(false);
            this.mThirdNum.setEnabled(false);
            this.mFourthNum.setEnabled(false);
            this.mFifthNum.setEnabled(false);
        }
    }

    private void b(EditText editText) {
        if (editText == this.mFirstNum) {
            this.mFirstNum.setEnabled(true);
            editText.requestFocus();
            this.mSixthNum.setEnabled(false);
            this.mSecondNum.setEnabled(false);
            this.mThirdNum.setEnabled(false);
            this.mFourthNum.setEnabled(false);
            this.mFifthNum.setEnabled(false);
        }
        if (editText == this.mSecondNum) {
            this.mSecondNum.setEnabled(true);
            editText.requestFocus();
            this.mFirstNum.setEnabled(false);
            this.mSixthNum.setEnabled(false);
            this.mThirdNum.setEnabled(false);
            this.mFourthNum.setEnabled(false);
            this.mFifthNum.setEnabled(false);
        }
        if (editText == this.mThirdNum) {
            this.mThirdNum.setEnabled(true);
            editText.requestFocus();
            this.mSecondNum.setEnabled(false);
            this.mSixthNum.setEnabled(false);
            this.mSecondNum.setEnabled(false);
            this.mFourthNum.setEnabled(false);
            this.mFifthNum.setEnabled(false);
        }
        if (editText == this.mFourthNum) {
            this.mFourthNum.setEnabled(true);
            editText.requestFocus();
            this.mThirdNum.setEnabled(false);
            this.mSixthNum.setEnabled(false);
            this.mSecondNum.setEnabled(false);
            this.mThirdNum.setEnabled(false);
            this.mFifthNum.setEnabled(false);
        }
        if (editText == this.mFifthNum) {
            this.mFifthNum.setEnabled(true);
            editText.requestFocus();
            this.mFourthNum.setEnabled(false);
            this.mSixthNum.setEnabled(false);
            this.mSecondNum.setEnabled(false);
            this.mThirdNum.setEnabled(false);
            this.mFourthNum.setEnabled(false);
        }
        if (editText == this.mSixthNum) {
            this.mSixthNum.setEnabled(true);
            editText.requestFocus();
            this.mFifthNum.setEnabled(false);
            this.mSecondNum.setEnabled(false);
            this.mThirdNum.setEnabled(false);
            this.mFourthNum.setEnabled(false);
            this.mFifthNum.setEnabled(false);
        }
    }

    private String k() {
        return this.mFirstNum.getEditableText().toString() + this.mSecondNum.getEditableText().toString() + this.mThirdNum.getEditableText().toString() + this.mFourthNum.getEditableText().toString() + this.mFifthNum.getEditableText().toString() + this.mSixthNum.getEditableText().toString();
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (ieVar) {
            case InvalidPasswordChosenAlertType:
                return;
            default:
                super.a(ieVar, bundle);
                return;
        }
    }

    public void afterTextChanged(Editable editable) {
        if (this.b < this.c) {
            if (this.c == 0) {
                b(this.mFirstNum);
            }
            if (this.c == 1) {
                b(this.mSecondNum);
            }
            if (this.c == 2) {
                b(this.mThirdNum);
            }
            if (this.c == 3) {
                b(this.mFourthNum);
            }
            if (this.c == 4) {
                b(this.mFifthNum);
            }
            if (this.c == 5) {
                b(this.mSixthNum);
            }
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

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        this.b = k().length();
        Log.d("TextWatcher", "Before : " + this.b);
    }

    public final void c(ie ieVar) {
        if (ieVar == ie.AlertType) {
            this.mFirstNum.setText("");
            this.mSecondNum.setText("");
            this.mThirdNum.setText("");
            this.mFourthNum.setText("");
            this.mFifthNum.setText("");
            this.mSixthNum.setText("");
            a(this.mFirstNum);
            return;
        }
        super.c(ieVar);
    }

    public final void d(ie ieVar) {
        super.d(ieVar);
    }

    public void onBackPressed() {
    }

    public void onClick(View view) {
        if (view != this.mLinearLayoutEye) {
            return;
        }
        if (this.d) {
            this.mFirstNum.setTransformationMethod(new PasswordTransformationMethod());
            this.mSecondNum.setTransformationMethod(new PasswordTransformationMethod());
            this.mThirdNum.setTransformationMethod(new PasswordTransformationMethod());
            this.mFourthNum.setTransformationMethod(new PasswordTransformationMethod());
            this.mFifthNum.setTransformationMethod(new PasswordTransformationMethod());
            this.mSixthNum.setTransformationMethod(new PasswordTransformationMethod());
            this.d = false;
            this.mEyeDescription.setText(R.string.activation_show_secret_code);
            return;
        }
        this.mFirstNum.setTransformationMethod(null);
        this.mSecondNum.setTransformationMethod(null);
        this.mThirdNum.setTransformationMethod(null);
        this.mFourthNum.setTransformationMethod(null);
        this.mFifthNum.setTransformationMethod(null);
        this.mSixthNum.setTransformationMethod(null);
        this.d = true;
        this.mEyeDescription.setText(R.string.activation_hide_secret_code);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeButtonEnabled(true);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle(R.string.activation_step_two_title);
        }
        setContentView((int) R.layout.activity_activation_step_two);
        ButterKnife.bind(this);
        ((InputMethodManager) getSystemService("input_method")).showSoftInputFromInputMethod(this.mFirstNum.getWindowToken(), 0);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("fr.smoney.android.izly.checkActivationData")) {
            this.e = (CheckUserActivationData) intent.getParcelableExtra("fr.smoney.android.izly.checkActivationData");
        }
        this.mFirstNum.setOnKeyListener(this);
        this.mSecondNum.setOnKeyListener(this);
        this.mThirdNum.setOnKeyListener(this);
        this.mFourthNum.setOnKeyListener(this);
        this.mFifthNum.setOnKeyListener(this);
        this.mSixthNum.setOnKeyListener(this);
        this.mLinearLayoutEye.setOnClickListener(this);
        this.mFirstNum.addTextChangedListener(this);
        this.mSecondNum.addTextChangedListener(this);
        this.mThirdNum.addTextChangedListener(this);
        this.mFourthNum.addTextChangedListener(this);
        this.mFifthNum.addTextChangedListener(this);
        this.mSixthNum.addTextChangedListener(this);
    }

    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        Log.d("onKey", "keyEvent : " + keyEvent.getKeyCode());
        if (keyEvent.getKeyCode() == 66) {
            ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(this.mFirstNum.getWindowToken(), 0);
            return true;
        } else if (keyEvent.getKeyCode() == 67 && keyEvent.getAction() == 0) {
            if (view instanceof EditText) {
                r6 = (EditText) view;
                if (r6.getEditableText().length() > 0) {
                    r6.setText("");
                    return true;
                } else if (r6.getEditableText().length() == 0 && (findViewById(r6.getNextFocusLeftId()) instanceof EditText)) {
                    findViewById(r6.getNextFocusLeftId()).requestFocus();
                    a((EditText) findViewById(r6.getNextFocusLeftId()));
                    ((EditText) findViewById(r6.getNextFocusLeftId())).setText("");
                    return true;
                }
            }
            return true;
        } else {
            if ((view instanceof EditText) && keyEvent.getAction() == 0) {
                r6 = (EditText) view;
                if (r6.getEditableText().length() == 0) {
                    r6.setText(((char) keyEvent.getUnicodeChar()));
                    return true;
                } else if (r6.getEditableText().length() > 0) {
                    if (findViewById(r6.getNextFocusRightId()) instanceof EditText) {
                        EditText editText = (EditText) findViewById(r6.getNextFocusRightId());
                        if (editText != null) {
                            editText.requestFocus();
                            a(editText);
                            editText.setText(((char) keyEvent.getUnicodeChar()));
                        }
                    }
                    return true;
                }
            }
            return false;
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                finish();
                break;
        }
        return true;
    }

    protected void onResume() {
        super.onResume();
        if (this.mFirstNum.getTransformationMethod() == null) {
            this.mFirstNum.setTransformationMethod(new PasswordTransformationMethod());
            this.mSecondNum.setTransformationMethod(new PasswordTransformationMethod());
            this.mThirdNum.setTransformationMethod(new PasswordTransformationMethod());
            this.mFourthNum.setTransformationMethod(new PasswordTransformationMethod());
            this.mFifthNum.setTransformationMethod(new PasswordTransformationMethod());
            this.mSixthNum.setTransformationMethod(new PasswordTransformationMethod());
            this.d = false;
        }
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        this.c = k().length();
        Log.d("TextWatcher", "After : " + this.c);
        this.mSubmit.setEnabled(k().length() == 6);
    }

    public void submit(View view) {
        Object obj;
        Iterator it = this.e.a.iterator();
        while (it.hasNext()) {
            if (((String) it.next()).equals(k())) {
                obj = 1;
                break;
            }
        }
        obj = null;
        if (obj == null) {
            Intent a = is.a(this, ActivationStepTwoConfirmActivity.class);
            a.putExtra("fr.smoney.android.izly.userActivationPassword", k());
            startActivity(a);
            return;
        }
        a(hv.a(getString(R.string.activation_cgu_security), getString(R.string.activation_password_invalid), getString(17039370), this, ie.AlertType));
    }
}
