package com.ad4screen.sdk.service.modules.f;

import android.content.Context;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.e.c;
import com.ad4screen.sdk.d.d.b;
import com.ad4screen.sdk.d.f;
import com.ad4screen.sdk.service.modules.f.a.a;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class d extends c {
    private final String c = "com.ad4screen.sdk.service.modules.inbox.LoadMessagesTask";
    private final String d = "content";
    private final Context e;
    private String f;
    private String[] g;

    public d(Context context) {
        super(context);
        this.e = context;
    }

    public d(String[] strArr, Context context) {
        super(context);
        this.g = strArr;
        this.e = context;
    }

    protected void a(String str) {
        try {
            Log.debug("Inbox|Successfully loaded messages");
            Log.internal("Inbox|Messages start parsing");
            JSONObject jSONObject = new JSONObject(str);
            c cVar = new c();
            cVar.a(jSONObject);
            if (cVar.a == null) {
                Log.error("Inbox|Messages parsing failed");
                f.a().a(new a());
                return;
            }
            Log.internal("Inbox|Messages parsing success");
            if (this.g != null) {
                com.ad4screen.sdk.d.d.a(this.e).e(b.InboxMessageDetailsWebservice);
            } else {
                Log.debug("Inbox|" + cVar.a.length + " inbox messages loaded");
                com.ad4screen.sdk.d.d.a(this.e).e(b.InboxMessageListWebservice);
            }
            f.a().a(new a.b(cVar.a));
        } catch (Throwable e) {
            Log.internal("Inbox|Response JSON Parsing error!", e);
            f.a().a(new a());
        }
    }

    protected void a(Throwable th) {
        Log.debug("Inbox|Failed to load inbox messages");
        f.a().a(new a());
    }

    protected boolean a() {
        c("application/json;charset=utf-8");
        a(4);
        com.ad4screen.sdk.d.b a = com.ad4screen.sdk.d.b.a(this.e);
        if (a.c() == null) {
            Log.warn("Inbox|No sharedId, skipping configuration");
            f.a().a(new a());
            return false;
        } else if (this.g == null && !com.ad4screen.sdk.d.d.a(this.e).c(b.InboxMessageListWebservice)) {
            Log.debug("Service interruption on LoadMessagesTask (List)");
            f.a().a(new a());
            return false;
        } else if (this.g == null || com.ad4screen.sdk.d.d.a(this.e).c(b.InboxMessageDetailsWebservice)) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("partnerId", a.l());
                jSONObject.put("sharedId", a.c());
                if (this.g != null) {
                    JSONArray jSONArray = new JSONArray();
                    for (Object put : this.g) {
                        jSONArray.put(put);
                    }
                    jSONObject.put("messageId", jSONArray);
                }
                this.f = jSONObject.toString();
                return true;
            } catch (Throwable e) {
                Log.error("Inbox|Could not build message to send to Ad4Screen", e);
                f.a().a(new a());
                return false;
            }
        } else {
            Log.debug("Service interruption on LoadMessageTask (Detail)");
            f.a().a(new a());
            return false;
        }
    }

    public c b(c cVar) {
        return cVar;
    }

    protected String c() {
        return b.InboxMessageListWebservice.toString();
    }

    protected String d() {
        return this.f;
    }

    public c e(String str) throws JSONException {
        super.e(str);
        JSONObject jSONObject = new JSONObject(str).getJSONObject("com.ad4screen.sdk.service.modules.inbox.LoadMessagesTask");
        if (!jSONObject.isNull("content")) {
            this.f = jSONObject.getString("content");
        }
        return this;
    }

    protected String e() {
        return this.g != null ? com.ad4screen.sdk.d.d.a(this.e).a(b.InboxMessageDetailsWebservice) : com.ad4screen.sdk.d.d.a(this.e).a(b.InboxMessageListWebservice);
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return e(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.inbox.LoadMessagesTask";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject toJSON = super.toJSON();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("content", this.f);
        toJSON.put("com.ad4screen.sdk.service.modules.inbox.LoadMessagesTask", jSONObject);
        return toJSON;
    }
}
