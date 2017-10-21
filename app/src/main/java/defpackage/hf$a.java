package defpackage;

import android.webkit.WebView;
import android.webkit.WebViewClient;

final class hf$a extends WebViewClient {
    final /* synthetic */ hf a;

    private hf$a(hf hfVar) {
        this.a = hfVar;
    }

    public final void onPageFinished(WebView webView, String str) {
        this.a.f.setVisibility(8);
    }
}
