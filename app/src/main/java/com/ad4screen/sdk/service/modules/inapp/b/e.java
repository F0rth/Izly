package com.ad4screen.sdk.service.modules.inapp.b;

import com.ad4screen.sdk.contract.A4SContract.GeofencesColumns;
import com.ad4screen.sdk.service.modules.inapp.a.g;
import com.ad4screen.sdk.service.modules.inapp.a.i;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

public class e {
    public List<g> a(JSONArray jSONArray) throws JSONException {
        if (jSONArray == null || jSONArray.length() == 0) {
            return null;
        }
        List<g> arrayList = new ArrayList();
        for (int i = 0; i < jSONArray.length(); i++) {
            g gVar = new g();
            gVar.a(i.a(GeofencesColumns.LATITUDE, jSONArray.getJSONObject(i)).doubleValue());
            gVar.b(i.a(GeofencesColumns.LONGITUDE, jSONArray.getJSONObject(i)).doubleValue());
            gVar.c(i.a("locationRange", jSONArray.getJSONObject(i)).doubleValue());
            arrayList.add(gVar);
        }
        return arrayList;
    }
}
