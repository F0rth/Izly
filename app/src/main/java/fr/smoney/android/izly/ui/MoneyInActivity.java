package fr.smoney.android.izly.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.IconTextView;
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
import defpackage.hv;
import defpackage.hw;
import defpackage.ie;
import defpackage.if$a;
import defpackage.is;
import defpackage.iu;
import defpackage.ja;
import defpackage.jb;
import defpackage.jh;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.CardPaymentsData;
import fr.smoney.android.izly.data.model.CheckMoneyInBankAccountData;
import fr.smoney.android.izly.data.model.GetActiveMandateData;
import fr.smoney.android.izly.data.model.LoginLightData;
import fr.smoney.android.izly.data.model.MoneyInCbCb;
import fr.smoney.android.izly.data.model.MoneyInCbCbListData;
import fr.smoney.android.izly.data.model.MoneyInCbData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;
import fr.smoney.android.izly.ui.widget.ContactAutoCompleteTextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Iterator;
import java.util.Locale;

public class MoneyInActivity extends SmoneyABSActivity implements TextWatcher, OnClickListener, OnItemSelectedListener, SmoneyRequestManager$a, if$a {
    public static String b = "fr.smoney.android.izly.ui.MoneyInActivity.VMeAmount";
    public static String c = "fr.smoney.android.izly.ui.MoneyInActivity.VMeDate";
    public static String d = "fr.smoney.android.izly.ui.MoneyInActivity.VMeIdTransaction";
    public static String e = "fr.smoney.android.izly.ui.MoneyInActivity.VMeResult";
    go f;
    int g = -1;
    private a h;
    private int[] i;
    private String j;
    private MoneyInCbCbListData k;
    private CardPaymentsData l;
    private GetActiveMandateData m;
    @Bind({2131755810})
    IconTextView mButtonChooseContact;
    @Bind({2131755192})
    Button mButtonSubmit;
    @Bind({2131755479})
    LinearLayout mCardsLayout;
    @Bind({2131755509})
    EditText mEditTextAmount;
    @Bind({2131755287})
    EditText mEditTextBic;
    @Bind({2131755288})
    EditText mEditTextIban;
    @Bind({2131755577})
    LinearLayout mLayoutAmount;
    @Bind({2131755877})
    LinearLayout mSepaLayout;
    @Bind({2131755480})
    Spinner mSpinnerCard;
    @Bind({2131755391})
    TextView mTextViewAmount;
    @Bind({2131755926})
    LinearLayout mTiersLayout;
    @Bind({2131755808})
    ContactAutoCompleteTextView mTiersMail;
    @Bind({2131755292})
    ToggleButton mToggleButtonAmount1;
    @Bind({2131755293})
    ToggleButton mToggleButtonAmount2;
    @Bind({2131755294})
    ToggleButton mToggleButtonAmount3;
    @Bind({2131755295})
    ToggleButton mToggleButtonAmount4;
    @Bind({2131755296})
    ToggleButton mToggleButtonAmount5;
    @Bind({2131755297})
    ToggleButton mToggleButtonAmount6;
    @Bind({2131755651})
    EditText messageEditText;
    @Bind({2131755650})
    View messageView;
    private int n;
    private ArrayList<ToggleButton> o;

    final class a extends BroadcastReceiver {
        final /* synthetic */ MoneyInActivity a;

        private a(MoneyInActivity moneyInActivity) {
            this.a = moneyInActivity;
        }

        public final void onReceive(Context context, Intent intent) {
            int intExtra = intent.getIntExtra("fr.smoney.android.izly.sessionState", -1);
            if (intExtra == 0) {
                this.a.e();
            } else if (intExtra == 1) {
                switch (this.a.n) {
                    case 3:
                        this.a.p();
                        return;
                    default:
                        return;
                }
            } else if (intExtra == 2) {
                this.a.a(hw.a(this.a, this.a, ie.ConnexionErrorDuringIsSessionValid));
            }
        }
    }

