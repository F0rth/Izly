package fr.smoney.android.izly.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import defpackage.is;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;

public class PhoneValidationResultActivity extends SmoneyABSActivity implements OnClickListener {
    private Button b;

    public void onClick(View view) {
        if (view == this.b) {
            startActivity(is.a(this, HomeActivity.class));
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.phone_validation_result);
        this.b = (Button) findViewById(R.id.b_confirm);
        this.b.setOnClickListener(this);
    }
}
