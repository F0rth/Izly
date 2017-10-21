package fr.smoney.android.izly.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;

import defpackage.he;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;

public class AllContactsActivity extends SmoneyABSActivity {
    private boolean b = false;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_all_contacts);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        int i = -1;
        if (getIntent().getExtras() != null) {
            i = getIntent().getExtras().getInt("fr.smoney.android.izly.intentExtrasModeContactPicker");
            this.b = getIntent().getExtras().getBoolean("fr.smoney.android.izly.intentExtrasModeTypeContact", false);
        }
        a((int) R.id.content, (Fragment) he.a(i, this.b));
    }
}
