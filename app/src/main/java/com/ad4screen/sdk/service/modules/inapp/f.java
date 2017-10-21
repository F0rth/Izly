package com.ad4screen.sdk.service.modules.inapp;

import android.content.Context;
import android.os.Bundle;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.analytics.Lead;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.service.modules.inapp.a.e;
import com.ad4screen.sdk.service.modules.inapp.a.i;
import com.ad4screen.sdk.service.modules.inapp.a.j;
import com.ad4screen.sdk.service.modules.inapp.a.k;
import com.ad4screen.sdk.service.modules.inapp.b.a;
import com.ad4screen.sdk.service.modules.inapp.b.b;
import com.ad4screen.sdk.service.modules.inapp.b.c;
import com.ad4screen.sdk.service.modules.inapp.b.d;
import com.ad4screen.sdk.service.modules.inapp.b.g;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class f {
    public com.ad4screen.sdk.service.modules.inapp.a.f a;
    private Context b;

    public f(Context context) {
        this.b = context;
    }

    private void a(HashMap<String, String> hashMap, JSONArray jSONArray) {
        if (hashMap != null) {
            for (int i = 0; i < jSONArray.length(); i++) {
                try {
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    String b = i.b(Lead.KEY_VALUE, jSONObject);
                    String b2 = i.b("key", jSONObject);
                    if (!(b == null || b2 == null)) {
                        hashMap.put(b2, b);
                    }
                } catch (Throwable e) {
                    Log.error("InApp|Error parsing customs params", e);
                }
            }
        }
    }

    private void a(JSONObject jSONObject, e eVar) throws JSONException {
        JSONObject jSONObject2 = jSONObject.getJSONObject("globalCapping");
        if (jSONObject2.has("inapp")) {
            JSONObject jSONObject3 = jSONObject2.getJSONObject("inapp");
            if (jSONObject3.has("duration") && jSONObject3.has("frequency")) {
                eVar.a(Integer.valueOf(jSONObject3.getInt("frequency")));
                eVar.a(Long.valueOf(jSONObject3.getLong("duration") * 1000));
            } else {
                Log.debug("InAppResponseParser|Impossible to parse In-App global capping");
            }
        } else {
            Log.debug("InAppResponseParser|No In-App global capping information");
        }
        if (jSONObject2.has("alarm")) {
            jSONObject2 = jSONObject2.getJSONObject("alarm");
            if (jSONObject2.has("duration") && jSONObject2.has("frequency")) {
                eVar.b(Integer.valueOf(jSONObject2.getInt("frequency")));
                eVar.b(Long.valueOf(jSONObject2.getLong("duration") * 1000));
                return;
            }
            Log.debug("InAppResponseParser|Impossible to parse Alarm global capping");
            return;
        }
        Log.debug("InAppResponseParser|No Alarm global capping information");
    }

    private k b(JSONObject jSONObject) throws JSONException {
        k kVar = new k();
        if (!jSONObject.isNull("views")) {
            kVar.c(new g().a(jSONObject.getJSONArray("views")));
        }
        if (!jSONObject.isNull("events")) {
            kVar.e(new c().a(jSONObject.getJSONArray("events")));
        }
        if (!jSONObject.isNull("states")) {
            kVar.d(new com.ad4screen.sdk.service.modules.inapp.b.f().a(jSONObject.getJSONArray("states")));
        }
        if (!jSONObject.isNull("locations")) {
            kVar.a(new com.ad4screen.sdk.service.modules.inapp.b.e().a(jSONObject.getJSONArray("locations")));
        }
        if (!jSONObject.isNull("dateRanges")) {
            kVar.b(new b().a(jSONObject.getJSONArray("dateRanges")));
        }
        if (!jSONObject.isNull("geofences")) {
            kVar.f(new d().a(jSONObject.getJSONArray("geofences")));
        }
        if (!jSONObject.isNull("beacons")) {
            kVar.g(new a().a(jSONObject.getJSONArray("beacons")));
        }
        return kVar;
    }

    private Long c(JSONObject jSONObject) {
        Integer d = i.d("timer", jSONObject);
        return d != null ? Long.valueOf((long) (d.intValue() * 1000)) : null;
    }

    private Long d(JSONObject jSONObject) {
        Integer d = i.d("sessionTimer", jSONObject);
        return d != null ? Long.valueOf((long) (d.intValue() * 1000)) : null;
    }

    private Long e(JSONObject jSONObject) {
        Integer d = i.d("pressure", jSONObject);
        return d != null ? Long.valueOf((long) (d.intValue() * 1000)) : null;
    }

    private com.ad4screen.sdk.c.a.d f(JSONObject jSONObject) {
        if (jSONObject.isNull("id")) {
            return null;
        }
        try {
            com.ad4screen.sdk.c.a.d bVar = new com.ad4screen.sdk.service.modules.a.a.b();
            bVar.h = i.b("id", jSONObject);
            JSONObject jSONObject2 = jSONObject.getJSONObject("data");
            if (!jSONObject2.isNull("alarms")) {
                JSONArray jSONArray = jSONObject2.getJSONArray("alarms");
                bVar.a = new String[jSONArray.length()];
                for (int i = 0; i < jSONArray.length(); i++) {
                    bVar.a[i] = jSONArray.getJSONObject(i).getString("id");
                }
            }
            if (!jSONObject2.isNull("controlGroup")) {
                bVar.o = i.c("controlGroup", jSONObject2).booleanValue();
            }
            return bVar;
        } catch (Throwable e) {
            Log.error("InApp|Error parsing cancelAlarm format", e);
            return null;
        }
    }

    private com.ad4screen.sdk.service.modules.a.a.c g(JSONObject jSONObject) {
        if (jSONObject.isNull("id")) {
            return null;
        }
        try {
            HashMap hashMap;
            Set<String> keySet;
            com.ad4screen.sdk.service.modules.a.a.c cVar = new com.ad4screen.sdk.service.modules.a.a.c();
            cVar.h = i.b("id", jSONObject);
            JSONObject jSONObject2 = jSONObject.getJSONObject("data");
            cVar.j = i.b("clickTrackingUrl", jSONObject2);
            cVar.i = i.b("displayTrackingUrl", jSONObject2);
            cVar.c(i.b("cancelTrackingUrl", jSONObject2));
            cVar.a(i.c("allowUpdate", jSONObject2).booleanValue());
            Bundle bundle = new Bundle();
            bundle.putString("isAlarm", "true");
            if (!jSONObject2.isNull("pushPayload")) {
                JSONArray jSONArray = jSONObject2.getJSONArray("pushPayload");
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject jSONObject3 = jSONArray.getJSONObject(i);
                    bundle.putString(jSONObject3.getString("name"), jSONObject3.getString(Lead.KEY_VALUE));
                }
            }
            bundle.putString("a4sid", i.b("id", jSONObject));
            bundle.putString("a4sforeground", i.b("allowForegroundDisplay", jSONObject2));
            if (!jSONObject2.isNull("at")) {
                cVar.b(h.a(jSONObject2.getString("at"), h.a.ISO8601));
                cVar.a(10000);
            }
            if (!jSONObject2.isNull("in")) {
                long j = (long) (jSONObject2.getInt("in") * 1000);
                if (j < 1000) {
                    j = 1000;
                }
                cVar.a(j);
            }
            if (!jSONObject2.isNull("displayCustomParams")) {
                hashMap = new HashMap();
                a(hashMap, jSONObject2.getJSONArray("displayCustomParams"));
                keySet = hashMap.keySet();
                cVar.a((Set) keySet);
                for (String str : keySet) {
                    bundle.putString(str, (String) hashMap.get(str));
                }
            }
            if (!jSONObject2.isNull("clickCustomParams")) {
                hashMap = new HashMap();
                a(hashMap, jSONObject2.getJSONArray("clickCustomParams"));
                keySet = hashMap.keySet();
                cVar.b((Set) keySet);
                for (String str2 : keySet) {
                    bundle.putString(str2, (String) hashMap.get(str2));
                }
            }
            if (!jSONObject2.isNull("controlGroup")) {
                cVar.o = i.c("controlGroup", jSONObject2).booleanValue();
            }
            cVar.a(bundle);
            return cVar;
        } catch (Throwable e) {
            Log.error("InApp|Error parsing setAlarm format", e);
            return null;
        }
    }

    private com.ad4screen.sdk.c.a.a h(JSONObject jSONObject) {
        if (jSONObject.isNull("id")) {
            return null;
        }
        com.ad4screen.sdk.c.a.a aVar = new com.ad4screen.sdk.c.a.a();
        try {
            aVar.h = i.b("id", jSONObject);
            JSONObject jSONObject2 = jSONObject.getJSONObject("data");
            aVar.a = i.d("autoclose", jSONObject2);
            aVar.c.c = i.b("url", jSONObject2);
            aVar.c.a = i.b("title", jSONObject2);
            aVar.c.b = i.b("body", jSONObject2);
            aVar.c.d = i.b("template", jSONObject2);
            aVar.c.e = i.b("inAnimation", jSONObject2);
            aVar.c.f = i.b("outAnimation", jSONObject2);
            aVar.j = i.b("clickTrackingUrl", jSONObject2);
            aVar.i = i.b("displayTrackingUrl", jSONObject2);
            aVar.k = i.b("closeTrackingUrl", jSONObject2);
            aVar.b = i.c("interstitial", jSONObject2).booleanValue();
            if (!jSONObject2.isNull("canUseOverlay")) {
                aVar.g = i.c("canUseOverlay", jSONObject2).booleanValue();
            }
            if (!jSONObject2.isNull("displayCustomParams")) {
                a(aVar.e, jSONObject2.getJSONArray("displayCustomParams"));
            }
            if (!jSONObject2.isNull("clickCustomParams")) {
                a(aVar.f, jSONObject2.getJSONArray("clickCustomParams"));
            }
            if (!jSONObject2.isNull("landingPage")) {
                JSONObject jSONObject3 = jSONObject2.getJSONObject("landingPage");
                com.ad4screen.sdk.c.a.d eVar = new com.ad4screen.sdk.c.a.e();
                eVar.h = aVar.h + '#' + "target";
                eVar.a.c = i.b("url", jSONObject3);
                eVar.a.a = i.b("title", jSONObject3);
                eVar.a.b = i.b("body", jSONObject3);
                eVar.a.d = i.b("template", jSONObject3);
                if (i.c("openWithSafari", jSONObject3).booleanValue()) {
                    eVar.b = com.ad4screen.sdk.c.a.e.a.System;
                }
                aVar.d = eVar;
            }
            if (jSONObject2.isNull("controlGroup")) {
                return aVar;
            }
            aVar.o = i.c("controlGroup", jSONObject2).booleanValue();
            return aVar;
        } catch (Throwable e) {
            Log.error("InApp|Error parsing banner format", e);
            return aVar;
        }
    }

    private com.ad4screen.sdk.c.a.g i(JSONObject jSONObject) {
        if (jSONObject.isNull("id")) {
            return null;
        }
        com.ad4screen.sdk.c.a.g gVar = new com.ad4screen.sdk.c.a.g();
        try {
            gVar.h = i.b("id", jSONObject);
            JSONObject jSONObject2 = jSONObject.getJSONObject("data");
            gVar.a(i.b("title", jSONObject2));
            gVar.c(i.b("body", jSONObject2));
            gVar.i = i.b("displayTrackingUrl", jSONObject2);
            gVar.j = i.b("clickTrackingUrl", jSONObject2);
            if (!jSONObject2.isNull("controlGroup")) {
                gVar.o = i.c("controlGroup", jSONObject2).booleanValue();
            }
            if (!jSONObject2.isNull("displayCustomParams")) {
                a(gVar.c(), jSONObject2.getJSONArray("displayCustomParams"));
            }
            if (!jSONObject2.isNull("buttons")) {
                ArrayList arrayList = new ArrayList();
                JSONArray jSONArray = jSONObject2.getJSONArray("buttons");
                for (int i = 0; i < jSONArray.length(); i++) {
                    arrayList.add(a(gVar.h, String.valueOf(i), jSONArray.getJSONObject(i)));
                }
                gVar.a((com.ad4screen.sdk.c.a.g.a[]) arrayList.toArray(new com.ad4screen.sdk.c.a.g.a[arrayList.size()]));
            }
        } catch (Throwable e) {
            Log.error("InApp|Error parsing popup format", e);
        }
        return gVar;
    }

    private com.ad4screen.sdk.c.a.e j(JSONObject jSONObject) {
        if (!jSONObject.isNull("id")) {
            com.ad4screen.sdk.c.a.e eVar = new com.ad4screen.sdk.c.a.e();
            try {
                eVar.h = i.b("id", jSONObject);
                eVar.b = com.ad4screen.sdk.c.a.e.a.System;
                JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                String b = i.b("url", jSONObject2);
                if (b != null) {
                    if (!jSONObject2.isNull("controlGroup")) {
                        eVar.o = i.c("controlGroup", jSONObject2).booleanValue();
                    }
                    eVar.a.c = b;
                    return eVar;
                }
            } catch (Throwable e) {
                Log.error("InApp|Error parsing system action format", e);
                return eVar;
            }
        }
        return null;
    }

    private com.ad4screen.sdk.c.a.c k(JSONObject jSONObject) {
        if (!jSONObject.isNull("id")) {
            com.ad4screen.sdk.c.a.c cVar = new com.ad4screen.sdk.c.a.c();
            try {
                cVar.h = i.b("id", jSONObject);
                JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                cVar.a = i.b("body", jSONObject2);
                if (!jSONObject2.isNull("controlGroup")) {
                    cVar.o = i.c("controlGroup", jSONObject2).booleanValue();
                }
                if (cVar.a != null) {
                    return cVar;
                }
            } catch (Throwable e) {
                Log.error("InApp|Error parsing file format", e);
                return cVar;
            }
        }
        return null;
    }

    private com.ad4screen.sdk.service.modules.inapp.a.h l(JSONObject jSONObject) {
        com.ad4screen.sdk.c.a.d h;
        String b = i.b("type", jSONObject);
        if ("banner".equalsIgnoreCase(b)) {
            h = h(jSONObject);
        } else if ("popmessage".equalsIgnoreCase(b)) {
            try {
                JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                if (!jSONObject2.isNull("action") && "system".equalsIgnoreCase(jSONObject2.getString("action"))) {
                    h = k(jSONObject);
                    if (h == null) {
                        h = i(jSONObject);
                    }
                }
            } catch (Throwable e) {
                Log.error("InApp|Failed to parse file format : ", e);
            }
            h = null;
            if (h == null) {
                h = i(jSONObject);
            }
        } else {
            h = "browser".equalsIgnoreCase(b) ? j(jSONObject) : "setAlarm".equalsIgnoreCase(b) ? g(jSONObject) : "cancelAlarm".equalsIgnoreCase(b) ? f(jSONObject) : null;
        }
        if (h == null) {
            return null;
        }
        com.ad4screen.sdk.service.modules.inapp.a.h hVar = new com.ad4screen.sdk.service.modules.inapp.a.h();
        hVar.a(h);
        return hVar;
    }

    public com.ad4screen.sdk.c.a.g.a a(String str, String str2, JSONObject jSONObject) throws JSONException {
        com.ad4screen.sdk.c.a.g.a aVar = new com.ad4screen.sdk.c.a.g.a();
        aVar.a(i.b("id", jSONObject));
        String b = i.b("action", jSONObject);
        com.ad4screen.sdk.c.a.d eVar;
        if ("browser".equalsIgnoreCase(b)) {
            eVar = new com.ad4screen.sdk.c.a.e();
            eVar.h = str + '#' + str2;
            eVar.b = com.ad4screen.sdk.c.a.e.a.System;
            eVar.a.c = i.b("url", jSONObject);
            aVar.a(eVar);
        } else if ("urlExec".equalsIgnoreCase(b)) {
            eVar = new com.ad4screen.sdk.c.a.h();
            eVar.h = str + "#" + str2;
            eVar.a = i.b("url", jSONObject);
            aVar.a(eVar);
        } else if ("webView".equalsIgnoreCase(b)) {
            eVar = new com.ad4screen.sdk.c.a.e();
            eVar.h = str + "#" + str2;
            eVar.a.d = "com_ad4screen_sdk_template_interstitial";
            eVar.a.c = i.b("url", jSONObject);
            aVar.a(eVar);
        }
        aVar.b(i.b("title", jSONObject));
        if (!jSONObject.isNull("icon")) {
            aVar.a(this.b.getResources().getIdentifier(jSONObject.getString("icon"), "drawable", this.b.getPackageName()));
        }
        if (!jSONObject.isNull("clickCustomParams")) {
            a(aVar.e(), jSONObject.getJSONArray("clickCustomParams"));
        }
        return aVar;
    }

    protected void a(LinkedList<j> linkedList, JSONObject jSONObject, String str) throws JSONException {
        j jVar = new j();
        jVar.b(str);
        if (!jSONObject.isNull("basics")) {
            JSONObject jSONObject2 = jSONObject.getJSONObject("basics");
            jVar.a(i.c("displayOnlyOnceByEvent", jSONObject2).booleanValue());
            jVar.a(h.a(i.b("startDate", jSONObject2), h.a.ISO8601));
            jVar.b(h.a(i.b("endDate", jSONObject2), h.a.ISO8601));
            jVar.a(i.d("capping", jSONObject2));
            Integer d = i.d("priority", jSONObject2);
            jVar.a(d == null ? 0 : d.intValue());
            jVar.b(i.d("globalClickCapping", jSONObject2));
            jVar.c(i.d("sessionClickCapping", jSONObject2));
            jVar.d(i.d("delayCapping", jSONObject2));
            jVar.a(c(jSONObject2));
            jVar.b(d(jSONObject2));
            jVar.c(e(jSONObject2));
            jVar.b(i.c("excludeFromGlobalCapping", jSONObject2).booleanValue());
            jVar.c(i.c("isIncludedInGlobalCappingCount", jSONObject2).booleanValue());
            jVar.d(i.c("offlineDisplay", jSONObject2).booleanValue());
            String b = i.b("networkRestriction", jSONObject2);
            if (b != null) {
                if ("3g".equalsIgnoreCase(b)) {
                    jVar.a(j.a.Cellular);
                } else if ("wifi".equalsIgnoreCase(b)) {
                    jVar.a(j.a.Wifi);
                }
            }
        }
        if (!jSONObject.isNull("inclusions")) {
            jVar.a(b(jSONObject.getJSONObject("inclusions")));
        }
        if (!jSONObject.isNull("exclusions")) {
            jVar.b(b(jSONObject.getJSONObject("exclusions")));
        }
        linkedList.add(jVar);
    }

    public void a(JSONObject jSONObject) {
        this.a = new com.ad4screen.sdk.service.modules.inapp.a.f();
        LinkedList linkedList = new LinkedList();
        LinkedList linkedList2 = new LinkedList();
        e eVar = new e();
        try {
            if (jSONObject.has("globalCapping")) {
                a(jSONObject, eVar);
            } else {
                Log.debug("InAppResponseParser|No global capping information");
            }
            JSONArray jSONArray = jSONObject.getJSONArray("notifications");
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                com.ad4screen.sdk.service.modules.inapp.a.h l = l(jSONObject2);
                if (l != null) {
                    linkedList2.add(l);
                    if (!jSONObject2.isNull("rules")) {
                        a(linkedList, jSONObject2.getJSONObject("rules"), l.a().h);
                    }
                }
            }
        } catch (Throwable e) {
            Log.error("InApp|Failed to parse configuration : ", e);
        }
        this.a.a = new j[linkedList.size()];
        linkedList.toArray(this.a.a);
        Iterator it = linkedList2.iterator();
        while (it.hasNext()) {
            com.ad4screen.sdk.service.modules.inapp.a.h hVar = (com.ad4screen.sdk.service.modules.inapp.a.h) it.next();
            this.a.b.put(hVar.a().h, hVar);
        }
        this.a.a(eVar);
    }
}
