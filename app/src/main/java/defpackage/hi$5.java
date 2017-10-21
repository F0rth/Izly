package defpackage;

import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import fr.smoney.android.izly.R;

final class hi$5 extends WebViewClient {
    final /* synthetic */ hi a;

    hi$5(hi hiVar) {
        this.a = hiVar;
    }

    public final void onPageFinished(WebView webView, String str) {
        if (this.a.d != null) {
            hi.c(this.a);
        }
    }

    public final boolean shouldOverrideUrlLoading(WebView webView, String str) {
        if (!str.startsWith(this.a.getString(R.string.bilendi_ad_banner_split))) {
            return false;
        }
        this.a.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str.split(this.a.getString(R.string.bilendi_ad_banner_split))[1])));
        return true;
    }
}
