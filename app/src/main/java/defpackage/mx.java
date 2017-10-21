package defpackage;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import org.json.JSONObject;

final class mx implements mv {
    private final jy a;

    public mx(jy jyVar) {
        this.a = jyVar;
    }

    public final JSONObject a() {
        Closeable fileInputStream;
        JSONObject jSONObject;
        Throwable th;
        Closeable closeable;
        Throwable th2;
        Closeable closeable2 = null;
        js.a().a("Fabric", "Reading cached settings...");
        try {
            File file = new File(new mm(this.a).a(), "com.crashlytics.settings.json");
            if (file.exists()) {
                fileInputStream = new FileInputStream(file);
                try {
                    jSONObject = new JSONObject(kp.a((InputStream) fileInputStream));
                } catch (Throwable e) {
                    th = e;
                    closeable = fileInputStream;
                    th2 = th;
                    try {
                        js.a().c("Fabric", "Failed to fetch cached settings", th2);
                        kp.a(closeable, "Error while closing settings cache file.");
                        return jSONObject;
                    } catch (Throwable th3) {
                        th2 = th3;
                        closeable2 = closeable;
                        kp.a(closeable2, "Error while closing settings cache file.");
                        throw th2;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    closeable2 = fileInputStream;
                    th2 = th;
                    kp.a(closeable2, "Error while closing settings cache file.");
                    throw th2;
                }
            }
            js.a().a("Fabric", "No cached settings found.");
            fileInputStream = null;
            kp.a(fileInputStream, "Error while closing settings cache file.");
        } catch (Exception e2) {
            th2 = e2;
            closeable = null;
            js.a().c("Fabric", "Failed to fetch cached settings", th2);
            kp.a(closeable, "Error while closing settings cache file.");
            return jSONObject;
        } catch (Throwable th5) {
            th2 = th5;
            kp.a(closeable2, "Error while closing settings cache file.");
            throw th2;
        }
        return jSONObject;
    }

    public final void a(long j, JSONObject jSONObject) {
        Throwable e;
        Throwable th;
        Throwable th2;
        Closeable closeable = null;
        js.a().a("Fabric", "Writing settings to cache file...");
        if (jSONObject != null) {
            Closeable fileWriter;
            try {
                jSONObject.put("expires_at", j);
                fileWriter = new FileWriter(new File(new mm(this.a).a(), "com.crashlytics.settings.json"));
                try {
                    fileWriter.write(jSONObject.toString());
                    fileWriter.flush();
                    kp.a(fileWriter, "Failed to close settings writer.");
                } catch (Exception e2) {
                    e = e2;
                    try {
                        js.a().c("Fabric", "Failed to cache settings", e);
                        kp.a(fileWriter, "Failed to close settings writer.");
                    } catch (Throwable e3) {
                        th = e3;
                        closeable = fileWriter;
                        th2 = th;
                        kp.a(closeable, "Failed to close settings writer.");
                        throw th2;
                    }
                }
            } catch (Throwable th22) {
                th = th22;
                fileWriter = null;
                e3 = th;
                js.a().c("Fabric", "Failed to cache settings", e3);
                kp.a(fileWriter, "Failed to close settings writer.");
            } catch (Throwable th3) {
                th22 = th3;
                kp.a(closeable, "Failed to close settings writer.");
                throw th22;
            }
        }
    }
}
