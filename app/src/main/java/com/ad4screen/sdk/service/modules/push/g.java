package com.ad4screen.sdk.service.modules.push;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.R;
import com.ad4screen.sdk.c.a.b;
import com.ad4screen.sdk.c.a.d;
import com.ad4screen.sdk.c.a.e;
import com.ad4screen.sdk.common.b.m.j;
import com.ad4screen.sdk.d.i;
import com.ad4screen.sdk.service.modules.inapp.k;
import com.ad4screen.sdk.service.modules.push.a.a;

import java.net.URISyntaxException;

public final class g {
    public static Intent a(Context context) throws ClassNotFoundException, URISyntaxException {
        k a = k.a(i.a(context));
        if (a.d() != null) {
            Log.debug("Push|Resuming activity : " + a.d());
            Intent intent = new Intent(context, Class.forName(a.d()));
            intent.addFlags(805306368);
            return intent;
        }
        intent = Intent.parseUri(com.ad4screen.sdk.common.i.c(context), 1);
        intent.addFlags(402653184);
        return intent;
    }

    public static e a(Context context, a aVar) {
        if (TextUtils.isEmpty(aVar.d)) {
            return null;
        }
        e eVar = new e();
        eVar.h = aVar.b;
        eVar.b = aVar.c;
        eVar.a = new b();
        eVar.a.c = aVar.d;
        eVar.a.d = "com_ad4screen_sdk_template_interstitial";
        if (aVar.e == null) {
            return eVar;
        }
        if ((context.getResources().getIdentifier(aVar.e, "layout", context.getPackageName()) > 0 ? 1 : null) == null) {
            Log.warn("PushUtil|Wrong LandingPage template provided : " + aVar.e + " using default");
            return eVar;
        }
        eVar.a.d = aVar.e;
        return eVar;
    }

    public static com.ad4screen.sdk.c.a.g a(Context context, a aVar, d dVar) {
        com.ad4screen.sdk.c.a.g gVar = new com.ad4screen.sdk.c.a.g();
        gVar.h = aVar.b;
        gVar.c(aVar.n);
        if (i.a(context).g() && !TextUtils.isEmpty(aVar.m)) {
            gVar.a(aVar.m);
        }
        com.ad4screen.sdk.c.a.g.a[] aVarArr = new com.ad4screen.sdk.c.a.g.a[2];
        aVarArr[0] = new com.ad4screen.sdk.c.a.g.a();
        aVarArr[0].a("0");
        if (aVar.w == null) {
            aVarArr[0].b(context.getString(R.string.a4s_popup_dismiss));
        } else {
            aVarArr[0].b(aVar.w);
        }
        aVarArr[1] = new com.ad4screen.sdk.c.a.g.a();
        aVarArr[1].a("1");
        if (aVar.v == null) {
            aVarArr[1].b(context.getString(R.string.a4s_popup_open));
        } else {
            aVarArr[1].b(aVar.v);
        }
        aVarArr[1].a(dVar);
        gVar.a(aVarArr);
        return gVar;
    }

    public static void a(Context context, PendingIntent pendingIntent, a aVar, d.b bVar) {
        j.a(context, pendingIntent, aVar, bVar);
    }

    public static boolean b(Context context) {
        return new b(context).a();
    }
}
