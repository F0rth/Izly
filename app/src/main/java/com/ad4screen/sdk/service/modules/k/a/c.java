package com.ad4screen.sdk.service.modules.k.a;

import android.content.Context;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.analytics.Lead;
import com.ad4screen.sdk.analytics.Purchase;
import com.ad4screen.sdk.common.c.e;
import com.ad4screen.sdk.common.g;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.d.b;
import com.ad4screen.sdk.d.d;
import com.ad4screen.sdk.service.modules.k.d.a.a;

import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

public class c extends com.ad4screen.sdk.common.e.c {
    private final String c = "com.ad4screen.sdk.service.modules.tracking.EventPurchaseTrackingTask";
    private final String d = "content";
    private final Context e;
    private final b f;
    private String g;
    private Purchase h;

    public c(Context context, b bVar, Purchase purchase) {
        super(context);
        this.e = context;
        this.f = bVar;
        this.h = purchase;
    }

    private c(Context context, b bVar, String str) throws JSONException {
        super(context);
        this.e = context;
        this.f = bVar;
        super.e(str);
        JSONObject jSONObject = new JSONObject(str).getJSONObject("com.ad4screen.sdk.service.modules.tracking.EventPurchaseTrackingTask");
        if (!jSONObject.isNull("content")) {
            this.g = jSONObject.getString("content");
        }
    }

    protected void a(String str) {
        Log.debug("EventPurchaseTrackingTask|Successfully sent purchase events to server");
        d.a(this.e).e(d.b.EventPurchaseWebservice);
    }

    protected void a(Throwable th) {
        Log.error("EventPurchaseTrackingTask|Failed to send purchase events to server");
    }

    protected boolean a() {
        i();
        j();
        if (this.h == null) {
            Log.debug("Purchase is null, cannot send event");
            return false;
        } else if (!d.a(this.e).c(d.b.EventWebservice)) {
            Log.debug("Service interruption on EventTrackingTask");
            return false;
        } else if (this.f.c() == null) {
            Log.warn("EventPurchaseTrackingTask|SharedId is undefined, cannot send event");
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
                Log.error("EventPurchaseTrackingTask|Could not build message to send to server", e);
                return false;
            }
        }
    }

    protected boolean a(int i, String str) {
        if (i == 500 && str != null) {
            Log.debug("EventPurchaseTrackingTask|Request succeeded but parameters are invalid, server returned :" + str);
            try {
                for (a aVar : new com.ad4screen.sdk.service.modules.k.d.a().a(str).a()) {
                    if (aVar.a().toLowerCase(Locale.US).contains(Lead.KEY_VALUE)) {
                        Log.error("EventPurchaseTrackingTask|Error with this purchase : " + aVar.b());
                        return true;
                    } else if (aVar.a().toLowerCase(Locale.US).contains("currency")) {
                        Log.error("EventPurchaseTrackingTask|Error with this purchase : " + aVar.b());
                        return true;
                    }
                }
            } catch (Throwable e) {
                Log.internal("EventPurchaseTrackingTask|Error Parsing failed : " + e.getMessage(), e);
            }
        }
        return super.a(i, str);
    }

    public com.ad4screen.sdk.common.e.c b(com.ad4screen.sdk.common.e.c cVar) {
        return cVar;
    }

    protected String c() {
        return d.b.EventPurchaseWebservice.toString() + "/" + g.e().a() + "/" + ((int) (Math.random() * 10000.0d));
    }

    protected String d() {
        return this.g;
    }

    public com.ad4screen.sdk.common.e.c e(String str) throws JSONException {
        return new c(this.e, this.f, str);
    }

    protected String e() {
        return d.a(this.e).a(d.b.EventPurchaseWebservice);
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return e(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.tracking.EventPurchaseTrackingTask";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject toJSON = super.toJSON();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("content", this.g);
        toJSON.put("com.ad4screen.sdk.service.modules.tracking.EventPurchaseTrackingTask", jSONObject);
        return toJSON;
    }
}
