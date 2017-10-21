package com.google.android.gms.measurement.internal;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.google.android.gms.common.internal.zzx;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Locale;

class zzt extends zzz {
    static final Pair<String, Long> zzaXh = new Pair("", Long.valueOf(0));
    private SharedPreferences zzTh;
    public final zzc zzaXi = new zzc("health_monitor", zzCp().zzkX());
    public final zzb zzaXj = new zzb(this, "last_upload", 0);
    public final zzb zzaXk = new zzb(this, "last_upload_attempt", 0);
    public final zzb zzaXl = new zzb(this, "backoff", 0);
    public final zzb zzaXm = new zzb(this, "last_delete_stale", 0);
    public final zzb zzaXn = new zzb(this, "midnight_offset", 0);
    private String zzaXo;
    private boolean zzaXp;
    private long zzaXq;
    private final SecureRandom zzaXr = new SecureRandom();
    public final zzb zzaXs = new zzb(this, "time_before_start", 10000);
    public final zzb zzaXt = new zzb(this, "session_timeout", 1800000);
    public final zza zzaXu = new zza(this, "start_new_session", true);
    public final zzb zzaXv = new zzb(this, "last_pause_time", 0);
    public final zzb zzaXw = new zzb(this, "time_active", 0);
    public boolean zzaXx;

    public final class zza {
        final /* synthetic */ zzt zzaXA;
        private final boolean zzaXy;
        private boolean zzaXz;
        private boolean zzagf;
        private final String zzvs;

        public zza(zzt com_google_android_gms_measurement_internal_zzt, String str, boolean z) {
            this.zzaXA = com_google_android_gms_measurement_internal_zzt;
            zzx.zzcM(str);
            this.zzvs = str;
            this.zzaXy = z;
        }

        @WorkerThread
        private void zzCR() {
            if (!this.zzaXz) {
                this.zzaXz = true;
                this.zzagf = this.zzaXA.zzTh.getBoolean(this.zzvs, this.zzaXy);
            }
        }

        @WorkerThread
        public final boolean get() {
            zzCR();
            return this.zzagf;
        }

        @WorkerThread
        public final void set(boolean z) {
            Editor edit = this.zzaXA.zzTh.edit();
            edit.putBoolean(this.zzvs, z);
            edit.apply();
            this.zzagf = z;
        }
    }

    public final class zzb {
        private long zzaDV;
        final /* synthetic */ zzt zzaXA;
        private final long zzaXB;
        private boolean zzaXz;
        private final String zzvs;

        public zzb(zzt com_google_android_gms_measurement_internal_zzt, String str, long j) {
            this.zzaXA = com_google_android_gms_measurement_internal_zzt;
            zzx.zzcM(str);
            this.zzvs = str;
            this.zzaXB = j;
        }

        @WorkerThread
        private void zzCR() {
            if (!this.zzaXz) {
                this.zzaXz = true;
                this.zzaDV = this.zzaXA.zzTh.getLong(this.zzvs, this.zzaXB);
            }
        }

        @WorkerThread
        public final long get() {
            zzCR();
            return this.zzaDV;
        }

        @WorkerThread
        public final void set(long j) {
            Editor edit = this.zzaXA.zzTh.edit();
            edit.putLong(this.zzvs, j);
            edit.apply();
            this.zzaDV = j;
        }
    }

    public final class zzc {
        private final long zzTl;
        final /* synthetic */ zzt zzaXA;
        final String zzaXC;
        private final String zzaXD;
        private final String zzaXE;

        private zzc(zzt com_google_android_gms_measurement_internal_zzt, String str, long j) {
            this.zzaXA = com_google_android_gms_measurement_internal_zzt;
            zzx.zzcM(str);
            zzx.zzac(j > 0);
            this.zzaXC = str + ":start";
            this.zzaXD = str + ":count";
            this.zzaXE = str + ":value";
            this.zzTl = j;
        }

        @WorkerThread
        private void zzlL() {
            this.zzaXA.zzjk();
            long currentTimeMillis = this.zzaXA.zzjl().currentTimeMillis();
            Editor edit = this.zzaXA.zzTh.edit();
            edit.remove(this.zzaXD);
            edit.remove(this.zzaXE);
            edit.putLong(this.zzaXC, currentTimeMillis);
            edit.apply();
        }

        @WorkerThread
        private long zzlM() {
            this.zzaXA.zzjk();
            long zzlO = zzlO();
            if (zzlO != 0) {
                return Math.abs(zzlO - this.zzaXA.zzjl().currentTimeMillis());
            }
            zzlL();
            return 0;
        }

        @WorkerThread
        private long zzlO() {
            return this.zzaXA.zzCO().getLong(this.zzaXC, 0);
        }

        @WorkerThread
        public final void zzbq(String str) {
            zzf(str, 1);
        }

