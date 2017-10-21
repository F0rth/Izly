package com.ad4screen.sdk.service.modules.i;

import android.content.Context;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.e.c;
import com.ad4screen.sdk.d.d;
import com.ad4screen.sdk.d.d.b;
import com.ad4screen.sdk.d.f;

import org.json.JSONObject;

public class a extends c {
    private final String c = "com.ad4screen.sdk.service.modules.permissions.LoadPermissionsTask";
    private final Context d;

    public a(Context context) {
        super(context);
        this.d = context;
    }

    protected void a(String str) {
        Log.debug("Permission|New Permissions received");
        d.a(this.d).e(b.PermissionsWebservice);
        try {
            Log.internal("Permission|Permissions start parsing");
            com.ad4screen.sdk.service.modules.i.a.a aVar = new com.ad4screen.sdk.service.modules.i.a.a();
            aVar.a(new JSONObject(str));
            if (aVar.a == null) {
                Log.error("Permission|Permissions parsing failed");
                return;
            }
            Log.internal("Permission|Permissions parsing success");
            Log.debug("Permission|Received " + aVar.a.length + " permissions");
            f.a().a(new b.b(aVar.a));
        } catch (Throwable e) {
            Log.internal("Permission|Permissions Parsing error!", e);
        }
    }

    protected void a(Throwable th) {
        Log.error("Permission|Failed to retrieve Permissions from server");
    }

    protected boolean a() {
        c("application/json;charset=utf-8");
        i();
        if (com.ad4screen.sdk.d.b.a(this.d).c() == null) {
            Log.warn("Permission|No sharedId, skipping configuration");
            return false;
        } else if (d.a(this.d).c(b.PermissionsWebservice)) {
            return true;
        } else {
            Log.debug("Service interruption on LoadPermissionsTask");
            return false;
        }
    }

    public c b(c cVar) {
        return cVar;
    }

    protected String c() {
        return b.PermissionsWebservice.toString();
    }

    protected String d() {
        return null;
    }

    protected String e() {
        return d.a(this.d).a(b.PermissionsWebservice);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.permissions.LoadPermissionsTask";
    }
}
