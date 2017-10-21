package fr.smoney.android.izly.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.ui.adapter.IzlyListAdapterWithCheckboxHelper;
import fr.smoney.android.izly.ui.fragment.SettingsFragment;

public class OffersAndServicesActivity extends SmoneyABSActivity {
    public static String b = "fr.smoney.android.izly.ui.OffersAndServicesActivity.ModeOffers";
    public static String c = "fr.smoney.android.izly.ui.OffersAndServicesActivity.ModeServices";
    private IzlyListAdapterWithCheckboxHelper d;
    private String e;
    @Bind({2131755281})
    LinearLayout mOffersServicesLayout;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_offers_and_services);
        ButterKnife.bind(this);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.e = extras.getString(SettingsFragment.e);
        }
        setTitle(this.e.compareTo(b) == 0 ? getString(R.string.title_activity_offers) : getString(R.string.title_activity_services));
        this.d = new IzlyListAdapterWithCheckboxHelper(getApplicationContext(), cf.values());
        for (int i = 0; i < this.d.getCount(); i++) {
            this.mOffersServicesLayout.addView(this.d.getView(i, null, null), i);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        finish();
        return true;
    }
}
