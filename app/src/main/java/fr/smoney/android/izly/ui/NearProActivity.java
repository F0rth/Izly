package fr.smoney.android.izly.ui;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import defpackage.gf;
import defpackage.hj;
import defpackage.hk;
import defpackage.hw;
import defpackage.ic;
import defpackage.ie;
import defpackage.if$a;
import defpackage.jb;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.data.model.GetNearProListData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;

public class NearProActivity extends TabSwipeActivity implements SmoneyRequestManager$a, if$a {
    public static int b = 10;
    public int c = 0;
    public gf d;
    public gf e;
    public Location f;
    private int h = 0;
    private int i = 1;
    private MenuItem j;
    private int k = 0;

    private void a(GetNearProListData getNearProListData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (getNearProListData == null) {
            a(hw.a(getApplicationContext(), this));
        } else {
            this.e.b_();
            this.d.b_();
        }
    }

    public final void a(ie ieVar, Bundle bundle) {
        if (ieVar == ie.ProCategorySelectionType) {
            this.c = bundle.getInt("Data.SelectedCategory");
            this.k = bundle.getInt("Data.SelectedCategory.Position");
            Location location = this.f;
            int i = this.c;
            if (location != null) {
                cl i2 = i();
                super.a(j().a(i2.b.a, i2.b.c, location.getLatitude(), location.getLongitude(), i, 0), 213, true);
                return;
            }
            return;
        }
        super.a(ieVar, bundle);
    }

    public final void a_(int i, int i2, int i3, Bundle bundle) {
        if (super.a(i, i2, i3, bundle)) {
            ServerError serverError = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
            if (i2 == 213) {
                a((GetNearProListData) bundle.getParcelable("fr.smoney.android.izly.extras.GetNearProList"), serverError);
            }
        }
    }

    public final void b_(int i) {
        cl i2 = i();
        if (i == 213) {
            a(i2.aW, i2.aX);
        }
    }

    protected final void d(int i) {
        jb.a(getApplicationContext(), i == this.h ? R.string.screen_name_around_me_list_activity : R.string.screen_name_around_me_map_activity);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        b(false);
        a(R.string.near_pro_activities_list, hj.class, null);
        a(R.string.near_pro_activities_map, hk.class, null);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        this.j = menu.add(R.string.near_pro_activities_dialog_title);
        this.j.setIcon(R.drawable.icon_filtres);
        this.j.setShowAsAction(2);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem != this.j) {
            return super.onOptionsItemSelected(menuItem);
        }
        a(ic.a(getString(R.string.near_pro_activities_dialog_title), this, this.k));
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        this.j.setVisible(true);
        return super.onPrepareOptionsMenu(menu);
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }
}
