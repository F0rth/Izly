package fr.smoney.android.izly.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import defpackage.hw;
import defpackage.ie;
import defpackage.is;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.SmoneyApplication;
import fr.smoney.android.izly.data.model.ActivateUserData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.model.UserSubscribingValues;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;

public class ActivationCGUActivity extends SmoneyABSActivity implements SmoneyRequestManager$a {
    private WebView b;
    private b c;
    private UserSubscribingValues d;
    private MenuItem e;
    private a f;
    private cl g;
    private String h;
    private String i;
    private String j;
    private int k;
    private boolean l = false;

    public enum a {
        CGU_FOR_SUBSCRIBE,
        CGU_CHANGED
    }

    final class b extends WebViewClient {
        boolean a;
        final /* synthetic */ ActivationCGUActivity b;

        private b(ActivationCGUActivity activationCGUActivity) {
            this.b = activationCGUActivity;
            this.a = false;
        }

        public final void onPageFinished(WebView webView, String str) {
            ActivationCGUActivity.b(this.b, false);
            this.a = false;
        }

        public final void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            ActivationCGUActivity.b(this.b, true);
            this.a = true;
        }

        public final boolean shouldOverrideUrlLoading(WebView webView, String str) {
            boolean z = false;
            String lastPathSegment = Uri.parse(str).getLastPathSegment();
            if (lastPathSegment.equals("Avertissement")) {
                this.b.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                this.b.getSupportActionBar().setTitle(this.b.getString(R.string.activation_cgu_security));
                this.b.l = true;
            } else if (lastPathSegment.equals("app_cgu_accept")) {
                if (this.b.f == a.CGU_FOR_SUBSCRIBE) {
                    this.b.k = 1;
                    this.b.k();
                    return true;
                }
                z = true;
            } else if (lastPathSegment.equals("app_cgu_decline")) {
                this.b.setResult(2);
                this.b.finish();
                return true;
            }
            return z;
        }
    }

    private void a(ActivateUserData activateUserData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (activateUserData == null) {
            a(hw.a(this, this));
        } else {
            ac acVar = SmoneyApplication.c;
            acVar.e = activateUserData;
            Editor edit = acVar.f.getSharedPreferences("fr.smoney.android.izly.config.sp", 0).edit();
            edit.putString("ActivationUserData.UserName", activateUserData.c);
            edit.putString("ActivationUserData.Token", activateUserData.b);
            edit.putInt("ActivationUserData.Status", activateUserData.a);
            edit.commit();
            this.g.cp = null;
            startActivity(is.a(this, ActivationAddPhoneNumberActivity.class));
            finish();
        }
    }

    static /* synthetic */ void b(ActivationCGUActivity activationCGUActivity, boolean z) {
        activationCGUActivity.setSupportProgressBarIndeterminateVisibility(z);
        if (activationCGUActivity.e != null) {
            activationCGUActivity.e.setVisible(!z);
        }
    }

    private void k() {
        int keyAt;
        SmoneyRequestManager j = j();
        String str = this.g.cp.a;
        String str2 = this.g.cp.b;
        int i = this.g.cp.c;
        String str3 = this.g.cp.d;
        int i2 = this.g.cp.e;
        String str4 = this.h;
        String str5 = this.i;
        String str6 = this.j;
        int i3 = this.k;
        int size = j.b.size();
        for (int i4 = 0; i4 < size; i4++) {
            Intent intent = (Intent) j.b.valueAt(i4);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 276 && intent.getStringExtra("fr.smoney.android.izly.extras.activateUserEmail").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.activateUserActivationCode").equals(str2) && intent.getStringExtra("fr.smoney.android.izly.extras.activateUserBirthdate").equals(str3) && intent.getStringExtra("fr.smoney.android.izly.extras.activateUserPassword").equals(str4) && intent.getStringExtra("fr.smoney.android.izly.extras.activateUserSecretQuestion").equals(str5) && intent.getStringExtra("fr.smoney.android.izly.extras.activateUserSecretAnswer").equals(str6)) {
                keyAt = j.b.keyAt(i4);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 276);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.activateUserEmail", str);
        intent2.putExtra("fr.smoney.android.izly.extras.activateUserActivationCode", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.activateUserCivility", i);
        intent2.putExtra("fr.smoney.android.izly.extras.activateUserBirthdate", str3);
        intent2.putExtra("fr.smoney.android.izly.extras.activateUserCountry", i2);
        intent2.putExtra("fr.smoney.android.izly.extras.activateUserPassword", str4);
        intent2.putExtra("fr.smoney.android.izly.extras.activateUserSecretQuestion", str5);
        intent2.putExtra("fr.smoney.android.izly.extras.activateUserSecretAnswer", str6);
        intent2.putExtra("fr.smoney.android.izly.extras.activateUserCgu", i3);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.co = null;
        super.a(keyAt, 276, true);
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (ieVar) {
            case ConnexionErrorType:
                if (h() == 276) {
                    k();
                    return;
                } else {
                    super.a(ieVar, bundle);
                    return;
                }
            default:
                super.a(ieVar, bundle);
                return;
        }
    }

    public final void a_(int i, int i2, int i3, Bundle bundle) {
        if (super.a(i, i2, i3, bundle)) {
            ServerError serverError = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
            if (i2 == 276) {
                a((ActivateUserData) bundle.getParcelable("fr.smoney.android.izly.extras.ActivateUserData"), serverError);
            }
        }
    }

    public final void b_(int i) {
        if (i == 276) {
            a(this.g.cn, this.g.co);
        }
    }

    public void onBackPressed() {
        if (!this.l) {
            super.onBackPressed();
        }
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    protected void onCreate(Bundle bundle) {
        requestWindowFeature(5);
        super.onCreate(bundle);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.d = (UserSubscribingValues) extras.getParcelable("fr.smoney.android.izly.extras.userSubscribingValues");
            this.f = (a) extras.getSerializable("fr.smoney.android.izly.extras.displayCase");
            this.h = extras.getString("fr.smoney.android.izly.userActivationPasswordConfirm");
            this.i = extras.getString("fr.smoney.android.izly.userActivationSecretQuestion");
            this.j = extras.getString("fr.smoney.android.izly.userActivationSecretAnswer");
        }
        this.g = i();
        this.b = new WebView(this);
        this.b.getSettings().setJavaScriptEnabled(true);
        this.c = new b();
        this.b.setWebViewClient(this.c);
        setContentView(this.b);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportInvalidateOptionsMenu();
        this.b.loadUrl(this.g.cl.b);
        this.b.setBackgroundColor(0);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        this.e = menu.add(R.string.menu_item_refresh);
        this.e.setIcon(R.drawable.pict_refresh);
        this.e.setShowAsAction(2);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem == this.e) {
            this.b.reload();
            return true;
        } else if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        } else {
            finish();
            return true;
        }
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        this.e.setVisible(!this.c.a);
        return super.onPrepareOptionsMenu(menu);
    }

    protected void onRestart() {
        super.onRestart();
        this.b.setWebViewClient(this.c);
    }

    protected void onStop() {
        this.b.stopLoading();
        this.b.setWebViewClient(null);
        super.onStop();
    }
}
