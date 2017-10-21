package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import defpackage.is;
import defpackage.jb;
import defpackage.jf;
import defpackage.jh;
import defpackage.jk;
import defpackage.jl;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.P2PPayCommerceInfos;
import fr.smoney.android.izly.data.model.P2PPayConfirmData;
import fr.smoney.android.izly.ui.widget.DetailTwoText;

import java.util.Currency;
import java.util.Locale;

public class P2PPayResultActivity extends SmoneyABSActivity implements OnClickListener {
    private P2PPayConfirmData b;
    private boolean c;
    private P2PPayCommerceInfos d;
    private ge e;
    @Nullable
    @Bind({2131755367})
    ImageView mAvatarImgMe;
    @Bind({2131755192})
    Button mButtonNewPay;
    @Nullable
    @Bind({2131755365})
    TextView mDateMessage;
    @Nullable
    @Bind({2131755659})
    View mLayoutCommission;
    @Nullable
    @Bind({2131755382})
    ImageView mMaskedAsyncImageViewRecipientPhoto;
    @Nullable
    @Bind({2131755366})
    TextView mMessage;
    @Nullable
    @Bind({2131755391})
    DetailTwoText mTextViewAmount;
    @Nullable
    @Bind({2131755495})
    DetailTwoText mTextViewCommission;
    @Nullable
    @Bind({2131755579})
    TextView mTextViewCommissionInfo;
    @Nullable
    @Bind({2131755390})
    DetailTwoText mTextViewDate;
    @Nullable
    @Bind({2131755386})
    TextView mTextViewRecipientId;
    @Nullable
    @Bind({2131755385})
    TextView mTextViewRecipientName;
    @Nullable
    @Bind({2131755578})
    View mViewCommissionInfo;
    @Nullable
    @Bind({2131755363})
    View messageViewRightMe;
    @Bind({2131755726})
    TextView titleInfo;
    @Bind({2131755727})
    TextView titleInfoResult;

    public void onClick(View view) {
        if (view == this.mButtonNewPay) {
            Intent a = is.a(this, P2PPayActivity.class);
            a.setFlags(67108864);
            startActivity(a);
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.p2p_confirm_result);
        ButterKnife.bind(this);
        this.e = new ge(this);
        this.mButtonNewPay.setOnClickListener(this);
        this.mButtonNewPay.setText(R.string.confirm_result_b_new_pay);
        Intent intent = getIntent();
        if (intent != null) {
            this.b = (P2PPayConfirmData) intent.getParcelableExtra("fr.smoney.android.izly.extras.p2pPayConfirmData");
            this.c = intent.getBooleanExtra("startFromWebPayement", false);
            this.d = (P2PPayCommerceInfos) intent.getParcelableExtra("startFromWebPayementObject");
        }
        ActionBar supportActionBar = getSupportActionBar();
        if (this.c) {
            supportActionBar.setHomeButtonEnabled(false);
            supportActionBar.setDisplayHomeAsUpEnabled(false);
        } else {
            supportActionBar.setHomeButtonEnabled(true);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        cl i = i();
        String symbol = Currency.getInstance(i.b.j).getSymbol();
        this.titleInfo.setVisibility(8);
        this.titleInfoResult.setVisibility(0);
        if ((this.b.a.g == null || this.b.a.g.length() <= 0) && (this.b.a.h == null || this.b.a.h.length() <= 0)) {
            this.titleInfoResult.setText(getString(R.string.p2p_pay_result_tv_payment_sent_format_with_id, new Object[]{this.b.a.d, Double.valueOf(this.b.a.j), symbol}));
        } else {
            this.titleInfoResult.setText(getString(R.string.p2p_pay_result_tv_payment_sent_format_with_names, new Object[]{this.b.a.g, this.b.a.h, Double.valueOf(this.b.a.j), symbol}));
            this.mTextViewRecipientName.setText(this.b.a.g + " " + this.b.a.h);
        }
        if (this.b.a.f) {
            if (!(i == null || i.b == null)) {
                this.e.a.displayImage(jl.a(this.b.a.d), this.mMaskedAsyncImageViewRecipientPhoto);
            }
            this.mTextViewRecipientName.setText(jf.a(this.b.a.g, this.b.a.h, this.b.a.d));
            if (jf.a(this.b.a.g, this.b.a.h)) {
                this.mTextViewRecipientId.setText(jf.a(this.b.a.d));
            } else {
                this.mTextViewRecipientId.setVisibility(8);
            }
        } else {
            this.mTextViewRecipientName.setText((this.b.a.g != null ? this.b.a.g + " " : "") + (this.b.a.h != null ? this.b.a.h : ""));
            this.mTextViewRecipientId.setText(jf.a(this.b.a.d));
        }
        this.mTextViewAmount.setRightText(String.format("%1$.2f%2$s", new Object[]{Double.valueOf(this.b.a.j), symbol}));
        this.mTextViewDate.setRightText(jk.b(this, this.b.a.b));
        if (this.b.a.m == null || TextUtils.isEmpty(this.b.a.m)) {
            this.messageViewRightMe.setVisibility(8);
        } else {
            this.messageViewRightMe.setVisibility(0);
            this.e.a.displayImage(jl.a(i.b.a), this.mAvatarImgMe);
            this.mDateMessage.setText(jk.a(this, this.b.a.b, true));
            this.mMessage.setText(this.b.a.m);
        }
        if (this.c) {
            this.mButtonNewPay.setVisibility(8);
            this.d.f = this.b.a.a;
        }
        if (this.b.a.k > 0.0d) {
            this.mLayoutCommission.setVisibility(0);
            this.mViewCommissionInfo.setVisibility(0);
            this.mTextViewCommissionInfo.setText(jh.a(this, this.b.a.k, this.b.a.l, true));
            this.mTextViewCommission.setRightText(String.format(Locale.getDefault(), "%1$.2f%2$s", new Object[]{Double.valueOf(this.b.a.k), symbol}));
        }
        jb.a(getApplicationContext(), R.string.screen_name_send_money_result_activity);
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
