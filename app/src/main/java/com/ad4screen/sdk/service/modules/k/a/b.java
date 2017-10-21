package com.ad4screen.sdk.service.modules.k.a;

import android.content.Context;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.analytics.Lead;
import com.ad4screen.sdk.common.c.e;
import com.ad4screen.sdk.common.e.c;
import com.ad4screen.sdk.common.g;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.d.d;

import org.json.JSONException;
import org.json.JSONObject;

public class b extends c {
    private final String c = "com.ad4screen.sdk.service.modules.tracking.EventLeadTrackingTask";
    private final String d = "content";
    private final Context e;
    private final com.ad4screen.sdk.d.b f;
    private String g;
    private Lead h;

    public b(Context context, com.ad4screen.sdk.d.b bVar, Lead lead) {
        super(context);
        this.e = context;
        this.f = bVar;
        this.h = lead;
    }

    private b(Context context, com.ad4screen.sdk.d.b bVar, String str) throws JSONException {
        super(context);
        this.e = context;
        this.f = bVar;
        super.e(str);
        JSONObject jSONObject = new JSONObject(str).getJSONObject("com.ad4screen.sdk.service.modules.tracking.EventLeadTrackingTask");
        if (!jSONObject.isNull("content")) {
            this.g = jSONObject.getString("content");
        }
    }

    protected void a(String str) {
        Log.debug("EventLeadTrackingTask|Successfully sent lead events to server");
        d.a(this.e).e(com.ad4screen.sdk.d.d.b.EventLeadWebservice);
    }

    protected void a(Throwable th) {
        Log.error("EventLeadTrackingTask|Failed to send lead events to server");
    }

    protected boolean a() {
        i();
        j();
        if (this.h == null) {
            Log.debug("Lead is null, cannot send event");
            return false;
        } else if (!d.a(this.e).c(com.ad4screen.sdk.d.d.b.EventLeadWebservice)) {
            Log.debug("Service interruption on EventTrackingTask");
            return false;
        } else if (this.f.c() == null) {
            Log.warn("EventLeadTrackingTask|SharedId is undefined, cannot send event");
            return false;
        } else {
            try {
                JSONObject a = new e().a(this.h);
                if (this.f.d() != null) {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put(Lead.KEY_VALUE, this.f.d());
                    jSONObject.put("date", this.f.e());
                    a.put("source", jSONObject);
                }
                a.put("date", h.a());
                a.put("ruuid", h.b());
                this.g = a.toString();
                return true;
            } catch (Throwable e) {
                Log.error("EventLeadTrackingTask|Could not build message to send to server", e);
                return false;
            }
        }
    }

    public c b(c cVar) {
        return cVar;
    }

    protected String c() {
        return com.ad4screen.sdk.d.d.b.EventLeadWebservice.toString() + "/" + g.e().a() + "/" + ((int) (Math.random() * 10000.0d));
    }

    protected String d() {
        return this.g;
    }

    public c e(String str) throws JSONException {
        return new b(this.e, this.f, str);
    }

    protected String e() {
        return d.a(this.e).a(com.ad4screen.sdk.d.d.b.EventLeadWebservice);
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return e(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.tracking.EventLeadTrackingTask";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject toJSON = super.toJSON();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("content", this.g);
        toJSON.put("com.ad4screen.sdk.service.modules.tracking.EventLeadTrackingTask", jSONObject);
        return toJSON;
    }
}
