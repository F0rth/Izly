package fr.smoney.android.izly.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.smoney.android.izly.R;

public class WebViewActivity extends AppCompatActivity {
    private String a;
    private b b;
    private a c;
    @Bind({2131755265})
    View mLoader;
    @Bind({2131755238})
    Toolbar mToolbar;
    @Bind({2131755285})
    WebView mWebView;

    public enum a {
        BOOK_TICKET,
        SOCIAL_URL
    }

    public final class b extends WebViewClient {
        final /* synthetic */ WebViewActivity a;

        public b(WebViewActivity webViewActivity) {
            this.a = webViewActivity;
        }

        public final void onPageFinished(WebView webView, String str) {
            if (!this.a.isFinishing()) {
                super.onPageFinished(webView, str);
                WebViewActivity.a(this.a, false);
            }
        }

        public final void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            if (!this.a.isFinishing()) {
                if (this.a.c == a.SOCIAL_URL && this.a.getSupportActionBar() != null) {
                    this.a.getSupportActionBar().setTitle(str);
                }
                WebViewActivity.a(this.a, true);
            }
        }

        public final boolean shouldOverrideUrlLoading(WebView webView, String str) {
            webView.loadUrl(str);
            return true;
        }
    }

    public static Intent a(Context context, String str, a aVar) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("ARG_URL", str);
        intent.putExtra("ARG_MODE", aVar);
        return intent;
    }

    static /* synthetic */ void a(WebViewActivity webViewActivity, boolean z) {
        if (z) {
            if (!webViewActivity.mLoader.isShown()) {
                webViewActivity.mLoader.setVisibility(0);
                if (webViewActivity.mLoader.getBackground() instanceof AnimationDrawable) {
                    ((AnimationDrawable) webViewActivity.mLoader.getBackground()).start();
                }
            }
        } else if (webViewActivity.mLoader.isShown()) {
            webViewActivity.mLoader.setVisibility(8);
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("ARG_URL") && intent.hasExtra("ARG_MODE")) {
            this.a = intent.getStringExtra("ARG_URL");
            this.c = (a) intent.getSerializableExtra("ARG_MODE");
        }
        setSupportActionBar(this.mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (this.c == a.BOOK_TICKET) {
            getSupportActionBar().setTitle(R.string.wingit_tickets_label_navbar_title);
        }
        WebSettings settings = this.mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(2);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        CookieManager.getInstance().removeAllCookie();
        this.mWebView.setWebViewClient(new WebViewClient(this) {
            final /* synthetic */ WebViewActivity a;

            {
                this.a = r1;
            }

            public final void onReceivedError(WebView webView, int i, String str, String str2) {
                super.onReceivedError(webView, i, str, str2);
                if (!this.a.isFinishing()) {
                    Toast.makeText(this.a, R.string.wingit_errors_message_could_not_launch_url, 0).show();
                    this.a.finish();
                }
            }
        });
        this.b = new b(this);
        this.mWebView.setWebViewClient(this.b);
        if (this.a == null || this.a.isEmpty()) {
            Toast.makeText(this, R.string.wingit_errors_message_could_not_launch_url, 0).show();
            finish();
            return;
        }
        this.mWebView.loadUrl(this.a);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }
}
