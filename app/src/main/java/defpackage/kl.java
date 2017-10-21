package defpackage;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Looper;

final class kl implements km {
    private final Context a;

    public kl(Context context) {
        this.a = context.getApplicationContext();
    }

    public final ki a() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            js.a().a("Fabric", "AdvertisingInfoServiceStrategy cannot be called on the main thread");
            return null;
        }
        try {
            this.a.getPackageManager().getPackageInfo("com.android.vending", 0);
            ServiceConnection kl_a = new kl$a();
            Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
            intent.setPackage("com.google.android.gms");
            try {
                if (this.a.bindService(intent, kl_a, 1)) {
                    kl$b kl_b = new kl$b(kl_a.a());
                    ki kiVar = new ki(kl_b.a(), kl_b.b());
                    this.a.unbindService(kl_a);
                    return kiVar;
                }
                js.a().a("Fabric", "Could not bind to Google Play Service to capture AdvertisingId");
                return null;
            } catch (Throwable e) {
                js.a().b("Fabric", "Exception in binding to Google Play Service to capture AdvertisingId", e);
                this.a.unbindService(kl_a);
                return null;
            } catch (Throwable e2) {
                js.a().a("Fabric", "Could not bind to Google Play Service to capture AdvertisingId", e2);
                return null;
            }
        } catch (NameNotFoundException e3) {
            js.a().a("Fabric", "Unable to find Google Play Services package name");
            return null;
        } catch (Throwable e22) {
            js.a().a("Fabric", "Unable to determine if Google Play Services is available", e22);
            return null;
        }
    }
}
