package com.ad4screen.sdk.service.modules.d;

import android.content.Context;
import android.os.Bundle;

import com.ad4screen.sdk.A4SService;
import com.ad4screen.sdk.common.e;
import com.ad4screen.sdk.d.b;
import com.ad4screen.sdk.j;
import com.ad4screen.sdk.service.modules.k.c.a;
import com.ad4screen.sdk.service.modules.k.c.c;
import com.ad4screen.sdk.service.modules.k.c.d;

import java.util.List;

public final class h {
    public static void a(Context context, j jVar) {
        new d(context, jVar).run();
    }

    public static void a(Context context, String str, e... eVarArr) {
        new g(context, str, eVarArr).run();
    }

    public static void a(Context context, List<com.ad4screen.sdk.service.modules.k.c.e> list) {
        new a(context, b.a(context), list).run();
    }

    public static void a(Context context, List<com.ad4screen.sdk.service.modules.k.c.e> list, j jVar) {
        new c(context, list, jVar).run();
    }

    public static void a(A4SService.a aVar, Bundle bundle, boolean z) {
        if (z) {
            new com.ad4screen.sdk.service.modules.j.c(aVar.a(), bundle).run();
        } else {
            new com.ad4screen.sdk.service.modules.j.b(aVar.a(), bundle).run();
        }
    }

    public static void a(A4SService.a aVar, String str) {
        new f(aVar.a(), str).run();
    }

    public static void a(A4SService.a aVar, String str, d.a aVar2, Bundle bundle) {
        if (str.contains("#")) {
            new d(aVar.a(), str.split("#")[0], str.split("#")[1], aVar2, bundle).run();
        } else {
            new d(aVar.a(), str, aVar2, bundle).run();
        }
    }

    public static void a(A4SService.a aVar, String str, e.a aVar2) {
        new e(aVar.a(), str, aVar2).run();
    }

    public static void a(A4SService.a aVar, String str, String str2, d.a aVar2, Bundle bundle) {
        new d(aVar.a(), str, str2, aVar2, bundle).run();
    }

    public static void a(A4SService.a aVar, String str, String str2, e.a aVar2) {
        new e(aVar.a(), str, str2, aVar2).run();
    }

    public static void b(Context context, List<com.ad4screen.sdk.service.modules.k.c.e> list) {
        new com.ad4screen.sdk.service.modules.k.c.b(context, list).run();
    }
}
