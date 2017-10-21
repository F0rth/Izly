package com.ad4screen.sdk.service.modules.inapp.a.b.a;

import com.ad4screen.sdk.common.c.e;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class a extends com.ad4screen.sdk.service.modules.inapp.a.b.a {
    protected List<com.ad4screen.sdk.service.modules.inapp.a.b.a> a;

    public a(List<com.ad4screen.sdk.service.modules.inapp.a.b.a> list) {
        this.a = list;
    }

    public /* synthetic */ com.ad4screen.sdk.service.modules.inapp.a.b.a a(String str) throws JSONException {
        return b(str);
    }

    public Long a() {
        return null;
    }

    public a b(String str) throws JSONException {
        e eVar = new e();
        a[] aVarArr = new a[]{new b(), new c()};
        JSONObject jSONObject = new JSONObject(str);
        this.a = new ArrayList();
        for (int i = 0; i < 2; i++) {
            if (!jSONObject.isNull(aVarArr[i].getClassKey())) {
                JSONArray jSONArray = jSONObject.getJSONArray(aVarArr[i].getClassKey());
                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                    this.a.add(eVar.a(jSONArray.getJSONObject(i2).toString(), new com.ad4screen.sdk.service.modules.inapp.a.b.a()));
                }
            }
        }
        return this;
    }

    public String b() {
        return null;
    }

    public List<com.ad4screen.sdk.service.modules.inapp.a.b.a> c() {
        return this.a;
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return b(str);
    }

    public JSONObject toJSON() throws JSONException {
        e eVar = new e();
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        for (com.ad4screen.sdk.service.modules.inapp.a.b.a a : this.a) {
            jSONArray.put(eVar.a(a));
        }
        jSONObject.put(getClassKey(), jSONArray);
        return jSONObject;
    }
}