        @WorkerThread
        public final void zzf(String str, long j) {
            this.zzaXA.zzjk();
            if (zzlO() == 0) {
                zzlL();
            }
            if (str == null) {
                str = "";
            }
            long j2 = this.zzaXA.zzTh.getLong(this.zzaXD, 0);
            if (j2 <= 0) {
                Editor edit = this.zzaXA.zzTh.edit();
                edit.putString(this.zzaXE, str);
                edit.putLong(this.zzaXD, j);
                edit.apply();
                return;
            }
            Object obj = (this.zzaXA.zzaXr.nextLong() & Long.MAX_VALUE) < (Long.MAX_VALUE / (j2 + j)) * j ? 1 : null;
            Editor edit2 = this.zzaXA.zzTh.edit();
            if (obj != null) {
                edit2.putString(this.zzaXE, str);
            }
            edit2.putLong(this.zzaXD, j2 + j);
            edit2.apply();
        }

        @WorkerThread
        public final Pair<String, Long> zzlN() {
            this.zzaXA.zzjk();
            long zzlM = zzlM();
            if (zzlM < this.zzTl) {
                return null;
            }
            if (zzlM > this.zzTl * 2) {
                zzlL();
                return null;
            }
            String string = this.zzaXA.zzCO().getString(this.zzaXE, null);
            zzlM = this.zzaXA.zzCO().getLong(this.zzaXD, 0);
            zzlL();
            return (string == null || zzlM <= 0) ? zzt.zzaXh : new Pair(string, Long.valueOf(zzlM));
        }
    }

    zzt(zzw com_google_android_gms_measurement_internal_zzw) {
        super(com_google_android_gms_measurement_internal_zzw);
    }

    @WorkerThread
    private SharedPreferences zzCO() {
        zzjk();
        zzjv();
        return this.zzTh;
    }

    @WorkerThread
    void setMeasurementEnabled(boolean z) {
        zzjk();
        zzAo().zzCK().zzj("Setting measurementEnabled", Boolean.valueOf(z));
        Editor edit = zzCO().edit();
        edit.putBoolean("measurement_enabled", z);
        edit.apply();
    }

    @WorkerThread
    boolean zzAr() {
        zzjk();
        return zzCO().getBoolean("measurement_enabled", !com.google.android.gms.measurement.zza.zzAs());
    }

    String zzCM() {
        this.zzaXr.nextBytes(new byte[16]);
        return String.format(Locale.US, "%032x", new Object[]{new BigInteger(1, r0)});
    }

    @WorkerThread
    long zzCN() {
        zzjv();
        zzjk();
        long j = this.zzaXn.get();
        if (j != 0) {
            return j;
        }
        j = (long) (this.zzaXr.nextInt(86400000) + 1);
        this.zzaXn.set(j);
        return j;
    }

    @WorkerThread
    Boolean zzCP() {
        zzjk();
        return !zzCO().contains("use_service") ? null : Boolean.valueOf(zzCO().getBoolean("use_service", false));
    }

    @WorkerThread
    protected String zzCQ() {
        zzjk();
        String string = zzCO().getString("previous_os_version", null);
        String zzCy = zzCh().zzCy();
        if (!(TextUtils.isEmpty(zzCy) || zzCy.equals(string))) {
            Editor edit = zzCO().edit();
            edit.putString("previous_os_version", zzCy);
            edit.apply();
        }
        return string;
    }

    @WorkerThread
    void zzar(boolean z) {
        zzjk();
        zzAo().zzCK().zzj("Setting useService", Boolean.valueOf(z));
        Editor edit = zzCO().edit();
        edit.putBoolean("use_service", z);
        edit.apply();
    }

    @WorkerThread
    Pair<String, Boolean> zzfh(String str) {
        zzjk();
        long elapsedRealtime = zzjl().elapsedRealtime();
        if (this.zzaXo != null && elapsedRealtime < this.zzaXq) {
            return new Pair(this.zzaXo, Boolean.valueOf(this.zzaXp));
        }
        this.zzaXq = elapsedRealtime + zzCp().zzeS(str);
        AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(true);
        try {
            Info advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(getContext());
            this.zzaXo = advertisingIdInfo.getId();
            this.zzaXp = advertisingIdInfo.isLimitAdTrackingEnabled();
        } catch (Throwable th) {
            zzAo().zzCJ().zzj("Unable to get advertising id", th);
            this.zzaXo = "";
        }
        AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(false);
        return new Pair(this.zzaXo, Boolean.valueOf(this.zzaXp));
    }

    String zzfi(String str) {
        String str2 = (String) zzfh(str).first;
        if (zzaj.zzbv("MD5") == null) {
            return null;
        }
        return String.format(Locale.US, "%032X", new Object[]{new BigInteger(1, zzaj.zzbv("MD5").digest(str2.getBytes()))});
    }

    protected void zziJ() {
        this.zzTh = getContext().getSharedPreferences("com.google.android.gms.measurement.prefs", 0);
        this.zzaXx = this.zzTh.getBoolean("has_been_opened", false);
        if (!this.zzaXx) {
            Editor edit = this.zzTh.edit();
            edit.putBoolean("has_been_opened", true);
            edit.apply();
        }
    }
}
