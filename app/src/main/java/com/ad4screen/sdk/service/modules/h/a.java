package com.ad4screen.sdk.service.modules.h;

import android.content.Context;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.e.c;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.d.d;
import com.ad4screen.sdk.service.modules.h.a.b;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class a extends c {
    private final String c = "com.ad4screen.sdk.service.modules.member.LinkMemberTask";
    private final String d = "content";
    private final Context e;
    private String f;
    private b g;

    public a(b bVar, Context context) {
        super(context);
        this.e = context;
        this.g = bVar;
    }

    protected void a(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (!jSONObject.isNull("linkMemberResponse")) {
                jSONObject = jSONObject.getJSONObject("linkMemberResponse");
                if (jSONObject.getInt("returnCode") == 0) {
                    Log.debug("MemberManager|Login Success " + this.f);
                    d.a(this.e).e(d.b.MemberLinkWebservice);
                    return;
                }
                Log.error("MemberManager|Login Failure with error " + jSONObject.getString("returnCode") + " : " + jSONObject.getString("returnLabel"));
            }
        } catch (Throwable e) {
            Log.error("MemberManager|Can't parse server response", e);
        }
    }

    protected void a(Throwable th) {
    }

    protected boolean a() {
        c("application/json;charset=utf-8");
        b(4);
        com.ad4screen.sdk.d.b a = com.ad4screen.sdk.d.b.a(this.e);
        if (a.c() == null) {
            Log.warn("MemberManager|No sharedId, skipping tracking");
            return false;
        } else if (d.a(this.e).c(d.b.MemberLinkWebservice)) {
            try {
                JSONObject jSONObject = new JSONObject();
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("partnerId", a.l());
                jSONObject2.put("deviceId", a.c());
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("id", this.g.a);
                jSONObject3.put("lastConnection", h.a(new Date(this.g.c), com.ad4screen.sdk.common.h.a.DEFAULT));
                jSONObject3.put("connectionCount", this.g.b);
                jSONObject2.put("member", jSONObject3);
                jSONObject.put("linkMember", jSONObject2);
                this.f = jSONObject.toString();
                return true;
            } catch (Throwable e) {
                Log.error("MemberManager|Could not build message to send to Ad4Screen", e);
                return false;
            }
        } else {
            Log.debug("Service interruption on LinkMemberTask");
            return false;
        }
    }

    public c b(c cVar) {
        return cVar;
    }

    protected String c() {
        return this.f;
    }

    protected String d() {
        return this.f;
    }

    public c e(String str) throws JSONException {
        super.e(str);
        JSONObject jSONObject = new JSONObject(str).getJSONObject("com.ad4screen.sdk.service.modules.member.LinkMemberTask");
        if (!jSONObject.isNull("content")) {
            this.f = jSONObject.getString("content");
        }
        return this;
    }

    protected String e() {
        return d.a(this.e).a(d.b.MemberLinkWebservice);
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return e(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.member.LinkMemberTask";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject toJSON = super.toJSON();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("content", this.f);
        toJSON.put("com.ad4screen.sdk.service.modules.member.LinkMemberTask", jSONObject);
        return toJSON;
    }
}
