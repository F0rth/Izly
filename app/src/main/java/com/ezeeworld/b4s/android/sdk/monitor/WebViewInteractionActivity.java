package com.ezeeworld.b4s.android.sdk.monitor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@SuppressLint({"SetJavaScriptEnabled"})
public class WebViewInteractionActivity extends Activity {
    private WebView a;
    private WebChromeClient b = new WebChromeClient(this) {
        final /* synthetic */ WebViewInteractionActivity a;

        {
            this.a = r1;
        }

        public void onProgressChanged(WebView webView, int i) {
            this.a.setProgress(i * 100);
        }
    };
    private WebViewClient c = new WebViewClient(this) {
        final /* synthetic */ WebViewInteractionActivity a;

        {
            this.a = r1;
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            return false;
        }
    };

    public static Intent newStartIntent(Context context, String str) {
        Intent intent = new Intent(context, WebViewInteractionActivity.class);
        intent.putExtra("url", str);
        return intent;
    }

    protected void onCreate(Bundle bundle) {
        requestWindowFeature(1);
        getWindow().requestFeature(2);
        super.onCreate(bundle);
        if (VERSION.SDK_INT < 21) {
            CookieSyncManager.createInstance(this);
            CookieSyncManager.getInstance().startSync();
        }
        this.a = new WebView(this);
        setContentView(this.a);
        if (getIntent().hasExtra("url")) {
            String stringExtra = getIntent().getStringExtra("url");
            CookieManager.getInstance().acceptCookie();
            this.a.getSettings().setJavaScriptEnabled(true);
            this.a.getSettings().setDomStorageEnabled(true);
            this.a.setWebViewClient(this.c);
            this.a.setWebChromeClient(this.b);
            this.a.loadUrl(stringExtra);
            return;
        }
        Log.e(WebViewInteractionActivity.class.getSimpleName(), "No url provided in the Intent extras!");
    }

    protected void onDestroy() {
        super.onDestroy();
        if (VERSION.SDK_INT < 21) {
            CookieSyncManager.getInstance().stopSync();
        }
    }

    public boolean onKeyDown(int i, @NonNull KeyEvent keyEvent) {
        if (i == 4 && this.a.canGoBack()) {
            this.a.goBack();
        } else {
            startActivity(getPackageManager().getLaunchIntentForPackage(getPackageName()));
            finish();
        }
        return true;
    }
}
