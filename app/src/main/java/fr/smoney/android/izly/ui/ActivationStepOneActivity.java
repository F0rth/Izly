package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import defpackage.hu;
import defpackage.hw;
import defpackage.hx;
import defpackage.ib;
import defpackage.ie;
import defpackage.is;
import defpackage.jk;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.AddressValues.a;
import fr.smoney.android.izly.data.model.CheckUserActivationData;
import fr.smoney.android.izly.data.model.GetUserActivationData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;

import java.util.EnumSet;
import java.util.Iterator;

public class ActivationStepOneActivity extends SmoneyABSActivity implements OnClickListener, SmoneyRequestManager$a {
    private int b = -1;
    private int c = 0;
    private Time d;
    private cl e;
    private GetUserActivationData f;
    private CharSequence[] g;
    private int[] h;
    @Bind({2131755206})
    Button mButtonBirthDate;
    @Bind({2131755200})
    Button mButtonCivility;
    @Bind({2131755208})
    Button mButtonCountry;
    @Bind({2131755202})
    EditText mFirstName;
    @Bind({2131755204})
    EditText mLastName;
    @Bind({2131755192})
    Button mSubmit;

    private void a(CheckUserActivationData checkUserActivationData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (checkUserActivationData == null) {
            a(hw.a(this, this));
        } else {
            Intent a = is.a(this, ActivationStepTwoActivity.class);
            a.putExtra("fr.smoney.android.izly.checkActivationData", checkUserActivationData);
            this.e.cp.c = this.b;
            this.e.cp.d = jk.b(this.d.toMillis(false));
            this.e.cp.e = n();
            this.e.cl.c.add(0, getString(R.string.activation_select_secret_question));
            this.e.cl.c.add(getString(R.string.activation_custom_question));
            startActivity(a);
        }
    }

    private void k() {
        this.mButtonCivility.setText(getResources().getStringArray(R.array.subscribe_civility_activation)[this.b + 1]);
    }

    private void l() {
        this.mButtonCountry.setText(this.g[this.c]);
    }

    private void m() {
        int keyAt;
        SmoneyRequestManager j = j();
        String str = this.e.cp.a;
        String str2 = this.e.cp.b;
        int i = this.b;
        String b = jk.b(this.d.toMillis(false));
        int n = n();
        int size = j.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            Intent intent = (Intent) j.b.valueAt(i2);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 275 && intent.getStringExtra("fr.smoney.android.izly.extras.checkUserActivationDataEmail").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.checkUserActivationDataActivationCode").equals(str2) && intent.getStringExtra("fr.smoney.android.izly.extras.checkUserActivationDataBirthdate").equals(b)) {
                keyAt = j.b.keyAt(i2);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 275);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.checkUserActivationDataEmail", str);
        intent2.putExtra("fr.smoney.android.izly.extras.checkUserActivationDataActivationCode", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.checkUserActivationDataCivility", i);
        intent2.putExtra("fr.smoney.android.izly.extras.checkUserActivationDataBirthdate", b);
        intent2.putExtra("fr.smoney.android.izly.extras.checkUserActivationDataCountry", n);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.cm = null;
        super.a(keyAt, 275, true);
    }

    private int n() {
        return this.h.length > 0 ? this.h[this.c] : 0;
    }

    private boolean o() {
        return this.b >= 0;
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (ieVar) {
            case ConnexionErrorType:
                if (h() != 275) {
                    super.a(ieVar, bundle);
                    break;
                } else {
                    m();
                    break;
                }
            case SelectCivilityType:
                this.b = bundle.getInt("Data.SelectItem") - 1;
                k();
                break;
            case SelectCountryType:
                this.c = bundle.getInt("Data.SelectItem");
                l();
                break;
            case SelectBirthDate:
                this.d.set(Long.valueOf(bundle.getLong("Data.Date")).longValue());
                this.mButtonBirthDate.setText(DateFormat.format("dd/MM/yyyy", this.d.toMillis(false)));
                break;
            default:
                super.a(ieVar, bundle);
                break;
        }
        this.mSubmit.setEnabled(o());
    }

    public final void a_(int i, int i2, int i3, Bundle bundle) {
        if (super.a(i, i2, i3, bundle)) {
            ServerError serverError = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
            if (i2 == 275) {
                a((CheckUserActivationData) bundle.getParcelable("fr.smoney.android.izly.extras.CheckUserActivationData"), serverError);
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

    public final void b_(int i) {
        if (i == 275) {
            a(this.e.cl, this.e.cm);
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

    public void onClick(View view) {
        if (view == this.mButtonCivility) {
            a(ib.a(getString(R.string.subscribe_dialog_civility_title), getResources().getTextArray(R.array.subscribe_civility_activation), this, ie.SelectCivilityType));
        } else if (view == this.mButtonCountry) {
            a(ib.a(getString(R.string.activation_country), this.g, this, ie.SelectCountryType));
        } else if (view == this.mButtonBirthDate) {
            a(hx.a(this.d, this, ie.SelectBirthDate));
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.e = i();
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeButtonEnabled(true);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle(R.string.activation_step_one_title);
        }
        setContentView((int) R.layout.activity_activation_step_one);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(2);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("fr.smoney.android.izly.userActivationData")) {
            this.f = (GetUserActivationData) intent.getParcelableExtra("fr.smoney.android.izly.userActivationData");
        }
        this.mButtonCivility.setOnClickListener(this);
        this.mButtonBirthDate.setOnClickListener(this);
        this.mButtonCountry.setOnClickListener(this);
        this.g = new String[EnumSet.allOf(a.class).size()];
        this.h = new int[EnumSet.allOf(a.class).size()];
        Iterator it = EnumSet.allOf(a.class).iterator();
        int i = 0;
        while (it.hasNext()) {
            a aVar = (a) it.next();
            this.g[i] = getString(aVar.G);
            this.h[i] = aVar.F;
            i++;
        }
        if (this.f != null) {
            this.mFirstName.setText(this.f.a);
            this.mLastName.setText(this.f.b);
        }
        this.d = new Time();
        this.d.set(jk.c(jk.a(this.f.c)));
        this.mButtonBirthDate.setText(DateFormat.format("dd/MM/yyyy", this.d.toMillis(true)));
        k();
        l();
        this.mSubmit.setEnabled(o());
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
        if (n() != a.Other.F) {
            m();
        } else {
            a(hu.a(getString(R.string.activation_not_authorized), getString(R.string.activation_other_country_error), getString(17039370)));
        }
    }
}
