package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;

import defpackage.hh;
import defpackage.jb;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.ui.fragment.ServicesFragment;

public class GoodDealsActivity extends TabSwipeActivity {
    public static String b = "goodDealsTabToDisplay";
    public static String c = "goodDealsServiceToDisplay";

    protected final void d(int i) {
        super.d(i);
        if (i == 1) {
            jb.a(this, R.string.tracking_category_trends, R.string.tracking_action_tab, R.string.tracking_label_gooddeals);
        }
        if (i == 0) {
            jb.a(this, R.string.tracking_category_trends, R.string.tracking_action_tab, R.string.tracking_label_services);
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setTitle(R.string.services_title);
        a(R.string.services_string, ServicesFragment.class, null);
        a(R.string.services_bons_plans, hh.class, null);
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            int intExtra = intent.getIntExtra(b, -1);
            int intExtra2 = intent.getIntExtra(c, -1);
            if (intExtra != -1) {
                this.mViewPager.setCurrentItem(intExtra);
            }
            if (intExtra2 != -1) {
                this.mViewPager.setCurrentItem(0);
                switch (intExtra2) {
                    case 0:
                        startActivity(new Intent(this, ServicesPotCommunActivity.class));
                        return;
                    case 1:
                        startActivity(new Intent(this, ServicesSynintraActivity.class));
                        return;
                    case 2:
                        startActivity(new Intent(this, ServicesMoneyFriendsActivity.class));
                        return;
                    case 3:
                        startActivity(new Intent(this, ServicesWingitActivity.class));
                        return;
                    default:
                        return;
                }
            }
        }
    }
}
