package com.ad4screen.sdk.service.modules.c;

import android.content.Context;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.e.c;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.d.d;
import com.ad4screen.sdk.d.d.b;
import com.ad4screen.sdk.service.modules.c.a.a;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class f extends c {
    private final String c = "com.ad4screen.sdk.service.modules.beacons.LoadBeaconConfigurationTask";
    private final String d = "lastUpdate";
    private final Context e;
    private Long f;

    public f(Context context, long j) {
        super(context);
        this.e = context;
        this.f = Long.valueOf(j);
    }

    protected void a(String str) {
        try {
            Log.internal("Beacons Configuration|Beacons start parsing");
            JSONObject jSONObject = new JSONObject(str);
            c cVar = new c();
            e eVar = new e();
            cVar.a(jSONObject, eVar);
            int size = eVar.a().size();
            if (size == 0 || eVar.b() == null) {
                Log.error("Beacons Configuration|Beacons parsing failed");
                com.ad4screen.sdk.d.f.a().a(new a());
                return;
            }
            Log.debug("Beacons Configuration|Received " + size + " Beacons");
            d.a(this.e).e(b.BeaconConfigurationWebservice);
            com.ad4screen.sdk.d.f.a().a(new a.b(eVar));
        } catch (Throwable e) {
            Log.internal("Beacons Configuration|Response JSON Parsing error!", e);
            com.ad4screen.sdk.d.f.a().a(new a());
        }
    }

    protected void a(Throwable th) {
        Log.error("Beacons Configuration|Failed to retrieve beacons configuration");
        com.ad4screen.sdk.d.f.a().a(new a());
    }

    protected boolean a() {
        i();
        j();
        if (com.ad4screen.sdk.d.b.a(this.e).c() == null) {
            Log.warn("Beacon Configuration|No sharedId, skipping reception of beacons");
            com.ad4screen.sdk.d.f.a().a(new a());
            return false;
        } else if (d.a(this.e).c(b.BeaconConfigurationWebservice)) {
            return true;
        } else {
            Log.debug("Service interruption on BeaconConfigurationTask");
            return false;
        }
    }

    public c b(c cVar) {
        return cVar;
    }

    protected String c() {
        return b.BeaconConfigurationWebservice.toString();
    }

    protected String d() {
        return null;
    }

    public c e(String str) throws JSONException {
        super.e(str);
        JSONObject jSONObject = new JSONObject(str).getJSONObject("com.ad4screen.sdk.service.modules.beacons.LoadBeaconConfigurationTask");
        if (!jSONObject.isNull("lastUpdate")) {
            this.f = Long.valueOf(jSONObject.getLong("lastUpdate"));
        }
        return this;
    }

    protected String e() {
        if (this.f.longValue() == 0) {
            return d.a(this.e).a(b.BeaconConfigurationWebservice);
        }
        String a = h.a(new Date(this.f.longValue()), h.a.ISO8601);
        try {
            a = URLEncoder.encode(a, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.internal("LoadGeofencingConfigurationTas|Impossible to encode date");
        }
        return d.a(this.e).a(b.BeaconConfigurationWebservice) + "?lastUpdate=" + a;
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return e(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.beacons.LoadBeaconConfigurationTask";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject toJSON = super.toJSON();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("lastUpdate", this.f);
        toJSON.put("com.ad4screen.sdk.service.modules.beacons.LoadBeaconConfigurationTask", jSONObject);
        return toJSON;
    }
}
