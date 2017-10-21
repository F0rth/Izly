package com.ad4screen.sdk.service.modules.d;

import android.content.Context;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.e;
import com.ad4screen.sdk.common.e.c;
import com.ad4screen.sdk.common.h;

import org.json.JSONException;
import org.json.JSONObject;

public class g extends c {
    Context c;
    String d;
    e[] e;
    private final String f = "com.ad4screen.sdk.service.modules.common.TrackUrlTask";
    private final String g = "url";

    public g(Context context, String str, e... eVarArr) {
        super(context);
        this.c = context;
        this.d = str;
        this.e = eVarArr;
    }

    protected void a(String str) {
        Log.debug("Tracking succeed : " + e() + " with response : " + str);
    }

    protected void a(Throwable th) {
        Log.debug("Tracking failed : " + e(), th);
    }

    protected boolean a() {
        this.d = h.a(this.c, this.d, false, this.e);
        return true;
    }

    public c b(c cVar) {
        return cVar;
    }

    protected String c() {
        return e();
    }

    protected String d() {
        return null;
    }

    public c e(String str) throws JSONException {
        super.e(str);
        JSONObject jSONObject = new JSONObject(str).getJSONObject("com.ad4screen.sdk.service.modules.common.TrackUrlTask");
        if (!jSONObject.isNull("url")) {
            this.d = jSONObject.getString("url");
        }
        return this;
    }

    protected String e() {
        return this.d;
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return e(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.common.TrackUrlTask";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject toJSON = super.toJSON();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("url", this.d);
        toJSON.put("com.ad4screen.sdk.service.modules.common.TrackUrlTask", jSONObject);
        return toJSON;
    }
}
