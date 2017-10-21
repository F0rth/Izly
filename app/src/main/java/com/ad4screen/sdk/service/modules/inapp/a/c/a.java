package com.ad4screen.sdk.service.modules.inapp.a.c;

import com.ad4screen.sdk.common.c.c;
import com.ad4screen.sdk.common.c.d;
import com.ad4screen.sdk.common.c.e;
import com.ad4screen.sdk.service.modules.inapp.a.c.a.b;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class a implements c<a>, d {
    private e a = new e();

    public a a(String str) throws JSONException {
        int i = 0;
        a[] aVarArr = new a[]{new b(), new c(), new com.ad4screen.sdk.service.modules.inapp.a.c.a.a(), new b()};
        JSONObject jSONObject = new JSONObject(str);
        while (i < 4) {
            if (!jSONObject.isNull(aVarArr[i].getClassKey())) {
                return (a) this.a.a(jSONObject.toString(), aVarArr[i]);
            }
            i++;
        }
        return this;
    }

    public boolean a(Map<String, c> map) {
        return false;
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
