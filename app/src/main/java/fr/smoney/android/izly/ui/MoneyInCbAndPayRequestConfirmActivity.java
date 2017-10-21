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
import defpackage.jk;
import defpackage.jl;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.MoneyInCbAndPayRequestConfirmData;
import fr.smoney.android.izly.data.model.MoneyInCbAndPayRequestData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;
import fr.smoney.android.izly.ui.widget.DetailTwoText;

import java.util.Currency;
import java.util.Locale;

public class MoneyInCbAndPayRequestConfirmActivity extends SmoneyABSActivity implements OnClickListener, SmoneyRequestManager$a {
    private String b;
    private MoneyInCbAndPayRequestData c;
    @Bind({2131755367})
    ImageView imgMessageMe;
    @Bind({2131755372})
    ImageView imgOther;
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

    private void a(MoneyInCbAndPayRequestConfirmData moneyInCbAndPayRequestConfirmData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (moneyInCbAndPayRequestConfirmData == null) {
            a_(76);
        } else {
            i().b.B = moneyInCbAndPayRequestConfirmData.f;
            i().b.D = this.c.a.v;
            Intent a = is.a(this, MoneyInCbAndPayRequestResultActivity.class);
            a.putExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestConfirmData", moneyInCbAndPayRequestConfirmData);
            startActivity(a);
        }
    }

    private void k() {
        int keyAt;
        cl i = i();
        SmoneyRequestManager j = j();
        String str = i.b.a;
        String str2 = i.b.c;
        String str3 = this.c.b.c.a;
        double d = this.c.b.e;
        double d2 = this.c.a.h;
        long j2 = this.c.a.a;
        String str4 = this.c.a.l;
        String str5 = this.b;
        int size = j.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            Intent intent = (Intent) j.b.valueAt(i2);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 76 && intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestConfirmUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestConfirmSessionId").equals(str2) && intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestConfirmCardId").equals(str3) && intent.getDoubleExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestConfirmCbAmount", -1.0d) == d && intent.getDoubleExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestConfirmAmount", -1.0d) == d2 && intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestConfirmPayRequestId").equals(Long.valueOf(j2)) && intent.getDoubleExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestConfirmResponseStatus", -1.0d) == 0.0d && intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestConfirmResponseMessage").equals(str4) && intent.getStringExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestConfirmPassword").equals(str5)) {
                keyAt = j.b.keyAt(i2);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 76);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestConfirmUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestConfirmSessionId", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestConfirmCardId", str3);
        intent2.putExtra("fr.smoney.android.smoney.extras.moneyInCbAndPayRequestConfirmEngagementId", -1);
        intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestConfirmCbAmount", d);
        intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestConfirmAmount", d2);
        intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestConfirmPayRequestId", j2);
        intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestConfirmResponseStatus", 0);
        intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestConfirmResponseMessage", str4);
        intent2.putExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestConfirmPassword", str5);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.aa = null;
        j.f.ab = null;
        super.a(keyAt, 76, true);
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (ieVar) {
            case ConnexionErrorType:
                if (h() == 76) {
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
            if (i2 == 76) {
                a((MoneyInCbAndPayRequestConfirmData) bundle.getParcelable("fr.smoney.android.izly.extras.moneyInCbAndPayRequestConfirmData"), serverError);
            }
        }
    }

    public final void b_(int i) {
        cl i2 = i();
        if (i == 76) {
            a(i2.aa, i2.ab);
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
        getSupportActionBar().setHomeButtonEnabled(false);
        setContentView((int) R.layout.money_in_cb_and_pay_confirm);
        ButterKnife.bind(this);
        this.mButtonConfirm.setOnClickListener(this);
        Intent intent = getIntent();
        if (intent != null) {
            this.c = (MoneyInCbAndPayRequestData) intent.getParcelableExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestData");
        }
        if (bundle != null) {
            this.b = bundle.getString("savedStateCurrentPassword");
        }
        cl i = i();
        String symbol = Currency.getInstance(i.b.j).getSymbol();
        String format = String.format("%1$.2f%2$s", new Object[]{Double.valueOf(this.c.b.e), symbol});
        String format2 = String.format("%1$.2f%2$s", new Object[]{Double.valueOf(this.c.a.h), symbol});
        this.mAlias.setText(this.c.b.c.b);
        this.mHint.setText(this.c.b.c.d);
        this.moneyInCommision.setRightText(String.format("%1$.2f%2$s", new Object[]{Double.valueOf(this.c.b.f), symbol}));
        if (!(i == null || i.b == null)) {
            this.a.a.displayImage(jl.a(this.c.a.d), this.recipientPhoto);
        }
        CharSequence a = jf.a(this.c.a.e, this.c.a.f, this.c.a.d);
        this.recipientName.setText(a);
        this.recipientInfo.setText(getString(R.string.recipient));
        if (jf.a(this.c.a.e, this.c.a.f)) {
            this.recipientId.setText(jf.a(this.c.a.d));
        } else {
            this.recipientId.setVisibility(8);
        }
        this.mTextViewAmount.setRightText(getString(R.string.reload_and_pay, new Object[]{format2, format}));
        this.mTextViewCommission.setRightText(String.format("%1$.2f%2$s", new Object[]{Double.valueOf(this.c.a.i), symbol}));
        this.mDate.setRightText(jk.b(this, this.c.a.c));
        if (this.c.a.k == null || TextUtils.isEmpty(this.c.a.k)) {
            this.messageOther.setVisibility(8);
        } else {
            this.messageOther.setVisibility(0);
            this.a.a.displayImage(jl.a(this.c.a.d), this.imgOther);
            ((TextView) findViewById(R.id.tv_message_name_other)).setText(a);
            ((TextView) findViewById(R.id.tv_message_date_other)).setText(jk.a(this, this.c.a.c, true));
            ((TextView) findViewById(R.id.tv_message_other)).setText(this.c.a.k);
        }
        if (this.c.a.l == null || TextUtils.isEmpty(this.c.a.l)) {
            this.messageRightMe.setVisibility(8);
        } else {
            this.messageRightMe.setVisibility(0);
            this.a.a.displayImage(jl.a(i.b.a), (ImageView) findViewById(R.id.img_message_me));
            this.messageDateMe.setText(jk.a(this, this.c.a.m, true));
            this.messageMe.setText(this.c.a.l);
        }
        if (this.c.a.i > 0.0d) {
            this.mTextViewCommission.setRightText(String.format(Locale.getDefault(), "%1$.2f%2$s", new Object[]{Double.valueOf(this.c.a.i), symbol}));
        }
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString("savedStateCurrentPassword", this.b);
    }
}
