package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import defpackage.gd;
import defpackage.go;
import defpackage.ht;
import defpackage.hu;
import defpackage.hw;
import defpackage.ie;
import defpackage.is;
import defpackage.iu;
import defpackage.je;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.MoneyInCbAndPayData;
import fr.smoney.android.izly.data.model.MoneyInCbAndPayRequestData;
import fr.smoney.android.izly.data.model.MoneyInCbCb;
import fr.smoney.android.izly.data.model.P2PPayCommerceInfos;
import fr.smoney.android.izly.data.model.P2PPayData;
import fr.smoney.android.izly.data.model.P2PPayRequestData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Iterator;
import java.util.Locale;

public class MoneyInCbAndPayActivity extends SmoneyABSActivity implements TextWatcher, OnClickListener, OnItemSelectedListener, SmoneyRequestManager$a {
    go b;
    int c = -1;
    private int d = -1;
    private double[] e;
    private P2PPayData f;
    private boolean g = false;
    private P2PPayCommerceInfos h;
    private String i;
    private P2PPayRequestData j;
    private boolean k;
    private boolean l = false;
    @Bind({2131755192})
    Button mButtonSubmit;
    @Bind({2131755479})
    LinearLayout mCardsLayout;
    @Bind({2131755509})
    EditText mEditTextAmount;
    @Bind({2131755577})
    LinearLayout mLayoutAmount;
    @Bind({2131755480})
    Spinner mSpinnerCard;
    @Bind({2131755391})
    TextView mTextViewAmount;
    @Bind({2131755292})
    ToggleButton mToggleButtonAmount1;
    @Bind({2131755293})
    ToggleButton mToggleButtonAmount2;
    @Bind({2131755297})
    ToggleButton mToggleButtonAmount6;

    private void a(ToggleButton toggleButton, double d) {
        CharSequence format = String.format(Locale.getDefault(), "%1$.2f%2$s", new Object[]{Double.valueOf(d), this.i});
        toggleButton.setTextOn(format);
        toggleButton.setTextOff(format);
        toggleButton.setChecked(toggleButton.isChecked());
    }

    private void a(MoneyInCbAndPayData moneyInCbAndPayData, ServerError serverError) {
        cl i = i();
        if (serverError != null) {
            a(serverError);
        } else if (moneyInCbAndPayData == null) {
            a(hw.a(this, this));
        } else {
            i.b.B = moneyInCbAndPayData.d;
            Intent a = is.a(this, MoneyInCbAndPayConfirmActivity.class);
            a.putExtra("fr.smoney.android.izly.extras.moneyInCbAndPayData", i().j);
            a.putExtra("startFromWebPayement", this.g);
            a.putExtra("startFromWebPayementObject", this.h);
            startActivity(a);
        }
    }

    private void a(MoneyInCbAndPayRequestData moneyInCbAndPayRequestData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (moneyInCbAndPayRequestData == null) {
            a(hw.a(this, this));
        } else {
            if (moneyInCbAndPayRequestData.a.h == -1.0d) {
                moneyInCbAndPayRequestData.a.h = this.j.a.h;
            }
            i().b.B = moneyInCbAndPayRequestData.d;
            Intent a = is.a(this, MoneyInCbAndPayRequestConfirmActivity.class);
            i().Y.a.v = this.j.f;
            a.putExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestData", i().Y);
            startActivity(a);
        }
    }

    private void a(P2PPayData p2PPayData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (p2PPayData == null) {
            a(hw.a(this, this));
        } else {
            this.f = p2PPayData;
            a(p2PPayData.d, p2PPayData.c);
        }
    }

    private void a(P2PPayRequestData p2PPayRequestData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (p2PPayRequestData == null) {
            a(hw.a(this, this));
        } else {
            this.j.d = p2PPayRequestData.d;
            this.j.c = p2PPayRequestData.c;
            i().U = this.j;
            a(this.j.d, this.j.c);
        }
    }

