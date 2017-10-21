package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import defpackage.hu;
import defpackage.hz;
import defpackage.ie;
import defpackage.is;
import defpackage.jb;
import defpackage.jk;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.MakeMoneyInBankAccountData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;
import fr.smoney.android.izly.ui.widget.DetailTwoText;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

public class MoneyInSepaConfirmActivity extends SmoneyABSActivity implements SmoneyRequestManager$a {
    private double b;
    private String c;
    private String d;
    private String e;
    private String f;
    private MakeMoneyInBankAccountData g;
    @Bind({2131755668})
    DetailTwoText mAmount;
    @Bind({2131755475})
    DetailTwoText mDate;
    @Bind({2131755382})
    ImageView mPhoto;
    @Bind({2131755385})
    TextView mRecipientName;
    @Bind({2131755870})
    TextView mRecipientType;
    @Bind({2131755869})
    TextView mTransactionType;

    private void a(MakeMoneyInBankAccountData makeMoneyInBankAccountData, ServerError serverError) {
        if (serverError == null) {
            this.g = makeMoneyInBankAccountData;
            this.c = jk.b(this, jk.a(this.g.a));
            Intent a = is.a(this, MoneyInSepaResultActivity.class);
            a.putExtra("fr.smoney.android.izly.extras.sepa_amount", this.b);
            a.putExtra("fr.smoney.android.izly.extras.sepa_date", this.c);
            a.putExtra("fr.smoney.android.izly.extras.sepa_iban", this.d);
            startActivity(a);
        } else if (serverError.b == 120) {
            a(hu.a(serverError.d, serverError.c, getString(17039370)));
        } else {
            a(serverError);
        }
    }

    private void k() {
        int keyAt;
        cl i = i();
        SmoneyRequestManager j = j();
        String str = i.b.a;
        String str2 = i.b.c;
        double d = this.b;
        String str3 = this.f;
        int size = j.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            Intent intent = (Intent) j.b.valueAt(i2);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 266 && intent.getStringExtra("fr.smoney.android.izly.extras.makeMoneyInBankAccountUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.makeMoneyInBankAccountSessionId").equals(str2) && intent.getStringExtra("fr.smoney.android.izly.extras.makeMoneyInBankAccountAmount").equals(Double.valueOf(d)) && intent.getStringExtra("fr.smoney.android.izly.extras.makeMoneyInBankAccountAmount").equals(str3)) {
                keyAt = j.b.keyAt(i2);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 266);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.makeMoneyInBankAccountUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.makeMoneyInBankAccountSessionId", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.makeMoneyInBankAccountAmount", d);
        intent2.putExtra("fr.smoney.android.izly.extras.makeMoneyInBankAccountPassword", str3);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.bV = null;
        super.a(keyAt, 266, true);
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (ieVar) {
            case InputPasswordType:
                this.f = bundle.getString("Data.Password");
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
            switch (i2) {
                case 232:
                    k();
                    return;
                case 266:
                    a((MakeMoneyInBankAccountData) bundle.getParcelable("fr.smoney.android.izly.extras.MakeMoneyInBankAccountData"), serverError);
                    return;
                default:
                    return;
            }
        }
    }

    public final void b_(int i) {
        cl i2 = i();
        switch (i) {
            case 232:
                k();
                return;
            case 266:
                a(i2.bX, i2.bY);
                return;
            default:
                return;
        }
    }

    public final void c(ie ieVar) {
        switch (ieVar) {
            case AlertType:
                a();
                return;
            default:
                return;
        }
    }

    @OnClick({2131755304})
    public void confirm() {
        a(hz.a(this, this));
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.money_in_sepa_confirm_activity);
        ButterKnife.bind(this);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        this.b = intent.getDoubleExtra("fr.smoney.android.izly.extras.money_in_amount", -1.0d);
        this.d = intent.getStringExtra("fr.smoney.android.izly.extras.money_in_iban");
        this.c = intent.getStringExtra("fr.smoney.android.izly.extras.money_in_date");
        this.e = Currency.getInstance(i().b.j).getSymbol();
        this.mPhoto.setImageDrawable(getResources().getDrawable(R.drawable.icon_home_moneyin));
        this.mTransactionType.setText(getResources().getString(R.string.reloading));
        this.mRecipientType.setText(getResources().getString(R.string.destinataire));
        if (this.b != -1.0d) {
            BigDecimal bigDecimal = new BigDecimal(this.b);
            this.mAmount.setRightText(String.format(Locale.getDefault(), "%1$.2f%2$s", new Object[]{Double.valueOf(bigDecimal.doubleValue()), this.e}));
        } else {
            this.mAmount.setRightText((int) R.string.confirm_result_tv_amount_without_amount);
        }
        this.c = jk.c(this, jk.a(this.c));
        this.mDate.setRightText(this.c);
        this.mRecipientType.setText(getString(R.string.sepa_bank_account));
        this.mRecipientName.setText(this.d);
        jb.a(getApplicationContext(), R.string.screen_name_money_in_sepa_confirm_activity);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        finish();
        return true;
    }
}
