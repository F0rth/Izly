package com.ad4screen.sdk;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

import com.ad4screen.sdk.a.c.c;
import com.ad4screen.sdk.c.a.d;
import com.ad4screen.sdk.c.a.e.a;
import com.ad4screen.sdk.common.annotations.API;
import com.ad4screen.sdk.common.c.e;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.d.f;
import com.ad4screen.sdk.service.modules.inapp.DisplayView;
import com.ad4screen.sdk.service.modules.inapp.DisplayView.b;
import com.ad4screen.sdk.service.modules.inapp.g;

@API
public class A4SInterstitial extends Activity {
    public static final int FLAG_INAPP = 1;
    public static final int FLAG_PUSH = 2;
    private static e a = new e();
    private DisplayView b;
    private boolean c;
    private String d;
    private com.ad4screen.sdk.c.a.e e;
    private int f;
    private Bundle g;

    private boolean a() {
        return (this.f & 1) != 0;
    }

    private boolean b() {
        return (this.f & 2) != 0;
    }

    public static Intent build(Context context, int i, com.ad4screen.sdk.c.a.e eVar) {
        return build(context, i, eVar, null);
    }

    public static Intent build(Context context, int i, com.ad4screen.sdk.c.a.e eVar, Bundle bundle) {
        Intent intent = new Intent(context, A4SInterstitial.class);
        intent.putExtra("com.ad4screen.sdk.A4SIntersticial.flags", i);
        if (bundle != null) {
            intent.putExtra("com.ad4screen.sdk.A4SIntersticial.payload", bundle);
        }
        try {
            String jSONObject = a.a(eVar).toString();
            if (jSONObject != null) {
                intent.putExtra("com.ad4screen.sdk.A4SIntersticial.format", jSONObject);
            } else {
                Log.error("A4SInterstitial|Could not serialize Interstitial as JSON");
            }
        } catch (Throwable e) {
            Log.internal("A4SInterstitial|Error while serializing Format", e);
        }
        return intent;
    }

    private void c() {
        if (!this.c) {
            this.c = true;
            if (a()) {
                f.a().a(new com.ad4screen.sdk.service.modules.inapp.e.f(this.e.h, -1, "com_ad4screen_sdk_theme_interstitial", null, this.e.o));
            }
        }
    }

    private void d() {
        View frameLayout = new FrameLayout(this);
        if (this.b != null) {
            this.b.e();
        }
        this.b = new DisplayView(this);
        setContentView(frameLayout, new LayoutParams(-1, -1));
        frameLayout.addView(this.b, new LayoutParams(-1, -1));
        this.b.a(this.e.a, 2);
        this.b.a(new b(this) {
            final /* synthetic */ A4SInterstitial a;

            {
                this.a = r1;
            }

            public void a(DisplayView displayView) {
            }

            public void b(DisplayView displayView) {
                if (!this.a.isFinishing()) {
                    this.a.finish();
                }
            }

            public void c(DisplayView displayView) {
            }

            public void d(DisplayView displayView) {
                if (this.a.e != null && this.a.e.a != null && this.a.e.a.c != null && this.a.e.a.c.length() != 0) {
                    try {
                        this.a.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(h.a(displayView.getContext(), this.a.e.a.c, new com.ad4screen.sdk.common.e[0]))));
                    } catch (Throwable e) {
                        Log.error("Failed to load interstitial url : '" + this.a.e.a.c + "'", e);
                    }
                }
            }

            public void e(DisplayView displayView) {
                Log.error("Failed to load interstitial url : '" + this.a.e.a.c + "'");
                if (!this.a.isFinishing()) {
                    this.a.finish();
                }
            }
        });
    }

    @TargetApi(5)
    public void onBackPressed() {
        if (this.b.b()) {
            this.b.c();
        } else if (!isFinishing()) {
            finish();
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        if (!this.b.a()) {
            Bundle bundle = new Bundle();
            onSaveInstanceState(bundle);
            onCreate(bundle);
        }
        super.onConfigurationChanged(configuration);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle == null) {
            this.c = false;
            this.d = null;
        } else {
            this.c = bundle.getBoolean("com.ad4screen.sdk.A4SIntersticial.displayTracked", false);
            this.d = bundle.getString("com.ad4screen.sdk.A4SIntersticial.savedUrl");
        }
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            Log.warn("A4SInterstitial|No extras found");
            if (!isFinishing()) {
                finish();
                return;
            }
            return;
        }
        this.f = extras.getInt("com.ad4screen.sdk.A4SIntersticial.flags", 0);
        Bundle bundle2 = extras.getBundle("com.ad4screen.sdk.A4SIntersticial.payload");
        if (bundle2 != null) {
            this.g = bundle2.getBundle(Constants.EXTRA_GCM_PAYLOAD);
        }
        String string = extras.getString("com.ad4screen.sdk.A4SIntersticial.format");
        if (string == null) {
            Log.warn("A4SInterstitial|No format found");
            if (!isFinishing()) {
                finish();
                return;
            }
            return;
        }
        try {
            this.e = (com.ad4screen.sdk.c.a.e) a.a(string, new d());
        } catch (Throwable e) {
            Log.internal("A4SInterstitial|Error while deserializing LandingPage", e);
        }
        if (this.e == null) {
            Log.warn("A4SInterstitial|Invalid Interstitial format found");
            if (!isFinishing()) {
                finish();
            }
        } else if (this.e.a.b == null && this.e.a.c == null) {
            Log.debug("A4SInterstitial|Interstitial should have content to be displayed properly");
            if (!isFinishing()) {
                finish();
            }
        } else if (this.e.b == a.System && this.e.a.c == null) {
            Log.debug("A4SInterstitial|We can't open system intent without intent uri. Please specify an uri for this notification");
            if (!isFinishing()) {
                finish();
            }
        } else if (this.e.o && a()) {
            g.a(this.c, this.e);
            Log.warn("A4SInterstitial|Interstitial with id #" + this.e.h + " has not been displayed since you're in control group");
            if (!isFinishing()) {
                finish();
            }
        } else {
            c();
            if (this.e.b == a.System) {
                try {
                    startActivity(Intent.parseUri(this.e.a.c, 1));
                } catch (Throwable e2) {
                    Log.warn("A4SInterstitial|Error while opening interstitial with url: " + this.e.a.c, e2);
                }
                if (!isFinishing()) {
                    finish();
                    return;
                }
                return;
            }
            d();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.b != null) {
            this.b.d();
        }
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return false;
        }
        if (this.b.b()) {
            this.b.c();
        } else if (!isFinishing()) {
            finish();
        }
        return true;
    }

    protected void onPause() {
        super.onPause();
        if (this.b != null) {
            this.b.f();
            if (this.b.i() != null) {
                this.d = this.b.i().getUrl();
                this.b.e();
            }
        }
        if (isFinishing()) {
            if (a()) {
                f.a().a(new com.ad4screen.sdk.service.modules.inapp.e.d(this.e.h, -1, "com_ad4screen_sdk_theme_interstitial", false, this.e.o));
            }
            if (b()) {
                f.a().a(new c(this.g));
            }
        }
        A4S.get(getApplicationContext()).b();
    }

    protected void onResume() {
        super.onResume();
        if (!(this.b.i() == null || TextUtils.isEmpty(this.d))) {
            this.b.i().loadUrl(this.d);
        }
        A4S.get(getApplicationContext()).a();
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean("com.ad4screen.sdk.A4SIntersticial.displayTracked", this.c);
        bundle.putString("com.ad4screen.sdk.A4SIntersticial.savedUrl", this.d);
    }
}
