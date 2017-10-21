package com.ad4screen.sdk.service.modules.e;

import com.ad4screen.sdk.plugins.model.Geofence;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class a {
    private Map<String, Geofence> a = new HashMap();
    private Date b;
    private boolean c = false;
    private boolean d = false;

    public Map<String, Geofence> a() {
        return this.a;
    }

    public void a(Date date) {
        this.b = new Date(date.getTime());
    }

    public void a(boolean z) {
        this.c = z;
    }

    public Date b() {
        return new Date(this.b.getTime());
    }

    public void b(boolean z) {
        this.d = z;
    }

    public boolean c() {
        return this.c;
    }

    public boolean d() {
        return this.d;
    }
}
