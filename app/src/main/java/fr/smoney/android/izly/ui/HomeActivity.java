package fr.smoney.android.izly.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.slidingmenu.lib.SlidingMenu;

import defpackage.hi;
import defpackage.hp;
import defpackage.hp$a;
import defpackage.hp$b;
import defpackage.ht;
import defpackage.hu;
import defpackage.hw;
import defpackage.ie;
import defpackage.ih;
import defpackage.ir;
import defpackage.is;
import defpackage.jb;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyApplication;
import fr.smoney.android.izly.SmoneySlidingActivity;
import fr.smoney.android.izly.data.model.LoginData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.ui.activity.GetCityLocationActivity;

import java.util.List;

public class HomeActivity extends SmoneySlidingActivity implements SmoneyRequestManager$a, hp$a, ih {
    public hp b;
    private FragmentManager c;
    private SlidingMenu d;
    private cl e;
    private Bundle f;
    private boolean g = true;
    private hi h;
    private Activity i;
    private int j = -1;
    private int k = -1;

    final class a extends AsyncTask<Integer, Void, Void> {
        final /* synthetic */ HomeActivity a;

        private a(HomeActivity homeActivity) {
            this.a = homeActivity;
        }

        protected final /* synthetic */ Object doInBackground(Object[] objArr) {
            switch (((Integer[]) objArr)[0].intValue()) {
                case 0:
                    this.a.a(is.a(this.a, PreMyAccountDetailsActivity.class), true);
                    break;
                case 1:
                    this.a.f();
                    break;
                case 2:
                    this.a.a(is.a(this.a, HistoryActivity.class), true);
                    break;
                case 3:
                    this.a.a(is.a(this.a, PreAskSendMoneyActivity.class), true);
                    break;
                case 4:
                    this.a.a(is.a(this.a, MyRequestActivity.class), true);
                    break;
                case 5:
                    HomeActivity.l(this.a);
                    break;
                case 6:
                    HomeActivity.f(this.a);
                    break;
                case 7:
                    HomeActivity.g(this.a);
                    break;
                case 8:
                    HomeActivity.h(this.a);
                    break;
                case 9:
                    HomeActivity.i(this.a);
                    break;
                case 10:
                    this.a.a(is.a(this.a, PreSettingsActivity.class), true);
                    break;
                case 11:
                    HomeActivity.m(this.a);
                    break;
                case 12:
                    this.a.a(ht.a(this.a.getString(R.string.dialog_logout_title), this.a.getString(R.string.dialog_logout_message), this.a.getString(17039370), this.a.getString(17039360), this.a, ie.LogoutType));
                    break;
            }
            return null;
        }

        protected final /* synthetic */ void onPostExecute(Object obj) {
            super.onPostExecute((Void) obj);
            if (this.a.d.isMenuShowing()) {
                this.a.d.toggle();
            }
            this.a.g = true;
        }

        protected final void onPreExecute() {
            this.a.g = false;
            super.onPreExecute();
        }
    }

    private void a(Bundle bundle, Intent intent) {
        if (bundle == null) {
            this.b = hp.n();
            FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
            beginTransaction.add(R.id.menu_fragment, this.b);
            beginTransaction.commit();
            getSupportFragmentManager().executePendingTransactions();
        } else {
            this.b = (hp) this.c.findFragmentById(R.id.menu_fragment);
        }
        if (intent != null) {
            switch (intent.getIntExtra("fr.smoney.android.izly.extras.launchActivity", -1)) {
                case 1:
                    String stringExtra = intent.getStringExtra("fr.smoney.android.izly.intentExtra.notifMessage");
                    if (stringExtra != null) {
                        a(hu.a(getString(2131230841), stringExtra, getString(R.string.dialog_button_close)));
                        return;
                    }
                    return;
                default:
                    CharSequence stringExtra2 = intent.getStringExtra("fr.smoney.android.izly.extras.toastMessage");
                    if (stringExtra2 != null && !TextUtils.isEmpty(stringExtra2)) {
                        Toast.makeText(this, stringExtra2, 1).show();
                        return;
                    }
                    return;
            }
        }
    }

    private void a(LoginData loginData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (loginData == null && this.a) {
            a(hw.a(this, this));
        } else {
            a(this.f, getIntent());
            this.f = null;
            if (loginData != null) {
                i();
                ir.a(this, loginData);
            }
        }
    }

