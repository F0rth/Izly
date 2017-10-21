package fr.smoney.android.izly.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;

import defpackage.jb;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;

public class ServicesWingitActivity extends SmoneyABSActivity {
    private cl b;
    private Activity c;

    public void btnWingit(View view) {
        jb.a(this, R.string.tracking_category_services, R.string.tracking_action_go, R.string.tracking_label_wingit);
        finish();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse("izly://MKG/menuentries/7"));
        startActivity(intent);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setTitle(R.string.services_string);
        setContentView((int) R.layout.services_wingit);
        this.b = i();
        this.c = this;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        onBackPressed();
        return true;
    }
}
