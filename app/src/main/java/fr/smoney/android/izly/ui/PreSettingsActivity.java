package fr.smoney.android.izly.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.ui.fragment.CbListFragment;
import fr.smoney.android.izly.ui.fragment.SettingsFragment;

public class PreSettingsActivity extends SmoneyABSActivity {
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_pre_settings);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        a((int) R.id.content_fragment, (Fragment) SettingsFragment.n());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        aa b = b((int) R.id.content_fragment);
        if (b instanceof CbListFragment) {
            return false;
        }
        switch (itemId) {
            case 16908332:
                if (!(b instanceof SettingsFragment)) {
                    f();
                    break;
                }
                finish();
                break;
        }
        return true;
    }
}