    private void a(CardPaymentsData cardPaymentsData, ServerError serverError) {
        i();
        if (serverError != null) {
            a(serverError);
        } else if (cardPaymentsData == null) {
            a(hw.a(this, this));
        } else {
            this.l = cardPaymentsData;
            Intent intent = new Intent(this, WebViewHelperActivity.class);
            intent.putExtra("webview_url", this.l.a);
            intent.putExtra("title_tag", R.string.title_activity_reload_vme);
            startActivityForResult(intent, 100);
            jb.a(getApplicationContext(), R.string.screen_name_money_in_v_me_webview_activity);
        }
    }

    private void a(CheckMoneyInBankAccountData checkMoneyInBankAccountData, ServerError serverError) {
        i();
        if (serverError != null) {
            a(serverError);
        } else if (checkMoneyInBankAccountData == null) {
            a(hw.a(this, this));
        } else {
            Intent intent = new Intent(this, MoneyInSepaConfirmActivity.class);
            intent.putExtra("fr.smoney.android.izly.extras.money_in_amount", checkMoneyInBankAccountData.b);
            intent.putExtra("fr.smoney.android.izly.extras.money_in_iban", this.m.e.c);
            intent.putExtra("fr.smoney.android.izly.extras.money_in_date", checkMoneyInBankAccountData.a);
            startActivity(intent);
        }
    }

    private void a(GetActiveMandateData getActiveMandateData, ServerError serverError) {
        cl i = i();
        if (serverError != null) {
            if (serverError.b == 1019) {
                a(hv.a(serverError.d, serverError.c, getString(R.string.button_ok), this, ie.AlertType));
            } else {
                a(serverError);
            }
        } else if (getActiveMandateData == null) {
            a(hw.a(this, this));
        } else {
            this.m = getActiveMandateData;
            i.b.B = getActiveMandateData.a;
            b();
            this.i = this.m.d;
            k();
            if (this.m.c == -1.0d || this.m.b == -1.0d) {
                this.mTextViewAmount.setText(R.string.amount_other);
            } else {
                this.mTextViewAmount.setText(getString(R.string.amount_between_10_1500, new Object[]{iu.c(String.valueOf(this.m.c)), iu.c(String.valueOf(this.m.b)), this.j}));
            }
            if (this.m.e == null) {
                a(hv.a(getString(R.string.dialog_error_title_empty_mandate), getString(R.string.dialog_error_message_empty_mandate), getString(R.string.button_ok), this, ie.AlertType));
                return;
            }
            this.mEditTextBic.setText(this.m.e.a);
            this.mEditTextIban.setText(this.m.e.b);
        }
    }

    private void a(LoginLightData loginLightData, ServerError serverError) {
        i();
        if (serverError != null) {
            a(serverError);
        } else if (loginLightData == null) {
            a(hw.a(this, this));
        } else {
            p();
        }
    }

    private void a(MoneyInCbCbListData moneyInCbCbListData, ServerError serverError) {
        cl i = i();
        if (serverError != null) {
            a(serverError);
        } else if (moneyInCbCbListData == null) {
            a(hw.a(this, this));
        } else {
            this.k = moneyInCbCbListData;
            i.b.B = moneyInCbCbListData.c;
            a(moneyInCbCbListData.b, moneyInCbCbListData.a);
        }
    }

    private void a(MoneyInCbData moneyInCbData, ServerError serverError) {
        cl i = i();
        if (serverError != null) {
            a(serverError);
        } else if (moneyInCbData == null) {
            a(hw.a(this, this));
        } else {
            i.b.B = moneyInCbData.c;
            Intent a = is.a(this, MoneyInCbConfirmActivity.class);
            a.putExtra("fr.smoney.android.izly.extras.moneyInCbData", i().t);
            startActivity(a);
        }
    }

    private void a(int[] iArr, ArrayList<MoneyInCbCb> arrayList) {
        cl i = i();
        this.i = iArr;
        if (this.n == 1) {
            if (arrayList.size() == 0) {
                a(ht.a(getString(R.string.general_dialog_error_no_cb_title), getString(R.string.general_dialog_error_no_cb_message), getString(R.string.general_dialog_error_no_cb_button_add), getString(17039360), this, ie.NoCBType));
            } else {
                if (this.f == null) {
                    this.f = new go(this);
                    this.mSpinnerCard.setAdapter(this.f);
                }
                this.f.setNotifyOnChange(false);
                Iterator it = i.r.a.iterator();
                int i2 = 0;
                int i3 = 0;
                while (it.hasNext()) {
                    MoneyInCbCb moneyInCbCb = (MoneyInCbCb) it.next();
                    this.f.add(moneyInCbCb);
                    int i4 = moneyInCbCb.e ? i3 : i2;
                    i3++;
                    i2 = i4;
                }
                this.mSpinnerCard.setSelection(i2);
                this.f.notifyDataSetChanged();
            }
        }
        k();
        l();
        if (i.b.f == -1.0d || i.b.g == -1.0d) {
            this.mTextViewAmount.setText(R.string.amount_other);
        } else {
            this.mTextViewAmount.setText(getString(R.string.amount_between_10_1500, new Object[]{iu.c(String.valueOf(i.b.f)), iu.c(String.valueOf(i.b.g)), this.j}));
        }
    }

