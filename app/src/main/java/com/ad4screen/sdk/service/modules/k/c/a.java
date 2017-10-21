package com.ad4screen.sdk.service.modules.k.c;

import android.content.Context;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.e.c;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.d.b;
import com.ad4screen.sdk.d.d;

import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class a extends c {
    private final Context c;
    private b d;
    private String e;
    private List<e> f;

    public a(Context context, b bVar, List<e> list) {
        super(context);
        this.c = context;
        this.d = bVar;
        this.f = list;
    }

    protected void a(String str) {
        d.a(this.c).e(d.b.ListsWebservice);
        try {
            JSONArray jSONArray = new JSONObject(str).getJSONArray("failed");
            if (jSONArray.length() != 0) {
                for (int i = 0; i < jSONArray.length(); i++) {
                    Log.error("ListsAddTask|Error : " + jSONArray.getJSONObject(i).getString("error") + " in the database. Make sure this list was created on the server-side");
                    Log.debug("ListsAddTask|Other lists were successfully subscribed to");
                }
                return;
            }
            Log.debug("ListsAddTask|Successfully subscribed to the lists");
        } catch (Throwable e) {
            Log.internal("ListsAddTask|Error Parsing failed : " + e.getMessage(), e);
        }
    }

    protected void a(Throwable th) {
        Log.error("ListsAddTask|StaticList failed", th);
    }

    protected boolean a() {
        i();
        j();
        if (this.d.c() == null) {
            Log.warn("ListsAddTask|No sharedId, skipping configuration");
            return false;
        } else if (d.a(this.c).c(d.b.ListsWebservice)) {
            try {
                JSONArray jSONArray = new JSONArray();
                for (e eVar : this.f) {
                    JSONObject jSONObject = new JSONObject();
                    if (eVar.b() != null) {
                        jSONObject.put("externalId", eVar.b());
                        if (eVar.c().getTime() != 0) {
                            jSONObject.put("expireAt", h.a(eVar.c(), com.ad4screen.sdk.common.h.a.ISO8601));
                        }
                    }
                    jSONArray.put(jSONObject);
                }
                Log.debug("ListsAddTask", jSONArray);
                this.e = new JSONObject().put("data", jSONArray).toString();
                return true;
            } catch (Throwable e) {
                Log.error("Accengage|Could not build message to send to server", e);
                return false;
            }
        } else {
            Log.debug("Service interruption on StaticListWebservice");
            return false;
        }
    }

    protected boolean a(int i, String str) {
        if (i == 404 && str != null) {
            Log.debug("ListsAddTask|Request succeeded but parameters are invalid, server returned :" + str);
            try {
                for (com.ad4screen.sdk.service.modules.k.d.a.a aVar : new com.ad4screen.sdk.service.modules.k.d.a().a(str).a()) {
                    if (aVar.a().toLowerCase(Locale.US).contains("api_err_db_table")) {
                        Log.error("ListsAddTask|Error with this list : " + aVar.b());
                        return true;
                    } else if (aVar.a().toLowerCase(Locale.US).contains("api_err_data")) {
                        Log.error("ListsAddTask|Error with this list : " + aVar.b());
                        return true;
                    }
                }
            } catch (Throwable e) {
                Log.internal("ListsAddTask|Error Parsing failed : " + e.getMessage(), e);
            }
        }
        if (i == 500 && str != null) {
            Log.debug("ListsAddTask|Request succeeded but parameters are invalid, server returned :" + str);
            try {
                for (com.ad4screen.sdk.service.modules.k.d.a.a aVar2 : new com.ad4screen.sdk.service.modules.k.d.a().a(str).a()) {
                    if (aVar2.a().toLowerCase(Locale.US).contains("api_err_db")) {
                        Log.error("ListsAddTask|Error with this list : " + aVar2.b());
                        return true;
                    }
                }
            } catch (Throwable e2) {
                Log.internal("ListsAddTask|Error Parsing failed : " + e2.getMessage(), e2);
            }
        }
        return super.a(i, str);
    }

    public c b(c cVar) {
        try {
            JSONArray jSONArray = new JSONObject(d()).getJSONArray("data");
            JSONArray jSONArray2 = new JSONObject(((a) cVar).d()).getJSONArray("data");
            for (int i = 0; i < jSONArray2.length(); i++) {
                jSONArray.put(jSONArray2.get(i));
            }
            this.e = new JSONObject().put("data", jSONArray).toString();
        } catch (JSONException e) {
            Log.debug("ListsAddTask|Can't merge these requests");
        }
        return this;
    }

    protected String c() {
        return d.b.ListsWebservice.toString();
    }

    protected String d() {
        return this.e;
    }

    protected String d(String str) {
        return "PUT";
    }

    public c e(String str) throws JSONException {
        super.e(str);
        JSONObject jSONObject = new JSONObject(str).getJSONObject("com.ad4screen.sdk.service.modules.tracking.lists.ListsAddTask");
        if (!jSONObject.isNull("content")) {
            this.e = jSONObject.getString("content");
        }
        return this;
    }

    protected String e() {
        return d.a(this.c).a(d.b.ListsWebservice);
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return e(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.tracking.lists.ListsAddTask";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject toJSON = super.toJSON();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("content", this.e);
        toJSON.put("com.ad4screen.sdk.service.modules.tracking.lists.ListsAddTask", jSONObject);
        return toJSON;
    }
}
