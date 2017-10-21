package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.util.client.zzb;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@zzhb
public class zzdu extends zzdr {
    private static final Set<String> zzzX = Collections.synchronizedSet(new HashSet());
    private static final DecimalFormat zzzY = new DecimalFormat("#,###");
    private boolean zzAa;
    private File zzzZ;

    public zzdu(zzjp com_google_android_gms_internal_zzjp) {
        super(com_google_android_gms_internal_zzjp);
        File cacheDir = this.mContext.getCacheDir();
        if (cacheDir == null) {
            zzb.zzaK("Context.getCacheDir() returned null");
            return;
        }
        this.zzzZ = new File(cacheDir, "admobVideoStreams");
        if (!this.zzzZ.isDirectory() && !this.zzzZ.mkdirs()) {
            zzb.zzaK("Could not create preload cache directory at " + this.zzzZ.getAbsolutePath());
            this.zzzZ = null;
        } else if (!this.zzzZ.setReadable(true, false) || !this.zzzZ.setExecutable(true, false)) {
            zzb.zzaK("Could not set cache file permissions at " + this.zzzZ.getAbsolutePath());
            this.zzzZ = null;
        }
    }

    private File zza(File file) {
        return new File(this.zzzZ, file.getName() + ".done");
    }

    private static void zzb(File file) {
        if (file.isFile()) {
            file.setLastModified(System.currentTimeMillis());
            return;
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
        }
    }

