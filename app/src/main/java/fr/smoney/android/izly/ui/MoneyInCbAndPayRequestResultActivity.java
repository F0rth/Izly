package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import defpackage.jf;
import defpackage.jk;
import defpackage.jl;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.MoneyInCbAndPayRequestConfirmData;
import fr.smoney.android.izly.ui.widget.DetailTwoText;

import java.util.Currency;
import java.util.Locale;

public class MoneyInCbAndPayRequestResultActivity extends SmoneyABSActivity implements OnClickListener {
    private MoneyInCbAndPayRequestConfirmData b;
    @Bind({2131755171})
    View balance;
    @Bind({2131755657})
    TextView header;
    @Bind({2131755367})
    ImageView imgMessageMe;
    @Bind({2131755372})
    ImageView imgOther;
    @Bind({2131755379})
    TextView mAlias;
    @Bind({2131755304})
    Button mButtonClose;
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

    private static String a(double d, String str) {
        return String.format(Locale.getDefault(), "%1$.2f%2$s", new Object[]{Double.valueOf(d), str});
    }

    public void onClick(View view) {
        if (view == this.mButtonClose) {
            a();
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.money_in_cb_and_pay_confirm);
        ButterKnife.bind(this);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        this.balance.setVisibility(8);
        this.mButtonClose.setOnClickListener(this);
        this.mButtonClose.setText(R.string.confirm_result_b_close);
        Intent intent = getIntent();
        if (intent != null) {
            this.b = (MoneyInCbAndPayRequestConfirmData) intent.getParcelableExtra("fr.smoney.android.izly.extras.moneyInCbAndPayRequestConfirmData");
        }
        cl i = i();
        String symbol = Currency.getInstance(i.b.j).getSymbol();
        String a = a(this.b.b.e, symbol);
        String a2 = a(this.b.a.h, symbol);
        this.header.setText(getString(R.string.money_in_cb_and_x_result_tv_payment_sent, new Object[]{a, a2}));
        this.mAlias.setText(this.b.b.c.b);
        this.mHint.setText(this.b.b.c.d);
        if (!(i == null || i.b == null)) {
            this.a.a.displayImage(jl.a(this.b.a.d), this.recipientPhoto);
        }
        CharSequence a3 = jf.a(this.b.a.e, this.b.a.f, this.b.a.d);
        this.recipientName.setText(a3);
        this.recipientInfo.setText(getString(R.string.recipient));
        if (jf.a(this.b.a.e, this.b.a.f)) {
            this.recipientId.setText(jf.a(this.b.a.d));
        } else {
            this.recipientId.setVisibility(8);
        }
        this.mTextViewAmount.setVisibility(0);
        this.mTextViewAmount.setRightText(getString(R.string.reload_and_pay, new Object[]{a2, a}));
        this.mTextViewCommission.setVisibility(8);
        this.mTextViewCommission.setRightText(String.format("%1$.2f%2$s", new Object[]{Double.valueOf(this.b.a.i), symbol}));
        this.mDate.setRightText(jk.b(this, this.b.a.c));
        if (this.b.a.k == null || TextUtils.isEmpty(this.b.a.k)) {
            this.messageOther.setVisibility(8);
        } else {
            this.messageOther.setVisibility(0);
            this.a.a.displayImage(jl.a(this.b.a.d), this.imgOther);
            ((TextView) findViewById(R.id.tv_message_name_other)).setText(a3);
            ((TextView) findViewById(R.id.tv_message_date_other)).setText(jk.a(this, this.b.a.c, true));
            ((TextView) findViewById(R.id.tv_message_other)).setText(this.b.a.k);
        }
        if (this.b.a.l == null || TextUtils.isEmpty(this.b.a.l)) {
            this.messageRightMe.setVisibility(8);
        } else {
            this.messageRightMe.setVisibility(0);
            this.a.a.displayImage(jl.a(i.b.a), this.imgMessageMe);
            this.messageDateMe.setText(jk.a(this, this.b.a.m, true));
            this.messageMe.setText(this.b.a.l);
        }
        if (this.b.a.i > 0.0d) {
            this.mTextViewCommission.setVisibility(0);
            this.mTextViewCommission.setRightText(String.format(Locale.getDefault(), "%1$.2f%2$s", new Object[]{Double.valueOf(this.b.a.i), symbol}));
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
