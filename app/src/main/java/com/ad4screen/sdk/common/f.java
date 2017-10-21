package com.ad4screen.sdk.common;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ServiceInfo;

import com.ad4screen.sdk.A4SApplication;
import com.ad4screen.sdk.A4SIDFVHandler;
import com.ad4screen.sdk.A4SInterstitial;
import com.ad4screen.sdk.A4SPopup;
import com.ad4screen.sdk.A4SService;
import com.ad4screen.sdk.Constants;
import com.ad4screen.sdk.GCMHandler;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.R;
import com.ad4screen.sdk.d.b;

public final class f {

    static class a extends RuntimeException {
        public a(String str) {
            super(str);
        }
    }

    public static void a(Context context) {
        c(context);
        if (b(context)) {
            d(context);
        }
        Log.info("ManifestChecker|Manifest configuration seems to be OK");
    }

    private static void a(Context context, Class<?> cls, String str, String[] strArr, String[] strArr2, boolean z) throws NameNotFoundException {
        PackageManager packageManager = context.getPackageManager();
        try {
            ActivityInfo receiverInfo = packageManager.getReceiverInfo(new ComponentName(context, cls), 0);
            if (receiverInfo.exported != z) {
                Log.error("ManifestChecker|Receiver '" + cls.getName() + "' must " + (z ? "" : "not ") + "be exported");
            }
            if (!(str == null || str.equals(receiverInfo.permission))) {
                Log.error("ManifestChecker|Receiver '" + cls.getName() + "' must require permission '" + str + "' for security reasons");
            }
        } catch (Throwable e) {
            Log.internal("ManifestChecker|", e);
            if (e instanceof a) {
                throw e;
            }
        }
        Intent intent = new Intent();
        for (String addCategory : strArr2) {
            intent.addCategory(addCategory);
        }
        intent.setPackage(context.getPackageName());
        for (String str2 : strArr) {
            intent.setAction(str2);
            if (packageManager.queryBroadcastReceivers(intent, 32).isEmpty()) {
                Log.error("ManifestChecker|Receiver '" + cls.getName() + "' must be declared with an intent filter { action = '" + str2 + "', categories = ['" + h.a("', '", strArr2) + "'] } in your AndroidManifest.xml file");
            }
        }
    }

    private static boolean b(Context context) {
        return b.a(context).C() != null;
    }

