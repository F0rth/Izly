package com.ad4screen.sdk.service.modules.k;

import android.content.Context;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.e;
import com.ad4screen.sdk.common.e.c;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.d.d;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class b extends c {
    String[] c;
    String d;
    String e;
    private final String f = "com.ad4screen.sdk.service.modules.tracking.FacebookProfileTrackingTask";
    private final String g = "content";
    private final Context h;
    private String i;

    public b(Context context, String str, String str2, String[] strArr) {
        super(context);
        this.h = context;
        this.c = strArr;
        this.d = str2;
        this.e = str;
    }

    protected void a(String str) {
        Log.debug("Facebook|Send profile Success : " + str);
        d.a(this.h).e(com.ad4screen.sdk.d.d.b.FacebookProfileWebservice);
    }

    protected void a(Throwable th) {
        Log.internal("Ad4Screen|Facebook Profile Tracking Service error!", th);
    }

    protected boolean a() {
        int i = 0;
        com.ad4screen.sdk.d.b a = com.ad4screen.sdk.d.b.a(this.h);
        if (!d.a(this.h).c(com.ad4screen.sdk.d.d.b.FacebookProfileWebservice)) {
            Log.debug("Service interruption on FacebookProfileTrackingTask");
            return false;
        } else if (a.c() == null) {
            Log.warn("Facebook|SharedId is undefined, cannot send profile");
            return false;
        } else if (this.e == null) {
            Log.warn("Facebook|App Id is undefined, cannot send profile");
            return false;
        } else {
            c("application/x-www-form-urlencoded;charset=utf-8");
            ArrayList arrayList = new ArrayList();
            arrayList.add(new e("fb_appid", this.e));
            arrayList.add(new e("sharedid", a.c()));
            arrayList.add(new e("partnerid", a.l()));
            arrayList.add(new e("fb_token", this.d));
            String[] strArr = this.c;
            int length = strArr.length;
            while (i < length) {
                arrayList.add(new e("fb_permissions[]", strArr[i]));
                i++;
            }
            this.i = h.a((e[]) arrayList.toArray(new e[arrayList.size()]));
            return true;
        }
    }

    public c b(c cVar) {
        return cVar;
    }

    protected String c() {
        return com.ad4screen.sdk.d.d.b.FacebookProfileWebservice.toString();
    }

    protected String d() {
        return this.i;
    }

    public c e(String str) throws JSONException {
        super.e(str);
        JSONObject jSONObject = new JSONObject(str).getJSONObject("com.ad4screen.sdk.service.modules.tracking.FacebookProfileTrackingTask");
        if (!jSONObject.isNull("content")) {
            this.i = jSONObject.getString("content");
        }
        return this;
    }

    protected String e() {
        return d.a(this.h).a(com.ad4screen.sdk.d.d.b.FacebookProfileWebservice);
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return e(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.tracking.FacebookProfileTrackingTask";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject toJSON = super.toJSON();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("content", this.i);
        toJSON.put("com.ad4screen.sdk.service.modules.tracking.FacebookProfileTrackingTask", jSONObject);
        return toJSON;
    }
}
