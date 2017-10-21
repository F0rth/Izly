package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import defpackage.jb;
import defpackage.jk;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.MoneyInCbConfirmData;
import fr.smoney.android.izly.ui.widget.DetailTwoText;

import java.util.Currency;

public class MoneyInCbResultActivity extends SmoneyABSActivity {
    private MoneyInCbConfirmData b;
    @Bind({2131755391})
    DetailTwoText mTextViewAmount;
    @Bind({2131755379})
    TextView mTextViewCardAlias;
    @Bind({2131755380})
    TextView mTextViewCardHint;
    @Bind({2131755390})
    DetailTwoText mTextViewDate;
    @Bind({2131755675})
    DetailTwoText mTextViewNewBalance;
    @Bind({2131755674})
    DetailTwoText mTextViewOldBalance;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.money_operation_result);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent != null) {
            this.b = (MoneyInCbConfirmData) intent.getParcelableExtra("fr.smoney.android.izly.extras.moneyInCbConfirmData");
        }
        String symbol = Currency.getInstance(i().b.j).getSymbol();
        this.mTextViewCardAlias.setText(this.b.a.c.b);
        this.mTextViewCardHint.setText(this.b.a.c.d);
        this.mTextViewAmount.setRightText(String.format("%1$.2f%2$s", new Object[]{Double.valueOf(this.b.a.e), symbol}));
        this.mTextViewOldBalance.setRightText(String.format("%1$.2f%2$s", new Object[]{Double.valueOf(this.b.e.a - this.b.a.e), symbol}));
        this.mTextViewNewBalance.setRightText(String.format("%1$.2f%2$s", new Object[]{Double.valueOf(this.b.e.a), symbol}));
        this.mTextViewDate.setRightText(jk.b(this, this.b.a.b));
        jb.a(getApplicationContext(), R.string.screen_name_money_in_cb_result_activity);
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
