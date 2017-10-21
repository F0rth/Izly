package com.ad4screen.sdk.a;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;

import com.ad4screen.sdk.A4S;
import com.ad4screen.sdk.A4SInterstitial;
import com.ad4screen.sdk.Constants;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.c;
import com.ad4screen.sdk.common.b.m.h;
import com.ad4screen.sdk.d.f;
import com.ad4screen.sdk.d.l;
import com.ad4screen.sdk.service.modules.k.g.d;
import com.ad4screen.sdk.service.modules.k.g.e;
import com.ad4screen.sdk.service.modules.push.a.a;
import com.ad4screen.sdk.service.modules.push.g;

import java.lang.ref.WeakReference;

public final class b {
    private static b h;
    private boolean a = false;
    private Intent b;
    private WeakReference<Activity> c = new WeakReference(null);
    private c d;
    private Intent e;
    private final ResultReceiver f;
    private final Context g;

    private b(Context context) {
        this.g = context.getApplicationContext();
        this.f = new ResultReceiver(this, new Handler(Looper.getMainLooper())) {
            final /* synthetic */ b a;

            protected void onReceiveResult(int i, Bundle bundle) {
                if (bundle != null) {
                    bundle.setClassLoader(this.a.g.getClassLoader());
                }
                if (i == 0) {
                    this.a.a(bundle.getString("com.ad4screen.sdk.A4SClient.inAppFormat"), bundle.getString("com.ad4screen.sdk.A4SClient.activityInstance"));
                } else if (i == 1) {
                    String string = bundle.getString("com.ad4screen.sdk.A4SClient.inAppId");
                    this.a.b(bundle.getString("com.ad4screen.sdk.A4SClient.activityInstance"), string);
                } else if (i == 2) {
                    this.a.a(bundle.getString("com.ad4screen.sdk.A4SClient.inAppFormat"));
                } else if (i == 3) {
                    this.a.a(bundle.getString("com.ad4screen.sdk.A4SClient.inAppFormat"), bundle.getInt("com.ad4screen.sdk.A4SClient.inAppTemplate"));
                }
            }
        };
        this.d = new c();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.facebook.sdk.ACTIVE_SESSION_OPENED");
        h.a(this.g, this.d, intentFilter);
    }

    public static b a(Context context) {
        if (h == null) {
            h = new b(context);
        }
        return h;
    }

    private void a(Uri uri) {
        d.a(uri).a(new e(A4S.get(this.g)));
    }

    private void a(String str) {
        Activity activity = (Activity) this.c.get();
        try {
            com.ad4screen.sdk.c.a.d dVar = (com.ad4screen.sdk.c.a.d) new com.ad4screen.sdk.common.c.e().a(str, new com.ad4screen.sdk.c.a.d());
            if (dVar != null) {
                l.a(this.g).b(activity, dVar);
            } else {
                Log.debug("Client|Could not deserialize JSON format");
            }
        } catch (Throwable e) {
            Log.internal("Client|Could not deserialize Format from JSON", e);
        }
    }

    private void a(String str, int i) {
        Activity activity = (Activity) this.c.get();
        try {
            com.ad4screen.sdk.c.a.d dVar = (com.ad4screen.sdk.c.a.d) new com.ad4screen.sdk.common.c.e().a(str, new com.ad4screen.sdk.c.a.d());
            if (dVar != null) {
                l.a(this.g).a(activity, dVar, i);
            } else {
                Log.debug("Client|Could not deserialize JSON format");
            }
        } catch (Throwable e) {
            Log.internal("Client|Could not deserialize Format from JSON", e);
        }
    }

