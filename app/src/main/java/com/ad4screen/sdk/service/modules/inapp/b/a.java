package com.ad4screen.sdk.service.modules.inapp.b;

import com.ad4screen.sdk.service.modules.inapp.a.b;
import com.ad4screen.sdk.service.modules.inapp.a.i;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

public class a {
    public List<b> a(JSONArray jSONArray) throws JSONException {
        if (jSONArray == null || jSONArray.length() == 0) {
            return null;
        }
        List<b> arrayList = new ArrayList();
        for (int i = 0; i < jSONArray.length(); i++) {
            b bVar = new b();
            bVar.a(jSONArray.getJSONObject(i));
            bVar.b(jSONArray.getJSONObject(i));
            bVar.c(jSONArray.getJSONObject(i));
            bVar.d(jSONArray.getJSONObject(i));
            bVar.e(jSONArray.getJSONObject(i));
            bVar.a(i.b("transition", jSONArray.getJSONObject(i)));
            bVar.b(i.b("acc", jSONArray.getJSONObject(i)));
            arrayList.add(bVar);
        }
        return arrayList;
    }
}
