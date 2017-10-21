package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import defpackage.hw;
import defpackage.ie;
import defpackage.is;
import defpackage.jh;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.SmoneyApplication;
import fr.smoney.android.izly.data.model.ActivationProcessData;
import fr.smoney.android.izly.data.model.GetUserActivationData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;

public class ActivationActivity extends SmoneyABSActivity implements TextWatcher, SmoneyRequestManager$a {
    String b;
    String c;
    private cl d;
    @Bind({2131755191})
    EditText mActivationCode;
    @Bind({2131755187})
    TextView mActivationCodeNotReceived;
    @Bind({2131755189})
    EditText mEmail;
    @Bind({2131755192})
    Button mSubmit;

    private void a(GetUserActivationData getUserActivationData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (getUserActivationData == null) {
            a(hw.a(this, this));
        } else {
            Intent a = is.a(this, ActivationStepOneActivity.class);
            a.putExtra("fr.smoney.android.izly.userActivationData", getUserActivationData);
            this.d.cp = new ActivationProcessData();
            this.d.cp.a = this.c;
            this.d.cp.b = this.b;
            startActivity(a);
        }
    }

    private void k() {
        int keyAt;
        if (SmoneyApplication.c.c() != null) {
            SmoneyApplication.c.d();
        }
        SmoneyRequestManager j = j();
        String str = this.c;
        String str2 = this.b;
        int size = j.b.size();
        for (int i = 0; i < size; i++) {
            Intent intent = (Intent) j.b.valueAt(i);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 274 && intent.getStringExtra("fr.smoney.android.izly.extras.getUserActivationDataEmail").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.getUserActivationDataActivationCode").equals(str2)) {
                keyAt = j.b.keyAt(i);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 274);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.getUserActivationDataEmail", str);
        intent2.putExtra("fr.smoney.android.izly.extras.getUserActivationDataActivationCode", str2);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.ck = null;
        super.a(keyAt, 274, true);
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
            if (i2 == 274) {
                a((GetUserActivationData) bundle.getParcelable("fr.smoney.android.izly.extras.GetUserActivationData"), serverError);
            }
        }
    }

    public void activationCodeNotReceived(View view) {
        Intent a = is.a(this, WebViewHelperActivity.class);
        a.putExtra("webview_url", "https://mon-espace.izly.fr/Auth/SendActivationMail");
        a.putExtra("title_tag", R.string.activation_first_connexion_title);
        a.putExtra("should_go_back", false);
        startActivity(a);
    }

    public void afterTextChanged(Editable editable) {
    }

    public final void b_(int i) {
        if (i == 274) {
            a(this.d.cj, this.d.ck);
        }
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.d = i();
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeButtonEnabled(true);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle(R.string.activation_first_connexion_title);
        }
        setContentView((int) R.layout.activity_activation);
        ButterKnife.bind(this);
        this.mEmail.addTextChangedListener(this);
        this.mActivationCode.addTextChangedListener(this);
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
        Button button = this.mSubmit;
        this.b = this.mActivationCode.getEditableText().toString().trim();
        this.c = this.mEmail.getEditableText().toString().trim();
        boolean z = jh.a(this.c) && this.c.length() > 0 && this.b.length() > 0 && this.b.length() == 10;
        button.setEnabled(z);
    }

    public void submit(View view) {
        k();
    }
}
