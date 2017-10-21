package com.ad4screen.sdk.common.c;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build.VERSION;

import com.ad4screen.sdk.A4SService;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.b.m.d;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.common.h.a;
import com.ad4screen.sdk.common.i;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class b implements a {
    public JSONObject a = new JSONObject();
    private final String b;
    private final Context c;
    private boolean d;

    public b(Context context, String str) {
        this.b = str;
        this.c = context;
        this.a = a(context, this.b);
    }

    @SuppressLint({"NewApi"})
    private JSONObject a(Context context, String str) {
        Object obj = null;
        try {
            String a = i.a(context, "com.ad4screen.usbstorage", A4SService.class);
            if (a != null && a.equalsIgnoreCase("true")) {
                obj = 1;
            }
            InputStream fileInputStream = obj != null ? new FileInputStream(new File(d.a(context), str)) : context.openFileInput(str);
            if (fileInputStream == null) {
                return new JSONObject();
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, Charset.forName("UTF-8")));
            StringBuffer stringBuffer = new StringBuffer("");
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                stringBuffer.append(readLine);
            }
            bufferedReader.close();
            JSONObject jSONObject = new JSONObject(stringBuffer.toString());
            String str2 = "";
            if (!jSONObject.isNull("installDate")) {
                str2 = jSONObject.getString("installDate");
            }
            try {
                PackageManager packageManager = context.getPackageManager();
                ApplicationInfo applicationInfo = context.getApplicationInfo();
                PackageInfo packageInfo = packageManager.getPackageInfo(applicationInfo.packageName, 0);
                String a2 = i.a(packageInfo, applicationInfo);
                if (VERSION.SDK_INT >= 9 && a2.equalsIgnoreCase(h.a(new Date(packageInfo.lastUpdateTime), a.ISO8601)) && !r0.equalsIgnoreCase(a2)) {
                    Log.internal("JSONArchive|App has been newly installed and file " + str + " has been restored from a backup. We are ignoring it and starting fresh..");
                    return new JSONObject();
                }
            } catch (Throwable e) {
                Log.warn("JSONArchive|Could not retrieve current package information", e);
            } catch (Throwable e2) {
                Log.internal(e2);
            }
            if (jSONObject.isNull("version")) {
                Log.internal("JSONArchive|File " + str + " has no version information, aborting load");
                return new JSONObject();
            }
            int b = b();
            int i = jSONObject.getInt("version");
            if (i == b) {
                return jSONObject;
            }
            Log.internal("JSONArchive|File " + str + " is too old, upgrading from " + i + " to " + b);
            if (a(i, jSONObject)) {
                return jSONObject;
            }
            Log.internal("JSONArchive|File " + str + " is too old and no upgrade can be performed (file version is older than " + b + "), aborting load");
            return new JSONObject();
        } catch (FileNotFoundException e3) {
            Log.internal("JSONArchive|Unable to open file (reading) : " + str);
        } catch (Throwable e22) {
            Log.internal("JSONArchive|Error while closing file (reading) : " + str, e22);
        } catch (Throwable e222) {
            Log.internal("JSONArchive|Error while converting file to JSONObject (reading) : " + str, e222);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(android.content.Context r9, java.lang.String r10, org.json.JSONObject r11) {
        /*
        r8 = this;
        r1 = 0;
        r0 = com.ad4screen.sdk.d.b.a(r9);	 Catch:{ ConcurrentModificationException -> 0x0153, FileNotFoundException -> 0x0150, IOException -> 0x014d, JSONException -> 0x0106 }
        r0 = r0.G();	 Catch:{ ConcurrentModificationException -> 0x0153, FileNotFoundException -> 0x0150, IOException -> 0x014d, JSONException -> 0x0106 }
        if (r0 == 0) goto L_0x0053;
    L_0x000b:
        r0 = new java.io.FileOutputStream;	 Catch:{ ConcurrentModificationException -> 0x0153, FileNotFoundException -> 0x0150, IOException -> 0x014d, JSONException -> 0x0106 }
        r2 = new java.io.File;	 Catch:{ ConcurrentModificationException -> 0x0153, FileNotFoundException -> 0x0150, IOException -> 0x014d, JSONException -> 0x0106 }
        r3 = com.ad4screen.sdk.common.b.m.d.a(r9);	 Catch:{ ConcurrentModificationException -> 0x0153, FileNotFoundException -> 0x0150, IOException -> 0x014d, JSONException -> 0x0106 }
        r2.<init>(r3, r10);	 Catch:{ ConcurrentModificationException -> 0x0153, FileNotFoundException -> 0x0150, IOException -> 0x014d, JSONException -> 0x0106 }
        r0.<init>(r2);	 Catch:{ ConcurrentModificationException -> 0x0153, FileNotFoundException -> 0x0150, IOException -> 0x014d, JSONException -> 0x0106 }
    L_0x0019:
        r2 = new java.io.OutputStreamWriter;	 Catch:{ ConcurrentModificationException -> 0x0060, FileNotFoundException -> 0x0088, IOException -> 0x00bf, JSONException -> 0x0148, all -> 0x0143 }
        r1 = "UTF-8";
        r2.<init>(r0, r1);	 Catch:{ ConcurrentModificationException -> 0x0060, FileNotFoundException -> 0x0088, IOException -> 0x00bf, JSONException -> 0x0148, all -> 0x0143 }
        r1 = "version";
        r3 = r8.b();	 Catch:{ ConcurrentModificationException -> 0x0060, FileNotFoundException -> 0x0088, IOException -> 0x00bf, JSONException -> 0x0148, all -> 0x0143 }
        r11.put(r1, r3);	 Catch:{ ConcurrentModificationException -> 0x0060, FileNotFoundException -> 0x0088, IOException -> 0x00bf, JSONException -> 0x0148, all -> 0x0143 }
        r1 = r9.getPackageManager();	 Catch:{ NameNotFoundException -> 0x0059, RuntimeException -> 0x0083, JSONException -> 0x00b8, ConcurrentModificationException -> 0x0060, FileNotFoundException -> 0x0088, IOException -> 0x00bf, all -> 0x0143 }
        r3 = r9.getApplicationInfo();	 Catch:{ NameNotFoundException -> 0x0059, RuntimeException -> 0x0083, JSONException -> 0x00b8, ConcurrentModificationException -> 0x0060, FileNotFoundException -> 0x0088, IOException -> 0x00bf, all -> 0x0143 }
        r4 = "installDate";
        r5 = r3.packageName;	 Catch:{ NameNotFoundException -> 0x0059, RuntimeException -> 0x0083, JSONException -> 0x00b8, ConcurrentModificationException -> 0x0060, FileNotFoundException -> 0x0088, IOException -> 0x00bf, all -> 0x0143 }
        r6 = 0;
        r1 = r1.getPackageInfo(r5, r6);	 Catch:{ NameNotFoundException -> 0x0059, RuntimeException -> 0x0083, JSONException -> 0x00b8, ConcurrentModificationException -> 0x0060, FileNotFoundException -> 0x0088, IOException -> 0x00bf, all -> 0x0143 }
        r1 = com.ad4screen.sdk.common.i.a(r1, r3);	 Catch:{ NameNotFoundException -> 0x0059, RuntimeException -> 0x0083, JSONException -> 0x00b8, ConcurrentModificationException -> 0x0060, FileNotFoundException -> 0x0088, IOException -> 0x00bf, all -> 0x0143 }
        r11.put(r4, r1);	 Catch:{ NameNotFoundException -> 0x0059, RuntimeException -> 0x0083, JSONException -> 0x00b8, ConcurrentModificationException -> 0x0060, FileNotFoundException -> 0x0088, IOException -> 0x00bf, all -> 0x0143 }
    L_0x0041:
        r1 = r11.toString();	 Catch:{ ConcurrentModificationException -> 0x0060, FileNotFoundException -> 0x0088, IOException -> 0x00bf, JSONException -> 0x0148, all -> 0x0143 }
        if (r1 == 0) goto L_0x004a;
    L_0x0047:
        r2.write(r1);	 Catch:{ ConcurrentModificationException -> 0x0060, FileNotFoundException -> 0x0088, IOException -> 0x00bf, JSONException -> 0x0148, all -> 0x0143 }
    L_0x004a:
        r2.close();	 Catch:{ ConcurrentModificationException -> 0x0060, FileNotFoundException -> 0x0088, IOException -> 0x00bf, JSONException -> 0x0148, all -> 0x0143 }
        if (r0 == 0) goto L_0x0052;
    L_0x004f:
        r0.close();	 Catch:{ IOException -> 0x00f1 }
    L_0x0052:
        return;
    L_0x0053:
        r0 = 0;
        r0 = r9.openFileOutput(r10, r0);	 Catch:{ ConcurrentModificationException -> 0x0153, FileNotFoundException -> 0x0150, IOException -> 0x014d, JSONException -> 0x0106 }
        goto L_0x0019;
    L_0x0059:
        r1 = move-exception;
        r3 = "JSONArchive|Could not retrieve current package information";
        com.ad4screen.sdk.Log.warn(r3, r1);	 Catch:{ ConcurrentModificationException -> 0x0060, FileNotFoundException -> 0x0088, IOException -> 0x00bf, JSONException -> 0x0148, all -> 0x0143 }
        goto L_0x0041;
    L_0x0060:
        r1 = move-exception;
        r7 = r1;
        r1 = r0;
        r0 = r7;
    L_0x0064:
        r2 = "JSONArchive|ConcurrentModificationException on JSON Object";
        com.ad4screen.sdk.Log.internal(r2, r0);	 Catch:{ all -> 0x0128 }
        if (r1 == 0) goto L_0x0052;
    L_0x006b:
        r1.close();	 Catch:{ IOException -> 0x006f }
        goto L_0x0052;
    L_0x006f:
        r0 = move-exception;
        r1 = new java.lang.StringBuilder;
        r2 = "JSONArchive|Error while closing file (writing) : ";
        r1.<init>(r2);
        r1 = r1.append(r10);
        r1 = r1.toString();
        com.ad4screen.sdk.Log.internal(r1, r0);
        goto L_0x0052;
    L_0x0083:
        r1 = move-exception;
        com.ad4screen.sdk.Log.internal(r1);	 Catch:{ ConcurrentModificationException -> 0x0060, FileNotFoundException -> 0x0088, IOException -> 0x00bf, JSONException -> 0x0148, all -> 0x0143 }
        goto L_0x0041;
    L_0x0088:
        r1 = move-exception;
        r7 = r1;
        r1 = r0;
        r0 = r7;
    L_0x008c:
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0128 }
        r3 = "JSONArchive|Unable to open file (writing) : ";
        r2.<init>(r3);	 Catch:{ all -> 0x0128 }
        r2 = r2.append(r10);	 Catch:{ all -> 0x0128 }
        r2 = r2.toString();	 Catch:{ all -> 0x0128 }
        com.ad4screen.sdk.Log.internal(r2, r0);	 Catch:{ all -> 0x0128 }
        if (r1 == 0) goto L_0x0052;
    L_0x00a0:
        r1.close();	 Catch:{ IOException -> 0x00a4 }
        goto L_0x0052;
    L_0x00a4:
        r0 = move-exception;
        r1 = new java.lang.StringBuilder;
        r2 = "JSONArchive|Error while closing file (writing) : ";
        r1.<init>(r2);
        r1 = r1.append(r10);
        r1 = r1.toString();
        com.ad4screen.sdk.Log.internal(r1, r0);
        goto L_0x0052;
    L_0x00b8:
        r1 = move-exception;
        r3 = "BackupStrategy|Strategy failed";
        com.ad4screen.sdk.Log.internal(r3, r1);	 Catch:{ ConcurrentModificationException -> 0x0060, FileNotFoundException -> 0x0088, IOException -> 0x00bf, JSONException -> 0x0148, all -> 0x0143 }
        goto L_0x0041;
    L_0x00bf:
        r1 = move-exception;
        r7 = r1;
        r1 = r0;
        r0 = r7;
    L_0x00c3:
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0128 }
        r3 = "JSONArchive|Error while writing file (writing) : ";
        r2.<init>(r3);	 Catch:{ all -> 0x0128 }
        r2 = r2.append(r10);	 Catch:{ all -> 0x0128 }
        r2 = r2.toString();	 Catch:{ all -> 0x0128 }
        com.ad4screen.sdk.Log.internal(r2, r0);	 Catch:{ all -> 0x0128 }
        if (r1 == 0) goto L_0x0052;
    L_0x00d7:
        r1.close();	 Catch:{ IOException -> 0x00dc }
        goto L_0x0052;
    L_0x00dc:
        r0 = move-exception;
        r1 = new java.lang.StringBuilder;
        r2 = "JSONArchive|Error while closing file (writing) : ";
        r1.<init>(r2);
        r1 = r1.append(r10);
        r1 = r1.toString();
        com.ad4screen.sdk.Log.internal(r1, r0);
        goto L_0x0052;
    L_0x00f1:
        r0 = move-exception;
        r1 = new java.lang.StringBuilder;
        r2 = "JSONArchive|Error while closing file (writing) : ";
        r1.<init>(r2);
        r1 = r1.append(r10);
        r1 = r1.toString();
        com.ad4screen.sdk.Log.internal(r1, r0);
        goto L_0x0052;
    L_0x0106:
        r0 = move-exception;
    L_0x0107:
        r2 = "JSONArchive|Error while writing into JSON Object : ";
        com.ad4screen.sdk.Log.internal(r2, r0);	 Catch:{ all -> 0x0128 }
        if (r1 == 0) goto L_0x0052;
    L_0x010e:
        r1.close();	 Catch:{ IOException -> 0x0113 }
        goto L_0x0052;
    L_0x0113:
        r0 = move-exception;
        r1 = new java.lang.StringBuilder;
        r2 = "JSONArchive|Error while closing file (writing) : ";
        r1.<init>(r2);
        r1 = r1.append(r10);
        r1 = r1.toString();
        com.ad4screen.sdk.Log.internal(r1, r0);
        goto L_0x0052;
    L_0x0128:
        r0 = move-exception;
    L_0x0129:
        if (r1 == 0) goto L_0x012e;
    L_0x012b:
        r1.close();	 Catch:{ IOException -> 0x012f }
    L_0x012e:
        throw r0;
    L_0x012f:
        r1 = move-exception;
        r2 = new java.lang.StringBuilder;
        r3 = "JSONArchive|Error while closing file (writing) : ";
        r2.<init>(r3);
        r2 = r2.append(r10);
        r2 = r2.toString();
        com.ad4screen.sdk.Log.internal(r2, r1);
        goto L_0x012e;
    L_0x0143:
        r1 = move-exception;
        r7 = r1;
        r1 = r0;
        r0 = r7;
        goto L_0x0129;
    L_0x0148:
        r1 = move-exception;
        r7 = r1;
        r1 = r0;
        r0 = r7;
        goto L_0x0107;
    L_0x014d:
        r0 = move-exception;
        goto L_0x00c3;
    L_0x0150:
        r0 = move-exception;
        goto L_0x008c;
    L_0x0153:
        r0 = move-exception;
        goto L_0x0064;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ad4screen.sdk.common.c.b.a(android.content.Context, java.lang.String, org.json.JSONObject):void");
    }

    public int a(String str, int i) {
        try {
            Object obj = this.a.get(str);
            if (obj instanceof Integer) {
                i = ((Integer) obj).intValue();
            }
        } catch (JSONException e) {
        }
        return i;
    }

    public long a(String str, long j) {
        try {
            Object obj = this.a.get(str);
            if (obj instanceof Long) {
                j = ((Long) obj).longValue();
            }
        } catch (JSONException e) {
        }
        return j;
    }

    public a a(String str, Object obj) {
        try {
            JSONObject a = new e().a(obj);
            if (a != null) {
                this.a.put(str, a);
            } else {
                this.a.put(str, obj);
            }
            if (!this.d) {
                d();
            }
        } catch (Throwable e) {
            Log.debug("JSONArchive|Error while putting data", e);
        }
        return this;
    }

    public String a(String str, String str2) {
        try {
            Object obj = this.a.get(str);
            return !(obj instanceof String) ? str2 : (String) obj;
        } catch (JSONException e) {
            return str2;
        }
    }

    public void a(String str) {
        this.a.remove(str);
        if (!this.d) {
            d();
        }
    }

    public boolean a(String str, boolean z) {
        try {
            Object obj = this.a.get(str);
            if (obj instanceof Boolean) {
                z = ((Boolean) obj).booleanValue();
            }
        } catch (JSONException e) {
        }
        return z;
    }

    public <T> T b(String str, T t) {
        try {
            t = new e().a(this.a.getString(str), t);
        } catch (JSONException e) {
        }
        return t;
    }

    public void c() {
        this.a = new JSONObject();
        if (!this.d) {
            d();
        }
    }

    public void d() {
        this.d = false;
        a(this.c, this.b, this.a);
    }
}
