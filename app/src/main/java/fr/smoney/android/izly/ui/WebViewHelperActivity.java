package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;

public class WebViewHelperActivity extends SmoneyABSActivity {
    private WebView b;
    private a c;
    private MenuItem d;
    private boolean e = true;

    final class a extends WebViewClient {
        boolean a;
        final /* synthetic */ WebViewHelperActivity b;

        private a(WebViewHelperActivity webViewHelperActivity) {
            this.b = webViewHelperActivity;
            this.a = false;
        }

        public final void onPageFinished(WebView webView, String str) {
            WebViewHelperActivity.a(this.b, false);
            this.a = false;
        }

        public final void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            Log.i("OnPageStarted", "Loading url : " + str);
            if (str == null || str.compareTo("https://mon-espace.izly.fr/") != 0) {
                if (str.indexOf("http://80.11.255.231") != -1) {
                    webView.stopLoading();
                    Uri parse = Uri.parse(str);
                    Intent intent = new Intent();
                    intent.putExtra(MoneyInActivity.b, parse.getQueryParameter("vads_amount"));
                    intent.putExtra(MoneyInActivity.c, parse.getQueryParameter("vads_effective_creation_date"));
                    this.b.setResult(-1, intent);
                    this.b.finish();
                    return;
                }
                WebViewHelperActivity.a(this.b, true);
                this.a = true;
                return;
            }
            this.b.finish();
        }
    }

    static /* synthetic */ void a(WebViewHelperActivity webViewHelperActivity, boolean z) {
        webViewHelperActivity.setSupportProgressBarIndeterminateVisibility(z);
        if (webViewHelperActivity.d != null) {
            webViewHelperActivity.d.setVisible(!z);
        }
    }

    public void onBackPressed() {
        if (this.b.canGoBack()) {
            this.b.goBack();
            return;
        }
        setResult(0);
        finish();
    }

    protected void onCreate(Bundle bundle) {
        requestWindowFeature(5);
        super.onCreate(bundle);
        String stringExtra = getIntent().getStringExtra("webview_url");
        if (stringExtra == null) {
            finish();
        }
        int intExtra = getIntent().getIntExtra("title_tag", 2131230841);
        this.e = getIntent().getBooleanExtra("should_go_back", true);
        this.b = new WebView(this);
        this.b.getSettings().setJavaScriptEnabled(true);
        this.c = new a();
        this.b.setWebViewClient(this.c);
        setContentView(this.b);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setTitle(intExtra);
        supportInvalidateOptionsMenu();
        CookieManager.getInstance().removeAllCookie();
        this.b.clearCache(true);
        this.b.loadUrl(stringExtra);
        this.b.setBackgroundColor(0);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        this.d = menu.add(R.string.menu_item_refresh);
        this.d.setIcon(R.drawable.pict_refresh);
        this.d.setShowAsAction(2);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem == this.d) {
            this.b.reload();
            return true;
        } else if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        } else {
            if (this.b.canGoBack() && this.e) {
                this.b.goBack();
                return true;
            }
            setResult(0);
            finish();
            return true;
        }
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        this.d.setVisible(!this.c.a);
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
