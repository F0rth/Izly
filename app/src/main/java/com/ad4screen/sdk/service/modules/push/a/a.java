package com.ad4screen.sdk.service.modules.push.a;

import android.content.Context;
import android.os.Bundle;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.c.a.d;
import com.ad4screen.sdk.c.a.d.b;
import com.ad4screen.sdk.plugins.model.Beacon.PersonalParamsReplacer;
import com.ad4screen.sdk.plugins.model.Geofence;
import com.ad4screen.sdk.service.modules.a.a.c;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public final class a {
    static final /* synthetic */ boolean C = (!a.class.desiredAssertionStatus());
    public boolean A;
    public boolean B;
    private String D;
    private String E;
    public b a = b.URLConnection;
    public String b;
    public com.ad4screen.sdk.c.a.e.a c = com.ad4screen.sdk.c.a.e.a.Webview;
    public String d;
    public String e;
    public boolean f;
    public boolean g;
    public int h;
    public String i;
    public int j;
    public String k;
    public String l;
    public String m;
    public String n;
    public String o;
    public String p;
    public String q;
    public String r;
    public String s;
    public int t;
    public String u;
    public String v;
    public String w;
    public int x = 1001;
    public boolean y;
    public String z;

    public static a a(Bundle bundle) {
        if (bundle != null) {
            a aVar = new a();
            aVar.n = bundle.getString("a4scontent");
            if (aVar.n != null) {
                aVar.h = bundle.getInt("a4spriority");
                aVar.i = bundle.getString("a4scategory");
                aVar.j = bundle.getInt("a4saccentcolor");
                aVar.k = bundle.getString("a4ssmalliconname");
                aVar.m = bundle.getString("a4stitle");
                aVar.o = bundle.getString("a4sbigcontent");
                aVar.q = bundle.getString("a4stemplate");
                aVar.r = bundle.getString("a4sbigtemplate");
                aVar.l = bundle.getString("a4sicon");
                String string = bundle.getString("a4strk");
                aVar.a = b.URLConnection;
                if (string != null) {
                    try {
                        if (Integer.valueOf(string).intValue() == 3) {
                            aVar.a = b.None;
                        }
                    } catch (Throwable e) {
                        Log.internal(e.getMessage(), e);
                    }
                }
                aVar.A = a(bundle.getString("isAlarm"));
                aVar.b = bundle.getString("a4sid");
                aVar.d = bundle.getString("a4surl");
                aVar.e = bundle.getString("a4st");
                string = bundle.getString("openWithSafari");
                if (string != null && string.matches(".*[yYtT].*")) {
                    aVar.c = com.ad4screen.sdk.c.a.e.a.System;
                }
                aVar.v = bundle.getString("a4sok");
                aVar.w = bundle.getString("a4scancel");
                string = bundle.getString("a4ssysid");
                if (string != null) {
                    try {
                        aVar.x = Integer.valueOf(string).intValue();
                    } catch (NumberFormatException e2) {
                        Log.internal("Could not parse a4ssysid parameter : " + string);
                    }
                }
                aVar.g = a(bundle.getString("a4smultiplelines"));
                aVar.f = a(bundle.getString("a4spopup"));
                aVar.y = a(bundle.getString("a4sforeground"));
                aVar.p = bundle.getString("a4sbigpicture");
                aVar.B = a(bundle.get("displayed"));
                aVar.s = bundle.getString("a4snotifsound");
                try {
                    aVar.t = Integer.valueOf(bundle.getString("a4sbadgecount")).intValue();
                } catch (Throwable e3) {
                    Log.debug("Impossible to parse badge count, use default value 0", e3);
                    aVar.t = 0;
                }
                aVar.u = bundle.getString("a4sb");
                if (aVar.A) {
                    aVar.z = "LocalNotification#" + aVar.b;
                    return aVar;
                }
                aVar.z = "Notification#" + aVar.b;
                return aVar;
            }
        }
        return null;
    }

    private void a(d dVar, Bundle bundle, com.ad4screen.sdk.c.a.d.a aVar) {
        if (this.m != null) {
            this.m = d.a(this.m, aVar);
        }
        if (this.n != null) {
            this.n = d.a(this.n, aVar);
        }
        if (this.o != null) {
            this.o = d.a(this.o, aVar);
        }
        if (this.d != null) {
            this.d = d.a(this.d, aVar);
        }
        if (dVar instanceof c) {
            c cVar = (c) dVar;
            HashMap hashMap = new HashMap(2);
            hashMap.put("display", cVar.a());
            hashMap.put("click", cVar.b());
            for (Entry entry : hashMap.entrySet()) {
                Set<String> set = (Set) entry.getValue();
                String str = (String) entry.getKey();
                if (set != null) {
                    for (String str2 : set) {
                        String string = bundle.getString(str2);
                        if (string != null) {
                            string = d.a(string, aVar);
                            bundle.putString(str2, string);
                            Log.internal("NotificationParameters|Custom " + str + " parameter: " + str2 + " is replaced by '" + string + "'");
                        } else {
                            Log.error("NotificationParameters|There is no custom " + str + " parameter: " + str2);
                        }
                    }
                }
            }
        }
    }

    public static boolean a(Object obj) {
        if (obj != null) {
            if (obj instanceof Boolean) {
                return ((Boolean) obj).booleanValue();
            }
            if (obj instanceof String) {
                return ((String) obj).matches(".*[yYtT].*");
            }
        }
        return false;
    }

    public final String a() {
        return this.D;
    }

    public final void a(Context context, d dVar, Bundle bundle) {
        if (C || this.D != null) {
            a(dVar, bundle, new PersonalParamsReplacer(context, this.D));
            return;
        }
        throw new AssertionError();
    }

    public final void a(String str) {
        this.D = str;
    }

    public final String b() {
        return this.E;
    }

    public final void b(Context context, d dVar, Bundle bundle) {
        if (C || this.E != null) {
            a(dVar, bundle, new Geofence.PersonalParamsReplacer(context, this.E));
            return;
        }
        throw new AssertionError();
    }

    public final void b(String str) {
        this.E = str;
    }
}
