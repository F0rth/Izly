package com.ad4screen.sdk.service.modules.d;

import android.content.Context;
import android.text.TextUtils;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.e.c;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.d.d;
import com.ad4screen.sdk.d.d.b;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class e extends c {
    private final String c = "com.ad4screen.sdk.service.modules.common.TrackInboxTask";
    private final String d = "content";
    private Context e;
    private String f;
    private String g;
    private a h;
    private String i;

    public enum a {
        DISP,
        CLICK,
        CLOSE
    }

    public e(Context context, String str, a aVar) {
        super(context);
        this.e = context;
        this.g = str;
        this.i = null;
        this.h = aVar;
    }

    public e(Context context, String str, String str2, a aVar) {
        super(context);
        this.e = context;
        this.g = str;
        this.i = str2;
        this.h = aVar;
    }

    protected void a(String str) {
        Log.debug("TrackInboxTask|Inbox Tracking successfully sent");
        d.a(this.e).e(b.TrackInboxWebservice);
    }

    protected void a(Throwable th) {
        Log.debug("TrackInAppTask|Inbox Tracking failed, will be retried later..");
    }

    protected boolean a() {
        i();
        j();
        if (this.h == null) {
            Log.debug("TrackInboxTask|TrackType is null, cannot send track inbox");
            return false;
        } else if (TextUtils.isEmpty(this.g)) {
            Log.debug("TrackInboxTask|InboxId is null, cannot send track inbox");
            return false;
        } else {
            com.ad4screen.sdk.d.b a = com.ad4screen.sdk.d.b.a(this.e);
            if (a.c() == null) {
                Log.warn("TrackInboxTask|No SharedId, not tracking inbox");
                return false;
            } else if (d.a(this.e).c(b.TrackInboxWebservice)) {
                try {
                    JSONObject jSONObject = new JSONObject();
                    JSONObject jSONObject2 = new JSONObject();
                    JSONArray jSONArray = new JSONArray();
                    jSONObject2.put("date", h.a());
                    jSONObject2.put("notifId", this.g);
                    jSONObject2.put("type", this.h);
                    if (this.i != null) {
                        jSONObject2.put("bid", this.i);
                    }
                    jSONObject2.put("ruuid", h.b());
                    Log.debug("TrackInboxTask", jSONObject2);
                    jSONArray.put(jSONObject2);
                    jSONObject.put("notifs", jSONArray);
                    jSONObject.put("sdk", a.i());
                    this.f = jSONObject.toString();
                    return true;
                } catch (Throwable e) {
                    Log.error("TrackInboxTask|Could not build message to send to Ad4Screen", e);
                    return false;
                }
            } else {
                Log.debug("Service interruption on TrackInboxTask");
                return false;
            }
        }
    }

    public c b(c cVar) {
        e eVar = (e) cVar;
        try {
            JSONObject jSONObject = new JSONObject(d());
            JSONArray jSONArray = new JSONObject(eVar.d()).getJSONArray("notifs");
            JSONArray jSONArray2 = jSONObject.getJSONArray("notifs");
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
        return b.TrackInboxWebservice.toString();
    }

    protected String d() {
        return this.f;
    }

    public c e(String str) throws JSONException {
        super.e(str);
        JSONObject jSONObject = new JSONObject(str).getJSONObject("com.ad4screen.sdk.service.modules.common.TrackInboxTask");
        if (!jSONObject.isNull("content")) {
            this.f = jSONObject.getString("content");
        }
        return this;
    }

    protected String e() {
        return d.a(this.e).a(b.TrackInboxWebservice);
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return e(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.common.TrackInboxTask";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject toJSON = super.toJSON();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("content", this.f);
        toJSON.put("com.ad4screen.sdk.service.modules.common.TrackInboxTask", jSONObject);
        return toJSON;
    }
}
