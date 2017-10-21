package com.ad4screen.sdk.service.modules.k.c;

import android.content.Context;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.common.h.a;
import com.ad4screen.sdk.d.b;
import com.ad4screen.sdk.d.d;
import com.ad4screen.sdk.j;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class c extends com.ad4screen.sdk.common.e.c {
    private final String c = "com.ad4screen.sdk.service.modules.tracking.lists.ListGetStatusTask";
    private final Context d;
    private List<e> e;
    private b f;
    private StringBuilder g;
    private j h;

    public c(Context context, List<e> list, j jVar) {
        super(context);
        this.d = context;
        this.f = b.a(this.d);
        this.h = jVar;
        this.e = list;
        this.g = new StringBuilder();
    }

    protected void a(String str) {
        Log.debug("ListsGetStatusTask|Successfully received status");
        try {
            JSONArray jSONArray = new JSONObject(str).getJSONArray("data");
            if (jSONArray != null) {
                List arrayList = new ArrayList();
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    if (jSONObject.has("expireAt")) {
                        arrayList.add(new e(jSONObject.getString("externalId"), h.a(jSONObject.getString("expireAt"), a.ISO8601), jSONObject.getString("status")));
                    } else {
                        arrayList.add(new e(jSONObject.getString("externalId"), new Date(0), jSONObject.getString("status")));
                    }
                }
                Log.debug("ListsGetStatusTask|Successfully parsed status from server");
                d.a(this.d).e(d.b.ListsWebservice);
                if (this.h != null) {
                    this.h.a(arrayList);
                    return;
                }
                return;
            }
        } catch (Throwable e) {
            Log.error("ListsGetStatusTask|Failed parsing response from server", e);
        } catch (Throwable e2) {
            Log.error("ListsGetStatusTask|No callback to trigger at the end of updateMessages method", e2);
        }
        try {
            if (this.h != null) {
                this.h.a(0, "Response returned from the server is invalid");
            }
        } catch (Throwable e22) {
            Log.error("ListsGetStatusTask|Exception when trying to return error through callback", e22);
        }
    }

    protected void a(Throwable th) {
        Log.error("ListsGetStatusTask|StaticList failed", th);
        try {
            if (this.h != null) {
                this.h.a(0, "Server is not reachable (are you offline?)");
            }
        } catch (Throwable e) {
            Log.error("ListsGetStatusTask|Exception when trying to return error through callback", e);
        }
    }

    protected boolean a() {
        a(4);
        i();
        if (this.f.c() == null) {
            Log.warn("ListsGetStatusTask|No sharedId, skipping configuration");
            return false;
        } else if (d.a(this.d).c(d.b.ListsWebservice)) {
            for (e b : this.e) {
                String b2 = b.b();
                if (b2 != null) {
                    if (!"".equals(this.g)) {
                        this.g.append("&");
                    }
                    this.g.append("externalIds[]=");
                    try {
                        this.g.append(URLEncoder.encode(b2, "UTF-8"));
                    } catch (Throwable e) {
                        Log.error("Error during encoding list id", e);
                    }
                }
            }
            return true;
        } else {
            Log.debug("Service interruption on StaticListWebservice");
            return false;
        }
    }

    protected boolean a(int i, String str) {
        if (i != 500) {
            return super.a(i, str);
        }
        if (str == null) {
            return true;
        }
        Log.debug("ListsGetStatusTask|Request succeeded but parameters are invalid, server returned :" + str);
        if (this.h == null) {
            return true;
        }
        try {
            this.h.a(1, "This list does not exist");
            return true;
        } catch (Throwable e) {
            Log.error("ListsGetStatusTask|Exception when trying to return error through callback", e);
            return true;
        }
    }

    public com.ad4screen.sdk.common.e.c b(com.ad4screen.sdk.common.e.c cVar) {
        return this;
    }

    protected String c() {
        return d.b.ListsStatusWebservice.toString();
    }

    protected String d() {
        return null;
    }

    protected String e() {
        return d.a(this.d).a(d.b.ListsWebservice) + "?" + this.g.toString();
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.tracking.lists.ListGetStatusTask";
    }
}
