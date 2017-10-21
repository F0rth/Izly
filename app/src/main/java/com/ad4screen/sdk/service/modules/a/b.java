package com.ad4screen.sdk.service.modules.a;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.ad4screen.sdk.A4SService;
import com.ad4screen.sdk.A4SService.a;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.b.m;
import com.ad4screen.sdk.service.modules.a.a.c;
import com.ad4screen.sdk.service.modules.d.d;
import com.ad4screen.sdk.service.modules.d.h;
import com.ad4screen.sdk.service.modules.inapp.a.f;
import com.ad4screen.sdk.service.modules.inapp.k;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class b {
    private static b a;
    private final a b;
    private final a c = new a(this.b.a());
    private final com.ad4screen.sdk.service.modules.a.a.a d = this.c.a();
    private final AlarmManager e = ((AlarmManager) this.b.a().getSystemService("alarm"));

    private b(a aVar) {
        this.b = aVar;
    }

    public static b a(a aVar) {
        synchronized (b.class) {
            try {
                if (a == null) {
                    a = new b(aVar);
                }
            } catch (Throwable th) {
                while (true) {
                    Class cls = b.class;
                }
            }
        }
        return a;
    }

    private void a() {
        this.c.a(this.d);
    }

    private void b(String str, k kVar) {
        this.e.cancel(c(str));
        Log.debug("Alarm|Alarm #" + str + " cancelled");
        c a = a(str);
        if (a == null) {
            Log.internal("Alarm|Can't find alarm #" + str + " in configuration. Already removed or triggered?");
            return;
        }
        if (kVar != null) {
            kVar.k().a(str);
        }
        if (a.o) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("controlGroup", true);
            h.a(this.b, str, d.a.CANCEL, bundle);
        } else {
            h.a(this.b, str, d.a.CANCEL, null);
        }
        this.d.a(a);
    }

    private PendingIntent c(String str) {
        Intent intent = new Intent(this.b.a(), A4SService.class);
        intent.setData(Uri.parse("a4slocalnotif:" + str));
        return PendingIntent.getService(this.b.a(), 0, intent, 0);
    }

    public final c a(String str) {
        return this.d.b(str);
    }

    public final Long a(c cVar) {
        return this.d.b(cVar);
    }

    public final void a(com.ad4screen.sdk.service.modules.a.a.b bVar, k kVar) {
        for (String str : bVar.a) {
            if ("*".equals(str)) {
                a(kVar);
                return;
            }
            if (!bVar.o) {
                b(str, kVar);
            }
        }
        a();
    }

    public final void a(c cVar, Date date) {
        if (a(cVar.h) == null) {
            this.d.a(cVar, date);
            a();
        }
        PendingIntent c = c(cVar.h);
        Log.debug("Alarm|Alarm #" + cVar.h + " set to " + DateFormat.getDateTimeInstance().format(cVar.e()));
        cVar.a(cVar.e());
        Bundle bundle = new Bundle();
        if (cVar.o) {
            bundle.putBoolean("controlGroup", true);
        }
        h.a(this.b, cVar.h, d.a.DISP, bundle);
        m.a.a(this.e, 0, cVar.e().getTime(), c);
    }

    public final void a(f fVar) {
        List<c> a = this.d.a();
        if (a != null && !a.isEmpty()) {
            for (c cVar : a) {
                com.ad4screen.sdk.c.a.d b = fVar.b(cVar.h);
                if (b != null && (b instanceof c)) {
                    cVar.a(((c) b).c());
                }
            }
            a();
        }
    }

    public final void a(k kVar) {
        Log.debug("Alarm|Cancelling all alarms");
        List arrayList = new ArrayList(this.d.a());
        for (int i = 0; i < arrayList.size(); i++) {
            b(((c) arrayList.get(i)).h, kVar);
        }
        a();
    }

    public final void a(String str, k kVar) {
        b(str, kVar);
        a();
    }

    public final void b(c cVar, Date date) {
        if (a(cVar.h) != null) {
            cVar.b(date);
            PendingIntent c = c(cVar.h);
            Log.debug("Alarm|Alarm #" + cVar.h + " shift to " + DateFormat.getDateTimeInstance().format(date));
            cVar.a(date);
            m.a.a(this.e, 0, cVar.e().getTime(), c);
        }
    }

    public final void b(String str) {
        this.d.a(this.d.b(str));
        a();
        Log.debug("Alarm|Alarm #" + str + " deleted");
    }
}
