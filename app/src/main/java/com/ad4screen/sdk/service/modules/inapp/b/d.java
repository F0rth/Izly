package com.ad4screen.sdk.service.modules.inapp.b;

import com.ad4screen.sdk.service.modules.inapp.a.c;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class d {
    public List<c> a(JSONArray jSONArray) throws JSONException {
        if (jSONArray == null || jSONArray.length() == 0) {
            return null;
        }
        List<c> arrayList = new ArrayList();
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            c cVar = new c();
            cVar.a(jSONObject);
            cVar.b(jSONObject);
            cVar.c(jSONObject);
            cVar.d(jSONObject);
            cVar.e(jSONObject);
            cVar.a(com.ad4screen.sdk.service.modules.inapp.a.d.a("enter"));
            arrayList.add(cVar);
        }
        return arrayList;
    }
}
