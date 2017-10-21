package com.ad4screen.sdk.service.modules.inapp.b;

import com.ad4screen.sdk.service.modules.inapp.a.i;
import com.ad4screen.sdk.service.modules.inapp.a.l;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

public class g {
    public List<l> a(JSONArray jSONArray) throws JSONException {
        if (jSONArray == null || jSONArray.length() == 0) {
            return null;
        }
        List<l> arrayList = new ArrayList();
        for (int i = 0; i < jSONArray.length(); i++) {
            arrayList.add(new l(i.b("name", jSONArray.getJSONObject(i))));
        }
        return arrayList;
    }
}
