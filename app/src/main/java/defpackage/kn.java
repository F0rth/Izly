package defpackage;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

public final class kn {
    protected static String b(Context context) {
        Object obj;
        String str = null;
        try {
            Bundle bundle = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData;
            if (bundle != null) {
                String string = bundle.getString("io.fabric.ApiKey");
                try {
                    if ("@string/twitter_consumer_secret".equals(string)) {
                        js.a().a("Fabric", "Ignoring bad default value for Fabric ApiKey set by FirebaseUI-Auth");
                    } else {
                        str = string;
                    }
                    if (str == null) {
                        js.a().a("Fabric", "Falling back to Crashlytics key lookup from Manifest");
                        str = bundle.getString("com.crashlytics.ApiKey");
                    }
                } catch (Exception e) {
                    String str2 = string;
                    obj = e;
                    str = str2;
                    js.a().a("Fabric", "Caught non-fatal exception while retrieving apiKey: " + obj);
                    return str;
                }
            }
        } catch (Exception e2) {
            obj = e2;
            js.a().a("Fabric", "Caught non-fatal exception while retrieving apiKey: " + obj);
            return str;
        }
        return str;
    }

    protected static String c(Context context) {
        int a = kp.a(context, "io.fabric.ApiKey", "string");
        if (a == 0) {
            js.a().a("Fabric", "Falling back to Crashlytics key lookup from Strings");
            a = kp.a(context, "com.crashlytics.ApiKey", "string");
        }
        return a != 0 ? context.getResources().getString(a) : null;
    }

    public final String a(Context context) {
        Object b = kn.b(context);
        if (TextUtils.isEmpty(b)) {
            b = kn.c(context);
        }
        if (TextUtils.isEmpty(b)) {
            b = new kv().a(context);
        }
        if (TextUtils.isEmpty(b)) {
            if (js.b() || kp.h(context)) {
                throw new IllegalArgumentException("Fabric could not be initialized, API key missing from AndroidManifest.xml. Add the following tag to your Application element \n\t<meta-data android:name=\"io.fabric.ApiKey\" android:value=\"YOUR_API_KEY\"/>");
            }
            js.a().e("Fabric", "Fabric could not be initialized, API key missing from AndroidManifest.xml. Add the following tag to your Application element \n\t<meta-data android:name=\"io.fabric.ApiKey\" android:value=\"YOUR_API_KEY\"/>");
        }
        return b;
    }
}
