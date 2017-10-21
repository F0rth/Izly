package defpackage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;

final class kj {
    private final Context a;
    private final mn b;

    public kj(Context context) {
        this.a = context.getApplicationContext();
        this.b = new mo(context, "TwitterAdvertisingInfoPreferences");
    }

    private static boolean b(ki kiVar) {
        return (kiVar == null || TextUtils.isEmpty(kiVar.a)) ? false : true;
    }

    public final ki a() {
        ki kiVar = new ki(this.b.a().getString("advertising_id", ""), this.b.a().getBoolean("limit_ad_tracking_enabled", false));
        if (kj.b(kiVar)) {
            js.a().a("Fabric", "Using AdvertisingInfo from Preference Store");
            new Thread(new kj$1(this, kiVar)).start();
            return kiVar;
        }
        kiVar = b();
        a(kiVar);
        return kiVar;
    }

    @SuppressLint({"CommitPrefEdits"})
    void a(ki kiVar) {
        if (kj.b(kiVar)) {
            this.b.a(this.b.b().putString("advertising_id", kiVar.a).putBoolean("limit_ad_tracking_enabled", kiVar.b));
        } else {
            this.b.a(this.b.b().remove("advertising_id").remove("limit_ad_tracking_enabled"));
        }
    }

    ki b() {
        ki a = new kk(this.a).a();
        if (kj.b(a)) {
            js.a().a("Fabric", "Using AdvertisingInfo from Reflection Provider");
        } else {
            a = new kl(this.a).a();
            if (kj.b(a)) {
                js.a().a("Fabric", "Using AdvertisingInfo from Service Provider");
            } else {
                js.a().a("Fabric", "AdvertisingInfo not present");
            }
        }
        return a;
    }
}
