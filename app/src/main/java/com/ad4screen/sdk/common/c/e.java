package com.ad4screen.sdk.common.c;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.widget.FrameLayout.LayoutParams;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.c.a.a.a;
import com.ad4screen.sdk.common.c.a.a.b;
import com.ad4screen.sdk.common.c.a.c;
import com.ad4screen.sdk.common.c.a.d;
import com.ad4screen.sdk.common.c.a.f;
import com.ad4screen.sdk.common.c.a.g;
import com.ad4screen.sdk.common.c.a.h;
import com.ad4screen.sdk.common.c.a.i;
import com.ad4screen.sdk.common.c.a.j;
import com.ad4screen.sdk.common.c.a.k;
import com.ad4screen.sdk.common.c.a.l;
import com.ad4screen.sdk.common.c.a.m;
import com.ad4screen.sdk.common.c.a.n;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONException;
import org.json.JSONObject;

public class e {
    private final HashMap<Class<?>, b<?>> a = new HashMap();
    private final HashMap<String, a<?>> b = new HashMap();

    public e() {
        this.a.put(Intent.class, new j());
        this.a.put(Bundle.class, new d());
        this.a.put(HashMap.class, new h());
        this.a.put(ConcurrentHashMap.class, new f());
        this.a.put(Location.class, new n());
        this.a.put(LayoutParams.class, new l());
        this.a.put(ArrayList.class, new com.ad4screen.sdk.common.c.a.b());
        i iVar = new i();
        c cVar = new c();
        g gVar = new g();
        com.ad4screen.sdk.common.c.a.e eVar = new com.ad4screen.sdk.common.c.a.e();
        m mVar = new m();
        k kVar = new k();
        com.ad4screen.sdk.common.c.a.a aVar = new com.ad4screen.sdk.common.c.a.a();
        this.b.put(iVar.a(), iVar);
        this.b.put(cVar.a(), cVar);
        this.b.put(gVar.a(), gVar);
        this.b.put(eVar.a(), eVar);
        this.b.put(mVar.a(), mVar);
        this.b.put(kVar.a(), kVar);
        this.b.put(aVar.a(), aVar);
    }

    public <T> T a(String str, T t) throws JSONException {
        if (t == null) {
            Log.error("SerializerManager|fromJSON Default object can't be null, aborting deserialization");
            return t;
        } else if (t instanceof c) {
            return ((c) t).fromJSON(str);
        } else {
            a aVar = (a) this.b.get(new JSONObject(str).getString("type"));
            return aVar != null ? aVar.b(str) : t;
        }
    }

    public <T> JSONObject a(T t) throws JSONException {
        if (t == null) {
            return null;
        }
        b bVar = (b) this.a.get(t.getClass());
        return bVar != null ? bVar.a(t) : t instanceof d ? ((d) t).toJSON() : null;
    }
}
