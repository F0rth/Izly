package com.ad4screen.sdk.service.modules.k.d;

import com.ad4screen.sdk.common.c.c;
import com.ad4screen.sdk.common.c.d;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class a implements c<a>, d {
    List<a> a = new ArrayList();
    private final String b = "com.ad4screen.sdk.service.modules.tracking.model.ErrorsResponse";

    public class a implements c<a>, d {
        final /* synthetic */ a a;
        private final String b = "com.ad4screen.sdk.service.modules.tracking.model.ErrorResponse";
        private String c;
        private String d;

        public a(a aVar) {
            this.a = aVar;
        }

        public a a(String str) throws JSONException {
            JSONObject jSONObject = new JSONObject(str);
            this.c = jSONObject.getString("label");
            this.d = jSONObject.getString("message");
            return this;
        }

        public String a() {
            return this.c;
        }

        public String b() {
            return this.d;
        }

        public /* synthetic */ Object fromJSON(String str) throws JSONException {
            return a(str);
        }

        public String getClassKey() {
            return "com.ad4screen.sdk.service.modules.tracking.model.ErrorResponse";
        }

        public JSONObject toJSON() throws JSONException {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("label", this.c);
            jSONObject.put("message", this.d);
            return jSONObject;
        }
    }

    public a a(String str) throws JSONException {
        this.a.clear();
        JSONArray jSONArray = new JSONObject(str).getJSONArray("errors");
        for (int i = 0; i < jSONArray.length(); i++) {
            this.a.add(new a(this).a(jSONArray.getJSONObject(i).toString()));
        }
        return this;
    }

    public List<a> a() {
        return this.a;
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return a(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.tracking.model.ErrorsResponse";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        for (a toJSON : this.a) {
            jSONArray.put(toJSON.toJSON());
        }
        jSONObject.put("errors", jSONArray);
        return jSONObject;
    }
}
