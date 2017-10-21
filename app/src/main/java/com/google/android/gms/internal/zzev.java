package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.request.AdRequestInfoParcel;

@zzhb
public class zzev implements zzem {
    private final Context mContext;
    private zzer zzCD;
    private final zzeo zzCf;
    private final AdRequestInfoParcel zzCu;
    private final long zzCv;
    private final long zzCw;
    private boolean zzCy = false;
    private final Object zzpV = new Object();
    private final zzcb zzpe;
    private final zzex zzpn;
    private final boolean zzsA;
    private final boolean zzuS;

    public zzev(Context context, AdRequestInfoParcel adRequestInfoParcel, zzex com_google_android_gms_internal_zzex, zzeo com_google_android_gms_internal_zzeo, boolean z, boolean z2, long j, long j2, zzcb com_google_android_gms_internal_zzcb) {
        this.mContext = context;
        this.zzCu = adRequestInfoParcel;
        this.zzpn = com_google_android_gms_internal_zzex;
        this.zzCf = com_google_android_gms_internal_zzeo;
        this.zzsA = z;
        this.zzuS = z2;
        this.zzCv = j;
        this.zzCw = j2;
        this.zzpe = com_google_android_gms_internal_zzcb;
    }

    public void cancel() {
        synchronized (this.zzpV) {
            this.zzCy = true;
            if (this.zzCD != null) {
                this.zzCD.cancel();
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.android.gms.internal.zzes zzc(java.util.List<com.google.android.gms.internal.zzen> r22) {
        /*
        r21 = this;
        r2 = "Starting mediation.";
        com.google.android.gms.ads.internal.util.client.zzb.zzaI(r2);
        r15 = new java.util.ArrayList;
        r15.<init>();
        r0 = r21;
        r2 = r0.zzpe;
        r16 = r2.zzdB();
        r17 = r22.iterator();
    L_0x0016:
        r2 = r17.hasNext();
        if (r2 == 0) goto L_0x0125;
    L_0x001c:
        r7 = r17.next();
        r7 = (com.google.android.gms.internal.zzen) r7;
        r2 = new java.lang.StringBuilder;
        r3 = "Trying mediation network: ";
        r2.<init>(r3);
        r3 = r7.zzBA;
        r2 = r2.append(r3);
        r2 = r2.toString();
        com.google.android.gms.ads.internal.util.client.zzb.zzaJ(r2);
        r2 = r7.zzBB;
        r18 = r2.iterator();
    L_0x003c:
        r2 = r18.hasNext();
        if (r2 == 0) goto L_0x0016;
    L_0x0042:
        r4 = r18.next();
        r4 = (java.lang.String) r4;
        r0 = r21;
        r2 = r0.zzpe;
        r19 = r2.zzdB();
        r0 = r21;
        r0 = r0.zzpV;
        r20 = r0;
        monitor-enter(r20);
        r0 = r21;
        r2 = r0.zzCy;	 Catch:{ all -> 0x00fc }
        if (r2 == 0) goto L_0x0065;
    L_0x005d:
        r2 = new com.google.android.gms.internal.zzes;	 Catch:{ all -> 0x00fc }
        r3 = -1;
        r2.<init>(r3);	 Catch:{ all -> 0x00fc }
        monitor-exit(r20);	 Catch:{ all -> 0x00fc }
    L_0x0064:
        return r2;
    L_0x0065:
        r2 = new com.google.android.gms.internal.zzer;	 Catch:{ all -> 0x00fc }
        r0 = r21;
        r3 = r0.mContext;	 Catch:{ all -> 0x00fc }
        r0 = r21;
        r5 = r0.zzpn;	 Catch:{ all -> 0x00fc }
        r0 = r21;
        r6 = r0.zzCf;	 Catch:{ all -> 0x00fc }
        r0 = r21;
        r8 = r0.zzCu;	 Catch:{ all -> 0x00fc }
        r8 = r8.zzHt;	 Catch:{ all -> 0x00fc }
        r0 = r21;
        r9 = r0.zzCu;	 Catch:{ all -> 0x00fc }
        r9 = r9.zzrp;	 Catch:{ all -> 0x00fc }
        r0 = r21;
        r10 = r0.zzCu;	 Catch:{ all -> 0x00fc }
        r10 = r10.zzrl;	 Catch:{ all -> 0x00fc }
        r0 = r21;
        r11 = r0.zzsA;	 Catch:{ all -> 0x00fc }
        r0 = r21;
        r12 = r0.zzuS;	 Catch:{ all -> 0x00fc }
        r0 = r21;
        r13 = r0.zzCu;	 Catch:{ all -> 0x00fc }
        r13 = r13.zzrD;	 Catch:{ all -> 0x00fc }
        r0 = r21;
        r14 = r0.zzCu;	 Catch:{ all -> 0x00fc }
        r14 = r14.zzrH;	 Catch:{ all -> 0x00fc }
        r2.<init>(r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14);	 Catch:{ all -> 0x00fc }
        r0 = r21;
        r0.zzCD = r2;	 Catch:{ all -> 0x00fc }
        monitor-exit(r20);	 Catch:{ all -> 0x00fc }
        r0 = r21;
        r2 = r0.zzCD;
        r0 = r21;
        r8 = r0.zzCv;
        r0 = r21;
        r10 = r0.zzCw;
        r2 = r2.zza(r8, r10);
        r3 = r2.zzCo;
        if (r3 != 0) goto L_0x00ff;
    L_0x00b5:
        r3 = "Adapter succeeded.";
        com.google.android.gms.ads.internal.util.client.zzb.zzaI(r3);
        r0 = r21;
        r3 = r0.zzpe;
        r5 = "mediation_network_succeed";
        r3.zzc(r5, r4);
        r3 = r15.isEmpty();
        if (r3 != 0) goto L_0x00d8;
    L_0x00c9:
        r0 = r21;
        r3 = r0.zzpe;
        r4 = "mediation_networks_fail";
        r5 = ",";
        r5 = android.text.TextUtils.join(r5, r15);
        r3.zzc(r4, r5);
    L_0x00d8:
        r0 = r21;
        r3 = r0.zzpe;
        r4 = 1;
        r4 = new java.lang.String[r4];
        r5 = 0;
        r6 = "mls";
        r4[r5] = r6;
        r0 = r19;
        r3.zza(r0, r4);
        r0 = r21;
        r3 = r0.zzpe;
        r4 = 1;
        r4 = new java.lang.String[r4];
        r5 = 0;
        r6 = "ttm";
        r4[r5] = r6;
        r0 = r16;
        r3.zza(r0, r4);
        goto L_0x0064;
    L_0x00fc:
        r2 = move-exception;
        monitor-exit(r20);	 Catch:{ all -> 0x00fc }
        throw r2;
    L_0x00ff:
        r15.add(r4);
        r0 = r21;
        r3 = r0.zzpe;
        r4 = 1;
        r4 = new java.lang.String[r4];
        r5 = 0;
        r6 = "mlf";
        r4[r5] = r6;
        r0 = r19;
        r3.zza(r0, r4);
        r3 = r2.zzCq;
        if (r3 == 0) goto L_0x003c;
    L_0x0117:
        r3 = com.google.android.gms.internal.zzir.zzMc;
        r4 = new com.google.android.gms.internal.zzev$1;
        r0 = r21;
        r4.<init>(r0, r2);
        r3.post(r4);
        goto L_0x003c;
    L_0x0125:
        r2 = r15.isEmpty();
        if (r2 != 0) goto L_0x013a;
    L_0x012b:
        r0 = r21;
        r2 = r0.zzpe;
        r3 = "mediation_networks_fail";
        r4 = ",";
        r4 = android.text.TextUtils.join(r4, r15);
        r2.zzc(r3, r4);
    L_0x013a:
        r2 = new com.google.android.gms.internal.zzes;
        r3 = 1;
        r2.<init>(r3);
        goto L_0x0064;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzev.zzc(java.util.List):com.google.android.gms.internal.zzes");
    }
}
