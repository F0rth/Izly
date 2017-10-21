package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import defpackage.is;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;

public class CompleteSubscriptionActivity extends SmoneyABSActivity implements OnClickListener {
    private Button b;
    private Button c;

    public void onClick(View view) {
        if (view == this.b) {
            startActivity(is.a(this, HomeActivity.class));
            finish();
        } else if (view == this.c) {
            Intent a = is.a(this, CompleteAccountWrapperActivity.class);
            a.putExtra("fr.smoney.android.izly.extras.activityToStart", 8);
            a.putExtra("fr.smoney.android.izly.extras.isADirectCall", true);
            startActivity(a);
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.complete_subscription);
        getSupportActionBar().setHomeButtonEnabled(false);
        this.b = (Button) findViewById(R.id.b_continue_to_smoney);
        this.b.setOnClickListener(this);
        this.c = (Button) findViewById(R.id.b_complete_subscription);
        this.c.setOnClickListener(this);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        switch (i) {
            case 4:
                a();
                return true;
            default:
                return super.onKeyUp(i, keyEvent);
        }
    }
}
