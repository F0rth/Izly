package fr.smoney.android.izly.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;

import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;

public class ChangePasswordResultActivity extends SmoneyABSActivity {
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.change_password_result);
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return super.onKeyDown(i, keyEvent);
        }
        a(false);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return false;
        }
        a(false);
        return true;
    }
}