    private void a(double[] dArr, ArrayList<MoneyInCbCb> arrayList) {
        cl i = i();
        this.e = dArr;
        if (arrayList.size() == 0) {
            a(ht.a(getString(R.string.general_dialog_error_no_cb_title), getString(R.string.general_dialog_error_no_cb_message), getString(R.string.general_dialog_error_no_cb_button_add), getString(17039360), this, ie.NoCBType));
        } else {
            if (this.b == null) {
                this.b = new go(this);
                this.mSpinnerCard.setAdapter(this.b);
            }
            this.b.setNotifyOnChange(false);
            Iterator it = arrayList.iterator();
            int i2 = 0;
            int i3 = 0;
            while (it.hasNext()) {
                MoneyInCbCb moneyInCbCb = (MoneyInCbCb) it.next();
                this.b.add(moneyInCbCb);
                int i4 = moneyInCbCb.e ? i3 : i2;
                i3++;
                i2 = i4;
            }
            this.mSpinnerCard.setSelection(i2);
            this.b.notifyDataSetChanged();
        }
        a(this.mToggleButtonAmount1, this.e[0]);
        a(this.mToggleButtonAmount2, this.e[1]);
        k();
        if (i.b.f == -1.0d || i.b.g == -1.0d) {
            this.mTextViewAmount.setText(R.string.amount_other);
        } else {
            this.mTextViewAmount.setText(getString(R.string.amount_between_10_1500, new Object[]{iu.c(String.valueOf(i.b.f)), iu.c(String.valueOf(i.b.g)), this.i}));
        }
    }

    private boolean a(double d) {
        cl i = i();
        if (d <= 0.0d) {
            a(hu.a(getString(R.string.general_dialog_error_negative_amount_title), getString(R.string.general_dialog_error_negative_amount_message), getString(17039370)));
            return false;
        }
        if (d >= i.b.f) {
            if (d >= i.b.B.a - (this.d == 0 ? this.f.a.i : this.j.a.h)) {
                if (d <= i.b.g) {
                    return true;
                }
                a(hu.a(getString(R.string.general_dialog_error_above_max_amount_title), getString(R.string.general_dialog_error_above_max_amount_message), getString(17039370)));
                return false;
            }
        }
        a(hu.a(getString(R.string.general_dialog_error_below_min_amount_title), getString(R.string.general_dialog_error_below_min_amount_message), getString(17039370)));
        return false;
    }

    private void k() {
        Button button = this.mButtonSubmit;
        boolean z = (this.b == null || this.mSpinnerCard.getSelectedItemPosition() != -1) && (this.mToggleButtonAmount1.isChecked() || this.mToggleButtonAmount2.isChecked() || this.mToggleButtonAmount6.isChecked());
        button.setEnabled(z);
    }

    private void l() {
        cl i = i();
        super.a(j().a(i.b.a, this.f.a.d, this.f.a.j, this.f.a.m, i.b.c), 11, true);
    }

    private void m() {
        cl i = i();
        super.a(j().a(i.b.a, i.b.c, this.j.a.a, 0, this.j.a.k, this.j.a.h), 73, true);
    }

