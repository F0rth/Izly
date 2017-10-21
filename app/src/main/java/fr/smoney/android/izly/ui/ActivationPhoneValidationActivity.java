package fr.smoney.android.izly.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import defpackage.ie;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;

public class ActivationPhoneValidationActivity extends SmoneyABSActivity implements OnClickListener {
    cl b;
    @Bind({2131755186})
    TextView mActivationDescription;

    public final void a(ie ieVar, Bundle bundle) {
        int[] iArr = AnonymousClass1.a;
        ieVar.ordinal();
        super.a(ieVar, bundle);
    }

    public void activationSmsNotReceived(View view) {
        finish();
    }

    public final void b(ie ieVar) {
        switch (ieVar) {
            case ConnexionErrorType:
                super.b(ieVar);
                return;
            default:
                super.b(ieVar);
                return;
        }
    }

    public final void c(ie ieVar) {
        switch (ieVar) {
            case ErrorType:
                return;
            default:
                super.c(ieVar);
                return;
        }
    }

    public final void d(ie ieVar) {
        super.d(ieVar);
    }

    public void onBackPressed() {
    }

    public void onClick(View view) {
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.b = i();
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeButtonEnabled(false);
            supportActionBar.setDisplayHomeAsUpEnabled(false);
            supportActionBar.setTitle(R.string.activation_phone_validation_title);
        }
        setContentView((int) R.layout.activity_activation_phone_validation);
        ButterKnife.bind(this);
        this.mActivationDescription.setText(getString(R.string.activation_phone_validation_description, new Object[]{this.b.cq.b}));
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                finish();
                break;
        }
        return true;
    }
}
