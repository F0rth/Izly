package com.ad4screen.sdk.service.modules.inapp.a;

import com.ad4screen.sdk.common.c.c;
import com.ad4screen.sdk.common.c.d;

import org.json.JSONException;
import org.json.JSONObject;

public class e implements c<e>, d {
    private Long a;
    private Integer b;
    private Long c;
    private Integer d;

    public e a(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str).getJSONObject("com.ad4screen.sdk.service.modules.inapp.model.GlobalRule");
        if (jSONObject.has("globalCappingInAppDuration")) {
            this.a = Long.valueOf(jSONObject.getLong("globalCappingInAppDuration"));
        } else {
            this.a = null;
        }
        if (jSONObject.has("globalCappingInAppFrequency")) {
            this.b = Integer.valueOf(jSONObject.getInt("globalCappingInAppFrequency"));
        } else {
            this.b = null;
        }
        if (jSONObject.has("globalCappingAlarmDuration")) {
            this.c = Long.valueOf(jSONObject.getLong("globalCappingAlarmDuration"));
        } else {
            this.c = null;
        }
        if (jSONObject.has("globalCappingAlarmFrequency")) {
            this.d = Integer.valueOf(jSONObject.getInt("globalCappingAlarmFrequency"));
        } else {
            this.d = null;
        }
        return this;
    }

    public Long a() {
        return this.a;
    }

    public void a(Integer num) {
        this.b = num;
    }

    public void a(Long l) {
        this.a = l;
    }

    public Integer b() {
        return this.b;
    }

    public void b(Integer num) {
        this.d = num;
    }

    public void b(Long l) {
        this.c = l;
    }

    public Long c() {
        return this.c;
    }

    public Integer d() {
        return this.d;
    }

    public boolean e() {
        return (this.c == null || this.d == null) ? false : true;
    }

    public boolean f() {
        return (this.a == null || this.b == null) ? false : true;
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return a(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.inapp.model.GlobalRule";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("globalCappingInAppDuration", this.a);
        jSONObject2.put("globalCappingInAppFrequency", this.b);
        jSONObject2.put("globalCappingAlarmDuration", this.c);
        jSONObject2.put("globalCappingAlarmFrequency", this.d);
        jSONObject.put("com.ad4screen.sdk.service.modules.inapp.model.GlobalRule", jSONObject2);
        return jSONObject;
    }
}