    private void f() {
        this.h = hi.n();
        if (this.j != -1) {
            this.h.e = this.j;
        }
        if (this.k != -1) {
            this.h.f = this.k;
        }
        a((int) R.id.content_fragment, (Fragment) this.h);
    }

    static /* synthetic */ void f(HomeActivity homeActivity) {
        jb.a(homeActivity, R.string.tracking_category_menu, R.string.tracking_action_button, R.string.tracking_label_createpot);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse("https://izly.lepotcommun.fr/?src=lepotcommun"));
        homeActivity.startActivity(intent);
    }

    private void g() {
        f();
        hp hpVar = this.b;
        hpVar.g = 1;
        hp$b hp_b = hpVar.f;
        hp_b.a = 1;
        hp_b.notifyDataSetChanged();
    }

    static /* synthetic */ void g(HomeActivity homeActivity) {
        if (homeActivity.e.cz) {
            jb.a(homeActivity, R.string.tracking_category_menu, R.string.tracking_action_button, R.string.tracking_label_events);
            Intent intent = new Intent(homeActivity, GetCityLocationActivity.class);
            intent.setFlags(67108864);
            homeActivity.startActivity(intent);
            return;
        }
        homeActivity.i.runOnUiThread(new Runnable(homeActivity) {
            final /* synthetic */ HomeActivity a;

            {
                this.a = r1;
            }

            public final void run() {
                if (this.a.i != null) {
                    Toast.makeText(this.a.i, R.string.service_not_available, 0).show();
                }
            }
        });
    }

    private void h() {
        String f = SmoneyApplication.c.f();
        if (f != null) {
            super.a(d().a(f), 228, true);
        }
    }

    static /* synthetic */ void h(HomeActivity homeActivity) {
        jb.a(homeActivity, R.string.tracking_category_menu, R.string.tracking_action_button, R.string.tracking_label_moneyfriends);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(268435456);
        intent.setData(Uri.parse("https://uym7.app.link/sign_in"));
        homeActivity.startActivity(intent);
    }

    private void i() {
        new cb(this, d(), c()).execute(new Void[]{null, null, null});
    }

    static /* synthetic */ void i(HomeActivity homeActivity) {
        if (ContextCompat.checkSelfPermission(homeActivity, "android.permission.READ_CONTACTS") != 0) {
            ActivityCompat.requestPermissions(homeActivity, new String[]{"android.permission.READ_CONTACTS"}, 118);
            return;
        }
        homeActivity.startActivity(new Intent(homeActivity, ContactsActivity.class));
    }

    static /* synthetic */ void l(HomeActivity homeActivity) {
        Intent a;
        if (homeActivity.c().b.a()) {
            a = is.a(homeActivity, CompleteAccountWrapperActivity.class);
            a.putExtra("fr.smoney.android.izly.extras.activityToStart", 4);
        } else {
            a = is.a(homeActivity, MoneyOutTransferActivity.class);
        }
        homeActivity.a(a, true);
    }

    static /* synthetic */ void m(HomeActivity homeActivity) {
        FragmentManager supportFragmentManager = homeActivity.getSupportFragmentManager();
        FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();
        Fragment findFragmentById = supportFragmentManager.findFragmentById(R.id.content_fragment);
        if (!(findFragmentById == null || (findFragmentById instanceof hi))) {
            beginTransaction.remove(findFragmentById);
        }
        beginTransaction.commit();
        homeActivity.a(is.a(homeActivity, HelpActivity.class), true);
    }

    public final void a(ie ieVar, Bundle bundle) {
        if (ieVar == ie.ConnexionErrorType) {
            if (b() == 228) {
                h();
            } else {
                super.a(ieVar, bundle);
            }
        } else if (ieVar == ie.LocationSettingsType) {
            startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
        } else {
            super.a(ieVar, bundle);
        }
    }

    public final void a_(int i, int i2, int i3, Bundle bundle) {
        if (super.a(i, i2, i3, bundle)) {
            ServerError serverError = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
            if (i2 == 228) {
                a((LoginData) bundle.getParcelable("fr.smoney.android.izly.extras.GetLogonInfos"), serverError);
            }
        }
    }

    public final void b(int i) {
        if (this.g) {
            new a().execute(new Integer[]{Integer.valueOf(i)});
        }
    }

    public final void b(ie ieVar) {
        if (ieVar != ie.ConnexionErrorType) {
            super.b(ieVar);
        } else if (b() == 228) {
            a(false);
        } else {
            super.b(ieVar);
        }
    }

    public final void b_(int i) {
        if (i == 228) {
            a(this.e.b, this.e.d);
        }
    }

    public final void c(ie ieVar) {
        if (ieVar == ie.ErrorType) {
            a(false);
        } else {
            super.c(ieVar);
        }
    }

    public final void d(ie ieVar) {
        if (ieVar == ie.ProgressType) {
            a();
            a(false);
            return;
        }
        super.d(ieVar);
    }

    public final void e() {
        g();
    }

    public final void l() {
        if (this.e.b == null) {
            h();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (this.d != null) {
            this.d.setSlidingEnabled(true);
        }
        super.l();
    }

    public final void m() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        if (this.d != null) {
            if (this.d.isMenuShowing()) {
                this.d.toggle();
            }
            this.d.setSlidingEnabled(false);
        }
        super.m();
    }

    public void onCreate(Bundle bundle) {
        requestWindowFeature(5);
        super.onCreate(bundle);
        this.e = c();
        this.c = getSupportFragmentManager();
        setContentView((int) R.layout.layout_activity_fragment);
        setBehindContentView((int) R.layout.fragment_sliding_menu_container);
        this.i = this;
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayShowTitleEnabled(false);
        supportActionBar.setCustomView(R.layout.logo_appli);
        supportActionBar.setDisplayShowCustomEnabled(true);
        supportActionBar.setHomeAsUpIndicator(R.drawable.icon_menu);
        this.d = getSlidingMenu();
        this.d.setSlidingEnabled(true);
        this.d.setTouchModeAbove(0);
        this.d.setShadowWidthRes(R.dimen.sliding_menu_shadow_width);
        this.d.setShadowDrawable((int) R.drawable.shadow);
        this.d.setBehindOffsetRes(R.dimen.sliding_menu_offset);
        this.d.setFadeDegree(0.35f);
        if (this.e.b == null) {
            this.f = bundle;
            h();
        } else {
            a(bundle, getIntent());
            i();
        }
        Intent intent = getIntent();
        if (intent != null) {
            Uri data = intent.getData();
            if (data != null) {
                List pathSegments = data.getPathSegments();
                String str = (String) pathSegments.get(0);
                if (str.equals("menuentries")) {
                    int intValue = Integer.valueOf((String) pathSegments.get(1)).intValue();
                    f();
                    b(intValue);
                } else if (str.equals("gooddealsandservices")) {
                    this.j = Integer.valueOf((String) pathSegments.get(1)).intValue();
                    if (pathSegments.size() >= 4) {
                        str = (String) pathSegments.get(2);
                        if (str != null && str.equals("services")) {
                            this.k = Integer.valueOf((String) pathSegments.get(3)).intValue();
                        }
                    }
                }
            }
        }
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return super.onKeyUp(i, keyEvent);
        }
        aa a = a((int) R.id.content_fragment);
        if (a != null && a.a()) {
            return true;
        }
        if (this.d.isMenuShowing()) {
            this.d.toggle();
            return true;
        }
        Fragment findFragmentById = this.c.findFragmentById(R.id.content_fragment);
        boolean z = findFragmentById != null && (findFragmentById instanceof hi);
        if (z || this.b == null) {
            setResult(-1);
            return super.onKeyUp(i, keyEvent);
        }
        g();
        return true;
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            Uri data = intent.getData();
            if (data != null) {
                List pathSegments = data.getPathSegments();
                String str = (String) pathSegments.get(0);
                if (str.equals("menuentries")) {
                    int intValue = Integer.valueOf((String) pathSegments.get(1)).intValue();
                    f();
                    b(intValue);
                } else if (str.equals("gooddealsandservices")) {
                    this.j = Integer.valueOf((String) pathSegments.get(1)).intValue();
                    if (pathSegments.size() >= 4) {
                        str = (String) pathSegments.get(2);
                        if (str != null && str.equals("services")) {
                            this.k = Integer.valueOf((String) pathSegments.get(3)).intValue();
                        }
                    }
                    f();
                }
            }
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return false;
        }
        toggle();
        return true;
    }

    public void onPause() {
        super.onPause();
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        switch (i) {
            case 118:
                if (iArr.length > 0 && iArr[0] == 0) {
                    startActivity(new Intent(this, ContactsActivity.class));
                    return;
                }
                return;
            default:
                return;
        }
    }

    protected void onResume() {
        super.onResume();
    }
}
