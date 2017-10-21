package com.ad4screen.sdk.service.modules.k.f.a;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri.Builder;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.c;
import com.ad4screen.sdk.common.d;
import com.ad4screen.sdk.common.e.b;
import com.ad4screen.sdk.common.h;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class e extends b {
    private static final Object c = new Object();
    private static volatile String d = "";
    private static volatile boolean e = false;

    public e(Context context) {
        super(context);
    }

    private static boolean b(String str) {
        boolean z = false;
        synchronized (c) {
            if (str.equals(d)) {
                z = e;
            } else {
                com.ad4screen.sdk.common.e eVar = new com.ad4screen.sdk.common.e("sdk", "android");
                com.ad4screen.sdk.common.e eVar2 = new com.ad4screen.sdk.common.e("format", "json");
                com.ad4screen.sdk.common.e eVar3 = new com.ad4screen.sdk.common.e("fields", "supports_attribution");
                Builder encodedPath = new Builder().encodedPath("https://graph.facebook.com/" + str);
                for (int i = z; i < 3; i++) {
                    com.ad4screen.sdk.common.e eVar4 = new com.ad4screen.sdk.common.e[]{eVar, eVar2, eVar3}[i];
                    encodedPath.appendQueryParameter(eVar4.a, eVar4.b);
                }
                String builder = encodedPath.toString();
                Log.internal("Facebook|Facebook GET " + builder);
                builder = d.a(builder);
                Log.internal("Facebook|Facebook attribution support response : " + builder);
                if (builder != null) {
                    try {
                        z = new JSONObject(builder).optBoolean("supports_attribution", false);
                    } catch (JSONException e) {
                        Log.error("Facebook|Failed to obtain Facebook app attribution support status");
                    }
                }
                d = str;
                e = z;
            }
        }
        return z;
    }

    public boolean a() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        com.ad4screen.sdk.d.b a = com.ad4screen.sdk.d.b.a(this.b);
        if (com.ad4screen.sdk.d.d.a(this.b).c(com.ad4screen.sdk.d.d.b.FacebookTrackingWebservice)) {
            try {
                if (a.K() == null) {
                    Log.info("Facebook|No Facebook AppId found, not publishing facebook installation.");
                    return true;
                }
                String a2 = c.a(this.b);
                SharedPreferences sharedPreferences = this.b.getSharedPreferences("com.facebook.sdk.attributionTracking", 0);
                String str = a.K() + "ping";
                if (sharedPreferences.getLong(str, 0) != 0) {
                    Log.debug("Facebook|Facebook installation was already published, skipping...");
                    return a.a(this.b);
                }
                Log.info("Facebook|Querying Facebook Attribution for appId : " + a.K());
                if (b(a.K())) {
                    Log.info("Facebook|Sending app installation attribution");
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(new com.ad4screen.sdk.common.e("event", "MOBILE_APP_INSTALL"));
                    arrayList.add(new com.ad4screen.sdk.common.e("sdk", "android"));
                    arrayList.add(new com.ad4screen.sdk.common.e("format", "json"));
                    arrayList.add(new com.ad4screen.sdk.common.e("application_tracking_enabled", "1"));
                    arrayList.add(new com.ad4screen.sdk.common.e("auto_publish", "1"));
                    arrayList.add(new com.ad4screen.sdk.common.e("application_package_name", a.p()));
                    if (a2 != null) {
                        arrayList.add(new com.ad4screen.sdk.common.e("attribution", a2));
                        Log.info("Facebook|Attribution Id found");
                        com.ad4screen.sdk.common.e[] eVarArr = (com.ad4screen.sdk.common.e[]) arrayList.toArray(new com.ad4screen.sdk.common.e[arrayList.size()]);
                        String[] strArr = new String[eVarArr.length];
                        for (int i = 0; i < eVarArr.length; i++) {
                            strArr[i] = eVarArr[i].a + "=" + eVarArr[i].b;
                        }
                        a2 = "https://graph.facebook.com/" + String.format("%s/activities", new Object[]{a.K()});
                        String a3 = h.a("&", strArr);
                        Log.internal("Facebook|Posting app attribution data @ " + a2 + ": " + a3);
                        a2 = d.a(a2, a3.getBytes());
                        Log.internal("Facebook|Facebook app installation attribution response : " + a2);
                        if (a2 == null) {
                            Log.info("Facebook|Could not post app attribution, trying again later");
                            return false;
                        }
                        com.ad4screen.sdk.d.d.a(this.b).e(com.ad4screen.sdk.d.d.b.FacebookTrackingWebservice);
                        Log.debug("Facebook|Facebook tracking succeeded !");
                        a.a(this.b);
                        Editor edit = sharedPreferences.edit();
                        edit.putLong(str, System.currentTimeMillis());
                        edit.commit();
                        return true;
                    }
                    Log.info("Facebook|No Attribution Id returned from Facebook Application");
                    return a.a(this.b);
                }
                Log.info("Facebook|Server reported Facebook Attribution is not supported by appId : " + a.K());
                return a.a(this.b);
            } catch (Throwable e2) {
                Log.error("Facebook|Error while publishing Facebook app attribution", e2);
                return false;
            }
        }
        Log.internal("Facebook|Facebook Tracking interrupted. Not sending facebook open/installation tracking");
        return true;
    }

    public String getClassKey() {
        return "com.facebook.sdk.tracking";
    }
}
