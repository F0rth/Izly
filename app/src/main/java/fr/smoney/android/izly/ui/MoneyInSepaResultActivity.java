package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import defpackage.iu;
import defpackage.jb;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.ui.widget.DetailTwoText;

import java.math.BigDecimal;
import java.util.Currency;

public class MoneyInSepaResultActivity extends SmoneyABSActivity {
    private double b;
    private String c;
    private String d;
    private cl e;
    @Bind({2131755668})
    DetailTwoText mAmount;
    @Bind({2131755475})
    DetailTwoText mDate;
    @Bind({2131755672})
    DetailTwoText mNewBalance;
    @Bind({2131755671})
    DetailTwoText mOldBalance;
    @Bind({2131755382})
    ImageView mPhoto;
    @Bind({2131755385})
    TextView mRecipientName;
    @Bind({2131755870})
    TextView mRecipientType;
    @Bind({2131755662})
    TextView mStatus;
    @Bind({2131755869})
    TextView mTransactionType;

    @OnClick({2131755308})
    public void close() {
        i().cs = true;
        a();
    }

    public void onBackPressed() {
        i().cs = true;
        a();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.result_money_in_activity);
        ButterKnife.bind(this);
        overridePendingTransition(R.anim.activity_start_enter, R.anim.activity_start_exit);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        this.e = i();
        Intent intent = getIntent();
        this.b = intent.getDoubleExtra("fr.smoney.android.izly.extras.sepa_amount", -1.0d);
        this.c = intent.getStringExtra("fr.smoney.android.izly.extras.sepa_date");
        this.d = intent.getStringExtra("fr.smoney.android.izly.extras.sepa_iban");
        String symbol = Currency.getInstance(this.e.b.j).getSymbol();
        this.mPhoto.setImageDrawable(getResources().getDrawable(R.drawable.icon_home_moneyin));
        this.mTransactionType.setText(getResources().getString(R.string.reloading));
        this.mOldBalance.setVisibility(8);
        this.mNewBalance.setVisibility(8);
        BigDecimal bigDecimal = new BigDecimal(this.b);
        BigDecimal bigDecimal2 = new BigDecimal(this.e.b.B.a);
        String a = iu.a(bigDecimal.doubleValue(), symbol);
        if (this.b != -1.0d) {
            this.mAmount.setRightText(a);
        }
        this.mDate.setRightText(this.c);
        this.mStatus.setText(getString(R.string.reload_v_me_status_result, new Object[]{a}));
        this.mRecipientName.setText(this.d);
        this.mRecipientType.setText(getString(R.string.sepa_bank_account));
        this.mOldBalance.setRightText(iu.a(bigDecimal2.doubleValue(), symbol));
        this.mNewBalance.setRightText(iu.a(bigDecimal2.doubleValue() + bigDecimal.doubleValue(), symbol));
        jb.a(getApplicationContext(), R.string.screen_name_money_in_sepa_result_activity);
    }
}
