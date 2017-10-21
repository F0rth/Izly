package com.ad4screen.sdk.service.modules.push.b;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.d.b;
import com.ad4screen.sdk.plugins.ADMPlugin;
import com.ad4screen.sdk.service.modules.push.PushProvider;
import com.ad4screen.sdk.service.modules.push.f;

public class a extends PushProvider {
    public a(com.ad4screen.sdk.A4SService.a aVar) {
        super(aVar);
    }

    private void a(Context context, String str, boolean z) {
        new f(context, str, com.ad4screen.sdk.service.modules.push.f.a.ADM, z).run();
    }

    public void a() {
        a(this.b.a());
    }

    protected void a(Context context) {
        if (b.a(context).c() == null) {
            Log.debug("ADMPushProvider|Skipping ADM registration, sharedId was unavailable");
        } else if (isEnabled()) {
            Log.debug("ADMPushProvider|Registering application '" + context.getPackageName() + "' with ADM...");
            ADMPlugin b = com.ad4screen.sdk.common.d.b.b();
            if (b != null) {
                String register = b.register(context, null);
                if (register != null) {
                    Log.internal("ADMPushProvider|ADM Plugin returned registration id : " + register);
                    a(register);
                    return;
                }
                Log.internal("ADMPushProvider|No registration id returned from ADM Plugin");
                return;
            }
            Log.debug("ADMPushProvider|ADM Plugin encountered an error while registering !");
        } else {
            Log.debug("ADMPushProvider|Notifications were explicitely disabled, skipping ADM registration.");
        }
    }

    protected void a(String str) {
        Log.debug("ADMPushProvider|ADM registration ID found : " + str);
        this.d = 3000;
        d(str);
        a(this.b.a(), str, true);
    }

    public void b() {
    }

    protected void b(Context context) {
        if (getPushToken() == null) {
            Log.debug("ADMPushProvider|Device was already unregistered, skipping ADM unregistration.");
            return;
        }
        Log.debug("ADMPushProvider|Unregistering application from ADM...");
        ADMPlugin b = com.ad4screen.sdk.common.d.b.b();
        if (b != null) {
            b.unregister(context);
        } else {
            Log.warn("ADMPushProvider|Skipping ADM unregistration, plugin unavailable");
        }
    }

    protected void b(String str) {
        Log.debug("ADMPushProvider|ADM unregistered with id : " + str);
        this.d = 3000;
        c();
    }

    protected void c(String str) {
        Log.error("ADMPushProvider|ADM registration failed with error: " + str);
        if ("SERVICE_NOT_AVAILABLE".equals(str)) {
            this.d *= 2;
            if (this.d > 3600000) {
                this.d = 3600000;
            }
            final String pushToken = getPushToken();
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable(this) {
                final /* synthetic */ a b;

                public void run() {
                    if (pushToken == null) {
                        this.b.a(this.b.b.a());
                    } else {
                        this.b.b(this.b.b.a());
                    }
                }
            }, this.d);
            return;
        }
        Log.error("ADMPushProvider|ADM unrecoverable error for this session");
    }
}
