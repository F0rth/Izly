package com.ad4screen.sdk.service.modules.f;

import android.content.Context;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.e.c;
import com.ad4screen.sdk.d.d;
import com.ad4screen.sdk.d.d.b;
import com.ad4screen.sdk.h;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class e extends c {
    private final String c = "com.ad4screen.sdk.service.modules.inbox.UpdateMessagesTask";
    private final String d = "content";
    private final Context e;
    private String f;
    private com.ad4screen.sdk.b.c[] g;
    private h h;

    public e(com.ad4screen.sdk.b.c[] cVarArr, Context context, h hVar) {
        super(context);
        this.e = context;
        this.g = cVarArr;
        this.h = hVar;
    }

    protected void a(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (!jSONObject.isNull("Response")) {
                jSONObject = jSONObject.getJSONObject("Response");
                if (jSONObject.getInt("returnCode") == 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < this.g.length; i++) {
                        if (this.g[i].o) {
                            stringBuilder.append(this.g[i].a + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
                            this.g[i].o = false;
                        }
                    }
                    Log.debug("Inbox|Successfully updated inbox { Updated Messages : [ " + stringBuilder.toString() + "]}");
                    d.a(this.e).e(b.InboxMessageUpdateWebservice);
                    this.h.a(this.g);
                    return;
                }
                Log.error("Inbox|Send update Failure with error : " + jSONObject.getString("returnLabel"));
                this.h.a();
            }
        } catch (Throwable e) {
            Log.error("Inbox|Can't parse server response", e);
        } catch (Throwable e2) {
            Log.error("Inbox|No callback to trigger at the end of updateMessages method", e2);
        }
    }

    protected void a(Throwable th) {
        Log.error("Inbox|Messages update Failure : No connection");
    }

    protected boolean a() {
        c("application/json;charset=utf-8");
        a(4);
        com.ad4screen.sdk.d.b a = com.ad4screen.sdk.d.b.a(this.e);
        if (a.c() == null) {
            Log.warn("Inbox|No sharedId, skipping tracking");
            return false;
        } else if (d.a(this.e).c(b.InboxMessageUpdateWebservice)) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("partnerId", a.l());
                jSONObject.put("sharedId", a.c());
                JSONArray jSONArray = new JSONArray();
                for (int i = 0; i < this.g.length; i++) {
                    if (this.g[i].o) {
                        JSONObject jSONObject2 = new JSONObject();
                        jSONObject2.put("id", this.g[i].a);
                        jSONObject2.put("read", this.g[i].l);
                        jSONObject2.put("archived", this.g[i].m);
                        jSONArray.put(jSONObject2);
                    }
                }
                if (jSONArray.length() == 0) {
                    Log.debug("Inbox|No Update to send");
                    return false;
                }
                jSONObject.put("messages", jSONArray);
                this.f = jSONObject.toString();
                return true;
            } catch (Throwable e) {
                Log.error("Inbox|Could not build message to send to Ad4Screen", e);
                return false;
            }
        } else {
            Log.debug("Service interruption on UpdateMessagesTask");
            return false;
        }
    }

    public c b(c cVar) {
        return cVar;
    }

    protected String c() {
        return b.InboxMessageUpdateWebservice.toString();
    }

    protected String d() {
        return this.f;
    }

    public c e(String str) throws JSONException {
        super.e(str);
        JSONObject jSONObject = new JSONObject(str).getJSONObject("com.ad4screen.sdk.service.modules.inbox.UpdateMessagesTask");
        if (!jSONObject.isNull("content")) {
            this.f = jSONObject.getString("content");
        }
        return this;
    }

    protected String e() {
        return d.a(this.e).a(b.InboxMessageUpdateWebservice);
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return e(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.inbox.UpdateMessagesTask";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject toJSON = super.toJSON();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("content", this.f);
        toJSON.put("com.ad4screen.sdk.service.modules.inbox.UpdateMessagesTask", jSONObject);
        return toJSON;
    }
}
