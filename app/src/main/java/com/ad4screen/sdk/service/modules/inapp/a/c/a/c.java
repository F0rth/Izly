package com.ad4screen.sdk.service.modules.inapp.a.c.a;

import com.ad4screen.sdk.common.c.e;
import com.ad4screen.sdk.service.modules.inapp.a.c.a;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class c extends a {
    public List<a> a = new ArrayList();
    private e b = new e();

    public a a(String str) throws JSONException {
        c[] cVarArr = new c[]{new a(), new b()};
        JSONObject jSONObject = new JSONObject(str);
        for (int i = 0; i < 2; i++) {
            if (!jSONObject.isNull(cVarArr[i].getClassKey())) {
                JSONArray jSONArray = jSONObject.getJSONArray(cVarArr[i].getClassKey());
                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                    this.a.add(this.b.a(jSONArray.getJSONObject(i2).toString(), new a()));
                }
            }
        }
        return this;
    }

    public boolean a(Map<String, com.ad4screen.sdk.service.modules.inapp.a.c.c> map) {
        return false;
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return a(str);
    }

    public String getClassKey() {
        return null;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        for (a a : this.a) {
            jSONArray.put(this.b.a(a));
        }
        jSONObject.put(getClassKey(), jSONArray);
        return jSONObject;
    }
}
