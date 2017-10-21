package fr.smoney.android.izly.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import defpackage.is;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;

public class RetrievePasswordBlockedActivity extends SmoneyABSActivity implements OnClickListener {
    private Button b;

    public void onClick(View view) {
        if (view == this.b) {
            startActivity(is.a(this, RetrievePasswordSecretQuestionActivity.class));
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.retrieve_password_blocked);
        this.b = (Button) findViewById(R.id.b_retrieve_password);
        this.b.setOnClickListener(this);
    }
}
