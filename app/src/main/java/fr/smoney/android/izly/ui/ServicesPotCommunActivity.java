package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;

import defpackage.jb;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;

public class ServicesPotCommunActivity extends SmoneyABSActivity {
    public void btnPotCommun(View view) {
        jb.a(this, R.string.tracking_category_services, R.string.tracking_action_go, R.string.tracking_label_lpc);
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://izly.lepotcommun.fr/?src=lepotcommun")));
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setTitle(R.string.services_string);
        setContentView((int) R.layout.services_pot_commun);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        onBackPressed();
        return true;
    }
}
