package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import defpackage.jf;
import defpackage.jh;
import defpackage.jk;
import defpackage.jl;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.P2PPayRequestConfirmData;
import fr.smoney.android.izly.ui.widget.DetailTwoText;

import java.util.Currency;
import java.util.Locale;

public class P2PPayRequestResultActivity extends SmoneyABSActivity {
    private View b;
    private LinearLayout c;
    private TextView d;
    private P2PPayRequestConfirmData e;
    private TextView f;
    private View g;
    private ge h;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.p2p_pay_request_result);
        this.h = new ge(this);
        this.d = (TextView) findViewById(R.id.tv_commission);
        this.f = (TextView) findViewById(R.id.tv_commission_info);
        this.g = findViewById(R.id.ll_commission_info);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        if (intent != null) {
            this.e = (P2PPayRequestConfirmData) intent.getParcelableExtra("fr.smoney.android.izly.extras.p2pPayRequestConfirmData");
        }
        cl i = i();
        String symbol = Currency.getInstance(i.b.j).getSymbol();
        TextView textView = (TextView) findViewById(R.id.tv_request_status);
        int i2 = this.e.a.n;
        if (i2 == 0) {
            i2 = 3;
        }
        CharSequence a = jf.a(this.e.a.e, this.e.a.f, this.e.a.d);
        if (i2 == 3 || i2 == 0) {
            textView.setText(getString(R.string.p2p_pay_result_tv_payment_sent_format_with_names_simple, new Object[]{this.e.d, Double.valueOf(this.e.a.h), symbol}));
            textView.setBackgroundResource(R.drawable.bg_header_status_paid);
            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.header_status_valide, 0, 0, 0);
        } else if (i2 == 2 || i2 == 1) {
            textView.setText(R.string.p2p_pay_request_result_tv_payment_refused);
            textView.setBackgroundResource(R.drawable.bg_header_status_refused);
            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.header_status_refuse, 0, 0, 0);
        }
        if (!(i == null || i.b == null)) {
            this.h.a.displayImage(jl.a(this.e.a.d), (ImageView) findViewById(R.id.aiv_recipient_photo));
        }
        ((TextView) findViewById(R.id.tv_recipient_name)).setText(a);
        textView = (TextView) findViewById(R.id.tv_recipient_id);
        if (jf.a(this.e.a.e, this.e.a.f)) {
            textView.setText(jf.a(this.e.a.d));
        } else {
            textView.setVisibility(8);
        }
        if (i2 == 3) {
            findViewById(R.id.tv_amount).setVisibility(0);
            ((DetailTwoText) findViewById(R.id.tv_amount)).setRightText(String.format("%1$.2f%2$s", new Object[]{Double.valueOf(this.e.a.h), symbol}));
        } else if (i2 == 2 || i2 == 1) {
            findViewById(R.id.tv_amount).setVisibility(8);
        }
        ((DetailTwoText) findViewById(R.id.tv_date)).setRightText(jk.b(this, this.e.a.c));
        if (this.e.a.k == null || TextUtils.isEmpty(this.e.a.k)) {
            findViewById(R.id.ll_message_other).setVisibility(8);
        } else {
            findViewById(R.id.ll_message_other).setVisibility(0);
            this.h.a.displayImage(jl.a(this.e.a.d), (ImageView) findViewById(R.id.aiv_message_avatar_other));
            ((TextView) findViewById(R.id.tv_message_name_other)).setText(a);
            ((TextView) findViewById(R.id.tv_message_date_other)).setText(jk.a(this, this.e.a.c, true));
            ((TextView) findViewById(R.id.tv_message_other)).setText(this.e.a.k);
        }
        if (this.e.a.l == null || TextUtils.isEmpty(this.e.a.l)) {
            findViewById(R.id.ll_message_me).setVisibility(8);
        } else {
            findViewById(R.id.ll_message_me).setVisibility(0);
            this.h.a.displayImage(jl.a(i.b.a), (ImageView) findViewById(R.id.img_message_me));
            ((TextView) findViewById(R.id.tv_message_date_me)).setText(jk.a(this, this.e.a.m, true));
            ((TextView) findViewById(R.id.tv_message_me)).setText(this.e.a.l);
        }
        if (this.e.a.i > 0.0d) {
            this.c.setVisibility(0);
            this.b.setVisibility(0);
            this.g.setVisibility(0);
            this.f.setText(jh.a(this, this.e.a.i, this.e.a.j, true));
            this.d.setText(String.format(Locale.getDefault(), "%1$.2f%2$s", new Object[]{Double.valueOf(this.e.a.i), symbol}));
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
