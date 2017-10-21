package com.ad4screen.sdk.service.modules.k.a;

import android.content.Context;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.analytics.Cart;
import com.ad4screen.sdk.analytics.Lead;
import com.ad4screen.sdk.common.c.e;
import com.ad4screen.sdk.common.e.c;
import com.ad4screen.sdk.common.g;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.d.b;
import com.ad4screen.sdk.d.d;

import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

public class a extends c {
    private final String c = "com.ad4screen.sdk.service.modules.tracking.EventCartTrackingTask";
    private final String d = "content";
    private final Context e;
    private final b f;
    private String g;
    private Cart h;

    public a(Context context, b bVar, Cart cart) {
        super(context);
        this.e = context;
        this.f = bVar;
        this.h = cart;
    }

    private a(Context context, b bVar, String str) throws JSONException {
        super(context);
        this.e = context;
        this.f = bVar;
        super.e(str);
        JSONObject jSONObject = new JSONObject(str).getJSONObject("com.ad4screen.sdk.service.modules.tracking.EventCartTrackingTask");
        if (!jSONObject.isNull("content")) {
            this.g = jSONObject.getString("content");
        }
    }

    protected void a(String str) {
        Log.debug("EventCartTrackingTask|Successfully sent cart events to server");
        d.a(this.e).e(d.b.EventCartWebservice);
    }

    protected void a(Throwable th) {
        Log.error("EventCartTrackingTask|Failed to send cart events to server");
    }

    protected boolean a() {
        i();
        j();
        if (this.h == null) {
            Log.debug("Cart is null, cannot send event");
            return false;
        } else if (!d.a(this.e).c(d.b.EventWebservice)) {
            Log.debug("Service interruption on EventTrackingTask");
            return false;
        } else if (this.f.c() == null) {
            Log.warn("EventCartTrackingTask|SharedId is undefined, cannot send event");
            return false;
        } else {
            try {
                JSONObject a = new e().a(this.h);
                if (this.f.d() != null) {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put(Lead.KEY_VALUE, this.f.d());
                    jSONObject.put("date", this.f.e());
                    a.put("source", jSONObject);
                }
                a.put("date", h.a());
                a.put("ruuid", h.b());
                this.g = a.toString();
                return true;
            } catch (Throwable e) {
                Log.error("EventCartTrackingTask|Could not build message to send to server", e);
                return false;
            }
        }
    }

    protected boolean a(int i, String str) {
        if (i == 500 && str != null) {
            Log.debug("EventCartTrackingTask|Request succeed but parameters are invalid, server returned :" + str);
            try {
                for (com.ad4screen.sdk.service.modules.k.d.a.a aVar : new com.ad4screen.sdk.service.modules.k.d.a().a(str).a()) {
                    if (aVar.a().toLowerCase(Locale.US).contains("currency")) {
                        Log.error("EventCartTrackingTask|Error with this cart : " + aVar.b());
                        return true;
                    }
                }
            } catch (Throwable e) {
                Log.internal("EventCartTrackingTask|Error Parsing failed : " + e.getMessage(), e);
            }
        }
        return super.a(i, str);
    }

    public c b(c cVar) {
        return cVar;
    }

    protected String c() {
        return d.b.EventCartWebservice.toString() + "/" + g.e().a() + "/" + ((int) (Math.random() * 10000.0d));
    }

    protected String d() {
        return this.g;
    }

    public c e(String str) throws JSONException {
        return new a(this.e, this.f, str);
    }

    protected String e() {
        return d.a(this.e).a(d.b.EventCartWebservice);
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return e(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.tracking.EventCartTrackingTask";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject toJSON = super.toJSON();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("content", this.g);
        toJSON.put("com.ad4screen.sdk.service.modules.tracking.EventCartTrackingTask", jSONObject);
        return toJSON;
    }
}
