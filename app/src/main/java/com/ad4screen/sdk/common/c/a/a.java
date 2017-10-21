package com.ad4screen.sdk.common.c.a;

import com.google.analytics.tracking.android.HitTypes;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class a<T> extends com.ad4screen.sdk.common.c.a.a.a<ArrayList<T>> {
    private final String a = "java.util.ArrayList";
    private final String b = HitTypes.ITEM;

    public String a() {
        return "java.util.ArrayList";
    }

    public ArrayList<T> a(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str);
        ArrayList<T> arrayList = new ArrayList();
        JSONArray jSONArray = jSONObject.getJSONArray("java.util.ArrayList");
        for (int i = 0; i < jSONArray.length(); i++) {
            arrayList.add(jSONArray.getJSONObject(i).get(HitTypes.ITEM));
        }
        return arrayList;
    }

    public /* synthetic */ Object b(String str) throws JSONException {
        return a(str);
    }
}
