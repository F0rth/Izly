package com.ad4screen.sdk.service.modules.e;

import android.content.Context;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.e.c;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.d.d;
import com.ad4screen.sdk.d.d.b;
import com.ad4screen.sdk.d.f;
import com.ad4screen.sdk.service.modules.e.b.a;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class e extends c {
    private final String c = "com.ad4screen.sdk.service.modules.geofencing.LoadGeofencingConfigurationTask";
    private final String d = "lastUpdate";
    private final Context e;
    private Long f;

    public e(Context context, long j) {
        super(context);
        this.e = context;
        this.f = Long.valueOf(j);
    }

    protected void a(String str) {
        try {
            Log.internal("Geofencing Configuration|Geofencing start parsing");
            JSONObject jSONObject = new JSONObject(str);
            c cVar = new c();
            a aVar = new a();
            cVar.a(jSONObject, aVar);
            int size = aVar.a().size();
            if (size == 0 || aVar.b() == null) {
                Log.error("Geofencing Configuration|Geofencing parsing failed");
                f.a().a(new a());
                return;
            }
            Log.debug("Geofencing Configuration|Received " + size + " Geofences");
            d.a(this.e).e(b.GeofencingConfigurationWebservice);
            f.a().a(new b.b(aVar));
        } catch (Throwable e) {
            Log.internal("Geofencing Configuration|Response JSON Parsing error!", e);
            f.a().a(new a());
        }
    }

    protected void a(Throwable th) {
        Log.error("Geofencing Configuration|Failed to retrieve geofences configuration");
        f.a().a(new a());
    }

    protected boolean a() {
        i();
        j();
        if (com.ad4screen.sdk.d.b.a(this.e).c() == null) {
            Log.warn("Geofencing Configuration|No sharedId, skipping reception of geofences");
            f.a().a(new a());
            return false;
        } else if (d.a(this.e).c(b.GeofencingConfigurationWebservice)) {
            return true;
        } else {
            Log.debug("Service interruption on GeofencingConfigurationTask");
            return false;
        }
    }

    public c b(c cVar) {
        return cVar;
    }

    protected String c() {
        return b.GeofencingConfigurationWebservice.toString();
    }

    protected String d() {
        return null;
    }

    public c e(String str) throws JSONException {
        super.e(str);
        JSONObject jSONObject = new JSONObject(str).getJSONObject("com.ad4screen.sdk.service.modules.geofencing.LoadGeofencingConfigurationTask");
        if (!jSONObject.isNull("lastUpdate")) {
            this.f = Long.valueOf(jSONObject.getLong("lastUpdate"));
        }
        return this;
    }

    protected String e() {
        if (this.f.longValue() == 0) {
            return d.a(this.e).a(b.GeofencingConfigurationWebservice);
        }
        String a = h.a(new Date(this.f.longValue()), h.a.ISO8601);
        try {
            a = URLEncoder.encode(a, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.internal("LoadGeofencingConfigurationTas|Impossible to encode date");
        }
        return d.a(this.e).a(b.GeofencingConfigurationWebservice) + "?lastUpdate=" + a;
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return e(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.geofencing.LoadGeofencingConfigurationTask";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject toJSON = super.toJSON();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("lastUpdate", this.f);
        toJSON.put("com.ad4screen.sdk.service.modules.geofencing.LoadGeofencingConfigurationTask", jSONObject);
        return toJSON;
    }
}
