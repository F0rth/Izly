package defpackage;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import org.spongycastle.asn1.x509.DisplayText;

final class na extends kh implements nm {
    public na(jy jyVar, String str, String str2, mh mhVar) {
        this(jyVar, str, str2, mhVar, mf.a);
    }

    private na(jy jyVar, String str, String str2, mh mhVar, int i) {
        super(jyVar, str, str2, mhVar, i);
    }

    private JSONObject a(String str) {
        try {
            return new JSONObject(str);
        } catch (Throwable e) {
            js.a().a("Fabric", "Failed to parse settings JSON from " + getUrl(), e);
            js.a().a("Fabric", "Settings response " + str);
            return null;
        }
    }

    private static void a(mg mgVar, String str, String str2) {
        if (str2 != null) {
            mgVar.a(str, str2);
        }
    }

    public final JSONObject a(nl nlVar) {
        JSONObject a;
        Throwable e;
        Throwable th;
        mg mgVar = null;
        mg httpRequest;
        try {
            Map hashMap = new HashMap();
            hashMap.put("build_version", nlVar.j);
            hashMap.put("display_version", nlVar.i);
            hashMap.put("source", Integer.toString(nlVar.k));
            if (nlVar.l != null) {
                hashMap.put("icon_hash", nlVar.l);
            }
            String str = nlVar.h;
            if (!kp.d(str)) {
                hashMap.put("instance", str);
            }
            httpRequest = getHttpRequest(hashMap);
            try {
                na.a(httpRequest, kh.HEADER_API_KEY, nlVar.a);
                na.a(httpRequest, kh.HEADER_CLIENT_TYPE, kh.ANDROID_CLIENT_TYPE);
                na.a(httpRequest, kh.HEADER_CLIENT_VERSION, this.kit.getVersion());
                na.a(httpRequest, kh.HEADER_ACCEPT, kh.ACCEPT_JSON_VALUE);
                na.a(httpRequest, "X-CRASHLYTICS-DEVICE-MODEL", nlVar.b);
                na.a(httpRequest, "X-CRASHLYTICS-OS-BUILD-VERSION", nlVar.c);
                na.a(httpRequest, "X-CRASHLYTICS-OS-DISPLAY-VERSION", nlVar.d);
                na.a(httpRequest, "X-CRASHLYTICS-INSTALLATION-ID", nlVar.f);
                if (TextUtils.isEmpty(nlVar.e)) {
                    na.a(httpRequest, "X-CRASHLYTICS-ANDROID-ID", nlVar.g);
                } else {
                    na.a(httpRequest, "X-CRASHLYTICS-ADVERTISING-TOKEN", nlVar.e);
                }
                js.a().a("Fabric", "Requesting settings from " + getUrl());
                js.a().a("Fabric", "Settings query params were: " + hashMap);
                int b = httpRequest.b();
                js.a().a("Fabric", "Settings result was: " + b);
                Object obj = (b == DisplayText.DISPLAY_TEXT_MAXIMUM_SIZE || b == 201 || b == 202 || b == 203) ? 1 : null;
                if (obj != null) {
                    a = a(httpRequest.c());
                } else {
                    js.a().e("Fabric", "Failed to retrieve settings from " + getUrl());
                }
                if (httpRequest != null) {
                    js.a().a("Fabric", "Settings request ID: " + httpRequest.a(kh.HEADER_REQUEST_ID));
                }
            } catch (mg$c e2) {
                e = e2;
                try {
                    js.a().c("Fabric", "Settings request failed.", e);
                    if (httpRequest != null) {
                        js.a().a("Fabric", "Settings request ID: " + httpRequest.a(kh.HEADER_REQUEST_ID));
                    }
                    return a;
                } catch (Throwable th2) {
                    Throwable th3 = th2;
                    mgVar = httpRequest;
                    th = th3;
                    if (mgVar != null) {
                        js.a().a("Fabric", "Settings request ID: " + mgVar.a(kh.HEADER_REQUEST_ID));
                    }
                    throw th;
                }
            }
        } catch (Throwable th4) {
            e = th4;
            httpRequest = null;
            js.a().c("Fabric", "Settings request failed.", e);
            if (httpRequest != null) {
                js.a().a("Fabric", "Settings request ID: " + httpRequest.a(kh.HEADER_REQUEST_ID));
            }
            return a;
        } catch (Throwable th5) {
            th4 = th5;
            if (mgVar != null) {
                js.a().a("Fabric", "Settings request ID: " + mgVar.a(kh.HEADER_REQUEST_ID));
            }
            throw th4;
        }
        return a;
    }
}