    private void a(String str, String str2) {
        Activity activity = (Activity) this.c.get();
        if (str2 == null || activity == null || str2.equals(l.c(activity))) {
            try {
                com.ad4screen.sdk.c.a.d dVar = (com.ad4screen.sdk.c.a.d) new com.ad4screen.sdk.common.c.e().a(str, new com.ad4screen.sdk.c.a.d());
                if (dVar != null) {
                    l.a(this.g).a(activity, dVar);
                } else {
                    Log.debug("Client|Could not deserialize JSON format to be displayed");
                }
            } catch (Throwable e) {
                Log.internal("Client|Could not deserialize Format from JSON", e);
            }
        }
    }

    private void b(String str) {
        l.a(this.g).a((Activity) this.c.get(), str);
    }

    private void b(String str, String str2) {
        Activity activity = (Activity) this.c.get();
        if (activity == null) {
            Log.warn("Client|No activity provided for closing inapp");
        } else if (str == null || str.equals(l.c(activity))) {
            b(str2);
        }
    }

    private boolean b(Intent intent) {
        return (intent == null || intent.getExtras() == null || intent.getExtras().getBundle(Constants.EXTRA_GCM_PAYLOAD) == null) ? false : true;
    }

    private void f() {
        l.a(this.g).a();
        l.a(this.g).b();
    }

    public final ResultReceiver a() {
        return this.f;
    }

    public final void a(Activity activity) {
        this.c = new WeakReference(activity);
    }

    public final void a(Intent intent) {
        this.e = intent;
    }

    public final void a(Bundle bundle) {
        if (this.c != null) {
            Activity activity = (Activity) this.c.get();
            if (activity != null) {
                if (activity.getIntent() == null) {
                    activity.setIntent(new Intent());
                }
                activity.getIntent().putExtra(Constants.EXTRA_GCM_PAYLOAD, bundle);
                b();
            }
        }
    }

    public final void a(boolean z) {
        this.a = z;
    }

    public final void b() {
        Activity activity = (Activity) this.c.get();
        Intent intent = this.e;
        if (activity != null) {
            if (!b(intent)) {
                intent = activity.getIntent();
            }
            if (this.a) {
                Log.debug("Push|Push is currently locked. Landing Page will be displayed later");
                if (b(intent)) {
                    this.b = intent;
                    return;
                }
                return;
            }
            if (this.b != null) {
                intent = this.b;
            }
            this.b = null;
            if (intent == null || intent.getExtras() == null) {
                Log.debug("Push|Started LaunchActivity intent or extras are null, skipping richpush display");
                return;
            }
            Bundle extras = intent.getExtras();
            Bundle bundle = extras.getBundle(Constants.EXTRA_GCM_PAYLOAD);
            a a = a.a(bundle);
            if (a == null) {
                Log.debug("Push|Could not find push payload in activity extras");
                return;
            }
            Log.debug("Push|Push payload found in activity extras");
            if (a.B) {
                Log.debug("Push|But notification was already displayed, skipping...");
                return;
            }
            f.a().a(new c.d(bundle));
            if (b(this.e)) {
                this.e = null;
            } else {
                bundle.putBoolean("displayed", true);
                activity.getIntent().putExtras(extras);
            }
            com.ad4screen.sdk.c.a.e a2 = g.a(activity, a);
            if (a2 == null) {
                Log.debug("Push|No RichPush was found in activity extras");
                return;
            }
            Log.debug("Push|RichPush was found in activity extras, starting RichPush");
            activity.startActivity(A4SInterstitial.build(activity, 2, a2, intent.getExtras()));
        }
    }

    public final void b(Activity activity) {
        f();
    }

    public final void c() {
        Intent intent = this.e;
        if (intent == null || intent.getData() == null) {
            Activity activity = (Activity) this.c.get();
            if (activity != null) {
                intent = activity.getIntent();
                if (intent == null || intent.getData() == null) {
                    return;
                }
            }
            return;
        }
        Uri data = intent.getData();
        if (data != null) {
            a(data);
        }
    }

    public final void d() {
        if (this.c != null) {
            Activity activity = (Activity) this.c.get();
            if (activity != null) {
                activity.finish();
            }
        }
    }

    public final boolean e() {
        return this.a;
    }
}
