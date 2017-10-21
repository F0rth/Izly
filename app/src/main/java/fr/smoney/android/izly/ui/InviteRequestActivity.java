package fr.smoney.android.izly.ui;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import defpackage.is;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;

public class InviteRequestActivity extends SmoneyABSActivity {
    private WebView b;
    private MenuItem c;
    private a d;

    final class a extends WebViewClient {
        boolean a;
        final /* synthetic */ InviteRequestActivity b;

        private a(InviteRequestActivity inviteRequestActivity) {
            this.b = inviteRequestActivity;
            this.a = false;
        }

        public final void onPageFinished(WebView webView, String str) {
            InviteRequestActivity.a(this.b, false);
            this.a = false;
        }

        public final void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            InviteRequestActivity.a(this.b, true);
            this.a = true;
        }

        public final boolean shouldOverrideUrlLoading(WebView webView, String str) {
            if (str.equals("https://mon-espace.izly.fr/Home/Enrollment")) {
                this.b.startActivity(is.a(this.b, SubscribeActivity.class));
                this.b.finish();
                return true;
            } else if (!str.equals("https://mon-espace.izly.fr/Home/Logon")) {
                return false;
            } else {
                this.b.finish();
                return true;
            }
        }
    }

    static /* synthetic */ void a(InviteRequestActivity inviteRequestActivity, boolean z) {
        inviteRequestActivity.setSupportProgressBarIndeterminateVisibility(z);
        if (inviteRequestActivity.c != null) {
            inviteRequestActivity.c.setVisible(!z);
        }
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    protected void onCreate(Bundle bundle) {
        requestWindowFeature(5);
        super.onCreate(bundle);
        this.b = new WebView(this);
        this.b.getSettings().setJavaScriptEnabled(true);
        this.d = new a();
        this.b.setWebViewClient(this.d);
        setContentView(this.b);
        getSupportActionBar().setHomeButtonEnabled(false);
        supportInvalidateOptionsMenu();
        this.b.loadUrl("https://mon-espace.izly.fr/Home/InvitationRequest");
        this.b.setBackgroundColor(0);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        this.c = menu.add(R.string.menu_item_refresh);
        this.c.setIcon(R.drawable.pict_refresh);
        this.c.setShowAsAction(2);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem != this.c) {
            return super.onOptionsItemSelected(menuItem);
        }
        this.b.reload();
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        this.c.setVisible(!this.d.a);
        return super.onPrepareOptionsMenu(menu);
    }

    protected void onRestart() {
        super.onRestart();
        this.b.setWebViewClient(this.d);
    }

    protected void onStop() {
        this.b.stopLoading();
        this.b.setWebViewClient(null);
        super.onStop();
    }
}
