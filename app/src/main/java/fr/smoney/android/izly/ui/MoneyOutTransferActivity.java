package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import defpackage.gd;
import defpackage.hu;
import defpackage.hw;
import defpackage.ie;
import defpackage.is;
import defpackage.iu;
import defpackage.ja;
import defpackage.jb;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.MoneyOutTransferAccountData;
import fr.smoney.android.izly.data.model.MoneyOutTransferData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;

import java.util.Currency;
import java.util.Locale;

public class MoneyOutTransferActivity extends SmoneyABSActivity implements TextWatcher, OnClickListener, SmoneyRequestManager$a {
    private ToggleButton b;
    private ToggleButton c;
    private ToggleButton d;
    private ToggleButton e;
    private ToggleButton f;
    private ToggleButton g;
    private LinearLayout h;
    private EditText i;
    private TextView j;
    private Button k;
    private boolean l = false;
    private String m;
    private String n;
    private float[] o;
    private String p;
    private int q = -1;

    private void a(ToggleButton toggleButton, float f) {
        CharSequence format = String.format(Locale.getDefault(), "%1$.2f%2$s", new Object[]{Float.valueOf(f), this.p});
        toggleButton.setTextOn(format);
        toggleButton.setTextOff(format);
        toggleButton.setChecked(toggleButton.isChecked());
    }

    private void a(MoneyOutTransferAccountData moneyOutTransferAccountData, ServerError serverError) {
        if (serverError != null) {
            if (serverError.b == 302) {
                Intent a = is.a(this, AddTransferAccountActivity.class);
                a.putExtra("fr.smoney.android.izly.intentExtrasMode", 0);
                startActivityForResult(a, 10);
                return;
            }
            a(serverError);
        } else if (moneyOutTransferAccountData == null) {
            a(hw.a(this, this));
        } else {
            this.l = true;
            this.m = moneyOutTransferAccountData.a;
            this.n = moneyOutTransferAccountData.b;
            this.o = moneyOutTransferAccountData.d;
            if (this.m == null || this.m.length() == 0) {
                this.m = this.n;
            }
            i().b.B = moneyOutTransferAccountData.e;
            k();
            l();
        }
    }

    private void a(MoneyOutTransferData moneyOutTransferData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (moneyOutTransferData == null) {
            a(hw.a(this, this));
        } else {
            i().b.B = moneyOutTransferData.h;
            Intent a = is.a(this, MoneyOutTransferConfirmActivity.class);
            a.putExtra("fr.smoney.android.izly.extras.moneyOutTransferData", moneyOutTransferData);
            startActivity(a);
        }
    }

    private void k() {
        if (this.o != null) {
            if (this.o.length > 0) {
                a(this.b, this.o[0]);
                this.b.setVisibility(0);
            }
            if (this.o.length > 1) {
                a(this.c, this.o[1]);
                this.c.setVisibility(0);
            }
            if (this.o.length > 2) {
                a(this.d, this.o[2]);
                this.d.setVisibility(0);
            }
            if (this.o.length > 3) {
                a(this.e, this.o[3]);
                this.e.setVisibility(0);
            }
            if (this.o.length > 4) {
                a(this.f, this.o[4]);
                this.f.setVisibility(0);
            }
        }
        if (i().b != null) {
            String format = String.format(Locale.getDefault(), "%1$.2f%2$s", new Object[]{Double.valueOf(i().b.h), this.p});
            String format2 = String.format(Locale.getDefault(), "%1$.2f%2$s", new Object[]{Double.valueOf(r0.b.i), this.p});
            this.j.setText(getString(R.string.money_out_transfer_t_info, new Object[]{format, format2}));
        }
    }

    private void l() {
        Button button = this.k;
        boolean z = this.b.isChecked() || this.c.isChecked() || this.d.isChecked() || this.e.isChecked() || this.f.isChecked() || (this.g.isChecked() && iu.b(this.i.getText().toString()));
        button.setEnabled(z);
    }

