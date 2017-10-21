package com.ad4screen.sdk;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.webkit.HttpAuthHandler;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ad4screen.sdk.A4S.Callback;
import com.ad4screen.sdk.analytics.Cart;
import com.ad4screen.sdk.analytics.Lead;
import com.ad4screen.sdk.analytics.Purchase;
import com.ad4screen.sdk.common.annotations.API;
import com.ad4screen.sdk.common.b;
import com.ad4screen.sdk.common.b.m.l;
import com.ad4screen.sdk.common.e;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.service.modules.k.g.d;

@API
@SuppressLint({"SetJavaScriptEnabled"})
public class A4SWebView extends WebView {
    private WebViewClient a;
    private Callback<Void> b;

    @API
    class A4SJSInterface {
        final /* synthetic */ A4SWebView a;

        private A4SJSInterface(A4SWebView a4SWebView) {
            this.a = a4SWebView;
        }

        @JavascriptInterface
        public void setView(String str) {
            A4S.get(this.a.getContext()).setView(str);
        }

        @JavascriptInterface
        public void trackAddToCart(String str) {
            Cart b = b.b(str);
            Log.debug("A4SWebView|Tracking Cart event WebView with id " + b.getId());
            A4S.get(this.a.getContext()).trackAddToCart(b);
        }

        @JavascriptInterface
        public void trackEvent(int i, String str) {
            switch (i) {
                case 10:
                    trackLead(str);
                    return;
                case 30:
                    trackAddToCart(str);
                    return;
                case 50:
                    trackPurchase(str);
                    return;
                default:
                    Log.debug("A4SWebView|Tracking event WebView with id " + i);
                    A4S.get(this.a.getContext()).trackEvent((long) i, str, new String[0]);
                    return;
            }
        }

        @JavascriptInterface
        public void trackLead(String str) {
            Lead a = b.a(str);
            Log.debug("A4SWebView|Tracking Lead event WebView with label " + a.getLabel() + " and value " + a.getValue());
            A4S.get(this.a.getContext()).trackLead(a);
        }

        @JavascriptInterface
        public void trackPurchase(String str) {
            Purchase c = b.c(str);
            Log.debug("A4SWebView|Tracking Purchase event WebView with id " + c.getId());
            A4S.get(this.a.getContext()).trackPurchase(c);
        }

        @JavascriptInterface
        public void updateDeviceInfo(String str) {
            A4S.get(this.a.getContext()).updateDeviceInfo(b.d(str));
        }
    }

    class a extends WebViewClient {
        final /* synthetic */ A4SWebView a;

        private a(A4SWebView a4SWebView) {
            this.a = a4SWebView;
        }

        private void a(String str) {
            d.a(str).a(new com.ad4screen.sdk.service.modules.k.g.a(A4S.get(this.a.getContext()), this.a.b));
        }

        public void doUpdateVisitedHistory(WebView webView, String str, boolean z) {
            if (this.a.a != null) {
                this.a.a.doUpdateVisitedHistory(webView, str, z);
            } else {
                super.doUpdateVisitedHistory(webView, str, z);
            }
        }

        public void onFormResubmission(WebView webView, Message message, Message message2) {
            if (this.a.a != null) {
                this.a.a.onFormResubmission(webView, message, message2);
            } else {
                super.onFormResubmission(webView, message, message2);
            }
        }

        public void onLoadResource(WebView webView, String str) {
            if (this.a.a != null) {
                this.a.a.onLoadResource(webView, str);
            } else {
                super.onLoadResource(webView, str);
            }
        }

