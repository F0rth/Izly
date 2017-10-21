package com.ad4screen.sdk.c.a;

import com.ad4screen.sdk.common.c.c;
import com.ad4screen.sdk.common.c.e;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

public class d implements c<d>, com.ad4screen.sdk.common.c.d {
    private static Pattern b = Pattern.compile("#\\{(.+?)\\}");
    private final String a = "controlGroup";
    public String h;
    public String i;
    public String j;
    public String k;
    public String l;
    public String m;
    public e n = new e();
    public boolean o;

    public interface a {
        void onPersonalParamFound(String str, StringBuffer stringBuffer);
    }

    public enum b {
        None,
        URLConnection
    }

    public static String a(String str, a aVar) {
        Matcher matcher = b.matcher(str);
        while (matcher.find()) {
            if (matcher.groupCount() == 1) {
                StringBuffer stringBuffer = new StringBuffer("");
                if (aVar != null) {
                    aVar.onPersonalParamFound(matcher.group(1), stringBuffer);
                }
                str = str.replaceFirst("#\\{(.+?)\\}", stringBuffer.toString());
            }
        }
        return str;
    }

    public d b(String str) throws JSONException {
        com.ad4screen.sdk.service.modules.a.a.b bVar = new com.ad4screen.sdk.service.modules.a.a.b();
        com.ad4screen.sdk.service.modules.a.a.c cVar = new com.ad4screen.sdk.service.modules.a.a.c();
        a aVar = new a();
        c cVar2 = new c();
        e eVar = new e();
        f fVar = new f();
        g gVar = new g();
        h hVar = new h();
        JSONObject jSONObject = new JSONObject(str);
        String string = jSONObject.getString("type");
        d dVar = null;
        for (int i = 0; i < 8; i++) {
            d dVar2 = new d[]{bVar, cVar, aVar, cVar2, eVar, fVar, gVar, hVar}[i];
            if (string.equals(dVar2.getClassKey())) {
                dVar = (d) this.n.a(str, dVar2);
                break;
            }
        }
        if (dVar == null) {
            dVar = new d();
        }
        if (!jSONObject.isNull("id")) {
            dVar.h = jSONObject.getString("id");
        }
        if (!jSONObject.isNull("displayTrackingUrl")) {
            dVar.i = jSONObject.getString("displayTrackingUrl");
        }
        if (!jSONObject.isNull("clickTrackingUrl")) {
            dVar.j = jSONObject.getString("clickTrackingUrl");
        }
        if (!jSONObject.isNull("closeTrackingUrl")) {
            dVar.k = jSONObject.getString("closeTrackingUrl");
        }
        if (!jSONObject.isNull("controlGroup")) {
            dVar.o = jSONObject.getBoolean("controlGroup");
        }
        if (!jSONObject.isNull("beaconClientId")) {
            dVar.l = jSONObject.getString("beaconClientId");
        }
        if (!jSONObject.isNull("geofenceClientId")) {
            dVar.m = jSONObject.getString("geofenceClientId");
        }
        return dVar;
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return b(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.model.displayformats";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("id", this.h);
        jSONObject.put("clickTrackingUrl", this.j);
        jSONObject.put("displayTrackingUrl", this.i);
        jSONObject.put("closeTrackingUrl", this.k);
        jSONObject.put("controlGroup", this.o);
        jSONObject.put("beaconClientId", this.l);
        jSONObject.put("geofenceClientId", this.m);
        return jSONObject;
    }
}
