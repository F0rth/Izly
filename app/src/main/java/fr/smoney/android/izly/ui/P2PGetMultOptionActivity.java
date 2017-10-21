package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.P2PGetMultOptions;

import java.util.Currency;
import java.util.Locale;

public class P2PGetMultOptionActivity extends SmoneyABSActivity implements OnClickListener, OnCheckedChangeListener {
    private P2PGetMultOptions b;
    private String c;
    private TextSwitcher d;
    private TextSwitcher e;
    private CheckBox f;
    private CheckBox g;
    private Button h;
    private ViewFactory i = new ViewFactory(this) {
        final /* synthetic */ P2PGetMultOptionActivity a;

        {
            this.a = r1;
        }

        public final View makeView() {
            return (TextView) LayoutInflater.from(this.a).inflate(R.layout.include_textview_motif, null);
        }
    };

    private void a(boolean z, boolean z2) {
        int i = z ? R.string.p2p_get_mult_option_total_amount_info_yes : R.string.p2p_get_mult_option_total_amount_info_no;
        double d = this.b.a;
        Object[] objArr = new Object[1];
        objArr[0] = String.format(Locale.getDefault(), "%1$.2f%2$s", new Object[]{Double.valueOf(d), this.c});
        CharSequence fromHtml = Html.fromHtml(getString(i, objArr));
        if (z2) {
            this.d.setText(fromHtml);
        } else {
            this.d.setCurrentText(fromHtml);
        }
    }

    private void b(boolean z, boolean z2) {
        CharSequence fromHtml = Html.fromHtml(getString(z ? R.string.p2p_get_mult_option_include_me_info_yes : R.string.p2p_get_mult_option_include_me_info_no));
        if (z2) {
            this.e.setText(fromHtml);
        } else {
            this.e.setCurrentText(fromHtml);
        }
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        if (compoundButton == this.g) {
            a(z, true);
            if (z) {
                this.f.setEnabled(true);
            } else {
                this.f.setChecked(false);
                this.f.setEnabled(false);
                this.b.b = false;
            }
            this.b.c = z;
        } else if (compoundButton == this.f) {
            b(z, true);
            this.b.b = z;
        }
    }

    public void onClick(View view) {
        Intent intent = new Intent();
        intent.putExtra("fr.smoney.android.izly.P2PGET_OPTIONS", this.b);
        setResult(-1, intent);
        finish();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.p2p_get_mult_option);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        this.c = Currency.getInstance(i().b.j).getSymbol();
        this.b = (P2PGetMultOptions) getIntent().getParcelableExtra("fr.smoney.android.izly.P2PGET_OPTIONS");
        this.d = (TextSwitcher) findViewById(R.id.tv_p2p_option_divide_amount_info);
        this.e = (TextSwitcher) findViewById(R.id.tv_p2p_option_include_me_info);
        this.d.setFactory(this.i);
        this.e.setFactory(this.i);
        this.g = (CheckBox) findViewById(R.id.cb_p2p_option_divide_amount);
        this.f = (CheckBox) findViewById(R.id.cb_p2p_option_include_me);
        this.h = (Button) findViewById(R.id.bt_option_valid);
        this.g.setOnCheckedChangeListener(this);
        this.f.setOnCheckedChangeListener(this);
        this.h.setOnClickListener(this);
        this.g.setChecked(this.b.c);
        this.f.setChecked(this.b.b);
        a(this.b.c, false);
        b(this.b.b, false);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                finish();
                break;
        }
        return true;
    }
}
