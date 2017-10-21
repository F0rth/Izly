package com.ad4screen.sdk.service.modules.h;

import android.content.Context;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.e.c;
import com.ad4screen.sdk.d.d;
import com.ad4screen.sdk.d.d.b;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class f extends c {
    private final String c = "com.ad4screen.sdk.service.modules.member.UnlinkMembersTask";
    private final String d = "content";
    private final Context e;
    private String f;
    private String[] g;

    public f(String[] strArr, Context context) {
        super(context);
        this.e = context;
        this.g = strArr;
    }

    protected void a(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (!jSONObject.isNull("unlinkMembersResponse")) {
                jSONObject = jSONObject.getJSONObject("unlinkMembersResponse");
                if (jSONObject.getInt("returnCode") == 0) {
                    Log.debug("MemberManager|Unlink Success " + this.f);
                    d.a(this.e).e(b.MemberUnlinkWebservice);
                    return;
                }
                Log.error("MemberManager|Unlink Failure with error " + jSONObject.getString("returnCode") + " : " + jSONObject.getString("returnLabel"));
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
        } else if (d.a(this.e).c(b.MemberUnlinkWebservice)) {
            try {
                JSONObject jSONObject = new JSONObject();
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("partnerId", a.l());
                jSONObject2.put("deviceId", a.c());
                JSONArray jSONArray = new JSONArray();
                for (Object put : this.g) {
                    jSONArray.put(put);
                }
                jSONObject2.put("members", jSONArray);
                jSONObject.put("unlinkMembers", jSONObject2);
                this.f = jSONObject.toString();
                return true;
            } catch (Throwable e) {
                Log.error("MemberManager|Could not build message to send to Ad4Screen", e);
                return false;
            }
        } else {
            Log.debug("Service interruption on UnlinkMembersTask");
            return false;
        }
    }

    public c b(c cVar) {
        f fVar = (f) cVar;
        try {
            JSONObject jSONObject = new JSONObject(d());
            JSONArray jSONArray = new JSONObject(fVar.d()).getJSONArray("members");
            JSONArray jSONArray2 = jSONObject.getJSONArray("members");
            for (int i = 0; i < jSONArray.length(); i++) {
                jSONArray2.put(jSONArray.get(i));
            }
            this.f = jSONObject.toString();
        } catch (Throwable e) {
            Log.internal("Failed to merge " + c(), e);
        } catch (Throwable e2) {
            Log.internal("Failed to merge " + c(), e2);
        }
        return this;
    }

    protected String c() {
        return b.MemberUnlinkWebservice.toString();
    }

    protected String d() {
        return this.f;
    }

    public c e(String str) throws JSONException {
        super.e(str);
        JSONObject jSONObject = new JSONObject(str).getJSONObject("com.ad4screen.sdk.service.modules.member.UnlinkMembersTask");
        if (!jSONObject.isNull("content")) {
            this.f = jSONObject.getString("content");
        }
        return this;
    }

    protected String e() {
        return d.a(this.e).a(b.MemberUnlinkWebservice);
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return e(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.member.UnlinkMembersTask";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject toJSON = super.toJSON();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("content", this.f);
        toJSON.put("com.ad4screen.sdk.service.modules.member.UnlinkMembersTask", jSONObject);
        return toJSON;
    }
}
