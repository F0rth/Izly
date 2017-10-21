package com.ad4screen.sdk.service.modules.h;

import android.content.Context;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.e.c;
import com.ad4screen.sdk.d.d;

import org.json.JSONException;
import org.json.JSONObject;

public class b extends c {
    private final String c = "com.ad4screen.sdk.service.modules.member.LoadMembersTask";
    private final String d = "content";
    private final a e;
    private final Context f;
    private String g;

    public interface a {
        void a();

        void a(String[] strArr);
    }

    public b(Context context, a aVar) {
        super(context);
        this.f = context;
        this.e = aVar;
    }

    protected void a(String str) {
        try {
            Log.internal("MemberManager|Members start parsing");
            JSONObject jSONObject = new JSONObject(str);
            e eVar = new e();
            eVar.a(jSONObject);
            if (eVar.a == null) {
                Log.error("MemberManager|Members parsing failed");
                if (this.e != null) {
                    this.e.a();
                    return;
                }
                return;
            }
            Log.internal("MemberManager|Members parsing success");
            d.a(this.f).e(com.ad4screen.sdk.d.d.b.MemberListWebservice);
            if (this.e != null) {
                this.e.a(eVar.a);
            }
        } catch (Throwable e) {
            Log.internal("MemberManager|Response JSON Parsing error!", e);
            if (this.e != null) {
                this.e.a();
            }
        }
    }

    protected void a(Throwable th) {
        if (this.e != null) {
            this.e.a();
        }
    }

    protected boolean a() {
        c("application/json;charset=utf-8");
        a(4);
        com.ad4screen.sdk.d.b a = com.ad4screen.sdk.d.b.a(this.f);
        if (a.c() == null) {
            Log.warn("MemberManager|No sharedId, skipping reception of Linked Members");
            if (this.e == null) {
                return false;
            }
            this.e.a();
            return false;
        } else if (d.a(this.f).c(com.ad4screen.sdk.d.d.b.MemberListWebservice)) {
            try {
                JSONObject jSONObject = new JSONObject();
                JSONObject jSONObject2 = new JSONObject();
                jSONObject.put("partnerId", a.l());
                jSONObject.put("deviceId", a.c());
                jSONObject2.put("listMembers", jSONObject);
                this.g = jSONObject2.toString();
                return true;
            } catch (Throwable e) {
                Log.error("MemberManager|Could not build message to send to Ad4Screen", e);
                if (this.e == null) {
                    return false;
                }
                this.e.a();
                return false;
            }
        } else {
            Log.debug("Service interruption on LoadMembersTask");
            return false;
        }
    }

    public c b(c cVar) {
        return cVar;
    }

    protected String c() {
        return com.ad4screen.sdk.d.d.b.MemberListWebservice.toString();
    }

    protected String d() {
        return this.g;
    }

    public c e(String str) throws JSONException {
        super.e(str);
        JSONObject jSONObject = new JSONObject(str).getJSONObject("com.ad4screen.sdk.service.modules.member.LoadMembersTask");
        if (!jSONObject.isNull("content")) {
            this.g = jSONObject.getString("content");
        }
        return this;
    }

    protected String e() {
        return d.a(this.f).a(com.ad4screen.sdk.d.d.b.MemberListWebservice);
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return e(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.member.LoadMembersTask";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject toJSON = super.toJSON();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("content", this.g);
        toJSON.put("com.ad4screen.sdk.service.modules.member.LoadMembersTask", jSONObject);
        return toJSON;
    }
}
