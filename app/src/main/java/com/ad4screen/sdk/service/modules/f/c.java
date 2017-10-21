package com.ad4screen.sdk.service.modules.f;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.analytics.Item;
import com.ad4screen.sdk.b.c.a;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class c {
    public com.ad4screen.sdk.b.c[] a;

    private a a(String str) {
        return str.equals("text") ? a.Text : str.equals("system") ? a.System : str.equals("richpush") ? a.Push : str.equals("web") ? a.Web : str.equals("event") ? a.Event : str.equals("url") ? a.Url : a.Close;
    }

    private com.ad4screen.sdk.b.c a(JSONArray jSONArray, com.ad4screen.sdk.b.c cVar) throws JSONException {
        com.ad4screen.sdk.b.a[] aVarArr = new com.ad4screen.sdk.b.a[jSONArray.length()];
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            com.ad4screen.sdk.b.a aVar = new com.ad4screen.sdk.b.a();
            aVar.a = jSONObject.getString("id");
            aVar.b = jSONObject.getString("title");
            jSONObject = jSONObject.getJSONObject("action");
            aVar.e = jSONObject.getString("trk");
            if (jSONObject.isNull("type")) {
                aVar.c = a("");
            } else {
                aVar.c = a(jSONObject.getString("type"));
            }
            if (!jSONObject.isNull("data")) {
                aVar.d = jSONObject.getString("data");
            }
            if (jSONObject.isNull("params") || jSONObject.optJSONArray("params") != null) {
                aVar.f = new HashMap();
            } else {
                JSONObject jSONObject2 = jSONObject.getJSONObject("params");
                Iterator keys = jSONObject2.keys();
                HashMap hashMap = new HashMap();
                while (keys.hasNext()) {
                    String str = (String) keys.next();
                    hashMap.put(str, jSONObject2.getString(str));
                }
                aVar.f = hashMap;
            }
            aVarArr[i] = aVar;
        }
        cVar.q = aVarArr;
        return cVar;
    }

    private com.ad4screen.sdk.b.c a(JSONObject jSONObject, com.ad4screen.sdk.b.c cVar) throws JSONException {
        cVar.n = true;
        try {
            Calendar instance = Calendar.getInstance();
            instance.setTimeZone(TimeZone.getTimeZone(jSONObject.getString("timezone")));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            simpleDateFormat.setCalendar(instance);
            cVar.c = simpleDateFormat.parse(jSONObject.getString("date"));
        } catch (Throwable e) {
            Log.internal("Date is incorrect for this message", e);
            e.printStackTrace();
        } catch (Throwable e2) {
            Log.internal("Date or Timezone is null or not found for this message", e2);
            e2.printStackTrace();
        }
        cVar.f = jSONObject.getString("from");
        cVar.b = jSONObject.getString("title");
        cVar.i = jSONObject.getString("text");
        cVar.g = jSONObject.getString(Item.KEY_CATEGORY);
        cVar.p = jSONObject.getString("icon");
        cVar.h = jSONObject.getString("trk");
        if (jSONObject.isNull("params")) {
            cVar.r = new HashMap();
        } else {
            JSONObject jSONObject2 = jSONObject.getJSONObject("params");
            Iterator keys = jSONObject2.keys();
            HashMap hashMap = new HashMap();
            while (keys.hasNext()) {
                String str = (String) keys.next();
                hashMap.put(str, jSONObject2.getString(str));
            }
            cVar.r = hashMap;
        }
        if (!jSONObject.isNull("content")) {
            cVar = c(jSONObject.getJSONObject("content"), cVar);
        }
        return !jSONObject.isNull("action") ? b(jSONObject.getJSONObject("action"), cVar) : cVar;
    }

    private void a(JSONArray jSONArray) throws JSONException {
        int i;
        int i2 = 0;
        for (i = 0; i < jSONArray.length(); i++) {
            i2 += jSONArray.getJSONObject(i).getJSONArray("messages").length();
        }
        this.a = new com.ad4screen.sdk.b.c[i2];
        i2 = 0;
        for (i = 0; i < jSONArray.length(); i++) {
            JSONArray jSONArray2 = jSONArray.getJSONObject(i).getJSONArray("messages");
            int i3 = 0;
            while (i3 < jSONArray2.length()) {
                this.a[i2] = b(jSONArray2.getJSONObject(i3));
                i3++;
                i2++;
            }
        }
    }

    private com.ad4screen.sdk.b.c b(JSONObject jSONObject) throws JSONException {
        com.ad4screen.sdk.b.c cVar = new com.ad4screen.sdk.b.c();
        cVar.a = jSONObject.getString("id");
        JSONObject jSONObject2 = jSONObject.getJSONObject("status");
        cVar.l = jSONObject2.getBoolean("read");
        if (jSONObject2.has("expired")) {
            cVar.k = jSONObject2.getBoolean("expired");
        }
        if (jSONObject2.has("archived")) {
            cVar.m = jSONObject2.getBoolean("archived");
        }
        if (!jSONObject.isNull("details")) {
            return a(jSONObject.getJSONObject("details"), cVar);
        }
        cVar.f = "";
        cVar.b = "";
        cVar.i = "";
        cVar.g = "";
        cVar.p = "";
        cVar.h = "";
        cVar.r = new HashMap();
        cVar.j = a.Text;
        cVar.d = "";
        cVar.c = new Date();
        cVar.e = "";
        cVar.q = new com.ad4screen.sdk.b.a[0];
        return cVar;
    }

    private com.ad4screen.sdk.b.c b(JSONObject jSONObject, com.ad4screen.sdk.b.c cVar) throws JSONException {
        cVar.d = "";
        cVar.q = new com.ad4screen.sdk.b.a[0];
        cVar.j = a(jSONObject.getString("type"));
        cVar.e = jSONObject.getString("data");
        return cVar;
    }

    private void b(JSONArray jSONArray) throws JSONException {
        int i = 0;
        this.a = new com.ad4screen.sdk.b.c[jSONArray.length()];
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            this.a[i] = b(jSONArray.getJSONObject(i2));
            i++;
        }
    }

    private com.ad4screen.sdk.b.c c(JSONObject jSONObject, com.ad4screen.sdk.b.c cVar) throws JSONException {
        cVar.j = a(jSONObject.getString("type"));
        cVar.d = jSONObject.getString("body");
        if (!jSONObject.isNull("buttons")) {
            return a(jSONObject.getJSONArray("buttons"), cVar);
        }
        cVar.q = new com.ad4screen.sdk.b.a[0];
        return cVar;
    }

    public void a(JSONObject jSONObject) {
        try {
            if (!jSONObject.isNull("Response")) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("Response");
                Log.error("Inbox|Server returned an error while querying Inbox : ", new Exception(jSONObject2.getString("returnCode") + " - " + jSONObject2.getString("returnLabel")));
            } else if (jSONObject.isNull("inboxMessageList")) {
                b(jSONObject.getJSONArray("inboxMessageDetail"));
            } else {
                a(jSONObject.getJSONArray("inboxMessageList"));
            }
        } catch (Throwable e) {
            Log.internal("Inbox|Messages JSON Parsing error!", e);
            this.a = null;
        }
    }
}