    public void abort() {
        this.zzAa = true;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean zzU(java.lang.String r26) {
        /*
        r25 = this;
        r0 = r25;
        r2 = r0.zzzZ;
        if (r2 != 0) goto L_0x0013;
    L_0x0006:
        r2 = 0;
        r3 = "noCacheDir";
        r4 = 0;
        r0 = r25;
        r1 = r26;
        r0.zza(r1, r2, r3, r4);
        r2 = 0;
    L_0x0012:
        return r2;
    L_0x0013:
        r3 = r25.zzea();
        r2 = com.google.android.gms.internal.zzbt.zzvO;
        r2 = r2.get();
        r2 = (java.lang.Integer) r2;
        r2 = r2.intValue();
        if (r3 <= r2) goto L_0x003d;
    L_0x0025:
        r2 = r25.zzeb();
        if (r2 != 0) goto L_0x0013;
    L_0x002b:
        r2 = "Unable to expire stream cache";
        com.google.android.gms.ads.internal.util.client.zzb.zzaK(r2);
        r2 = 0;
        r3 = "expireFailed";
        r4 = 0;
        r0 = r25;
        r1 = r26;
        r0.zza(r1, r2, r3, r4);
        r2 = 0;
        goto L_0x0012;
    L_0x003d:
        r2 = r25.zzV(r26);
        r9 = new java.io.File;
        r0 = r25;
        r3 = r0.zzzZ;
        r9.<init>(r3, r2);
        r0 = r25;
        r10 = r0.zza(r9);
        r2 = r9.isFile();
        if (r2 == 0) goto L_0x0082;
    L_0x0056:
        r2 = r10.isFile();
        if (r2 == 0) goto L_0x0082;
    L_0x005c:
        r2 = r9.length();
        r2 = (int) r2;
        r3 = new java.lang.StringBuilder;
        r4 = "Stream cache hit at ";
        r3.<init>(r4);
        r0 = r26;
        r3 = r3.append(r0);
        r3 = r3.toString();
        com.google.android.gms.ads.internal.util.client.zzb.zzaI(r3);
        r3 = r9.getAbsolutePath();
        r0 = r25;
        r1 = r26;
        r0.zza(r1, r3, r2);
        r2 = 1;
        goto L_0x0012;
    L_0x0082:
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r0 = r25;
        r3 = r0.zzzZ;
        r3 = r3.getAbsolutePath();
        r2 = r2.append(r3);
        r0 = r26;
        r2 = r2.append(r0);
        r11 = r2.toString();
        r3 = zzzX;
        monitor-enter(r3);
        r2 = zzzX;	 Catch:{ all -> 0x00ce }
        r2 = r2.contains(r11);	 Catch:{ all -> 0x00ce }
        if (r2 == 0) goto L_0x00d1;
    L_0x00a8:
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00ce }
        r4 = "Stream cache already in progress at ";
        r2.<init>(r4);	 Catch:{ all -> 0x00ce }
        r0 = r26;
        r2 = r2.append(r0);	 Catch:{ all -> 0x00ce }
        r2 = r2.toString();	 Catch:{ all -> 0x00ce }
        com.google.android.gms.ads.internal.util.client.zzb.zzaK(r2);	 Catch:{ all -> 0x00ce }
        r2 = r9.getAbsolutePath();	 Catch:{ all -> 0x00ce }
        r4 = "inProgress";
        r5 = 0;
        r0 = r25;
        r1 = r26;
        r0.zza(r1, r2, r4, r5);	 Catch:{ all -> 0x00ce }
        monitor-exit(r3);	 Catch:{ all -> 0x00ce }
        r2 = 0;
        goto L_0x0012;
    L_0x00ce:
        r2 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x00ce }
        throw r2;
    L_0x00d1:
        r2 = zzzX;	 Catch:{ all -> 0x00ce }
        r2.add(r11);	 Catch:{ all -> 0x00ce }
        monitor-exit(r3);	 Catch:{ all -> 0x00ce }
        r4 = 0;
        r2 = new java.net.URL;	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r0 = r26;
        r2.<init>(r0);	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r3 = r2.openConnection();	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r2 = com.google.android.gms.internal.zzbt.zzvT;	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r2 = r2.get();	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r2 = (java.lang.Integer) r2;	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r2 = r2.intValue();	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r3.setConnectTimeout(r2);	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r3.setReadTimeout(r2);	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r2 = r3 instanceof java.net.HttpURLConnection;	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        if (r2 == 0) goto L_0x01a6;
    L_0x00f9:
        r0 = r3;
        r0 = (java.net.HttpURLConnection) r0;	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r2 = r0;
        r5 = r2.getResponseCode();	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r2 = 400; // 0x190 float:5.6E-43 double:1.976E-321;
        if (r5 < r2) goto L_0x01a6;
    L_0x0105:
        r2 = "badUrl";
        r3 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x03d4, RuntimeException -> 0x03a3 }
        r6 = "HTTP request failed. Code: ";
        r3.<init>(r6);	 Catch:{ IOException -> 0x03d4, RuntimeException -> 0x03a3 }
        r6 = java.lang.Integer.toString(r5);	 Catch:{ IOException -> 0x03d4, RuntimeException -> 0x03a3 }
        r3 = r3.append(r6);	 Catch:{ IOException -> 0x03d4, RuntimeException -> 0x03a3 }
        r3 = r3.toString();	 Catch:{ IOException -> 0x03d4, RuntimeException -> 0x03a3 }
        r6 = new java.io.IOException;	 Catch:{ IOException -> 0x013b, RuntimeException -> 0x03ad }
        r7 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x013b, RuntimeException -> 0x03ad }
        r8 = "HTTP status code ";
        r7.<init>(r8);	 Catch:{ IOException -> 0x013b, RuntimeException -> 0x03ad }
        r5 = r7.append(r5);	 Catch:{ IOException -> 0x013b, RuntimeException -> 0x03ad }
        r7 = " at ";
        r5 = r5.append(r7);	 Catch:{ IOException -> 0x013b, RuntimeException -> 0x03ad }
        r0 = r26;
        r5 = r5.append(r0);	 Catch:{ IOException -> 0x013b, RuntimeException -> 0x03ad }
        r5 = r5.toString();	 Catch:{ IOException -> 0x013b, RuntimeException -> 0x03ad }
        r6.<init>(r5);	 Catch:{ IOException -> 0x013b, RuntimeException -> 0x03ad }
        throw r6;	 Catch:{ IOException -> 0x013b, RuntimeException -> 0x03ad }
    L_0x013b:
        r5 = move-exception;
        r24 = r4;
        r4 = r3;
        r3 = r2;
        r2 = r24;
    L_0x0142:
        r6 = r5 instanceof java.lang.RuntimeException;
        if (r6 == 0) goto L_0x014e;
    L_0x0146:
        r6 = com.google.android.gms.ads.internal.zzr.zzbF();
        r7 = 1;
        r6.zzb(r5, r7);
    L_0x014e:
        r2.close();	 Catch:{ IOException -> 0x03de, NullPointerException -> 0x03e1 }
    L_0x0151:
        r0 = r25;
        r2 = r0.zzAa;
        if (r2 == 0) goto L_0x037b;
    L_0x0157:
        r2 = new java.lang.StringBuilder;
        r5 = "Preload aborted for URL \"";
        r2.<init>(r5);
        r0 = r26;
        r2 = r2.append(r0);
        r5 = "\"";
        r2 = r2.append(r5);
        r2 = r2.toString();
        com.google.android.gms.ads.internal.util.client.zzb.zzaJ(r2);
    L_0x0171:
        r2 = r9.exists();
        if (r2 == 0) goto L_0x0193;
    L_0x0177:
        r2 = r9.delete();
        if (r2 != 0) goto L_0x0193;
    L_0x017d:
        r2 = new java.lang.StringBuilder;
        r5 = "Could not delete partial cache file at ";
        r2.<init>(r5);
        r5 = r9.getAbsolutePath();
        r2 = r2.append(r5);
        r2 = r2.toString();
        com.google.android.gms.ads.internal.util.client.zzb.zzaK(r2);
    L_0x0193:
        r2 = r9.getAbsolutePath();
        r0 = r25;
        r1 = r26;
        r0.zza(r1, r2, r3, r4);
        r2 = zzzX;
        r2.remove(r11);
        r2 = 0;
        goto L_0x0012;
    L_0x01a6:
        r6 = r3.getContentLength();	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        if (r6 >= 0) goto L_0x01d6;
    L_0x01ac:
        r2 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r3 = "Stream cache aborted, missing content-length header at ";
        r2.<init>(r3);	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r0 = r26;
        r2 = r2.append(r0);	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r2 = r2.toString();	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        com.google.android.gms.ads.internal.util.client.zzb.zzaK(r2);	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r2 = r9.getAbsolutePath();	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r3 = "contentLengthMissing";
        r5 = 0;
        r0 = r25;
        r1 = r26;
        r0.zza(r1, r2, r3, r5);	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r2 = zzzX;	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r2.remove(r11);	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r2 = 0;
        goto L_0x0012;
    L_0x01d6:
        r2 = zzzY;	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r12 = (long) r6;	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r5 = r2.format(r12);	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r2 = com.google.android.gms.internal.zzbt.zzvP;	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r2 = r2.get();	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r2 = (java.lang.Integer) r2;	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r12 = r2.intValue();	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        if (r6 <= r12) goto L_0x022d;
    L_0x01eb:
        r2 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r3 = "Content length ";
        r2.<init>(r3);	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r2 = r2.append(r5);	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r3 = " exceeds limit at ";
        r2 = r2.append(r3);	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r0 = r26;
        r2 = r2.append(r0);	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r2 = r2.toString();	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        com.google.android.gms.ads.internal.util.client.zzb.zzaK(r2);	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r2 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r3 = "File too big for full file cache. Size: ";
        r2.<init>(r3);	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r2 = r2.append(r5);	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r2 = r2.toString();	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r3 = r9.getAbsolutePath();	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r5 = "sizeExceeded";
        r0 = r25;
        r1 = r26;
        r0.zza(r1, r3, r5, r2);	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r2 = zzzX;	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r2.remove(r11);	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r2 = 0;
        goto L_0x0012;
    L_0x022d:
        r2 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r7 = "Caching ";
        r2.<init>(r7);	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r2 = r2.append(r5);	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r5 = " bytes from ";
        r2 = r2.append(r5);	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r0 = r26;
        r2 = r2.append(r0);	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r2 = r2.toString();	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        com.google.android.gms.ads.internal.util.client.zzb.zzaI(r2);	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r2 = r3.getInputStream();	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r13 = java.nio.channels.Channels.newChannel(r2);	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r8 = new java.io.FileOutputStream;	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r8.<init>(r9);	 Catch:{ IOException -> 0x03c8, RuntimeException -> 0x0397 }
        r14 = r8.getChannel();	 Catch:{ IOException -> 0x0327, RuntimeException -> 0x03b6 }
        r2 = 1048576; // 0x100000 float:1.469368E-39 double:5.180654E-318;
        r15 = java.nio.ByteBuffer.allocate(r2);	 Catch:{ IOException -> 0x0327, RuntimeException -> 0x03b6 }
        r16 = com.google.android.gms.ads.internal.zzr.zzbG();	 Catch:{ IOException -> 0x0327, RuntimeException -> 0x03b6 }
        r5 = 0;
        r18 = r16.currentTimeMillis();	 Catch:{ IOException -> 0x0327, RuntimeException -> 0x03b6 }
        r17 = new com.google.android.gms.internal.zziz;	 Catch:{ IOException -> 0x0327, RuntimeException -> 0x03b6 }
        r2 = com.google.android.gms.internal.zzbt.zzvS;	 Catch:{ IOException -> 0x0327, RuntimeException -> 0x03b6 }
        r2 = r2.get();	 Catch:{ IOException -> 0x0327, RuntimeException -> 0x03b6 }
        r2 = (java.lang.Long) r2;	 Catch:{ IOException -> 0x0327, RuntimeException -> 0x03b6 }
        r2 = r2.longValue();	 Catch:{ IOException -> 0x0327, RuntimeException -> 0x03b6 }
        r0 = r17;
        r0.<init>(r2);	 Catch:{ IOException -> 0x0327, RuntimeException -> 0x03b6 }
        r2 = com.google.android.gms.internal.zzbt.zzvR;	 Catch:{ IOException -> 0x0327, RuntimeException -> 0x03b6 }
        r2 = r2.get();	 Catch:{ IOException -> 0x0327, RuntimeException -> 0x03b6 }
        r2 = (java.lang.Long) r2;	 Catch:{ IOException -> 0x0327, RuntimeException -> 0x03b6 }
        r20 = r2.longValue();	 Catch:{ IOException -> 0x0327, RuntimeException -> 0x03b6 }
    L_0x028a:
        r2 = r13.read(r15);	 Catch:{ IOException -> 0x0327, RuntimeException -> 0x03b6 }
        if (r2 < 0) goto L_0x0331;
    L_0x0290:
        r5 = r5 + r2;
        if (r5 <= r12) goto L_0x02b7;
    L_0x0293:
        r2 = "sizeExceeded";
        r3 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x030b, RuntimeException -> 0x03c0 }
        r4 = "File too big for full file cache. Size: ";
        r3.<init>(r4);	 Catch:{ IOException -> 0x030b, RuntimeException -> 0x03c0 }
        r4 = java.lang.Integer.toString(r5);	 Catch:{ IOException -> 0x030b, RuntimeException -> 0x03c0 }
        r3 = r3.append(r4);	 Catch:{ IOException -> 0x030b, RuntimeException -> 0x03c0 }
        r3 = r3.toString();	 Catch:{ IOException -> 0x030b, RuntimeException -> 0x03c0 }
        r4 = new java.io.IOException;	 Catch:{ IOException -> 0x02b0, RuntimeException -> 0x02f4 }
        r5 = "stream cache file size limit exceeded";
        r4.<init>(r5);	 Catch:{ IOException -> 0x02b0, RuntimeException -> 0x02f4 }
        throw r4;	 Catch:{ IOException -> 0x02b0, RuntimeException -> 0x02f4 }
    L_0x02b0:
        r4 = move-exception;
        r5 = r4;
        r4 = r3;
        r3 = r2;
        r2 = r8;
        goto L_0x0142;
    L_0x02b7:
        r15.flip();	 Catch:{ IOException -> 0x0327, RuntimeException -> 0x03b6 }
    L_0x02ba:
        r2 = r14.write(r15);	 Catch:{ IOException -> 0x0327, RuntimeException -> 0x03b6 }
        if (r2 > 0) goto L_0x02ba;
    L_0x02c0:
        r15.clear();	 Catch:{ IOException -> 0x0327, RuntimeException -> 0x03b6 }
        r2 = r16.currentTimeMillis();	 Catch:{ IOException -> 0x0327, RuntimeException -> 0x03b6 }
        r2 = r2 - r18;
        r22 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r22 = r22 * r20;
        r2 = (r2 > r22 ? 1 : (r2 == r22 ? 0 : -1));
        if (r2 <= 0) goto L_0x02fb;
    L_0x02d1:
        r2 = "downloadTimeout";
        r3 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x030b, RuntimeException -> 0x03c0 }
        r4 = "Timeout exceeded. Limit: ";
        r3.<init>(r4);	 Catch:{ IOException -> 0x030b, RuntimeException -> 0x03c0 }
        r4 = java.lang.Long.toString(r20);	 Catch:{ IOException -> 0x030b, RuntimeException -> 0x03c0 }
        r3 = r3.append(r4);	 Catch:{ IOException -> 0x030b, RuntimeException -> 0x03c0 }
        r4 = " sec";
        r3 = r3.append(r4);	 Catch:{ IOException -> 0x030b, RuntimeException -> 0x03c0 }
        r3 = r3.toString();	 Catch:{ IOException -> 0x030b, RuntimeException -> 0x03c0 }
        r4 = new java.io.IOException;	 Catch:{ IOException -> 0x02b0, RuntimeException -> 0x02f4 }
        r5 = "stream cache time limit exceeded";
        r4.<init>(r5);	 Catch:{ IOException -> 0x02b0, RuntimeException -> 0x02f4 }
        throw r4;	 Catch:{ IOException -> 0x02b0, RuntimeException -> 0x02f4 }
    L_0x02f4:
        r4 = move-exception;
        r5 = r4;
        r4 = r3;
        r3 = r2;
        r2 = r8;
        goto L_0x0142;
    L_0x02fb:
        r0 = r25;
        r2 = r0.zzAa;	 Catch:{ IOException -> 0x0327, RuntimeException -> 0x03b6 }
        if (r2 == 0) goto L_0x0313;
    L_0x0301:
        r2 = "externalAbort";
        r3 = new java.io.IOException;	 Catch:{ IOException -> 0x030b, RuntimeException -> 0x03c0 }
        r4 = "abort requested";
        r3.<init>(r4);	 Catch:{ IOException -> 0x030b, RuntimeException -> 0x03c0 }
        throw r3;	 Catch:{ IOException -> 0x030b, RuntimeException -> 0x03c0 }
    L_0x030b:
        r4 = move-exception;
        r3 = 0;
        r5 = r4;
        r4 = r3;
        r3 = r2;
        r2 = r8;
        goto L_0x0142;
    L_0x0313:
        r2 = r17.tryAcquire();	 Catch:{ IOException -> 0x0327, RuntimeException -> 0x03b6 }
        if (r2 == 0) goto L_0x028a;
    L_0x0319:
        r4 = r9.getAbsolutePath();	 Catch:{ IOException -> 0x0327, RuntimeException -> 0x03b6 }
        r7 = 0;
        r2 = r25;
        r3 = r26;
        r2.zza(r3, r4, r5, r6, r7);	 Catch:{ IOException -> 0x0327, RuntimeException -> 0x03b6 }
        goto L_0x028a;
    L_0x0327:
        r4 = move-exception;
        r3 = 0;
        r2 = "error";
        r5 = r4;
        r4 = r3;
        r3 = r2;
        r2 = r8;
        goto L_0x0142;
    L_0x0331:
        r8.close();	 Catch:{ IOException -> 0x0327, RuntimeException -> 0x03b6 }
        r2 = 3;
        r2 = com.google.android.gms.ads.internal.util.client.zzb.zzQ(r2);	 Catch:{ IOException -> 0x0327, RuntimeException -> 0x03b6 }
        if (r2 == 0) goto L_0x0360;
    L_0x033b:
        r2 = zzzY;	 Catch:{ IOException -> 0x0327, RuntimeException -> 0x03b6 }
        r6 = (long) r5;	 Catch:{ IOException -> 0x0327, RuntimeException -> 0x03b6 }
        r2 = r2.format(r6);	 Catch:{ IOException -> 0x0327, RuntimeException -> 0x03b6 }
        r3 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0327, RuntimeException -> 0x03b6 }
        r4 = "Preloaded ";
        r3.<init>(r4);	 Catch:{ IOException -> 0x0327, RuntimeException -> 0x03b6 }
        r2 = r3.append(r2);	 Catch:{ IOException -> 0x0327, RuntimeException -> 0x03b6 }
        r3 = " bytes from ";
        r2 = r2.append(r3);	 Catch:{ IOException -> 0x0327, RuntimeException -> 0x03b6 }
        r0 = r26;
        r2 = r2.append(r0);	 Catch:{ IOException -> 0x0327, RuntimeException -> 0x03b6 }
        r2 = r2.toString();	 Catch:{ IOException -> 0x0327, RuntimeException -> 0x03b6 }
        com.google.android.gms.ads.internal.util.client.zzb.zzaI(r2);	 Catch:{ IOException -> 0x0327, RuntimeException -> 0x03b6 }
    L_0x0360:
        r2 = 1;
        r3 = 0;
        r9.setReadable(r2, r3);	 Catch:{ IOException -> 0x0327, RuntimeException -> 0x03b6 }
        zzb(r10);	 Catch:{ IOException -> 0x0327, RuntimeException -> 0x03b6 }
        r2 = r9.getAbsolutePath();	 Catch:{ IOException -> 0x0327, RuntimeException -> 0x03b6 }
        r0 = r25;
        r1 = r26;
        r0.zza(r1, r2, r5);	 Catch:{ IOException -> 0x0327, RuntimeException -> 0x03b6 }
        r2 = zzzX;	 Catch:{ IOException -> 0x0327, RuntimeException -> 0x03b6 }
        r2.remove(r11);	 Catch:{ IOException -> 0x0327, RuntimeException -> 0x03b6 }
        r2 = 1;
        goto L_0x0012;
    L_0x037b:
        r2 = new java.lang.StringBuilder;
        r6 = "Preload failed for URL \"";
        r2.<init>(r6);
        r0 = r26;
        r2 = r2.append(r0);
        r6 = "\"";
        r2 = r2.append(r6);
        r2 = r2.toString();
        com.google.android.gms.ads.internal.util.client.zzb.zzd(r2, r5);
        goto L_0x0171;
    L_0x0397:
        r5 = move-exception;
        r3 = 0;
        r2 = "error";
        r24 = r4;
        r4 = r3;
        r3 = r2;
        r2 = r24;
        goto L_0x0142;
    L_0x03a3:
        r5 = move-exception;
        r3 = 0;
        r24 = r4;
        r4 = r3;
        r3 = r2;
        r2 = r24;
        goto L_0x0142;
    L_0x03ad:
        r5 = move-exception;
        r24 = r4;
        r4 = r3;
        r3 = r2;
        r2 = r24;
        goto L_0x0142;
    L_0x03b6:
        r4 = move-exception;
        r3 = 0;
        r2 = "error";
        r5 = r4;
        r4 = r3;
        r3 = r2;
        r2 = r8;
        goto L_0x0142;
    L_0x03c0:
        r4 = move-exception;
        r3 = 0;
        r5 = r4;
        r4 = r3;
        r3 = r2;
        r2 = r8;
        goto L_0x0142;
    L_0x03c8:
        r5 = move-exception;
        r3 = 0;
        r2 = "error";
        r24 = r4;
        r4 = r3;
        r3 = r2;
        r2 = r24;
        goto L_0x0142;
    L_0x03d4:
        r5 = move-exception;
        r3 = 0;
        r24 = r4;
        r4 = r3;
        r3 = r2;
        r2 = r24;
        goto L_0x0142;
    L_0x03de:
        r2 = move-exception;
        goto L_0x0151;
    L_0x03e1:
        r2 = move-exception;
        goto L_0x0151;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzdu.zzU(java.lang.String):boolean");
    }

    public int zzea() {
        int i = 0;
        if (this.zzzZ != null) {
            for (File name : this.zzzZ.listFiles()) {
                if (!name.getName().endsWith(".done")) {
                    i++;
                }
            }
        }
        return i;
    }

    public boolean zzeb() {
        if (this.zzzZ == null) {
            return false;
        }
        File file = null;
        long j = Long.MAX_VALUE;
        File[] listFiles = this.zzzZ.listFiles();
        int length = listFiles.length;
        int i = 0;
        while (i < length) {
            long j2;
            File file2 = listFiles[i];
            if (!file2.getName().endsWith(".done")) {
                long lastModified = file2.lastModified();
                if (lastModified < j) {
                    j2 = lastModified;
                    j = j2;
                    i++;
                    file = file2;
                }
            }
            file2 = file;
            j2 = j;
            j = j2;
            i++;
            file = file2;
        }
        if (file == null) {
            return false;
        }
        boolean delete = file.delete();
        file2 = zza(file);
        return file2.isFile() ? delete & file2.delete() : delete;
    }
}
