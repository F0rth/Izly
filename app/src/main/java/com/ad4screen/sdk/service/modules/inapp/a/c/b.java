package com.ad4screen.sdk.service.modules.inapp.a.c;

import java.util.Map;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

public final class b extends a {
    public String a;
    public Pattern b;

    public b(String str, String str2) {
        this.a = str;
        this.b = Pattern.compile(str2, 2);
    }

    public final /* synthetic */ a a(String str) throws JSONException {
        return b(str);
    }

    public final boolean a(Map<String, c> map) {
        c cVar = (c) map.get(this.a);
        return cVar == null ? false : this.a == null ? false : !this.a.equals(cVar.a) ? false : this.b != null ? this.b.matcher(cVar.b).matches() : false;
    }

    public final b b(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str).getJSONObject("com.ad4screen.sdk.service.modules.inapp.model.states.RegexState");
        return new b(jSONObject.getString("name"), jSONObject.getString("regex"));
    }

    public final /* synthetic */ Object fromJSON(String str) throws JSONException {
        return b(str);
    }

    public final String getClassKey() {
        return "com.ad4screen.sdk.service.modules.inapp.model.states.RegexState";
    }

    public final JSONObject toJSON() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("name", this.a);
        jSONObject2.put("regex", this.b.pattern());
        jSONObject.put("com.ad4screen.sdk.service.modules.inapp.model.states.RegexState", jSONObject2);
        return jSONObject;
    }
}
