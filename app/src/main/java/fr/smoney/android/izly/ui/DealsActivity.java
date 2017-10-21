package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thewingitapp.thirdparties.wingitlib.BuildConfig;

import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.DealsData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;

public class DealsActivity extends SmoneyABSActivity implements SmoneyRequestManager$a {
    private cl b;
    private Intent c;
    private String d;
    private String e;
    private DealsData f;

    private void a(DealsData dealsData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (dealsData != null) {
            this.f = dealsData;
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            ((TextView) findViewById(R.id.title_deals)).setText(dealsData.b);
            ((WebView) findViewById(R.id.text_deals)).loadDataWithBaseURL(null, dealsData.a, "text/html", "utf-8", null);
            Button button = (Button) findViewById(R.id.button_deals_share);
            double d = ((double) displayMetrics.widthPixels) * 0.45d;
            ((Button) findViewById(R.id.button_deals)).setWidth((int) d);
            button.setWidth((int) d);
            d = ((double) displayMetrics.widthPixels) * 0.8d;
            Picasso.with(this).load(dealsData.c).resize((int) d, ((int) d) / 2).into((ImageView) findViewById(R.id.image_deals));
            this.d = dealsData.e;
            this.e = dealsData.f;
            this.c = new Intent(this, DealsSubscriptionActivity.class);
            this.c.putExtra(getString(R.string.services_bons_plans_extra_short_desc), dealsData.b);
            this.c.putExtra(getString(R.string.services_bons_plans_extra_id), dealsData.d);
            findViewById(R.id.loadingPanelDeals).setVisibility(8);
            findViewById(R.id.subscription_button_layout).setVisibility(0);
        }
    }

    public final void a_(int i, int i2, int i3, Bundle bundle) {
        if (super.a(i, i2, i3, bundle)) {
            a((DealsData) bundle.getParcelable("fr.smoney.android.izly.extra.DealsData"), (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError"));
        }
    }

    public final void b_(int i) {
        a(this.b.cf, this.b.ce);
    }

    public void displaySubscriptionPage(View view) {
        if (this.d.equals("3")) {
            if (this.c != null) {
                startActivity(this.c);
            }
        } else if (this.d.equals(BuildConfig.VERSION_NAME)) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(this.e)));
        }
    }

    protected void onCreate(Bundle bundle) {
        int keyAt;
        super.onCreate(bundle);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setTitle(R.string.services_bons_plans);
        setContentView((int) R.layout.good_deals);
        this.b = i();
        Bundle extras = getIntent().getExtras();
        SmoneyRequestManager j = j();
        String string = extras.getString(getString(R.string.services_bons_plans_extra_url));
        int size = j.b.size();
        for (int i = 0; i < size; i++) {
            Intent intent = (Intent) j.b.valueAt(i);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 271 && intent.getStringExtra("fr.smoney.android.izly.extras.urlDeals").equals(string)) {
                keyAt = j.b.keyAt(i);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 271);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.urlDeals", string);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.cc = null;
        super.a(keyAt, 271, false);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        onBackPressed();
        return true;
    }

    public void shareDeals(View view) {
        String string;
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.SUBJECT", getString(R.string.services_bons_plans_share_subject));
        if (this.f != null) {
            string = getString(R.string.services_bons_plans_share_body, new Object[]{this.f.b});
        } else {
            string = getString(R.string.services_bons_plans_share_body);
        }
        intent.putExtra("android.intent.extra.TEXT", string);
        startActivity(Intent.createChooser(intent, getString(R.string.services_bons_plans_share_button)));
    }
}
