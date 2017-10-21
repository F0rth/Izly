package com.ad4screen.sdk.service.modules.k.c;

import android.content.Context;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.e.c;
import com.ad4screen.sdk.d.d;
import com.ad4screen.sdk.service.modules.k.d.a.a;

import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class b extends c {
    private final String c = "com.ad4screen.sdk.service.modules.tracking.lists.ListsDeleteTask";
    private final Context d;
    private com.ad4screen.sdk.d.b e;
    private StringBuilder f;
    private List<e> g;

    public b(Context context, List<e> list) {
        super(context);
        this.d = context;
        this.e = com.ad4screen.sdk.d.b.a(this.d);
        this.g = list;
        this.f = new StringBuilder();
    }

    protected void a(String str) {
        d.a(this.d).e(com.ad4screen.sdk.d.d.b.ListsWebservice);
        try {
            JSONArray jSONArray = new JSONObject(str).getJSONArray("failed");
            if (jSONArray.length() != 0) {
                for (int i = 0; i < jSONArray.length(); i++) {
                    Log.error("ListsDeleteTask|Error : " + jSONArray.getJSONObject(i).getString("error") + " in the database. Make sure this list was created on the server-side");
                    Log.debug("ListsDeleteTask|Other lists were successfully unsubscribed from");
                }
                return;
            }
            Log.debug("ListsDeleteTask|Successfully subscribed to the lists");
        } catch (Throwable e) {
            Log.internal("ListsDeleteTask|Error Parsing failed : " + e.getMessage(), e);
        }
    }

    protected void a(Throwable th) {
        Log.error("ListsDeleteTask|StaticList failed", th);
    }

    protected boolean a() {
        i();
        j();
        if (this.e.c() == null) {
            Log.warn("ListsDeleteTask|No sharedId, skipping configuration");
            return false;
        } else if (d.a(this.d).c(com.ad4screen.sdk.d.d.b.ListsWebservice)) {
            for (int i = 0; i < this.g.size(); i++) {
                if (!(this.f == null || this.f.toString().equals(""))) {
                    this.f.append("&");
                }
                e eVar = (e) this.g.get(i);
                if (eVar.b() != null) {
                    this.f.append("externalIds[]=");
                    this.f.append(eVar.b());
                }
            }
            return true;
        } else {
            Log.debug("Service interruption on StaticListWebservice");
            return false;
        }
    }

    protected boolean a(int i, String str) {
        if (i == 500 && str != null) {
            Log.debug("ListsDeleteTask|Request succeeded but parameters are invalid, server returned :" + str);
            try {
                for (a aVar : new com.ad4screen.sdk.service.modules.k.d.a().a(str).a()) {
                    if (aVar.a().toLowerCase(Locale.US).contains("api_err_db")) {
                        Log.error("ListsDeleteTask|Error with this list : " + aVar.b());
                        return true;
                    }
                }
            } catch (Throwable e) {
                Log.internal("ListsDeleteTask|Error Parsing failed : " + e.getMessage(), e);
            }
        }
        return super.a(i, str);
    }

    public c b(c cVar) {
        b().append("&").append(((b) cVar).b().toString());
        return this;
    }

    public StringBuilder b() {
        return this.f;
    }

    protected String c() {
        return com.ad4screen.sdk.d.d.b.ListsDeleteWebservice.toString();
    }

    protected String d() {
        return null;
    }

    protected String d(String str) {
        return "DELETE";
    }

    public c e(String str) throws JSONException {
        super.e(str);
        JSONObject jSONObject = new JSONObject(str).getJSONObject("com.ad4screen.sdk.service.modules.tracking.lists.ListsDeleteTask");
        if (!jSONObject.isNull("url")) {
            this.f = new StringBuilder();
            this.f.append(jSONObject.getString("url"));
        }
        return this;
    }

    protected String e() {
        return d.a(this.d).a(com.ad4screen.sdk.d.d.b.ListsWebservice) + "?" + this.f.toString();
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return e(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.tracking.lists.ListsDeleteTask";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject toJSON = super.toJSON();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("url", this.f.toString());
        toJSON.put("com.ad4screen.sdk.service.modules.tracking.lists.ListsDeleteTask", jSONObject);
        return toJSON;
    }
}
