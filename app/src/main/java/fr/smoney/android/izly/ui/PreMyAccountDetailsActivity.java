package fr.smoney.android.izly.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;

import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.ui.fragment.MyAccountDetailsFragment;

public class PreMyAccountDetailsActivity extends SmoneyABSActivity {
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_pre_my_account_details);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        a((int) R.id.content_fragment, (Fragment) MyAccountDetailsFragment.n());
    }
}
