package com.ad4screen.sdk.service.modules.push;

import android.content.Context;
import android.os.Build.VERSION;

import com.ad4screen.sdk.A4SService.a;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.plugins.ADMPlugin;
import com.ad4screen.sdk.service.modules.push.b.b;
import com.ad4screen.sdk.service.modules.push.b.c;

public class e {
    public static a a(a aVar) {
        switch (a(aVar.a())) {
            case ADM:
                Log.info("PushFactory|Use ADM");
                return new com.ad4screen.sdk.service.modules.push.b.a(aVar);
            case GCM:
                Log.info("PushFactory|Use GCM");
                return new c(aVar);
            default:
                Log.debug("Push|Cannot use GCM with android version < 8");
                return new b();
        }
    }

    public static f.a a(Context context) {
        ADMPlugin b = com.ad4screen.sdk.common.d.b.b();
        return (b == null || !b.isSupported(context)) ? VERSION.SDK_INT >= 8 ? f.a.GCM : f.a.UNKNOWN : f.a.ADM;
    }
}
