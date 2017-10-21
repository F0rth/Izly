package com.ad4screen.sdk.d;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.os.Build;
import android.os.Build.VERSION;
import android.support.v4.os.EnvironmentCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.ad4screen.sdk.A4SIdsProvider;
import com.ad4screen.sdk.A4SService;
import com.ad4screen.sdk.BuildConfig;
import com.ad4screen.sdk.Constants;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.common.i;
import com.ad4screen.sdk.plugins.AdvertiserPlugin;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public final class b {
    private static b N;
    private boolean A;
    private boolean B;
    private boolean C;
    private boolean D;
    private String E;
    private boolean F;
    private String G;
    private boolean H;
    private String I;
    private String J;
    private b K;
    private String L;
    private Date M;
    private final c a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private String g;
    private String h;
    private String i;
    private String j;
    private String k;
    private String l;
    private String m;
    private String n;
    private String o;
    private String p;
    private String q;
    private String r;
    private int s;
    private int t;
    private String u;
    private String v;
    private a w;
    private String x;
    private boolean y;
    private boolean z;

    public enum a {
        unknown,
        ldpi,
        mdpi,
        hdpi,
        xhdpi,
        xxhdpi,
        xxxhdpi
    }

    public enum b {
        NORMAL,
        RESTRICTED
    }

    private b(Context context) {
        boolean z = false;
        this.a = new c(context);
        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(applicationInfo.packageName, 0);
            this.q = i.a(packageInfo, applicationInfo);
            this.o = Integer.toString(packageInfo.versionCode);
        } catch (NameNotFoundException e) {
            Log.warn("DeviceInfo|Could not retrieve current package information");
        } catch (RuntimeException e2) {
        }
        this.b = Constants.SDK_VERSION;
        this.h = "Android " + Build.MODEL;
        this.i = VERSION.RELEASE;
        this.j = applicationInfo.packageName;
        this.k = Resources.getSystem().getConfiguration().locale.toString();
        this.l = Resources.getSystem().getConfiguration().locale.getDisplayCountry();
        this.m = Resources.getSystem().getConfiguration().locale.getCountry();
        this.n = Resources.getSystem().getConfiguration().locale.getLanguage();
        this.p = (String) packageManager.getApplicationLabel(applicationInfo);
        this.M = f();
        this.v = d(context);
        this.w = e(context);
        this.r = TimeZone.getDefault().getID();
        this.e = f(context);
        this.f = g(context);
        this.d = Q();
        this.c = i.b(context);
        this.x = h(context);
        this.E = BuildConfig.ENV;
        if (TextUtils.isEmpty(this.E)) {
            this.E = BuildConfig.ENV;
        }
        String a = i.a(context, "com.ad4screen.logging", A4SService.class);
        String a2 = i.a(context, "com.ad4screen.no_geoloc", A4SService.class);
        String a3 = i.a(context, "com.ad4screen.debuggable", A4SService.class);
        String a4 = i.a(context, "com.ad4screen.usbstorage", A4SService.class);
        String a5 = i.a(context, "com.ad4screen.unsecurepush", A4SService.class);
        String a6 = i.a(context, "com.ad4screen.facebook_appid", A4SService.class);
        String a7 = i.a(context, "com.ad4screen.facebook.deferred_applink_redirection", A4SService.class);
        String a8 = i.a(context, "com.ad4screen.advertiser_id", A4SService.class);
        String a9 = i.a(context, "com.ad4screen.anonym_id", A4SService.class);
        String a10 = i.a(context, "com.ad4screen.tracking_mode", A4SService.class);
        this.L = i.a(context, "com.ad4screen.webview.script_url", A4SService.class);
        this.z = f(a);
        this.A = g(a);
        boolean z2 = a2 != null && a2.equalsIgnoreCase("true");
        this.B = z2;
        z2 = a3 != null && a3.equalsIgnoreCase("true");
        this.y = z2;
        z2 = a4 != null && a4.equalsIgnoreCase("true");
        this.C = z2;
        z2 = a5 != null && a5.equalsIgnoreCase("true");
        this.D = z2;
        this.G = h(a6);
        z2 = a7 != null && a7.equalsIgnoreCase("true");
        this.H = z2;
        if ((a8 == null || a8.equalsIgnoreCase("true")) && (a9 == null || a9.equalsIgnoreCase("false"))) {
            z = true;
        }
        this.F = z;
        this.K = b.NORMAL;
        if (a10 != null && a10.equalsIgnoreCase("Restricted")) {
            this.K = b.RESTRICTED;
        }
        this.g = P();
        this.I = R();
        this.J = S();
        this.s = this.a.f();
        this.t = this.a.g();
        this.u = this.a.h();
    }

    private String P() {
        String a = this.a.a();
        if (a == null || a.length() == 0) {
            a = k();
        }
        return (a == null || a.length() == 0) ? null : a;
    }

    private String Q() {
        return this.a.e();
    }

    private String R() {
        return this.a.j();
    }

    private String S() {
        return this.a.k();
    }

    public static b a(Context context) {
        return a(context, false);
    }

    public static b a(Context context, boolean z) {
        b bVar;
        synchronized (b.class) {
            try {
                if (N == null || z) {
                    N = new b(context.getApplicationContext());
                }
                bVar = N;
            } catch (Throwable th) {
                Class cls = b.class;
            }
        }
        return bVar;
    }

    private String d(Context context) {
        switch (context.getResources().getConfiguration().screenLayout & 15) {
            case 1:
                return "small";
            case 2:
                return "normal";
            case 3:
                return "large";
            case 4:
                return "xlarge";
            default:
                return EnvironmentCompat.MEDIA_UNKNOWN;
        }
    }

    private a e(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        a aVar = displayMetrics.densityDpi <= 120 ? a.ldpi : displayMetrics.densityDpi <= 160 ? a.mdpi : displayMetrics.densityDpi <= 240 ? a.hdpi : a.xhdpi;
        if (VERSION.SDK_INT < 16) {
            return aVar;
        }
        if (displayMetrics.densityDpi <= 480) {
            aVar = a.xxhdpi;
        }
        return (VERSION.SDK_INT < 18 || displayMetrics.densityDpi > 640) ? aVar : a.xxxhdpi;
    }

    private String f(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.ad4screen.sdk.common.DeviceInfo", 0);
        String a = i.a(context, "com.ad4screen.partnerid", A4SService.class);
        if (a == null) {
            a = sharedPreferences.getString("com.ad4screen.partnerid", null);
        }
        if (a != null) {
            return a;
        }
        String partnerId;
        String a2 = i.a(context, "com.ad4screen.idsprovider", A4SService.class);
        try {
            partnerId = ((A4SIdsProvider) Class.forName(a2).newInstance()).getPartnerId(context);
        } catch (Throwable e) {
            Log.error("DeviceInfo|Exception while calling your class : " + a2, e);
            partnerId = a;
        }
        if (partnerId == null) {
            return partnerId;
        }
        sharedPreferences.edit().putString("com.ad4screen.partnerid", partnerId).commit();
        return partnerId;
    }

    private boolean f(String str) {
        boolean l = this.a.l();
        if (l || str == null) {
            return l;
        }
        for (String equalsIgnoreCase : str.split(",")) {
            if ("true".equalsIgnoreCase(equalsIgnoreCase)) {
                return true;
            }
        }
        return l;
    }

    private String g(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.ad4screen.sdk.common.DeviceInfo", 0);
        String a = i.a(context, "com.ad4screen.privatekey", A4SService.class);
        if (a == null) {
            a = sharedPreferences.getString("com.ad4screen.privatekey", null);
        }
        if (a != null) {
            return a;
        }
        String privateKey;
        String a2 = i.a(context, "com.ad4screen.idsprovider", A4SService.class);
        try {
            privateKey = ((A4SIdsProvider) Class.forName(a2).newInstance()).getPrivateKey(context);
        } catch (Throwable e) {
            Log.error("DeviceInfo|Exception while calling your class : " + a2, e);
            privateKey = a;
        }
        if (privateKey == null) {
            return privateKey;
        }
        sharedPreferences.edit().putString("com.ad4screen.privatekey", privateKey).commit();
        return privateKey;
    }

    private boolean g(String str) {
        if (str == null) {
            return false;
        }
        for (String equalsIgnoreCase : str.split(",")) {
            if ("no-toast".equalsIgnoreCase(equalsIgnoreCase)) {
                return true;
            }
        }
        return false;
    }

    private static String h(Context context) {
        String a = i.a(context, "com.ad4screen.senderid", A4SService.class);
        return (a == null || !a.toLowerCase(Locale.US).startsWith("gcm:")) ? a : a.substring(4);
    }

    private String h(String str) {
        return this.a.i() != null ? this.a.i() : (str == null || !str.toLowerCase(Locale.US).startsWith("fb:")) ? str : str.substring(3);
    }

    public final String A() {
        return this.v;
    }

    public final a B() {
        return this.w;
    }

    public final String C() {
        return this.x;
    }

    public final boolean D() {
        return this.y;
    }

    public final boolean E() {
        return this.z;
    }

    public final boolean F() {
        return this.B;
    }

    public final boolean G() {
        return this.C;
    }

    public final boolean H() {
        return this.D;
    }

    public final String I() {
        return this.E;
    }

    public final boolean J() {
        return this.F;
    }

    public final String K() {
        return this.G;
    }

    public final boolean L() {
        return this.H;
    }

    public final b M() {
        return this.K;
    }

    public final String N() {
        return this.L;
    }

    public final Date O() {
        return this.M;
    }

    public final void a() {
        a(x() + 1);
        this.a.a(x());
    }

    public final void a(int i) {
        this.s = i;
    }

    public final void a(String str) {
        this.u = str;
        this.a.d(this.u);
    }

    public final void a(Date date) {
        this.a.h(h.a(date, com.ad4screen.sdk.common.h.a.ISO8601));
        this.M = date;
    }

    public final void a(boolean z) {
        this.z = z;
        this.a.a(this.z);
    }

    public final String b(Context context) {
        AdvertiserPlugin c = com.ad4screen.sdk.common.d.b.c();
        if (c == null) {
            return null;
        }
        Log.debug("AdvertiserPlugin|Looking for an advertiser id..");
        return c.getId(context);
    }

    public final void b() {
        b(y() + 1);
        this.a.b(y());
    }

    public final void b(int i) {
        this.t = i;
    }

    public final void b(String str) {
        this.g = str;
        this.a.b(str);
    }

    public final void b(Date date) {
        this.a.i(h.a(date, com.ad4screen.sdk.common.h.a.ISO8601));
    }

    public final String c() {
        return this.g;
    }

    public final void c(String str) {
        this.d = str;
        this.a.c(str);
        this.g = P();
    }

    public final boolean c(Context context) {
        AdvertiserPlugin c = com.ad4screen.sdk.common.d.b.c();
        if (c == null) {
            return false;
        }
        Log.debug("AdvertiserPlugin|Looking for an advertiser id..");
        return c.isLimitAdTrackingEnabled(context);
    }

    public final String d() {
        return this.I;
    }

    public final void d(String str) {
        this.G = str;
        this.a.e(str);
    }

    public final String e() {
        return this.J;
    }

    public final void e(String str) {
        this.I = str;
        this.J = h.a(Calendar.getInstance(Locale.US).getTime(), com.ad4screen.sdk.common.h.a.ISO8601);
        this.a.f(this.I);
        this.a.g(this.J);
    }

    public final Date f() {
        String m = this.a.m();
        return m == null ? null : h.a(m, com.ad4screen.sdk.common.h.a.ISO8601);
    }

    public final Date g() {
        String n = this.a.n();
        return n == null ? null : h.a(n, com.ad4screen.sdk.common.h.a.ISO8601);
    }

    public final boolean h() {
        return this.A;
    }

    public final String i() {
        return this.b;
    }

    public final String j() {
        return this.c;
    }

    public final String k() {
        return this.d;
    }

    public final String l() {
        return this.e;
    }

    public final String m() {
        return this.f;
    }

    public final String n() {
        return this.h;
    }

    public final String o() {
        return this.i;
    }

    public final String p() {
        return this.j;
    }

    public final String q() {
        return this.l;
    }

    public final String r() {
        return this.m;
    }

    public final String s() {
        return this.n;
    }

    public final String t() {
        return this.o;
    }

    public final String u() {
        return this.p;
    }

    public final String v() {
        return this.q;
    }

    public final String w() {
        return this.r;
    }

    public final int x() {
        return this.s;
    }

    public final int y() {
        return this.t;
    }

    public final String z() {
        return this.u;
    }
}
