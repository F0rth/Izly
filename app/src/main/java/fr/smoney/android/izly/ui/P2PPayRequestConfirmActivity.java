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
import defpackage.jh;
import defpackage.jk;
import defpackage.jl;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.P2PPayRequest;
import fr.smoney.android.izly.data.model.P2PPayRequestConfirmData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;
import fr.smoney.android.izly.ui.widget.DetailTwoText;

import java.util.Currency;
import java.util.Locale;

public class P2PPayRequestConfirmActivity extends SmoneyABSActivity implements OnClickListener, SmoneyRequestManager$a {
    private String b;
    private P2PPayRequest c;
    private int d;
    @Bind({2131755367})
    ImageView imgMessageMe;
    @Bind({2131755372})
    ImageView imgOther;
    @Bind({2131755304})
    Button mButtonConfirm;
    @Bind({2131755390})
    DetailTwoText mDate;
    @Bind({2131755391})
    DetailTwoText mTextViewAmount;
    @Bind({2131755495})
    DetailTwoText mTextViewCommission;
    @Bind({2131755365})
    TextView messageDateMe;
    @Bind({2131755366})
    TextView messageMe;
    @Bind({2131755363})
    View messageMeView;
    @Bind({2131755371})
    View messageOtherView;
    @Bind({2131755376})
    TextView otherDate;
    @Bind({2131755374})
    TextView otherMessage;
    @Bind({2131755373})
    TextView otherName;
    @Bind({2131755386})
    TextView recipientId;
    @Bind({2131755385})
    TextView recipientName;
    @Bind({2131755382})
    ImageView recipientPhoto;

    private void a(P2PPayRequestConfirmData p2PPayRequestConfirmData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (p2PPayRequestConfirmData == null) {
            a_(74);
        } else {
            i().b.B = p2PPayRequestConfirmData.b;
            i().b.D = this.c.v;
            p2PPayRequestConfirmData.d = jf.a(this.c.e, this.c.f, this.c.d);
            Intent a = is.a(this, P2PPayRequestResultActivity.class);
            a.putExtra("fr.smoney.android.izly.extras.p2pPayRequestConfirmData", p2PPayRequestConfirmData);
            startActivity(a);
        }
    }

    private void k() {
        int keyAt;
        cl i = i();
        SmoneyRequestManager j = j();
        String str = i.b.a;
        String str2 = i.b.c;
        long j2 = this.c.a;
        int i2 = this.d;
        String str3 = this.c.l;
        double d = this.c.h;
        String str4 = this.b;
        int size = j.b.size();
        for (int i3 = 0; i3 < size; i3++) {
            Intent intent = (Intent) j.b.valueAt(i3);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 74 && intent.getStringExtra("fr.smoney.android.izly.extras.p2pPayRequestConfirmUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.p2pPayRequestConfirmSessionId").equals(str2) && intent.getLongExtra("fr.smoney.android.izly.extras.p2pPayRequestConfirmPayRequestId", -1) == j2 && intent.getIntExtra("fr.smoney.android.izly.extras.p2pPayRequestConfirmResponseStatus", -1) == i2 && intent.getStringExtra("fr.smoney.android.izly.extras.p2pPayRequestConfirmResponseMessage").equals(str3) && intent.getDoubleExtra("fr.smoney.android.izly.extras.p2pPayRequestConfirmAmount", -1.0d) == d && intent.getStringExtra("fr.smoney.android.izly.extras.p2pPayRequestConfirmPassword").equals(str4)) {
                keyAt = j.b.keyAt(i3);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 74);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pPayRequestConfirmUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pPayRequestConfirmSessionId", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pPayRequestConfirmPayRequestId", j2);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pPayRequestConfirmResponseStatus", i2);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pPayRequestConfirmResponseMessage", str3);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pPayRequestConfirmAmount", d);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pPayRequestConfirmPassword", str4);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.W = null;
        j.f.X = null;
        super.a(keyAt, 74, true);
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (ieVar) {
            case ConnexionErrorType:
                if (h() == 74) {
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
            if (i2 == 74) {
                a((P2PPayRequestConfirmData) bundle.getParcelable("fr.smoney.android.izly.extras.p2pPayRequestConfirmData"), serverError);
            }
        }
    }

    public final void b_(int i) {
        cl i2 = i();
        if (i == 74) {
            a(i2.W, i2.X);
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
        setContentView((int) R.layout.p2p_pay_request_confirm);
        ButterKnife.bind(this);
        getSupportActionBar().setHomeButtonEnabled(false);
        this.mButtonConfirm.setOnClickListener(this);
        Intent intent = getIntent();
        if (intent != null) {
            this.c = (P2PPayRequest) intent.getParcelableExtra("fr.smoney.android.izly.extras.p2pPayRequest");
            this.d = intent.getIntExtra("fr.smoney.android.izly.extras.responseStatus", 0);
        }
        cl i = i();
        String symbol = Currency.getInstance(i.b.j).getSymbol();
        CharSequence a = jf.a(this.c.e, this.c.f, this.c.d);
        if (!(i == null || i.b == null)) {
            this.a.a.displayImage(jl.a(this.c.d), this.recipientPhoto);
        }
        this.recipientName.setText(a);
        if (jf.a(this.c.e, this.c.f)) {
            this.recipientId.setText(jf.a(this.c.d));
        } else {
            this.recipientId.setVisibility(8);
        }
        if (this.c.g) {
            this.mTextViewAmount.setRightText((int) R.string.amount_other);
        } else {
            this.mTextViewAmount.setRightText(String.format("%1$.2f%2$s", new Object[]{Double.valueOf(this.c.h), symbol}));
        }
        this.mDate.setRightText(jk.b(this, this.c.c));
        if (this.c.k == null || TextUtils.isEmpty(this.c.k)) {
            this.messageOtherView.setVisibility(8);
        } else {
            this.a.a.displayImage(jl.a(this.c.d), this.imgOther);
            this.otherName.setText(a);
            this.otherDate.setText(jk.a(this, this.c.c, true));
            this.otherMessage.setText(this.c.k);
        }
        if (this.c.l == null || TextUtils.isEmpty(this.c.l)) {
            this.messageMeView.setVisibility(8);
        } else {
            this.messageMeView.setVisibility(0);
            this.a.a.displayImage(jl.a(i.b.a), this.imgMessageMe);
            this.messageDateMe.setText(jk.a(this, this.c.m, true));
            this.messageMe.setText(this.c.l);
        }
        if (this.c.i > 0.0d) {
            this.mTextViewCommission.setVisibility(0);
            this.mTextViewCommission.setLeftText(jh.a(this, this.c.i, this.c.j, false));
            this.mTextViewCommission.setRightText(String.format(Locale.getDefault(), "%1$.2f%2$s", new Object[]{Double.valueOf(this.c.i), symbol}));
        }
        this.mButtonConfirm.setText(this.d == 0 ? R.string.p2p_pay_request_confirm_b_confirm_pay : R.string.p2p_pay_request_confirm_b_confirm_refuse);
        if (bundle != null) {
            this.b = bundle.getString("savedStateCurrentPassword");
        }
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString("savedStateCurrentPassword", this.b);
    }
}
