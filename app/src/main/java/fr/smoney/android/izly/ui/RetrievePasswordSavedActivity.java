package fr.smoney.android.izly.ui;

import android.os.Bundle;
import android.view.KeyEvent;

import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;

public class RetrievePasswordSavedActivity extends SmoneyABSActivity {
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.retrieve_password_request);
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
}