    private static void c(Context context) {
        b a = b.a(context);
        PackageManager packageManager = context.getPackageManager();
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
            if (!(applicationInfo.className == null || applicationInfo.className.length() <= 0 || A4SApplication.class.isAssignableFrom(Class.forName(applicationInfo.className)))) {
                Log.warn("ManifestChecker|Your Application class must extend A4SApplication");
            }
        } catch (Throwable e) {
            Log.internal("ManifestChecker|Impossible to check Application class", e);
        } catch (Throwable e2) {
            Log.internal("ManifestChecker|Impossible to check Application class", e2);
        } catch (Throwable e22) {
            Log.internal("ManifestChecker|Impossible to check Application class", e22);
            if (e22 instanceof a) {
                throw e22;
            }
        }
        if (a.l() == null) {
            throw new a("ManifestChecker|PartnerID needs to be properly setup in your AndroidManifest.xml file");
        } else if (a.m() == null) {
            throw new a("ManifestChecker|PrivateKey needs to be properly setup in your AndroidManifest.xml file");
        } else if (!i.a(context, "android.permission.INTERNET")) {
            throw new a("ManifestChecker|Permission 'android.permission.INTERNET' is required in your AndroidManifest.xml file");
        } else if (i.a(context, "android.permission.ACCESS_NETWORK_STATE")) {
            try {
                ServiceInfo serviceInfo = packageManager.getServiceInfo(new ComponentName(context, A4SService.class), 0);
                if (!serviceInfo.processName.equals(serviceInfo.packageName + ":A4SService")) {
                    throw new a("ManifestChecker|android:process=\":A4SService\" is required into " + A4SService.class.getCanonicalName() + " tag in your AndroidManifest.xml file");
                }
            } catch (NameNotFoundException e3) {
                throw new a("ManifestChecker|'" + A4SService.class.getCanonicalName() + "' service is required in your AndroidManifest.xml file");
            } catch (Throwable e222) {
                Log.internal("ManifestChecker|Impossible to check A4SService", e222);
                if (e222 instanceof a) {
                    throw e222;
                }
            }
            try {
                ActivityInfo activityInfo = packageManager.getActivityInfo(new ComponentName(context, A4SPopup.class), 0);
                if (activityInfo.theme != R.style.com_ad4screen_sdk_theme_popup) {
                    Log.warn("ManifestChecker|You should use style/a4s_popup_theme for Popup theme in your AndroidManifest.xml file");
                }
                if (activityInfo.taskAffinity != null) {
                    throw new a("ManifestChecker|A4SPopup must have taskAffinity set to \"\" (empty)");
                }
            } catch (Throwable e2222) {
                Log.internal("ManifestChecker|A4SPopup not found", e2222);
                throw new a("ManifestChecker|LaunchActivity '" + A4SPopup.class.getCanonicalName() + "' is required in your AndroidManifest.xml file");
            } catch (Throwable e22222) {
                Log.internal("ManifestChecker|Impossible to check A4SPopup", e22222);
            }
            try {
                if (packageManager.getActivityInfo(new ComponentName(context, A4SInterstitial.class), 0).theme != R.style.com_ad4screen_sdk_theme_interstitial) {
                    Log.warn("ManifestChecker|You should use style/a4s_richpush_theme for LandingPage theme in your AndroidManifest.xml file");
                }
            } catch (Throwable e222222) {
                Log.internal("ManifestChecker|A4SInterstitial not found", e222222);
                throw new a("ManifestChecker|LaunchActivity '" + A4SInterstitial.class.getCanonicalName() + "' is required in your AndroidManifest.xml file");
            } catch (Throwable e2222222) {
                Log.internal("ManifestChecker|Impossible to check A4SInterstitial", e2222222);
            }
            try {
                Context context2 = context;
                a(context2, A4SIDFVHandler.class, null, new String[]{Constants.ACTION_QUERY}, new String[]{Constants.CATEGORY_IDFV}, true);
            } catch (Throwable e22222222) {
                Log.error("ManifestChecker|Receiver 'com.ad4screen.sdk.A4SIDFVHandler' must be declared in your AndroidManifest.xml file.");
                Log.internal(e22222222);
            }
        } else {
            throw new a("ManifestChecker|Permission 'android.permission.ACCESS_NETWORK_STATE' is required in your AndroidManifest.xml file");
        }
    }

    private static void d(Context context) {
        int i;
        try {
            context.getPackageManager().getPackageInfo("com.google.android.gsf", 0);
        } catch (Throwable e) {
            Log.info("ManifestChecker|Device does not have package 'com.google.android.gsf', notification integration will not be validated");
            Log.internal(e);
            return;
        } catch (Throwable e2) {
            Log.internal("ManifestChecker|Impossible to check Push Configuration", e2);
            if (e2 instanceof a) {
                throw e2;
            }
        }
        String str = context.getPackageName() + ".permission.C2D_MESSAGE";
        for (i = 0; i < 3; i++) {
            String str2 = new String[]{str, "com.google.android.c2dm.permission.RECEIVE", "android.permission.INTERNET"}[i];
            if (!i.a(context, str2)) {
                Log.error("ManifestChecker|Permission '" + str2 + "' is required in your AndroidManifest.xml file");
            }
        }
        for (i = 0; i <= 0; i++) {
            str = new String[]{"android.permission.VIBRATE"}[0];
            if (!i.a(context, str)) {
                Log.warn("ManifestChecker|Permission '" + str + "' should be used in your AndroidManifest.xml file");
            }
        }
        str = "com.google.android.c2dm.permission.SEND";
        if (b.a(context).H()) {
            str = null;
        }
        try {
            Context context2 = context;
            a(context2, GCMHandler.class, str, new String[]{"com.google.android.c2dm.intent.RECEIVE", "com.google.android.c2dm.intent.REGISTRATION"}, new String[]{r5}, true);
        } catch (Throwable e22) {
            Log.warn("ManifestChecker|Receiver 'com.ad4screen.sdk.GCMHandler' must be declared in your AndroidManifest.xml file. Ignore this message if you are using your own custom GCMHandler.");
            Log.internal(e22);
        }
    }
}
