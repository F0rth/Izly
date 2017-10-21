package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import defpackage.hw;
import defpackage.hz;
import defpackage.ie;
import defpackage.ij;
import defpackage.is;
import defpackage.jb;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.MakeBankAccountUpdateData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;
import fr.smoney.android.izly.ui.widget.FormEditText;

import java.util.regex.Pattern;

public class AddTransferAccountActivity extends SmoneyABSActivity implements TextWatcher, OnClickListener, SmoneyRequestManager$a {
    private EditText b;
    private FormEditText c;
    private FormEditText d;
    private Button e;
    private String f;
    private int g;

    private void a(MakeBankAccountUpdateData makeBankAccountUpdateData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (makeBankAccountUpdateData == null) {
            a(hw.a(this, this));
        } else {
            Intent a = is.a(this, AddChangeTransferAccountResultActivity.class);
            a.putExtra("fr.smoney.android.izly.intentExtrasMode", this.g);
            a.putExtra("fr.smoney.android.izly.extras.addTransferAccountData", makeBankAccountUpdateData);
            startActivityForResult(a, 11);
        }
    }

    private void k() {
        int keyAt;
        cl i = i();
        SmoneyRequestManager j = j();
        String str = i.b.a;
        String str2 = i.b.c;
        String obj = this.b.getText().toString();
        String obj2 = this.c.getText().toString();
        String obj3 = this.d.getText().toString();
        String str3 = this.f;
        int size = j.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            Intent intent = (Intent) j.b.valueAt(i2);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 91 && intent.getStringExtra("fr.smoney.android.izly.extras.makeBankAccountUpdateUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.makeBankAccountUpdateSessionId").equals(str2) && intent.getStringExtra("fr.smoney.android.izly.extras.makeBankAccountUpdateAlias").equals(obj) && intent.getStringExtra("fr.smoney.android.izly.extras.makeBankAccountUpdateIban").equals(obj2) && intent.getStringExtra("fr.smoney.android.izly.extras.makeBankAccountUpdateBic").equals(obj3) && intent.getStringExtra("fr.smoney.android.izly.extras.makeBankAccountUpdatePassword").equals(str3)) {
                keyAt = j.b.keyAt(i2);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 91);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.makeBankAccountUpdateUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.makeBankAccountUpdateSessionId", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.makeBankAccountUpdateAlias", obj);
        intent2.putExtra("fr.smoney.android.izly.extras.makeBankAccountUpdateIban", obj2);
        intent2.putExtra("fr.smoney.android.izly.extras.makeBankAccountUpdateBic", obj3);
        intent2.putExtra("fr.smoney.android.izly.extras.makeBankAccountUpdatePassword", str3);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.ag = null;
        j.f.ah = null;
        super.a(keyAt, 91, true);
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (ieVar) {
            case InputPasswordType:
                this.f = bundle.getString("Data.Password");
                if (this.g == 0) {
                    k();
                    return;
                } else if (this.g == 1) {
                    k();
                    return;
                } else {
                    return;
                }
            case ConnexionErrorType:
                if (h() == 91) {
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
            if (i2 == 91) {
                a((MakeBankAccountUpdateData) bundle.getParcelable("fr.smoney.android.izly.extras.changeTransferAccountData"), serverError);
            }
        }
    }

    public void afterTextChanged(Editable editable) {
        Button button = this.e;
        boolean z = this.c.c() && this.d.c() && this.b.getText().length() > 0;
        button.setEnabled(z);
    }

    public final void b_(int i) {
        cl i2 = i();
        if (i == 91) {
            a(i2.ag, i2.ah);
        }
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public final void d(ie ieVar) {
        switch (ieVar) {
            case InputPasswordType:
                return;
            default:
                super.d(ieVar);
                return;
        }
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        if (i2 == -1 && i == 11) {
            setResult(-1);
            finish();
        }
    }

    public void onClick(View view) {
        if (view == this.e) {
            a(hz.a(this, this));
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.add_transfer_account);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.g = extras.getInt("fr.smoney.android.izly.intentExtrasMode");
            this.b = (EditText) findViewById(R.id.et_alias);
            this.b.addTextChangedListener(this);
            this.c = (FormEditText) findViewById(R.id.et_iban);
            this.c.a(new ij(getString(R.string.add_transfer_account_iban_error_msg), Pattern.compile("[a-zA-Z]{2}[0-9]{2}[a-zA-Z0-9]{4}[0-9]{7}([a-zA-Z0-9]?){0,16}"), false));
            this.c.addTextChangedListener(this);
            this.d = (FormEditText) findViewById(R.id.et_bic);
            this.d.a(new ij(getString(R.string.add_transfer_account_bic_error_msg), Pattern.compile("([a-zA-Z]{4}[a-zA-Z]{2}[a-zA-Z0-9]{2}([a-zA-Z0-9]{3})?)"), false));
            this.d.addTextChangedListener(this);
            this.e = (Button) findViewById(R.id.b_submit);
            this.e.setOnClickListener(this);
            if (bundle != null) {
                this.f = bundle.getString("savedStateCurrentPassword");
            }
            jb.a(getApplicationContext(), R.string.screen_name_edit_bank_account_activity);
            return;
        }
        throw new RuntimeException("AddTransferAccountActivity.INTENT_EXTRAS_MODE is mandatory!");
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        finish();
        return true;
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString("savedStateCurrentPassword", this.f);
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }
}
