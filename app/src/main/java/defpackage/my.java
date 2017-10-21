package defpackage;

import android.content.SharedPreferences.Editor;
import org.json.JSONException;
import org.json.JSONObject;

final class my implements nh {
    private final nl a;
    private final nk b;
    private final kr c;
    private final mv d;
    private final nm e;
    private final jy f;
    private final mn g = new mo(this.f);

    public my(jy jyVar, nl nlVar, kr krVar, nk nkVar, mv mvVar, nm nmVar) {
        this.f = jyVar;
        this.a = nlVar;
        this.c = krVar;
        this.b = nkVar;
        this.d = mvVar;
        this.e = nmVar;
    }

    private static void a(JSONObject jSONObject, String str) throws JSONException {
        js.a().a("Fabric", str + jSONObject.toString());
    }

    private String b() {
        return kp.a(kp.k(this.f.getContext()));
    }

    private ni b(ng ngVar) {
        Throwable e;
        ni a;
        try {
            if (!ng.SKIP_CACHE_LOOKUP.equals(ngVar)) {
                JSONObject a2 = this.d.a();
                if (a2 != null) {
                    a = this.b.a(this.c, a2);
                    my.a(a2, "Loaded cached settings: ");
                    long a3 = this.c.a();
                    if (!ng.IGNORE_CACHE_EXPIRATION.equals(ngVar)) {
                        if ((a.g < a3 ? 1 : null) != null) {
                            js.a().a("Fabric", "Cached settings have expired.");
                            return null;
                        }
                    }
                    try {
                        js.a().a("Fabric", "Returning cached settings.");
                        return a;
                    } catch (Exception e2) {
                        e = e2;
                    }
                } else {
                    js.a().a("Fabric", "No cached settings data found.");
                }
            }
            return null;
        } catch (Throwable e3) {
            e = e3;
            a = null;
            js.a().c("Fabric", "Failed to get cached settings", e);
            return a;
        }
    }

    public final ni a() {
        return a(ng.USE_CACHE);
    }

    public final ni a(ng ngVar) {
        Throwable th;
        ni niVar = null;
        try {
            ni niVar2;
            if (!js.b()) {
                if ((!this.g.a().getString("existing_instance_identifier", "").equals(b()) ? 1 : null) == null) {
                    niVar = b(ngVar);
                }
            }
            if (niVar == null) {
                try {
                    JSONObject a = this.e.a(this.a);
                    if (a != null) {
                        niVar = this.b.a(this.c, a);
                        this.d.a(niVar.g, a);
                        my.a(a, "Loaded settings: ");
                        String b = b();
                        Editor b2 = this.g.b();
                        b2.putString("existing_instance_identifier", b);
                        this.g.a(b2);
                        niVar2 = niVar;
                        if (niVar2 != null) {
                            try {
                            } catch (Throwable e) {
                                Throwable th2 = e;
                                niVar = niVar2;
                                th = th2;
                                js.a().c("Fabric", "Unknown error while loading Crashlytics settings. Crashes will be cached until settings can be retrieved.", th);
                                return niVar;
                            }
                        }
                    }
                } catch (Exception e2) {
                    th = e2;
                    js.a().c("Fabric", "Unknown error while loading Crashlytics settings. Crashes will be cached until settings can be retrieved.", th);
                    return niVar;
                }
            }
            niVar2 = niVar;
            return niVar2 != null ? niVar2 : b(ng.IGNORE_CACHE_EXPIRATION);
        } catch (Exception e3) {
            th = e3;
            js.a().c("Fabric", "Unknown error while loading Crashlytics settings. Crashes will be cached until settings can be retrieved.", th);
            return niVar;
        }
    }
}
