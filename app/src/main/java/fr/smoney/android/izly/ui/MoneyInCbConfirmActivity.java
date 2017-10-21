package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import defpackage.hz;
import defpackage.ie;
import defpackage.is;
import defpackage.jb;
import defpackage.jk;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.MoneyInCbConfirmData;
import fr.smoney.android.izly.data.model.MoneyInCbData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;
import fr.smoney.android.izly.ui.widget.DetailTwoText;

import java.util.Currency;

public class MoneyInCbConfirmActivity extends SmoneyABSActivity implements OnClickListener, SmoneyRequestManager$a {
    private String b;
    private MoneyInCbData c;
    private String d;
    @Bind({2131755379})
    TextView mAlias;
    @Bind({2131755304})
    Button mButtonConfirm;
    @Bind({2131755390})
    DetailTwoText mDate;
    @Bind({2131755380})
    TextView mHint;
    @Bind({2131755391})
    DetailTwoText mTextViewAmount;
    @Bind({2131755495})
    DetailTwoText mTextViewCommission;

    private void a(MoneyInCbConfirmData moneyInCbConfirmData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (moneyInCbConfirmData == null) {
            a_(33);
        } else {
            i().b.B = moneyInCbConfirmData.e;
            Intent a = is.a(this, MoneyInCbResultActivity.class);
            a.putExtra("fr.smoney.android.izly.extras.moneyInCbConfirmData", moneyInCbConfirmData);
            startActivity(a);
        }
    }

    private void k() {
        int keyAt;
        cl i = i();
        SmoneyRequestManager j = j();
        String str = i.b.a;
        String str2 = i.b.c;
        String str3 = this.c.a.c.a;
        double d = this.c.a.e;
        String str4 = this.b;
        int size = j.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            Intent intent = (Intent) j.b.valueAt(i2);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 33 && intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbConfirmUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbConfirmSessionId").equals(str2) && intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbConfirmCardId").equals(str3) && intent.getDoubleExtra("fr.smoney.android.izly.extras.moneyInCbConfirmAmount", -1.0d) == d && intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbConfirmPassword").equals(str4)) {
                keyAt = j.b.keyAt(i2);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 33);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbConfirmUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbConfirmSessionId", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbConfirmCardId", str3);
        intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbConfirmAmount", d);
        intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbConfirmPassword", str4);
        intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbConfirmEngagementId", -1);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.w = null;
        j.f.x = null;
        super.a(keyAt, 33, true);
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (ieVar) {
            case ConnexionErrorType:
                if (h() == 33) {
                    k();
                    return;
                } else {
                    super.a(ieVar, bundle);
                    return;
                }
            case InputPasswordType:
                this.b = bundle.getString("Data.Password");
                k();
                return;
            default:
                super.a(ieVar, bundle);
                return;
        }
    }

    public final void a_(int i, int i2, int i3, Bundle bundle) {
        if (super.a(i, i2, i3, bundle)) {
            ServerError serverError = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
            if (i2 == 33) {
                a((MoneyInCbConfirmData) bundle.getParcelable("fr.smoney.android.izly.extras.moneyInCbConfirmData"), serverError);
            }
        }
    }

    public final void b_(int i) {
        cl i2 = i();
        if (i == 33) {
            a(i2.w, i2.x);
        }
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

    public void onClick(View view) {
        if (view == this.mButtonConfirm) {
            a(hz.a(this, this));
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        setContentView((int) R.layout.money_operation_confirm);
        ButterKnife.bind(this);
        this.mButtonConfirm = (Button) findViewById(R.id.b_confirm);
        this.mButtonConfirm.setOnClickListener(this);
        Intent intent = getIntent();
        if (intent != null) {
            this.c = (MoneyInCbData) intent.getParcelableExtra("fr.smoney.android.izly.extras.moneyInCbData");
        }
        if (bundle != null) {
            this.b = bundle.getString("savedStateCurrentPassword");
        }
        this.d = Currency.getInstance(i().b.j).getSymbol();
        this.mAlias.setText(this.c.a.c.b);
        this.mHint.setText(this.c.a.c.d);
        this.mTextViewAmount.setRightText(String.format("%1$.2f%2$s", new Object[]{Double.valueOf(this.c.a.e), this.d}));
        this.mTextViewCommission.setRightText(String.format("%1$.2f%2$s", new Object[]{Double.valueOf(this.c.a.f), this.d}));
        this.mDate.setRightText(jk.b(this, this.c.a.b));
        jb.a(getApplicationContext(), R.string.screen_name_money_in_cb_confirm_activity);
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
        bundle.putString("savedStateCurrentPassword", this.b);
    }
}
