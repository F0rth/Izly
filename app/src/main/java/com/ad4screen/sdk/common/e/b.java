package com.ad4screen.sdk.common.e;

import android.content.Context;

import com.ad4screen.sdk.common.a.a;
import com.ad4screen.sdk.common.c.c;
import com.ad4screen.sdk.common.c.d;
import com.ad4screen.sdk.common.g;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class b implements c<b>, d, Runnable {
    public long a = g.e().a();
    public Context b;

    public b(Context context) {
        this.b = context;
    }

    public b a(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str);
        if (!jSONObject.isNull("creationTimestamp")) {
            this.a = jSONObject.getLong("creationTimestamp");
        }
        return this;
    }

    public abstract boolean a();

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return a(str);
    }

    public final void run() {
        if (!a()) {
            a.a(this.b).a(this);
        }
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject.put("type", getClassKey());
        jSONObject.put("creationTimestamp", this.a);
        jSONObject.put("com.ad4screen.sdk.common.tasks.ExternalTask", jSONObject2);
        return jSONObject;
    }
}
