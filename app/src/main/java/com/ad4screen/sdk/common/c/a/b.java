package com.ad4screen.sdk.common.c.a;

import com.ad4screen.sdk.common.c.d;
import com.ad4screen.sdk.common.c.e;
import com.google.analytics.tracking.android.HitTypes;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class b<T> extends com.ad4screen.sdk.common.c.a.a.b<ArrayList<T>> {
    private final String a = "java.util.ArrayList";
    private final String b = HitTypes.ITEM;

    public JSONObject a(ArrayList<T> arrayList) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Object next = it.next();
            JSONObject jSONObject2 = new JSONObject();
            if (next instanceof d) {
                jSONObject2.put(HitTypes.ITEM, new e().a(next));
            } else {
                jSONObject2.put(HitTypes.ITEM, next);
            }
            jSONArray.put(jSONObject2);
        }
        jSONObject.put("type", "java.util.ArrayList");
        jSONObject.put("java.util.ArrayList", jSONArray);
        return jSONObject;
    }
}
