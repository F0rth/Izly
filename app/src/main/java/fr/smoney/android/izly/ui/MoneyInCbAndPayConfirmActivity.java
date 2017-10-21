package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import defpackage.hz;
import defpackage.ie;
import defpackage.is;
import defpackage.jf;
import defpackage.jj;
import defpackage.jk;
import defpackage.jl;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.MoneyInCbAndPayConfirmData;
import fr.smoney.android.izly.data.model.MoneyInCbAndPayData;
import fr.smoney.android.izly.data.model.P2PPayCommerceInfos;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;
import fr.smoney.android.izly.ui.widget.DetailTwoText;

import java.util.Currency;
import java.util.Locale;

public class MoneyInCbAndPayConfirmActivity extends SmoneyABSActivity implements OnClickListener, SmoneyRequestManager$a {
    private String b;
    private boolean c;
    private P2PPayCommerceInfos d;
    private MoneyInCbAndPayData e;
    @Bind({2131755367})
    ImageView imgMessageMe;
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
    @Bind({2131755365})
    TextView messageDateMe;
    @Bind({2131755366})
    TextView messageMe;
    @Bind({2131755371})
    View messageOther;
    @Bind({2131755363})
    View messageRightMe;
    @Bind({2131755381})
    DetailTwoText moneyInCommision;
    @Bind({2131755386})
    TextView recipientId;
    @Bind({2131755384})
    TextView recipientInfo;
    @Bind({2131755385})
    TextView recipientName;
    @Bind({2131755382})
    ImageView recipientPhoto;

    private void a(MoneyInCbAndPayConfirmData moneyInCbAndPayConfirmData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (moneyInCbAndPayConfirmData == null) {
            a_(14);
        } else {
            i().b.B = moneyInCbAndPayConfirmData.g;
            Intent a = is.a(this, MoneyInCbAndPayResultActivity.class);
            a.putExtra("fr.smoney.android.izly.extras.moneyInCbAndPayConfirmData", moneyInCbAndPayConfirmData);
            a.putExtra("startFromWebPayement", this.c);
            a.putExtra("startFromWebPayementObject", this.d);
            startActivity(a);
        }
    }

    private void k() {
        int keyAt;
        cl i = i();
        SmoneyRequestManager j = j();
        String str = i.b.a;
        String str2 = i.b.c;
        String str3 = this.e.b.c.a;
        double d = this.e.b.e;
        String str4 = this.e.a.d;
        double d2 = this.e.a.j;
        String str5 = this.e.a.m;
        String str6 = this.b;
        int size = j.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            Intent intent = (Intent) j.b.valueAt(i2);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 14 && intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbAndPayConfirmUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbAndPayConfirmSessionId").equals(str2) && intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbAndPayConfirmCardId").equals(str3) && intent.getDoubleExtra("fr.smoney.android.izly.extras.moneyInCbAndPayConfirmCbAmount", -1.0d) == d && intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbAndPayConfirmRecipient").equals(str4) && intent.getDoubleExtra("fr.smoney.android.izly.extras.moneyInCbAndPayConfirmAmount", -1.0d) == d2 && intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbAndPayConfirmMessage").equals(str5) && intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbAndPayConfirmPassword").equals(str6)) {
                keyAt = j.b.keyAt(i2);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 14);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbAndPayConfirmUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbAndPayConfirmSessionId", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbAndPayConfirmCardId", str3);
        intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbAndPayConfirmEngagementId", -1);
        intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbAndPayConfirmCbAmount", d);
        intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbAndPayConfirmRecipient", str4);
        intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbAndPayConfirmAmount", d2);
        intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbAndPayConfirmMessage", str5);
        intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbAndPayConfirmPassword", str6);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.l = null;
        j.f.m = null;
        super.a(keyAt, 14, true);
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (ieVar) {
            case ConnexionErrorType:
                if (h() == 14) {
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
            if (i2 == 14) {
                a((MoneyInCbAndPayConfirmData) bundle.getParcelable("fr.smoney.android.izly.extras.moneyInCbAndPayConfirmData"), serverError);
            }
        }
    }

    public final void b_(int i) {
        cl i2 = i();
        if (i == 14) {
            a(i2.l, i2.m);
        }
    }

    public final void c(ie ieVar) {
        switch (ieVar) {
            case ErrorForNotMustBeRetryRequest:
                if (this.d != null) {
                    jj.a(this, this.d, false);
                    return;
                } else {
                    super.c(ieVar);
                    return;
                }
            default:
                super.c(ieVar);
                return;
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
        setContentView((int) R.layout.money_in_cb_and_pay_confirm);
        ButterKnife.bind(this);
        getSupportActionBar().setHomeButtonEnabled(false);
        this.mButtonConfirm.setOnClickListener(this);
        Intent intent = getIntent();
        if (intent != null) {
            this.e = (MoneyInCbAndPayData) intent.getParcelableExtra("fr.smoney.android.izly.extras.moneyInCbAndPayData");
            this.d = (P2PPayCommerceInfos) intent.getParcelableExtra("startFromWebPayementObject");
            this.c = intent.getBooleanExtra("startFromWebPayement", false);
        }
        if (bundle != null) {
            this.b = bundle.getString("savedStateCurrentPassword");
        }
        cl i = i();
        String symbol = Currency.getInstance(i.b.j).getSymbol();
        String format = String.format("%1$.2f%2$s", new Object[]{Double.valueOf(this.e.b.e), symbol});
        String format2 = String.format("%1$.2f%2$s", new Object[]{Double.valueOf(this.e.a.j), symbol});
        this.messageOther.setVisibility(8);
        this.mAlias.setText(this.e.b.c.b);
        this.mHint.setText(this.e.b.c.d);
        this.moneyInCommision.setRightText(String.format("%1$.2f%2$s", new Object[]{Double.valueOf(this.e.b.f), symbol}));
        this.recipientInfo.setText(getString(R.string.recipient));
        if (this.e.a.f) {
            if (!(i == null || i.b == null)) {
                this.a.a.displayImage(jl.a(this.e.a.d), this.recipientPhoto);
            }
            this.recipientName.setText(jf.a(this.e.a.g, this.e.a.h, this.e.a.d));
            if (jf.a(this.e.a.g, this.e.a.h)) {
                this.recipientId.setText(jf.a(this.e.a.d));
            } else {
                this.recipientId.setVisibility(8);
            }
        } else {
            this.recipientName.setText(jf.a(this.e.a.d));
            this.recipientId.setVisibility(8);
        }
        this.mTextViewAmount.setRightText(getString(R.string.reload_and_pay, new Object[]{format2, format}));
        this.mTextViewCommission.setRightText(String.format("%1$.2f%2$s", new Object[]{Double.valueOf(this.e.a.k), symbol}));
        this.mDate.setRightText(jk.b(this, this.e.a.b));
        if (this.e.a.m == null || TextUtils.isEmpty(this.e.a.m)) {
            this.messageRightMe.setVisibility(8);
        } else {
            this.messageRightMe.setVisibility(0);
            this.a.a.displayImage(jl.a(i.b.a), this.imgMessageMe);
            this.messageDateMe.setText(jk.b(this, this.e.a.b));
            this.messageMe.setText(this.e.a.m);
        }
        if (this.e.a.k > 0.0d) {
            this.mTextViewCommission.setVisibility(0);
            this.mTextViewCommission.setRightText(String.format(Locale.getDefault(), "%1$.2f%2$s", new Object[]{Double.valueOf(this.e.a.k), symbol}));
        }
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString("savedStateCurrentPassword", this.b);
    }
}
