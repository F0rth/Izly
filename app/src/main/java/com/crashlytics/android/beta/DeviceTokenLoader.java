package com.crashlytics.android.beta;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DeviceTokenLoader implements kg<String> {
    private static final String BETA_APP_PACKAGE_NAME = "io.crash.air";
    private static final String DIRFACTOR_DEVICE_TOKEN_PREFIX = "assets/com.crashlytics.android.beta/dirfactor-device-token=";

    String determineDeviceToken(ZipInputStream zipInputStream) throws IOException {
        ZipEntry nextEntry = zipInputStream.getNextEntry();
        if (nextEntry != null) {
            String name = nextEntry.getName();
            if (name.startsWith(DIRFACTOR_DEVICE_TOKEN_PREFIX)) {
                return name.substring(59, name.length() - 1);
            }
        }
        return "";
    }

    ZipInputStream getZipInputStreamOfApkFrom(Context context, String str) throws NameNotFoundException, FileNotFoundException {
        return new ZipInputStream(new FileInputStream(context.getPackageManager().getApplicationInfo(str, 0).sourceDir));
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String load(android.content.Context r9) throws java.lang.Exception {
        /*
        r8 = this;
        r2 = 0;
        r4 = java.lang.System.nanoTime();
        r0 = "";
        r1 = "io.crash.air";
        r2 = r8.getZipInputStreamOfApkFrom(r9, r1);	 Catch:{ NameNotFoundException -> 0x004e, FileNotFoundException -> 0x00c7, IOException -> 0x00c5, all -> 0x00c3 }
        r0 = r8.determineDeviceToken(r2);	 Catch:{ NameNotFoundException -> 0x004e, FileNotFoundException -> 0x006d, IOException -> 0x008c }
        if (r2 == 0) goto L_0x0016;
    L_0x0013:
        r2.close();	 Catch:{ IOException -> 0x0041 }
    L_0x0016:
        r2 = java.lang.System.nanoTime();
        r2 = r2 - r4;
        r2 = (double) r2;
        r4 = 4696837146684686336; // 0x412e848000000000 float:0.0 double:1000000.0;
        r2 = r2 / r4;
        r1 = defpackage.js.a();
        r4 = "Beta";
        r5 = new java.lang.StringBuilder;
        r6 = "Beta device token load took ";
        r5.<init>(r6);
        r2 = r5.append(r2);
        r3 = "ms";
        r2 = r2.append(r3);
        r2 = r2.toString();
        r1.a(r4, r2);
        return r0;
    L_0x0041:
        r1 = move-exception;
        r2 = defpackage.js.a();
        r3 = "Beta";
        r6 = "Failed to close the APK file";
        r2.c(r3, r6, r1);
        goto L_0x0016;
    L_0x004e:
        r1 = move-exception;
        r1 = defpackage.js.a();	 Catch:{ all -> 0x00c1 }
        r3 = "Beta";
        r6 = "Beta by Crashlytics app is not installed";
        r1.a(r3, r6);	 Catch:{ all -> 0x00c1 }
        if (r2 == 0) goto L_0x0016;
    L_0x005c:
        r2.close();	 Catch:{ IOException -> 0x0060 }
        goto L_0x0016;
    L_0x0060:
        r1 = move-exception;
        r2 = defpackage.js.a();
        r3 = "Beta";
        r6 = "Failed to close the APK file";
        r2.c(r3, r6, r1);
        goto L_0x0016;
    L_0x006d:
        r1 = move-exception;
    L_0x006e:
        r3 = defpackage.js.a();	 Catch:{ all -> 0x00ad }
        r6 = "Beta";
        r7 = "Failed to find the APK file";
        r3.c(r6, r7, r1);	 Catch:{ all -> 0x00ad }
        if (r2 == 0) goto L_0x0016;
    L_0x007b:
        r2.close();	 Catch:{ IOException -> 0x007f }
        goto L_0x0016;
    L_0x007f:
        r1 = move-exception;
        r2 = defpackage.js.a();
        r3 = "Beta";
        r6 = "Failed to close the APK file";
        r2.c(r3, r6, r1);
        goto L_0x0016;
    L_0x008c:
        r1 = move-exception;
    L_0x008d:
        r3 = defpackage.js.a();	 Catch:{ all -> 0x00ad }
        r6 = "Beta";
        r7 = "Failed to read the APK file";
        r3.c(r6, r7, r1);	 Catch:{ all -> 0x00ad }
        if (r2 == 0) goto L_0x0016;
    L_0x009a:
        r2.close();	 Catch:{ IOException -> 0x009f }
        goto L_0x0016;
    L_0x009f:
        r1 = move-exception;
        r2 = defpackage.js.a();
        r3 = "Beta";
        r6 = "Failed to close the APK file";
        r2.c(r3, r6, r1);
        goto L_0x0016;
    L_0x00ad:
        r0 = move-exception;
    L_0x00ae:
        if (r2 == 0) goto L_0x00b3;
    L_0x00b0:
        r2.close();	 Catch:{ IOException -> 0x00b4 }
    L_0x00b3:
        throw r0;
    L_0x00b4:
        r1 = move-exception;
        r2 = defpackage.js.a();
        r3 = "Beta";
        r4 = "Failed to close the APK file";
        r2.c(r3, r4, r1);
        goto L_0x00b3;
    L_0x00c1:
        r0 = move-exception;
        goto L_0x00ae;
    L_0x00c3:
        r0 = move-exception;
        goto L_0x00ae;
    L_0x00c5:
        r1 = move-exception;
        goto L_0x008d;
    L_0x00c7:
        r1 = move-exception;
        goto L_0x006e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.crashlytics.android.beta.DeviceTokenLoader.load(android.content.Context):java.lang.String");
    }
}
