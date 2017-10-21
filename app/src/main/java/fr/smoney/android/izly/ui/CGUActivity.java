package fr.smoney.android.izly.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import defpackage.is;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.LoginData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.model.UserSubscribingValues;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;

public class CGUActivity extends SmoneyABSActivity implements SmoneyRequestManager$a {
    private WebView b;
    private b c;
    private UserSubscribingValues d;
    private MenuItem e;
    private a f;
    private cl g;

    public enum a {
        CGU_FOR_SUBSCRIBE,
        CGU_CHANGED
    }

    final class b extends WebViewClient {
        boolean a;
        final /* synthetic */ CGUActivity b;

        private b(CGUActivity cGUActivity) {
            this.b = cGUActivity;
            this.a = false;
        }

        public final void onPageFinished(WebView webView, String str) {
            CGUActivity.a(this.b, false);
            this.a = false;
        }

        public final void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            CGUActivity.a(this.b, true);
            this.a = true;
        }

        public final boolean shouldOverrideUrlLoading(WebView webView, String str) {
            String lastPathSegment = Uri.parse(str).getLastPathSegment();
            if (lastPathSegment.equals("app_cgu_accept")) {
                if (this.b.f == a.CGU_FOR_SUBSCRIBE) {
                    this.b.setResult(-1);
                    CGUActivity.a(this.b, this.b.d);
                    this.b.finish();
                    return true;
                }
                CGUActivity.c(this.b);
                return true;
            } else if (!lastPathSegment.equals("app_cgu_decline")) {
                return false;
            } else {
                this.b.setResult(2);
                this.b.finish();
                return true;
            }
        }
    }

    static /* synthetic */ void a(CGUActivity cGUActivity, UserSubscribingValues userSubscribingValues) {
        Intent a = is.a(cGUActivity, SubscribeConfirmActivity.class);
        a.putExtra("fr.smoney.android.izly.extras.userSubscribingValues", userSubscribingValues);
        cGUActivity.startActivity(a);
    }

    static /* synthetic */ void a(CGUActivity cGUActivity, boolean z) {
        cGUActivity.setSupportProgressBarIndeterminateVisibility(z);
        if (cGUActivity.e != null) {
            cGUActivity.e.setVisible(!z);
        }
    }

    private void b(ServerError serverError) {
        if (serverError != null) {
            a(serverError);
            return;
        }
        setResult(-1);
        finish();
    }

    static /* synthetic */ void c(CGUActivity cGUActivity) {
        int keyAt;
        LoginData loginData = cGUActivity.i().b;
        SmoneyRequestManager j = cGUActivity.j();
        String str = loginData.a;
        String str2 = loginData.c;
        int size = j.b.size();
        for (int i = 0; i < size; i++) {
            Intent intent = (Intent) j.b.valueAt(i);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 233 && intent.getStringExtra("fr.smoney.android.izly.extras.acceptCGUUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.acceptCGUSessionId").equals(str2)) {
                keyAt = j.b.keyAt(i);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 233);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.acceptCGUUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.acceptCGUSessionId", str2);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.e = null;
        super.a(keyAt, 233, true);
    }

    public final void a_(int i, int i2, int i3, Bundle bundle) {
        if (super.a(i, i2, i3, bundle)) {
            ServerError serverError = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
            if (i2 == 233) {
                b(serverError);
            }
        }
    }

    public final void b_(int i) {
        cl i2 = i();
        if (i == 233) {
            b(i2.e);
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
        }
        this.g = i();
        this.b = new WebView(this);
        this.b.getSettings().setJavaScriptEnabled(true);
        this.c = new b();
        this.b.setWebViewClient(this.c);
        setContentView(this.b);
        getSupportActionBar().setHomeButtonEnabled(false);
        supportInvalidateOptionsMenu();
        String str = (this.g.b == null || this.g.b.a == null) ? "https://mon-espace.izly.fr/home/cguembedded?user=" : "https://mon-espace.izly.fr/home/cguembedded?user=" + i().b.a;
        this.b.loadUrl(str);
        this.b.setBackgroundColor(0);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        this.e = menu.add(R.string.menu_item_refresh);
        this.e.setIcon(R.drawable.pict_refresh);
        this.e.setShowAsAction(2);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem != this.e) {
            return super.onOptionsItemSelected(menuItem);
        }
        this.b.reload();
        return true;
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
