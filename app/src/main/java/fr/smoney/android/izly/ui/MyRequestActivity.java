package fr.smoney.android.izly.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

import defpackage.hl;
import defpackage.jb;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.ui.fragment.P2PPayRequestListFragment;

public class MyRequestActivity extends TabSwipeActivity {
    private int b = 0;
    private int c = 1;

    protected final void d(int i) {
        jb.a(getApplicationContext(), i == this.b ? R.string.screen_name_money_demand_list_activity : R.string.screen_name_money_demand_received_list_activity);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        b(true);
        a(R.string.send_request, hl.class, null);
        a(R.string.received_request, P2PPayRequestListFragment.class, null);
    }
}
