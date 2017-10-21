package com.ad4screen.sdk.service.modules.k;

import android.content.Context;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.d.b;
import com.ad4screen.sdk.d.d;

import org.json.JSONException;
import org.json.JSONObject;

public class c extends com.ad4screen.sdk.common.e.c {
    private final String c = "com.ad4screen.sdk.service.modules.tracking.ReferrerTrackingTask";
    private final String d = "content";
    private final Context e;
    private final b f;
    private String g;

    public c(Context context) {
        super(context);
        this.e = context;
        this.f = b.a(this.e);
    }

    protected void a(String str) {
        d.a(this.e).e(d.b.ReferrerWebservice);
    }

    protected void a(Throwable th) {
    }

    protected boolean a() {
        i();
        j();
        if (!d.a(this.e).c(d.b.ReferrerWebservice)) {
            Log.debug("Service interruption on ReferrerTrackingTask");
            return false;
        } else if (this.f.c() == null) {
            Log.warn("Accengage|SharedId is undefined, cannot send event");
            return false;
        } else {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("date", h.a());
                jSONObject.put("referrer", this.f.z());
                jSONObject.put("ruuid", h.b());
                this.g = jSONObject.toString();
                return true;
            } catch (Throwable e) {
                Log.error("Accengage|Could not build message to send to server", e);
                return false;
            }
        }
    }

    public com.ad4screen.sdk.common.e.c b(com.ad4screen.sdk.common.e.c cVar) {
        return cVar;
    }

    protected String c() {
        return d.b.ReferrerWebservice.toString();
    }

    protected String d() {
        return this.g;
    }

    public com.ad4screen.sdk.common.e.c e(String str) throws JSONException {
        super.e(str);
        JSONObject jSONObject = new JSONObject(str).getJSONObject("com.ad4screen.sdk.service.modules.tracking.ReferrerTrackingTask");
        if (!jSONObject.isNull("content")) {
            this.g = jSONObject.getString("content");
        }
        return this;
    }

    protected String e() {
        return d.a(this.e).a(d.b.ReferrerWebservice);
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return e(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.tracking.ReferrerTrackingTask";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject toJSON = super.toJSON();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("content", this.g);
        toJSON.put("com.ad4screen.sdk.service.modules.tracking.ReferrerTrackingTask", jSONObject);
        return toJSON;
    }
}