    private void k() {
        if (this.i != null) {
            Iterator it = this.o.iterator();
            int i = 0;
            while (it.hasNext()) {
                ToggleButton toggleButton = (ToggleButton) it.next();
                if (i < this.i.length) {
                    toggleButton.setVisibility(0);
                    int i2 = this.i[i];
                    CharSequence format = String.format(Locale.getDefault(), "%1$d %2$s", new Object[]{Integer.valueOf(i2), this.j});
                    toggleButton.setTextOn(format);
                    toggleButton.setTextOff(format);
                    toggleButton.setChecked(toggleButton.isChecked());
                    i++;
                } else {
                    toggleButton.setVisibility(8);
                }
            }
        }
    }

    private void l() {
        boolean z = true;
        Button button;
        switch (this.n) {
            case 3:
                button = this.mButtonSubmit;
                if ((this.f != null && this.mSpinnerCard.getSelectedItemPosition() == -1) || (!(this.mToggleButtonAmount1.isChecked() || this.mToggleButtonAmount2.isChecked() || this.mToggleButtonAmount3.isChecked() || this.mToggleButtonAmount4.isChecked() || this.mToggleButtonAmount5.isChecked() || (this.mToggleButtonAmount6.isChecked() && iu.b(this.mEditTextAmount.getText().toString()))) || r() < this.m.c || r() > this.m.b)) {
                    z = false;
                }
                button.setEnabled(z);
                return;
            default:
                button = this.mButtonSubmit;
                if ((this.f != null && this.mSpinnerCard.getSelectedItemPosition() == -1) || (!(this.mToggleButtonAmount1.isChecked() || this.mToggleButtonAmount2.isChecked() || this.mToggleButtonAmount3.isChecked() || this.mToggleButtonAmount4.isChecked() || this.mToggleButtonAmount5.isChecked() || (this.mToggleButtonAmount6.isChecked() && iu.b(this.mEditTextAmount.getText().toString()))) || r() < i().b.f || r() > i().b.g)) {
                    z = false;
                }
                button.setEnabled(z);
                return;
        }
    }

    private void m() {
        boolean z = true;
        k();
        this.mToggleButtonAmount1.setChecked(this.g == 0);
        this.mToggleButtonAmount2.setChecked(this.g == 1);
        this.mToggleButtonAmount3.setChecked(this.g == 2);
        this.mToggleButtonAmount4.setChecked(this.g == 3);
        this.mToggleButtonAmount5.setChecked(this.g == 4);
        ToggleButton toggleButton = this.mToggleButtonAmount6;
        if (this.g != 5) {
            z = false;
        }
        toggleButton.setChecked(z);
    }

