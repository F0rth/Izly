package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.widget.TextView;

import defpackage.jb;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.MoneyOutTransferConfirmData;
import fr.smoney.android.izly.ui.widget.DetailTwoText;

import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Locale;

public class MoneyOutTransferResultActivity extends SmoneyABSActivity {
    private DetailTwoText b;
    private TextView c;
    private TextView d;
    private TextView e;
    private DetailTwoText f;
    private DetailTwoText g;
    private DetailTwoText h;
    private MoneyOutTransferConfirmData i;
    private DetailTwoText j;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.money_operation_result);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        this.d = (TextView) findViewById(R.id.tv_money_operation_info);
        this.b = (DetailTwoText) findViewById(R.id.tv_commission);
        this.c = (TextView) findViewById(R.id.tv_money_operation_alias);
        this.e = (TextView) findViewById(R.id.tv_money_operation_hint);
        this.f = (DetailTwoText) findViewById(R.id.tv_amount);
        this.g = (DetailTwoText) findViewById(R.id.tv_old_balance_value);
        this.j = (DetailTwoText) findViewById(R.id.tv_new_balance_value);
        this.h = (DetailTwoText) findViewById(R.id.tv_date);
        Intent intent = getIntent();
        if (intent != null) {
            this.i = (MoneyOutTransferConfirmData) intent.getParcelableExtra("fr.smoney.android.izly.extras.moneyOutTransferConfirmData");
        }
        String symbol = Currency.getInstance(i().b.j).getSymbol();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM", Locale.getDefault());
        this.d.setText(R.string.money_out_transfer_title);
        this.c.setText(this.i.c);
        this.e.setText(this.i.d);
        this.f.setRightText(String.format("%1$.2f%2$s", new Object[]{Double.valueOf(this.i.e), symbol}));
        double d = i().b.B.a;
        this.g.setRightText(String.format("%1$.2f%2$s", new Object[]{Double.valueOf(this.i.e + d), symbol}));
        this.j.setRightText(String.format("%1$.2f%2$s", new Object[]{Double.valueOf(d), symbol}));
        this.h.setRightText(new SimpleDateFormat("EEEE dd MMMM yyyy 'à' HH'h'mm", Locale.getDefault()).format(Long.valueOf(this.i.b)));
        if (this.i.g > 0.0d) {
            this.b.setVisibility(0);
            this.b.setRightText(String.format(Locale.getDefault(), "%1$.2f%2$s", new Object[]{Double.valueOf(this.i.g), symbol}));
        }
        jb.a(getApplicationContext(), R.string.screen_name_money_out_result_activity);
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
