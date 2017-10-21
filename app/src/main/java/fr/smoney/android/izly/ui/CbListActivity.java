package fr.smoney.android.izly.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;

public class CbListActivity extends SmoneyABSActivity {
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.cb_list_activity_container);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
    }
}
