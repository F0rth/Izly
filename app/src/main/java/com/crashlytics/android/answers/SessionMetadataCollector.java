package com.crashlytics.android.answers;

import android.content.Context;
import android.os.Build.VERSION;
import java.util.Map;
import java.util.UUID;

class SessionMetadataCollector {
    private final Context context;
    private final kw idManager;
    private final String versionCode;
    private final String versionName;

    public SessionMetadataCollector(Context context, kw kwVar, String str, String str2) {
        this.context = context;
        this.idManager = kwVar;
        this.versionCode = str;
        this.versionName = str2;
    }

    public SessionEventMetadata getMetadata() {
        Map c = this.idManager.c();
        String str = this.idManager.b;
        String a = this.idManager.a();
        String str2 = (String) c.get(a.d);
        String str3 = (String) c.get(a.g);
        Boolean e = this.idManager.e();
        String str4 = (String) c.get(a.c);
        String k = kp.k(this.context);
        kw kwVar = this.idManager;
        return new SessionEventMetadata(str, UUID.randomUUID().toString(), a, str2, str3, e, str4, k, kw.a(VERSION.RELEASE) + "/" + kw.a(VERSION.INCREMENTAL), this.idManager.b(), this.versionCode, this.versionName);
    }
}
