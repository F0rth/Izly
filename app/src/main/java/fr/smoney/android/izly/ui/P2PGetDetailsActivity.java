package fr.smoney.android.izly.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import defpackage.hm;
import defpackage.hn;
import defpackage.hw;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.GetNewsFeedDetailsData;
import fr.smoney.android.izly.data.model.NewsFeedItem;
import fr.smoney.android.izly.data.model.P2PGetMult;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;

public class P2PGetDetailsActivity extends SmoneyABSActivity implements SmoneyRequestManager$a {
    private static final String b = P2PGetDetailsActivity.class.getSimpleName();

    private void a(GetNewsFeedDetailsData getNewsFeedDetailsData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (getNewsFeedDetailsData == null) {
            a(hw.a(this, this));
        } else {
            a((P2PGetMult) getNewsFeedDetailsData.a);
        }
    }

    private void a(P2PGetMult p2PGetMult) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        if (p2PGetMult.b.size() > 1) {
            beginTransaction.add(R.id.fl_framgent_p2p_get_details_content, hm.a(p2PGetMult));
        } else {
            beginTransaction.add(R.id.fl_framgent_p2p_get_details_content, hn.a(p2PGetMult));
        }
        beginTransaction.commit();
    }

    public final void a_(int i, int i2, int i3, Bundle bundle) {
        if (super.a(i, i2, i3, bundle)) {
            ServerError serverError = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
            if (i2 == 227) {
                a((GetNewsFeedDetailsData) bundle.getParcelable("fr.smoney.android.izly.extras.GetNewsFeedDetails"), serverError);
            }
        }
    }

    public final void b_(int i) {
        cl i2 = i();
        if (i == 227) {
            a(i2.bx, i2.by);
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.p2p_get_details);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            P2PGetMult p2PGetMult = (P2PGetMult) extras.getParcelable("fr.smoney.android.izly.extras.p2pGetMultDetailsData");
            NewsFeedItem newsFeedItem = (NewsFeedItem) extras.getParcelable("fr.smoney.android.izly.extras.relatedFeedItem");
            if (p2PGetMult == null && newsFeedItem == null) {
                finish();
                return;
            } else if (p2PGetMult != null) {
                a(p2PGetMult);
                return;
            } else {
                cl i = i();
                super.a(j().a(i.b.a, i.b.c, newsFeedItem), 227, true);
                return;
            }
        }
        finish();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
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
