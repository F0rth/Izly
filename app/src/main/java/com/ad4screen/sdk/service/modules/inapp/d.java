package com.ad4screen.sdk.service.modules.inapp;

import com.ad4screen.sdk.common.c.c;
import com.ad4screen.sdk.common.c.e;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class d implements c<d>, com.ad4screen.sdk.common.c.d {
    private List<c> a = new ArrayList();
    private Object b = new Object();

    private void a() {
        synchronized (this.b) {
            Collections.sort(this.a, new Comparator<c>(this) {
                final /* synthetic */ d a;

                {
                    this.a = r1;
                }

                public int a(c cVar, c cVar2) {
                    return cVar.b().before(cVar2.b()) ? -1 : cVar.b().after(cVar2.b()) ? 1 : 0;
                }

                public /* synthetic */ int compare(Object obj, Object obj2) {
                    return a((c) obj, (c) obj2);
                }
            });
        }
    }

    private c c(String str) {
        a();
        synchronized (this.b) {
            for (int size = this.a.size() - 1; size >= 0; size--) {
                c cVar = (c) this.a.get(size);
                if (cVar.a().equals(str)) {
                    return cVar;
                }
            }
            return null;
        }
    }

    public List<c> a(Date date, Date date2, boolean z) {
        List<c> arrayList = new ArrayList();
        synchronized (this.b) {
            for (c cVar : this.a) {
                if (((z && cVar.c()) || !(z || cVar.c())) && cVar.b().after(date) && cVar.b().before(date2)) {
                    arrayList.add(cVar);
                }
            }
        }
        return arrayList;
    }

    public void a(String str) {
        a();
        c c = c(str);
        if (c != null) {
            synchronized (this.b) {
                this.a.remove(c);
            }
        }
    }

    public void a(String str, Date date, Class<?> cls) {
        if (str != null && date != null) {
            synchronized (this.b) {
                this.a.add(new c(str, date, cls));
            }
        }
    }

    public void a(Date date, boolean z) {
        Collection arrayList = new ArrayList();
        synchronized (this.b) {
            for (c cVar : this.a) {
                if (z != cVar.c() || cVar.b().after(date)) {
                    arrayList.add(cVar);
                }
            }
            this.a.clear();
            this.a.addAll(arrayList);
        }
    }

    public int b(Date date, Date date2, boolean z) {
        return a(date, date2, z).size();
    }

    public d b(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str).getJSONObject(getClassKey());
        e eVar = new e();
        ArrayList arrayList = (ArrayList) eVar.a(jSONObject.getJSONObject("displays").toString(), new ArrayList());
        synchronized (this.b) {
            this.a.clear();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                this.a.add(eVar.a(((JSONObject) it.next()).toString(), new c()));
            }
        }
        return this;
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return b(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.inapp.DisplayList";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        e eVar = new e();
        synchronized (this.b) {
            jSONObject2.put("displays", eVar.a(this.a));
        }
        jSONObject.put(getClassKey(), jSONObject2);
        return jSONObject;
    }
}
