package com.ad4screen.sdk.common.e;

import android.content.Context;
import android.net.Uri;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.d.f;
import com.ad4screen.sdk.service.modules.d.b;
import com.ad4screen.sdk.service.modules.e.e;
import com.ad4screen.sdk.service.modules.g.d;
import com.ad4screen.sdk.service.modules.k.g;
import com.ad4screen.sdk.service.modules.k.h;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class a extends c {
    private final String c = "com.ad4screen.sdk.common.tasks.BulkManager";
    private Context d;
    private ArrayList<c> e = new ArrayList();
    private String f;
    private boolean g;

    public a(Context context) {
        super(context);
        this.d = context;
        a(0);
        i();
    }

    public void a(c cVar) {
        c hVar;
        if (cVar instanceof g) {
            this.g = true;
            hVar = new h(this.d);
            if (hVar.a()) {
                this.e.add(hVar);
                this.e.add(cVar);
                hVar = new b(this.d);
                if (hVar.a()) {
                    this.e.add(hVar);
                    return;
                } else {
                    Log.debug("BulkManager|ConfigurationTrackingTask can't be launched right now because of service interruption.");
                    return;
                }
            }
            Log.debug("BulkManager|TrackingTask can't be launched right now because of service interruption on VersionTrackingTask.");
            return;
        }
        this.e.add(cVar);
        if (cVar instanceof d) {
            int i = 0;
            while (i < this.e.size()) {
                if ((this.e.get(i) instanceof e) || (this.e.get(i) instanceof com.ad4screen.sdk.service.modules.e.d)) {
                    hVar = (c) this.e.get(i);
                    this.e.remove(i);
                    this.e.add(hVar);
                    this.g = true;
                }
                i++;
            }
        }
    }

    protected void a(String str) {
        JSONArray jSONArray = new JSONObject(str).getJSONArray("_bulk");
        for (int i = 0; i < this.e.size(); i++) {
            c cVar = (c) this.e.get(i);
            try {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                try {
                    if (!jSONObject.isNull("code")) {
                        String jSONObject2 = jSONObject.toString();
                        int i2 = jSONObject.getInt("code");
                        if (!jSONObject.isNull("body")) {
                            jSONObject2 = jSONObject.getJSONObject("body").toString();
                        }
                        if (i2 == 200) {
                            cVar.a(jSONObject2);
                            f.a().a(new d.b(cVar, cVar.c()));
                        } else if (!cVar.a(i2, jSONObject2)) {
                            cVar.a(new ConnectException("Could not reach Accengage servers"));
                            f.a().a(new com.ad4screen.sdk.common.e.d.a(cVar, cVar.c()));
                        }
                    }
                    if (!jSONObject.isNull("links")) {
                        com.ad4screen.sdk.service.modules.d.h.a(this.d, jSONObject.getJSONObject("links").getString("next"), new com.ad4screen.sdk.common.e[0]);
                    }
                } catch (Throwable e) {
                    a(new JSONException("BulkManager failed to read response from server").initCause(e));
                    return;
                }
            } catch (Throwable e2) {
                Log.internal("BulkManager|Impossible to get response Json object", e2);
                cVar.a(new ConnectException("Could not reach Accengage servers"));
                f.a().a(new com.ad4screen.sdk.common.e.d.a(cVar, cVar.c()));
            }
        }
        this.e.clear();
    }

    protected void a(Throwable th) {
        Iterator it = this.e.iterator();
        while (it.hasNext()) {
            c cVar = (c) it.next();
            cVar.a(new ConnectException("BulkManager request failed").initCause(th));
            f.a().a(new com.ad4screen.sdk.common.e.d.a(cVar, cVar.c()));
        }
        this.e.clear();
    }

    protected boolean a() {
        return true;
    }

    protected boolean a(int i, String str) {
        if (i != 404) {
            return super.a(i, str);
        }
        Log.internal("BulkManager|404 error on _bulk request. Has this _bulk been wrongly sent in GET?");
        return true;
    }

    public c b(c cVar) {
        return cVar;
    }

    protected void b(int i, String str) {
        String k = k();
        Log.internal(k + " HttpRootResp[" + String.valueOf(i) + "] " + e() + (str == null ? "" : " Content=" + str));
        if (str != null) {
            JSONArray jSONArray = new JSONObject(str).getJSONArray("_bulk");
            for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                String str2;
                JSONObject jSONObject = jSONArray.getJSONObject(i2);
                if (jSONObject.isNull("code")) {
                    str2 = "";
                } else {
                    try {
                        str2 = jSONObject.getString("code");
                    } catch (Throwable e) {
                        Log.internal(e);
                        return;
                    }
                }
                String string = jSONObject.isNull("body") ? null : jSONObject.getString("body");
                Log.internal(k + " HttpResp[" + i2 + "][" + str2 + "] " + (string == null ? "" : " Content=" + string));
            }
        }
    }

    public boolean b() {
        return this.e.size() == 0;
    }

    protected String c() {
        return com.ad4screen.sdk.d.d.b.BulkWebservice.toString();
    }

    protected String d() {
        return this.f;
    }

    protected String e() {
        return com.ad4screen.sdk.d.d.a(this.d).a(com.ad4screen.sdk.d.d.b.BulkWebservice);
    }

    protected void f() {
        String k = k();
        String e = e();
        String d = d();
        Log.internal(k + " HttpRootReq[" + this.a.getRequestMethod() + "] " + e + (d == null ? "" : " Content=" + d));
        if (d != null) {
            JSONArray jSONArray = new JSONObject(d).getJSONArray("_bulk");
            int i = 0;
            while (i < jSONArray.length()) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                String string = jSONObject.getString("url");
                String string2 = jSONObject.getString("method");
                e = jSONObject.isNull("body") ? null : jSONObject.getString("body");
                try {
                    Log.internal(k + " HttpReq[" + i + "][" + string2 + "] " + string + (e == null ? "" : " Content=" + e));
                    i++;
                } catch (Throwable e2) {
                    Log.internal(e2);
                    return;
                }
            }
        }
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.common.tasks.BulkManager";
    }

    public void run() {
        try {
            com.ad4screen.sdk.d.b a = com.ad4screen.sdk.d.b.a(this.d);
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("sync", this.g);
            JSONArray jSONArray = new JSONArray();
            Iterator it = this.e.iterator();
            while (it.hasNext()) {
                c cVar = (c) it.next();
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("url", com.ad4screen.sdk.common.h.a(this.d, cVar.e(), new com.ad4screen.sdk.common.e("partnerId", Uri.encode(a.l())), new com.ad4screen.sdk.common.e("sharedId", Uri.encode(a.c()))));
                jSONObject2.put("method", cVar.d(cVar.d()));
                if (cVar.d() != null && cVar.d().length() > 0) {
                    jSONObject2.put("body", new JSONObject(cVar.d()));
                }
                jSONArray.put(jSONObject2);
            }
            jSONObject.put("_bulk", jSONArray);
            this.f = jSONObject.toString();
            if (a()) {
                g();
            }
        } catch (Throwable e) {
            a(new JSONException("BulkManager failed to construct valid request").initCause(e));
        }
    }
}
