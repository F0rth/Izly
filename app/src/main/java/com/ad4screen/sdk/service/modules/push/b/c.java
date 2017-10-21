package com.ad4screen.sdk.service.modules.push.b;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.ad4screen.sdk.A4SService.a;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.d.b;
import com.ad4screen.sdk.plugins.GCMPlugin;
import com.ad4screen.sdk.plugins.PushPlugin;
import com.ad4screen.sdk.service.modules.push.PushProvider;
import com.ad4screen.sdk.service.modules.push.f;

public class c extends PushProvider {
    public c(a aVar) {
        super(aVar);
    }

    private void a(Context context, b bVar) {
        try {
            context.getPackageManager().getPackageInfo("com.google.android.gsf", 0);
        } catch (NameNotFoundException e) {
            Log.debug("GCMPushProvider|Device does not have package com.google.android.gsf, notification will not be enabled");
            return;
        } catch (RuntimeException e2) {
        }
        Log.debug("GCMPushProvider|Registering to GCM using standard method");
        Intent intent = new Intent("com.google.android.c2dm.intent.REGISTER");
        PackageManager packageManager = context.getPackageManager();
        if (packageManager != null) {
            ResolveInfo resolveService = packageManager.resolveService(intent, 0);
            if (resolveService != null) {
                ServiceInfo serviceInfo = resolveService.serviceInfo;
                if (serviceInfo != null) {
                    intent.setClassName(serviceInfo.packageName, serviceInfo.name);
                }
            }
        }
        intent.putExtra("app", PendingIntent.getBroadcast(context, 0, new Intent(), 0));
        intent.putExtra("sender", bVar.C());
        context.startService(intent);
    }

    private void a(Context context, String str, boolean z) {
        new f(context, str, f.a.GCM, z).run();
    }

    private boolean a(Context context, b bVar, PushPlugin pushPlugin) {
        String register = pushPlugin.register(context, bVar.C());
        if (register == null) {
            Log.internal("GCMPushProvider|No registration id returned from GCM Plugin");
            return false;
        }
        Log.internal("GCMPushProvider|GCM Plugin returned registration id : " + register);
        Bundle bundle = new Bundle();
        bundle.putString("registration_id", register);
        updateRegistration(bundle);
        return true;
    }

    private void c(Context context) {
        Log.debug("GCMPushProvider|Unregistering to GCM using standard method...");
        Intent intent = new Intent("com.google.android.c2dm.intent.UNREGISTER");
        PackageManager packageManager = context.getPackageManager();
        if (packageManager != null) {
            ResolveInfo resolveService = packageManager.resolveService(intent, 0);
            if (resolveService != null) {
                ServiceInfo serviceInfo = resolveService.serviceInfo;
                if (serviceInfo != null) {
                    intent.setClassName(serviceInfo.packageName, serviceInfo.name);
                }
            }
        }
        intent.putExtra("app", PendingIntent.getBroadcast(context, 0, new Intent(), 0));
        context.startService(intent);
    }

    private boolean e(String str) {
        if (str == null) {
            return false;
        }
        if (!str.contains(".") && str.length() >= 100) {
            return true;
        }
        Log.debug("GCMPushProvider|Registration Id found but invalid.");
        return false;
    }

    public void a() {
        b a = b.a(this.b.a());
        String g = this.c.g();
        if (a.C() != null) {
            if (!(g == null || a.C().equals(g))) {
                Log.debug("GCMPushProvider|SenderID is different from previous session, we will register again to GCM");
                c();
            }
            this.c.c(a.C());
        }
        a(this.b.a());
    }

    protected void a(Context context) {
        boolean z = false;
        b a = b.a(context);
        if (a.c() == null) {
            Log.debug("GCMPushProvider|Skipping GCM registration, sharedId was unavailable");
        } else if (isEnabled()) {
            int i;
            try {
                i = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
            } catch (NameNotFoundException e) {
                Log.internal("GCMPushProvider|Unable to retrieve current app version");
                i = 0;
            }
            String pushToken = getPushToken();
            if (e(pushToken) && i == this.c.h()) {
                Log.debug("GCMPushProvider|Device was already registered");
                a(context, pushToken, false);
            } else if (a.C() == null) {
                Log.debug("GCMPushProvider|No senderID provided, notifications will not be enabled.");
            } else {
                Log.debug("GCMPushProvider|Registering application '" + context.getPackageName() + "' with GCM with senderID : '" + a.C() + "'...");
                this.c.a(i);
                PushPlugin a2 = com.ad4screen.sdk.common.d.b.a();
                boolean z2 = a2 == null;
                if (z2) {
                    z = z2;
                } else if (!a(context, a, a2)) {
                    z = true;
                }
                if (z) {
                    Log.error("GCMPushProvider|GCM Plugin encountered an error while registering !");
                    a(context, a);
                }
            }
        } else {
            Log.debug("GCMPushProvider|Notifications were explicitely disabled, skipping GCM registration.");
        }
    }

    protected void a(String str) {
        Log.debug("GCMPushProvider|Handling GCM registration status update");
        if (e(str)) {
            Log.debug("GCMPushProvider|GCM registration ID found");
            this.d = 3000;
            d(str);
            a(this.b.a(), str, true);
        }
    }

    public void b() {
        Object obj = 1;
        b a = b.a(this.b.a());
        PushPlugin a2 = com.ad4screen.sdk.common.d.b.a();
        Object obj2 = a2 == null ? 1 : null;
        if (obj2 != null) {
            obj = obj2;
        } else if (a(this.b.a(), a, a2)) {
            obj = null;
        }
        if (obj != null) {
            a(this.b.a(), a);
            Log.error("GCMPushProvider|GCM Plugin encountered an error while refreshing !");
        }
    }

    protected void b(Context context) {
        Object obj = 1;
        b a = b.a(context);
        if (getPushToken() == null) {
            Log.debug("GCMPushProvider|Device was already unregistered, skipping GCM unregistration.");
        }
        Log.debug("GCMPushProvider|Unregistering application from GCM...");
        GCMPlugin a2 = com.ad4screen.sdk.common.d.b.a();
        Object obj2 = a2 == null ? 1 : null;
        if (obj2 == null) {
            Log.debug("GCMPushProvider|Unregistering to GCM using GCM Plugin...");
            if (a2.unregister(context, a.C())) {
                obj = null;
            }
        } else {
            obj = obj2;
        }
        if (obj != null) {
            Log.debug("GCMPushProvider|GCM Plugin encountered an error while unregistering !");
            c(context);
        }
    }

    protected void b(String str) {
        if (str != null) {
            Log.debug("GCMPushProvider|GCM unregistered with message : " + str);
            this.d = 3000;
            c();
        }
    }

    protected void c(String str) {
        if (str != null) {
            Log.error("GCMPushProvider|GCM registration failed with error: " + str);
            if ("SERVICE_NOT_AVAILABLE".equals(str)) {
                this.d *= 2;
                if (this.d > 3600000) {
                    this.d = 3600000;
                }
                final String pushToken = getPushToken();
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable(this) {
                    final /* synthetic */ c b;

                    public void run() {
                        if (this.b.e(pushToken)) {
                            this.b.b(this.b.b.a());
                        } else {
                            this.b.a(this.b.b.a());
                        }
                    }
                }, this.d);
                return;
            }
            Log.error("GCMPushProvider|GCM unrecoverable error for this session");
        }
    }
}
