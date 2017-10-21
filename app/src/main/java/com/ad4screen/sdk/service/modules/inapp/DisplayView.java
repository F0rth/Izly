package com.ad4screen.sdk.service.modules.inapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.ad4screen.sdk.A4S;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.R;
import com.ad4screen.sdk.common.b.m.l;
import com.ad4screen.sdk.common.e;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.common.i;
import com.ad4screen.sdk.service.modules.k.g.c;
import com.ad4screen.sdk.service.modules.k.g.d;

public class DisplayView extends FrameLayout {
    private com.ad4screen.sdk.c.a.b a;
    private b b;
    private WebView c;
    private ToggleButton d;
    private Button e;
    private ProgressBar f;
    private com.ad4screen.sdk.c.a.a g;
    private Button h;
    private Button i;
    private View j;
    private int k = -1;
    private int l;
    private View m;
    private boolean n;
    private final OnClickListener o = new OnClickListener(this) {
        final /* synthetic */ DisplayView a;

        {
            this.a = r1;
        }

        public void onClick(View view) {
            if (this.a.c != null) {
                if (this.a.d.isChecked()) {
                    this.a.c.reload();
                } else {
                    this.a.c.stopLoading();
                }
            }
        }
    };
    private final OnClickListener p = new OnClickListener(this) {
        final /* synthetic */ DisplayView a;

        {
            this.a = r1;
        }

        public void onClick(View view) {
            this.a.r();
        }
    };
    private final OnClickListener q = new OnClickListener(this) {
        final /* synthetic */ DisplayView a;

        {
            this.a = r1;
        }

        public void onClick(View view) {
            if (this.a.b != null) {
                this.a.b.d(this.a);
            }
        }
    };
    private final OnClickListener r = new OnClickListener(this) {
        final /* synthetic */ DisplayView a;

        {
            this.a = r1;
        }

        public void onClick(View view) {
            if (this.a.c != null) {
                if (this.a.c.canGoBack()) {
                    this.a.c.goBack();
                }
                this.a.q();
            }
        }
    };
    private final OnClickListener s = new OnClickListener(this) {
        final /* synthetic */ DisplayView a;

        {
            this.a = r1;
        }

        public void onClick(View view) {
            if (this.a.c != null) {
                if (this.a.c.canGoForward()) {
                    this.a.c.goForward();
                }
                this.a.q();
            }
        }
    };

    public interface b {
        void a(DisplayView displayView);

        void b(DisplayView displayView);

        void c(DisplayView displayView);

        void d(DisplayView displayView);

        void e(DisplayView displayView);
    }

    @SuppressLint({"ClickableViewAccessibility"})
    class a extends FrameLayout {
        final /* synthetic */ DisplayView a;

