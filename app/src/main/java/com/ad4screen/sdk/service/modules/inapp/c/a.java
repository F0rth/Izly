package com.ad4screen.sdk.service.modules.inapp.c;

import android.content.Context;

import com.ad4screen.sdk.service.modules.a.a.c;
import com.ad4screen.sdk.service.modules.inapp.a.a.b;
import com.ad4screen.sdk.service.modules.inapp.a.a.d;
import com.ad4screen.sdk.service.modules.inapp.a.h;
import com.ad4screen.sdk.service.modules.inapp.a.j;
import com.ad4screen.sdk.service.modules.inapp.k;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public abstract class a implements m {
    protected com.ad4screen.sdk.common.a a;

    public a(com.ad4screen.sdk.common.a aVar) {
        this.a = aVar;
    }

    protected List<com.ad4screen.sdk.service.modules.inapp.a.a.a> a(j jVar) {
        return a(jVar.b(), jVar.c(), jVar.o().b());
    }

    protected List<com.ad4screen.sdk.service.modules.inapp.a.a.a> a(Date date, Date date2, List<b> list) {
        List<com.ad4screen.sdk.service.modules.inapp.a.a.a> arrayList = new ArrayList();
        com.ad4screen.sdk.service.modules.inapp.j jVar = new com.ad4screen.sdk.service.modules.inapp.j(this.a);
        if (list != null) {
            for (b bVar : list) {
                d c = bVar.c();
                if (c != null) {
                    Date a = bVar.a();
                    Date b = bVar.b();
                    if (a == null) {
                        a = date;
                    }
                    if (b == null) {
                        b = date2;
                    }
                    arrayList.addAll(jVar.a(c, a, b, bVar.d()));
                } else if (bVar.d()) {
                    Calendar d = this.a.d();
                    long offset = (long) d.getTimeZone().getOffset(d.getTimeInMillis());
                    arrayList.add(new com.ad4screen.sdk.service.modules.inapp.a.a.a(new Date(bVar.a().getTime() - offset), new Date(bVar.b().getTime() - offset)));
                } else {
                    arrayList.add(new com.ad4screen.sdk.service.modules.inapp.a.a.a(bVar.a(), bVar.b()));
                }
            }
        }
        return arrayList;
    }

    public void a(Context context, k kVar) {
    }

    public void a(j jVar, h hVar) {
    }

    protected boolean a(com.ad4screen.sdk.c.a.d dVar) {
        return dVar instanceof c;
    }

    protected List<com.ad4screen.sdk.service.modules.inapp.a.a.a> b(j jVar) {
        return a(jVar.b(), jVar.c(), jVar.n().b());
    }

    protected boolean c(j jVar) {
        return (jVar.o() == null || jVar.o().b() == null || jVar.o().b().isEmpty()) ? false : true;
    }

    protected boolean d(j jVar) {
        return (jVar.n() == null || jVar.n().b() == null || jVar.n().b().isEmpty()) ? false : true;
    }
}
