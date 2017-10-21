package defpackage;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

final class kc extends jy<Boolean> {
    private final mh a = new me();
    private PackageManager b;
    private String c;
    private PackageInfo d;
    private String e;
    private String f;
    private String g;
    private String h;
    private String i;
    private final Future<Map<String, ka>> j;
    private final Collection<jy> k;

    public kc(Future<Map<String, ka>> future, Collection<jy> collection) {
        this.j = future;
        this.k = collection;
    }

    private Boolean a() {
        boolean z;
        String i = kp.i(getContext());
        ni b = b();
        if (b != null) {
            try {
                Map a = kc.a(this.j != null ? (Map) this.j.get() : new HashMap(), this.k);
                mt mtVar = b.a;
                Collection values = a.values();
                z = true;
                if ("new".equals(mtVar.b)) {
                    if (new mw(this, c(), mtVar.c, this.a).a(a(nc.a(getContext(), i), values))) {
                        z = nf$a.a.c();
                    } else {
                        js.a().c("Fabric", "Failed to create app with Crashlytics service.", null);
                        z = false;
                    }
                } else if ("configured".equals(mtVar.b)) {
                    z = nf$a.a.c();
                } else if (mtVar.f) {
                    js.a().a("Fabric", "Server says an update is required - forcing a full App update.");
                    new nn(this, c(), mtVar.c, this.a).a(a(nc.a(getContext(), i), values));
                }
            } catch (Throwable e) {
                js.a().c("Fabric", "Error performing auto configuration.", e);
                z = false;
            }
        } else {
            z = false;
        }
        return Boolean.valueOf(z);
    }

    private static Map<String, ka> a(Map<String, ka> map, Collection<jy> collection) {
        for (jy jyVar : collection) {
            if (!map.containsKey(jyVar.getIdentifier())) {
                map.put(jyVar.getIdentifier(), new ka(jyVar.getIdentifier(), jyVar.getVersion(), "binary"));
            }
        }
        return map;
    }

    private ms a(nc ncVar, Collection<ka> collection) {
        return new ms(new kn().a(getContext()), getIdManager().b, this.f, this.e, kp.a(kp.k(r0)), this.h, ks.a(this.g).e, this.i, "0", ncVar, collection);
    }

    private ni b() {
        try {
            nf$a.a.a(this, this.idManager, this.a, this.e, this.f, c()).b();
            return nf$a.a.a();
        } catch (Throwable e) {
            js.a().c("Fabric", "Error dealing with settings", e);
            return null;
        }
    }

    private String c() {
        return kp.c(getContext(), "com.crashlytics.ApiEndpoint");
    }

    protected final /* synthetic */ Object doInBackground() {
        return a();
    }

    public final String getIdentifier() {
        return "io.fabric.sdk.android:fabric";
    }

    public final String getVersion() {
        return "1.4.0.18";
    }

    protected final boolean onPreExecute() {
        try {
            this.g = getIdManager().d();
            this.b = getContext().getPackageManager();
            this.c = getContext().getPackageName();
            this.d = this.b.getPackageInfo(this.c, 0);
            this.e = Integer.toString(this.d.versionCode);
            this.f = this.d.versionName == null ? "0.0" : this.d.versionName;
            this.h = this.b.getApplicationLabel(getContext().getApplicationInfo()).toString();
            this.i = Integer.toString(getContext().getApplicationInfo().targetSdkVersion);
            return true;
        } catch (Throwable e) {
            js.a().c("Fabric", "Failed init", e);
            return false;
        }
    }
}
