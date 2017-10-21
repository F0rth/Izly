package com.ad4screen.sdk.service.modules.j;

import android.content.Context;
import android.os.Bundle;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.e.c;
import com.ad4screen.sdk.d.d;
import com.ad4screen.sdk.d.f;

import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class b extends c {
    private final String c = "com.ad4screen.sdk.service.modules.profile.UpdateDeviceInfoTask";
    private final String d = "content";
    private final String e = "preferences";
    private final Context f;
    private Bundle g;
    private String h;

    public interface a {
        void a(Bundle bundle);
    }

    public static final class b implements com.ad4screen.sdk.d.f.a<a> {
        private final Bundle a;

        public b(Bundle bundle) {
            this.a = bundle;
        }

        public final void a(a aVar) {
            aVar.a(this.a);
        }
    }

    public b(Context context, Bundle bundle) {
        super(context);
        this.f = context;
        this.g = bundle;
    }

    protected void a(String str) {
        Log.debug("UpdateDeviceInfoTask|Profile is successfully updated");
        d.a(this.f).e(com.ad4screen.sdk.d.d.b.UpdateDeviceInfoWebservice);
        if (this.g != null && !this.g.isEmpty()) {
            f.a().a(new b(this.g));
        }
    }

    protected void a(Throwable th) {
        Log.error("UpdateDeviceInfoTask|Profile update failed");
    }

    protected boolean a() {
        if (com.ad4screen.sdk.d.b.a(this.f).c() == null) {
            Log.warn("UpdateDeviceInfoTask|No sharedId, skipping user info update");
            return false;
        } else if (d.a(this.f).c(com.ad4screen.sdk.d.d.b.UpdateDeviceInfoWebservice)) {
            i();
            j();
            try {
                JSONObject jSONObject = new JSONObject();
                for (String str : this.g.keySet()) {
                    jSONObject.put(str, this.g.get(str).toString());
                }
                this.h = jSONObject.toString();
                Log.debug("UpdateDeviceInfoTask", jSONObject);
                return true;
            } catch (Throwable e) {
                Log.error("UpdateDeviceInfoTask|Could not build message to send to Ad4Screen", e);
                return false;
            }
        } else {
            Log.debug("Service interruption on UpdateUserPreferencesTask");
            return false;
        }
    }

    protected boolean a(int i, String str) {
        if (i == 500 && str != null) {
            Log.error("UpdateDeviceInfoTask|Request succeeded but parameters are invalid, server returned :" + str);
            try {
                for (com.ad4screen.sdk.service.modules.k.d.a.a aVar : new com.ad4screen.sdk.service.modules.k.d.a().a(str).a()) {
                    if (aVar.b().toLowerCase(Locale.US).contains("unknown fields")) {
                        Log.error("UpdateDeviceInfoTask|Some fields do not exist : " + aVar.b());
                        return true;
                    }
                }
            } catch (Throwable e) {
                Log.internal("UpdateDeviceInfoTask|Error Parsing failed : " + e.getMessage(), e);
            }
        }
        return super.a(i, str);
    }

    protected Bundle b() {
        return this.g;
    }

    public c b(c cVar) {
        b bVar = (b) cVar;
        try {
            JSONObject jSONObject = new JSONObject(bVar.d());
            JSONObject jSONObject2 = new JSONObject(d());
            JSONArray names = jSONObject.names();
            for (int i = 0; i < names.length(); i++) {
                String string = names.getString(i);
                jSONObject2.put(string, String.valueOf(jSONObject.get(string)));
            }
            this.g.putAll(bVar.b());
            this.h = jSONObject2.toString();
        } catch (Throwable e) {
            Log.internal("Failed to merge " + c(), e);
        } catch (Throwable e2) {
            Log.internal("Failed to merge " + c(), e2);
        }
        return this;
    }

    protected String c() {
        return com.ad4screen.sdk.d.d.b.UpdateDeviceInfoWebservice.toString();
    }

    protected String d() {
        return this.h;
    }

    public c e(String str) throws JSONException {
        super.e(str);
        JSONObject jSONObject = new JSONObject(str).getJSONObject("com.ad4screen.sdk.service.modules.profile.UpdateDeviceInfoTask");
        if (!jSONObject.isNull("content")) {
            this.h = jSONObject.getString("content");
        }
        if (jSONObject.isNull("preferences")) {
            this.g = new Bundle();
        } else {
            this.g = new com.ad4screen.sdk.common.c.a.c().a(jSONObject.getJSONObject("preferences").toString());
        }
        return this;
    }

    protected String e() {
        return d.a(this.f).a(com.ad4screen.sdk.d.d.b.UpdateDeviceInfoWebservice);
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return e(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.profile.UpdateDeviceInfoTask";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject toJSON = super.toJSON();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("content", this.h);
        if (!(this.g == null || this.g.isEmpty())) {
            jSONObject.put("preferences", new com.ad4screen.sdk.common.c.a.d().a(this.g));
        }
        toJSON.put("com.ad4screen.sdk.service.modules.profile.UpdateDeviceInfoTask", jSONObject);
        return toJSON;
    }
}
