package fr.smoney.android.izly.ui;

import android.os.Bundle;
import android.view.KeyEvent;

import defpackage.jb;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;

public class PhoneNonValidatedActivity extends SmoneyABSActivity {
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.phone_non_validated);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        switch (i) {
            case 4:
                a(true);
                return true;
            default:
                return super.onKeyUp(i, keyEvent);
        }
    }

    protected void onStart() {
        super.onStart();
        jb.a(getApplicationContext(), R.string.screen_name_phone_sms_activity);
    }
}