    private void m() {
        int keyAt;
        cl i = i();
        SmoneyRequestManager j = j();
        String str = i.b.a;
        String str2 = i.b.c;
        int size = j.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            Intent intent = (Intent) j.b.valueAt(i2);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 51 && intent.getStringExtra("fr.smoney.android.izly.extras.moneyOutTransferAccountUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.moneyOutTransferAccountSessionId").equals(str2)) {
                keyAt = j.b.keyAt(i2);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 51);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.moneyOutTransferAccountUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.moneyOutTransferAccountSessionId", str2);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.A = null;
        j.f.B = null;
        super.a(keyAt, 51, true);
    }

    private void n() {
        boolean z;
        double o = o();
        cl i = i();
        if (o <= 0.0d) {
            a(hu.a(getString(R.string.general_dialog_error_negative_amount_title), getString(R.string.general_dialog_error_negative_amount_message), getString(17039370)));
            z = false;
        } else if (o < i.b.h && i.b.h >= 0.0d) {
            a(hu.a(getString(R.string.general_dialog_error_below_min_amount_title), getString(R.string.general_dialog_error_below_min_amount_message), getString(17039370)));
            z = false;
        } else if (o > i.b.i && i.b.i >= 0.0d) {
            a(hu.a(getString(R.string.general_dialog_error_above_max_amount_title), getString(R.string.general_dialog_error_above_max_amount_message), getString(17039370)));
            z = false;
        } else if (o > i.b.B.a) {
            a(hu.a(getString(R.string.general_dialog_error_above_max_amount_title), getString(R.string.general_dialog_error_amount_above_user_balance_message), getString(17039370)));
            z = false;
        } else {
            z = true;
        }
        if (z) {
            int keyAt;
            i = i();
            SmoneyRequestManager j = j();
            String str = i.b.a;
            String str2 = i.b.c;
            int size = j.b.size();
            for (int i2 = 0; i2 < size; i2++) {
                Intent intent = (Intent) j.b.valueAt(i2);
                if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 52 && intent.getStringExtra("fr.smoney.android.izly.extras.moneyOutTransferUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.moneyOutTransferSessionId").equals(str2) && intent.getDoubleExtra("fr.smoney.android.izly.extras.moneyOutTransferAmount", -1.0d) == o && intent.getStringExtra("fr.smoney.android.izly.extras.moneyOutTransferLabel").equals("")) {
                    keyAt = j.b.keyAt(i2);
                    break;
                }
            }
            keyAt = SmoneyRequestManager.a.nextInt(1000000);
            Intent intent2 = new Intent(j.c, SmoneyService.class);
            intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 52);
            intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
            intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
            intent2.putExtra("fr.smoney.android.izly.extras.moneyOutTransferUserId", str);
            intent2.putExtra("fr.smoney.android.izly.extras.moneyOutTransferSessionId", str2);
            intent2.putExtra("fr.smoney.android.izly.extras.moneyOutTransferAmount", o);
            intent2.putExtra("fr.smoney.android.izly.extras.moneyOutTransferLabel", "");
            j.c.startService(intent2);
            j.b.append(keyAt, intent2);
            j.f.C = null;
            j.f.D = null;
            super.a(keyAt, 52, true);
        }
    }

    private double o() {
        if (this.q != 5) {
            return (double) this.o[this.q];
        }
        try {
            return iu.a(this.i.getEditableText().toString());
        } catch (gd e) {
            a(hu.a(getString(R.string.general_dialog_error_amount_format_title), getString(R.string.general_dialog_error_amount_format_message), getString(17039370)));
            return -1.0d;
        }
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (ieVar) {
            case ConnexionErrorType:
                int h = h();
                if (h == 51) {
                    m();
                    return;
                } else if (h == 52) {
                    n();
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
            if (i2 == 51) {
                a((MoneyOutTransferAccountData) bundle.getParcelable("fr.smoney.android.izly.extras.moneyOutTransferAccountData"), serverError);
            } else if (i2 == 52) {
                a((MoneyOutTransferData) bundle.getParcelable("fr.smoney.android.izly.extras.moneyOutTransferData"), serverError);
            }
        }
    }

    public void afterTextChanged(Editable editable) {
        l();
    }

    public final void b(ie ieVar) {
        switch (ieVar) {
            case ConnexionErrorType:
                if (h() == 51) {
                    finish();
                    return;
                } else {
                    super.b(ieVar);
                    return;
                }
            default:
                super.b(ieVar);
                return;
        }
    }

    public final void b_(int i) {
        cl i2 = i();
        if (i == 51) {
            a(i2.A, i2.B);
        } else if (i == 52) {
            a(i2.C, i2.D);
        }
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public final void d(ie ieVar) {
        if (ieVar == ie.ProgressType && h() == 51) {
            g();
            finish();
            return;
        }
        super.d(ieVar);
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        if (i2 == -1 && i == 10) {
            m();
        }
    }

    public void onClick(View view) {
        boolean z = true;
        if (view == this.k) {
            n();
            return;
        }
        if (view == this.b) {
            this.q = 0;
        } else if (view == this.c) {
            this.q = 1;
        } else if (view == this.d) {
            this.q = 2;
        } else if (view == this.e) {
            this.q = 3;
        } else if (view == this.f) {
            this.q = 4;
        } else if (view == this.g) {
            this.q = 5;
        }
        this.b.setChecked(this.q == 0);
        this.c.setChecked(this.q == 1);
        this.d.setChecked(this.q == 2);
        this.e.setChecked(this.q == 3);
        this.f.setChecked(this.q == 4);
        ToggleButton toggleButton = this.g;
        if (this.q != 5) {
            z = false;
        }
        toggleButton.setChecked(z);
        l();
        if (this.q == 5) {
            this.h.setVisibility(0);
            this.i.requestFocus();
            return;
        }
        this.h.setVisibility(8);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.money_operation);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        this.p = Currency.getInstance(i().b.j).getSymbol();
        this.b = (ToggleButton) findViewById(R.id.tb_amount_1);
        this.c = (ToggleButton) findViewById(R.id.tb_amount_2);
        this.d = (ToggleButton) findViewById(R.id.tb_amount_3);
        this.e = (ToggleButton) findViewById(R.id.tb_amount_4);
        this.f = (ToggleButton) findViewById(R.id.tb_amount_5);
        this.g = (ToggleButton) findViewById(R.id.tb_amount_6);
        this.b.setOnClickListener(this);
        this.c.setOnClickListener(this);
        this.d.setOnClickListener(this);
        this.e.setOnClickListener(this);
        this.f.setOnClickListener(this);
        this.g.setOnClickListener(this);
        this.j = (TextView) findViewById(R.id.tv_amount);
        this.h = (LinearLayout) findViewById(R.id.fl_amount);
        this.i = (EditText) findViewById(R.id.et_amount);
        this.i.addTextChangedListener(this);
        this.i.setKeyListener(new ja(true, 3, 2));
        this.k = (Button) findViewById(R.id.b_submit);
        this.k.setOnClickListener(this);
        if (bundle != null) {
            this.l = bundle.getBoolean("savedStateIsAccountLoaded", false);
            this.m = bundle.getString("savedStateAccountAlias");
            this.n = bundle.getString("savedStateAccountHint");
            this.o = bundle.getFloatArray("savedStateAmountSteps");
            k();
            l();
        }
        if (!this.l) {
            m();
        }
        jb.a(getApplicationContext(), R.string.screen_name_money_out_activity);
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("savedStateIsAccountLoaded", this.l);
        bundle.putString("savedStateAccountAlias", this.m);
        bundle.putString("savedStateAccountHint", this.n);
        bundle.putFloatArray("savedStateAmountSteps", this.o);
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }
}
