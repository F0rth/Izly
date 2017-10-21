package com.ad4screen.sdk.service.modules.d;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.e.c;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.d.d.b;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class d extends c {
    private final String c = "com.ad4screen.sdk.service.modules.common.TrackInAppTask";
    private final String d = "content";
    private Context e;
    private String f;
    private String g;
    private a h;
    private String i;
    private boolean j = false;

    public enum a {
        DISP,
        CLICK,
        CLOSE,
        CANCEL
    }

    public d(Context context, String str, a aVar, Bundle bundle) {
        super(context);
        this.e = context;
        this.g = str;
        this.i = null;
        this.h = aVar;
        if (bundle != null) {
            this.j = bundle.getBoolean("controlGroup", false);
        }
    }

    public d(Context context, String str, String str2, a aVar, Bundle bundle) {
        super(context);
        this.e = context;
        this.g = str;
        this.i = str2;
        this.h = aVar;
        if (bundle != null) {
            this.j = bundle.getBoolean("controlGroup", false);
        }
    }

    protected void a(String str) {
        Log.debug("TrackInAppTask|InApp Tracking successfully sent");
        com.ad4screen.sdk.d.d.a(this.e).e(b.TrackInAppWebservice);
    }

    protected void a(Throwable th) {
        Log.error("TrackInAppTask|InApp Tracking failed, will be retried later..");
    }

    protected boolean a() {
        i();
        j();
        if (TextUtils.isEmpty(this.g) || this.h == null) {
            Log.debug("TrackInAppTask|NotifId or Type is null, cannot send track in-app");
            return false;
        }
        com.ad4screen.sdk.d.b a = com.ad4screen.sdk.d.b.a(this.e);
        if (a.c() == null) {
            Log.warn("TrackInAppTask|No SharedId, not tracking in-app");
            return false;
        } else if (com.ad4screen.sdk.d.d.a(this.e).c(b.TrackInAppWebservice)) {
            try {
                JSONObject jSONObject = new JSONObject();
                JSONObject jSONObject2 = new JSONObject();
                JSONArray jSONArray = new JSONArray();
                jSONObject2.put("date", h.a());
                jSONObject2.put("type", this.h.toString());
                jSONObject2.put("notifId", this.g);
                if (this.i != null) {
                    jSONObject2.put("bid", this.i);
                }
                if (this.j) {
                    jSONObject2.put("controlGroup", true);
                }
                jSONObject2.put("ruuid", h.b());
                Log.debug("TrackInAppTask", jSONObject2);
                jSONArray.put(jSONObject2);
                jSONObject.put("notifs", jSONArray);
                jSONObject.put("sdk", a.i());
                this.f = jSONObject.toString();
                return true;
            } catch (Throwable e) {
                Log.error("TrackInAppTask|Could not build message to send to Ad4Screen", e);
                return false;
            }
        } else {
            Log.debug("Service interruption on TrackInAppTask");
            return false;
        }
    }

    public c b(c cVar) {
        Throwable e;
        d dVar = (d) cVar;
        try {
            JSONObject jSONObject = new JSONObject(d());
            JSONArray jSONArray = new JSONObject(dVar.d()).getJSONArray("notifs");
            JSONArray jSONArray2 = jSONObject.getJSONArray("notifs");
            for (int i = 0; i < jSONArray.length(); i++) {
                jSONArray2.put(jSONArray.get(i));
            }
            this.f = jSONObject.toString();
        } catch (JSONException e2) {
            e = e2;
            Log.internal("Failed to merge " + c(), e);
            return this;
        } catch (NullPointerException e3) {
            e = e3;
            Log.internal("Failed to merge " + c(), e);
            return this;
        }
        return this;
    }

    protected String c() {
        return b.TrackInAppWebservice.toString();
    }

    protected String d() {
        return this.f;
    }

    public c e(String str) throws JSONException {
        super.e(str);
        JSONObject jSONObject = new JSONObject(str).getJSONObject("com.ad4screen.sdk.service.modules.common.TrackInAppTask");
        if (!jSONObject.isNull("content")) {
            this.f = jSONObject.getString("content");
        }
        return this;
    }

    protected String e() {
        return com.ad4screen.sdk.d.d.a(this.e).a(b.TrackInAppWebservice);
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return e(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.common.TrackInAppTask";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject toJSON = super.toJSON();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("content", this.f);
        toJSON.put("com.ad4screen.sdk.service.modules.common.TrackInAppTask", jSONObject);
        return toJSON;
    }
}
