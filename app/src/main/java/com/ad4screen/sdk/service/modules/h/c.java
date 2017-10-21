package com.ad4screen.sdk.service.modules.h;

import android.content.Context;

import com.ad4screen.sdk.A4S.Callback;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.g;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.common.h.a;
import com.ad4screen.sdk.service.modules.h.a.b;

import java.util.ArrayList;
import java.util.Date;

public final class c {
    private static c c;
    private final d a;
    private final Context b;

    private c(Context context) {
        this.b = context;
        this.a = new d(context);
    }

    private int a(String str, ArrayList<b> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (((b) arrayList.get(i)).a.equals(str)) {
                Log.internal("MemberManager|Found member " + str + " on this device. Last connection : " + h.a(new Date(((b) arrayList.get(i)).c), a.ISO8601));
                return i;
            }
        }
        return -1;
    }

    public static c a(Context context) {
        return a(context, false);
    }

    public static c a(Context context, boolean z) {
        synchronized (c.class) {
            try {
                if (c == null || z) {
                    c = new c(context.getApplicationContext());
                }
            } catch (Throwable th) {
                while (true) {
                    Class cls = c.class;
                }
            }
        }
        return c;
    }

    private void a(b bVar) {
        if (bVar == null) {
            this.a.f();
        } else {
            this.a.a(bVar);
        }
    }

    private b b(String str) {
        com.ad4screen.sdk.service.modules.h.a.a d = d();
        ArrayList arrayList = d.a;
        int a = a(str, arrayList);
        if (a != -1) {
            ((b) arrayList.get(a)).c = g.e().a();
            b bVar = (b) arrayList.get(a);
            bVar.b++;
            d.a = arrayList;
            new a((b) arrayList.get(a), this.b).run();
            this.a.a(d);
            return (b) arrayList.get(a);
        }
        bVar = new b(str);
        Log.debug("MemberManager|Linking member " + str + " to this device");
        arrayList.add(bVar);
        new a(bVar, this.b).run();
        this.a.a(d);
        Log.internal("MemberManager|Member " + str + " is now linked to this device");
        return bVar;
    }

    public final void a() {
        if (c()) {
            Log.debug("MemberManager|Logging out member : " + b());
            a(null);
            Log.internal("MemberManager|Logged out");
            return;
        }
        Log.internal("MemberManager|No member currently logged in. No member to log out");
    }

    public final void a(Callback<com.ad4screen.sdk.service.modules.h.a.a> callback) {
        b((Callback) callback);
    }

    public final void a(String str) {
        if (c()) {
            a();
        }
        b b = b(str);
        a(b);
        Log.debug("MemberManager|Member : " + b.a + " is now connected. Total Connections : " + b.b);
    }

    public final void a(String[] strArr) {
        com.ad4screen.sdk.service.modules.h.a.a d = d();
        ArrayList arrayList = d.a;
        new f(strArr, this.b).run();
        for (int i = 0; i < strArr.length; i++) {
            if (b().equals(strArr[i])) {
                a();
            }
            Log.internal("MemberManager|Removing member " + strArr[i]);
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                if (((b) arrayList.get(i2)).a.equals(strArr[i])) {
                    arrayList.remove(i2);
                    Log.debug("MemberManager|Member " + strArr[i] + " has been removed from this device");
                }
            }
        }
        this.a.a(d);
    }

    public final String b() {
        String str = "";
        b e = this.a.e();
        return (e == null || e.a == null) ? str : e.a;
    }

    public final void b(final Callback<com.ad4screen.sdk.service.modules.h.a.a> callback) {
        final com.ad4screen.sdk.service.modules.h.a.a d = d();
        new b(this.b, new b.a(this) {
            final /* synthetic */ c c;

            public void a() {
                Log.debug("MemberManager|Can't update Members right now. Retrieving from local storage");
                callback.onResult(this.c.d());
            }

            public void a(String[] strArr) {
                for (int i = 0; i < strArr.length; i++) {
                    if (this.c.a(strArr[i], d.a) == -1) {
                        d.a.add(new b(strArr[i]));
                        this.c.a.a(d);
                    }
                }
                callback.onResult(d);
            }
        }).run();
    }

    public final boolean c() {
        return b() != null && b().length() > 0;
    }

    public final com.ad4screen.sdk.service.modules.h.a.a d() {
        return this.a.a();
    }
}
