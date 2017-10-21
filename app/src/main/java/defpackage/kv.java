package defpackage;

import android.content.Context;
import android.text.TextUtils;

public final class kv {
    public static boolean b(Context context) {
        if (kp.a(context, "com.crashlytics.useFirebaseAppId", false)) {
            return true;
        }
        boolean z;
        boolean z2 = kp.a(context, "google_app_id", "string") != 0;
        kn knVar = new kn();
        if (TextUtils.isEmpty(kn.b(context))) {
            knVar = new kn();
            if (TextUtils.isEmpty(kn.c(context))) {
                z = false;
                return z2 && !z;
            }
        }
        z = true;
        if (!z2) {
        }
    }

    protected final String a(Context context) {
        int a = kp.a(context, "google_app_id", "string");
        if (a == 0) {
            return null;
        }
        js.a().a("Fabric", "Generating Crashlytics ApiKey from google_app_id in Strings");
        return kp.b(context.getResources().getString(a)).substring(0, 40);
    }
}
