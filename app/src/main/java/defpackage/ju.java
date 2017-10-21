package defpackage;

import android.os.SystemClock;
import android.text.TextUtils;
import java.io.Closeable;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

final class ju implements Callable<Map<String, ka>> {
    final String a;

    ju(String str) {
        this.a = str;
    }

    private static Map<String, ka> a() {
        Map<String, ka> hashMap = new HashMap();
        try {
            Class.forName("com.google.android.gms.ads.AdView");
            ka kaVar = new ka("com.google.firebase.firebase-ads", "0.0.0", "binary");
            hashMap.put(kaVar.a, kaVar);
            js.a().b("Fabric", "Found kit: com.google.firebase.firebase-ads");
        } catch (Exception e) {
        }
        return hashMap;
    }

    private static ka a(ZipEntry zipEntry, ZipFile zipFile) {
        Closeable inputStream;
        Throwable e;
        Closeable closeable = null;
        try {
            inputStream = zipFile.getInputStream(zipEntry);
            try {
                Properties properties = new Properties();
                properties.load(inputStream);
                Object property = properties.getProperty("fabric-identifier");
                Object property2 = properties.getProperty("fabric-version");
                String property3 = properties.getProperty("fabric-build-type");
                if (TextUtils.isEmpty(property) || TextUtils.isEmpty(property2)) {
                    throw new IllegalStateException("Invalid format of fabric file," + zipEntry.getName());
                }
                ka kaVar = new ka(property, property2, property3);
                kp.a(inputStream);
                return kaVar;
            } catch (IOException e2) {
                e = e2;
                try {
                    js.a().c("Fabric", "Error when parsing fabric properties " + zipEntry.getName(), e);
                    kp.a(inputStream);
                    return null;
                } catch (Throwable th) {
                    e = th;
                    closeable = inputStream;
                    kp.a(closeable);
                    throw e;
                }
            }
        } catch (IOException e3) {
            e = e3;
            inputStream = null;
            js.a().c("Fabric", "Error when parsing fabric properties " + zipEntry.getName(), e);
            kp.a(inputStream);
            return null;
        } catch (Throwable th2) {
            e = th2;
            kp.a(closeable);
            throw e;
        }
    }

    private Map<String, ka> b() throws Exception {
        Map<String, ka> hashMap = new HashMap();
        ZipFile zipFile = new ZipFile(this.a);
        Enumeration entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry zipEntry = (ZipEntry) entries.nextElement();
            if (zipEntry.getName().startsWith("fabric/") && zipEntry.getName().length() > 7) {
                ka a = ju.a(zipEntry, zipFile);
                if (a != null) {
                    hashMap.put(a.a, a);
                    js.a().b("Fabric", String.format("Found kit:[%s] version:[%s]", new Object[]{a.a, a.b}));
                }
            }
        }
        try {
            zipFile.close();
            return hashMap;
        } catch (IOException e) {
            return hashMap;
        }
    }

    public final /* synthetic */ Object call() throws Exception {
        Map hashMap = new HashMap();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        hashMap.putAll(ju.a());
        hashMap.putAll(b());
        js.a().b("Fabric", "finish scanning in " + (SystemClock.elapsedRealtime() - elapsedRealtime));
        return hashMap;
    }
}