    private void n() {
        int keyAt;
        cl i = i();
        SmoneyRequestManager j = j();
        String str = i.b.a;
        String str2 = i.b.c;
        int size = j.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            Intent intent = (Intent) j.b.valueAt(i2);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 31 && intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbCbListUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbCbListSessionId").equals(str2)) {
                keyAt = j.b.keyAt(i2);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 31);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbCbListUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbCbListSessionId", str2);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.r = null;
        j.f.s = null;
        super.a(keyAt, 31, true);
    }

    private void o() {
        int i;
        int i2 = 0;
        double r = r();
        cl i3 = i();
        if (r <= 0.0d) {
            a(hu.a(getString(R.string.general_dialog_error_negative_amount_title), getString(R.string.general_dialog_error_negative_amount_message), getString(17039370)));
            i = 0;
        } else if (r < i3.b.f) {
            a(hu.a(getString(R.string.general_dialog_error_below_min_amount_title), getString(R.string.general_dialog_error_below_min_amount_message), getString(17039370)));
            i = 0;
        } else if (r > i3.b.g) {
            a(hu.a(getString(R.string.general_dialog_error_above_max_amount_title), getString(R.string.general_dialog_error_above_max_amount_message), getString(17039370)));
            i = 0;
        } else {
            boolean z = true;
        }
        if (i != 0) {
            cl i4 = i();
            String str = this.f != null ? ((MoneyInCbCb) this.f.getItem(this.mSpinnerCard.getSelectedItemPosition())).a : null;
            SmoneyRequestManager j = j();
            String str2 = i4.b.a;
            String str3 = i4.b.c;
            int size = j.b.size();
            while (i2 < size) {
                Intent intent = (Intent) j.b.valueAt(i2);
                if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 32 && intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbUserId").equals(str2) && intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbSessionId").equals(str3) && intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbCardId").equals(str) && intent.getDoubleExtra("fr.smoney.android.izly.extras.moneyInCbAmount", -1.0d) == r) {
                    i = j.b.keyAt(i2);
                    break;
                }
                i2++;
            }
            i = SmoneyRequestManager.a.nextInt(1000000);
            Intent intent2 = new Intent(j.c, SmoneyService.class);
            intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 32);
            intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
            intent2.putExtra("com.foxykeep.datadroid.extras.requestId", i);
            intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbUserId", str2);
            intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbSessionId", str3);
            intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbCardId", str);
            intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbAmount", r);
            intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbEgagementId", -1);
            j.c.startService(intent2);
            j.b.append(i, intent2);
            j.f.t = null;
            j.f.u = null;
            super.a(i, 32, true);
        }
    }

    private void p() {
        int keyAt;
        cl i = i();
        SmoneyRequestManager j = j();
        String str = i.b.a;
        String str2 = i.b.c;
        int size = j.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            Intent intent = (Intent) j.b.valueAt(i2);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 264 && intent.getStringExtra("fr.smoney.android.izly.extras.getMandatesUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.getMandatesSessionId").equals(str2)) {
                keyAt = j.b.keyAt(i2);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 264);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.getMandatesUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.getMandatesSessionId", str2);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.bT = null;
        super.a(keyAt, 264, true);
    }

    private void q() {
        int keyAt;
        double r = r();
        cl i = i();
        SmoneyRequestManager j = j();
        String str = i.b.a;
        String str2 = i.b.c;
        int size = j.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            Intent intent = (Intent) j.b.valueAt(i2);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 267 && intent.getStringExtra("fr.smoney.android.izly.extras.checkMoneyInBankAccountUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.checkMoneyInBankAccountSessionId").equals(str2) && intent.getStringExtra("fr.smoney.android.izly.extras.checkMoneyInBankAccountAmount").equals(Double.valueOf(r))) {
                keyAt = j.b.keyAt(i2);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 265);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.checkMoneyInBankAccountUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.checkMoneyInBankAccountSessionId", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.checkMoneyInBankAccountAmount", r);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.bV = null;
        super.a(keyAt, 265, true);
    }

    private double r() {
        if (this.g != 5) {
            return (double) this.i[this.g];
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
                int h = h();
                if (h == 31) {
                    n();
                    return;
                } else if (h == 32) {
                    o();
                    return;
                } else if (h == 264) {
                    p();
                    return;
                } else {
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
                case 31:
                    a((MoneyInCbCbListData) bundle.getParcelable("fr.smoney.android.izly.extras.moneyInCbCbListData"), serverError);
                    return;
                case 32:
                    a((MoneyInCbData) bundle.getParcelable("fr.smoney.android.izly.extras.moneyInCbData"), serverError);
                    return;
                case 232:
                    a((LoginLightData) bundle.getParcelable("fr.smoney.android.izly.extras.LoginLight"), serverError);
                    return;
                case 263:
                    a((CardPaymentsData) bundle.getParcelable("fr.smoney.android.izly.extras.cardPayments"), serverError);
                    return;
                case 264:
                    a((GetActiveMandateData) bundle.getParcelable("fr.smoney.android.izly.extras.GetActiveMandate"), serverError);
                    return;
                case 265:
                    a((CheckMoneyInBankAccountData) bundle.getParcelable("fr.smoney.android.izly.extras.CheckMoneyInBankAccount"), serverError);
                    return;
                default:
                    return;
            }
        }
    }

    public void afterTextChanged(Editable editable) {
        l();
    }

    public final void b(ie ieVar) {
        switch (ieVar) {
            case ConnexionErrorType:
                if (h() == 31 || h() == 264) {
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
            case 31:
                a(i2.r, i2.s);
                return;
            case 32:
                a(i2.t, i2.u);
                return;
            case 232:
                a(i2.bJ, i2.bK);
                return;
            case 263:
                a(i2.bR, i2.bS);
                return;
            case 264:
                a(i2.bT, i2.bU);
                return;
            case 265:
                a(i2.bV, i2.bW);
                return;
            default:
                return;
        }
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public final void c(ie ieVar) {
        if (ieVar == ie.AlertType) {
            finish();
        } else {
            super.c(ieVar);
        }
    }

    public final void d(ie ieVar) {
        if (ieVar == ie.ProgressType && h() == 31) {
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
                    cl i3 = i();
                    ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra("fr.smoney.android.izly.cbListExtra");
                    i3.r.a = parcelableArrayListExtra;
                    a(i3.r.b, parcelableArrayListExtra);
                    return;
                }
                finish();
                return;
            case 2:
                if (i2 == 1) {
                    this.mTiersMail.setText(intent.getStringExtra("resultIntentExtrasContactId"));
                    return;
                }
                return;
            case 100:
                if (intent != null) {
                    Intent intent2 = new Intent(this, MoneyInVmeResult.class);
                    intent2.putExtra("fr.smoney.android.izly.extras.vme_amount", intent.getExtras().getString(b));
                    intent2.putExtra("fr.smoney.android.izly.extras.vme_date", intent.getExtras().getString(c));
                    finish();
                    startActivity(intent2);
                    return;
                }
                return;
            default:
                super.onActivityResult(i, i2, intent);
                return;
        }
    }

    public void onClick(View view) {
        Intent a;
        if (view == this.mButtonSubmit) {
            switch (this.n) {
                case 1:
                    o();
                    return;
                case 2:
                    if (jh.a(this.mTiersMail.getText().toString())) {
                        a = is.a(this, MoneyInTiersConfirmActivity.class);
                        a.putExtra("fr.smoney.android.izly.extras.tiers_amount", new Double(r()).toString());
                        a.putExtra("fr.smoney.android.izly.extras.tiers_recipient", this.mTiersMail.getText().toString());
                        a.putExtra("fr.smoney.android.izly.extras.tiers_message", this.messageEditText.getText().toString());
                        startActivity(a);
                        return;
                    }
                    this.mTiersMail.setError(getString(R.string.invalid_mail));
                    return;
                case 3:
                    q();
                    return;
                case 4:
                    cl i = i();
                    super.a(j().b(i.b.a, i.b.c, String.valueOf(new BigDecimal(r()).multiply(new BigDecimal(100)).doubleValue()), "V_ME"), 263, true);
                    return;
                default:
                    return;
            }
        } else if (view != this.mButtonChooseContact) {
            if (view == this.mToggleButtonAmount1) {
                this.g = 0;
            } else if (view == this.mToggleButtonAmount2) {
                this.g = 1;
            } else if (view == this.mToggleButtonAmount3) {
                this.g = 2;
            } else if (view == this.mToggleButtonAmount4) {
                this.g = 3;
            } else if (view == this.mToggleButtonAmount5) {
                this.g = 4;
            } else if (view == this.mToggleButtonAmount6) {
                this.g = 5;
            }
            m();
            l();
            if (this.g == 5) {
                this.mLayoutAmount.setVisibility(0);
                this.mEditTextAmount.requestFocus();
                return;
            }
            this.mLayoutAmount.setVisibility(8);
        } else if (ContextCompat.checkSelfPermission(this, "android.permission.READ_CONTACTS") != 0) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_CONTACTS"}, 118);
        } else {
            a = is.a(this, AllContactsActivity.class);
            a.putExtra("fr.smoney.android.izly.intentExtrasModeContactPicker", 1);
            a.putExtra("fr.smoney.android.izly.intentExtrasModeTypeContact", true);
            startActivityForResult(a, 2);
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.money_operation);
        ButterKnife.bind(this);
        this.n = getIntent().getIntExtra("MONEY_IN_MODE", -1);
        if (this.n == -1) {
            finish();
        }
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        this.j = Currency.getInstance(i().b.j).getSymbol();
        this.mSpinnerCard.setOnItemSelectedListener(this);
        this.mToggleButtonAmount1.setOnClickListener(this);
        this.mToggleButtonAmount1.setVisibility(0);
        this.mToggleButtonAmount2.setOnClickListener(this);
        this.mToggleButtonAmount2.setVisibility(0);
        this.mToggleButtonAmount2.setOnClickListener(this);
        this.mToggleButtonAmount3.setVisibility(0);
        this.mToggleButtonAmount3.setOnClickListener(this);
        this.mToggleButtonAmount4.setVisibility(0);
        this.mToggleButtonAmount4.setOnClickListener(this);
        this.mToggleButtonAmount5.setVisibility(0);
        this.mToggleButtonAmount5.setOnClickListener(this);
        this.mToggleButtonAmount6.setVisibility(0);
        this.mToggleButtonAmount6.setOnClickListener(this);
        this.o = new ArrayList();
        this.o.add(this.mToggleButtonAmount1);
        this.o.add(this.mToggleButtonAmount2);
        this.o.add(this.mToggleButtonAmount3);
        this.o.add(this.mToggleButtonAmount4);
        this.o.add(this.mToggleButtonAmount5);
        this.mEditTextAmount.addTextChangedListener(this);
        this.mEditTextAmount.setKeyListener(new ja(true, 3, 2));
        this.mButtonSubmit.setOnClickListener(this);
        this.mButtonChooseContact.setOnClickListener(this);
        switch (this.n) {
            case 1:
                this.mCardsLayout.setVisibility(0);
                if (bundle != null) {
                    this.k = (MoneyInCbCbListData) bundle.getParcelable("moneyInCbCbListData");
                    this.m = (GetActiveMandateData) bundle.getParcelable("mGetActiveMandateData");
                    if (this.m != null) {
                        this.i = this.m.d;
                    }
                }
                if (this.k == null) {
                    n();
                    break;
                } else {
                    a(this.k.b, this.k.a);
                    break;
                }
            case 2:
                this.mTiersLayout.setVisibility(0);
                this.messageView.setVisibility(0);
                n();
                break;
            case 3:
                this.mSepaLayout.setVisibility(0);
                p();
                break;
            case 4:
                setTitle(getResources().getString(R.string.title_activity_reload_vme));
                n();
                break;
        }
        switch (this.n) {
            case 1:
                jb.a(getApplicationContext(), R.string.screen_name_money_in_cb_activity);
                return;
            case 2:
                jb.a(getApplicationContext(), R.string.screen_name_money_in_tier_activity);
                return;
            case 3:
                jb.a(getApplicationContext(), R.string.screen_name_money_in_sepa_activity);
                return;
            case 4:
                jb.a(getApplicationContext(), R.string.screen_name_money_in_v_me_activity);
                return;
            default:
                return;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
        l();
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
        l();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        finish();
        return true;
    }

    protected void onPause() {
        unregisterReceiver(this.h);
        this.h = null;
        super.onPause();
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        switch (i) {
            case 118:
                if (iArr.length > 0 && iArr[0] == 0) {
                    Intent a = is.a(this, AllContactsActivity.class);
                    a.putExtra("fr.smoney.android.izly.intentExtrasModeContactPicker", 1);
                    a.putExtra("fr.smoney.android.izly.intentExtrasModeTypeContact", true);
                    startActivityForResult(a, 2);
                    return;
                }
                return;
            default:
                return;
        }
    }

    protected void onResume() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("fr.smoney.android.izly.notifications.NOTIFICATION_SESSION_STATE_CHANGE_INTENT_URI");
        if (this.h == null) {
            this.h = new a();
        }
        registerReceiver(this.h, intentFilter);
        super.onResume();
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (this.k != null) {
            bundle.putParcelable("moneyInCbCbListData", this.k);
        }
        bundle.putParcelable("mGetActiveMandateData", this.m);
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }
}
