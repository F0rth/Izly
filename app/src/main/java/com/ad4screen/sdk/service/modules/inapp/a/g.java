package com.ad4screen.sdk.service.modules.inapp.a;

import com.ad4screen.sdk.common.c.c;
import com.ad4screen.sdk.common.c.d;
import com.ad4screen.sdk.contract.A4SContract.GeofencesColumns;

import org.json.JSONException;
import org.json.JSONObject;

public class g implements c<g>, d {
    private double a;
    private double b;
    private double c;

    public double a() {
        return this.a;
    }

    public g a(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str).getJSONObject(getClassKey());
        this.a = jSONObject.getDouble(GeofencesColumns.LATITUDE);
        this.b = jSONObject.getDouble(GeofencesColumns.LONGITUDE);
        this.c = jSONObject.getDouble(GeofencesColumns.RADIUS);
        return this;
    }

    public void a(double d) {
        this.a = d;
    }

    public double b() {
        return this.b;
    }

    public void b(double d) {
        this.b = d;
    }

    public double c() {
        return this.c;
    }

    public void c(double d) {
        this.c = d;
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return a(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.inapp.model.LocationRule";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(GeofencesColumns.LATITUDE, this.a);
        jSONObject2.put(GeofencesColumns.LONGITUDE, this.b);
        jSONObject2.put(GeofencesColumns.RADIUS, this.c);
        jSONObject.put(getClassKey(), jSONObject2);
        return jSONObject;
    }
}
