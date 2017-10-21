package com.ad4screen.sdk.service.modules.b;

import android.content.Context;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.e.c;
import com.ad4screen.sdk.d.d;
import com.ad4screen.sdk.d.f;
import com.ad4screen.sdk.d.i;
import com.ad4screen.sdk.service.modules.b.a.a;

import org.json.JSONObject;

public class b extends c {
    private final String c = "com.ad4screen.sdk.service.modules.authentication.AuthenticationTask";
    private final Context d;
    private String e;
    private com.ad4screen.sdk.d.b f;

    public b(Context context) {
        super(context);
        this.d = context;
        this.f = com.ad4screen.sdk.d.b.a(this.d);
    }

    protected void a(String str) {
        try {
            Log.internal("AuthenticationTask|Authentication response start parsing");
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.isNull("access_token") || jSONObject.isNull("token_type")) {
                Log.error("AuthenticationTask|Response parsing failed");
                f.a().a(new a());
                return;
            }
            Log.internal("AuthenticationTask|Received Token : " + jSONObject.getString("access_token"));
            d.a(this.d).e(com.ad4screen.sdk.d.d.b.AuthenticationWebservice);
            com.ad4screen.sdk.service.modules.b.a.a aVar = new com.ad4screen.sdk.service.modules.b.a.a(jSONObject.getString("access_token"), jSONObject.getString("token_type"));
            i.a(this.d).a(aVar);
            boolean z = false;
            if (!jSONObject.isNull("sharedId")) {
                String string = jSONObject.getString("sharedId");
                if (this.f.c() == null || !this.f.c().equals(string)) {
                    this.f.b(string);
                    z = true;
                }
            }
            Log.debug("AuthenticationTask|Authentication succeed. Shared Id : " + this.f.c());
            f.a().a(new com.ad4screen.sdk.service.modules.b.a.b(aVar, z));
        } catch (Throwable e) {
            Log.debug("AuthenticationTask|Response JSON Parsing error!", e);
            f.a().a(new a());
        }
    }

    protected void a(Throwable th) {
        Log.error("AuthenticationTask|Authentication failed.");
        f.a().a(new a());
    }

    protected boolean a() {
        if (this.f.c() == null) {
            Log.warn("AuthenticationTask|No sharedId, skipping configuration");
            f.a().a(new a());
            return false;
        } else if (d.a(this.d).b(com.ad4screen.sdk.d.d.b.AuthenticationWebservice)) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("partnerId", this.f.l());
                JSONObject jSONObject2 = new JSONObject();
                if (this.f.J()) {
                    String b = this.f.b(this.d);
                    if (b != null) {
                        Log.debug("AdvertiserPlugin|Advertiser id returned from plugin : " + b);
                        jSONObject2.put("idfa", b);
                    } else {
                        Log.debug("TrackingTask|No Advertiser id found, using android id : " + this.f.j());
                        jSONObject2.put("androidid", this.f.j());
                    }
                }
                jSONObject2.put("idfv", this.f.k());
                jSONObject.put("deviceId", jSONObject2);
                jSONObject.put("sharedId", this.f.c());
                this.e = jSONObject.toString();
                a(4);
                i();
                return true;
            } catch (Throwable e) {
                Log.error("AuthenticationTask|Could not build message to send to Ad4Screen", e);
                f.a().a(new a());
                return false;
            }
        } else {
            Log.debug("Service interruption on AuthenticationTask");
            f.a().a(new a());
            return false;
        }
    }

    public c b(c cVar) {
        return cVar;
    }

    protected String c() {
        return com.ad4screen.sdk.d.d.b.AuthenticationWebservice.toString();
    }

    protected String d() {
        return this.e;
    }

    protected String e() {
        return d.a(this.d).a(com.ad4screen.sdk.d.d.b.AuthenticationWebservice);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.authentication.AuthenticationTask";
    }
}
