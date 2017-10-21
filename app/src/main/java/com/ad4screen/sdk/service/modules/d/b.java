package com.ad4screen.sdk.service.modules.d;

import android.content.Context;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.analytics.Lead;
import com.ad4screen.sdk.common.e.c;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.common.h.a;
import com.ad4screen.sdk.d.d;
import com.ad4screen.sdk.d.f;

import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

public class b extends c {
    private final String c = "com.ad4screen.sdk.service.modules.common.ConfigurationTask";
    private final Context d;
    private com.ad4screen.sdk.d.b e;

    public b(Context context) {
        super(context);
        this.d = context;
        this.e = com.ad4screen.sdk.d.b.a(this.d);
    }

    protected void a(String str) {
        int i = 0;
        Log.internal("ConfigurationTask|Configuration request succeed with response : " + str);
        d.a(this.d).e(com.ad4screen.sdk.d.d.b.ConfigurationWebservice);
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (!jSONObject.isNull("facebookId")) {
                String K = this.e.K();
                String valueOf = String.valueOf(jSONObject.getInt("facebookId"));
                this.e.d(valueOf);
                if (!K.equals(valueOf)) {
                    f.a().a(new com.ad4screen.sdk.service.modules.k.f.b(valueOf));
                }
                Log.debug("ConfigurationTask|Facebook App Id Updated to " + valueOf);
            }
            d.a(this.d).c();
            d.a(this.d).d();
            if (!jSONObject.isNull("interruptions")) {
                JSONArray jSONArray = jSONObject.getJSONArray("interruptions");
                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                    com.ad4screen.sdk.service.modules.k.d.d dVar = new com.ad4screen.sdk.service.modules.k.d.d();
                    dVar.a = jSONArray.getJSONObject(i2).getString("serviceName");
                    dVar.b = jSONArray.getJSONObject(i2).getInt("frequency");
                    dVar.c = h.a(jSONArray.getJSONObject(i2).getString("nextTime"), a.ISO8601).getTime();
                    com.ad4screen.sdk.d.d.b a = com.ad4screen.sdk.d.d.b.a(dVar.a);
                    if (a == null) {
                        Log.internal("ConfigurationTask|Could not find service with name '" + dVar.a + "'");
                    } else {
                        d.a(this.d).a(a, dVar.b);
                        d.a(this.d).a(a, dVar.c);
                        Log.warn("ConfigurationTask|Updated '" + dVar.a + "' interruption date to " + h.a(new Date(dVar.c), a.ISO8601) + " and frequency " + dVar.b);
                    }
                }
                Log.debug("ConfigurationTask|Service interruptions updated");
            }
            if (!jSONObject.isNull("events")) {
                JSONArray jSONArray2 = jSONObject.getJSONArray("events");
                com.ad4screen.sdk.service.modules.k.d.c cVar = new com.ad4screen.sdk.service.modules.k.d.c();
                cVar.a = new com.ad4screen.sdk.service.modules.k.d.b[jSONArray2.length()];
                while (i < jSONArray2.length()) {
                    cVar.a[i] = new com.ad4screen.sdk.service.modules.k.d.b();
                    cVar.a[i].a = String.valueOf(jSONArray2.getJSONObject(i).getInt("name"));
                    cVar.a[i].b = jSONArray2.getJSONObject(i).getInt(Lead.KEY_VALUE);
                    Log.debug("ConfigurationTask|Updated Event " + cVar.a[i].a + " dispatch to " + cVar.a[i].b);
                    i++;
                }
                Log.debug("ConfigurationTask|Event dispatch updated");
                f.a().a(new com.ad4screen.sdk.service.modules.k.f.a(cVar));
            }
            if (!jSONObject.isNull("lastReloadRoutes")) {
                this.e.a(h.a(jSONObject.getString("lastReloadRoutes"), a.ISO8601));
                Log.debug("ConfigurationTask|Last reload routes date updated to " + h.a(jSONObject.getString("lastReloadRoutes"), a.ISO8601));
            }
        } catch (Throwable e) {
            Log.internal("Accengage|Could not parse server response", e);
        }
        if (this.e.g() != null && this.e.f() != null && this.e.g().before(this.e.O())) {
            Log.debug("ConfigurationTask|We need to refresh routes, refreshing now...");
            com.ad4screen.sdk.common.a.a.a(this.d).a(new c(this.d));
        }
    }

    protected void a(Throwable th) {
        Log.internal("ConfigurationTask|Tracking failed", th);
    }

    protected boolean a() {
        a(4);
        i();
        j();
        if (!d.a(this.d).b(com.ad4screen.sdk.d.d.b.ConfigurationWebservice)) {
            Log.debug("Service interruption on ConfigurationTask");
            return false;
        } else if (this.e.c() != null) {
            return true;
        } else {
            Log.warn("ConfigurationTask|No sharedId, skipping configuration");
            return false;
        }
    }

    public c b(c cVar) {
        return cVar;
    }

    protected String c() {
        return com.ad4screen.sdk.d.d.b.ConfigurationWebservice.toString();
    }

    protected String d() {
        return null;
    }

    protected String e() {
        return d.a(this.d).a(com.ad4screen.sdk.d.d.b.ConfigurationWebservice);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.common.ConfigurationTask";
    }
}
