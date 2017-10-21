package com.ad4screen.sdk.service.modules.inapp.c;

import android.content.Context;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.a;
import com.ad4screen.sdk.d.d;
import com.ad4screen.sdk.d.d.b;
import com.ad4screen.sdk.service.modules.inapp.a.e;
import com.ad4screen.sdk.service.modules.inapp.a.h;
import com.ad4screen.sdk.service.modules.inapp.a.j;

import java.util.Date;

public class k implements m {
    private a a;
    private Context b;
    private boolean c;
    private Date d;
    private boolean e;

    public k(a aVar, Context context) {
        this.a = aVar;
        this.b = context;
    }

    private boolean b() {
        return this.a.b() - this.d.getTime() > 7776000000L;
    }

    private boolean c() {
        return this.a.b() - this.d.getTime() < 300000;
    }

    public String a() {
        return "OfflineDisplayCheck";
    }

    public void a(Context context, com.ad4screen.sdk.service.modules.inapp.k kVar) {
        this.c = kVar.m();
        this.d = kVar.n();
        if (this.d == null) {
            this.d = new Date(0);
        }
        this.e = kVar.o();
    }

    public void a(j jVar, h hVar) {
    }

    public boolean a(e eVar, j jVar, h hVar) {
        if (this.c) {
            Log.internal("OfflineDisplayCheck|isInAppConfigUpdated true");
            return true;
        } else if (this.e && c()) {
            Log.internal("OfflineDisplayCheck|fromBeaconOrGeofence true and isFreshInAppConfig true (" + this.d + ")");
            return true;
        } else if (!d.a(this.b).c(b.OfflineInAppDisplay)) {
            Log.debug("Service interruption on OfflineDisplayCheck");
            return false;
        } else if (!b()) {
            return jVar.r();
        } else {
            Log.internal("OfflineDisplayCheck|isExpiredInAppConfig true (" + this.d + ")");
            return false;
        }
    }
}
