package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.request.AdRequestInfoParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@zzhb
public class zzeu implements zzem {
    private final Context mContext;
    private final zzeo zzCf;
    private final AdRequestInfoParcel zzCu;
    private final long zzCv;
    private final long zzCw;
    private final int zzCx;
    private boolean zzCy = false;
    private final Map<zzjg<zzes>, zzer> zzCz = new HashMap();
    private final Object zzpV = new Object();
    private final zzex zzpn;
    private final boolean zzsA;
    private final boolean zzuS;

    public zzeu(Context context, AdRequestInfoParcel adRequestInfoParcel, zzex com_google_android_gms_internal_zzex, zzeo com_google_android_gms_internal_zzeo, boolean z, boolean z2, long j, long j2, int i) {
        this.mContext = context;
        this.zzCu = adRequestInfoParcel;
        this.zzpn = com_google_android_gms_internal_zzex;
        this.zzCf = com_google_android_gms_internal_zzeo;
        this.zzsA = z;
        this.zzuS = z2;
        this.zzCv = j;
        this.zzCw = j2;
        this.zzCx = i;
    }

    private void zza(final zzjg<zzes> com_google_android_gms_internal_zzjg_com_google_android_gms_internal_zzes) {
        zzir.zzMc.post(new Runnable(this) {
            final /* synthetic */ zzeu zzCB;

            public void run() {
                for (zzjg com_google_android_gms_internal_zzjg : this.zzCB.zzCz.keySet()) {
                    if (com_google_android_gms_internal_zzjg != com_google_android_gms_internal_zzjg_com_google_android_gms_internal_zzes) {
                        ((zzer) this.zzCB.zzCz.get(com_google_android_gms_internal_zzjg)).cancel();
                    }
                }
            }
        });
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.google.android.gms.internal.zzes zzd(java.util.List<com.google.android.gms.internal.zzjg<com.google.android.gms.internal.zzes>> r5) {
        /*
        r4 = this;
        r1 = r4.zzpV;
        monitor-enter(r1);
        r0 = r4.zzCy;	 Catch:{ all -> 0x0038 }
        if (r0 == 0) goto L_0x000f;
    L_0x0007:
        r0 = new com.google.android.gms.internal.zzes;	 Catch:{ all -> 0x0038 }
        r2 = -1;
        r0.<init>(r2);	 Catch:{ all -> 0x0038 }
        monitor-exit(r1);	 Catch:{ all -> 0x0038 }
    L_0x000e:
        return r0;
    L_0x000f:
        monitor-exit(r1);	 Catch:{ all -> 0x0038 }
        r2 = r5.iterator();
    L_0x0014:
        r0 = r2.hasNext();
        if (r0 == 0) goto L_0x003b;
    L_0x001a:
        r0 = r2.next();
        r1 = r0;
        r1 = (com.google.android.gms.internal.zzjg) r1;
        r0 = r1.get();	 Catch:{ InterruptedException -> 0x0031, ExecutionException -> 0x0046 }
        r0 = (com.google.android.gms.internal.zzes) r0;	 Catch:{ InterruptedException -> 0x0031, ExecutionException -> 0x0046 }
        if (r0 == 0) goto L_0x0014;
    L_0x0029:
        r3 = r0.zzCo;	 Catch:{ InterruptedException -> 0x0031, ExecutionException -> 0x0046 }
        if (r3 != 0) goto L_0x0014;
    L_0x002d:
        r4.zza(r1);	 Catch:{ InterruptedException -> 0x0031, ExecutionException -> 0x0046 }
        goto L_0x000e;
    L_0x0031:
        r0 = move-exception;
    L_0x0032:
        r1 = "Exception while processing an adapter; continuing with other adapters";
        com.google.android.gms.ads.internal.util.client.zzb.zzd(r1, r0);
        goto L_0x0014;
    L_0x0038:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0038 }
        throw r0;
    L_0x003b:
        r0 = 0;
        r4.zza(r0);
        r0 = new com.google.android.gms.internal.zzes;
        r1 = 1;
        r0.<init>(r1);
        goto L_0x000e;
    L_0x0046:
        r0 = move-exception;
        goto L_0x0032;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzeu.zzd(java.util.List):com.google.android.gms.internal.zzes");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.google.android.gms.internal.zzes zze(java.util.List<com.google.android.gms.internal.zzjg<com.google.android.gms.internal.zzes>> r15) {
        /*
        r14 = this;
        r2 = 0;
        r3 = -1;
        r12 = 0;
        r1 = r14.zzpV;
        monitor-enter(r1);
        r0 = r14.zzCy;	 Catch:{ all -> 0x0076 }
        if (r0 == 0) goto L_0x0013;
    L_0x000b:
        r2 = new com.google.android.gms.internal.zzes;	 Catch:{ all -> 0x0076 }
        r0 = -1;
        r2.<init>(r0);	 Catch:{ all -> 0x0076 }
        monitor-exit(r1);	 Catch:{ all -> 0x0076 }
    L_0x0012:
        return r2;
    L_0x0013:
        monitor-exit(r1);	 Catch:{ all -> 0x0076 }
        r0 = r14.zzCf;
        r0 = r0.zzBY;
        r4 = -1;
        r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1));
        if (r0 == 0) goto L_0x0079;
    L_0x001e:
        r0 = r14.zzCf;
        r0 = r0.zzBY;
    L_0x0022:
        r8 = r15.iterator();
        r4 = r0;
        r6 = r3;
        r3 = r2;
    L_0x0029:
        r0 = r8.hasNext();
        if (r0 == 0) goto L_0x00ad;
    L_0x002f:
        r0 = r8.next();
        r0 = (com.google.android.gms.internal.zzjg) r0;
        r1 = com.google.android.gms.ads.internal.zzr.zzbG();
        r10 = r1.currentTimeMillis();
        r1 = (r4 > r12 ? 1 : (r4 == r12 ? 0 : -1));
        if (r1 != 0) goto L_0x007c;
    L_0x0041:
        r1 = r0.isDone();	 Catch:{ InterruptedException -> 0x00c0, ExecutionException -> 0x00be, RemoteException -> 0x00c2, TimeoutException -> 0x0086 }
        if (r1 == 0) goto L_0x007c;
    L_0x0047:
        r1 = r0.get();	 Catch:{ InterruptedException -> 0x00c0, ExecutionException -> 0x00be, RemoteException -> 0x00c2, TimeoutException -> 0x0086 }
        r1 = (com.google.android.gms.internal.zzes) r1;	 Catch:{ InterruptedException -> 0x00c0, ExecutionException -> 0x00be, RemoteException -> 0x00c2, TimeoutException -> 0x0086 }
        r7 = r1;
    L_0x004e:
        if (r7 == 0) goto L_0x00ba;
    L_0x0050:
        r1 = r7.zzCo;	 Catch:{ InterruptedException -> 0x00c0, ExecutionException -> 0x00be, RemoteException -> 0x00c2, TimeoutException -> 0x0086 }
        if (r1 != 0) goto L_0x00ba;
    L_0x0054:
        r1 = r7.zzCt;	 Catch:{ InterruptedException -> 0x00c0, ExecutionException -> 0x00be, RemoteException -> 0x00c2, TimeoutException -> 0x0086 }
        if (r1 == 0) goto L_0x00ba;
    L_0x0058:
        r9 = r1.zzeD();	 Catch:{ InterruptedException -> 0x00c0, ExecutionException -> 0x00be, RemoteException -> 0x00c2, TimeoutException -> 0x0086 }
        if (r9 <= r6) goto L_0x00ba;
    L_0x005e:
        r1 = r1.zzeD();	 Catch:{ InterruptedException -> 0x00c0, ExecutionException -> 0x00be, RemoteException -> 0x00c2, TimeoutException -> 0x0086 }
        r2 = r0;
        r0 = r7;
    L_0x0064:
        r3 = com.google.android.gms.ads.internal.zzr.zzbG();
        r6 = r3.currentTimeMillis();
        r6 = r6 - r10;
        r4 = r4 - r6;
        r4 = java.lang.Math.max(r4, r12);
        r3 = r2;
        r6 = r1;
        r2 = r0;
        goto L_0x0029;
    L_0x0076:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0076 }
        throw r0;
    L_0x0079:
        r0 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;
        goto L_0x0022;
    L_0x007c:
        r1 = java.util.concurrent.TimeUnit.MILLISECONDS;	 Catch:{ InterruptedException -> 0x00c0, ExecutionException -> 0x00be, RemoteException -> 0x00c2, TimeoutException -> 0x0086 }
        r1 = r0.get(r4, r1);	 Catch:{ InterruptedException -> 0x00c0, ExecutionException -> 0x00be, RemoteException -> 0x00c2, TimeoutException -> 0x0086 }
        r1 = (com.google.android.gms.internal.zzes) r1;	 Catch:{ InterruptedException -> 0x00c0, ExecutionException -> 0x00be, RemoteException -> 0x00c2, TimeoutException -> 0x0086 }
        r7 = r1;
        goto L_0x004e;
    L_0x0086:
        r0 = move-exception;
    L_0x0087:
        r1 = "Exception while processing an adapter; continuing with other adapters";
        com.google.android.gms.ads.internal.util.client.zzb.zzd(r1, r0);	 Catch:{ all -> 0x009d }
        r0 = com.google.android.gms.ads.internal.zzr.zzbG();
        r0 = r0.currentTimeMillis();
        r0 = r0 - r10;
        r0 = r4 - r0;
        r0 = java.lang.Math.max(r0, r12);
        r4 = r0;
        goto L_0x0029;
    L_0x009d:
        r0 = move-exception;
        r1 = com.google.android.gms.ads.internal.zzr.zzbG();
        r2 = r1.currentTimeMillis();
        r2 = r2 - r10;
        r2 = r4 - r2;
        java.lang.Math.max(r2, r12);
        throw r0;
    L_0x00ad:
        r14.zza(r3);
        if (r2 != 0) goto L_0x0012;
    L_0x00b2:
        r2 = new com.google.android.gms.internal.zzes;
        r0 = 1;
        r2.<init>(r0);
        goto L_0x0012;
    L_0x00ba:
        r1 = r6;
        r0 = r2;
        r2 = r3;
        goto L_0x0064;
    L_0x00be:
        r0 = move-exception;
        goto L_0x0087;
    L_0x00c0:
        r0 = move-exception;
        goto L_0x0087;
    L_0x00c2:
        r0 = move-exception;
        goto L_0x0087;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzeu.zze(java.util.List):com.google.android.gms.internal.zzes");
    }

    public void cancel() {
        synchronized (this.zzpV) {
            this.zzCy = true;
            for (zzer cancel : this.zzCz.values()) {
                cancel.cancel();
            }
        }
    }

    public zzes zzc(List<zzen> list) {
        zzb.zzaI("Starting mediation.");
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        List arrayList = new ArrayList();
        for (zzen com_google_android_gms_internal_zzen : list) {
            zzb.zzaJ("Trying mediation network: " + com_google_android_gms_internal_zzen.zzBA);
            for (String com_google_android_gms_internal_zzer : com_google_android_gms_internal_zzen.zzBB) {
                final zzer com_google_android_gms_internal_zzer2 = new zzer(this.mContext, com_google_android_gms_internal_zzer, this.zzpn, this.zzCf, com_google_android_gms_internal_zzen, this.zzCu.zzHt, this.zzCu.zzrp, this.zzCu.zzrl, this.zzsA, this.zzuS, this.zzCu.zzrD, this.zzCu.zzrH);
                zzjg zza = zziq.zza(newCachedThreadPool, new Callable<zzes>(this) {
                    final /* synthetic */ zzeu zzCB;

                    public /* synthetic */ Object call() throws Exception {
                        return zzeE();
                    }

                    public zzes zzeE() throws Exception {
                        synchronized (this.zzCB.zzpV) {
                            if (this.zzCB.zzCy) {
                                return null;
                            }
                            return com_google_android_gms_internal_zzer2.zza(this.zzCB.zzCv, this.zzCB.zzCw);
                        }
                    }
                });
                this.zzCz.put(zza, com_google_android_gms_internal_zzer2);
                arrayList.add(zza);
            }
        }
        switch (this.zzCx) {
            case 2:
                return zze(arrayList);
            default:
                return zzd(arrayList);
        }
    }
}
