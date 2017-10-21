package defpackage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

public final class kw {
    private static final Pattern g = Pattern.compile("[^\\p{Alnum}]");
    private static final String h = Pattern.quote("/");
    public final boolean a;
    public final String b;
    kj c;
    ki d;
    boolean e;
    kv f;
    private final ReentrantLock i = new ReentrantLock();
    private final kx j;
    private final boolean k;
    private final Context l;
    private final String m;
    private final Collection<jy> n;

    public kw(Context context, String str, String str2, Collection<jy> collection) {
        if (context == null) {
            throw new IllegalArgumentException("appContext must not be null");
        } else if (str == null) {
            throw new IllegalArgumentException("appIdentifier must not be null");
        } else if (collection == null) {
            throw new IllegalArgumentException("kits must not be null");
        } else {
            this.l = context;
            this.b = str;
            this.m = str2;
            this.n = collection;
            this.j = new kx();
            this.c = new kj(context);
            this.f = new kv();
            this.k = kp.a(context, "com.crashlytics.CollectDeviceIdentifiers", true);
            if (!this.k) {
                js.a().a("Fabric", "Device ID collection disabled for " + context.getPackageName());
            }
            this.a = kp.a(context, "com.crashlytics.CollectUserIdentifiers", true);
            if (!this.a) {
                js.a().a("Fabric", "User information collection disabled for " + context.getPackageName());
            }
        }
    }

    @SuppressLint({"CommitPrefEdits"})
    private String a(SharedPreferences sharedPreferences) {
        this.i.lock();
        try {
            String string = sharedPreferences.getString("crashlytics.installation.id", null);
            if (string == null) {
                string = kw.b(UUID.randomUUID().toString());
                sharedPreferences.edit().putString("crashlytics.installation.id", string).commit();
            }
            this.i.unlock();
            return string;
        } catch (Throwable th) {
            this.i.unlock();
        }
    }

    public static String a(String str) {
        return str.replaceAll(h, "");
    }

    @SuppressLint({"CommitPrefEdits"})
    private void a(SharedPreferences sharedPreferences, String str) {
        this.i.lock();
        try {
            if (!TextUtils.isEmpty(str)) {
                Object string = sharedPreferences.getString("crashlytics.advertising.id", null);
                if (TextUtils.isEmpty(string)) {
                    sharedPreferences.edit().putString("crashlytics.advertising.id", str).commit();
                } else if (!string.equals(str)) {
                    sharedPreferences.edit().remove("crashlytics.installation.id").putString("crashlytics.advertising.id", str).commit();
                }
                this.i.unlock();
            }
        } finally {
            this.i.unlock();
        }
    }

    private static void a(Map<kw$a, String> map, kw$a kw_a, String str) {
        if (str != null) {
            map.put(kw_a, str);
        }
    }

    private static String b(String str) {
        return str == null ? null : g.matcher(str).replaceAll("").toLowerCase(Locale.US);
    }

    private boolean h() {
        return this.k && !kv.b(this.l);
    }

    private ki i() {
        ki kiVar;
        synchronized (this) {
            if (!this.e) {
                this.d = this.c.a();
                this.e = true;
            }
            kiVar = this.d;
        }
        return kiVar;
    }

    private Boolean j() {
        ki i = i();
        return i != null ? Boolean.valueOf(i.b) : null;
    }

    public final String a() {
        String str = this.m;
        if (str != null) {
            return str;
        }
        SharedPreferences a = kp.a(this.l);
        ki i = i();
        if (i != null) {
            a(a, i.a);
        }
        str = a.getString("crashlytics.installation.id", null);
        return str == null ? a(a) : str;
    }

    public final String b() {
        return String.format(Locale.US, "%s/%s", new Object[]{kw.a(Build.MANUFACTURER), kw.a(Build.MODEL)});
    }

    public final Map<kw$a, String> c() {
        Map hashMap = new HashMap();
        for (jy jyVar : this.n) {
            if (jyVar instanceof kt) {
                for (Entry entry : ((kt) jyVar).getDeviceIdentifiers().entrySet()) {
                    kw.a(hashMap, (kw$a) entry.getKey(), (String) entry.getValue());
                }
            }
        }
        Object f = f();
        if (TextUtils.isEmpty(f)) {
            kw.a(hashMap, kw$a.ANDROID_ID, g());
        } else {
            kw.a(hashMap, kw$a.ANDROID_ADVERTISING_ID, f);
        }
        return Collections.unmodifiableMap(hashMap);
    }

    public final String d() {
        return this.j.a(this.l);
    }

    public final Boolean e() {
        return h() ? j() : null;
    }

    public final String f() {
        if (!h()) {
            return null;
        }
        ki i = i();
        return (i == null || i.b) ? null : i.a;
    }

    public final String g() {
        boolean equals = Boolean.TRUE.equals(j());
        if (!h() || equals) {
            return null;
        }
        String string = Secure.getString(this.l.getContentResolver(), "android_id");
        return !"9774d56d682e549c".equals(string) ? kw.b(string) : null;
    }
}
