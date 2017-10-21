package fr.smoney.android.izly.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import defpackage.hb;
import defpackage.hg;
import defpackage.jb;
import fr.smoney.android.izly.R;

public class ContactsActivity extends TabSwipeActivity {
    public boolean b = false;
    private int c = 0;
    private int d = 1;

    protected final void d(int i) {
        jb.a(getApplicationContext(), i == this.c ? R.string.screen_name_favorite_contacts_activity : R.string.screen_name_phone_contacts_activity);
    }

    protected void onCreate(Bundle bundle) {
        int i;
        super.onCreate(bundle);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        b(false);
        if (getIntent().getExtras() != null) {
            i = getIntent().getExtras().getInt("fr.smoney.android.izly.intentExtrasModeContactPicker");
            this.b = getIntent().getExtras().getBoolean("fr.smoney.android.izly.intentExtrasModeTypeContact", false);
        } else {
            i = -1;
        }
        Bundle bundle2 = new Bundle();
        if (i == -1) {
            i = 0;
        }
        bundle2.putInt("fr.smoney.android.izly.argumentModeContactPicker", i);
        bundle2.putBoolean("fr.smoney.android.izly.intentExtrasModeTypeContact", this.b);
        a(R.string.my_account_contacts_favorites, hg.class, bundle2);
        a(R.string.my_account_contacts_all, hb.class, bundle2);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return false;
        }
        finish();
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                finish();
                return true;
            default:
                return false;
        }
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }
}