    private void n() {
        double q = q();
        if (a(q)) {
            int keyAt;
            cl i = i();
            String p = p();
            SmoneyRequestManager j = j();
            String str = i.b.a;
            String str2 = i.b.c;
            String str3 = this.f.a.d;
            double d = this.f.a.j;
            String str4 = this.f.a.m;
            int size = j.b.size();
            for (int i2 = 0; i2 < size; i2++) {
                Intent intent = (Intent) j.b.valueAt(i2);
                if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 13 && intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbAndPayUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbAndPaySessionId").equals(str2) && intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbAndPayCardId").equals(p) && intent.getDoubleExtra("fr.smoney.android.izly.extras.moneyInCbAndPayCbAmount", -1.0d) == q && intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRecipient").equals(str3) && intent.getDoubleExtra("fr.smoney.android.izly.extras.moneyInCbAndPayAmount", -1.0d) == d && intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbAndPayMessage").equals(str4)) {
                    keyAt = j.b.keyAt(i2);
                    break;
                }
            }
            keyAt = SmoneyRequestManager.a.nextInt(1000000);
            Intent intent2 = new Intent(j.c, SmoneyService.class);
            intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 13);
            intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
            intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
            intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbAndPayUserId", str);
            intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbAndPaySessionId", str2);
            intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbAndPayCardId", p);
            intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbAndPayEngagementId", -1);
            intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbAndPayCbAmount", q);
            intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRecipient", str3);
            intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbAndPayAmount", d);
            intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbAndPayMessage", str4);
            j.c.startService(intent2);
            j.b.append(keyAt, intent2);
            j.f.j = null;
            j.f.k = null;
            super.a(keyAt, 13, true);
        }
    }

    private void o() {
        double q = q();
        if (a(q)) {
            int keyAt;
            cl i = i();
            String p = p();
            double d = this.j.a.h;
            SmoneyRequestManager j = j();
            String str = i.b.a;
            String str2 = i.b.c;
            long j2 = this.j.a.a;
            String str3 = this.j.a.l;
            int size = j.b.size();
            for (int i2 = 0; i2 < size; i2++) {
                Intent intent = (Intent) j.b.valueAt(i2);
                if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 75 && intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestSessionId").equals(str2) && intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestCardId").equals(p) && intent.getDoubleExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestCbAmount", -1.0d) == q && intent.getDoubleExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestAmount", -1.0d) == d && intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestPayRequestId").equals(Long.valueOf(j2)) && intent.getDoubleExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestResponseStatus", -1.0d) == 0.0d && intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestResponseMessage").equals(str3)) {
                    keyAt = j.b.keyAt(i2);
                    break;
                }
            }
            keyAt = SmoneyRequestManager.a.nextInt(1000000);
            Intent intent2 = new Intent(j.c, SmoneyService.class);
            intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 75);
            intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
            intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
            intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestUserId", str);
            intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestSessionId", str2);
            intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestCardId", p);
            intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestEngagementId", -1);
            intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestCbAmount", q);
            intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestAmount", d);
            intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestPayRequestId", j2);
            intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestResponseStatus", 0);
            intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestResponseMessage", str3);
            j.c.startService(intent2);
            j.b.append(keyAt, intent2);
            j.f.Y = null;
            j.f.Z = null;
            super.a(keyAt, 75, true);
        }
    }

    private String p() {
        return this.b != null ? ((MoneyInCbCb) this.b.getItem(this.mSpinnerCard.getSelectedItemPosition())).a : null;
    }

    private double q() {
        if (this.c != 5) {
            return this.e[this.c];
        }
        try {
            return iu.a(this.mEditTextAmount.getEditableText().toString());
        } catch (gd e) {
            a(hu.a(getString(R.string.general_dialog_error_amount_format_title), getString(R.string.general_dialog_error_amount_format_message), getString(17039370)));
            return -1.0d;
        }
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (ieVar) {
            case ConnexionErrorType:
                switch (h()) {
                    case 11:
                        l();
                        return;
                    case 13:
                        n();
                        return;
                    case 73:
                        m();
                        return;
                    case 75:
                        o();
                        return;
                    default:
                        super.a(ieVar, bundle);
                        return;
                }
            case NoCBType:
                Intent a = is.a(this, CbAddActivity.class);
                a.putExtra("fr.smoney.android.izly.intentExtraMode", 1);
                startActivityForResult(a, 1);
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
                case 11:
                    a((P2PPayData) bundle.getParcelable("fr.smoney.android.izly.extras.p2pPayData"), serverError);
                    return;
                case 13:
                    a((MoneyInCbAndPayData) bundle.getParcelable("fr.smoney.android.izly.extras.moneyInCbAndPayData"), serverError);
                    return;
                case 73:
                    a((P2PPayRequestData) bundle.getParcelable("fr.smoney.android.izly.extras.p2pPayRequestData"), serverError);
                    return;
                case 75:
                    a((MoneyInCbAndPayRequestData) bundle.getParcelable("fr.smoney.android.izly.extras.moneyInCbAndPayRequestData"), serverError);
                    return;
                default:
                    return;
            }
        }
    }

    public void afterTextChanged(Editable editable) {
        k();
    }

    public final void b(ie ieVar) {
        switch (ieVar) {
            case ConnexionErrorType:
                if (h() == 11 || h() == 73) {
                    finish();
                    return;
                } else {
                    super.b(ieVar);
                    return;
                }
            case NoCBType:
                finish();
                return;
            default:
                super.b(ieVar);
                return;
        }
    }

    public final void b_(int i) {
        cl i2 = i();
        switch (i) {
            case 11:
                a(i2.f, i2.g);
                return;
            case 13:
                a(i2.j, i2.k);
                return;
            case 73:
                a(i2.U, i2.V);
                return;
            case 75:
                a(i2.Y, i2.Z);
                return;
            default:
                return;
        }
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public final void d(ie ieVar) {
        if (ieVar == ie.ProgressType && (h() == 11 || h() == 73)) {
            g();
            finish();
            return;
        }
        super.d(ieVar);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        switch (i) {
            case 1:
                if (i2 == -1) {
                    ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra("fr.smoney.android.izly.cbListExtra");
                    switch (this.d) {
                        case 0:
                            i().f.c = parcelableArrayListExtra;
                            this.f.c = parcelableArrayListExtra;
                            a(this.f.d, parcelableArrayListExtra);
                            return;
                        case 1:
                            i().U.c = parcelableArrayListExtra;
                            this.j.c = parcelableArrayListExtra;
                            a(this.j.d, parcelableArrayListExtra);
                            return;
                        default:
                            return;
                    }
                }
                finish();
                return;
            default:
                super.onActivityResult(i, i2, intent);
                return;
        }
    }

    public void onClick(View view) {
        boolean z = true;
        if (view == this.mButtonSubmit) {
            switch (this.d) {
                case 0:
                    n();
                    return;
                case 1:
                    o();
                    return;
                default:
                    return;
            }
        }
        if (view == this.mToggleButtonAmount1) {
            this.c = 0;
        } else if (view == this.mToggleButtonAmount2) {
            this.c = 1;
        } else if (view == this.mToggleButtonAmount6) {
            this.c = 5;
        }
        this.mToggleButtonAmount1.setChecked(this.c == 0);
        this.mToggleButtonAmount2.setChecked(this.c == 1);
        ToggleButton toggleButton = this.mToggleButtonAmount6;
        if (this.c != 5) {
            z = false;
        }
        toggleButton.setChecked(z);
        k();
        if (this.c == 5) {
            this.mLayoutAmount.setVisibility(0);
            this.mEditTextAmount.requestFocus();
            return;
        }
        je.a(this, this.mEditTextAmount);
        this.mLayoutAmount.setVisibility(8);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.money_operation);
        ButterKnife.bind(this);
        this.mCardsLayout.setVisibility(0);
        this.mToggleButtonAmount1.setVisibility(0);
        this.mToggleButtonAmount2.setVisibility(0);
        this.mToggleButtonAmount1.setOnClickListener(this);
        this.mToggleButtonAmount2.setOnClickListener(this);
        this.mToggleButtonAmount6.setOnClickListener(this);
        this.mButtonSubmit.setOnClickListener(this);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        this.i = Currency.getInstance(i().b.j).getSymbol();
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("fr.smoney.android.izly.extras.p2pPayData")) {
                this.d = 0;
                this.f = (P2PPayData) intent.getParcelableExtra("fr.smoney.android.izly.extras.p2pPayData");
                this.h = (P2PPayCommerceInfos) intent.getParcelableExtra("startFromWebPayementObject");
                this.g = this.f.g;
                this.l = intent.getBooleanExtra("fr.smoney.android.izly.extras.forceReloadP2PPay", false);
            }
            if (intent.hasExtra("fr.smoney.android.izly.extras.p2pPayRequestData")) {
                this.d = 1;
                this.j = (P2PPayRequestData) intent.getParcelableExtra("fr.smoney.android.izly.extras.p2pPayRequestData");
                this.k = intent.getBooleanExtra("fr.smoney.android.izly.extras.forceReloadP2PPay", false);
            }
        }
        if (this.l) {
            l();
        } else if (this.k) {
            m();
        } else if ((this.d == 0 && this.f.c.size() == 0) || (this.d == 1 && this.j.c.size() == 0)) {
            a(ht.a(getString(R.string.general_dialog_error_no_cb_title), getString(R.string.general_dialog_error_no_cb_message), getString(R.string.general_dialog_error_no_cb_button_add), getString(17039360), this, ie.NoCBType));
        } else {
            switch (this.d) {
                case 0:
                    a(this.f.d, this.f.c);
                    return;
                case 1:
                    a(this.j.d, this.j.c);
                    return;
                default:
                    return;
            }
        }
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
        k();
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
        k();
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }
}