        public void onPageFinished(WebView webView, String str) {
            com.ad4screen.sdk.d.b a = com.ad4screen.sdk.d.b.a(this.a.getContext());
            String N = a.N();
            if (TextUtils.isEmpty(N)) {
                N = com.ad4screen.sdk.d.d.a(this.a.getContext()).a(com.ad4screen.sdk.d.d.b.WebviewScriptWebservice);
            }
            this.a.loadUrl("javascript:(function() {var ad4sInsertScript=function(u){var b=document,c=b.getElementsByTagName('head')[0],e=b.createElement('script');e.text='var A4SPartnerID=\"" + a.l() + "\"';d=b.createElement('script');d.setAttribute('src',u);c.appendChild(e);c.appendChild(d);};ad4sInsertScript('" + h.a(this.a.getContext(), N, new e("partnerId", Uri.encode(a.l())), new e("sharedId", Uri.encode(a.c()))) + "')})()");
            if (this.a.a != null) {
                this.a.a.onPageFinished(webView, str);
            } else {
                super.onPageFinished(webView, str);
            }
        }

        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            if (this.a.a != null) {
                this.a.a.onPageStarted(webView, str, bitmap);
            } else {
                super.onPageStarted(webView, str, bitmap);
            }
        }

        public void onReceivedError(WebView webView, int i, String str, String str2) {
            if (this.a.a != null) {
                this.a.a.onReceivedError(webView, i, str, str2);
            } else {
                super.onReceivedError(webView, i, str, str2);
            }
        }

        public void onReceivedHttpAuthRequest(WebView webView, HttpAuthHandler httpAuthHandler, String str, String str2) {
            if (this.a.a != null) {
                this.a.a.onReceivedHttpAuthRequest(webView, httpAuthHandler, str, str2);
            } else {
                super.onReceivedHttpAuthRequest(webView, httpAuthHandler, str, str2);
            }
        }

        @TargetApi(12)
        public void onReceivedLoginRequest(WebView webView, String str, String str2, String str3) {
            if (this.a.a != null) {
                this.a.a.onReceivedLoginRequest(webView, str, str2, str3);
            } else {
                super.onReceivedLoginRequest(webView, str, str2, str3);
            }
        }

        @TargetApi(8)
        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            if (this.a.a != null) {
                this.a.a.onReceivedSslError(webView, sslErrorHandler, sslError);
            } else {
                super.onReceivedSslError(webView, sslErrorHandler, sslError);
            }
        }

        public void onScaleChanged(WebView webView, float f, float f2) {
            if (this.a.a != null) {
                this.a.a.onScaleChanged(webView, f, f2);
            } else {
                super.onScaleChanged(webView, f, f2);
            }
        }

        public void onTooManyRedirects(WebView webView, Message message, Message message2) {
            if (this.a.a != null) {
                this.a.a.onTooManyRedirects(webView, message, message2);
            } else {
                super.onTooManyRedirects(webView, message, message2);
            }
        }

        public void onUnhandledKeyEvent(WebView webView, KeyEvent keyEvent) {
            if (this.a.a != null) {
                this.a.a.onUnhandledKeyEvent(webView, keyEvent);
            } else {
                super.onUnhandledKeyEvent(webView, keyEvent);
            }
        }

        @TargetApi(11)
        public WebResourceResponse shouldInterceptRequest(WebView webView, String str) {
            return this.a.a != null ? this.a.a.shouldInterceptRequest(webView, str) : super.shouldInterceptRequest(webView, str);
        }

        public boolean shouldOverrideKeyEvent(WebView webView, KeyEvent keyEvent) {
            return this.a.a != null ? this.a.a.shouldOverrideKeyEvent(webView, keyEvent) : super.shouldOverrideKeyEvent(webView, keyEvent);
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            a(str);
            return this.a.a != null ? this.a.a.shouldOverrideUrlLoading(webView, str) : super.shouldOverrideUrlLoading(webView, str);
        }
    }

    public A4SWebView(Context context) {
        super(context);
        a();
    }

    public A4SWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a();
    }

    public A4SWebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a();
    }

    private void a() {
        if (!isInEditMode()) {
            l.d(this, true);
            l.a(this, true);
            addJavascriptInterface(new A4SJSInterface(), "A4S");
            super.setWebViewClient(new a());
        }
    }

    public void setCloseUrlActionCallback(Callback<Void> callback) {
        this.b = callback;
    }

    public void setWebViewClient(WebViewClient webViewClient) {
        this.a = webViewClient;
    }
}
