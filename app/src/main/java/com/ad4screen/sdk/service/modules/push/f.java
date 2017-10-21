package com.ad4screen.sdk.service.modules.push;

import android.content.Context;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.e.c;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.d.b;
import com.ad4screen.sdk.d.d;

import org.json.JSONException;
import org.json.JSONObject;

public class f extends c {
    private final String c = "token";
    private final String d = "releaseMode";
    private final String e = "timezone";
    private final String f = "fresh";
    private final String g = "com.ad4screen.sdk.service.modules.push.SendRegistrationTokenTask";
    private final String h = "content";
    private final String i = "newToken";
    private final String j = "token";
    private final String k = "tokenType";
    private final Context l;
    private b m;
    private boolean n;
    private String o;
    private a p = a.GCM;
    private String q;

    public enum a {
        UNKNOWN,
        GCM,
        ADM
    }

    public f(Context context, String str, a aVar, boolean z) {
        super(context);
        this.l = context;
        this.m = b.a(this.l);
        this.n = z;
        this.o = str;
        if (aVar != null) {
            this.p = aVar;
        }
    }

    protected void a(String str) {
        Log.debug("The following GCM registration token has been successfully sent : " + this.o);
        d.a(this.l).e(d.b.PushTokenWebservice);
        com.ad4screen.sdk.d.f.a().a(new d.d());
    }

    protected void a(Throwable th) {
        Log.debug("Failed to send GCM registration token to server");
        com.ad4screen.sdk.d.f.a().a(new d.c());
    }

    protected boolean a() {
        i();
        j();
        if (this.m.c() == null) {
            Log.warn("Push|No SharedId, not sending token");
            return false;
        } else if (d.a(this.l).c(d.b.PushTokenWebservice)) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("token", this.o);
                jSONObject.put("releaseMode", this.p);
                jSONObject.put("timezone", this.m.w());
                jSONObject.put("fresh", this.n);
                jSONObject.put("ruuid", h.b());
                this.q = jSONObject.toString();
                return true;
            } catch (Throwable e) {
                Log.error("Push|Could not build message to send to server", e);
                com.ad4screen.sdk.d.f.a().a(new d.c());
                return false;
            }
        } else {
            Log.debug("Service interruption on SendRegistrationTokenTask");
            return false;
        }
    }

    public c b(c cVar) {
        return cVar;
    }

    protected String c() {
        return d.b.PushTokenWebservice.toString();
    }

    protected String d() {
        return this.q;
    }

    public c e(String str) throws JSONException {
        super.e(str);
        JSONObject jSONObject = new JSONObject(str).getJSONObject("com.ad4screen.sdk.service.modules.push.SendRegistrationTokenTask");
        if (!jSONObject.isNull("content")) {
            this.q = jSONObject.getString("content");
        }
        if (!jSONObject.isNull("newToken")) {
            this.n = jSONObject.getBoolean("newToken");
        }
        if (!jSONObject.isNull("token")) {
            this.o = jSONObject.getString("token");
        }
        if (!jSONObject.isNull("tokenType")) {
            this.p = a.valueOf(jSONObject.getString("tokenType"));
        }
        return this;
    }

    protected String e() {
        return d.a(this.l).a(d.b.PushTokenWebservice);
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return e(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.push.SendRegistrationTokenTask";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject toJSON = super.toJSON();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("content", this.q);
        jSONObject.put("newToken", this.n);
        jSONObject.put("token", this.o);
        jSONObject.put("tokenType", this.p.toString());
        toJSON.put("com.ad4screen.sdk.service.modules.push.SendRegistrationTokenTask", jSONObject);
        return toJSON;
    }
}
