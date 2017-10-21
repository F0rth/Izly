package defpackage;

import android.content.res.Resources.NotFoundException;
import java.io.Closeable;
import java.io.InputStream;
import java.util.Locale;

abstract class mp extends kh {
    public mp(jy jyVar, String str, String str2, mh mhVar, int i) {
        super(jyVar, str, str2, mhVar, i);
    }

    private mg a(mg mgVar, ms msVar) {
        Closeable openRawResource;
        Throwable e;
        Closeable closeable = null;
        mg a = mgVar.a("app[identifier]", null, msVar.b).a("app[name]", null, msVar.f).a("app[display_version]", null, msVar.c).a("app[build_version]", null, msVar.d).a("app[source]", Integer.valueOf(msVar.g)).a("app[minimum_sdk_version]", null, msVar.h).a("app[built_sdk_version]", null, msVar.i);
        if (!kp.d(msVar.e)) {
            a.a("app[instance_identifier]", null, msVar.e);
        }
        if (msVar.j != null) {
            try {
                openRawResource = this.kit.getContext().getResources().openRawResource(msVar.j.b);
                try {
                    a.a("app[icon][hash]", null, msVar.j.a).a("app[icon][data]", "icon.png", "application/octet-stream", (InputStream) openRawResource).a("app[icon][width]", Integer.valueOf(msVar.j.c)).a("app[icon][height]", Integer.valueOf(msVar.j.d));
                    kp.a(openRawResource, "Failed to close app icon InputStream.");
                } catch (NotFoundException e2) {
                    e = e2;
                    try {
                        js.a().c("Fabric", "Failed to find app icon with resource ID: " + msVar.j.b, e);
                        kp.a(openRawResource, "Failed to close app icon InputStream.");
                        if (msVar.k != null) {
                            for (ka kaVar : msVar.k) {
                                a.a(String.format(Locale.US, "app[build][libraries][%s][version]", new Object[]{kaVar.a}), null, kaVar.b);
                                a.a(String.format(Locale.US, "app[build][libraries][%s][type]", new Object[]{kaVar.a}), null, kaVar.c);
                            }
                        }
                        return a;
                    } catch (Throwable th) {
                        e = th;
                        closeable = openRawResource;
                        kp.a(closeable, "Failed to close app icon InputStream.");
                        throw e;
                    }
                }
            } catch (NotFoundException e3) {
                e = e3;
                openRawResource = null;
                js.a().c("Fabric", "Failed to find app icon with resource ID: " + msVar.j.b, e);
                kp.a(openRawResource, "Failed to close app icon InputStream.");
                if (msVar.k != null) {
                    for (ka kaVar2 : msVar.k) {
                        a.a(String.format(Locale.US, "app[build][libraries][%s][version]", new Object[]{kaVar2.a}), null, kaVar2.b);
                        a.a(String.format(Locale.US, "app[build][libraries][%s][type]", new Object[]{kaVar2.a}), null, kaVar2.c);
                    }
                }
                return a;
            } catch (Throwable th2) {
                e = th2;
                kp.a(closeable, "Failed to close app icon InputStream.");
                throw e;
            }
        }
        if (msVar.k != null) {
            for (ka kaVar22 : msVar.k) {
                a.a(String.format(Locale.US, "app[build][libraries][%s][version]", new Object[]{kaVar22.a}), null, kaVar22.b);
                a.a(String.format(Locale.US, "app[build][libraries][%s][type]", new Object[]{kaVar22.a}), null, kaVar22.c);
            }
        }
        return a;
    }

    public boolean a(ms msVar) {
        mg a = a(getHttpRequest().a(kh.HEADER_API_KEY, msVar.a).a(kh.HEADER_CLIENT_TYPE, kh.ANDROID_CLIENT_TYPE).a(kh.HEADER_CLIENT_VERSION, this.kit.getVersion()), msVar);
        js.a().a("Fabric", "Sending app info to " + getUrl());
        if (msVar.j != null) {
            js.a().a("Fabric", "App icon hash is " + msVar.j.a);
            js.a().a("Fabric", "App icon size is " + msVar.j.c + "x" + msVar.j.d);
        }
        int b = a.b();
        js.a().a("Fabric", ("POST".equals(a.a().getRequestMethod()) ? "Create" : "Update") + " app request ID: " + a.a(kh.HEADER_REQUEST_ID));
        js.a().a("Fabric", "Result was " + b);
        return kz.a(b) == 0;
    }
}
