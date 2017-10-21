package com.ad4screen.sdk.service.modules.push;

import android.content.Context;
import android.text.TextUtils;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.R;
import com.ad4screen.sdk.c.a.d;
import com.ad4screen.sdk.c.a.e;
import com.ad4screen.sdk.c.a.h;
import com.ad4screen.sdk.plugins.model.Beacon.PersonalParamsReplacer;
import com.ad4screen.sdk.plugins.model.Geofence;
import com.ad4screen.sdk.service.modules.push.a.a;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

public class c {
    private static final int a = R.drawable.com_ad4screen_sdk_notification_action_default_icon;
    private String b;
    private String c;
    private String d;
    private boolean e = true;
    private HashMap<String, String> f;
    private d g;

    public static c a(Context context, a aVar, JSONObject jSONObject) throws JSONException {
        String str = null;
        c cVar = new c();
        String string = jSONObject.getString("id");
        String string2 = jSONObject.getString("action");
        String a = aVar.a();
        String b = aVar.b();
        d.a personalParamsReplacer = a != null ? new PersonalParamsReplacer(context, a) : b != null ? new Geofence.PersonalParamsReplacer(context, b) : null;
        if (!jSONObject.isNull("url")) {
            str = jSONObject.getString("url");
            Log.internal("PushButton|url: " + str);
            if (personalParamsReplacer != null) {
                str = d.a(str, personalParamsReplacer);
                Log.internal("PushButton|new url: " + str);
            }
        }
        cVar.b(jSONObject.getString("title"));
        cVar.a(string);
        d eVar;
        if ("browser".equalsIgnoreCase(string2)) {
            eVar = new e();
            eVar.h = aVar.b + '#' + string;
            eVar.a.c = str;
            eVar.b = e.a.System;
            cVar.a(eVar);
        } else if ("webView".equalsIgnoreCase(string2)) {
            eVar = new e();
            eVar.h = aVar.b + '#' + string;
            eVar.a.c = str;
            eVar.a.d = "com_ad4screen_sdk_template_interstitial";
            cVar.a(eVar);
        } else if ("urlExec".equalsIgnoreCase(string2)) {
            eVar = new h();
            eVar.h = aVar.b + "#" + string;
            eVar.a = str;
            cVar.a(eVar);
        }
        if (!jSONObject.isNull("icon")) {
            cVar.c(jSONObject.getString("icon"));
        }
        if (!jSONObject.isNull("ccp")) {
            HashMap hashMap = new HashMap();
            JSONObject jSONObject2 = jSONObject.getJSONObject("ccp");
            for (int i = 0; i < jSONObject2.names().length(); i++) {
                b = jSONObject2.names().getString(i);
                Object string3 = jSONObject2.getString(b);
                Log.internal("PushButton|custom param [" + b + "] = " + string3);
                if (personalParamsReplacer != null) {
                    string3 = d.a(string3, personalParamsReplacer);
                    Log.internal("PushButton|new custom param [" + b + "] = " + string3);
                }
                hashMap.put(b, string3);
            }
            cVar.a(hashMap);
        }
        if (jSONObject.isNull("destructive")) {
            cVar.a(true);
            return cVar;
        }
        cVar.a(jSONObject.getBoolean("destructive"));
        return cVar;
    }

    private boolean a(int i) {
        return i != 0;
    }

    public int a(Context context) {
        if (TextUtils.isEmpty(this.d)) {
            return a;
        }
        int identifier = context.getResources().getIdentifier(this.d, "drawable", context.getPackageName());
        return !a(identifier) ? a : identifier;
    }

    public String a() {
        return this.c;
    }

    public void a(d dVar) {
        this.g = dVar;
    }

    public void a(String str) {
        this.b = str;
    }

    public void a(HashMap<String, String> hashMap) {
        this.f = hashMap;
    }

    public void a(boolean z) {
        this.e = z;
    }

    public HashMap<String, String> b() {
        if (this.f == null) {
            this.f = new HashMap();
        }
        return this.f;
    }

    public void b(String str) {
        this.c = str;
    }

    public d c() {
        return this.g;
    }

    public void c(String str) {
        this.d = str;
    }

    public boolean d() {
        return this.e;
    }
}
