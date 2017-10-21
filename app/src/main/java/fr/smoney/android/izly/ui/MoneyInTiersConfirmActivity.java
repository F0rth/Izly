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
import defpackage.is;
import defpackage.jb;
import defpackage.jk;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;
import fr.smoney.android.izly.ui.widget.DetailTwoText;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

public class MoneyInTiersConfirmActivity extends SmoneyABSActivity implements SmoneyRequestManager$a {
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    @Bind({2131755668})
    DetailTwoText mAmount;
    @Bind({2131755475})
    DetailTwoText mDate;
    @Bind({2131755670})
    DetailTwoText mMessage;
    @Bind({2131755382})
    ImageView mPhoto;
    @Bind({2131755669})
    DetailTwoText mRecipient;
    @Bind({2131755385})
    TextView mRecipientName;
    @Bind({2131755870})
    TextView mRecipientType;
    @Bind({2131755869})
    TextView mTransactionType;

    private void b(ServerError serverError) {
        if (serverError == null) {
            Intent a = is.a(this, MoneyInTiersResultActivity.class);
            a.putExtra("fr.smoney.android.izly.extras.tiers_amount", this.b);
            a.putExtra("fr.smoney.android.izly.extras.tiers_recipient", this.d);
            a.putExtra("fr.smoney.android.izly.extras.tiers_message", this.c);
            a.putExtra("fr.smoney.android.izly.extras.tiers_date", this.e);
            startActivity(a);
            finish();
        } else if (serverError.b == 120) {
            a(hu.a(serverError.d, serverError.c, getString(17039370)));
        } else {
            a(serverError);
        }
    }

    public final void a_(int i, int i2, int i3, Bundle bundle) {
        if (super.a(i, i2, i3, bundle)) {
            ServerError serverError = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
            switch (i2) {
                case 241:
                    b(serverError);
                    return;
                default:
                    return;
            }
        }
    }

    public final void b_(int i) {
        cl i2 = i();
        switch (i) {
            case 241:
                b(i2.v);
                return;
            default:
                return;
        }
    }

    @OnClick({2131755304})
    public void confirm() {
        int keyAt;
        cl i = i();
        SmoneyRequestManager j = j();
        String str = i.b.a;
        String str2 = i.b.c;
        String str3 = this.d;
        String str4 = this.b;
        String str5 = this.c;
        int size = j.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            Intent intent = (Intent) j.b.valueAt(i2);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 241 && intent.getStringExtra("fr.smoney.android.izly.extras.MoneyInTiersUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.MoneyInTiersSessionId").equals(str2)) {
                keyAt = j.b.keyAt(i2);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 241);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.MoneyInTiersUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.MoneyInTiersSessionId", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.MoneyInTiersEmail", str3);
        intent2.putExtra("fr.smoney.android.izly.extras.MoneyInTiersAmount", str4);
        intent2.putExtra("fr.smoney.android.izly.extras.MoneyInTiersMessage", str5);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.v = null;
        super.a(keyAt, 241, true);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.confirm_tiers_activity);
        ButterKnife.bind(this);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        this.b = intent.getStringExtra("fr.smoney.android.izly.extras.tiers_amount");
        this.c = intent.getStringExtra("fr.smoney.android.izly.extras.tiers_message");
        this.d = intent.getStringExtra("fr.smoney.android.izly.extras.tiers_recipient");
        this.f = Currency.getInstance(i().b.j).getSymbol();
        this.mPhoto.setImageDrawable(getResources().getDrawable(R.drawable.icon_home_moneyin));
        this.mTransactionType.setText(getResources().getString(R.string.reloading));
        this.mRecipientType.setText(getResources().getString(R.string.destinataire));
        this.mRecipientName.setText(this.d);
        if (this.c == null || this.c.length() == 0) {
            this.mMessage.setVisibility(8);
        } else {
            this.mMessage.setRightText(this.c);
        }
        if (this.b != null || this.b.length() > 0) {
            BigDecimal bigDecimal = new BigDecimal(this.b);
            this.mAmount.setRightText(String.format(Locale.getDefault(), "%1$.2f%2$s", new Object[]{Double.valueOf(bigDecimal.doubleValue()), this.f}));
        } else {
            this.mAmount.setRightText((int) R.string.confirm_result_tv_amount_without_amount);
        }
        this.e = jk.b(this, new Date().getTime());
        this.mDate.setRightText(this.e);
        this.mRecipient.setRightText(this.d);
        jb.a(getApplicationContext(), R.string.screen_name_money_in_tier_confirm_activity);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        finish();
        return true;
    }
}
