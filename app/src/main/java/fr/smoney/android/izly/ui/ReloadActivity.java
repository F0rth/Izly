package fr.smoney.android.izly.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;

import defpackage.ho;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;

public class ReloadActivity extends SmoneyABSActivity {
    protected void onCreate(Bundle bundle) {
        requestWindowFeature(5);
        super.onCreate(bundle);
        setContentView((int) R.layout.layout_activity_fragment);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayUseLogoEnabled(true);
        supportActionBar.setDisplayShowTitleEnabled(true);
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        a((int) R.id.content_fragment, (Fragment) ho.n());
    }
}
