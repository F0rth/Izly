package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import defpackage.jb;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.ui.widget.DetailTwoText;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

public class MoneyInTiersResultActivity extends SmoneyABSActivity {
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    @Bind({2131755668})
    DetailTwoText mAmount;
    @Bind({2131755475})
    DetailTwoText mDate;
    @Bind({2131755670})
    DetailTwoText mMessage;
    @Bind({2131755382})
    ImageView mPhoto;
    @Bind({2131755669})
    DetailTwoText mRecipient;
    @Bind({2131755385})
    TextView mRecipientName;
    @Bind({2131755870})
    TextView mRecipientType;
    @Bind({2131755869})
    TextView mTransactionType;

    @OnClick({2131755308})
    public void close() {
        a();
    }

    public void onBackPressed() {
        a();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.result_tiers_activity);
        ButterKnife.bind(this);
        overridePendingTransition(R.anim.activity_start_enter, R.anim.activity_start_exit);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        this.b = intent.getStringExtra("fr.smoney.android.izly.extras.tiers_amount");
        this.c = intent.getStringExtra("fr.smoney.android.izly.extras.tiers_message");
        this.d = intent.getStringExtra("fr.smoney.android.izly.extras.tiers_recipient");
        this.e = intent.getStringExtra("fr.smoney.android.izly.extras.tiers_date");
        this.f = Currency.getInstance(i().b.j).getSymbol();
        this.mPhoto.setImageDrawable(getResources().getDrawable(R.drawable.icon_home_moneyin));
        this.mTransactionType.setText(getResources().getString(R.string.reloading));
        this.mRecipientType.setText(getResources().getString(R.string.destinataire));
        this.mRecipientName.setText(this.d);
        if (this.c == null || this.c.length() == 0) {
            this.mMessage.setVisibility(8);
        } else {
            this.mMessage.setRightText(this.c);
        }
        if (this.b != null || this.b.length() > 0) {
            BigDecimal bigDecimal = new BigDecimal(this.b);
            this.mAmount.setRightText(String.format(Locale.getDefault(), "%1$.2f%2$s", new Object[]{Double.valueOf(bigDecimal.doubleValue()), this.f}));
        } else {
            this.mAmount.setRightText((int) R.string.confirm_result_tv_amount_without_amount);
        }
        this.mDate.setRightText(this.e);
        this.mRecipient.setRightText(this.d);
        jb.a(getApplicationContext(), R.string.screen_name_money_in_tier_result_activity);
    }
}
