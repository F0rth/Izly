package fr.smoney.android.izly.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;

public class PasswordChangedActivity extends SmoneyABSActivity implements OnClickListener {
    private Button b;

    public void onClick(View view) {
        if (view.equals(this.b)) {
            a(true);
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.password_changed);
        this.b = (Button) findViewById(R.id.b_close);
        this.b.setOnClickListener(this);
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
