package com.ad4screen.sdk.common.b;

import android.annotation.TargetApi;
import android.webkit.WebView;

@TargetApi(7)
public final class j {
    public static String a() {
        return System.getProperty("http.agent");
    }

    public static void a(WebView webView, boolean z) {
        webView.getSettings().setDomStorageEnabled(z);
    }

    public static void b(WebView webView, boolean z) {
        webView.getSettings().setLoadWithOverviewMode(z);
    }
}
