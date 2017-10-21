package com.ad4screen.sdk.service.modules.inapp;

import android.content.Context;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.service.modules.inapp.a.f;

import org.json.JSONArray;
import org.json.JSONObject;

public class b extends com.ad4screen.sdk.common.c.b {
    public b(Context context) {
        super(context, "com.ad4screen.sdk.service.modules.inapp.InAppNotification");
    }

    public f a() {
        return (f) b("configuration", new f());
    }

    public void a(f fVar) {
        a("configuration", (Object) fVar);
    }

    public boolean a(int i, JSONObject jSONObject) {
        switch (i) {
            case 4:
                try {
                    jSONObject.getJSONObject("configuration").getJSONObject("com.ad4screen.sdk.service.modules.inapp.model.InAppConfig").put("rules", new JSONArray());
                    return true;
                } catch (Throwable e) {
                    Log.internal("InAppArchive|Error during upgrade of Json file", e);
                    return false;
                }
            default:
                return false;
        }
    }

    public int b() {
        return 5;
    }
}
