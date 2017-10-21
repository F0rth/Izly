package defpackage;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Debug;
import android.os.StatFs;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;
import org.spongycastle.asn1.cmp.PKIFailureInfo;
import org.spongycastle.crypto.tls.CipherSuite;

public final class kp {
    public static final Comparator<File> a = new kp$1();
    private static Boolean b = null;
    private static final char[] c = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static long d = -1;

    public static int a() {
        return kp$a.a().ordinal();
    }

    public static int a(Context context, String str, String str2) {
        Resources resources = context.getResources();
        int i = context.getApplicationContext().getApplicationInfo().icon;
        return resources.getIdentifier(str, str2, i > 0 ? context.getResources().getResourcePackageName(i) : context.getPackageName());
    }

    public static int a(Context context, boolean z) {
        Float c = kp.c(context);
        return (!z || c == null) ? 1 : ((double) c.floatValue()) >= 99.0d ? 3 : ((double) c.floatValue()) < 99.0d ? 2 : 0;
    }

    private static long a(String str, String str2, int i) {
        return Long.parseLong(str.split(str2)[0].trim()) * ((long) i);
    }

    public static RunningAppProcessInfo a(String str, Context context) {
        List<RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
        if (runningAppProcesses != null) {
            for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                if (runningAppProcessInfo.processName.equals(str)) {
                    return runningAppProcessInfo;
                }
            }
        }
        return null;
    }

    public static SharedPreferences a(Context context) {
        return context.getSharedPreferences("com.crashlytics.prefs", 0);
    }

    public static String a(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("value must be zero or greater");
        }
        return String.format(Locale.US, "%1$10s", new Object[]{Integer.valueOf(i)}).replace(' ', '0');
    }

    private static String a(File file, String str) {
        Closeable bufferedReader;
        Throwable e;
        String str2;
        Closeable closeable = null;
        if (file.exists()) {
            try {
                String[] split;
                bufferedReader = new BufferedReader(new FileReader(file), PKIFailureInfo.badRecipientNonce);
                while (true) {
                    try {
                        CharSequence readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        }
                        split = Pattern.compile("\\s*:\\s*").split(readLine, 2);
                        if (split.length > 1 && split[0].equals(str)) {
                            break;
                        }
                    } catch (Exception e2) {
                        e = e2;
                    }
                }
                str2 = split[1];
                kp.a(bufferedReader, "Failed to close system file reader.");
            } catch (Exception e3) {
                e = e3;
                bufferedReader = null;
                try {
                    js.a().c("Fabric", "Error parsing " + file, e);
                    kp.a(bufferedReader, "Failed to close system file reader.");
                    return str2;
                } catch (Throwable th) {
                    e = th;
                    closeable = bufferedReader;
                    kp.a(closeable, "Failed to close system file reader.");
                    throw e;
                }
            } catch (Throwable th2) {
                e = th2;
                kp.a(closeable, "Failed to close system file reader.");
                throw e;
            }
        }
        return str2;
    }

    public static String a(InputStream inputStream) throws IOException {
        Scanner useDelimiter = new Scanner(inputStream).useDelimiter("\\A");
        return useDelimiter.hasNext() ? useDelimiter.next() : "";
    }

    private static String a(InputStream inputStream, String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance(str);
            byte[] bArr = new byte[PKIFailureInfo.badRecipientNonce];
            while (true) {
                int read = inputStream.read(bArr);
                if (read == -1) {
                    return kp.a(instance.digest());
                }
                instance.update(bArr, 0, read);
            }
        } catch (Throwable e) {
            js.a().c("Fabric", "Could not calculate hash for app icon.", e);
            return "";
        }
    }

    public static String a(String str) {
        return kp.a(str, "SHA-1");
    }

    private static String a(String str, String str2) {
        return kp.a(str.getBytes(), str2);
    }

    public static String a(byte[] bArr) {
        char[] cArr = new char[(bArr.length * 2)];
        for (int i = 0; i < bArr.length; i++) {
            int i2 = bArr[i] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV;
            cArr[i * 2] = c[i2 >>> 4];
            cArr[(i * 2) + 1] = c[i2 & 15];
        }
        return new String(cArr);
    }

    private static String a(byte[] bArr, String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance(str);
            instance.update(bArr);
            return kp.a(instance.digest());
        } catch (Throwable e) {
            js.a().c("Fabric", "Could not create hashing algorithm: " + str + ", returning empty string.", e);
            return "";
        }
    }

    public static String a(String... strArr) {
        List<String> arrayList = new ArrayList();
        for (int i = 0; i <= 0; i++) {
            String str = strArr[0];
            if (str != null) {
                arrayList.add(str.replace("-", "").toLowerCase(Locale.US));
            }
        }
        Collections.sort(arrayList);
        StringBuilder stringBuilder = new StringBuilder();
        for (String append : arrayList) {
            stringBuilder.append(append);
        }
        String append2 = stringBuilder.toString();
        return append2.length() > 0 ? kp.a(append2, "SHA-1") : null;
    }

    public static void a(Context context, int i, String str) {
        if (kp.m(context)) {
            js.a().a(4, "Fabric", str);
        }
    }

    public static void a(Context context, String str) {
        if (kp.m(context)) {
            js.a().a("Fabric", str);
        }
    }

    public static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e2) {
            }
        }
    }

    public static void a(Closeable closeable, String str) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable e) {
                js.a().c("Fabric", str, e);
            }
        }
    }

    public static void a(Flushable flushable, String str) {
        if (flushable != null) {
            try {
                flushable.flush();
            } catch (Throwable e) {
                js.a().c("Fabric", str, e);
            }
        }
    }

    public static void a(InputStream inputStream, OutputStream outputStream, byte[] bArr) throws IOException {
        while (true) {
            int read = inputStream.read(bArr);
            if (read != -1) {
                outputStream.write(bArr, 0, read);
            } else {
                return;
            }
        }
    }

    public static boolean a(Context context, String str, boolean z) {
        if (context == null) {
            return z;
        }
        Resources resources = context.getResources();
        if (resources == null) {
            return z;
        }
        int a = kp.a(context, str, "bool");
        if (a > 0) {
            return resources.getBoolean(a);
        }
        int a2 = kp.a(context, str, "string");
        return a2 > 0 ? Boolean.parseBoolean(context.getString(a2)) : z;
    }

    public static long b() {
        String toUpperCase;
        long a;
        synchronized (kp.class) {
            try {
                if (d == -1) {
                    Object a2 = kp.a(new File("/proc/meminfo"), "MemTotal");
                    if (!TextUtils.isEmpty(a2)) {
                        toUpperCase = a2.toUpperCase(Locale.US);
                        if (toUpperCase.endsWith("KB")) {
                            a = kp.a(toUpperCase, "KB", (int) PKIFailureInfo.badRecipientNonce);
                        } else if (toUpperCase.endsWith("MB")) {
                            a = kp.a(toUpperCase, "MB", (int) PKIFailureInfo.badCertTemplate);
                        } else if (toUpperCase.endsWith("GB")) {
                            a = kp.a(toUpperCase, "GB", 1073741824);
                        } else {
                            js.a().a("Fabric", "Unexpected meminfo format while computing RAM: " + toUpperCase);
                            a = 0;
                        }
                        d = a;
                    }
                    a = 0;
                    d = a;
                }
            } catch (Throwable e) {
                js.a().c("Fabric", "Unexpected meminfo format while computing RAM: " + toUpperCase, e);
            } catch (Throwable th) {
                Class cls = kp.class;
            }
            a = d;
        }
        return a;
    }

    public static long b(Context context) {
        MemoryInfo memoryInfo = new MemoryInfo();
        ((ActivityManager) context.getSystemService("activity")).getMemoryInfo(memoryInfo);
        return memoryInfo.availMem;
    }

    public static String b(int i) {
        switch (i) {
            case 2:
                return "V";
            case 3:
                return "D";
            case 4:
                return "I";
            case 5:
                return "W";
            case 6:
                return "E";
            case 7:
                return "A";
            default:
                return "?";
        }
    }

    public static String b(String str) {
        return kp.a(str, "SHA-256");
    }

    public static void b(Context context, String str) {
        if (kp.m(context)) {
            js.a().e("Fabric", str);
        }
    }

    public static long c(String str) {
        StatFs statFs = new StatFs(str);
        long blockSize = (long) statFs.getBlockSize();
        return (((long) statFs.getBlockCount()) * blockSize) - (((long) statFs.getAvailableBlocks()) * blockSize);
    }

    public static Float c(Context context) {
        Intent registerReceiver = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        if (registerReceiver == null) {
            return null;
        }
        return Float.valueOf(((float) registerReceiver.getIntExtra("level", -1)) / ((float) registerReceiver.getIntExtra("scale", -1)));
    }

    public static String c(Context context, String str) {
        int a = kp.a(context, str, "string");
        return a > 0 ? context.getString(a) : "";
    }

    public static boolean d(Context context) {
        return kp.e(context) ? false : ((SensorManager) context.getSystemService("sensor")).getDefaultSensor(8) != null;
    }

    public static boolean d(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean e(Context context) {
        return "sdk".equals(Build.PRODUCT) || "google_sdk".equals(Build.PRODUCT) || Secure.getString(context.getContentResolver(), "android_id") == null;
    }

    public static boolean f(Context context) {
        boolean e = kp.e(context);
        String str = Build.TAGS;
        if ((e || str == null || !str.contains("test-keys")) && !new File("/system/app/Superuser.apk").exists()) {
            File file = new File("/system/xbin/su");
            if (e || !file.exists()) {
                return false;
            }
        }
        return true;
    }

    public static int g(Context context) {
        Object obj = null;
        int i = kp.e(context) ? 1 : 0;
        if (kp.f(context)) {
            i |= 2;
        }
        if (Debug.isDebuggerConnected() || Debug.waitingForDebugger()) {
            obj = 1;
        }
        return obj != null ? i | 4 : i;
    }

    public static boolean h(Context context) {
        return (context.getApplicationInfo().flags & 2) != 0;
    }

    public static String i(Context context) {
        String str;
        Throwable e;
        Closeable closeable = null;
        Closeable openRawResource;
        try {
            openRawResource = context.getResources().openRawResource(kp.j(context));
            try {
                String a = kp.a((InputStream) openRawResource, "SHA-1");
                if (!kp.d(a)) {
                    str = a;
                }
                kp.a(openRawResource, "Failed to close icon input stream.");
            } catch (Exception e2) {
                e = e2;
                try {
                    js.a().c("Fabric", "Could not calculate hash for app icon.", e);
                    kp.a(openRawResource, "Failed to close icon input stream.");
                    return str;
                } catch (Throwable th) {
                    e = th;
                    closeable = openRawResource;
                    kp.a(closeable, "Failed to close icon input stream.");
                    throw e;
                }
            }
        } catch (Exception e3) {
            e = e3;
            openRawResource = null;
            js.a().c("Fabric", "Could not calculate hash for app icon.", e);
            kp.a(openRawResource, "Failed to close icon input stream.");
            return str;
        } catch (Throwable th2) {
            e = th2;
            kp.a(closeable, "Failed to close icon input stream.");
            throw e;
        }
        return str;
    }

    public static int j(Context context) {
        return context.getApplicationContext().getApplicationInfo().icon;
    }

    public static String k(Context context) {
        int a = kp.a(context, "io.fabric.android.build_id", "string");
        if (a == 0) {
            a = kp.a(context, "com.crashlytics.android.build_id", "string");
        }
        if (a == 0) {
            return null;
        }
        String string = context.getResources().getString(a);
        js.a().a("Fabric", "Build ID is: " + string);
        return string;
    }

    @SuppressLint({"MissingPermission"})
    public static boolean l(Context context) {
        if ((context.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") == 0 ? 1 : null) == null) {
            return true;
        }
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    private static boolean m(Context context) {
        if (b == null) {
            b = Boolean.valueOf(kp.a(context, "com.crashlytics.Trace", false));
        }
        return b.booleanValue();
    }
}
