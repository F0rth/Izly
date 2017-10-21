package defpackage;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import fr.smoney.android.izly.R;

public final class z extends y {
    public boolean o = false;

    protected static void d() {
    }

    protected static void o() {
    }

    protected static void p() {
    }

    protected static boolean q() {
        return false;
    }

    public final void a(FragmentActivity fragmentActivity, String str) {
        ActionBar supportActionBar = this.d.getSupportActionBar();
        if (str == null || str.length() <= 0) {
            supportActionBar.setDisplayShowTitleEnabled(false);
            supportActionBar.setCustomView(R.layout.logo_appli);
            supportActionBar.setDisplayShowCustomEnabled(true);
            return;
        }
        supportActionBar.setDisplayShowTitleEnabled(true);
        supportActionBar.setDisplayShowCustomEnabled(false);
        fragmentActivity.setTitle(str);
    }

    protected final void a(View view) {
        if (view != null) {
            this.k = view.findViewById(R.id.cash_layout);
            this.f = view.findViewById(R.id.rl_account_balance_container);
            this.g = (TextView) view.findViewById(R.id.tv_account_balance_value);
            this.j = (TextView) view.findViewById(R.id.cash_value);
            this.h = (TextView) view.findViewById(R.id.tv_account_balance_currency);
            this.i = (TextView) view.findViewById(R.id.tv_account_balance_date);
            this.l = (RelativeLayout) view.findViewById(R.id.counter_layout);
            this.m = (LinearLayout) view.findViewById(R.id.ll_counter_details);
        }
    }

    protected final void r() {
        ActionBar supportActionBar = this.d.getSupportActionBar();
        supportActionBar.removeAllTabs();
        supportActionBar.setNavigationMode(0);
        this.d.setSupportProgressBarIndeterminateVisibility(false);
    }
}
