package com.ad4screen.sdk.service.modules.k.c;

import android.content.Context;
import android.os.RemoteException;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.e.c;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.common.h.a;
import com.ad4screen.sdk.d.b;
import com.ad4screen.sdk.j;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class d extends c {
    private final String c = "com.ad4screen.sdk.service.modules.tracking.lists.ListsGetSubscribedLists";
    private final Context d;
    private b e;
    private String f;
    private j g;

    public d(Context context, j jVar) {
        super(context);
        this.d = context;
        this.e = b.a(this.d);
        this.g = jVar;
    }

    protected void a(String str) {
        try {
            JSONArray jSONArray = new JSONObject(str).getJSONArray("lists");
            List arrayList = new ArrayList();
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                arrayList.add(new e(jSONObject.getString("externalId"), jSONObject.getString("name"), h.a(jSONObject.getString("expireAt"), a.ISO8601), "OK"));
            }
            if (this.g != null) {
                this.g.a(arrayList);
            }
        } catch (Throwable e) {
            Log.error("ListsGetSubscribedLists|Failed parsing response from server", e);
            try {
                if (this.g != null) {
                    this.g.a(0, "Response returned from the server is invalid");
                }
            } catch (Throwable e2) {
                Log.error("ListsGetStatusTask|Exception when trying to return error through callback", e2);
            }
        } catch (Throwable e22) {
            Log.error("ListsGetStatusTask|No callback to trigger at the end of updateMessages method", e22);
            if (this.g != null) {
                this.g.a(0, "Response returned from the server is invalid");
            }
        }
    }

    protected void a(Throwable th) {
        Log.error("ListsGetSubscribedLists|StaticList failed", th);
        try {
            if (this.g != null) {
                this.g.a(0, "Server is not reachable (are you offline?)");
            }
        } catch (RemoteException e) {
            Log.error("ListsGetStatusTask|Exception when trying to return error through callback", th);
        }
    }

    protected boolean a() {
        a(4);
        i();
        if (this.e.c() == null) {
            Log.warn("ListsGetSubscribedLists|No sharedId, skipping configuration");
            return false;
        } else if (com.ad4screen.sdk.d.d.a(this.d).c(com.ad4screen.sdk.d.d.b.ListsSubscriptionWebservice)) {
            return true;
        } else {
            Log.debug("Service interruption on StaticListWebservice");
            return false;
        }
    }

    public c b(c cVar) {
        return this;
    }

    protected String c() {
        return com.ad4screen.sdk.d.d.b.ListsSubscriptionWebservice.toString();
    }

    protected String d() {
        return null;
    }

    public c e(String str) throws JSONException {
        super.e(str);
        JSONObject jSONObject = new JSONObject(str).getJSONObject("com.ad4screen.sdk.service.modules.tracking.lists.ListsGetSubscribedLists");
        if (!jSONObject.isNull("content")) {
            this.f = jSONObject.getString("content");
        }
        return this;
    }

    protected String e() {
        return com.ad4screen.sdk.d.d.a(this.d).a(com.ad4screen.sdk.d.d.b.ListsSubscriptionWebservice);
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return e(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.tracking.lists.ListsGetSubscribedLists";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject toJSON = super.toJSON();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("content", this.f);
        toJSON.put("com.ad4screen.sdk.service.modules.tracking.lists.ListsGetSubscribedLists", jSONObject);
        return toJSON;
    }
}
