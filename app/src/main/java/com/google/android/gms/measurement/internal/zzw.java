package com.google.android.gms.measurement.internal;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.WorkerThread;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Pair;
import com.ad4screen.sdk.analytics.Lead;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzmq;
import com.google.android.gms.internal.zzqa;
import com.google.android.gms.internal.zzqb.zzb;
import com.google.android.gms.internal.zzqb.zzd;
import com.google.android.gms.internal.zzqb.zze;
import com.google.android.gms.internal.zzqb.zzg;
import com.google.android.gms.measurement.AppMeasurement;
import com.google.android.gms.measurement.AppMeasurementReceiver;
import com.google.android.gms.measurement.AppMeasurementService;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class zzw {
    private static zzaa zzaXV;
    private static volatile zzw zzaXW;
    private final Context mContext;
    private final boolean zzQk;
    private final zzd zzaXX;
    private final zzt zzaXY;
    private final zzp zzaXZ;
    private final zzv zzaYa;
    private final zzad zzaYb;
    private final zzu zzaYc;
    private final AppMeasurement zzaYd;
    private final zzaj zzaYe;
    private final zze zzaYf;
    private final zzq zzaYg;
    private final zzac zzaYh;
    private final zzg zzaYi;
    private final zzab zzaYj;
    private final zzn zzaYk;
    private final zzr zzaYl;
    private final zzag zzaYm;
    private final zzc zzaYn;
    private Boolean zzaYo;
    private List<Long> zzaYp;
    private int zzaYq;
    private int zzaYr;
    private final zzmq zzqW;

    class zza implements zzb {
        final /* synthetic */ zzw zzaYs;
        zze zzaYt;
        List<Long> zzaYu;
        long zzaYv;
        List<zzb> zzpH;

        private zza(zzw com_google_android_gms_measurement_internal_zzw) {
            this.zzaYs = com_google_android_gms_measurement_internal_zzw;
        }

        private long zza(zzb com_google_android_gms_internal_zzqb_zzb) {
            return ((com_google_android_gms_internal_zzqb_zzb.zzbaf.longValue() / 1000) / 60) / 60;
        }

        boolean isEmpty() {
            return this.zzpH == null || this.zzpH.isEmpty();
        }

        public boolean zza(long j, zzb com_google_android_gms_internal_zzqb_zzb) {
            zzx.zzz(com_google_android_gms_internal_zzqb_zzb);
            if (this.zzpH == null) {
                this.zzpH = new ArrayList();
            }
            if (this.zzaYu == null) {
                this.zzaYu = new ArrayList();
            }
            if (this.zzpH.size() > 0 && zza((zzb) this.zzpH.get(0)) != zza(com_google_android_gms_internal_zzqb_zzb)) {
                return false;
            }
            long serializedSize = this.zzaYv + ((long) com_google_android_gms_internal_zzqb_zzb.getSerializedSize());
            if (serializedSize >= ((long) this.zzaYs.zzCp().zzBT())) {
                return false;
            }
            this.zzaYv = serializedSize;
            this.zzpH.add(com_google_android_gms_internal_zzqb_zzb);
            this.zzaYu.add(Long.valueOf(j));
            return this.zzpH.size() < this.zzaYs.zzCp().zzBU();
        }

        public void zzc(zze com_google_android_gms_internal_zzqb_zze) {
            zzx.zzz(com_google_android_gms_internal_zzqb_zze);
            this.zzaYt = com_google_android_gms_internal_zzqb_zze;
        }
    }

    zzw(zzaa com_google_android_gms_measurement_internal_zzaa) {
        zzx.zzz(com_google_android_gms_measurement_internal_zzaa);
        this.mContext = com_google_android_gms_measurement_internal_zzaa.mContext;
        this.zzqW = com_google_android_gms_measurement_internal_zzaa.zzl(this);
        this.zzaXX = com_google_android_gms_measurement_internal_zzaa.zza(this);
        zzt zzb = com_google_android_gms_measurement_internal_zzaa.zzb(this);
        zzb.zza();
        this.zzaXY = zzb;
        zzp zzc = com_google_android_gms_measurement_internal_zzaa.zzc(this);
        zzc.zza();
        this.zzaXZ = zzc;
        zzAo().zzCI().zzj("App measurement is starting up, version", Long.valueOf(zzCp().zzBp()));
        zzAo().zzCI().zzfg("To enable debug logging run: adb shell setprop log.tag.GMPM VERBOSE");
        zzAo().zzCJ().zzfg("Debug logging enabled");
        this.zzaYe = com_google_android_gms_measurement_internal_zzaa.zzi(this);
        zzg zzn = com_google_android_gms_measurement_internal_zzaa.zzn(this);
        zzn.zza();
        this.zzaYi = zzn;
        zzn zzo = com_google_android_gms_measurement_internal_zzaa.zzo(this);
        zzo.zza();
        this.zzaYk = zzo;
        zze zzj = com_google_android_gms_measurement_internal_zzaa.zzj(this);
        zzj.zza();
        this.zzaYf = zzj;
        zzc zzr = com_google_android_gms_measurement_internal_zzaa.zzr(this);
        zzr.zza();
        this.zzaYn = zzr;
        zzq zzk = com_google_android_gms_measurement_internal_zzaa.zzk(this);
        zzk.zza();
        this.zzaYg = zzk;
        zzac zzm = com_google_android_gms_measurement_internal_zzaa.zzm(this);
        zzm.zza();
        this.zzaYh = zzm;
        zzab zzh = com_google_android_gms_measurement_internal_zzaa.zzh(this);
        zzh.zza();
        this.zzaYj = zzh;
        zzag zzq = com_google_android_gms_measurement_internal_zzaa.zzq(this);
        zzq.zza();
        this.zzaYm = zzq;
        this.zzaYl = com_google_android_gms_measurement_internal_zzaa.zzp(this);
        this.zzaYd = com_google_android_gms_measurement_internal_zzaa.zzg(this);
        zzad zze = com_google_android_gms_measurement_internal_zzaa.zze(this);
        zze.zza();
        this.zzaYb = zze;
        zzu zzf = com_google_android_gms_measurement_internal_zzaa.zzf(this);
        zzf.zza();
        this.zzaYc = zzf;
        zzv zzd = com_google_android_gms_measurement_internal_zzaa.zzd(this);
        zzd.zza();
        this.zzaYa = zzd;
        if (this.zzaYq != this.zzaYr) {
            zzAo().zzCE().zze("Not all components initialized", Integer.valueOf(this.zzaYq), Integer.valueOf(this.zzaYr));
        }
        this.zzQk = true;
        if (!(this.zzaXX.zzkr() || zzCZ())) {
            if (!(this.mContext.getApplicationContext() instanceof Application)) {
                zzAo().zzCF().zzfg("Application context is not an Application");
            } else if (VERSION.SDK_INT >= 14) {
                zzCf().zzDk();
            } else {
                zzAo().zzCJ().zzfg("Not tracking deep linking pre-ICS");
            }
        }
        this.zzaYa.zzg(new Runnable(this) {
            final /* synthetic */ zzw zzaYs;

            {
                this.zzaYs = r1;
            }

            public void run() {
                this.zzaYs.start();
            }
        });
    }

    private void zzA(List<Long> list) {
        zzx.zzac(!list.isEmpty());
        if (this.zzaYp != null) {
            zzAo().zzCE().zzfg("Set uploading progress before finishing the previous upload");
        } else {
            this.zzaYp = new ArrayList(list);
        }
    }

    @WorkerThread
    private boolean zzDb() {
        zzjk();
        return this.zzaYp != null;
    }

    private boolean zzDd() {
        zzjk();
        zzjv();
        return zzCj().zzCv() || !TextUtils.isEmpty(zzCj().zzCq());
    }

    @WorkerThread
    private void zzDe() {
        zzjk();
        zzjv();
        if (zzCS() && zzDd()) {
            long zzDf = zzDf();
            if (zzDf == 0) {
                zzCX().unregister();
                zzCY().cancel();
                return;
            } else if (zzCW().zzlB()) {
                long j = zzCo().zzaXl.get();
                long zzBX = zzCp().zzBX();
                if (!zzCk().zzc(j, zzBX)) {
                    zzDf = Math.max(zzDf, j + zzBX);
                }
                zzCX().unregister();
                zzDf -= zzjl().currentTimeMillis();
                if (zzDf <= 0) {
                    zzCY().zzt(1);
                    return;
                }
                zzAo().zzCK().zzj("Upload scheduled in approximately ms", Long.valueOf(zzDf));
                zzCY().zzt(zzDf);
                return;
            } else {
                zzCX().zzly();
                zzCY().cancel();
                return;
            }
        }
        zzCX().unregister();
        zzCY().cancel();
    }

    private long zzDf() {
        long currentTimeMillis = zzjl().currentTimeMillis();
        long zzCa = zzCp().zzCa();
        long zzBY = zzCp().zzBY();
        long j = zzCo().zzaXj.get();
        long j2 = zzCo().zzaXk.get();
        long max = Math.max(zzCj().zzCt(), zzCj().zzCu());
        if (max == 0) {
            return 0;
        }
        max = currentTimeMillis - Math.abs(max - currentTimeMillis);
        j2 = currentTimeMillis - Math.abs(j2 - currentTimeMillis);
        j = Math.max(currentTimeMillis - Math.abs(j - currentTimeMillis), j2);
        currentTimeMillis = max + zzCa;
        if (!zzCk().zzc(j, zzBY)) {
            currentTimeMillis = j + zzBY;
        }
        if (j2 == 0 || j2 < max) {
            return currentTimeMillis;
        }
        for (int i = 0; i < zzCp().zzCc(); i++) {
            currentTimeMillis += ((long) (1 << i)) * zzCp().zzCb();
            if (currentTimeMillis > j2) {
                return currentTimeMillis;
            }
        }
        return 0;
    }

    @WorkerThread
    private void zza(int i, Throwable th, byte[] bArr) {
        int i2 = 0;
        zzjk();
        zzjv();
        if (bArr == null) {
            bArr = new byte[0];
        }
        List<Long> list = this.zzaYp;
        this.zzaYp = null;
        if ((i == 200 || i == 204) && th == null) {
            zzCo().zzaXj.set(zzjl().currentTimeMillis());
            zzCo().zzaXk.set(0);
            zzDe();
            zzAo().zzCK().zze("Successful upload. Got network response. code, size", Integer.valueOf(i), Integer.valueOf(bArr.length));
            zzCj().beginTransaction();
            try {
                for (Long longValue : list) {
                    zzCj().zzZ(longValue.longValue());
                }
                zzCj().setTransactionSuccessful();
                if (zzCW().zzlB() && zzDd()) {
                    zzDc();
                } else {
                    zzDe();
                }
            } finally {
                zzCj().endTransaction();
            }
        } else {
            zzAo().zzCK().zze("Network upload failed. Will retry later. code, error", Integer.valueOf(i), th);
            zzCo().zzaXk.set(zzjl().currentTimeMillis());
            if (i == 503 || i == 429) {
                i2 = 1;
            }
            if (i2 != 0) {
                zzCo().zzaXl.set(zzjl().currentTimeMillis());
            }
            zzDe();
        }
    }

    private void zza(zzy com_google_android_gms_measurement_internal_zzy) {
        if (com_google_android_gms_measurement_internal_zzy == null) {
            throw new IllegalStateException("Component not created");
        }
    }

    private void zza(zzz com_google_android_gms_measurement_internal_zzz) {
        if (com_google_android_gms_measurement_internal_zzz == null) {
            throw new IllegalStateException("Component not created");
        } else if (!com_google_android_gms_measurement_internal_zzz.isInitialized()) {
            throw new IllegalStateException("Component not initialized");
        }
    }

    private com.google.android.gms.internal.zzqb.zza[] zza(String str, zzg[] com_google_android_gms_internal_zzqb_zzgArr, zzb[] com_google_android_gms_internal_zzqb_zzbArr) {
        zzx.zzcM(str);
        return zzCe().zza(str, com_google_android_gms_internal_zzqb_zzbArr, com_google_android_gms_internal_zzqb_zzgArr);
    }

    public static zzw zzaT(Context context) {
        zzx.zzz(context);
        zzx.zzz(context.getApplicationContext());
        if (zzaXW == null) {
            synchronized (zzw.class) {
                try {
                    if (zzaXW == null) {
                        zzaXW = (zzaXV != null ? zzaXV : new zzaa(context)).zzDj();
                    }
                } catch (Throwable th) {
                    Class cls = zzw.class;
                }
            }
        }
        return zzaXW;
    }

    private void zzb(Bundle bundle, int i) {
        if (bundle.getLong("_err") == 0) {
            bundle.putLong("_err", (long) i);
        }
    }

    @WorkerThread
    private void zzb(String str, int i, Throwable th, byte[] bArr) {
        int i2 = 0;
        zzjk();
        zzjv();
        zzx.zzcM(str);
        if (bArr == null) {
            bArr = new byte[0];
        }
        zzCj().beginTransaction();
        zza zzeY = zzCj().zzeY(str);
        int i3 = ((i == 200 || i == 204 || i == 304) && th == null) ? 1 : 0;
        if (i3 != 0 || i == 404) {
            if (i == 404 || i == 304) {
                if (zzCl().zzfk(str) == null && !zzCl().zze(str, null)) {
                    return;
                }
            } else if (!zzCl().zze(str, bArr)) {
                zzCj().endTransaction();
                return;
            }
            try {
                zzeY.zzT(zzjl().currentTimeMillis());
                zzCj().zza(zzeY);
                if (i == 404) {
                    zzAo().zzCF().zzfg("Config not found. Using empty config");
                } else {
                    zzAo().zzCK().zze("Successfully fetched config. Got network response. code, size", Integer.valueOf(i), Integer.valueOf(bArr.length));
                }
                if (zzCW().zzlB() && zzDd()) {
                    zzDc();
                } else {
                    zzDe();
                }
            } finally {
                zzCj().endTransaction();
            }
        } else {
            zzeY.zzU(zzjl().currentTimeMillis());
            zzCj().zza(zzeY);
            zzAo().zzCK().zze("Fetching config failed. code, error", Integer.valueOf(i), th);
            zzCo().zzaXk.set(zzjl().currentTimeMillis());
            if (i == 503 || i == 429) {
                i2 = 1;
            }
            if (i2 != 0) {
                zzCo().zzaXl.set(zzjl().currentTimeMillis());
            }
            zzDe();
        }
        zzCj().setTransactionSuccessful();
        zzCj().endTransaction();
    }

    @WorkerThread
    private void zze(AppMetadata appMetadata) {
        Object obj = 1;
        zzjk();
        zzjv();
        zzx.zzz(appMetadata);
        zzx.zzcM(appMetadata.packageName);
        zza zzeY = zzCj().zzeY(appMetadata.packageName);
        String zzfi = zzCo().zzfi(appMetadata.packageName);
        Object obj2 = null;
        if (zzeY == null) {
            zzeY = new zza(this, appMetadata.packageName);
            zzeY.zzeM(zzCo().zzCM());
            zzeY.zzeO(zzfi);
            obj2 = 1;
        } else if (!zzfi.equals(zzeY.zzBl())) {
            zzeY.zzeO(zzfi);
            zzeY.zzeM(zzCo().zzCM());
            int i = 1;
        }
        if (!(TextUtils.isEmpty(appMetadata.zzaVt) || appMetadata.zzaVt.equals(zzeY.zzBk()))) {
            zzeY.zzeN(appMetadata.zzaVt);
            obj2 = 1;
        }
        if (!(appMetadata.zzaVv == 0 || appMetadata.zzaVv == zzeY.zzBp())) {
            zzeY.zzQ(appMetadata.zzaVv);
            obj2 = 1;
        }
        if (!(TextUtils.isEmpty(appMetadata.zzaMV) || appMetadata.zzaMV.equals(zzeY.zzli()))) {
            zzeY.setAppVersion(appMetadata.zzaMV);
            obj2 = 1;
        }
        if (!(TextUtils.isEmpty(appMetadata.zzaVu) || appMetadata.zzaVu.equals(zzeY.zzBo()))) {
            zzeY.zzeP(appMetadata.zzaVu);
            obj2 = 1;
        }
        if (appMetadata.zzaVw != zzeY.zzBq()) {
            zzeY.zzR(appMetadata.zzaVw);
            obj2 = 1;
        }
        if (appMetadata.zzaVy != zzeY.zzAr()) {
            zzeY.setMeasurementEnabled(appMetadata.zzaVy);
        } else {
            obj = obj2;
        }
        if (obj != null) {
            zzCj().zza(zzeY);
        }
    }

    private boolean zzg(String str, long j) {
        zzCj().beginTransaction();
        try {
            zzb com_google_android_gms_measurement_internal_zzw_zza = new zza();
            zzCj().zza(str, j, com_google_android_gms_measurement_internal_zzw_zza);
            if (com_google_android_gms_measurement_internal_zzw_zza.isEmpty()) {
                zzCj().setTransactionSuccessful();
                zzCj().endTransaction();
                return false;
            }
            int i;
            zze com_google_android_gms_internal_zzqb_zze = com_google_android_gms_measurement_internal_zzw_zza.zzaYt;
            com_google_android_gms_internal_zzqb_zze.zzbam = new zzb[com_google_android_gms_measurement_internal_zzw_zza.zzpH.size()];
            int i2 = 0;
            int i3 = 0;
            while (i3 < com_google_android_gms_measurement_internal_zzw_zza.zzpH.size()) {
                if (zzCl().zzP(com_google_android_gms_measurement_internal_zzw_zza.zzaYt.appId, ((zzb) com_google_android_gms_measurement_internal_zzw_zza.zzpH.get(i3)).name)) {
                    zzAo().zzCK().zzj("Dropping blacklisted raw event", ((zzb) com_google_android_gms_measurement_internal_zzw_zza.zzpH.get(i3)).name);
                    i = i2;
                } else {
                    com_google_android_gms_internal_zzqb_zze.zzbam[i2] = (zzb) com_google_android_gms_measurement_internal_zzw_zza.zzpH.get(i3);
                    i = i2 + 1;
                }
                i3++;
                i2 = i;
            }
            if (i2 < com_google_android_gms_measurement_internal_zzw_zza.zzpH.size()) {
                com_google_android_gms_internal_zzqb_zze.zzbam = (zzb[]) Arrays.copyOf(com_google_android_gms_internal_zzqb_zze.zzbam, i2);
            }
            com_google_android_gms_internal_zzqb_zze.zzbaF = zza(com_google_android_gms_measurement_internal_zzw_zza.zzaYt.appId, com_google_android_gms_measurement_internal_zzw_zza.zzaYt.zzban, com_google_android_gms_internal_zzqb_zze.zzbam);
            com_google_android_gms_internal_zzqb_zze.zzbap = com_google_android_gms_internal_zzqb_zze.zzbam[0].zzbaf;
            com_google_android_gms_internal_zzqb_zze.zzbaq = com_google_android_gms_internal_zzqb_zze.zzbam[0].zzbaf;
            for (i = 1; i < com_google_android_gms_internal_zzqb_zze.zzbam.length; i++) {
                zzb com_google_android_gms_internal_zzqb_zzb = com_google_android_gms_internal_zzqb_zze.zzbam[i];
                if (com_google_android_gms_internal_zzqb_zzb.zzbaf.longValue() < com_google_android_gms_internal_zzqb_zze.zzbap.longValue()) {
                    com_google_android_gms_internal_zzqb_zze.zzbap = com_google_android_gms_internal_zzqb_zzb.zzbaf;
                }
                if (com_google_android_gms_internal_zzqb_zzb.zzbaf.longValue() > com_google_android_gms_internal_zzqb_zze.zzbaq.longValue()) {
                    com_google_android_gms_internal_zzqb_zze.zzbaq = com_google_android_gms_internal_zzqb_zzb.zzbaf;
                }
            }
            String str2 = com_google_android_gms_measurement_internal_zzw_zza.zzaYt.appId;
            zza zzeY = zzCj().zzeY(str2);
            if (zzeY == null) {
                zzAo().zzCE().zzfg("Bundling raw events w/o app info");
            } else {
                long zzBn = zzeY.zzBn();
                com_google_android_gms_internal_zzqb_zze.zzbas = zzBn != 0 ? Long.valueOf(zzBn) : null;
                long zzBm = zzeY.zzBm();
                if (zzBm == 0) {
                    zzBm = zzBn;
                }
                com_google_android_gms_internal_zzqb_zze.zzbar = zzBm != 0 ? Long.valueOf(zzBm) : null;
                zzeY.zzBu();
                com_google_android_gms_internal_zzqb_zze.zzbaD = Integer.valueOf((int) zzeY.zzBr());
                zzeY.zzO(com_google_android_gms_internal_zzqb_zze.zzbap.longValue());
                zzeY.zzP(com_google_android_gms_internal_zzqb_zze.zzbaq.longValue());
                zzCj().zza(zzeY);
            }
            com_google_android_gms_internal_zzqb_zze.zzaVx = zzAo().zzCL();
            zzCj().zza(com_google_android_gms_internal_zzqb_zze);
            zzCj().zzz(com_google_android_gms_measurement_internal_zzw_zza.zzaYu);
            zzCj().zzfc(str2);
            zzCj().setTransactionSuccessful();
            return true;
        } finally {
            zzCj().endTransaction();
        }
    }

    public Context getContext() {
        return this.mContext;
    }

    @WorkerThread
    protected void start() {
        zzjk();
        if (!zzCZ() || (this.zzaYa.isInitialized() && !this.zzaYa.zzDi())) {
            zzCj().zzCr();
            if (zzCS()) {
                if (!(zzCp().zzkr() || zzCZ() || TextUtils.isEmpty(zzCg().zzBk()))) {
                    zzCf().zzDl();
                }
            } else if (zzCo().zzAr()) {
                if (!zzCk().zzbk("android.permission.INTERNET")) {
                    zzAo().zzCE().zzfg("App is missing INTERNET permission");
                }
                if (!zzCk().zzbk("android.permission.ACCESS_NETWORK_STATE")) {
                    zzAo().zzCE().zzfg("App is missing ACCESS_NETWORK_STATE permission");
                }
                if (!AppMeasurementReceiver.zzY(getContext())) {
                    zzAo().zzCE().zzfg("AppMeasurementReceiver not registered/enabled");
                }
                if (!AppMeasurementService.zzZ(getContext())) {
                    zzAo().zzCE().zzfg("AppMeasurementService not registered/enabled");
                }
                zzAo().zzCE().zzfg("Uploading is not possible. App measurement disabled");
            }
            zzDe();
            return;
        }
        zzAo().zzCE().zzfg("Scheduler shutting down before Scion.start() called");
    }

    public zzp zzAo() {
        zza(this.zzaXZ);
        return this.zzaXZ;
    }

    @WorkerThread
    protected boolean zzCS() {
        boolean z = true;
        zzjv();
        zzjk();
        if (this.zzaYo == null) {
            boolean z2 = zzCk().zzbk("android.permission.INTERNET") && zzCk().zzbk("android.permission.ACCESS_NETWORK_STATE") && AppMeasurementReceiver.zzY(getContext()) && AppMeasurementService.zzZ(getContext());
            this.zzaYo = Boolean.valueOf(z2);
            if (this.zzaYo.booleanValue() && !zzCp().zzkr()) {
                if (TextUtils.isEmpty(zzCg().zzBk())) {
                    z = false;
                }
                this.zzaYo = Boolean.valueOf(z);
            }
        }
        return this.zzaYo.booleanValue();
    }

    public zzp zzCT() {
        return (this.zzaXZ == null || !this.zzaXZ.isInitialized()) ? null : this.zzaXZ;
    }

    zzv zzCU() {
        return this.zzaYa;
    }

    public AppMeasurement zzCV() {
        return this.zzaYd;
    }

    public zzq zzCW() {
        zza(this.zzaYg);
        return this.zzaYg;
    }

    public zzr zzCX() {
        if (this.zzaYl != null) {
            return this.zzaYl;
        }
        throw new IllegalStateException("Network broadcast receiver not created");
    }

    public zzag zzCY() {
        zza(this.zzaYm);
        return this.zzaYm;
    }

    protected boolean zzCZ() {
        return false;
    }

    public zzc zzCe() {
        zza(this.zzaYn);
        return this.zzaYn;
    }

    public zzab zzCf() {
        zza(this.zzaYj);
        return this.zzaYj;
    }

    public zzn zzCg() {
        zza(this.zzaYk);
        return this.zzaYk;
    }

    public zzg zzCh() {
        zza(this.zzaYi);
        return this.zzaYi;
    }

    public zzac zzCi() {
        zza(this.zzaYh);
        return this.zzaYh;
    }

    public zze zzCj() {
        zza(this.zzaYf);
        return this.zzaYf;
    }

    public zzaj zzCk() {
        zza(this.zzaYe);
        return this.zzaYe;
    }

    public zzu zzCl() {
        zza(this.zzaYc);
        return this.zzaYc;
    }

    public zzad zzCm() {
        zza(this.zzaYb);
        return this.zzaYb;
    }

    public zzv zzCn() {
        zza(this.zzaYa);
        return this.zzaYa;
    }

    public zzt zzCo() {
        zza(this.zzaXY);
        return this.zzaXY;
    }

    public zzd zzCp() {
        return this.zzaXX;
    }

    long zzDa() {
        return ((((zzjl().currentTimeMillis() + zzCo().zzCN()) / 1000) / 60) / 60) / 24;
    }

    @WorkerThread
    public void zzDc() {
        Map map = null;
        int i = 0;
        zzjk();
        zzjv();
        if (!zzCp().zzkr()) {
            Boolean zzCP = zzCo().zzCP();
            if (zzCP == null) {
                zzAo().zzCF().zzfg("Upload data called on the client side before use of service was decided");
                return;
            } else if (zzCP.booleanValue()) {
                zzAo().zzCE().zzfg("Upload called in the client side when service should be used");
                return;
            }
        }
        if (zzDb()) {
            zzAo().zzCF().zzfg("Uploading requested multiple times");
        } else if (zzCW().zzlB()) {
            long currentTimeMillis = zzjl().currentTimeMillis();
            zzad(currentTimeMillis - zzCp().zzBW());
            long j = zzCo().zzaXj.get();
            if (j != 0) {
                zzAo().zzCJ().zzj("Uploading events. Elapsed time since last upload attempt (ms)", Long.valueOf(Math.abs(currentTimeMillis - j)));
            }
            String zzCq = zzCj().zzCq();
            if (TextUtils.isEmpty(zzCq)) {
                String zzaa = zzCj().zzaa(currentTimeMillis - zzCp().zzBW());
                if (!TextUtils.isEmpty(zzaa)) {
                    zza zzeY = zzCj().zzeY(zzaa);
                    if (zzeY != null) {
                        String zzH = zzCp().zzH(zzeY.zzBk(), zzeY.zzBj());
                        try {
                            URL url = new URL(zzH);
                            zzAo().zzCK().zzj("Fetching remote configuration", zzeY.zzwK());
                            zzqa.zzb zzfk = zzCl().zzfk(zzeY.zzwK());
                            if (!(zzfk == null || zzfk.zzaZT == null)) {
                                map = new ArrayMap();
                                map.put("Config-Version", String.valueOf(zzfk.zzaZT));
                            }
                            zzCW().zza(zzaa, url, map, new zza(this) {
                                final /* synthetic */ zzw zzaYs;

                                {
                                    this.zzaYs = r1;
                                }

                                public void zza(String str, int i, Throwable th, byte[] bArr) {
                                    this.zzaYs.zzb(str, i, th, bArr);
                                }
                            });
                            return;
                        } catch (MalformedURLException e) {
                            zzAo().zzCE().zzj("Failed to parse config URL. Not fetching", zzH);
                            return;
                        }
                    }
                    return;
                }
                return;
            }
            List<Pair> zzn = zzCj().zzn(zzCq, zzCp().zzeU(zzCq), zzCp().zzeV(zzCq));
            if (!zzn.isEmpty()) {
                zze com_google_android_gms_internal_zzqb_zze;
                Object obj;
                List subList;
                for (Pair pair : zzn) {
                    com_google_android_gms_internal_zzqb_zze = (zze) pair.first;
                    if (!TextUtils.isEmpty(com_google_android_gms_internal_zzqb_zze.zzbaz)) {
                        obj = com_google_android_gms_internal_zzqb_zze.zzbaz;
                        break;
                    }
                }
                obj = null;
                if (obj != null) {
                    for (int i2 = 0; i2 < zzn.size(); i2++) {
                        com_google_android_gms_internal_zzqb_zze = (zze) ((Pair) zzn.get(i2)).first;
                        if (!TextUtils.isEmpty(com_google_android_gms_internal_zzqb_zze.zzbaz) && !com_google_android_gms_internal_zzqb_zze.zzbaz.equals(obj)) {
                            subList = zzn.subList(0, i2);
                            break;
                        }
                    }
                }
                subList = zzn;
                zzd com_google_android_gms_internal_zzqb_zzd = new zzd();
                com_google_android_gms_internal_zzqb_zzd.zzbaj = new zze[subList.size()];
                List arrayList = new ArrayList(subList.size());
                while (i < com_google_android_gms_internal_zzqb_zzd.zzbaj.length) {
                    com_google_android_gms_internal_zzqb_zzd.zzbaj[i] = (zze) ((Pair) subList.get(i)).first;
                    arrayList.add(((Pair) subList.get(i)).second);
                    com_google_android_gms_internal_zzqb_zzd.zzbaj[i].zzbay = Long.valueOf(zzCp().zzBp());
                    com_google_android_gms_internal_zzqb_zzd.zzbaj[i].zzbao = Long.valueOf(currentTimeMillis);
                    com_google_android_gms_internal_zzqb_zzd.zzbaj[i].zzbaE = Boolean.valueOf(zzCp().zzkr());
                    i++;
                }
                Object zzb = zzAo().zzQ(2) ? zzaj.zzb(com_google_android_gms_internal_zzqb_zzd) : null;
                byte[] zza = zzCk().zza(com_google_android_gms_internal_zzqb_zzd);
                String zzBV = zzCp().zzBV();
                try {
                    URL url2 = new URL(zzBV);
                    zzA(arrayList);
                    zzCo().zzaXk.set(currentTimeMillis);
                    Object obj2 = "?";
                    if (com_google_android_gms_internal_zzqb_zzd.zzbaj.length > 0) {
                        obj2 = com_google_android_gms_internal_zzqb_zzd.zzbaj[0].appId;
                    }
                    zzAo().zzCK().zzd("Uploading data. app, uncompressed size, data", obj2, Integer.valueOf(zza.length), zzb);
                    zzCW().zza(zzCq, url2, zza, null, new zza(this) {
                        final /* synthetic */ zzw zzaYs;

                        {
                            this.zzaYs = r1;
                        }

                        public void zza(String str, int i, Throwable th, byte[] bArr) {
                            this.zzaYs.zza(i, th, bArr);
                        }
                    });
                } catch (MalformedURLException e2) {
                    zzAo().zzCE().zzj("Failed to parse upload URL. Not uploading", zzBV);
                }
            }
        } else {
            zzAo().zzCF().zzfg("Network not connected, ignoring upload request");
            zzDe();
        }
    }

    void zzDg() {
        this.zzaYr++;
    }

    void zzE(String str, int i) {
    }

    public void zzJ(boolean z) {
        zzDe();
    }

    void zza(EventParcel eventParcel, String str) {
        zza zzeY = zzCj().zzeY(str);
        if (zzeY == null || TextUtils.isEmpty(zzeY.zzli())) {
            zzAo().zzCJ().zzj("No app data available; dropping event", str);
            return;
        }
        try {
            String str2 = getContext().getPackageManager().getPackageInfo(str, 0).versionName;
            if (!(zzeY.zzli() == null || zzeY.zzli().equals(str2))) {
                zzAo().zzCF().zzj("App version does not match; dropping event", str);
                return;
            }
        } catch (NameNotFoundException e) {
            zzAo().zzCF().zzj("Could not find package", str);
        }
        zzb(eventParcel, new AppMetadata(str, zzeY.zzBk(), zzeY.zzli(), zzeY.zzBo(), zzeY.zzBp(), zzeY.zzBq(), null, zzeY.zzAr(), false));
    }

    void zza(zzh com_google_android_gms_measurement_internal_zzh, AppMetadata appMetadata) {
        zzjk();
        zzjv();
        zzx.zzz(com_google_android_gms_measurement_internal_zzh);
        zzx.zzz(appMetadata);
        zzx.zzcM(com_google_android_gms_measurement_internal_zzh.zzaUa);
        zzx.zzac(com_google_android_gms_measurement_internal_zzh.zzaUa.equals(appMetadata.packageName));
        zze com_google_android_gms_internal_zzqb_zze = new zze();
        com_google_android_gms_internal_zzqb_zze.zzbal = Integer.valueOf(1);
        com_google_android_gms_internal_zzqb_zze.zzbat = "android";
        com_google_android_gms_internal_zzqb_zze.appId = appMetadata.packageName;
        com_google_android_gms_internal_zzqb_zze.zzaVu = appMetadata.zzaVu;
        com_google_android_gms_internal_zzqb_zze.zzaMV = appMetadata.zzaMV;
        com_google_android_gms_internal_zzqb_zze.zzbax = Long.valueOf(appMetadata.zzaVv);
        com_google_android_gms_internal_zzqb_zze.zzaVt = appMetadata.zzaVt;
        com_google_android_gms_internal_zzqb_zze.zzbaC = appMetadata.zzaVw == 0 ? null : Long.valueOf(appMetadata.zzaVw);
        Pair zzfh = zzCo().zzfh(appMetadata.packageName);
        if (!(zzfh == null || zzfh.first == null || zzfh.second == null)) {
            com_google_android_gms_internal_zzqb_zze.zzbaz = (String) zzfh.first;
            com_google_android_gms_internal_zzqb_zze.zzbaA = (Boolean) zzfh.second;
        }
        com_google_android_gms_internal_zzqb_zze.zzbau = zzCh().zzht();
        com_google_android_gms_internal_zzqb_zze.osVersion = zzCh().zzCy();
        com_google_android_gms_internal_zzqb_zze.zzbaw = Integer.valueOf((int) zzCh().zzCz());
        com_google_android_gms_internal_zzqb_zze.zzbav = zzCh().zzCA();
        com_google_android_gms_internal_zzqb_zze.zzbay = null;
        com_google_android_gms_internal_zzqb_zze.zzbao = null;
        com_google_android_gms_internal_zzqb_zze.zzbap = null;
        com_google_android_gms_internal_zzqb_zze.zzbaq = null;
        zza zzeY = zzCj().zzeY(appMetadata.packageName);
        if (zzeY == null) {
            zzeY = new zza(this, appMetadata.packageName);
            zzeY.zzeM(zzCo().zzCM());
            zzeY.zzeN(appMetadata.zzaVt);
            zzeY.zzeO(zzCo().zzfi(appMetadata.packageName));
            zzeY.zzS(0);
            zzeY.zzO(0);
            zzeY.zzP(0);
            zzeY.setAppVersion(appMetadata.zzaMV);
            zzeY.zzeP(appMetadata.zzaVu);
            zzeY.zzQ(appMetadata.zzaVv);
            zzeY.zzR(appMetadata.zzaVw);
            zzeY.setMeasurementEnabled(appMetadata.zzaVy);
            zzCj().zza(zzeY);
        }
        com_google_android_gms_internal_zzqb_zze.zzbaB = zzeY.zzBj();
        List zzeX = zzCj().zzeX(appMetadata.packageName);
        com_google_android_gms_internal_zzqb_zze.zzban = new zzg[zzeX.size()];
        for (int i = 0; i < zzeX.size(); i++) {
            zzg com_google_android_gms_internal_zzqb_zzg = new zzg();
            com_google_android_gms_internal_zzqb_zze.zzban[i] = com_google_android_gms_internal_zzqb_zzg;
            com_google_android_gms_internal_zzqb_zzg.name = ((zzai) zzeX.get(i)).mName;
            com_google_android_gms_internal_zzqb_zzg.zzbaJ = Long.valueOf(((zzai) zzeX.get(i)).zzaZp);
            zzCk().zza(com_google_android_gms_internal_zzqb_zzg, ((zzai) zzeX.get(i)).zzNc);
        }
        try {
            zzCj().zza(com_google_android_gms_measurement_internal_zzh, zzCj().zzb(com_google_android_gms_internal_zzqb_zze));
        } catch (IOException e) {
            zzAo().zzCE().zzj("Data loss. Failed to insert raw event metadata", e);
        }
    }

    boolean zzad(long j) {
        return zzg(null, j);
    }

    void zzb(EventParcel eventParcel, AppMetadata appMetadata) {
        long nanoTime = System.nanoTime();
        zzjk();
        zzjv();
        String str = appMetadata.packageName;
        zzx.zzcM(str);
        if (!TextUtils.isEmpty(appMetadata.zzaVt)) {
            if (!appMetadata.zzaVy) {
                zze(appMetadata);
            } else if (zzCl().zzP(str, eventParcel.name)) {
                zzAo().zzCK().zzj("Dropping blacklisted event", eventParcel.name);
            } else {
                if (zzAo().zzQ(2)) {
                    zzAo().zzCK().zzj("Logging event", eventParcel);
                }
                zzCj().beginTransaction();
                try {
                    Bundle zzCC = eventParcel.zzaVV.zzCC();
                    zze(appMetadata);
                    if ("_iap".equals(eventParcel.name) || "ecommerce_purchase".equals(eventParcel.name)) {
                        Object string = zzCC.getString("currency");
                        long j = zzCC.getLong(Lead.KEY_VALUE);
                        if (!TextUtils.isEmpty(string) && j > 0) {
                            String toUpperCase = string.toUpperCase(Locale.US);
                            if (toUpperCase.matches("[A-Z]{3}")) {
                                zzai com_google_android_gms_measurement_internal_zzai;
                                String str2 = "_ltv_" + toUpperCase;
                                zzai zzK = zzCj().zzK(str, str2);
                                if (zzK == null || !(zzK.zzNc instanceof Long)) {
                                    zzCj().zzA(str, zzCp().zzeT(str) - 1);
                                    com_google_android_gms_measurement_internal_zzai = new zzai(str, str2, zzjl().currentTimeMillis(), Long.valueOf(j));
                                } else {
                                    com_google_android_gms_measurement_internal_zzai = new zzai(str, str2, zzjl().currentTimeMillis(), Long.valueOf(j + ((Long) zzK.zzNc).longValue()));
                                }
                                zzCj().zza(com_google_android_gms_measurement_internal_zzai);
                            }
                        }
                    }
                    boolean zzfq = zzaj.zzfq(eventParcel.name);
                    boolean zzI = zzaj.zzI(zzCC);
                    zze zzCj = zzCj();
                    long zzDa = zzDa();
                    boolean z = zzfq && zzI;
                    com.google.android.gms.measurement.internal.zze.zza zza = zzCj.zza(zzDa, str, zzfq, z);
                    zzDa = zza.zzaVF - zzCp().zzBI();
                    if (zzDa > 0) {
                        if (zzDa % 1000 == 1) {
                            zzAo().zzCF().zzj("Data loss. Too many events logged. count", Long.valueOf(zza.zzaVF));
                        }
                        zzCj().setTransactionSuccessful();
                        return;
                    }
                    zzi zzab;
                    if (zzfq) {
                        zzDa = zza.zzaVE - zzCp().zzBJ();
                        if (zzDa > 0) {
                            zzE(str, 2);
                            if (zzDa % 1000 == 1) {
                                zzAo().zzCF().zzj("Data loss. Too many public events logged. count", Long.valueOf(zza.zzaVE));
                            }
                            zzCj().setTransactionSuccessful();
                            zzCj().endTransaction();
                            return;
                        }
                    }
                    if (zzfq && zzI) {
                        if (zza.zzaVG - zzCp().zzBK() > 0) {
                            zzCC.remove("_c");
                            zzb(zzCC, 4);
                        }
                    }
                    long zzeZ = zzCj().zzeZ(str);
                    if (zzeZ > 0) {
                        zzAo().zzCF().zzj("Data lost. Too many events stored on disk, deleted", Long.valueOf(zzeZ));
                    }
                    zzh com_google_android_gms_measurement_internal_zzh = new zzh(this, eventParcel.zzaVW, str, eventParcel.name, eventParcel.zzaVX, 0, zzCC);
                    zzi zzI2 = zzCj().zzI(str, com_google_android_gms_measurement_internal_zzh.mName);
                    if (zzI2 != null) {
                        com_google_android_gms_measurement_internal_zzh = com_google_android_gms_measurement_internal_zzh.zza(this, zzI2.zzaVR);
                        zzab = zzI2.zzab(com_google_android_gms_measurement_internal_zzh.zzaez);
                    } else if (zzCj().zzfd(str) >= ((long) zzCp().zzBH())) {
                        zzAo().zzCF().zze("Too many event names used, ignoring event. name, supported count", com_google_android_gms_measurement_internal_zzh.mName, Integer.valueOf(zzCp().zzBH()));
                        zzE(str, 1);
                        zzCj().endTransaction();
                        return;
                    } else {
                        zzab = new zzi(str, com_google_android_gms_measurement_internal_zzh.mName, 0, 0, com_google_android_gms_measurement_internal_zzh.zzaez);
                    }
                    zzCj().zza(zzab);
                    zza(com_google_android_gms_measurement_internal_zzh, appMetadata);
                    zzCj().setTransactionSuccessful();
                    if (zzAo().zzQ(2)) {
                        zzAo().zzCK().zzj("Event recorded", com_google_android_gms_measurement_internal_zzh);
                    }
                    zzCj().endTransaction();
                    zzDe();
                    zzAo().zzCK().zzj("Background event processing time, ms", Long.valueOf(((System.nanoTime() - nanoTime) + 500000) / 1000000));
                } finally {
                    zzCj().endTransaction();
                }
            }
        }
    }

    @WorkerThread
    void zzb(UserAttributeParcel userAttributeParcel, AppMetadata appMetadata) {
        zzjk();
        zzjv();
        if (!TextUtils.isEmpty(appMetadata.zzaVt)) {
            if (appMetadata.zzaVy) {
                zzCk().zzfs(userAttributeParcel.name);
                Object zzm = zzCk().zzm(userAttributeParcel.name, userAttributeParcel.getValue());
                if (zzm != null) {
                    zzai com_google_android_gms_measurement_internal_zzai = new zzai(appMetadata.packageName, userAttributeParcel.name, userAttributeParcel.zzaZm, zzm);
                    zzAo().zzCJ().zze("Setting user property", com_google_android_gms_measurement_internal_zzai.mName, zzm);
                    zzCj().beginTransaction();
                    try {
                        zze(appMetadata);
                        boolean zza = zzCj().zza(com_google_android_gms_measurement_internal_zzai);
                        zzCj().setTransactionSuccessful();
                        if (zza) {
                            zzAo().zzCJ().zze("User property set", com_google_android_gms_measurement_internal_zzai.mName, com_google_android_gms_measurement_internal_zzai.zzNc);
                        } else {
                            zzAo().zzCH().zze("Ignoring user property. Value too long", com_google_android_gms_measurement_internal_zzai.mName, com_google_android_gms_measurement_internal_zzai.zzNc);
                        }
                        zzCj().endTransaction();
                        return;
                    } catch (Throwable th) {
                        zzCj().endTransaction();
                    }
                } else {
                    return;
                }
            }
            zze(appMetadata);
        }
    }

    void zzb(zzz com_google_android_gms_measurement_internal_zzz) {
        this.zzaYq++;
    }

    void zzc(AppMetadata appMetadata) {
        zzjk();
        zzjv();
        zzx.zzcM(appMetadata.packageName);
        zze(appMetadata);
    }

    @WorkerThread
    void zzc(UserAttributeParcel userAttributeParcel, AppMetadata appMetadata) {
        zzjk();
        zzjv();
        if (!TextUtils.isEmpty(appMetadata.zzaVt)) {
            if (appMetadata.zzaVy) {
                zzAo().zzCJ().zzj("Removing user property", userAttributeParcel.name);
                zzCj().beginTransaction();
                try {
                    zze(appMetadata);
                    zzCj().zzJ(appMetadata.packageName, userAttributeParcel.name);
                    zzCj().setTransactionSuccessful();
                    zzAo().zzCJ().zzj("User property removed", userAttributeParcel.name);
                } finally {
                    zzCj().endTransaction();
                }
            } else {
                zze(appMetadata);
            }
        }
    }

    @WorkerThread
    public void zzd(AppMetadata appMetadata) {
        zzjk();
        zzjv();
        zzx.zzz(appMetadata);
        zzx.zzcM(appMetadata.packageName);
        if (!TextUtils.isEmpty(appMetadata.zzaVt)) {
            if (appMetadata.zzaVy) {
                long currentTimeMillis = zzjl().currentTimeMillis();
                zzCj().beginTransaction();
                try {
                    Bundle bundle;
                    zza zzeY = zzCj().zzeY(appMetadata.packageName);
                    if (!(zzeY == null || zzeY.zzli() == null || zzeY.zzli().equals(appMetadata.zzaMV))) {
                        bundle = new Bundle();
                        bundle.putString("_pv", zzeY.zzli());
                        zzb(new EventParcel("_au", new EventParams(bundle), "auto", currentTimeMillis), appMetadata);
                    }
                    zze(appMetadata);
                    if (zzCj().zzI(appMetadata.packageName, "_f") == null) {
                        zzb(new UserAttributeParcel("_fot", currentTimeMillis, Long.valueOf(((currentTimeMillis / 3600000) + 1) * 3600000), "auto"), appMetadata);
                        bundle = new Bundle();
                        bundle.putLong("_c", 1);
                        zzb(new EventParcel("_f", new EventParams(bundle), "auto", currentTimeMillis), appMetadata);
                    } else if (appMetadata.zzaVz) {
                        zzb(new EventParcel("_cd", new EventParams(new Bundle()), "auto", currentTimeMillis), appMetadata);
                    }
                    zzCj().setTransactionSuccessful();
                } finally {
                    zzCj().endTransaction();
                }
            } else {
                zze(appMetadata);
            }
        }
    }

    void zzjj() {
        if (zzCp().zzkr()) {
            throw new IllegalStateException("Unexpected call on package side");
        }
    }

    @WorkerThread
    public void zzjk() {
        zzCn().zzjk();
    }

    public zzmq zzjl() {
        return this.zzqW;
    }

    void zzjv() {
        if (!this.zzQk) {
            throw new IllegalStateException("AppMeasurement is not initialized");
        }
    }
}