        public a(DisplayView displayView, Context context) {
            this.a = displayView;
            super(context);
        }

        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            return motionEvent.getAction() == 0;
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            if (motionEvent.getAction() != 0) {
                return false;
            }
            if (this.a.b != null) {
                this.a.b.c(this.a);
            }
            return true;
        }
    }

    public DisplayView(Context context) {
        super(context);
        j();
    }

    public DisplayView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        j();
    }

    public DisplayView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        j();
    }

    private <V> V a(Class<V> cls, int i) {
        View findViewById = this.m.findViewById(i);
        return cls.isInstance(findViewById) ? cls.cast(findViewById) : null;
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private void a(WebView webView) {
        WebSettings settings = webView.getSettings();
        l.d(webView, true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        settings.setUseWideViewPort(true);
        l.b(webView, false);
        l.c(webView, true);
        l.a(webView, true);
        webView.setScrollBarStyle(33554432);
        l.a(webView);
        webView.setWebViewClient(new WebViewClient(this) {
            public boolean a = false;
            final /* synthetic */ DisplayView b;
            private boolean c;
            private int d = 0;

            {
                this.b = r2;
            }

            private void a(String str) {
                d.a(str).a(new c(A4S.get(this.b.getContext()), this.b.b, this.b));
            }

            public void doUpdateVisitedHistory(WebView webView, String str, boolean z) {
                this.b.q();
            }

            public void onPageFinished(WebView webView, String str) {
                if (!this.a) {
                    int i = this.d - 1;
                    this.d = i;
                    if (i == 0) {
                        this.a = true;
                    } else {
                        return;
                    }
                }
                Drawable background = webView.getBackground();
                if ((background instanceof ColorDrawable) && ((ColorDrawable) background).getAlpha() == 0) {
                    webView.setBackgroundColor(0);
                }
                if (this.b.f != null) {
                    this.b.f.setVisibility(8);
                }
                if (this.b.d != null) {
                    this.b.d.setChecked(false);
                }
                if (!this.c) {
                    this.b.p();
                } else if ("file:///android_asset/inAppEmptyPage.html".equalsIgnoreCase(str)) {
                    this.c = false;
                } else if (this.b.b != null) {
                    this.b.b.e(this.b);
                }
            }

            public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
                this.b.q();
                this.d = Math.max(this.d, 1);
                if (this.b.f != null) {
                    this.b.f.setVisibility(0);
                }
                if (this.b.d != null) {
                    this.b.d.setChecked(true);
                }
            }

            public void onReceivedError(WebView webView, int i, String str, String str2) {
                this.c = true;
            }

            public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                a(str);
                this.d++;
                if (str.startsWith("http")) {
                    webView.loadUrl(str);
                } else {
                    try {
                        this.b.getContext().startActivity(Intent.parseUri(str, 1));
                    } catch (Throwable e) {
                        Log.error("Error while browsing url : " + str, e);
                    }
                }
                return true;
            }
        });
    }

    private void a(View... viewArr) {
        int i = 0;
        while (i < viewArr.length) {
            if (!(viewArr[i] == null || viewArr[i].getVisibility() == 8)) {
                viewArr[i].setVisibility(8);
            }
            i++;
        }
    }

    private void j() {
        if (getVisibility() != 8) {
            setVisibility(8);
        }
    }

    private void k() {
        o();
        e();
        if (getVisibility() != 8) {
            setVisibility(8);
        }
    }

    private void l() {
        this.f = (ProgressBar) a(ProgressBar.class, R.id.com_ad4screen_sdk_progress);
        this.c = (WebView) a(WebView.class, R.id.com_ad4screen_sdk_webview);
        this.d = (ToggleButton) a(ToggleButton.class, R.id.com_ad4screen_sdk_reloadbutton);
        this.h = (Button) a(Button.class, R.id.com_ad4screen_sdk_backbutton);
        this.i = (Button) a(Button.class, R.id.com_ad4screen_sdk_forwardbutton);
        this.e = (Button) a(Button.class, R.id.com_ad4screen_sdk_closebutton);
        Button button = (Button) a(Button.class, R.id.com_ad4screen_sdk_browsebutton);
        TextView textView = (TextView) a(TextView.class, R.id.com_ad4screen_sdk_title);
        TextView textView2 = (TextView) a(TextView.class, R.id.com_ad4screen_sdk_body);
        ImageView imageView = (ImageView) a(ImageView.class, R.id.com_ad4screen_sdk_logo);
        if (this.d != null) {
            this.d.setChecked(false);
            this.d.setOnClickListener(this.o);
        }
        if (this.h != null) {
            this.h.setEnabled(false);
            this.h.setOnClickListener(this.r);
        }
        if (this.i != null) {
            this.i.setEnabled(false);
            this.i.setOnClickListener(this.s);
        }
        if (button != null) {
            button.setOnClickListener(this.q);
        }
        if (this.c == null || this.a.c == null) {
            a(this.f, this.d, this.h, this.i, button);
        } else {
            a(this.c);
            this.c.setVisibility(0);
            this.c.loadUrl(h.a(getContext(), this.a.c, new e[0]));
        }
        if (textView != null) {
            if (this.a.a == null) {
                textView.setText(i.e(getContext()));
            } else {
                textView.setText(this.a.a);
            }
            textView.setVisibility(0);
        }
        if (!(textView2 == null || this.a.b == null)) {
            textView2.setText(this.a.b);
            textView2.setVisibility(0);
        }
        if (imageView != null) {
            imageView.setImageDrawable(getResources().getDrawable(i.d(getContext())));
        }
        if (this.e != null) {
            this.e.setOnClickListener(this.p);
        }
    }

    private void m() {
        LayoutParams layoutParams = this.m.getLayoutParams();
        LayoutParams layoutParams2 = getLayoutParams();
        layoutParams2.width = layoutParams.width;
        layoutParams2.height = layoutParams.height;
        setLayoutParams(layoutParams2);
        if ((this.l & 2) != 0) {
            n();
        }
        if ((this.l & 1) != 0) {
            View aVar = new a(this, getContext());
            aVar.setLayoutParams(new FrameLayout.LayoutParams(layoutParams.width, layoutParams.height));
            aVar.addView(this.m);
            addView(aVar);
            if (this.e != null) {
                ViewParent parent = this.e.getParent();
                if (parent instanceof ViewGroup) {
                    ((ViewGroup) parent).removeView(this.e);
                }
                int a = i.a(getContext(), 33);
                addView(this.e, new FrameLayout.LayoutParams(a, a, 53));
                return;
            }
            return;
        }
        addView(this.m);
    }

    private void n() {
        if (getVisibility() != 0) {
            setVisibility(0);
            if (this.a != null && this.a.e != null) {
                com.ad4screen.sdk.common.b.m.b.a(getContext(), this, this.a.e, null);
            }
        }
    }

    private void o() {
        f();
        removeAllViews();
        this.n = false;
        this.a = null;
    }

    private void p() {
        n();
        if (this.b != null && !this.n) {
            this.n = true;
            this.b.a(this);
        }
    }

    private void q() {
        if (this.c != null) {
            if (this.h != null) {
                this.h.setEnabled(this.c.canGoBack());
            }
            if (this.i != null) {
                this.i.setEnabled(this.c.canGoForward());
            }
        }
    }

    private void r() {
        if (this.b != null) {
            this.b.b(this);
        }
    }

    public void a(View view) {
        this.j = view;
    }

    public void a(com.ad4screen.sdk.c.a.a aVar) {
        this.g = aVar;
    }

    public void a(com.ad4screen.sdk.c.a.b bVar, int i) {
        int i2 = 0;
        o();
        if (bVar == null) {
            Log.debug("DisplayView|No banner provided, skipping display");
            return;
        }
        this.a = bVar;
        if (this.a.d != null) {
            i2 = getResources().getIdentifier(this.a.d, "layout", getContext().getPackageName());
        }
        if (i2 == 0) {
            Log.warn("DisplayView|Wrong banner template provided : " + this.a.d + " using default");
            if (this.b != null) {
                this.b.e(this);
                return;
            }
            return;
        }
        try {
            this.m = LayoutInflater.from(getContext()).inflate(i2, this, false);
            l();
            this.l = i;
            if (this.c == null && (this.l & 1) == 0) {
                this.l = i | 1;
            }
            m();
            if (this.c == null) {
                p();
            }
        } catch (Throwable e) {
            Log.error("DisplayView|A fatal error occured when inflating template " + this.a.d + " or default template. Aborting display..", e);
            if (this.b != null) {
                this.b.e(this);
            }
        }
    }

    public void a(b bVar) {
        this.b = bVar;
    }

    public boolean a() {
        return this.a == null || this.a.c == null || !this.a.c.contains("|o|");
    }

    public boolean b() {
        return this.c != null ? this.c.canGoBack() : false;
    }

    public void c() {
        this.c.goBack();
    }

    public void d() {
        com.ad4screen.sdk.common.b.m.b.a anonymousClass1 = new com.ad4screen.sdk.common.b.m.b.a(this) {
            final /* synthetic */ DisplayView a;

            {
                this.a = r1;
            }

            public void a() {
            }

            public void b() {
                this.a.k();
            }
        };
        if (this.a == null || this.a.f == null || !com.ad4screen.sdk.common.b.m.b.a(getContext(), this, this.a.f, anonymousClass1)) {
            k();
        }
        this.b = null;
        this.j = null;
        this.k = -1;
    }

    public void e() {
        if (this.c != null) {
            this.c.loadUrl("file:///android_asset/inAppEmptyPage.html");
        }
    }

    public void f() {
        if (this.c != null) {
            this.c.stopLoading();
        }
    }

    public com.ad4screen.sdk.c.a.a g() {
        return this.g;
    }

    public int getLayout() {
        return this.k;
    }

    public View h() {
        return this.j;
    }

    public WebView i() {
        return this.c;
    }

    public void setLayout(int i) {
        this.k = i;
    }
}
