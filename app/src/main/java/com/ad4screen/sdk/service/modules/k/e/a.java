package com.ad4screen.sdk.service.modules.k.e;

import android.content.Context;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.e.c;
import com.ad4screen.sdk.d.d;
import com.ad4screen.sdk.d.d.b;

import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

public class a extends c {
    private final String c = "com.ad4screen.sdk.service.modules.tracking.permissions.PermissionsTrackingTask";
    private final String d = "content";
    private final Context e;
    private String f;
    private HashMap<String, Boolean> g;

    public a(Context context, HashMap<String, Boolean> hashMap) {
        super(context);
        this.e = context;
        this.g = hashMap;
    }

    protected void a(String str) {
        Log.debug("PermissionsTrackingTask|Successfully sent permissions to server");
        d.a(this.e).e(b.PermissionsWebserviceUpdate);
    }

    protected void a(Throwable th) {
        Log.error("PermissionsTrackingTask|Failed to send permissions to server");
    }

    protected boolean a() {
        i();
        j();
        if (!d.a(this.e).c(b.PermissionsWebserviceUpdate)) {
            Log.debug("Service interruption on PermissionsTrackingTask");
            return false;
        } else if (com.ad4screen.sdk.d.b.a(this.e).c() == null) {
            Log.warn("PermissionsTrackingTask|SharedId is undefined, cannot send permissions");
            return false;
        } else {
            try {
                JSONObject jSONObject = new JSONObject();
                for (String str : this.g.keySet()) {
                    jSONObject.put(str, this.g.get(str));
                }
                this.f = jSONObject.toString();
                return true;
            } catch (Throwable e) {
                Log.error("PermissionsTrackingTask|Could not build message to send to server", e);
                return false;
            }
        }
    }

    public c b(c cVar) {
        a aVar = (a) cVar;
        try {
            JSONObject jSONObject = new JSONObject(d());
            JSONObject jSONObject2 = new JSONObject(aVar.d());
            Iterator keys = jSONObject2.keys();
            while (keys.hasNext()) {
                String str = (String) keys.next();
                jSONObject.put(str, jSONObject2.get(str));
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
        return b.PermissionsWebserviceUpdate.toString();
    }

    protected String d() {
        return this.f;
    }

    public c e(String str) throws JSONException {
        super.e(str);
        JSONObject jSONObject = new JSONObject(str).getJSONObject("com.ad4screen.sdk.service.modules.tracking.permissions.PermissionsTrackingTask");
        if (!jSONObject.isNull("content")) {
            this.f = jSONObject.getString("content");
        }
        return this;
    }

    protected String e() {
        return d.a(this.e).a(b.PermissionsWebserviceUpdate);
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return e(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.tracking.permissions.PermissionsTrackingTask";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject toJSON = super.toJSON();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("content", this.f);
        toJSON.put("com.ad4screen.sdk.service.modules.tracking.permissions.PermissionsTrackingTask", jSONObject);
        return toJSON;
    }
}
