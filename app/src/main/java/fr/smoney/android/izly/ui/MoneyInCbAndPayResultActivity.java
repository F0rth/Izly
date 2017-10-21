package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import defpackage.il;
import defpackage.in;
import defpackage.is;
import defpackage.jf;
import defpackage.jk;
import defpackage.jl;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.MoneyInCbAndPayConfirmData;
import fr.smoney.android.izly.data.model.P2PPayCommerceInfos;
import fr.smoney.android.izly.ui.widget.DetailTwoText;

import java.util.Currency;
import java.util.Locale;

public class MoneyInCbAndPayResultActivity extends SmoneyABSActivity implements OnClickListener {
    private MoneyInCbAndPayConfirmData b;
    @Bind({2131755171})
    View balance;
    private boolean c;
    private P2PPayCommerceInfos d;
    @Bind({2131755367})
    ImageView imgMessageMe;
    @Bind({2131755379})
    TextView mAlias;
    @Bind({2131755304})
    Button mButtonNewPay;
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
    @Bind({2131755385})
    TextView recipientName;
    @Bind({2131755382})
    ImageView recipientPhoto;
    @Bind({2131755657})
    TextView statusHeader;

    private static String a(double d, String str) {
        return String.format(Locale.getDefault(), "%1$.2f%2$s", new Object[]{Double.valueOf(d), str});
    }

    public void onClick(View view) {
        if (view == this.mButtonNewPay) {
            Intent a = is.a(this, P2PPayActivity.class);
            a.setFlags(67108864);
            startActivity(a);
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActionBar supportActionBar = getSupportActionBar();
        setContentView((int) R.layout.money_in_cb_and_pay_confirm);
        ButterKnife.bind(this);
        this.balance.setVisibility(8);
        this.mButtonNewPay.setOnClickListener(this);
        this.mButtonNewPay.setText(R.string.confirm_result_b_new_pay);
        Intent intent = getIntent();
        if (intent != null) {
            this.d = (P2PPayCommerceInfos) intent.getParcelableExtra("startFromWebPayementObject");
            this.c = intent.getBooleanExtra("startFromWebPayement", false);
            this.b = (MoneyInCbAndPayConfirmData) intent.getParcelableExtra("fr.smoney.android.izly.extras.moneyInCbAndPayConfirmData");
        }
        if (this.c) {
            supportActionBar.setHomeButtonEnabled(false);
            supportActionBar.setDisplayHomeAsUpEnabled(false);
        } else {
            supportActionBar.setHomeButtonEnabled(true);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        this.messageOther.setVisibility(8);
        cl i = i();
        String symbol = Currency.getInstance(i.b.j).getSymbol();
        String a = a(this.b.b.e, symbol);
        String a2 = a(this.b.a.j, symbol);
        CharSequence charSequence = this.b.b.c.b;
        CharSequence charSequence2 = this.b.b.c.d;
        this.mAlias.setText(charSequence);
        this.mHint.setText(charSequence2);
        ViewGroup viewGroup = (ViewGroup) this.statusHeader.getParent();
        int indexOfChild = viewGroup.indexOfChild(this.statusHeader);
        viewGroup.removeView(this.statusHeader);
        this.statusHeader = in.a(this, il.a);
        this.statusHeader.setText(getString(R.string.money_in_cb_and_x_result_tv_payment_sent, new Object[]{a, a2}));
        viewGroup.addView(this.statusHeader, indexOfChild);
        if (this.b.a.f) {
            if (!(i == null || i.b == null)) {
                this.a.a.displayImage(jl.a(this.b.a.d), this.recipientPhoto);
            }
            this.recipientName.setText(jf.a(this.b.a.g, this.b.a.h, this.b.a.d));
            if (jf.a(this.b.a.g, this.b.a.h)) {
                this.recipientId.setText(jf.a(this.b.a.d));
            } else {
                this.recipientId.setVisibility(8);
            }
        } else {
            this.recipientName.setText(jf.a(this.b.a.d));
            this.recipientId.setVisibility(8);
        }
        this.mTextViewAmount.setRightText(getString(R.string.reload_and_pay, new Object[]{a2, a}));
        this.mTextViewCommission.setRightText(String.format("%1$.2f%2$s", new Object[]{Double.valueOf(this.b.a.k), symbol}));
        this.mDate.setRightText(jk.b(this, this.b.a.b));
        if (this.b.a.m == null || TextUtils.isEmpty(this.b.a.m)) {
            this.messageRightMe.setVisibility(8);
        } else {
            this.messageDateMe.setText(jk.a(this, this.b.a.b, true));
            this.messageMe.setText(this.b.a.m);
            this.a.a.displayImage(jl.a(i().b.a), this.imgMessageMe);
        }
        if (this.c) {
            this.d.f = this.b.a.a;
            this.mButtonNewPay.setVisibility(8);
        }
        if (this.b.a.k > 0.0d) {
            this.mTextViewCommission.setVisibility(0);
            this.mTextViewCommission.setRightText(String.format(Locale.getDefault(), "%1$.2f%2$s", new Object[]{Double.valueOf(this.b.a.k), symbol}));
        }
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        switch (i) {
            case 4:
                a();
                return true;
            default:
                return super.onKeyUp(i, keyEvent);
        }
    }
}
