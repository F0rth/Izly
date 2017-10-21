package com.ad4screen.sdk.service.modules.inapp.a.b;

import com.ad4screen.sdk.common.c.c;
import com.ad4screen.sdk.common.c.d;
import com.ad4screen.sdk.common.c.e;
import com.ad4screen.sdk.service.modules.inapp.a.b.b.b;

import org.json.JSONException;
import org.json.JSONObject;

public class a implements c<a>, d {
    public a a(String str) throws JSONException {
        int i = 0;
        e eVar = new e();
        a[] aVarArr = new a[]{new com.ad4screen.sdk.service.modules.inapp.a.b.b.c(), new b(), new com.ad4screen.sdk.service.modules.inapp.a.b.a.b(), new com.ad4screen.sdk.service.modules.inapp.a.b.a.c()};
        JSONObject jSONObject = new JSONObject(str);
        while (i < 4) {
            if (!jSONObject.isNull(aVarArr[i].getClassKey())) {
                return (a) eVar.a(jSONObject.toString(), aVarArr[i]);
            }
            i++;
        }
        return this;
    }

    public Long a() {
        return null;
    }

    public String b() {
        return null;
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return a(str);
    }

    public String getClassKey() {
        return null;
    }

    public JSONObject toJSON() throws JSONException {
        return null;
    }
}
