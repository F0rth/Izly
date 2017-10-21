package com.ad4screen.sdk.service.modules.k;

import android.content.Context;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.analytics.Lead;
import com.ad4screen.sdk.common.b.m;
import com.ad4screen.sdk.common.e.c;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.common.i;
import com.ad4screen.sdk.d.b;
import com.ad4screen.sdk.d.d;
import com.ad4screen.sdk.external.shortcutbadger.impl.NewHtcHomeBadger;
import com.ezeeworld.b4s.android.sdk.server.Api2;

import org.json.JSONException;
import org.json.JSONObject;

public class g extends c {
    b c;
    private final String d = "com.ad4screen.sdk.service.modules.tracking.TrackingTask";
    private final String e = "content";
    private final Context f;
    private String g;

    public g(Context context) {
        super(context);
        this.f = context;
        this.c = b.a(this.f);
    }

    protected void a(String str) {
        Log.debug("TrackingTask|Tracking succeed");
        Log.internal("Tracking|Response : " + str);
        d.a(this.f).e(d.b.TrackingWebservice);
        this.c.b();
        if ((d.a(this.f).d(d.b.UploadConnectionType) && d.a(this.f).c(d.b.UploadConnectionType)) || this.c.M() == b.b.NORMAL) {
            d.a(this.f).e(d.b.UploadConnectionType);
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (!jSONObject.isNull("source")) {
                String string = jSONObject.getString("source");
                this.c.e(string);
                Log.debug("Tracking|New source : " + string);
            }
        } catch (Throwable e) {
            Log.internal("Accengage|Could not parse server response", e);
        }
    }

    protected void a(Throwable th) {
        Log.error("Tracking|Tracking failed", th);
    }

    protected boolean a() {
        b(6);
        i();
        j();
        if (!d.a(this.f).c(d.b.TrackingWebservice)) {
            Log.debug("Service interruption on TrackingTask");
            return false;
        } else if (this.c.c() == null) {
            Log.warn("TrackingTask|No sharedId, skipping configuration");
            return false;
        } else {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("date", h.a());
                jSONObject.put("install", this.c.v());
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("open", this.c.x());
                jSONObject2.put(Api2.HEADER_TRACKING, this.c.y());
                jSONObject.put(NewHtcHomeBadger.COUNT, jSONObject2);
                if ((d.a(this.f).d(d.b.UploadConnectionType) && d.a(this.f).c(d.b.UploadConnectionType)) || this.c.M() == b.b.NORMAL) {
                    jSONObject.put("connection", i.h(this.f) ? "wifi" : "cell");
                }
                jSONObject.put("ua", m.a(this.f));
                boolean z = com.ad4screen.sdk.service.modules.push.g.b(this.f) ? !i.i(this.f) : false;
                jSONObject.put("notificationsEnabled", z);
                if (this.c.d() != null) {
                    jSONObject2 = new JSONObject();
                    jSONObject2.put(Lead.KEY_VALUE, this.c.d());
                    jSONObject2.put("date", this.c.e());
                    jSONObject.put("source", jSONObject2);
                }
                jSONObject.put("ruuid", h.b());
                Log.debug("TrackingTask", jSONObject);
                this.g = jSONObject.toString();
                return true;
            } catch (Throwable e) {
                Log.error("Accengage|Could not build message to send to server", e);
                return false;
            }
        }
    }

    public c b(c cVar) {
        return cVar;
    }

    protected String c() {
        return d.b.TrackingWebservice.toString();
    }

    protected String d() {
        return this.g;
    }

    public c e(String str) throws JSONException {
        super.e(str);
        JSONObject jSONObject = new JSONObject(str).getJSONObject("com.ad4screen.sdk.service.modules.tracking.TrackingTask");
        if (!jSONObject.isNull("content")) {
            this.g = jSONObject.getString("content");
        }
        return this;
    }

    protected String e() {
        return d.a(this.f).a(d.b.TrackingWebservice);
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return e(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.tracking.TrackingTask";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject toJSON = super.toJSON();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("content", this.g);
        toJSON.put("com.ad4screen.sdk.service.modules.tracking.TrackingTask", jSONObject);
        return toJSON;
    }
}
