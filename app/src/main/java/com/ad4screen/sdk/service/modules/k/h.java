package com.ad4screen.sdk.service.modules.k;

import android.content.Context;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.e.c;
import com.ad4screen.sdk.common.i;
import com.ad4screen.sdk.d.b;
import com.ad4screen.sdk.d.d;

import org.json.JSONObject;

public class h extends c {
    private final String c = "com.ad4screen.sdk.service.modules.tracking.VersionTrackingTask";
    private final Context d;
    private String e;
    private b f;

    public h(Context context) {
        super(context);
        this.d = context;
        this.f = b.a(this.d);
    }

    protected void a(String str) {
        Log.internal("VersionTrackingTask|Tracking succeed");
        d.a(this.d).e(d.b.VersionTrackingWebservice);
        if (((d.a(this.d).d(d.b.UploadCarrierName) && d.a(this.d).c(d.b.UploadCarrierName)) || this.f.M() == b.b.NORMAL) && i.f(this.d) != null) {
            d.a(this.d).e(d.b.UploadCarrierName);
        }
    }

    protected void a(Throwable th) {
        Log.internal("VersionTrackingTask|Tracking failed");
    }

    protected boolean a() {
        a(4);
        i();
        j();
        if (this.f.c() == null) {
            Log.warn("VersionTrackingTask|No sharedId, skipping configuration");
            return false;
        } else if (d.a(this.d).c(d.b.VersionTrackingWebservice)) {
            try {
                JSONObject jSONObject = new JSONObject();
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("version", this.f.o());
                jSONObject2.put("machine", this.f.n());
                jSONObject2.put("capacity", 0);
                jSONObject2.put("language", this.f.s());
                jSONObject2.put("countryCode", this.f.r());
                jSONObject2.put("timezone", this.f.w());
                if ((d.a(this.d).d(d.b.UploadCarrierName) && d.a(this.d).c(d.b.UploadCarrierName)) || this.f.M() == b.b.NORMAL) {
                    String f = i.f(this.d);
                    if (f != null) {
                        jSONObject2.put("carrierName", f);
                    }
                }
                if (this.f.J()) {
                    jSONObject2.put("idfaEnabled", !this.f.c(this.d));
                }
                jSONObject.put("device", jSONObject2);
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("version", this.f.t());
                jSONObject3.put("name", this.f.p());
                jSONObject3.put("display", this.f.u());
                jSONObject.put("bundle", jSONObject3);
                jSONObject.put("sdk", this.f.i());
                jSONObject.put("ruuid", com.ad4screen.sdk.common.h.b());
                Log.debug("VersionTrackingTask", jSONObject);
                this.e = jSONObject.toString();
                return true;
            } catch (Throwable e) {
                Log.error("Accengage|Could not build message to send to server", e);
                return false;
            }
        } else {
            Log.debug("Service interruption on VersionTrackingTask");
            return false;
        }
    }

    public c b(c cVar) {
        return cVar;
    }

    protected String c() {
        return d.b.VersionTrackingWebservice.toString();
    }

    protected String d() {
        return this.e;
    }

    protected String e() {
        return d.a(this.d).a(d.b.VersionTrackingWebservice);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.tracking.VersionTrackingTask";
    }
}
