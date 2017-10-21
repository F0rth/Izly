package fr.smoney.android.izly.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import butterknife.Bind;
import butterknife.ButterKnife;
import defpackage.gs;
import defpackage.hw;
import defpackage.ie;
import defpackage.is;
import defpackage.jh;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.SmoneyApplication;
import fr.smoney.android.izly.data.model.AssociatePhoneNumberData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;

import java.util.EnumSet;
import java.util.Iterator;

public class ActivationAddPhoneNumberActivity extends SmoneyABSActivity implements TextWatcher, OnClickListener, SmoneyRequestManager$a {
    private gs b;
    private int c = 3;
    @Bind({2131755196})
    EditText mPhoneNumber;
    @Bind({2131755195})
    Spinner mSpinnerCountry;
    @Bind({2131755192})
    Button mSubmit;

    private void a(AssociatePhoneNumberData associatePhoneNumberData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (associatePhoneNumberData == null) {
            a(hw.a(this, this));
        } else {
            startActivity(is.a(this, ActivationPhoneValidationActivity.class));
        }
    }

    private void k() {
        super.a(j().b((this.b.getCount() > 0 ? ((cm) this.b.getItem(this.mSpinnerCountry.getSelectedItemPosition())).k : 0) + this.mPhoneNumber.getEditableText().toString()), 277, true);
    }

    private boolean l() {
        return !jh.d(this.mPhoneNumber.getEditableText().toString()) && this.mPhoneNumber.getEditableText().toString().length() > 0;
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (ieVar) {
            case ConnexionErrorType:
                if (h() == 274) {
                    k();
                    return;
                } else {
                    super.a(ieVar, bundle);
                    return;
                }
            default:
                super.a(ieVar, bundle);
                return;
        }
    }

    public final void a_(int i, int i2, int i3, Bundle bundle) {
        if (super.a(i, i2, i3, bundle)) {
            ServerError serverError = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
            if (i2 == 277) {
                a((AssociatePhoneNumberData) bundle.getParcelable("fr.smoney.android.izly.extras.AssociatePhoneNumberData"), serverError);
            }
        }
    }

    public void afterTextChanged(Editable editable) {
        this.mSubmit.setEnabled(l());
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

    public final void b_(int i) {
        if (i == 277) {
            a(SmoneyApplication.a.cq, SmoneyApplication.a.cr);
        }
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
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

    public void onBackPressed() {
    }

    public void onClick(View view) {
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeButtonEnabled(false);
            supportActionBar.setDisplayHomeAsUpEnabled(false);
            supportActionBar.setTitle(R.string.activation_add_phone_number_title);
        }
        setContentView((int) R.layout.activity_activation_add_phonenumber);
        ButterKnife.bind(this);
        this.mPhoneNumber.addTextChangedListener(this);
        this.b = new gs(this);
        Iterator it = EnumSet.allOf(cm.class).iterator();
        while (it.hasNext()) {
            this.b.add((cm) it.next());
        }
        this.mSpinnerCountry.setAdapter(this.b);
        this.mSubmit.setEnabled(l());
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                finish();
                break;
        }
        return true;
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public void submit(View view) {
        k();
    }
}
