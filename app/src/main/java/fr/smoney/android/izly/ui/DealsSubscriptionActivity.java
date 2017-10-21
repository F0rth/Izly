package fr.smoney.android.izly.ui;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.PostDealsCodeResponse;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;

import java.util.regex.Pattern;

public class DealsSubscriptionActivity extends SmoneyABSActivity implements SmoneyRequestManager$a {
    private static final Pattern g = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", 2);
    private cl b;
    private EditText c;
    private EditText d;
    private EditText e;
    private Button f;

    private void a(PostDealsCodeResponse postDealsCodeResponse, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (postDealsCodeResponse.a == 201) {
            new Builder(this).setTitle(getIntent().getStringExtra(getString(R.string.services_bons_plans_extra_short_desc))).setMessage(getString(R.string.services_subscription_success_message) + " " + this.c.getText().toString() + ".").setNeutralButton(getString(R.string.services_subscription_button_ok), new OnClickListener(this) {
                final /* synthetic */ DealsSubscriptionActivity a;

                {
                    this.a = r1;
                }

                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.a.a();
                }
            }).show();
        } else {
            new Builder(this).setTitle(getIntent().getStringExtra(getString(R.string.services_bons_plans_extra_short_desc))).setMessage(getString(R.string.services_subscription_failure_message)).setNeutralButton(getString(R.string.services_subscription_button_ok), new OnClickListener(this) {
                final /* synthetic */ DealsSubscriptionActivity a;

                {
                    this.a = r1;
                }

                public final void onClick(DialogInterface dialogInterface, int i) {
                    this.a.a();
                }
            }).show();
        }
    }

    public final void a_(int i, int i2, int i3, Bundle bundle) {
        if (super.a(i, i2, i3, bundle)) {
            a((PostDealsCodeResponse) bundle.getParcelable("fr.smoney.android.izly.extra.PostDealsData"), (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError"));
        }
    }

    public final void b_(int i) {
        a(this.b.cg, this.b.ce);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setTitle(R.string.services_bons_plans);
        setContentView((int) R.layout.deals_subscription);
        this.b = i();
        this.c = (EditText) findViewById(R.id.deals_subscription_mail);
        this.d = (EditText) findViewById(R.id.deals_subscription_name);
        this.e = (EditText) findViewById(R.id.deals_subscription_first_name);
        this.f = (Button) findViewById(R.id.deals_subscription_button);
        this.c.setText(this.b.b.r);
        this.d.setText(this.b.b.q);
        this.e.setText(this.b.b.p);
        ((TextView) findViewById(R.id.deals_subscription_deals_name)).setText(getIntent().getStringExtra(getString(R.string.services_bons_plans_extra_short_desc)));
        this.c.addTextChangedListener(new TextWatcher(this) {
            final /* synthetic */ DealsSubscriptionActivity a;

            {
                this.a = r1;
            }

            public final void afterTextChanged(Editable editable) {
                if (DealsSubscriptionActivity.g.matcher(this.a.c.getText().toString()).find()) {
                    this.a.f.setEnabled(true);
                    this.a.f.setBackgroundColor(this.a.getResources().getColor(R.color.izly_orange));
                    return;
                }
                this.a.f.setEnabled(false);
                this.a.f.setBackgroundColor(this.a.getResources().getColor(R.color.izly_grey));
            }

            public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
        });
        this.d.addTextChangedListener(new TextWatcher(this) {
            final /* synthetic */ DealsSubscriptionActivity a;

            {
                this.a = r1;
            }

            public final void afterTextChanged(Editable editable) {
                if (this.a.d.getText().toString().length() > 0) {
                    this.a.f.setEnabled(true);
                    this.a.f.setBackgroundColor(this.a.getResources().getColor(R.color.izly_orange));
                    return;
                }
                this.a.f.setEnabled(false);
                this.a.f.setBackgroundColor(this.a.getResources().getColor(R.color.izly_grey));
            }

            public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
        });
        this.e.addTextChangedListener(new TextWatcher(this) {
            final /* synthetic */ DealsSubscriptionActivity a;

            {
                this.a = r1;
            }

            public final void afterTextChanged(Editable editable) {
                if (this.a.e.getText().toString().length() > 0) {
                    this.a.f.setEnabled(true);
                    this.a.f.setBackgroundColor(this.a.getResources().getColor(R.color.izly_orange));
                    return;
                }
                this.a.f.setEnabled(false);
                this.a.f.setBackgroundColor(this.a.getResources().getColor(R.color.izly_grey));
            }

            public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        onBackPressed();
        return true;
    }

    public void subscriptionValidation(View view) {
        String obj = this.c.getText().toString();
        String obj2 = this.d.getText().toString();
        String obj3 = this.e.getText().toString();
        String str = this.b.b.b;
        super.a(j().d("http://izly.maximiles.com/api/order/" + getIntent().getStringExtra(getString(R.string.services_bons_plans_extra_id)), obj, obj2, obj3, str), 272, false);
    }
}
