package com.google.android.gms.internal;

import android.content.Context;
import com.google.ads.afma.nano.NanoAfmaSignals.AFMASignals;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class zzam extends zzal {
    private static AdvertisingIdClient zzok = null;
    private static CountDownLatch zzol = new CountDownLatch(1);
    private boolean zzom;

    class zza {
        private String zzon;
        private boolean zzoo;
        final /* synthetic */ zzam zzop;

        public zza(zzam com_google_android_gms_internal_zzam, String str, boolean z) {
            this.zzop = com_google_android_gms_internal_zzam;
            this.zzon = str;
            this.zzoo = z;
        }

        public String getId() {
            return this.zzon;
        }

        public boolean isLimitAdTrackingEnabled() {
            return this.zzoo;
        }
    }

    static final class zzb implements Runnable {
        private Context zzoq;

        public zzb(Context context) {
            this.zzoq = context.getApplicationContext();
            if (this.zzoq == null) {
                this.zzoq = context;
            }
        }

        public final void run() {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Exception block dominator not found, method:com.google.android.gms.internal.zzam.zzb.run():void. bs: [B:6:0x001a, B:13:0x0027]
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:86)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r2 = this;
            r0 = com.google.android.gms.internal.zzam.class;
            monitor-enter(r0);
            r0 = com.google.android.gms.internal.zzam.zzok;	 Catch:{ GooglePlayServicesNotAvailableException -> 0x0040, IOException -> 0x0025, GooglePlayServicesRepairableException -> 0x0042 }
            if (r0 != 0) goto L_0x001a;	 Catch:{ GooglePlayServicesNotAvailableException -> 0x0040, IOException -> 0x0025, GooglePlayServicesRepairableException -> 0x0042 }
        L_0x0009:
            r0 = 1;	 Catch:{ GooglePlayServicesNotAvailableException -> 0x0040, IOException -> 0x0025, GooglePlayServicesRepairableException -> 0x0042 }
            com.google.android.gms.ads.identifier.AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(r0);	 Catch:{ GooglePlayServicesNotAvailableException -> 0x0040, IOException -> 0x0025, GooglePlayServicesRepairableException -> 0x0042 }
            r0 = new com.google.android.gms.ads.identifier.AdvertisingIdClient;	 Catch:{ GooglePlayServicesNotAvailableException -> 0x0040, IOException -> 0x0025, GooglePlayServicesRepairableException -> 0x0042 }
            r1 = r2.zzoq;	 Catch:{ GooglePlayServicesNotAvailableException -> 0x0040, IOException -> 0x0025, GooglePlayServicesRepairableException -> 0x0042 }
            r0.<init>(r1);	 Catch:{ GooglePlayServicesNotAvailableException -> 0x0040, IOException -> 0x0025, GooglePlayServicesRepairableException -> 0x0042 }
            r0.start();	 Catch:{ GooglePlayServicesNotAvailableException -> 0x0040, IOException -> 0x0025, GooglePlayServicesRepairableException -> 0x0042 }
            com.google.android.gms.internal.zzam.zzok = r0;	 Catch:{ GooglePlayServicesNotAvailableException -> 0x0040, IOException -> 0x0025, GooglePlayServicesRepairableException -> 0x0042 }
        L_0x001a:
            r0 = com.google.android.gms.internal.zzam.zzol;	 Catch:{ all -> 0x0032 }
            r0.countDown();	 Catch:{ all -> 0x0032 }
        L_0x0021:
            r0 = com.google.android.gms.internal.zzam.class;	 Catch:{ all -> 0x0032 }
            monitor-exit(r0);	 Catch:{ all -> 0x0032 }
            return;
        L_0x0025:
            r0 = move-exception;
        L_0x0026:
            r0 = 0;
            com.google.android.gms.internal.zzam.zzok = r0;	 Catch:{ all -> 0x0037 }
            r0 = com.google.android.gms.internal.zzam.zzol;	 Catch:{ all -> 0x0032 }
            r0.countDown();	 Catch:{ all -> 0x0032 }
            goto L_0x0021;	 Catch:{ all -> 0x0032 }
        L_0x0032:
            r0 = move-exception;	 Catch:{ all -> 0x0032 }
            r1 = com.google.android.gms.internal.zzam.class;	 Catch:{ all -> 0x0032 }
            monitor-exit(r1);	 Catch:{ all -> 0x0032 }
            throw r0;
        L_0x0037:
            r0 = move-exception;
            r1 = com.google.android.gms.internal.zzam.zzol;	 Catch:{ all -> 0x0032 }
            r1.countDown();	 Catch:{ all -> 0x0032 }
            throw r0;	 Catch:{ all -> 0x0032 }
        L_0x0040:
            r0 = move-exception;
            goto L_0x0026;
        L_0x0042:
            r0 = move-exception;
            goto L_0x0026;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzam.zzb.run():void");
        }
    }

    protected zzam(Context context, zzap com_google_android_gms_internal_zzap, boolean z) {
        super(context, com_google_android_gms_internal_zzap);
        this.zzom = z;
    }

    public static zzam zza(String str, Context context, boolean z) {
        zzap com_google_android_gms_internal_zzah = new zzah();
        zzal.zza(str, context, com_google_android_gms_internal_zzah);
        if (z) {
            synchronized (zzam.class) {
                try {
                    if (zzok == null) {
                        new Thread(new zzb(context)).start();
                    }
                } catch (Throwable th) {
                    while (true) {
                        Class cls = zzam.class;
                    }
                }
            }
        }
        return new zzam(context, com_google_android_gms_internal_zzah, z);
    }

    private void zza(Context context, AFMASignals aFMASignals) {
        if (this.zzom) {
            try {
                if (zzS()) {
                    zza zzY = zzY();
                    String id = zzY.getId();
                    if (id != null) {
                        aFMASignals.didOptOut = Boolean.valueOf(zzY.isLimitAdTrackingEnabled());
                        aFMASignals.didSignalType = Integer.valueOf(5);
                        aFMASignals.didSignal = id;
                        zzal.zza(28, zzob);
                        return;
                    }
                    return;
                }
                aFMASignals.didSignal = zzal.zzf(context);
                zzal.zza(24, zzob);
            } catch (IOException e) {
            } catch (zza e2) {
            }
        }
    }

    zza zzY() throws IOException {
        try {
            if (!zzol.await(2, TimeUnit.SECONDS)) {
                return new zza(this, null, false);
            }
            Info info;
            synchronized (zzam.class) {
                String str;
                try {
                    if (zzok == null) {
                        str = null;
                        zza com_google_android_gms_internal_zzam_zza = new zza(this, null, false);
                        return com_google_android_gms_internal_zzam_zza;
                    }
                    info = zzok.getInfo();
                } finally {
                    str = zzam.class;
                }
            }
            return new zza(this, zzk(info.getId()), info.isLimitAdTrackingEnabled());
        } catch (InterruptedException e) {
            return new zza(this, null, false);
        }
    }

    protected AFMASignals zzc(Context context) {
        AFMASignals zzc = super.zzc(context);
        zza(context, zzc);
        return zzc;
    }
}
