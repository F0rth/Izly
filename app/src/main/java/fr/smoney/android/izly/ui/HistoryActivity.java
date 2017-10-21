package fr.smoney.android.izly.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

import defpackage.hq;
import defpackage.jb;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.ui.fragment.MyCountersFragment;

public class HistoryActivity extends TabSwipeActivity {
    public int b = 1;
    private int c = 0;

    protected final void d(int i) {
        jb.a(getApplicationContext(), i == this.c ? R.string.screen_name_historic_activity : R.string.screen_name_historic_counter_activity);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        a(R.string.operations, hq.class, null);
        a(R.string.counters, MyCountersFragment.class, null);
    }
}
