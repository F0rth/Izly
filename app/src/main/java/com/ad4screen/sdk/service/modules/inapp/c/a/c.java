package com.ad4screen.sdk.service.modules.inapp.c.a;

import android.content.Context;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.d.g;
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
        List f = jVar.o().f();
        return (f == null || f.isEmpty()) ? false : true;
    }

    public String a() {
        return "GeofenceExclusionCheck";
    }

    public void a(j jVar, h hVar) {
    }

    public boolean a(e eVar, j jVar, h hVar) {
        this.a = null;
        if (!a(jVar)) {
            Log.internal("GeofenceExclusionCheck|isValid no rules");
            return true;
        } else if (this.b != null) {
            return !a(jVar.o().f());
        } else {
            Log.warn("GeofenceExclusionCheck|isValid location is not detected");
            return true;
        }
    }
}
