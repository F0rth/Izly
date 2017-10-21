package com.ad4screen.sdk.service.modules.j;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.e;
import com.ad4screen.sdk.common.g;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.d.d;
import com.ad4screen.sdk.d.d.b;
import com.ad4screen.sdk.service.modules.k.d.a.a;

import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class c extends com.ad4screen.sdk.common.e.c {
    private final String c = "com.ad4screen.sdk.service.modules.profile.UpdateMemberInfoTask";
    private final String d = "content";
    private final String e = "activeMember";
    private final Context f;
    private Bundle g;
    private String h;
    private String i;

    public c(Context context, Bundle bundle) {
        super(context);
        this.f = context;
        this.g = bundle;
    }

    protected void a(String str) {
        Log.debug("UpdateMemberInfoTask|Profile is successfully updated");
        d.a(this.f).e(b.UpdateMemberInfoWebservice);
    }

    protected void a(Throwable th) {
        Log.error("UpdateMemberInfoTask|Profile update failed");
    }

    protected boolean a() {
        com.ad4screen.sdk.d.b a = com.ad4screen.sdk.d.b.a(this.f);
        com.ad4screen.sdk.service.modules.h.c a2 = com.ad4screen.sdk.service.modules.h.c.a(this.f);
        if (a.c() == null) {
            Log.warn("UpdateMemberInfoTask|No sharedId, skipping user info update");
            return false;
        } else if (!a2.c()) {
            Log.warn("UpdateMemberInfoTask|No member logged in. Please use login method before updating member information");
            return false;
        } else if (d.a(this.f).c(b.UpdateMemberInfoWebservice)) {
            i();
            j();
            this.i = a2.b();
            try {
                JSONObject jSONObject = new JSONObject();
                for (String str : this.g.keySet()) {
                    jSONObject.put(str, this.g.get(str).toString());
                }
                Log.debug("UpdateMemberInfoTask", jSONObject);
                this.h = jSONObject.toString();
                return true;
            } catch (Throwable e) {
                Log.error("UpdateMemberInfoTask|Could not build message to send to server", e);
                return false;
            }
        } else {
            Log.debug("Service interruption on UpdateMemberInfoTask");
            return false;
        }
    }

    protected boolean a(int i, String str) {
        if (i == 500 && str != null) {
            Log.error("UpdateMemberInfoTask|Request succeeded but parameters are invalid, server returned :" + str);
            try {
                for (a aVar : new com.ad4screen.sdk.service.modules.k.d.a().a(str).a()) {
                    if (aVar.b().toLowerCase(Locale.US).contains("unknown fields")) {
                        Log.error("UpdatMemberInfoTask|Some fields does not exists : " + aVar.b());
                        return true;
                    }
                }
            } catch (Throwable e) {
                Log.internal("UpdateMemberInfoTask|Error Parsing failed : " + e.getMessage(), e);
            }
        }
        return super.a(i, str);
    }

    public com.ad4screen.sdk.common.e.c b(com.ad4screen.sdk.common.e.c cVar) {
        try {
            JSONObject jSONObject = new JSONObject(((c) cVar).d());
            JSONObject jSONObject2 = new JSONObject(d());
            JSONArray names = jSONObject.names();
            for (int i = 0; i < names.length(); i++) {
                String string = names.getString(i);
                jSONObject2.put(string, String.valueOf(jSONObject.get(string)));
            }
            this.h = jSONObject2.toString();
        } catch (Throwable e) {
            Log.internal("Failed to merge " + c(), e);
        } catch (Throwable e2) {
            Log.internal("Failed to merge " + c(), e2);
        }
        return this;
    }

    protected String c() {
        return b.UpdateMemberInfoWebservice.toString() + "/" + g.e().a() + "/" + ((int) (Math.random() * 10000.0d));
    }

    protected String d() {
        return this.h;
    }

    public com.ad4screen.sdk.common.e.c e(String str) throws JSONException {
        super.e(str);
        JSONObject jSONObject = new JSONObject(str).getJSONObject("com.ad4screen.sdk.service.modules.profile.UpdateMemberInfoTask");
        if (!jSONObject.isNull("content")) {
            this.h = jSONObject.getString("content");
        }
        if (!jSONObject.isNull("activeMember")) {
            this.i = jSONObject.getString("activeMember");
        }
        return this;
    }

    protected String e() {
        return h.a(this.f, d.a(this.f).a(b.UpdateMemberInfoWebservice), false, new e("memberId", Uri.encode(this.i)));
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return e(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.profile.UpdateMemberInfoTask";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject toJSON = super.toJSON();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("content", this.h);
        jSONObject.put("activeMember", this.i);
        toJSON.put("com.ad4screen.sdk.service.modules.profile.UpdateMemberInfoTask", jSONObject);
        return toJSON;
    }
}
