package com.ad4screen.sdk.service.modules.k.f.a;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.b.m.i;
import com.ad4screen.sdk.common.c;
import com.ad4screen.sdk.common.c.e;
import com.ad4screen.sdk.common.e.b;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class d extends b {
    private JSONArray c;
    private Bundle d;

    public d(Context context, JSONArray jSONArray, Bundle bundle) {
        super(context);
        this.c = jSONArray;
        this.d = bundle;
    }

    public b a(String str) throws JSONException {
        super.a(str);
        JSONObject jSONObject = new JSONObject(str);
        if (!jSONObject.isNull("array")) {
            this.c = jSONObject.getJSONArray("array");
        }
        if (!jSONObject.isNull("bundle")) {
            this.d = (Bundle) new e().a(jSONObject.getString("bundle"), new Bundle());
        }
        return this;
    }

    public boolean a() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        com.ad4screen.sdk.d.b a = com.ad4screen.sdk.d.b.a(this.b);
        String K = a.K();
        if (K == null) {
            Log.info("Facebook|No Facebook AppId found, not sending facebook event.");
            return true;
        }
        String a2 = c.a(this.b);
        String a3 = c.a(this.b, c.b(this.b));
        if (com.ad4screen.sdk.d.d.a(this.b).c(com.ad4screen.sdk.d.d.b.FacebookTrackingWebservice)) {
            String string = this.d.getString("FBToken");
            if (string == null) {
                string = "";
                try {
                    Log.info("Facebook|No AccessToken found, not linking event with user.");
                } catch (Throwable e2) {
                    Log.error("Facebook|Error while sending Facebook event", e2);
                } finally {
                    i.a();
                }
            } else {
                Log.info("Facebook|AccessToken Found, this event is now linked with logged Facebook User");
                Log.internal("Facebook|Token :" + string);
            }
            string = "https://graph.facebook.com/" + K + "/activities?format" + "=json" + "&sdk" + "=android" + "&application_package_name" + "=" + Uri.encode(a.p()) + "&application_tracking_enabled" + "=1&advertiser_tracking_enabled" + "=1&access_token=" + string + "&event=CUSTOM_APP_EVENTS";
            r3 = a2 != null ? string + "&attribution=" + a2 : a3 != null ? string + "&advertiser_id=" + a3 : string;
            URL url = new URL(r3);
            i.a(17986);
            K = "custom_events=" + Uri.encode(this.c.toString());
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setRequestProperty("User-Agent", "FBAndroidSDK.4.7.0");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            Log.internal("Facebook|Sending query to : " + r3 + " with content : " + K);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(httpURLConnection.getOutputStream());
            bufferedOutputStream.write(K.getBytes());
            bufferedOutputStream.close();
            Log.internal("Facebook|Query sent");
            int responseCode = httpURLConnection.getResponseCode();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder(2048);
            for (string = bufferedReader.readLine(); string != null; string = bufferedReader.readLine()) {
                stringBuilder.append(string);
            }
            if (responseCode == 200) {
                Log.debug("Facebook|Send Facebook event success : " + this.c.toString());
                com.ad4screen.sdk.d.d.a(this.b).e(com.ad4screen.sdk.d.d.b.FacebookTrackingWebservice);
                i.a();
                return true;
            }
            Log.error("Facebook|Send Facebook event failed : " + responseCode + "  " + stringBuilder.toString());
            i.a();
            return false;
        }
        Log.internal("Facebook|Facebook Tracking interrupted. Not sending facebook event");
        return true;
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return a(str);
    }

    public String getClassKey() {
        return "com.facebook.sdk.event";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject toJSON = super.toJSON();
        if (this.c != null) {
            toJSON.put("array", this.c);
        }
        if (this.d != null) {
            toJSON.put("bundle", new e().a(this.d));
        }
        return toJSON;
    }
}
