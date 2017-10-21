package com.ad4screen.sdk.service.modules.h.a;

import com.ad4screen.sdk.common.c.c;
import com.ad4screen.sdk.common.c.d;
import com.ad4screen.sdk.common.c.e;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class a implements c<a>, d {
    public ArrayList<b> a = new ArrayList();
    private final String b = "com.ad4screen.sdk.service.modules.member.model.LinkedMembers";
    private final String c = "members";
    private e d = new e();

    public a a(String str) throws JSONException {
        if (str == null) {
            return new a();
        }
        JSONArray jSONArray = new JSONObject(str).getJSONObject("com.ad4screen.sdk.service.modules.member.model.LinkedMembers").getJSONArray("members");
        for (int i = 0; i < jSONArray.length(); i++) {
            this.a.add(this.d.a(jSONArray.getString(i), new b()));
        }
        return this;
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return a(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.member.model.LinkedMembers";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        Iterator it = this.a.iterator();
        while (it.hasNext()) {
            jSONArray.put(this.d.a((b) it.next()));
        }
        jSONObject2.put("members", jSONArray);
        jSONObject.put("com.ad4screen.sdk.service.modules.member.model.LinkedMembers", jSONObject2);
        return jSONObject;
    }
}
