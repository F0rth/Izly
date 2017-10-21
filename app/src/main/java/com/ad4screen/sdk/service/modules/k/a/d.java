package com.ad4screen.sdk.service.modules.k.a;

import android.content.Context;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.analytics.Lead;
import com.ad4screen.sdk.common.e.c;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.d.b;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class d extends c {
    private final String c = "com.ad4screen.sdk.service.modules.tracking.EventTrackingTask";
    private final String d = "content";
    private final Context e;
    private final String f;
    private final b g;
    private String h;
    private Long i;

    public d(Context context, b bVar, Long l, String str) {
        super(context);
        this.e = context;
        this.g = bVar;
        this.i = l;
        this.f = str;
    }

    protected void a(String str) {
        Log.debug("EventTrackingTask|Successfully sent events to server");
        com.ad4screen.sdk.d.d.a(this.e).e(com.ad4screen.sdk.d.d.b.EventWebservice);
    }

    protected void a(Throwable th) {
        Log.error("EventTrackingTask|Failed to send events to server");
    }

    protected boolean a() {
        i();
        j();
        if (this.i == null || this.f == null) {
            Log.debug("Event type or value is null, cannot send event");
            return false;
        } else if (!com.ad4screen.sdk.d.d.a(this.e).c(com.ad4screen.sdk.d.d.b.EventWebservice)) {
            Log.debug("Service interruption on EventTrackingTask");
            return false;
        } else if (this.g.c() == null) {
            Log.warn("EventTrackingTask|SharedId is undefined, cannot send event");
            return false;
        } else {
            try {
                JSONObject jSONObject = new JSONObject();
                JSONArray jSONArray = new JSONArray();
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("type", this.i);
                jSONObject2.put("date", h.a());
                jSONObject2.put(Lead.KEY_VALUE, this.f);
                if (this.g.d() != null) {
                    JSONObject jSONObject3 = new JSONObject();
                    jSONObject3.put(Lead.KEY_VALUE, this.g.d());
                    jSONObject3.put("date", this.g.e());
                    jSONObject2.put("source", jSONObject3);
                }
                jSONObject2.put("ruuid", h.b());
                jSONArray.put(jSONObject2);
                jSONObject.put("events", jSONArray);
                this.h = jSONObject.toString();
                return true;
            } catch (Throwable e) {
                Log.error("EventTrackingTask|Could not build message to send to server", e);
                return false;
            }
        }
    }

    public c b(c cVar) {
        d dVar = (d) cVar;
        try {
            JSONObject jSONObject = new JSONObject(d());
            JSONArray jSONArray = new JSONObject(dVar.d()).getJSONArray("events");
            JSONArray jSONArray2 = jSONObject.getJSONArray("events");
            for (int i = 0; i < jSONArray.length(); i++) {
                jSONArray2.put(jSONArray.get(i));
            }
            this.h = jSONObject.toString();
        } catch (Throwable e) {
            Log.internal("Failed to merge " + c(), e);
        } catch (Throwable e2) {
            Log.internal("Failed to merge " + c(), e2);
        }
        return this;
    }

    protected String c() {
        return com.ad4screen.sdk.d.d.b.EventWebservice.toString();
    }

    protected String d() {
        return this.h;
    }

    public c e(String str) throws JSONException {
        super.e(str);
        JSONObject jSONObject = new JSONObject(str).getJSONObject("com.ad4screen.sdk.service.modules.tracking.EventTrackingTask");
        if (!jSONObject.isNull("content")) {
            this.h = jSONObject.getString("content");
        }
        return this;
    }

    protected String e() {
        return com.ad4screen.sdk.d.d.a(this.e).a(com.ad4screen.sdk.d.d.b.EventWebservice);
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return e(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.tracking.EventTrackingTask";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject toJSON = super.toJSON();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("content", this.h);
        jSONObject.put("type", this.i);
        toJSON.put("com.ad4screen.sdk.service.modules.tracking.EventTrackingTask", jSONObject);
        return toJSON;
    }
}
