package com.ad4screen.sdk.service.modules.inapp.c.b;

import android.content.Context;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.d.g;
import com.ad4screen.sdk.plugins.model.Geofence;
import com.ad4screen.sdk.service.modules.inapp.a.e;
import com.ad4screen.sdk.service.modules.inapp.a.h;
import com.ad4screen.sdk.service.modules.inapp.a.j;
import com.ad4screen.sdk.service.modules.inapp.c.i;

import java.util.List;

public class c extends i {
    public c(Context context, g gVar) {
        super(context, gVar);
    }

    private boolean a(j jVar) {
        List f = jVar.n().f();
        return (f == null || f.isEmpty()) ? false : true;
    }

    public String a() {
        return "GeofenceInclusionCheck";
    }

    public void a(j jVar, h hVar) {
    }

    public boolean a(e eVar, j jVar, h hVar) {
        this.a = null;
        if (!a(jVar)) {
            Log.internal("GeofenceInclusionCheck|isValid no rules");
            return true;
        } else if (this.b != null) {
            return a(jVar.n().f());
        } else {
            Log.warn("GeofenceInclusionCheck|isValid location is not detected");
            return false;
        }
    }

    public Geofence b() {
        return this.a;
    }
}
