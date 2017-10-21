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
import defpackage.jk;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.ui.widget.DetailTwoText;

import java.math.BigDecimal;
import java.util.Currency;

public class MoneyInVmeResult extends SmoneyABSActivity {
    private String b;
    private String c;
    private cl d;
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
        setContentView((int) R.layout.result_v_me_activity);
        ButterKnife.bind(this);
        overridePendingTransition(R.anim.activity_start_enter, R.anim.activity_start_exit);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        this.d = i();
        Intent intent = getIntent();
        this.b = intent.getStringExtra("fr.smoney.android.izly.extras.vme_amount");
        this.c = intent.getStringExtra("fr.smoney.android.izly.extras.vme_date");
        String symbol = Currency.getInstance(this.d.b.j).getSymbol();
        this.mPhoto.setImageDrawable(getResources().getDrawable(R.drawable.icon_home_moneyin));
        this.mTransactionType.setText(getResources().getString(R.string.title_activity_reload_vme));
        this.mRecipientName.setVisibility(8);
        this.mRecipientType.setVisibility(8);
        BigDecimal divide = new BigDecimal(this.b).divide(new BigDecimal(100));
        BigDecimal bigDecimal = new BigDecimal(this.d.b.B.a);
        String a = iu.a(divide.doubleValue(), symbol);
        if (this.b != null) {
            this.mAmount.setRightText(a);
        }
        this.mDate.setRightText(jk.a(this.c, "dd/MM/yyyy 'à' HH:mm"));
        this.mStatus.setText(getString(R.string.reload_v_me_status_result, new Object[]{a}));
        this.mOldBalance.setRightText(iu.a(bigDecimal.doubleValue(), symbol));
        this.mNewBalance.setRightText(iu.a(bigDecimal.doubleValue() + divide.doubleValue(), symbol));
        jb.a(getApplicationContext(), R.string.screen_name_money_in_v_me_result_activity);
    }
}
