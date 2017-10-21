package fr.smoney.android.izly.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import defpackage.jb;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;

public class BlockAccountResultActivity extends SmoneyABSActivity implements OnClickListener {
    private Button b;

    public void onClick(View view) {
        if (view.equals(this.b)) {
            a(false);
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.block_account_result);
        this.b = (Button) findViewById(R.id.b_close);
        this.b.setOnClickListener(this);
        jb.a(getApplicationContext(), R.string.screen_name_block_account_result_activity);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        switch (i) {
            case 4:
                a(false);
                return true;
            default:
                return super.onKeyUp(i, keyEvent);
        }
    }
}
