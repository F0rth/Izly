package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import defpackage.hz;
import defpackage.ie;
import defpackage.is;
import defpackage.jb;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.MoneyOutTransferConfirmData;
import fr.smoney.android.izly.data.model.MoneyOutTransferData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;
import fr.smoney.android.izly.ui.widget.DetailTwoText;

import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Locale;

public class MoneyOutTransferConfirmActivity extends SmoneyABSActivity implements OnClickListener, SmoneyRequestManager$a {
    private DetailTwoText b;
    private TextView c;
    private TextView d;
    private DetailTwoText e;
    private DetailTwoText f;
    private Button g;
    private String h;
    private String i;
    private MoneyOutTransferData j;

    private void a(MoneyOutTransferConfirmData moneyOutTransferConfirmData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (moneyOutTransferConfirmData == null) {
            a_(53);
        } else {
            i().b.B = moneyOutTransferConfirmData.i;
            Intent a = is.a(this, MoneyOutTransferResultActivity.class);
            a.putExtra("fr.smoney.android.izly.extras.moneyOutTransferConfirmData", moneyOutTransferConfirmData);
            startActivity(a);
        }
    }

    private void k() {
        int keyAt;
        cl i = i();
        SmoneyRequestManager j = j();
        String str = i.b.a;
        String str2 = i.b.c;
        double d = this.j.d;
        String str3 = this.h;
        int size = j.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            Intent intent = (Intent) j.b.valueAt(i2);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 53 && intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbConfirmUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbConfirmSessionId").equals(str2) && intent.getDoubleExtra("fr.smoney.android.izly.extras.moneyInCbConfirmAmount", -1.0d) == d && intent.getStringExtra("fr.smoney.android.izly.extras.moneyOutTransferLabel").equals("") && intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbConfirmPassword").equals(str3)) {
                keyAt = j.b.keyAt(i2);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 53);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbConfirmUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbConfirmSessionId", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.moneyOutTransferLabel", "");
        intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbConfirmAmount", d);
        intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbConfirmPassword", str3);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.E = null;
        j.f.F = null;
        super.a(keyAt, 53, true);
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (ieVar) {
            case ConnexionErrorType:
                if (h() == 53) {
                    k();
                    return;
                } else {
                    super.a(ieVar, bundle);
                    return;
                }
            case InputPasswordType:
                this.h = bundle.getString("Data.Password");
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
            if (i2 == 53) {
                a((MoneyOutTransferConfirmData) bundle.getParcelable("fr.smoney.android.izly.extras.moneyOutTransferConfirmData"), serverError);
            }
        }
    }

    public final void b_(int i) {
        cl i2 = i();
        if (i == 53) {
            a(i2.E, i2.F);
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
        if (view == this.g) {
            a(hz.a(this, this));
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.money_operation_confirm);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(false);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        this.b = (DetailTwoText) findViewById(R.id.tv_commission);
        this.c = (TextView) findViewById(R.id.tv_money_operation_alias);
        this.d = (TextView) findViewById(R.id.tv_money_operation_hint);
        this.e = (DetailTwoText) findViewById(R.id.tv_amount);
        this.f = (DetailTwoText) findViewById(R.id.tv_date);
        this.g = (Button) findViewById(R.id.b_confirm);
        this.g.setOnClickListener(this);
        ((ImageView) findViewById(R.id.operation_image)).setImageResource(R.drawable.icon_home_moneyout);
        this.i = Currency.getInstance(i().b.j).getSymbol();
        Intent intent = getIntent();
        if (intent != null) {
            this.j = (MoneyOutTransferData) intent.getParcelableExtra("fr.smoney.android.izly.extras.moneyOutTransferData");
        }
        if (bundle != null) {
            this.h = bundle.getString("savedStateCurrentPassword");
        }
        this.d.setText(this.j.c);
        this.c.setText(this.j.b);
        this.f.setRightText(new SimpleDateFormat("EEEE dd MMMM yyyy 'à' HH'h'mm", Locale.getDefault()).format(Long.valueOf(System.currentTimeMillis())));
        this.e.setRightText(String.format(Locale.getDefault(), "%1$.2f%2$s", new Object[]{Double.valueOf(this.j.d), this.i}));
        if (this.j.f > 0.0d) {
            this.b.setVisibility(0);
            this.b.setRightText(String.format(Locale.getDefault(), "%1$.2f%2$s", new Object[]{Double.valueOf(this.j.f), this.i}));
        }
        jb.a(getApplicationContext(), R.string.screen_name_money_out_confirm_activity);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                finish();
                break;
        }
        return true;
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString("savedStateCurrentPassword", this.h);
    }
}
